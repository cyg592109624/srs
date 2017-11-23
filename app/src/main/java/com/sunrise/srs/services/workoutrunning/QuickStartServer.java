package com.sunrise.srs.services.workoutrunning;

import com.sunrise.srs.Constant;
import com.sunrise.srs.utils.DateUtil;
import com.sunrise.srs.views.LevelView;

/**
 * Created by ChuHui on 2017/11/10.
 */

public class QuickStartServer extends BaseFloatWindowService {

    @Override
    public void initBottomView() {

    }

    @Override
    protected void setUpInfo() {
        super.setUpInfo();

        runningDistanceTarget = Integer.valueOf(workOutInfo.getDistance());
        runningDistanceTotal = Integer.valueOf(workOutInfo.getRunningDistance());
        runningDistanceSurplus = runningDistanceTarget - runningDistanceTotal;

        floatWindowHead.setDistanceValue(runningDistanceTotal + "");

        runningCaloriesTarget = Integer.valueOf(workOutInfo.getCalories());
        runningCaloriesTotal = Integer.valueOf(workOutInfo.getRunningCalories());
        runningCaloriesSurplus = runningCaloriesTarget - runningCaloriesTotal;

        floatWindowHead.setCaloriesValue(runningCaloriesTotal + "");

        //当前心跳
        floatWindowHead.setPulseValue(runningPulseTarget + "");

        floatWindowHead.setWattValue(valueWatt + "");

        floatWindowHead.setSpeedValue(valueSpeed + "");
    }
}
