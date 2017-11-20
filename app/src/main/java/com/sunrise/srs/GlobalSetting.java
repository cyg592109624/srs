package com.sunrise.srs;

import java.util.Locale;

/**
 * Created by ChuHui on 2017/9/4.
 */

public class GlobalSetting {
    public static String AppLanguage = "";

    public static String UnitType = Constant.UNIT_TYPE_METRIC;
    public static String MachineType =  Constant.MACHINE_BIKE;

    public static String CustomerPassWord = "0000";



    public static String Setting_Time = "0";
    public static String Setting_RemainingTime = "0";


    /**
     * 以公制单位记录数据 取出时再手动转换
     */
    public static String Setting_Distance = "0";

    /**
     * 以公制单位记录数据 取出时再手动转换
     */
    public static String Setting_RemainingDistance = "0";

    public static boolean Factory2Mode_Display;
    public static boolean Factory2Mode_Pause;
    public static boolean Factory2Mode_KeyTone;
    public static boolean Factory2Mode_Buzzer;
    public static boolean Factory2Mode_ChildLock;
    public static boolean Factory2Mode_CtrlPage;


    public static String Factory2_Level = "6";
    public static String Factory2_PWM = "9";

    public static String Factory2_TotalTime= "0";
    /**
     * 以公制单位记录数据 取出时再手动转换
     */
    public static String Factory2_TotalDistant= "0";

}
