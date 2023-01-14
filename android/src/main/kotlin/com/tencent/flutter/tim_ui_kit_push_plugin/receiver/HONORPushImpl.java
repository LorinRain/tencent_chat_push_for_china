package com.tencent.flutter.tim_ui_kit_push_plugin.receiver;

import com.hihonor.push.sdk.HonorMessageService;
import com.hihonor.push.sdk.HonorPushDataMsg;
import com.tencent.flutter.tim_ui_kit_push_plugin.ChannelPushManager;
import com.tencent.flutter.tim_ui_kit_push_plugin.TimUiKitPushPlugin;
import com.tencent.flutter.tim_ui_kit_push_plugin.common.Extras;
import com.tencent.flutter.tim_ui_kit_push_plugin.common.JsonUtil;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.flutter.Log;

public class HONORPushImpl extends HonorMessageService {
    private static final String TAG = "TUIKitPush | HONOR";
    public static String honorToken = "";

    //Token发生变化时，会以onNewToken方法返回
    @Override
    public void onNewToken(String pushToken) {
        Log.i(TAG, "onRegisterStatus HONOR token = " + pushToken);
        honorToken = pushToken;
        ChannelPushManager.token = pushToken;
    }

    @Override
    public void onMessageReceived(HonorPushDataMsg msg) {
        Log.i(TAG, "invokeClickListener: " + msg.getMsgId() + " - " + msg.getData());

        Map para = new HashMap();
        para.put(Extras.TITLE, msg.getMsgId());
        para.put(Extras.CONTENT, msg.getData());

        try {
            Map extMap = JsonUtil.jsonToMap(msg.getData());
            para.put(Extras.EXT, extMap.get("ext"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
