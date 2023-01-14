package com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils;

import android.content.Context;

import com.meizu.cloud.pushsdk.PushManager;
import com.tencent.flutter.tim_ui_kit_push_plugin.ChannelPushManager;
import com.tencent.flutter.tim_ui_kit_push_plugin.common.Util;

import io.flutter.Log;

public class MeizuUtils implements ChannelBaseUtils {

    private String TAG = "TUIKitPush | MEIZU";

    public MeizuUtils(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public void initChannel() {
        if (Util.isNullOrEmptyString(ChannelPushManager.mzAppid)) {

            Log.i(TAG, "registerPush Error for meizu null AppID");
            return;
        }

        if (Util.isNullOrEmptyString(ChannelPushManager.mzAppkey)) {

            Log.i(TAG, "registerPush Error for meizu null AppKey");
            return;
        }

        PushManager.register(context, ChannelPushManager.mzAppid, ChannelPushManager.mzAppkey);
    }

    @Override
    public String getToken() {
        return PushManager.getPushId(context);
    }

    @Override
    public void requirePermission() {
        Log.i(TAG, "requirePermission: 魅族默认安装后有通知权限，无需申请。");
    }

    @Override
    public void setBadgeNum(int setNum) {
        Log.i(TAG, "setBadgeNum");
        Log.i(TAG, "魅族官方推送SDK不支持角标设置，请参考 http://open.res.flyme.cn/fileserver/upload/file/202109/7bf101e2843642709c7a11f4b57861cd.pdf");
    }

    @Override
    public void clearAllNotification() {
        Log.i(TAG, "clearAllNotification");
        Util.clearAllNotification(context);
    }

}
