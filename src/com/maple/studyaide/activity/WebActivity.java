package com.maple.studyaide.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.maple.studyaide.R;

/**
 * 网页
 * 
 * @author shaoshuai
 * 
 */
public class WebActivity extends FragmentActivity implements OnClickListener {
	@ViewInject(R.id.tv_back)
	private TextView tv_back;// 返回
	@ViewInject(R.id.tv_title)
	private TextView tv_title;// 标题

	@ViewInject(R.id.progressbar)
	private ProgressBar progressBar;// 加载圈
	@ViewInject(R.id.cwv_content)
	private WebView webview;// 网页

	public static final String EXTRA_TITLE = "extra_title";
	public static final String EXTRA_URL = "extra_url";

	// private CommonProgressDialog mCommonProgressDialog;
	private Context mContext;
	private String title;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		ViewUtils.inject(this);
		mContext = WebActivity.this;

		Intent it = getIntent();
		title = (String) it.getSerializableExtra(EXTRA_TITLE);
		url = (String) it.getSerializableExtra(EXTRA_URL);

		initView();
		initListener();
		initDate();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {
		// mCommonProgressDialog = new CommonProgressDialog(this);
		// mCommonProgressDialog.show();

		if (TextUtils.isEmpty(title)) {
			tv_title.setText("新闻资讯");// 标题
		} else {
			tv_title.setText(title);// 标题
		}
		webview.getSettings().setJavaScriptEnabled(true);// 支持javascript
		webview.getSettings().setSupportZoom(true);// 设置可以支持缩放
		webview.getSettings().setBuiltInZoomControls(true);// 设置出现缩放工具
		webview.getSettings().setUseWideViewPort(true);// 扩大比例的缩放
		// 自适应屏幕
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.getSettings().setLoadWithOverviewMode(true);

	}

	private void initDate() {
		// 商品详情
		webview.loadUrl(url);// "http://m.zol.com/tuan/"
	}

	private void initListener() {
		tv_back.setOnClickListener(this);
		// 加载监听
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// Log.e("123", "开始加载");
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// Log.e("123", "加载完成");
				progressBar.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_back:// 返回
			finish();
			break;
		default:
			break;
		}
	}

}
