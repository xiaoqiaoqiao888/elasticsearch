package com.rails.elasticsearch.document;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
public class HotelFacility {
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String facilityName;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String facilityValue;
	@Field(type = FieldType.Keyword)
	private String facilityNameEn;
	@Field(type = FieldType.Keyword)
	private String facilityValueEn;

}
