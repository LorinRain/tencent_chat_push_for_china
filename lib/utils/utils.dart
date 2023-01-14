import 'package:flutter/services.dart';
import 'package:tencent_im_base/tencent_im_base.dart';

import '../model/appInfo.dart';

class Utils{
  /// Set appinfo for each channel manualy
  static Future<void> setAppInfoForChannel(MethodChannel _channel, PushAppInfo appInfo) async {
    if(await _channel.invokeMethod("isOppoRom") && appInfo.oppo_app_key != null && appInfo.oppo_app_secret != null){
      await _channel.invokeMethod("setOppoPushAppId", {
        "appId": appInfo.oppo_app_key!,
      });
      await _channel.invokeMethod("setOppoPushAppKey", {
        "appKey": appInfo.oppo_app_secret!,
      });
      return;
    }else if(await _channel.invokeMethod("isMiuiRom") && appInfo.mi_app_id != null && appInfo.mi_app_key != null){
      print("TUIKitPush | init | setMiPushAppId");
      await _channel.invokeMethod("setMiPushAppId", {
        "appId": appInfo.mi_app_id!,
      });
      print("TUIKitPush | init | setMiPushAppKey");
      await _channel.invokeMethod("setMiPushAppKey", {
        "appKey": appInfo.mi_app_key!,
      });
      return;
    }else if(await _channel.invokeMethod("isMeizuRom") && appInfo.mz_app_id != null && appInfo.mz_app_key != null){
      await _channel.invokeMethod("setMzPushAppId", {
        "appId": appInfo.mz_app_id!,
      });
      await _channel.invokeMethod("setMzPushAppKey", {
        "appKey": appInfo.mz_app_key!,
      });
      return;
    }
  }

  static String createExtForMessage(V2TimMessage message){
    String createJSON(String convID) {
      return "{\"conversationID\": \"$convID\"}";
    }

    String ext = ((message.groupID != null && message.groupID != "")
        ? createJSON("group_${message.groupID}")
        : createJSON("c2c_${message.sender}"));

    return ext;
  }

  static String getMessageSummary(V2TimMessage message){
    String messageSummary = "";
    switch (message.elemType) {
      case MessageElemType.V2TIM_ELEM_TYPE_CUSTOM:
        messageSummary = TIM_t("自定义消息");
        break;
      case MessageElemType.V2TIM_ELEM_TYPE_FACE:
        messageSummary = TIM_t("表情消息");
        break;
      case MessageElemType.V2TIM_ELEM_TYPE_FILE:
        messageSummary = TIM_t("文件消息");
        break;
      case MessageElemType.V2TIM_ELEM_TYPE_GROUP_TIPS:
        messageSummary = TIM_t("群提示消息");
        break;
      case MessageElemType.V2TIM_ELEM_TYPE_IMAGE:
        messageSummary = TIM_t("图片消息");
        break;
      case MessageElemType.V2TIM_ELEM_TYPE_LOCATION:
        messageSummary = TIM_t("位置消息");
        break;
      case MessageElemType.V2TIM_ELEM_TYPE_MERGER:
        messageSummary = TIM_t("合并转发消息");
        break;
      case MessageElemType.V2TIM_ELEM_TYPE_SOUND:
        messageSummary = TIM_t("语音消息");
        break;
      case MessageElemType.V2TIM_ELEM_TYPE_TEXT:
        messageSummary = message.textElem!.text!;
        break;
      case MessageElemType.V2TIM_ELEM_TYPE_VIDEO:
        messageSummary = TIM_t("视频消息");
        break;
    }
    return messageSummary;
  }
}