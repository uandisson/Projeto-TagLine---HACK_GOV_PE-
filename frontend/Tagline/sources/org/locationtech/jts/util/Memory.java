package org.locationtech.jts.util;

public class Memory {

    /* renamed from: GB */
    public static final double f517GB = 1.073741824E9d;

    /* renamed from: KB */
    public static final double f518KB = 1024.0d;

    /* renamed from: MB */
    public static final double f519MB = 1048576.0d;

    public Memory() {
    }

    public static long used() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public static String usedString() {
        return format(used());
    }

    public static long free() {
        return Runtime.getRuntime().freeMemory();
    }

    public static String freeString() {
        return format(free());
    }

    public static long total() {
        return Runtime.getRuntime().totalMemory();
    }

    public static String totalString() {
        return format(total());
    }

    public static String usedTotalString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("Used: ").append(usedString()).append("   Total: ").append(totalString()).toString();
    }

    public static String allString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("Used: ").append(usedString()).append("   Free: ").append(freeString()).append("   Total: ").append(totalString()).toString();
    }

    public static String format(long j) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        long mem = j;
        if (((double) mem) < 2048.0d) {
            new StringBuilder();
            return sb4.append(mem).append(" bytes").toString();
        } else if (((double) mem) < 2097152.0d) {
            new StringBuilder();
            return sb3.append(round(((double) mem) / 1024.0d)).append(" KB").toString();
        } else if (((double) mem) < 2.147483648E9d) {
            new StringBuilder();
            return sb2.append(round(((double) mem) / 1048576.0d)).append(" MB").toString();
        } else {
            new StringBuilder();
            return sb.append(round(((double) mem) / 1.073741824E9d)).append(" GB").toString();
        }
    }

    public static double round(double d) {
        return Math.ceil(d * 100.0d) / 100.0d;
    }
}
