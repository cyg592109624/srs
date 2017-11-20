package com.sunrise.srs.interfaces.home;

/**
 * Created by ChuHui on 2017/9/20.
 */

public interface OnModeSelectReturn {
    /**
     * 告诉activity 选择什么返回
     * @param result 返回内容
     */
    void onWorkOutSetting(int result);

    void onMediaStart(int mediaType);

}
