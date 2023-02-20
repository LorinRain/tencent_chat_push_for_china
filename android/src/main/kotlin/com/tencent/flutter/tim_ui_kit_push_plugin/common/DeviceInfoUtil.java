package com.tencent.flutter.tim_ui_kit_push_plugin.common;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils.HonorUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.flutter.Log;

/**
 * 获取系统设备信息的工具类
 */
public class DeviceInfoUtil {
    public static Context context = null;

    public static String TAG = "TUIKitPush | DeviceInfoUtil";

    /* 获取操作系统版本号 */
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /* 获取手机型号 */
    public static String getModel() {
        return Build.MODEL;
    }


    /* 获取手机厂商 */
    public static String getManufacturers() {
        String mf = Build.MANUFACTURER;
        if (!TextUtils.isEmpty(mf)) {
            mf = mf.trim().toLowerCase();
        }
        return mf;
    }

    /* 获取app的版本信息 */
    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;// 系统版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;// 系统版本名
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * getSystemProperty
     *
     * @param propName property name
     * @return result
     */
    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.d("DeviceInfoUtil", "close bufferedReader error " + e.toString());
                }
            }
        }
        return line;
    }

    /**
     * 判断 google miui emui meizu oppo vivo 360 Rom
     *
     * @return result
     */

    public static boolean isGoogleRom() {
        String property = getSystemProperty("ro.product.vendor.manufacturer");
        if (TextUtils.isEmpty(property)) return false;
        assert property != null;
        return property.toLowerCase().contains("google");
    }

    public static boolean isMiuiRom() {
        String property = getSystemProperty("ro.miui.ui.version.name");
        return "xiaomi".equalsIgnoreCase(Build.MANUFACTURER)
                || "blackshark".equalsIgnoreCase(Build.MANUFACTURER)
                || !TextUtils.isEmpty(property);
    }

    public static boolean isBrandHuawei() {
        return "huawei".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isBrandHonorUseHuaweiPush() {
        return isBrandHonor() && !HonorUtils.checkSupportHonorPush(context);
    }

    public static boolean isBrandHonor() {
        return "honor".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isMeizuRom() {
        String property = getSystemProperty("ro.build.display.id");
        return property != null && property.toLowerCase().contains("flyme");
    }

    public static boolean isOppoRom() {
        String deviceType = getManufacturers();
        String property = getSystemProperty("ro.build.version.opporom");
        return "oppo".equals(deviceType)
                || "oneplus".equals(deviceType)
                || "realme".equals(deviceType)
                || !TextUtils.isEmpty(property);
    }

    public static boolean isVivoRom() {
        String deviceType = getManufacturers();
        String property = getSystemProperty("ro.vivo.os.version");
        return "vivo".equals(deviceType)
                || "iqoo".equals(deviceType)
                || !TextUtils.isEmpty(property);
    }

    public static void createChannel(
            String channelID, String channelName, String description, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =
                    new NotificationChannel(channelID, channelName, importance);
            channel.setDescription(description);
            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
