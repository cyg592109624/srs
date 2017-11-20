package com.sunrise.srs.dialog.workoutrunning;

import android.app.Dialog;
import android.os.Bundle;

import com.sunrise.srs.Constant;
import com.sunrise.srs.R;
import com.sunrise.srs.activity.workout.running.BaseRunningActivity;
import com.sunrise.srs.base.BaseDialogFragment;
import com.sunrise.srs.interfaces.workout.running.DialogPauseClick;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/29.
 */

public class PauseDialog extends BaseDialogFragment {
    private DialogPauseClick pauseDialogClick;
    private ScheduledExecutorService pool;
    private TimerTask task=new TimerTask() {
        @Override
        public void run() {
            dismiss();
            pauseDialogClick.onPauseTimeOut();
        }
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.Dialog_No_BG);
        return dialog;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_workout_running_pause;
    }

    @Override
    public void recycleObject() {
        pauseDialogClick = null;
        pool = null;
        task = null;

    }
    @Override
    protected void init() {
        pauseDialogClick=(BaseRunningActivity)getActivity();
        pool = Executors.newScheduledThreadPool(1);
        pool.schedule(task, Constant.DIALOG_WAIT_TIME, TimeUnit.MILLISECONDS);
    }

    @OnClick(R.id.workout_running_pause_quit)
    public void onQuit() {
        pool.shutdownNow();
        dismiss();
        pauseDialogClick.onPauseQuit();
    }
    @OnClick(R.id.workout_running_pause_continue)
    public void onContinue() {
        pool.shutdownNow();
        dismiss();
        pauseDialogClick.onPauseContinue();
    }
}
