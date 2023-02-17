class PushAppInfo {
  /// HUAWEI Business ID (From Tencent Cloud IM console)
  final int? hw_buz_id;

  /// XiaoMi Business ID (From Tencent Cloud IM console)
  final int? mi_buz_id;

  /// XiaoMi APP_ID
  final String? mi_app_id;

  /// XiaoMi APP_Key
  final String? mi_app_key;

  /// Meizu Business ID (From Tencent Cloud IM console)
  final int? mz_buz_id;

  /// Meizu APP_ID
  final String? mz_app_id;

  /// Meizu APP_Key
  final String? mz_app_key;

  /// Vivo Business ID (From Tencent Cloud IM console)
  final int? vivo_buz_id;

  /// OPPO Business ID (From Tencent Cloud IM console)
  final int? oppo_buz_id;

  /// OPPO APP_Key
  final String? oppo_app_key;

  /// OPPO APP_Secret
  final String? oppo_app_secret;

  /// OPPO APP_ID
  final String? oppo_app_id;

  /// APPLE APNS Business ID (From Tencent Cloud IM console)
  final int? apple_buz_id;

  /// HONOR Business ID (From Tencent Cloud IM console)
  final int? honor_buz_id;

  /// The buz ID refer to the Business ID from the main page From Tencent Cloud IM console, after you uploading the account info for each channel.
  PushAppInfo({
    this.mi_app_id,
    this.mi_app_key,
    this.mz_buz_id,
    this.mz_app_id,
    this.mz_app_key,
    this.vivo_buz_id,
    this.oppo_buz_id,
    this.oppo_app_key,
    this.oppo_app_secret,
    this.oppo_app_id,
    this.hw_buz_id,
    this.mi_buz_id,
    this.apple_buz_id,
    this.honor_buz_id
  });
}
