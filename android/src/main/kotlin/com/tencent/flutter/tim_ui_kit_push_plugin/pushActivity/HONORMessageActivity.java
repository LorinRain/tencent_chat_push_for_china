package com.tencent.flutter.tim_ui_kit_push_plugin.pushActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.tencent.flutter.tim_ui_kit_push_plugin.TimUiKitPushPlugin;
import com.tencent.flutter.tim_ui_kit_push_plugin.common.Extras;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import io.flutter.Log;

public class HONORMessageActivity extends Activity {

    private static final String TAG = "TUIKitPush | HONOR";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Map keyMap = new HashMap();

        try {
            Bundle bundle = intent.getExtras();
            Log.i(TAG, "honor enter activity, intent bundle: " + bundle.toString());
            Set<String> set = bundle.keySet();
            if (set != null) {
                for (String key : set) {
                    // 其中 key 和 value 分别为发送端设置的 extKey 和 ext content
                    String value = bundle.getString(key);
                    keyMap.put(key, value);
                    Log.i("oppo push custom data", "key = " + key + ":value = " + value);
                }
            }
        } catch (Exception e) {

        }

        finish();

        Intent intentLaunchMain = this.getPackageManager().getLaunchIntentForPackage(this.getPackageName());
        this.startActivity(intentLaunchMain);

        Timer timer = new Timer();

        Log.i(TAG, "invokeClickListener");

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "Checking instance isInitialized");
                try {
                    TimUiKitPushPlugin.instance.toFlutterMethod(Extras.PUSH_CLICK_ACTION, keyMap);
                    cancel();
                } catch (Exception e) {

                }
            }
        }, 100, 500);
    }
}
