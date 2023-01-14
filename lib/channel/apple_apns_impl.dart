import 'package:flutter_apns_only/flutter_apns_only.dart';
import 'package:plain_notification_token/plain_notification_token.dart';
import 'package:tencent_chat_push_for_china/channel/flutter_push_base_impl.dart';
import 'package:tencent_chat_push_for_china/tencent_chat_push_for_china.dart';

class AppleAPNSImpl extends FlutterPushBase{

  /// [Developers should not use this field directly] Initialize APPLE Push Plug-in
  final ApnsPushConnectorOnly applePushConnector = ApnsPushConnectorOnly();

  /// [Developers should not use this field directly] Initialize APPLE Token Plug-in
  final PlainNotificationToken getAppleNotificationToken = PlainNotificationToken();

  /// [Developers should not use this field directly] Initialize APPLE APNS Push
  @override
  Future<void> init(PushClickAction onClickNotificationFunction) async {
    applePushConnector.configureApns(
      onLaunch: (ApnsRemoteMessage message) async {
        onClickNotificationFunction(message.payload);
        return;
      },
      onResume: (ApnsRemoteMessage message) async {
        onClickNotificationFunction(message.payload);
        return;
      },
    );
    return;
  }

  /// [Developers should not use this field directly] Get Appple APNS Token
  @override
  Future<String?> getToken() async {
    getAppleNotificationToken.requestPermission();
    await getAppleNotificationToken.onIosSettingsRegistered.first;
    return await getAppleNotificationToken.getToken();
  }

  /// [Developers should not use this field directly] Require APPLE iOS Push permission
  @override
  void requirePermission() {
    applePushConnector.requestNotificationPermissions();
  }

  /// [Developers should not use this field directly] Determine whether the device supports Apple APNS push
  @override
  Future<bool> isSupport() async {
    return true;
  }
}