package com.rails.elasticsearch.utils;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.rails.elasticsearch.common.ComsumerConfig;

public class MQComsumerFactory {
	public static DefaultMQPushConsumer createPushComsumer(ComsumerConfig config) {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
		if (config.getConsumerGroup() != null) {
			consumer.setConsumerGroup(config.getConsumerGroup());
		}
		consumer.setNamesrvAddr(config.getNamesrvAddr());
		consumer.setSubscription(config.getSubscription());
		consumer.setPullBatchSize(config.getPullBatchSize());
		consumer.setConsumeMessageBatchMaxSize(config.getConsumeMessageBatchMaxSize());
		consumer.setConsumeFromWhere(config.getConsumeFromWhere());
		consumer.setClientCallbackExecutorThreads(config.getClientCallbackExecutorThreads());
		consumer.setConsumeThreadMax(config.getConsumeThreadMax());
		consumer.setConsumeThreadMin(config.getConsumeThreadMin());
		// consumer.setConsumeTimeout(config.getConsumeTimeout());
		consumer.setPullInterval(config.getPullInterval());
		consumer.setPersistConsumerOffsetInterval(config.getPersistConsumerOffsetInterval());
		consumer.setHeartbeatBrokerInterval(config.getHeartbeatBrokerInterval());
		consumer.setPullThresholdForQueue(config.getPullThresholdForQueue());
		return consumer;
	}

	public static DefaultMQPushConsumer createPushComsumerByConf(String prefix, ConfigService configService) {
		ComsumerConfig config = new ComsumerConfig();
		config.setConsumerGroup(configService.getProperty("mq." + prefix + ".group", ""));
		config.setNamesrvAddr(configService.getProperty("mq." + prefix + ".namesrvAddr", ""));
		config.setConsumeMessageBatchMaxSize(configService
				.getIntProperty("mq." + prefix + ".consumeMessageBatchMaxSize", Integer.valueOf(1)).intValue());
		config.setPullBatchSize(
				configService.getIntProperty("mq." + prefix + ".pullBatchSize", Integer.valueOf(32)).intValue());
		config.setConsumeThreadMin(
				configService.getIntProperty("mq." + prefix + ".minThread", Integer.valueOf(20)).intValue());
		config.setConsumeThreadMax(
				configService.getIntProperty("mq." + prefix + ".maxThread", Integer.valueOf(64)).intValue());
		config.setConsumeTimeout(
				configService.getIntProperty("mq." + prefix + ".consumeTimeout", Integer.valueOf(15)).intValue());
		config.setPullInterval(
				configService.getLongProperty("mq." + prefix + ".pullInterval", Long.valueOf(0L)).longValue());
		config.setPersistConsumerOffsetInterval(configService
				.getIntProperty("mq." + prefix + ".persistConsumerOffsetInterval", Integer.valueOf(5000)).intValue());
		config.setHeartbeatBrokerInterval(configService
				.getIntProperty("mq." + prefix + ".heartbeatBrokerInterval", Integer.valueOf(30000)).intValue());
		config.setPullThresholdForQueue(configService
				.getIntProperty("mq." + prefix + ".pullThresholdForQueue", Integer.valueOf(1000)).intValue());
		String consumeFromWhere = configService.getProperty("mq." + prefix + ".consumeFromWhere", "last");
		if ("last".equals(consumeFromWhere))
			config.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		else if ("timestamp".equals(consumeFromWhere)) {
			config.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
		}
		String[] topics = configService.getProperty("mq." + prefix + ".topics", "").split(",");
		for (String topic : topics) {
			String[] topicTags = topic.split("#");
			config.getSubscription().put(topicTags[0], topicTags[1]);
		}
		return createPushComsumer(config);
	}
}
