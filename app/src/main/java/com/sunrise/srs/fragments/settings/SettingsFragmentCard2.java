package com.sunrise.srs.fragments.settings;

import android.content.Intent;
import android.provider.Settings;

import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseFragment;
import com.sunrise.srs.services.settings.BackPressServer;

/**
 * Created by ChuHui on 2017/9/14.
 */

public class SettingsFragmentCard2 extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings_card_2;
    }

    @Override
    public void recycleObject() {
    }

    @Override
    protected void init() {
        openBle();
    }

    private void openBle() {
        try {
            Thread.sleep(200);
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
