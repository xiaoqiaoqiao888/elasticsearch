package com.rails.elasticsearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rails.elasticsearch.common.MessageRequest;
import com.rails.elasticsearch.service.impl.DataProcess_Hotel_gt10_hotel_adapter;

/**
 * 该测试类暂时解决PG往MQ导入数据失败后，提取未导入的json串，导入ES
 * 
 * @author qiaodongjie
 * @date 2018年9月13日 下午2:54:03
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Test_hotel_adapter_error {
	private Logger logger = LoggerFactory.getLogger(Test_hotel_adapter_error.class);
	@Autowired
	private DataProcess_Hotel_gt10_hotel_adapter dataProcess_Hotel_gt10_hotel_adapter;

	@Test
	public void testDataProcess_Hotel_gt10_hotel_adapter() {
		List<MessageRequest> datas = new ArrayList<>();
		MessageRequest messageRequest = null;
		File file = new File("D:\\Hotel_gt10_hotel_adapter.error.log");
		// BufferedReader reader = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			logger.info("以行为单位读取文件内容，一次读一行");
			String tempString = null;
			int line = 1;
			// 一次读一行，读入null时文件结束
			while ((tempString = reader.readLine()) != null) {
				// 把当前行号显示出来
				logger.info("line " + line + ": " + tempString);
				messageRequest = new MessageRequest();
				messageRequest.setBody(tempString);
				datas.add(messageRequest);
				line++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("重新导入的数据条数为：" + datas.size());
		boolean mqData2Elasticsearch = dataProcess_Hotel_gt10_hotel_adapter.mqData2Elasticsearch(datas);
		Assert.assertTrue(!mqData2Elasticsearch);
	}
}
