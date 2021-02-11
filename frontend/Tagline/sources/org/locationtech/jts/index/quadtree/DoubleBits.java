package org.locationtech.jts.index.quadtree;

public class DoubleBits {
    public static final int EXPONENT_BIAS = 1023;

    /* renamed from: x */
    private double f444x;
    private long xBits;

    public static double powerOf2(int i) {
        Throwable th;
        int exp = i;
        if (exp <= 1023 && exp >= -1022) {
            return Double.longBitsToDouble(((long) (exp + EXPONENT_BIAS)) << 52);
        }
        Throwable th2 = th;
        new IllegalArgumentException("Exponent out of bounds");
        throw th2;
    }

    public static int exponent(double d) {
        DoubleBits db;
        new DoubleBits(d);
        return db.getExponent();
    }

    public static double truncateToPowerOfTwo(double d) {
        DoubleBits doubleBits;
        new DoubleBits(d);
        DoubleBits db = doubleBits;
        db.zeroLowerBits(52);
        return db.getDouble();
    }

    public static String toBinaryString(double d) {
        DoubleBits db;
        new DoubleBits(d);
        return db.toString();
    }

    public static double maximumCommonMantissa(double d, double d2) {
        DoubleBits doubleBits;
        DoubleBits doubleBits2;
        double d1 = d;
        double d22 = d2;
        if (d1 == 0.0d || d22 == 0.0d) {
            return 0.0d;
        }
        new DoubleBits(d1);
        DoubleBits db1 = doubleBits;
        new DoubleBits(d22);
        DoubleBits db2 = doubleBits2;
        if (db1.getExponent() != db2.getExponent()) {
            return 0.0d;
        }
        db1.zeroLowerBits(64 - (12 + db1.numCommonMantissaBits(db2)));
        return db1.getDouble();
    }

    public DoubleBits(double d) {
        double x = d;
        this.f444x = x;
        this.xBits = Double.doubleToLongBits(x);
    }

    public double getDouble() {
        return Double.longBitsToDouble(this.xBits);
    }

    public int biasedExponent() {
        return ((int) (this.xBits >> 52)) & 2047;
    }

    public int getExponent() {
        return biasedExponent() - 1023;
    }

    public void zeroLowerBits(int nBits) {
        this.xBits &= ((1 << nBits) - 1) ^ -1;
    }

    public int getBit(int i) {
        return (this.xBits & (1 << i)) != 0 ? 1 : 0;
    }

    public int numCommonMantissaBits(DoubleBits doubleBits) {
        DoubleBits db = doubleBits;
        for (int i = 0; i < 52; i++) {
            int i2 = i + 12;
            if (getBit(i) != db.getBit(i)) {
                return i;
            }
        }
        return 52;
    }

    /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
        java.lang.NullPointerException
        	at jadx.core.dex.instructions.args.InsnArg.wrapInstruction(InsnArg.java:118)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.inline(CodeShrinkVisitor.java:146)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkBlock(CodeShrinkVisitor.java:71)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.shrinkMethod(CodeShrinkVisitor.java:43)
        	at jadx.core.dex.visitors.shrink.CodeShrinkVisitor.visit(CodeShrinkVisitor.java:35)
        */
    public java.lang.String toString() {
        /*
            r11 = this;
            r0 = r11
            r6 = r0
            long r6 = r6.xBits
            java.lang.String r6 = java.lang.Long.toBinaryString(r6)
            r1 = r6
            java.lang.String r6 = "0000000000000000000000000000000000000000000000000000000000000000"
            r2 = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r10 = r6
            r6 = r10
            r7 = r10
            r7.<init>()
            r7 = r2
            java.lang.StringBuilder r6 = r6.append(r7)
            r7 = r1
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r3 = r6
            r6 = r3
            r7 = r3
            int r7 = r7.length()
            r8 = 64
            int r7 = r7 + -64
            java.lang.String r6 = r6.substring(r7)
            r4 = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r10 = r6
            r6 = r10
            r7 = r10
            r7.<init>()
            r7 = r4
            r8 = 0
            r9 = 1
            java.lang.String r7 = r7.substring(r8, r9)
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = "  "
            java.lang.StringBuilder r6 = r6.append(r7)
            r7 = r4
            r8 = 1
            r9 = 12
            java.lang.String r7 = r7.substring(r8, r9)
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = "("
            java.lang.StringBuilder r6 = r6.append(r7)
            r7 = r0
            int r7 = r7.getExponent()
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = ") "
            java.lang.StringBuilder r6 = r6.append(r7)
            r7 = r4
            r8 = 12
            java.lang.String r7 = r7.substring(r8)
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = " [ "
            java.lang.StringBuilder r6 = r6.append(r7)
            r7 = r0
            double r7 = r7.f444x
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r7 = " ]"
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.String r6 = r6.toString()
            r5 = r6
            r6 = r5
            r0 = r6
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.index.quadtree.DoubleBits.toString():java.lang.String");
    }
}
