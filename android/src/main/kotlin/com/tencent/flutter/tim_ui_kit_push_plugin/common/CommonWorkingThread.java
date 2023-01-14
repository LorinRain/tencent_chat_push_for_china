package com.tencent.flutter.tim_ui_kit_push_plugin.common;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;

import com.huawei.hms.framework.common.Logger;

public class CommonWorkingThread {

    public static String TAG = "TUIKitPush | CommonWorkingThread";

    private static HandlerThread thread = null;

    private static Handler handler = null;

    private CommonWorkingThread() {
    }

    public static class CommonWorkingThreadHolder {
        public static CommonWorkingThread instance = new CommonWorkingThread();
    }

    public static CommonWorkingThread getInstance() {
        initHandler();
        return CommonWorkingThreadHolder.instance;
    }

    /**
     * 将任务r添加到工作线程队列，并在工作线程中执行此任务
     *
     * @param r
     * @return 当任务成功添加到队列时返回true；添加失败时返回false
     */
    public boolean execute(Runnable r) {
        if (handler != null) {
            return handler.post(r);
        }
        return false;

    }

    /**
     * 将任务r添加到工作线程队列，并在指定时间之后在工作线程中执行此任务
     *
     * @param r
     * @param delayMillis 延时毫秒数
     * @return 当任务成功添加到队列时返回true；添加失败时返回false
     */
    public boolean execute(Runnable r, long delayMillis) {
        if (handler != null) {
            return handler.postDelayed(r, delayMillis);
        }
        return false;
    }

    public boolean executeAtTime(Runnable r, int what, long delayMillis) {
        if (handler != null) {
            return handler.postAtTime(r, what, SystemClock.uptimeMillis()
                    + delayMillis);
        }
        return false;
    }

    public void remove(int what) {
        if (handler != null) {
            handler.removeCallbacksAndMessages(what);
        }

    }

    public void remove(Runnable r) {
        if (handler != null) {
            handler.removeCallbacks(r);
        }
    }

    public Handler getHandler() {
        return handler;
    }

    /**
     * 初始化handler
     */
    private static void initHandler() {
        try {
            if (thread == null || !thread.isAlive() || thread.isInterrupted()
                    || thread.getState() == Thread.State.TERMINATED) {
                thread = new HandlerThread("tpns.baseapi.thread");
                thread.start();
                Looper looper = thread.getLooper();
                if (looper != null) {
                    handler = new Handler(looper);

                } else {
                    Logger.e(TAG,
                            ">>> Create new working thread false, cause thread.getLooper()==null");
                }
            }
        } catch (Throwable e) {
            Logger.e(TAG, "unexpected for initHandler", e);
        }
    }

}
