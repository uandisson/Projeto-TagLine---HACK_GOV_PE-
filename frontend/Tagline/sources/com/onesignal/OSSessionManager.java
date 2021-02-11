package com.onesignal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalDbContract;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class OSSessionManager {
    private static final String DIRECT_TAG = "direct";
    @Nullable
    private String directNotificationId;
    @Nullable
    private JSONArray indirectNotificationIds;
    @NonNull
    protected Session session;
    @NonNull
    private SessionListener sessionListener;

    interface SessionListener {
        void onSessionEnding(@NonNull SessionResult sessionResult);
    }

    public static class SessionResult {
        @Nullable
        JSONArray notificationIds;
        @NonNull
        Session session;

        SessionResult(@NonNull Builder builder) {
            Builder builder2 = builder;
            this.notificationIds = builder2.notificationIds;
            this.session = builder2.session;
        }

        public static class Builder {
            /* access modifiers changed from: private */
            public JSONArray notificationIds;
            /* access modifiers changed from: private */
            public Session session;

            public static Builder newInstance() {
                Builder builder;
                Builder builder2 = builder;
                new Builder();
                return builder2;
            }

            private Builder() {
            }

            public Builder setNotificationIds(@Nullable JSONArray notificationIds2) {
                this.notificationIds = notificationIds2;
                return this;
            }

            public Builder setSession(@NonNull Session session2) {
                this.session = session2;
                return this;
            }

            public SessionResult build() {
                SessionResult sessionResult;
                new SessionResult(this);
                return sessionResult;
            }
        }
    }

    public enum Session {
        ;

        public boolean isDirect() {
            return equals(DIRECT);
        }

        public boolean isIndirect() {
            return equals(INDIRECT);
        }

        public boolean isAttributed() {
            return isDirect() || isIndirect();
        }

        public boolean isUnattributed() {
            return equals(UNATTRIBUTED);
        }

        public boolean isDisabled() {
            return equals(DISABLED);
        }

        @NonNull
        public static Session fromString(String str) {
            String value = str;
            if (value == null || value.isEmpty()) {
                return UNATTRIBUTED;
            }
            Session[] values = values();
            int length = values.length;
            for (int i = 0; i < length; i++) {
                Session type = values[i];
                if (type.name().equalsIgnoreCase(value)) {
                    return type;
                }
            }
            return UNATTRIBUTED;
        }
    }

    public OSSessionManager(@NonNull SessionListener sessionListener2) {
        this.sessionListener = sessionListener2;
        initSessionFromCache();
    }

    private void initSessionFromCache() {
        this.session = OutcomesUtils.getCachedSession();
        if (this.session.isIndirect()) {
            this.indirectNotificationIds = getLastNotificationsReceivedIds();
        } else if (this.session.isDirect()) {
            this.directNotificationId = OutcomesUtils.getCachedNotificationOpenId();
        }
    }

    /* access modifiers changed from: package-private */
    public void addSessionNotificationsIds(@NonNull JSONObject jSONObject) {
        JSONArray jSONArray;
        JSONObject jsonObject = jSONObject;
        if (!this.session.isUnattributed()) {
            try {
                if (this.session.isDirect()) {
                    JSONObject put = jsonObject.put(DIRECT_TAG, true);
                    new JSONArray();
                    JSONObject put2 = jsonObject.put(OneSignalDbContract.OutcomeEventsTable.COLUMN_NAME_NOTIFICATION_IDS, jSONArray.put(this.directNotificationId));
                } else if (this.session.isIndirect()) {
                    JSONObject put3 = jsonObject.put(DIRECT_TAG, false);
                    JSONObject put4 = jsonObject.put(OneSignalDbContract.OutcomeEventsTable.COLUMN_NAME_NOTIFICATION_IDS, this.indirectNotificationIds);
                }
            } catch (JSONException e) {
                OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Generating addNotificationId:JSON Failed.", e);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void restartSessionIfNeeded() {
        if (!OneSignal.getAppEntryState().isNotificationClick()) {
            JSONArray lastNotifications = getLastNotificationsReceivedIds();
            if (lastNotifications.length() > 0) {
                setSession(Session.INDIRECT, (String) null, lastNotifications);
            } else {
                setSession(Session.UNATTRIBUTED, (String) null, (JSONArray) null);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Session getSession() {
        return this.session;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public String getDirectNotificationId() {
        return this.directNotificationId;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public JSONArray getIndirectNotificationIds() {
        return this.indirectNotificationIds;
    }

    /* access modifiers changed from: package-private */
    public void onDirectSessionFromNotificationOpen(@NonNull String notificationId) {
        setSession(Session.DIRECT, notificationId, (JSONArray) null);
    }

    private void setSession(@NonNull Session session2, @Nullable String str, @Nullable JSONArray jSONArray) {
        StringBuilder sb;
        Session session3 = session2;
        String directNotificationId2 = str;
        JSONArray indirectNotificationIds2 = jSONArray;
        if (willChangeSession(session3, directNotificationId2, indirectNotificationIds2)) {
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            new StringBuilder();
            OneSignal.Log(log_level, sb.append("OSSession changed\nfrom:\nsession: ").append(this.session).append(", directNotificationId: ").append(this.directNotificationId).append(", indirectNotificationIds: ").append(this.indirectNotificationIds).append("\nto:\nsession: ").append(session3).append(", directNotificationId: ").append(directNotificationId2).append(", indirectNotificationIds: ").append(indirectNotificationIds2).toString());
            OutcomesUtils.cacheCurrentSession(session3);
            OutcomesUtils.cacheNotificationOpenId(directNotificationId2);
            this.sessionListener.onSessionEnding(getSessionResult());
            this.session = session3;
            this.directNotificationId = directNotificationId2;
            this.indirectNotificationIds = indirectNotificationIds2;
        }
    }

    private boolean willChangeSession(@NonNull Session session2, @Nullable String str, @Nullable JSONArray jSONArray) {
        String directNotificationId2 = str;
        JSONArray indirectNotificationIds2 = jSONArray;
        if (!session2.equals(this.session)) {
            return true;
        }
        if (this.session.isDirect() && this.directNotificationId != null && !this.directNotificationId.equals(directNotificationId2)) {
            return true;
        }
        if (!this.session.isIndirect() || this.indirectNotificationIds == null || this.indirectNotificationIds.length() <= 0 || JSONUtils.compareJSONArrays(this.indirectNotificationIds, indirectNotificationIds2)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public JSONArray getLastNotificationsReceivedIds() {
        JSONArray jSONArray;
        JSONArray notificationsReceived = OutcomesUtils.getLastNotificationsReceivedData();
        new JSONArray();
        JSONArray notificationsIds = jSONArray;
        long attributionWindow = ((long) (OutcomesUtils.getIndirectAttributionWindow() * 60)) * 1000;
        long currentTime = System.currentTimeMillis();
        for (int i = 0; i < notificationsReceived.length(); i++) {
            try {
                JSONObject jsonObject = notificationsReceived.getJSONObject(i);
                if (currentTime - jsonObject.getLong("time") <= attributionWindow) {
                    JSONArray put = notificationsIds.put(jsonObject.getString("notification_id"));
                }
            } catch (JSONException e) {
                OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "From getting notification from array:JSON Failed.", e);
            }
        }
        return notificationsIds;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public SessionResult getSessionResult() {
        JSONArray jSONArray;
        if (this.session.isDirect()) {
            if (OutcomesUtils.isDirectSessionEnabled()) {
                new JSONArray();
                return SessionResult.Builder.newInstance().setNotificationIds(jSONArray.put(this.directNotificationId)).setSession(Session.DIRECT).build();
            }
        } else if (this.session.isIndirect()) {
            if (OutcomesUtils.isIndirectSessionEnabled()) {
                return SessionResult.Builder.newInstance().setNotificationIds(this.indirectNotificationIds).setSession(Session.INDIRECT).build();
            }
        } else if (OutcomesUtils.isUnattributedSessionEnabled()) {
            return SessionResult.Builder.newInstance().setSession(Session.UNATTRIBUTED).build();
        }
        return SessionResult.Builder.newInstance().setSession(Session.DISABLED).build();
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public SessionResult getIAMSessionResult() {
        if (OutcomesUtils.isUnattributedSessionEnabled()) {
            return SessionResult.Builder.newInstance().setSession(Session.UNATTRIBUTED).build();
        }
        return SessionResult.Builder.newInstance().setSession(Session.DISABLED).build();
    }

    /* access modifiers changed from: package-private */
    public void attemptSessionUpgrade() {
        if (OneSignal.getAppEntryState().isNotificationClick()) {
            setSession(Session.DIRECT, this.directNotificationId, (JSONArray) null);
        } else if (this.session.isUnattributed()) {
            JSONArray lastNotificationIds = getLastNotificationsReceivedIds();
            if (lastNotificationIds.length() > 0 && OneSignal.getAppEntryState().isAppOpen()) {
                setSession(Session.INDIRECT, (String) null, lastNotificationIds);
            }
        }
    }
}
