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
//        super.setUpInfo();
        //这里获取到的是目标运行分钟数
        runningTimeTarget = Integer.valueOf(workOutInfo.getTime());
        //这里获取已经运行的时间 以秒为单位
        runningTimeTotal = Integer.valueOf(workOutInfo.getRunningTime());

        timerMissionTimes = workOutInfo.getRunningLevelCount();

        if (runningTimeTarget > Constant.COUNTDOWN_FLAG) {
            //累减形式 计算时间
            isCountDownTime = true;

            //在这里转换成秒数
            runningTimeTarget = runningTimeTarget * 60;

            runningTimeSurplus = runningTimeTarget - runningTimeTotal;
            floatWindowHead.setTimeValue(DateUtil.getFormatMMSS(runningTimeSurplus));

            avgLevelTime = runningTimeTarget / LevelView.columnCount;

            tgLevel = timerMissionTimes;

            floatWindowHead.setLevelValue(workOutInfo.getLevelList().get(tgLevel).getLevel());

        } else {
            //累加形式 计算时间
            isCountDownTime = false;

            floatWindowHead.setTimeValue(DateUtil.getFormatMMSS(runningTimeTotal));
            avgLevelTime = 60;

            tgLevel = timerMissionTimes % LevelView.columnCount;

            floatWindowHead.setLevelValue(workOutInfo.getLevelList().get(timerMissionTimes).getLevel());
        }


        runningDistanceTarget = Integer.valueOf(workOutInfo.getDistance());
        runningDistanceTotal = Integer.valueOf(workOutInfo.getRunningDistance());
        runningDistanceSurplus = runningDistanceTarget - runningDistanceTotal;
        floatWindowHead.setDistanceValue(runningDistanceTotal + "");

        runningCaloriesTarget = Integer.valueOf(workOutInfo.getCalories());
        runningCaloriesTotal = Integer.valueOf(workOutInfo.getRunningCalories());
        runningCaloriesSurplus = runningCaloriesTarget - runningCaloriesTotal;
        floatWindowHead.setCaloriesValue(runningCaloriesTotal + "");

        //当前脉搏速率
        floatWindowHead.setPulseValue(runningPulseTarget + "");

        floatWindowHead.setWattValue(valueWatt + "");

        floatWindowHead.setSpeedValue(valueSpeed + "");
    }
}
