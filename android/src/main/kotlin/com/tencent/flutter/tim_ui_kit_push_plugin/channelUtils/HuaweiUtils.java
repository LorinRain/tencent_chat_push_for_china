package com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils;

import android.content.Context;

import com.tencent.flutter.tim_ui_kit_push_plugin.common.BadgeUtil;
import com.tencent.flutter.tim_ui_kit_push_plugin.common.Util;

import io.flutter.Log;

public class HuaweiUtils implements ChannelBaseUtils {

    private String TAG = "TUIKitPush | HUAWEI";

    boolean mIsSupportedBade = true;

    public HuaweiUtils(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public void initChannel() {

    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public void requirePermission() {

    }

    @Override
    public void setBadgeNum(int setNum) {
        Log.i(TAG, "setBadgeNum");
        BadgeUtil.setHuaweiBadgeNum(context, setNum);
    }

    @Override
    public void clearAllNotification() {
        Log.i(TAG, "clearAllNotification");
        Util.clearAllNotification(context);
    }

}
