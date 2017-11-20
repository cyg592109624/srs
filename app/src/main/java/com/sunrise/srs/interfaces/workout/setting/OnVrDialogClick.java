package com.sunrise.srs.interfaces.workout.setting;

/**
 * Created by ChuHui on 2017/9/20.
 */

public interface OnVrDialogClick {
    /**
     * 返回值
     * @param vrNum VR编号
     * @param time 时间
     */
    void onStartClick(int vrNum,String time);
    /**
     * 返回
     */
    void onBackClick();
}
