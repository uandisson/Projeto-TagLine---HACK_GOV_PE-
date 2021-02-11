package com.onesignal;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.google.appinventor.components.common.ComponentDescriptorConstants;
import com.google.appinventor.components.runtime.util.GoogleMapStyleOptions;
import com.onesignal.LocationGMS;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSSessionManager;
import com.onesignal.OneSignalDbContract;
import com.onesignal.OneSignalRemoteParams;
import com.onesignal.OneSignalRestClient;
import com.onesignal.PushRegistrator;
import com.onesignal.UserStateSynchronizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OneSignal {
    static final long MIN_ON_SESSION_TIME_MILLIS = 30000;
    public static final String VERSION = "031302";
    static Context appContext;
    @NonNull
    private static AppEntryAction appEntryState = AppEntryAction.APP_CLOSE;
    @Nullable
    static String appId;
    private static OSEmailSubscriptionState currentEmailSubscriptionState;
    private static OSPermissionState currentPermissionState;
    private static OSSubscriptionState currentSubscriptionState;
    static DelayedConsentInitializationParameters delayedInitParams;
    private static String emailId = null;
    private static EmailUpdateHandler emailLogoutHandler;
    private static OSObservable<OSEmailSubscriptionObserver, OSEmailSubscriptionStateChanges> emailSubscriptionStateChangesObserver;
    private static EmailUpdateHandler emailUpdateHandler;
    private static boolean foreground;
    /* access modifiers changed from: private */
    public static boolean getTagsCall;
    private static IAPUpdateJob iapUpdateJob;
    private static IdsAvailableHandler idsAvailableHandler;
    private static boolean initDone;
    static OSEmailSubscriptionState lastEmailSubscriptionState;
    private static LocationGMS.LocationPoint lastLocationPoint;
    static OSPermissionState lastPermissionState;
    private static String lastRegistrationId;
    static OSSubscriptionState lastSubscriptionState;
    static AtomicLong lastTaskId;
    private static boolean locationFired;
    private static LOG_LEVEL logCatLevel = LOG_LEVEL.WARN;
    private static String mGoogleProjectNumber;
    @NonNull
    static Builder mInitBuilder;
    private static PushRegistrator mPushRegistrator;
    private static AdvertisingIdentifierProvider mainAdIdProvider;
    @NonNull
    private static OSUtils osUtils;
    /* access modifiers changed from: private */
    @Nullable
    public static OutcomeEventsController outcomeEventsController;
    /* access modifiers changed from: private */
    public static ArrayList<GetTagsHandler> pendingGetTagsHandlers;
    static ExecutorService pendingTaskExecutor;
    private static OSObservable<OSPermissionObserver, OSPermissionStateChanges> permissionStateChangesObserver;
    private static HashSet<String> postedOpenedNotifIds;
    private static boolean promptedLocation;
    private static boolean registerForPushFired;
    static OneSignalRemoteParams.Params remoteParams;
    static boolean requiresUserPrivacyConsent = false;
    public static String sdkType = ComponentDescriptorConstants.NATIVE_TARGET;
    @Nullable
    private static OSSessionManager sessionManager;
    static boolean shareLocation = true;
    /* access modifiers changed from: private */
    public static int subscribableStatus;
    private static OSObservable<OSSubscriptionObserver, OSSubscriptionStateChanges> subscriptionStateChangesObserver;
    public static ConcurrentLinkedQueue<Runnable> taskQueueWaitingForInit;
    private static TrackAmazonPurchase trackAmazonPurchase;
    private static TrackFirebaseAnalytics trackFirebaseAnalytics;
    private static TrackGooglePurchase trackGooglePurchase;
    private static Collection<JSONArray> unprocessedOpenedNotifis;
    /* access modifiers changed from: private */
    public static String userId = null;
    private static LOG_LEVEL visualLogLevel = LOG_LEVEL.NONE;
    private static boolean waitingToPostStateSync;

    public interface ChangeTagsUpdateHandler {
        void onFailure(SendTagsError sendTagsError);

        void onSuccess(JSONObject jSONObject);
    }

    public enum EmailErrorType {
    }

    public interface EmailUpdateHandler {
        void onFailure(EmailUpdateError emailUpdateError);

        void onSuccess();
    }

    public interface GetTagsHandler {
        void tagsAvailable(JSONObject jSONObject);
    }

    public interface IdsAvailableHandler {
        void idsAvailable(String str, String str2);
    }

    public interface InAppMessageClickHandler {
        void inAppMessageClicked(OSInAppMessageAction oSInAppMessageAction);
    }

    public enum LOG_LEVEL {
    }

    public interface NotificationOpenedHandler {
        void notificationOpened(OSNotificationOpenResult oSNotificationOpenResult);
    }

    public interface NotificationReceivedHandler {
        void notificationReceived(OSNotification oSNotification);
    }

    public interface OSExternalUserIdUpdateCompletionHandler {
        void onComplete(JSONObject jSONObject);
    }

    public enum OSInFocusDisplayOption {
    }

    interface OSInternalExternalUserIdUpdateCompletionHandler {
        void onComplete(String str, boolean z);
    }

    interface OSPromptActionCompletionCallback {
        void completed(boolean z);
    }

    public interface OutcomeCallback {
        void onSuccess(@Nullable OutcomeEvent outcomeEvent);
    }

    public interface PostNotificationResponseHandler {
        void onFailure(JSONObject jSONObject);

        void onSuccess(JSONObject jSONObject);
    }

    public OneSignal() {
    }

    static /* synthetic */ String access$1102(String x0) {
        String x02 = x0;
        lastRegistrationId = x02;
        return x02;
    }

    static /* synthetic */ boolean access$1202(boolean x0) {
        boolean x02 = x0;
        registerForPushFired = x02;
        return x02;
    }

    static /* synthetic */ String access$1402(String x0) {
        String x02 = x0;
        mGoogleProjectNumber = x02;
        return x02;
    }

    static /* synthetic */ boolean access$2102(boolean x0) {
        boolean x02 = x0;
        getTagsCall = x02;
        return x02;
    }

    static /* synthetic */ boolean access$2302(boolean x0) {
        boolean x02 = x0;
        promptedLocation = x02;
        return x02;
    }

    static /* synthetic */ LocationGMS.LocationPoint access$602(LocationGMS.LocationPoint x0) {
        LocationGMS.LocationPoint x02 = x0;
        lastLocationPoint = x02;
        return x02;
    }

    static /* synthetic */ boolean access$702(boolean x0) {
        boolean x02 = x0;
        locationFired = x02;
        return x02;
    }

    static /* synthetic */ int access$902(int x0) {
        int x02 = x0;
        subscribableStatus = x02;
        return x02;
    }

    enum AppEntryAction {
        ;

        /* access modifiers changed from: package-private */
        public boolean isNotificationClick() {
            return equals(NOTIFICATION_CLICK);
        }

        /* access modifiers changed from: package-private */
        public boolean isAppOpen() {
            return equals(APP_OPEN);
        }

        /* access modifiers changed from: package-private */
        public boolean isAppClose() {
            return equals(APP_CLOSE);
        }
    }

    public static class SendTagsError {
        private int code;
        private String message;

        SendTagsError(int errorCode, String errorMessage) {
            this.message = errorMessage;
            this.code = errorCode;
        }

        public int getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }
    }

    public static class EmailUpdateError {
        private String message;
        private EmailErrorType type;

        EmailUpdateError(EmailErrorType type2, String message2) {
            this.type = type2;
            this.message = message2;
        }

        public EmailErrorType getType() {
            return this.type;
        }

        public String getMessage() {
            return this.message;
        }
    }

    public static class Builder {
        Context mContext;
        boolean mDisableGmsMissingPrompt;
        OSInFocusDisplayOption mDisplayOption;
        boolean mDisplayOptionCarryOver;
        boolean mFilterOtherGCMReceivers;
        InAppMessageClickHandler mInAppMessageClickHandler;
        NotificationOpenedHandler mNotificationOpenedHandler;
        NotificationReceivedHandler mNotificationReceivedHandler;
        boolean mPromptLocation;
        boolean mUnsubscribeWhenNotificationsAreDisabled;

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ Builder(Context x0, C14371 r7) {
            this(x0);
            C14371 r2 = r7;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ Builder(C14371 r4) {
            this();
            C14371 r1 = r4;
        }

        private Builder() {
            this.mDisplayOption = OSInFocusDisplayOption.InAppAlert;
        }

        private Builder(Context context) {
            this.mDisplayOption = OSInFocusDisplayOption.InAppAlert;
            this.mContext = context;
        }

        private void setDisplayOptionCarryOver(boolean carryOver) {
            boolean z = carryOver;
            this.mDisplayOptionCarryOver = z;
        }

        public Builder setNotificationOpenedHandler(NotificationOpenedHandler handler) {
            this.mNotificationOpenedHandler = handler;
            return this;
        }

        public Builder setNotificationReceivedHandler(NotificationReceivedHandler handler) {
            this.mNotificationReceivedHandler = handler;
            return this;
        }

        public Builder setInAppMessageClickHandler(InAppMessageClickHandler handler) {
            this.mInAppMessageClickHandler = handler;
            return this;
        }

        public Builder autoPromptLocation(boolean enable) {
            this.mPromptLocation = enable;
            return this;
        }

        public Builder disableGmsMissingPrompt(boolean disable) {
            this.mDisableGmsMissingPrompt = disable;
            return this;
        }

        public Builder inFocusDisplaying(OSInFocusDisplayOption displayOption) {
            this.mDisplayOptionCarryOver = false;
            this.mDisplayOption = displayOption;
            return this;
        }

        public Builder unsubscribeWhenNotificationsAreDisabled(boolean set) {
            this.mUnsubscribeWhenNotificationsAreDisabled = set;
            return this;
        }

        public Builder filterOtherGCMReceivers(boolean set) {
            this.mFilterOtherGCMReceivers = set;
            return this;
        }

        public void init() {
            OneSignal.init(this);
        }
    }

    static {
        ConcurrentLinkedQueue<Runnable> concurrentLinkedQueue;
        AtomicLong atomicLong;
        AdvertisingIdentifierProvider advertisingIdentifierProvider;
        OSUtils oSUtils;
        Builder builder;
        Collection<JSONArray> collection;
        HashSet<String> hashSet;
        ArrayList<GetTagsHandler> arrayList;
        new ConcurrentLinkedQueue<>();
        taskQueueWaitingForInit = concurrentLinkedQueue;
        new AtomicLong();
        lastTaskId = atomicLong;
        new AdvertisingIdProviderGPS();
        mainAdIdProvider = advertisingIdentifierProvider;
        new OSUtils();
        osUtils = oSUtils;
        new Builder((C14371) null);
        mInitBuilder = builder;
        new ArrayList();
        unprocessedOpenedNotifis = collection;
        new HashSet<>();
        postedOpenedNotifIds = hashSet;
        new ArrayList<>();
        pendingGetTagsHandlers = arrayList;
    }

    static boolean isInitDone() {
        return initDone;
    }

    static boolean isForeground() {
        return foreground;
    }

    @NonNull
    static AppEntryAction getAppEntryState() {
        return appEntryState;
    }

    private static OSSessionManager.SessionListener getNewSessionListener() {
        OSSessionManager.SessionListener sessionListener;
        OSSessionManager.SessionListener sessionListener2 = sessionListener;
        new OSSessionManager.SessionListener() {
            public void onSessionEnding(@NonNull OSSessionManager.SessionResult lastSessionResult) {
                OneSignal.outcomeEventsController.cleanOutcomes();
                FocusTimeController.getInstance().onSessionEnded(lastSessionResult);
            }
        };
        return sessionListener2;
    }

    private static OSPermissionState getCurrentPermissionState(Context context) {
        OSPermissionState oSPermissionState;
        Object obj;
        if (context == null) {
            return null;
        }
        if (currentPermissionState == null) {
            new OSPermissionState(false);
            currentPermissionState = oSPermissionState;
            new OSPermissionChangedInternalObserver();
            currentPermissionState.observable.addObserverStrong(obj);
        }
        return currentPermissionState;
    }

    private static OSPermissionState getLastPermissionState(Context context) {
        OSPermissionState oSPermissionState;
        if (context == null) {
            return null;
        }
        if (lastPermissionState == null) {
            new OSPermissionState(true);
            lastPermissionState = oSPermissionState;
        }
        return lastPermissionState;
    }

    static OSObservable<OSPermissionObserver, OSPermissionStateChanges> getPermissionStateChangesObserver() {
        OSObservable<OSPermissionObserver, OSPermissionStateChanges> oSObservable;
        if (permissionStateChangesObserver == null) {
            new OSObservable<>("onOSPermissionChanged", true);
            permissionStateChangesObserver = oSObservable;
        }
        return permissionStateChangesObserver;
    }

    /* access modifiers changed from: private */
    public static OSSubscriptionState getCurrentSubscriptionState(Context context) {
        OSSubscriptionState oSSubscriptionState;
        Object obj;
        Context context2 = context;
        if (context2 == null) {
            return null;
        }
        if (currentSubscriptionState == null) {
            new OSSubscriptionState(false, getCurrentPermissionState(context2).getEnabled());
            currentSubscriptionState = oSSubscriptionState;
            getCurrentPermissionState(context2).observable.addObserver(currentSubscriptionState);
            new OSSubscriptionChangedInternalObserver();
            currentSubscriptionState.observable.addObserverStrong(obj);
        }
        return currentSubscriptionState;
    }

    private static OSSubscriptionState getLastSubscriptionState(Context context) {
        OSSubscriptionState oSSubscriptionState;
        if (context == null) {
            return null;
        }
        if (lastSubscriptionState == null) {
            new OSSubscriptionState(true, false);
            lastSubscriptionState = oSSubscriptionState;
        }
        return lastSubscriptionState;
    }

    static OSObservable<OSSubscriptionObserver, OSSubscriptionStateChanges> getSubscriptionStateChangesObserver() {
        OSObservable<OSSubscriptionObserver, OSSubscriptionStateChanges> oSObservable;
        if (subscriptionStateChangesObserver == null) {
            new OSObservable<>("onOSSubscriptionChanged", true);
            subscriptionStateChangesObserver = oSObservable;
        }
        return subscriptionStateChangesObserver;
    }

    /* access modifiers changed from: private */
    public static OSEmailSubscriptionState getCurrentEmailSubscriptionState(Context context) {
        OSEmailSubscriptionState oSEmailSubscriptionState;
        Object obj;
        if (context == null) {
            return null;
        }
        if (currentEmailSubscriptionState == null) {
            new OSEmailSubscriptionState(false);
            currentEmailSubscriptionState = oSEmailSubscriptionState;
            new OSEmailSubscriptionChangedInternalObserver();
            currentEmailSubscriptionState.observable.addObserverStrong(obj);
        }
        return currentEmailSubscriptionState;
    }

    private static OSEmailSubscriptionState getLastEmailSubscriptionState(Context context) {
        OSEmailSubscriptionState oSEmailSubscriptionState;
        if (context == null) {
            return null;
        }
        if (lastEmailSubscriptionState == null) {
            new OSEmailSubscriptionState(true);
            lastEmailSubscriptionState = oSEmailSubscriptionState;
        }
        return lastEmailSubscriptionState;
    }

    static OSObservable<OSEmailSubscriptionObserver, OSEmailSubscriptionStateChanges> getEmailSubscriptionStateChangesObserver() {
        OSObservable<OSEmailSubscriptionObserver, OSEmailSubscriptionStateChanges> oSObservable;
        if (emailSubscriptionStateChangesObserver == null) {
            new OSObservable<>("onOSEmailSubscriptionChanged", true);
            emailSubscriptionStateChangesObserver = oSObservable;
        }
        return emailSubscriptionStateChangesObserver;
    }

    private static class IAPUpdateJob {
        boolean newAsExisting;
        OneSignalRestClient.ResponseHandler restResponseHandler;
        JSONArray toReport;

        IAPUpdateJob(JSONArray toReport2) {
            this.toReport = toReport2;
        }
    }

    public static Builder getCurrentOrNewInitBuilder() {
        return mInitBuilder;
    }

    public static void setAppContext(@NonNull Context context) {
        OSSessionManager oSSessionManager;
        OutcomeEventsController outcomeEventsController2;
        Context context2 = context;
        if (context2 == null) {
            Log(LOG_LEVEL.WARN, "setAppContext(null) is not valid, ignoring!");
            return;
        }
        boolean wasAppContextNull = appContext == null;
        appContext = context2.getApplicationContext();
        ActivityLifecycleListener.registerActivityLifecycleCallbacks((Application) appContext);
        if (wasAppContextNull) {
            new OSSessionManager(getNewSessionListener());
            sessionManager = oSSessionManager;
            new OutcomeEventsController(sessionManager, getDBHelperInstance());
            outcomeEventsController = outcomeEventsController2;
            OneSignalPrefs.startDelayedWrite();
            OneSignalCacheCleaner.cleanOldCachedData(context2);
        }
    }

    static OneSignalDbHelper getDBHelperInstance() {
        return OneSignalDbHelper.getInstance(appContext);
    }

    public static Builder startInit(Context context) {
        Builder builder;
        new Builder(context, (C14371) null);
        return builder;
    }

    /* access modifiers changed from: private */
    public static void init(Builder builder) {
        Builder inBuilder = builder;
        if (mInitBuilder.mDisplayOptionCarryOver) {
            inBuilder.mDisplayOption = mInitBuilder.mDisplayOption;
        }
        mInitBuilder = inBuilder;
        Context context = mInitBuilder.mContext;
        mInitBuilder.mContext = null;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            String sender_id = bundle.getString("onesignal_google_project_number");
            if (sender_id != null && sender_id.length() > 4) {
                sender_id = sender_id.substring(4);
            }
            init(context, sender_id, bundle.getString("onesignal_app_id"), mInitBuilder.mNotificationOpenedHandler, mInitBuilder.mNotificationReceivedHandler);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void init(Context context, String googleProjectNumber, String oneSignalAppId) {
        init(context, googleProjectNumber, oneSignalAppId, (NotificationOpenedHandler) null, (NotificationReceivedHandler) null);
    }

    public static void init(Context context, String googleProjectNumber, String oneSignalAppId, NotificationOpenedHandler notificationOpenedHandler) {
        init(context, googleProjectNumber, oneSignalAppId, notificationOpenedHandler, (NotificationReceivedHandler) null);
    }

    public static void init(Context context, String str, String str2, NotificationOpenedHandler notificationOpenedHandler, NotificationReceivedHandler notificationReceivedHandler) {
        TrackFirebaseAnalytics trackFirebaseAnalytics2;
        TrackGooglePurchase trackGooglePurchase2;
        DelayedConsentInitializationParameters delayedConsentInitializationParameters;
        Context context2 = context;
        String googleProjectNumber = str;
        String oneSignalAppId = str2;
        NotificationOpenedHandler notificationOpenedHandler2 = notificationOpenedHandler;
        NotificationReceivedHandler notificationReceivedHandler2 = notificationReceivedHandler;
        mInitBuilder = createInitBuilder(notificationOpenedHandler2, notificationReceivedHandler2);
        setAppContext(context2);
        setupPrivacyConsent(context2);
        if (requiresUserPrivacyConsent()) {
            Log(LOG_LEVEL.VERBOSE, "OneSignal SDK initialization delayed, user privacy consent is set to required for this application.");
            new DelayedConsentInitializationParameters(context2, googleProjectNumber, oneSignalAppId, notificationOpenedHandler2, notificationReceivedHandler2);
            delayedInitParams = delayedConsentInitializationParameters;
            return;
        }
        mInitBuilder = createInitBuilder(notificationOpenedHandler2, notificationReceivedHandler2);
        if (!isGoogleProjectNumberRemote()) {
            mGoogleProjectNumber = googleProjectNumber;
        }
        subscribableStatus = osUtils.initializationChecker(context2, oneSignalAppId);
        if (!isSubscriptionStatusUninitializable()) {
            if (appId != null && !appId.equals(oneSignalAppId)) {
                initDone = false;
            }
            if (!initDone) {
                appId = oneSignalAppId;
                saveFilterOtherGCMReceivers(mInitBuilder.mFilterOtherGCMReceivers);
                handleActivityLifecycleHandler(context2);
                OneSignalStateSynchronizer.initUserState();
                handleAmazonPurchase();
                handleAppIdChange();
                OSPermissionChangedInternalObserver.handleInternalChanges(getCurrentPermissionState(appContext));
                doSessionInit();
                if (mInitBuilder.mNotificationOpenedHandler != null) {
                    fireCallbackForOpenedNotifications();
                }
                if (TrackGooglePurchase.CanTrack(appContext)) {
                    new TrackGooglePurchase(appContext);
                    trackGooglePurchase = trackGooglePurchase2;
                }
                if (TrackFirebaseAnalytics.CanTrack()) {
                    new TrackFirebaseAnalytics(appContext);
                    trackFirebaseAnalytics = trackFirebaseAnalytics2;
                }
                PushRegistratorFCM.disableFirebaseInstanceIdService(appContext);
                initDone = true;
                outcomeEventsController.sendSavedOutcomes();
                startPendingTasks();
            } else if (mInitBuilder.mNotificationOpenedHandler != null) {
                fireCallbackForOpenedNotifications();
            }
        }
    }

    private static void setupPrivacyConsent(Context context) {
        Context context2 = context;
        try {
            setRequiresUserPrivacyConsent("ENABLE".equalsIgnoreCase(context2.getPackageManager().getApplicationInfo(context2.getPackageName(), 128).metaData.getString("com.onesignal.PrivacyConsent")));
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static Builder createInitBuilder(NotificationOpenedHandler notificationOpenedHandler, NotificationReceivedHandler notificationReceivedHandler) {
        mInitBuilder.mDisplayOptionCarryOver = false;
        mInitBuilder.mNotificationOpenedHandler = notificationOpenedHandler;
        mInitBuilder.mNotificationReceivedHandler = notificationReceivedHandler;
        return mInitBuilder;
    }

    private static void handleAppIdChange() {
        String oldAppId = getSavedAppId();
        if (oldAppId == null) {
            BadgeCountUpdater.updateCount(0, appContext);
            saveAppId(appId);
        } else if (!oldAppId.equals(appId)) {
            Log(LOG_LEVEL.DEBUG, "APP ID changed, clearing user id as it is no longer valid.");
            saveAppId(appId);
            OneSignalStateSynchronizer.resetCurrentState();
            remoteParams = null;
        }
    }

    public static boolean userProvidedPrivacyConsent() {
        return getSavedUserConsentStatus();
    }

    private static boolean isGoogleProjectNumberRemote() {
        return (remoteParams == null || remoteParams.googleProjectNumber == null) ? false : true;
    }

    private static boolean isSubscriptionStatusUninitializable() {
        return subscribableStatus == -999;
    }

    private static void handleActivityLifecycleHandler(Context context) {
        Context context2 = context;
        foreground = isContextActivity(context2);
        if (foreground) {
            ActivityLifecycleHandler.curActivity = (Activity) context2;
            NotificationRestorer.asyncRestore(appContext);
            FocusTimeController.getInstance().appForegrounded();
            return;
        }
        ActivityLifecycleHandler.nextResumeIsFirstActivity = true;
    }

    private static void handleAmazonPurchase() {
        TrackAmazonPurchase trackAmazonPurchase2;
        try {
            Class<?> cls = Class.forName("com.amazon.device.iap.PurchasingListener");
            new TrackAmazonPurchase(appContext);
            trackAmazonPurchase = trackAmazonPurchase2;
        } catch (ClassNotFoundException e) {
            ClassNotFoundException classNotFoundException = e;
        }
    }

    private static void doSessionInit() {
        if (isPastOnSessionTime()) {
            OneSignalStateSynchronizer.setNewSession();
            if (foreground) {
                outcomeEventsController.cleanOutcomes();
                sessionManager.restartSessionIfNeeded();
            }
        } else if (foreground) {
            OSInAppMessageController.getController().initWithCachedInAppMessages();
            sessionManager.attemptSessionUpgrade();
        }
        if (foreground || !hasUserId()) {
            setLastSessionTime(System.currentTimeMillis());
            startRegistrationOrOnSession();
        }
    }

    private static boolean isContextActivity(Context context) {
        return context instanceof Activity;
    }

    /* access modifiers changed from: private */
    public static void onTaskRan(long taskId) {
        if (lastTaskId.get() == taskId) {
            Log(LOG_LEVEL.INFO, "Last Pending Task has ran, shutting down");
            pendingTaskExecutor.shutdown();
        }
    }

    private static class PendingTaskRunnable implements Runnable {
        private Runnable innerTask;
        /* access modifiers changed from: private */
        public long taskId;

        static /* synthetic */ long access$502(PendingTaskRunnable x0, long x1) {
            long j = x1;
            long j2 = j;
            x0.taskId = j2;
            return j;
        }

        PendingTaskRunnable(Runnable innerTask2) {
            this.innerTask = innerTask2;
        }

        public void run() {
            this.innerTask.run();
            OneSignal.onTaskRan(this.taskId);
        }
    }

    private static void startPendingTasks() {
        ThreadFactory threadFactory;
        if (!taskQueueWaitingForInit.isEmpty()) {
            new ThreadFactory() {
                public Thread newThread(@NonNull Runnable runnable) {
                    Thread thread;
                    StringBuilder sb;
                    new Thread(runnable);
                    Thread newThread = thread;
                    new StringBuilder();
                    newThread.setName(sb.append("OS_PENDING_EXECUTOR_").append(newThread.getId()).toString());
                    return newThread;
                }
            };
            pendingTaskExecutor = Executors.newSingleThreadExecutor(threadFactory);
            while (!taskQueueWaitingForInit.isEmpty()) {
                Future<?> submit = pendingTaskExecutor.submit(taskQueueWaitingForInit.poll());
            }
        }
    }

    private static void addTaskToQueue(PendingTaskRunnable pendingTaskRunnable) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        PendingTaskRunnable task = pendingTaskRunnable;
        long access$502 = PendingTaskRunnable.access$502(task, lastTaskId.incrementAndGet());
        if (pendingTaskExecutor == null) {
            LOG_LEVEL log_level = LOG_LEVEL.INFO;
            new StringBuilder();
            Log(log_level, sb3.append("Adding a task to the pending queue with ID: ").append(task.taskId).toString());
            boolean add = taskQueueWaitingForInit.add(task);
        } else if (!pendingTaskExecutor.isShutdown()) {
            LOG_LEVEL log_level2 = LOG_LEVEL.INFO;
            new StringBuilder();
            Log(log_level2, sb.append("Executor is still running, add to the executor with ID: ").append(task.taskId).toString());
            try {
                Future<?> submit = pendingTaskExecutor.submit(task);
            } catch (RejectedExecutionException e) {
                RejectedExecutionException e2 = e;
                LOG_LEVEL log_level3 = LOG_LEVEL.INFO;
                new StringBuilder();
                Log(log_level3, sb2.append("Executor is shutdown, running task manually with ID: ").append(task.taskId).toString());
                task.run();
                e2.printStackTrace();
            }
        }
    }

    private static boolean shouldRunTaskThroughQueue() {
        if (initDone && pendingTaskExecutor == null) {
            return false;
        }
        if (!initDone && pendingTaskExecutor == null) {
            return true;
        }
        if (pendingTaskExecutor == null || pendingTaskExecutor.isShutdown()) {
            return false;
        }
        return true;
    }

    private static void startRegistrationOrOnSession() {
        if (!waitingToPostStateSync) {
            waitingToPostStateSync = true;
            if (OneSignalStateSynchronizer.getSyncAsNewSession()) {
                locationFired = false;
            }
            startLocationUpdate();
            registerForPushFired = false;
            makeAndroidParamsRequest();
        }
    }

    private static void startLocationUpdate() {
        LocationGMS.LocationHandler locationHandler;
        new LocationGMS.LocationHandler() {
            public LocationGMS.PermissionType getType() {
                return LocationGMS.PermissionType.STARTUP;
            }

            public void complete(LocationGMS.LocationPoint point) {
                LocationGMS.LocationPoint access$602 = OneSignal.access$602(point);
                boolean access$702 = OneSignal.access$702(true);
                OneSignal.registerUser();
            }
        };
        LocationGMS.LocationHandler locationHandler2 = locationHandler;
        boolean doPrompt = mInitBuilder.mPromptLocation && !promptedLocation;
        promptedLocation = promptedLocation || mInitBuilder.mPromptLocation;
        LocationGMS.getLocation(appContext, doPrompt, locationHandler2);
    }

    private static PushRegistrator getPushRegistrator() {
        PushRegistrator pushRegistrator;
        PushRegistrator pushRegistrator2;
        PushRegistrator pushRegistrator3;
        if (mPushRegistrator != null) {
            return mPushRegistrator;
        }
        if (osUtils.getDeviceType() == 2) {
            new PushRegistratorADM();
            mPushRegistrator = pushRegistrator3;
        } else if (OSUtils.hasFCMLibrary()) {
            new PushRegistratorFCM();
            mPushRegistrator = pushRegistrator2;
        } else {
            new PushRegistratorGCM();
            mPushRegistrator = pushRegistrator;
        }
        return mPushRegistrator;
    }

    /* access modifiers changed from: private */
    public static void registerForPushToken() {
        PushRegistrator.RegisteredHandler registeredHandler;
        new PushRegistrator.RegisteredHandler() {
            public void complete(String str, int i) {
                String id = str;
                int status = i;
                if (status < 1) {
                    if (OneSignalStateSynchronizer.getRegistrationId() == null && (OneSignal.subscribableStatus == 1 || OneSignal.pushStatusRuntimeError(OneSignal.subscribableStatus))) {
                        int access$902 = OneSignal.access$902(status);
                    }
                } else if (OneSignal.pushStatusRuntimeError(OneSignal.subscribableStatus)) {
                    int access$9022 = OneSignal.access$902(status);
                }
                String access$1102 = OneSignal.access$1102(id);
                boolean access$1202 = OneSignal.access$1202(true);
                OneSignal.getCurrentSubscriptionState(OneSignal.appContext).setPushToken(id);
                OneSignal.registerUser();
            }
        };
        getPushRegistrator().registerForPush(appContext, mGoogleProjectNumber, registeredHandler);
    }

    /* access modifiers changed from: private */
    public static boolean pushStatusRuntimeError(int subscribableStatus2) {
        return subscribableStatus2 < -6;
    }

    private static void makeAndroidParamsRequest() {
        OneSignalRemoteParams.CallBack callBack;
        if (remoteParams != null) {
            registerForPushToken();
            return;
        }
        new OneSignalRemoteParams.CallBack() {
            public void complete(OneSignalRemoteParams.Params params) {
                OneSignalRemoteParams.Params params2 = params;
                OneSignal.remoteParams = params2;
                if (OneSignal.remoteParams.googleProjectNumber != null) {
                    String access$1402 = OneSignal.access$1402(OneSignal.remoteParams.googleProjectNumber);
                }
                OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_GT_FIREBASE_TRACKING_ENABLED, OneSignal.remoteParams.firebaseAnalytics);
                OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_RESTORE_TTL_FILTER, OneSignal.remoteParams.restoreTTLFilter);
                OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_CLEAR_GROUP_SUMMARY_CLICK, OneSignal.remoteParams.clearGroupOnSummaryClick);
                OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_RECEIVE_RECEIPTS_ENABLED, OneSignal.remoteParams.receiveReceiptEnabled);
                OutcomesUtils.saveOutcomesParams(params2.outcomesParams);
                NotificationChannelManager.processChannelList(OneSignal.appContext, params2.notificationChannels);
                OneSignal.registerForPushToken();
            }
        };
        OneSignalRemoteParams.makeAndroidParamsRequest(callBack);
    }

    private static void fireCallbackForOpenedNotifications() {
        for (JSONArray dataArray : unprocessedOpenedNotifis) {
            runNotificationOpenedCallback(dataArray, true, false);
        }
        unprocessedOpenedNotifis.clear();
    }

    public static void onesignalLog(LOG_LEVEL level, String message) {
        Log(level, message);
    }

    public static void provideUserConsent(boolean z) {
        boolean consent = z;
        boolean previousConsentStatus = userProvidedPrivacyConsent();
        saveUserConsentStatus(consent);
        if (!previousConsentStatus && consent && delayedInitParams != null) {
            init(delayedInitParams.context, delayedInitParams.googleProjectNumber, delayedInitParams.appId, delayedInitParams.openedHandler, delayedInitParams.receivedHandler);
            delayedInitParams = null;
        }
    }

    public static void setRequiresUserPrivacyConsent(boolean z) {
        boolean required = z;
        if (!requiresUserPrivacyConsent || required) {
            requiresUserPrivacyConsent = required;
        } else {
            Log(LOG_LEVEL.ERROR, "Cannot change requiresUserPrivacyConsent() from TRUE to FALSE");
        }
    }

    public static boolean requiresUserPrivacyConsent() {
        return requiresUserPrivacyConsent && !userProvidedPrivacyConsent();
    }

    static boolean shouldLogUserPrivacyConsentErrorMessageForMethodName(String str) {
        StringBuilder sb;
        String methodName = str;
        if (!requiresUserPrivacyConsent()) {
            return false;
        }
        if (methodName != null) {
            LOG_LEVEL log_level = LOG_LEVEL.WARN;
            new StringBuilder();
            Log(log_level, sb.append("Method ").append(methodName).append(" was called before the user provided privacy consent. Your application is set to require the user's privacy consent before the OneSignal SDK can be initialized. Please ensure the user has provided consent before calling this method. You can check the latest OneSignal consent status by calling OneSignal.userProvidedPrivacyConsent()").toString());
        }
        return true;
    }

    public static void setLogLevel(LOG_LEVEL inLogCatLevel, LOG_LEVEL inVisualLogLevel) {
        logCatLevel = inLogCatLevel;
        visualLogLevel = inVisualLogLevel;
    }

    public static void setLogLevel(int inLogCatLevel, int inVisualLogLevel) {
        setLogLevel(getLogLevel(inLogCatLevel), getLogLevel(inVisualLogLevel));
    }

    private static LOG_LEVEL getLogLevel(int i) {
        int level = i;
        switch (level) {
            case 0:
                return LOG_LEVEL.NONE;
            case 1:
                return LOG_LEVEL.FATAL;
            case 2:
                return LOG_LEVEL.ERROR;
            case 3:
                return LOG_LEVEL.WARN;
            case 4:
                return LOG_LEVEL.INFO;
            case 5:
                return LOG_LEVEL.DEBUG;
            case 6:
                return LOG_LEVEL.VERBOSE;
            default:
                if (level < 0) {
                    return LOG_LEVEL.NONE;
                }
                return LOG_LEVEL.VERBOSE;
        }
    }

    static boolean atLogLevel(LOG_LEVEL log_level) {
        LOG_LEVEL level = log_level;
        return level.compareTo(visualLogLevel) < 1 || level.compareTo(logCatLevel) < 1;
    }

    static void Log(@NonNull LOG_LEVEL level, @NonNull String message) {
        Log(level, message, (Throwable) null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: java.io.Writer} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v18, resolved type: java.io.StringWriter} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void Log(@android.support.annotation.NonNull com.onesignal.OneSignal.LOG_LEVEL r12, @android.support.annotation.NonNull java.lang.String r13, @android.support.annotation.Nullable java.lang.Throwable r14) {
        /*
            r0 = r12
            r1 = r13
            r2 = r14
            java.lang.String r7 = "OneSignal"
            r3 = r7
            r7 = r0
            com.onesignal.OneSignal$LOG_LEVEL r8 = logCatLevel
            int r7 = r7.compareTo(r8)
            r8 = 1
            if (r7 >= r8) goto L_0x001f
            r7 = r0
            com.onesignal.OneSignal$LOG_LEVEL r8 = com.onesignal.OneSignal.LOG_LEVEL.VERBOSE
            if (r7 != r8) goto L_0x00a7
            java.lang.String r7 = "OneSignal"
            r8 = r1
            r9 = r2
            int r7 = android.util.Log.v(r7, r8, r9)
        L_0x001f:
            r7 = r0
            com.onesignal.OneSignal$LOG_LEVEL r8 = visualLogLevel
            int r7 = r7.compareTo(r8)
            r8 = 1
            if (r7 >= r8) goto L_0x00a6
            android.app.Activity r7 = com.onesignal.ActivityLifecycleHandler.curActivity
            if (r7 == 0) goto L_0x00a6
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ec }
            r11 = r7
            r7 = r11
            r8 = r11
            r8.<init>()     // Catch:{ Throwable -> 0x00ec }
            r8 = r1
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x00ec }
            java.lang.String r8 = "\n"
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x00ec }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00ec }
            r4 = r7
            r7 = r2
            if (r7 == 0) goto L_0x0097
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ec }
            r11 = r7
            r7 = r11
            r8 = r11
            r8.<init>()     // Catch:{ Throwable -> 0x00ec }
            r8 = r4
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x00ec }
            r8 = r2
            java.lang.String r8 = r8.getMessage()     // Catch:{ Throwable -> 0x00ec }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x00ec }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00ec }
            r4 = r7
            java.io.StringWriter r7 = new java.io.StringWriter     // Catch:{ Throwable -> 0x00ec }
            r11 = r7
            r7 = r11
            r8 = r11
            r8.<init>()     // Catch:{ Throwable -> 0x00ec }
            r5 = r7
            java.io.PrintWriter r7 = new java.io.PrintWriter     // Catch:{ Throwable -> 0x00ec }
            r11 = r7
            r7 = r11
            r8 = r11
            r9 = r5
            r8.<init>(r9)     // Catch:{ Throwable -> 0x00ec }
            r6 = r7
            r7 = r2
            r8 = r6
            r7.printStackTrace(r8)     // Catch:{ Throwable -> 0x00ec }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ec }
            r11 = r7
            r7 = r11
            r8 = r11
            r8.<init>()     // Catch:{ Throwable -> 0x00ec }
            r8 = r4
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x00ec }
            r8 = r5
            java.lang.String r8 = r8.toString()     // Catch:{ Throwable -> 0x00ec }
            java.lang.StringBuilder r7 = r7.append(r8)     // Catch:{ Throwable -> 0x00ec }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00ec }
            r4 = r7
        L_0x0097:
            r7 = r4
            r5 = r7
            com.onesignal.OneSignal$6 r7 = new com.onesignal.OneSignal$6     // Catch:{ Throwable -> 0x00ec }
            r11 = r7
            r7 = r11
            r8 = r11
            r9 = r0
            r10 = r5
            r8.<init>(r9, r10)     // Catch:{ Throwable -> 0x00ec }
            com.onesignal.OSUtils.runOnMainUIThread(r7)     // Catch:{ Throwable -> 0x00ec }
        L_0x00a6:
            return
        L_0x00a7:
            r7 = r0
            com.onesignal.OneSignal$LOG_LEVEL r8 = com.onesignal.OneSignal.LOG_LEVEL.DEBUG
            if (r7 != r8) goto L_0x00b7
            java.lang.String r7 = "OneSignal"
            r8 = r1
            r9 = r2
            int r7 = android.util.Log.d(r7, r8, r9)
            goto L_0x001f
        L_0x00b7:
            r7 = r0
            com.onesignal.OneSignal$LOG_LEVEL r8 = com.onesignal.OneSignal.LOG_LEVEL.INFO
            if (r7 != r8) goto L_0x00c7
            java.lang.String r7 = "OneSignal"
            r8 = r1
            r9 = r2
            int r7 = android.util.Log.i(r7, r8, r9)
            goto L_0x001f
        L_0x00c7:
            r7 = r0
            com.onesignal.OneSignal$LOG_LEVEL r8 = com.onesignal.OneSignal.LOG_LEVEL.WARN
            if (r7 != r8) goto L_0x00d7
            java.lang.String r7 = "OneSignal"
            r8 = r1
            r9 = r2
            int r7 = android.util.Log.w(r7, r8, r9)
            goto L_0x001f
        L_0x00d7:
            r7 = r0
            com.onesignal.OneSignal$LOG_LEVEL r8 = com.onesignal.OneSignal.LOG_LEVEL.ERROR
            if (r7 == r8) goto L_0x00e1
            r7 = r0
            com.onesignal.OneSignal$LOG_LEVEL r8 = com.onesignal.OneSignal.LOG_LEVEL.FATAL
            if (r7 != r8) goto L_0x001f
        L_0x00e1:
            java.lang.String r7 = "OneSignal"
            r8 = r1
            r9 = r2
            int r7 = android.util.Log.e(r7, r8, r9)
            goto L_0x001f
        L_0x00ec:
            r7 = move-exception
            r4 = r7
            java.lang.String r7 = "OneSignal"
            java.lang.String r8 = "Error showing logging message."
            r9 = r4
            int r7 = android.util.Log.e(r7, r8, r9)
            goto L_0x00a6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OneSignal.Log(com.onesignal.OneSignal$LOG_LEVEL, java.lang.String, java.lang.Throwable):void");
    }

    static void logHttpError(String str, int i, Throwable th, String str2) {
        StringBuilder sb;
        StringBuilder sb2;
        String errorString = str;
        int statusCode = i;
        Throwable throwable = th;
        String errorResponse = str2;
        String jsonError = "";
        if (errorResponse != null && atLogLevel(LOG_LEVEL.INFO)) {
            new StringBuilder();
            jsonError = sb2.append("\n").append(errorResponse).append("\n").toString();
        }
        LOG_LEVEL log_level = LOG_LEVEL.WARN;
        new StringBuilder();
        Log(log_level, sb.append("HTTP code: ").append(statusCode).append(" ").append(errorString).append(jsonError).toString(), throwable);
    }

    @WorkerThread
    static void onAppLostFocus() {
        foreground = false;
        appEntryState = AppEntryAction.APP_CLOSE;
        setLastSessionTime(System.currentTimeMillis());
        LocationGMS.onFocusChange();
        if (initDone) {
            if (trackAmazonPurchase != null) {
                trackAmazonPurchase.checkListener();
            }
            if (appContext == null) {
                Log(LOG_LEVEL.ERROR, "Android Context not found, please call OneSignal.init when your app starts.");
                return;
            }
            FocusTimeController.getInstance().appBackgrounded();
            boolean scheduleSyncService = scheduleSyncService();
        }
    }

    private static boolean scheduleSyncService() {
        boolean unsyncedChanges = OneSignalStateSynchronizer.persist();
        if (unsyncedChanges) {
            OneSignalSyncServiceUtils.scheduleSyncTask(appContext);
        }
        return LocationGMS.scheduleUpdate(appContext) || unsyncedChanges;
    }

    static void onAppFocus() {
        foreground = true;
        if (!appEntryState.equals(AppEntryAction.NOTIFICATION_CLICK)) {
            appEntryState = AppEntryAction.APP_OPEN;
        }
        LocationGMS.onFocusChange();
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("onAppFocus") && !OSUtils.shouldLogMissingAppIdError(appId)) {
            FocusTimeController.getInstance().appForegrounded();
            doSessionInit();
            if (trackGooglePurchase != null) {
                trackGooglePurchase.trackIAP();
            }
            NotificationRestorer.asyncRestore(appContext);
            getCurrentPermissionState(appContext).refreshAsTo();
            if (trackFirebaseAnalytics != null && getFirebaseAnalyticsEnabled()) {
                trackFirebaseAnalytics.trackInfluenceOpenEvent();
            }
            OneSignalSyncServiceUtils.cancelSyncTask(appContext);
        }
    }

    static void addNetType(JSONObject jsonObj) {
        try {
            JSONObject put = jsonObj.put("net_type", osUtils.getNetType());
        } catch (Throwable th) {
            Throwable th2 = th;
        }
    }

    private static int getTimeZoneOffset() {
        Date date;
        TimeZone timezone = Calendar.getInstance().getTimeZone();
        int offset = timezone.getRawOffset();
        new Date();
        if (timezone.inDaylightTime(date)) {
            offset += timezone.getDSTSavings();
        }
        return offset / 1000;
    }

    /* access modifiers changed from: private */
    public static void registerUser() {
        StringBuilder sb;
        Thread thread;
        Runnable runnable;
        LOG_LEVEL log_level = LOG_LEVEL.DEBUG;
        new StringBuilder();
        Log(log_level, sb.append("registerUser:registerForPushFired:").append(registerForPushFired).append(", locationFired: ").append(locationFired).append(", remoteParams: ").append(remoteParams).append(", appId: ").append(appId).toString());
        if (registerForPushFired && locationFired && remoteParams != null && appId != null) {
            new Runnable() {
                public void run() {
                    try {
                        OneSignal.registerUserTask();
                        OneSignalChromeTabAndroidFrame.setup(OneSignal.appId, OneSignal.userId, AdvertisingIdProviderGPS.getLastValue());
                    } catch (JSONException e) {
                        OneSignal.Log(LOG_LEVEL.FATAL, "FATAL Error registering device!", e);
                    }
                }
            };
            new Thread(runnable, "OS_REG_USER");
            thread.start();
        }
    }

    /* access modifiers changed from: private */
    public static void registerUserTask() throws JSONException {
        JSONObject jSONObject;
        JSONObject jSONObject2;
        String packageName = appContext.getPackageName();
        PackageManager packageManager = appContext.getPackageManager();
        new JSONObject();
        JSONObject deviceInfo = jSONObject;
        JSONObject put = deviceInfo.put("app_id", getSavedAppId());
        String adId = mainAdIdProvider.getIdentifier(appContext);
        if (adId != null) {
            JSONObject put2 = deviceInfo.put("ad_id", adId);
        }
        JSONObject put3 = deviceInfo.put("device_os", Build.VERSION.RELEASE);
        JSONObject put4 = deviceInfo.put("timezone", getTimeZoneOffset());
        JSONObject put5 = deviceInfo.put("language", OSUtils.getCorrectedLanguage());
        JSONObject put6 = deviceInfo.put("sdk", VERSION);
        JSONObject put7 = deviceInfo.put("sdk_type", sdkType);
        JSONObject put8 = deviceInfo.put("android_package", packageName);
        JSONObject put9 = deviceInfo.put("device_model", Build.MODEL);
        try {
            JSONObject put10 = deviceInfo.put("game_version", packageManager.getPackageInfo(packageName, 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            PackageManager.NameNotFoundException nameNotFoundException = e;
        }
        JSONObject put11 = deviceInfo.put("net_type", osUtils.getNetType());
        JSONObject put12 = deviceInfo.put("carrier", osUtils.getCarrierName());
        JSONObject put13 = deviceInfo.put("rooted", RootToolsInternalMethods.isRooted());
        OneSignalStateSynchronizer.updateDeviceInfo(deviceInfo);
        new JSONObject();
        JSONObject pushState = jSONObject2;
        JSONObject put14 = pushState.put("identifier", lastRegistrationId);
        JSONObject put15 = pushState.put("subscribableStatus", subscribableStatus);
        JSONObject put16 = pushState.put("androidPermission", areNotificationsEnabledForSubscribedState());
        JSONObject put17 = pushState.put("device_type", osUtils.getDeviceType());
        OneSignalStateSynchronizer.updatePushState(pushState);
        if (shareLocation && lastLocationPoint != null) {
            OneSignalStateSynchronizer.updateLocation(lastLocationPoint);
        }
        OneSignalStateSynchronizer.readyToUpdate(true);
        waitingToPostStateSync = false;
    }

    @Deprecated
    public static void syncHashedEmail(String str) {
        Runnable runnable;
        PendingTaskRunnable pendingTaskRunnable;
        String email = str;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("SyncHashedEmail()") && OSUtils.isValidEmail(email)) {
            final String str2 = email;
            new Runnable() {
                public void run() {
                    OneSignalStateSynchronizer.syncHashedEmail(str2.trim().toLowerCase());
                }
            };
            Runnable runSyncHashedEmail = runnable;
            if (appContext == null || shouldRunTaskThroughQueue()) {
                Log(LOG_LEVEL.ERROR, "You should initialize OneSignal before calling syncHashedEmail! Moving this operation to a pending task queue.");
                new PendingTaskRunnable(runSyncHashedEmail);
                addTaskToQueue(pendingTaskRunnable);
                return;
            }
            runSyncHashedEmail.run();
        }
    }

    public static void setEmail(@NonNull String email, EmailUpdateHandler callback) {
        setEmail(email, (String) null, callback);
    }

    public static void setEmail(@NonNull String email) {
        setEmail(email, (String) null, (EmailUpdateHandler) null);
    }

    public static void setEmail(@NonNull String email, @Nullable String emailAuthHash) {
        setEmail(email, emailAuthHash, (EmailUpdateHandler) null);
    }

    public static void setEmail(@NonNull String str, @Nullable String str2, @Nullable EmailUpdateHandler emailUpdateHandler2) {
        Runnable runnable;
        PendingTaskRunnable pendingTaskRunnable;
        EmailUpdateError emailUpdateError;
        EmailUpdateError emailUpdateError2;
        String email = str;
        String emailAuthHash = str2;
        EmailUpdateHandler callback = emailUpdateHandler2;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("setEmail()")) {
            if (!OSUtils.isValidEmail(email)) {
                String errorMessage = "Email is invalid";
                if (callback != null) {
                    new EmailUpdateError(EmailErrorType.VALIDATION, errorMessage);
                    callback.onFailure(emailUpdateError2);
                }
                Log(LOG_LEVEL.ERROR, errorMessage);
            } else if (remoteParams == null || !remoteParams.useEmailAuth || emailAuthHash != null) {
                emailUpdateHandler = callback;
                final String str3 = email;
                final String str4 = emailAuthHash;
                new Runnable() {
                    public void run() {
                        String trimmedEmail = str3.trim();
                        String internalEmailAuthHash = str4;
                        if (internalEmailAuthHash != null) {
                            String lowerCase = internalEmailAuthHash.toLowerCase();
                        }
                        OneSignal.getCurrentEmailSubscriptionState(OneSignal.appContext).setEmailAddress(trimmedEmail);
                        OneSignalStateSynchronizer.setEmail(trimmedEmail.toLowerCase(), internalEmailAuthHash);
                    }
                };
                Runnable runSetEmail = runnable;
                if (appContext == null || shouldRunTaskThroughQueue()) {
                    Log(LOG_LEVEL.ERROR, "You should initialize OneSignal before calling setEmail! Moving this operation to a pending task queue.");
                    new PendingTaskRunnable(runSetEmail);
                    addTaskToQueue(pendingTaskRunnable);
                    return;
                }
                runSetEmail.run();
            } else {
                String errorMessage2 = "Email authentication (auth token) is set to REQUIRED for this application. Please provide an auth token from your backend server or change the setting in the OneSignal dashboard.";
                if (callback != null) {
                    new EmailUpdateError(EmailErrorType.REQUIRES_EMAIL_AUTH, errorMessage2);
                    callback.onFailure(emailUpdateError);
                }
                Log(LOG_LEVEL.ERROR, errorMessage2);
            }
        }
    }

    public static void logoutEmail() {
        logoutEmail((EmailUpdateHandler) null);
    }

    public static void logoutEmail(@Nullable EmailUpdateHandler emailUpdateHandler2) {
        Runnable runnable;
        PendingTaskRunnable pendingTaskRunnable;
        EmailUpdateError emailUpdateError;
        EmailUpdateHandler callback = emailUpdateHandler2;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("logoutEmail()")) {
            if (getEmailId() == null) {
                Object obj = "logoutEmail not valid as email was not set or already logged out!";
                if (callback != null) {
                    new EmailUpdateError(EmailErrorType.INVALID_OPERATION, "logoutEmail not valid as email was not set or already logged out!");
                    callback.onFailure(emailUpdateError);
                }
                Log(LOG_LEVEL.ERROR, "logoutEmail not valid as email was not set or already logged out!");
                return;
            }
            emailLogoutHandler = callback;
            new Runnable() {
                public void run() {
                    OneSignalStateSynchronizer.logoutEmail();
                }
            };
            Runnable emailLogout = runnable;
            if (appContext == null || shouldRunTaskThroughQueue()) {
                Log(LOG_LEVEL.ERROR, "You should initialize OneSignal before calling logoutEmail! Moving this operation to a pending task queue.");
                new PendingTaskRunnable(emailLogout);
                addTaskToQueue(pendingTaskRunnable);
                return;
            }
            emailLogout.run();
        }
    }

    public static void setExternalUserId(@NonNull String externalId) {
        setExternalUserId(externalId, (OSExternalUserIdUpdateCompletionHandler) null);
    }

    public static void setExternalUserId(@NonNull String str, @Nullable OSExternalUserIdUpdateCompletionHandler oSExternalUserIdUpdateCompletionHandler) {
        Runnable runnable;
        PendingTaskRunnable pendingTaskRunnable;
        String externalId = str;
        OSExternalUserIdUpdateCompletionHandler completionCallback = oSExternalUserIdUpdateCompletionHandler;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("setExternalUserId()")) {
            final String str2 = externalId;
            final OSExternalUserIdUpdateCompletionHandler oSExternalUserIdUpdateCompletionHandler2 = completionCallback;
            new Runnable() {
                public void run() {
                    StringBuilder sb;
                    if (str2 == null) {
                        OneSignal.Log(LOG_LEVEL.WARN, "External id can't be null, set an empty string to remove an external id");
                        return;
                    }
                    try {
                        OneSignalStateSynchronizer.setExternalUserId(str2, oSExternalUserIdUpdateCompletionHandler2);
                    } catch (JSONException e) {
                        JSONException exception = e;
                        String operation = str2.equals("") ? "remove" : "set";
                        LOG_LEVEL log_level = LOG_LEVEL.ERROR;
                        new StringBuilder();
                        OneSignal.onesignalLog(log_level, sb.append("Attempted to ").append(operation).append(" external ID but encountered a JSON exception").toString());
                        exception.printStackTrace();
                    }
                }
            };
            Runnable runSetExternalUserId = runnable;
            if (appContext == null || shouldRunTaskThroughQueue()) {
                new PendingTaskRunnable(runSetExternalUserId);
                addTaskToQueue(pendingTaskRunnable);
                return;
            }
            runSetExternalUserId.run();
        }
    }

    public static void removeExternalUserId() {
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("removeExternalUserId()")) {
            removeExternalUserId((OSExternalUserIdUpdateCompletionHandler) null);
        }
    }

    public static void removeExternalUserId(OSExternalUserIdUpdateCompletionHandler oSExternalUserIdUpdateCompletionHandler) {
        OSExternalUserIdUpdateCompletionHandler completionHandler = oSExternalUserIdUpdateCompletionHandler;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("removeExternalUserId()")) {
            setExternalUserId("", completionHandler);
        }
    }

    public static void sendTag(String str, String str2) {
        JSONObject jSONObject;
        String key = str;
        String value = str2;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("sendTag()")) {
            try {
                new JSONObject();
                sendTags(jSONObject.put(key, value));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendTags(String jsonString) {
        JSONObject jSONObject;
        try {
            new JSONObject(jsonString);
            sendTags(jSONObject);
        } catch (JSONException e) {
            Log(LOG_LEVEL.ERROR, "Generating JSONObject for sendTags failed!", e);
        }
    }

    public static void sendTags(JSONObject keyValues) {
        sendTags(keyValues, (ChangeTagsUpdateHandler) null);
    }

    public static void sendTags(JSONObject jSONObject, ChangeTagsUpdateHandler changeTagsUpdateHandler) {
        Runnable runnable;
        PendingTaskRunnable pendingTaskRunnable;
        SendTagsError sendTagsError;
        JSONObject keyValues = jSONObject;
        ChangeTagsUpdateHandler changeTagsUpdateHandler2 = changeTagsUpdateHandler;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("sendTags()")) {
            final JSONObject jSONObject2 = keyValues;
            final ChangeTagsUpdateHandler changeTagsUpdateHandler3 = changeTagsUpdateHandler2;
            new Runnable() {
                public void run() {
                    JSONObject jSONObject;
                    StringBuilder sb;
                    SendTagsError sendTagsError;
                    if (jSONObject2 != null) {
                        JSONObject existingKeys = OneSignalStateSynchronizer.getTags(false).result;
                        new JSONObject();
                        JSONObject toSend = jSONObject;
                        Iterator<String> keys = jSONObject2.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            try {
                                Object value = jSONObject2.opt(key);
                                if ((value instanceof JSONArray) || (value instanceof JSONObject)) {
                                    LOG_LEVEL log_level = LOG_LEVEL.ERROR;
                                    new StringBuilder();
                                    OneSignal.Log(log_level, sb.append("Omitting key '").append(key).append("'! sendTags DO NOT supported nested values!").toString());
                                } else if (!jSONObject2.isNull(key) && !"".equals(value)) {
                                    JSONObject put = toSend.put(key, value.toString());
                                } else if (existingKeys != null && existingKeys.has(key)) {
                                    JSONObject put2 = toSend.put(key, "");
                                }
                            } catch (Throwable th) {
                                Throwable th2 = th;
                            }
                        }
                        if (!toSend.toString().equals("{}")) {
                            OneSignalStateSynchronizer.sendTags(toSend, changeTagsUpdateHandler3);
                        } else if (changeTagsUpdateHandler3 != null) {
                            changeTagsUpdateHandler3.onSuccess(existingKeys);
                        }
                    } else if (changeTagsUpdateHandler3 != null) {
                        new SendTagsError(-1, "Attempted to send null tags");
                        changeTagsUpdateHandler3.onFailure(sendTagsError);
                    }
                }
            };
            Runnable sendTagsRunnable = runnable;
            if (appContext == null || shouldRunTaskThroughQueue()) {
                Log(LOG_LEVEL.ERROR, "You must initialize OneSignal before modifying tags!Moving this operation to a pending task queue.");
                if (changeTagsUpdateHandler2 != null) {
                    new SendTagsError(-1, "You must initialize OneSignal before modifying tags!Moving this operation to a pending task queue.");
                    changeTagsUpdateHandler2.onFailure(sendTagsError);
                }
                new PendingTaskRunnable(sendTagsRunnable);
                addTaskToQueue(pendingTaskRunnable);
                return;
            }
            sendTagsRunnable.run();
        }
    }

    public static void postNotification(String str, PostNotificationResponseHandler postNotificationResponseHandler) {
        StringBuilder sb;
        JSONObject jSONObject;
        String json = str;
        PostNotificationResponseHandler handler = postNotificationResponseHandler;
        try {
            new JSONObject(json);
            postNotification(jSONObject, handler);
        } catch (JSONException e) {
            JSONException jSONException = e;
            LOG_LEVEL log_level = LOG_LEVEL.ERROR;
            new StringBuilder();
            Log(log_level, sb.append("Invalid postNotification JSON format: ").append(json).toString());
        }
    }

    public static void postNotification(JSONObject jSONObject, PostNotificationResponseHandler postNotificationResponseHandler) {
        JSONObject jSONObject2;
        OneSignalRestClient.ResponseHandler responseHandler;
        JSONObject jSONObject3;
        JSONObject json = jSONObject;
        PostNotificationResponseHandler handler = postNotificationResponseHandler;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("postNotification()")) {
            try {
                if (!json.has("app_id")) {
                    JSONObject put = json.put("app_id", getSavedAppId());
                }
                if (!json.has("app_id")) {
                    if (handler != null) {
                        new JSONObject();
                        handler.onFailure(jSONObject3.put("error", "Missing app_id"));
                    }
                    return;
                }
                final PostNotificationResponseHandler postNotificationResponseHandler2 = handler;
                new OneSignalRestClient.ResponseHandler() {
                    public void onSuccess(String str) {
                        StringBuilder sb;
                        JSONObject jSONObject;
                        JSONObject jSONObject2;
                        String response = str;
                        LOG_LEVEL log_level = LOG_LEVEL.DEBUG;
                        new StringBuilder();
                        OneSignal.Log(log_level, sb.append("HTTP create notification success: ").append(response != null ? response : "null").toString());
                        if (postNotificationResponseHandler2 != null) {
                            try {
                                new JSONObject(response);
                                JSONObject jsonObject = jSONObject;
                                if (jsonObject.has("errors")) {
                                    postNotificationResponseHandler2.onFailure(jsonObject);
                                    return;
                                }
                                new JSONObject(response);
                                postNotificationResponseHandler2.onSuccess(jSONObject2);
                            } catch (Throwable th) {
                                th.printStackTrace();
                            }
                        }
                    }

                    /* access modifiers changed from: package-private */
                    public void onFailure(int i, String str, Throwable throwable) {
                        JSONObject jSONObject;
                        JSONObject jSONObject2;
                        int statusCode = i;
                        String response = str;
                        OneSignal.logHttpError("create notification failed", statusCode, throwable, response);
                        if (postNotificationResponseHandler2 != null) {
                            if (statusCode == 0) {
                                response = "{\"error\": \"HTTP no response error\"}";
                            }
                            try {
                                new JSONObject(response);
                                postNotificationResponseHandler2.onFailure(jSONObject2);
                            } catch (Throwable th) {
                                Throwable th2 = th;
                                try {
                                    new JSONObject("{\"error\": \"Unknown response!\"}");
                                    postNotificationResponseHandler2.onFailure(jSONObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                };
                OneSignalRestClient.post("notifications/", json, responseHandler);
            } catch (JSONException e) {
                Log(LOG_LEVEL.ERROR, "HTTP create notification json exception!", e);
                if (handler != null) {
                    PostNotificationResponseHandler postNotificationResponseHandler3 = handler;
                    try {
                        new JSONObject("{'error': 'HTTP create notification json exception!'}");
                        postNotificationResponseHandler3.onFailure(jSONObject2);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
    }

    public static void getTags(GetTagsHandler getTagsHandler) {
        Thread thread;
        Runnable runnable;
        GetTagsHandler getTagsHandler2 = getTagsHandler;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("getTags()")) {
            if (getTagsHandler2 == null) {
                Log(LOG_LEVEL.ERROR, "getTagsHandler is null!");
                return;
            }
            final GetTagsHandler getTagsHandler3 = getTagsHandler2;
            new Runnable() {
                /* JADX INFO: finally extract failed */
                public void run() {
                    Object obj;
                    ArrayList access$1900 = OneSignal.pendingGetTagsHandlers;
                    ArrayList arrayList = access$1900;
                    synchronized (access$1900) {
                        try {
                            boolean add = OneSignal.pendingGetTagsHandlers.add(getTagsHandler3);
                            if (OneSignal.pendingGetTagsHandlers.size() > 1) {
                                return;
                            }
                            if (OneSignal.appContext == null) {
                                OneSignal.Log(LOG_LEVEL.ERROR, "You must initialize OneSignal before getting tags! Moving this tag operation to a pending queue.");
                                new Runnable(this) {
                                    final /* synthetic */ C144214 this$0;

                                    {
                                        this.this$0 = this$0;
                                    }

                                    public void run() {
                                        OneSignal.runGetTags();
                                    }
                                };
                                boolean add2 = OneSignal.taskQueueWaitingForInit.add(obj);
                                return;
                            }
                            OneSignal.runGetTags();
                        } catch (Throwable th) {
                            while (true) {
                                Throwable th2 = th;
                                ArrayList arrayList2 = arrayList;
                                throw th2;
                            }
                        }
                    }
                }
            };
            new Thread(runnable, "OS_GETTAGS");
            thread.start();
        }
    }

    /* access modifiers changed from: private */
    public static void runGetTags() {
        if (getUserId() != null) {
            internalFireGetTagsCallbacks();
        }
    }

    /* JADX INFO: finally extract failed */
    private static void internalFireGetTagsCallbacks() {
        Thread thread;
        Runnable runnable;
        ArrayList<GetTagsHandler> arrayList = pendingGetTagsHandlers;
        ArrayList<GetTagsHandler> arrayList2 = arrayList;
        synchronized (arrayList) {
            try {
                if (pendingGetTagsHandlers.size() == 0) {
                    return;
                }
                new Runnable() {
                    /* JADX INFO: finally extract failed */
                    public void run() {
                        UserStateSynchronizer.GetTagsResult tags = OneSignalStateSynchronizer.getTags(!OneSignal.getTagsCall);
                        if (tags.serverSuccess) {
                            boolean access$2102 = OneSignal.access$2102(true);
                        }
                        ArrayList access$1900 = OneSignal.pendingGetTagsHandlers;
                        ArrayList arrayList = access$1900;
                        synchronized (access$1900) {
                            try {
                                Iterator it = OneSignal.pendingGetTagsHandlers.iterator();
                                while (it.hasNext()) {
                                    ((GetTagsHandler) it.next()).tagsAvailable((tags.result == null || tags.toString().equals("{}")) ? null : tags.result);
                                }
                                OneSignal.pendingGetTagsHandlers.clear();
                            } catch (Throwable th) {
                                Throwable th2 = th;
                                ArrayList arrayList2 = arrayList;
                                throw th2;
                            }
                        }
                    }
                };
                new Thread(runnable, "OS_GETTAGS_CALLBACK");
                thread.start();
            } catch (Throwable th) {
                while (true) {
                    Throwable th2 = th;
                    ArrayList<GetTagsHandler> arrayList3 = arrayList2;
                    throw th2;
                }
            }
        }
    }

    public static void deleteTag(String key) {
        deleteTag(key, (ChangeTagsUpdateHandler) null);
    }

    public static void deleteTag(String str, ChangeTagsUpdateHandler changeTagsUpdateHandler) {
        Collection<String> collection;
        String key = str;
        ChangeTagsUpdateHandler handler = changeTagsUpdateHandler;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("deleteTag()")) {
            new ArrayList<>(1);
            Collection<String> tempList = collection;
            boolean add = tempList.add(key);
            deleteTags(tempList, handler);
        }
    }

    public static void deleteTags(Collection<String> keys) {
        deleteTags(keys, (ChangeTagsUpdateHandler) null);
    }

    public static void deleteTags(Collection<String> collection, ChangeTagsUpdateHandler changeTagsUpdateHandler) {
        JSONObject jSONObject;
        Collection<String> keys = collection;
        ChangeTagsUpdateHandler handler = changeTagsUpdateHandler;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("deleteTags()")) {
            try {
                new JSONObject();
                JSONObject jsonTags = jSONObject;
                for (String key : keys) {
                    JSONObject put = jsonTags.put(key, "");
                }
                sendTags(jsonTags, handler);
            } catch (Throwable th) {
                Log(LOG_LEVEL.ERROR, "Failed to generate JSON for deleteTags.", th);
            }
        }
    }

    public static void deleteTags(String jsonArrayString) {
        deleteTags(jsonArrayString, (ChangeTagsUpdateHandler) null);
    }

    public static void deleteTags(String jsonArrayString, ChangeTagsUpdateHandler changeTagsUpdateHandler) {
        JSONArray jSONArray;
        ChangeTagsUpdateHandler handler = changeTagsUpdateHandler;
        try {
            new JSONArray(jsonArrayString);
            deleteTags(jSONArray, handler);
        } catch (Throwable th) {
            Log(LOG_LEVEL.ERROR, "Failed to generate JSON for deleteTags.", th);
        }
    }

    public static void deleteTags(JSONArray jSONArray, ChangeTagsUpdateHandler changeTagsUpdateHandler) {
        JSONObject jSONObject;
        JSONArray jsonArray = jSONArray;
        ChangeTagsUpdateHandler handler = changeTagsUpdateHandler;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("deleteTags()")) {
            try {
                new JSONObject();
                JSONObject jsonTags = jSONObject;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject put = jsonTags.put(jsonArray.getString(i), "");
                }
                sendTags(jsonTags, handler);
            } catch (Throwable th) {
                Log(LOG_LEVEL.ERROR, "Failed to generate JSON for deleteTags.", th);
            }
        }
    }

    public static void idsAvailable(IdsAvailableHandler idsAvailableHandler2) {
        Runnable runnable;
        PendingTaskRunnable pendingTaskRunnable;
        IdsAvailableHandler inIdsAvailableHandler = idsAvailableHandler2;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("idsAvailable()")) {
            idsAvailableHandler = inIdsAvailableHandler;
            new Runnable() {
                public void run() {
                    Runnable runnable;
                    if (OneSignal.getUserId() != null) {
                        new Runnable(this) {
                            final /* synthetic */ C144516 this$0;

                            {
                                this.this$0 = this$0;
                            }

                            public void run() {
                                OneSignal.internalFireIdsAvailableCallback();
                            }
                        };
                        OSUtils.runOnMainUIThread(runnable);
                    }
                }
            };
            Runnable runIdsAvailable = runnable;
            if (appContext == null || shouldRunTaskThroughQueue()) {
                Log(LOG_LEVEL.ERROR, "You must initialize OneSignal before getting tags! Moving this tag operation to a pending queue.");
                new PendingTaskRunnable(runIdsAvailable);
                addTaskToQueue(pendingTaskRunnable);
                return;
            }
            runIdsAvailable.run();
        }
    }

    static void fireIdsAvailableCallback() {
        Runnable runnable;
        if (idsAvailableHandler != null) {
            new Runnable() {
                public void run() {
                    OneSignal.internalFireIdsAvailableCallback();
                }
            };
            OSUtils.runOnMainUIThread(runnable);
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void internalFireIdsAvailableCallback() {
        synchronized (OneSignal.class) {
            if (idsAvailableHandler != null) {
                String regId = OneSignalStateSynchronizer.getRegistrationId();
                if (!OneSignalStateSynchronizer.getSubscribed()) {
                    regId = null;
                }
                String userId2 = getUserId();
                if (userId2 != null) {
                    idsAvailableHandler.idsAvailable(userId2, regId);
                    if (regId != null) {
                        idsAvailableHandler = null;
                    }
                }
            }
        }
    }

    static void sendPurchases(JSONArray jSONArray, boolean z, OneSignalRestClient.ResponseHandler responseHandler) {
        JSONObject jSONObject;
        StringBuilder sb;
        StringBuilder sb2;
        IAPUpdateJob iAPUpdateJob;
        JSONArray purchases = jSONArray;
        boolean newAsExisting = z;
        OneSignalRestClient.ResponseHandler responseHandler2 = responseHandler;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("sendPurchases()")) {
            if (getUserId() == null) {
                new IAPUpdateJob(purchases);
                iapUpdateJob = iAPUpdateJob;
                iapUpdateJob.newAsExisting = newAsExisting;
                iapUpdateJob.restResponseHandler = responseHandler2;
                return;
            }
            try {
                new JSONObject();
                JSONObject jsonBody = jSONObject;
                JSONObject put = jsonBody.put("app_id", getSavedAppId());
                if (newAsExisting) {
                    JSONObject put2 = jsonBody.put("existing", true);
                }
                JSONObject put3 = jsonBody.put("purchases", purchases);
                new StringBuilder();
                OneSignalRestClient.post(sb.append("players/").append(getUserId()).append("/on_purchase").toString(), jsonBody, responseHandler2);
                if (getEmailId() != null) {
                    new StringBuilder();
                    OneSignalRestClient.post(sb2.append("players/").append(getEmailId()).append("/on_purchase").toString(), jsonBody, (OneSignalRestClient.ResponseHandler) null);
                }
            } catch (Throwable th) {
                Log(LOG_LEVEL.ERROR, "Failed to generate JSON for sendPurchases.", th);
            }
        }
    }

    private static boolean openURLFromNotification(Context context, JSONArray jSONArray) {
        StringBuilder sb;
        JSONObject jSONObject;
        String url;
        Context context2 = context;
        JSONArray dataArray = jSONArray;
        if (shouldLogUserPrivacyConsentErrorMessageForMethodName((String) null)) {
            return false;
        }
        int jsonArraySize = dataArray.length();
        boolean urlOpened = false;
        for (int i = 0; i < jsonArraySize; i++) {
            try {
                JSONObject data = dataArray.getJSONObject(i);
                if (data.has(GoogleMapStyleOptions.THEME_CUSTOM)) {
                    new JSONObject(data.optString(GoogleMapStyleOptions.THEME_CUSTOM));
                    JSONObject customJSON = jSONObject;
                    if (customJSON.has("u") && (url = customJSON.optString("u", (String) null)) != null) {
                        OSUtils.openURLInBrowser(url);
                        urlOpened = true;
                    }
                }
            } catch (Throwable th) {
                Throwable t = th;
                LOG_LEVEL log_level = LOG_LEVEL.ERROR;
                new StringBuilder();
                Log(log_level, sb.append("Error parsing JSON item ").append(i).append("/").append(jsonArraySize).append(" for launching a web URL.").toString(), t);
            }
        }
        return urlOpened;
    }

    private static void runNotificationOpenedCallback(JSONArray jSONArray, boolean z, boolean z2) {
        JSONArray dataArray = jSONArray;
        boolean shown = z;
        boolean fromAlert = z2;
        if (mInitBuilder == null || mInitBuilder.mNotificationOpenedHandler == null) {
            boolean add = unprocessedOpenedNotifis.add(dataArray);
        } else {
            fireNotificationOpenedHandler(generateOsNotificationOpenResult(dataArray, shown, fromAlert));
        }
    }

    @NonNull
    private static OSNotificationOpenResult generateOsNotificationOpenResult(JSONArray jSONArray, boolean shown, boolean z) {
        OSNotificationOpenResult oSNotificationOpenResult;
        OSNotification oSNotification;
        OSNotificationAction oSNotificationAction;
        StringBuilder sb;
        List<OSNotificationPayload> list;
        JSONArray dataArray = jSONArray;
        boolean fromAlert = z;
        int jsonArraySize = dataArray.length();
        boolean firstMessage = true;
        new OSNotificationOpenResult();
        OSNotificationOpenResult openResult = oSNotificationOpenResult;
        new OSNotification();
        OSNotification notification = oSNotification;
        notification.isAppInFocus = isAppActive();
        notification.shown = shown;
        notification.androidNotificationId = dataArray.optJSONObject(0).optInt("notificationId");
        String actionSelected = null;
        for (int i = 0; i < jsonArraySize; i++) {
            try {
                JSONObject data = dataArray.getJSONObject(i);
                notification.payload = NotificationBundleProcessor.OSNotificationPayloadFrom(data);
                if (actionSelected == null && data.has("actionSelected")) {
                    actionSelected = data.optString("actionSelected", (String) null);
                }
                if (firstMessage) {
                    firstMessage = false;
                } else {
                    if (notification.groupedNotifications == null) {
                        new ArrayList();
                        notification.groupedNotifications = list;
                    }
                    boolean add = notification.groupedNotifications.add(notification.payload);
                }
            } catch (Throwable th) {
                Throwable t = th;
                LOG_LEVEL log_level = LOG_LEVEL.ERROR;
                new StringBuilder();
                Log(log_level, sb.append("Error parsing JSON item ").append(i).append("/").append(jsonArraySize).append(" for callback.").toString(), t);
            }
        }
        openResult.notification = notification;
        new OSNotificationAction();
        openResult.action = oSNotificationAction;
        openResult.action.actionID = actionSelected;
        openResult.action.type = actionSelected != null ? OSNotificationAction.ActionType.ActionTaken : OSNotificationAction.ActionType.Opened;
        if (fromAlert) {
            openResult.notification.displayType = OSNotification.DisplayType.InAppAlert;
        } else {
            openResult.notification.displayType = OSNotification.DisplayType.Notification;
        }
        return openResult;
    }

    private static void fireNotificationOpenedHandler(OSNotificationOpenResult openedResult) {
        Runnable runnable;
        final OSNotificationOpenResult oSNotificationOpenResult = openedResult;
        new Runnable() {
            public void run() {
                OneSignal.mInitBuilder.mNotificationOpenedHandler.notificationOpened(oSNotificationOpenResult);
            }
        };
        OSUtils.runOnMainUIThread(runnable);
    }

    static void handleNotificationReceived(JSONArray data, boolean displayed, boolean fromAlert) {
        OSNotificationOpenResult openResult = generateOsNotificationOpenResult(data, displayed, fromAlert);
        if (trackFirebaseAnalytics != null && getFirebaseAnalyticsEnabled()) {
            trackFirebaseAnalytics.trackReceivedEvent(openResult);
        }
        if (mInitBuilder != null && mInitBuilder.mNotificationReceivedHandler != null) {
            mInitBuilder.mNotificationReceivedHandler.notificationReceived(openResult.notification);
        }
    }

    public static void handleNotificationOpen(Context context, JSONArray jSONArray, boolean z, String str) {
        Context inContext = context;
        JSONArray data = jSONArray;
        boolean fromAlert = z;
        String notificationId = str;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName((String) null)) {
            notificationOpenedRESTCall(inContext, data);
            if (trackFirebaseAnalytics != null && getFirebaseAnalyticsEnabled()) {
                trackFirebaseAnalytics.trackOpenedEvent(generateOsNotificationOpenResult(data, true, fromAlert));
            }
            boolean urlOpened = false;
            boolean defaultOpenActionDisabled = "DISABLE".equals(OSUtils.getManifestMeta(inContext, "com.onesignal.NotificationOpened.DEFAULT"));
            if (!defaultOpenActionDisabled) {
                urlOpened = openURLFromNotification(inContext, data);
            }
            if (shouldInitDirectSessionFromNotificationOpen(inContext, fromAlert, urlOpened, defaultOpenActionDisabled)) {
                appEntryState = AppEntryAction.NOTIFICATION_CLICK;
                sessionManager.onDirectSessionFromNotificationOpen(notificationId);
            }
            runNotificationOpenedCallback(data, true, fromAlert);
        }
    }

    static boolean startOrResumeApp(Context context) {
        Context inContext = context;
        Intent launchIntent = inContext.getPackageManager().getLaunchIntentForPackage(inContext.getPackageName());
        if (launchIntent == null) {
            return false;
        }
        Intent flags = launchIntent.setFlags(268566528);
        inContext.startActivity(launchIntent);
        return true;
    }

    private static boolean shouldInitDirectSessionFromNotificationOpen(Context context, boolean fromAlert, boolean urlOpened, boolean defaultOpenActionDisabled) {
        return !fromAlert && !urlOpened && !defaultOpenActionDisabled && !foreground && startOrResumeApp(context);
    }

    private static void notificationOpenedRESTCall(Context context, JSONArray jSONArray) {
        JSONObject customJson;
        JSONObject jSONObject;
        StringBuilder sb;
        OneSignalRestClient.ResponseHandler responseHandler;
        Context inContext = context;
        JSONArray dataArray = jSONArray;
        for (int i = 0; i < dataArray.length(); i++) {
            try {
                new JSONObject(dataArray.getJSONObject(i).optString(GoogleMapStyleOptions.THEME_CUSTOM, (String) null));
                String notificationId = customJson.optString("i", (String) null);
                if (!postedOpenedNotifIds.contains(notificationId)) {
                    boolean add = postedOpenedNotifIds.add(notificationId);
                    new JSONObject();
                    JSONObject jsonBody = jSONObject;
                    JSONObject put = jsonBody.put("app_id", getSavedAppId(inContext));
                    JSONObject put2 = jsonBody.put("player_id", getSavedUserId(inContext));
                    JSONObject put3 = jsonBody.put(OneSignalDbContract.NotificationTable.COLUMN_NAME_OPENED, true);
                    JSONObject put4 = jsonBody.put("device_type", osUtils.getDeviceType());
                    new StringBuilder();
                    new OneSignalRestClient.ResponseHandler() {
                        /* access modifiers changed from: package-private */
                        public void onFailure(int statusCode, String response, Throwable throwable) {
                            OneSignal.logHttpError("sending Notification Opened Failed", statusCode, throwable, response);
                        }
                    };
                    OneSignalRestClient.put(sb.append("notifications/").append(notificationId).toString(), jsonBody, responseHandler);
                }
            } catch (Throwable th) {
                Log(LOG_LEVEL.ERROR, "Failed to generate JSON to send notification opened.", th);
            }
        }
    }

    private static void saveAppId(String str) {
        String appId2 = str;
        if (appContext != null) {
            OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_GT_APP_ID, appId2);
        }
    }

    static String getSavedAppId() {
        return getSavedAppId(appContext);
    }

    private static String getSavedAppId(Context inContext) {
        if (inContext == null) {
            return null;
        }
        return OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_GT_APP_ID, (String) null);
    }

    static boolean getSavedUserConsentStatus() {
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_ONESIGNAL_USER_PROVIDED_CONSENT, false);
    }

    static void saveUserConsentStatus(boolean consent) {
        OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_ONESIGNAL_USER_PROVIDED_CONSENT, consent);
    }

    private static String getSavedUserId(Context inContext) {
        if (inContext == null) {
            return null;
        }
        return OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_GT_PLAYER_ID, (String) null);
    }

    static boolean hasUserId() {
        return getUserId() != null;
    }

    static String getUserId() {
        if (userId == null && appContext != null) {
            userId = getSavedUserId(appContext);
        }
        return userId;
    }

    static void saveUserId(String id) {
        userId = id;
        if (appContext != null) {
            OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_GT_PLAYER_ID, userId);
        }
    }

    static boolean hasEmailId() {
        return !TextUtils.isEmpty(emailId);
    }

    static String getEmailId() {
        if (TextUtils.isEmpty(emailId) && appContext != null) {
            emailId = OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_EMAIL_ID, (String) null);
        }
        return emailId;
    }

    static void saveEmailId(String id) {
        emailId = id;
        if (appContext != null) {
            OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_EMAIL_ID, "".equals(emailId) ? null : emailId);
        }
    }

    static boolean getFilterOtherGCMReceivers(Context context) {
        Context context2 = context;
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_FILTER_OTHER_GCM_RECEIVERS, false);
    }

    static void saveFilterOtherGCMReceivers(boolean z) {
        boolean set = z;
        if (appContext != null) {
            OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_FILTER_OTHER_GCM_RECEIVERS, set);
        }
    }

    static void updateUserIdDependents(String str) {
        String userId2 = str;
        saveUserId(userId2);
        fireIdsAvailableCallback();
        internalFireGetTagsCallbacks();
        getCurrentSubscriptionState(appContext).setUserId(userId2);
        if (iapUpdateJob != null) {
            sendPurchases(iapUpdateJob.toReport, iapUpdateJob.newAsExisting, iapUpdateJob.restResponseHandler);
            iapUpdateJob = null;
        }
        OneSignalStateSynchronizer.refreshEmailState();
        OneSignalChromeTabAndroidFrame.setup(appId, userId2, AdvertisingIdProviderGPS.getLastValue());
    }

    static void updateEmailIdDependents(String str) {
        JSONObject jSONObject;
        String emailId2 = str;
        saveEmailId(emailId2);
        getCurrentEmailSubscriptionState(appContext).setEmailUserId(emailId2);
        try {
            new JSONObject();
            OneSignalStateSynchronizer.updatePushState(jSONObject.put("parent_player_id", emailId2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static boolean getFirebaseAnalyticsEnabled() {
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_GT_FIREBASE_TRACKING_ENABLED, false);
    }

    static boolean getClearGroupSummaryClick() {
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_CLEAR_GROUP_SUMMARY_CLICK, true);
    }

    public static void enableVibrate(boolean z) {
        boolean enable = z;
        if (appContext != null) {
            OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_GT_VIBRATE_ENABLED, enable);
        }
    }

    static boolean getVibrate() {
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_GT_VIBRATE_ENABLED, true);
    }

    public static void enableSound(boolean z) {
        boolean enable = z;
        if (appContext != null) {
            OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_GT_SOUND_ENABLED, enable);
        }
    }

    static boolean getSoundEnabled() {
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_GT_SOUND_ENABLED, true);
    }

    static void setLastSessionTime(long time) {
        OneSignalPrefs.saveLong(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_LAST_SESSION_TIME, time);
    }

    private static long getLastSessionTime() {
        return OneSignalPrefs.getLong(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_LAST_SESSION_TIME, -31000);
    }

    public static void setInFocusDisplaying(OSInFocusDisplayOption displayOption) {
        mInitBuilder.mDisplayOptionCarryOver = true;
        mInitBuilder.mDisplayOption = displayOption;
    }

    public static void setInFocusDisplaying(int displayOption) {
        setInFocusDisplaying(getInFocusDisplaying(displayOption));
    }

    public static OSInFocusDisplayOption currentInFocusDisplayOption() {
        return mInitBuilder.mDisplayOption;
    }

    private static OSInFocusDisplayOption getInFocusDisplaying(int i) {
        int displayOption = i;
        switch (displayOption) {
            case 0:
                return OSInFocusDisplayOption.None;
            case 1:
                return OSInFocusDisplayOption.InAppAlert;
            case 2:
                return OSInFocusDisplayOption.Notification;
            default:
                if (displayOption < 0) {
                    return OSInFocusDisplayOption.None;
                }
                return OSInFocusDisplayOption.Notification;
        }
    }

    static boolean getNotificationsWhenActiveEnabled() {
        return mInitBuilder == null || mInitBuilder.mDisplayOption == OSInFocusDisplayOption.Notification;
    }

    static boolean getInAppAlertNotificationEnabled() {
        return mInitBuilder != null && mInitBuilder.mDisplayOption == OSInFocusDisplayOption.InAppAlert;
    }

    public static void setSubscription(boolean z) {
        Runnable runnable;
        PendingTaskRunnable pendingTaskRunnable;
        boolean enable = z;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("setSubscription()")) {
            final boolean z2 = enable;
            new Runnable() {
                public void run() {
                    OneSignal.getCurrentSubscriptionState(OneSignal.appContext).setUserSubscriptionSetting(z2);
                    OneSignalStateSynchronizer.setSubscription(z2);
                }
            };
            Runnable runSetSubscription = runnable;
            if (appContext == null || shouldRunTaskThroughQueue()) {
                Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Moving subscription action to a waiting task queue.");
                new PendingTaskRunnable(runSetSubscription);
                addTaskToQueue(pendingTaskRunnable);
                return;
            }
            runSetSubscription.run();
        }
    }

    public static void setLocationShared(boolean z) {
        StringBuilder sb;
        boolean enable = z;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("setLocationShared()")) {
            shareLocation = enable;
            if (!enable) {
                OneSignalStateSynchronizer.clearLocation();
            }
            LOG_LEVEL log_level = LOG_LEVEL.DEBUG;
            new StringBuilder();
            Log(log_level, sb.append("shareLocation:").append(shareLocation).toString());
        }
    }

    public static void promptLocation() {
        promptLocation((OSPromptActionCompletionCallback) null);
    }

    static void promptLocation(@Nullable OSPromptActionCompletionCallback oSPromptActionCompletionCallback) {
        Runnable runnable;
        PendingTaskRunnable pendingTaskRunnable;
        OSPromptActionCompletionCallback callback = oSPromptActionCompletionCallback;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("promptLocation()")) {
            final OSPromptActionCompletionCallback oSPromptActionCompletionCallback2 = callback;
            new Runnable() {
                public void run() {
                    LocationGMS.LocationHandler locationHandler;
                    new LocationGMS.LocationPromptCompletionHandler(this) {
                        final /* synthetic */ C145221 this$0;

                        {
                            this.this$0 = this$0;
                        }

                        public LocationGMS.PermissionType getType() {
                            return LocationGMS.PermissionType.PROMPT_LOCATION;
                        }

                        public void complete(LocationGMS.LocationPoint locationPoint) {
                            LocationGMS.LocationPoint point = locationPoint;
                            if (!OneSignal.shouldLogUserPrivacyConsentErrorMessageForMethodName("promptLocation()") && point != null) {
                                OneSignalStateSynchronizer.updateLocation(point);
                            }
                        }

                        /* access modifiers changed from: package-private */
                        public void onAnswered(boolean z) {
                            boolean accepted = z;
                            super.onAnswered(accepted);
                            if (oSPromptActionCompletionCallback2 != null) {
                                oSPromptActionCompletionCallback2.completed(accepted);
                            }
                        }
                    };
                    LocationGMS.getLocation(OneSignal.appContext, true, locationHandler);
                    boolean access$2302 = OneSignal.access$2302(true);
                }
            };
            Runnable runPromptLocation = runnable;
            if (appContext == null || shouldRunTaskThroughQueue()) {
                Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not prompt for location at this time - moving this operation to awaiting queue.");
                new PendingTaskRunnable(runPromptLocation);
                addTaskToQueue(pendingTaskRunnable);
                return;
            }
            runPromptLocation.run();
        }
    }

    public static void clearOneSignalNotifications() {
        Runnable runnable;
        PendingTaskRunnable pendingTaskRunnable;
        new Runnable() {
            /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
                java.lang.NullPointerException
                	at jadx.core.dex.instructions.args.InsnArg.wrapInstruction(InsnArg.java:118)
                	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.inline(CodeShrinkVisitor.java:146)
                	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:71)
                	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
                	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
                */
            public void run() {
                /*
                    r21 = this;
                    r0 = r21
                    android.content.Context r12 = com.onesignal.OneSignal.appContext
                    android.app.NotificationManager r12 = com.onesignal.OneSignalNotificationManager.getNotificationManager(r12)
                    r1 = r12
                    android.content.Context r12 = com.onesignal.OneSignal.appContext
                    com.onesignal.OneSignalDbHelper r12 = com.onesignal.OneSignalDbHelper.getInstance(r12)
                    r2 = r12
                    r12 = 0
                    r3 = r12
                    r12 = r2
                    android.database.sqlite.SQLiteDatabase r12 = r12.getReadableDbWithRetries()     // Catch:{ Throwable -> 0x00e1 }
                    r4 = r12
                    r12 = 1
                    java.lang.String[] r12 = new java.lang.String[r12]     // Catch:{ Throwable -> 0x00e1 }
                    r20 = r12
                    r12 = r20
                    r13 = r20
                    r14 = 0
                    java.lang.String r15 = "android_notification_id"
                    r13[r14] = r15     // Catch:{ Throwable -> 0x00e1 }
                    r5 = r12
                    r12 = r4
                    java.lang.String r13 = "notification"
                    r14 = r5
                    java.lang.String r15 = "dismissed = 0 AND opened = 0"
                    r16 = 0
                    r17 = 0
                    r18 = 0
                    r19 = 0
                    android.database.Cursor r12 = r12.query(r13, r14, r15, r16, r17, r18, r19)     // Catch:{ Throwable -> 0x00e1 }
                    r3 = r12
                    r12 = r3
                    boolean r12 = r12.moveToFirst()     // Catch:{ Throwable -> 0x00e1 }
                    if (r12 == 0) goto L_0x005e
                L_0x0044:
                    r12 = r3
                    r13 = r3
                    java.lang.String r14 = "android_notification_id"
                    int r13 = r13.getColumnIndex(r14)     // Catch:{ Throwable -> 0x00e1 }
                    int r12 = r12.getInt(r13)     // Catch:{ Throwable -> 0x00e1 }
                    r6 = r12
                    r12 = r1
                    r13 = r6
                    r12.cancel(r13)     // Catch:{ Throwable -> 0x00e1 }
                    r12 = r3
                    boolean r12 = r12.moveToNext()     // Catch:{ Throwable -> 0x00e1 }
                    if (r12 != 0) goto L_0x0044
                L_0x005e:
                    r12 = 0
                    r6 = r12
                    r12 = r2
                    android.database.sqlite.SQLiteDatabase r12 = r12.getWritableDbWithRetries()     // Catch:{ Throwable -> 0x00b7 }
                    r6 = r12
                    r12 = r6
                    r12.beginTransaction()     // Catch:{ Throwable -> 0x00b7 }
                    java.lang.String r12 = "opened = 0"
                    r7 = r12
                    android.content.ContentValues r12 = new android.content.ContentValues     // Catch:{ Throwable -> 0x00b7 }
                    r20 = r12
                    r12 = r20
                    r13 = r20
                    r13.<init>()     // Catch:{ Throwable -> 0x00b7 }
                    r8 = r12
                    r12 = r8
                    java.lang.String r13 = "dismissed"
                    r14 = 1
                    java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ Throwable -> 0x00b7 }
                    r12.put(r13, r14)     // Catch:{ Throwable -> 0x00b7 }
                    r12 = r6
                    java.lang.String r13 = "notification"
                    r14 = r8
                    r15 = r7
                    r16 = 0
                    int r12 = r12.update(r13, r14, r15, r16)     // Catch:{ Throwable -> 0x00b7 }
                    r12 = r6
                    r12.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00b7 }
                    r12 = r6
                    if (r12 == 0) goto L_0x009d
                    r12 = r6
                    r12.endTransaction()     // Catch:{ Throwable -> 0x00ab }
                L_0x009d:
                    r12 = 0
                    android.content.Context r13 = com.onesignal.OneSignal.appContext     // Catch:{ Throwable -> 0x00e1 }
                    com.onesignal.BadgeCountUpdater.updateCount(r12, r13)     // Catch:{ Throwable -> 0x00e1 }
                    r12 = r3
                    if (r12 == 0) goto L_0x00aa
                    r12 = r3
                    r12.close()
                L_0x00aa:
                    return
                L_0x00ab:
                    r12 = move-exception
                    r7 = r12
                    com.onesignal.OneSignal$LOG_LEVEL r12 = com.onesignal.OneSignal.LOG_LEVEL.ERROR     // Catch:{ Throwable -> 0x00e1 }
                    java.lang.String r13 = "Error closing transaction! "
                    r14 = r7
                    com.onesignal.OneSignal.Log(r12, r13, r14)     // Catch:{ Throwable -> 0x00e1 }
                    goto L_0x009d
                L_0x00b7:
                    r12 = move-exception
                    r7 = r12
                    com.onesignal.OneSignal$LOG_LEVEL r12 = com.onesignal.OneSignal.LOG_LEVEL.ERROR     // Catch:{ all -> 0x00d6 }
                    java.lang.String r13 = "Error marking all notifications as dismissed! "
                    r14 = r7
                    com.onesignal.OneSignal.Log(r12, r13, r14)     // Catch:{ all -> 0x00d6 }
                    r12 = r6
                    if (r12 == 0) goto L_0x009d
                    r12 = r6
                    r12.endTransaction()     // Catch:{ Throwable -> 0x00ca }
                    goto L_0x009d
                L_0x00ca:
                    r12 = move-exception
                    r7 = r12
                    com.onesignal.OneSignal$LOG_LEVEL r12 = com.onesignal.OneSignal.LOG_LEVEL.ERROR     // Catch:{ Throwable -> 0x00e1 }
                    java.lang.String r13 = "Error closing transaction! "
                    r14 = r7
                    com.onesignal.OneSignal.Log(r12, r13, r14)     // Catch:{ Throwable -> 0x00e1 }
                    goto L_0x009d
                L_0x00d6:
                    r12 = move-exception
                    r9 = r12
                    r12 = r6
                    if (r12 == 0) goto L_0x00df
                    r12 = r6
                    r12.endTransaction()     // Catch:{ Throwable -> 0x00f4 }
                L_0x00df:
                    r12 = r9
                    throw r12     // Catch:{ Throwable -> 0x00e1 }
                L_0x00e1:
                    r12 = move-exception
                    r4 = r12
                    com.onesignal.OneSignal$LOG_LEVEL r12 = com.onesignal.OneSignal.LOG_LEVEL.ERROR     // Catch:{ all -> 0x0100 }
                    java.lang.String r13 = "Error canceling all notifications! "
                    r14 = r4
                    com.onesignal.OneSignal.Log(r12, r13, r14)     // Catch:{ all -> 0x0100 }
                    r12 = r3
                    if (r12 == 0) goto L_0x00aa
                    r12 = r3
                    r12.close()
                    goto L_0x00aa
                L_0x00f4:
                    r12 = move-exception
                    r10 = r12
                    com.onesignal.OneSignal$LOG_LEVEL r12 = com.onesignal.OneSignal.LOG_LEVEL.ERROR     // Catch:{ Throwable -> 0x00e1 }
                    java.lang.String r13 = "Error closing transaction! "
                    r14 = r10
                    com.onesignal.OneSignal.Log(r12, r13, r14)     // Catch:{ Throwable -> 0x00e1 }
                    goto L_0x00df
                L_0x0100:
                    r12 = move-exception
                    r11 = r12
                    r12 = r3
                    if (r12 == 0) goto L_0x0109
                    r12 = r3
                    r12.close()
                L_0x0109:
                    r12 = r11
                    throw r12
                */
                throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OneSignal.C145422.run():void");
            }
        };
        Runnable runClearOneSignalNotifications = runnable;
        if (appContext == null || shouldRunTaskThroughQueue()) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not clear notifications at this time - moving this operation toa waiting task queue.");
            new PendingTaskRunnable(runClearOneSignalNotifications);
            addTaskToQueue(pendingTaskRunnable);
            return;
        }
        runClearOneSignalNotifications.run();
    }

    public static void cancelNotification(int i) {
        Runnable runnable;
        StringBuilder sb;
        int id = i;
        final int i2 = id;
        new Runnable() {
            public void run() {
                StringBuilder sb;
                StringBuilder sb2;
                ContentValues contentValues;
                try {
                    SQLiteDatabase writableDb = OneSignalDbHelper.getInstance(OneSignal.appContext).getWritableDbWithRetries();
                    writableDb.beginTransaction();
                    new StringBuilder();
                    String whereStr = sb2.append("android_notification_id = ").append(i2).append(" AND ").append(OneSignalDbContract.NotificationTable.COLUMN_NAME_OPENED).append(" = 0 AND ").append(OneSignalDbContract.NotificationTable.COLUMN_NAME_DISMISSED).append(" = 0").toString();
                    new ContentValues();
                    ContentValues values = contentValues;
                    values.put(OneSignalDbContract.NotificationTable.COLUMN_NAME_DISMISSED, 1);
                    if (writableDb.update(OneSignalDbContract.NotificationTable.TABLE_NAME, values, whereStr, (String[]) null) > 0) {
                        NotificationSummaryManager.updatePossibleDependentSummaryOnDismiss(OneSignal.appContext, writableDb, i2);
                    }
                    BadgeCountUpdater.update(writableDb, OneSignal.appContext);
                    writableDb.setTransactionSuccessful();
                    if (writableDb != null) {
                        try {
                            writableDb.endTransaction();
                        } catch (Throwable th) {
                            OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", th);
                        }
                    }
                } catch (Throwable th2) {
                    OneSignal.Log(LOG_LEVEL.ERROR, "Error closing transaction! ", th2);
                }
                OneSignalNotificationManager.getNotificationManager(OneSignal.appContext).cancel(i2);
            }
        };
        Runnable runCancelNotification = runnable;
        if (appContext == null || shouldRunTaskThroughQueue()) {
            LOG_LEVEL log_level = LOG_LEVEL.ERROR;
            new StringBuilder();
            Log(log_level, sb.append("OneSignal.init has not been called. Could not clear notification id: ").append(id).append(" at this time - movingthis operation to a waiting task queue. The notification will still be canceledfrom NotificationManager at this time.").toString());
            boolean add = taskQueueWaitingForInit.add(runCancelNotification);
            return;
        }
        runCancelNotification.run();
    }

    public static void cancelGroupedNotifications(String str) {
        Runnable runnable;
        StringBuilder sb;
        PendingTaskRunnable pendingTaskRunnable;
        String group = str;
        if (!shouldLogUserPrivacyConsentErrorMessageForMethodName("cancelGroupedNotifications()")) {
            final String str2 = group;
            new Runnable() {
                /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
                    java.lang.NullPointerException
                    	at jadx.core.dex.instructions.args.InsnArg.wrapInstruction(InsnArg.java:118)
                    	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.inline(CodeShrinkVisitor.java:146)
                    	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:71)
                    	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
                    	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
                    */
                public void run() {
                    /*
                        r21 = this;
                        r0 = r21
                        android.content.Context r12 = com.onesignal.OneSignal.appContext
                        android.app.NotificationManager r12 = com.onesignal.OneSignalNotificationManager.getNotificationManager(r12)
                        r1 = r12
                        android.content.Context r12 = com.onesignal.OneSignal.appContext
                        com.onesignal.OneSignalDbHelper r12 = com.onesignal.OneSignalDbHelper.getInstance(r12)
                        r2 = r12
                        r12 = 0
                        r3 = r12
                        r12 = r2
                        android.database.sqlite.SQLiteDatabase r12 = r12.getReadableDbWithRetries()     // Catch:{ Throwable -> 0x00d2 }
                        r4 = r12
                        r12 = 1
                        java.lang.String[] r12 = new java.lang.String[r12]     // Catch:{ Throwable -> 0x00d2 }
                        r20 = r12
                        r12 = r20
                        r13 = r20
                        r14 = 0
                        java.lang.String r15 = "android_notification_id"
                        r13[r14] = r15     // Catch:{ Throwable -> 0x00d2 }
                        r5 = r12
                        java.lang.String r12 = "group_id = ? AND dismissed = 0 AND opened = 0"
                        r6 = r12
                        r12 = 1
                        java.lang.String[] r12 = new java.lang.String[r12]     // Catch:{ Throwable -> 0x00d2 }
                        r20 = r12
                        r12 = r20
                        r13 = r20
                        r14 = 0
                        r15 = r0
                        java.lang.String r15 = r4     // Catch:{ Throwable -> 0x00d2 }
                        r13[r14] = r15     // Catch:{ Throwable -> 0x00d2 }
                        r7 = r12
                        r12 = r4
                        java.lang.String r13 = "notification"
                        r14 = r5
                        r15 = r6
                        r16 = r7
                        r17 = 0
                        r18 = 0
                        r19 = 0
                        android.database.Cursor r12 = r12.query(r13, r14, r15, r16, r17, r18, r19)     // Catch:{ Throwable -> 0x00d2 }
                        r3 = r12
                    L_0x004f:
                        r12 = r3
                        boolean r12 = r12.moveToNext()     // Catch:{ Throwable -> 0x00d2 }
                        if (r12 == 0) goto L_0x006e
                        r12 = r3
                        r13 = r3
                        java.lang.String r14 = "android_notification_id"
                        int r13 = r13.getColumnIndex(r14)     // Catch:{ Throwable -> 0x00d2 }
                        int r12 = r12.getInt(r13)     // Catch:{ Throwable -> 0x00d2 }
                        r8 = r12
                        r12 = r8
                        r13 = -1
                        if (r12 == r13) goto L_0x006d
                        r12 = r1
                        r13 = r8
                        r12.cancel(r13)     // Catch:{ Throwable -> 0x00d2 }
                    L_0x006d:
                        goto L_0x004f
                    L_0x006e:
                        r12 = r3
                        if (r12 == 0) goto L_0x007c
                        r12 = r3
                        boolean r12 = r12.isClosed()
                        if (r12 != 0) goto L_0x007c
                        r12 = r3
                        r12.close()
                    L_0x007c:
                        r12 = 0
                        r4 = r12
                        r12 = r2
                        android.database.sqlite.SQLiteDatabase r12 = r12.getWritableDbWithRetries()     // Catch:{ Throwable -> 0x0125 }
                        r4 = r12
                        r12 = r4
                        r12.beginTransaction()     // Catch:{ Throwable -> 0x0125 }
                        java.lang.String r12 = "group_id = ? AND opened = 0 AND dismissed = 0"
                        r5 = r12
                        r12 = 1
                        java.lang.String[] r12 = new java.lang.String[r12]     // Catch:{ Throwable -> 0x0125 }
                        r20 = r12
                        r12 = r20
                        r13 = r20
                        r14 = 0
                        r15 = r0
                        java.lang.String r15 = r4     // Catch:{ Throwable -> 0x0125 }
                        r13[r14] = r15     // Catch:{ Throwable -> 0x0125 }
                        r6 = r12
                        android.content.ContentValues r12 = new android.content.ContentValues     // Catch:{ Throwable -> 0x0125 }
                        r20 = r12
                        r12 = r20
                        r13 = r20
                        r13.<init>()     // Catch:{ Throwable -> 0x0125 }
                        r7 = r12
                        r12 = r7
                        java.lang.String r13 = "dismissed"
                        r14 = 1
                        java.lang.Integer r14 = java.lang.Integer.valueOf(r14)     // Catch:{ Throwable -> 0x0125 }
                        r12.put(r13, r14)     // Catch:{ Throwable -> 0x0125 }
                        r12 = r4
                        java.lang.String r13 = "notification"
                        r14 = r7
                        r15 = r5
                        r16 = r6
                        int r12 = r12.update(r13, r14, r15, r16)     // Catch:{ Throwable -> 0x0125 }
                        r12 = r4
                        android.content.Context r13 = com.onesignal.OneSignal.appContext     // Catch:{ Throwable -> 0x0125 }
                        com.onesignal.BadgeCountUpdater.update(r12, r13)     // Catch:{ Throwable -> 0x0125 }
                        r12 = r4
                        r12.setTransactionSuccessful()     // Catch:{ Throwable -> 0x0125 }
                        r12 = r4
                        if (r12 == 0) goto L_0x00d1
                        r12 = r4
                        r12.endTransaction()     // Catch:{ Throwable -> 0x0119 }
                    L_0x00d1:
                        return
                    L_0x00d2:
                        r12 = move-exception
                        r4 = r12
                        com.onesignal.OneSignal$LOG_LEVEL r12 = com.onesignal.OneSignal.LOG_LEVEL.ERROR     // Catch:{ all -> 0x0107 }
                        java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x0107 }
                        r20 = r13
                        r13 = r20
                        r14 = r20
                        r14.<init>()     // Catch:{ all -> 0x0107 }
                        java.lang.String r14 = "Error getting android notifications part of group: "
                        java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x0107 }
                        r14 = r0
                        java.lang.String r14 = r4     // Catch:{ all -> 0x0107 }
                        java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x0107 }
                        java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x0107 }
                        r14 = r4
                        com.onesignal.OneSignal.Log(r12, r13, r14)     // Catch:{ all -> 0x0107 }
                        r12 = r3
                        if (r12 == 0) goto L_0x007c
                        r12 = r3
                        boolean r12 = r12.isClosed()
                        if (r12 != 0) goto L_0x007c
                        r12 = r3
                        r12.close()
                        goto L_0x007c
                    L_0x0107:
                        r12 = move-exception
                        r9 = r12
                        r12 = r3
                        if (r12 == 0) goto L_0x0117
                        r12 = r3
                        boolean r12 = r12.isClosed()
                        if (r12 != 0) goto L_0x0117
                        r12 = r3
                        r12.close()
                    L_0x0117:
                        r12 = r9
                        throw r12
                    L_0x0119:
                        r12 = move-exception
                        r5 = r12
                        com.onesignal.OneSignal$LOG_LEVEL r12 = com.onesignal.OneSignal.LOG_LEVEL.ERROR
                        java.lang.String r13 = "Error closing transaction! "
                        r14 = r5
                        com.onesignal.OneSignal.Log(r12, r13, r14)
                        goto L_0x00d1
                    L_0x0125:
                        r12 = move-exception
                        r5 = r12
                        com.onesignal.OneSignal$LOG_LEVEL r12 = com.onesignal.OneSignal.LOG_LEVEL.ERROR     // Catch:{ all -> 0x0167 }
                        java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x0167 }
                        r20 = r13
                        r13 = r20
                        r14 = r20
                        r14.<init>()     // Catch:{ all -> 0x0167 }
                        java.lang.String r14 = "Error marking a notifications with group "
                        java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x0167 }
                        r14 = r0
                        java.lang.String r14 = r4     // Catch:{ all -> 0x0167 }
                        java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x0167 }
                        java.lang.String r14 = " as dismissed! "
                        java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ all -> 0x0167 }
                        java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x0167 }
                        r14 = r5
                        com.onesignal.OneSignal.Log(r12, r13, r14)     // Catch:{ all -> 0x0167 }
                        r12 = r4
                        if (r12 == 0) goto L_0x00d1
                        r12 = r4
                        r12.endTransaction()     // Catch:{ Throwable -> 0x015a }
                        goto L_0x00d1
                    L_0x015a:
                        r12 = move-exception
                        r5 = r12
                        com.onesignal.OneSignal$LOG_LEVEL r12 = com.onesignal.OneSignal.LOG_LEVEL.ERROR
                        java.lang.String r13 = "Error closing transaction! "
                        r14 = r5
                        com.onesignal.OneSignal.Log(r12, r13, r14)
                        goto L_0x00d1
                    L_0x0167:
                        r12 = move-exception
                        r10 = r12
                        r12 = r4
                        if (r12 == 0) goto L_0x0170
                        r12 = r4
                        r12.endTransaction()     // Catch:{ Throwable -> 0x0172 }
                    L_0x0170:
                        r12 = r10
                        throw r12
                    L_0x0172:
                        r12 = move-exception
                        r11 = r12
                        com.onesignal.OneSignal$LOG_LEVEL r12 = com.onesignal.OneSignal.LOG_LEVEL.ERROR
                        java.lang.String r13 = "Error closing transaction! "
                        r14 = r11
                        com.onesignal.OneSignal.Log(r12, r13, r14)
                        goto L_0x0170
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OneSignal.C145624.run():void");
                }
            };
            Runnable runCancelGroupedNotifications = runnable;
            if (appContext == null || shouldRunTaskThroughQueue()) {
                LOG_LEVEL log_level = LOG_LEVEL.ERROR;
                new StringBuilder();
                Log(log_level, sb.append("OneSignal.init has not been called. Could not clear notifications part of group ").append(group).append(" - movingthis operation to a waiting task queue.").toString());
                new PendingTaskRunnable(runCancelGroupedNotifications);
                addTaskToQueue(pendingTaskRunnable);
                return;
            }
            runCancelGroupedNotifications.run();
        }
    }

    public static void removeNotificationOpenedHandler() {
        mInitBuilder.mNotificationOpenedHandler = null;
    }

    public static void removeInAppMessageClickHandler() {
        mInitBuilder.mInAppMessageClickHandler = null;
    }

    public static void removeNotificationReceivedHandler() {
        mInitBuilder.mNotificationReceivedHandler = null;
    }

    public static void addPermissionObserver(OSPermissionObserver oSPermissionObserver) {
        OSPermissionObserver observer = oSPermissionObserver;
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not add permission observer");
            return;
        }
        getPermissionStateChangesObserver().addObserver(observer);
        if (getCurrentPermissionState(appContext).compare(getLastPermissionState(appContext))) {
            OSPermissionChangedInternalObserver.fireChangesToPublicObserver(getCurrentPermissionState(appContext));
        }
    }

    public static void removePermissionObserver(OSPermissionObserver oSPermissionObserver) {
        OSPermissionObserver observer = oSPermissionObserver;
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not modify permission observer");
        } else {
            getPermissionStateChangesObserver().removeObserver(observer);
        }
    }

    public static void addSubscriptionObserver(OSSubscriptionObserver oSSubscriptionObserver) {
        OSSubscriptionObserver observer = oSSubscriptionObserver;
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not add subscription observer");
            return;
        }
        getSubscriptionStateChangesObserver().addObserver(observer);
        if (getCurrentSubscriptionState(appContext).compare(getLastSubscriptionState(appContext))) {
            OSSubscriptionChangedInternalObserver.fireChangesToPublicObserver(getCurrentSubscriptionState(appContext));
        }
    }

    public static void removeSubscriptionObserver(OSSubscriptionObserver oSSubscriptionObserver) {
        OSSubscriptionObserver observer = oSSubscriptionObserver;
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not modify subscription observer");
        } else {
            getSubscriptionStateChangesObserver().removeObserver(observer);
        }
    }

    public static void addEmailSubscriptionObserver(@NonNull OSEmailSubscriptionObserver oSEmailSubscriptionObserver) {
        OSEmailSubscriptionObserver observer = oSEmailSubscriptionObserver;
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not add email subscription observer");
            return;
        }
        getEmailSubscriptionStateChangesObserver().addObserver(observer);
        if (getCurrentEmailSubscriptionState(appContext).compare(getLastEmailSubscriptionState(appContext))) {
            OSEmailSubscriptionChangedInternalObserver.fireChangesToPublicObserver(getCurrentEmailSubscriptionState(appContext));
        }
    }

    public static void removeEmailSubscriptionObserver(@NonNull OSEmailSubscriptionObserver oSEmailSubscriptionObserver) {
        OSEmailSubscriptionObserver observer = oSEmailSubscriptionObserver;
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not modify email subscription observer");
        } else {
            getEmailSubscriptionStateChangesObserver().removeObserver(observer);
        }
    }

    public static OSPermissionSubscriptionState getPermissionSubscriptionState() {
        OSPermissionSubscriptionState oSPermissionSubscriptionState;
        if (shouldLogUserPrivacyConsentErrorMessageForMethodName("getPermissionSubscriptionState()")) {
            return null;
        }
        if (appContext == null) {
            Log(LOG_LEVEL.ERROR, "OneSignal.init has not been called. Could not get OSPermissionSubscriptionState");
            return null;
        }
        new OSPermissionSubscriptionState();
        OSPermissionSubscriptionState status = oSPermissionSubscriptionState;
        status.subscriptionStatus = getCurrentSubscriptionState(appContext);
        status.permissionStatus = getCurrentPermissionState(appContext);
        status.emailSubscriptionStatus = getCurrentEmailSubscriptionState(appContext);
        return status;
    }

    public static void addTriggers(Map<String, Object> triggers) {
        OSInAppMessageController.getController().addTriggers(triggers);
    }

    public static void addTriggersFromJsonString(String triggersJsonString) {
        JSONObject jsonObject;
        try {
            new JSONObject(triggersJsonString);
            addTriggers(JSONUtils.jsonObjectToMap(jsonObject));
        } catch (JSONException e) {
            Log(LOG_LEVEL.ERROR, "addTriggersFromJsonString, invalid json", e);
        }
    }

    public static void addTrigger(String key, Object object) {
        HashMap hashMap;
        new HashMap();
        HashMap hashMap2 = hashMap;
        Object put = hashMap2.put(key, object);
        OSInAppMessageController.getController().addTriggers(hashMap2);
    }

    public static void removeTriggersForKeys(Collection<String> keys) {
        OSInAppMessageController.getController().removeTriggersForKeys(keys);
    }

    public static void removeTriggersForKeysFromJsonArrayString(@NonNull String keys) {
        JSONArray jSONArray;
        try {
            new JSONArray(keys);
            JSONArray jsonArray = jSONArray;
            Collection<String> keysCollection = OSUtils.extractStringsFromCollection(JSONUtils.jsonArrayToList(jsonArray));
            if (jsonArray.length() != keysCollection.size()) {
                Log(LOG_LEVEL.WARN, "removeTriggersForKeysFromJsonArrayString: Skipped removing non-String type keys ");
            }
            OSInAppMessageController.getController().removeTriggersForKeys(keysCollection);
        } catch (JSONException e) {
            Log(LOG_LEVEL.ERROR, "removeTriggersForKeysFromJsonArrayString, invalid json", e);
        }
    }

    public static void removeTriggerForKey(String key) {
        ArrayList arrayList;
        new ArrayList();
        ArrayList arrayList2 = arrayList;
        boolean add = arrayList2.add(key);
        OSInAppMessageController.getController().removeTriggersForKeys(arrayList2);
    }

    @Nullable
    public static Object getTriggerValueForKey(String key) {
        return OSInAppMessageController.getController().getTriggerValue(key);
    }

    public static void pauseInAppMessages(boolean pause) {
        OSInAppMessageController.getController().setInAppMessagingEnabled(!pause);
    }

    private static boolean isDuplicateNotification(String str, Context context) {
        StringBuilder sb;
        String id = str;
        Context context2 = context;
        if (id == null || "".equals(id)) {
            return false;
        }
        boolean exists = false;
        Cursor cursor = null;
        try {
            cursor = OneSignalDbHelper.getInstance(context2).getReadableDbWithRetries().query(OneSignalDbContract.NotificationTable.TABLE_NAME, new String[]{"notification_id"}, "notification_id = ?", new String[]{id}, (String) null, (String) null, (String) null);
            exists = cursor.moveToFirst();
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable th) {
            Throwable th2 = th;
            if (cursor != null) {
                cursor.close();
            }
            throw th2;
        }
        if (!exists) {
            return false;
        }
        LOG_LEVEL log_level = LOG_LEVEL.DEBUG;
        new StringBuilder();
        Log(log_level, sb.append("Duplicate GCM message received, skip processing of ").append(id).toString());
        return true;
    }

    static boolean notValidOrDuplicated(Context context, JSONObject jsonPayload) {
        Context context2 = context;
        String id = getNotificationIdFromGCMJsonPayload(jsonPayload);
        return id == null || isDuplicateNotification(id, context2);
    }

    static String getNotificationIdFromGCMJson(@Nullable JSONObject jSONObject) {
        JSONObject jSONObject2;
        JSONObject jsonObject = jSONObject;
        if (jsonObject == null) {
            return null;
        }
        try {
            new JSONObject(jsonObject.getString(GoogleMapStyleOptions.THEME_CUSTOM));
            JSONObject customJSON = jSONObject2;
            if (customJSON.has("i")) {
                return customJSON.optString("i", (String) null);
            }
            Log(LOG_LEVEL.DEBUG, "Not a OneSignal formatted GCM message. No 'i' field in custom.");
            return null;
        } catch (JSONException e) {
            JSONException jSONException = e;
            Log(LOG_LEVEL.DEBUG, "Not a OneSignal formatted GCM message. No 'custom' field in the JSONObject.");
        }
    }

    static String getNotificationIdFromGCMBundle(@Nullable Bundle bundle) {
        JSONObject jSONObject;
        Bundle bundle2 = bundle;
        if (bundle2 == null || bundle2.isEmpty()) {
            return null;
        }
        try {
            if (bundle2.containsKey(GoogleMapStyleOptions.THEME_CUSTOM)) {
                new JSONObject(bundle2.getString(GoogleMapStyleOptions.THEME_CUSTOM));
                JSONObject customJSON = jSONObject;
                if (customJSON.has("i")) {
                    return customJSON.optString("i", (String) null);
                }
                Log(LOG_LEVEL.DEBUG, "Not a OneSignal formatted GCM message. No 'i' field in custom.");
            } else {
                Log(LOG_LEVEL.DEBUG, "Not a OneSignal formatted GCM message. No 'custom' field in the bundle.");
            }
        } catch (Throwable th) {
            Log(LOG_LEVEL.DEBUG, "Could not parse bundle, probably not a OneSignal notification.", th);
        }
        return null;
    }

    private static String getNotificationIdFromGCMJsonPayload(JSONObject jsonPayload) {
        JSONObject customJSON;
        try {
            new JSONObject(jsonPayload.optString(GoogleMapStyleOptions.THEME_CUSTOM));
            return customJSON.optString("i", (String) null);
        } catch (Throwable th) {
            Throwable th2 = th;
            return null;
        }
    }

    static boolean isAppActive() {
        return initDone && isForeground();
    }

    private static boolean isPastOnSessionTime() {
        return System.currentTimeMillis() - getLastSessionTime() >= MIN_ON_SESSION_TIME_MILLIS;
    }

    static boolean areNotificationsEnabledForSubscribedState() {
        if (mInitBuilder.mUnsubscribeWhenNotificationsAreDisabled) {
            return OSUtils.areNotificationsEnabled(appContext);
        }
        return true;
    }

    static void handleSuccessfulEmailLogout() {
        if (emailLogoutHandler != null) {
            emailLogoutHandler.onSuccess();
            emailLogoutHandler = null;
        }
    }

    static void handleFailedEmailLogout() {
        EmailUpdateError emailUpdateError;
        if (emailLogoutHandler != null) {
            new EmailUpdateError(EmailErrorType.NETWORK, "Failed due to network failure. Will retry on next sync.");
            emailLogoutHandler.onFailure(emailUpdateError);
            emailLogoutHandler = null;
        }
    }

    static void fireEmailUpdateSuccess() {
        if (emailUpdateHandler != null) {
            emailUpdateHandler.onSuccess();
            emailUpdateHandler = null;
        }
    }

    static void fireEmailUpdateFailure() {
        EmailUpdateError emailUpdateError;
        if (emailUpdateHandler != null) {
            new EmailUpdateError(EmailErrorType.NETWORK, "Failed due to network failure. Will retry on next sync.");
            emailUpdateHandler.onFailure(emailUpdateError);
            emailUpdateHandler = null;
        }
    }

    static OSSessionManager getSessionManager() {
        return sessionManager;
    }

    static void sendClickActionOutcome(@NonNull String name) {
        sendClickActionOutcomeWithValue(name, 0.0f);
    }

    static void sendClickActionOutcomeWithValue(@NonNull String str, float f) {
        String name = str;
        float value = f;
        if (outcomeEventsController == null) {
            Log(LOG_LEVEL.ERROR, "Make sure OneSignal.init is called first");
        } else {
            outcomeEventsController.sendClickOutcomeEventWithValue(name, value);
        }
    }

    public static void sendOutcome(@NonNull String name) {
        sendOutcome(name, (OutcomeCallback) null);
    }

    public static void sendOutcome(@NonNull String str, OutcomeCallback outcomeCallback) {
        String name = str;
        OutcomeCallback callback = outcomeCallback;
        if (isValidOutcomeEntry(name)) {
            if (outcomeEventsController == null) {
                Log(LOG_LEVEL.ERROR, "Make sure OneSignal.init is called first");
            } else {
                outcomeEventsController.sendOutcomeEvent(name, callback);
            }
        }
    }

    static void sendClickActionUniqueOutcome(@NonNull String str) {
        String name = str;
        if (outcomeEventsController == null) {
            Log(LOG_LEVEL.ERROR, "Make sure OneSignal.init is called first");
        } else {
            outcomeEventsController.sendUniqueClickOutcomeEvent(name);
        }
    }

    public static void sendUniqueOutcome(@NonNull String name) {
        sendUniqueOutcome(name, (OutcomeCallback) null);
    }

    public static void sendUniqueOutcome(@NonNull String str, OutcomeCallback outcomeCallback) {
        String name = str;
        OutcomeCallback callback = outcomeCallback;
        if (isValidOutcomeEntry(name)) {
            if (outcomeEventsController == null) {
                Log(LOG_LEVEL.ERROR, "Make sure OneSignal.init is called first");
            } else {
                outcomeEventsController.sendUniqueOutcomeEvent(name, callback);
            }
        }
    }

    public static void sendOutcomeWithValue(@NonNull String name, float value) {
        sendOutcomeWithValue(name, value, (OutcomeCallback) null);
    }

    public static void sendOutcomeWithValue(@NonNull String str, float f, OutcomeCallback outcomeCallback) {
        String name = str;
        float value = f;
        OutcomeCallback callback = outcomeCallback;
        if (isValidOutcomeEntry(name) && isValidOutcomeValue(value)) {
            if (outcomeEventsController == null) {
                Log(LOG_LEVEL.ERROR, "Make sure OneSignal.init is called first");
            } else {
                outcomeEventsController.sendOutcomeEventWithValue(name, value, callback);
            }
        }
    }

    private static boolean isValidOutcomeValue(float value) {
        if (value > 0.0f) {
            return true;
        }
        Log(LOG_LEVEL.ERROR, "Outcome value must be greater than 0");
        return false;
    }

    private static boolean isValidOutcomeEntry(String str) {
        String name = str;
        if (name != null && !name.isEmpty()) {
            return true;
        }
        Log(LOG_LEVEL.ERROR, "Outcome name must not be empty");
        return false;
    }
}
