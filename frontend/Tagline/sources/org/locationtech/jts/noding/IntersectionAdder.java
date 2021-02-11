package org.locationtech.jts.noding;

import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.geom.Coordinate;

public class IntersectionAdder implements SegmentIntersector {
    private boolean hasInterior = false;
    private boolean hasIntersection = false;
    private boolean hasProper = false;
    private boolean hasProperInterior = false;
    private boolean isSelfIntersection;

    /* renamed from: li */
    private LineIntersector f463li;
    public int numInteriorIntersections = 0;
    public int numIntersections = 0;
    public int numProperIntersections = 0;
    public int numTests = 0;
    private Coordinate properIntersectionPoint = null;

    public static boolean isAdjacentSegments(int i1, int i2) {
        return Math.abs(i1 - i2) == 1;
    }

    public IntersectionAdder(LineIntersector li) {
        this.f463li = li;
    }

    public LineIntersector getLineIntersector() {
        return this.f463li;
    }

    public Coordinate getProperIntersectionPoint() {
        return this.properIntersectionPoint;
    }

    public boolean hasIntersection() {
        return this.hasIntersection;
    }

    public boolean hasProperIntersection() {
        return this.hasProper;
    }

    public boolean hasProperInteriorIntersection() {
        return this.hasProperInterior;
    }

    public boolean hasInteriorIntersection() {
        return this.hasInterior;
    }

    private boolean isTrivialIntersection(SegmentString segmentString, int i, SegmentString e1, int i2) {
        SegmentString e0 = segmentString;
        int segIndex0 = i;
        int segIndex1 = i2;
        if (e0 == e1 && this.f463li.getIntersectionNum() == 1) {
            if (isAdjacentSegments(segIndex0, segIndex1)) {
                return true;
            }
            if (e0.isClosed()) {
                int maxSegIndex = e0.size() - 1;
                if ((segIndex0 == 0 && segIndex1 == maxSegIndex) || (segIndex1 == 0 && segIndex0 == maxSegIndex)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void processIntersections(SegmentString segmentString, int i, SegmentString segmentString2, int i2) {
        SegmentString e0 = segmentString;
        int segIndex0 = i;
        SegmentString e1 = segmentString2;
        int segIndex1 = i2;
        if (e0 != e1 || segIndex0 != segIndex1) {
            this.numTests++;
            this.f463li.computeIntersection(e0.getCoordinates()[segIndex0], e0.getCoordinates()[segIndex0 + 1], e1.getCoordinates()[segIndex1], e1.getCoordinates()[segIndex1 + 1]);
            if (this.f463li.hasIntersection()) {
                this.numIntersections++;
                if (this.f463li.isInteriorIntersection()) {
                    this.numInteriorIntersections++;
                    this.hasInterior = true;
                }
                if (!isTrivialIntersection(e0, segIndex0, e1, segIndex1)) {
                    this.hasIntersection = true;
                    ((NodedSegmentString) e0).addIntersections(this.f463li, segIndex0, 0);
                    ((NodedSegmentString) e1).addIntersections(this.f463li, segIndex1, 1);
                    if (this.f463li.isProper()) {
                        this.numProperIntersections++;
                        this.hasProper = true;
                        this.hasProperInterior = true;
                    }
                }
            }
        }
    }

    public boolean isDone() {
        return false;
    }
}
