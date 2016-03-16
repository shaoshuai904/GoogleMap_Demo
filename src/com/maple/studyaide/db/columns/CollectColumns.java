package com.maple.studyaide.db.columns;


public class CollectColumns extends BaseColumns {

	public final static String TABLE_NAME = "collect";

	public static String getCreateTableSql() {
		return CREATE_TABLE + TABLE_NAME + START_SQL + COLUMN_TIMU_TITLE + TEXT + COLUMN_TIMU_ONE + VARCHAR_64
				+ COLUMN_TIMU_TOW + VARCHAR_64 + COLUMN_TIMU_THREE + VARCHAR_64 + COLUMN_TIMU_FOUR + VARCHAR_64
				+ COLUMN_DAAN_ONE + VARCHAR_64 + COLUMN_DAAN_TOW + VARCHAR_64 + COLUMN_DAAN_THREE + VARCHAR_64
				+ COLUMN_DAAN_FOUR + VARCHAR_64 + COLUMN_DAAN_DETAIL + TEXT + COLUMN_TYPES + VARCHAR_64 + COLUMN_REPLY
				+ END_CREATE_SQL;
	}

}
