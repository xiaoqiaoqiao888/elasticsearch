package com.rails.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.rails.elasticsearch.document.Product;

public interface ProductDao extends ElasticsearchRepository<Product, String> {

}
