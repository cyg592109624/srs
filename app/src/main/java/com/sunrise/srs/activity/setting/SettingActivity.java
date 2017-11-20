package com.sunrise.srs.activity.setting;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseFragmentActivity;
import com.sunrise.srs.fragments.settings.SettingsFragmentCard1;
import com.sunrise.srs.fragments.settings.SettingsFragmentCard2;
import com.sunrise.srs.fragments.settings.SettingsFragmentCard3;
import com.sunrise.srs.fragments.settings.SettingsFragmentCard4;
import com.sunrise.srs.services.settings.BackPressServer;
import com.sunrise.srs.utils.ImageUtils;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/13.
 */

public class SettingActivity extends BaseFragmentActivity {

    @BindView(R.id.settings_view_bg)
    ImageView bgView;

    @BindViews({R.id.settings_card_system, R.id.settings_card_bluetooth, R.id.settings_card_wifi, R.id.settings_card_lock, R.id.settings_title})
    List<TextView> txtList;

    private Fragment nowFragment;
    private SettingsFragmentCard1 card1;
    private SettingsFragmentCard2 card2;
    private SettingsFragmentCard3 card3;
    private SettingsFragmentCard4 card4;
    private static final int CARD_1 = 0;
    private static final int CARD_2 = 1;
    private static final int CARD_3 = 2;
    private static final int CARD_4 = 3;

    private static final float SELECT_TEXT_SIZE = 35f;
    private static final float UN_SELECT_TEXT_SIZE = 30f;

    private BackPressServer backPressServer;
    private ServiceConnection backConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if(iBinder!=null){
                backPressServer = ((BackPressServer.BackPressBinder) iBinder).getService();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void recycleObject() {
        bgView = null;
        txtList = null;
        nowFragment = null;
        card1 = null;
        card2 = null;
        card3 = null;
        card4 = null;
        bgView = null;
    }

    @Override
    protected void init() {
        card1 = new SettingsFragmentCard1();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.settings_views, card1).commit();
        nowFragment = card1;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.BACK_PRESS_SERVER_ACTION);
        registerReceiver(backPressReceiver, intentFilter);

        Intent serverIntent = new Intent(activityContext, BackPressServer.class);
        bindService(serverIntent, backConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    protected void setTextStyle() {
        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.MicrosoftBold(activityContext));
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.ArialBold(activityContext));
        }
    }

    @OnClick({R.id.settings_card_system, R.id.settings_card_bluetooth, R.id.settings_card_wifi, R.id.settings_card_lock})
    public void onSelectCardClick(View view) {
        int bgResource = -1;
        int tgCard = -1;
        Fragment tgFragment = null;
        switch (view.getId()) {
            case R.id.settings_card_system:
                tgCard = CARD_1;
                bgResource = R.mipmap.img_factory_3_1;
                tgFragment = card1;
                break;
            case R.id.settings_card_bluetooth:
                tgCard = CARD_2;
                bgResource = R.mipmap.img_factory_3_2;
                card2 = new SettingsFragmentCard2();
                backPressServer.addBackView();
                tgFragment = card2;
                break;
            case R.id.settings_card_wifi:
                tgCard = CARD_3;
                bgResource = R.mipmap.img_factory_3_3;
                card3 = new SettingsFragmentCard3();
                backPressServer.addBackView();
                tgFragment = card3;
                break;
            case R.id.settings_card_lock:
                tgCard = CARD_4;
                bgResource = R.mipmap.img_factory_3_4;
//                if(card4==null){
//                    card4 = new SettingsFragmentCard4();
//                }
//                tgFragment = card4;
                break;
            default:
                bgResource = -1;
                tgCard = -1;
                tgFragment = null;
                break;
        }
        if (bgResource != -1 && tgFragment != null) {
            ImageUtils.changeImageView(bgView, bgResource);
            for (int i = 0; i < txtList.size() - 1; i++) {
                if (i == tgCard) {
                    TextUtils.changeTextColor(txtList.get(i), ContextCompat.getColor(activityContext, R.color.settings_tabs_on));
                    TextUtils.changeTextSize(txtList.get(i), SELECT_TEXT_SIZE);
                } else {
                    TextUtils.changeTextColor(txtList.get(i), ContextCompat.getColor(activityContext, R.color.settings_tabs_off));
                    TextUtils.changeTextSize(txtList.get(i), UN_SELECT_TEXT_SIZE);
                }
            }
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (!tgFragment.isAdded()) {
                ft.hide(nowFragment).add(R.id.settings_views, tgFragment).commit();
            } else {
                ft.hide(nowFragment).show(tgFragment).commit();
            }
            nowFragment = tgFragment;
        }
        if (tgCard == CARD_4) {
            Intent intent = new Intent(activityContext, SettingLockActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.bottom_logo_tab_home)
    public void onBackHome() {
        unregisterReceiver(backPressReceiver);
        unbindService(backConnection);
        finishActivity();
    }


    private BroadcastReceiver backPressReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.BACK_PRESS_SERVER_ACTION)) {
                backPressServer.moveBackView();
//                ThreadPoolUtils.runTaskOnThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            //需要一个系统级别权限INJECT_EVENTS
//                            Instrumentation backPressIntent = new Instrumentation();
//                            backPressIntent.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
            }
        }
    };
}
