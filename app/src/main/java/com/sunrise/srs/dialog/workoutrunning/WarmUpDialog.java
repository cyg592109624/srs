package com.sunrise.srs.dialog.workoutrunning;

import android.app.Dialog;
import android.os.Bundle;

import com.sunrise.srs.Constant;
import com.sunrise.srs.R;
import com.sunrise.srs.activity.workout.running.FitnessTestRunningActivity;
import com.sunrise.srs.base.BaseDialogFragment;
import com.sunrise.srs.interfaces.workout.running.DialogWarmUpClick;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/10/17.
 */

public class WarmUpDialog extends BaseDialogFragment {
    private DialogWarmUpClick dialogClick;

    private ScheduledExecutorService pool;
    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            dialogClick.onWarmUpSkip();
            dismiss();
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.dialog_workout_running_warm_up;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.Dialog_No_BG);
        return dialog;
    }

    @Override
    public void recycleObject() {
        dialogClick = null;
        pool = null;
        task = null;

    }

    @Override
    protected void init() {
        dialogClick = (FitnessTestRunningActivity) getActivity();
        pool = Executors.newScheduledThreadPool(1);
        pool.schedule(task, Constant.DIALOG_WAIT_TIME, TimeUnit.MILLISECONDS);
    }

    @OnClick({R.id.workout_running_warm_up_skip})
    public void onSkip() {
        pool.shutdownNow();
        dialogClick.onWarmUpSkip();
        dismiss();
    }
}
