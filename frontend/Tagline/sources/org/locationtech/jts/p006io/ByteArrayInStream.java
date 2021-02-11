package org.locationtech.jts.p006io;

/* renamed from: org.locationtech.jts.io.ByteArrayInStream */
public class ByteArrayInStream implements InStream {
    private byte[] buffer;
    private int position;

    public ByteArrayInStream(byte[] buffer2) {
        setBytes(buffer2);
    }

    public void setBytes(byte[] buffer2) {
        this.buffer = buffer2;
        this.position = 0;
    }

    public void read(byte[] bArr) {
        byte[] buf = bArr;
        int numToRead = buf.length;
        if (this.position + numToRead > this.buffer.length) {
            numToRead = this.buffer.length - this.position;
            System.arraycopy(this.buffer, this.position, buf, 0, numToRead);
            for (int i = numToRead; i < buf.length; i++) {
                buf[i] = 0;
            }
        } else {
            System.arraycopy(this.buffer, this.position, buf, 0, numToRead);
        }
        this.position += numToRead;
    }
}
