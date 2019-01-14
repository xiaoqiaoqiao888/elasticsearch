package com.rails.elasticsearch.document;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
public class OldBusinessArea {
	@Field(type = FieldType.Keyword)
	private String oldBusinessAreaCode;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String oldBusinessAreaName;

}
