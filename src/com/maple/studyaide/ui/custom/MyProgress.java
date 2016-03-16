package com.maple.studyaide.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.ProgressBar;

/**
 * 进度条
 * 
 * @author shaoshuai
 * 
 */
public class MyProgress extends ProgressBar {

	private Paint pt;

	private int maxWidth = 1;

	private int displayWidth;

	private static final int PROGRESS_COLOR = Color.parseColor("#66ccff");

	public MyProgress(Context context) {
		super(context, null, android.R.attr.progressBarStyleHorizontal);
		init();

	}

	public MyProgress(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, android.R.attr.progressBarStyleHorizontal);
		init();
	}

	public MyProgress(Context context, AttributeSet attrs) {
		super(context, attrs, android.R.attr.progressBarStyleHorizontal);
		init();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		ViewGroup parent = (ViewGroup) this.getParent();
		if (parent != null)
			maxWidth = parent.getWidth();
		if (maxWidth <= 1)
			maxWidth = displayWidth;

		super.onLayout(changed, left, top, right, bottom);
	}

	public void init() {
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 5, 0, 0));
		setBackgroundDrawable(null);
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		displayWidth = wm.getDefaultDisplay().getWidth();
		//
		pt = new Paint();
		pt.setColor(PROGRESS_COLOR);

		pt.setStyle(Style.FILL_AND_STROKE);
		pt.setDither(true);
		pt.setStrokeWidth(5f);
	}

	private int currentProgressX = 0;

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		canvas.drawLine(0, 0, currentProgressX, 0, pt);
	}

	@Override
	public synchronized void setProgress(int progress) {
		currentProgressX = (int) mapProgressToX(progress);
		invalidate();
	}

	private float mapProgressToX(float progress) {
		return progress / 100 * maxWidth;
	}
}
