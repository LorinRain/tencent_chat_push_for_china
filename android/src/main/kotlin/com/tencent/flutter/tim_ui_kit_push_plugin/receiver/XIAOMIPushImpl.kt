package com.tencent.flutter.tim_ui_kit_push_plugin.receiver

import android.content.Context
import com.tencent.flutter.tim_ui_kit_push_plugin.TimUiKitPushPlugin
import com.tencent.flutter.tim_ui_kit_push_plugin.common.Extras
import com.tencent.flutter.tim_ui_kit_push_plugin.enum.XiaoMiListenerTypeEnum
import com.xiaomi.mipush.sdk.MiPushCommandMessage
import com.xiaomi.mipush.sdk.MiPushMessage
import com.xiaomi.mipush.sdk.PushMessageReceiver
import io.flutter.Log
import java.util.*


/**
 * 小米广播接收器
 */
class XIAOMIPushImpl : PushMessageReceiver() {
    private val TAG = "TUIKitPush | XIAOMI"
    override fun onNotificationMessageClicked(context: Context?, message: MiPushMessage?) {
        super.onNotificationMessageClicked(context, message)
        Log.i(TAG, "onNotificationMessageClicked" + message?.title)
        this.invokeClickListener(XiaoMiListenerTypeEnum.NotificationMessageClicked, message, context)
    }

    override fun onRequirePermissions(context: Context?, p1: Array<out String>?) {
        super.onRequirePermissions(context, p1)
    }

    override fun onReceivePassThroughMessage(context: Context?, message: MiPushMessage?) {
        super.onReceivePassThroughMessage(context, message)
    }

    override fun onCommandResult(context: Context?, message: MiPushCommandMessage?) {
        super.onCommandResult(context, message)
    }

    override fun onReceiveRegisterResult(context: Context?, message: MiPushCommandMessage?) {
        super.onReceiveRegisterResult(context, message)
    }

    override fun onNotificationMessageArrived(context: Context?, message: MiPushMessage?) {
        super.onNotificationMessageArrived(context, message)
    }

    private fun invokeClickListener(
        type: XiaoMiListenerTypeEnum,
        message: MiPushMessage?,
        context: Context?
    ) {
        val para: MutableMap<String, Any> = HashMap()
        para[Extras.TITLE] = message?.title ?: ""
        para[Extras.CONTENT] = message?.content ?: ""
        para[Extras.CUSTOM_MESSAGE] = message?.extra ?: ""
        para[Extras.MSG_ID] = message?.messageId ?: ""
        para[Extras.EXT] = message?.extra?.get("ext") ?: ""

        val intent = context?.packageManager?.getLaunchIntentForPackage(
            context.packageName
        )
        context?.startActivity(intent)

        Log.i(TAG, "invokeClickListener" + para[Extras.TITLE])

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                Log.i(TAG, "Checking instance isInitialized")
                try{
                    TimUiKitPushPlugin.instance.toFlutterMethod(Extras.PUSH_CLICK_ACTION, para)
                    cancel()
                }catch (e: Exception){

                }
            }
        }, 100, 500)
    }
}