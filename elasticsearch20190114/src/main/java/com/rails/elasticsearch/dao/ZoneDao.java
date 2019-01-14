package com.rails.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.rails.elasticsearch.document.Zone;

public interface ZoneDao extends ElasticsearchRepository<Zone, String> {

}
