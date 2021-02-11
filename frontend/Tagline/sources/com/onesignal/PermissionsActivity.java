package com.onesignal;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import com.onesignal.ActivityLifecycleHandler;
import com.onesignal.AndroidSupportV4Compat;

public class PermissionsActivity extends Activity {
    private static final int DELAY_TIME_CALLBACK_CALL = 500;
    private static final int REQUEST_LOCATION = 2;
    private static final String TAG = PermissionsActivity.class.getCanonicalName();
    private static ActivityLifecycleHandler.ActivityAvailableListener activityAvailableListener;
    static boolean answered;
    static boolean waiting;

    public PermissionsActivity() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        Bundle savedInstanceState = bundle;
        super.onCreate(savedInstanceState);
        OneSignal.setAppContext(this);
        if (savedInstanceState == null || !savedInstanceState.getBoolean("android:hasCurrentPermissionsRequest", false)) {
            requestPermission();
        } else {
            waiting = true;
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (OneSignal.isInitDone()) {
            requestPermission();
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            finish();
            overridePendingTransition(C1238R.anim.onesignal_fade_in, C1238R.anim.onesignal_fade_out);
        } else if (!waiting) {
            waiting = true;
            AndroidSupportV4Compat.ActivityCompat.requestPermissions(this, new String[]{LocationGMS.requestPermission}, 2);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] strArr, @NonNull int[] iArr) {
        Handler handler;
        Runnable runnable;
        String[] strArr2 = strArr;
        int[] grantResults = iArr;
        answered = true;
        waiting = false;
        if (requestCode == 2) {
            new Handler();
            final int[] iArr2 = grantResults;
            new Runnable(this) {
                final /* synthetic */ PermissionsActivity this$0;

                {
                    this.this$0 = this$0;
                }

                public void run() {
                    boolean granted = iArr2.length > 0 && iArr2[0] == 0;
                    LocationGMS.sendAndClearPromptHandlers(true, granted);
                    if (granted) {
                        LocationGMS.startGetLocation();
                    } else {
                        LocationGMS.fireFailedComplete();
                    }
                }
            };
            boolean postDelayed = handler.postDelayed(runnable, 500);
        }
        ActivityLifecycleHandler.removeActivityAvailableListener(TAG);
        finish();
        overridePendingTransition(C1238R.anim.onesignal_fade_in, C1238R.anim.onesignal_fade_out);
    }

    static void startPrompt() {
        ActivityLifecycleHandler.ActivityAvailableListener activityAvailableListener2;
        if (!waiting && !answered) {
            new ActivityLifecycleHandler.ActivityAvailableListener() {
                public void available(@NonNull Activity activity) {
                    Intent intent;
                    Activity activity2 = activity;
                    if (!activity2.getClass().equals(PermissionsActivity.class)) {
                        new Intent(activity2, PermissionsActivity.class);
                        Intent intent2 = intent;
                        Intent flags = intent2.setFlags(131072);
                        activity2.startActivity(intent2);
                        activity2.overridePendingTransition(C1238R.anim.onesignal_fade_in, C1238R.anim.onesignal_fade_out);
                    }
                }
            };
            activityAvailableListener = activityAvailableListener2;
            ActivityLifecycleHandler.setActivityAvailableListener(TAG, activityAvailableListener);
        }
    }
}
