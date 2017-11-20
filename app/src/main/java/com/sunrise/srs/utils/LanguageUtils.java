package com.sunrise.srs.utils;

import android.app.backup.BackupManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by ChuHui on 2017/9/4.
 */

public class LanguageUtils {
    /**
     * 伊朗 (阿拉伯联合酋长国)ar_AE
     */
    public static final String IR = "ar";
    public static final String ir_IR = "ar_AE";
    public static final Locale LOCALE_IR = new Locale("ar", "AE");

    /**
     * 德文 (德国)de_DE
     */
    public static final String de = "de";
    public static final String de_DE = "de_DE";
    public static final Locale LOCALE_DE = new Locale("de", "DE");

    /**
     * 英语(美国)en_US
     */
    public static final String en = "en";
    public static final String en_US = "en_US";
    public static final Locale LOCALE_US = new Locale("en", "US");

    /**
     * 西班牙文 (西班牙)es_ES
     */
    public static final String es = "es";
    public static final String es_ES = "es_ES";
    public static final Locale LOCALE_ESP = new Locale("es", "ES");
    /**
     * 葡萄牙文 (葡萄牙)pt_PT
     */
    public static final String pt = "pt";
    public static final String pt_PT = "pt_PT";
    public static final Locale LOCALE_PT = new Locale("pt", "PT");

    /**
     * 土耳其文 (土耳其)tr_TR
     */
    public static final String tr = "tr";
    public static final String tr_TR = "tr_TR";
    public static final Locale LOCALE_TR = new Locale("tr", "TR");

    /**
     * 俄文 (俄罗斯)ru_RU
     */
    public static final String ru = "ru";
    public static final String ru_RU = "ru_RU";
    public static final Locale LOCALE_RU = new Locale("ru", "RU");

    /**
     * 中文(简体)zh_CN
     */
    public static final String zh = "zh";
    public static final String zh_CN = "zh_CN";
    public static final Locale LOCALE_CN = new Locale("zh", "CN");

    private LanguageUtils() {

    }

    /**
     * 获取当前app运行语言环境
     *
     * @param resources
     * @return
     */
    public static String getAppLanguage(@NonNull Resources resources) {
        return resources.getConfiguration().locale.toString();
    }


    /**
     * @param language
     * @param country
     * @return
     */
    public static Locale buildLocale(@NonNull String language, @NonNull String country) {
        if (!"".equals(language) && !"".equals(country)) {
            Locale locale = new Locale(language, country);
            return locale;
        }
        return null;
    }

    /**
     * 连同系统语言一起改变
     *
     * @param locale
     */
    public static void updateLanguage(@NonNull Locale locale) {
        try {
            Object objIActMag, objActMagNative;
            Class clzIActMag = Class.forName("android.app.IActivityManager");

            Class clzActMagNative = Class.forName("android.app.ActivityManagerNative");

            //amn = ActivityManagerNative.getDefault();
            Method mtdActMagNative$getDefault = clzActMagNative.getDeclaredMethod("getDefault");

            objIActMag = mtdActMagNative$getDefault.invoke(clzActMagNative);

            // objIActMag = amn.getConfiguration();
            Method mtdIActMag$getConfiguration = clzIActMag.getDeclaredMethod("getConfiguration");

            Configuration config = (Configuration) mtdIActMag$getConfiguration
                    .invoke(objIActMag);

            // set the locale to the new value
            config.locale = locale;

            //持久化  config.userSetLocale = true;
            Class clzConfig = Class.forName("android.content.res.Configuration");
            java.lang.reflect.Field userSetLocale = clzConfig.getField("userSetLocale");
            userSetLocale.set(config, true);

            // 此处需要声明权限:android.permission.CHANGE_CONFIGURATION
            // 会重新调用 onCreate();
            Class[] clzParams = {Configuration.class};

            // objIActMag.updateConfiguration(config);
            Method mtdIActMag$updateConfiguration = clzIActMag.getDeclaredMethod("updateConfiguration", clzParams);

            mtdIActMag$updateConfiguration.invoke(objIActMag, config);

            BackupManager.dataChanged("com.android.providers.settings");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 单纯改变app内部语言
     */
    public static void updateLanguage(@NonNull Locale locale, @NonNull Resources resources) {
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
//        Locale currentLocal = config.locale;
        config.locale = locale;
        resources.updateConfiguration(config, dm);

        // 如果切换了语言
//        if (!currentLocal.equals(config.locale)) {
//            return true;
//        }
//        return false;
    }
}
