import '../tencent_chat_push_for_china.dart';

/// [Developers should not use this field directly] This class is the base class for Flutter layer access device processing.
/// If you need to implement Push capability for a new channel directly at Flutter layer, please inherit this class.
abstract class FlutterPushBase{

  /// [Developers should not use this field directly] Device Token
  String token =  "";

  /// [Developers should not use this field directly] Bind the click callback
  PushClickAction? onClickNotification;

  /// [Developers should not use this field directly] Initialize the push capability
  Future<void> init(PushClickAction onClickNotificationFunction);

  /// [Developers should not use this field directly] Require push permission
  void requirePermission();

  /// [Developers should not use this field directly] Get device Token
  Future<String?> getToken();

  /// [Developers should not use this field directly] Determin whether the device supports push for each channel
  Future<bool> isSupport();
}