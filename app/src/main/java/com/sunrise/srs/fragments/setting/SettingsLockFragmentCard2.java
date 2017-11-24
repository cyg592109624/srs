package com.sunrise.srs.fragments.setting;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseFragment;
import com.sunrise.srs.interfaces.workout.setting.OnKeyBoardReturn;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.SharedPreferencesUtils;
import com.sunrise.srs.utils.TextUtils;
import com.sunrise.srs.views.NumberKeyBoardView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/14.
 */

public class SettingsLockFragmentCard2 extends BaseFragment implements OnKeyBoardReturn {
    @BindView(R.id.settings_card4_2_customer_psw_value)
    TextView psw;

    @BindView(R.id.settings_card4_2_right)
    NumberKeyBoardView keyBoard;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings_lock_card_2;
    }

    @Override
    public void recycleObject() {
        psw = null;

        keyBoard.recycle();
        keyBoard = null;
    }

    @Override
    protected void setTextStyle() {
        List<TextView> txtList = new ArrayList<TextView>();
        txtList.add((TextView) parentView.findViewById(R.id.settings_card4_2_customer_psw));
        txtList.add((TextView) parentView.findViewById(R.id.settings_card4_2_srs_psw));
        txtList.add((TextView) parentView.findViewById(R.id.settings_card4_2_srs_psw_value));
        txtList.add(psw);
        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.MicrosoftBold());
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.ArialBold());
        }
        txtList.clear();
        txtList = null;
    }

    @Override
    protected void init() {
        keyBoard.setKeyBoardReturn(this);
        keyBoard.setTitleImage(R.mipmap.tv_keybord_password);
        psw.setText(GlobalSetting.CustomerPassWord);
    }

    @OnClick(R.id.settings_card4_2_reset_1)
    public void reSet(View view) {
        keyBoard.setVisibility(View.VISIBLE);
        TextUtils.changeTextColor(psw, ContextCompat.getColor(getContext(), R.color.settings_tabs_on));
    }

    @Override
    public void onKeyBoardEnter(String result) {
        if ("".equals(result)) {
            return;
        }
        GlobalSetting.CustomerPassWord = result;
        psw.setText(GlobalSetting.CustomerPassWord);
        SharedPreferencesUtils.put(getContext(), Constant.CUSTOMER_PASS_WORD, GlobalSetting.CustomerPassWord);
    }

    @Override
    public void onKeyBoardClose() {
        keyBoard.setVisibility(View.INVISIBLE);
        TextUtils.changeTextColor(psw, ContextCompat.getColor(getContext(), R.color.settings_white));
    }
}
