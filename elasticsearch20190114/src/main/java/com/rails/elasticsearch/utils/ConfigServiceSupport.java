/**
 * Copyright 2018 电子计算技术研究所
 * Author：WenLi
 * 创建日期：2018年8月10日
 */
package com.rails.elasticsearch.utils;

import org.springframework.stereotype.Service;

import com.ctrip.framework.apollo.Config;

@Service
public class ConfigServiceSupport implements ConfigService {
	final static String SYSTEMCONF = "systemconf";
	Config conf = com.ctrip.framework.apollo.ConfigService.getConfig(SYSTEMCONF);

	@Override
	public String getProperty(String key, String defaultValue) {
		return conf.getProperty(key, defaultValue);
	}

	@Override
	public Integer getIntProperty(String key, Integer defaultValue) {
		return conf.getIntProperty(key, defaultValue);
	}

	@Override
	public Long getLongProperty(String key, Long defaultValue) {
		return conf.getLongProperty(key, defaultValue);
	}

	@Override
	public Short getShortProperty(String key, Short defaultValue) {
		return conf.getShortProperty(key, defaultValue);
	}

	@Override
	public Float getFloatProperty(String key, Float defaultValue) {
		return conf.getFloatProperty(key, defaultValue);
	}

	@Override
	public Double getDoubleProperty(String key, Double defaultValue) {
		return conf.getDoubleProperty(key, defaultValue);
	}

	@Override
	public Byte getByteProperty(String key, Byte defaultValue) {
		return conf.getByteProperty(key, defaultValue);
	}

	@Override
	public Boolean getBooleanProperty(String key, Boolean defaultValue) {
		return conf.getBooleanProperty(key, defaultValue);
	}
}
