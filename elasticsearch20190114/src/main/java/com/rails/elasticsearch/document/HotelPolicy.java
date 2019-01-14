package com.rails.elasticsearch.document;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
public class HotelPolicy {
	@Field(type = FieldType.Keyword)
	private String policyName;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String policyValue;
	@Field(type = FieldType.Keyword)
	private String policyNameEn;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String policyValueEn;

}
