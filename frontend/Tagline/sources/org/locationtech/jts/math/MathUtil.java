package org.locationtech.jts.math;

public class MathUtil {
    private static final double LOG_10 = Math.log(10.0d);

    public MathUtil() {
    }

    public static double clamp(double d, double d2, double d3) {
        double x = d;
        double min = d2;
        double max = d3;
        if (x < min) {
            return min;
        }
        if (x > max) {
            return max;
        }
        return x;
    }

    public static int clamp(int i, int i2, int i3) {
        int x = i;
        int min = i2;
        int max = i3;
        if (x < min) {
            return min;
        }
        if (x > max) {
            return max;
        }
        return x;
    }

    public static double log10(double x) {
        double ln = Math.log(x);
        if (Double.isInfinite(ln)) {
            return ln;
        }
        if (Double.isNaN(ln)) {
            return ln;
        }
        return ln / LOG_10;
    }

    public static int wrap(int i, int i2) {
        int index = i;
        int max = i2;
        if (index < 0) {
            return max - ((-index) % max);
        }
        return index % max;
    }

    public static double average(double x1, double x2) {
        return (x1 + x2) / 2.0d;
    }

    public static double max(double v1, double d, double d2) {
        double v2 = d;
        double v3 = d2;
        double max = v1;
        if (v2 > max) {
            max = v2;
        }
        if (v3 > max) {
            max = v3;
        }
        return max;
    }

    public static double max(double v1, double d, double d2, double d3) {
        double v2 = d;
        double v3 = d2;
        double v4 = d3;
        double max = v1;
        if (v2 > max) {
            max = v2;
        }
        if (v3 > max) {
            max = v3;
        }
        if (v4 > max) {
            max = v4;
        }
        return max;
    }

    public static double min(double v1, double d, double d2, double d3) {
        double v2 = d;
        double v3 = d2;
        double v4 = d3;
        double min = v1;
        if (v2 < min) {
            min = v2;
        }
        if (v3 < min) {
            min = v3;
        }
        if (v4 < min) {
            min = v4;
        }
        return min;
    }
}
