package com.rails.elasticsearch.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "room_type", type = "room_type")
public class RoomType {

	@Field(type = FieldType.Keyword)
	private String hotelId;
	@Id
	@Field(type = FieldType.Keyword)
	private String roomTypeAdapterId;
	@Field(type = FieldType.Integer)
	private Integer city;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String roomName;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String feature;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String introductions;
	@Field(type = FieldType.Integer)
	private Integer roomTypeNum;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String bedDetail;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String bedDetailAdd;
	@Field(type = FieldType.Keyword)
	private String capacity;
	@Field(type = FieldType.Keyword)
	private String capacityExtend;
	@Field(type = FieldType.Keyword)
	private String storeyNum;
	@Field(type = FieldType.Keyword)
	private String storeyTotal;
	@Field(type = FieldType.Keyword)
	private String roomArea;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String kuandaiDetail;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String smokelessDetail;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String winDetail;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String breakfastDetail;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String toiletDetail;
	@Field(type = FieldType.Nested)
	private List<RoomFacilitys> roomFacilitys;
	@Field(type = FieldType.Keyword)
	private String flag1;
	@Field(type = FieldType.Keyword)
	private String flag2;
	@Field(type = FieldType.Keyword)
	private String flag3;
	@Field(type = FieldType.Keyword)
	private String flag4;
	@Field(type = FieldType.Keyword)
	private String flag5;

}
