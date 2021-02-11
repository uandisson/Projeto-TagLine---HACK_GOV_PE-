package org.locationtech.jts.p006io;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: org.locationtech.jts.io.OutputStreamOutStream */
public class OutputStreamOutStream implements OutStream {

    /* renamed from: os */
    private OutputStream f447os;

    public OutputStreamOutStream(OutputStream os) {
        this.f447os = os;
    }

    public void write(byte[] buf, int len) throws IOException {
        this.f447os.write(buf, 0, len);
    }
}
