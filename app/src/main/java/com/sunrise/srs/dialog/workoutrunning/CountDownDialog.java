package com.sunrise.srs.dialog.workoutrunning;

import android.content.DialogInterface;
import android.widget.ImageView;

import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseDialogFragment;
import com.sunrise.srs.utils.AnimationsContainer;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by ChuHui on 2017/10/9.
 */

public class CountDownDialog extends BaseDialogFragment {

    @BindView(R.id.workout_running_dialog_count_down_img)
    ImageView img;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_workout_running_count_down;
    }

    @Override
    public void recycleObject() {
        img = null;
        parentView = null;
    }

    @Override
    protected void init() {
        final AnimationsContainer.FramesSequenceAnimation animation = AnimationsContainer.getInstance(fragmentContext, R.array.count_down, 1000).createProgressDialogAnim(img);
        animation.setOnAnimStopListener((AnimationsContainer.OnAnimationStoppedListener) getActivity());
        animation.start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                animation.stop();
                dismiss();
            }
        }, 3900);
    }
}
