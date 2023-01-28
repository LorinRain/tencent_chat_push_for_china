import Flutter
import UIKit

public class SwiftTimUiKitPushPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "tim_ui_kit_push_plugin", binaryMessenger: registrar.messenger())
    let instance = SwiftTimUiKitPushPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    if (call.method.elementsEqual("clearAllNotification")) {
      channelMethodClearAllNotifications(result: result)
    }
    if (call.method.elementsEqual("setBadgeNum")) {
          setBadgeNum(call: call, result: result)
        }
    result("iOS " + UIDevice.current.systemVersion)
  }

  private func channelMethodClearAllNotifications(result: @escaping FlutterResult) {
    if #available(iOS 10.0, *) {
      UIApplication.shared.applicationIconBadgeNumber = 0
      let center = UNUserNotificationCenter.current()
      center.removeAllDeliveredNotifications()
      center.removeAllPendingNotificationRequests()
      result(true)
      return
    }
    result(false)
  }

  private func setBadgeNum(call: FlutterMethodCall, result: @escaping FlutterResult) {
      if let args = call.arguments as? Dictionary<String, Any>,
                  let count = args["badgeNum"] as? Int {
                  UIApplication.shared.applicationIconBadgeNumber = count
                  result(nil)
                }
    }
}
