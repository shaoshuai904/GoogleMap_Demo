package com.maple.studyaide.ui.dialog;

import junit.framework.Assert;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.maple.studyaide.R;

/**
 * 加载Dialog
 * 
 * @author shaoshuai
 * 
 */
public class LoadingDialog extends Dialog {

	private final TextView tvMsg;

	public LoadingDialog(Context context) {
		this(context, false, "loading...");
	}

	public LoadingDialog(Context context, boolean isShowAsFloatWindow) {
		this(context, isShowAsFloatWindow, "loading...");
	}

	public LoadingDialog(Context context, boolean isShowAsFloatWindow, String msg) {
		super(context, R.style.CustomProgressDialog);
		if (!isShowAsFloatWindow) {
			Assert.assertTrue("context must be Activity in Dialog.", context instanceof Activity);
		} else {
			getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		}
		this.getWindow().getAttributes().gravity = Gravity.CENTER;
		this.setCancelable(true);
		this.setContentView(R.layout.dialog_common_progress);
		tvMsg = (TextView) this.findViewById(R.id.tvMsg);
		tvMsg.setTextColor(Color.WHITE);
		tvMsg.setText(msg);

	}

	public void show(String msg) {
		show(msg, true);
	}

	public void show(String msg, boolean cancelable) {
		this.setCancelable(cancelable);// 可撤销
		tvMsg.setText(msg);
		this.show();
	}

}
