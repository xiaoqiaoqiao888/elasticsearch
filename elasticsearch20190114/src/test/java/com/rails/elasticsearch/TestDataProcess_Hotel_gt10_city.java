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
import com.rails.elasticsearch.service.impl.DataProcess_Hotel_gt10_city;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestDataProcess_Hotel_gt10_city {

	@Autowired
	private DataProcess_Hotel_gt10_city dataProcess_Hotel_gt10_city;

	@Test
	public void testDataProcess_Hotel_gt10_city() {
		List<MessageRequest> datas = new ArrayList<>();
		String body = "{\"cityCode\":22028,\"cityName\":\"和田县\",\"cityNameEn\":\"hetianxian\",\"priority\":0,\"validFlag\":\"1\",\"suoXie\":\"htx\",\"provinceCode\":29,\"provinceName\":\"新疆\",\"countryCode\":1,\"countryName\":\"中国\",\"hotFlag\":\"0\",\"parentCityCode\":20931,\"lng\":null,\"lat\":null,\"pinyin\":\"hetianxian\"}";
		MessageRequest messageRequest = new MessageRequest();
		messageRequest.setBody(body);
		datas.add(messageRequest);
		boolean mqData2Elasticsearch = dataProcess_Hotel_gt10_city.mqData2Elasticsearch(datas);
		Assert.assertTrue(!mqData2Elasticsearch);
	}
}
