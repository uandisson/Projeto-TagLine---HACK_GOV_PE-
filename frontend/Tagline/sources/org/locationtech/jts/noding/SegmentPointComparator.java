package org.locationtech.jts.noding;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.util.Assert;

public class SegmentPointComparator {
    public SegmentPointComparator() {
    }

    public static int compare(int i, Coordinate coordinate, Coordinate coordinate2) {
        int octant = i;
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        if (p0.equals2D(p1)) {
            return 0;
        }
        int xSign = relativeSign(p0.f412x, p1.f412x);
        int ySign = relativeSign(p0.f413y, p1.f413y);
        switch (octant) {
            case 0:
                return compareValue(xSign, ySign);
            case 1:
                return compareValue(ySign, xSign);
            case 2:
                return compareValue(ySign, -xSign);
            case 3:
                return compareValue(-xSign, ySign);
            case 4:
                return compareValue(-xSign, -ySign);
            case 5:
                return compareValue(-ySign, -xSign);
            case 6:
                return compareValue(-ySign, xSign);
            case 7:
                return compareValue(xSign, -ySign);
            default:
                Assert.shouldNeverReachHere("invalid octant value");
                return 0;
        }
    }

    public static int relativeSign(double d, double d2) {
        double x0 = d;
        double x1 = d2;
        if (x0 < x1) {
            return -1;
        }
        if (x0 > x1) {
            return 1;
        }
        return 0;
    }

    private static int compareValue(int i, int i2) {
        int compareSign0 = i;
        int compareSign1 = i2;
        if (compareSign0 < 0) {
            return -1;
        }
        if (compareSign0 > 0) {
            return 1;
        }
        if (compareSign1 < 0) {
            return -1;
        }
        if (compareSign1 > 0) {
            return 1;
        }
        return 0;
    }
}
