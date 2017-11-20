package com.sunrise.srs.dialog.home;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseDialogFragment;
import com.sunrise.srs.interfaces.home.OnLanguageSelectResult;
import com.sunrise.srs.Constant;
import com.sunrise.srs.utils.ImageUtils;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.SharedPreferencesUtils;
import com.sunrise.srs.utils.TextUtils;

import java.util.List;

import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/6.
 * 校正对话框
 */

public class LanguageDialog extends BaseDialogFragment {
    private OnLanguageSelectResult dialogFragmentReturn;
    @BindViews({R.id.home_dialog_language_img_us, R.id.home_dialog_language_img_zh, R.id.home_dialog_language_img_de, R.id.home_dialog_language_img_tr, R.id.home_dialog_language_img_ir, R.id.home_dialog_language_img_es, R.id.home_dialog_language_img_pt, R.id.home_dialog_language_img_ru})
    List<ImageView> imageViews;

    @BindViews({R.id.home_dialog_language_txt_us, R.id.home_dialog_language_txt_zh, R.id.home_dialog_language_txt_de, R.id.home_dialog_language_txt_tr, R.id.home_dialog_language_txt_ir, R.id.home_dialog_language_txt_es, R.id.home_dialog_language_txt_pt, R.id.home_dialog_language_txt_ru})
    List<TextView> textViews;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_home_language;
    }

    @Override
    public void recycleObject() {
        dialogFragmentReturn = null;
        imageViews = null;
        textViews = null;
    }

    @Override
    protected void setTextStyle() {
        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(textViews, TextUtils.Microsoft(getContext()));
        } else {
            TextUtils.setTextTypeFace(textViews, TextUtils.Arial(getContext()));
        }
        TextUtils.changeTextColor(textViews, ContextCompat.getColor(getContext(), R.color.language_btn_on));
        ImageUtils.changeImageView(imageViews);
    }

    @Override
    public void init() {
        dialogFragmentReturn = (OnLanguageSelectResult) getActivity();

    }


    @OnClick({R.id.home_dialog_language_img_us, R.id.home_dialog_language_img_zh, R.id.home_dialog_language_img_de, R.id.home_dialog_language_img_tr, R.id.home_dialog_language_img_ir, R.id.home_dialog_language_img_es, R.id.home_dialog_language_img_pt, R.id.home_dialog_language_img_ru})
    void languageBtnClick(View btn) {
        boolean isChange = false;
        switch (btn.getId()) {
            default:
                break;
            case R.id.home_dialog_language_img_us:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.en_US)) {
                    isChange = true;
                    GlobalSetting.AppLanguage = LanguageUtils.en_US;
//                    LanguageUtils.updateLanguage(LanguageUtils.LOCALE_US, getResources());
                }
                break;
            case R.id.home_dialog_language_img_zh:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
                    isChange = true;
                    GlobalSetting.AppLanguage = LanguageUtils.zh_CN;
//                    LanguageUtils.updateLanguage(LanguageUtils.LOCALE_CN, getResources());
                }
                break;
            case R.id.home_dialog_language_img_de:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.de_DE)) {
                    isChange = true;
                    GlobalSetting.AppLanguage = LanguageUtils.de_DE;
//                    LanguageUtils.updateLanguage(LanguageUtils.LOCALE_DE, getResources());
                }
                break;
            case R.id.home_dialog_language_img_tr:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.tr_TR)) {
                    isChange = true;
                    GlobalSetting.AppLanguage = LanguageUtils.tr_TR;
//                    LanguageUtils.updateLanguage(LanguageUtils.LOCALE_TR, getResources());
                }
                break;
            case R.id.home_dialog_language_img_ir:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.ir_IR)) {
                    isChange = true;
                    GlobalSetting.AppLanguage = LanguageUtils.ir_IR;
//                    LanguageUtils.updateLanguage(LanguageUtils.LOCALE_IR, getResources());
                }
                break;
            case R.id.home_dialog_language_img_es:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.es_ES)) {
                    isChange = true;
                    GlobalSetting.AppLanguage = LanguageUtils.es_ES;
//                    LanguageUtils.updateLanguage(LanguageUtils.LOCALE_ESP, getResources());
                }
                break;
            case R.id.home_dialog_language_img_pt:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.pt_PT)) {
                    isChange = true;
                    GlobalSetting.AppLanguage = LanguageUtils.pt_PT;
//                    LanguageUtils.updateLanguage(LanguageUtils.LOCALE_PT, getResources());
                }
                break;
            case R.id.home_dialog_language_img_ru:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.ru_RU)) {
                    isChange = true;
                    GlobalSetting.AppLanguage = LanguageUtils.ru_RU;
//                    LanguageUtils.updateLanguage(LanguageUtils.LOCALE_RU, getResources());
                }
                break;
        }
        SharedPreferencesUtils.put(getContext(), Constant.APP_LANGUAGE, GlobalSetting.AppLanguage);
        dialogFragmentReturn.onLanguageResult(isChange);
        dismiss();

    }


}
