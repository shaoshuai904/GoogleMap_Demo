package com.maple.studyaide.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * 常用单位转换的辅助类
 * 
 * @author shao
 * 
 */
public class DensityUtils {
	private DensityUtils() {
		/* 不能被实例化 */
		throw new UnsupportedOperationException("不能被实例化");
	}

	/**
	 * dp转px
	 */
	public static int dp2px(Context context, float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources()
				.getDisplayMetrics());
	}

	/**
	 * px转dp
	 */
	public static float px2dp(Context context, float pxVal) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxVal / scale);
	}

	/**
	 * sp转px
	 */
	public static int sp2px(Context context, float spVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources()
				.getDisplayMetrics());
	}

	/**
	 * px转sp
	 */
	public static float px2sp(Context context, float pxVal) {
		return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
	}

}
