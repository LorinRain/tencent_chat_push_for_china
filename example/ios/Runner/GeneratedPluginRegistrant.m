//
//  Generated file. Do not edit.
//

// clang-format off

#import "GeneratedPluginRegistrant.h"

#if __has_include(<flutter_apns_only/FlutterApnsPlugin.h>)
#import <flutter_apns_only/FlutterApnsPlugin.h>
#else
@import flutter_apns_only;
#endif

#if __has_include(<flutter_local_notifications/FlutterLocalNotificationsPlugin.h>)
#import <flutter_local_notifications/FlutterLocalNotificationsPlugin.h>
#else
@import flutter_local_notifications;
#endif

#if __has_include(<plain_notification_token/PlainNotificationTokenPlugin.h>)
#import <plain_notification_token/PlainNotificationTokenPlugin.h>
#else
@import plain_notification_token;
#endif

#if __has_include(<tencent_chat_push_for_china/TimUiKitPushPlugin.h>)
#import <tencent_chat_push_for_china/TimUiKitPushPlugin.h>
#else
@import tencent_chat_push_for_china;
#endif

#if __has_include(<tencent_im_sdk_plugin/TencentImSDKPlugin.h>)
#import <tencent_im_sdk_plugin/TencentImSDKPlugin.h>
#else
@import tencent_im_sdk_plugin;
#endif

@implementation GeneratedPluginRegistrant

+ (void)registerWithRegistry:(NSObject<FlutterPluginRegistry>*)registry {
  [FlutterApnsPlugin registerWithRegistrar:[registry registrarForPlugin:@"FlutterApnsPlugin"]];
  [FlutterLocalNotificationsPlugin registerWithRegistrar:[registry registrarForPlugin:@"FlutterLocalNotificationsPlugin"]];
  [PlainNotificationTokenPlugin registerWithRegistrar:[registry registrarForPlugin:@"PlainNotificationTokenPlugin"]];
  [TimUiKitPushPlugin registerWithRegistrar:[registry registrarForPlugin:@"TimUiKitPushPlugin"]];
  [TencentImSDKPlugin registerWithRegistrar:[registry registrarForPlugin:@"TencentImSDKPlugin"]];
}

@end
