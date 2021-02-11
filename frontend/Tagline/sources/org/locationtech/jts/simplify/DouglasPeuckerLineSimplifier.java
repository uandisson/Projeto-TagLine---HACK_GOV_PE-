package org.locationtech.jts.simplify;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.LineSegment;

class DouglasPeuckerLineSimplifier {
    private double distanceTolerance;
    private Coordinate[] pts;
    private LineSegment seg;
    private boolean[] usePt;

    public static Coordinate[] simplify(Coordinate[] pts2, double distanceTolerance2) {
        DouglasPeuckerLineSimplifier douglasPeuckerLineSimplifier;
        new DouglasPeuckerLineSimplifier(pts2);
        DouglasPeuckerLineSimplifier simp = douglasPeuckerLineSimplifier;
        simp.setDistanceTolerance(distanceTolerance2);
        return simp.simplify();
    }

    public DouglasPeuckerLineSimplifier(Coordinate[] pts2) {
        LineSegment lineSegment;
        new LineSegment();
        this.seg = lineSegment;
        this.pts = pts2;
    }

    public void setDistanceTolerance(double distanceTolerance2) {
        double d = distanceTolerance2;
        this.distanceTolerance = d;
    }

    public Coordinate[] simplify() {
        CoordinateList coordinateList;
        Object obj;
        this.usePt = new boolean[this.pts.length];
        for (int i = 0; i < this.pts.length; i++) {
            this.usePt[i] = true;
        }
        simplifySection(0, this.pts.length - 1);
        new CoordinateList();
        CoordinateList coordList = coordinateList;
        for (int i2 = 0; i2 < this.pts.length; i2++) {
            if (this.usePt[i2]) {
                new Coordinate(this.pts[i2]);
                boolean add = coordList.add(obj);
            }
        }
        return coordList.toCoordinateArray();
    }

    private void simplifySection(int i, int i2) {
        int i3 = i;
        int j = i2;
        if (i3 + 1 != j) {
            this.seg.f422p0 = this.pts[i3];
            this.seg.f423p1 = this.pts[j];
            double maxDistance = -1.0d;
            int maxIndex = i3;
            for (int k = i3 + 1; k < j; k++) {
                double distance = this.seg.distance(this.pts[k]);
                if (distance > maxDistance) {
                    maxDistance = distance;
                    maxIndex = k;
                }
            }
            if (maxDistance <= this.distanceTolerance) {
                for (int k2 = i3 + 1; k2 < j; k2++) {
                    this.usePt[k2] = false;
                }
                return;
            }
            simplifySection(i3, maxIndex);
            simplifySection(maxIndex, j);
        }
    }
}
