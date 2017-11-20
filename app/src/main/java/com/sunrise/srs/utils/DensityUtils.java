package com.sunrise.treadmill.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by ChuHui on 2017/9/11.
 */

public class DensityUtils {
    private DensityUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * pt转px （附公式：px = pt * DPI / 72)
     *
     * @param context
     * @param ptVal
     * @return
     */
    public static float pt2px(Context context, float ptVal) {
        return (ptVal * context.getResources().getDisplayMetrics().densityDpi) / 72;
    }

    /**
     * px转pt
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2pt(Context context, float pxVal) {
        return (72 * pxVal) / context.getResources().getDisplayMetrics().densityDpi;
    }
}
