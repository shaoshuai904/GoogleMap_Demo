package com.maple.studyaide.db.columns;

public class BaseColumns {

	/**
	 * 数据库reply字段说明 0 - 默认null 1 - 单选A 2 - 单选B 3 - 单选C 4 - 单选D 5 - 多选A、B 6 -
	 * 多选A、C 7 - 多选A、D 8 - 多选B、C 9 - 多选B、D 10 - 多选C、D 11 - 多选A、B、C 12 - 多选A、B、D
	 * 13 - 多选A、C、D 14 - 多选B、C、D 15 - 多选A、B、C、D 16 - 判断True 17 - 判断False
	 */
	public final static int NULL = 0;
	public final static int A = 1;
	public final static int B = 2;
	public final static int C = 3;
	public final static int D = 4;
	public final static int AB = 5;
	public final static int AC = 6;
	public final static int AD = 7;
	public final static int BC = 8;
	public final static int BD = 9;
	public final static int CD = 10;
	public final static int ABC = 11;
	public final static int ABD = 12;
	public final static int ACD = 13;
	public final static int BCD = 14;
	public final static int ABCD = 15;
	public final static int TRUE = 16;
	public final static int FALSE = 17;

	public final static String CREATE_TABLE = "create table if not exists ";
	public final static String ID = "id";
	public final static String INTEGER_PRIMARY_KEY = " integer primary key autoincrement,";
	public final static String COLUMN_TIMU_TITLE = "timu_title";
	public final static String COLUMN_TIMU_ONE = "timu1";
	public final static String COLUMN_TIMU_TOW = "timu2";
	public final static String COLUMN_TIMU_THREE = "timu3";
	public final static String COLUMN_TIMU_FOUR = "timu4";
	public final static String COLUMN_DAAN_ONE = "daan1";
	public final static String COLUMN_DAAN_TOW = "daan2";
	public final static String COLUMN_DAAN_THREE = "daan3";
	public final static String COLUMN_DAAN_FOUR = "daan4";
	public final static String COLUMN_DAAN_DETAIL = "detail";
	public final static String COLUMN_TYPES = "types";
	public final static String COLUMN_REPLY = "reply";
	public final static String TEXT = " text,";
	public final static String VARCHAR_64 = " varchar(64),";
	public final static String START_SQL = "(" + ID + INTEGER_PRIMARY_KEY;
	public final static String END_CREATE_SQL = " integer" + ")";

}
