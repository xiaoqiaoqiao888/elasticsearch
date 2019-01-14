package com.rails.elasticsearch.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import lombok.Data;

@Data
@Document(indexName = "landmark", type = "landmark")
public class Landmark_1 {
	@Id
	@Field(type = FieldType.Keyword)
	private String landmarkCode;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String landmarkName;
	@Field(type = FieldType.Integer)
	private Integer landmarkType;
	@Field(type = FieldType.Keyword)
	private String validFlag;
	@Field(type = FieldType.Integer)
	private Integer cityCode;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String cityName;
	@Field(type = FieldType.Integer)
	private Integer belongCode;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String belongName;
	@Field(type = FieldType.Keyword)
	private String hotFlag;
	@Field(type = FieldType.Double)
	private Double lng;
	@Field(type = FieldType.Double)
	private Double lat;
	@Field(type = FieldType.Integer)
	private Integer priority;
	@GeoPointField
	private String location;

}
