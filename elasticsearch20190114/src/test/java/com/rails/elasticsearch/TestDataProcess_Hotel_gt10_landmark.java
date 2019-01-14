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
import com.rails.elasticsearch.service.impl.DataProcess_Hotel_gt10_landmark;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestDataProcess_Hotel_gt10_landmark {

	@Autowired
	private DataProcess_Hotel_gt10_landmark dataProcess_Hotel_gt10_landmark;

	@Test
	public void testProcess_Hotel_gt10_landmark() {
		List<MessageRequest> datas = new ArrayList<>();
		String body = "{\"landmarkCode\":\"5_6263\",\"landmarkName\":\"台北动物园站\",\"landmarkType\":5,\"validFlag\":\"1\",\"cityCode\":617,\"cityName\":\"台北\",\"belongCode\":5,\"belongName\":\"棕线\",\"hotFlag\":\"0\",\"lng\":121.5793,\"lat\":24.9982,\"priority\":0}";
		MessageRequest messageRequest = new MessageRequest();
		messageRequest.setBody(body);
		datas.add(messageRequest);
		boolean mqData2Elasticsearch = dataProcess_Hotel_gt10_landmark.mqData2Elasticsearch(datas);
		Assert.assertTrue(!mqData2Elasticsearch);
	}
}
