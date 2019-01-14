/**
 * Copyright 2018 电子计算技术研究所
 * Author：WenLi
 * 创建日期：2018年8月13日
 */
package com.rails.elasticsearch.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.rails.elasticsearch.common.MessageRequest;
import com.rails.elasticsearch.service.MQ2Elasticsearch;
import com.rails.elasticsearch.utils.JSONUtils;
import com.rails.elasticsearch.utils.KeywordSuggest;
import com.rails.elasticsearch.utils.SuggestEntity;

@Service("DataProcess_Hotel_gt10_landmark")
public class DataProcess_Hotel_gt10_landmark implements MQ2Elasticsearch {
	private static final int bulkNum = 100;
	private Logger logger = LoggerFactory.getLogger(DataProcess_Hotel_gt10_business_area.class);
	private Logger log = LoggerFactory.getLogger("com.rails.searchengines.matlab");
	@Autowired
	private TransportClient client;

	@Override
	public boolean mqData2Elasticsearch(List<MessageRequest> datas) {

		int count = 0;
		int loopNum = 0;// 批量导入数据循环次数
		logger.info("DataProcess_Hotel_gt10_landmark需要导入的数据总条数为：" + datas.size() + "条,以" + bulkNum + "条为一个批次进行导入。");
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		BulkRequestBuilder requestBuilder = null;
		List<JSONObject> landmarkList = new ArrayList<>();
		for (int i = 0; i < datas.size(); i++) {
			JSONObject parseObject = JSONObject.parseObject(datas.get(i).getBody());
			if (parseObject != null) {
				parseObject = JSONUtils.getLocation(parseObject, "lat", "lat", "lon", "lng", "location");

				landmarkList.add(parseObject);
				// 批量往ES导入数据
				IndexRequestBuilder setSource = client
						.prepareIndex("landmark", "landmark", parseObject.getString("landmarkCode"))
						.setRouting(parseObject.getString("landmarkCode"))
						.setSource(parseObject.toJSONString(), XContentType.JSON);
				requestBuilder = bulkRequest.add(setSource);
				count++;
				// 每100条数据进行批量导入
				if (count % bulkNum == 0) {
					loopNum++;
					BulkResponse response = requestBuilder.get();
					bulkRequest = client.prepareBulk();
					if (response.hasFailures()) {
						// 数据导入失败时，打印这个批次全部的数据
						List<String> collect = datas.subList(bulkNum * (loopNum - 1), bulkNum * loopNum).stream()
								.map(e -> e.getBody()).collect(Collectors.toList());
						log.error("DataProcess_Hotel_gt10_landmark批量导入失败的数据为：" + collect);
						logger.error("DataProcess_Hotel_gt10_landmark批量导入失败的数据为：" + collect);
					} else {
						logger.info("DataProcess_Hotel_gt10_landmark批量导入的数据总条数为：" + bulkNum * loopNum + ",状态为："
								+ response.status());
					}
				}
			}
		}
		// 批量导入后剩余的数据
		BulkResponse response = requestBuilder.get();
		if (response.hasFailures()) {
			// 数据导入失败时，打印这个批次全部的数据
			List<String> collect = datas.subList(bulkNum * loopNum, datas.size()).stream().map(e -> e.getBody())
					.collect(Collectors.toList());
			log.error("DataProcess_Hotel_gt10_landmark批量导入后剩余的数据导入失败的数据为：" + collect);
			logger.error("DataProcess_Hotel_gt10_landmark批量导入后剩余的数据导入失败的数据为：" + collect);
		} else {
			logger.info("DataProcess_Hotel_gt10_landmark批量导入后剩余的数据导入的总条数为：" + (count - bulkNum * loopNum) + ",状态为："
					+ response.status());
		}
		// 往suggest导入Landmark数据
		landmarkChild(landmarkList);
		return false;
	}

	/**
	 * 往suggest导入Landmark数据
	 * 
	 * @param parseObject
	 */
	public void landmarkChild(List<JSONObject> landmarkList) {
		List<SuggestEntity> subwayLineSuggestEntityList = new ArrayList<>();

		List<SuggestEntity> landmarkSuggestEntityList = new ArrayList<>();
		landmarkList.stream().forEach(e -> {
			SuggestEntity subwayLineSuggestEntity = new SuggestEntity();
			SuggestEntity landmarkSuggestEntity = new SuggestEntity();

			JSONObject landmark = new JSONObject();
			landmark.put("location", e.get("location"));
			landmark.put("priority", e.get("priority"));
			landmark.put("vaildFlag", e.get("validFlag"));
			landmark.put("itemCode", e.getString("landmarkCode"));
			landmark.put("itemId", e.getString("landmarkCode"));
			landmark.put("hotFlag", e.get("hotFlag"));
			landmark.put("cityCode", e.get("cityCode"));
			landmark.put("cityName", e.get("cityName"));

			String str = e.getString("landmarkType");
			if (str != null && str.length() > 0) {
				int strParse = Integer.parseInt(str);
				if (1 == strParse) {
					landmark.put("itemName", e.getString("landmarkName"));
					landmark.put("typeClass", "airport");
				}
				if (2 == strParse) {
					landmark.put("itemName", e.getString("landmarkName"));
					landmark.put("typeClass", "station");
				}
				if (3 == strParse || 4 == strParse) {
					landmark.put("itemName", e.getString("landmarkName"));
					landmark.put("typeClass", "landMark");
				}
				if (5 == strParse) {
					landmark.put("itemName",
							e.getString("landmarkName") + "地铁站" + "(" + e.getString("belongName") + ")");
					landmark.put("typeClass", "subwayStation");

					JSONObject subwayLine = new JSONObject();
					subwayLine.put("priority", e.get("priority"));
					subwayLine.put("vaildFlag", e.get("validFlag"));
					subwayLine.put("hotFlag", e.get("hotFlag"));
					subwayLine.put("itemId", e.getString("belongCode"));
					subwayLine.put("itemCode", e.getString("belongCode"));
					subwayLine.put("itemName", e.getString("belongName"));
					subwayLine.put("typeClass", "subwayLine");

					String subwayLineId = "subwayLine" + "_" + e.get("belongCode") + "_" + e.get("cityCode");
					subwayLineSuggestEntity.setId(subwayLineId);
					subwayLineSuggestEntity.setJsonObject(subwayLine);
					subwayLineSuggestEntity.setRouting("");

					subwayLineSuggestEntityList.add(subwayLineSuggestEntity);
				}
				if (6 == strParse) {
					landmark.put("itemName", e.getString("landmarkName"));
					landmark.put("typeClass", "district");
				}
			}
			String landmarkId = e.getString("landmarkCode");
			landmarkSuggestEntity.setId(landmarkId);
			landmarkSuggestEntity.setJsonObject(landmark);
			landmarkSuggestEntity.setRouting("");

			landmarkSuggestEntityList.add(landmarkSuggestEntity);
		});
		// 往suggest导入subwayLine数据
		new KeywordSuggest().getKeywordSuggest(
				subwayLineSuggestEntityList.stream().distinct().collect(Collectors.toList()), client, "subwayLine");
		// 往suggest导入数据
		new KeywordSuggest().getKeywordSuggest(
				landmarkSuggestEntityList.stream().distinct().collect(Collectors.toList()), client, "landmark");
	}
}