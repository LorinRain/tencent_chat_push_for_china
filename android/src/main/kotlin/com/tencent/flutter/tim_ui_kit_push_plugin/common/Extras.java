package com.tencent.flutter.tim_ui_kit_push_plugin.common;

public interface Extras {
    //参数
    String CONTENT = "content";
    String EXT = "ext";
    String TITLE = "title";
    String CUSTOM_MESSAGE = "customMessage";
    String MSG_ID = "msgId";
    String APP_KEY = "appKey";
    String APP_ID = "appId";
    String CHANNEL_ID = "channelId";
    String CHANNEL_NAME = "channelName";
    String CHANNEL_DESCRIPTION = "channelDescription";
    String BADGE_NUM = "badgeNum";

    //Flutter调用native的函数名称
    String FOR_FLUTTER_METHOD_CREATE_NOTIFICATION_CHANNEL = "createNotificationChannel";
    String FOR_FLUTTER_METHOD_SET_MI_PUSH_APP_ID = "setMiPushAppId";
    String FOR_FLUTTER_METHOD_SET_MI_PUSH_APP_KEY = "setMiPushAppKey";
    String FOR_FLUTTER_METHOD_SET_MZ_PUSH_ID = "setMzPushAppId";
    String FOR_FLUTTER_METHOD_SET_MZ_PUSH_KEY = "setMzPushAppKey";
    String FOR_FLUTTER_METHOD_SET_OPPO_PUSH_APP_ID = "setOppoPushAppId";
    String FOR_FLUTTER_METHOD_SET_OPPO_PUSH_APP_KEY = "setOppoPushAppKey";
    String FOR_FLUTTER_METHOD_SET_BADGE_NUM = "setBadgeNum";
    String FOR_FLUTTER_METHOD_GET_PUSH_TOKEN = "getPushToken";
    String FOR_FLUTTER_METHOD_INIT = "initPush";
    String FOR_FLUTTER_METHOD_IS_MIUI_ROM = "isMiuiRom";
    String FOR_FLUTTER_METHOD_IS_EMUI_ROM = "isEmuiRom";
    String FOR_FLUTTER_METHOD_IS_MEIZU_ROM = "isMeizuRom";
    String FOR_FLUTTER_METHOD_IS_OPPO_ROM = "isOppoRom";
    String FOR_FLUTTER_METHOD_IS_VIVO_ROM = "isVivoRom";
    String FOR_FLUTTER_METHOD_IS_FCM_ROM = "isFcmRom";
    String FOR_FLUTTER_METHOD_IS_GOOGLE_ROM = "isGoogleRom";
    String FOR_FLUTTER_METHOD_GET_VERSION = "getPlatformVersion";
    String FOR_FLUTTER_METHOD_GET_MANUFACTURER = "getDeviceManufacturer";
    String FOR_FLUTTER_METHOD_GET_NOTIFICATION_PERMISSION = "getNotificationPermission";
    String FOR_FLUTTER_METHOD_CLEAR_ALL_NOTIFICATION = "clearAllNotification";

    //调用Flutter的函数名称
    String PUSH_CLICK_ACTION = "TIMPushClickAction";   //通知点击事件
}
