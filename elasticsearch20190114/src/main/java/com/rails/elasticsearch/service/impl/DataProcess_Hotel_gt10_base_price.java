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

@Service("DataProcess_Hotel_gt10_base_price")
public class DataProcess_Hotel_gt10_base_price implements MQ2Elasticsearch {

	private static final int bulkNum = 100;
	private Logger logger = LoggerFactory.getLogger(DataProcess_Hotel_gt10_base_price.class);
	private Logger log = LoggerFactory.getLogger("com.rails.searchengines.matlab");
	@Autowired
	private TransportClient client;

	@Override
	public boolean mqData2Elasticsearch(List<MessageRequest> datas) {

		int count = 0;
		int loopNum = 0;// 批量导入数据循环次数
		logger.info("DataProcess_Hotel_gt10_base_price需要导入的数据总条数为：" + datas.size() + ",以" + bulkNum + "为一个批次进行导入。");
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		BulkRequestBuilder requestBuilder = null;
		List<JSONObject> basePriceList = new ArrayList<>();
		for (int i = 0; i < datas.size(); i++) {
			JSONObject parseObject = JSONObject.parseObject(datas.get(i).getBody());
			if (parseObject != null) {
				// 格式化时间类型
				parseObject = JSONUtils.formatDate("yyyy-MM-dd HH:mm:ss.SSS", parseObject, "createTime");
				parseObject = JSONUtils.formatDate("yyyy-MM-dd HH:mm:ss.SSS", parseObject, "updateTime");

				basePriceList.add(parseObject);
				// 和hotel构建es父子关系
				Map<String, Object> join = new HashMap<>();
				join.put("name", "basePrice");
				join.put("parent", parseObject.get("hotelId") + "_" + parseObject.get("city"));
				parseObject.put("hotelJoin", join);
				// 构建base_price子文档id
				String basePriceId = parseObject.get("hotelId") + "_" + parseObject.get("city") + "_"
						+ parseObject.get("bizdate");

				// 批量往ES导入hotel数据
				IndexRequestBuilder setSource = client.prepareIndex("hotel", "hotel", basePriceId)
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
						log.error("DataProcess_Hotel_gt10_base_price批量导入失败的数据为：" + collect);
						logger.error("DataProcess_Hotel_gt10_base_price批量导入失败的数据为：" + collect);
					} else {
						logger.info("DataProcess_Hotel_gt10_base_price批量导入的数据总条数为：" + bulkNum * loopNum + ",状态为："
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
			log.error("DataProcess_Hotel_gt10_base_price批量导入后剩余的数据导入失败的数据为：" + collect);
			logger.error("DataProcess_Hotel_gt10_base_price批量导入后剩余的数据导入失败的数据为：" + collect);
		} else {
			logger.info("DataProcess_Hotel_gt10_base_price批量导入后剩余的数据导入的总条数为：" + (count - bulkNum * loopNum) + ",状态为："
					+ response.status());
		}
		// 往suggest导入BasePrice数据
		basePriceChild(basePriceList);
		return false;
	}

	/**
	 * 往suggest导入BasePrice数据
	 * 
	 * @param parseObject
	 */
	public void basePriceChild(List<JSONObject> basePriceList) {
		List<SuggestEntity> basePriceSuggestEntityList = new ArrayList<>();

		basePriceList.stream().forEach(e -> {
			SuggestEntity basePriceSuggestEntity = new SuggestEntity();
			JSONObject basePrice = new JSONObject();
			basePrice.put("createTime", e.get("createTime"));
			basePrice.put("updateTime", e.get("updateTime"));
			basePrice.put("itemId", e.getString("hotelId") + "_" + e.getString("city") + "_" + e.getString("bizdate"));
			basePrice.put("basePrice", e.get("basePrice"));
			basePrice.put("hotelId", e.get("hotelId"));

			// 构建suggest-base_pricce子文档
			Map<String, Object> suggestJoinMap = new HashMap<>();
			suggestJoinMap.put("name", "basePrice");
			suggestJoinMap.put("parent", e.get("hotelId") + "_" + e.get("city"));
			basePrice.put("suggestJoin", suggestJoinMap);
			basePrice.put("typeClass", "hotel");

			String id = "base_price" + "_" + basePrice.get("itemId");
			String routing = e.get("hotelId") + "_" + e.get("city");
			basePriceSuggestEntity.setId(id);
			basePriceSuggestEntity.setJsonObject(basePrice);
			basePriceSuggestEntity.setRouting(routing);

			basePriceSuggestEntityList.add(basePriceSuggestEntity);
		});
		// 往suggest导入数据
		new KeywordSuggest().getKeywordSuggest(
				basePriceSuggestEntityList.stream().distinct().collect(Collectors.toList()), client, "basePrice");
	}
}
