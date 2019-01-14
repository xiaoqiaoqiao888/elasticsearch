package com.rails.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.rails.elasticsearch.document.Province;

public interface ProvinceDao extends ElasticsearchRepository<Province, String> {

}
