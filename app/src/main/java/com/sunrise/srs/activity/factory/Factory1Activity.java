package com.sunrise.srs.activity.factory;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseActivity;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.SharedPreferencesUtils;
import com.sunrise.srs.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/13.
 */

public class Factory1Activity extends BaseActivity {

    @BindView(R.id.factory1_unit_type_metrial)
    RadioButton unitType_metrial;
    @BindView(R.id.factory1_unit_type_imperial)
    RadioButton unitType_imperial;

    @BindView(R.id.factory1_machine_type_bike)
    RadioButton machineType_bike;
    @BindView(R.id.factory1_machine_type_recumbent)
    RadioButton machineType_recumbent;
    @BindView(R.id.factory1_machine_type_elliptical)
    RadioButton machineType_elliptical;

    @Override
    public int getLayoutId() {
        return R.layout.activity_factory1;
    }

    @Override
    public void recycleObject() {

    }

    @Override
    protected void init() {
        if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
            unitType_metrial.setChecked(true);
        } else {
            unitType_imperial.setChecked(true);
        }

        if (GlobalSetting.MachineType.equals(Constant.MACHINE_BIKE)) {
            machineType_bike.setChecked(true);
        } else if (GlobalSetting.MachineType.equals(Constant.MACHINE_RECUMBENT)) {
            machineType_recumbent.setChecked(true);
        } else {
            machineType_elliptical.setChecked(true);
        }
        unitType_metrial.setOnClickListener(radioClick);
        unitType_imperial.setOnClickListener(radioClick);

        machineType_bike.setOnClickListener(radioClick);
        machineType_recumbent.setOnClickListener(radioClick);
        machineType_elliptical.setOnClickListener(radioClick);
    }

    @Override
    protected void setTextStyle() {
        List<TextView> txtList = new ArrayList<TextView>();
        txtList.add((TextView) findViewById(R.id.factory_1_title));
        txtList.add((TextView) findViewById(R.id.factory_1_card_setting));
        txtList.add((TextView) findViewById(R.id.factory1_unit));
        txtList.add((TextView) findViewById(R.id.factory1_type));

        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.MicrosoftBold(this));
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.ArialBold(this));
        }
        txtList.clear();
        txtList = null;
    }

    private View.OnClickListener radioClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                default:
                    break;
                case R.id.factory1_unit_type_metrial:
                    unitType_imperial.setChecked(false);
                    unitType_metrial.setChecked(true);
                    GlobalSetting.UnitType = Constant.UNIT_TYPE_METRIC;
                    break;
                case R.id.factory1_unit_type_imperial:
                    unitType_imperial.setChecked(true);
                    unitType_metrial.setChecked(false);
                    GlobalSetting.UnitType = Constant.UNIT_TYPE_IMPERIAL;
                    break;
                case R.id.factory1_machine_type_bike:
                    machineType_bike.setChecked(true);
                    machineType_recumbent.setChecked(false);
                    machineType_elliptical.setChecked(false);
                    GlobalSetting.MachineType = Constant.MACHINE_BIKE;
                    break;
                case R.id.factory1_machine_type_recumbent:
                    machineType_bike.setChecked(false);
                    machineType_recumbent.setChecked(true);
                    machineType_elliptical.setChecked(false);
                    GlobalSetting.MachineType = Constant.MACHINE_RECUMBENT;
                    break;
                case R.id.factory1_machine_type_elliptical:
                    machineType_bike.setChecked(false);
                    machineType_recumbent.setChecked(false);
                    machineType_elliptical.setChecked(true);
                    GlobalSetting.MachineType = Constant.MACHINE_ElSTRING;
                    break;
            }
            SharedPreferencesUtils.put(activityContext, Constant.UNIT_TYPE, GlobalSetting.UnitType);
            SharedPreferencesUtils.put(activityContext, Constant.MACHINE_TYPE, GlobalSetting.MachineType);
        }
    };


    @OnClick(R.id.bottom_logo_tab_home)
    public void onBackHome() {
        finishActivity();
    }
}
