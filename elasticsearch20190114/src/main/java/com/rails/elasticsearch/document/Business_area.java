package com.rails.elasticsearch.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import lombok.Data;

@Data
@Document(indexName = "business_area", type = "business_area")
public class Business_area {
	@Id
	@Field(type = FieldType.Integer)
	private Integer businessCode;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String businessName;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String shortName;
	@Field(type = FieldType.Keyword)
	private String priority;
	@Field(type = FieldType.Keyword)
	private String validFlag;
	@Field(type = FieldType.Integer)
	private Integer cityCode;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String cityName;
	@Field(type = FieldType.Keyword)
	private String hotFlag;
	@Field(type = FieldType.Double)
	private Double lng;
	@Field(type = FieldType.Double)
	private Double lat;
	@Field(type = FieldType.Keyword)
	private String vaildFlag;
	@GeoPointField
	private String location;

}
