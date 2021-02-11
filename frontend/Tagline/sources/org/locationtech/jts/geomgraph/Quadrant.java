package org.locationtech.jts.geomgraph;

import org.locationtech.jts.geom.Coordinate;

public class Quadrant {

    /* renamed from: NE */
    public static final int f434NE = 0;

    /* renamed from: NW */
    public static final int f435NW = 1;

    /* renamed from: SE */
    public static final int f436SE = 3;

    /* renamed from: SW */
    public static final int f437SW = 2;

    public Quadrant() {
    }

    public static int quadrant(double d, double d2) {
        Throwable th;
        StringBuilder sb;
        double dx = d;
        double dy = d2;
        if (dx == 0.0d && dy == 0.0d) {
            Throwable th2 = th;
            new StringBuilder();
            new IllegalArgumentException(sb.append("Cannot compute the quadrant for point ( ").append(dx).append(", ").append(dy).append(" )").toString());
            throw th2;
        } else if (dx >= 0.0d) {
            if (dy >= 0.0d) {
                return 0;
            }
            return 3;
        } else if (dy >= 0.0d) {
            return 1;
        } else {
            return 2;
        }
    }

    public static int quadrant(Coordinate coordinate, Coordinate coordinate2) {
        Throwable th;
        StringBuilder sb;
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        if (p1.f412x == p0.f412x && p1.f413y == p0.f413y) {
            Throwable th2 = th;
            new StringBuilder();
            new IllegalArgumentException(sb.append("Cannot compute the quadrant for two identical points ").append(p0).toString());
            throw th2;
        } else if (p1.f412x >= p0.f412x) {
            if (p1.f413y >= p0.f413y) {
                return 0;
            }
            return 3;
        } else if (p1.f413y >= p0.f413y) {
            return 1;
        } else {
            return 2;
        }
    }

    public static boolean isOpposite(int i, int i2) {
        int quad1 = i;
        int quad2 = i2;
        if (quad1 == quad2) {
            return false;
        }
        if (((quad1 - quad2) + 4) % 4 == 2) {
            return true;
        }
        return false;
    }

    public static int commonHalfPlane(int i, int i2) {
        int quad1 = i;
        int quad2 = i2;
        if (quad1 == quad2) {
            return quad1;
        }
        if (((quad1 - quad2) + 4) % 4 == 2) {
            return -1;
        }
        int min = quad1 < quad2 ? quad1 : quad2;
        int max = quad1 > quad2 ? quad1 : quad2;
        if (min == 0 && max == 3) {
            return 3;
        }
        return min;
    }

    public static boolean isInHalfPlane(int i, int i2) {
        int quad = i;
        int halfPlane = i2;
        if (halfPlane == 3) {
            return quad == 3 || quad == 2;
        }
        return quad == halfPlane || quad == halfPlane + 1;
    }

    public static boolean isNorthern(int i) {
        int quad = i;
        return quad == 0 || quad == 1;
    }
}
