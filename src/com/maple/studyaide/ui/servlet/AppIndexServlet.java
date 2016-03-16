//package com.maple.studyaide.ui.servlet;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//import com.lidroid.xutils.HttpUtils;
//import com.lidroid.xutils.exception.HttpException;
//import com.lidroid.xutils.http.ResponseInfo;
//import com.lidroid.xutils.http.callback.RequestCallBack;
//import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
//import com.maple.studyaide.Const;
//import com.maple.studyaide.FlyApp;
//import com.maple.studyaide.js.bean.JsIndex;
//import com.maple.studyaide.utils.GsonUtil;
//import com.maple.studyaide.utils.SPUtils;
//
///**
// * APP索引-服务程序
// * 
// * @author shaoshuai
// * @param <T>
// * 
// * 
// */
//@SuppressWarnings("rawtypes")
//public class AppIndexServlet extends AndroidServelet {
//
//	private ThreadPoolWrapper pool = ThreadPoolWrapper.newThreadPool(1);
//	private Callback<JsIndex> callBack;
//
//	@SuppressWarnings("unchecked")
//	public AppIndexServlet(Context appContext, Callback listener) {
//		this.mContext = appContext;
//		this.callBack = listener;
//	}
//
//	public void get(ServeletParams params) {
//		url = (String) params.get(Const.GET_URL);
//		String result = SPUtils.getStr(mContext, url, "");
//		processData(result);
//
//		pool.execute(new Runnable() {
//			@Override
//			public void run() {
//				HttpUtils httpUtils = new HttpUtils();
//				// httpUtils.configCurrentHttpCacheExpiry(1000 * 10); //
//				// 设置缓存10秒，10秒内直接返回上次成功请求的结果。
//				httpUtils.send(HttpMethod.GET, url, null, new RequestCallBack<String>() {
//					// 请求成功
//					@Override
//					public void onSuccess(ResponseInfo<String> responseInfo) {
//						// 成功获取最新数据需要去做存储操作
//						SPUtils.putStr(mContext, url, responseInfo.result);
//						processData(responseInfo.result);
//					}
//
//					// 失败
//					@Override
//					public void onFailure(HttpException error, String msg) {
//						// Log.e("功能列表", "获取失败");
//						// String result = SPUtils.getStr(mContext, url, "");
//						// processData(result);
//					}
//				});
//			}
//		});
//
//	}
//
//	/**
//	 * 处理数据
//	 * 
//	 * @param result
//	 */
//	private void processData(final String result) {
//		if (TextUtils.isEmpty(result)) {
//			return;
//		}
//
//		final JsIndex shopList = GsonUtil.json2Bean(result, JsIndex.class);
//		FlyApp.postUi(new Runnable() {
//			@Override
//			public void run() {
//				if (callBack != null) {
//					callBack.onLoaded(shopList);
//				} else {
//					callBack.onFail();
//				}
//			}
//		});
//	}
//
//}
