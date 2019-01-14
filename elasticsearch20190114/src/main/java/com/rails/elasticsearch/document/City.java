package com.rails.elasticsearch.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import lombok.Data;

@Data
@Document(indexName = "city", type = "city")
public class City {
	
	@Field(type = FieldType.Integer)
	private Integer cityCode;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String cityName;
	@Field(type = FieldType.Keyword)
	private String cityNameEn;
	@Field(type = FieldType.Keyword)
	private String pinyin;
	@Field(type = FieldType.Integer)
	private Integer priority;
	@Field(type = FieldType.Keyword)
	private String validFlag;
	@Id
	@Field(type = FieldType.Keyword)
	private String suoXie;
	@Field(type = FieldType.Integer)
	private Integer provinceCode;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String provinceName;
	@Field(type = FieldType.Integer)
	private Integer countryCode;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String countryName;
	@Field(type = FieldType.Keyword)
	private String hotFlag;
	@Field(type = FieldType.Integer)
	private Integer parentCityCode;
	@Field(type = FieldType.Double)
	private Double lng;
	@Field(type = FieldType.Double)
	private Double lat;
	@GeoPointField
	private String location;

}
