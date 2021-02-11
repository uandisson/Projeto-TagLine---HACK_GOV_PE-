package com.microsoft.appcenter.analytics.ingestion.models;

import com.microsoft.appcenter.ingestion.models.CommonProperties;
import com.microsoft.appcenter.ingestion.models.json.JSONUtils;
import com.microsoft.appcenter.ingestion.models.properties.TypedProperty;
import com.microsoft.appcenter.ingestion.models.properties.TypedPropertyUtils;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class EventLog extends LogWithNameAndProperties {
    public static final String TYPE = "event";

    /* renamed from: id */
    private UUID f289id;
    private List<TypedProperty> typedProperties;

    public EventLog() {
    }

    public String getType() {
        return "event";
    }

    public UUID getId() {
        return this.f289id;
    }

    public void setId(UUID id) {
        UUID uuid = id;
        this.f289id = uuid;
    }

    public List<TypedProperty> getTypedProperties() {
        return this.typedProperties;
    }

    public void setTypedProperties(List<TypedProperty> typedProperties2) {
        List<TypedProperty> list = typedProperties2;
        this.typedProperties = list;
    }

    public void read(JSONObject jSONObject) throws JSONException {
        JSONObject object = jSONObject;
        super.read(object);
        setId(UUID.fromString(object.getString(CommonProperties.f295ID)));
        setTypedProperties(TypedPropertyUtils.read(object));
    }

    public void write(JSONStringer jSONStringer) throws JSONException {
        JSONStringer writer = jSONStringer;
        super.write(writer);
        JSONStringer value = writer.key(CommonProperties.f295ID).value(getId());
        JSONUtils.writeArray(writer, CommonProperties.TYPED_PROPERTIES, getTypedProperties());
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
        EventLog eventLog = (EventLog) o;
        if (this.f289id == null ? eventLog.f289id != null : !this.f289id.equals(eventLog.f289id)) {
            return false;
        }
        return this.typedProperties != null ? this.typedProperties.equals(eventLog.typedProperties) : eventLog.typedProperties == null;
    }

    public int hashCode() {
        return (31 * ((31 * super.hashCode()) + (this.f289id != null ? this.f289id.hashCode() : 0))) + (this.typedProperties != null ? this.typedProperties.hashCode() : 0);
    }
}
