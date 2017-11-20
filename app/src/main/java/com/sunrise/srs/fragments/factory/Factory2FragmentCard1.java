package com.sunrise.srs.fragments.factory;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

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

public class Factory2FragmentCard1 extends BaseFragment implements OnKeyBoardReturn {
    @BindView(R.id.factory2_card1_1)
    ConstraintLayout leftLayout;

    @BindView(R.id.factory2_card1_1_display_mode_toggle)
    ToggleButton toggle_Display;

    @BindView(R.id.factory2_card1_1_pause_mode_toggle)
    ToggleButton toggle_Pause;

    @BindView(R.id.factory2_card1_1_key_tone_toggle)
    ToggleButton toggle_KeyTone;

    @BindView(R.id.factory2_card1_1_buzzer_toggle)
    ToggleButton toggle_Buzzer;

    @BindView(R.id.factory2_card1_1_child_lock_toggle)
    ToggleButton toggle_ChildLock;


    @BindView(R.id.factory2_card1_2_ctrl_page_toggle)
    ToggleButton toggle_CtrlPage;

    @BindView(R.id.factory2_card1_2_level_value)
    TextView levelValue;

    @BindView(R.id.factory2_card1_2_pwm_value)
    TextView pwmValue;

    @BindView(R.id.factory2_card1_1_keyboard)
    NumberKeyBoardView keyBoardView;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_factory2_card_1;
    }

    @Override
    public void recycleObject() {
        leftLayout = null;

        toggle_Display = null;
        toggle_Pause = null;
        toggle_KeyTone = null;
        toggle_Buzzer = null;
        toggle_ChildLock = null;

        toggle_CtrlPage = null;

        levelValue = null;
        pwmValue = null;
        keyBoardView.recycle();
        keyBoardView = null;
    }

    @Override
    protected void setTextStyle() {
        List<TextView> txtList = new ArrayList<TextView>();
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card1_1_display_mode));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card1_1_pause_mode));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card1_1_key_tone));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card1_1_buzzer));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card1_1_child_lock));

        txtList.add((TextView) parentView.findViewById(R.id.factory2_card1_2_ctrl_page));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card1_2_key_test));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card1_2_brake_test));

        txtList.add((TextView) parentView.findViewById(R.id.factory2_card1_2_level));
        txtList.add(levelValue);

        txtList.add((TextView) parentView.findViewById(R.id.factory2_card1_2_pwm));
        txtList.add(pwmValue);

        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.MicrosoftBold(getContext()));
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.ArialBold(getContext()));
        }
        txtList.clear();
        txtList = null;
    }

    @Override
    protected void init() {
        toggle_Display.setChecked(GlobalSetting.Factory2Mode_Display);
        toggle_Pause.setChecked(GlobalSetting.Factory2Mode_Pause);
        toggle_KeyTone.setChecked(GlobalSetting.Factory2Mode_KeyTone);
        toggle_Buzzer.setChecked(GlobalSetting.Factory2Mode_Buzzer);
        toggle_ChildLock.setChecked(GlobalSetting.Factory2Mode_ChildLock);

        toggle_CtrlPage.setEnabled(false);
        toggle_CtrlPage.setChecked(GlobalSetting.Factory2Mode_CtrlPage);

        keyBoardView.setKeyBoardReturn(this);
        levelValue.setText(GlobalSetting.Factory2_Level);
        pwmValue.setText(GlobalSetting.Factory2_PWM);
    }

    @Override
    public void onKeyBoardEnter(String result) {
        if ("".equals(result)) {
            return;
        }
        switch (reSetTG) {
            default:
                break;
            case RE_SET_LEVEL:
                levelValue.setText(result);
                GlobalSetting.Factory2_Level = result;
                SharedPreferencesUtils.put(getContext(), Constant.FACTORY2_LEVEL, GlobalSetting.Factory2_Level);
                break;
            case RE_SET_PWM:
                pwmValue.setText(result);
                GlobalSetting.Factory2_PWM = result;
                SharedPreferencesUtils.put(getContext(), Constant.FACTORY2_PWM, GlobalSetting.Factory2_PWM);
                break;
        }
    }

    @Override
    public void onKeyBoardClose() {
        switch (reSetTG) {
            default:
                break;
            case RE_SET_LEVEL:
                TextUtils.changeTextColor(levelValue, ContextCompat.getColor(getContext(), R.color.factory_white));
                TextUtils.changeTextBackground(levelValue, R.mipmap.img_number_frame_1);
                break;
            case RE_SET_PWM:
                TextUtils.changeTextColor(pwmValue, ContextCompat.getColor(getContext(), R.color.factory_white));
                TextUtils.changeTextBackground(pwmValue, R.mipmap.img_number_frame_1);
                break;
        }
        reSetTG = -1;
        isShowingKeyBoard = false;
        keyBoardView.setVisibility(View.GONE);
        leftLayout.setVisibility(View.VISIBLE);
    }


    @OnClick({R.id.factory2_card1_1_display_mode_toggle, R.id.factory2_card1_1_pause_mode_toggle,
            R.id.factory2_card1_1_key_tone_toggle, R.id.factory2_card1_1_buzzer_toggle,
            R.id.factory2_card1_1_child_lock_toggle})
    public void toggleButtonClick(View view) {
        ToggleButton toggleButton = (ToggleButton) view;
        switch (view.getId()) {
            default:
                break;
            case R.id.factory2_card1_1_display_mode_toggle:
                GlobalSetting.Factory2Mode_Display = toggleButton.isChecked();
                SharedPreferencesUtils.put(getContext(), Constant.FACTORY2_MODE_DISPLAY, GlobalSetting.Factory2Mode_Display);
                break;
            case R.id.factory2_card1_1_pause_mode_toggle:
                GlobalSetting.Factory2Mode_Pause = toggleButton.isChecked();
                SharedPreferencesUtils.put(getContext(), Constant.FACTORY2_MODE_PAUSE, GlobalSetting.Factory2Mode_Pause);
                break;
            case R.id.factory2_card1_1_key_tone_toggle:
                GlobalSetting.Factory2Mode_KeyTone = toggleButton.isChecked();
                SharedPreferencesUtils.put(getContext(), Constant.FACTORY2_MODE_KEY_TONE, GlobalSetting.Factory2Mode_KeyTone);
                break;
            case R.id.factory2_card1_1_buzzer_toggle:
                GlobalSetting.Factory2Mode_Buzzer = toggleButton.isChecked();
                SharedPreferencesUtils.put(getContext(), Constant.FACTORY2_MODE_BUZZER, GlobalSetting.Factory2Mode_Buzzer);
                break;
            case R.id.factory2_card1_1_child_lock_toggle:
                GlobalSetting.Factory2Mode_ChildLock = toggleButton.isChecked();
                SharedPreferencesUtils.put(getContext(), Constant.FACTORY2_MODE_CTRL_PAGE, GlobalSetting.Factory2Mode_ChildLock);
                break;
        }
    }

    private boolean isShowingKeyBoard = false;
    private static int reSetTG = -1;
    private static final int RE_SET_LEVEL = 1001;
    private static final int RE_SET_PWM = 1002;

    @OnClick({R.id.factory2_card1_2_pwm_value, R.id.factory2_card1_2_level_value})
    public void changeValue(View view) {
        if (isShowingKeyBoard) {
            return;
        }
        isShowingKeyBoard = true;
        keyBoardView.setVisibility(View.VISIBLE);
        leftLayout.setVisibility(View.GONE);
        switch (view.getId()) {
            default:
                break;
            case R.id.factory2_card1_2_level_value:
                reSetTG = RE_SET_LEVEL;
                keyBoardView.setTitleImage(R.mipmap.tv_keybord_level);
                TextUtils.changeTextColor(levelValue, ContextCompat.getColor(getContext(), R.color.factory_tabs_on));
                TextUtils.changeTextBackground(levelValue, R.mipmap.img_number_frame_2);
                break;
            case R.id.factory2_card1_2_pwm_value:
                reSetTG = RE_SET_PWM;
                keyBoardView.setTitleImage(R.mipmap.tv_keybord_pwm);
                TextUtils.changeTextColor(pwmValue, ContextCompat.getColor(getContext(), R.color.factory_tabs_on));
                TextUtils.changeTextBackground(pwmValue, R.mipmap.img_number_frame_2);
                break;
        }
    }


    @OnClick(R.id.factory2_card1_2_key_test_start)
    public void keyTestStart() {

    }

}
