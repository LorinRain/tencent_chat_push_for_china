import 'package:flutter/services.dart';
import 'package:huawei_push/huawei_push.dart';
import 'package:tencent_chat_push_for_china/channel/flutter_push_base_impl.dart';
import 'package:tencent_chat_push_for_china/tencent_chat_push_for_china.dart';

class HuaweiImpl extends FlutterPushBase{

  /// [Developers should not use this field directly] Get HUAWEI Push Token
  @override
  Future<String?> getToken() async {
    return token;
  }

  /// [Developers should not use this field directly] Initialize HUAWEI Push
  @override
  Future<void> init(PushClickAction onClickNotificationFunction) async {
    onClickNotification = onClickNotificationFunction;
    await initHuaweiNotificationListener();
    await initHuaweiTokenStream();
    return;
  }

  /// [Developers should not use this field directly] Require HUAWEI Push permission
  @override
  void requirePermission() {
    // TODO: implement requirePermission
  }

  /// [Developers should not use this field directly] Initialize HUAWEI token stream
  Future<void> initHuaweiTokenStream() async {
    print("TUIKitPush | Dart | HUAWEI | init");
    Push.getToken("");
    Push.getTokenStream.listen(_onHuaweiTokenEvent, onError: _onHuaweiTokenError);
    return;
  }

  /// [Developers should not use this field directly] Initialize HUAWEI notification listener
  Future<void> initHuaweiNotificationListener() async {
    Push.onNotificationOpenedApp.listen(_onHaweiNotificationOpenedApp);
    return;
  }

  /// [Developers should not use this field directly] Deal with click event from notification via HUAWEI
  void _onHaweiNotificationOpenedApp(remoteMessage) {
    print("onHUAWEINotificationOpenedApp: " + remoteMessage["extras"].toString());
    onClickNotification!(remoteMessage["extras"]);
  }

  /// [Developers should not use this field directly] Callback after receving HUAWEI token
  void _onHuaweiTokenEvent(String event) {
    // Requested tokens can be obtained here
    token = event;
    print("HuaweiTokenEvent: " + token);
  }

  /// [Developers should not use this field directly] Callback when failure on require HUAWEI token
  void _onHuaweiTokenError(Object error) {
    PlatformException e = error as PlatformException;
    print("HuaweiTokenErrorEvent: ${e.message}");
  }

  /// [Developers should not use this field directly] Determin whether the device support HUAWEI Push
  @override
  Future<bool> isSupport() async {
    return true;
  }
  
}