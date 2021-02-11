package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.math.Vector3D;

public class CGAlgorithms3D {
    private CGAlgorithms3D() {
    }

    public static double distance(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        if (Double.isNaN(p0.f414z) || Double.isNaN(p1.f414z)) {
            return p0.distance(p1);
        }
        double dx = p0.f412x - p1.f412x;
        double dy = p0.f413y - p1.f413y;
        double dz = p0.f414z - p1.f414z;
        return Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
    }

    public static double distancePointSegment(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Throwable th;
        Coordinate p = coordinate;
        Coordinate A = coordinate2;
        Coordinate B = coordinate3;
        if (A.equals3D(B)) {
            return distance(p, A);
        }
        double len2 = ((B.f412x - A.f412x) * (B.f412x - A.f412x)) + ((B.f413y - A.f413y) * (B.f413y - A.f413y)) + ((B.f414z - A.f414z) * (B.f414z - A.f414z));
        if (Double.isNaN(len2)) {
            Throwable th2 = th;
            new IllegalArgumentException("Ordinates must not be NaN");
            throw th2;
        }
        double r = ((((p.f412x - A.f412x) * (B.f412x - A.f412x)) + ((p.f413y - A.f413y) * (B.f413y - A.f413y))) + ((p.f414z - A.f414z) * (B.f414z - A.f414z))) / len2;
        if (r <= 0.0d) {
            return distance(p, A);
        }
        if (r >= 1.0d) {
            return distance(p, B);
        }
        double qx = A.f412x + (r * (B.f412x - A.f412x));
        double qy = A.f413y + (r * (B.f413y - A.f413y));
        double qz = A.f414z + (r * (B.f414z - A.f414z));
        double dx = p.f412x - qx;
        double dy = p.f413y - qy;
        double dz = p.f414z - qz;
        return Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
    }

    public static double distanceSegmentSegment(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        double s;
        double t;
        Coordinate coordinate5;
        Coordinate coordinate6;
        Throwable th;
        Coordinate A = coordinate;
        Coordinate B = coordinate2;
        Coordinate C = coordinate3;
        Coordinate D = coordinate4;
        if (A.equals3D(B)) {
            return distancePointSegment(A, C, D);
        }
        if (C.equals3D(B)) {
            return distancePointSegment(C, A, B);
        }
        double a = Vector3D.dot(A, B, A, B);
        double b = Vector3D.dot(A, B, C, D);
        double c = Vector3D.dot(C, D, C, D);
        double d = Vector3D.dot(A, B, C, A);
        double e = Vector3D.dot(C, D, C, A);
        double denom = (a * c) - (b * b);
        if (Double.isNaN(denom)) {
            Throwable th2 = th;
            new IllegalArgumentException("Ordinates must not be NaN");
            throw th2;
        }
        if (denom <= 0.0d) {
            s = 0.0d;
            if (b > c) {
                t = d / b;
            } else {
                t = e / c;
            }
        } else {
            s = ((b * e) - (c * d)) / denom;
            t = ((a * e) - (b * d)) / denom;
        }
        if (s < 0.0d) {
            return distancePointSegment(A, C, D);
        }
        if (s > 1.0d) {
            return distancePointSegment(B, C, D);
        }
        if (t < 0.0d) {
            return distancePointSegment(C, A, B);
        }
        if (t > 1.0d) {
            return distancePointSegment(D, A, B);
        }
        double x1 = A.f412x + (s * (B.f412x - A.f412x));
        double y1 = A.f413y + (s * (B.f413y - A.f413y));
        double z1 = A.f414z + (s * (B.f414z - A.f414z));
        double x2 = C.f412x + (t * (D.f412x - C.f412x));
        double y2 = C.f413y + (t * (D.f413y - C.f413y));
        double z2 = C.f414z + (t * (D.f414z - C.f414z));
        new Coordinate(x1, y1, z1);
        new Coordinate(x2, y2, z2);
        return distance(coordinate5, coordinate6);
    }
}
