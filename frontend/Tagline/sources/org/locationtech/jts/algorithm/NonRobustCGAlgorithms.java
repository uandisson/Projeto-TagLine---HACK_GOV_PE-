package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;

public class NonRobustCGAlgorithms extends CGAlgorithms {
    public NonRobustCGAlgorithms() {
    }

    public static boolean isPointInRing(Coordinate coordinate, Coordinate[] coordinateArr) {
        Coordinate p = coordinate;
        Coordinate[] ring = coordinateArr;
        int crossings = 0;
        int nPts = ring.length;
        for (int i = 1; i < nPts; i++) {
            Coordinate p1 = ring[i];
            Coordinate p2 = ring[i - 1];
            double x1 = p1.f412x - p.f412x;
            double y1 = p1.f413y - p.f413y;
            double x2 = p2.f412x - p.f412x;
            double y2 = p2.f413y - p.f413y;
            if (((y1 > 0.0d && y2 <= 0.0d) || (y2 > 0.0d && y1 <= 0.0d)) && 0.0d < ((x1 * y2) - (x2 * y1)) / (y2 - y1)) {
                crossings++;
            }
        }
        if (crossings % 2 == 1) {
            return true;
        }
        return false;
    }

    public static boolean isCCW(Coordinate[] coordinateArr) {
        Throwable th;
        Coordinate[] ring = coordinateArr;
        int nPts = ring.length - 1;
        if (nPts < 4) {
            return false;
        }
        Coordinate hip = ring[0];
        int hii = 0;
        for (int i = 1; i <= nPts; i++) {
            Coordinate p = ring[i];
            if (p.f413y > hip.f413y) {
                hip = p;
                hii = i;
            }
        }
        int iPrev = hii;
        do {
            iPrev = (iPrev - 1) % nPts;
            if (!ring[iPrev].equals(hip) || iPrev == hii) {
                int iNext = hii;
            }
            iPrev = (iPrev - 1) % nPts;
            break;
        } while (iPrev == hii);
        int iNext2 = hii;
        do {
            iNext2 = (iNext2 + 1) % nPts;
            if (!ring[iNext2].equals(hip) || iNext2 == hii) {
                Coordinate prev = ring[iPrev];
                Coordinate next = ring[iNext2];
            }
            iNext2 = (iNext2 + 1) % nPts;
            break;
        } while (iNext2 == hii);
        Coordinate prev2 = ring[iPrev];
        Coordinate next2 = ring[iNext2];
        if (prev2.equals(hip) || next2.equals(hip) || prev2.equals(next2)) {
            Throwable th2 = th;
            new IllegalArgumentException("degenerate ring (does not contain 3 different points)");
            throw th2;
        }
        double disc = ((next2.f412x - hip.f412x) * (prev2.f413y - hip.f413y)) - ((next2.f413y - hip.f413y) * (prev2.f412x - hip.f412x));
        if (disc == 0.0d) {
            return prev2.f412x > next2.f412x;
        }
        return disc > 0.0d;
    }

    public static int computeOrientation(Coordinate p1, Coordinate p2, Coordinate q) {
        return orientationIndex(p1, p2, q);
    }

    public static int orientationIndex(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q = coordinate3;
        double dx1 = p2.f412x - p1.f412x;
        double dy1 = p2.f413y - p1.f413y;
        double dx2 = q.f412x - p2.f412x;
        double det = (dx1 * (q.f413y - p2.f413y)) - (dx2 * dy1);
        if (det > 0.0d) {
            return 1;
        }
        if (det < 0.0d) {
            return -1;
        }
        return 0;
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
        double r_top = ((A.f413y - C.f413y) * (D.f412x - C.f412x)) - ((A.f412x - C.f412x) * (D.f413y - C.f413y));
        double r_bot = ((B.f412x - A.f412x) * (D.f413y - C.f413y)) - ((B.f413y - A.f413y) * (D.f412x - C.f412x));
        double s_top = ((A.f413y - C.f413y) * (B.f412x - A.f412x)) - ((A.f412x - C.f412x) * (B.f413y - A.f413y));
        double s_bot = ((B.f412x - A.f412x) * (D.f413y - C.f413y)) - ((B.f413y - A.f413y) * (D.f412x - C.f412x));
        if (r_bot == 0.0d || s_bot == 0.0d) {
            return Math.min(distancePointLine(A, C, D), Math.min(distancePointLine(B, C, D), Math.min(distancePointLine(C, A, B), distancePointLine(D, A, B))));
        }
        double s = s_top / s_bot;
        double r = r_top / r_bot;
        if (r < 0.0d || r > 1.0d || s < 0.0d || s > 1.0d) {
            return Math.min(distancePointLine(A, C, D), Math.min(distancePointLine(B, C, D), Math.min(distancePointLine(C, A, B), distancePointLine(D, A, B))));
        }
        return 0.0d;
    }
}
