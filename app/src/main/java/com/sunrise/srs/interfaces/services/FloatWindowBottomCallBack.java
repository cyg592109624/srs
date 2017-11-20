package com.sunrise.srs.interfaces.services;

/**
 * Created by ChuHui on 2017/10/11.
 */

public interface FloatWindowBottomCallBack {
    /**
     * 段数提高
     */
    void onLevelUp();

    /**
     * 段数降低
     */
    void onLevelDown();

    /**
     * 改变风量
     */
    void onWindyClick();

    /**
     * 按下停止按钮
     */
    void onStopClick();
    /**
     * 按下开始按钮
     */
    void onStartClick();
    /**
     * 按下开始按钮
     */
    void onHomeClick();
    /**
     * 按下开始按钮
     */
    void onBackClick();
}
