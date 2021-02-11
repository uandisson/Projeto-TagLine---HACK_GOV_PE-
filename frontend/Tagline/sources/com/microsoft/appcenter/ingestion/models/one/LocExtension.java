package com.microsoft.appcenter.ingestion.models.one;

import com.microsoft.appcenter.ingestion.models.Model;
import com.microsoft.appcenter.ingestion.models.json.JSONUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class LocExtension implements Model {

    /* renamed from: TZ */
    private static final String f302TZ = "tz";

    /* renamed from: tz */
    private String f303tz;

    public LocExtension() {
    }

    public String getTz() {
        return this.f303tz;
    }

    public void setTz(String tz) {
        String str = tz;
        this.f303tz = str;
    }

    public void read(JSONObject object) {
        setTz(object.optString(f302TZ, (String) null));
    }

    public void write(JSONStringer writer) throws JSONException {
        JSONUtils.write(writer, f302TZ, getTz());
    }

    public boolean equals(Object obj) {
        Object o = obj;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocExtension that = (LocExtension) o;
        return this.f303tz != null ? this.f303tz.equals(that.f303tz) : that.f303tz == null;
    }

    public int hashCode() {
        return this.f303tz != null ? this.f303tz.hashCode() : 0;
    }
}
