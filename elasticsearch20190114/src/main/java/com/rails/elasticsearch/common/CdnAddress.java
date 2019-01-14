package com.rails.elasticsearch.common;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CdnAddress implements Serializable {

	private String city;
	private String province;
	private String county;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

}
