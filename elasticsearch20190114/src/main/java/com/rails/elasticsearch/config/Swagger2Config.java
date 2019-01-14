/**
 * Copyright 2018 电子计算技术研究所
 * Author：WenLi
 * 创建日期：2018年7月10日
 */
package com.rails.elasticsearch.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

	@Bean
	public Docket createRestApi() {

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(regex("/api/v1.*")).build().pathMapping("/").directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class).useDefaultResponseMessages(false)
				.tags(new Tag("search-service", "搜索服务"))//
				.apiInfo(apiInfo());
	}

	ApiInfo apiInfo() {
		Contact contact = new Contact("elasticsearch搜索服务", "https://github.com/xiaoqiaoqiao888/elascticsearch.git",
				"qiaodongjie@rails.com");
		return new ApiInfoBuilder()//
				.title("ES提供的Api")//
				.description("ES服务接口说明")//
				.contact(contact)//
				.version("1.0")//
				.build();
	}
}
