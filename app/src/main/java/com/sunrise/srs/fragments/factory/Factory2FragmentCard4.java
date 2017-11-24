package com.sunrise.srs.fragments.factory;

import android.widget.ImageView;
import android.widget.TextView;

import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseFragment;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.TextUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/14.
 */

public class Factory2FragmentCard4 extends BaseFragment {
    @BindView(R.id.factory2_card4_update_logo)
    ImageView upDataLogo;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_factory2_card_4;
    }

    @Override
    public void recycleObject() {
        upDataLogo = null;
    }

    @Override
    protected void setTextStyle() {
        TextView hint = (TextView) parentView.findViewById(R.id.factory2_card4_hint);
        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(hint, TextUtils.Microsoft());
        } else {
            TextUtils.setTextTypeFace(hint, TextUtils.Arial());
        }
        hint = null;
    }

    @Override
    protected void init() {
        upDataLogo.setEnabled(false);
    }

    @OnClick(R.id.factory2_card4_update_logo)
    public void upDataLogo() {

    }
}
