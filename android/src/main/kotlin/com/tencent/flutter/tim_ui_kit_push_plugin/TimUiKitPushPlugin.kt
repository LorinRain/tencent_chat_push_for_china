package com.tencent.flutter.tim_ui_kit_push_plugin

import android.app.Activity
import android.content.Context
import androidx.annotation.NonNull
import com.tencent.flutter.tim_ui_kit_push_plugin.channelUtils.HonorUtils
import com.tencent.flutter.tim_ui_kit_push_plugin.common.DeviceInfoUtil
import com.tencent.flutter.tim_ui_kit_push_plugin.common.Extras
import com.tencent.flutter.tim_ui_kit_push_plugin.common.MainHandler
import io.flutter.Log
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.util.*

/** TimUiKitPushPlugin */
class TimUiKitPushPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  private var channels: Array<MethodChannel> = emptyArray<MethodChannel>()
  private var TAG = "TUIKitPush | MAIN"
  private var channelPushManager: ChannelPushManager? = null
  private var isInitializeSuccess:Boolean = false
  private var useHuaweiPushService: Boolean = false;

  constructor() {
    instance = this
  }

  companion object {
    lateinit var instance: TimUiKitPushPlugin
    lateinit var activity: Activity
    lateinit var context: Context
  }

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    Log.i(TAG, "onAttachedToEngine")
    val newChannel = MethodChannel(flutterPluginBinding.binaryMessenger, "tim_ui_kit_push_plugin")
    newChannel.setMethodCallHandler(this)
    channels = appendMethodChannel(channels, newChannel)
    context = flutterPluginBinding.applicationContext
    channelPushManager = ChannelPushManager.getInstance(context)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
      Extras.FOR_FLUTTER_METHOD_IS_MIUI_ROM -> isMiuiRom(call, result)
      Extras.FOR_FLUTTER_METHOD_IS_EMUI_ROM -> isEmuiRom(call, result)
      Extras.FOR_FLUTTER_METHOD_IS_MEIZU_ROM -> isMeizuRom(call, result)
      Extras.FOR_FLUTTER_METHOD_IS_OPPO_ROM -> isOppoRom(call, result)
      Extras.FOR_FLUTTER_METHOD_IS_VIVO_ROM -> isVivoRom(call, result)
      Extras.FOR_FLUTTER_METHOD_IS_FCM_ROM -> isFcmRom(call, result)
      Extras.FOR_FLUTTER_METHOD_IS_GOOGLE_ROM -> isGoogleRom(call, result)
      Extras.FOR_FLUTTER_METHOD_INIT -> init(call, result)
      Extras.FOR_FLUTTER_METHOD_GET_NOTIFICATION_PERMISSION -> requireNotificationPermission(call, result)
      Extras.FOR_FLUTTER_METHOD_GET_PUSH_TOKEN -> getPushToken(call, result)
      Extras.FOR_FLUTTER_METHOD_GET_VERSION -> result.success("Android ${android.os.Build.VERSION.RELEASE}")
      Extras.FOR_FLUTTER_METHOD_CREATE_NOTIFICATION_CHANNEL -> createNotificationChannel(call, result)
      Extras.FOR_FLUTTER_METHOD_SET_MI_PUSH_APP_ID -> setMiPushAppId(call, result)
      Extras.FOR_FLUTTER_METHOD_SET_MI_PUSH_APP_KEY -> setMiPushAppKey(call, result)
      Extras.FOR_FLUTTER_METHOD_SET_MZ_PUSH_ID -> setMzPushAppId(call, result)
      Extras.FOR_FLUTTER_METHOD_SET_MZ_PUSH_KEY -> setMzPushAppKey(call, result)
      Extras.FOR_FLUTTER_METHOD_SET_OPPO_PUSH_APP_KEY -> setOppoPushAppKey(call, result)
      Extras.FOR_FLUTTER_METHOD_SET_OPPO_PUSH_APP_ID -> setOppoPushAppId(call, result)
      Extras.FOR_FLUTTER_METHOD_GET_MANUFACTURER -> getDeviceManufacturer(call, result)
      Extras.FOR_FLUTTER_METHOD_GET_PUSH_BRAND -> getPushBrand(call, result)
      Extras.FOR_FLUTTER_METHOD_SET_BADGE_NUM -> setBadgeNum(call, result)
      Extras.FOR_FLUTTER_METHOD_CLEAR_ALL_NOTIFICATION -> clearAllNotification(call, result)
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    for (channel in channels) {
      channel.setMethodCallHandler(null)
    }
    channels = emptyArray<MethodChannel>()
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    Log.i(TAG, "ActivityPluginBinding")
    activity = binding.activity
    channelPushManager = ChannelPushManager.getInstance(activity.applicationContext)
  }

  override fun onDetachedFromActivityForConfigChanges() {
    this.onDetachedFromActivity();
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    this.onAttachedToActivity(binding)
  }

  override fun onDetachedFromActivity() {

  }

  private fun appendMethodChannel(arr: Array<MethodChannel>, element: MethodChannel): Array<MethodChannel> {
    val array = arr.copyOf(arr.size + 1)
    array[arr.size] = element

    Log.i(TAG, "append ${array.size} ${arr.size}")
    return array as Array<MethodChannel>
  }

  // 初始化推送服务
  private fun init(call: MethodCall, result: MethodChannel.Result) {
    val map = call.arguments<Map<String, Any>>()
    useHuaweiPushService = map!!["useHuaweiPushService"] as Boolean;
    try{
      try{
        Log.i(TAG, "init, CPManager: $channelPushManager, activity: ${activity}")
        if(channelPushManager == null){
          channelPushManager = ChannelPushManager.getInstance(activity.applicationContext)
        }
        channelPushManager!!.initChannel(useHuaweiPushService)
        isInitializeSuccess = true
        result.success("")
      }catch (e: Exception){
        Log.i(TAG, "init, CPManager: $channelPushManager, context: $context")
        if(channelPushManager == null){
          channelPushManager = ChannelPushManager.getInstance(context)
        }
        channelPushManager!!.initChannel(useHuaweiPushService)
        isInitializeSuccess = true
        result.success("")
      }
    }catch (e : Exception){
      result.error("-1", "Initialization failed.", "Initialization failed.")
    }
  }

  fun toFlutterMethod(methodName: String, para: Map<String, Any?>?) {
    Log.i(TAG, "Will invoke Flutter=>${methodName}, waiting for initialization")
    Timer().scheduleAtFixedRate(object : TimerTask() {
      override fun run() {
        Log.i(TAG, "Checking plugin isInitialized, $isInitializeSuccess")
        if(isInitializeSuccess){
          Log.i(TAG, "Invoke Flutter=>${methodName}; ${channels.toString()} ${channels.size}")
          MainHandler.getInstance().post {
            for (channel in channels) {
              channel.invokeMethod(methodName, para)
            }
          }
          cancel()
        }
      }
    }, 100, 500)
  }

  // 获取厂商推送Token
  private fun getPushToken(call: MethodCall, result: MethodChannel.Result) {
    val token: String? = channelPushManager?.pushToken
    result.success(token)
  }

  private fun setBadgeNum(call: MethodCall, result: MethodChannel.Result) {
    val map = call.arguments<Map<String, String>>()
    val badgeNum = map!![Extras.BADGE_NUM]
    badgeNum?.toInt()?.let { channelPushManager?.setBadgeNum(it) }
    result.success("")
  }

  // 获取厂商品牌
  private fun getDeviceManufacturer(call: MethodCall, result: MethodChannel.Result) {
    result.success(DeviceInfoUtil.getManufacturers())
  }

  // 获取推送品牌
  private fun getPushBrand(call: MethodCall, result: MethodChannel.Result) {
    var brand = DeviceInfoUtil.getManufacturers();
    if ("honor" == brand) {
      if (useHuaweiPushService || !HonorUtils.checkSupportHonorPush(DeviceInfoUtil.context)) {
        brand = "huawei"
      }
    }
    Log.i(TAG, "pushBrand: $brand, useHuaweiPushService: $useHuaweiPushService")
    result.success(brand);
  }

  private fun isFcmRom(call: MethodCall?, result: MethodChannel.Result?) { //        boolean is
  }

  private fun isGoogleRom(call: MethodCall, result: MethodChannel.Result) {
    result.success(DeviceInfoUtil.isGoogleRom())
  }

  private fun isVivoRom(call: MethodCall, result: MethodChannel.Result) {
    Log.i(TAG, "isVivoRom===" + DeviceInfoUtil.isVivoRom())
    result.success(DeviceInfoUtil.isVivoRom())
  }

  private fun isOppoRom(call: MethodCall, result: MethodChannel.Result) {
    Log.i(TAG, "isOppoRom===" + DeviceInfoUtil.isOppoRom())
    result.success(DeviceInfoUtil.isOppoRom())
  }

  private fun isMeizuRom(call: MethodCall, result: MethodChannel.Result) {
    Log.i(TAG, "isMeizuRom===" + DeviceInfoUtil.isMeizuRom())
    result.success(DeviceInfoUtil.isMeizuRom())
  }

  private fun isEmuiRom(call: MethodCall, result: MethodChannel.Result) {
    Log.i(TAG, "isEmuiRom===" + DeviceInfoUtil.isBrandHuawei())
    result.success(DeviceInfoUtil.isBrandHuawei())
  }

  private fun isMiuiRom(call: MethodCall, result: MethodChannel.Result) {
    Log.i(TAG, "isMiuiRom===" + DeviceInfoUtil.isMiuiRom())
    result.success(DeviceInfoUtil.isMiuiRom())
  }

  private fun setMiPushAppId(call: MethodCall, result: MethodChannel.Result) {
    val map = call.arguments<Map<String, String>>()
    val appId = map!![Extras.APP_ID]
    Log.i(TAG, "setMiPushAppId id:$appId")
    ChannelPushManager.setMiPushAppid(appId)
    result.success(appId)
  }

  private fun setMiPushAppKey(call: MethodCall, result: MethodChannel.Result) {
    val map = call.arguments<Map<String, String>>()
    val appKey = map!![Extras.APP_KEY]
    ChannelPushManager.setMiPushAppKey(appKey)
    result.success(appKey)
  }

  private fun setMzPushAppId(call: MethodCall, result: MethodChannel.Result) {
    val map = call.arguments<Map<String, String>>()
    val appId = map!![Extras.APP_ID]
    ChannelPushManager.setMZPushAppid(appId)
    result.success(appId)
  }

  private fun setMzPushAppKey(call: MethodCall, result: MethodChannel.Result) {
    val map = call.arguments<Map<String, String>>()
    val appKey = map!![Extras.APP_KEY]
    ChannelPushManager.setMZPushAppKey(appKey)
    result.success(appKey)
  }

  private fun setOppoPushAppId(call: MethodCall, result: MethodChannel.Result) {
    val map = call.arguments<Map<String, String>>()
    val appId = map!![Extras.APP_ID]
    ChannelPushManager.setOppoPushAppid(appId)
    result.success(appId)
  }

  private fun setOppoPushAppKey(call: MethodCall, result: MethodChannel.Result) {
    val map = call.arguments<Map<String, String>>()
    val appKey = map!![Extras.APP_KEY]
    ChannelPushManager.setOppoPushAppKey(appKey)
    result.success(appKey)
  }

  private fun requireNotificationPermission(call: MethodCall, result: MethodChannel.Result){
    channelPushManager?.requireNotificationPermission()
    result.success("")
  }

  private fun createNotificationChannel(call: MethodCall, result: MethodChannel.Result?) {
    val map = call.arguments<Map<String, String>>()
    val channelId = map!![Extras.CHANNEL_ID] as String
    val channelName = map[Extras.CHANNEL_NAME] as String
    val channelDescription = map[Extras.CHANNEL_DESCRIPTION] as String
    Log.i(TAG, "创建Android 通知渠道(${channelId}, ${channelName})")
    try {
      DeviceInfoUtil.createChannel(channelId, channelName, channelDescription, activity.applicationContext)
    }catch (e: Exception){
      DeviceInfoUtil.createChannel(channelId, channelName, channelDescription, context)
    }
  }

  private fun clearAllNotification(call: MethodCall, result: MethodChannel.Result) {
    channelPushManager?.clearAllNotification()
    result.success("")
  }

}
