package org.locationtech.jts.noding;

import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.geom.Coordinate;

public class SegmentIntersectionDetector implements SegmentIntersector {
    private boolean findAllTypes;
    private boolean findProper;
    private boolean hasIntersection;
    private boolean hasNonProperIntersection;
    private boolean hasProperIntersection;
    private Coordinate intPt;
    private Coordinate[] intSegments;

    /* renamed from: li */
    private LineIntersector f470li;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SegmentIntersectionDetector() {
        /*
            r5 = this;
            r0 = r5
            r1 = r0
            org.locationtech.jts.algorithm.RobustLineIntersector r2 = new org.locationtech.jts.algorithm.RobustLineIntersector
            r4 = r2
            r2 = r4
            r3 = r4
            r3.<init>()
            r1.<init>(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.noding.SegmentIntersectionDetector.<init>():void");
    }

    public SegmentIntersectionDetector(LineIntersector li) {
        this.findProper = false;
        this.findAllTypes = false;
        this.hasIntersection = false;
        this.hasProperIntersection = false;
        this.hasNonProperIntersection = false;
        this.intPt = null;
        this.intSegments = null;
        this.f470li = li;
    }

    public void setFindProper(boolean findProper2) {
        boolean z = findProper2;
        this.findProper = z;
    }

    public void setFindAllIntersectionTypes(boolean findAllTypes2) {
        boolean z = findAllTypes2;
        this.findAllTypes = z;
    }

    public boolean hasIntersection() {
        return this.hasIntersection;
    }

    public boolean hasProperIntersection() {
        return this.hasProperIntersection;
    }

    public boolean hasNonProperIntersection() {
        return this.hasNonProperIntersection;
    }

    public Coordinate getIntersection() {
        return this.intPt;
    }

    public Coordinate[] getIntersectionSegments() {
        return this.intSegments;
    }

    public void processIntersections(SegmentString segmentString, int i, SegmentString segmentString2, int i2) {
        SegmentString e0 = segmentString;
        int segIndex0 = i;
        SegmentString e1 = segmentString2;
        int segIndex1 = i2;
        if (e0 != e1 || segIndex0 != segIndex1) {
            Coordinate p00 = e0.getCoordinates()[segIndex0];
            Coordinate p01 = e0.getCoordinates()[segIndex0 + 1];
            Coordinate p10 = e1.getCoordinates()[segIndex1];
            Coordinate p11 = e1.getCoordinates()[segIndex1 + 1];
            this.f470li.computeIntersection(p00, p01, p10, p11);
            if (this.f470li.hasIntersection()) {
                this.hasIntersection = true;
                boolean isProper = this.f470li.isProper();
                if (isProper) {
                    this.hasProperIntersection = true;
                }
                if (!isProper) {
                    this.hasNonProperIntersection = true;
                }
                boolean saveLocation = true;
                if (this.findProper && !isProper) {
                    saveLocation = false;
                }
                if (this.intPt == null || saveLocation) {
                    this.intPt = this.f470li.getIntersection(0);
                    this.intSegments = new Coordinate[4];
                    this.intSegments[0] = p00;
                    this.intSegments[1] = p01;
                    this.intSegments[2] = p10;
                    this.intSegments[3] = p11;
                }
            }
        }
    }

    public boolean isDone() {
        if (this.findAllTypes) {
            return this.hasProperIntersection && this.hasNonProperIntersection;
        } else if (this.findProper) {
            return this.hasProperIntersection;
        } else {
            return this.hasIntersection;
        }
    }
}
