package com.microsoft.appcenter.crashes.ingestion.models;

import com.microsoft.appcenter.crashes.ingestion.models.json.StackFrameFactory;
import com.microsoft.appcenter.ingestion.models.CommonProperties;
import com.microsoft.appcenter.ingestion.models.Model;
import com.microsoft.appcenter.ingestion.models.json.JSONUtils;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class Thread implements Model {
    private List<StackFrame> frames;

    /* renamed from: id */
    private long f293id;
    private String name;

    public Thread() {
    }

    public long getId() {
        return this.f293id;
    }

    public void setId(long id) {
        long j = id;
        this.f293id = j;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        String str = name2;
        this.name = str;
    }

    public List<StackFrame> getFrames() {
        return this.frames;
    }

    public void setFrames(List<StackFrame> frames2) {
        List<StackFrame> list = frames2;
        this.frames = list;
    }

    public void read(JSONObject jSONObject) throws JSONException {
        JSONObject object = jSONObject;
        setId(object.getLong(CommonProperties.f295ID));
        setName(object.optString("name", (String) null));
        setFrames(JSONUtils.readArray(object, CommonProperties.FRAMES, StackFrameFactory.getInstance()));
    }

    public void write(JSONStringer jSONStringer) throws JSONException {
        JSONStringer writer = jSONStringer;
        JSONUtils.write(writer, CommonProperties.f295ID, Long.valueOf(getId()));
        JSONUtils.write(writer, "name", getName());
        JSONUtils.writeArray(writer, CommonProperties.FRAMES, getFrames());
    }

    public boolean equals(Object obj) {
        Object o = obj;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Thread that = (Thread) o;
        if (this.f293id != that.f293id) {
            return false;
        }
        if (this.name == null ? that.name != null : !this.name.equals(that.name)) {
            return false;
        }
        return this.frames != null ? this.frames.equals(that.frames) : that.frames == null;
    }

    public int hashCode() {
        return (31 * ((31 * ((int) (this.f293id ^ (this.f293id >>> 32)))) + (this.name != null ? this.name.hashCode() : 0))) + (this.frames != null ? this.frames.hashCode() : 0);
    }
}
