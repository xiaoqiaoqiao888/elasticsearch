package com.rails.elasticsearch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
//@RequestMapping("/${api.version}")
public class KeywordSuggestSearchApi {

	/**
	 * @author embrace
	 * @time: 2018-09-01 00:00:00
	 * @param String
	 *            keyword 查询关键字
	 * @param String
	 *            cityCode
	 * @return ResponseEntity<String>
	 * @description:关键字联想搜索
	 */
	@ApiOperation(value = "关键词联想", notes = "", httpMethod = "POST")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "keyword", value = "关键字", required = true, dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "cityCode", value = "城市编码", defaultValue = "0", dataType = "String", paramType = "query") })
	@RequestMapping(value = "/h5/keywordSuggest/search", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> keywordSuggestSearch(String keyword, String cityCode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("consumptionLevel", "0");// 构建用户标签。
		// return
		// AjaxUtil.success(keywordSuggestServiceSupport.searchKeywordSuggest(keyword,cityCode,jsonObject));
		return null;
	}
}