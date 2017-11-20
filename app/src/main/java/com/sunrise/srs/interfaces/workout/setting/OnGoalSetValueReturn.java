package com.sunrise.srs.interfaces.workout.setting;

/**
 * Created by ChuHui on 2017/9/20.
 */

public interface OnGoalSetValueReturn {
    /**
     * workout中的GOAL模式选择的目标
     * @param result 分为3种情况 TIME,DISTANCE,CALORIES
     */
    void onGoalSetValueResult(String result);
}
