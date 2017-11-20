package com.sunrise.srs.interfaces.workout.setting;

/**
 * Created by ChuHui on 2017/9/20.
 */

public interface OnKeyBoardReturn {
    /**
     *  输入结果
     * @param result 返回输入结果
     */
    void onKeyBoardEnter(String result);

    /**
     * 关闭动作触发
     */
    void onKeyBoardClose();
}
