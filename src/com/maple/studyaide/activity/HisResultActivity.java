package com.maple.studyaide.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.maple.studyaide.R;
import com.maple.studyaide.db.DBManager;
import com.maple.studyaide.db.columns.HistoryResultColumns;
import com.maple.studyaide.js.bean.HisResult;
import com.maple.studyaide.utils.ViewHolder;

public class HisResultActivity extends Activity implements OnClickListener {

	private ArrayList<HisResult> list;
	private MyAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		super.onCreate(savedInstanceState);
		// 先读取数据库中的缓存, 数据量较多比较耗时，应使用AsyncTask
		new QueryTask().execute();
		setContentView(R.layout.activity_his_result);
		resetTitlebar();
		ListView listView = (ListView) findViewById(R.id.listView1);
		myAdapter = new MyAdapter();
		listView.setAdapter(myAdapter);
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			if (list.size() == 0) {
				return 0;
			} else {
				return list.size();
			}
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_listview_hisresult, null);
			}
			TextView name = ViewHolder.get(convertView, R.id.name);
			TextView curTime = ViewHolder.get(convertView, R.id.curTime);
			TextView useTime = ViewHolder.get(convertView, R.id.useTime);
			TextView hisResult = ViewHolder.get(convertView, R.id.hisResult);
			HisResult myData = list.get(position);
			name.setText(myData.getName());
			curTime.setText(myData.getCurTime());
			useTime.setText(myData.getUseTime());
			hisResult.setText(myData.getHisResult());
			return convertView;
		}

	}

	private class QueryTask extends AsyncTask<Void, Void, ArrayList<HisResult>> {

		@Override
		protected ArrayList<HisResult> doInBackground(Void... params) {
			list = DBManager.getInstance(HisResultActivity.this).queryHisResult(HistoryResultColumns.TABLE_NAME);
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<HisResult> list) {
			if (list.size() == 0) {
				return;
			}
			myAdapter.notifyDataSetChanged();
		}
	}

	private void resetTitlebar() {
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.base_comm_titlebar);
		final TextView title = (TextView) findViewById(R.id.titlebar_title);
		TextView right = (TextView) findViewById(R.id.titlebar_right_text);
		LinearLayout back = (LinearLayout) findViewById(R.id.titlebar_left_layout);
		title.setText("历史成绩");
		right.setText("清空");
		right.setOnClickListener(this);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebar_right_text:
			DBManager.getInstance(this).removeAll(HistoryResultColumns.TABLE_NAME);
			list.clear();
			myAdapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}

}
