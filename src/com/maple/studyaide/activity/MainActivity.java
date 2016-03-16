package com.maple.studyaide.activity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.maple.studyaide.FlyApp;
import com.maple.studyaide.R;
import com.maple.studyaide.db.DBManager;
import com.maple.studyaide.db.columns.AnswerColumns;
import com.maple.studyaide.db.columns.BaseColumns;
import com.maple.studyaide.js.bean.CauseInfo;
import com.maple.studyaide.ui.dialog.LoadingDialog;
import com.maple.studyaide.utils.T;
import com.maple.studyaide.utils.TimeUtils;

/**
 * 主界面
 * 
 * @author shaoshuai
 * 
 */
public class MainActivity extends Activity implements OnClickListener {
	@ViewInject(R.id.tv_back)
	private TextView tv_back;// 返回
	@ViewInject(R.id.tv_title)
	private TextView tv_title;// 标题

	@ViewInject(R.id.rl_lianxi)
	private RelativeLayout order;// 练习
	@ViewInject(R.id.rl_kaoshi)
	private RelativeLayout simulate;// 考试

	@ViewInject(R.id.favorite)
	private LinearLayout favorite;// 收藏
	@ViewInject(R.id.wrong)
	private LinearLayout wrong;// 错题
	@ViewInject(R.id.history)
	private LinearLayout history;// 成绩

	@ViewInject(R.id.recommend)
	private TextView recommend;// 应用推荐

	private Context mContext;
	private String title = "四级题库";
	private LoadingDialog mLoadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (TimeUtils.isNetworkAvailable(this)) {
			DBManager.getInstance(this).removeAll(AnswerColumns.TABLE_NAME);
		}
		setContentView(R.layout.activity_main);
		mContext = getApplicationContext();

		ViewUtils.inject(this);
		initView();
	}

	private void initView() {
		mLoadingDialog = new LoadingDialog(this);
		tv_back.setVisibility(View.GONE);
		tv_title.setText(title);

		asynctaskInstance();

		order.setOnClickListener(this);
		simulate.setOnClickListener(this);
		recommend.setOnClickListener(this);
		favorite.setOnClickListener(this);
		wrong.setOnClickListener(this);
		history.setOnClickListener(this);
	}

	private void asynctaskInstance() {
		mLoadingDialog.show("正在加载...");
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(FlyApp.HOME_CATEGORIES, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String result = new String(arg2);
				try {
					JSONObject jsonObject = new JSONObject(result);
					int code = jsonObject.getInt("code");
					if (code == 1) {
						String content = jsonObject.getString("content");
						JSONArray array = new JSONArray(content);
						for (int i = 0; i < content.length(); i++) {
							JSONObject object = array.getJSONObject(i);
							String timu_title = new JSONObject(object.getString("timu")).getString("title");
							String timu_one = new JSONObject(object.getString("timu")).getString("one");
							String timu_tow = new JSONObject(object.getString("timu")).getString("tow");
							String timu_three = new JSONObject(object.getString("timu")).getString("three");
							String timu_four = new JSONObject(object.getString("timu")).getString("four");
							String daan_one = new JSONObject(object.getString("daan")).getString("daan_one");
							String daan_tow = new JSONObject(object.getString("daan")).getString("daan_tow");
							String daan_three = new JSONObject(object.getString("daan")).getString("daan_three");
							String daan_four = new JSONObject(object.getString("daan")).getString("daan_four");
							String types = new JSONObject(object.getString("types")).getString("types");
							String detail = new JSONObject(object.getString("detail")).getString("detail");
							int reply = BaseColumns.NULL;
							CauseInfo myData = new CauseInfo(timu_title, timu_one, timu_tow, timu_three, timu_four,
									daan_one, daan_tow, daan_three, daan_four, detail, types, reply);
							DBManager.getInstance(mContext).insert(AnswerColumns.TABLE_NAME, myData);
						}
					} else {
						T.showShort(mContext, "数据解析出现异常，请联系管理员");
					}
					// progressBar.setVisibility(View.GONE);
					mLoadingDialog.dismiss();
				} catch (JSONException e) {
					mLoadingDialog.dismiss();
					// progressBar.setVisibility(View.GONE);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				// progressBar.setVisibility(View.GONE);
				mLoadingDialog.dismiss();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.recommend:
			// 调用方式一：直接打开全屏积分墙
			toWebPage("讲讲", "http://www.speaktool.com/");
			break;
		case R.id.rl_lianxi:// 顺序练习
			startActivity(new Intent(this, OrderActivity.class));
			break;
		case R.id.rl_kaoshi:// 模拟考试
			kaoshi();
			break;
		case R.id.favorite:// 我的收藏
			startActivity(new Intent(this, CollectActivity.class));
			break;
		case R.id.wrong:// 我的错题
			startActivity(new Intent(this, ErrorActivity.class));
			break;
		case R.id.history:// 历史成绩
			startActivity(new Intent(this, HisResultActivity.class));
			break;
		default:
			break;
		}
	}

	private void kaoshi() {
		View layout = getLayoutInflater().inflate(R.layout.enter_simulate, null);
		final Dialog dialog = new Dialog(this);
		dialog.setTitle("温馨提示");
		dialog.show();
		dialog.getWindow().setContentView(layout);
		final EditText et_name = (EditText) layout.findViewById(R.id.et_name);
		TextView confirm = (TextView) layout.findViewById(R.id.confirm);
		TextView cancel = (TextView) layout.findViewById(R.id.cancel);
		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
					T.showShort(mContext, "请先输入考试姓名");
				} else {
					ExamActivity.intentToExamActivity(MainActivity.this, et_name.getText().toString().trim());
					T.showShort(mContext, "考试开始");
				}
				dialog.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				T.showShort(mContext, "再按一次退出程序");
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				// System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/** 去新闻页面 */
	private void toWebPage(String title, String url) {
		Intent intent = new Intent(mContext, WebActivity.class);
		intent.putExtra(WebActivity.EXTRA_TITLE, title);// 功能Item
		intent.putExtra(WebActivity.EXTRA_URL, url);// 功能Item
		startActivity(intent);// 开启目标Activity
	}

}
