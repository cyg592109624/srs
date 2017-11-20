package com.sunrise.srs.activity.workout.running;

import android.content.Context;
import android.content.Intent;

import com.sunrise.srs.Constant;
import com.sunrise.srs.services.workoutrunning.GoalServer;
import com.sunrise.srs.utils.DateUtil;
import com.sunrise.srs.utils.ThreadPoolUtils;
import com.sunrise.srs.views.LevelView;

/**
 * Created by ChuHui on 2017/9/27.
 */

public class GoalRunningActivity extends BaseRunningActivity {
    //残留问题
    //数据返回间隔 如何计算

    @Override
    protected void setUpInfo() {
        //获取分钟数 转为秒数
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

                tgLevel = workOutInfo.getRunningLevelCount();

                headView.setLevelValue(workOutInfo.getLevelList().get(tgLevel).getLevel());
                headView.setTimeValue(DateUtil.getFormatMMSS(runningTimeSurplus));

                headView.setDistanceValue(runningDistanceTotal + "");
                headView.setCaloriesValue(runningCaloriesTotal + "");

                break;
            case Constant.MODE_GOAL_DISTANCE:
                isCountDownTime = false;
                avgLevelTime = 60;

                timerMissionTimes = workOutInfo.getRunningLevelCount();
                tgLevel = timerMissionTimes % LevelView.columnCount;

                headView.setLevelValue(workOutInfo.getLevelList().get(timerMissionTimes).getLevel());
                headView.setTimeValue(DateUtil.getFormatMMSS(runningTimeTotal));

                headView.setDistanceValue(runningDistanceSurplus + "");

                headView.setCaloriesValue(runningCaloriesTotal + "");

                break;
            case Constant.MODE_GOAL_CALORIES:
                isCountDownTime = false;
                avgLevelTime = 60;

                timerMissionTimes = workOutInfo.getRunningLevelCount();
                tgLevel = timerMissionTimes % LevelView.columnCount;

                headView.setLevelValue(workOutInfo.getLevelList().get(timerMissionTimes).getLevel());
                headView.setTimeValue(DateUtil.getFormatMMSS(runningTimeTotal));

                headView.setDistanceValue(runningDistanceTotal + "");

                headView.setCaloriesValue(runningCaloriesSurplus+ "");

                break;
        }

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
                Intent intent = new Intent(activityContext, GoalServer.class);
                intent.putExtra(Constant.WORK_OUT_INFO, workOutInfo);
                bindService(intent, floatWindowConnection, Context.BIND_AUTO_CREATE);
            }
        });
    }
}
