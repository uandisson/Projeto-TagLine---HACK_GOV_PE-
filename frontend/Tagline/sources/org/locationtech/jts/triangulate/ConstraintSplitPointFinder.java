package org.locationtech.jts.triangulate;

import org.locationtech.jts.geom.Coordinate;

public interface ConstraintSplitPointFinder {
    Coordinate findSplitPoint(Segment segment, Coordinate coordinate);
}
