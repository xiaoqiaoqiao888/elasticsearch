package com.rails.elasticsearch.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rails.elasticsearch.document.Hotel;
import com.rails.elasticsearch.service.HotelService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/v1", produces = "application/json;charset=utf-8")
public class HotelController {

	@Autowired
	private HotelService hotelService;

	/**
	 * 根据索引和类型获取mapping
	 * 
	 * @param indexName
	 * @param type
	 * @return
	 */
	@GetMapping("/hotel/get/mapping")
	@ApiOperation(value = "根据索引和类型获取mapping", notes = "根据索引和类型获取mapping")
	public Map<?, ?> getMapping(@RequestParam String indexName, @RequestParam String type) {
		return hotelService.getMapping(indexName, type);
	}

	/**
	 * 根据城市名称term查询
	 * 
	 * @return
	 */
	@GetMapping("/hotel/cityName/term")
	@ApiOperation(value = "根据城市名称term查询", notes = "根据城市名称term查询")
	public Iterable<Hotel> termQueryByCityName(@RequestParam String cityName) {
		return hotelService.termQueryByCityName(cityName);
	}

	/**
	 * 根据商业区名称查找
	 * 
	 * @return
	 */
	@GetMapping("/hotel/businessArea/match")
	@ApiOperation(value = "根据商业区匹配查找", notes = "根据商业区匹配查找")
	public List<Hotel> matchQuery(@RequestParam String businessAreaCode, @RequestParam String businessAreaName) {
		return hotelService.matchQuery(businessAreaCode, businessAreaName);
	}
}
