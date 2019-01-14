/**
 * Copyright 2018 电子计算技术研究所
 * Author：WenLi
 * 创建日期：2018年8月10日
 */
package com.rails.elasticsearch.consumer.mq;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.rails.elasticsearch.utils.ConfigService;
import com.rails.elasticsearch.utils.MQComsumerFactory;

@Service("mqMessageConsumerService")
public class MqMessageConsumerService {
	Logger logger = LoggerFactory.getLogger(getClass());

	DefaultMQPushConsumer defaultMQPushConsumer;

	public void start() {
		try {
			defaultMQPushConsumer.start();
			logger.info("##########################启动消费程序##########################");
		} catch (MQClientException e) {
			logger.error("" + e);
		}
	}

	@Autowired
	ConfigService configService;
	@Autowired
	MqMessageListener mqMessageListener;

	@PostConstruct
	void initData() {
		defaultMQPushConsumer = MQComsumerFactory.createPushComsumerByConf("consumer", configService);
		defaultMQPushConsumer.registerMessageListener(mqMessageListener);
	}

	@PreDestroy
	void destroy() {
		defaultMQPushConsumer.shutdown();
	}
}
