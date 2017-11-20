package com.sunrise.srs.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ChuHui on 2017/9/15.
 */

public class ScreenUtils {

    public static final int MAX_BRIGHTNESS = 255;
    private static ContentResolver mContentResolver;
    private static Window mWindow;

    private ScreenUtils() {

    }

    public static void setContentResolver(@NonNull ContentResolver contentResolver) {
        mContentResolver = contentResolver;
    }

    public static void setContentResolver(@NonNull Context context) {
        setContentResolver(context.getContentResolver());
    }

    public static void setContentResolver(@NonNull Activity activity) {
        setContentResolver(activity.getApplicationContext());
    }

    public static void setWindow(@NonNull Window window) {
        mWindow = window;
    }

    public static void setWindow(@NonNull Activity activity) {
        setWindow(activity.getWindow());
    }

    /**
     * 判断是否开启了自动亮度调节
     *
     * @return
     */
    public static boolean isAutoBrightness() {
        boolean result = false;
        try {
            result = Settings.System.getInt(mContentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取屏幕的亮度
     *
     * @return
     */
    public static int getScreenBrightness() {
        int nowBrightnessValue = 0;
        try {
            nowBrightnessValue = android.provider.Settings.System.getInt(
                    mContentResolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    /**
     * 设置亮度
     *
     * @param brightness
     */
    public static void setBrightness(int brightness) {
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        mWindow.setAttributes(lp);
    }

    /**
     * 停止自动亮度调节
     */
    public static void stopAutoBrightness() {
        Settings.System.putInt(mContentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    /**
     * 开启亮度自动调节
     */
    public static void startAutoBrightness() {
        Settings.System.putInt(mContentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

}
