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
import com.rails.elasticsearch.service.impl.DataProcess_Hotel_gt10_hotel_adapter;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestDataProcess_Hotel_gt10_hotel_adapter {

	@Autowired
	private DataProcess_Hotel_gt10_hotel_adapter dataProcess_Hotel_gt10_hotel_adapter;

	@Test
	public void testDataProcess_Hotel_gt10_hotel_adapter() {
		List<MessageRequest> datas = new ArrayList<>();
		String body = "{\"address\":\"五爱路42号\",\"businessArea\":\"14734\",\"businessAreaName\":\"国际博览中心及梅湖体育中心\",\"chainBrand\":\"0\",\"chainBrandName\":\"其他\",\"city\":536,\"cityName\":\"义乌\",\"companyType\":\"0\",\"country\":1,\"countryName\":\"中国\",\"createTime\":1537990669478,\"decorateYear\":\"20120801\",\"examineType\":\"2\",\"fax\":\"\",\"fromChannel\":\"4\",\"hotelFacility\":[{\"facilityName\":\"客房设施和服务\",\"facilityValue\":\"电视机,手动窗帘,床具:毯子或被子\"}],\"hotelId\":\"4_4729282\",\"hotelName\":\"小麦加豪华单身公寓\",\"hotelPolicy\":[{\"policyName\":\"CheckInCheckOut\",\"policyValue\":\"入住时间：14:00以后&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;离店时间：12:00-12:00\"},{\"policyName\":\"Child\",\"policyValue\":\"不接受18岁以下客人单独入住。<br/>不接受18岁以下客人在无监护人陪同的情况下入住\"},{\"policyName\":\"Meal\",\"policyValue\":\"\"},{\"policyName\":\"Pet\",\"policyValue\":\"\"},{\"policyName\":\"CheckInFrom\",\"policyValue\":\"14:00\"},{\"policyName\":\"CheckInTo\",\"policyValue\":\"\"},{\"policyName\":\"CheckOutFrom\",\"policyValue\":\"12:00\"},{\"policyName\":\"CheckOutTo\",\"policyValue\":\"12:00\"},{\"policyName\":\"childAndExtraBedPolicy\",\"policyValue\":\"{\\\"allowChildrenToStay\\\":false,\\\"allowExtraBed\\\":false,\\\"allowExtraCrib\\\":false,\\\"allowUseExistingBed\\\":false,\\\"descriptions\\\":[{\\\"category\\\":\\\"ChildStayNotice\\\",\\\"text\\\":\\\"不接受18岁以下客人在无监护人陪同的情况下入住\\\"}]}\"}],\"hotelScore\":0.0,\"hotelState\":\"1\",\"hotelTips\":\"\",\"introduction\":\"地址位于义乌市五爱路42号。\",\"landmarkName\":\"\",\"latitude\":29.307451,\"linkmanPhone\":\"13095899287\",\"longitude\":120.09929,\"oldBusinessArea\":\"14734\",\"oldBusinessAreaName\":\"国际博览中心及梅湖体育中心\",\"oldChainBrand\":\"0\",\"oldChainBrandName\":\"其他\",\"oldCity\":\"536\",\"oldCityName\":\"义乌\",\"oldCountry\":\"1\",\"oldCountryName\":\"中国\",\"oldHotelId\":\"4729282\",\"oldLandmarkName\":\"\",\"oldProvince\":\"16\",\"oldProvinceName\":\"浙江\",\"oldStarLevel\":\"0\",\"province\":16,\"provinceName\":\"浙江\",\"setupYear\":\"2009\",\"starLevel\":\"0\",\"supportCard\":\"\",\"tags\":\"\",\"tel\":\"13095899287\",\"validFlag\":\"1\",\"versionNo\":1537990669478}";
		MessageRequest messageRequest = new MessageRequest();
		messageRequest.setBody(body);
		datas.add(messageRequest);
		boolean mqData2Elasticsearch = dataProcess_Hotel_gt10_hotel_adapter.mqData2Elasticsearch(datas);
		Assert.assertTrue(!mqData2Elasticsearch);
	}
}
