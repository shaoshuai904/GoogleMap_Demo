package com.maple.studyaide.ui.servlet;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * Android 服务基类
 * 
 * @author shaoshuai
 * 
 */
public abstract class AndroidServelet {

	public Context mContext;
	public String url;

	public static interface Callback<T> {
		void onLoaded(T t);

		void onFail();

	}

	/**
	 * 获取数据
	 * 
	 * @param params
	 */
	abstract void get(ServeletParams params);

	/**
	 * 加载数据
	 * 
	 * @param url
	 * @param callBack
	 */
	public static void loadData(String url, RequestCallBack<String> callBack) {
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.configCurrentHttpCacheExpiry(1000 * 10); // 设置缓存10秒，10秒内直接返回上次成功请求的结果。
		httpUtils.send(HttpMethod.GET, url, null, callBack);
	}

}
