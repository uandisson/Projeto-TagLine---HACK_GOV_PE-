package com.microsoft.appcenter.crashes.ingestion.models;

import com.microsoft.appcenter.ingestion.models.CommonProperties;
import com.microsoft.appcenter.ingestion.models.LogWithProperties;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class HandledErrorLog extends LogWithProperties {
    private static final String EXCEPTION = "exception";
    public static final String TYPE = "handledError";
    private Exception exception;

    /* renamed from: id */
    private UUID f292id;

    public HandledErrorLog() {
    }

    public String getType() {
        return TYPE;
    }

    public UUID getId() {
        return this.f292id;
    }

    public void setId(UUID id) {
        UUID uuid = id;
        this.f292id = uuid;
    }

    public Exception getException() {
        return this.exception;
    }

    public void setException(Exception exception2) {
        Exception exception3 = exception2;
        this.exception = exception3;
    }

    public void read(JSONObject jSONObject) throws JSONException {
        Exception exception2;
        JSONObject object = jSONObject;
        super.read(object);
        setId(UUID.fromString(object.getString(CommonProperties.f295ID)));
        if (object.has(EXCEPTION)) {
            JSONObject jException = object.getJSONObject(EXCEPTION);
            new Exception();
            Exception exception3 = exception2;
            exception3.read(jException);
            setException(exception3);
        }
    }

    public void write(JSONStringer jSONStringer) throws JSONException {
        JSONStringer writer = jSONStringer;
        super.write(writer);
        JSONStringer value = writer.key(CommonProperties.f295ID).value(getId());
        if (getException() != null) {
            JSONStringer object = writer.key(EXCEPTION).object();
            this.exception.write(writer);
            JSONStringer endObject = writer.endObject();
        }
    }

    public boolean equals(Object obj) {
        Object o = obj;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        HandledErrorLog errorLog = (HandledErrorLog) o;
        if (this.f292id == null ? errorLog.f292id != null : !this.f292id.equals(errorLog.f292id)) {
            return false;
        }
        return this.exception != null ? this.exception.equals(errorLog.exception) : errorLog.exception == null;
    }

    public int hashCode() {
        return (31 * ((31 * super.hashCode()) + (this.f292id != null ? this.f292id.hashCode() : 0))) + (this.exception != null ? this.exception.hashCode() : 0);
    }
}
