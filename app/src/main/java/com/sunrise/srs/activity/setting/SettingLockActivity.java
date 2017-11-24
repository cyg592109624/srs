package com.sunrise.srs.activity.setting;

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
import com.sunrise.srs.dialog.setting.SettingsLockPswDialog;
import com.sunrise.srs.fragments.setting.SettingsLockFragmentCard1;
import com.sunrise.srs.fragments.setting.SettingsLockFragmentCard2;
import com.sunrise.srs.interfaces.workout.setting.OnKeyBoardReturn;
import com.sunrise.srs.utils.ImageUtils;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.TextUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/15.
 */

public class SettingLockActivity extends BaseFragmentActivity implements OnKeyBoardReturn {
    @BindView(R.id.settings_view_bg)
    ImageView bgView;

    @BindViews({R.id.settings_card_lock, R.id.settings_card_psw, R.id.settings_title})
    List<TextView> txtList;

    private Fragment nowFragment;
    private SettingsLockFragmentCard1 card1;
    private SettingsLockFragmentCard2 card2;

    private SettingsLockPswDialog pswDialog;

    private int editState = -1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings_lock;
    }

    @Override
    public void recycleObject() {
        bgView = null;
        txtList = null;
        nowFragment = null;
        card1 = null;
        card2 = null;
        pswDialog = null;
    }

    @Override
    protected void setTextStyle() {
        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.MicrosoftBold());
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.ArialBold());
        }
    }

    @Override
    protected void init() {
        showLockDialog();
    }

    @OnClick({R.id.settings_card_lock, R.id.settings_card_psw})
    public void onSelectCardClick(View view) {
        int bgResource = -1;
        int tgCard = -1;
        Fragment tgFragment = null;
        switch (view.getId()) {
            case R.id.settings_card_lock:
                bgResource = R.mipmap.img_factory_2_1;
                tgCard = 0;
                tgFragment = card1;
                break;
            case R.id.settings_card_psw:
                tgCard = 1;
                bgResource = R.mipmap.img_factory_2_2;
                if (card2 == null) {
                    card2 = new SettingsLockFragmentCard2();
                }
                tgFragment = card2;
                break;
            default:
                bgResource = -1;
                tgCard = -1;
                break;
        }
        if (bgResource != -1 && tgCard != -1) {
            ImageUtils.changeImageView(bgView, bgResource);
            for (int i = 0; i < txtList.size() - 1; i++) {
                if (i == tgCard) {
                    TextUtils.changeTextColor(txtList.get(i), ContextCompat.getColor(activityContext, R.color.settings_tabs_on));
                    TextUtils.changeTextSize(txtList.get(i), 35f);
                } else {
                    TextUtils.changeTextColor(txtList.get(i), ContextCompat.getColor(activityContext, R.color.settings_tabs_off));
                    TextUtils.changeTextSize(txtList.get(i), 30f);
                }
            }
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (!tgFragment.isAdded()) {
                ft.hide(nowFragment).add(R.id.settings_lock_views, tgFragment).commit();
            } else {
                ft.hide(nowFragment).show(tgFragment).commit();
            }
            nowFragment = tgFragment;
        }
    }

    @OnClick(R.id.bottom_logo_tab_back)
    public void onBack() {
        finishActivity();
    }

    @Override
    public void onKeyBoardEnter(String result) {
        if (result.equals(GlobalSetting.CustomerPassWord)) {
            editState = 1;
            editView();
        } else {
            editState = 0;
        }
    }

    @Override
    public void onKeyBoardClose() {
        if (editState == 1) {
            pswDialog.dismiss();
        }else if (editState == -1) {
            finishActivity();
        }
    }

    private void showLockDialog() {
        pswDialog = new SettingsLockPswDialog();
        pswDialog.show(fragmentManager, Constant.TAG);
    }

    private void editView() {
        card1 = new SettingsLockFragmentCard1();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.settings_lock_views, card1).commit();
        nowFragment = card1;
    }
}
