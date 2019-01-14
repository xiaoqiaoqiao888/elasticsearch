package com.rails.elasticsearch.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

@Data
@Document(indexName = "product", type = "product")
public class Product {
	@Id
	@Field(type = FieldType.Keyword)
	private String productId;
	@Field(type = FieldType.Keyword)
	private String oldProductId;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String productName;
	@Field(type = FieldType.Integer)
	private Integer city;
	@Field(type = FieldType.Keyword)
	private String supplierId;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String supplierName;
	@Field(type = FieldType.Keyword)
	private String vendorId;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String vendorName;
	@Field(type = FieldType.Integer)
	private Integer priority;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String fromChannel;
	@Field(type = FieldType.Keyword)
	private String hotelId;
	@Field(type = FieldType.Keyword)
	private String oldHotelOutid;
	@Field(type = FieldType.Keyword)
	private String roomTypeId;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String roomTypeName;
	@Field(type = FieldType.Keyword)
	private String oldRoomTypeId;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String oldRoomTypeName;
	@Field(type = FieldType.Keyword)
	private String oldRoomTypeId1;
	@Field(type = FieldType.Integer)
	private Integer roomTypeNum;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String bedDetail;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String bedDetailAdd;
	@Field(type = FieldType.Keyword)
	private String capacity;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
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
	@Field(type = FieldType.Integer)
	private Integer applicability;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String applicabilityDetail;
	@Field(type = FieldType.Keyword)
	private String payWay;
	@Field(type = FieldType.Keyword)
	private String payCanChange;
	@Field(type = FieldType.Keyword)
	private String lineChannel;
	@Field(type = FieldType.Keyword)
	private String isSupported;
	@Field(type = FieldType.Double)
	private Double depositRatio;
	@Field(type = FieldType.Keyword)
	private String receiptFlag;
	@Field(type = FieldType.Keyword)
	private String isSpecialInvoice;
	@Field(type = FieldType.Keyword)
	private String isHourlyRoom;
	@Field(type = FieldType.Keyword)
	private String isConnection;
	@Field(type = FieldType.Keyword)
	private String isAgency;
	@Field(type = FieldType.Keyword)
	private String receiveTextRemark;
	@Field(type = FieldType.Keyword)
	private String stockSaleFlag;
	@Field(type = FieldType.Keyword)
	private String examineType;
	@Field(type = FieldType.Keyword)
	private String productState;
	@Field(type = FieldType.Keyword)
	private String validFlag;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private String createTime;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private String versionNo;
	@Field(type = FieldType.Keyword)
	private String exchangeFlag;
	@Field(type = FieldType.Keyword)
	private String integralFlag;
	@Field(type = FieldType.Keyword)
	private String hotelFlag1;
	@Field(type = FieldType.Keyword)
	private String hotelFlag2;
	@Field(type = FieldType.Keyword)
	private String hotelFlag3;
	@Field(type = FieldType.Keyword)
	private String hotelFlag4;
	@Field(type = FieldType.Keyword)
	private String hotelFlag5;
	@Field(type = FieldType.Keyword)
	private String hotelFlag6;
	@Field(type = FieldType.Keyword)
	private String hotelFlag7;
	@Field(type = FieldType.Keyword)
	private String hotelFlag8;
	@Field(type = FieldType.Keyword)
	private String hotelFlag9;
	@Field(type = FieldType.Keyword)
	private String hotelFlag10;

}
