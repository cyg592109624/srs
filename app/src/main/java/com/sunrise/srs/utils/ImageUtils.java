package com.sunrise.srs.utils;

import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;

import java.util.List;

/**
 * Created by ChuHui on 2017/9/11.
 */

public class ImageUtils {


    public static void changeImageView(ImageView imageView, int imgResource) {
        imageView.setImageResource(imgResource);
    }

    public static void changeImageView(List<ImageView> imgViews) {
        if (GlobalSetting.AppLanguage.equals(LanguageUtils.de_DE)) {
            changeImageView(imgViews.get(2), R.mipmap.btn_home_language_de_4);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.en_US)) {
            changeImageView(imgViews.get(0), R.mipmap.btn_home_language_us_4);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.es_ES)) {
            changeImageView(imgViews.get(5), R.mipmap.btn_home_language_esp_4);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.ir_IR)) {
            changeImageView(imgViews.get(4), R.mipmap.btn_home_language_ir_4);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.pt_PT)) {
            changeImageView(imgViews.get(6), R.mipmap.btn_home_language_pt_4);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.ru_RU)) {
            changeImageView(imgViews.get(7), R.mipmap.btn_home_language_rus_4);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.tr_TR)) {
            changeImageView(imgViews.get(3), R.mipmap.btn_home_language_tr_4);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            changeImageView(imgViews.get(1), R.mipmap.btn_home_language_cn_4);
        }
    }

    public static void changeImageView(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
