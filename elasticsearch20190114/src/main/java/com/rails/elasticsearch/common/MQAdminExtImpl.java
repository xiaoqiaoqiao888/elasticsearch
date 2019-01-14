package com.rails.elasticsearch.common;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.rocketmq.client.QueryResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.MQAdminImpl;
import org.apache.rocketmq.common.TopicConfig;
import org.apache.rocketmq.common.admin.ConsumeStats;
import org.apache.rocketmq.common.admin.RollbackStats;
import org.apache.rocketmq.common.admin.TopicStatsTable;
import org.apache.rocketmq.common.message.MessageClientIDSetter;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.body.BrokerStatsData;
import org.apache.rocketmq.common.protocol.body.ClusterInfo;
import org.apache.rocketmq.common.protocol.body.ConsumeMessageDirectlyResult;
import org.apache.rocketmq.common.protocol.body.ConsumeStatsList;
import org.apache.rocketmq.common.protocol.body.ConsumerConnection;
import org.apache.rocketmq.common.protocol.body.ConsumerRunningInfo;
import org.apache.rocketmq.common.protocol.body.GroupList;
import org.apache.rocketmq.common.protocol.body.KVTable;
import org.apache.rocketmq.common.protocol.body.ProducerConnection;
import org.apache.rocketmq.common.protocol.body.QueueTimeSpan;
import org.apache.rocketmq.common.protocol.body.SubscriptionGroupWrapper;
import org.apache.rocketmq.common.protocol.body.TopicConfigSerializeWrapper;
import org.apache.rocketmq.common.protocol.body.TopicList;
import org.apache.rocketmq.common.protocol.route.TopicRouteData;
import org.apache.rocketmq.common.subscription.SubscriptionGroupConfig;
import org.apache.rocketmq.remoting.RemotingClient;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;
import org.apache.rocketmq.remoting.exception.RemotingConnectException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.remoting.exception.RemotingSendRequestException;
import org.apache.rocketmq.remoting.exception.RemotingTimeoutException;
import org.apache.rocketmq.remoting.protocol.RemotingCommand;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;
import org.apache.rocketmq.tools.admin.MQAdminExt;
import org.apache.rocketmq.tools.admin.api.MessageTrack;
import org.joor.Reflect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.base.Throwables;
import com.rails.rpc.invoke.rocketmq.MQAdminInstance;

@Service
public class MQAdminExtImpl implements MQAdminExt {
	Logger logger = LoggerFactory.getLogger(getClass());

	public void updateBrokerConfig(String brokerAddr, Properties properties)
			throws RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException,
			UnsupportedEncodingException, InterruptedException, MQBrokerException {
		MQAdminInstance.threadLocalMQAdminExt().updateBrokerConfig(brokerAddr, properties);
	}

	public void createAndUpdateTopicConfig(String addr, TopicConfig config)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		MQAdminInstance.threadLocalMQAdminExt().createAndUpdateTopicConfig(addr, config);
	}

	public void createAndUpdateSubscriptionGroupConfig(String addr, SubscriptionGroupConfig config)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		MQAdminInstance.threadLocalMQAdminExt().createAndUpdateSubscriptionGroupConfig(addr, config);
	}

	public SubscriptionGroupConfig examineSubscriptionGroupConfig(String addr, String group) {
		RemotingClient remotingClient = MQAdminInstance.threadLocalRemotingClient();
		RemotingCommand request = RemotingCommand.createRequestCommand(201, null);
		RemotingCommand response = null;
		try {
			response = remotingClient.invokeSync(addr, request, 3000L);
		} catch (Exception err) {
			logger.error("" + err);
		}
		assert (response != null);
		switch (response.getCode()) {
		case 0:
			SubscriptionGroupWrapper subscriptionGroupWrapper = (SubscriptionGroupWrapper) RemotingSerializable
					.decode(response.getBody(), SubscriptionGroupWrapper.class);
			return (SubscriptionGroupConfig) subscriptionGroupWrapper.getSubscriptionGroupTable().get(group);
		}

		throw Throwables.propagate(new MQBrokerException(response.getCode(), response.getRemark()));
	}

	public TopicConfig examineTopicConfig(String addr, String topic) {
		RemotingClient remotingClient = MQAdminInstance.threadLocalRemotingClient();
		RemotingCommand request = RemotingCommand.createRequestCommand(21, null);
		RemotingCommand response = null;
		try {
			response = remotingClient.invokeSync(addr, request, 3000L);
		} catch (Exception err) {
			throw Throwables.propagate(err);
		}
		switch (response.getCode()) {
		case 0:
			TopicConfigSerializeWrapper topicConfigSerializeWrapper = (TopicConfigSerializeWrapper) RemotingSerializable
					.decode(response.getBody(), TopicConfigSerializeWrapper.class);
			return (TopicConfig) topicConfigSerializeWrapper.getTopicConfigTable().get(topic);
		}

		throw Throwables.propagate(new MQBrokerException(response.getCode(), response.getRemark()));
	}

	public TopicStatsTable examineTopicStats(String topic)
			throws RemotingException, MQClientException, InterruptedException, MQBrokerException {
		return MQAdminInstance.threadLocalMQAdminExt().examineTopicStats(topic);
	}

	public TopicList fetchAllTopicList() throws RemotingException, MQClientException, InterruptedException {
		TopicList topicList = MQAdminInstance.threadLocalMQAdminExt().fetchAllTopicList();
		return topicList;
	}

	public KVTable fetchBrokerRuntimeStats(String brokerAddr) throws RemotingConnectException,
			RemotingSendRequestException, RemotingTimeoutException, InterruptedException, MQBrokerException {
		return MQAdminInstance.threadLocalMQAdminExt().fetchBrokerRuntimeStats(brokerAddr);
	}

	public ConsumeStats examineConsumeStats(String consumerGroup)
			throws RemotingException, MQClientException, InterruptedException, MQBrokerException {
		return MQAdminInstance.threadLocalMQAdminExt().examineConsumeStats(consumerGroup);
	}

	public ConsumeStats examineConsumeStats(String consumerGroup, String topic)
			throws RemotingException, MQClientException, InterruptedException, MQBrokerException {
		return MQAdminInstance.threadLocalMQAdminExt().examineConsumeStats(consumerGroup, topic);
	}

	public ClusterInfo examineBrokerClusterInfo() throws InterruptedException, MQBrokerException,
			RemotingTimeoutException, RemotingSendRequestException, RemotingConnectException {
		return MQAdminInstance.threadLocalMQAdminExt().examineBrokerClusterInfo();
	}

	public TopicRouteData examineTopicRouteInfo(String topic)
			throws RemotingException, MQClientException, InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().examineTopicRouteInfo(topic);
	}

	public ConsumerConnection examineConsumerConnectionInfo(String consumerGroup)
			throws RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException,
			InterruptedException, MQBrokerException, RemotingException, MQClientException {
		return MQAdminInstance.threadLocalMQAdminExt().examineConsumerConnectionInfo(consumerGroup);
	}

	public ProducerConnection examineProducerConnectionInfo(String producerGroup, String topic)
			throws RemotingException, MQClientException, InterruptedException, MQBrokerException {
		return MQAdminInstance.threadLocalMQAdminExt().examineProducerConnectionInfo(producerGroup, topic);
	}

	public List<String> getNameServerAddressList() {
		return MQAdminInstance.threadLocalMQAdminExt().getNameServerAddressList();
	}

	public int wipeWritePermOfBroker(String namesrvAddr, String brokerName)
			throws RemotingCommandException, RemotingConnectException, RemotingSendRequestException,
			RemotingTimeoutException, InterruptedException, MQClientException {
		return MQAdminInstance.threadLocalMQAdminExt().wipeWritePermOfBroker(namesrvAddr, brokerName);
	}

	public void putKVConfig(String namespace, String key, String value) {
		MQAdminInstance.threadLocalMQAdminExt().putKVConfig(namespace, key, value);
	}

	public String getKVConfig(String namespace, String key)
			throws RemotingException, MQClientException, InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().getKVConfig(namespace, key);
	}

	public KVTable getKVListByNamespace(String namespace)
			throws RemotingException, MQClientException, InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().getKVListByNamespace(namespace);
	}

	public void deleteTopicInBroker(Set<String> addrs, String topic)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		MQAdminInstance.threadLocalMQAdminExt().deleteTopicInBroker(addrs, topic);
	}

	public void deleteTopicInNameServer(Set<String> addrs, String topic)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		MQAdminInstance.threadLocalMQAdminExt().deleteTopicInNameServer(addrs, topic);
	}

	public void deleteSubscriptionGroup(String addr, String groupName)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		MQAdminInstance.threadLocalMQAdminExt().deleteSubscriptionGroup(addr, groupName);
	}

	public void createAndUpdateKvConfig(String namespace, String key, String value)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		MQAdminInstance.threadLocalMQAdminExt().createAndUpdateKvConfig(namespace, key, value);
	}

	public void deleteKvConfig(String namespace, String key)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		MQAdminInstance.threadLocalMQAdminExt().deleteKvConfig(namespace, key);
	}

	public List<RollbackStats> resetOffsetByTimestampOld(String consumerGroup, String topic, long timestamp,
			boolean force) throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		return MQAdminInstance.threadLocalMQAdminExt().resetOffsetByTimestampOld(consumerGroup, topic, timestamp,
				force);
	}

	public Map<MessageQueue, Long> resetOffsetByTimestamp(String topic, String group, long timestamp, boolean isForce)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		return MQAdminInstance.threadLocalMQAdminExt().resetOffsetByTimestamp(topic, group, timestamp, isForce);
	}

	public void resetOffsetNew(String consumerGroup, String topic, long timestamp)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		MQAdminInstance.threadLocalMQAdminExt().resetOffsetNew(consumerGroup, topic, timestamp);
	}

	public Map<String, Map<MessageQueue, Long>> getConsumeStatus(String topic, String group, String clientAddr)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		return MQAdminInstance.threadLocalMQAdminExt().getConsumeStatus(topic, group, clientAddr);
	}

	public void createOrUpdateOrderConf(String key, String value, boolean isCluster)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		MQAdminInstance.threadLocalMQAdminExt().createOrUpdateOrderConf(key, value, isCluster);
	}

	public GroupList queryTopicConsumeByWho(String topic) throws RemotingConnectException, RemotingSendRequestException,
			RemotingTimeoutException, InterruptedException, MQBrokerException, RemotingException, MQClientException {
		return MQAdminInstance.threadLocalMQAdminExt().queryTopicConsumeByWho(topic);
	}

	public boolean cleanExpiredConsumerQueue(String cluster) throws RemotingConnectException,
			RemotingSendRequestException, RemotingTimeoutException, MQClientException, InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().cleanExpiredConsumerQueue(cluster);
	}

	public boolean cleanExpiredConsumerQueueByAddr(String addr) throws RemotingConnectException,
			RemotingSendRequestException, RemotingTimeoutException, MQClientException, InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().cleanExpiredConsumerQueueByAddr(addr);
	}

	public ConsumerRunningInfo getConsumerRunningInfo(String consumerGroup, String clientId, boolean jstack)
			throws RemotingException, MQClientException, InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().getConsumerRunningInfo(consumerGroup, clientId, jstack);
	}

	public ConsumeMessageDirectlyResult consumeMessageDirectly(String consumerGroup, String clientId, String msgId)
			throws RemotingException, MQClientException, InterruptedException, MQBrokerException {
		return MQAdminInstance.threadLocalMQAdminExt().consumeMessageDirectly(consumerGroup, clientId, msgId);
	}

	public List<MessageTrack> messageTrackDetail(MessageExt msg)
			throws RemotingException, MQClientException, InterruptedException, MQBrokerException {
		return MQAdminInstance.threadLocalMQAdminExt().messageTrackDetail(msg);
	}

	public void cloneGroupOffset(String srcGroup, String destGroup, String topic, boolean isOffline)
			throws RemotingException, MQClientException, InterruptedException, MQBrokerException {
		MQAdminInstance.threadLocalMQAdminExt().cloneGroupOffset(srcGroup, destGroup, topic, isOffline);
	}

	public void createTopic(String key, String newTopic, int queueNum) throws MQClientException {
		MQAdminInstance.threadLocalMQAdminExt().createTopic(key, newTopic, queueNum);
	}

	public void createTopic(String key, String newTopic, int queueNum, int topicSysFlag) throws MQClientException {
		MQAdminInstance.threadLocalMQAdminExt().createTopic(key, newTopic, queueNum, topicSysFlag);
	}

	public long searchOffset(MessageQueue mq, long timestamp) throws MQClientException {
		return MQAdminInstance.threadLocalMQAdminExt().searchOffset(mq, timestamp);
	}

	public long maxOffset(MessageQueue mq) throws MQClientException {
		return MQAdminInstance.threadLocalMQAdminExt().maxOffset(mq);
	}

	public long minOffset(MessageQueue mq) throws MQClientException {
		return MQAdminInstance.threadLocalMQAdminExt().minOffset(mq);
	}

	public long earliestMsgStoreTime(MessageQueue mq) throws MQClientException {
		return MQAdminInstance.threadLocalMQAdminExt().earliestMsgStoreTime(mq);
	}

	public MessageExt viewMessage(String msgId)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		return MQAdminInstance.threadLocalMQAdminExt().viewMessage(msgId);
	}

	public QueryResult queryMessage(String topic, String key, int maxNum, long begin, long end)
			throws MQClientException, InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().queryMessage(topic, key, maxNum, begin, end);
	}

	@Deprecated
	public void start() throws MQClientException {
		throw new IllegalStateException(
				"thisMethod is deprecated.use org.apache.rocketmq.console.aspect.admin.MQAdminAspect instead of this");
	}

	@Deprecated
	public void shutdown() {
		throw new IllegalStateException(
				"thisMethod is deprecated.use org.apache.rocketmq.console.aspect.admin.MQAdminAspect instead of this");
	}

	public List<QueueTimeSpan> queryConsumeTimeSpan(String topic, String group)
			throws InterruptedException, MQBrokerException, RemotingException, MQClientException {
		return MQAdminInstance.threadLocalMQAdminExt().queryConsumeTimeSpan(topic, group);
	}

	public MessageExt viewMessage(String topic, String msgId)
			throws RemotingException, MQBrokerException, InterruptedException, MQClientException {
		try {
			return viewMessage(msgId);
		} catch (Exception localException) {
			MQAdminImpl mqAdminImpl = MQAdminInstance.threadLocalMqClientInstance().getMQAdminImpl();
			QueryResult qr = (QueryResult) Reflect.on(mqAdminImpl).call("queryMessage",
					new Object[] { topic, msgId, Integer.valueOf(32),
							Long.valueOf(MessageClientIDSetter.getNearlyTimeFromID(msgId).getTime() - 46800000L),
							Long.valueOf(9223372036854775807L), Boolean.valueOf(true) })
					.get();
			if ((qr != null) && (qr.getMessageList() != null) && (qr.getMessageList().size() > 0)) {
				return (MessageExt) qr.getMessageList().get(0);
			}
		}
		return null;
	}

	public ConsumeMessageDirectlyResult consumeMessageDirectly(String consumerGroup, String clientId, String topic,
			String msgId) throws RemotingException, MQClientException, InterruptedException, MQBrokerException {
		return MQAdminInstance.threadLocalMQAdminExt().consumeMessageDirectly(consumerGroup, clientId, topic, msgId);
	}

	public Properties getBrokerConfig(String brokerAddr) throws RemotingConnectException, RemotingSendRequestException,
			RemotingTimeoutException, UnsupportedEncodingException, InterruptedException, MQBrokerException {
		return MQAdminInstance.threadLocalMQAdminExt().getBrokerConfig(brokerAddr);
	}

	public TopicList fetchTopicsByCLuster(String clusterName)
			throws RemotingException, MQClientException, InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().fetchTopicsByCLuster(clusterName);
	}

	public boolean cleanUnusedTopic(String cluster) throws RemotingConnectException, RemotingSendRequestException,
			RemotingTimeoutException, MQClientException, InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().cleanUnusedTopic(cluster);
	}

	public boolean cleanUnusedTopicByAddr(String addr) throws RemotingConnectException, RemotingSendRequestException,
			RemotingTimeoutException, MQClientException, InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().cleanUnusedTopicByAddr(addr);
	}

	public BrokerStatsData viewBrokerStatsData(String brokerAddr, String statsName, String statsKey)
			throws RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException, MQClientException,
			InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().viewBrokerStatsData(brokerAddr, statsName, statsKey);
	}

	public Set<String> getClusterList(String topic) throws RemotingConnectException, RemotingSendRequestException,
			RemotingTimeoutException, MQClientException, InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().getClusterList(topic);
	}

	public ConsumeStatsList fetchConsumeStatsInBroker(String brokerAddr, boolean isOrder, long timeoutMillis)
			throws RemotingConnectException, RemotingSendRequestException, RemotingTimeoutException, MQClientException,
			InterruptedException {
		return MQAdminInstance.threadLocalMQAdminExt().fetchConsumeStatsInBroker(brokerAddr, isOrder, timeoutMillis);
	}

	public Set<String> getTopicClusterList(String topic)
			throws InterruptedException, MQBrokerException, MQClientException, RemotingException {
		return MQAdminInstance.threadLocalMQAdminExt().getTopicClusterList(topic);
	}

	public SubscriptionGroupWrapper getAllSubscriptionGroup(String brokerAddr, long timeoutMillis)
			throws InterruptedException, RemotingTimeoutException, RemotingSendRequestException,
			RemotingConnectException, MQBrokerException {
		return MQAdminInstance.threadLocalMQAdminExt().getAllSubscriptionGroup(brokerAddr, timeoutMillis);
	}

	public TopicConfigSerializeWrapper getAllTopicGroup(String brokerAddr, long timeoutMillis)
			throws InterruptedException, RemotingTimeoutException, RemotingSendRequestException,
			RemotingConnectException, MQBrokerException {
		return MQAdminInstance.threadLocalMQAdminExt().getAllTopicGroup(brokerAddr, timeoutMillis);
	}

	public void updateConsumeOffset(String brokerAddr, String consumeGroup, MessageQueue mq, long offset)
			throws RemotingException, InterruptedException, MQBrokerException {
		MQAdminInstance.threadLocalMQAdminExt().updateConsumeOffset(brokerAddr, consumeGroup, mq, offset);
	}

	public void updateNameServerConfig(Properties properties, List<String> list)
			throws InterruptedException, RemotingConnectException, UnsupportedEncodingException,
			RemotingSendRequestException, RemotingTimeoutException, MQClientException, MQBrokerException {
	}

	public Map<String, Properties> getNameServerConfig(List<String> list)
			throws InterruptedException, RemotingTimeoutException, RemotingSendRequestException,
			RemotingConnectException, MQClientException, UnsupportedEncodingException {
		return null;
	}
}