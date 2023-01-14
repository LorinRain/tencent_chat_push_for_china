class PushDeviceConfig {
  /// Device manufacturer
  String? deviceManufacturer;

  /// The corresponding business ID of current device on Tencent Cloud IM console.
  int? businessID;

  /// Device push token, sometimes been called as 'register ID'.
  String? deviceToken;

  PushDeviceConfig(
      {this.deviceManufacturer, this.businessID, this.deviceToken});
}
