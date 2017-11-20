package com.sunrise.srs.activity.summary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.activity.home.HomeActivity;
import com.sunrise.srs.adapter.summary.SummaryViewPageAdapter;
import com.sunrise.srs.base.BaseFragmentActivity;
import com.sunrise.srs.fragments.summary.SummaryFragmentPage1;
import com.sunrise.srs.fragments.summary.SummaryFragmentPage2;
import com.sunrise.srs.fragments.summary.SummaryFragmentPage3;
import com.sunrise.srs.fragments.summary.SummaryFragmentPage4;
import com.sunrise.srs.utils.BitMapUtils;
import com.sunrise.srs.utils.ImageUtils;
import com.sunrise.srs.utils.SharedPreferencesUtils;
import com.sunrise.srs.utils.UnitUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/10/17.
 */

public class SummaryActivity extends BaseFragmentActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.summary_fragment_img)
    ImageView selectTg;
    @BindView(R.id.summary_viewPage)
    ViewPager viewPager;

    private Bitmap selectTgBitmap;
    private SummaryViewPageAdapter viewPageAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_summary;
    }

    @Override
    public void recycleObject() {
        selectTg = null;
        if (selectTgBitmap != null) {
            selectTgBitmap.recycle();
            selectTgBitmap = null;
        }
        viewPager.removeAllViews();
        viewPager = null;
        viewPageAdapter.recycle();
        viewPageAdapter = null;
    }

    @Override
    protected void init() {
        workOutInfo = getIntent().getParcelableExtra(Constant.WORK_OUT_INFO);
        List<Fragment> list = new ArrayList<Fragment>();
        if (workOutInfo.getWorkOutModeName().equals(Constant.WORK_OUT_MODE_FITNESS_TEST)) {
            list.add(new SummaryFragmentPage4());
        } else {
            list.add(new SummaryFragmentPage1());
        }
        list.add(new SummaryFragmentPage2());
        list.add(new SummaryFragmentPage3());
        viewPageAdapter = new SummaryViewPageAdapter(fragmentManager, list);
        viewPager.setAdapter(viewPageAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(SummaryActivity.this);
        viewPager.setOffscreenPageLimit(3);
        upDataInof();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (selectTgBitmap != null) {
            selectTgBitmap.recycle();
        }
        switch (position) {
            case 0:
                selectTgBitmap = BitMapUtils.loadMipMapResource(activityContext.getResources(), R.mipmap.img_virtual_reality_page_1);
                ImageUtils.changeImageView(selectTg, selectTgBitmap);
                break;
            case 1:
                selectTgBitmap = BitMapUtils.loadMipMapResource(activityContext.getResources(), R.mipmap.img_virtual_reality_page_2);
                ImageUtils.changeImageView(selectTg, selectTgBitmap);
                break;
            case 2:
                selectTgBitmap = BitMapUtils.loadMipMapResource(activityContext.getResources(), R.mipmap.img_virtual_reality_page_3);
                ImageUtils.changeImageView(selectTg, selectTgBitmap);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    @OnClick(R.id.bottom_logo_tab_home)
    public void onHomeClick() {
        Intent intent = new Intent(activityContext, HomeActivity.class);
        finishActivity();
        startActivity(intent);
    }

    /**
     * 更新数据(目标界面是SettingLongc与Factory2)
     */
    private void upDataInof() {
        //针对用户计算
        //计算剩下时间
        long remainingTime = Long.valueOf(GlobalSetting.Setting_RemainingTime) * 60 - Long.valueOf(workOutInfo.getRunningTime());

        long resultTime = remainingTime / 60;
        GlobalSetting.Setting_RemainingTime = resultTime + "";
        SharedPreferencesUtils.put(activityContext, Constant.SETTING_REMAINING_TIME, GlobalSetting.Setting_RemainingTime);

        float remainingDistance;
        if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
            //公制单位
            remainingDistance = Float.valueOf(GlobalSetting.Setting_RemainingDistance) - Float.valueOf(workOutInfo.getRunningDistance());
        } else {
            //英制单位 但是数据是以公制保存的
            remainingDistance = Float.valueOf(GlobalSetting.Setting_RemainingDistance) - UnitUtils.mile2km(workOutInfo.getRunningDistance());
        }
        GlobalSetting.Setting_RemainingDistance = remainingDistance + "";
        SharedPreferencesUtils.put(activityContext, Constant.SETTING_REMAINING_DISTANCE, GlobalSetting.Setting_RemainingDistance);

        //针对机器运行总数计算
        //分钟数
        long totalTime = Long.valueOf(workOutInfo.getRunningTime()) % 60;
        if (totalTime == 0) {
            totalTime = Long.valueOf(workOutInfo.getRunningTime()) / 60;
        } else {
            totalTime = 1 + Long.valueOf(workOutInfo.getRunningTime()) / 60;
        }
        GlobalSetting.Factory2_TotalTime = GlobalSetting.Factory2_TotalTime + totalTime;
        SharedPreferencesUtils.put(activityContext, Constant.FACTORY2_TOTAL_TIME, GlobalSetting.Factory2_TotalTime);

        float totalDistance;
        if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
            //公制单位
            totalDistance = Float.valueOf(GlobalSetting.Factory2_TotalDistant) + Float.valueOf(workOutInfo.getRunningDistance());
        } else {
            //英制单位 但是数据是以公制保存的
            totalDistance = Float.valueOf(GlobalSetting.Factory2_TotalDistant) + UnitUtils.mile2km(workOutInfo.getRunningDistance());
        }
        GlobalSetting.Factory2_TotalDistant = totalDistance + "";
        SharedPreferencesUtils.put(activityContext, Constant.FACTORY2_TOTAL_DISTANT, GlobalSetting.Factory2_TotalDistant);

    }
}
