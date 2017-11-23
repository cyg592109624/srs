package com.sunrise.srs.activity.workout.setting;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.activity.workout.WorkOutSettingCommon;
import com.sunrise.srs.activity.workout.running.FitnessTestRunningActivity;
import com.sunrise.srs.base.BaseActivity;
import com.sunrise.srs.bean.Level;
import com.sunrise.srs.interfaces.workout.setting.OnGenderReturn;
import com.sunrise.srs.interfaces.workout.setting.OnKeyBoardReturn;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.TextUtils;
import com.sunrise.srs.views.LevelView;
import com.sunrise.srs.views.GenderView;
import com.sunrise.srs.views.NumberKeyBoardView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/22.
 */

public class FitnessTestActivity extends BaseActivity implements OnGenderReturn, OnKeyBoardReturn {

    @BindView(R.id.workout_mode_gender_select)
    GenderView genderView;

    @BindView(R.id.workout_mode_keyboard)
    NumberKeyBoardView keyBoardView;

    @BindView(R.id.workout_edit_age_value)
    TextView ageValue;

    @BindView(R.id.workout_edit_weight_value)
    TextView weightValue;

    @BindView(R.id.workout_setting_start)
    ImageView startBtn;

    @Override
    public int getLayoutId() {
        return R.layout.activity_workout_setting_fitness_test;
    }

    @Override
    public void recycleObject() {

        genderView.recycle();
        genderView = null;

        keyBoardView.recycle();
        keyBoardView = null;

        ageValue = null;
        weightValue = null;
        startBtn = null;
    }

    @Override
    protected void setTextStyle() {
        List<TextView> txtList = new ArrayList<TextView>();

        txtList.add((TextView) findViewById(R.id.workout_setting_head_name));
        txtList.add((TextView) findViewById(R.id.workout_setting_head_hint));
        txtList.add((TextView) findViewById(R.id.workout_setting_hint));

        txtList.add((TextView) findViewById(R.id.workout_edit_age));

        txtList.add((TextView) findViewById(R.id.workout_edit_weight));
        txtList.add((TextView) findViewById(R.id.workout_edit_weight_unit));

        txtList.add(ageValue);
        txtList.add(weightValue);
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
        genderView.setOnGenderReturn(FitnessTestActivity.this);
        keyBoardView.setKeyBoardReturn(FitnessTestActivity.this);
        startBtn.setEnabled(false);
        ageValue.setText("20");
        if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
            weightValue.setText("70");
        } else {
            weightValue.setText("150");
        }
    }


    @Override
    public void genderReturn(int gender) {
        workOutInfo.setGender(gender);
        startBtn.setEnabled(true);
    }

    @Override
    public void onKeyBoardEnter(String result) {
        switch (WorkOutSettingCommon.changeTg) {
            default:
                break;
            case WorkOutSettingCommon.CHANGE_AGE:
                ageValue.setText(result);
                break;
            case WorkOutSettingCommon.CHANGE_WEIGHT:
                weightValue.setText(result);
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
    }

    private boolean isShowingKeyBoard = false;

    @OnClick({R.id.workout_edit_age_value, R.id.workout_edit_weight_value})
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
        }
        if (!isShowingKeyBoard) {
            keyBoardView.setVisibility(View.VISIBLE);
            genderView.setVisibility(View.GONE);
            isShowingKeyBoard = true;
            startBtn.setEnabled(false);
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
    }

    @OnClick({R.id.workout_setting_start})
    public void beginWorkOut() {
        workOutInfo.setWorkOutMode(Constant.MODE_FITNESS_TEST);
        workOutInfo.setWorkOutModeName(Constant.WORK_OUT_MODE_FITNESS_TEST);

        workOutInfo.setAge(ageValue.getText().toString());
        workOutInfo.setWeight(weightValue.getText().toString());
        workOutInfo.setTime("0");

        float hrc = (220 - Integer.valueOf(ageValue.getText().toString())) * 0.85f;
        workOutInfo.setHrc(Math.round(hrc) + "");

        List<Level> array = new ArrayList<>();
        for (int i = 0; i < Constant.LEVEL_TIME_AVG; i++) {
            Level level = new Level();
            level.setLevel(1);
            array.add(level);
        }
        workOutInfo.setLevelList(array);
        Intent intent = new Intent();
        intent.setClass(activityContext, FitnessTestRunningActivity.class);
        intent.putExtra(Constant.RUNNING_START_TYPE, Constant.RUNNING_START_TYPE_1);
        intent.putExtra(Constant.WORK_OUT_INFO, workOutInfo);
        startActivity(intent);

    }

    @OnClick(R.id.bottom_logo_tab_home)
    public void onBackHome() {
        finishActivity();
    }

}
