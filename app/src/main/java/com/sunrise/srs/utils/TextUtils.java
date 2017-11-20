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

    private TextUtils() {
    }

    public static Typeface Arial(Context context) {
        if (ass == null) {
            ass = context.getAssets();
        }
        Typeface tf = Typeface.createFromAsset(ass, "fonts/srs_arial_0.ttf");
        return tf;
    }

    public static Typeface ArialBold(Context context) {
        if (ass == null) {
            ass = context.getAssets();
        }
        Typeface tf = Typeface.createFromAsset(ass, "fonts/srs_arialbd_0.ttf");
        return tf;
    }

    public static Typeface ArialBlack(Context context) {
        if (ass == null) {
            ass = context.getAssets();
        }
        Typeface tf = Typeface.createFromAsset(ass, "fonts/srs_ariblk_0.ttf");
        return tf;
    }

    public static Typeface Microsoft(Context context) {
        if (ass == null) {
            ass = context.getAssets();
        }
        Typeface tf = Typeface.createFromAsset(ass, "fonts/srs_msjh.ttf");
        return tf;
    }

    public static Typeface MicrosoftBold(Context context) {
        if (ass == null) {
            ass = context.getAssets();
        }
        Typeface tf = Typeface.createFromAsset(ass, "fonts/srs_msjhbd.ttf");
        return tf;
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
