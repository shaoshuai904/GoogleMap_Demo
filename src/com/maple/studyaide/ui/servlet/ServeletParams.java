package com.maple.studyaide.ui.servlet;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务参数
 * 
 * @author shaoshuai
 * 
 */
public class ServeletParams {

	private final Map<String, Object> map = new HashMap<String, Object>();

	public Object get(String key) {
		return map.get(key);

	}

	public ServeletParams put(String key, Object value) {
		map.put(key, value);
		return this;
	}

}
