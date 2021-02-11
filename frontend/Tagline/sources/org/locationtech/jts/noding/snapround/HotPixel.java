package org.locationtech.jts.noding.snapround;

import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.noding.NodedSegmentString;

public class HotPixel {
    private static final double SAFE_ENV_EXPANSION_FACTOR = 0.75d;
    private Coordinate[] corner = new Coordinate[4];

    /* renamed from: li */
    private LineIntersector f472li;
    private double maxx;
    private double maxy;
    private double minx;
    private double miny;
    private Coordinate originalPt;
    private Coordinate p0Scaled;
    private Coordinate p1Scaled;

    /* renamed from: pt */
    private Coordinate f473pt;
    private Coordinate ptScaled;
    private Envelope safeEnv = null;
    private double scaleFactor;

    public HotPixel(Coordinate coordinate, double d, LineIntersector li) {
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        Throwable th;
        Coordinate pt = coordinate;
        double scaleFactor2 = d;
        this.originalPt = pt;
        this.f473pt = pt;
        this.scaleFactor = scaleFactor2;
        this.f472li = li;
        if (scaleFactor2 <= 0.0d) {
            Throwable th2 = th;
            new IllegalArgumentException("Scale factor must be non-zero");
            throw th2;
        }
        if (scaleFactor2 != 1.0d) {
            new Coordinate(scale(pt.f412x), scale(pt.f413y));
            this.f473pt = coordinate2;
            new Coordinate();
            this.p0Scaled = coordinate3;
            new Coordinate();
            this.p1Scaled = coordinate4;
        }
        initCorners(this.f473pt);
    }

    public Coordinate getCoordinate() {
        return this.originalPt;
    }

    public Envelope getSafeEnvelope() {
        Envelope envelope;
        if (this.safeEnv == null) {
            double safeTolerance = SAFE_ENV_EXPANSION_FACTOR / this.scaleFactor;
            new Envelope(this.originalPt.f412x - safeTolerance, this.originalPt.f412x + safeTolerance, this.originalPt.f413y - safeTolerance, this.originalPt.f413y + safeTolerance);
            this.safeEnv = envelope;
        }
        return this.safeEnv;
    }

    private void initCorners(Coordinate coordinate) {
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        Coordinate coordinate5;
        Coordinate pt = coordinate;
        this.minx = pt.f412x - 0.5d;
        this.maxx = pt.f412x + 0.5d;
        this.miny = pt.f413y - 0.5d;
        this.maxy = pt.f413y + 0.5d;
        new Coordinate(this.maxx, this.maxy);
        this.corner[0] = coordinate2;
        new Coordinate(this.minx, this.maxy);
        this.corner[1] = coordinate3;
        new Coordinate(this.minx, this.miny);
        this.corner[2] = coordinate4;
        new Coordinate(this.maxx, this.miny);
        this.corner[3] = coordinate5;
    }

    private double scale(double val) {
        return (double) Math.round(val * this.scaleFactor);
    }

    public boolean intersects(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        if (this.scaleFactor == 1.0d) {
            return intersectsScaled(p0, p1);
        }
        copyScaled(p0, this.p0Scaled);
        copyScaled(p1, this.p1Scaled);
        return intersectsScaled(this.p0Scaled, this.p1Scaled);
    }

    private void copyScaled(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p = coordinate;
        Coordinate pScaled = coordinate2;
        pScaled.f412x = scale(p.f412x);
        pScaled.f413y = scale(p.f413y);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x00a6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean intersectsScaled(org.locationtech.jts.geom.Coordinate r21, org.locationtech.jts.geom.Coordinate r22) {
        /*
            r20 = this;
            r3 = r20
            r4 = r21
            r5 = r22
            r16 = r4
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r18 = r5
            r0 = r18
            double r0 = r0.f412x
            r18 = r0
            double r16 = java.lang.Math.min(r16, r18)
            r6 = r16
            r16 = r4
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r18 = r5
            r0 = r18
            double r0 = r0.f412x
            r18 = r0
            double r16 = java.lang.Math.max(r16, r18)
            r8 = r16
            r16 = r4
            r0 = r16
            double r0 = r0.f413y
            r16 = r0
            r18 = r5
            r0 = r18
            double r0 = r0.f413y
            r18 = r0
            double r16 = java.lang.Math.min(r16, r18)
            r10 = r16
            r16 = r4
            r0 = r16
            double r0 = r0.f413y
            r16 = r0
            r18 = r5
            r0 = r18
            double r0 = r0.f413y
            r18 = r0
            double r16 = java.lang.Math.max(r16, r18)
            r12 = r16
            r16 = r3
            r0 = r16
            double r0 = r0.maxx
            r16 = r0
            r18 = r6
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 < 0) goto L_0x0096
            r16 = r3
            r0 = r16
            double r0 = r0.minx
            r16 = r0
            r18 = r8
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 > 0) goto L_0x0096
            r16 = r3
            r0 = r16
            double r0 = r0.maxy
            r16 = r0
            r18 = r10
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 < 0) goto L_0x0096
            r16 = r3
            r0 = r16
            double r0 = r0.miny
            r16 = r0
            r18 = r12
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 <= 0) goto L_0x00a3
        L_0x0096:
            r16 = 1
        L_0x0098:
            r14 = r16
            r16 = r14
            if (r16 == 0) goto L_0x00a6
            r16 = 0
            r3 = r16
        L_0x00a2:
            return r3
        L_0x00a3:
            r16 = 0
            goto L_0x0098
        L_0x00a6:
            r16 = r3
            r17 = r4
            r18 = r5
            boolean r16 = r16.intersectsToleranceSquare(r17, r18)
            r15 = r16
            r16 = r14
            if (r16 == 0) goto L_0x00ba
            r16 = r15
            if (r16 != 0) goto L_0x00c7
        L_0x00ba:
            r16 = 1
        L_0x00bc:
            java.lang.String r17 = "Found bad envelope test"
            org.locationtech.jts.util.Assert.isTrue(r16, r17)
            r16 = r15
            r3 = r16
            goto L_0x00a2
        L_0x00c7:
            r16 = 0
            goto L_0x00bc
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.noding.snapround.HotPixel.intersectsScaled(org.locationtech.jts.geom.Coordinate, org.locationtech.jts.geom.Coordinate):boolean");
    }

    private boolean intersectsToleranceSquare(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        boolean intersectsLeft = false;
        boolean intersectsBottom = false;
        this.f472li.computeIntersection(p0, p1, this.corner[0], this.corner[1]);
        if (this.f472li.isProper()) {
            return true;
        }
        this.f472li.computeIntersection(p0, p1, this.corner[1], this.corner[2]);
        if (this.f472li.isProper()) {
            return true;
        }
        if (this.f472li.hasIntersection()) {
            intersectsLeft = true;
        }
        this.f472li.computeIntersection(p0, p1, this.corner[2], this.corner[3]);
        if (this.f472li.isProper()) {
            return true;
        }
        if (this.f472li.hasIntersection()) {
            intersectsBottom = true;
        }
        this.f472li.computeIntersection(p0, p1, this.corner[3], this.corner[0]);
        if (this.f472li.isProper()) {
            return true;
        }
        if (intersectsLeft && intersectsBottom) {
            return true;
        }
        if (p0.equals(this.f473pt)) {
            return true;
        }
        if (p1.equals(this.f473pt)) {
            return true;
        }
        return false;
    }

    private boolean intersectsPixelClosure(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        this.f472li.computeIntersection(p0, p1, this.corner[0], this.corner[1]);
        if (this.f472li.hasIntersection()) {
            return true;
        }
        this.f472li.computeIntersection(p0, p1, this.corner[1], this.corner[2]);
        if (this.f472li.hasIntersection()) {
            return true;
        }
        this.f472li.computeIntersection(p0, p1, this.corner[2], this.corner[3]);
        if (this.f472li.hasIntersection()) {
            return true;
        }
        this.f472li.computeIntersection(p0, p1, this.corner[3], this.corner[0]);
        if (this.f472li.hasIntersection()) {
            return true;
        }
        return false;
    }

    public boolean addSnappedNode(NodedSegmentString nodedSegmentString, int i) {
        NodedSegmentString segStr = nodedSegmentString;
        int segIndex = i;
        if (!intersects(segStr.getCoordinate(segIndex), segStr.getCoordinate(segIndex + 1))) {
            return false;
        }
        segStr.addIntersection(getCoordinate(), segIndex);
        return true;
    }
}
