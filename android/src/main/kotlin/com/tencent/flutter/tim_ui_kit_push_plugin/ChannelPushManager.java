package com.tencent.flutter.tim_ui_kit_push_plugin;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils.ChannelBaseUtils;
import com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils.DefaultUtils;
import com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils.HonorUtils;
import com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils.HuaweiUtils;
import com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils.MeizuUtils;
import com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils.OppoUtils;
import com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils.VivoUtils;
import com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils.XiaoMiUtils;
import com.tencent.flutter.tim_ui_kit_push_plugin.common.DeviceInfoUtil;
import com.tencent.flutter.tim_ui_kit_push_plugin.receiver.HONORPushImpl;

import io.flutter.Log;

public class ChannelPushManager {
    public String TAG = "TUIKitPush | CPManager";
    public static String miAppid = "";
    public static String miAppkey = "";

    public static String mzAppid = "";
    public static String mzAppkey = "";
    public static String oppoAppid = "";
    public static String oppoAppkey = "";

    public static String token = "";

    private static volatile ChannelPushManager instance = null;

    private static volatile ChannelBaseUtils channelUtils = null;

    private Context mContext = null;

    public static void setMiPushAppid(String appid) {
        miAppid = appid;
    }

    public static void setMiPushAppKey(String appkey) {
        miAppkey = appkey;
    }

    public static void setMZPushAppid(String appid) {
        mzAppid = appid;
    }

    public static void setMZPushAppKey(String appkey) {
        mzAppkey = appkey;
    }

    public static void setOppoPushAppid(String appid) {
        oppoAppid = appid;
    }

    public static void setOppoPushAppKey(String appkey) {
        oppoAppkey = appkey;
    }

    public static String checkDevice() {
        String mf = Build.MANUFACTURER;
        if (!TextUtils.isEmpty(mf)) {
            mf = mf.trim().toLowerCase();
        }
        return mf;
    }

    ChannelBaseUtils getChannelUtils() {
        if (channelUtils == null) {
            String deviceType = checkDevice();
            Log.i(TAG, "deviceType: " + deviceType);
            if (DeviceInfoUtil.isMiuiRom()) {
                Log.i(TAG, "USE xiaomi");
                channelUtils = new XiaoMiUtils(mContext);
            } else if (DeviceInfoUtil.isBrandHuaWei()) {
                Log.i(TAG, "USE Huawei");
                channelUtils = new HuaweiUtils(mContext);
            } else if (DeviceInfoUtil.isBrandHonor()) {
                Log.i(TAG, "USE Honor");
                channelUtils = new HonorUtils(mContext);
            } else if (DeviceInfoUtil.isMeizuRom()) {
                Log.i(TAG, "USE Meizu");
                channelUtils = new MeizuUtils(mContext);
            } else if (DeviceInfoUtil.isOppoRom()) {
                Log.i(TAG, "USE oppo");
                channelUtils = new OppoUtils(mContext);
            } else if (DeviceInfoUtil.isVivoRom()) {
                Log.i(TAG, "USE vivo");
                channelUtils = new VivoUtils(mContext);
            } else {
                Log.i(TAG, "USE default, deviceType:" + deviceType);
                channelUtils = new DefaultUtils(mContext);
            }
        }
        return channelUtils;
    }

    private ChannelPushManager(Context context) {
        Log.i(TAG, "start");
        mContext = context;
    }

    public static ChannelPushManager getInstance(Context context) {
        Log.i("TUIKitPush | CPManager", "getInstance");
        if (instance == null) {
            synchronized (ChannelPushManager.class) {
                if (instance == null) {
                    instance = new ChannelPushManager(context);
                }
            }
        }
        return instance;
    }

    public String getPushToken() {
        try {
            String token = getChannelUtils().getToken();
            if(token.isEmpty() && DeviceInfoUtil.isBrandHonor()){
                token = HONORPushImpl.honorToken;
            }
            Log.i(TAG, "getPushToken, Token: " + token);
            return token;
        } catch (Exception e) {
            Log.i(TAG, "Get Token Failed! Please refer to our documentation to troubleshoot this error.");
            return "";
        }
    }

    public void initChannel() {
        String deviceType = checkDevice();
        Log.i(TAG, "initChannel, device: " + deviceType);
        getChannelUtils().initChannel();
    }

    public void requireNotificationPermission() {
        getChannelUtils().requirePermission();
    }

    public void setBadgeNum(final int setNum) {
        getChannelUtils().setBadgeNum(setNum);
    }

    public void clearAllNotification() {
        getChannelUtils().clearAllNotification();
    }
}
