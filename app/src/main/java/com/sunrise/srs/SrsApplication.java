package com.sunrise.srs;

import android.app.Application;

import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.SharedPreferencesUtils;
import com.sunrise.srs.utils.TextUtils;
import com.sunrise.srs.utils.ThreadPoolUtils;

/**
 * Created by ChuHui on 2017/9/4.
 */

public class SrsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalSetting.AppLanguage = LanguageUtils.getAppLanguage(getResources());
        ThreadPoolUtils.runTaskOnThread(new Runnable() {
            @Override
            public void run() {
                TextUtils.getInstance(getApplicationContext());
                //所以数据保存都是以公制保存的 使用时 按实际情况转换
                GlobalSetting.CustomerPassWord = (String) SharedPreferencesUtils.get(getApplicationContext(), Constant.CUSTOMER_PASS_WORD, GlobalSetting.CustomerPassWord);

                GlobalSetting.Setting_Time = (String) SharedPreferencesUtils.get(getApplicationContext(), Constant.SETTING_TIME, GlobalSetting.Setting_Time);
                GlobalSetting.Setting_RemainingTime = (String) SharedPreferencesUtils.get(getApplicationContext(), Constant.SETTING_REMAINING_TIME, GlobalSetting.Setting_RemainingTime);

                GlobalSetting.Setting_Distance = (String) SharedPreferencesUtils.get(getApplicationContext(), Constant.SETTING_DISTANCE, GlobalSetting.Setting_Distance);
                GlobalSetting.Setting_RemainingDistance = (String) SharedPreferencesUtils.get(getApplicationContext(), Constant.SETTING_REMAINING_DISTANCE, GlobalSetting.Setting_RemainingDistance);

                GlobalSetting.MachineType = (String) SharedPreferencesUtils.get(getApplicationContext(), Constant.MACHINE_TYPE, GlobalSetting.MachineType);
                GlobalSetting.UnitType = (String) SharedPreferencesUtils.get(getApplicationContext(), Constant.UNIT_TYPE, GlobalSetting.UnitType);

                GlobalSetting.Factory2Mode_Display = (boolean) SharedPreferencesUtils.get(getApplicationContext(), Constant.FACTORY2_MODE_DISPLAY, true);
                GlobalSetting.Factory2Mode_Pause = (boolean) SharedPreferencesUtils.get(getApplicationContext(), Constant.FACTORY2_MODE_PAUSE, true);
                GlobalSetting.Factory2Mode_KeyTone = (boolean) SharedPreferencesUtils.get(getApplicationContext(), Constant.FACTORY2_MODE_KEY_TONE, true);
                GlobalSetting.Factory2Mode_Buzzer = (boolean) SharedPreferencesUtils.get(getApplicationContext(), Constant.FACTORY2_MODE_BUZZER, true);
                GlobalSetting.Factory2Mode_ChildLock = (boolean) SharedPreferencesUtils.get(getApplicationContext(), Constant.FACTORY2_MODE_CHILD_LOCK, true);
                GlobalSetting.Factory2Mode_CtrlPage = (boolean) SharedPreferencesUtils.get(getApplicationContext(), Constant.FACTORY2_MODE_CTRL_PAGE, false);

                GlobalSetting.Factory2_Level = (String) SharedPreferencesUtils.get(getApplicationContext(), Constant.FACTORY2_LEVEL, GlobalSetting.Factory2_Level);
                GlobalSetting.Factory2_PWM = (String) SharedPreferencesUtils.get(getApplicationContext(), Constant.FACTORY2_PWM, GlobalSetting.Factory2_PWM);

                GlobalSetting.Factory2_TotalTime = (String) SharedPreferencesUtils.get(getApplicationContext(), Constant.FACTORY2_TOTAL_TIME, GlobalSetting.Factory2_TotalTime);
                GlobalSetting.Factory2_TotalDistant = (String) SharedPreferencesUtils.get(getApplicationContext(), Constant.FACTORY2_TOTAL_DISTANT, GlobalSetting.Factory2_TotalDistant);

            }
        });
    }

}
