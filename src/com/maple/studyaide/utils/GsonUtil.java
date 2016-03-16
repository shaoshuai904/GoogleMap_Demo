package com.maple.studyaide.utils;

import com.google.gson.Gson;

public class GsonUtil {
	public static <T> T json2Bean(String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}
}
