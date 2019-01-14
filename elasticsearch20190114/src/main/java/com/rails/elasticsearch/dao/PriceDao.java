package com.rails.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.rails.elasticsearch.document.Price;

public interface PriceDao extends ElasticsearchRepository<Price, String> {

}
