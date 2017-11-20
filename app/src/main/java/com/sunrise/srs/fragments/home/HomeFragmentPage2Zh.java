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

public class HomeFragmentPage2Zh extends BaseFragment {

    private OnModeSelectReturn selectReturn;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_2_zh;
    }

    @Override
    protected void init() {
        selectReturn = (OnModeSelectReturn) getActivity();

        ImageView imageView1 = parentView.findViewById(R.id.home_app_mode_baidu);
        ImageView imageView2 = parentView.findViewById(R.id.home_app_mode_weibo);
        ImageView imageView3 = parentView.findViewById(R.id.home_app_mode_i71);
        ImageView imageView4 = parentView.findViewById(R.id.home_app_mode_avin);
        ImageView imageView5 = parentView.findViewById(R.id.home_app_mode_mp3);
        ImageView imageView6 = parentView.findViewById(R.id.workout_mode_quick_start);

        Glide.with(this).load(R.drawable.btn_home_bai).into(imageView1);
        Glide.with(this).load(R.drawable.btn_home_weibo).into(imageView2);
        Glide.with(this).load(R.drawable.btn_home_i71).into(imageView3);
        Glide.with(this).load(R.drawable.btn_home_av_in).into(imageView4);
        Glide.with(this).load(R.drawable.btn_home_mp3).into(imageView5);
        Glide.with(this).load(R.drawable.btn_home_quick_start).into(imageView6);
    }

    @Override
    public void recycleObject() {
    }

    public void setSelectReturn(OnModeSelectReturn selectReturn) {
        this.selectReturn = selectReturn;
    }

    @OnClick({R.id.home_app_mode_baidu, R.id.home_app_mode_weibo, R.id.home_app_mode_i71,
            R.id.home_app_mode_avin, R.id.home_app_mode_mp3, R.id.workout_mode_quick_start})
    public void onMediaClick(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.home_app_mode_baidu:
                selectReturn.onMediaStart(Constant.MODE_MEDIA_BAI_DU);
                break;
            case R.id.home_app_mode_weibo:
                selectReturn.onMediaStart(Constant.MODE_MEDIA_WEI_BO);
                break;
            case R.id.home_app_mode_i71:
                selectReturn.onMediaStart(Constant.MODE_MEDIA_I71);
                break;
            case R.id.home_app_mode_avin:
                selectReturn.onMediaStart(Constant.MODE_MEDIA_AVIN);
                break;
            case R.id.home_app_mode_mp3:
                selectReturn.onMediaStart(Constant.MODE_MEDIA_MP3);
                break;
            case R.id.workout_mode_quick_start:
                selectReturn.onWorkOutSetting(Constant.MODE_QUICK_START);
                break;
        }
    }


}
