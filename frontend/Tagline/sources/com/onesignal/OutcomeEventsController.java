package com.onesignal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.onesignal.OSSessionManager;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalRestClient;
import java.util.Set;
import org.json.JSONArray;

class OutcomeEventsController {
    private static final String OS_SAVE_OUTCOMES = "OS_SAVE_OUTCOMES";
    private static final String OS_SAVE_UNIQUE_OUTCOME_NOTIFICATIONS = "OS_SAVE_UNIQUE_OUTCOME_NOTIFICATIONS";
    private static final String OS_SEND_SAVED_OUTCOMES = "OS_SEND_SAVED_OUTCOMES";
    @NonNull
    private final OSSessionManager osSessionManager;
    /* access modifiers changed from: private */
    @NonNull
    public final OutcomeEventsRepository outcomeEventsRepository;
    private Set<String> unattributedUniqueOutcomeEventsSentSet;

    public OutcomeEventsController(@NonNull OSSessionManager osSessionManager2, @NonNull OutcomeEventsRepository outcomeEventsRepository2) {
        this.osSessionManager = osSessionManager2;
        this.outcomeEventsRepository = outcomeEventsRepository2;
        initUniqueOutcomeEventsSentSets();
    }

    OutcomeEventsController(@NonNull OSSessionManager osSessionManager2, @NonNull OneSignalDbHelper dbHelper) {
        OutcomeEventsRepository outcomeEventsRepository2;
        new OutcomeEventsRepository(dbHelper);
        this.outcomeEventsRepository = outcomeEventsRepository2;
        this.osSessionManager = osSessionManager2;
        initUniqueOutcomeEventsSentSets();
    }

    private void initUniqueOutcomeEventsSentSets() {
        this.unattributedUniqueOutcomeEventsSentSet = OSUtils.newConcurrentSet();
        Set<String> tempUnattributedUniqueOutcomeEventsSentSet = OneSignalPrefs.getStringSet(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_UNATTRIBUTED_UNIQUE_OUTCOME_EVENTS_SENT, (Set<String>) null);
        if (tempUnattributedUniqueOutcomeEventsSentSet != null) {
            boolean addAll = this.unattributedUniqueOutcomeEventsSentSet.addAll(tempUnattributedUniqueOutcomeEventsSentSet);
        }
    }

    /* access modifiers changed from: package-private */
    public void cleanOutcomes() {
        this.unattributedUniqueOutcomeEventsSentSet = OSUtils.newConcurrentSet();
        saveUnattributedUniqueOutcomeEvents();
    }

    /* access modifiers changed from: package-private */
    public void sendSavedOutcomes() {
        Thread thread;
        Runnable runnable;
        new Runnable(this) {
            final /* synthetic */ OutcomeEventsController this$0;

            {
                this.this$0 = this$0;
            }

            public void run() {
                Thread.currentThread().setPriority(10);
                for (OutcomeEvent event : this.this$0.outcomeEventsRepository.getSavedOutcomeEvents()) {
                    this.this$0.sendSavedOutcomeEvent(event);
                }
            }
        };
        new Thread(runnable, OS_SEND_SAVED_OUTCOMES);
        thread.start();
    }

    /* access modifiers changed from: package-private */
    public void sendUniqueOutcomeEvent(@NonNull String name, @Nullable OneSignal.OutcomeCallback callback) {
        sendUniqueOutcomeEvent(name, this.osSessionManager.getSessionResult(), this.osSessionManager.getSession(), callback);
    }

    /* access modifiers changed from: package-private */
    public void sendUniqueClickOutcomeEvent(@NonNull String name) {
        sendUniqueOutcomeEvent(name, this.osSessionManager.getIAMSessionResult(), OSSessionManager.Session.UNATTRIBUTED, (OneSignal.OutcomeCallback) null);
    }

    /* access modifiers changed from: package-private */
    public void sendOutcomeEvent(@NonNull String name, @Nullable OneSignal.OutcomeCallback callback) {
        OSSessionManager.SessionResult sessionResult = this.osSessionManager.getSessionResult();
        sendAndCreateOutcomeEvent(name, 0.0f, sessionResult.notificationIds, sessionResult.session, callback);
    }

    /* access modifiers changed from: package-private */
    public void sendOutcomeEventWithValue(@NonNull String name, float weight, @Nullable OneSignal.OutcomeCallback callback) {
        OSSessionManager.SessionResult sessionResult = this.osSessionManager.getSessionResult();
        sendAndCreateOutcomeEvent(name, weight, sessionResult.notificationIds, sessionResult.session, callback);
    }

    /* access modifiers changed from: package-private */
    public void sendClickOutcomeEventWithValue(@NonNull String name, float weight) {
        OSSessionManager.SessionResult sessionResult = this.osSessionManager.getIAMSessionResult();
        sendAndCreateOutcomeEvent(name, weight, sessionResult.notificationIds, sessionResult.session, (OneSignal.OutcomeCallback) null);
    }

    private void sendUniqueOutcomeEvent(@NonNull String str, @NonNull OSSessionManager.SessionResult sessionResult, OSSessionManager.Session session, @Nullable OneSignal.OutcomeCallback outcomeCallback) {
        StringBuilder sb;
        StringBuilder sb2;
        String name = str;
        OSSessionManager.SessionResult sessionResult2 = sessionResult;
        OSSessionManager.Session currentSession = session;
        OneSignal.OutcomeCallback callback = outcomeCallback;
        OSSessionManager.Session session2 = sessionResult2.session;
        JSONArray notificationIds = sessionResult2.notificationIds;
        if (currentSession.isAttributed()) {
            JSONArray uniqueNotificationIds = getUniqueNotificationIds(name, notificationIds);
            if (uniqueNotificationIds == null) {
                OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
                new StringBuilder();
                OneSignal.Log(log_level, sb2.append("Measure endpoint will not send because unique outcome already sent for: \nSession: ").append(this.osSessionManager.getSession().toString()).append("\nOutcome name: ").append(name).append("\nnotificationIds: ").append(notificationIds).toString());
                if (callback != null) {
                    callback.onSuccess((OutcomeEvent) null);
                    return;
                }
                return;
            }
            sendAndCreateOutcomeEvent(name, 0.0f, uniqueNotificationIds, session2, callback);
        } else if (!currentSession.isUnattributed()) {
            OneSignal.Log(OneSignal.LOG_LEVEL.DEBUG, "Unique Outcome for current session is disabled");
        } else if (this.unattributedUniqueOutcomeEventsSentSet.contains(name)) {
            OneSignal.LOG_LEVEL log_level2 = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.Log(log_level2, sb.append("Measure endpoint will not send because unique outcome already sent for: \nSession: ").append(this.osSessionManager.getSession().toString()).append("\nOutcome name: ").append(name).toString());
            if (callback != null) {
                callback.onSuccess((OutcomeEvent) null);
            }
        } else {
            boolean add = this.unattributedUniqueOutcomeEventsSentSet.add(name);
            sendAndCreateOutcomeEvent(name, 0.0f, (JSONArray) null, session2, callback);
        }
    }

    /* access modifiers changed from: private */
    public void sendSavedOutcomeEvent(@NonNull OutcomeEvent outcomeEvent) {
        OneSignalRestClient.ResponseHandler responseHandler;
        OutcomeEvent event = outcomeEvent;
        final OutcomeEvent outcomeEvent2 = event;
        new OneSignalRestClient.ResponseHandler(this) {
            final /* synthetic */ OutcomeEventsController this$0;

            {
                this.this$0 = this$0;
            }

            /* access modifiers changed from: package-private */
            public void onSuccess(String response) {
                super.onSuccess(response);
                this.this$0.outcomeEventsRepository.removeEvent(outcomeEvent2);
            }
        };
        sendOutcomeEvent(event, responseHandler);
    }

    private void sendAndCreateOutcomeEvent(@NonNull String str, @NonNull float weight, @Nullable JSONArray jSONArray, @NonNull OSSessionManager.Session session, @Nullable OneSignal.OutcomeCallback callback) {
        OutcomeEvent outcomeEvent;
        OneSignalRestClient.ResponseHandler responseHandler;
        String name = str;
        JSONArray notificationIds = jSONArray;
        OSSessionManager.Session session2 = session;
        new OutcomeEvent(session2, notificationIds, name, System.currentTimeMillis() / 1000, weight);
        OutcomeEvent outcomeEvent2 = outcomeEvent;
        final OSSessionManager.Session session3 = session2;
        final JSONArray jSONArray2 = notificationIds;
        final String str2 = name;
        final OneSignal.OutcomeCallback outcomeCallback = callback;
        final OutcomeEvent outcomeEvent3 = outcomeEvent2;
        new OneSignalRestClient.ResponseHandler(this) {
            final /* synthetic */ OutcomeEventsController this$0;

            {
                this.this$0 = this$0;
            }

            /* access modifiers changed from: package-private */
            public void onSuccess(String response) {
                super.onSuccess(response);
                if (session3.isAttributed()) {
                    this.this$0.saveAttributedUniqueOutcomeNotifications(jSONArray2, str2);
                } else {
                    this.this$0.saveUnattributedUniqueOutcomeEvents();
                }
                if (outcomeCallback != null) {
                    outcomeCallback.onSuccess(outcomeEvent3);
                }
            }

            /* access modifiers changed from: package-private */
            public void onFailure(int i, String str, Throwable throwable) {
                Thread thread;
                Runnable runnable;
                StringBuilder sb;
                int statusCode = i;
                String response = str;
                super.onFailure(statusCode, response, throwable);
                new Runnable(this) {
                    final /* synthetic */ C14823 this$1;

                    {
                        this.this$1 = this$1;
                    }

                    public void run() {
                        Thread.currentThread().setPriority(10);
                        this.this$1.this$0.outcomeEventsRepository.saveOutcomeEvent(outcomeEvent3);
                    }
                };
                new Thread(runnable, OutcomeEventsController.OS_SAVE_OUTCOMES);
                thread.start();
                OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.WARN;
                new StringBuilder();
                OneSignal.onesignalLog(log_level, sb.append("Sending outcome with name: ").append(str2).append(" failed with status code: ").append(statusCode).append(" and response: ").append(response).append("\nOutcome event was cached and will be reattempted on app cold start").toString());
                if (outcomeCallback != null) {
                    outcomeCallback.onSuccess((OutcomeEvent) null);
                }
            }
        };
        sendOutcomeEvent(outcomeEvent2, responseHandler);
    }

    private void sendOutcomeEvent(@NonNull OutcomeEvent outcomeEvent, OneSignalRestClient.ResponseHandler responseHandler) {
        OSUtils oSUtils;
        OutcomeEvent outcomeEvent2 = outcomeEvent;
        OneSignalRestClient.ResponseHandler responseHandler2 = responseHandler;
        String appId = OneSignal.appId;
        new OSUtils();
        int deviceType = oSUtils.getDeviceType();
        switch (outcomeEvent2.getSession()) {
            case DIRECT:
                this.outcomeEventsRepository.requestMeasureDirectOutcomeEvent(appId, deviceType, outcomeEvent2, responseHandler2);
                return;
            case INDIRECT:
                this.outcomeEventsRepository.requestMeasureIndirectOutcomeEvent(appId, deviceType, outcomeEvent2, responseHandler2);
                return;
            case UNATTRIBUTED:
                this.outcomeEventsRepository.requestMeasureUnattributedOutcomeEvent(appId, deviceType, outcomeEvent2, responseHandler2);
                return;
            case DISABLED:
                OneSignal.Log(OneSignal.LOG_LEVEL.VERBOSE, "Outcomes for current session are disabled");
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public void saveAttributedUniqueOutcomeNotifications(JSONArray notificationIds, String name) {
        Thread thread;
        Runnable runnable;
        final JSONArray jSONArray = notificationIds;
        final String str = name;
        new Runnable(this) {
            final /* synthetic */ OutcomeEventsController this$0;

            {
                this.this$0 = this$0;
            }

            public void run() {
                Thread.currentThread().setPriority(10);
                this.this$0.outcomeEventsRepository.saveUniqueOutcomeNotifications(jSONArray, str);
            }
        };
        new Thread(runnable, OS_SAVE_UNIQUE_OUTCOME_NOTIFICATIONS);
        thread.start();
    }

    /* access modifiers changed from: private */
    public void saveUnattributedUniqueOutcomeEvents() {
        OneSignalPrefs.saveStringSet(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_UNATTRIBUTED_UNIQUE_OUTCOME_EVENTS_SENT, this.unattributedUniqueOutcomeEventsSentSet);
    }

    private JSONArray getUniqueNotificationIds(String name, JSONArray notificationIds) {
        JSONArray uniqueNotificationIds = this.outcomeEventsRepository.getNotCachedUniqueOutcomeNotifications(name, notificationIds);
        if (uniqueNotificationIds.length() == 0) {
            return null;
        }
        return uniqueNotificationIds;
    }
}
