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
import com.rails.elasticsearch.service.impl.DataProcess_Hotel_gt10_product_adapter;;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestDataProcess_Hotel_gt10_product_adapter {

	@Autowired
	private DataProcess_Hotel_gt10_product_adapter dataProcess_Hotel_gt10_product_adapter;

	@Test
	public void testDataProcess_Hotel_gt10_city() {
		List<MessageRequest> datas = new ArrayList<>();
		String body = "{\"productId\":\"4_2042910_5834072_217844937\",\"oldProductId\":\"217844937\",\"productName\":\"豪华单间\",\"city\":218,\"supplierId\":\"10224\",\"supplierName\":\"SupplierID\",\"vendorId\":\"0\",\"vendorName\":\"VendorID\",\"priority\":0,\"fromChannel\":\"4\",\"hotelId\":\"4_2042910\",\"oldHotelOutid\":\"2042910\",\"roomTypeId\":\"4_2042910_5834072\",\"roomTypeName\":\"豪华单间\",\"oldRoomTypeId\":\"5834072\",\"oldRoomTypeName\":\"豪华单间\",\"oldRoomTypeId1\":\"\",\"roomTypeNum\":15,\"bedDetail\":\"1张1.8米宽双人床\",\"bedDetailAdd\":\"不能加床\",\"capacity\":\"2\",\"capacityExtend\":\"\",\"storeyNum\":\"10\",\"storeyTotal\":\"15\",\"roomArea\":\"50\",\"kuandaiDetail\":\"宽带资费:无;无线宽带:全部房间有,且免费;有线宽带:全部房间有,且免费\",\"smokelessDetail\":\"允许吸烟\",\"winDetail\":\"有窗\",\"breakfastDetail\":null,\"toiletDetail\":null,\"applicability\":32,\"applicabilityDetail\":\"适用持大陆身份证的中国人;\",\"payWay\":null,\"payCanChange\":\"1\",\"lineChannel\":\"1,4\",\"isSupported\":\"0\",\"depositRatio\":1,\"receiptFlag\":\"1\",\"isSpecialInvoice\":\"1\",\"isHourlyRoom\":\"0\",\"isConnection\":\"0\",\"isAgency\":\"0\",\"receiveTextRemark\":\"0\",\"stockSaleFlag\":\"1\",\"examineType\":\"2\",\"productState\":\"1\",\"validFlag\":\"0\",\"createTime\":1537269271910,\"versionNo\":1538018706077,\"exchangeFlag\":\"\",\"integralFlag\":\"\",\"hotelFlag1\":null,\"hotelFlag2\":null,\"hotelFlag3\":null,\"hotelFlag4\":null,\"hotelFlag5\":null,\"hotelFlag6\":null,\"hotelFlag7\":null,\"hotelFlag8\":null,\"hotelFlag9\":null,\"hotelFlag10\":null}";
		MessageRequest messageRequest = new MessageRequest();
		messageRequest.setBody(body);
		datas.add(messageRequest);
		boolean mqData2Elasticsearch = dataProcess_Hotel_gt10_product_adapter.mqData2Elasticsearch(datas);
		Assert.assertTrue(!mqData2Elasticsearch);
	}
}
