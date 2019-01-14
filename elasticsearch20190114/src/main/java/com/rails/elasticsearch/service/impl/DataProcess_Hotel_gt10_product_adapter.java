/**
 * Copyright 2018 电子计算技术研究所
 * Author：WenLi
 * 创建日期：2018年8月13日
 */
package com.rails.elasticsearch.service.impl;

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

@Service("DataProcess_Hotel_gt10_product_adapter")
public class DataProcess_Hotel_gt10_product_adapter implements MQ2Elasticsearch {
	private static final int bulkNum = 100;
	private Logger logger = LoggerFactory.getLogger(DataProcess_Hotel_gt10_product_adapter.class);
	private Logger log = LoggerFactory.getLogger("com.rails.searchengines.matlab");
	@Autowired
	private TransportClient client;

	@Override
	public boolean mqData2Elasticsearch(List<MessageRequest> datas) {
		int count = 0;
		int loopNum = 0;// 批量导入数据循环次数
		logger.info(
				"DataProcess_Hotel_gt10_product_adapter需要导入的数据总条数为：" + datas.size() + "条,以" + bulkNum + "条为一个批次进行导入。");
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		BulkRequestBuilder requestBuilder = null;
		for (int i = 0; i < datas.size(); i++) {
			JSONObject parseObject = JSONObject.parseObject(datas.get(i).getBody());
			if (parseObject != null) {
				// 格式化时间类型
				parseObject = JSONUtils.formatDate("yyyy-MM-dd HH:mm:ss.SSS", parseObject, "versionNo");
				parseObject = JSONUtils.formatDate("yyyy-MM-dd HH:mm:ss.SSS", parseObject, "createTime");

				String productId = parseObject.getString("productId") + "_" + parseObject.getString("city");

				IndexRequestBuilder setSource = client.prepareIndex("product", "product", productId)
						.setRouting(productId).setSource(parseObject.toJSONString(), XContentType.JSON);
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
						log.error("DataProcess_Hotel_gt10_product_adapter批量导入失败的数据为：" + collect);
						logger.error("DataProcess_Hotel_gt10_product_adapter批量导入失败的数据为：" + collect);
					} else {
						logger.info("DataProcess_Hotel_gt10_product_adapter批量导入的数据总条数为：" + bulkNum * loopNum + ",状态为："
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
			log.error("DataProcess_Hotel_gt10_product_adapter批量导入后剩余的数据导入失败的数据为：" + collect);
			logger.error("DataProcess_Hotel_gt10_product_adapter批量导入后剩余的数据导入失败的数据为：" + collect);
		} else {
			logger.info("DataProcess_Hotel_gt10_product_adapter批量导入后剩余的数据导入的总条数为：" + (count - bulkNum * loopNum)
					+ ",状态为：" + response.status());
		}
		return false;
	}
}