package com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils;

public interface ChannelBaseUtils {

    public void initChannel();

    public String getToken();

    public void requirePermission();

    public void setBadgeNum(final int setNum);

    public void clearAllNotification();
}
