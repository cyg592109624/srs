package com.sunrise.srs.dialog.home;

import android.support.v4.app.FragmentManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseDialogFragment;
import com.sunrise.srs.interfaces.home.OnInitialReturn;

import butterknife.BindView;

/**
 * Created by ChuHui on 2017/9/12.
 */

public class InitialiteDialog extends BaseDialogFragment {

    private OnInitialReturn onInitialReturn;
    private boolean isShowing = false;

    @BindView(R.id.dialog_home_inititalite_img)
    ImageView img;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_home_initialite;
    }

    @Override
    public void recycleObject() {
        img = null;
        onInitialReturn = null;
    }

    @Override
    public void init() {
        onInitialReturn = (OnInitialReturn) getActivity();
        animal();
    }

    public void animal() {
        if (!isShowing) {
            isShowing = true;
            Animation rotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.home_dialog_initialite);
            rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isShowing = false;
                    dismiss();
                    onInitialReturn.onInitialResult("");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            img.startAnimation(rotateAnimation);
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }
}
