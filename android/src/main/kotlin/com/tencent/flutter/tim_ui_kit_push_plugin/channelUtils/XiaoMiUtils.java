package com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.tencent.flutter.tim_ui_kit_push_plugin.ChannelPushManager;
import com.tencent.flutter.tim_ui_kit_push_plugin.common.Util;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.flutter.Log;

public class XiaoMiUtils implements ChannelBaseUtils {
    private String TAG = "TUIKitPush | XIAOMI";

    public XiaoMiUtils(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public void initChannel() {

        if (Util.isNullOrEmptyString(ChannelPushManager.miAppid)) {

            Log.i(TAG, "registerPush Error for xiaomi null AppID");
            return;
        }

        if (Util.isNullOrEmptyString(ChannelPushManager.miAppkey)) {

            Log.i(TAG, "registerPush Error for xiaomi null AppKey");
            return;
        }

        Log.i(TAG, "initial mi push with app id" + ChannelPushManager.miAppid);
        MiPushClient.registerPush(this.context, ChannelPushManager.miAppid, ChannelPushManager.miAppkey);
    }

    @Override
    public String getToken() {
        Log.i(TAG, "getTokenXiaomi");

        return MiPushClient.getRegId(this.context);
    }

    @Override
    public void requirePermission() {
        Log.i(TAG, "requirePermission: 小米默认安装后有通知权限，无需申请。");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setBadgeNum(int setNum) {
        Log.i(TAG, "setBadgeNum: " + setNum);
        // 仅支持MIUI6-MIUI11
        try {
            Field field = context.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(context);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, setNum);

        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    @Override
    public void clearAllNotification() {
        Log.i(TAG, "clearAllNotification");
        MiPushClient.clearNotification(context);
        Util.clearAllNotification(context);
    }

}
