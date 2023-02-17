import 'dart:async';
import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:tencent_chat_push_for_china/model/app_info.dart';
import 'package:tencent_chat_push_for_china/tencent_chat_push_for_china.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();

    // initialize the push service with the 'onClick' callback
    ChannelPush.init(handleClickNotification);

    // After user login, send token and businessID to Tencent Cloud IM Server
    uploadBuzIDAndToken();
  }

  uploadBuzIDAndToken() async {
    int? businessID = await TimUiKitPushPlugin.getBuzId(PushConfig.appInfo);
    String token = await ChannelPush.getDeviceToken();
    if (token != "") {
      // Send token and businessID to Tencent Cloud IM Server. Example below:

      // coreInstance.setOfflinePushConfig(
      //     token: token,
      //     businessID: businessID
      // );
    }
  }

  void handleClickNotification(Map<String, dynamic> msg) async {
    String ext = msg['ext'] ?? "";
    Map<String, dynamic> extMsp = jsonDecode(ext);
    String convId = extMsp["conversationID"] ?? "";
    // Here, you can jump to target conversation via `convId`. Example below:

    // V2TimConversation? targetConversation =
    //    await _conversationService.getConversation(conversationID: convId);
    // print("TUIKitPush | onclickonflutter: $msg");
    // if(targetConversation != null){
    //   Future.delayed(const Duration(milliseconds: 100),(){
    //     Navigator.push(
    //         context,
    //         MaterialPageRoute(
    //           builder: (context) => Chat(
    //             selectedConversation: targetConversation,
    //           ),
    //         ));
    //   });
    // }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: const Center(),
      ),
    );
  }
}

class ChannelPush {
  static final TimUiKitPushPlugin cPush = TimUiKitPushPlugin();

  static init(PushClickAction pushClickAction) async {
    // initialize the push plugin
    cPush.init(
      pushClickAction: pushClickAction,
      appInfo: PushConfig.appInfo,
    );

    // create new notification channel
    cPush.createNotificationChannel(
        channelId: "new_message",
        channelName: "chat_message",
        channelDescription:
            "The notification for chat message from Tencent Cloud IM");

    // require the permission for notification
    cPush.requireNotificationPermission();
  }

  static Future<String> getDeviceToken() async {
    return cPush.getDevicePushToken();
  }
}

class PushConfig {
  //These `Business ID` can be found in the offline push section for each manufacturers,
  // on the main page of console from Tencent Cloud IM.

  // Business ID for HUAWEI
  static const HWPushBuzID = 0;

  // Business ID for XiaoMi
  static const XMPushBuzID = 0;

  // APP Info of XiaoMi
  static const String XMPushAPPID = "";
  static const String XMPushAPPKEY = "";

  // Business ID for Meizu
  static const MZPushBuzID = 0;

  // APP Info of Meizu
  static const String MZPushAPPID = "";
  static const String MZPushAPPKEY = "";

  // Business ID for Vivo
  static const VIVOPushBuzID = 0;

  // Business ID for OPPO
  static const OPPOPushBuzID = 0;

  // APP Info of OPPO
  static const String OPPOPushAPPKEY = "";
  static const String OPPOPushAPPSECRET = "";
  static const String OPPOPushAPPID = "";
  static const String OPPOChannelID = "new_message";

  // Business ID for Apple APNS
  static const ApplePushBuzID = 0;

  static final PushAppInfo appInfo = PushAppInfo(
      hw_buz_id: PushConfig.HWPushBuzID,
      mi_app_id: PushConfig.XMPushAPPID,
      mi_app_key: PushConfig.XMPushAPPKEY,
      mi_buz_id: PushConfig.XMPushBuzID,
      mz_app_id: PushConfig.MZPushAPPID,
      mz_app_key: PushConfig.MZPushAPPKEY,
      mz_buz_id: PushConfig.MZPushBuzID,
      vivo_buz_id: PushConfig.VIVOPushBuzID,
      oppo_app_key: PushConfig.OPPOPushAPPKEY,
      oppo_app_secret: PushConfig.OPPOPushAPPSECRET,
      oppo_buz_id: PushConfig.OPPOPushBuzID,
      oppo_app_id: PushConfig.OPPOPushAPPID,
      apple_buz_id: PushConfig.ApplePushBuzID);
}
