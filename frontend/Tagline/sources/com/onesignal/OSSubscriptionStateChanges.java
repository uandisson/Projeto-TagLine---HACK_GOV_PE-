package com.onesignal;

import org.json.JSONObject;

public class OSSubscriptionStateChanges {
    OSSubscriptionState from;

    /* renamed from: to */
    OSSubscriptionState f309to;

    public OSSubscriptionStateChanges() {
    }

    public OSSubscriptionState getTo() {
        return this.f309to;
    }

    public OSSubscriptionState getFrom() {
        return this.from;
    }

    public JSONObject toJSONObject() {
        JSONObject jSONObject;
        new JSONObject();
        JSONObject mainObj = jSONObject;
        try {
            JSONObject put = mainObj.put("from", this.from.toJSONObject());
            JSONObject put2 = mainObj.put("to", this.f309to.toJSONObject());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return mainObj;
    }

    public String toString() {
        return toJSONObject().toString();
    }
}
