package com.sunrise.srs.fragments.home;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sunrise.srs.Constant;
import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseFragment;
import com.sunrise.srs.interfaces.home.OnModeSelectReturn;

import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/12.
 */

public class HomeFragmentPage1 extends BaseFragment {

    private OnModeSelectReturn selectReturn;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_1;
    }

    @Override
    protected void init() {
        selectReturn = (OnModeSelectReturn) getActivity();
//        ImageView imageView1 = parentView.findViewById(R.id.workout_mode_hill);
//        ImageView imageView2 = parentView.findViewById(R.id.workout_mode_interval);
//        ImageView imageView3 = parentView.findViewById(R.id.workout_mode_goal);
//        ImageView imageView4 = parentView.findViewById(R.id.workout_mode_fitness_test);
//        ImageView imageView5 = parentView.findViewById(R.id.workout_mode_hrc);
//        ImageView imageView6 = parentView.findViewById(R.id.workout_mode_user_program);
//        ImageView imageView7 = parentView.findViewById(R.id.workout_mode_vr);
//        ImageView imageView8 = parentView.findViewById(R.id.workout_mode_quick_start);
//
//        Glide.with(this).load(R.drawable.btn_home_hill).into(imageView1);
//        Glide.with(this).load(R.drawable.btn_home_interval).into(imageView2);
//        Glide.with(this).load(R.drawable.btn_home_goal).into(imageView3);
//        Glide.with(this).load(R.drawable.btn_home_fitness).into(imageView4);
//        Glide.with(this).load(R.drawable.btn_home_hrc).into(imageView5);
//        Glide.with(this).load(R.drawable.btn_home_user_program).into(imageView6);
//        Glide.with(this).load(R.drawable.btn_home_virtual).into(imageView7);
//        Glide.with(this).load(R.drawable.btn_home_quick_start).into(imageView8);

    }

    @Override
    public void recycleObject() {
        selectReturn = null;
    }

    @OnClick({R.id.workout_mode_hill, R.id.workout_mode_interval, R.id.workout_mode_goal,
            R.id.workout_mode_fitness_test, R.id.workout_mode_hrc, R.id.workout_mode_user_program,
            R.id.workout_mode_vr, R.id.workout_mode_quick_start})
    public void selectWorkOutMode(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.workout_mode_hill:
                selectReturn.onWorkOutSetting(Constant.MODE_HILL);
                break;
            case R.id.workout_mode_interval:
                selectReturn.onWorkOutSetting(Constant.MODE_INTERVAL);
                break;
            case R.id.workout_mode_goal:
                selectReturn.onWorkOutSetting(Constant.MODE_GOAL);
                break;
            case R.id.workout_mode_fitness_test:
                selectReturn.onWorkOutSetting(Constant.MODE_FITNESS_TEST);
                break;
            case R.id.workout_mode_hrc:
                selectReturn.onWorkOutSetting(Constant.MODE_HRC);
                break;
            case R.id.workout_mode_user_program:
                selectReturn.onWorkOutSetting(Constant.MODE_USER_PROGRAM);
                break;
            case R.id.workout_mode_vr:
                selectReturn.onWorkOutSetting(Constant.MODE_VR);
                break;
            case R.id.workout_mode_quick_start:
                selectReturn.onWorkOutSetting(Constant.MODE_QUICK_START);
                break;
        }
    }

    public void setSelectReturn(OnModeSelectReturn selectReturn) {
        this.selectReturn = selectReturn;
    }
}
