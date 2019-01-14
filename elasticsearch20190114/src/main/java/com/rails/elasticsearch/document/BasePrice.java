package com.rails.elasticsearch.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "base_price", type = "base_price")
public class BasePrice {
	@Id
	@Field(type = FieldType.Keyword)
	private String basePriceId;
	@Field(type = FieldType.Keyword)
	private String hotelId;
	@Field(type = FieldType.Integer)
	private Integer city;
	@Field(type = FieldType.Double)
	private Double basePrice;
	@Field(type = FieldType.Keyword)
	private String bizdate;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private String createTime;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private String updateTime;
}
