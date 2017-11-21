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

public class HomeFragmentPage2 extends BaseFragment {
    private OnModeSelectReturn selectReturn;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_2;
    }

    @Override
    protected void init() {
        selectReturn = (OnModeSelectReturn) getActivity();

//        ImageView imageView1 = parentView.findViewById(R.id.home_app_mode_youtube);
//        ImageView imageView2 = parentView.findViewById(R.id.home_app_mode_chrome);
//        ImageView imageView3 = parentView.findViewById(R.id.home_app_mode_facebook);
//        ImageView imageView4 = parentView.findViewById(R.id.home_app_mode_flikr);
//        ImageView imageView5 = parentView.findViewById(R.id.home_app_mode_instagram);
//        ImageView imageView6 = parentView.findViewById(R.id.workout_mode_quick_start);
//
//        Glide.with(this).load(R.drawable.btn_home_youtube).into(imageView1);
//        Glide.with(this).load(R.drawable.btn_home_chrome).into(imageView2);
//        Glide.with(this).load(R.drawable.btn_home_flikr).into(imageView3);
//        Glide.with(this).load(R.drawable.btn_home_face_book).into(imageView4);
//        Glide.with(this).load(R.drawable.btn_home_instagram).into(imageView5);
//        Glide.with(this).load(R.drawable.btn_home_quick_start).into(imageView6);
    }

    @Override
    public void recycleObject() {
    }
    public void setSelectReturn(OnModeSelectReturn selectReturn){
        this.selectReturn=selectReturn;
    }


    @OnClick({R.id.home_app_mode_youtube, R.id.home_app_mode_chrome, R.id.home_app_mode_facebook,
            R.id.home_app_mode_flikr, R.id.home_app_mode_instagram, R.id.workout_mode_quick_start})
    public void onMediaClick(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.home_app_mode_youtube:
                selectReturn.onMediaStart(Constant.MODE_MEDIA_YOUTUBE);
                break;
            case R.id.home_app_mode_chrome:
                selectReturn.onMediaStart(Constant.MODE_MEDIA_CHROME);
                break;
            case R.id.home_app_mode_facebook:
                selectReturn.onMediaStart(Constant.MODE_MEDIA_FACEBOOK);
                break;
            case R.id.home_app_mode_flikr:
                selectReturn.onMediaStart(Constant.MODE_MEDIA_FLIKR);
                break;
            case R.id.home_app_mode_instagram:
                selectReturn.onMediaStart(Constant.MODE_MEDIA_INSTAGRAM);
                break;
            case R.id.workout_mode_quick_start:
                selectReturn.onWorkOutSetting(Constant.MODE_QUICK_START);
                break;
        }
    }
}
