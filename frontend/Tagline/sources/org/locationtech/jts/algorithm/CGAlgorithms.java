package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.math.MathUtil;

public class CGAlgorithms {
    public static final int CLOCKWISE = -1;
    public static final int COLLINEAR = 0;
    public static final int COUNTERCLOCKWISE = 1;
    public static final int LEFT = 1;
    public static final int RIGHT = -1;
    public static final int STRAIGHT = 0;

    public static int orientationIndex(Coordinate p1, Coordinate p2, Coordinate q) {
        return CGAlgorithmsDD.orientationIndex(p1, p2, q);
    }

    public CGAlgorithms() {
    }

    public static boolean isPointInRing(Coordinate p, Coordinate[] ring) {
        return locatePointInRing(p, ring) != 2;
    }

    public static int locatePointInRing(Coordinate p, Coordinate[] ring) {
        return RayCrossingCounter.locatePointInRing(p, ring);
    }

    public static boolean isOnLine(Coordinate coordinate, Coordinate[] coordinateArr) {
        LineIntersector lineIntersector;
        Coordinate p = coordinate;
        Coordinate[] pt = coordinateArr;
        new RobustLineIntersector();
        LineIntersector lineIntersector2 = lineIntersector;
        for (int i = 1; i < pt.length; i++) {
            lineIntersector2.computeIntersection(p, pt[i - 1], pt[i]);
            if (lineIntersector2.hasIntersection()) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0044  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isCCW(org.locationtech.jts.geom.Coordinate[] r15) {
        /*
            r0 = r15
            r10 = r0
            int r10 = r10.length
            r11 = 1
            int r10 = r10 + -1
            r1 = r10
            r10 = r1
            r11 = 3
            if (r10 >= r11) goto L_0x0017
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            r14 = r10
            r10 = r14
            r11 = r14
            java.lang.String r12 = "Ring has fewer than 4 points, so orientation cannot be determined"
            r11.<init>(r12)
            throw r10
        L_0x0017:
            r10 = r0
            r11 = 0
            r10 = r10[r11]
            r2 = r10
            r10 = 0
            r3 = r10
            r10 = 1
            r4 = r10
        L_0x0020:
            r10 = r4
            r11 = r1
            if (r10 > r11) goto L_0x003a
            r10 = r0
            r11 = r4
            r10 = r10[r11]
            r5 = r10
            r10 = r5
            double r10 = r10.f413y
            r12 = r2
            double r12 = r12.f413y
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 <= 0) goto L_0x0037
            r10 = r5
            r2 = r10
            r10 = r4
            r3 = r10
        L_0x0037:
            int r4 = r4 + 1
            goto L_0x0020
        L_0x003a:
            r10 = r3
            r4 = r10
        L_0x003c:
            r10 = r4
            r11 = 1
            int r10 = r10 + -1
            r4 = r10
            r10 = r4
            if (r10 >= 0) goto L_0x0046
            r10 = r1
            r4 = r10
        L_0x0046:
            r10 = r0
            r11 = r4
            r10 = r10[r11]
            r11 = r2
            boolean r10 = r10.equals2D(r11)
            if (r10 == 0) goto L_0x0055
            r10 = r4
            r11 = r3
            if (r10 != r11) goto L_0x003c
        L_0x0055:
            r10 = r3
            r5 = r10
        L_0x0057:
            r10 = r5
            r11 = 1
            int r10 = r10 + 1
            r11 = r1
            int r10 = r10 % r11
            r5 = r10
            r10 = r0
            r11 = r5
            r10 = r10[r11]
            r11 = r2
            boolean r10 = r10.equals2D(r11)
            if (r10 == 0) goto L_0x006d
            r10 = r5
            r11 = r3
            if (r10 != r11) goto L_0x0057
        L_0x006d:
            r10 = r0
            r11 = r4
            r10 = r10[r11]
            r6 = r10
            r10 = r0
            r11 = r5
            r10 = r10[r11]
            r7 = r10
            r10 = r6
            r11 = r2
            boolean r10 = r10.equals2D(r11)
            if (r10 != 0) goto L_0x008f
            r10 = r7
            r11 = r2
            boolean r10 = r10.equals2D(r11)
            if (r10 != 0) goto L_0x008f
            r10 = r6
            r11 = r7
            boolean r10 = r10.equals2D(r11)
            if (r10 == 0) goto L_0x0092
        L_0x008f:
            r10 = 0
            r0 = r10
        L_0x0091:
            return r0
        L_0x0092:
            r10 = r6
            r11 = r2
            r12 = r7
            int r10 = computeOrientation(r10, r11, r12)
            r8 = r10
            r10 = r8
            if (r10 != 0) goto L_0x00ae
            r10 = r6
            double r10 = r10.f412x
            r12 = r7
            double r12 = r12.f412x
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 <= 0) goto L_0x00ac
            r10 = 1
        L_0x00a8:
            r9 = r10
        L_0x00a9:
            r10 = r9
            r0 = r10
            goto L_0x0091
        L_0x00ac:
            r10 = 0
            goto L_0x00a8
        L_0x00ae:
            r10 = r8
            if (r10 <= 0) goto L_0x00b4
            r10 = 1
        L_0x00b2:
            r9 = r10
            goto L_0x00a9
        L_0x00b4:
            r10 = 0
            goto L_0x00b2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.algorithm.CGAlgorithms.isCCW(org.locationtech.jts.geom.Coordinate[]):boolean");
    }

    public static int computeOrientation(Coordinate p1, Coordinate p2, Coordinate q) {
        return orientationIndex(p1, p2, q);
    }

    public static double distancePointLine(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate p = coordinate;
        Coordinate A = coordinate2;
        Coordinate B = coordinate3;
        if (A.f412x == B.f412x && A.f413y == B.f413y) {
            return p.distance(A);
        }
        double len2 = ((B.f412x - A.f412x) * (B.f412x - A.f412x)) + ((B.f413y - A.f413y) * (B.f413y - A.f413y));
        double r = (((p.f412x - A.f412x) * (B.f412x - A.f412x)) + ((p.f413y - A.f413y) * (B.f413y - A.f413y))) / len2;
        if (r <= 0.0d) {
            return p.distance(A);
        }
        if (r >= 1.0d) {
            return p.distance(B);
        }
        return Math.abs((((A.f413y - p.f413y) * (B.f412x - A.f412x)) - ((A.f412x - p.f412x) * (B.f413y - A.f413y))) / len2) * Math.sqrt(len2);
    }

    public static double distancePointLinePerpendicular(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate p = coordinate;
        Coordinate A = coordinate2;
        Coordinate B = coordinate3;
        double len2 = ((B.f412x - A.f412x) * (B.f412x - A.f412x)) + ((B.f413y - A.f413y) * (B.f413y - A.f413y));
        return Math.abs((((A.f413y - p.f413y) * (B.f412x - A.f412x)) - ((A.f412x - p.f412x) * (B.f413y - A.f413y))) / len2) * Math.sqrt(len2);
    }

    public static double distancePointLine(Coordinate coordinate, Coordinate[] coordinateArr) {
        Throwable th;
        Coordinate p = coordinate;
        Coordinate[] line = coordinateArr;
        if (line.length == 0) {
            Throwable th2 = th;
            new IllegalArgumentException("Line array must contain at least one vertex");
            throw th2;
        }
        double minDistance = p.distance(line[0]);
        for (int i = 0; i < line.length - 1; i++) {
            double dist = distancePointLine(p, line[i], line[i + 1]);
            if (dist < minDistance) {
                minDistance = dist;
            }
        }
        return minDistance;
    }

    public static double distanceLineLine(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate A = coordinate;
        Coordinate B = coordinate2;
        Coordinate C = coordinate3;
        Coordinate D = coordinate4;
        if (A.equals(B)) {
            return distancePointLine(A, C, D);
        }
        if (C.equals(D)) {
            return distancePointLine(D, A, B);
        }
        boolean noIntersection = false;
        if (!Envelope.intersects(A, B, C, D)) {
            noIntersection = true;
        } else {
            double denom = ((B.f412x - A.f412x) * (D.f413y - C.f413y)) - ((B.f413y - A.f413y) * (D.f412x - C.f412x));
            if (denom == 0.0d) {
                noIntersection = true;
            } else {
                double r_num = ((A.f413y - C.f413y) * (D.f412x - C.f412x)) - ((A.f412x - C.f412x) * (D.f413y - C.f413y));
                double s = (((A.f413y - C.f413y) * (B.f412x - A.f412x)) - ((A.f412x - C.f412x) * (B.f413y - A.f413y))) / denom;
                double r = r_num / denom;
                if (r < 0.0d || r > 1.0d || s < 0.0d || s > 1.0d) {
                    noIntersection = true;
                }
            }
        }
        if (noIntersection) {
            return MathUtil.min(distancePointLine(A, C, D), distancePointLine(B, C, D), distancePointLine(C, A, B), distancePointLine(D, A, B));
        }
        return 0.0d;
    }

    public static double signedArea(Coordinate[] coordinateArr) {
        Coordinate[] ring = coordinateArr;
        if (ring.length < 3) {
            return 0.0d;
        }
        double sum = 0.0d;
        double x0 = ring[0].f412x;
        for (int i = 1; i < ring.length - 1; i++) {
            sum += (ring[i].f412x - x0) * (ring[i - 1].f413y - ring[i + 1].f413y);
        }
        return sum / 2.0d;
    }

    public static double signedArea(CoordinateSequence coordinateSequence) {
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate coordinate3;
        CoordinateSequence ring = coordinateSequence;
        int n = ring.size();
        if (n < 3) {
            return 0.0d;
        }
        new Coordinate();
        Coordinate p0 = coordinate;
        new Coordinate();
        Coordinate p1 = coordinate2;
        new Coordinate();
        Coordinate p2 = coordinate3;
        ring.getCoordinate(0, p1);
        ring.getCoordinate(1, p2);
        double x0 = p1.f412x;
        p2.f412x -= x0;
        double sum = 0.0d;
        for (int i = 1; i < n - 1; i++) {
            p0.f413y = p1.f413y;
            p1.f412x = p2.f412x;
            p1.f413y = p2.f413y;
            ring.getCoordinate(i + 1, p2);
            p2.f412x -= x0;
            sum += p1.f412x * (p0.f413y - p2.f413y);
        }
        return sum / 2.0d;
    }

    public static double length(CoordinateSequence coordinateSequence) {
        Coordinate coordinate;
        CoordinateSequence pts = coordinateSequence;
        int n = pts.size();
        if (n <= 1) {
            return 0.0d;
        }
        double len = 0.0d;
        new Coordinate();
        Coordinate p = coordinate;
        pts.getCoordinate(0, p);
        double x0 = p.f412x;
        double y0 = p.f413y;
        for (int i = 1; i < n; i++) {
            pts.getCoordinate(i, p);
            double x1 = p.f412x;
            double y1 = p.f413y;
            double dx = x1 - x0;
            double dy = y1 - y0;
            len += Math.sqrt((dx * dx) + (dy * dy));
            x0 = x1;
            y0 = y1;
        }
        return len;
    }
}
