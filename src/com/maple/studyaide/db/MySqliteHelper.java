package com.maple.studyaide.db;

import com.maple.studyaide.db.columns.AnswerColumns;
import com.maple.studyaide.db.columns.CollectColumns;
import com.maple.studyaide.db.columns.ErrorColumns;
import com.maple.studyaide.db.columns.ExamErrorColumns;
import com.maple.studyaide.db.columns.ExamResultColumns;
import com.maple.studyaide.db.columns.HistoryResultColumns;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper {

	private final static int VERSION = 1;

	public MySqliteHelper(Context context) {
		super(context, "RailwaySystem.db", null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(AnswerColumns.getCreateTableSql());
		db.execSQL(CollectColumns.getCreateTableSql());
		db.execSQL(ErrorColumns.getCreateTableSql());
		db.execSQL(ExamResultColumns.getCreateTableSql());
		db.execSQL(ExamErrorColumns.getCreateTableSql());
		db.execSQL(HistoryResultColumns.getCreateTableSql());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
