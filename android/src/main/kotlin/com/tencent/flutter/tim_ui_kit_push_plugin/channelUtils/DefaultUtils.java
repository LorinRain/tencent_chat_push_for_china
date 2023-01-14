package com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.tencent.flutter.tim_ui_kit_push_plugin.common.Util;

import io.flutter.Log;

public class DefaultUtils implements ChannelBaseUtils {
    private String TAG = "TUIKitPush | DEFAULT";

    public DefaultUtils(Context context) {
        this.context = context;
    }

    private Context context;

    public static String checkDevice() {
        String mf = Build.MANUFACTURER;
        android.util.Log.i("TUIKitPush | CPManager", "checkDevice" + mf);
        if (!TextUtils.isEmpty(mf)) {
            mf = mf.trim().toLowerCase();
        }
        return mf;
    }

    @Override
    public void initChannel() {
        String deviceType = checkDevice();
        Log.i(TAG, "initChannel on device: " + deviceType);
    }

    @Override
    public String getToken() {
        Log.i(TAG, "getToken");
        return null;
    }

    @Override
    public void requirePermission() {
        Log.i(TAG, "requirePermission");
    }

    @Override
    public void setBadgeNum(int setNum) {

    }

    @Override
    public void clearAllNotification() {
        Log.i(TAG, "clearAllNotification");
        Util.clearAllNotification(context);
    }

}
