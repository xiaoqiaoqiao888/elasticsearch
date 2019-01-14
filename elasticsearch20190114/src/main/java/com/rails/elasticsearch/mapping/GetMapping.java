package com.rails.elasticsearch.mapping;

import java.io.IOException;
import java.net.InetAddress;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

public class GetMapping {

	TransportClient client;

	@SuppressWarnings("resource")
	@Test
	public void CreateMapping() throws IOException {

		client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));// 建立链接
		CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate("studentexamtest");
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("properties") // 设置之定义字段
				.startObject("id")// 字段id
				.field("type", "integer")// 设置数据类型
				.field("index", true).endObject()//
				.startObject("classs").field("type", "integer").field("index", true).endObject()//
				.startObject("courseClass").field("type", "integer").field("index", true).endObject()//
				.startObject("courseClassExam").field("type", "integer").field("index", true).endObject()//
				.startObject("examnum").field("type", "integer").field("index", true).endObject()//
				.startObject("ok").field("type", "integer").field("index", true).endObject()//
				.startObject("room").field("type", "integer").field("index", true).endObject()//
				.startObject("score").field("type", "integer").field("index", true).endObject()//
				.startObject("student").field("type", "integer").field("index", true).endObject()//
				.startObject("updatetime").field("type", "integer").field("index", true).endObject()//
				.startObject("createtime").field("type", "integer").field("index", true).endObject()//
				.startObject("answer").field("type", "text").field("index", true).endObject()//
				.startObject("courseplan").field("type", "keyword").field("index", true).endObject()//
				.endObject().endObject();
		cib.addMapping("studentexamtest", mapping);
		cib.execute().actionGet();
	}

	@SuppressWarnings("resource")
	@Test
	public void CreateMapping1() throws IOException {
		client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));// 建立链接
		CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate("hotel_landmark");
		XContentBuilder mapping = XContentFactory.jsonBuilder().startObject().startObject("properties") // 设置之定义字段
				.startObject("landmark_code")// 字段id
				.field("type", "text")// 设置数据类型
				.field("index", true).endObject()//
				.startObject("landmark_name").field("type", "text").field("index", true)
				.field("analyzer", "ik_max_word").field("search_analyzer", "ik_max_word").endObject()//
				.startObject("landmark_type").field("type", "keyword").field("index", true).endObject()//
				.startObject("valid_flag").field("type", "keyword").field("index", true).endObject()//
				.startObject("city_code").field("type", "text").field("index", true).endObject()//
				.startObject("city_name").field("type", "text").field("index", true).field("analyzer", "ik_max_word")
				.field("search_analyzer", "ik_max_word").endObject()//
				.startObject("belong_code").field("type", "text").field("index", true).endObject()//
				.startObject("belong_name").field("type", "text").field("index", true).field("analyzer", "ik_max_word")
				.field("search_analyzer", "ik_max_word").endObject()//
				.startObject("hot_flag").field("type", "keyword").field("index", true).endObject()//
				.startObject("lng").field("type", "text").field("index", true).endObject()//
				.startObject("lat").field("type", "text").field("index", true).endObject()//
				.startObject("priority").field("type", "keyword").field("index", true).endObject()//
				.startObject("location").field("type", "geo_point").field("index", true).endObject()//
				.endObject().endObject();
		cib.addMapping("landmark", mapping);
		cib.execute().actionGet();
	}
}
