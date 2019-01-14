/**
 * Copyright 2017 电子计算技术研究所
 * Author：WenLi
 * 创建日期：2017年7月7日
 */
package com.rails.elasticsearch.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageRequest {
	/**
	 * TODO 请求id，用于判定重复数据 sale_mode#service_type#业务唯一码
	 */
	private String request_id;
	private String register_name;
	private String mobile_no;
	private String email;
	private String order_no;
	private String bind_id;
	private String service_type;
	private String sale_mode;
	private String message_id;
	/**
	 * yyyyMMdd HH:mm
	 */
	private String train_datetime;
	private String station_train_code;
	/**
	 * TODO 发送渠道@MessageType
	 */
	private String message_type;
	private String callBackUrl;
	/**
	 * 用户自定义json字段
	 */
	private String body;
	private String queue_success = "1";
	private String create_time;

	public MessageRequest() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String formatDate = sdf.format(date);
		create_time = formatDate;
	}

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public String getRegister_name() {
		return register_name;
	}

	public void setRegister_name(String register_name) {
		this.register_name = register_name;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getSale_mode() {
		return sale_mode;
	}

	public void setSale_mode(String sale_mode) {
		this.sale_mode = sale_mode;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getQueue_success() {
		return queue_success;
	}

	public void setQueue_success(String queue_success) {
		this.queue_success = queue_success;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getBind_id() {
		return bind_id;
	}

	public void setBind_id(String bind_id) {
		this.bind_id = bind_id;
	}

	public String getTrain_datetime() {
		return train_datetime;
	}

	public void setTrain_datetime(String train_datetime) {
		this.train_datetime = train_datetime;
	}

	public String getStation_train_code() {
		return station_train_code;
	}

	public void setStation_train_code(String station_train_code) {
		this.station_train_code = station_train_code;
	}

	@Override
	public String toString() {
		return "MessageRequest [request_id=" + request_id + ", register_name=" + register_name + ", mobile_no="
				+ mobile_no + ", email=" + email + ", order_no=" + order_no + ", bind_id=" + bind_id + ", service_type="
				+ service_type + ", sale_mode=" + sale_mode + ", message_id=" + message_id + ", train_datetime="
				+ train_datetime + ", station_train_code=" + station_train_code + ", message_type=" + message_type
				+ ", callBackUrl=" + callBackUrl + ", body=" + body + ", queue_success=" + queue_success
				+ ", create_time=" + create_time + "]";
	}
}
