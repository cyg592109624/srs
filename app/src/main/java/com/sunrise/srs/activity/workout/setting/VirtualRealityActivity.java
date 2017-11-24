package com.sunrise.srs.activity.workout.setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.adapter.workoutsetting.VrViewPageAdapter;
import com.sunrise.srs.base.BaseFragmentActivity;
import com.sunrise.srs.dialog.workoutsetting.VirtualSetValueDialog;
import com.sunrise.srs.fragments.workoutsetting.VrFragmentPage1;
import com.sunrise.srs.fragments.workoutsetting.VrFragmentPage2;
import com.sunrise.srs.fragments.workoutsetting.VrFragmentPage3;
import com.sunrise.srs.interfaces.workout.setting.OnVrDialogClick;
import com.sunrise.srs.interfaces.workout.setting.OnVrSelectReturn;
import com.sunrise.srs.utils.ImageUtils;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/26.
 *
 * @author cyg
 */

public class VirtualRealityActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener, OnVrSelectReturn, OnVrDialogClick {
    public static final String SELECT_VR_NUM = "SELECT_VR_NUM";
    public static final String SELECT_VR_IMG = "SELECT_VR_IMG";
    public static final int SELECT_NOTHING = -1;

    @BindView(R.id.workout_setting_head_hint)
    TextView settingHeadHint;


    @BindView(R.id.include2)
    LinearLayout optionBody;

    @BindView(R.id.workout_vr_view_page)
    ViewPager viewPage;

    @BindView(R.id.workout_vr_img)
    ImageView viewPageTg;

    private VirtualSetValueDialog setValueDialog;

    private VrViewPageAdapter fragmentAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_workout_setting_virtual_reality;
    }

    @Override
    public void recycleObject() {
        settingHeadHint = null;

        optionBody = null;

        viewPageTg = null;

        viewPage.removeAllViews();
        viewPage = null;

        fragmentAdapter.recycle();
        fragmentAdapter = null;
    }

    @Override
    protected void init() {
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(new VrFragmentPage1());
        list.add(new VrFragmentPage2());
        list.add(new VrFragmentPage3());
        fragmentAdapter = new VrViewPageAdapter(fragmentManager, list);
        viewPage.setAdapter(fragmentAdapter);
        viewPage.setCurrentItem(0);
        viewPage.addOnPageChangeListener(this);
        viewPage.setOffscreenPageLimit(3);
    }

    @Override
    protected void setTextStyle() {
        workOutInfo = getIntent().getParcelableExtra(Constant.WORK_OUT_INFO);
        List<TextView> txtList = new ArrayList<TextView>();
        txtList.add((TextView) findViewById(R.id.workout_setting_head_name));
        txtList.add(settingHeadHint);
        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.Microsoft());
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.Arial());
        }
        txtList.clear();
        txtList = null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                ImageUtils.changeImageView(viewPageTg, R.mipmap.img_virtual_reality_page_1);
                break;
            case 1:
                ImageUtils.changeImageView(viewPageTg, R.mipmap.img_virtual_reality_page_2);
                break;
            case 2:
                ImageUtils.changeImageView(viewPageTg, R.mipmap.img_virtual_reality_page_3);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onVRSelect(int vrNum) {
        if (vrNum != SELECT_NOTHING) {
            isShowOptionBody(false);
            Bundle bundle = new Bundle();
            bundle.putInt(SELECT_VR_NUM, vrNum);
            bundle.putInt(SELECT_VR_IMG, changeVRImage(vrNum));
            if (setValueDialog == null) {
                setValueDialog = new VirtualSetValueDialog();
            }
            setValueDialog.setArguments(bundle);
            setValueDialog.show(fragmentManager, Constant.TAG);
        }
    }

    @Override
    public void onStartClick(int vrNum, String time) {
        if (vrNum != SELECT_NOTHING) {
            workOutInfo.setWorkOutMode(Constant.MODE_VR);
            workOutInfo.setWorkOutModeName(Constant.WORK_OUT_MODE_VR);

            workOutInfo.setVrType(vrNum);
            workOutInfo.setTime(time);
        }
    }

    @Override
    public void onBackClick() {
        isShowOptionBody(true);
    }

    private void isShowOptionBody(boolean isShow) {
        if (isShow) {
            optionBody.setVisibility(View.VISIBLE);
            settingHeadHint.setText(R.string.workout_mode_hint_i);
        } else {
            optionBody.setVisibility(View.INVISIBLE);
            settingHeadHint.setText(R.string.workout_mode_hint_h);
        }
    }

    @OnClick(R.id.bottom_logo_tab_home)
    public void onBackHome() {
        finishActivity();
    }

    private int changeVRImage(int vrNum) {
        int img = -1;
        switch (vrNum) {
            default:
                break;
            case Constant.MODE_VR_TYPE_VR1:
                img = R.mipmap.img_program_virtual_01_4;
                break;
            case Constant.MODE_VR_TYPE_VR2:
                img = R.mipmap.img_program_virtual_02_4;
                break;
            case Constant.MODE_VR_TYPE_VR3:
                img = R.mipmap.img_program_virtual_03_4;
                break;
            case Constant.MODE_VR_TYPE_VR4:
                img = R.mipmap.img_program_virtual_04_4;
                break;
            case Constant.MODE_VR_TYPE_VR5:
                img = R.mipmap.img_program_virtual_05_4;
                break;
            case Constant.MODE_VR_TYPE_VR6:
                img = R.mipmap.img_program_virtual_06_4;
                break;
            case Constant.MODE_VR_TYPE_VR7:
                img = R.mipmap.img_program_virtual_07_4;
                break;
            case Constant.MODE_VR_TYPE_VR8:
                img = R.mipmap.img_program_virtual_08_4;
                break;
            case Constant.MODE_VR_TYPE_VR9:
                img = R.mipmap.img_program_virtual_09_4;
                break;
            case Constant.MODE_VR_TYPE_VR10:
                img = R.mipmap.img_program_virtual_10_4;
                break;
        }
        return img;
    }

}
