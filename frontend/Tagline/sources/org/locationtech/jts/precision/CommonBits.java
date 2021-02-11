package org.locationtech.jts.precision;

public class CommonBits {
    private long commonBits = 0;
    private int commonMantissaBitsCount = 53;
    private long commonSignExp;
    private boolean isFirst = true;

    public static long signExpBits(long num) {
        return num >> 52;
    }

    public static int numCommonMostSigMantissaBits(long j, long j2) {
        long num1 = j;
        long num2 = j2;
        int count = 0;
        for (int i = 52; i >= 0; i--) {
            if (getBit(num1, i) != getBit(num2, i)) {
                return count;
            }
            count++;
        }
        return 52;
    }

    public static long zeroLowerBits(long bits, int nBits) {
        return bits & (((1 << nBits) - 1) ^ -1);
    }

    public static int getBit(long bits, int i) {
        return (bits & (1 << i)) != 0 ? 1 : 0;
    }

    public CommonBits() {
    }

    public void add(double num) {
        long numBits = Double.doubleToLongBits(num);
        if (this.isFirst) {
            this.commonBits = numBits;
            this.commonSignExp = signExpBits(this.commonBits);
            this.isFirst = false;
        } else if (signExpBits(numBits) != this.commonSignExp) {
            this.commonBits = 0;
        } else {
            this.commonMantissaBitsCount = numCommonMostSigMantissaBits(this.commonBits, numBits);
            this.commonBits = zeroLowerBits(this.commonBits, 64 - (12 + this.commonMantissaBitsCount));
        }
    }

    public double getCommon() {
        return Double.longBitsToDouble(this.commonBits);
    }

    public String toString(long j) {
        StringBuilder sb;
        StringBuilder sb2;
        long bits = j;
        double x = Double.longBitsToDouble(bits);
        String numStr = Long.toBinaryString(bits);
        new StringBuilder();
        String padStr = sb.append("0000000000000000000000000000000000000000000000000000000000000000").append(numStr).toString();
        String bitStr = padStr.substring(padStr.length() - 64);
        new StringBuilder();
        return sb2.append(bitStr.substring(0, 1)).append("  ").append(bitStr.substring(1, 12)).append("(exp) ").append(bitStr.substring(12)).append(" [ ").append(x).append(" ]").toString();
    }
}
