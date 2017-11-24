package com.sunrise.srs.fragments.factory;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseFragment;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.SDCardUtils;
import com.sunrise.srs.utils.TextUtils;
import com.sunrise.srs.utils.ThreadPoolUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ChuHui on 2017/9/14.
 */

public class Factory2FragmentCard3 extends BaseFragment {
    private final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private File apkFile;
    private UsbManager usbManager;
    private UsbDevice usbDevice;
    private final String USB_PATH = "/storage/udiskh";
    @BindView(R.id.factory2_card3_1_btn)
    ImageView upDataBtn;
    private final int SCAN_APK_FINISH = 20001;
    private final int COPY_APK_FINISH = 20002;

    private BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                default:
                    break;
                case UsbManager.ACTION_USB_DEVICE_ATTACHED:
                    usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    searchDevice();
                    break;
                case UsbManager.ACTION_USB_DEVICE_DETACHED:
                    usbDevice = null;
                    break;
                case ACTION_USB_PERMISSION:
                    searchApk();
                    break;
            }
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCAN_APK_FINISH:
                    upDataBtn.setEnabled(true);
                    break;
                case COPY_APK_FINISH:
                    Toast.makeText(getContext(), "copy完成", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_factory2_card_3;
    }

    @Override
    public void recycleObject() {
        usbDevice = null;
        usbManager = null;
        if (usbReceiver != null) {
            try {
                getActivity().unregisterReceiver(usbReceiver);
                usbReceiver = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void setTextStyle() {
        List<TextView> txtList = new ArrayList<TextView>();

        txtList.add((TextView) parentView.findViewById(R.id.factory2_card3_1_usb));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card3_2_updating));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card3_2_not_shut_down));
        txtList.add((TextView) parentView.findViewById(R.id.factory2_card3_3_download_failed));

        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.Microsoft());
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.Arial());
        }
        txtList.clear();
        txtList = null;
    }

    @Override
    protected void init() {
        usbManager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);

        IntentFilter usbIntent = new IntentFilter();
        usbIntent.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        usbIntent.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        usbIntent.addAction(ACTION_USB_PERMISSION);
        getActivity().registerReceiver(usbReceiver, usbIntent);
        upDataBtn.setEnabled(false);
        searchDevice();
    }

    @OnClick({R.id.factory2_card3_1_btn, R.id.factory2_card3_3_btn})
    public void card3Click(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.factory2_card3_1_btn:
                if (apkFile != null) {
//                    PackageManager pm = getContext().getPackageManager();
//                    String path = apkFile.getAbsolutePath();
//                    PackageInfo packageInfo = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
//                    System.out.println(packageInfo.applicationInfo.packageName);
//
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
//                    startActivity(intent);

                    Toast.makeText(getContext(), "开始复制APK文件", Toast.LENGTH_SHORT).show();
                    upDataBtn.setEnabled(false);
                    copyApk();
                }
                break;
            case R.id.factory2_card3_3_btn:


                break;
        }
    }

    /**
     * 检索USB设备
     */
    private void searchDevice() {
        //更新app软件 前提条件是先检索U盘是否存在
        if (usbDevice == null) {
            Map<String, UsbDevice> map = usbManager.getDeviceList();
            if (map.size() == 1) {
                for (String key : map.keySet()) {
                    usbDevice = map.get(key);
                }
            }
        }
        if (usbDevice != null) {
            Boolean res = usbManager.hasPermission(usbDevice);
            if (!res) {
                PendingIntent permissionIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent(ACTION_USB_PERMISSION), 0);
                usbManager.requestPermission(usbDevice, permissionIntent);
            } else {
                searchApk();
            }
        }
    }

    private void searchApk() {
        try {
            File parent = new File(USB_PATH);
            File[] files = parent.listFiles();
            for (File file : files) {
                if (file.getName().indexOf(".apk") > -1) {
                    apkFile = file;
                    return;
                }
            }
            if (apkFile != null) {
                handler.sendEmptyMessage(SCAN_APK_FINISH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将APk复制到内存卡
     */
    private void copyApk() {
        //这里是否需要将文件复制到sd卡再执行安装？
        ThreadPoolUtils.runTaskOnThread(new Runnable() {
            @Override
            public void run() {
                try {
                    File sdFile = new File(SDCardUtils.getSDCardPath() + "running.apk");
                    BufferedOutputStream out = null;
                    BufferedInputStream in = null;
                    if (sdFile.exists()) {
                        sdFile.delete();
                    }
                    sdFile.createNewFile();
                    sdFile.setWritable(true);
                    sdFile.setReadable(true);
                    while (usbDevice != null) {
                        out = new BufferedOutputStream(new FileOutputStream(sdFile));
                        in = new BufferedInputStream(new FileInputStream(apkFile));

                        byte[] buf = new byte[1024];
                        int len = 0;
                        while ((len = in.read(buf)) > -1) {
                            out.write(buf, 0, len);
                            out.flush();
                        }
                        out.close();
                        in.close();
                        handler.sendEmptyMessage(COPY_APK_FINISH);
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        });
    }

}
