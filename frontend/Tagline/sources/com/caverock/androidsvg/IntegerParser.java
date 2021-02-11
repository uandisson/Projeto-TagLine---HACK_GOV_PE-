package com.caverock.androidsvg;

public class IntegerParser {
    boolean isNegative;
    int pos;
    long value;

    public IntegerParser(boolean isNegative2, long value2, int pos2) {
        this.isNegative = isNegative2;
        this.value = value2;
        this.pos = pos2;
    }

    public int getEndPos() {
        return this.pos;
    }

    public static IntegerParser parseInt(String str) {
        String str2 = str;
        return parseInt(str2, 0, str2.length());
    }

    public static IntegerParser parseInt(String str, int startpos, int i) {
        IntegerParser integerParser;
        char ch;
        String input = str;
        int len = i;
        int pos2 = startpos;
        boolean isNegative2 = false;
        long value2 = 0;
        if (pos2 >= len) {
            return null;
        }
        switch (input.charAt(pos2)) {
            case '+':
                break;
            case '-':
                isNegative2 = true;
                break;
        }
        pos2++;
        int sigStart = pos2;
        while (pos2 < len && (ch = input.charAt(pos2)) >= '0' && ch <= '9') {
            if (isNegative2) {
                value2 = (value2 * 10) - ((long) (ch - '0'));
                if (value2 < -2147483648L) {
                    return null;
                }
            } else {
                value2 = (value2 * 10) + ((long) (ch - '0'));
                if (value2 > 2147483647L) {
                    return null;
                }
            }
            pos2++;
        }
        if (pos2 == sigStart) {
            return null;
        }
        new IntegerParser(isNegative2, value2, pos2);
        return integerParser;
    }

    public int value() {
        return (int) this.value;
    }

    public static IntegerParser parseHex(String str) {
        String str2 = str;
        return parseHex(str2, 0, str2.length());
    }

    public static IntegerParser parseHex(String str, int i, int i2) {
        IntegerParser integerParser;
        String input = str;
        int startpos = i;
        int len = i2;
        int pos2 = startpos;
        long value2 = 0;
        if (pos2 >= len) {
            return null;
        }
        while (pos2 < len) {
            char ch = input.charAt(pos2);
            if (ch >= '0' && ch <= '9') {
                value2 = (value2 * 16) + ((long) (ch - '0'));
            } else if (ch < 'A' || ch > 'F') {
                if (ch < 'a' || ch > 'f') {
                    break;
                }
                value2 = (value2 * 16) + ((long) (ch - 'a')) + 10;
            } else {
                value2 = (value2 * 16) + ((long) (ch - 'A')) + 10;
            }
            if (value2 > 4294967295L) {
                return null;
            }
            pos2++;
        }
        if (pos2 == startpos) {
            return null;
        }
        new IntegerParser(false, value2, pos2);
        return integerParser;
    }
}
