//
// TimUiKitPushPlugin.m
// tencent_chat_push_for_china
//
// Created by owennwang on 2022/05/10
// Copyright (c) 2022 Tencent. All rights reserved.
//
#import "TimUiKitPushPlugin.h"
#if __has_include(<tencent_chat_push_for_china/tencent_chat_push_for_china-Swift.h>)
#import <tencent_chat_push_for_china/tencent_chat_push_for_china-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "tencent_chat_push_for_china-Swift.h"
#endif

@implementation TimUiKitPushPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftTimUiKitPushPlugin registerWithRegistrar:registrar];
}
@end
