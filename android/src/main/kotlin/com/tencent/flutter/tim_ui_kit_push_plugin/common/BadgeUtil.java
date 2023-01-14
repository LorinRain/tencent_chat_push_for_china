package com.tencent.flutter.tim_ui_kit_push_plugin.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.concurrent.locks.ReentrantLock;

import io.flutter.Log;

public class BadgeUtil {

    public static final String TAG = "TUIKitPush | BadgeUtils";

    private static final String URI_HUAWEI_BADGE = "content://com.huawei.android.launcher.settings/badge/";
    // 虽然脱离且启动类包名改变为 hihonor，但当前荣耀手机角标访问 uri 还是和之前华为的一样的。
//    private static final String URI_HONOR_BADGE = "content://com.hihonor.android.launcher.settings/badge/";
    private static final String URI_HONOR_BADGE = URI_HUAWEI_BADGE;
    private static final String URI_OPPO_BADGE = "content://com.android.badge/badge";
    private static final String ACTION_VIVO_BADGE = "launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM";

    private static int totalChangeNum = 0;      // 累计连续一段时间的角标修改值
    private static int totalBadgeTimes = 0;     // 累计连续一段时间的角标修改调用次数
    private static final int HANDLER_MSG_HWBADGE_CHANGE = 1;
    private static final int HANDLER_MSG_HWBADGE_SET = 2;
    private static Handler badgeHandler;

    private static class BadgeHandler extends Handler {
        private Context context;

        BadgeHandler(Context context) {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            if (context == null) {
                super.handleMessage(msg);
                return;
            }
            switch (msg.what) {
                case HANDLER_MSG_HWBADGE_CHANGE:
                    if (totalBadgeTimes > 0) {
                        totalBadgeTimes -= 1;
                    }

                    if (totalBadgeTimes == 0) {
                        changeHuaweiBadgeNumReal(context, totalChangeNum);
                        totalChangeNum = 0;
                        badgeHandler.removeCallbacks(null);
                    }
                    break;
                case HANDLER_MSG_HWBADGE_SET:
                    final int setNum = msg.arg1;
                    setHuaweiBadgeNumReal(context, setNum);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private static void changeHuaweiBadgeNumReal(final Context context, final int changeNum) {
        if (DeviceInfoUtil.isBrandHuaWei() || DeviceInfoUtil.isBrandHonor()) {
            CommonWorkingThread.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 2020年底荣耀手机脱离华为，区分角标业务的 provider uri
                        String badgeUri = "";
                        if (DeviceInfoUtil.isBrandHuaWei()) {
                            badgeUri = URI_HUAWEI_BADGE;
                        } else if (DeviceInfoUtil.isBrandHonor()) {
                            badgeUri = URI_HONOR_BADGE;
                        }

                        Log.i(TAG, "change huawei badge " + changeNum);
                        Bundle extra = new Bundle();
                        extra.putString("package", context.getPackageName());
                        extra.putString("class", Util.getLauncherClassName(context));

                        int currentBadge = 0;
                        // 获取当前角标数量
                        Bundle bundle = context.getContentResolver().call(
                                Uri.parse(badgeUri),
                                "getbadgeNumber", null, extra);
                        if (bundle != null) {
                            currentBadge = bundle.getInt("badgenumber", 0);
                        }

                        currentBadge += changeNum;
                        if (currentBadge < 0) {
                            currentBadge = 0;
                        }
                        extra.putInt("badgenumber", currentBadge);
                        context.getContentResolver().call(
                                Uri.parse(badgeUri),
                                "change_badge", null, extra);
                    } catch (Throwable e) {
                        Log.w(TAG, "change huawei badge error: " + e.toString());
                    }
                }
            });
        }
    }

    private static void setHuaweiBadgeNumReal(final Context context, final int setNum) {
        if (DeviceInfoUtil.isBrandHuaWei() || DeviceInfoUtil.isBrandHonor()) {
            CommonWorkingThread.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 2020年底荣耀手机脱离华为，区分角标业务的 provider uri
                        String badgeUri = "";
                        if (DeviceInfoUtil.isBrandHuaWei()) {
                            badgeUri = URI_HUAWEI_BADGE;
                        } else if (DeviceInfoUtil.isBrandHonor()) {
                            badgeUri = URI_HONOR_BADGE;
                        }

                        Log.i(TAG, "set huawei badge " + setNum);
                        Bundle extra = new Bundle();
                        extra.putString("package", context.getPackageName());
                        extra.putString("class", Util.getLauncherClassName(context));
                        extra.putInt("badgenumber", setNum);
                        context.getContentResolver().call(
                                Uri.parse(badgeUri),
                                "change_badge", null, extra);
                    } catch (Throwable e) {
                        Log.w(TAG, "set huawei badge error: " + e.toString());
                    }
                }
            });
        }
    }

    /**
     * 设置华为手机应用角标归0
     *
     * 建议在应用打开时将角标清0
     *
     * @param context 应用上下文
     */
    public static void resetHuaweiBadgeNum(Context context) {
        setHuaweiBadgeNum(context, 0);
    }

    // 加锁防止批量删除通知造成的不同步
    private static ReentrantLock lock = new ReentrantLock();

    /**
     * 修改华为手机应用角标
     *
     * 利用 handler 延时机制，先累计连续一段时间内（当前每200ms内）的角标修改调用次数，然后再写 Provider 修改
     *
     * @param context   应用上下文
     * @param changeNum 改变的数字，注意为累加；例如先前为5，入参为1，则角标数被设置为6
     */
    public static void changeHuaweiBadgeNum(Context context, int changeNum) {
        if (DeviceInfoUtil.isBrandHuaWei() || DeviceInfoUtil.isBrandHonor()) {
            try {
                lock.lock();
                if (badgeHandler == null) {
                    badgeHandler = new BadgeHandler(context.getApplicationContext());

                }
                totalBadgeTimes += 1;
                totalChangeNum += changeNum;
                Message message = new Message();
                message.what = HANDLER_MSG_HWBADGE_CHANGE;
                badgeHandler.sendMessageDelayed(message, 200);
            } catch (Throwable e) {
                Log.w(TAG, "change huawei badge error: " + e.toString());
            } finally {
                try {
                    lock.unlock();
                } catch (Throwable e) {
                    Log.e(TAG, "change huawei badge unlock error: " + e.toString());
                }
            }
        }
    }

    public static void setHuaweiBadgeNum(Context context, int setNum) {
        if (DeviceInfoUtil.isBrandHuaWei() || DeviceInfoUtil.isBrandHonor()) {
            try {
                if (badgeHandler == null) {
                    badgeHandler = new BadgeHandler(context.getApplicationContext());
                }
                Message message = new Message();
                message.what = HANDLER_MSG_HWBADGE_SET;
                message.arg1 = setNum;
                badgeHandler.sendMessage(message);
            } catch (Throwable e) {
                Log.w(TAG, "set huawei badge error: " + e.toString());
            }
        }
    }

    public static void setBadgeNum(Context context, int setNum) {
        if (setNum < 0) {
            return;
        }
        setHuaweiBadgeNum(context, setNum);
        setVivoBadgeNum(context, setNum);
        setOPPOBadgeNum(context, setNum);
    }

    public static void resetBadgeNum(Context context) {
        setHuaweiBadgeNum(context, 0);
        setOPPOBadgeNum(context, 0);
        setVivoBadgeNum(context, 0);
    }

    /**
     * 设置 OPPO 脚标数
     * @param context
     * @param setNum
     */
    public static void setOPPOBadgeNum(final Context context, final int setNum) {
        if (DeviceInfoUtil.isOppoRom()) {
            CommonWorkingThread.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(TAG, "set oppo badge " + setNum);
                        Bundle extras = new Bundle();
                        extras.putInt("app_badge_count", setNum);
                        context.getContentResolver().call(Uri.parse(URI_OPPO_BADGE),
                                "setAppBadgeCount", null, extras);
                    } catch (Throwable e) {
                        Log.w(TAG, "set oppo badge error" + e.toString());
                    }
                }
            });
        }

    }

    /**
     * 设置 VIVO 脚标数
     * @param context
     * @param setNum
     */
    public static void setVivoBadgeNum(final Context context, final int setNum) {
        if (DeviceInfoUtil.isVivoRom()) {
            CommonWorkingThread.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(TAG, "set vivo badge " + setNum);
                        Intent intent = new Intent();
                        intent.setAction(ACTION_VIVO_BADGE);
                        intent.putExtra("packageName", context.getPackageName());
                        intent.putExtra("className", Util.getLauncherClassName(context));
                        intent.putExtra("notificationNum", setNum);
                        // Intent.FLAG_RECEIVER_INCLUDE_BACKGROUND 安卓8.0隐式广播后台广播接收者同样能接收
                        intent.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                        context.sendBroadcast(intent);
                    } catch (Throwable e) {
                        Log.w(TAG, "set vivo badge error: " + e.toString());
                    }
                }
            });
        }
    }
}
