package com.sunrise.srs;

/**
 * Created by ChuHui on 2017/9/7.
 */

public class Constant {
    /**
     * 第一次启动时的校正动作
     */
    public static int initialFinish = -1;

    public static String TAG = "";

    public static final String APP_LANGUAGE = "APP_LANGUAGE";

    public static final String CUSTOMER_PASS_WORD = "CUSTOMER_PASS_WORD";

    public static final String SETTING_TIME = "SETTING_TIME";
    public static final String SETTING_REMAINING_TIME = "SETTING_REMAINING_TIME";


    public static final String BACK_PRESS_SERVER_ACTION = "BACK_PRESS_SERVER_ACTION";

    public static final String SETTING_DISTANCE = "0";
    public static final String SETTING_REMAINING_DISTANCE = "0";

    public static final String MACHINE_TYPE = "MACHINE_TYPE";
    public static final String MACHINE_BIKE = "Bike";
    public static final String MACHINE_RECUMBENT = "Recumbent";
    public static final String MACHINE_ElSTRING = "Elliptical";

    public static final String UNIT_TYPE = "UNIT_TYPE";
    /**
     * 公制
     */
    public static final String UNIT_TYPE_METRIC = "Metric";

    /**
     * 英制
     */
    public static final String UNIT_TYPE_IMPERIAL = "Imperial";


    public static final String WORK_OUT_MODE_HILL = "Hill";
    public static final String WORK_OUT_MODE_INTERVAL = "Interval";
    public static final String WORK_OUT_MODE_GOAL = "Goal";
    public static final String WORK_OUT_MODE_FITNESS_TEST = "Fitness Test";
    public static final String WORK_OUT_MODE_HRC = "HRC";
    public static final String WORK_OUT_MODE_VR = "Virtual Reality";
    public static final String WORK_OUT_MODE_USER_PROGRAM = "User Program";
    public static final String WORK_OUT_MODE_QUICK_START = "Quick Start";
    public static final String WORK_OUT_MODE_MEDIA = "Media";


    public static final int MODE_HILL = 21000;

    public static final int MODE_INTERVAL = 22000;

    public static final int MODE_GOAL = 23000;
    public static final int MODE_GOAL_TIME = 23001;
    public static final int MODE_GOAL_DISTANCE = 23002;
    public static final int MODE_GOAL_CALORIES = 23003;

    public static final int MODE_FITNESS_TEST = 24000;

    public static final int MODE_HRC = 25000;
    public static final int MODE_HRC_TYPE_60 = 25001;
    public static final int MODE_HRC_TYPE_80 = 25002;
    public static final int MODE_HRC_TYPE_TG = 25003;


    public static final int MODE_VR = 26000;
    public static final int MODE_VR_TYPE_VR1 = 26001;
    public static final int MODE_VR_TYPE_VR2 = 26002;
    public static final int MODE_VR_TYPE_VR3 = 26003;
    public static final int MODE_VR_TYPE_VR4 = 26004;
    public static final int MODE_VR_TYPE_VR5 = 26005;
    public static final int MODE_VR_TYPE_VR6 = 26006;
    public static final int MODE_VR_TYPE_VR7 = 26007;
    public static final int MODE_VR_TYPE_VR8 = 26008;
    public static final int MODE_VR_TYPE_VR9 = 26009;
    public static final int MODE_VR_TYPE_VR10 = 26010;
    public static final int MODE_VR_TYPE_VR11 = 26011;


    public static final int MODE_USER_PROGRAM = 27000;

    public static final int MODE_QUICK_START = 28000;

    public static final int MODE_MEDIA = 29000;
    public static final int MODE_MEDIA_YOUTUBE = 40001;

    public static final String MEDIA_YOUTUBE = "com.google.android.youtube";

    public static final int MODE_MEDIA_CHROME = 40002;
    public static final String MEDIA_CHROME = "com.android.chrome";

    public static final int MODE_MEDIA_FACEBOOK = 40003;
    public static final String MEDIA_FACEBOOK = "com.facebook.katana";

    public static final int MODE_MEDIA_FLIKR = 40004;
    public static final String MEDIA_FLIKR = "com.yahoo.mobile.client.android.flickr";

    public static final int MODE_MEDIA_INSTAGRAM = 40005;
    public static final String MEDIA_INSTAGRAM = "com.instagram.android";

    public static final int MODE_MEDIA_MP3 = 40006;
    public static final String MEDIA_MP3 = "";

    public static final int MODE_MEDIA_MP4 = 40007;
    public static final String MEDIA_MP4 = "";

    public static final int MODE_MEDIA_AVIN = 40008;
    public static final String MEDIA_AVIN = "";

    public static final int MODE_MEDIA_TWITTER = 40009;
    public static final String MEDIA_TWITTER = "";

    public static final int MODE_MEDIA_SCREEN_MIRROR = 40010;
    public static final String MEDIA_SCREEN_MIRROR = "";

    public static final int MODE_MEDIA_BAI_DU = 40011;
    public static final String MEDIA_BAI_DU = "";

    public static final int MODE_MEDIA_WEI_BO = 40012;
    public static final String MEDIA_WEI_BO = "com.sina.weibo";

    public static final int MODE_MEDIA_I71 = 40013;
    public static final String MEDIA_I71 = "com.qiyi.video.pad";


    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;


    public static final int MAX_LEVEL = 36;
    public static final int MIN_LEVEL = 0;

    public static final String FACTORY2_MODE_DISPLAY = "FACTORY2_MODE_DISPLAY";
    public static final String FACTORY2_MODE_PAUSE = "FACTORY2_MODE_PAUSE";
    public static final String FACTORY2_MODE_KEY_TONE = "FACTORY2_MODE_KEY_TONE";
    public static final String FACTORY2_MODE_BUZZER = "FACTORY2_MODE_BUZZER";
    public static final String FACTORY2_MODE_CHILD_LOCK = "FACTORY2_MODE_CHILD_LOCK";
    public static final String FACTORY2_MODE_CTRL_PAGE = "FACTORY2_MODE_CTRL_PAGE";


    public static final String FACTORY2_LEVEL = "FACTORY2_LEVEL";
    public static final String FACTORY2_PWM = "FACTORY2_PWM";

    public static final String FACTORY2_TOTAL_TIME = "FACTORY2_TOTAL_TIME";
    public static final String FACTORY2_TOTAL_DISTANT = "FACTORY2_TOTAL_DISTANT";


    public static final String RUNNING_START_TYPE = "RUNNING_START_TYPE";

    public static final int RUNNING_START_TYPE_1 = 1;
    public static final int RUNNING_START_TYPE_2 = 2;
    public static final int RUNNING_START_TYPE_3 = 3;
    public static final int RUNNING_START_TYPE_FINISH = -1;


    public static final String WORK_OUT_INFO = "WORK_OUT_INFO";
    public static final int COUNTDOWN_FLAG = 5;

    /**
     * 暂停,冷却对话框等待时间
     */
    public static final long DIALOG_WAIT_TIME = 60 * 3 * 1000L;


}
