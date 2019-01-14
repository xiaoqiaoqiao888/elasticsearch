package com.rails.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.rails.elasticsearch.document.City;

public interface CityDao extends ElasticsearchRepository<City, Integer> {

}
