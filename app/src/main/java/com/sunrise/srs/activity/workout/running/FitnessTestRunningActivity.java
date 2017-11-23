package com.sunrise.srs.activity.workout.running;

import android.widget.ImageView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.R;
import com.sunrise.srs.bean.Level;
import com.sunrise.srs.dialog.workoutrunning.WarmUpDialog;
import com.sunrise.srs.interfaces.workout.running.DialogWarmUpClick;
import com.sunrise.srs.utils.DateUtil;
import com.sunrise.srs.utils.ImageUtils;
import com.sunrise.srs.utils.ThreadPoolUtils;
import com.sunrise.srs.views.LevelView;

import java.util.List;

/**
 * Created by ChuHui on 2017/9/27.
 */
public class FitnessTestRunningActivity extends BaseRunningActivity implements DialogWarmUpClick {
    //残留问题:
    //如何界定这个模式的自动跳出;
    //阶段跃迁 受当前心率影响 心率满足要求就会跃迁到下一个阶段不受阶段时间影响
    //阶段跃迁需要同时更新tglevel跟timerMissionTimes,就是说这个2个参数在这个模式当中的值必须保持同步
    //否者会出错




    private int showWarmTimes = -1;

    private int tgHRC = 0;

    @Override
    public void init() {
        super.init();
        ImageUtils.changeImageView((ImageView) bottomView.findViewById(R.id.workout_running_level_up), R.drawable.btn_sportmode_up_3);
        ImageUtils.changeImageView((ImageView) bottomView.findViewById(R.id.workout_running_level_down), R.drawable.btn_sportmode_down_3);
    }

    @Override
    protected void setUpInfo() {
        //特殊 这里只运行495秒
        runningTimeTarget = 60 + 15 * 29;
        //这里获取已经运行的时间 以秒为单位
        runningTimeTotal = Integer.valueOf(workOutInfo.getRunningTime());

        runningTimeSurplus = runningTimeTarget - runningTimeTotal;

        //累减 计算时间
        isCountDownTime = true;

        headView.setTimeValue(DateUtil.getFormatMMSS(runningTimeSurplus));

        avgLevelTime = 60;
        timerMissionTimes = workOutInfo.getRunningLevelCount();

        tgLevel = timerMissionTimes %Constant.LEVEL_TIME_AVG;

        headView.setLevelValue(workOutInfo.getLevelList().get(timerMissionTimes).getLevel());

        runningDistanceTarget = Integer.valueOf(workOutInfo.getDistance());
        runningDistanceTotal = Integer.valueOf(workOutInfo.getRunningDistance());
        runningDistanceSurplus = runningDistanceTarget - runningDistanceTotal;
        headView.setDistanceValue(runningDistanceTotal + "");

        runningCaloriesTarget = Integer.valueOf(workOutInfo.getCalories());
        runningCaloriesTotal = Integer.valueOf(workOutInfo.getRunningCalories());
        runningCaloriesSurplus = runningCaloriesTarget - runningCaloriesTotal;
        headView.setCaloriesValue(runningCaloriesTotal + "");

        tgHRC = Integer.valueOf(workOutInfo.getHrc());

        headView.setPulseValue(runningPulseNow + "");

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

    @Override
    public void animationStopped() {
        if (showWarmTimes == -1) {
            showWarmUpDialog();
            showWarmTimes = 1;
            return;
        }
        if (runningTimer != null) {
            runningTimer.start();
        }
    }

    @Override
    public void onWarmUpSkip() {
        if (runningTimer != null) {
            runningTimer.start();
        }
    }

    @Override
    public void timerTick() {
        runningTimeTotal++;
        runningTimeSurplus = runningTimeTarget - runningTimeTotal;
        headView.setTimeValue(DateUtil.getFormatMMSS(runningTimeSurplus));
        //切换到下一阶段的Level
        if (runningTimeTotal % avgLevelTime == 0) {
            //特殊地方将阶段时间间隔由60秒缩减到15秒
            avgLevelTime = 15;
            timerMissionTimes++;
            tgLevel++;
            if (!isCountDownTime) {
                //累加时间时才触发
                if (timerMissionTimes % Constant.LEVEL_TIME_AVG == 0) {
                    tgLevel = 0;
                    List<Level> arr = workOutInfo.getLevelList();
                    for (int i = 0; i < Constant.LEVEL_TIME_AVG; i++) {
                        Level level = new Level();
                        level.setLevel(1);
                        arr.add(level);
                    }
                    workOutInfo.setLevelList(arr);
                    levelView.setLevelList(workOutInfo.getLevelList());
                }
            }
            moveBuoy();
        }
    }

    /**
     * 热身运动
     */
    private void showWarmUpDialog() {
        ThreadPoolUtils.runTaskOnThread(new Runnable() {
            @Override
            public void run() {
                WarmUpDialog dialog = new WarmUpDialog();
                dialog.show(fragmentManager, Constant.TAG);
            }
        });
    }

}
