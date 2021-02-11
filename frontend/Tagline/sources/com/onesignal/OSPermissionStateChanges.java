package com.onesignal;

import org.json.JSONObject;

public class OSPermissionStateChanges {
    OSPermissionState from;

    /* renamed from: to */
    OSPermissionState f308to;

    public OSPermissionStateChanges() {
    }

    public OSPermissionState getTo() {
        return this.f308to;
    }

    public OSPermissionState getFrom() {
        return this.from;
    }

    public JSONObject toJSONObject() {
        JSONObject jSONObject;
        new JSONObject();
        JSONObject mainObj = jSONObject;
        try {
            JSONObject put = mainObj.put("from", this.from.toJSONObject());
            JSONObject put2 = mainObj.put("to", this.f308to.toJSONObject());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return mainObj;
    }

    public String toString() {
        return toJSONObject().toString();
    }
}
