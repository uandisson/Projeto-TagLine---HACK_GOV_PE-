package com.caverock.androidsvg;

public class NumberParser {
    static long TOO_BIG = 922337203685477580L;
    private static final float[] negativePowersOf10 = {1.0f, 0.1f, 0.01f, 0.001f, 1.0E-4f, 1.0E-5f, 1.0E-6f, 1.0E-7f, 1.0E-8f, 1.0E-9f, 1.0E-10f, 1.0E-11f, 1.0E-12f, 1.0E-13f, 1.0E-14f, 1.0E-15f, 1.0E-16f, 1.0E-17f, 1.0E-18f, 1.0E-19f, 1.0E-20f, 1.0E-21f, 1.0E-22f, 1.0E-23f, 1.0E-24f, 1.0E-25f, 1.0E-26f, 1.0E-27f, 1.0E-28f, 1.0E-29f, 1.0E-30f, 1.0E-31f, 1.0E-32f, 1.0E-33f, 1.0E-34f, 1.0E-35f, 1.0E-36f, 1.0E-37f, 1.0E-38f};
    private static final float[] positivePowersOf10 = {1.0f, 10.0f, 100.0f, 1000.0f, 10000.0f, 100000.0f, 1000000.0f, 1.0E7f, 1.0E8f, 1.0E9f, 1.0E10f, 9.9999998E10f, 1.0E12f, 9.9999998E12f, 1.0E14f, 9.9999999E14f, 1.00000003E16f, 9.9999998E16f, 9.9999998E17f, 1.0E19f, 1.0E20f, 1.0E21f, 1.0E22f, 1.0E23f, 1.0E24f, 1.0E25f, 1.0E26f, 1.0E27f, 1.0E28f, 1.0E29f, 1.0E30f, 1.0E31f, 1.0E32f, 1.0E33f, 1.0E34f, 1.0E35f, 1.0E36f, 1.0E37f, 1.0E38f};
    int pos;

    public NumberParser() {
    }

    public int getEndPos() {
        return this.pos;
    }

    public float parseNumber(String str) {
        String str2 = str;
        return parseNumber(str2, 0, str2.length());
    }

    public float parseNumber(String str, int startpos, int i) {
        int exponent;
        float f;
        String input = str;
        int len = i;
        boolean isNegative = false;
        long significand = 0;
        int numDigits = 0;
        int numLeadingZeroes = 0;
        int numTrailingZeroes = 0;
        boolean decimalSeen = false;
        int decimalPos = 0;
        this.pos = startpos;
        if (this.pos >= len) {
            return Float.NaN;
        }
        switch (input.charAt(this.pos)) {
            case '+':
                break;
            case '-':
                isNegative = true;
                break;
        }
        this.pos++;
        int sigStart = this.pos;
        while (true) {
            if (this.pos < len) {
                char ch = input.charAt(this.pos);
                if (ch == '0') {
                    if (numDigits == 0) {
                        numLeadingZeroes++;
                    } else {
                        numTrailingZeroes++;
                    }
                } else if (ch >= '1' && ch <= '9') {
                    int numDigits2 = numDigits + numTrailingZeroes;
                    while (numTrailingZeroes > 0) {
                        if (significand > TOO_BIG) {
                            return Float.NaN;
                        }
                        significand *= 10;
                        numTrailingZeroes--;
                    }
                    if (significand > TOO_BIG) {
                        return Float.NaN;
                    }
                    significand = (significand * 10) + ((long) (ch - '0'));
                    numDigits = numDigits2 + 1;
                    if (significand < 0) {
                        return Float.NaN;
                    }
                } else if (ch == '.' && !decimalSeen) {
                    decimalPos = this.pos - sigStart;
                    decimalSeen = true;
                }
                this.pos++;
            }
        }
        if (decimalSeen) {
            if (this.pos == decimalPos + 1) {
                return Float.NaN;
            }
        }
        if (numDigits == 0) {
            if (numLeadingZeroes == 0) {
                return Float.NaN;
            }
            numDigits = 1;
        }
        if (decimalSeen) {
            exponent = (decimalPos - numLeadingZeroes) - numDigits;
        } else {
            exponent = numTrailingZeroes;
        }
        if (this.pos < len) {
            char ch2 = input.charAt(this.pos);
            if (ch2 == 'E' || ch2 == 'e') {
                boolean expIsNegative = false;
                int expVal = 0;
                boolean abortExponent = false;
                this.pos++;
                if (this.pos == len) {
                    return Float.NaN;
                }
                switch (input.charAt(this.pos)) {
                    case '+':
                        break;
                    case '-':
                        expIsNegative = true;
                        break;
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        break;
                    default:
                        abortExponent = true;
                        this.pos--;
                        break;
                }
                this.pos++;
                if (!abortExponent) {
                    int expStart = this.pos;
                    while (true) {
                        if (this.pos < len) {
                            char ch3 = input.charAt(this.pos);
                            if (ch3 >= '0' && ch3 <= '9') {
                                if (((long) expVal) > TOO_BIG) {
                                    return Float.NaN;
                                }
                                expVal = (expVal * 10) + (ch3 - '0');
                                this.pos++;
                            }
                        }
                    }
                    if (this.pos == expStart) {
                        return Float.NaN;
                    }
                    if (expIsNegative) {
                        exponent -= expVal;
                    } else {
                        exponent += expVal;
                    }
                }
            }
        }
        if (exponent + numDigits > 39 || exponent + numDigits < -44) {
            return Float.NaN;
        }
        float f2 = (float) significand;
        if (significand != 0) {
            if (exponent > 0) {
                f2 *= positivePowersOf10[exponent];
            } else if (exponent < 0) {
                if (exponent < -38) {
                    f2 = (float) (((double) f2) * 1.0E-20d);
                    exponent += 20;
                }
                f2 *= negativePowersOf10[-exponent];
            }
        }
        if (isNegative) {
            f = -f2;
        } else {
            f = f2;
        }
        return f;
    }
}
