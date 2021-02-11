package com.onesignal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.ViewTreeObserver;
import com.microsoft.appcenter.Constants;
import com.onesignal.OSSystemConditionController;
import com.onesignal.OneSignal;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ActivityLifecycleHandler {
    @SuppressLint({"StaticFieldLeak"})
    static Activity curActivity;
    static FocusHandlerThread focusHandlerThread;
    static boolean nextResumeIsFirstActivity;
    private static Map<String, ActivityAvailableListener> sActivityAvailableListeners;
    private static Map<String, KeyboardListener> sKeyboardListeners;
    private static Map<String, OSSystemConditionController.OSSystemConditionObserver> sSystemConditionObservers;

    ActivityLifecycleHandler() {
    }

    static abstract class ActivityAvailableListener {
        ActivityAvailableListener() {
        }

        /* access modifiers changed from: package-private */
        public void available(@NonNull Activity activity) {
        }

        /* access modifiers changed from: package-private */
        public void stopped(WeakReference<Activity> weakReference) {
        }
    }

    static {
        Map<String, ActivityAvailableListener> map;
        Map<String, OSSystemConditionController.OSSystemConditionObserver> map2;
        Map<String, KeyboardListener> map3;
        FocusHandlerThread focusHandlerThread2;
        new ConcurrentHashMap();
        sActivityAvailableListeners = map;
        new ConcurrentHashMap();
        sSystemConditionObservers = map2;
        new ConcurrentHashMap();
        sKeyboardListeners = map3;
        new FocusHandlerThread();
        focusHandlerThread = focusHandlerThread2;
    }

    static void setSystemConditionObserver(String str, OSSystemConditionController.OSSystemConditionObserver oSSystemConditionObserver) {
        KeyboardListener keyboardListener;
        String key = str;
        OSSystemConditionController.OSSystemConditionObserver systemConditionObserver = oSSystemConditionObserver;
        if (curActivity != null) {
            ViewTreeObserver treeObserver = curActivity.getWindow().getDecorView().getViewTreeObserver();
            new KeyboardListener(systemConditionObserver, key, (C13941) null);
            KeyboardListener keyboardListener2 = keyboardListener;
            treeObserver.addOnGlobalLayoutListener(keyboardListener2);
            KeyboardListener put = sKeyboardListeners.put(key, keyboardListener2);
        }
        OSSystemConditionController.OSSystemConditionObserver put2 = sSystemConditionObservers.put(key, systemConditionObserver);
    }

    static void setActivityAvailableListener(String key, ActivityAvailableListener activityAvailableListener) {
        ActivityAvailableListener activityAvailableListener2 = activityAvailableListener;
        ActivityAvailableListener put = sActivityAvailableListeners.put(key, activityAvailableListener2);
        if (curActivity != null) {
            activityAvailableListener2.available(curActivity);
        }
    }

    static void removeSystemConditionObserver(String str) {
        String key = str;
        KeyboardListener remove = sKeyboardListeners.remove(key);
        OSSystemConditionController.OSSystemConditionObserver remove2 = sSystemConditionObservers.remove(key);
    }

    static void removeActivityAvailableListener(String key) {
        ActivityAvailableListener remove = sActivityAvailableListeners.remove(key);
    }

    private static void setCurActivity(Activity activity) {
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
        curActivity = activity;
        for (Map.Entry<String, ActivityAvailableListener> value : sActivityAvailableListeners.entrySet()) {
            ((ActivityAvailableListener) value.getValue()).available(curActivity);
        }
        try {
            ViewTreeObserver treeObserver = curActivity.getWindow().getDecorView().getViewTreeObserver();
            for (Map.Entry<String, OSSystemConditionController.OSSystemConditionObserver> entry : sSystemConditionObservers.entrySet()) {
                new KeyboardListener(entry.getValue(), entry.getKey(), (C13941) null);
                ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener2 = onGlobalLayoutListener;
                treeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener2);
                KeyboardListener put = sKeyboardListeners.put(entry.getKey(), onGlobalLayoutListener2);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    static void onConfigurationChanged(Configuration configuration) {
        Configuration newConfig = configuration;
        if (curActivity != null && OSUtils.hasConfigChangeFlag(curActivity, 128)) {
            logOrientationChange(newConfig.orientation);
            onOrientationChanged();
        }
    }

    static void onActivityCreated(Activity activity) {
    }

    static void onActivityStarted(Activity activity) {
    }

    static void onActivityResumed(Activity activity) {
        setCurActivity(activity);
        logCurActivity();
        handleFocus();
    }

    static void onActivityPaused(Activity activity) {
        if (activity == curActivity) {
            curActivity = null;
            handleLostFocus();
        }
        logCurActivity();
    }

    static void onActivityStopped(Activity activity) {
        StringBuilder sb;
        WeakReference weakReference;
        Activity activity2 = activity;
        OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
        new StringBuilder();
        OneSignal.Log(log_level, sb.append("onActivityStopped: ").append(activity2).toString());
        if (activity2 == curActivity) {
            curActivity = null;
            handleLostFocus();
        }
        for (Map.Entry<String, ActivityAvailableListener> value : sActivityAvailableListeners.entrySet()) {
            new WeakReference(activity2);
            ((ActivityAvailableListener) value.getValue()).stopped(weakReference);
        }
        logCurActivity();
    }

    static void onActivityDestroyed(Activity activity) {
        StringBuilder sb;
        Activity activity2 = activity;
        OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
        new StringBuilder();
        OneSignal.Log(log_level, sb.append("onActivityDestroyed: ").append(activity2).toString());
        sKeyboardListeners.clear();
        if (activity2 == curActivity) {
            curActivity = null;
            handleLostFocus();
        }
        logCurActivity();
    }

    private static void logCurActivity() {
        StringBuilder sb;
        String str;
        StringBuilder sb2;
        OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
        new StringBuilder();
        StringBuilder append = sb.append("curActivity is NOW: ");
        if (curActivity != null) {
            new StringBuilder();
            str = sb2.append("").append(curActivity.getClass().getName()).append(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR).append(curActivity).toString();
        } else {
            str = "null";
        }
        OneSignal.Log(log_level, append.append(str).toString());
    }

    private static void logOrientationChange(int i) {
        StringBuilder sb;
        StringBuilder sb2;
        int orientation = i;
        if (orientation == 2) {
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.onesignalLog(log_level, sb2.append("Configuration Orientation Change: LANDSCAPE (").append(orientation).append(")").toString());
        } else if (orientation == 1) {
            OneSignal.LOG_LEVEL log_level2 = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.onesignalLog(log_level2, sb.append("Configuration Orientation Change: PORTRAIT (").append(orientation).append(")").toString());
        }
    }

    private static void onOrientationChanged() {
        KeyboardListener keyboardListener;
        WeakReference weakReference;
        handleLostFocus();
        for (Map.Entry<String, ActivityAvailableListener> value : sActivityAvailableListeners.entrySet()) {
            new WeakReference(curActivity);
            ((ActivityAvailableListener) value.getValue()).stopped(weakReference);
        }
        for (Map.Entry<String, ActivityAvailableListener> value2 : sActivityAvailableListeners.entrySet()) {
            ((ActivityAvailableListener) value2.getValue()).available(curActivity);
        }
        ViewTreeObserver treeObserver = curActivity.getWindow().getDecorView().getViewTreeObserver();
        for (Map.Entry<String, OSSystemConditionController.OSSystemConditionObserver> entry : sSystemConditionObservers.entrySet()) {
            new KeyboardListener(entry.getValue(), entry.getKey(), (C13941) null);
            KeyboardListener keyboardListener2 = keyboardListener;
            treeObserver.addOnGlobalLayoutListener(keyboardListener2);
            KeyboardListener put = sKeyboardListeners.put(entry.getKey(), keyboardListener2);
        }
        handleFocus();
    }

    private static void handleLostFocus() {
        AppFocusRunnable appFocusRunnable;
        new AppFocusRunnable((C13941) null);
        focusHandlerThread.runRunnable(appFocusRunnable);
    }

    private static void handleFocus() {
        if (focusHandlerThread.hasBackgrounded() || nextResumeIsFirstActivity) {
            nextResumeIsFirstActivity = false;
            focusHandlerThread.resetBackgroundState();
            OneSignal.onAppFocus();
            return;
        }
        focusHandlerThread.stopScheduledRunnable();
    }

    static class FocusHandlerThread extends HandlerThread {
        private AppFocusRunnable appFocusRunnable;
        private Handler mHandler;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        FocusHandlerThread() {
            super("FocusHandlerThread");
            Handler handler;
            start();
            new Handler(getLooper());
            this.mHandler = handler;
        }

        /* access modifiers changed from: package-private */
        public Looper getHandlerLooper() {
            return this.mHandler.getLooper();
        }

        /* access modifiers changed from: package-private */
        public void resetBackgroundState() {
            if (this.appFocusRunnable != null) {
                boolean access$202 = AppFocusRunnable.access$202(this.appFocusRunnable, false);
            }
        }

        /* access modifiers changed from: package-private */
        public void stopScheduledRunnable() {
            this.mHandler.removeCallbacksAndMessages((Object) null);
        }

        /* access modifiers changed from: package-private */
        public void runRunnable(AppFocusRunnable appFocusRunnable2) {
            AppFocusRunnable runnable = appFocusRunnable2;
            if (this.appFocusRunnable == null || !this.appFocusRunnable.backgrounded || this.appFocusRunnable.completed) {
                this.appFocusRunnable = runnable;
                this.mHandler.removeCallbacksAndMessages((Object) null);
                boolean postDelayed = this.mHandler.postDelayed(runnable, 2000);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean hasBackgrounded() {
            return this.appFocusRunnable != null && this.appFocusRunnable.backgrounded;
        }
    }

    private static class AppFocusRunnable implements Runnable {
        /* access modifiers changed from: private */
        public boolean backgrounded;
        /* access modifiers changed from: private */
        public boolean completed;

        private AppFocusRunnable() {
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ AppFocusRunnable(C13941 r4) {
            this();
            C13941 r1 = r4;
        }

        static /* synthetic */ boolean access$202(AppFocusRunnable x0, boolean x1) {
            boolean z = x1;
            boolean z2 = z;
            x0.backgrounded = z2;
            return z;
        }

        public void run() {
            if (ActivityLifecycleHandler.curActivity == null) {
                this.backgrounded = true;
                OneSignal.onAppLostFocus();
                this.completed = true;
            }
        }
    }

    private static class KeyboardListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private final String key;
        private final OSSystemConditionController.OSSystemConditionObserver observer;

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ KeyboardListener(OSSystemConditionController.OSSystemConditionObserver x0, String x1, C13941 r10) {
            this(x0, x1);
            C13941 r3 = r10;
        }

        private KeyboardListener(OSSystemConditionController.OSSystemConditionObserver observer2, String key2) {
            this.observer = observer2;
            this.key = key2;
        }

        public void onGlobalLayout() {
            WeakReference weakReference;
            new WeakReference(ActivityLifecycleHandler.curActivity);
            if (!OSViewUtils.isKeyboardUp(weakReference)) {
                if (ActivityLifecycleHandler.curActivity != null) {
                    ViewTreeObserver treeObserver = ActivityLifecycleHandler.curActivity.getWindow().getDecorView().getViewTreeObserver();
                    if (Build.VERSION.SDK_INT < 16) {
                        treeObserver.removeGlobalOnLayoutListener(this);
                    } else {
                        treeObserver.removeOnGlobalLayoutListener(this);
                    }
                }
                ActivityLifecycleHandler.removeSystemConditionObserver(this.key);
                this.observer.systemConditionChanged();
            }
        }
    }
}
