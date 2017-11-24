package com.sunrise.srs.services.workoutrunning;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.R;
import com.sunrise.srs.activity.home.HomeActivity;
import com.sunrise.srs.activity.summary.SummaryActivity;
import com.sunrise.srs.activity.workout.running.BaseRunningActivity;
import com.sunrise.srs.activity.workout.running.QuickStartRunningActivity;
import com.sunrise.srs.bean.Level;
import com.sunrise.srs.bean.WorkOut;
import com.sunrise.srs.interfaces.services.FloatWindowBottomCallBack;
import com.sunrise.srs.utils.AnimationsContainer;
import com.sunrise.srs.utils.DateUtil;
import com.sunrise.srs.utils.ImageUtils;
import com.sunrise.srs.views.FloatWindowBottom;
import com.sunrise.srs.views.FloatWindowHead;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by ChuHui on 2017/10/14.
 */

public abstract class BaseFloatWindowService extends Service implements FloatWindowBottomCallBack, AnimationsContainer.OnAnimationStoppedListener {
    public String runningActivityName = "";

    public final int FloatWindowNotification = 62111;

    public static final int LEVEL_UP = 1;

    public static final int LEVEL_DOWN = -1;

    public BaseRunningActivity activity;

    public WindowManager mWindowManager;

    public WindowManager.LayoutParams paramsHead;
    public WindowManager.LayoutParams paramsBottom;

    public WindowManager.LayoutParams dialogParams;
    public WindowManager.LayoutParams toggleParams;

    public FloatWindowHead floatWindowHead;
    public FloatWindowBottom floatWindowBottom;

    public ConstraintLayout dialogCountDown;
    public ConstraintLayout dialogPause;
    public ConstraintLayout dialogCoolDown;


    public ImageView toggleImage;

    /**
     * 运行时间为一年
     */
    public final long timeCountDown = 365 * 24 * 60 * 60 * 1000;


    /**
     * 当前Level 同时也是浮标位置 应该由计时器进行更新
     */
    public int tgLevel = 0;

    /**
     * 计时器运行次数 当累加时间的时候可以得出 经历了多少次Level迭代(就是说可以突然30这个数值)
     */
    public int timerMissionTimes = 0;

    public final long timeSpace = 1000;

    public WorkOut workOutInfo;

    /**
     * 是否以倒计时形式显示时间
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
     * 一共运行多长时间  以秒为单位
     */
    public long runningTimeTotal = 0L;

    /**
     * 剩余运动时间  以秒为单位
     */
    public long runningTimeSurplus = 0L;

    /**
     * 运动时间 计时器
     */
    public CountDownTimer runningTimer;

    /**
     * 暂停,冷却等待计时器
     */
    public ScheduledExecutorService waitTask;

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

    public int screenWidth = 0;
    public int screenHeight = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mWindowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        dm = null;
        stageService();
        initWindowParams();
        initFloatWindow();
        initDialog();
        initToggleBtn();
        initBottomView();
    }

    private final IBinder floatBinder = new FloatBinder();

    public class FloatBinder extends Binder {
        /**
         * @return 主要为了获取该服务的实例对象
         */
        public BaseFloatWindowService getService() {
            return BaseFloatWindowService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //可以通过这里获取bind方法中传递数据
        workOutInfo = intent.getParcelableExtra(Constant.WORK_OUT_INFO);
        setUpInfo();
        initCountDownTimer();
        return floatBinder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        workOutInfo = intent.getParcelableExtra(Constant.WORK_OUT_INFO);
        if (workOutInfo.getWorkOutModeName().equals(Constant.WORK_OUT_MODE_MEDIA)) {
            floatWindowBottom.homeBtnVisibility(View.VISIBLE);
            floatWindowBottom.startBtnVisibility(View.VISIBLE);

            floatWindowBottom.backBtnVisibility(View.INVISIBLE);
            floatWindowBottom.stopBtnVisibility(View.INVISIBLE);

            toggleFloatWindow();
            //专门针对FloatWindow的显示和隐藏
            mWindowManager.addView(toggleImage, toggleParams);
        }
        setUpInfo();
        initCountDownTimer();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        activity = null;
        View[] views = {floatWindowHead, floatWindowBottom, dialogPause, dialogCoolDown,toggleImage};
        for (View v : views) {
            reMoveView(v);
        }
        views = null;
        if (runningTimer != null) {
            runningTimer.cancel();
            runningTimer = null;
        }
        animation = null;

        workOutInfo = null;

        mWindowManager = null;

        paramsHead = null;
        paramsBottom = null;

        dialogParams = null;

        floatWindowHead.recycle();
        floatWindowHead.removeAllViews();
        floatWindowHead = null;

        floatWindowBottom.recycle();
        floatWindowBottom.removeAllViews();
        floatWindowBottom = null;

        dialogPause.removeAllViews();
        dialogPause = null;

        dialogCoolDown.removeAllViews();
        dialogCoolDown = null;
    }

    @Override
    public void animationStopped() {
        mWindowManager.removeView(dialogCountDown);
        runningTimer.start();
    }

    @Override
    public void onLevelUp() {
        floatWindowHead.levelChange(LEVEL_UP);
    }

    @Override
    public void onLevelDown() {
        floatWindowHead.levelChange(LEVEL_DOWN);
    }

    @Override
    public void onWindyClick() {

    }

    @Override
    public void onStartClick() {
        floatWindowBottom.homeBtnVisibility(View.INVISIBLE);
        floatWindowBottom.backBtnVisibility(View.VISIBLE);

        floatWindowBottom.startBtnVisibility(View.INVISIBLE);
        floatWindowBottom.stopBtnVisibility(View.VISIBLE);
        showCountDown();
    }

    @Override
    public void onStopClick() {
        runningTimer.cancel();
        startWaiteTimerTask();
        saveWorkOutInfo();
        mWindowManager.addView(dialogPause, paramsBottom);
    }


    @Override
    public void onHomeClick() {
        stopForeground(true);
        runningTimer.cancel();
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        Intent serverIntent = new Intent(getApplicationContext(), getClass());
        toggleFloatWindow();

        stopService(serverIntent);
    }

    @Override
    public void onBackClick() {
        stopForeground(true);
        runningTimer.cancel();
        saveWorkOutInfo();
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), QuickStartRunningActivity.class);
        intent.putExtra(Constant.RUNNING_START_TYPE, Constant.RUNNING_START_TYPE_2);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.WORK_OUT_INFO, workOutInfo);

        startActivity(intent);

        Intent serverIntent = new Intent(getApplicationContext(), getClass());
        toggleFloatWindow();

        stopService(serverIntent);
    }

    /**
     * 将workout信息写入界面 并重调整各个部位的实例对象
     */
    protected void setUpInfo() {
        if (workOutInfo == null) {
            return;
        }
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

            floatWindowHead.setTimeValue(DateUtil.getFormatMMSS(runningTimeSurplus));

            avgLevelTime = runningTimeTarget / Constant.LEVEL_COLUMN;

            timerMissionTimes = workOutInfo.getRunningLevelCount();

            tgLevel = workOutInfo.getRunningLevelCount();
            floatWindowHead.setLevelValue(workOutInfo.getLevelList().get(tgLevel).getLevel());

        } else {
            //累加形式 计算时间
            isCountDownTime = false;

            floatWindowHead.setTimeValue(DateUtil.getFormatMMSS(runningTimeTotal));

            avgLevelTime = 60;

            timerMissionTimes = workOutInfo.getRunningLevelCount();

            tgLevel = timerMissionTimes % Constant.LEVEL_COLUMN;
            floatWindowHead.setLevelValue(workOutInfo.getLevelList().get(timerMissionTimes).getLevel());
        }
    }

    /**
     * 切为前台服务
     */
    private void stageService() {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setContentText("前台服务");
        builder.setContentTitle("悬浮窗口服务");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(false);
        Notification notification = builder.build();
        startForeground(FloatWindowNotification, notification);
    }

    /**
     * 设置LayoutParams
     */
    private void initWindowParams() {
        paramsHead = setUpParams(0, -800);
        paramsBottom = setUpParams(0, 800);
        dialogParams = setUpParams(0, 0);

        toggleParams  = new WindowManager.LayoutParams();
        // 设置window type
        toggleParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // 设置图片格式，效果为背景透明
        toggleParams.format = PixelFormat.RGBA_8888;

        // 设置Window flag
        toggleParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        toggleParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        toggleParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        toggleParams.horizontalMargin = 0;
        toggleParams.verticalMargin = 0;
        toggleParams.x = screenWidth / 2;
        toggleParams.y = 0;
    }

    private WindowManager.LayoutParams setUpParams(int x, int y) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 设置window type
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // 设置图片格式，效果为背景透明
        params.format = PixelFormat.RGBA_8888;

        // 设置Window flag
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.horizontalMargin = 0;
        params.verticalMargin = 0;
        params.x = x;
        params.y = y;
        return params;
    }


    /**
     * 创建悬浮窗口
     */
    private void initFloatWindow() {
        floatWindowHead = new FloatWindowHead(getApplicationContext(), null);
        floatWindowBottom = new FloatWindowBottom(getApplicationContext(), null);

        floatWindowHead.setLayoutParams(paramsHead);

        floatWindowBottom.setLayoutParams(paramsBottom);
        floatWindowBottom.setWindowBottomCallBack(BaseFloatWindowService.this);
    }

    private void initToggleBtn() {
        toggleImage = new ImageView(getApplicationContext());
        ImageUtils.changeImageView(toggleImage, R.drawable.btn_back_2);
        toggleImage.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;
            int paramX, paramY;
            int dx, dy;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    default:
                        break;
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        paramX = toggleParams.x;
                        paramY = toggleParams.y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        dx = (int) event.getRawX() - lastX;
                        dy = (int) event.getRawY() - lastY;
                        toggleParams.x = paramX + dx;
                        toggleParams.y = paramY + dy;
                        // 更新悬浮窗位置
                        mWindowManager.updateViewLayout(toggleImage, toggleParams);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(dx) < 10 && Math.abs(dy) < 10) {
                            toggleFloatWindow();
                        }
                        break;
                }
                return true;
            }
        });
        toggleImage.setLayoutParams(toggleParams);
    }

    /**
     * 创建弹窗
     */
    private void initDialog() {
        dialogPause = (ConstraintLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_workout_running_pause, null);

        dialogCoolDown = (ConstraintLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_workout_running_cool_down, null);

        dialogCountDown = (ConstraintLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_workout_running_count_down, null);

        dialogPause.setLayoutParams(dialogParams);
        dialogPause.setMinWidth(screenWidth);
        dialogPause.setMinHeight(screenHeight);

        dialogCoolDown.setLayoutParams(dialogParams);
        dialogCoolDown.setMinWidth(screenWidth);
        dialogCoolDown.setMinHeight(screenHeight);

        dialogCountDown.setLayoutParams(dialogParams);
        dialogCountDown.setMinWidth(screenWidth);
        dialogCountDown.setMinHeight(screenHeight);

        dialogClick();
        ImageView countDownImage = dialogCountDown.findViewById(R.id.workout_running_dialog_count_down_img);
        animation = countDownAnimation(countDownImage);
        animation.setOnAnimStopListener(BaseFloatWindowService.this);
    }

    /**
     * 弹窗点击事件
     */
    private void dialogClick() {
        View.OnClickListener pauseClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    default:
                        break;
                    case R.id.workout_running_pause_quit:
                        runningTimer.cancel();
                        stopTimerTask();
                        mWindowManager.removeView(dialogPause);
                        mWindowManager.addView(dialogCoolDown, dialogParams);
                        startWaiteTimerTask();
                        break;
                    case R.id.workout_running_pause_continue:
                        stopTimerTask();
                        mWindowManager.removeView(dialogPause);
                        showCountDown();
                        break;
                    case R.id.workout_running_cool_down_skip:
                        //进入summary界面 同时关闭服务，关闭窗口
                        stopTimerTask();
                        mWindowManager.removeView(dialogCoolDown);
                        goToSummary();
                        break;
                }
            }
        };

        dialogPause.findViewById(R.id.workout_running_pause_quit).setOnClickListener(pauseClick);
        dialogPause.findViewById(R.id.workout_running_pause_continue).setOnClickListener(pauseClick);

        dialogCoolDown.findViewById(R.id.workout_running_cool_down_skip).setOnClickListener(pauseClick);

    }

    public AnimationsContainer.FramesSequenceAnimation animation;

    private AnimationsContainer.FramesSequenceAnimation countDownAnimation(ImageView countDownImage) {
        AnimationsContainer.FramesSequenceAnimation animation = AnimationsContainer.getInstance(getApplicationContext(), R.array.count_down, 1000).createProgressDialogAnim(countDownImage);
        return animation;
    }

    /**
     * 调整悬浮内容
     */
    public abstract void initBottomView();


    public void setRunningActivityName(String activityName) {
        this.runningActivityName = activityName;
    }

    public void setActivity(BaseRunningActivity act) {
        this.activity = act;
    }

    public void setWorkOutInfo(WorkOut workOut) {
        workOutInfo = workOut;
    }

    public WorkOut getWorkOutInfo() {
        return workOutInfo;
    }


    private boolean isShowView = false;

    /**
     * 自动判断当前应该添加还是删除悬浮窗口
     */
    public void toggleFloatWindow() {
        if (floatWindowHead != null && floatWindowBottom != null) {
            if (!isShowView) {
                mWindowManager.addView(floatWindowHead, paramsHead);
                mWindowManager.addView(floatWindowBottom, paramsBottom);
            } else {
                mWindowManager.removeView(floatWindowHead);
                mWindowManager.removeView(floatWindowBottom);
            }
            isShowView = !isShowView;
        }
    }

    public void showCountDown() {
        mWindowManager.addView(dialogCountDown, dialogParams);
        animation.start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                animation.stop();
            }
        }, 3900);
    }

    /**
     * 计时任务 同时操作level移动
     */
    public void initCountDownTimer() {
        if (runningTimer != null) {
            runningTimer.cancel();
            runningTimer = null;
        }
        if (isCountDownTime) {
            runningTimer = new CountDownTimer(runningTimeSurplus * timeSpace, timeSpace) {
                @Override
                public void onTick(long l) {
                    timerTick();
                }

                @Override
                public void onFinish() {
                    runningTimeTotal = runningTimeTarget;
                    //倒计时结束时的动作
                    System.out.println("runningTimeTotal     --------》" + runningTimeTotal);
                }
            };
        } else {
            runningTimer = new CountDownTimer(timeCountDown, timeSpace) {
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

    public synchronized void timerTick() {
        runningTimeTotal++;
        if (isCountDownTime) {
            runningTimeSurplus = runningTimeTarget - runningTimeTotal;
            floatWindowHead.setTimeValue(DateUtil.getFormatMMSS(runningTimeSurplus));
        } else {
            floatWindowHead.setTimeValue(DateUtil.getFormatMMSS(runningTimeTotal));
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
                        level.setLevel(floatWindowHead.getLevel());
                        arr.add(level);
                    }
                    workOutInfo.setLevelList(arr);
                }
            }
            floatWindowHead.setLevelValue(workOutInfo.getLevelList().get(timerMissionTimes).getLevel());
        }
    }

    public void reMoveView(View view) {
        try {
            mWindowManager.removeView(view);
        } catch (Exception e) {

        }
    }

    public void startWaiteTimerTask() {
        if (waitTask != null) {
            waitTask.shutdownNow();
            waitTask = null;
        }
        waitTask = Executors.newSingleThreadScheduledExecutor();
        waitTask.schedule(new Runnable() {
            @Override
            public void run() {
                reMoveView(dialogPause);
                reMoveView(dialogCoolDown);
                goToSummary();
            }
        }, Constant.DIALOG_WAIT_TIME, TimeUnit.MILLISECONDS);
    }

    public void stopTimerTask() {
        if (waitTask != null) {
            waitTask.shutdownNow();
            waitTask = null;
        }
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

    /**
     * 前往统计页面
     */
    public void goToSummary() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), SummaryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constant.WORK_OUT_INFO, workOutInfo);

        startActivity(intent);

        Intent serverIntent = new Intent(getApplicationContext(), BaseFloatWindowService.class);
        toggleFloatWindow();
        stopService(serverIntent);
    }

}
