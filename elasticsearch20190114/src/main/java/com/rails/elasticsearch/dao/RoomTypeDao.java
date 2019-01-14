package com.rails.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.rails.elasticsearch.document.RoomType;

public interface RoomTypeDao extends ElasticsearchRepository<RoomType, String> {

}
