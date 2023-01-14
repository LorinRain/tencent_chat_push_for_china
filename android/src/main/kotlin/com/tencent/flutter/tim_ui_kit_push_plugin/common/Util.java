package com.tencent.flutter.tim_ui_kit_push_plugin.common;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.flutter.Log;

public class Util {

    /**
     * 工具类只提供静态的工具方法，不允许外部生成Util实例
     */
    private Util() {

    }

    public static boolean isTokenValid(String token) {
        return !TextUtils.isEmpty(token) && token.length() >= 32;
    }

    public static boolean isMidValid(String mid) {
        if (mid != null && mid.trim().length() >= 40) {
            return true;
        }
        return false;
    }

    public static void jsonPut(JSONObject obj, String key, String value)
            throws JSONException {
        try {
            if (value != null && value.length() > 0) {
                obj.put(key, value);
            }
        } catch (Throwable ex) {
            //Log.e(StatConstants.LOG_TAG, "jsonPut error", ex);
        }
    }

    public static void jsonPut(JSONObject obj, String key, long value)
            throws JSONException {
        try {
            if (key != null && value > 0) {
                obj.put(key, value);
            }
        } catch (Throwable ex) {
            //Log.e(StatConstants.LOG_TAG, "jsonPut error", ex);
        }
    }

    private static AtomicBoolean isInited = new AtomicBoolean(false);
    

    public static boolean isClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }


    /**
     * 判断是否是null或空字符串
     *
     * @param s 源字符串
     * @return
     */
    public static boolean isNullOrEmptyString(String s) {
        return (s == null || s.trim().length() == 0);
    }


    /**
     * 获取保存本地设置的shared preferences name
     *
     * @param context 应用的上下文实例
     * @return shared prefs name
     */
    public static String getPrivatesSharedPrefsName(Context context) {
        return context.getPackageName() + ".tencent.im.flutter.push.local.setting.private";
    }


    public static final int SERVICE_NOT_START = 0;
    public static final int SERVICE_RUNNING = 1;
    public static final int SERVICE_RESTARTING = 2;
    private static final String TAG = "Util";
    
    /**
     * 如果广播被安全软件禁用了，则启用之<br>
     * foreachli, 20150608
     *
     * @param context
     * @param componentName
     */
    private static void enableComponentIfNecessary(Context context,
                                                   String componentName) {
        try {
            if (context == null || componentName == null
                    || componentName.trim().length() == 0) {
                return;
            }
            PackageManager pmManager = context.getPackageManager();
            if (pmManager != null) {
                ComponentName cnComponentName = new ComponentName(
                        context.getPackageName(), componentName);
                int status = pmManager.getComponentEnabledSetting(cnComponentName);
                if (status != PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                    pmManager.setComponentEnabledSetting(cnComponentName,
                            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                            PackageManager.DONT_KILL_APP);
                }
            }
        } catch (Throwable throwable) {
            Log.e(TAG, "getDateString", throwable);
        }
    }

    private static boolean isEnableComponents = false;


    /**
     * @param time 时间戳
     * @return yyyyMMdd形式的字符串
     */
    public static String getDateString(long time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String ctime = formatter.format(time);
            return ctime;
        } catch (Throwable e) {
            Log.e(TAG, "getDateString", e);
        }
        return "20141111";
    }


    public static String checkDevice() {

        String mf = Build.MANUFACTURER;
        if (!TextUtils.isEmpty(mf)) {
            mf = mf.trim().toLowerCase();
        }

        return mf;
    }

    public static boolean isStringValid(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        return true;
    }

    public static long parseIpAddress(String ipAddress) {
        if (ipAddress == null || ipAddress.equals("0") || ipAddress.equals("")) {
            return 0;
        }
        ipAddress = ipAddress.trim();
        long[] ip = new long[4];
        // 先找到IP地址字符串中.的位置
        int position1 = ipAddress.indexOf(".");
        int position2 = ipAddress.indexOf(".", position1 + 1);
        int position3 = ipAddress.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        try {
            ip[3] = Long.parseLong(ipAddress.substring(0, position1));
            ip[2] = Long.parseLong(ipAddress.substring(position1 + 1, position2));
            ip[1] = Long.parseLong(ipAddress.substring(position2 + 1, position3));
            ip[0] = Long.parseLong(ipAddress.substring(position3 + 1));
        } catch (Throwable e) {
            for (int i = 0; i < ip.length; i++) {
                ip[i] = 0;
            }
            Log.e(TAG, "service Util@@parseIpAddress(" + ipAddress + ")", e);
        }
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));

        return sb.toString();
    }



    public static String fixedLengthString(String s, int expectedLength) {
        int l = s.length();
        if (l >= expectedLength) {
            return s;
            // return s.substring(0, expectedLength);
        }
        StringBuffer stringBuffer = new StringBuffer(s);
        for (int i = 0; i < expectedLength - l; i++) {
            stringBuffer.append(" ");
        }
        return stringBuffer.toString();
    }


    /**
     * 获取启动类名
     *
     * @param context 应用上下文
     * @return 启动类名
     */
    public static String getLauncherClassName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
            for (ResolveInfo resolveInfo : resolveInfos) {
                String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
                if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                    return resolveInfo.activityInfo.name;
                }
            }
        } catch (Throwable e) {
            Log.e(TAG, "get launcher class name error: " + e.toString());
        }
        return "";
    }

    public static boolean isFirstInstall(Context context, String pkgName) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(pkgName, 0);
            return packageInfo.firstInstallTime == packageInfo.lastUpdateTime;
        } catch (Throwable e) {
            Log.e(TAG, "unexpected for isFirstInstall: " + e.getMessage());
        }
        return false;
    }

    public static void clearAllNotification(Context context) {
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }
}
