package com.rails.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.rails.elasticsearch.document.Hotel;

public interface HotelDao extends ElasticsearchRepository<Hotel, String> {

}
