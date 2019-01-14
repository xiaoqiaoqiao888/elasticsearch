package com.rails.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.rails.elasticsearch.document.Business_area;

public interface Business_areaDao extends ElasticsearchRepository<Business_area, Integer> {

}
