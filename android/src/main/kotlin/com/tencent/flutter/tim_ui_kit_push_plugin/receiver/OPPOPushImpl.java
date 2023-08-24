package com.tencent.flutter.tim_ui_kit_push_plugin.receiver;

import com.heytap.msp.push.callback.ICallBackResultService;
import com.tencent.flutter.tim_ui_kit_push_plugin.ChannelPushManager;

import io.flutter.Log;

public class OPPOPushImpl implements ICallBackResultService {
    private static final String TAG = "TUIKitPush | OPPO";
    public static String oppoToken = "";

    @Override
    public void onRegister(int responseCode, String registerID, String packageName, String miniPackageName) {
        Log.i(TAG, "onRegister responseCode: " + responseCode + " registerID: " + registerID);
        oppoToken = registerID;
        ChannelPushManager.token = registerID;
    }

    @Override
    public void onUnRegister(int responseCode, String packageName, String miniProgramPkg) {
        Log.i(TAG, "onUnRegister responseCode: " + responseCode);
    }

    @Override
    public void onSetPushTime(int responseCode, String s) {
        Log.i(TAG, "onSetPushTime responseCode: " + responseCode + " s: " + s);
    }

    @Override
    public void onGetPushStatus(int responseCode, int status) {
        Log.i(TAG, "onGetPushStatus responseCode: " + responseCode + " status: " + status);
    }

    @Override
    public void onGetNotificationStatus(int responseCode, int status) {
        Log.i(TAG, "onGetNotificationStatus responseCode: " + responseCode + " status: " + status);
    }

    @Override
    public void onError(int errorCode, String message, String packageName, String miniProgramPkg) {

    }
}
