package com.rails.elasticsearch.service;

import java.util.List;
import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.rails.elasticsearch.dao.HotelDao;
import com.rails.elasticsearch.document.BusinessArea;
import com.rails.elasticsearch.document.Hotel;

@Service
public class HotelService {

	@Autowired
	private HotelDao hotelDao;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	/**
	 * 根据索引和类型获取mapping
	 * 
	 * @param indexName
	 * @param type
	 * @return
	 */
	public Map<?, ?> getMapping(String indexName, String type) {
		Map<?, ?> mapping = elasticsearchTemplate.getMapping(indexName, type);

		return mapping;
	}

	/**
	 * term 查询
	 */
	public Iterable<Hotel> termQueryByCityName(String cityName) {
		TermQueryBuilder termQuery = QueryBuilders.termQuery("cityName", cityName);

//		TermQueryBuilder builder = new TermQueryBuilder("cityName", cityName);
		Iterable<Hotel> hotels = hotelDao.search(termQuery);

		return hotels;
	}

	/**
	 * terms 查询
	 */
	public Iterable<Hotel> termsQuery() {
		TermsQueryBuilder builder = new TermsQueryBuilder("businessAreaName", "北京", "南京", "天津");

		Iterable<Hotel> hotels = hotelDao.search(builder);

		return hotels;
	}

	/**
	 * match 查询
	 */
	public List<Hotel> matchQuery(String businessAreaCode, String businessAreaName) {
		BusinessArea businessArea = new BusinessArea();
		businessArea.setBusinessAreaCode(businessAreaCode);
		businessArea.setBusinessAreaName(businessAreaName);
		Pageable page = PageRequest.of(0, 10);
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		queryBuilder.must(QueryBuilders.matchQuery("businessArea", businessArea));
		SearchQuery query = new NativeSearchQueryBuilder().withQuery(queryBuilder).withPageable(page).build();
		Page<Hotel> pages = hotelDao.search(query);
		return pages.getContent();
	}

	/**
	 * multi-match查询
	 */
	public Iterable<Hotel> multiMatchQuery() {
		int pageNum = 1;
		int pageSize = 15;
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
		MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery("国贸地区", "hotelName", "businessAreaName");
		return hotelDao.search(queryBuilder, pageable);
	}
}
