package org.locationtech.jts.p006io;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: org.locationtech.jts.io.InputStreamInStream */
public class InputStreamInStream implements InStream {

    /* renamed from: is */
    private InputStream f446is;

    public InputStreamInStream(InputStream is) {
        this.f446is = is;
    }

    public void read(byte[] buf) throws IOException {
        int read = this.f446is.read(buf);
    }
}
