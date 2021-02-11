package com.onesignal;

import com.microsoft.appcenter.ingestion.models.properties.LongTypedProperty;
import com.onesignal.LocationGMS;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

abstract class UserState {
    public static final int DEVICE_TYPE_ANDROID = 1;
    public static final int DEVICE_TYPE_EMAIL = 11;
    public static final int DEVICE_TYPE_FIREOS = 2;
    private static final String[] LOCATION_FIELDS;
    private static final Set<String> LOCATION_FIELDS_SET;
    static final int PUSH_STATUS_FIREBASE_FCM_ERROR_IOEXCEPTION = -11;
    static final int PUSH_STATUS_FIREBASE_FCM_ERROR_MISC_EXCEPTION = -12;
    static final int PUSH_STATUS_FIREBASE_FCM_ERROR_SERVICE_NOT_AVAILABLE = -9;
    static final int PUSH_STATUS_FIREBASE_FCM_INIT_ERROR = -8;
    static final int PUSH_STATUS_INVALID_FCM_SENDER_ID = -6;
    static final int PUSH_STATUS_MISSING_ANDROID_SUPPORT_LIBRARY = -3;
    static final int PUSH_STATUS_MISSING_FIREBASE_FCM_LIBRARY = -4;
    static final int PUSH_STATUS_NO_PERMISSION = 0;
    static final int PUSH_STATUS_OUTDATED_ANDROID_SUPPORT_LIBRARY = -5;
    static final int PUSH_STATUS_OUTDATED_GOOGLE_PLAY_SERVICES_APP = -7;
    static final int PUSH_STATUS_SUBSCRIBED = 1;
    static final int PUSH_STATUS_UNSUBSCRIBE = -2;
    private static final Object syncLock;
    JSONObject dependValues;
    private String persistKey;
    JSONObject syncValues;

    /* access modifiers changed from: protected */
    public abstract void addDependFields();

    /* access modifiers changed from: package-private */
    public abstract boolean isSubscribed();

    /* access modifiers changed from: package-private */
    public abstract UserState newInstance(String str);

    static {
        Set<String> set;
        Object obj;
        String[] strArr = new String[7];
        strArr[0] = "lat";
        String[] strArr2 = strArr;
        strArr2[1] = LongTypedProperty.TYPE;
        String[] strArr3 = strArr2;
        strArr3[2] = "loc_acc";
        String[] strArr4 = strArr3;
        strArr4[3] = "loc_type";
        String[] strArr5 = strArr4;
        strArr5[4] = "loc_bg";
        String[] strArr6 = strArr5;
        strArr6[5] = "loc_time_stamp";
        String[] strArr7 = strArr6;
        strArr7[6] = "ad_id";
        LOCATION_FIELDS = strArr7;
        new HashSet(Arrays.asList(LOCATION_FIELDS));
        LOCATION_FIELDS_SET = set;
        new Object() {
        };
        syncLock = obj;
    }

    UserState(String inPersistKey, boolean load) {
        JSONObject jSONObject;
        JSONObject jSONObject2;
        this.persistKey = inPersistKey;
        if (load) {
            loadState();
            return;
        }
        new JSONObject();
        this.dependValues = jSONObject;
        new JSONObject();
        this.syncValues = jSONObject2;
    }

    /* access modifiers changed from: package-private */
    public UserState deepClone(String persistKey2) {
        JSONObject jSONObject;
        JSONObject jSONObject2;
        UserState clonedUserState = newInstance(persistKey2);
        UserState userState = clonedUserState;
        try {
            new JSONObject(this.dependValues.toString());
            userState.dependValues = jSONObject;
            new JSONObject(this.syncValues.toString());
            clonedUserState.syncValues = jSONObject2;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return clonedUserState;
    }

    private Set<String> getGroupChangeFields(UserState userState) {
        UserState changedTo = userState;
        try {
            if (this.dependValues.optLong("loc_time_stamp") != changedTo.dependValues.getLong("loc_time_stamp")) {
                JSONObject put = changedTo.syncValues.put("loc_bg", changedTo.dependValues.opt("loc_bg"));
                JSONObject put2 = changedTo.syncValues.put("loc_time_stamp", changedTo.dependValues.opt("loc_time_stamp"));
                return LOCATION_FIELDS_SET;
            }
        } catch (Throwable th) {
            Throwable th2 = th;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setLocation(LocationGMS.LocationPoint locationPoint) {
        LocationGMS.LocationPoint point = locationPoint;
        try {
            JSONObject put = this.syncValues.put("lat", point.lat);
            JSONObject put2 = this.syncValues.put(LongTypedProperty.TYPE, point.log);
            JSONObject put3 = this.syncValues.put("loc_acc", point.accuracy);
            JSONObject put4 = this.syncValues.put("loc_type", point.type);
            JSONObject put5 = this.dependValues.put("loc_bg", point.f304bg);
            JSONObject put6 = this.dependValues.put("loc_time_stamp", point.timeStamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void clearLocation() {
        try {
            JSONObject put = this.syncValues.put("lat", (Object) null);
            JSONObject put2 = this.syncValues.put(LongTypedProperty.TYPE, (Object) null);
            JSONObject put3 = this.syncValues.put("loc_acc", (Object) null);
            JSONObject put4 = this.syncValues.put("loc_type", (Object) null);
            JSONObject put5 = this.syncValues.put("loc_bg", (Object) null);
            JSONObject put6 = this.syncValues.put("loc_time_stamp", (Object) null);
            JSONObject put7 = this.dependValues.put("loc_bg", (Object) null);
            JSONObject put8 = this.dependValues.put("loc_time_stamp", (Object) null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public JSONObject generateJsonDiff(UserState userState, boolean isSessionCall) {
        UserState newState = userState;
        addDependFields();
        newState.addDependFields();
        JSONObject sendJson = generateJsonDiff(this.syncValues, newState.syncValues, (JSONObject) null, getGroupChangeFields(newState));
        if (!isSessionCall && sendJson.toString().equals("{}")) {
            return null;
        }
        try {
            if (!sendJson.has("app_id")) {
                JSONObject put = sendJson.put("app_id", this.syncValues.optString("app_id"));
            }
            if (this.syncValues.has("email_auth_hash")) {
                JSONObject put2 = sendJson.put("email_auth_hash", this.syncValues.optString("email_auth_hash"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sendJson;
    }

    /* access modifiers changed from: package-private */
    public void set(String key, Object value) {
        try {
            JSONObject put = this.syncValues.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadState() {
        StringBuilder sb;
        JSONObject jSONObject;
        StringBuilder sb2;
        JSONObject jSONObject2;
        JSONObject jSONObject3;
        JSONObject jSONObject4;
        int subscribableStatus;
        String str = OneSignalPrefs.PREFS_ONESIGNAL;
        new StringBuilder();
        String dependValuesStr = OneSignalPrefs.getString(str, sb.append(OneSignalPrefs.PREFS_ONESIGNAL_USERSTATE_DEPENDVALYES_).append(this.persistKey).toString(), (String) null);
        if (dependValuesStr == null) {
            new JSONObject();
            this.dependValues = jSONObject4;
            boolean userSubscribePref = true;
            try {
                if (this.persistKey.equals("CURRENT_STATE")) {
                    subscribableStatus = OneSignalPrefs.getInt(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_ONESIGNAL_SUBSCRIPTION, 1);
                } else {
                    subscribableStatus = OneSignalPrefs.getInt(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_ONESIGNAL_SYNCED_SUBSCRIPTION, 1);
                }
                if (subscribableStatus == -2) {
                    subscribableStatus = 1;
                    userSubscribePref = false;
                }
                JSONObject put = this.dependValues.put("subscribableStatus", subscribableStatus);
                JSONObject put2 = this.dependValues.put("userSubscribePref", userSubscribePref);
            } catch (JSONException e) {
                JSONException jSONException = e;
            }
        } else {
            try {
                new JSONObject(dependValuesStr);
                this.dependValues = jSONObject;
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        String str2 = OneSignalPrefs.PREFS_ONESIGNAL;
        new StringBuilder();
        String syncValuesStr = OneSignalPrefs.getString(str2, sb2.append(OneSignalPrefs.PREFS_ONESIGNAL_USERSTATE_SYNCVALYES_).append(this.persistKey).toString(), (String) null);
        if (syncValuesStr == null) {
            try {
                new JSONObject();
                this.syncValues = jSONObject3;
                JSONObject put3 = this.syncValues.put("identifier", OneSignalPrefs.getString(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_GT_REGISTRATION_ID, (String) null));
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        } else {
            new JSONObject(syncValuesStr);
            this.syncValues = jSONObject2;
        }
    }

    /* access modifiers changed from: package-private */
    public void persistState() {
        StringBuilder sb;
        StringBuilder sb2;
        Object obj = syncLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                String str = OneSignalPrefs.PREFS_ONESIGNAL;
                new StringBuilder();
                OneSignalPrefs.saveString(str, sb.append(OneSignalPrefs.PREFS_ONESIGNAL_USERSTATE_SYNCVALYES_).append(this.persistKey).toString(), this.syncValues.toString());
                String str2 = OneSignalPrefs.PREFS_ONESIGNAL;
                new StringBuilder();
                OneSignalPrefs.saveString(str2, sb2.append(OneSignalPrefs.PREFS_ONESIGNAL_USERSTATE_DEPENDVALYES_).append(this.persistKey).toString(), this.dependValues.toString());
            } catch (Throwable th) {
                Throwable th2 = th;
                Object obj3 = obj2;
                throw th2;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void persistStateAfterSync(JSONObject jSONObject, JSONObject jSONObject2) {
        JSONObject inDependValues = jSONObject;
        JSONObject inSyncValues = jSONObject2;
        if (inDependValues != null) {
            JSONObject generateJsonDiff = generateJsonDiff(this.dependValues, inDependValues, this.dependValues, (Set<String>) null);
        }
        if (inSyncValues != null) {
            JSONObject generateJsonDiff2 = generateJsonDiff(this.syncValues, inSyncValues, this.syncValues, (Set<String>) null);
            mergeTags(inSyncValues, (JSONObject) null);
        }
        if (inDependValues != null || inSyncValues != null) {
            persistState();
        }
    }

    /* access modifiers changed from: package-private */
    public void mergeTags(JSONObject jSONObject, JSONObject jSONObject2) {
        JSONObject jSONObject3;
        JSONObject newTags;
        JSONObject jSONObject4;
        JSONObject jSONObject5;
        JSONObject inSyncValues = jSONObject;
        JSONObject omitKeys = jSONObject2;
        Object obj = syncLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                if (inSyncValues.has("tags")) {
                    if (this.syncValues.has("tags")) {
                        JSONObject jSONObject6 = jSONObject5;
                        new JSONObject(this.syncValues.optString("tags"));
                        newTags = jSONObject6;
                    } else {
                        JSONObject jSONObject7 = jSONObject3;
                        new JSONObject();
                        newTags = jSONObject7;
                    }
                    JSONObject curTags = inSyncValues.optJSONObject("tags");
                    Iterator<String> keys = curTags.keys();
                    while (keys.hasNext()) {
                        try {
                            String key = keys.next();
                            if ("".equals(curTags.optString(key))) {
                                Object remove = newTags.remove(key);
                            } else {
                                if (omitKeys != null) {
                                    if (omitKeys.has(key)) {
                                    }
                                }
                                JSONObject put = newTags.put(key, curTags.optString(key));
                            }
                        } catch (Throwable th) {
                            Throwable th2 = th;
                        }
                    }
                    if (newTags.toString().equals("{}")) {
                        Object remove2 = this.syncValues.remove("tags");
                    } else {
                        JSONObject put2 = this.syncValues.put("tags", newTags);
                    }
                }
            } catch (JSONException e) {
                JSONException jSONException = e;
                new JSONObject();
                newTags = jSONObject4;
            } catch (Throwable th3) {
                while (true) {
                    Throwable th4 = th3;
                    Object obj3 = obj2;
                    throw th4;
                }
            }
        }
    }

    private static JSONObject generateJsonDiff(JSONObject jSONObject, JSONObject jSONObject2, JSONObject jSONObject3, Set<String> set) {
        JSONObject cur = jSONObject;
        JSONObject changedTo = jSONObject2;
        JSONObject baseOutput = jSONObject3;
        Set<String> includeFields = set;
        Object obj = syncLock;
        Object obj2 = obj;
        synchronized (obj) {
            try {
                JSONObject cur2 = JSONUtils.generateJsonDiff(cur, changedTo, baseOutput, includeFields);
                return cur2;
            } catch (Throwable th) {
                Throwable th2 = th;
                Object obj3 = obj2;
                throw th2;
            }
        }
    }
}
