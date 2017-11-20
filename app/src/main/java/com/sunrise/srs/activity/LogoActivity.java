package com.sunrise.srs.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.activity.home.HomeActivity;
import com.sunrise.srs.base.BaseActivity;
import com.sunrise.srs.Constant;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.SharedPreferencesUtils;
import com.sunrise.srs.utils.ThreadPoolUtils;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class LogoActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    private int permissionRequestCode = 1001;

    private final int permissionRequestCode2 = 1002;
    private final int permissionRequestCode3 = 1003;

    private String[] lackOfPerms = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.GET_TASKS", "android.permission.REORDER_TASKS"};
    /**
     * 权限申请结果
     */
    private final int easyPermissionResult = 2001;
    private final int allowWindowPermission = 2002;
    private final int writeSettingPermission = 2003;
    private final int permissionFinish = 2004;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                default:
                    break;
                case easyPermissionResult:
                    mHandler.sendEmptyMessage(allowWindowPermission);
                    break;
                case allowWindowPermission:
                    requestAllowWindowPermission();
                    break;
                case writeSettingPermission:
                    requestWriteSettingPermission();
                    break;
                case permissionFinish:
                    syncLanguage();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasyPermissions.requestPermissions(this, "权限请求", permissionRequestCode, lackOfPerms);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_logo;
    }

    @Override
    public void recycleObject() {
        lackOfPerms = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == permissionRequestCode) {
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, activityContext);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (perms.size() != 0) {
            String[] args = new String[perms.size()];
            for (int i = 0; i < perms.size(); i++) {
                args[i] = perms.get(i);
            }
            mHandler.sendEmptyMessage(easyPermissionResult);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (perms.size() != 0) {
            String[] args = new String[perms.size()];
            for (int i = 0; i < perms.size(); i++) {
                args[i] = perms.get(i);
                System.out.println("拒接的权限     -->" + perms.get(i));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            default:
                break;
            case permissionRequestCode2:
                if (!Settings.canDrawOverlays(LogoActivity.this)) {
                    finishActivity();
                } else {
                    mHandler.sendEmptyMessage(writeSettingPermission);
                }
                break;
            case permissionRequestCode3:
                if (!Settings.System.canWrite(activityContext)) {
                    finishActivity();
                } else {
                    mHandler.sendEmptyMessage(permissionFinish);
                }
                break;
        }
    }

    /**
     * 悬浮窗权限申请
     */
    private void requestAllowWindowPermission() {
        if (!Settings.canDrawOverlays(activityContext)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, permissionRequestCode2);
        } else {
            mHandler.sendEmptyMessage(writeSettingPermission);
        }
    }

    /**
     * 设置写入权限
     */
    private void requestWriteSettingPermission() {
        if (!Settings.System.canWrite(activityContext)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, permissionRequestCode3);
        } else {
            mHandler.sendEmptyMessage(permissionFinish);
        }
    }

    /**
     * 切换app语言
     */
    private void syncLanguage() {
        try {
            //启动app时 获取语言设置
            String appLanguage = (String) SharedPreferencesUtils.get(activityContext, Constant.APP_LANGUAGE, LanguageUtils.zh_CN);
            //获取当前设置
            String nowAppLanguage = LanguageUtils.getAppLanguage(activityContext.getResources());
            Intent intent = new Intent();
//            if (!appLanguage.equals(nowAppLanguage)) {
//                GlobalSetting.AppLanguage = appLanguage;
//                String[] he = appLanguage.split("_");
//                LanguageUtils.updateLanguage(LanguageUtils.buildLocale(he[0], he[1]));
//            }
//            intent.setClass(activityContext, HomeActivity.class);

            if (appLanguage.equals(nowAppLanguage)) {
                intent.setClass(activityContext, HomeActivity.class);
            } else {
                GlobalSetting.AppLanguage = appLanguage;
                String[] he = appLanguage.split("_");
                LanguageUtils.updateLanguage(LanguageUtils.buildLocale(he[0], he[1]), activityContext.getResources());
//                LanguageUtils.updateLanguage(LanguageUtils.buildLocale(he[0], he[1]));
                intent.setClass(activityContext, LogoActivity.class);
                Thread.sleep(300);
            }

            finishActivity();
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
