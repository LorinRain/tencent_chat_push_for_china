package com.tencent.flutter.tim_ui_kit_push_plugin.receiver;

import android.content.Context;

import com.meizu.cloud.pushsdk.MzPushMessageReceiver;
import com.meizu.cloud.pushsdk.handler.MzPushMessage;
import com.meizu.cloud.pushsdk.platform.message.PushSwitchStatus;
import com.meizu.cloud.pushsdk.platform.message.RegisterStatus;
import com.meizu.cloud.pushsdk.platform.message.SubAliasStatus;
import com.meizu.cloud.pushsdk.platform.message.SubTagsStatus;
import com.meizu.cloud.pushsdk.platform.message.UnRegisterStatus;
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

public class MEIZUPushImpl extends MzPushMessageReceiver {
    private static final String TAG = "TUIKitPush | MEIZU";
    public static String meizuToken = "";

    @Override
    public void onNotificationClicked(Context context, MzPushMessage mzPushMessage) {
        super.onNotificationClicked(context, mzPushMessage);
        Log.i(TAG, "invokeClickListener: " + mzPushMessage.getTitle());


        Map para = new HashMap();
        para.put(Extras.TITLE, mzPushMessage.getTitle());
        para.put(Extras.CONTENT, mzPushMessage.getContent());

        try {
            Map extMap = JsonUtil.jsonToMap(mzPushMessage.getSelfDefineContentString());
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

    @Override
    public void onNotificationArrived(Context context, MzPushMessage mzPushMessage) {
        super.onNotificationArrived(context, mzPushMessage);
    }

    @Override
    public void onRegisterStatus(Context context, RegisterStatus registerStatus) {
        String token = registerStatus.getPushId();
        Log.i(TAG, "onRegisterStatus MEIZU token = " + token);
        meizuToken = token;
        ChannelPushManager.token = token;
    }

    @Override
    public void onUnRegisterStatus(Context context, UnRegisterStatus unRegisterStatus) {

    }

    @Override
    public void onPushStatus(Context context, PushSwitchStatus pushSwitchStatus) {

    }

    @Override
    public void onSubTagsStatus(Context context, SubTagsStatus subTagsStatus) {

    }

    @Override
    public void onSubAliasStatus(Context context, SubAliasStatus subAliasStatus) {

    }


}
