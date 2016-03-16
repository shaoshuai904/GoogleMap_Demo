package com.maple.studyaide.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.maple.studyaide.Const;
import com.maple.studyaide.FlyApp;
import com.maple.studyaide.R;
import com.maple.studyaide.ui.adapter.GuideViewPagerAdapter;
import com.maple.studyaide.utils.SPUtils;

/**
 * 启动界面
 * 
 * @author shaoshuai
 * 
 */
public class SplashActivity extends Activity {
	@ViewInject(R.id.rl_root)
	private RelativeLayout rl_root;// 根布局
	@ViewInject(R.id.guide_viewpager)
	private ViewPager guide_viewpager;// ViwePager
	@ViewInject(R.id.ll_point)
	private LinearLayout ll_point;// 相应的点区域

	protected static final int MSG_ENTER_HOME = 20;
	protected static final int MSG_IO_ERROR = 30;

	private ArrayList<View> guideViews;
	private ImageView[] guide_dot_iv;
	private GuideViewPagerAdapter guideViewPagerAdapter;
	private Context mContext;
	private LayoutInflater inflater;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_ENTER_HOME:// 进入下一个界面
				enterHome();
				break;
			case MSG_IO_ERROR:// io 异常
				Toast.makeText(mContext, "错误：" + MSG_IO_ERROR, 0).show();
				enterHome();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ViewUtils.inject(this);// 把所有带注解的控件全部初始化

		mContext = getApplicationContext();
		inflater = LayoutInflater.from(this);
		long startTime = System.currentTimeMillis();
		boolean isFirst = SPUtils.getBol(mContext, Const.First_ComeIn, true);
		initData();
		//
		// copyDb("address.db");
		// copyDb("antivirus.db");
		if (isFirst) {
			initViews();
		} else {
			ll_point.setVisibility(View.INVISIBLE);
			guide_viewpager.setVisibility(View.INVISIBLE);

			View guideView3 = inflater.inflate(R.layout.activity_splash_view3, null);
			rl_root.addView(guideView3, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			// rl_root.setBackgroundResource(R.drawable.guide_page2);

			Message msg = Message.obtain();
			msg.what = MSG_ENTER_HOME;
			long sleepTime = Const.SplashMinTime - (System.currentTimeMillis() - startTime);
			Log.e("", "失眠时间：" + sleepTime);
			handler.sendMessageDelayed(msg, sleepTime);
		}
	}

	private void initViews() {
		// 初始化点
		guide_dot_iv = new ImageView[3];
		guide_dot_iv[0] = (ImageView) findViewById(R.id.guide_dot1_iv);
		guide_dot_iv[1] = (ImageView) findViewById(R.id.guide_dot2_iv);
		guide_dot_iv[2] = (ImageView) findViewById(R.id.guide_dot3_iv);
		// 初始化页面
		View guideView1 = inflater.inflate(R.layout.activity_splash_view1, null);
		View guideView2 = inflater.inflate(R.layout.activity_splash_view2, null);
		View guideView3 = inflater.inflate(R.layout.activity_splash_view3, null);
		// 按钮点击
		((Button) guideView3.findViewById(R.id.guide_start_btn)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				enterHome();
			}
		});
		// 添加页面
		guideViews = new ArrayList<View>();
		guideViews.add(guideView1);
		guideViews.add(guideView2);
		guideViews.add(guideView3);

		guideViewPagerAdapter = new GuideViewPagerAdapter(guideViews);
		guide_viewpager.setAdapter(guideViewPagerAdapter);
		guide_viewpager.setOnPageChangeListener(pageChangeListener);
	}

	private void initData() {
		// http://192.168.8.101:8080/qfx/pages/index.json
		String url = FlyApp.HOME_CATEGORIES;
		Log.e("SplashActivity", "连接服务器：" + url);

		// new AppIndexServlet(mContext, new AndroidServelet.Callback<JsIndex>()
		// {
		// @Override
		// public void onLoaded(JsIndex jsBean) {
		// Log.e("APP", "当前版本： " + jsBean.version);
		// // 主界面URL
		// SPUtils.putStr(mContext, Const.HOME_PAGE_URL, jsBean.homeData.url);//
		// 主URL
		//
		// }
		//
		// @Override
		// public void onFail() {
		// }
		// }).get(new ServeletParams().put(Const.GET_URL, url));
	}

	/**
	 * 页面变化监听
	 */
	OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			for (int i = 0; i < guide_dot_iv.length; i++) {
				if (position == i) {
					guide_dot_iv[position].setImageResource(R.drawable.guide_dot_pressed);
				} else {
					guide_dot_iv[i].setImageResource(R.drawable.guide_dot_normal);
				}
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};

	/**
	 * 进入主界面
	 */
	protected void enterHome() {
		finish();
		SPUtils.putBol(mContext, Const.First_ComeIn, false);// 不再是第一次了
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}
