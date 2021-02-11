package com.onesignal;

import android.support.annotation.Nullable;
import com.onesignal.OneSignalStateSynchronizer;
import com.onesignal.UserStateSynchronizer;
import org.json.JSONException;
import org.json.JSONObject;

class UserStateEmailSynchronizer extends UserStateSynchronizer {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    UserStateEmailSynchronizer() {
        super(OneSignalStateSynchronizer.UserStateSynchronizerType.EMAIL);
    }

    /* access modifiers changed from: protected */
    public UserState newUserState(String inPersistKey, boolean load) {
        UserState userState;
        new UserStateEmail(inPersistKey, load);
        return userState;
    }

    /* access modifiers changed from: package-private */
    public boolean getSubscribed() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public UserStateSynchronizer.GetTagsResult getTags(boolean z) {
        boolean z2 = z;
        return null;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public String getExternalId(boolean z) {
        boolean z2 = z;
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setSubscription(boolean enable) {
    }

    public boolean getUserSubscribePreference() {
        return false;
    }

    public void setPermission(boolean enable) {
    }

    /* access modifiers changed from: package-private */
    public void updateState(JSONObject state) {
    }

    /* access modifiers changed from: package-private */
    public void refresh() {
        scheduleSyncToServer();
    }

    /* access modifiers changed from: protected */
    public void scheduleSyncToServer() {
        if (!(getId() == null && getRegistrationId() == null) && OneSignal.getUserId() != null) {
            getNetworkHandlerThread(0).runNewJobDelayed();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x003b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setEmail(java.lang.String r14, java.lang.String r15) {
        /*
            r13 = this;
            r0 = r13
            r1 = r14
            r2 = r15
            r7 = r0
            com.onesignal.UserState r7 = r7.getUserStateForModification()
            org.json.JSONObject r7 = r7.syncValues
            r3 = r7
            r7 = r1
            r8 = r3
            java.lang.String r9 = "identifier"
            java.lang.String r8 = r8.optString(r9)
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0039
            r7 = r3
            java.lang.String r8 = "email_auth_hash"
            java.lang.String r7 = r7.optString(r8)
            r8 = r2
            if (r8 != 0) goto L_0x0037
            java.lang.String r8 = ""
        L_0x0028:
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L_0x0039
            r7 = 1
        L_0x002f:
            r4 = r7
            r7 = r4
            if (r7 == 0) goto L_0x003b
            com.onesignal.OneSignal.fireEmailUpdateSuccess()
        L_0x0036:
            return
        L_0x0037:
            r8 = r2
            goto L_0x0028
        L_0x0039:
            r7 = 0
            goto L_0x002f
        L_0x003b:
            r7 = r3
            java.lang.String r8 = "identifier"
            r9 = 0
            java.lang.String r7 = r7.optString(r8, r9)
            r5 = r7
            r7 = r5
            if (r7 != 0) goto L_0x004c
            r7 = r0
            r7.setNewSession()
        L_0x004c:
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0094 }
            r12 = r7
            r7 = r12
            r8 = r12
            r8.<init>()     // Catch:{ JSONException -> 0x0094 }
            r6 = r7
            r7 = r6
            java.lang.String r8 = "identifier"
            r9 = r1
            org.json.JSONObject r7 = r7.put(r8, r9)     // Catch:{ JSONException -> 0x0094 }
            r7 = r2
            if (r7 == 0) goto L_0x006a
            r7 = r6
            java.lang.String r8 = "email_auth_hash"
            r9 = r2
            org.json.JSONObject r7 = r7.put(r8, r9)     // Catch:{ JSONException -> 0x0094 }
        L_0x006a:
            r7 = r2
            if (r7 != 0) goto L_0x0086
            r7 = r5
            if (r7 == 0) goto L_0x0086
            r7 = r5
            r8 = r1
            boolean r7 = r7.equals(r8)     // Catch:{ JSONException -> 0x0094 }
            if (r7 != 0) goto L_0x0086
            java.lang.String r7 = ""
            com.onesignal.OneSignal.saveEmailId(r7)     // Catch:{ JSONException -> 0x0094 }
            r7 = r0
            r7.resetCurrentState()     // Catch:{ JSONException -> 0x0094 }
            r7 = r0
            r7.setNewSession()     // Catch:{ JSONException -> 0x0094 }
        L_0x0086:
            r7 = r0
            r8 = r3
            r9 = r6
            r10 = r3
            r11 = 0
            org.json.JSONObject r7 = r7.generateJsonDiff(r8, r9, r10, r11)     // Catch:{ JSONException -> 0x0094 }
            r7 = r0
            r7.scheduleSyncToServer()     // Catch:{ JSONException -> 0x0094 }
        L_0x0093:
            goto L_0x0036
        L_0x0094:
            r7 = move-exception
            r6 = r7
            r7 = r6
            r7.printStackTrace()
            goto L_0x0093
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.UserStateEmailSynchronizer.setEmail(java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: protected */
    public String getId() {
        return OneSignal.getEmailId();
    }

    /* access modifiers changed from: package-private */
    public void updateIdDependents(String id) {
        OneSignal.updateEmailIdDependents(id);
    }

    /* access modifiers changed from: protected */
    public void addOnSessionOrCreateExtras(JSONObject jSONObject) {
        JSONObject jsonBody = jSONObject;
        try {
            JSONObject put = jsonBody.put("device_type", 11);
            JSONObject putOpt = jsonBody.putOpt("device_player_id", OneSignal.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void logoutEmail() {
        OneSignal.saveEmailId("");
        resetCurrentState();
        Object remove = getToSyncUserState().syncValues.remove("identifier");
        Object remove2 = this.toSyncUserState.syncValues.remove("email_auth_hash");
        Object remove3 = this.toSyncUserState.syncValues.remove("device_player_id");
        Object remove4 = this.toSyncUserState.syncValues.remove("external_user_id");
        this.toSyncUserState.persistState();
        OneSignal.getPermissionSubscriptionState().emailSubscriptionStatus.clearEmailAndId();
    }

    /* access modifiers changed from: protected */
    public void fireEventsForUpdateFailure(JSONObject jsonFields) {
        if (jsonFields.has("identifier")) {
            OneSignal.fireEmailUpdateFailure();
        }
    }

    /* access modifiers changed from: protected */
    public void onSuccessfulSync(JSONObject jsonFields) {
        if (jsonFields.has("identifier")) {
            OneSignal.fireEmailUpdateSuccess();
        }
    }
}
