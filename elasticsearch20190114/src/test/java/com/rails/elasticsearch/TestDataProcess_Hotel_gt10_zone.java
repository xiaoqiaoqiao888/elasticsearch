package com.rails.elasticsearch;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rails.elasticsearch.common.MessageRequest;
import com.rails.elasticsearch.service.impl.DataProcess_Hotel_gt10_zone;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestDataProcess_Hotel_gt10_zone {

	@Autowired
	private DataProcess_Hotel_gt10_zone dataProcess_Hotel_gt10_zone;

	@Test
	public void testDataProcess_Hotel_gt10_zone() {
		List<MessageRequest> datas = new ArrayList<>();
		String body = "{\"zoneCode\":92,\"zoneName\":\"西城区\",\"priority\":\"0\",\"validFlag\":\"1\",\"cityCode\":1,\"cityName\":\"北京\",\"hotFlag\":\"0\",\"vaildFlag\":\"1\"}";
		MessageRequest messageRequest = new MessageRequest();
		messageRequest.setBody(body);
		datas.add(messageRequest);
		boolean mqData2Elasticsearch = dataProcess_Hotel_gt10_zone.mqData2Elasticsearch(datas);
		Assert.assertTrue(!mqData2Elasticsearch);
	}
}
