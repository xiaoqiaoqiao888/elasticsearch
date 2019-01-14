/**
 * Copyright 2018 电子计算技术研究所
 * Author：WenLi
 * 创建日期：2018年8月13日
 */
package com.rails.elasticsearch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

@Service("DataProcess_Hotel_gt10_hotel_adapter")
public class DataProcess_Hotel_gt10_hotel_adapter implements MQ2Elasticsearch {

	private static final int bulkNum = 100;
	private Logger logger = LoggerFactory.getLogger(DataProcess_Hotel_gt10_hotel_adapter.class);
	private Logger log = LoggerFactory.getLogger("com.rails.searchengines.matlab");
	@Autowired
	private TransportClient client;

	@Override
	public boolean mqData2Elasticsearch(List<MessageRequest> datas) {
		int count = 0;
		int loopNum = 0;// 批量导入数据循环次数
		logger.info(
				"DataProcess_Hotel_gt10_hotel_adapter需要导入的数据总条数为：" + datas.size() + "条,以" + bulkNum + "条为一个批次进行导入。");
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		BulkRequestBuilder requestBuilder = null;
		List<JSONObject> hotelList = new ArrayList<>();
		for (int i = 0; i < datas.size(); i++) {
			JSONObject parseObject = JSONObject.parseObject(datas.get(i).getBody());
			if (parseObject != null) {
				// 格式化时间类型
				parseObject = JSONUtils.formatDate("yyyy-MM-dd HH:mm:ss.SSS", parseObject, "versionNo");
				parseObject = JSONUtils.formatDate("yyyy-MM-dd HH:mm:ss.SSS", parseObject, "createTime");
				parseObject = JSONUtils.formatDate("yyyy-MM-dd HH:mm:ss.SSS", parseObject, "latestOrderTime");

				parseObject = JSONUtils.getNested(parseObject, "businessAreaCode", "businessArea", "businessAreaName");

				parseObject = JSONUtils.getNested(parseObject, "oldBusinessAreaCode", "oldBusinessArea",
						"oldBusinessAreaName");

				parseObject = JSONUtils.getNested(parseObject, "landmarkCode", "landmark", "landmarkName");

				parseObject = JSONUtils.getNested(parseObject, "oldLandmarkCode", "oldLandmark", "oldLandmarkName");

				parseObject = JSONUtils.getLocation(parseObject, "lat", "latitude", "lon", "longitude", "location");
				hotelList.add(parseObject);

				// 构建hotel父文档
				Map<String, Object> join = new HashMap<>();
				join.put("name", "hotel");
				parseObject.put("hotelJoin", join);

				// 批量往ES导入数据
				IndexRequestBuilder setSource = client
						.prepareIndex("hotel", "hotel", parseObject.get("hotelId") + "_" + parseObject.get("city"))
						.setRouting(parseObject.getString("hotelId"))
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
						log.error("DataProcess_Hotel_gt10_hotel_adapter批量导入失败的数据为：" + collect);
						logger.error("DataProcess_Hotel_gt10_hotel_adapter批量导入失败的数据为：" + collect);
					} else {
						logger.info("DataProcess_Hotel_gt10_hotel_adapter批量导入的数据总条数为：" + bulkNum * loopNum + ",状态为："
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
			log.error("DataProcess_Hotel_gt10_hotel_adapter批量导入后剩余的数据导入失败的数据为：" + collect);
			logger.error("DataProcess_Hotel_gt10_hotel_adapter批量导入后剩余的数据导入失败的数据为：" + collect);
		} else {
			logger.info("DataProcess_Hotel_gt10_hotel_adapter批量导入后剩余的数据导入的总条数为：" + (count - bulkNum * loopNum) + ",状态为："
					+ response.status());
		}
		// 往suggest导入Hotel数据
		hotelChild(hotelList);
		return false;
	}

	/**
	 * 往suggest导入Hotel数据
	 * 
	 * @param parseObject
	 */
	public void hotelChild(List<JSONObject> hotelList) {
		List<SuggestEntity> chainBrandSuggestEntityList = new ArrayList<>();
		List<SuggestEntity> hotelSuggestEntityList = new ArrayList<>();

		hotelList.stream().forEach(e -> {
			SuggestEntity chainBrandSuggestEntity = new SuggestEntity();
			SuggestEntity hotelSuggestEntity = new SuggestEntity();
			// 封装chainBrand数据
			JSONObject chainBrand = new JSONObject();

			chainBrand.put("itemId", e.getString("chainBrand"));
			chainBrand.put("itemCode", e.getString("chainBrand"));
			chainBrand.put("itemName", e.getString("chainBrandName"));

			chainBrand.put("cityCode", e.get("city"));
			chainBrand.put("cityName", e.get("cityName"));
			chainBrand.put("typeClass", "chainBrand");

			String chainBrandId = "chainBrand" + "_" + e.get("chainBrand") + "_" + e.get("city");
			chainBrandSuggestEntity.setId(chainBrandId);
			chainBrandSuggestEntity.setJsonObject(chainBrand);
			chainBrandSuggestEntity.setRouting("");

			chainBrandSuggestEntityList.add(chainBrandSuggestEntity);

			// 封装hotel数据
			JSONObject hotel = new JSONObject();
			hotel.put("itemId", e.getString("hotelId") + "_" + e.getString("city"));
			hotel.put("itemName", e.getString("hotelName"));
			hotel.put("itemNameEn", e.getString("hotelNameEn"));

			hotel = getKeywordSuggestObject(hotel, e);
			hotel.put("typeClass", "hotel");
			// 构建suggest父文档
			Map<String, Object> suggestJoin = new HashMap<>();
			suggestJoin.put("name", "suggest");
			hotel.put("suggestJoin", suggestJoin);

			String hotelId = e.get("hotelId") + "_" + e.get("city");
			String hotelRouting = e.get("hotelId") + "_" + e.get("city");
			hotelSuggestEntity.setId(hotelId);
			hotelSuggestEntity.setJsonObject(hotel);
			hotelSuggestEntity.setRouting(hotelRouting);

			hotelSuggestEntityList.add(hotelSuggestEntity);
		});
		// 往suggest导入chainBrand数据
		new KeywordSuggest().getKeywordSuggest(
				chainBrandSuggestEntityList.stream().distinct().collect(Collectors.toList()), client, "chainBrand");
		// 往suggest导入hotel数据
		new KeywordSuggest().getKeywordSuggest(hotelSuggestEntityList.stream().distinct().collect(Collectors.toList()),
				client, "hotel");
	}

	/**
	 * 封装属性
	 * 
	 * @param keywordSuggest
	 * @param parseObject
	 * @return
	 */
	private JSONObject getKeywordSuggestObject(JSONObject hotel, JSONObject parseObject) {
		hotel.put("location", parseObject.get("location"));
		hotel.put("priority", parseObject.get("priority"));
		hotel.put("vaildFlag", parseObject.get("validFlag"));
		hotel.put("examineType", parseObject.get("examineType"));
		hotel.put("hotelState", parseObject.get("hotelState"));
		hotel.put("cityCode", parseObject.get("city"));
		hotel.put("cityName", parseObject.get("cityName"));

		hotel.put("hotelId", parseObject.get("hotelId"));
		hotel.put("hotelName", parseObject.get("hotelName"));
		hotel.put("provinceCode", parseObject.get("province"));
		hotel.put("provinceName", parseObject.get("provinceName"));
		hotel.put("countryCode", parseObject.get("country"));
		hotel.put("countryName", parseObject.get("countryName"));
		hotel.put("countyCode", parseObject.get("countyCode"));
		hotel.put("countyName", parseObject.get("countyName"));
		hotel.put("chainBrand", parseObject.get("chainBrand"));
		hotel.put("chainBrandName", parseObject.get("chainBrandName"));
		hotel.put("starLevel", parseObject.get("starLevel"));

		hotel.put("landmark", parseObject.get("landmark"));
		hotel.put("businessArea", parseObject.get("businessArea"));
		hotel.put("versionNo", parseObject.get("versionNo"));
		hotel.put("createTime", parseObject.get("createTime"));
		hotel.put("latestOrderTime", parseObject.get("latestOrderTime"));
		hotel.put("hotelScore", parseObject.get("hotelScore"));
		hotel.put("supportCard", parseObject.get("supportCard"));
		hotel.put("dawnFlag", parseObject.get("dawnFlag"));
		hotel.put("isSmokeless", parseObject.get("isSmokeless"));
		hotel.put("supportCurrency", parseObject.get("supportCurrency"));
		hotel.put("hotelFacility", parseObject.get("hotelFacility"));
		hotel.put("hotelPolicy", parseObject.get("hotelPolicy"));
		hotel.put("tags", parseObject.get("tags"));

		return hotel;
	}
}
