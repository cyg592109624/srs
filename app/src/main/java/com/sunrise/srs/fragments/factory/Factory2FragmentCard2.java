package com.sunrise.srs.fragments.factory;

import android.widget.ImageView;
import android.widget.TextView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseFragment;
import com.sunrise.srs.dialog.factory.Factory2FragmentCard2Dialog;
import com.sunrise.srs.interfaces.factory.Card2DialogResult;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.PackageUtils;
import com.sunrise.srs.utils.SharedPreferencesUtils;
import com.sunrise.srs.utils.TextUtils;
import com.sunrise.srs.utils.UnitUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/14.
 */

public class Factory2FragmentCard2 extends BaseFragment {
    @BindView(R.id.factory2_card2_1_reset)
    ImageView reSet;

    @BindView(R.id.factory2_card2_1_total_time_value_h)
    TextView totalTimeHour;

    @BindView(R.id.factory2_card2_1_total_time_value_min)
    TextView totalTimeMin;

    @BindView(R.id.factory2_card2_1_total_distance_value)
    TextView totalDistance;

    @BindView(R.id.factory2_card2_2_ver_soft_value)
    TextView appVersionName;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_factory2_card_2;
    }

    @Override
    public void recycleObject() {
        reSet = null;
        totalTimeHour = null;
        totalTimeMin = null;
        totalDistance = null;

        appVersionName = null;
    }

    @Override
    protected void setTextStyle() {
        List<TextView> txtList = new ArrayList<TextView>();

        txtList.add((TextView) parentView.findViewById(R.id.factory2_card2_1_total_time));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card2_1_total_time_value_unit_h));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card2_1_total_time_value_unit_min));
        txtList.add(totalTimeHour);
        txtList.add(totalTimeMin);


        txtList.add((TextView) parentView.findViewById(R.id.factory2_card2_1_total_distance));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card2_1_total_distance_unit));
        txtList.add(totalDistance);

        txtList.add((TextView) parentView.findViewById(R.id.factory2_card2_2_ver_sdk));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card2_2_ver_sdk_value));

        txtList.add((TextView) parentView.findViewById(R.id.factory2_card2_2_ver_firmware));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card2_2_ver_firmware_value));

        txtList.add((TextView) parentView.findViewById(R.id.factory2_card2_2_ver_soft));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card2_2_ver_soft_value));

        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.MicrosoftBold());
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.ArialBold());
        }
        if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
            txtList.get(6).setText(R.string.unit_km);
        } else {
            txtList.get(6).setText(R.string.unit_mile);
        }

        txtList.clear();
        txtList = null;
    }

    @Override
    protected void init() {
        int hour = (Integer.valueOf(GlobalSetting.Factory2_TotalTime)) / 60;
        int minute = (Integer.valueOf(GlobalSetting.Factory2_TotalTime)) % 60;
        totalTimeHour.setText("" + hour);
        totalTimeMin.setText("" + minute);
        if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
            totalDistance.setText(GlobalSetting.Factory2_TotalDistant);
        } else {
            totalDistance.setText(UnitUtils.km2mile(GlobalSetting.Factory2_TotalDistant) + "");
        }
        appVersionName.setText(PackageUtils.getVersionName(getContext()));
    }

    @OnClick(R.id.factory2_card2_1_reset)
    public void reSetValue() {
        Factory2FragmentCard2Dialog dialog = new Factory2FragmentCard2Dialog();
        dialog.setDialogReturn(new Card2DialogResult() {
            @Override
            public void onYes() {
                GlobalSetting.Factory2_TotalTime = "0";
                totalTimeHour.setText(GlobalSetting.Factory2_TotalTime);
                totalTimeMin.setText(GlobalSetting.Factory2_TotalTime);

                GlobalSetting.Factory2_TotalDistant = "0";
                totalDistance.setText(GlobalSetting.Factory2_TotalDistant);
                SharedPreferencesUtils.put(getContext(), Constant.FACTORY2_TOTAL_TIME, GlobalSetting.Factory2_TotalTime);
                SharedPreferencesUtils.put(getContext(), Constant.FACTORY2_TOTAL_DISTANT, GlobalSetting.Factory2_TotalDistant);
            }

            @Override
            public void onNo() {

            }
        });
        dialog.show(fragmentManager, Constant.TAG);
    }
}
