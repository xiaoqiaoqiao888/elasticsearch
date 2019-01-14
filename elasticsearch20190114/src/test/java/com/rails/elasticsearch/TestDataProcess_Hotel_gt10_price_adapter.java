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
import com.rails.elasticsearch.service.impl.DataProcess_Hotel_gt10_price_adapter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestDataProcess_Hotel_gt10_price_adapter {

	@Autowired
	private DataProcess_Hotel_gt10_price_adapter dataProcess_Hotel_gt10_price_adapter;

	@Test
	public void testDataProcess_Hotel_gt10_price_adapter() {
		List<MessageRequest> datas = new ArrayList<>();
		String body = "{\"productPriceId\":\"2f502a71a391e04853748bafbed7e829\",\"fromChannel\":\"4\",\"productId\":\"4_1281055_85018080_192562478\",\"city\":354,\"oldProductId\":\"1281055_85018080_192562478\",\"roomTypeId\":\"4_1281055_192562478\",\"oldRoomtypeId\":\"85018080\",\"activityId\":\"4_192562478\",\"oldActivityId\":\"192562478\",\"bizdate\":\"20181003\",\"listingPrice\":0,\"salePrice\":15800,\"purchasePrice\":0,\"minimumFloating\":0,\"maximumFloating\":0,\"commission\":0,\"commissionRate\":0,\"extrabedPrice\":0,\"doublePrice\":0,\"triplePrice\":0,\"quadPrice\":0,\"exchangeValue\":0,\"webScore\":0,\"burScore\":null,\"webCommission\":null,\"burCommission\":null,\"feesRemark\":\"[]\",\"isBreakFast\":\"0\",\"numberOfBreakfast\":0,\"laterBookingTime\":null,\"guaranteeCode\":\"4\",\"holdTime\":null,\"starCancel\":1538589600,\"endCancel\":1538611200,\"forfeit\":15800,\"priceRemark\":null,\"productRemain\":0,\"productBooked\":null,\"avstat\":\"1\",\"isInstantConfirm\":\"0\",\"validFlag\":\"1\",\"publishState\":\"1\",\"createTime\":1536314823764,\"versionNo\":1536314823764,\"nodeNum\":null,\"flag1\":null,\"flag2\":null,\"flag3\":null,\"flag4\":null,\"flag5\":null,\"flag6\":null,\"flag7\":null,\"flag8\":null,\"flag9\":null,\"flag10\":null,\"hotelId\":\"4_1281055\"}";
		MessageRequest messageRequest = new MessageRequest();
		messageRequest.setBody(body);
		datas.add(messageRequest);
		boolean mqData2Elasticsearch = dataProcess_Hotel_gt10_price_adapter.mqData2Elasticsearch(datas);
		Assert.assertTrue(!mqData2Elasticsearch);
	}
}
