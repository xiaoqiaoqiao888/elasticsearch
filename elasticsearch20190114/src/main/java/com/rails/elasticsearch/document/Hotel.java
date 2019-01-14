package com.rails.elasticsearch.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import lombok.Data;

@Data
@Document(indexName = "hotel", type = "hotel")
public class Hotel {
	@Id
	@Field(type = FieldType.Keyword)
	private String hotelId;
	@Field(type = FieldType.Keyword)
	private String bizdate;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private String updateTime;
	@Field(type = FieldType.Keyword)
	private String oldHotelId;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String hotelName;
	@Field(type = FieldType.Keyword)
	private String hotelNameEn;
	@Field(type = FieldType.Double)
	private Double basePrice;
	@Field(type = FieldType.Text)
	private String hotelGroup;
	@Field(type = FieldType.Keyword)
	private String companyType;
	@Field(type = FieldType.Keyword)
	private String fromChannel;
	@Field(type = FieldType.Keyword)
	private String hotelClass1;
	@Field(type = FieldType.Keyword)
	private String hotelClass2;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String hotelClass2Name;
	@Field(type = FieldType.Keyword)
	private String oldHotelClass;
	@Field(type = FieldType.Keyword)
	private String starLevel;
	@Field(type = FieldType.Keyword)
	private String oldStarLevel;
	@Field(type = FieldType.Keyword)
	private String chainBrand;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String chainBrandName;
	@Field(type = FieldType.Keyword)
	private String oldChainBrand;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String oldChainBrandName;
	@Field(type = FieldType.Keyword)
	private String isOnlineSignUp;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String address;
	@Field(type = FieldType.Keyword)
	private String tel;
	@Field(type = FieldType.Keyword)
	private String email;
	@Field(type = FieldType.Keyword)
	private String fax;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String linkman;
	@Field(type = FieldType.Keyword)
	private String linkmanPhone;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String linkmanOther;
	@Field(type = FieldType.Integer)
	private Integer priority;
	@GeoPointField
	private String location;
	@Field(type = FieldType.Double)
	private Double longitude;
	@Field(type = FieldType.Double)
	private Double latitude;
	@Field(type = FieldType.Integer)
	private Integer country;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String countryName;
	@Field(type = FieldType.Keyword)
	private String oldCountry;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String oldCountryName;
	@Field(type = FieldType.Integer)
	private Integer province;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String provinceName;
	@Field(type = FieldType.Keyword)
	private String oldProvince;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String oldProvinceName;
	@Field(type = FieldType.Integer)
	private Integer city;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String cityName;
	@Field(type = FieldType.Keyword)
	private String oldCity;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String oldCityName;
	@Field(type = FieldType.Keyword)
	private String countyCode;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String countyName;
	@Field(type = FieldType.Keyword)
	private String oldCountyCode;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String oldCountyName;
	@Field(type = FieldType.Nested)
	private List<Landmark> landmark;
	@Field(type = FieldType.Nested)
	private List<OldLandmark> oldLandmark;
	@Field(type = FieldType.Nested)
	private List<BusinessArea> businessArea;
	@Field(type = FieldType.Nested)
	private List<OldBusinessArea> oldBusinessArea;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String announcement;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String introduction;
	@Field(type = FieldType.Keyword)
	private String setupYear;
	@Field(type = FieldType.Keyword)
	private String decorateYear;
	@Field(type = FieldType.Nested)
	private List<HotelPolicy> hotelPolicy;
	@Field(type = FieldType.Nested)
	private List<HotelFacility> hotelFacility;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String tags;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String hotelTips;
	@Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
	private String supportCard;
	@Field(type = FieldType.Keyword)
	private String supportCurrency;
	@Field(type = FieldType.Keyword)
	private String foreignGuests;
	@Field(type = FieldType.Keyword)
	private String isSmokeless;
	@Field(type = FieldType.Keyword)
	private String dawnFlag;
	@Field(type = FieldType.Keyword)
	private String receiptFlag;
	@Field(type = FieldType.Keyword)
	private String examineType;
	@Field(type = FieldType.Keyword)
	private String hotelState;
	@Field(type = FieldType.Keyword)
	private String validFlag;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private String createTime;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private String versionNo;
	@Field(type = FieldType.Keyword)
	private String introductionVector;
	@Field(type = FieldType.Double)
	private Double hotelScore;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private String latestOrderTime;
	@Field(type = FieldType.Keyword)
	private String belongComp1;
	@Field(type = FieldType.Keyword)
	private String belongComp2;
	@Field(type = FieldType.Keyword)
	private String nodeNum;
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