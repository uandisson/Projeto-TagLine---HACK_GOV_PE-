package com.onesignal;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {
    @Nullable
    private static ComponentCallbacks configuration;
    @Nullable
    private static ActivityLifecycleListener instance;

    ActivityLifecycleListener() {
    }

    static void registerActivityLifecycleCallbacks(@NonNull Application application) {
        ComponentCallbacks componentCallbacks;
        ActivityLifecycleListener activityLifecycleListener;
        Application application2 = application;
        if (instance == null) {
            new ActivityLifecycleListener();
            instance = activityLifecycleListener;
            application2.registerActivityLifecycleCallbacks(instance);
        }
        if (configuration == null) {
            new ComponentCallbacks() {
                public void onConfigurationChanged(Configuration newConfig) {
                    ActivityLifecycleHandler.onConfigurationChanged(newConfig);
                }

                public void onLowMemory() {
                }
            };
            configuration = componentCallbacks;
            application2.registerComponentCallbacks(configuration);
        }
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        Bundle bundle2 = bundle;
        ActivityLifecycleHandler.onActivityCreated(activity);
    }

    public void onActivityStarted(Activity activity) {
        ActivityLifecycleHandler.onActivityStarted(activity);
    }

    public void onActivityResumed(Activity activity) {
        ActivityLifecycleHandler.onActivityResumed(activity);
    }

    public void onActivityPaused(Activity activity) {
        ActivityLifecycleHandler.onActivityPaused(activity);
    }

    public void onActivityStopped(Activity activity) {
        ActivityLifecycleHandler.onActivityStopped(activity);
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public void onActivityDestroyed(Activity activity) {
        ActivityLifecycleHandler.onActivityDestroyed(activity);
    }
}
