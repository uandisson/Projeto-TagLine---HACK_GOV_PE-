package com.onesignal;

import org.json.JSONException;
import org.json.JSONObject;

class UserStatePush extends UserState {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    UserStatePush(String inPersistKey, boolean load) {
        super(inPersistKey, load);
    }

    /* access modifiers changed from: package-private */
    public UserState newInstance(String persistKey) {
        UserStatePush userStatePush;
        new UserStatePush(persistKey, false);
        return userStatePush;
    }

    /* access modifiers changed from: protected */
    public void addDependFields() {
        try {
            JSONObject put = this.syncValues.put("notification_types", getNotificationTypes());
        } catch (JSONException e) {
            JSONException jSONException = e;
        }
    }

    private int getNotificationTypes() {
        int subscribableStatus = this.dependValues.optInt("subscribableStatus", 1);
        if (subscribableStatus < -2) {
            return subscribableStatus;
        }
        if (!this.dependValues.optBoolean("androidPermission", true)) {
            return 0;
        }
        if (!this.dependValues.optBoolean("userSubscribePref", true)) {
            return -2;
        }
        return 1;
    }

    /* access modifiers changed from: package-private */
    public boolean isSubscribed() {
        return getNotificationTypes() > 0;
    }
}
