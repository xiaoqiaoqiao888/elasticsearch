package com.rails.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rails.elasticsearch.common.SpringBeanUtils;
import com.rails.elasticsearch.consumer.mq.MqMessageConsumerService;

@SpringBootApplication
public class ElasticsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchApplication.class, args);

		MqMessageConsumerService mqMessageConsumerService = SpringBeanUtils.getBean(MqMessageConsumerService.class);
		mqMessageConsumerService.start();
	}
}
