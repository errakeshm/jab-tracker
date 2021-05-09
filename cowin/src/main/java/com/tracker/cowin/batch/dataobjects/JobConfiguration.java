package com.tracker.cowin.batch.dataobjects;

import java.util.HashMap;
import java.util.Map;

public class JobConfiguration {
	private String name;
	private String api;
	private Map<String, String> params;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public void addParam(String key, String value) {
		if(params == null)
			params = new HashMap<>();
		params.put(key, value);
	}
	@Override
	public String toString() {
		return "JobConfiguration [name=" + name + ", api=" + api + ", params=" + params + "]";
	}
	
}