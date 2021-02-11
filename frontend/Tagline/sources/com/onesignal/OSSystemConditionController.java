package com.onesignal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.p000v4.app.DialogFragment;
import android.support.p000v4.app.Fragment;
import android.support.p000v4.app.FragmentManager;
import android.support.p003v7.app.AppCompatActivity;
import com.onesignal.OneSignal;
import java.lang.ref.WeakReference;
import java.util.List;

class OSSystemConditionController {
    private static final String TAG = OSSystemConditionController.class.getCanonicalName();
    /* access modifiers changed from: private */
    public final OSSystemConditionObserver systemConditionObserver;

    interface OSSystemConditionObserver {
        void systemConditionChanged();
    }

    OSSystemConditionController(OSSystemConditionObserver systemConditionObserver2) {
        this.systemConditionObserver = systemConditionObserver2;
    }

    /* access modifiers changed from: package-private */
    public boolean systemConditionsAvailable() {
        StringBuilder sb;
        WeakReference weakReference;
        boolean z;
        if (ActivityLifecycleHandler.curActivity == null) {
            OneSignal.onesignalLog(OneSignal.LOG_LEVEL.WARN, "OSSystemConditionObserver curActivity null");
            return false;
        }
        try {
            if (isDialogFragmentShowing(ActivityLifecycleHandler.curActivity)) {
                OneSignal.onesignalLog(OneSignal.LOG_LEVEL.WARN, "OSSystemConditionObserver dialog fragment detected");
                return false;
            }
        } catch (NoClassDefFoundError e) {
            NoClassDefFoundError exception = e;
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.INFO;
            new StringBuilder();
            OneSignal.onesignalLog(log_level, sb.append("AppCompatActivity is not used in this app, skipping 'isDialogFragmentShowing' check: ").append(exception).toString());
        }
        new WeakReference(ActivityLifecycleHandler.curActivity);
        boolean keyboardUp = OSViewUtils.isKeyboardUp(weakReference);
        if (keyboardUp) {
            ActivityLifecycleHandler.setSystemConditionObserver(TAG, this.systemConditionObserver);
            OneSignal.onesignalLog(OneSignal.LOG_LEVEL.WARN, "OSSystemConditionObserver keyboard up detected");
        }
        if (!keyboardUp) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    public boolean isDialogFragmentShowing(Context context) throws NoClassDefFoundError {
        FragmentManager.FragmentLifecycleCallbacks fragmentLifecycleCallbacks;
        Context context2 = context;
        if (context2 instanceof AppCompatActivity) {
            FragmentManager manager = ((AppCompatActivity) context2).getSupportFragmentManager();
            final FragmentManager fragmentManager = manager;
            new FragmentManager.FragmentLifecycleCallbacks(this) {
                final /* synthetic */ OSSystemConditionController this$0;

                {
                    this.this$0 = this$0;
                }

                public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment fragment) {
                    Fragment fragmentDetached = fragment;
                    super.onFragmentDetached(fm, fragmentDetached);
                    if (fragmentDetached instanceof DialogFragment) {
                        fragmentManager.unregisterFragmentLifecycleCallbacks(this);
                        this.this$0.systemConditionObserver.systemConditionChanged();
                    }
                }
            };
            manager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true);
            List<Fragment> fragments = manager.getFragments();
            int size = fragments.size();
            if (size > 0) {
                Fragment fragment = fragments.get(size - 1);
                return fragment.isVisible() && (fragment instanceof DialogFragment);
            }
        }
        return false;
    }
}
