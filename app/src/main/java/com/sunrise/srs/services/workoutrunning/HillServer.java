package com.sunrise.srs.services.workoutrunning;

/**
 * Created by ChuHui on 2017/11/10.
 */

public class HillServer extends BaseFloatWindowService {

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

        floatWindowHead.setPulseValue(runningPulseTarget + "");

        floatWindowHead.setWattValue(valueWatt + "");

        floatWindowHead.setSpeedValue(valueSpeed + "");
    }
}
