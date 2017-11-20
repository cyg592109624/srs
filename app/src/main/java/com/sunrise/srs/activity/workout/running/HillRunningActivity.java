package com.sunrise.srs.activity.workout.running;

import android.content.Context;
import android.content.Intent;

import com.sunrise.srs.Constant;
import com.sunrise.srs.services.workoutrunning.HillServer;
import com.sunrise.srs.utils.ThreadPoolUtils;

/**
 * Created by ChuHui on 2017/9/27.
 */

public class HillRunningActivity extends BaseRunningActivity {


    @Override
    protected void setUpInfo() {
        super.setUpInfo();

        runningDistanceTarget = Integer.valueOf(workOutInfo.getDistance());
        runningDistanceTotal = Integer.valueOf(workOutInfo.getRunningDistance());
        runningDistanceSurplus = runningDistanceTarget - runningDistanceTotal;
        headView.setDistanceValue(runningDistanceTotal + "");

        runningCaloriesTarget = Integer.valueOf(workOutInfo.getCalories());
        runningCaloriesTotal = Integer.valueOf(workOutInfo.getRunningCalories());
        runningCaloriesSurplus = runningCaloriesTarget - runningCaloriesTotal;
        headView.setCaloriesValue(runningCaloriesTotal + "");

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
                Intent intent = new Intent(activityContext, HillServer.class);
                intent.putExtra(Constant.WORK_OUT_INFO, workOutInfo);
                bindService(intent, floatWindowConnection, Context.BIND_AUTO_CREATE);
            }
        });
    }
}
