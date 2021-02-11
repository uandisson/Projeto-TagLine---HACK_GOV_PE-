package com.onesignal;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.microsoft.appcenter.ingestion.models.CommonProperties;
import com.onesignal.OSSessionManager;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalRestClient;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

class FocusTimeController {
    private static FocusTimeController sInstance;
    private List<FocusTimeProcessorBase> focusTimeProcessors;
    @Nullable
    private Long timeFocusedAtMs;

    private enum FocusEventType {
    }

    private FocusTimeController() {
        FocusTimeProcessorBase focusTimeProcessorBase;
        FocusTimeProcessorBase focusTimeProcessorBase2;
        FocusTimeProcessorBase[] focusTimeProcessorBaseArr = new FocusTimeProcessorBase[2];
        new FocusTimeProcessorUnattributed();
        focusTimeProcessorBaseArr[0] = focusTimeProcessorBase;
        FocusTimeProcessorBase[] focusTimeProcessorBaseArr2 = focusTimeProcessorBaseArr;
        new FocusTimeProcessorAttributed();
        focusTimeProcessorBaseArr2[1] = focusTimeProcessorBase2;
        this.focusTimeProcessors = Arrays.asList(focusTimeProcessorBaseArr2);
    }

    public static synchronized FocusTimeController getInstance() {
        FocusTimeController focusTimeController;
        FocusTimeController focusTimeController2;
        synchronized (FocusTimeController.class) {
            if (sInstance == null) {
                new FocusTimeController();
                sInstance = focusTimeController2;
            }
            focusTimeController = sInstance;
        }
        return focusTimeController;
    }

    /* access modifiers changed from: package-private */
    public void appForegrounded() {
        Long valueOf = Long.valueOf(SystemClock.elapsedRealtime());
        this.timeFocusedAtMs = valueOf;
    }

    /* access modifiers changed from: package-private */
    public void appBackgrounded() {
        boolean giveProcessorsValidFocusTime = giveProcessorsValidFocusTime(OneSignal.getSessionManager().getSessionResult(), FocusEventType.BACKGROUND);
        this.timeFocusedAtMs = null;
    }

    /* access modifiers changed from: package-private */
    public void onSessionEnded(@NonNull OSSessionManager.SessionResult lastSessionResult) {
        FocusEventType focusEventType = FocusEventType.END_SESSION;
        if (!giveProcessorsValidFocusTime(lastSessionResult, focusEventType)) {
            for (FocusTimeProcessorBase focusTimeProcessor : this.focusTimeProcessors) {
                focusTimeProcessor.sendUnsentTimeNow(focusEventType);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void doBlockingBackgroundSyncOfUnsentTime() {
        if (!OneSignal.isForeground()) {
            for (FocusTimeProcessorBase focusTimeProcessor : this.focusTimeProcessors) {
                focusTimeProcessor.syncUnsentTimeFromSyncJob();
            }
        }
    }

    private boolean giveProcessorsValidFocusTime(@NonNull OSSessionManager.SessionResult sessionResult, @NonNull FocusEventType focusEventType) {
        OSSessionManager.SessionResult lastSessionResult = sessionResult;
        FocusEventType focusType = focusEventType;
        Long timeElapsed = getTimeFocusedElapsed();
        if (timeElapsed == null) {
            return false;
        }
        for (FocusTimeProcessorBase focusTimeProcessor : this.focusTimeProcessors) {
            focusTimeProcessor.addTime(timeElapsed.longValue(), lastSessionResult.session, focusType);
        }
        return true;
    }

    @Nullable
    private Long getTimeFocusedElapsed() {
        if (this.timeFocusedAtMs == null) {
            return null;
        }
        long timeElapsed = (long) ((((double) (SystemClock.elapsedRealtime() - this.timeFocusedAtMs.longValue())) / 1000.0d) + 0.5d);
        if (timeElapsed < 1 || timeElapsed > 86400) {
            return null;
        }
        return Long.valueOf(timeElapsed);
    }

    private static class FocusTimeProcessorUnattributed extends FocusTimeProcessorBase {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        FocusTimeProcessorUnattributed() {
            super((C13971) null);
            this.MIN_ON_FOCUS_TIME_SEC = 60;
            this.PREF_KEY_FOR_UNSENT_TIME = OneSignalPrefs.PREFS_GT_UNSENT_ACTIVE_TIME;
        }

        /* access modifiers changed from: protected */
        public boolean timeTypeApplies(@NonNull OSSessionManager.Session session) {
            OSSessionManager.Session session2 = session;
            return session2.isUnattributed() || session2.isDisabled();
        }

        /* access modifiers changed from: protected */
        public void sendTime(@NonNull FocusEventType focusType) {
            if (!focusType.equals(FocusEventType.END_SESSION)) {
                syncUnsentTimeOnBackgroundEvent();
            }
        }
    }

    private static class FocusTimeProcessorAttributed extends FocusTimeProcessorBase {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        FocusTimeProcessorAttributed() {
            super((C13971) null);
            this.MIN_ON_FOCUS_TIME_SEC = 1;
            this.PREF_KEY_FOR_UNSENT_TIME = OneSignalPrefs.PREFS_OS_UNSENT_ATTRIBUTED_ACTIVE_TIME;
        }

        /* access modifiers changed from: protected */
        public boolean timeTypeApplies(@NonNull OSSessionManager.Session session) {
            return session.isAttributed();
        }

        /* access modifiers changed from: protected */
        public void additionalFieldsToAddToOnFocusPayload(@NonNull JSONObject jsonBody) {
            OneSignal.getSessionManager().addSessionNotificationsIds(jsonBody);
        }

        /* access modifiers changed from: protected */
        public void sendTime(@NonNull FocusEventType focusType) {
            if (focusType.equals(FocusEventType.END_SESSION)) {
                syncOnFocusTime();
            } else {
                OneSignalSyncServiceUtils.scheduleSyncTask(OneSignal.appContext);
            }
        }
    }

    private static abstract class FocusTimeProcessorBase {
        protected long MIN_ON_FOCUS_TIME_SEC;
        @NonNull
        protected String PREF_KEY_FOR_UNSENT_TIME;
        @NonNull
        private final AtomicBoolean runningOnFocusTime;
        @Nullable
        private Long unsentActiveTime;

        /* access modifiers changed from: protected */
        public abstract void sendTime(@NonNull FocusEventType focusEventType);

        /* access modifiers changed from: protected */
        public abstract boolean timeTypeApplies(@NonNull OSSessionManager.Session session);

        private FocusTimeProcessorBase() {
            AtomicBoolean atomicBoolean;
            this.unsentActiveTime = null;
            new AtomicBoolean();
            this.runningOnFocusTime = atomicBoolean;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ FocusTimeProcessorBase(C13971 r4) {
            this();
            C13971 r1 = r4;
        }

        private long getUnsentActiveTime() {
            StringBuilder sb;
            if (this.unsentActiveTime == null) {
                this.unsentActiveTime = Long.valueOf(OneSignalPrefs.getLong(OneSignalPrefs.PREFS_ONESIGNAL, this.PREF_KEY_FOR_UNSENT_TIME, 0));
            }
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.Log(log_level, sb.append(getClass().getSimpleName()).append(":getUnsentActiveTime: ").append(this.unsentActiveTime).toString());
            return this.unsentActiveTime.longValue();
        }

        /* access modifiers changed from: private */
        public void saveUnsentActiveTime(long j) {
            StringBuilder sb;
            long time = j;
            this.unsentActiveTime = Long.valueOf(time);
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.Log(log_level, sb.append(getClass().getSimpleName()).append(":saveUnsentActiveTime: ").append(this.unsentActiveTime).toString());
            OneSignalPrefs.saveLong(OneSignalPrefs.PREFS_ONESIGNAL, this.PREF_KEY_FOR_UNSENT_TIME, time);
        }

        /* access modifiers changed from: private */
        public void addTime(long j, @NonNull OSSessionManager.Session session, @NonNull FocusEventType focusEventType) {
            long time = j;
            FocusEventType focusType = focusEventType;
            if (timeTypeApplies(session)) {
                saveUnsentActiveTime(getUnsentActiveTime() + time);
                sendUnsentTimeNow(focusType);
            }
        }

        /* access modifiers changed from: private */
        public void sendUnsentTimeNow(FocusEventType focusEventType) {
            FocusEventType focusType = focusEventType;
            if (OneSignal.hasUserId()) {
                sendTime(focusType);
            }
        }

        private boolean hasMinSyncTime() {
            return getUnsentActiveTime() >= this.MIN_ON_FOCUS_TIME_SEC;
        }

        /* access modifiers changed from: protected */
        public void syncUnsentTimeOnBackgroundEvent() {
            if (hasMinSyncTime()) {
                OneSignalSyncServiceUtils.scheduleSyncTask(OneSignal.appContext);
                syncOnFocusTime();
            }
        }

        /* access modifiers changed from: private */
        public void syncUnsentTimeFromSyncJob() {
            if (hasMinSyncTime()) {
                syncOnFocusTime();
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: protected */
        @WorkerThread
        public void syncOnFocusTime() {
            if (!this.runningOnFocusTime.get()) {
                AtomicBoolean atomicBoolean = this.runningOnFocusTime;
                AtomicBoolean atomicBoolean2 = atomicBoolean;
                synchronized (atomicBoolean) {
                    try {
                        this.runningOnFocusTime.set(true);
                        if (hasMinSyncTime()) {
                            sendOnFocus(getUnsentActiveTime());
                        }
                        this.runningOnFocusTime.set(false);
                    } catch (Throwable th) {
                        Throwable th2 = th;
                        AtomicBoolean atomicBoolean3 = atomicBoolean2;
                        throw th2;
                    }
                }
            }
        }

        private void sendOnFocusToPlayer(@NonNull String userId, @NonNull JSONObject jsonBody) {
            OneSignalRestClient.ResponseHandler responseHandler;
            StringBuilder sb;
            new OneSignalRestClient.ResponseHandler(this) {
                final /* synthetic */ FocusTimeProcessorBase this$0;

                {
                    this.this$0 = this$0;
                }

                /* access modifiers changed from: package-private */
                public void onFailure(int statusCode, String response, Throwable throwable) {
                    OneSignal.logHttpError("sending on_focus Failed", statusCode, throwable, response);
                }

                /* access modifiers changed from: package-private */
                public void onSuccess(String str) {
                    String str2 = str;
                    this.this$0.saveUnsentActiveTime(0);
                }
            };
            new StringBuilder();
            OneSignalRestClient.postSync(sb.append("players/").append(userId).append("/on_focus").toString(), jsonBody, responseHandler);
        }

        /* access modifiers changed from: protected */
        public void additionalFieldsToAddToOnFocusPayload(@NonNull JSONObject jsonBody) {
        }

        @NonNull
        private JSONObject generateOnFocusPayload(long totalTimeActive) throws JSONException {
            JSONObject jSONObject;
            OSUtils oSUtils;
            new JSONObject();
            JSONObject put = jSONObject.put("app_id", OneSignal.getSavedAppId()).put(CommonProperties.TYPE, 1).put("state", "ping").put("active_time", totalTimeActive);
            new OSUtils();
            JSONObject jsonBody = put.put("device_type", oSUtils.getDeviceType());
            OneSignal.addNetType(jsonBody);
            return jsonBody;
        }

        private void sendOnFocus(long j) {
            long totalTimeActive = j;
            try {
                JSONObject jsonBody = generateOnFocusPayload(totalTimeActive);
                additionalFieldsToAddToOnFocusPayload(jsonBody);
                sendOnFocusToPlayer(OneSignal.getUserId(), jsonBody);
                if (OneSignal.hasEmailId()) {
                    sendOnFocusToPlayer(OneSignal.getEmailId(), generateOnFocusPayload(totalTimeActive));
                }
            } catch (JSONException e) {
                OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Generating on_focus:JSON Failed.", e);
            }
        }
    }
}
