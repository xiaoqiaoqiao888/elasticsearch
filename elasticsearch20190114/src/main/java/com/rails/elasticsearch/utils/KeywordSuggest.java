package com.rails.elasticsearch.utils;

import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class KeywordSuggest {
	private Logger logger = LoggerFactory.getLogger(KeywordSuggest.class);
	private Logger log = LoggerFactory.getLogger("com.rails.searchengines.matlab");
	private static final int bulkNum = 1000;

	public boolean getKeywordSuggest(List<SuggestEntity> suggestEntityList, TransportClient client, String string) {

		int count = 0;
		int loopNum = 0;// 批量导入数据循环次数
		logger.info("KeywordSuggest--" + string + "--需要导入的数据总条数为：" + suggestEntityList.size() + "条,以" + bulkNum
				+ "条为一个批次进行导入。");
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		BulkRequestBuilder requestBuilder = null;
		for (int i = 0; i < suggestEntityList.size(); i++) {
			JSONObject parseObject = suggestEntityList.get(i).getJsonObject();
			if (parseObject != null) {

				IndexRequestBuilder setSource = client
						.prepareIndex("suggest", "suggest", suggestEntityList.get(i).getId())
						.setRouting(suggestEntityList.get(i).getRouting())
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
						List<SuggestEntity> subList = suggestEntityList.subList(bulkNum * (loopNum - 1),
								bulkNum * loopNum);
						log.error("KeywordSuggest--" + string + "--批量导入失败的数据为：" + subList);
						logger.error("KeywordSuggest--" + string + "--批量导入失败的数据为：" + subList);
					} else {
						logger.info("KeywordSuggest--" + string + "--批量导入的数据总条数为：" + bulkNum * loopNum + ",状态为："
								+ response.status());
					}
				}
			}
		}
		// 批量导入后剩余的数据
		BulkResponse response = requestBuilder.get();
		if (response.hasFailures()) {
			// 数据导入失败时，打印这个批次全部的数据
			List<SuggestEntity> subList = suggestEntityList.subList(bulkNum * loopNum, suggestEntityList.size());
			log.error("KeywordSuggest--" + string + "--批量导入后剩余的数据导入失败的数据为：" + subList);
			logger.error("KeywordSuggest--" + string + "--批量导入后剩余的数据导入失败的数据为：" + subList);
		} else {
			logger.info("KeywordSuggest--" + string + "--批量导入后剩余的数据导入的总条数为：" + (count - bulkNum * loopNum) + ",状态为："
					+ response.status());
		}
		return false;

	}
}
