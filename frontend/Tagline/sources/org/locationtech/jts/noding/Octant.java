package org.locationtech.jts.noding;

import org.locationtech.jts.geom.Coordinate;

public class Octant {
    public static int octant(double d, double d2) {
        Throwable th;
        StringBuilder sb;
        double dx = d;
        double dy = d2;
        if (dx == 0.0d && dy == 0.0d) {
            Throwable th2 = th;
            new StringBuilder();
            new IllegalArgumentException(sb.append("Cannot compute the octant for point ( ").append(dx).append(", ").append(dy).append(" )").toString());
            throw th2;
        }
        double adx = Math.abs(dx);
        double ady = Math.abs(dy);
        if (dx >= 0.0d) {
            if (dy >= 0.0d) {
                if (adx >= ady) {
                    return 0;
                }
                return 1;
            } else if (adx >= ady) {
                return 7;
            } else {
                return 6;
            }
        } else if (dy >= 0.0d) {
            if (adx >= ady) {
                return 3;
            }
            return 2;
        } else if (adx >= ady) {
            return 4;
        } else {
            return 5;
        }
    }

    public static int octant(Coordinate coordinate, Coordinate coordinate2) {
        Throwable th;
        StringBuilder sb;
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        double dx = p1.f412x - p0.f412x;
        double dy = p1.f413y - p0.f413y;
        if (dx != 0.0d || dy != 0.0d) {
            return octant(dx, dy);
        }
        Throwable th2 = th;
        new StringBuilder();
        new IllegalArgumentException(sb.append("Cannot compute the octant for two identical points ").append(p0).toString());
        throw th2;
    }

    private Octant() {
    }
}
