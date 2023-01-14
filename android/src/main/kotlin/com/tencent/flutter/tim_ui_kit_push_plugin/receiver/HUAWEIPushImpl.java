package com.tencent.flutter.tim_ui_kit_push_plugin.receiver;

import android.content.Context;

import com.huawei.hms.push.HmsMessageService;

import io.flutter.Log;

public class HUAWEIPushImpl extends HmsMessageService {
    private static final String TAG = "TUIKitPush | HUAWEI";

    @Override
    public void onMessageSent(String msgId) {
        Log.i(TAG, "onMessageSent msgId=" + msgId);
    }

    @Override
    public void onSendError(String msgId, Exception exception) {
        Log.i(TAG, "onSendError msgId=" + msgId);
    }

    @Override
    public void onNewToken(String token) {
        Log.i(TAG, "onNewToken token=" + token);

    }

    @Override
    public void onTokenError(Exception exception) {
        Log.i(TAG, "onTokenError exception=" + exception);
    }

    @Override
    public void onMessageDelivered(String msgId, Exception exception) {
        Log.i(TAG, "onMessageDelivered msgId=" + msgId);
    }


    public static void updateBadge(final Context context, final int number) {
        Log.i(TAG, "huawei badge = " + number);
    }
}
