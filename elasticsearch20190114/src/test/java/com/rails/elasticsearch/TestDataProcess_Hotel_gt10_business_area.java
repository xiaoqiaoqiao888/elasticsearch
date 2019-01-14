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
import com.rails.elasticsearch.service.impl.DataProcess_Hotel_gt10_business_area;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestDataProcess_Hotel_gt10_business_area {

	@Autowired
	private DataProcess_Hotel_gt10_business_area dataProcess_Hotel_gt10_business_area;

	@Test
	public void testDataProcess_Hotel_gt10_business_area() {
		List<MessageRequest> datas = new ArrayList<>();
		String body = "{\"businessCode\":15095,\"businessName\":\"梅溪湖大河西先导区\",\"shortName\":\"梅溪湖商圈\",\"priority\":\"0\",\"validFlag\":\"1\",\"cityCode\":206,\"cityName\":\"长沙\",\"hotFlag\":\"0\",\"lng\":112.908027648926,\"lat\":28.1960506439209,\"vaildFlag\":null}";
		MessageRequest messageRequest = new MessageRequest();
		messageRequest.setBody(body);
		datas.add(messageRequest);
		boolean mqData2Elasticsearch = dataProcess_Hotel_gt10_business_area.mqData2Elasticsearch(datas);
		Assert.assertTrue(!mqData2Elasticsearch);
	}
}
