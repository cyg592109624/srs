package com.sunrise.srs.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.sunrise.srs.Constant;
import com.sunrise.srs.R;
import com.sunrise.srs.interfaces.workout.setting.OnGenderReturn;
import com.sunrise.srs.utils.ImageUtils;

/**
 * Created by ChuHui on 2017/9/20.
 */

public class GenderView extends ConstraintLayout {
    private RadioButton male, female;
    private OnGenderReturn onGenderReturn;
    private ImageView genderImg;
    private ConstraintLayout bg;

    private int hasClick = 0;

    public GenderView(Context context) {
        this(context, null);
    }

    public GenderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GenderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.workout_gender, this, true);

        bg = findViewById(R.id.gender_bg);
        genderImg = findViewById(R.id.gender_img);

        male = findViewById(R.id.gender_male);
        female = findViewById(R.id.gender_female);


        male.setOnClickListener(radioClick);
        female.setOnClickListener(radioClick);
    }

    public void setOnGenderReturn(OnGenderReturn genderReturn) {
        onGenderReturn = genderReturn;
    }

    public void recycle() {
        male = null;
        female = null;
        onGenderReturn = null;
        genderImg = null;
        bg = null;
    }

    private View.OnClickListener radioClick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                default:
                    break;
                case R.id.gender_male:
                    male.setChecked(true);
                    female.setChecked(false);
                    onGenderReturn.genderReturn(Constant.GENDER_MALE);
                    ImageUtils.changeImageView(genderImg, R.mipmap.img_gender_draw_1);
                    if (hasClick == 0) {
                        hasClick = 1;
                    }
                    break;
                case R.id.gender_female:
                    male.setChecked(false);
                    female.setChecked(true);
                    onGenderReturn.genderReturn(Constant.GENDER_FEMALE);
                    ImageUtils.changeImageView(genderImg, R.mipmap.img_gender_draw_2);
                    if (hasClick == 0) {
                        hasClick = 1;
                    }
                    break;
            }
            if (hasClick == 1) {
                hasClick = -1;
                bg.setBackgroundResource(R.mipmap.img_gender_frame_2);
            }
        }
    };
}
