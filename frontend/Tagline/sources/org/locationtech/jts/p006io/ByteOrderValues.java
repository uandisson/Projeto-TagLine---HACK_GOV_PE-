package org.locationtech.jts.p006io;

import com.google.appinventor.components.runtime.util.Ev3Constants;

/* renamed from: org.locationtech.jts.io.ByteOrderValues */
public class ByteOrderValues {
    public static final int BIG_ENDIAN = 1;
    public static final int LITTLE_ENDIAN = 2;

    public ByteOrderValues() {
    }

    public static int getInt(byte[] bArr, int byteOrder) {
        byte[] buf = bArr;
        if (byteOrder == 1) {
            return ((buf[0] & Ev3Constants.Opcode.TST) << 24) | ((buf[1] & Ev3Constants.Opcode.TST) << 16) | ((buf[2] & Ev3Constants.Opcode.TST) << 8) | (buf[3] & Ev3Constants.Opcode.TST);
        }
        return ((buf[3] & Ev3Constants.Opcode.TST) << 24) | ((buf[2] & Ev3Constants.Opcode.TST) << 16) | ((buf[1] & Ev3Constants.Opcode.TST) << 8) | (buf[0] & Ev3Constants.Opcode.TST);
    }

    public static void putInt(int i, byte[] bArr, int byteOrder) {
        int intValue = i;
        byte[] buf = bArr;
        if (byteOrder == 1) {
            buf[0] = (byte) (intValue >> 24);
            buf[1] = (byte) (intValue >> 16);
            buf[2] = (byte) (intValue >> 8);
            buf[3] = (byte) intValue;
            return;
        }
        buf[0] = (byte) intValue;
        buf[1] = (byte) (intValue >> 8);
        buf[2] = (byte) (intValue >> 16);
        buf[3] = (byte) (intValue >> 24);
    }

    public static long getLong(byte[] bArr, int byteOrder) {
        byte[] buf = bArr;
        return byteOrder == 1 ? (((long) (buf[0] & Ev3Constants.Opcode.TST)) << 56) | (((long) (buf[1] & Ev3Constants.Opcode.TST)) << 48) | (((long) (buf[2] & Ev3Constants.Opcode.TST)) << 40) | (((long) (buf[3] & Ev3Constants.Opcode.TST)) << 32) | (((long) (buf[4] & Ev3Constants.Opcode.TST)) << 24) | (((long) (buf[5] & Ev3Constants.Opcode.TST)) << 16) | (((long) (buf[6] & Ev3Constants.Opcode.TST)) << 8) | ((long) (buf[7] & Ev3Constants.Opcode.TST)) : (((long) (buf[7] & Ev3Constants.Opcode.TST)) << 56) | (((long) (buf[6] & Ev3Constants.Opcode.TST)) << 48) | (((long) (buf[5] & Ev3Constants.Opcode.TST)) << 40) | (((long) (buf[4] & Ev3Constants.Opcode.TST)) << 32) | (((long) (buf[3] & Ev3Constants.Opcode.TST)) << 24) | (((long) (buf[2] & Ev3Constants.Opcode.TST)) << 16) | (((long) (buf[1] & Ev3Constants.Opcode.TST)) << 8) | ((long) (buf[0] & Ev3Constants.Opcode.TST));
    }

    public static void putLong(long j, byte[] bArr, int byteOrder) {
        long longValue = j;
        byte[] buf = bArr;
        if (byteOrder == 1) {
            buf[0] = (byte) ((int) (longValue >> 56));
            buf[1] = (byte) ((int) (longValue >> 48));
            buf[2] = (byte) ((int) (longValue >> 40));
            buf[3] = (byte) ((int) (longValue >> 32));
            buf[4] = (byte) ((int) (longValue >> 24));
            buf[5] = (byte) ((int) (longValue >> 16));
            buf[6] = (byte) ((int) (longValue >> 8));
            buf[7] = (byte) ((int) longValue);
            return;
        }
        buf[0] = (byte) ((int) longValue);
        buf[1] = (byte) ((int) (longValue >> 8));
        buf[2] = (byte) ((int) (longValue >> 16));
        buf[3] = (byte) ((int) (longValue >> 24));
        buf[4] = (byte) ((int) (longValue >> 32));
        buf[5] = (byte) ((int) (longValue >> 40));
        buf[6] = (byte) ((int) (longValue >> 48));
        buf[7] = (byte) ((int) (longValue >> 56));
    }

    public static double getDouble(byte[] buf, int byteOrder) {
        return Double.longBitsToDouble(getLong(buf, byteOrder));
    }

    public static void putDouble(double doubleValue, byte[] buf, int byteOrder) {
        putLong(Double.doubleToLongBits(doubleValue), buf, byteOrder);
    }
}
