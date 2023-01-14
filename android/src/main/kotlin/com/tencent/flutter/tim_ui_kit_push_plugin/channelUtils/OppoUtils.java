package com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils;

import android.content.Context;

import com.heytap.msp.push.HeytapPushManager;
import com.tencent.flutter.tim_ui_kit_push_plugin.ChannelPushManager;
import com.tencent.flutter.tim_ui_kit_push_plugin.common.BadgeUtil;
import com.tencent.flutter.tim_ui_kit_push_plugin.common.Util;
import com.tencent.flutter.tim_ui_kit_push_plugin.receiver.OPPOPushImpl;

import io.flutter.Log;

public class OppoUtils implements ChannelBaseUtils {
    private String TAG = "TUIKitPush | OPPO";

    public OppoUtils(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public void initChannel() {
        HeytapPushManager.init(context, true);

        if (Util.isNullOrEmptyString(ChannelPushManager.oppoAppid)) {

            Log.i(TAG, "registerPush Error for oppo null AppKey");
            return;
        }

        if (Util.isNullOrEmptyString(ChannelPushManager.oppoAppkey)) {

            Log.i(TAG, "registerPush Error for oppo null AppSecret");
            return;
        }

        if (HeytapPushManager.isSupportPush(context)) {
            OPPOPushImpl oppo = new OPPOPushImpl();
            HeytapPushManager.register(
                    context,
                    ChannelPushManager.oppoAppid,
                    ChannelPushManager.oppoAppkey,
                    oppo
            );
        }
    }

    @Override
    public String getToken() {
        return HeytapPushManager.getRegisterID();
    }

    @Override
    public void requirePermission() {
        HeytapPushManager.requestNotificationPermission();
    }

    @Override
    public void setBadgeNum(int setNum) {
        Log.i(TAG, "setBadgeNum");
        BadgeUtil.setOPPOBadgeNum(context, setNum);
    }

    @Override
    public void clearAllNotification() {
        Log.i(TAG, "clearAllNotification");
        Util.clearAllNotification(context);
    }
}
