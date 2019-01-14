package com.rails.elasticsearch.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "zone", type = "zone")
public class Zone {
	@Id
	@Field(type = FieldType.Integer)
	private Integer zoneCode;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String zoneName;
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
	@Field(type = FieldType.Keyword)
	private String vaildFlag;

}
