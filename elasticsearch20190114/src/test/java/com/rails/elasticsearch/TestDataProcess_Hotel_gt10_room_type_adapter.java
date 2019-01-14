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
import com.rails.elasticsearch.service.impl.DataProcess_Hotel_gt10_room_type_adapter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestDataProcess_Hotel_gt10_room_type_adapter {

	@Autowired
	private DataProcess_Hotel_gt10_room_type_adapter dataProcess_Hotel_gt10_room_type_adapter;

	@Test
	public void testDataProcess_Hotel_gt10_city() {
		List<MessageRequest> datas = new ArrayList<>();
		String body = "{\"bedDetail\":\"1张1.5米宽双人床\",\"bedDetailAdd\":\"不能加床\",\"breakfastDetail\":\"\",\"capacity\":\"2\",\"capacityExtend\":\"\",\"city\":57,\"hotelId\":\"4_5384586\",\"kuandaiDetail\":\"宽带资费:0;无线宽带:全部房间有,且免费;有线宽带:全部房间有,且免费\",\"roomArea\":\"20\",\"roomFacilitys\":[{\"facilityClassifyName\":\"媒体科技\",\"facilityName\":\"电视机\"},{\"facilityClassifyName\":\"便利设施\",\"facilityName\":\"手动窗帘,床具:毯子或被子\"}],\"roomName\":\"麻将房\",\"roomTypeAdapterId\":\"4_5384586_21153397\",\"roomTypeNum\":0,\"smokelessDetail\":\"允许吸烟\",\"toiletDetail\":\"\",\"winDetail\":\"未知\"}";
		MessageRequest messageRequest = new MessageRequest();
		messageRequest.setBody(body);
		datas.add(messageRequest);
		boolean mqData2Elasticsearch = dataProcess_Hotel_gt10_room_type_adapter.mqData2Elasticsearch(datas);
		Assert.assertTrue(!mqData2Elasticsearch);
	}
}
