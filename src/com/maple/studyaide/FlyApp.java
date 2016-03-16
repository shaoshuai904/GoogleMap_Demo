package com.maple.studyaide;

import java.io.File;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Handler;
import android.util.Log;

/**
 * 去飞行APP
 * 
 * @author shaoshuai
 * 
 */
public class FlyApp extends Application {
	private static FlyApp app;
	private static Handler sHandler = new Handler();

	/** 服务器连接地址 */
	// public final static String BASE_URL = "http://192.168.1.102:8080/qfx/";
	public final static String BASE_URL = "http://120.27.36.87:8081/answer/json/";
	/** 服务器解析根地址 */
	public final static String HOME_CATEGORIES = BASE_URL + "answers.php";

	//
	@Override
	public void onCreate() {
		app = this;
		super.onCreate();
		Log.e("FlyApp", "app创建");

		// 初始化 存储路径
		initPath();
		// 设置异常的处理类
		// Thread.currentThread().setUncaughtExceptionHandler(
		// new MyUncaughtExceptionHandler());
	}

	/**
	 * 初始化存储路径
	 */
	private void initPath() {

	}

	@SuppressLint("SdCardPath")
	private class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {
		// 异常处理代码
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			// 死前一言
			System.out.println("发现一个异常，但是被哥捕获了");
			PrintStream err;
			try {
				err = new PrintStream(new File("/mnt/sdcard/err.txt"));
				ex.printStackTrace(err);
			} catch (Exception e) {
				e.printStackTrace();
			}
			android.os.Process.killProcess(android.os.Process.myPid()); // 杀死进程，自杀，闪退
			// 防崩设计
		}
	}

	/**
	 * 返回LeaderApp对象
	 */
	public static FlyApp app() {
		return app;
	}

	public static void postUi(Runnable run) {
		sHandler.post(run);
	}
}
