package com.sunrise.srs.fragments.home;

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

public class HomeFragmentPage3Zh extends BaseFragment {
    private OnModeSelectReturn selectReturn;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_3_zh;
    }

    @Override
    protected void init() {
        selectReturn = (OnModeSelectReturn) getActivity();

        ImageView imageView1 = parentView.findViewById(R.id.home_app_mode_mp4);
        ImageView imageView2 = parentView.findViewById(R.id.home_app_mode_screen_mirror);
        ImageView imageView3 = parentView.findViewById(R.id.workout_mode_quick_start);

        Glide.with(this).load(R.drawable.btn_home_mp4).into(imageView1);
        Glide.with(this).load(R.drawable.btn_home_screen_mirroring).into(imageView2);
        Glide.with(this).load(R.drawable.btn_home_quick_start).into(imageView3);

    }

    @Override
    public void recycleObject() {
    }

    public void setSelectReturn(OnModeSelectReturn selectReturn) {
        this.selectReturn = selectReturn;
    }

    @OnClick({R.id.home_app_mode_mp4, R.id.home_app_mode_screen_mirror, R.id.workout_mode_quick_start})
    public void onMediaClick(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.home_app_mode_mp4:
                selectReturn.onMediaStart(Constant.MODE_MEDIA_MP4);
                break;
            case R.id.home_app_mode_screen_mirror:
                selectReturn.onMediaStart(Constant.MODE_MEDIA_SCREEN_MIRROR);
                break;
            case R.id.workout_mode_quick_start:
                selectReturn.onWorkOutSetting(Constant.MODE_QUICK_START);
                break;
        }
    }
}
