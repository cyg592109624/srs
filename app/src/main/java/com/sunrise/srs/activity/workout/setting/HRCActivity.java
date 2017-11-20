package com.sunrise.srs.activity.workout.setting;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.activity.workout.WorkOutSettingCommon;
import com.sunrise.srs.activity.workout.running.HRCRunningActivity;
import com.sunrise.srs.base.BaseActivity;
import com.sunrise.srs.bean.Level;
import com.sunrise.srs.interfaces.workout.setting.OnGenderReturn;
import com.sunrise.srs.interfaces.workout.setting.OnKeyBoardReturn;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.TextUtils;
import com.sunrise.srs.views.GenderView;
import com.sunrise.srs.views.NumberKeyBoardView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/22.
 */

public class HRCActivity extends BaseActivity implements OnGenderReturn, OnKeyBoardReturn {

    @BindView(R.id.workout_mode_gender_select)
    GenderView genderView;

    @BindView(R.id.workout_mode_keyboard)
    NumberKeyBoardView keyBoardView;

    @BindView(R.id.workout_edit_age_value)
    TextView ageValue;
    @BindView(R.id.workout_edit_weight_value)
    TextView weightValue;

    @BindView(R.id.workout_edit_time_value)
    TextView timeValue;


    @BindView(R.id.workout_edit_hrc60)
    TextView hrc60Name;
    @BindView(R.id.workout_edit_hrc60_value)
    TextView hrc60Value;
    @BindView(R.id.workout_edit_hrc60_unit)
    TextView hrc60Unit;


    @BindView(R.id.workout_edit_hrc80)
    TextView hrc80Name;
    @BindView(R.id.workout_edit_hrc80_value)
    TextView hrc80Value;
    @BindView(R.id.workout_edit_hrc80_unit)
    TextView hrc80Unit;

    @BindView(R.id.workout_edit_target_hr)
    TextView hrcTgName;
    @BindView(R.id.workout_edit_target_hr_value)
    TextView hrcTgValue;
    @BindView(R.id.workout_edit_target_hr_unit)
    TextView hrcTgUnit;

    @BindView(R.id.workout_setting_hint)
    TextView settingHint;


    @BindView(R.id.include2)
    ConstraintLayout infoType1;

    @BindView(R.id.include3)
    ConstraintLayout infoType3;


    @BindView(R.id.workout_setting_next)
    ImageView nextBtn;

    @BindView(R.id.workout_setting_back)
    ImageView backBtn;


    @BindView(R.id.workout_setting_start)
    ImageView startBtn;

    private int selectHrcType = -1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_workout_setting_hrc;
    }

    @Override
    public void recycleObject() {
        genderView.recycle();
        genderView = null;

        keyBoardView.recycle();
        keyBoardView = null;

        ageValue = null;

        weightValue = null;

        timeValue = null;

        hrc60Name = null;
        hrc60Value = null;
        hrc60Unit = null;

        hrc80Name = null;
        hrc80Value = null;
        hrc80Unit = null;

        hrcTgName = null;
        hrcTgValue = null;
        hrcTgUnit = null;

        settingHint = null;

        infoType1 = null;
        infoType3 = null;

        nextBtn = null;
        backBtn = null;
        startBtn = null;
    }

    @Override
    protected void setTextStyle() {
        List<TextView> txtList = new ArrayList<TextView>();

        txtList.add((TextView) findViewById(R.id.workout_setting_head_name));
        txtList.add((TextView) findViewById(R.id.workout_setting_head_hint));
        txtList.add(settingHint);

        txtList.add((TextView) findViewById(R.id.workout_edit_age));

        txtList.add((TextView) findViewById(R.id.workout_edit_weight));
        txtList.add((TextView) findViewById(R.id.workout_edit_weight_unit));

        txtList.add((TextView) findViewById(R.id.workout_edit_time));
        txtList.add((TextView) findViewById(R.id.workout_edit_time_unit));

        txtList.add(hrc60Name);
        txtList.add(hrc60Unit);

        txtList.add(hrc80Name);
        txtList.add(hrc80Unit);

        txtList.add(hrcTgName);
        txtList.add(hrcTgUnit);

        txtList.add(ageValue);
        txtList.add(weightValue);
        txtList.add(timeValue);

        txtList.add(hrc60Value);
        txtList.add(hrc80Value);
        txtList.add(hrcTgValue);

        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.Microsoft(activityContext));
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.Arial(activityContext));
        }
        if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
            txtList.get(5).setText(R.string.unit_kg);
        } else {
            txtList.get(5).setText(R.string.unit_lb);
        }
        txtList.clear();
        txtList = null;
    }

    @Override
    protected void init() {
        workOutInfo = getIntent().getParcelableExtra(Constant.WORK_OUT_INFO);
        infoType1.setVisibility(View.VISIBLE);
        infoType3.setVisibility(View.GONE);
        genderView.setOnGenderReturn(this);
        keyBoardView.setKeyBoardReturn(this);
        startBtn.setEnabled(false);

        ageValue.setText("20");
        if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
            weightValue.setText("70");
        } else {
            weightValue.setText("150");
        }
        timeValue.setText("20");

        hrc60Value.setText("120");
        hrc80Value.setText("160");
        hrcTgValue.setText("80");
        setHrcSelectState(hrc60Name, hrc60Value, hrc60Unit);
        selectHrcType = Constant.MODE_HRC_TYPE_TG;
    }


    @Override
    public void genderReturn(int gender) {
        workOutInfo.setGender(gender);
        startBtn.setEnabled(true);
    }

    @Override
    public void onKeyBoardEnter(String result) {
        if ("".equals(result)) {
            return;
        }
        switch (WorkOutSettingCommon.changeTg) {
            default:
                break;
            case WorkOutSettingCommon.CHANGE_AGE:
                ageValue.setText(result);
                hrc60Value.setText(calcHrcValue(result, WorkOutSettingCommon.CHANGE_HRC_60));
                hrc80Value.setText(calcHrcValue(result, WorkOutSettingCommon.CHANGE_HRC_80));
                break;
            case WorkOutSettingCommon.CHANGE_WEIGHT:
                weightValue.setText(result);
                break;
            case WorkOutSettingCommon.CHANGE_TIME:
                int re = Integer.valueOf(result);
                if (re < 5) {
                    timeValue.setText("0");
                } else {
                    timeValue.setText(result);
                }
                break;
            case WorkOutSettingCommon.CHANGE_TARGET_HR:
                hrcTgValue.setText(result);
                break;
        }
    }

    @Override
    public void onKeyBoardClose() {
        clearChangeState();
        WorkOutSettingCommon.changeTg = WorkOutSettingCommon.RE_SET;
        isShowingKeyBoard = false;
        keyBoardView.setVisibility(View.GONE);
        genderView.setVisibility(View.VISIBLE);
        startBtn.setEnabled(true);
        nextBtn.setEnabled(true);
    }

    private boolean isShowingKeyBoard = false;

    @OnClick({R.id.workout_edit_age_value, R.id.workout_edit_weight_value, R.id.workout_edit_time_value})
    public void changValue(View view) {
        clearChangeState();
        switch (view.getId()) {
            default:
                break;
            case R.id.workout_edit_age_value:
                setChangeState(ageValue, R.mipmap.tv_keybord_age, WorkOutSettingCommon.CHANGE_AGE);
                break;
            case R.id.workout_edit_weight_value:
                setChangeState(weightValue, R.mipmap.tv_keybord_weight, WorkOutSettingCommon.CHANGE_WEIGHT);
                break;
            case R.id.workout_edit_time_value:
                setChangeState(timeValue, R.mipmap.tv_keybord_time, WorkOutSettingCommon.CHANGE_TIME);
                break;
        }
        if (!isShowingKeyBoard) {
            keyBoardView.setVisibility(View.VISIBLE);
            genderView.setVisibility(View.GONE);
            isShowingKeyBoard = true;
            startBtn.setEnabled(false);
            nextBtn.setEnabled(false);
        }
    }

    /**
     * 切换到选中状态
     *
     * @param textView
     * @param keyBoardTitle
     * @param changeType
     */
    private void setChangeState(TextView textView, int keyBoardTitle, int changeType) {
        WorkOutSettingCommon.changeTg = changeType;
        keyBoardView.setTitleImage(keyBoardTitle);
        keyBoardView.setChangeType(WorkOutSettingCommon.changeTg);
        TextUtils.changeTextColor(textView, ContextCompat.getColor(activityContext, R.color.factory_tabs_on));
        TextUtils.changeTextBackground(textView, R.mipmap.img_number_frame_2);
    }

    /**
     * 恢复默认状态
     */
    private void clearChangeState() {
        TextUtils.changeTextColor(ageValue, ContextCompat.getColor(activityContext, R.color.factory_white));
        TextUtils.changeTextBackground(ageValue, R.mipmap.img_number_frame_1);

        TextUtils.changeTextColor(weightValue, ContextCompat.getColor(activityContext, R.color.factory_white));
        TextUtils.changeTextBackground(weightValue, R.mipmap.img_number_frame_1);

        TextUtils.changeTextColor(timeValue, ContextCompat.getColor(activityContext, R.color.factory_white));
        TextUtils.changeTextBackground(timeValue, R.mipmap.img_number_frame_1);

    }


    @OnClick({R.id.workout_edit_hrc60_value, R.id.workout_edit_hrc80_value, R.id.workout_edit_target_hr_value})
    public void hrcTypeSelect(View view) {
        if (isShowingKeyBoard) {
            return;
        }
        clearHrcSelectState();
        switch (view.getId()) {
            default:
                break;
            case R.id.workout_edit_hrc60_value:
                setHrcSelectState(hrc60Name, hrc60Value, hrc60Unit);
                selectHrcType = Constant.MODE_HRC_TYPE_60;
                break;
            case R.id.workout_edit_hrc80_value:
                setHrcSelectState(hrc80Name, hrc80Value, hrc80Unit);
                selectHrcType = Constant.MODE_HRC_TYPE_80;
                break;
            case R.id.workout_edit_target_hr_value:
                if (!isShowingKeyBoard) {
                    setHrcSelectState(hrcTgName, hrcTgValue, hrcTgUnit);
                    selectHrcType = Constant.MODE_HRC_TYPE_TG;
                    WorkOutSettingCommon.changeTg = WorkOutSettingCommon.CHANGE_TARGET_HR;
                    keyBoardView.setTitleImage(R.mipmap.tv_keybord_targethr);
                    keyBoardView.setChangeType(WorkOutSettingCommon.changeTg);
                    keyBoardView.setVisibility(View.VISIBLE);
                    genderView.setVisibility(View.GONE);
                    isShowingKeyBoard = true;
                    startBtn.setEnabled(false);
                    nextBtn.setEnabled(false);
                }
                break;
        }
    }


    /**
     * 切换到选中状态
     *
     * @param name
     * @param value
     * @param unit
     */
    private void setHrcSelectState(TextView name, TextView value, TextView unit) {
        TextUtils.changeTextColor(name, ContextCompat.getColor(activityContext, R.color.factory_tabs_on));
        TextUtils.changeTextColor(unit, ContextCompat.getColor(activityContext, R.color.factory_tabs_on));
        TextUtils.changeTextColor(value, ContextCompat.getColor(activityContext, R.color.factory_tabs_on));
        TextUtils.changeTextBackground(value, R.mipmap.img_number_frame_2);
    }


    /**
     * 恢复默认状态
     */
    private void clearHrcSelectState() {
        TextUtils.changeTextColor(hrc60Name, ContextCompat.getColor(activityContext, R.color.factory_white));
        TextUtils.changeTextColor(hrc60Unit, ContextCompat.getColor(activityContext, R.color.factory_white));
        TextUtils.changeTextColor(hrc60Value, ContextCompat.getColor(activityContext, R.color.factory_white));
        TextUtils.changeTextBackground(hrc60Value, R.mipmap.img_number_frame_1);

        TextUtils.changeTextColor(hrc80Name, ContextCompat.getColor(activityContext, R.color.factory_white));
        TextUtils.changeTextColor(hrc80Unit, ContextCompat.getColor(activityContext, R.color.factory_white));
        TextUtils.changeTextColor(hrc80Value, ContextCompat.getColor(activityContext, R.color.factory_white));
        TextUtils.changeTextBackground(hrc80Value, R.mipmap.img_number_frame_1);

        TextUtils.changeTextColor(hrcTgName, ContextCompat.getColor(activityContext, R.color.factory_white));
        TextUtils.changeTextColor(hrcTgUnit, ContextCompat.getColor(activityContext, R.color.factory_white));
        TextUtils.changeTextColor(hrcTgValue, ContextCompat.getColor(activityContext, R.color.factory_white));
        TextUtils.changeTextBackground(hrcTgValue, R.mipmap.img_number_frame_1);
    }


    private String calcHrcValue(String age, int type) {
        String result = "";
        int curAge = Integer.valueOf(age);
        float answer = 0f;
        switch (type) {
            case WorkOutSettingCommon.CHANGE_HRC_60:
                answer = (220 - curAge) * 0.6f;
                break;
            case WorkOutSettingCommon.CHANGE_HRC_80:
                answer = (220 - curAge) * 0.8f;
                break;
        }
        result = Math.round(answer) + "";
        return result;
    }


    @OnClick({R.id.workout_setting_next})
    public void onNextEdit() {
        if (isShowingKeyBoard) {
            return;
        }
        settingHint.setText(R.string.workout_mode_hint_c);

        infoType1.setVisibility(View.GONE);
        infoType3.setVisibility(View.VISIBLE);

        nextBtn.setVisibility(View.GONE);
        backBtn.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.workout_setting_back})
    public void onBackEdit() {
        if (isShowingKeyBoard) {
            return;
        }
        settingHint.setText(R.string.workout_mode_hint_b);

        infoType1.setVisibility(View.VISIBLE);
        infoType3.setVisibility(View.GONE);

        nextBtn.setVisibility(View.VISIBLE);
        backBtn.setVisibility(View.GONE);
    }


    @OnClick({R.id.workout_setting_start})
    public void beginWorkOut() {
        workOutInfo.setWorkOutMode(Constant.MODE_HRC);
        workOutInfo.setWorkOutModeName(Constant.WORK_OUT_MODE_HRC);

        workOutInfo.setAge(ageValue.getText().toString());
        workOutInfo.setWeight(weightValue.getText().toString());
        workOutInfo.setTime(timeValue.getText().toString());

        workOutInfo.setHrcType(selectHrcType);

        String hrc = "";
        switch (selectHrcType) {
            default:
                hrc = hrcTgValue.getText().toString();
                break;
            case Constant.MODE_HRC_TYPE_60:
                hrc = hrc60Value.getText().toString();
                break;
            case Constant.MODE_HRC_TYPE_80:
                hrc = hrc80Value.getText().toString();
                break;
            case Constant.MODE_HRC_TYPE_TG:
                hrc = hrcTgValue.getText().toString();
                break;
        }
        workOutInfo.setHrc(hrc);
        List<Level> array = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Level level = new Level();
            level.setLevel(1);
            array.add(level);
        }
        workOutInfo.setLevelList(array);

        Intent intent = new Intent();
        intent.setClass(HRCActivity.this, HRCRunningActivity.class);
        intent.putExtra(Constant.RUNNING_START_TYPE, Constant.RUNNING_START_TYPE_1);
        intent.putExtra(Constant.WORK_OUT_INFO, workOutInfo);
        startActivity(intent);

    }

    @OnClick(R.id.bottom_logo_tab_home)
    public void onBackHome() {
        finishActivity();
    }
}
