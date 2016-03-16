package com.maple.studyaide.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maple.studyaide.R;
import com.maple.studyaide.db.DBManager;
import com.maple.studyaide.db.columns.BaseColumns;
import com.maple.studyaide.db.columns.CollectColumns;
import com.maple.studyaide.db.columns.ErrorColumns;
import com.maple.studyaide.db.columns.ExamErrorColumns;
import com.maple.studyaide.db.columns.ExamResultColumns;
import com.maple.studyaide.js.bean.CauseInfo;
import com.maple.studyaide.js.bean.HisResult;
import com.maple.studyaide.utils.TimeUtils;

public class ResultActivity extends Activity implements OnClickListener {

	private TextView yida;
	private TextView weida;
	private TextView dadui;
	private TextView haoshi;
	private TextView other;
	private TextView search;
	private int intYida;
	private int intWeida;
	private int intDadui;
	private int intHaoshi;
	private int intDefen;
	private TextView score;
	private int time;
	public static String TIME = "time";
	private String curTime;
	private String exam_name;

	public static void intentToResultActivity(Context context, int time, String name) {
		Intent intent = new Intent(context, ResultActivity.class);
		intent.putExtra(TIME, time);
		intent.putExtra(ExamActivity.NAME, name);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		setContentView(R.layout.activity_result);
		resetTitlebar();
		time = getIntent().getIntExtra(TIME, 0);
		exam_name = getIntent().getStringExtra(ExamActivity.NAME);
		score = (TextView) findViewById(R.id.score);
		yida = (TextView) findViewById(R.id.yida);
		weida = (TextView) findViewById(R.id.weida);
		dadui = (TextView) findViewById(R.id.dadui);
		haoshi = (TextView) findViewById(R.id.haoshi);
		other = (TextView) findViewById(R.id.other);
		search = (TextView) findViewById(R.id.search);
		new QueryTask().execute();
		search.setOnClickListener(this);
	}

	private class QueryTask extends AsyncTask<Void, Void, ArrayList<CauseInfo>> {
		@Override
		protected ArrayList<CauseInfo> doInBackground(Void... params) {
			ArrayList<CauseInfo> list = DBManager.getInstance(ResultActivity.this).query(ExamResultColumns.TABLE_NAME);
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<CauseInfo> list) {
			if (list.size() == 0) {
				yida.setText("已答：" + 0 + "题");
				weida.setText("未答：" + 20 + "题");
				dadui.setText("答对：" + 0 + "题");
				haoshi.setText("耗时：" + TimeUtils.secToTime(60 * 10 - time));
				score.setText("得分：" + 0 + "分");
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				CauseInfo causeInfo = list.get(i);
				int reply = causeInfo.getReply();
				String types = causeInfo.getTypes();
				String daan_one = causeInfo.getDaan_one();
				String daan_tow = causeInfo.getDaan_tow();
				String daan_three = causeInfo.getDaan_three();
				String daan_four = causeInfo.getDaan_four();
				if (reply == BaseColumns.NULL) {
					intWeida++;
				} else {
					intYida++;
					int rightIntValue = getRightIntValue(daan_one, daan_tow, daan_three, daan_four, types);
					if (rightIntValue == reply) {
						intDadui++;
					} else {
						DBManager.getInstance(ResultActivity.this).insert(ExamErrorColumns.TABLE_NAME, causeInfo);
					}
				}
			}
			String useTime = "" + TimeUtils.secToTime(60 * 10 - time);
			String hisResult = "" + intDadui * 5 + "分";
			HisResult myData = new HisResult(curTime, useTime, hisResult, exam_name);
			DBManager.getInstance(ResultActivity.this).insertHisResult(myData);
			yida.setText("已答：" + intYida + "题");
			weida.setText("未答：" + intWeida + "题");
			dadui.setText("答对：" + intDadui + "题");
			haoshi.setText("耗时：" + TimeUtils.secToTime(60 * 10 - time));
			score.setText("得分：" + intDadui * 5 + "分");
			if (intDadui * 5 >= 90) {
				other.setText("成绩还不错，再接再厉哦！");
			} else if (intDadui * 5 == 100) {
				other.setText("您太厉害了，全答对！");
			}
		}
	}

	private void resetTitlebar() {
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.base_comm_titlebar);
		final TextView title = (TextView) findViewById(R.id.titlebar_title);
		LinearLayout back = (LinearLayout) findViewById(R.id.titlebar_left_layout);
		title.setText("本次测试结果");
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private int getRightIntValue(String daan_one, String daan_tow, String daan_three, String daan_four, String types) {
		boolean isRightA = TextUtils.isEmpty(daan_one) ? false : true;
		boolean isRightB = TextUtils.isEmpty(daan_tow) ? false : true;
		boolean isRightC = TextUtils.isEmpty(daan_three) ? false : true;
		boolean isRightD = TextUtils.isEmpty(daan_four) ? false : true;
		int RIGHT_ANSWER = 0;
		if ("单选".equals(types)) {
			if (isRightA == true && isRightB == false && isRightC == false && isRightD == false) {
				RIGHT_ANSWER = BaseColumns.A;
			} else if (isRightA == false && isRightB == true && isRightC == false && isRightD == false) {
				RIGHT_ANSWER = BaseColumns.B;
			} else if (isRightA == false && isRightB == false && isRightC == true && isRightD == false) {
				RIGHT_ANSWER = BaseColumns.C;
			} else if (isRightA == false && isRightB == false && isRightC == false && isRightD == true) {
				RIGHT_ANSWER = BaseColumns.D;
			}
		} else if ("多选".equals(types)) {
			if (isRightA == true && isRightB == true && isRightC == false && isRightD == false) {
				RIGHT_ANSWER = BaseColumns.AB;
			} else if (isRightA == true && isRightC == true && isRightB == false && isRightD == false) {
				RIGHT_ANSWER = BaseColumns.AC;
			} else if (isRightA == true && isRightD == true && isRightB == false && isRightC == false) {
				RIGHT_ANSWER = BaseColumns.AD;
			} else if (isRightB == true && isRightC == true && isRightA == false && isRightD == false) {
				RIGHT_ANSWER = BaseColumns.BC;
			} else if (isRightB == true && isRightD == true && isRightA == false && isRightC == false) {
				RIGHT_ANSWER = BaseColumns.BD;
			} else if (isRightC == true && isRightD == true && isRightA == false && isRightB == false) {
				RIGHT_ANSWER = BaseColumns.CD;
			} else if (isRightA == true && isRightB == true && isRightC == true && isRightD == false) {
				RIGHT_ANSWER = BaseColumns.ABC;
			} else if (isRightA == true && isRightB == true && isRightD == true && isRightC == false) {
				RIGHT_ANSWER = BaseColumns.ABD;
			} else if (isRightA == true && isRightC == true && isRightD == true && isRightB == false) {
				RIGHT_ANSWER = BaseColumns.ACD;
			} else if (isRightB == true && isRightC == true && isRightD == true && isRightA == false) {
				RIGHT_ANSWER = BaseColumns.BCD;
			} else if (isRightA == true && isRightB == true && isRightC == true && isRightD == true) {
				RIGHT_ANSWER = BaseColumns.ABCD;
			}
		} else if ("判断".equals(types)) {
			if (isRightA == true && isRightB == false && isRightC == false && isRightD == false) {
				RIGHT_ANSWER = BaseColumns.TRUE;
			} else if (isRightA == false && isRightB == true && isRightC == false && isRightD == false) {
				RIGHT_ANSWER = BaseColumns.FALSE;
			}
		}

		return RIGHT_ANSWER;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		DBManager.getInstance(this).removeAll(ExamResultColumns.TABLE_NAME);
		DBManager.getInstance(this).removeAll(ExamErrorColumns.TABLE_NAME);
		DBManager.getInstance(this).updateWhenDestroy(CollectColumns.TABLE_NAME);
		DBManager.getInstance(this).updateWhenDestroy(ErrorColumns.TABLE_NAME);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search:
			startActivity(new Intent(this, ExamErrorActivity.class));
			break;

		default:
			break;
		}
	}

}
