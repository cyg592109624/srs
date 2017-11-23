package com.sunrise.srs.services.settings;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.R;
import com.sunrise.srs.utils.ImageUtils;

/**
 * Created by ChuHui on 2017/11/11.
 */

public class BackPressServer extends Service {
    //打开蓝牙跟WiFi时出现的返回键
    private WindowManager mWindowManager;
    public WindowManager.LayoutParams floatParams;
    private ImageView backImage;

    @Override
    public void onCreate() {
        super.onCreate();
        initImageView();
    }

    private final IBinder floatBinder = new BackPressBinder();

    public class BackPressBinder extends Binder {
        /**
         * @return 主要为了获取该服务的实例对象
         */
        public BackPressServer getService() {
            return BackPressServer.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return floatBinder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        onDestroy();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (backImage != null) {
                mWindowManager.removeView(backImage);
                backImage = null;
            }
            mWindowManager = null;
        } catch (Exception e) {
        }
    }

    private void initImageView() {
        mWindowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics dm = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        dm = null;

        floatParams = new WindowManager.LayoutParams();
        // 设置window type
        floatParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // 设置图片格式，效果为背景透明
        floatParams.format = PixelFormat.RGBA_8888;

        // 设置Window flag
        floatParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        floatParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        floatParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        floatParams.horizontalMargin = 0;
        floatParams.verticalMargin = 0;
        floatParams.x = screenWidth / 2;
        floatParams.y = 0;

        backImage = new ImageView(getApplicationContext());
        ImageUtils.changeImageView(backImage, R.drawable.btn_back_2);

        backImage.setOnTouchListener(new View.OnTouchListener() {
            int lastX, lastY;
            int paramX, paramY;
            int dx, dy;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        paramX = floatParams.x;
                        paramY = floatParams.y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        dx = (int) event.getRawX() - lastX;
                        dy = (int) event.getRawY() - lastY;
                        floatParams.x = paramX + dx;
                        floatParams.y = paramY + dy;
                        // 更新悬浮窗位置
                        mWindowManager.updateViewLayout(backImage, floatParams);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(dx) < 10 && Math.abs(dy) < 10) {
                            Intent intent = new Intent();
                            intent.setAction(Constant.BACK_PRESS_SERVER_ACTION);
                            sendBroadcast(intent);
                        }
                        break;
                }
                return true;
            }
        });

        backImage.setLayoutParams(floatParams);
    }

    public void moveBackView() {
        try {
            mWindowManager.removeView(backImage);
        } catch (Exception e) {

        }
    }

    public void addBackView() {
        try {
            mWindowManager.addView(backImage, floatParams);
        } catch (Exception e) {

        }
    }
}
