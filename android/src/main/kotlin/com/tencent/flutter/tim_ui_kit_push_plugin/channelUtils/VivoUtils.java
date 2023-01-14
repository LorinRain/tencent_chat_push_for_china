package com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils;

import android.content.Context;

import com.tencent.flutter.tim_ui_kit_push_plugin.ChannelPushManager;
import com.tencent.flutter.tim_ui_kit_push_plugin.common.BadgeUtil;
import com.tencent.flutter.tim_ui_kit_push_plugin.common.Util;
import com.vivo.push.PushClient;

import io.flutter.Log;

public class VivoUtils implements ChannelBaseUtils {

    private String TAG = "TUIKitPush | VIVO";

    public VivoUtils(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public void initChannel() {
        Log.i(TAG, "VIVO推送启动中");
        try {
            PushClient.getInstance(context).initialize();
            PushClient.getInstance(context).turnOnPush(state -> {
                if (state == 0) {
                    Log.i(TAG, "VIVO推送开启成功");
                    ChannelPushManager.token = PushClient.getInstance(context).getRegId();
                } else {
                    Log.i(TAG, "VIVO推送开启失败，errorCode: " + state);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public String getToken() {
        return Util.isNullOrEmptyString(PushClient.getInstance(context).getRegId())
                ? ChannelPushManager.token :
                PushClient.getInstance(context).getRegId();
    }

    @Override
    public void requirePermission() {
        Log.i(TAG, "requirePermission: vivo默认安装后有通知权限，无需申请。");
    }

    @Override
    public void setBadgeNum(int setNum) {
        Log.i(TAG, "setBadgeNum");
        BadgeUtil.setVivoBadgeNum(context, setNum);
    }

    @Override
    public void clearAllNotification() {
        Log.i(TAG, "clearAllNotification");
        Util.clearAllNotification(context);
    }

}
