package com.maple.studyaide.utils;

import android.content.Context;

/**
 * 存储升级相关的信息
 * @author summer
 *
 */
public class ConfigPreferences extends BaseSharedPreferences{

	private static final String NAME = "config";
	public static final String LAST_SELECT_ORDER = "last_select_order";
	public static final String LAST_SELECT_COLLECT = "last_select_collect";
	public static final String LAST_SELECT_ERROR = "last_select_error";
	public static final String LAST_SELECT_EXAM = "last_select_exam";
	public static final String LAST_EXAM_ERROR = "last_exam_error";
	private static ConfigPreferences mInstance;
	
	public static ConfigPreferences getInstance(Context ctx) {
		if(mInstance == null) {
			mInstance = new ConfigPreferences(ctx);
		}
		return mInstance;
	}
	
	private ConfigPreferences(Context ctx) {
		super(ctx);
	}

	@Override
	public String getSpNmae() {
		return NAME;
	}
	
	public void setLastSelectOrder(int lastSelect) {
		getSp().edit().putInt(LAST_SELECT_ORDER, lastSelect).commit();
	}
	
	public int isLastSelectOrder() {
		return getSp().getInt(LAST_SELECT_ORDER, 1);
	}
	
	public void setLastSelectCollect(int lastCollect) {
		getSp().edit().putInt(LAST_SELECT_COLLECT, lastCollect).commit();
	}
	
	public int isLastSelectCollect() {
		return getSp().getInt(LAST_SELECT_COLLECT, 1);
	}
	
	public void setLastSelectError(int lastError) {
		getSp().edit().putInt(LAST_SELECT_ERROR, lastError).commit();
	}
	
	public int isLastSelectError() {
		return getSp().getInt(LAST_SELECT_ERROR, 1);
	}
	
	public void setLastSelectExam(int lastExam) {
		getSp().edit().putInt(LAST_SELECT_EXAM, lastExam).commit();
	}
	
	public int isLastSelectExam() {
		return getSp().getInt(LAST_SELECT_EXAM, 1);
	}
	
	public void setLastExamError(int lastExamRrror) {
		getSp().edit().putInt(LAST_EXAM_ERROR, lastExamRrror).commit();
	}
	
	public int isLastExamError() {
		return getSp().getInt(LAST_EXAM_ERROR, 1);
	}
	
}
