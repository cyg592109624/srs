package com.sunrise.srs.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.activity.workout.WorkOutSettingCommon;
import com.sunrise.srs.interfaces.workout.setting.OnKeyBoardReturn;
import com.sunrise.srs.utils.ImageUtils;

/**
 * Created by ChuHui on 2017/9/20.
 */

public class NumberKeyBoardView extends LinearLayout {
    private ImageView title;
    private TextView showText;
    private OnKeyBoardReturn keyBoardReturn;
    private int changeType = WorkOutSettingCommon.RE_SET;
    private final int EDIT_MAX = 10;

    private int changeTypeMax;
    private int changeTypeMin;

    public NumberKeyBoardView(Context context) {
        this(context, null);
    }

    public NumberKeyBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberKeyBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.key_board, this, true);

        title = (ImageView) findViewById(R.id.key_board_title);
        showText = (TextView) findViewById(R.id.key_board_edit_value);

        findViewById(R.id.key_board_0).setOnClickListener(keyBoardClick);
        findViewById(R.id.key_board_1).setOnClickListener(keyBoardClick);
        findViewById(R.id.key_board_2).setOnClickListener(keyBoardClick);
        findViewById(R.id.key_board_3).setOnClickListener(keyBoardClick);
        findViewById(R.id.key_board_4).setOnClickListener(keyBoardClick);
        findViewById(R.id.key_board_5).setOnClickListener(keyBoardClick);
        findViewById(R.id.key_board_6).setOnClickListener(keyBoardClick);
        findViewById(R.id.key_board_7).setOnClickListener(keyBoardClick);
        findViewById(R.id.key_board_8).setOnClickListener(keyBoardClick);
        findViewById(R.id.key_board_9).setOnClickListener(keyBoardClick);

        findViewById(R.id.key_board_del).setOnClickListener(keyBoardClick2);
        findViewById(R.id.key_board_enter).setOnClickListener(keyBoardClick2);
        findViewById(R.id.key_board_close).setOnClickListener(keyBoardClick2);
    }

    private View.OnClickListener keyBoardClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String oldText = showText.getText().toString();
            if (oldText.length() >= EDIT_MAX) {
                return;
            }
            switch (view.getId()) {
                case R.id.key_board_0:
                    oldText = oldText + "0";
                    break;
                case R.id.key_board_1:
                    oldText = oldText + "1";
                    break;
                case R.id.key_board_2:
                    oldText = oldText + "2";
                    break;
                case R.id.key_board_3:
                    oldText = oldText + "3";
                    break;
                case R.id.key_board_4:
                    oldText = oldText + "4";
                    break;
                case R.id.key_board_5:
                    oldText = oldText + "5";
                    break;
                case R.id.key_board_6:
                    oldText = oldText + "6";
                    break;
                case R.id.key_board_7:
                    oldText = oldText + "7";
                    break;
                case R.id.key_board_8:
                    oldText = oldText + "8";
                    break;
                case R.id.key_board_9:
                    oldText = oldText + "9";
                    break;
            }
            showText.setText(oldText);
        }
    };

    private View.OnClickListener keyBoardClick2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String value = showText.getText().toString();
            switch (view.getId()) {
                default:
                    break;
                case R.id.key_board_enter:
                    if (keyBoardReturn != null) {
                        showText.setText("");
                        keyBoardReturn.onKeyBoardEnter(reCalc(value));
                        keyBoardReturn.onKeyBoardClose();
                    }
                    break;
                case R.id.key_board_close:
                    if (keyBoardReturn != null) {
                        showText.setText("");
                        keyBoardReturn.onKeyBoardClose();
                    }
                    break;
                case R.id.key_board_del:
                    if (value.length() != 0) {
                        showText.setText(value.subSequence(0, value.length() - 1));
                    }
                    break;
            }
        }
    };

    private String reCalc(String editText) {
        if (changeType != WorkOutSettingCommon.RE_SET) {
            String result = "";
            if(!result.equals(editText)){
                long value = Long.valueOf(editText);
                if (value <= changeTypeMin) {
                    result = changeTypeMin + "";
                } else if (value >= changeTypeMax) {
                    result = changeTypeMax + "";
                } else {
                    result = editText;
                }
                return result;
            }
            return result;
        }
        return editText;
    }


    public void setTitleImage(int imgResource) {
        ImageUtils.changeImageView(title, imgResource);
    }

    public void setKeyBoardReturn(@NonNull OnKeyBoardReturn onKeyBoardReturn) {
        keyBoardReturn = onKeyBoardReturn;
    }

    public void setChangeType(int type) {
        changeType = type;
        switch (changeType) {
            default:
                break;
            case WorkOutSettingCommon.CHANGE_AGE:
                changeTypeMin = WorkOutSettingCommon.MIN_AGE;
                changeTypeMax = WorkOutSettingCommon.MAX_AGE;
                break;
            case WorkOutSettingCommon.CHANGE_WEIGHT:
                if (GlobalSetting.UnitType.equals(Constant.UNIT_TYPE_METRIC)) {
                    changeTypeMin = WorkOutSettingCommon.MIN_WEIGHT_KG;
                    changeTypeMax = WorkOutSettingCommon.MAX_WEIGHT_KG;
                } else {
                    changeTypeMin = WorkOutSettingCommon.MIN_WEIGHT_LB;
                    changeTypeMax = WorkOutSettingCommon.MAX_WEIGHT_LB;
                }
                break;
            case WorkOutSettingCommon.CHANGE_TIME:
                changeTypeMin = WorkOutSettingCommon.MIN_TIME;
                changeTypeMax = WorkOutSettingCommon.MAX_TIME;
                break;
            case WorkOutSettingCommon.CHANGE_DISTANCE:
                changeTypeMin = WorkOutSettingCommon.MIN_DISTANCE;
                changeTypeMax = WorkOutSettingCommon.MAX_DISTANCE;
                break;
            case WorkOutSettingCommon.CHANGE_CALORIES:
                changeTypeMin = WorkOutSettingCommon.MIN_CALORIES;
                changeTypeMax = WorkOutSettingCommon.MAX_CALORIES;
                break;
            case WorkOutSettingCommon.CHANGE_TARGET_HR:
                changeTypeMin = WorkOutSettingCommon.MIN_TARGET_HR;
                changeTypeMax = WorkOutSettingCommon.MAX_TARGET_HR;
                break;
        }
    }

    public void recycle() {
        title = null;
        showText = null;
        keyBoardReturn = null;
        keyBoardClick = null;
    }


}
