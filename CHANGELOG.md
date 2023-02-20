## 2.3.3

* 新增配置项控制荣耀使用华为推送还是荣耀推送。

## 2.3.0

* Improve the choose between Huawei and new Honor.

## 2.2.0

* Remove the support for Google Firebase Cloud Messaging (FCM).

## 2.1.1

* Add `clearToken` to disable Push.

## 2.1.0

* Upgrade Huawei Push SDK to 6.5.0.300.
* Upgrade Firebase Messaging to ^14.1.4.

## 2.0.3

* Fix the issue from Firebase. "PlatformException(channel-error, Unable to establish connection on channel., null, null)"

## 2.0.2

* Support set badge number on iOS.

## 2.0.1

* Avoid HUAWEI push from auto initialization.

## 2.0.0

* Supports new Honor, separeted from Huawei since 2020, devices.

## 1.0.0

* Upgrade to latest SDK.
* Support add Flutter to Native.

## 0.4.11

* Supported multiple flutter engines.

## 0.4.10

* Bug fixed.


## 0.4.9

* Support add Flutter to Native APP developing mode.

## 0.4.8

* Fix the issue caused by `firebase_core_platform_interface`.

## 0.4.7

* Fix the issue caused by `firebase_core_platform_interface`.

## 0.4.5

* Fix the issue that crash may happened when no bundle provided on OPPO and vivo devices.

## 0.4.4

* Auto retry for push token.

## 0.4.2

* Add limits to dependencies.

## 0.4.1

* Make sure you initilize this plugin, after logging to Tencent IM.

## 0.4.0

* Supports invoke `pushClickAction` after user clicking notification when APP is not active.

## 0.3.3

* Upgrade [firebase_messaging] to 11.2.6

## 0.3.2

* Add Error reprot.

## 0.3.1

* Add `getDevicePushConfig`.
* Improve doc.

## 0.3.0

* Add support to online push and create notification from local.

## 0.2.5

* Upgrade SDK.

## 0.2.4

* Fixed a bug on Google FCM.

## 0.2.3

* Fixed a bug on Google FCM.

## 0.2.2

* Fixed a bug on Google FCM.

## 0.2.1

* Upload token automatically.

## 0.2.0

* Upload token automatically.

## 0.1.17

* Await init.

## 0.1.16

* Fix a bug occurs when using Google and Apple at the same time

## 0.1.15

* Document improvement.

## 0.1.14

* Change HUAWEI repo to sucure link.

## 0.1.13

* Add `setBadgeNum( int badgeNum )`: to set the badge number on XIAOMI(MIUI6 - MIUI 11), HUAWEI, HONOR, vivo and OPPO devices. Apple iOS will be setted by IM SDK automatically.
* Add `clearAllNotification()`: to clear all the notification for current application.

## 0.1.12

* Fix an another bug on Android 12+ for OPPO devices.

## 0.1.11

* Fix an another bug on Android 12+.

## 0.1.10

* Fix bugs on Android 12+.

## 0.1.9

* Fix bugs on Android.

## 0.1.8

* Sample improvement.

## 0.1.7

* Document improvement.

## 0.1.6

* Fix a bug with vivo.

## 0.1.5

* Document improvement.

## 0.1.4

* Lock the version of `firebase_messaging_platform_interface` at 3.1.6, to support Dart version 2.12+. If you have any issues with this, please join the QQ Group: 788910197.

## 0.1.3

* Document improvement.

## 0.1.2

* Add example.

## 0.1.1

* Improve the native layer.
* Degrade the version of Google Firebase plug-in.
* Improve the document.
* Add dartdoc comments.

## 0.1.0

* First release Tencent IM Flutter Push plug-in.
