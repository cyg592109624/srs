package com.sunrise.srs.interfaces.services;

import android.content.ComponentName;
import android.os.IBinder;

/**
 * Created by ChuHui on 2017/10/11.
 */

public interface FloatServiceBinder {
    /**
     *  启动并且绑定服务完成
     * @param componentName ServiceConnection的componentName
     * @param iBinder ServiceConnection的iBinder
     */
    void onBindSucceed(ComponentName componentName, IBinder iBinder);

    /**
     * 当服务意外死亡时触发
     * @param componentName ServiceConnection的componentName
     */
    void onServiceDisconnected(ComponentName componentName);
}
