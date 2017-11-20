package com.sunrise.srs.utils;

import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by meimarco on 15-7-24.
 */
public class ActivityManageUtils {

    /**
     * 用于activity的管理和程序的退出
     */
    private Stack<Activity> activityStack;
    private static ActivityManageUtils instance;

    private ActivityManageUtils() {
    }

    /**
     * 单一实例
     */
    public static ActivityManageUtils getInstance() {
        if (instance == null) {
            instance = new ActivityManageUtils();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            if (!activity.isFinishing()){
                activity.finish();
            }
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity list
     */
    public void finishActivity(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            for (Activity activity : activityStack) {
                if (activity.getClass().getSimpleName().equals(list.get(i))) {
                    finishActivity(activity);
                    break;
                }
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
