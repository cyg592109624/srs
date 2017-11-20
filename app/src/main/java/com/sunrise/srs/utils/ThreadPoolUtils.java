package com.sunrise.srs.utils;

import android.os.Handler;

/**
 * Created by Administrator on 2015/11/26.
 */
public class ThreadPoolUtils {

	public static void runTaskOnThread(Runnable runnable) {
		ThreadPoolFactory.getCommonThreadPool().execute(runnable);
	}

	// UI的handler
	private static Handler handler = new Handler();

	public static void runTaskOnUIThread(Runnable runnable) {
		handler.post(runnable);
	}

}
