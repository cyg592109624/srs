package com.sunrise.srs.fragments.settings;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseFragment;
import com.sunrise.srs.interfaces.workout.setting.OnKeyBoardReturn;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.SharedPreferencesUtils;
import com.sunrise.srs.utils.TextUtils;
import com.sunrise.srs.utils.UnitUtils;
import com.sunrise.srs.views.NumberKeyBoardView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/14.
 */

public class SettingsLockFragmentCard1 extends BaseFragment implements OnKeyBoardReturn {
    @BindView(R.id.settings_lock_fragment_card_1_1)
    ConstraintLayout leftLayout;

    @BindView(R.id.settings_card4_1_1_reset)
    ImageView leftReset;

    @BindView(R.id.settings_card4_1_1_time_value)
    TextView timeValue;

    @BindView(R.id.settings_card4_1_1_remaining_time_value)
    TextView remainingTimeValue;


    @BindView(R.id.settings_lock_fragment_card_1_2)
    ConstraintLayout rightLayout;

    @BindView(R.id.settings_card4_1_2_reset)
    ImageView rightReset;


    @BindView(R.id.settings_card4_1_2_distance_value)
    TextView distanceValue;

    @BindView(R.id.settings_card4_1_2_remaining_distance_value)
    TextView remainingDistanceValue;


    @BindView(R.id.settings_lock_fragment_card_1_2_keyboard)
    NumberKeyBoardView rightKeyBoard;

    @BindView(R.id.settings_lock_fragment_card_1_1_keyboard)
    NumberKeyBoardView leftKeyBoard;

    private static int reSetTG = -1;
    private static final int RE_SET_TIME = 1001;
    private static final int RE_SET_DISTANCE = 1002;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings_lock_card_1;
    }

    @Override
    public void recycleObject() {
        leftLayout = null;
        leftReset = null;
        timeValue = null;
        remainingTimeValue = null;

        rightLayout = null;

        distanceValue = null;

        remainingDistanceValue = null;

        rightKeyBoard.recycle();
        rightKeyBoard = null;

        leftKeyBoard.recycle();
        leftKeyBoard = null;
    }

    @Override
    protected void setTextStyle() {
        List<TextView> txtList = new ArrayList<>();

        txtList.add((TextView) parentView.findViewById(R.id.settings_card4_1_1_time));
        txtList.add((TextView) parentView.findViewById(R.id.settings_card4_1_1_time_unit));
        txtList.add((TextView) parentView.findViewById(R.id.settings_card4_1_1_remaining_time));
        txtList.add((TextView) parentView.findViewById(R.id.settings_card4_1_1_remaining_time_unit));

        txtList.add((TextView) parentView.findViewById(R.id.settings_card4_1_2_distance));
        txtList.add((TextView) parentView.findViewById(R.id.settings_card4_1_2_distance_unit));

        txtList.add((TextView) parentView.findViewById(R.id.settings_card4_1_2_remaining_distance));
        txtList.add((TextView) parentView.findViewById(R.id.settings_card4_1_2_remaining_distance_unit));

        txtList.add(timeValue);
        txtList.add(remainingTimeValue);

        txtList.add(distanceValue);
        txtList.add(remainingDistanceValue);

        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.MicrosoftBold(getContext()));
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.ArialBold(getContext()));
        }

        //所以数据保存都是以国际标准保存的 再进行转换
        if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
            txtList.get(5).setText(R.string.unit_km);
            txtList.get(7).setText(R.string.unit_km);
        } else {
            txtList.get(5).setText(R.string.unit_mile);
            txtList.get(7).setText(R.string.unit_mile);
        }

        txtList.clear();
        txtList = null;
    }

    @Override
    protected void init() {

        leftKeyBoard.setTitleImage(R.mipmap.tv_keybord_time);
        rightKeyBoard.setTitleImage(R.mipmap.tv_keybord_distance);

        leftKeyBoard.setKeyBoardReturn(this);
        rightKeyBoard.setKeyBoardReturn(this);


        timeValue.setText(GlobalSetting.Setting_Time);
        remainingTimeValue.setText(GlobalSetting.Setting_RemainingTime);


        if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
            distanceValue.setText(GlobalSetting.Setting_Distance);
            remainingDistanceValue.setText(GlobalSetting.Setting_RemainingDistance);
        } else {
            String dis = UnitUtils.km2mile(GlobalSetting.Setting_Distance) + "";
            distanceValue.setText(dis);
            dis = UnitUtils.km2mile(GlobalSetting.Setting_RemainingDistance) + "";
            remainingDistanceValue.setText(dis);
        }
    }

    @OnClick({R.id.settings_card4_1_1_reset, R.id.settings_card4_1_2_reset})
    public void reSet(View view) {
        switch (view.getId()) {
            case R.id.settings_card4_1_1_reset:
                reSetTG = RE_SET_TIME;
                rightLayout.setVisibility(View.GONE);
                leftKeyBoard.setVisibility(View.VISIBLE);
                TextUtils.changeTextColor(timeValue, ContextCompat.getColor(getContext(), R.color.settings_tabs_on));
                break;
            case R.id.settings_card4_1_2_reset:
                reSetTG = RE_SET_DISTANCE;
                leftLayout.setVisibility(View.GONE);
                rightKeyBoard.setVisibility(View.VISIBLE);
                TextUtils.changeTextColor(distanceValue, ContextCompat.getColor(getContext(), R.color.settings_tabs_on));
                break;
            default:
                break;
        }
    }

    @Override
    public void onKeyBoardEnter(String result) {
        if ("".equals(result)) {
            return;
        }
        switch (reSetTG) {
            default:
                break;
            case RE_SET_TIME:
                timeValue.setText(result);
                GlobalSetting.Setting_Time = result;
                SharedPreferencesUtils.put(getContext(), Constant.SETTING_TIME, GlobalSetting.Setting_Time);

                Long min = Long.valueOf(result) * 60L;
                GlobalSetting.Setting_RemainingTime = "" + min;
                SharedPreferencesUtils.put(getContext(), Constant.SETTING_REMAINING_TIME, GlobalSetting.Setting_RemainingTime);
                remainingTimeValue.setText("" + min);
                break;
            case RE_SET_DISTANCE:
                distanceValue.setText(result);
                remainingDistanceValue.setText(result);
                if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
                    GlobalSetting.Setting_Distance = result;
                    SharedPreferencesUtils.put(getContext(), Constant.SETTING_DISTANCE, GlobalSetting.Setting_Distance);

                    GlobalSetting.Setting_RemainingDistance = result;
                    SharedPreferencesUtils.put(getContext(), Constant.SETTING_REMAINING_DISTANCE, GlobalSetting.Setting_RemainingDistance);
                } else {
                    GlobalSetting.Setting_Distance = "" + UnitUtils.mile2km(result);
                    SharedPreferencesUtils.put(getContext(), Constant.SETTING_DISTANCE, GlobalSetting.Setting_Distance);

                    GlobalSetting.Setting_RemainingDistance = "" + UnitUtils.mile2km(result);
                    SharedPreferencesUtils.put(getContext(), Constant.SETTING_REMAINING_DISTANCE, GlobalSetting.Setting_RemainingDistance);
                }
                break;
        }
    }

    @Override
    public void onKeyBoardClose() {
        switch (reSetTG) {
            case RE_SET_TIME:
                TextUtils.changeTextColor(timeValue, ContextCompat.getColor(getContext(), R.color.settings_white));
                rightLayout.setVisibility(View.VISIBLE);
                leftKeyBoard.setVisibility(View.GONE);
                break;
            case RE_SET_DISTANCE:
                TextUtils.changeTextColor(distanceValue, ContextCompat.getColor(getContext(), R.color.settings_white));
                leftLayout.setVisibility(View.VISIBLE);
                rightKeyBoard.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}
