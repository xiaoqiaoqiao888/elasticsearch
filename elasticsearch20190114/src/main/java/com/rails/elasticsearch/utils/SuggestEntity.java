package com.rails.elasticsearch.utils;

import com.alibaba.fastjson.JSONObject;

public class SuggestEntity {
	private String id;
	private String routing;
	private JSONObject jsonObject;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRouting() {
		return routing;
	}

	public void setRouting(String routing) {
		this.routing = routing;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jsonObject == null) ? 0 : jsonObject.hashCode());
		result = prime * result + ((routing == null) ? 0 : routing.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuggestEntity other = (SuggestEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (jsonObject == null) {
			if (other.jsonObject != null)
				return false;
		} else if (!jsonObject.equals(other.jsonObject))
			return false;
		if (routing == null) {
			if (other.routing != null)
				return false;
		} else if (!routing.equals(other.routing))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SuggestEntity [id=" + id + ", routing=" + routing + ", jsonObject=" + jsonObject + "]";
	}

}
