package com.rails.elasticsearch.common;

public class MessageInfo {
	private MessageRequest messageRequest;
	private String pre_message_id;
	private String topic;
	private String tags;
	private String title;
	boolean timeMsg = false;

	public MessageRequest getMessageRequest() {
		return messageRequest;
	}

	public void setMessageRequest(MessageRequest messageRequest) {
		this.messageRequest = messageRequest;
	}

	public String getPre_message_id() {
		return pre_message_id;
	}

	public void setPre_message_id(String pre_message_id) {
		this.pre_message_id = pre_message_id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isTimeMsg() {
		return timeMsg;
	}

	public void setTimeMsg(boolean timeMsg) {
		this.timeMsg = timeMsg;
	}

	@Override
	public String toString() {
		return "MessageInfo [messageRequest=" + messageRequest + ", pre_message_id=" + pre_message_id + ", topic="
				+ topic + ", tags=" + tags + ", title=" + title + ", timeMsg=" + timeMsg + "]";
	}
}
