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
import com.rails.elasticsearch.service.impl.DataProcess_Hotel_gt10_base_price;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestDataProcess_Hotel_gt10_base_price {

	@Autowired
	private DataProcess_Hotel_gt10_base_price dataProcess_Hotel_gt10_base_price;

	@Test
	public void testDataProcess_Hotel_gt10_hotel_adapter() {
		List<MessageRequest> datas = new ArrayList<>();
		String body = "{\"hotelId\":\"4_6050551\",\"city\":380,\"basePrice\":32900,\"bizdate\":\"20180919\",\"createTime\":1534417932747,\"updateTime\":1534432951038}";
		MessageRequest messageRequest = new MessageRequest();
		messageRequest.setBody(body);
		datas.add(messageRequest);

		boolean mqData2Elasticsearch = dataProcess_Hotel_gt10_base_price.mqData2Elasticsearch(datas);
		Assert.assertTrue(!mqData2Elasticsearch);
	}
}
