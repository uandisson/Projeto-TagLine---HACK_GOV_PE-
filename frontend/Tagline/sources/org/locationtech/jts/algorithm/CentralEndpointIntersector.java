package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;

public class CentralEndpointIntersector {
    private Coordinate intPt = null;
    private double minDist = Double.MAX_VALUE;
    private Coordinate[] pts;

    public static Coordinate getIntersection(Coordinate p00, Coordinate p01, Coordinate p10, Coordinate p11) {
        CentralEndpointIntersector intor;
        new CentralEndpointIntersector(p00, p01, p10, p11);
        return intor.getIntersection();
    }

    public CentralEndpointIntersector(Coordinate p00, Coordinate p01, Coordinate p10, Coordinate p11) {
        Coordinate[] coordinateArr = new Coordinate[4];
        coordinateArr[0] = p00;
        Coordinate[] coordinateArr2 = coordinateArr;
        coordinateArr2[1] = p01;
        Coordinate[] coordinateArr3 = coordinateArr2;
        coordinateArr3[2] = p10;
        Coordinate[] coordinateArr4 = coordinateArr3;
        coordinateArr4[3] = p11;
        this.pts = coordinateArr4;
        compute();
    }

    private void Ocompute() {
        Coordinate coordinate;
        new Coordinate(findNearestPoint(average(this.pts), this.pts));
        this.intPt = coordinate;
    }

    public Coordinate getIntersection() {
        return this.intPt;
    }

    private static Coordinate average(Coordinate[] coordinateArr) {
        Coordinate coordinate;
        Coordinate[] pts2 = coordinateArr;
        new Coordinate();
        Coordinate avg = coordinate;
        int n = pts2.length;
        for (int i = 0; i < pts2.length; i++) {
            avg.f412x += pts2[i].f412x;
            avg.f413y += pts2[i].f413y;
        }
        if (n > 0) {
            avg.f412x /= (double) n;
            avg.f413y /= (double) n;
        }
        return avg;
    }

    private Coordinate findNearestPoint(Coordinate coordinate, Coordinate[] coordinateArr) {
        Coordinate p = coordinate;
        Coordinate[] pts2 = coordinateArr;
        double minDist2 = Double.MAX_VALUE;
        Coordinate result = null;
        for (int i = 0; i < pts2.length; i++) {
            double dist = p.distance(pts2[i]);
            if (i == 0 || dist < minDist2) {
                minDist2 = dist;
                result = pts2[i];
            }
        }
        return result;
    }

    private void compute() {
        tryDist(this.pts[0], this.pts[2], this.pts[3]);
        tryDist(this.pts[1], this.pts[2], this.pts[3]);
        tryDist(this.pts[2], this.pts[0], this.pts[1]);
        tryDist(this.pts[3], this.pts[0], this.pts[1]);
    }

    private void tryDist(Coordinate coordinate, Coordinate p0, Coordinate p1) {
        Coordinate p = coordinate;
        double dist = CGAlgorithms.distancePointLine(p, p0, p1);
        if (dist < this.minDist) {
            this.minDist = dist;
            this.intPt = p;
        }
    }
}
