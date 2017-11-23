package com.sunrise.srs.activity.home;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.R;
import com.sunrise.srs.activity.LogoActivity;
import com.sunrise.srs.activity.factory.FactoriesActivity;
import com.sunrise.srs.activity.setting.SettingActivity;
import com.sunrise.srs.activity.workout.running.QuickStartRunningActivity;
import com.sunrise.srs.activity.workout.setting.FitnessTestActivity;
import com.sunrise.srs.activity.workout.setting.GoalActivity;
import com.sunrise.srs.activity.workout.setting.HRCActivity;
import com.sunrise.srs.activity.workout.setting.HillActivity;
import com.sunrise.srs.activity.workout.setting.IntervalActivity;
import com.sunrise.srs.activity.workout.setting.UserProgramActivity;
import com.sunrise.srs.activity.workout.setting.VirtualRealityActivity;
import com.sunrise.srs.adapter.home.HomeViewPageAdapter;
import com.sunrise.srs.base.BaseFragmentActivity;
import com.sunrise.srs.bean.Level;
import com.sunrise.srs.bean.WorkOut;
import com.sunrise.srs.dialog.home.InitialiteDialog;
import com.sunrise.srs.dialog.home.LanguageDialog;
import com.sunrise.srs.fragments.home.HomeFragmentPage1;
import com.sunrise.srs.fragments.home.HomeFragmentPage2;
import com.sunrise.srs.fragments.home.HomeFragmentPage3;
import com.sunrise.srs.interfaces.home.OnLanguageSelectResult;
import com.sunrise.srs.interfaces.home.OnInitialReturn;
import com.sunrise.srs.interfaces.home.OnModeSelectReturn;
import com.sunrise.srs.services.workoutrunning.MediaQuickStartServer;
import com.sunrise.srs.utils.BitMapUtils;
import com.sunrise.srs.utils.ImageUtils;
import com.sunrise.srs.utils.ThreadPoolUtils;
import com.sunrise.srs.views.LogoImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/6.
 */
public class HomeActivity extends BaseFragmentActivity implements OnLanguageSelectResult, OnInitialReturn, ViewPager.OnPageChangeListener, OnModeSelectReturn {
    @BindView(R.id.home_img_vp)
    ImageView selectTg;

    @BindView(R.id.home_viewPage)
    ViewPager viewPager;

    private HomeViewPageAdapter fragmentAdapter;

    private Bitmap selectTgBitmap;

    private Intent mediaServerIntent;
    private PackageManager packageManager;

    private final String mediaMode = "MEDIA_MODE";

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void recycleObject() {
        selectTg = null;
        if (selectTgBitmap != null) {
            selectTgBitmap.recycle();
            selectTgBitmap = null;
        }
        mediaServerIntent = null;
        viewPager.removeAllViews();
        viewPager = null;
        fragmentAdapter.recycle();
        fragmentAdapter = null;
        fragmentManager = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaServerIntent != null) {
            stopService(mediaServerIntent);
        }
    }

    @Override
    protected void init() {
        workOutInfo = new WorkOut();
        packageManager = getApplicationContext().getPackageManager();
        List<Fragment> list = new ArrayList<Fragment>();
        HomeFragmentPage1 page1 = new HomeFragmentPage1();
        HomeFragmentPage2 page2 = new HomeFragmentPage2();
        HomeFragmentPage3 page3 = new HomeFragmentPage3();

        page1.setSelectReturn(HomeActivity.this);
        page2.setSelectReturn(HomeActivity.this);
        page3.setSelectReturn(HomeActivity.this);

        list.add(page1);
        list.add(page2);
        list.add(page3);
        fragmentAdapter = new HomeViewPageAdapter(fragmentManager, list);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(HomeActivity.this);
        viewPager.setOffscreenPageLimit(3);

        LogoImageView logo = findViewById(R.id.bottom_logo);
        logo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(activityContext, FactoriesActivity.class);
                startActivity(intent);
                return true;
            }
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Constant.initialFinish == -1) {
            showInitialDialog();
            Constant.initialFinish = 1;
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (selectTgBitmap != null) {
            selectTgBitmap.recycle();
        }
        switch (position) {
            case 0:
                selectTgBitmap = BitMapUtils.loadMipMapResource(getResources(), R.mipmap.img_virtual_reality_page_1);
                ImageUtils.changeImageView(selectTg, selectTgBitmap);
                break;
            case 1:
                selectTgBitmap = BitMapUtils.loadMipMapResource(getResources(), R.mipmap.img_virtual_reality_page_2);
                ImageUtils.changeImageView(selectTg, selectTgBitmap);
                break;
            case 2:
                selectTgBitmap = BitMapUtils.loadMipMapResource(getResources(), R.mipmap.img_virtual_reality_page_3);
                ImageUtils.changeImageView(selectTg, selectTgBitmap);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onInitialResult(String result) {

    }

    @Override
    public void onLanguageResult(boolean isChange) {
        if (isChange) {
            Intent intent = new Intent(activityContext, LogoActivity.class);
            finishActivity();
            startActivity(intent);
        }
    }

    @Override
    public void onWorkOutSetting(int result) {
        Intent intent = null;
        switch (result) {
            default:
                break;
            case Constant.MODE_HILL:
                intent = new Intent(HomeActivity.this, HillActivity.class);
                break;
            case Constant.MODE_INTERVAL:
                intent = new Intent(HomeActivity.this, IntervalActivity.class);
                break;
            case Constant.MODE_GOAL:
                intent = new Intent(HomeActivity.this, GoalActivity.class);
                break;
            case Constant.MODE_FITNESS_TEST:
                intent = new Intent(HomeActivity.this, FitnessTestActivity.class);
                break;
            case Constant.MODE_HRC:
                intent = new Intent(HomeActivity.this, HRCActivity.class);
                break;
            case Constant.MODE_VR:
                intent = new Intent(HomeActivity.this, VirtualRealityActivity.class);
                break;
            case Constant.MODE_USER_PROGRAM:
                intent = new Intent(HomeActivity.this, UserProgramActivity.class);
                break;
            case Constant.MODE_QUICK_START:
                intent = new Intent(HomeActivity.this, QuickStartRunningActivity.class);
                Random random = new Random();
                int max = 36;
                int min = 1;
                List<Level> array = new ArrayList<>();
                for (int i = 0; i < 30; i++) {
                    Level level = new Level();
                    level.setLevel(random.nextInt(max) % (max - min + 1) + min);
                    array.add(level);
                }
                workOutInfo.setLevelList(array);

                break;
        }
        if (mediaServerIntent != null) {
            stopService(mediaServerIntent);
            mediaServerIntent = null;
        }
        if (intent != null) {
            intent.putExtra(Constant.WORK_OUT_INFO, workOutInfo);
            startActivity(intent);
        }
    }

    @Override
    public void onMediaStart(int mediaType) {
        workOutInfo.setWorkOutModeName(Constant.WORK_OUT_MODE_MEDIA);
        Intent mediaIntent=null;
        switch (mediaType) {
            default:
                break;
            case Constant.MODE_MEDIA_YOUTUBE:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_YOUTUBE);
                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_YOUTUBE);
                break;
            case Constant.MODE_MEDIA_CHROME:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_CHROME);
                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_CHROME);
                break;
            case Constant.MODE_MEDIA_FACEBOOK:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_FACEBOOK);
                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_FACEBOOK);
                break;
            case Constant.MODE_MEDIA_FLIKR:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_FLIKR);
                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_FLIKR);
                break;
            case Constant.MODE_MEDIA_INSTAGRAM:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_INSTAGRAM);
                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_INSTAGRAM);
                break;
            case Constant.MODE_MEDIA_MP3:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_MP3);
                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_MP4);
                break;
            case Constant.MODE_MEDIA_MP4:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_MP4);
                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_MP4);
                break;
            case Constant.MODE_MEDIA_AVIN:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_AVIN);
//                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_AVIN);
                break;
            case Constant.MODE_MEDIA_TWITTER:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_TWITTER);
                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_TWITTER);
                break;
            case Constant.MODE_MEDIA_SCREEN_MIRROR:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_SCREEN_MIRROR);
                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_SCREEN_MIRROR);
                break;
            case Constant.MODE_MEDIA_BAI_DU:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_BAI_DU);
                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_BAI_DU);
                break;
            case Constant.MODE_MEDIA_WEI_BO:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_WEI_BO);
                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_WEI_BO);
                break;
            case Constant.MODE_MEDIA_I71:
                workOutInfo.setWorkOutMode(Constant.MODE_MEDIA_I71);
                mediaIntent = packageManager.getLaunchIntentForPackage(Constant.MEDIA_I71);
                break;
        }

        if(mediaIntent!=null){
            mediaServerIntent = new Intent(HomeActivity.this, MediaQuickStartServer.class);

            Random random = new Random();
            int max = 36;
            int min = 1;
            List<Level> array = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                Level level = new Level();
                level.setLevel(random.nextInt(max) % (max - min + 1) + min);
                array.add(level);
            }
            workOutInfo.setLevelList(array);
            mediaServerIntent.putExtra(Constant.WORK_OUT_INFO, workOutInfo);

            startService(mediaServerIntent);
            startActivity(mediaIntent);
        }

    }

    @OnClick(R.id.home_btn_language)
    public void changeLanguage() {
        LanguageDialog languageDialog = new LanguageDialog();
        languageDialog.show(fragmentManager, Constant.TAG);
    }

    @OnClick(R.id.home_btn_setting)
    public void toSettings() {
        Intent intent = new Intent(activityContext, SettingActivity.class);
        startActivity(intent);
    }

    private void showInitialDialog() {
        ThreadPoolUtils.runTaskOnThread(new Runnable() {
            @Override
            public void run() {
                InitialiteDialog initialiteDialog = new InitialiteDialog();
                initialiteDialog.show(fragmentManager, Constant.TAG);
            }
        });
    }
}
