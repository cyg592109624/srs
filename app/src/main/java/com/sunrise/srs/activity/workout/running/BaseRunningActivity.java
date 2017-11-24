package com.sunrise.srs.activity.workout.running;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.activity.summary.SummaryActivity;
import com.sunrise.srs.base.BaseFragmentActivity;
import com.sunrise.srs.bean.Level;
import com.sunrise.srs.dialog.workoutrunning.CoolDownDialog;
import com.sunrise.srs.dialog.workoutrunning.CountDownDialog;
import com.sunrise.srs.dialog.workoutrunning.PauseDialog;
import com.sunrise.srs.interfaces.services.FloatServiceBinder;
import com.sunrise.srs.interfaces.services.FloatWindowBottomCallBack;
import com.sunrise.srs.interfaces.workout.running.DialogCoolDownClick;
import com.sunrise.srs.interfaces.workout.running.DialogPauseClick;
import com.sunrise.srs.services.workoutrunning.BaseFloatWindowService;
import com.sunrise.srs.utils.AnimationsContainer;
import com.sunrise.srs.utils.DateUtil;
import com.sunrise.srs.utils.ImageUtils;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.TextUtils;
import com.sunrise.srs.utils.ThreadPoolUtils;
import com.sunrise.srs.views.FloatWindowBottom;
import com.sunrise.srs.views.FloatWindowHead;
import com.sunrise.srs.views.LevelView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/30.
 */
public abstract class BaseRunningActivity extends BaseFragmentActivity implements FloatServiceBinder, FloatWindowBottomCallBack,
        AnimationsContainer.OnAnimationStoppedListener, DialogPauseClick, DialogCoolDownClick {

    private int parentWidth;
    private int parentHeight;


    /**
     * 是否正在展示左右2则的控件
     */
    private boolean isShowView = false;
    /**
     * 左边控件动画 收放判断
     */
    private boolean isAnimLeftView = false;
    /**
     * 右边控件动画 收放判断
     */
    private boolean isAnimRightView = false;

    @BindView(R.id.include2)
    ConstraintLayout leftView;

    @BindView(R.id.include3)
    ConstraintLayout rightLayout;

    @BindView(R.id.workout_running_media_bg)
    ImageView rightLayoutBg;

    @BindView(R.id.workout_running_media_ctr)
    ImageView mediaCtrlImage;

    @BindView(R.id.workout_running_level_view)
    LevelView levelView;

    @BindView(R.id.workout_running_head)
    FloatWindowHead headView;

    @BindView(R.id.workout_running_bottom)
    FloatWindowBottom bottomView;

    /**
     * 第一次进入该界面时 是否先进入倒数对话框 进入时倒数动作只进行一次
     */
    public int showCountDown = -1;

    public PackageManager packageManager;

    public BaseFloatWindowService floatWindowServer;

    public FloatServiceBinder serviceBinder;

    public ServiceConnection floatWindowConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            serviceBinder.onBindSucceed(componentName, iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceBinder.onServiceDisconnected(componentName);
        }
    };

    public CountDownTimer coolDownTimer;

    /**
     * 当前Level 同时也是浮标位置 应该由计时器进行更新
     */
    public int tgLevel = 0;

    /**
     * 累计时间进行运算时 定时任务运行时间
     */
    public final long timeCountDown = 365 * 24 * 60 * 60 * 1000;

    /**
     * 时间计算任务，用于计算时间和推移Level位置
     */
    public CountDownTimer runningTimer;

    /**
     * 计时器运行次数 当累加时间的时候可以得出 经历了多少次Level迭代(就是说可以突然30这个数值)
     * 更新workOutInfo时 应该使用这个值，也就是实际需要计算值
     */
    public int timerMissionTimes = 0;

    /**
     * true则为累减形式  false则为累加形式
     */
    public boolean isCountDownTime = false;

    /**
     * LevelView中每阶持续时间 根据目前情况决定 以秒为单位
     */
    public long avgLevelTime = 0L;

    /**
     * 目标运动时间 以秒为单位
     */
    public long runningTimeTarget = 0L;

    /**
     * 已经运行多长时间  以秒为单位
     */
    public long runningTimeTotal = 0L;

    /**
     * 剩余运动时间  以秒为单位
     */
    public long runningTimeSurplus = 0L;

    /**
     * 目标奔跑距离 单位根据设定进行改变 km或者mile
     */
    public int runningDistanceTarget = 0;

    /**
     * 已经奔跑距离
     */
    public int runningDistanceTotal = 0;

    /**
     * 剩余奔跑距离
     */
    public int runningDistanceSurplus = 0;

    /**
     * 目标calories
     */
    public int runningCaloriesTarget = 0;
    /**
     * 已经消耗calories
     */
    public int runningCaloriesTotal = 0;
    /**
     * 剩余calories
     */
    public int runningCaloriesSurplus = 0;

    /**
     * 目前脉搏速率
     */
    public int runningPulseTarget = 0;

    /**
     * 当前脉搏速率
     */
    public int runningPulseNow = 0;

    /**
     * 当前功率
     */
    public int valueWatt = 0;

    /**
     * 当前速度
     */
    public float valueSpeed = 0.0f;

    /**
     * 当前BMP值
     */
    public int valueBMP = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_workout_running;
    }

    @Override
    protected void setTextStyle() {
        List<TextView> txtList = new ArrayList<TextView>();
        txtList.add((TextView) findViewById(R.id.workout_running_level_view_hint));

        txtList.add((TextView) findViewById(R.id.workout_running_media_youtube_name));
        txtList.add((TextView) findViewById(R.id.workout_running_media_chrome_name));
        txtList.add((TextView) findViewById(R.id.workout_running_media_facebook_name));
        txtList.add((TextView) findViewById(R.id.workout_running_media_flikr_name));
        txtList.add((TextView) findViewById(R.id.workout_running_media_instagram_name));

        txtList.add((TextView) findViewById(R.id.workout_running_media_mp3_name));
        txtList.add((TextView) findViewById(R.id.workout_running_media_mp4_name));
        txtList.add((TextView) findViewById(R.id.workout_running_media_av_name));
        txtList.add((TextView) findViewById(R.id.workout_running_media_twitter_name));
        txtList.add((TextView) findViewById(R.id.workout_running_media_screen_mirroring_name));
        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.Microsoft());
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.Arial());
        }
    }

    @Override
    public void recycleObject() {
        leftView = null;
        levelView = null;

        rightLayout = null;
        rightLayoutBg = null;
        mediaCtrlImage = null;

        headView = null;
        bottomView = null;

        packageManager = null;

        if (floatWindowConnection != null) {
            unbindService(floatWindowConnection);
        }
        floatWindowConnection = null;
        serviceBinder = null;
        floatWindowServer = null;
    }

    @Override
    public void init() {
        serviceBinder = BaseRunningActivity.this;
        showCountDown = getIntent().getIntExtra(Constant.RUNNING_START_TYPE, Constant.RUNNING_START_TYPE_1);
        workOutInfo = getIntent().getParcelableExtra(Constant.WORK_OUT_INFO);

        packageManager = activityContext.getPackageManager();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        parentWidth = dm.widthPixels;
        parentHeight = dm.heightPixels;
        dm = null;

        bottomView.setWindowBottomCallBack(BaseRunningActivity.this);

        setUpInfo();
        createRunningTimer();
        setCoolDownTimer();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        switch (showCountDown) {
            default:
                break;
            case Constant.RUNNING_START_TYPE_1:
                showCountDown = Constant.RUNNING_START_TYPE_FINISH;
                onStartTypeA();
                break;
            case Constant.RUNNING_START_TYPE_2:
                showCountDown = Constant.RUNNING_START_TYPE_FINISH;
                onStartTypeB();
                break;
            case Constant.RUNNING_START_TYPE_3:
                showCountDown = Constant.RUNNING_START_TYPE_FINISH;
                onStartTypeC();
                break;
        }
    }


    @Override
    public void onBindSucceed(ComponentName componentName, IBinder iBinder) {
        if (iBinder != null) {
            floatWindowServer = ((BaseFloatWindowService.FloatBinder) iBinder).getService();
            floatWindowServer.setRunningActivityName(getPackageName() + "." + getLocalClassName());
            floatWindowServer.setActivity(BaseRunningActivity.this);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    @Override
    public void onLevelUp() {
        if (headView.getLevel() >= Constant.MAX_LEVEL) {
            return;
        }
        headView.levelChange(1);
        upDataLevelValue(1);
    }

    @Override
    public void onLevelDown() {
        if (headView.getLevel() <= Constant.MIN_LEVEL) {
            return;
        }
        headView.levelChange(-1);
        upDataLevelValue(-1);
    }

    @Override
    public void onWindyClick() {

    }

    @Override
    public void onStopClick() {
        if (runningTimer != null) {
            runningTimer.cancel();
        }
        showPauseDialog();
    }

    @Override
    public void onStartClick() {

    }

    @Override
    public void onHomeClick() {

    }

    @Override
    public void onBackClick() {

    }

    @Override
    public void animationStopped() {
        if (runningTimer != null) {
            runningTimer.start();
        }
    }

    @Override
    public void onPauseQuit() {
        if (coolDownTimer != null) {
            coolDownTimer.start();
        }
        showCoolDownDialog();
    }

    @Override
    public void onPauseContinue() {
        showCountDownDialog();
    }

    @Override
    public void onPauseTimeOut() {
        if (coolDownTimer != null) {
            coolDownTimer.cancel();
        }
        goToSummary();
    }

    @Override
    public void onCoolDownSkip() {
        if (runningTimer != null) {
            runningTimer.cancel();
            runningTimer = null;
        }

        if (coolDownTimer != null) {
            coolDownTimer.cancel();
            coolDownTimer = null;
        }
        goToSummary();
    }

    @OnClick(R.id.include2)
    public void showLeftView() {
        if (isShowView) {
            if (isAnimLeftView) {
                isShowView = false;
                isAnimLeftView = false;
                animLeftView(false);
            }
        } else {
            if (!isAnimLeftView) {
                isShowView = true;
                isAnimLeftView = true;
                animLeftView(true);
            }
        }
    }

    @OnClick(R.id.workout_running_media_ctr)
    public void mediaPop() {
        if (isShowView) {
            if (isAnimRightView) {
                isShowView = false;
                isAnimRightView = false;
                animRightView(false);
            }
        } else {
            if (!isAnimLeftView) {
                isShowView = true;
                isAnimRightView = true;
                animRightView(true);
            }
        }
    }


    @OnClick({R.id.workout_running_media_youtube, R.id.workout_running_media_chrome, R.id.workout_running_media_facebook,
            R.id.workout_running_media_flikr, R.id.workout_running_media_instagram, R.id.workout_running_media_mp3,
            R.id.workout_running_media_mp4, R.id.workout_running_media_av, R.id.workout_running_media_twitter,
            R.id.workout_running_media_screen_mirroring})
    public void mediaClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
        mediaPop();
    }

    private void animLeftView(boolean isScale) {
        PropertyValuesHolder scaleX = null;
        PropertyValuesHolder scaleY = null;
        PropertyValuesHolder translationX = null;
        PropertyValuesHolder translationY = null;
        if (isScale) {
            scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.7f);
            scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.7f);
            translationX = PropertyValuesHolder.ofFloat("translationX", 0, (parentWidth - leftView.getWidth()) / 2);
            translationY = PropertyValuesHolder.ofFloat("translationY", 0, -(parentHeight - leftView.getHeight()) / 5);
        } else {
            scaleX = PropertyValuesHolder.ofFloat("scaleX", 1.7f, 1f);
            scaleY = PropertyValuesHolder.ofFloat("scaleY", 1.7f, 1f);
            translationX = PropertyValuesHolder.ofFloat("translationX", (parentWidth - leftView.getWidth()) / 2, 0);
            translationY = PropertyValuesHolder.ofFloat("translationY", -(parentHeight - leftView.getHeight()) / 5, 0);
        }
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(leftView, scaleX, scaleY, translationX, translationY);
        animator.setDuration(500);
        animator.start();

    }

    private void animRightView(final boolean translation) {
        PropertyValuesHolder translationX = null;
        if (translation) {
            translationX = PropertyValuesHolder.ofFloat("translationX", rightLayout.getWidth() - mediaCtrlImage.getWidth(), 0);
        } else {
            translationX = PropertyValuesHolder.ofFloat("translationX", 0, rightLayout.getWidth() - mediaCtrlImage.getWidth());
        }
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(rightLayout, translationX);
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (translation) {
                    rightLayoutBg.setVisibility(View.VISIBLE);
                    ImageUtils.changeImageView(mediaCtrlImage, R.drawable.bg_dialog_3);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!translation) {
                    rightLayoutBg.setVisibility(View.INVISIBLE);
                    ImageUtils.changeImageView(mediaCtrlImage, R.mipmap.btn_sportmode_media_1);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    public abstract void onStartTypeA();

    public abstract void onStartTypeB();

    public abstract void onStartTypeC();


    /**
     * 将workout信息写入界面 并重调整各个部位的实例对象
     */
    protected void setUpInfo() {
        //这里获取到的是目标运行分钟数
        runningTimeTarget = Integer.valueOf(workOutInfo.getTime());
        //这里获取已经运行的时间 以秒为单位
        runningTimeTotal = Integer.valueOf(workOutInfo.getRunningTime());

        if (runningTimeTarget > Constant.COUNTDOWN_FLAG) {
            //累减形式 计算时间
            isCountDownTime = true;

            //在这里转换成秒数
            runningTimeTarget = runningTimeTarget * 60;

            runningTimeSurplus = runningTimeTarget - runningTimeTotal;
            headView.setTimeValue(DateUtil.getFormatMMSS(runningTimeSurplus));

            avgLevelTime = runningTimeTarget / Constant.LEVEL_COLUMN;
            tgLevel = workOutInfo.getRunningLevelCount();

            headView.setLevelValue(workOutInfo.getLevelList().get(tgLevel).getLevel());

        } else {
            //累加形式 计算时间
            isCountDownTime = false;
            headView.setTimeValue(DateUtil.getFormatMMSS(runningTimeTotal));
            avgLevelTime = 60;
            timerMissionTimes = workOutInfo.getRunningLevelCount();
            tgLevel = timerMissionTimes % Constant.LEVEL_COLUMN;
            headView.setLevelValue(workOutInfo.getLevelList().get(timerMissionTimes).getLevel());
        }
    }


    /**
     * 时间计算任务，用于计算时间和推移Level位置
     */
    public void createRunningTimer() {
        if (runningTimer != null) {
            runningTimer.cancel();
            runningTimer = null;
        }
        if (isCountDownTime) {
            runningTimer = new CountDownTimer(runningTimeSurplus * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    timerTick();
                }

                @Override
                public void onFinish() {
                    runningTimeTotal = runningTimeTarget;
                    //倒计时结束时的动作
                    headView.setTimeValue(DateUtil.getFormatMMSS(0));
                    showCoolDownDialog();
                }
            };
        } else {
            runningTimer = new CountDownTimer(timeCountDown, 1000) {
                @Override
                public void onTick(long l) {
                    timerTick();
                }

                @Override
                public void onFinish() {
                    //超出累加时间范围
                }
            };
        }
    }

    /**
     * 运动时间 计时器
     */
    public void timerTick() {
        runningTimeTotal++;
        if (isCountDownTime) {
            runningTimeSurplus = runningTimeTarget - runningTimeTotal;
            headView.setTimeValue(DateUtil.getFormatMMSS(runningTimeSurplus));
        } else {
            headView.setTimeValue(DateUtil.getFormatMMSS(runningTimeTotal));
        }
        //切换到下一阶段的Level
        if (runningTimeTotal % avgLevelTime == 0) {
            timerMissionTimes++;
            tgLevel++;
            if (!isCountDownTime) {
                //累加时间时才触发
                if (timerMissionTimes % Constant.LEVEL_COLUMN == 0) {
                    tgLevel = 0;
                    List<Level> arr = workOutInfo.getLevelList();
                    for (int i = 0; i < Constant.LEVEL_COLUMN; i++) {
                        Level level = new Level();
                        level.setLevel(headView.getLevel());
                        arr.add(level);
                    }
                    workOutInfo.setLevelList(arr);
                    levelView.setLevelList(workOutInfo.getLevelList());
                }
            }
            headView.setLevelValue(workOutInfo.getLevelList().get(timerMissionTimes).getLevel());
            levelView.reFlashView();
            moveBuoy();
        }
    }

    /**
     * 冷却时间降低Level
     */
    private void setCoolDownTimer() {
        coolDownTimer = new CountDownTimer(Constant.DIALOG_WAIT_TIME, 5 * 1000) {
            @Override
            public void onTick(long l) {
                if (headView != null) {
                    if (headView.getLevel() > 0) {
                        headView.setLevelValue(headView.getLevel() - 1);
                    } else {
                        coolDownTimer.onFinish();
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        };
    }


    public void bindServer() {

    }

    /**
     * 重新绘制LevelView
     */
    public void drawLevelView() {
        if (levelView != null) {
            levelView.calcLength();
            levelView.setLevelList(workOutInfo.getLevelList());
        }
        moveBuoy();
    }


    /**
     * 更新Level值以及将新的Level值写入workOutInfo
     */
    public void upDataLevelValue(int upOrDown) {
        if (levelView != null) {
            int level = 0;
            for (int i = timerMissionTimes; i < workOutInfo.getLevelList().size(); i++) {
                level = workOutInfo.getLevelList().get(i).getLevel();
                int newLevel = level + upOrDown;
                if (newLevel <= Constant.LEVEL_MIN) {
                    newLevel = Constant.LEVEL_MIN;
                }
                if (newLevel >= Constant.LEVEL_MAX) {
                    newLevel = Constant.LEVEL_MAX;
                }
                //这里保存到workout中
                workOutInfo.getLevelList().get(i).setLevel(newLevel);
                level = 0;
            }
            levelView.setLevelList(workOutInfo.getLevelList());
            //刷新(重新绘制onDraw())
            levelView.reFlashView();
        }
    }

    /**
     * 移动浮标位置
     */
    public void moveBuoy() {
        if (levelView != null) {
            levelView.setTgLevel(tgLevel);
            levelView.reFlashView();
        }
    }

    /**
     * 更新bmp显示内容
     */
    public void upDateBMP() {

    }

    /**
     * 倒计时对话框
     */
    public void showCountDownDialog() {
        ThreadPoolUtils.runTaskOnThread(new Runnable() {
            @Override
            public void run() {
                CountDownDialog dialog = new CountDownDialog();
                dialog.show(fragmentManager, Constant.TAG);
            }
        });
    }


    /**
     * 暂停对话框
     */
    public void showPauseDialog() {
        ThreadPoolUtils.runTaskOnThread(new Runnable() {
            @Override
            public void run() {
                PauseDialog pauseDialog = new PauseDialog();
                pauseDialog.show(fragmentManager, Constant.TAG);
            }
        });
    }

    /**
     * 冷却对话框
     */
    public void showCoolDownDialog() {
        ThreadPoolUtils.runTaskOnThread(new Runnable() {
            @Override
            public void run() {
                CoolDownDialog dialog = new CoolDownDialog();
                dialog.show(fragmentManager, Constant.TAG);
            }
        });
    }

    /**
     * 跳转到summary界面
     * 必然带数据跳转
     */
    public void goToSummary() {
        saveWorkOutInfo();
        ThreadPoolUtils.runTaskOnUIThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activityContext, SummaryActivity.class);
                intent.putExtra(Constant.WORK_OUT_INFO, workOutInfo);
                finishActivity();
                startActivity(intent);
            }
        });
    }

    /**
     * 记录当前运动信息
     */
    public void saveWorkOutInfo() {
        workOutInfo.setRunningTime(runningTimeTotal + "");
        workOutInfo.setDistance(runningDistanceTotal + "");
        workOutInfo.setCalories(runningCaloriesTotal + "");
        workOutInfo.setRunningLevelCount(timerMissionTimes);
    }

}
