package com.sunrise.srs.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChuHui on 2017/10/11.
 */

public class FloatWindowHead extends ConstraintLayout {
    private int curLevel = 0;

    TextView levelValue, timeValue, distanceValue, caloriesValue, pulseValue, wattValue, speedValue;

    public FloatWindowHead(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatWindowHead(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.float_window_workout_running_head, this, true);

        levelValue = (TextView) this.findViewById(R.id.workout_running_head_level_value);
        timeValue = (TextView) this.findViewById(R.id.workout_running_head_time_value);
        distanceValue = (TextView) this.findViewById(R.id.workout_running_head_distance_value);
        caloriesValue = (TextView) this.findViewById(R.id.workout_running_head_calories_value);
        pulseValue = (TextView) this.findViewById(R.id.workout_running_head_pulse_value);
        wattValue = (TextView) this.findViewById(R.id.workout_running_head_watt_value);
        speedValue = (TextView) this.findViewById(R.id.workout_running_head_speed_value);
        setTextStyle();
    }


    private void setTextStyle() {
        List<TextView> txtList = new ArrayList<>();
        txtList.add((TextView) this.findViewById(R.id.workout_running_head_level));
        txtList.add(levelValue);

        txtList.add((TextView) this.findViewById(R.id.workout_running_head_time));
        txtList.add(timeValue);

        txtList.add((TextView) this.findViewById(R.id.workout_running_head_distance));
        txtList.add(distanceValue);
        txtList.add((TextView) this.findViewById(R.id.workout_running_head_distance_unit));

        txtList.add((TextView) this.findViewById(R.id.workout_running_head_calories));
        txtList.add(caloriesValue);

        txtList.add((TextView) this.findViewById(R.id.workout_running_head_pulse));
        txtList.add(pulseValue);

        txtList.add((TextView) this.findViewById(R.id.workout_running_head_watt));
        txtList.add(wattValue);

        txtList.add((TextView) this.findViewById(R.id.workout_running_head_speed));
        txtList.add(speedValue);
        txtList.add((TextView) this.findViewById(R.id.workout_running_head_speed_unit));

        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.Microsoft(getContext()));
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.Arial(getContext()));
        }
        if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
            txtList.get(6).setText(R.string.unit_km);
            txtList.get(15).setText(R.string.unit_kph);
        } else {
            txtList.get(6).setText(R.string.unit_mile);
            txtList.get(15).setText(R.string.unit_mph);
        }
        txtList.clear();
        txtList = null;
    }

    public void levelChange(int i) {
        curLevel += i;
        if (curLevel > Constant.MAX_LEVEL) {
            curLevel = Constant.MAX_LEVEL;
            return;
        }
        if (curLevel < Constant.MIN_LEVEL) {
            curLevel = Constant.MIN_LEVEL;
            return;
        }
        levelValue.setText(curLevel + "");
    }

    public int getLevel() {
        return curLevel;
    }

    public void setLevelValue(int i) {
        curLevel = i;
        levelValue.setText(curLevel + "");
    }

    public void setTimeValue(String time) {
        timeValue.setText(time);
    }

    public void setCaloriesValue(String calories) {
        caloriesValue.setText(calories);
    }

    public void setDistanceValue(String distance) {
        distanceValue.setText(distance);
    }

    public void setPulseValue(String pulse) {
        pulseValue.setText(pulse);
    }

    public void setWattValue(String watt) {
        wattValue.setText(watt);
    }

    public void setSpeedValue(String speed) {
        speedValue.setText(speed);
    }

    public void recycle() {
        levelValue = null;
        timeValue = null;
        distanceValue = null;
        caloriesValue = null;
        pulseValue = null;
        wattValue = null;
        speedValue = null;
    }
}
