package com.rails.elasticsearch.service;

import java.util.List;

import com.rails.elasticsearch.common.MessageRequest;

public interface MQ2Elasticsearch {
	boolean mqData2Elasticsearch(List<MessageRequest> datas);
}
