package com.rails.elasticsearch.common;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.rocketmq.common.protocol.body.BrokerStatsData;
import org.apache.rocketmq.common.protocol.body.ConsumerConnection;
import org.apache.rocketmq.common.protocol.route.BrokerData;
import org.apache.rocketmq.common.protocol.route.TopicRouteData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.RateLimiter;
import com.rails.elasticsearch.utils.ConfigService;
import com.rails.rpc.invoke.rocketmq.MQAdminExtImpl;

public class ConsumerMqTpsCollect {
	static Logger logger = LoggerFactory.getLogger(ConsumerMqTpsCollect.class);
	static MQAdminExtImpl mqAdminExt;
	static ConfigService configService;
	static Map<String, RateLimiter> consumerRateLimiterMap = new HashMap<>();

	static Map<String, String[]> topicesMap = new HashMap<>();

	static Map<String, Integer> consumerSzieMap = new HashMap<>();

	static Map<String, Integer> nowConsumerMqTpsMap = new HashMap<>();

	static volatile boolean isInit = false;

	public static void registeConsumeLimit(String consumerGroup, String topices, int defaultLimit) {
		consumerRateLimiterMap.put(consumerGroup, RateLimiter.create(defaultLimit));
		if (topices != null) {
			topicesMap.put(consumerGroup, topices.split(","));
		}
		consumerSzieMap.put(consumerGroup, Integer.valueOf(1));
	}

	public static void acquire(String consumerGroup) {
		RateLimiter rateLimiter = (RateLimiter) consumerRateLimiterMap.get(consumerGroup);
		if (rateLimiter != null)
			rateLimiter.acquire();
	}

	// public static void initTimmer(ConfigService config, MQAdminExtImpl mqAdmin)
	// {
	// if (isInit) {
	// return;
	// }
	// configService = config;
	// isInit = true;
	// mqAdminExt = mqAdmin;
	// TimerConfig timerConfig = new TimerConfig(60L, "ConsumeTps");
	// logger.info("启动MqTpsTimer");
	// new AbstractProcessTimer(timerConfig)
	// {
	// public boolean doInvoke(TimerConfig timerConfig) {
	// Iterator keys =
	// ConsumerMqTpsCollect.consumerRateLimiterMap.keySet().iterator();
	// while (keys.hasNext()) {
	// String consumeGroup = (String)keys.next();
	// ConsumerMqTpsCollect.collectTopic(consumeGroup);
	// }
	// return true;
	// }
	// };
	// }

	public static void collectTopic(String consumeGroup) {
		try {
			ConsumerConnection consumerConnection = mqAdminExt.examineConsumerConnectionInfo(consumeGroup);
			if (consumerConnection != null) {
				consumerSzieMap.put(consumeGroup, Integer.valueOf(consumerConnection.getConnectionSet().size()));
			}
			String[] topics = (String[]) topicesMap.get(consumeGroup);
			double outTPS = 0.0D;
			for (String topic : topics) {
				String[] topicSplits = topic.split("#");
				TopicRouteData topicRouteData = mqAdminExt.examineTopicRouteInfo(topicSplits[0]);
				for (BrokerData bd : topicRouteData.getBrokerDatas()) {
					String masterAddr = (String) bd.getBrokerAddrs().get(Long.valueOf(0L));
					if (masterAddr != null) {
						try {
							String statsKey = String.format("%s@%s", new Object[] { topicSplits[0], consumeGroup });
							BrokerStatsData bsd = mqAdminExt.viewBrokerStatsData(masterAddr, "GROUP_GET_NUMS",
									statsKey);
							outTPS += bsd.getStatsMinute().getTps();
						} catch (Exception e) {
							logger.error("Query Topic Tps Error : ", e);
						}
					}
				}
			}

			int nowConsumerMqTps = new BigDecimal(outTPS).setScale(0, 4).intValue();
			nowConsumerMqTpsMap.put(consumeGroup, Integer.valueOf(nowConsumerMqTps));
			int maxTps = 1000;
			if (((Integer) consumerSzieMap.get(consumeGroup)).intValue() > 0) {
				try {
					maxTps = configService.getIntProperty("limit." + consumeGroup + ".max.tps", Integer.valueOf(1000))
							.intValue() / ((Integer) consumerSzieMap.get(consumeGroup)).intValue();
				} catch (Exception e) {
					logger.error("Query ConsumerSize Error", e);
				}
				((RateLimiter) consumerRateLimiterMap.get(consumeGroup)).setRate(maxTps);
			}
			logger.info("Now Consumer：" + consumeGroup + " Mq Tps is " + nowConsumerMqTps + " , ConsumerSize is "
					+ consumerSzieMap.get(consumeGroup) + " MaxConfigTps is " + maxTps);
		} catch (Exception e) {
			logger.error("Query Topic Tps Error : ", e);
			return;
		}
	}

	public static int getNowConsumerMqTps(String consumerGroup) {
		return ((Integer) nowConsumerMqTpsMap.get(consumerGroup)).intValue();
	}
}