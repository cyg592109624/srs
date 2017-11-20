package com.sunrise.srs.services.workoutrunning;

import com.sunrise.srs.Constant;
import com.sunrise.srs.utils.DateUtil;
import com.sunrise.srs.views.LevelView;

/**
 * Created by ChuHui on 2017/11/10.
 */

public class GoalServer extends BaseFloatWindowService {

    @Override
    public void initBottomView() {

    }

    @Override
    protected void setUpInfo() {
        runningTimeTarget = Integer.valueOf(workOutInfo.getTime()) * 60;
        runningTimeTotal = Integer.valueOf(workOutInfo.getRunningTime());
        runningTimeSurplus = runningTimeTarget - runningTimeTotal;

        runningDistanceTarget = Integer.valueOf(workOutInfo.getDistance());
        runningDistanceTotal = Integer.valueOf(workOutInfo.getRunningDistance());
        runningDistanceSurplus = runningDistanceTarget - runningDistanceTotal;

        runningCaloriesTarget = Integer.valueOf(workOutInfo.getCalories());
        runningCaloriesTotal = Integer.valueOf(workOutInfo.getRunningCalories());
        runningCaloriesSurplus = runningCaloriesTarget - runningCaloriesTotal;

        switch (workOutInfo.getGoalType()) {
            default:
                break;
            case Constant.MODE_GOAL_TIME:
                isCountDownTime = true;
                avgLevelTime = runningTimeTarget / LevelView.columnCount;

                tgLevel = (int) runningTimeTotal / (int) avgLevelTime;

                floatWindowHead.setLevelValue(workOutInfo.getLevelList().get(tgLevel).getLevel());
                floatWindowHead.setTimeValue(DateUtil.getFormatMMSS(runningTimeSurplus));

                floatWindowHead.setDistanceValue(runningDistanceTotal + "");
                floatWindowHead.setCaloriesValue(runningCaloriesTotal + "");

                break;
            case Constant.MODE_GOAL_DISTANCE:
                isCountDownTime = false;
                avgLevelTime = 60;

                timerMissionTimes = (int) runningTimeTotal / (int) avgLevelTime;
                tgLevel = timerMissionTimes % LevelView.columnCount;

                floatWindowHead.setLevelValue(workOutInfo.getLevelList().get(timerMissionTimes).getLevel());
                floatWindowHead.setTimeValue(DateUtil.getFormatMMSS(runningTimeTotal));

                floatWindowHead.setDistanceValue(runningDistanceSurplus + "");

                floatWindowHead.setCaloriesValue(runningCaloriesTotal + "");

                break;
            case Constant.MODE_GOAL_CALORIES:
                isCountDownTime = false;
                avgLevelTime = 60;

                timerMissionTimes = (int) runningTimeTotal / (int) avgLevelTime;
                tgLevel = timerMissionTimes % LevelView.columnCount;

                floatWindowHead.setLevelValue(workOutInfo.getLevelList().get(timerMissionTimes).getLevel());
                floatWindowHead.setTimeValue(DateUtil.getFormatMMSS(runningTimeTotal));

                floatWindowHead.setDistanceValue(runningDistanceTotal + "");

                floatWindowHead.setCaloriesValue(runningCaloriesSurplus+ "");

                break;
        }

        floatWindowHead.setPulseValue(runningPulseNow + "");

        floatWindowHead.setWattValue(valueWatt + "");

        floatWindowHead.setSpeedValue(valueSpeed + "");
    }


}
