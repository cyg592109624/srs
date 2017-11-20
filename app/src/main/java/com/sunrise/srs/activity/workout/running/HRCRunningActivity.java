package com.sunrise.srs.activity.workout.running;

import android.widget.ImageView;

import com.sunrise.srs.R;
import com.sunrise.srs.utils.ImageUtils;

/**
 * Created by ChuHui on 2017/9/27.
 */

public class HRCRunningActivity extends BaseRunningActivity {

    @Override
    public void init() {
        super.init();
        ImageUtils.changeImageView((ImageView) bottomView.findViewById(R.id.workout_running_level_up), R.drawable.btn_sportmode_up_3);
        ImageUtils.changeImageView((ImageView) bottomView.findViewById(R.id.workout_running_level_down), R.drawable.btn_sportmode_down_3);
    }

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
    public void onLevelUp() {

    }

    @Override
    public void onLevelDown() {

    }
}
