package com.sunrise.srs.activity.workout.running;

import android.content.Context;
import android.content.Intent;

import com.sunrise.srs.Constant;
import com.sunrise.srs.services.workoutrunning.QuickStartServer;
import com.sunrise.srs.utils.DateUtil;
import com.sunrise.srs.utils.ThreadPoolUtils;
import com.sunrise.srs.views.LevelView;

/**
 * Created by ChuHui on 2017/9/27.
 */

public class QuickStartRunningActivity extends BaseRunningActivity {

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
            headView.setTimeValue(DateUtil.getFormatMMSS(runningTimeSurplus));

            avgLevelTime = runningTimeTarget / Constant.LEVEL_TIME_AVG;


            tgLevel = timerMissionTimes;

            headView.setLevelValue(workOutInfo.getLevelList().get(tgLevel).getLevel());

        } else {
            //累加形式 计算时间
            isCountDownTime = false;
            headView.setTimeValue(DateUtil.getFormatMMSS(runningTimeTotal));

            avgLevelTime = 60;

            tgLevel = timerMissionTimes % Constant.LEVEL_TIME_AVG;

            headView.setLevelValue(workOutInfo.getLevelList().get(timerMissionTimes).getLevel());
        }


        runningDistanceTarget = Integer.valueOf(workOutInfo.getDistance());
        runningDistanceTotal = Integer.valueOf(workOutInfo.getRunningDistance());
        runningDistanceSurplus = runningDistanceTarget - runningDistanceTotal;
        headView.setDistanceValue(runningDistanceTotal + "");

        runningCaloriesTarget = Integer.valueOf(workOutInfo.getCalories());
        runningCaloriesTotal = Integer.valueOf(workOutInfo.getRunningCalories());
        runningCaloriesSurplus = runningCaloriesTarget - runningCaloriesTotal;
        headView.setCaloriesValue(runningCaloriesTotal + "");

        //当前脉搏速率
        headView.setPulseValue(runningPulseTarget + "");

        headView.setWattValue(valueWatt + "");

        headView.setSpeedValue(valueSpeed + "");
    }

    @Override
    public void onStartTypeA() {
        drawLevelView();
        bindServer();
        showCountDownDialog();
    }

    @Override
    public void onStartTypeB() {
        drawLevelView();
        bindServer();
        runningTimer.start();
    }

    @Override
    public void onStartTypeC() {

    }

    @Override
    public void bindServer() {
        ThreadPoolUtils.runTaskOnThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activityContext, QuickStartServer.class);
                intent.putExtra(Constant.WORK_OUT_INFO, workOutInfo);
                bindService(intent, floatWindowConnection, Context.BIND_AUTO_CREATE);
            }
        });
    }
}
