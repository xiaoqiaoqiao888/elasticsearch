package com.rails.elasticsearch.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class Pinying2CityUtils {

	private static Map<String, String> citys = new HashMap<String, String>();
	private static Map<String, String> provinces = new HashMap<String, String>();

	Logger logger = LoggerFactory.getLogger(Pinying2CityUtils.class);

	@SuppressWarnings("unchecked")
	@Scheduled(fixedDelay = 120 * 60 * 1000)
	public void refush() {
		String path = Pinying2CityUtils.class.getResource("/city.json").getPath();
		String str = TxtFileUtils.readTxtToString(path, "UTF-8");
		String[] arr = str.split("\\|");
		provinces = JSON.toJavaObject(JSON.parseObject(arr[0].trim()), Map.class);
		citys = JSON.toJavaObject(JSON.parseObject(arr[1].trim()), Map.class);
		logger.info("--------------------------刷新省份城市成功--------------------------");
	}

	public static CdnAddress getCdnAddress(String pinying) {
		CdnAddress cdnCity = new CdnAddress();
		if (StringUtils.isBlank(pinying)) {
			// cdnCity.setCounty("中国");
			// cdnCity.setProvince("北京");
			// cdnCity.setCity("北京");
			return cdnCity;
		}
		String[] arr = pinying.split(",");
		if ("ChinaMainland".equals(arr[2].trim())) {
			cdnCity.setCounty("中国");
		} else {
			cdnCity.setCounty("未知");
		}
		String currentProvince = getProvince(arr[1].trim().toLowerCase());
		cdnCity.setProvince(currentProvince);
		String currentCity = getCity(arr[0].trim().toLowerCase());
		if (currentCity == null) {
			currentCity = currentProvince;
		}
		cdnCity.setCity(currentCity);
		return cdnCity;
	}

	public static String getCity(String pinying) {
		if (StringUtils.isBlank(pinying)) {
			return "北京";
		}
		String city = citys.get(pinying.toLowerCase());
		return city;
	}

	public static String getProvince(String pinying) {
		if (StringUtils.isBlank(pinying)) {
			return "北京";
		}
		String province = provinces.get(pinying.toLowerCase());
		return province == null ? "北京" : province;
	}

	public static Map<String, String> getAllCity() {
		return citys;
	}

	public static boolean containsCity(String city) {
		return citys.containsValue(city);
	}

	// 获取cdn地址测试
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String path = "D:/WorkSpace3/ticket-searchengines/ticket-searchengines-api/target/classes/city.json";
		String str = TxtFileUtils.readTxtToString(path, "UTF-8");
		String[] arr = str.split("\\|");
		provinces = JSON.toJavaObject(JSON.parseObject(arr[0].trim()), Map.class);
		citys = JSON.toJavaObject(JSON.parseObject(arr[1].trim()), Map.class);
		String add = " qita,qita,HongKong";
		CdnAddress cdn = Pinying2CityUtils.getCdnAddress(add);
		System.out.println(cdn.getCity());
		System.out.println(cdn.getCounty());
		System.out.println(cdn.getProvince());
	}
}
