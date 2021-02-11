package org.locationtech.jts.triangulate;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineSegment;

public class NonEncroachingSplitPointFinder implements ConstraintSplitPointFinder {
    public NonEncroachingSplitPointFinder() {
    }

    public Coordinate findSplitPoint(Segment segment, Coordinate coordinate) {
        SplitSegment splitSegment;
        Segment seg = segment;
        Coordinate encroachPt = coordinate;
        LineSegment lineSeg = seg.getLineSegment();
        double midPtLen = lineSeg.getLength() / 2.0d;
        new SplitSegment(lineSeg);
        SplitSegment splitSeg = splitSegment;
        Coordinate projPt = projectedSplitPoint(seg, encroachPt);
        double maxSplitLen = projPt.distance(encroachPt) * 2.0d * 0.8d;
        if (maxSplitLen > midPtLen) {
            maxSplitLen = midPtLen;
        }
        splitSeg.setMinimumLength(maxSplitLen);
        splitSeg.splitAt(projPt);
        return splitSeg.getSplitPoint();
    }

    public static Coordinate projectedSplitPoint(Segment seg, Coordinate encroachPt) {
        return seg.getLineSegment().project(encroachPt);
    }
}
