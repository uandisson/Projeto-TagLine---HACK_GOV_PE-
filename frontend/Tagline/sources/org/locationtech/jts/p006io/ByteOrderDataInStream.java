package org.locationtech.jts.p006io;

import java.io.IOException;

/* renamed from: org.locationtech.jts.io.ByteOrderDataInStream */
public class ByteOrderDataInStream {
    private byte[] buf1;
    private byte[] buf4;
    private byte[] buf8;
    private int byteOrder;
    private InStream stream;

    public ByteOrderDataInStream() {
        this.byteOrder = 1;
        this.buf1 = new byte[1];
        this.buf4 = new byte[4];
        this.buf8 = new byte[8];
        this.stream = null;
    }

    public ByteOrderDataInStream(InStream stream2) {
        this.byteOrder = 1;
        this.buf1 = new byte[1];
        this.buf4 = new byte[4];
        this.buf8 = new byte[8];
        this.stream = stream2;
    }

    public void setInStream(InStream stream2) {
        InStream inStream = stream2;
        this.stream = inStream;
    }

    public void setOrder(int byteOrder2) {
        int i = byteOrder2;
        this.byteOrder = i;
    }

    public byte readByte() throws IOException {
        this.stream.read(this.buf1);
        return this.buf1[0];
    }

    public int readInt() throws IOException {
        this.stream.read(this.buf4);
        return ByteOrderValues.getInt(this.buf4, this.byteOrder);
    }

    public long readLong() throws IOException {
        this.stream.read(this.buf8);
        return ByteOrderValues.getLong(this.buf8, this.byteOrder);
    }

    public double readDouble() throws IOException {
        this.stream.read(this.buf8);
        return ByteOrderValues.getDouble(this.buf8, this.byteOrder);
    }
}
