package com.maple.studyaide.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 线程池管理
 * @author WuYiMing
 */
public class ThreadUtil {
	/**
	 * 固定数量线程池
	 */
	private static ExecutorService executorService = Executors.newFixedThreadPool(1);
	/**
	 * 非固定数量线程池
	 */
	private static ExecutorService moreExecutorService = Executors.newCachedThreadPool();
	
	/**
	 * 该方法为单线程执行
	 * @author WuYiMing
	 */
	public static void execute(Runnable runnable) {
		executorService.execute(runnable);
	}

	/**
	 * 非固定数量线程池
	 * @author WuYiMing
	 */
	public static void executeMore(Runnable runnable) {
		moreExecutorService.execute(runnable);
	}
	
}
