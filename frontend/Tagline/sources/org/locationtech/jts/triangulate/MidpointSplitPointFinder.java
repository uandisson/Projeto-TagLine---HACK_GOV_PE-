package org.locationtech.jts.triangulate;

import org.locationtech.jts.geom.Coordinate;

public class MidpointSplitPointFinder implements ConstraintSplitPointFinder {
    public MidpointSplitPointFinder() {
    }

    public Coordinate findSplitPoint(Segment segment, Coordinate coordinate) {
        Coordinate coordinate2;
        Segment seg = segment;
        Coordinate coordinate3 = coordinate;
        Coordinate p0 = seg.getStart();
        Coordinate p1 = seg.getEnd();
        new Coordinate((p0.f412x + p1.f412x) / 2.0d, (p0.f413y + p1.f413y) / 2.0d);
        return coordinate2;
    }
}
