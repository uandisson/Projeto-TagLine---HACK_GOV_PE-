package com.onesignal;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.onesignal.OSSessionManager;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalRemoteParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class OutcomesUtils {
    static final String NOTIFICATIONS_IDS = "notification_ids";
    static final String NOTIFICATION_ID = "notification_id";
    static final String TIME = "time";

    OutcomesUtils() {
    }

    static void cacheCurrentSession(@NonNull OSSessionManager.Session session) {
        OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_OUTCOMES_CURRENT_SESSION, session.toString());
    }

    @NonNull
    static OSSessionManager.Session getCachedSession() {
        return OSSessionManager.Session.fromString(OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_OUTCOMES_CURRENT_SESSION, OSSessionManager.Session.UNATTRIBUTED.toString()));
    }

    static void cacheNotificationOpenId(@Nullable String id) {
        OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_LAST_ATTRIBUTED_NOTIFICATION_OPEN, id);
    }

    @Nullable
    static String getCachedNotificationOpenId() {
        return OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_LAST_ATTRIBUTED_NOTIFICATION_OPEN, (String) null);
    }

    static void markLastNotificationReceived(@Nullable String str) {
        StringBuilder sb;
        JSONArray jSONArray;
        JSONObject jSONObject;
        JSONArray jSONArray2;
        String notificationId = str;
        OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
        new StringBuilder();
        OneSignal.Log(log_level, sb.append("Notification markLastNotificationReceived with id: ").append(notificationId).toString());
        if (notificationId != null && !notificationId.isEmpty()) {
            try {
                new JSONArray(OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_LAST_NOTIFICATIONS_RECEIVED, "[]"));
                JSONArray notificationsReceived = jSONArray;
                new JSONObject();
                JSONArray put = notificationsReceived.put(jSONObject.put("notification_id", notificationId).put(TIME, System.currentTimeMillis()));
                int notificationLimit = getNotificationLimit();
                JSONArray notificationsToSave = notificationsReceived;
                if (notificationsReceived.length() > notificationLimit) {
                    int lengthDifference = notificationsReceived.length() - notificationLimit;
                    if (Build.VERSION.SDK_INT >= 19) {
                        for (int i = 0; i < lengthDifference; i++) {
                            Object remove = notificationsReceived.remove(i);
                        }
                    } else {
                        new JSONArray();
                        notificationsToSave = jSONArray2;
                        for (int i2 = lengthDifference; i2 < notificationsReceived.length(); i2++) {
                            JSONArray put2 = notificationsToSave.put(notificationsReceived.get(i2));
                        }
                    }
                }
                OneSignalPrefs.saveString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_LAST_NOTIFICATIONS_RECEIVED, notificationsToSave.toString());
            } catch (JSONException e) {
                OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Generating direct notification arrived:JSON Failed.", e);
            }
        }
    }

    static JSONArray getLastNotificationsReceivedData() {
        JSONArray jSONArray;
        JSONArray jSONArray2;
        try {
            JSONArray jSONArray3 = jSONArray2;
            new JSONArray(OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_LAST_NOTIFICATIONS_RECEIVED, "[]"));
            return jSONArray3;
        } catch (JSONException e) {
            OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Generating last notifications received data:JSON Failed.", e);
            new JSONArray();
            return jSONArray;
        }
    }

    static int getNotificationLimit() {
        return OneSignalPrefs.getInt(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_NOTIFICATION_LIMIT, 10);
    }

    static int getIndirectAttributionWindow() {
        return OneSignalPrefs.getInt(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_INDIRECT_ATTRIBUTION_WINDOW, 1440);
    }

    static boolean isDirectSessionEnabled() {
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_DIRECT_ENABLED, false);
    }

    static boolean isIndirectSessionEnabled() {
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_INDIRECT_ENABLED, false);
    }

    static boolean isUnattributedSessionEnabled() {
        return OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_UNATTRIBUTED_ENABLED, false);
    }

    static void saveOutcomesParams(OneSignalRemoteParams.OutcomesParams outcomesParams) {
        OneSignalRemoteParams.OutcomesParams outcomesParams2 = outcomesParams;
        OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_DIRECT_ENABLED, outcomesParams2.directEnabled);
        OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_INDIRECT_ENABLED, outcomesParams2.indirectEnabled);
        OneSignalPrefs.saveBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_UNATTRIBUTED_ENABLED, outcomesParams2.unattributedEnabled);
        OneSignalPrefs.saveInt(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_NOTIFICATION_LIMIT, outcomesParams2.notificationLimit);
        OneSignalPrefs.saveInt(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_INDIRECT_ATTRIBUTION_WINDOW, outcomesParams2.indirectAttributionWindow);
    }
}
