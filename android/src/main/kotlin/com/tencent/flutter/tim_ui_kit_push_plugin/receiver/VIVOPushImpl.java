package com.tencent.flutter.tim_ui_kit_push_plugin.receiver;

import android.content.Context;

import com.tencent.flutter.tim_ui_kit_push_plugin.TimUiKitPushPlugin;
import com.tencent.flutter.tim_ui_kit_push_plugin.common.Extras;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.flutter.Log;

public class VIVOPushImpl extends OpenClientPushMessageReceiver {
    private static final String TAG = "TUIKitPush | VIVO";
    public static String vivoToken = "";

    @Override
    public void onReceiveRegId(Context context, String regId) {
        Log.i(TAG, "onReceiveRegId = " + regId);
        vivoToken = regId;
    }

    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage upsNotificationMessage) {
        super.onNotificationMessageClicked(context, upsNotificationMessage);
        Log.i(TAG, "invokeClickListener: " + upsNotificationMessage.getTitle() + upsNotificationMessage.getParams());

        Map para = new HashMap();
        para.put(Extras.TITLE, upsNotificationMessage.getTitle());
        para.put(Extras.CONTENT, upsNotificationMessage.getContent());
        para.put(Extras.EXT, upsNotificationMessage.getParams().get("ext"));

        Timer timer = new Timer();

        Log.i(TAG, "invokeClickListener");

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "Checking instance isInitialized");
                try {
                    TimUiKitPushPlugin.instance.toFlutterMethod(Extras.PUSH_CLICK_ACTION, para);
                    cancel();
                } catch (Exception e) {

                }
            }
        }, 100, 500);
    }
}
