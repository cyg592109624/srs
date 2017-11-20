package com.sunrise.srs.utils;

/**
 * Created by ChuHui on 2017/10/30.
 */

public class UnitUtils {
    /**
     * 公里转英里
     */
    private static final float A1 = 0.6f;


    /**
     * 千克转磅
     */
    private static final float A2 = 2.2f;


    /**
     * 公里转英里
     *
     * @return
     */
    public static float km2mile(float km) {
        return (float) Math.round(km / A1 * 10) / 10;
    }

    /**
     * 公里转英里
     *
     * @return
     */
    public static float km2mile(String km) {
        try {
            float f = Float.valueOf(km);
            return km2mile(f);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 英里转公里
     *
     * @return
     */
    public static float mile2km(float mile) {
        return (float) Math.round(mile * A1 * 10) / 10;
    }

    /**
     * 英里转公里
     *
     * @return
     */
    public static float mile2km(String mile) {
        try {
            float f = Float.valueOf(mile);
            return mile2km(f);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 千克转磅数
     *
     * @return
     */
    public static float kg2lb(float kg) {
        return (float) Math.round(kg * A2 * 10) / 10;
    }

    /**
     * 千克转磅数
     *
     * @return
     */
    public static float kg2lb(String kg) {
        try {
            float f = Float.valueOf(kg);
            return kg2lb(f);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 磅数转千克
     *
     * @return
     */
    public static float lb2kg(float lb) {
        return (float) Math.round(lb / A2 * 10) / 10;
    }

    /**
     * 磅数转千克
     *
     * @return
     */
    public static float lb2kg(String lb) {
        try {
            float f = Float.valueOf(lb);
            return lb2kg(f);
        } catch (Exception e) {
            return -1;
        }
    }
}
