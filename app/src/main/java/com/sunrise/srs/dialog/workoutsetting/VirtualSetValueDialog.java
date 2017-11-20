package com.sunrise.srs.dialog.workoutsetting;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.activity.workout.setting.VirtualRealityActivity;
import com.sunrise.srs.base.BaseDialogFragment;
import com.sunrise.srs.interfaces.workout.setting.OnKeyBoardReturn;
import com.sunrise.srs.interfaces.workout.setting.OnVrDialogClick;
import com.sunrise.srs.utils.ImageUtils;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.TextUtils;
import com.sunrise.srs.views.NumberKeyBoardView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/27.
 */

public class VirtualSetValueDialog extends BaseDialogFragment implements OnKeyBoardReturn {

    @BindView(R.id.dialog_workout_vr_img)
    ImageView vrImage;

    @BindView(R.id.dialog_workout_vr_keyboard)
    NumberKeyBoardView keyBoardView;

    @BindView(R.id.dialog_workout_vr_edit_time)
    TextView editValue;


    @BindView(R.id.workout_setting_start)
    ImageView startBtn;

    @BindView(R.id.workout_setting_back)
    ImageView backBtn;

    OnVrDialogClick vrDialogClick;

    private int vrNum;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.Dialog_No_BG);
        return dialog;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_workout_setting_vr;
    }

    @Override
    public void recycleObject() {
        vrNum=-1;

        vrImage = null;
        editValue = null;

        keyBoardView.recycle();
        keyBoardView = null;

        vrDialogClick=null;
        startBtn = null;
        backBtn = null;

    }

    @Override
    protected void init() {
        vrDialogClick = (VirtualRealityActivity) getActivity();

        keyBoardView.setKeyBoardReturn(this);
        keyBoardView.setTitleImage(R.mipmap.tv_keybord_time);

        Bundle bundle = getArguments();
        vrNum = bundle.getInt(VirtualRealityActivity.SELECT_VR_NUM, VirtualRealityActivity.SELECT_NOTHING);
        int imgID = bundle.getInt(VirtualRealityActivity.SELECT_VR_IMG, VirtualRealityActivity.SELECT_NOTHING);
        ImageUtils.changeImageView(vrImage, imgID);
    }

    @Override
    protected void setTextStyle() {
        List<TextView> txtList = new ArrayList<TextView>();
        txtList.add((TextView) parentView.findViewById(R.id.workout_setting_hint));
        txtList.add(editValue);
        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.Microsoft(getContext()));
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.Arial(getContext()));
        }
        txtList.clear();
        txtList = null;
    }


    @Override
    public void onKeyBoardEnter(String result) {
        editValue.setText(result);
    }

    @Override
    public void onKeyBoardClose() {
        startBtn.setEnabled(true);
        backBtn.setEnabled(true);
        vrImage.setVisibility(View.VISIBLE);
        keyBoardView.setVisibility(View.GONE);
        TextUtils.changeTextColor(editValue, ContextCompat.getColor(getContext(), R.color.workout_mode_white));
        TextUtils.changeTextBackground(editValue, R.mipmap.btn_virtual_time_1);
    }

    @OnClick(R.id.dialog_workout_vr_edit_time)
    public void changeEditValue() {
        startBtn.setEnabled(false);
        backBtn.setEnabled(false);
        vrImage.setVisibility(View.GONE);
        keyBoardView.setVisibility(View.VISIBLE);

        TextUtils.changeTextColor(editValue, ContextCompat.getColor(getContext(), R.color.workout_mode_select));
        TextUtils.changeTextBackground(editValue, R.mipmap.btn_virtual_time_2);
    }


    @OnClick({R.id.workout_setting_start})
    public void beginWorkOut() {
        vrDialogClick.onStartClick(vrNum,editValue.getText().toString());
    }

    @OnClick(R.id.workout_setting_back)
    public void back() {
        vrDialogClick.onBackClick();
        dismiss();
    }
}
