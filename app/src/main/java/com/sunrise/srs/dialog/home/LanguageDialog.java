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
import java.util.Locale;

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
        Locale locale=null;
        String appLanguage="";
        switch (btn.getId()) {
            default:
                break;
            case R.id.home_dialog_language_img_us:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.en_US)) {
                    isChange = true;
                    appLanguage = LanguageUtils.en_US;
                    locale=LanguageUtils.LOCALE_US;
                }
                break;
            case R.id.home_dialog_language_img_zh:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
                    isChange = true;
                    appLanguage= LanguageUtils.zh_CN;
                    locale=LanguageUtils.LOCALE_CN;
                }
                break;
            case R.id.home_dialog_language_img_de:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.de_DE)) {
                    isChange = true;
                    appLanguage = LanguageUtils.de_DE;
                    locale=LanguageUtils.LOCALE_DE;
                }
                break;
            case R.id.home_dialog_language_img_tr:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.tr_TR)) {
                    isChange = true;
                    appLanguage = LanguageUtils.tr_TR;
                    locale=LanguageUtils.LOCALE_TR;
                }
                break;
            case R.id.home_dialog_language_img_ir:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.ir_IR)) {
                    isChange = true;
                    appLanguage = LanguageUtils.ir_IR;
                    locale=LanguageUtils.LOCALE_IR;
                }
                break;
            case R.id.home_dialog_language_img_es:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.es_ES)) {
                    isChange = true;
                    appLanguage = LanguageUtils.es_ES;
                    locale=LanguageUtils.LOCALE_ESP;
                }
                break;
            case R.id.home_dialog_language_img_pt:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.pt_PT)) {
                    isChange = true;
                    appLanguage = LanguageUtils.pt_PT;
                    locale=LanguageUtils.LOCALE_PT;
                }
                break;
            case R.id.home_dialog_language_img_ru:
                if (!GlobalSetting.AppLanguage.equals(LanguageUtils.ru_RU)) {
                    isChange = true;
                    appLanguage = LanguageUtils.ru_RU;
                    locale=LanguageUtils.LOCALE_RU;
                }
                break;
        }
        if(isChange){
            GlobalSetting.AppLanguage = appLanguage;
            SharedPreferencesUtils.put(getContext(), Constant.APP_LANGUAGE, GlobalSetting.AppLanguage);
            LanguageUtils.updateLanguage(locale, getResources());
            dialogFragmentReturn.onLanguageResult(isChange);
        }
        dismiss();

    }


}
