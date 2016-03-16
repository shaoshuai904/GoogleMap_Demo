package com.maple.studyaide.db;

import java.util.ArrayList;

import com.maple.studyaide.db.columns.AnswerColumns;
import com.maple.studyaide.db.columns.BaseColumns;
import com.maple.studyaide.db.columns.HistoryResultColumns;
import com.maple.studyaide.js.bean.CauseInfo;
import com.maple.studyaide.js.bean.CauseInfoHasId;
import com.maple.studyaide.js.bean.HisResult;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	private static DBManager mInstance;
	private SQLiteDatabase mDB;

	public ArrayList<CauseInfo> query(String tableName) {
		Cursor cursor = mDB.query(tableName, null, null, null, null, null, null);
		boolean hasNext = cursor.moveToFirst();
		ArrayList<CauseInfo> mData = new ArrayList<CauseInfo>();
		while (hasNext) {
			String timu_title = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_TIMU_TITLE));
			String timu_one = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_TIMU_ONE));
			String timu_tow = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_TIMU_TOW));
			String timu_three = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_TIMU_THREE));
			String timu_four = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_TIMU_FOUR));
			String daan_one = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_DAAN_ONE));
			String daan_tow = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_DAAN_TOW));
			String daan_three = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_DAAN_THREE));
			String daan_four = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_DAAN_FOUR));
			String detail = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_DAAN_DETAIL));
			String types = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_TYPES));
			int reply = cursor.getInt(cursor.getColumnIndex(BaseColumns.COLUMN_REPLY));
			CauseInfo info = new CauseInfo(timu_title, timu_one, timu_tow, timu_three, timu_four, daan_one, daan_tow,
					daan_three, daan_four, detail, types, reply);
			mData.add(info);
			hasNext = cursor.moveToNext();
		}
		cursor.close();
		return mData;
	}

	public ArrayList<CauseInfoHasId> queryHasId(String tableName) {
		Cursor cursor = mDB.query(tableName, null, null, null, null, null, null);
		boolean hasNext = cursor.moveToFirst();
		ArrayList<CauseInfoHasId> mData = new ArrayList<CauseInfoHasId>();
		while (hasNext) {
			int id = cursor.getInt(cursor.getColumnIndex(BaseColumns.ID));
			String timu_title = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_TIMU_TITLE));
			String timu_one = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_TIMU_ONE));
			String timu_tow = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_TIMU_TOW));
			String timu_three = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_TIMU_THREE));
			String timu_four = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_TIMU_FOUR));
			String daan_one = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_DAAN_ONE));
			String daan_tow = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_DAAN_TOW));
			String daan_three = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_DAAN_THREE));
			String daan_four = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_DAAN_FOUR));
			String detail = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_DAAN_DETAIL));
			String types = cursor.getString(cursor.getColumnIndex(BaseColumns.COLUMN_TYPES));
			int reply = cursor.getInt(cursor.getColumnIndex(BaseColumns.COLUMN_REPLY));
			CauseInfoHasId info = new CauseInfoHasId(id, timu_title, timu_one, timu_tow, timu_three, timu_four,
					daan_one, daan_tow, daan_three, daan_four, detail, types, reply);
			mData.add(info);
			hasNext = cursor.moveToNext();
		}
		cursor.close();
		return mData;
	}

	public ArrayList<CauseInfo> queryExam(String tableName) {
		Cursor cursorDan = mDB.query(tableName, null, BaseColumns.COLUMN_TYPES + "=?", new String[] { "单选" }, null,
				null, "RANDOM() limit 8");
		Cursor cursorDuo = mDB.query(tableName, null, BaseColumns.COLUMN_TYPES + "=?", new String[] { "多选" }, null,
				null, "RANDOM() limit 6");
		Cursor cursorPan = mDB.query(tableName, null, BaseColumns.COLUMN_TYPES + "=?", new String[] { "判断" }, null,
				null, "RANDOM() limit 6");
		boolean hasNextDan = cursorDan.moveToFirst();
		ArrayList<CauseInfo> mData = new ArrayList<CauseInfo>();
		while (hasNextDan) {
			String timu_title = cursorDan.getString(cursorDan.getColumnIndex(BaseColumns.COLUMN_TIMU_TITLE));
			String timu_one = cursorDan.getString(cursorDan.getColumnIndex(BaseColumns.COLUMN_TIMU_ONE));
			String timu_tow = cursorDan.getString(cursorDan.getColumnIndex(BaseColumns.COLUMN_TIMU_TOW));
			String timu_three = cursorDan.getString(cursorDan.getColumnIndex(BaseColumns.COLUMN_TIMU_THREE));
			String timu_four = cursorDan.getString(cursorDan.getColumnIndex(BaseColumns.COLUMN_TIMU_FOUR));
			String daan_one = cursorDan.getString(cursorDan.getColumnIndex(BaseColumns.COLUMN_DAAN_ONE));
			String daan_tow = cursorDan.getString(cursorDan.getColumnIndex(BaseColumns.COLUMN_DAAN_TOW));
			String daan_three = cursorDan.getString(cursorDan.getColumnIndex(BaseColumns.COLUMN_DAAN_THREE));
			String daan_four = cursorDan.getString(cursorDan.getColumnIndex(BaseColumns.COLUMN_DAAN_FOUR));
			String detail = cursorDan.getString(cursorDan.getColumnIndex(BaseColumns.COLUMN_DAAN_DETAIL));
			String types = cursorDan.getString(cursorDan.getColumnIndex(BaseColumns.COLUMN_TYPES));
			int reply = cursorDan.getInt(cursorDan.getColumnIndex(BaseColumns.COLUMN_REPLY));
			CauseInfo info = new CauseInfo(timu_title, timu_one, timu_tow, timu_three, timu_four, daan_one, daan_tow,
					daan_three, daan_four, detail, types, reply);
			mData.add(info);
			hasNextDan = cursorDan.moveToNext();
		}
		cursorDan.close();
		boolean hasNextDuo = cursorDuo.moveToFirst();
		while (hasNextDuo) {
			String timu_title = cursorDuo.getString(cursorDuo.getColumnIndex(BaseColumns.COLUMN_TIMU_TITLE));
			String timu_one = cursorDuo.getString(cursorDuo.getColumnIndex(BaseColumns.COLUMN_TIMU_ONE));
			String timu_tow = cursorDuo.getString(cursorDuo.getColumnIndex(BaseColumns.COLUMN_TIMU_TOW));
			String timu_three = cursorDuo.getString(cursorDuo.getColumnIndex(BaseColumns.COLUMN_TIMU_THREE));
			String timu_four = cursorDuo.getString(cursorDuo.getColumnIndex(BaseColumns.COLUMN_TIMU_FOUR));
			String daan_one = cursorDuo.getString(cursorDuo.getColumnIndex(BaseColumns.COLUMN_DAAN_ONE));
			String daan_tow = cursorDuo.getString(cursorDuo.getColumnIndex(BaseColumns.COLUMN_DAAN_TOW));
			String daan_three = cursorDuo.getString(cursorDuo.getColumnIndex(BaseColumns.COLUMN_DAAN_THREE));
			String daan_four = cursorDuo.getString(cursorDuo.getColumnIndex(BaseColumns.COLUMN_DAAN_FOUR));
			String detail = cursorDuo.getString(cursorDuo.getColumnIndex(BaseColumns.COLUMN_DAAN_DETAIL));
			String types = cursorDuo.getString(cursorDuo.getColumnIndex(BaseColumns.COLUMN_TYPES));
			int reply = cursorDuo.getInt(cursorDuo.getColumnIndex(BaseColumns.COLUMN_REPLY));
			CauseInfo info = new CauseInfo(timu_title, timu_one, timu_tow, timu_three, timu_four, daan_one, daan_tow,
					daan_three, daan_four, detail, types, reply);
			mData.add(info);
			hasNextDuo = cursorDuo.moveToNext();
		}
		cursorDuo.close();
		boolean hasNextPan = cursorPan.moveToFirst();
		while (hasNextPan) {
			String timu_title = cursorPan.getString(cursorPan.getColumnIndex(BaseColumns.COLUMN_TIMU_TITLE));
			String timu_one = cursorPan.getString(cursorPan.getColumnIndex(BaseColumns.COLUMN_TIMU_ONE));
			String timu_tow = cursorPan.getString(cursorPan.getColumnIndex(BaseColumns.COLUMN_TIMU_TOW));
			String timu_three = cursorPan.getString(cursorPan.getColumnIndex(BaseColumns.COLUMN_TIMU_THREE));
			String timu_four = cursorPan.getString(cursorPan.getColumnIndex(BaseColumns.COLUMN_TIMU_FOUR));
			String daan_one = cursorPan.getString(cursorPan.getColumnIndex(BaseColumns.COLUMN_DAAN_ONE));
			String daan_tow = cursorPan.getString(cursorPan.getColumnIndex(BaseColumns.COLUMN_DAAN_TOW));
			String daan_three = cursorPan.getString(cursorPan.getColumnIndex(BaseColumns.COLUMN_DAAN_THREE));
			String daan_four = cursorPan.getString(cursorPan.getColumnIndex(BaseColumns.COLUMN_DAAN_FOUR));
			String detail = cursorPan.getString(cursorPan.getColumnIndex(BaseColumns.COLUMN_DAAN_DETAIL));
			String types = cursorPan.getString(cursorPan.getColumnIndex(BaseColumns.COLUMN_TYPES));
			int reply = cursorPan.getInt(cursorPan.getColumnIndex(BaseColumns.COLUMN_REPLY));
			CauseInfo info = new CauseInfo(timu_title, timu_one, timu_tow, timu_three, timu_four, daan_one, daan_tow,
					daan_three, daan_four, detail, types, reply);
			mData.add(info);
			hasNextPan = cursorPan.moveToNext();
		}
		cursorPan.close();
		return mData;
	}

	public ArrayList<HisResult> queryHisResult(String tableName) {
		Cursor cursor = mDB.query(tableName, null, null, null, null, null, null);
		boolean hasNext = cursor.moveToFirst();
		ArrayList<HisResult> mData = new ArrayList<HisResult>();
		while (hasNext) {
			String curTime = cursor.getString(cursor.getColumnIndex(HistoryResultColumns.COLUMN_CUR_TIME));
			String useTime = cursor.getString(cursor.getColumnIndex(HistoryResultColumns.COLUMN_USE_TIME));
			String hisResult = cursor.getString(cursor.getColumnIndex(HistoryResultColumns.COLUMN_HIS_RESULT));
			String userName = cursor.getString(cursor.getColumnIndex(HistoryResultColumns.COLUMN_USER_NAME));
			HisResult result = new HisResult(curTime, useTime, hisResult, userName);
			mData.add(result);
			hasNext = cursor.moveToNext();
		}
		cursor.close();
		return mData;
	}

	public void insert(String tableName, CauseInfo info) {
		// 判断数据库中是否已存在
		Cursor cursor = mDB.query(tableName, new String[] { BaseColumns.COLUMN_TIMU_TITLE },
				BaseColumns.COLUMN_TIMU_TITLE + "=?", new String[] { info.getTimu_title() }, null, null, null);
		if (cursor.getCount() == 0) {
			ContentValues values = new ContentValues();
			values.put(AnswerColumns.COLUMN_TIMU_TITLE, info.getTimu_title());
			values.put(AnswerColumns.COLUMN_TIMU_ONE, info.getTimu_one());
			values.put(AnswerColumns.COLUMN_TIMU_TOW, info.getTimu_tow());
			values.put(AnswerColumns.COLUMN_TIMU_THREE, info.getTimu_three());
			values.put(AnswerColumns.COLUMN_TIMU_FOUR, info.getTimu_four());
			values.put(AnswerColumns.COLUMN_DAAN_ONE, info.getDaan_one());
			values.put(AnswerColumns.COLUMN_DAAN_TOW, info.getDaan_tow());
			values.put(AnswerColumns.COLUMN_DAAN_THREE, info.getDaan_three());
			values.put(AnswerColumns.COLUMN_DAAN_FOUR, info.getDaan_four());
			values.put(AnswerColumns.COLUMN_DAAN_DETAIL, info.getDetail());
			values.put(AnswerColumns.COLUMN_TYPES, info.getTypes());
			values.put(AnswerColumns.COLUMN_REPLY, info.reply);
			mDB.insert(tableName, null, values);
		}
	}

	public void insertHisResult(HisResult info) {
		// 判断数据库中是否已存在
		ContentValues values = new ContentValues();
		values.put(HistoryResultColumns.COLUMN_CUR_TIME, info.getCurTime());
		values.put(HistoryResultColumns.COLUMN_USE_TIME, info.getUseTime());
		values.put(HistoryResultColumns.COLUMN_HIS_RESULT, info.getHisResult());
		values.put(HistoryResultColumns.COLUMN_USER_NAME, info.getName());
		mDB.insert(HistoryResultColumns.TABLE_NAME, null, values);
	}

	public void update(String tableName, CauseInfo info) {
		ContentValues values = new ContentValues();
		values.put(BaseColumns.COLUMN_REPLY, info.reply);
		mDB.update(tableName, values, BaseColumns.COLUMN_TIMU_TITLE + "=?", new String[] { info.getTimu_title() });
	}

	public void updateWhenDestroy(String tableName) {
		ContentValues values = new ContentValues();
		values.put(BaseColumns.COLUMN_REPLY, BaseColumns.NULL);
		mDB.update(tableName, values, null, null);
	}

	public void remove(String tableName, CauseInfo info) {
		mDB.delete(tableName, BaseColumns.COLUMN_TIMU_TITLE + "=?", new String[] { info.getTimu_title() });
	}

	public void removeAll(String tableName) {
		mDB.delete(tableName, null, null);
	}

	public static synchronized DBManager getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DBManager(context);
		}
		return mInstance;
	}

	private DBManager(Context context) {
		MySqliteHelper helper = new MySqliteHelper(context);
		mDB = helper.getWritableDatabase();
	}

}
