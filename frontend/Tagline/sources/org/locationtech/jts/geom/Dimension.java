package org.locationtech.jts.geom;

public class Dimension {

    /* renamed from: A */
    public static final int f419A = 2;
    public static final int DONTCARE = -3;
    public static final int FALSE = -1;

    /* renamed from: L */
    public static final int f420L = 1;

    /* renamed from: P */
    public static final int f421P = 0;
    public static final char SYM_A = '2';
    public static final char SYM_DONTCARE = '*';
    public static final char SYM_FALSE = 'F';
    public static final char SYM_L = '1';
    public static final char SYM_P = '0';
    public static final char SYM_TRUE = 'T';
    public static final int TRUE = -2;

    public Dimension() {
    }

    public static char toDimensionSymbol(int i) {
        Throwable th;
        StringBuilder sb;
        int dimensionValue = i;
        switch (dimensionValue) {
            case -3:
                return SYM_DONTCARE;
            case -2:
                return SYM_TRUE;
            case -1:
                return 'F';
            case 0:
                return SYM_P;
            case 1:
                return SYM_L;
            case 2:
                return SYM_A;
            default:
                Throwable th2 = th;
                new StringBuilder();
                new IllegalArgumentException(sb.append("Unknown dimension value: ").append(dimensionValue).toString());
                throw th2;
        }
    }

    public static int toDimensionValue(char c) {
        Throwable th;
        StringBuilder sb;
        char dimensionSymbol = c;
        switch (Character.toUpperCase(dimensionSymbol)) {
            case '*':
                return -3;
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case 'F':
                return -1;
            case 'T':
                return -2;
            default:
                Throwable th2 = th;
                new StringBuilder();
                new IllegalArgumentException(sb.append("Unknown dimension symbol: ").append(dimensionSymbol).toString());
                throw th2;
        }
    }
}
