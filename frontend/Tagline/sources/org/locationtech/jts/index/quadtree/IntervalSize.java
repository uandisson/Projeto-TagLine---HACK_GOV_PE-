package org.locationtech.jts.index.quadtree;

public class IntervalSize {
    public static final int MIN_BINARY_EXPONENT = -50;

    public IntervalSize() {
    }

    public static boolean isZeroWidth(double d, double d2) {
        double min = d;
        double max = d2;
        double width = max - min;
        if (width == 0.0d) {
            return true;
        }
        return DoubleBits.exponent(width / Math.max(Math.abs(min), Math.abs(max))) <= -50;
    }
}
