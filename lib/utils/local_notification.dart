import 'dart:math';

import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:tencent_chat_push_for_china/tencent_chat_push_for_china.dart';

class LocalNotification {
  FlutterLocalNotificationsPlugin? flutterLocalNotificationsPlugin;

  /// [Developers should not use this field directly] Bind the click callback
  PushClickAction? onClickNotification;

  void init(PushClickAction onClickNotificationFunction) async {
    flutterLocalNotificationsPlugin = FlutterLocalNotificationsPlugin();
    onClickNotification = onClickNotificationFunction;

    /// initialise the plugin. app_icon needs to be a added as a drawable resource to the Android head project
    const AndroidInitializationSettings initializationSettingsAndroid =
        AndroidInitializationSettings('@mipmap/ic_launcher');

    final DarwinInitializationSettings initializationSettingsIOS =
        DarwinInitializationSettings(
      onDidReceiveLocalNotification: (
        int id,
        String? title,
        String? body,
        String? payload,
      ) {
        onSelectNotification(payload);
      },
    );

    final InitializationSettings initializationSettings =
        InitializationSettings(
      android: initializationSettingsAndroid,
      iOS: initializationSettingsIOS,
    );

    await flutterLocalNotificationsPlugin!.initialize(
      initializationSettings,
      onDidReceiveNotificationResponse: (NotificationResponse res) {
        onSelectNotification(res.payload);
      },
    );
  }

  void onSelectNotification(String? payload) {
    if (onClickNotification != null) {
      try {
        final Map<String, dynamic> tempMap = {};
        tempMap['ext'] = payload;
        onClickNotification!(tempMap);
      } catch (e) {
        print("notification error $e");
      }
    }
  }

  Future<void> displayNotification({
    required String channelID,
    required String channelName,
    String? channelDescription,
    required String title,
    required String body,

    /// External information
    String? ext,
  }) async {
    if (flutterLocalNotificationsPlugin == null) return;

    final AndroidNotificationDetails androidPlatformChannelSpecifics =
        AndroidNotificationDetails(
      channelID,
      channelName,
      channelDescription: channelDescription,
      importance: Importance.max,
      priority: Priority.high,
    );

    final NotificationDetails platformChannelSpecifics = NotificationDetails(
      android: androidPlatformChannelSpecifics,
    );

    return await flutterLocalNotificationsPlugin!.show(
      Random().nextInt(100000),
      title,
      body,
      platformChannelSpecifics,
      payload: ext,
    );
  }
}
