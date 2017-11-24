package com.sunrise.srs.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.TextView;

import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ChuHui on 2017/9/11.
 */

public class TextUtils {
    private static AssetManager ass;
    private static Typeface Arial,ArialBold,ArialBlack,Microsoft,MicrosoftBold;

    public static void getInstance(Context context){
        if (Arial == null) {
            ass = context.getAssets();
            Arial = Typeface.createFromAsset(ass, "fonts/srs_arial_0.ttf");
            ArialBold = Typeface.createFromAsset(ass, "fonts/srs_arial_0.ttf");
            ArialBlack = Typeface.createFromAsset(ass, "fonts/srs_ariblk_0.ttf");
            Microsoft = Typeface.createFromAsset(ass, "fonts/srs_msjh.ttf");
            MicrosoftBold = Typeface.createFromAsset(ass, "fonts/srs_msjhbd.ttf");
        }
    }

    private TextUtils() {
    }

    public static Typeface Arial() {
        return Arial;
    }

    public static Typeface ArialBold() {
        return ArialBold;
    }

    public static Typeface ArialBlack() {
        return ArialBlack;
    }

    public static Typeface Microsoft() {
        return Microsoft;
    }

    public static Typeface MicrosoftBold() {
        return MicrosoftBold;
    }


    public static void setTextSize(TextView textView, float size) {
        textView.setTextSize(size);
    }

    public static void setTextSize(List<TextView> textViews, float size) {
        for (TextView t : textViews) {
            setTextSize(t, size);
        }
    }

    public static void setTextTypeFace(TextView textView, Typeface typeface) {
        textView.setTypeface(typeface);
    }

    public static void setTextTypeFace(List<TextView> textViews, Typeface typeface) {
        for (TextView t : textViews) {
            setTextTypeFace(t, typeface);
        }
    }

    public static void changeTextColor(TextView textView, int color) {
        textView.setTextColor(color);
    }

    public static void changeTextColor(List<TextView> textViews, int color) {
        if (GlobalSetting.AppLanguage.equals(LanguageUtils.de_DE)) {
            changeTextColor(textViews.get(2), color);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.en_US)) {
            changeTextColor(textViews.get(0), color);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.es_ES)) {
            changeTextColor(textViews.get(5), color);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.ir_IR)) {
            changeTextColor(textViews.get(4), color);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.pt_PT)) {
            changeTextColor(textViews.get(6), color);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.ru_RU)) {
            changeTextColor(textViews.get(7), color);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.tr_TR)) {
            changeTextColor(textViews.get(3), color);
        } else if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            changeTextColor(textViews.get(1), color);
        }
    }

    public static void changeTextSize(TextView textView, float size) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
    }

    public static void changeTextSize(List<TextView> textViews, float size) {
        for (TextView t : textViews) {
            changeTextSize(t, size);
        }
    }

    public static void changeTextBackground(TextView textView, int bgId) {
        textView.setBackgroundResource(bgId);
    }

    public static void changeTextBackground(List<TextView> textViews, int bgId) {
        for (TextView t : textViews) {
            changeTextBackground(t, bgId);
        }
    }

}
