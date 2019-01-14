/**
 * Copyright 2018 电子计算技术研究所
 * Author：WenLi
 * 创建日期：2018年8月13日
 */
package com.rails.elasticsearch.consumer.mq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.rails.elasticsearch.common.ConsumerMqTpsCollect;
import com.rails.elasticsearch.common.MessageInfo;
import com.rails.elasticsearch.common.MessageRequest;
import com.rails.elasticsearch.common.SpringBeanUtils;
import com.rails.elasticsearch.service.MQ2Elasticsearch;
import com.rails.elasticsearch.utils.ConfigService;

@Service
public class MqMessageListener implements MessageListenerConcurrently {
	Logger logger = LoggerFactory.getLogger(getClass());

	AtomicLong sendTotal = new AtomicLong();
	AtomicLong sendFailTotal = new AtomicLong();

	String consumerGroup;
	@Autowired
	ConfigService configService;

	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		long start = System.currentTimeMillis();
		Map<String, List<MessageRequest>> map = new HashMap<String, List<MessageRequest>>();
		for (MessageExt messageExt : msgs) {
			ConsumerMqTpsCollect.acquire(consumerGroup);
			sendTotal.incrementAndGet();
			MessageInfo messageInfo = null;

			try {
				JSONObject json = JSONObject.parseObject(new String(messageExt.getBody()));
				messageInfo = JSONObject.toJavaObject(json, MessageInfo.class);
				messageInfo.getMessageRequest().setMessage_id(messageExt.getMsgId());
			} catch (Exception e) {
				sendFailTotal.incrementAndGet();
				logger.error("解析消息失败,msgId:" + messageExt.getMsgId(), e);
				continue;
			}

			if (null != messageInfo) {
				String topic = messageInfo.getTopic();
				if (null == map.get(topic)) {
					map.put(topic, new ArrayList<MessageRequest>());
				}
				map.get(topic).add(messageInfo.getMessageRequest());
			}
		}
		try {
			Iterator<String> topics = map.keySet().iterator();
			while (topics.hasNext()) {
				String key = topics.next();
				String beanKey = "DataProcess_" + key;
				MQ2Elasticsearch dataProcessService = SpringBeanUtils.getBean(beanKey);
				if (null != dataProcessService) {
					dataProcessService.mqData2Elasticsearch(map.get(key));
				} else {
					logger.warn("没有找到对应的处理实现：" + beanKey);
				}
			}
		} catch (Exception e) {
			logger.error("导入消息失败", e);
		}
		logger.info(String.format("%s-收到消息数量：%s , 总量：%s , 失败： %s , 处理时间：%s", Thread.currentThread().getName(),
				msgs.size(), sendTotal.get(), sendFailTotal.get(), System.currentTimeMillis() - start));
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	@PostConstruct
	void initData() {
		consumerGroup = configService.getProperty("mq.consumer.group", "");
		String topices = configService.getProperty("mq.consumer.topics", "");
		ConsumerMqTpsCollect.registeConsumeLimit(consumerGroup, topices, 500);
	}
}
