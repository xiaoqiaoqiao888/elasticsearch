package com.rails.elasticsearch.common;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;

public class ComsumerConfig {
	private String consumerGroup;
	private String namesrvAddr;
	private Map<String, String> subscription = new HashMap<String, String>();
	private int consumeMessageBatchMaxSize = 1;
	private int clientCallbackExecutorThreads = Runtime.getRuntime().availableProcessors();
	private int consumeThreadMin = 20;
	private int consumeThreadMax = 64;
	private long consumeTimeout = 15L;
	private int pullBatchSize = 32;
	private long pullInterval = 0L;
	private ConsumeFromWhere consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET;
	private int persistConsumerOffsetInterval = 5000;
	private int heartbeatBrokerInterval = 30000;
	private int pullThresholdForQueue = 1000;

	public String getConsumerGroup() {
		return this.consumerGroup;
	}

	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}

	public String getNamesrvAddr() {
		return this.namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public Map<String, String> getSubscription() {
		return this.subscription;
	}

	public void setSubscription(Map<String, String> subscription) {
		this.subscription = subscription;
	}

	public int getConsumeMessageBatchMaxSize() {
		return this.consumeMessageBatchMaxSize;
	}

	public void setConsumeMessageBatchMaxSize(int consumeMessageBatchMaxSize) {
		this.consumeMessageBatchMaxSize = consumeMessageBatchMaxSize;
	}

	public int getClientCallbackExecutorThreads() {
		return this.clientCallbackExecutorThreads;
	}

	public void setClientCallbackExecutorThreads(int clientCallbackExecutorThreads) {
		this.clientCallbackExecutorThreads = clientCallbackExecutorThreads;
	}

	public int getConsumeThreadMin() {
		return this.consumeThreadMin;
	}

	public void setConsumeThreadMin(int consumeThreadMin) {
		this.consumeThreadMin = consumeThreadMin;
	}

	public int getConsumeThreadMax() {
		return this.consumeThreadMax;
	}

	public void setConsumeThreadMax(int consumeThreadMax) {
		this.consumeThreadMax = consumeThreadMax;
	}

	public long getConsumeTimeout() {
		return this.consumeTimeout;
	}

	public void setConsumeTimeout(long consumeTimeout) {
		this.consumeTimeout = consumeTimeout;
	}

	public ConsumeFromWhere getConsumeFromWhere() {
		return this.consumeFromWhere;
	}

	public void setConsumeFromWhere(ConsumeFromWhere consumeFromWhere) {
		this.consumeFromWhere = consumeFromWhere;
	}

	public int getPullBatchSize() {
		return this.pullBatchSize;
	}

	public void setPullBatchSize(int pullBatchSize) {
		this.pullBatchSize = pullBatchSize;
	}

	public long getPullInterval() {
		return this.pullInterval;
	}

	public void setPullInterval(long pullInterval) {
		this.pullInterval = pullInterval;
	}

	public int getPersistConsumerOffsetInterval() {
		return this.persistConsumerOffsetInterval;
	}

	public void setPersistConsumerOffsetInterval(int persistConsumerOffsetInterval) {
		this.persistConsumerOffsetInterval = persistConsumerOffsetInterval;
	}

	public int getHeartbeatBrokerInterval() {
		return this.heartbeatBrokerInterval;
	}

	public void setHeartbeatBrokerInterval(int heartbeatBrokerInterval) {
		this.heartbeatBrokerInterval = heartbeatBrokerInterval;
	}

	public int getPullThresholdForQueue() {
		return this.pullThresholdForQueue;
	}

	public void setPullThresholdForQueue(int pullThresholdForQueue) {
		this.pullThresholdForQueue = pullThresholdForQueue;
	}

	public String toString() {
		return "ComsumerConfig [consumerGroup=" + this.consumerGroup + ", namesrvAddr=" + this.namesrvAddr
				+ ", subscription=" + this.subscription + ", consumeMessageBatchMaxSize="
				+ this.consumeMessageBatchMaxSize + ", clientCallbackExecutorThreads="
				+ this.clientCallbackExecutorThreads + ", consumeThreadMin=" + this.consumeThreadMin
				+ ", consumeThreadMax=" + this.consumeThreadMax + ", consumeTimeout=" + this.consumeTimeout
				+ ", pullBatchSize=" + this.pullBatchSize + ", pullInterval=" + this.pullInterval
				+ ", consumeFromWhere=" + this.consumeFromWhere + ", persistConsumerOffsetInterval="
				+ this.persistConsumerOffsetInterval + ", heartbeatBrokerInterval=" + this.heartbeatBrokerInterval
				+ ", pullThresholdForQueue=" + this.pullThresholdForQueue + "]";
	}
}
