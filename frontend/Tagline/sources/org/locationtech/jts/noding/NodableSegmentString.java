package org.locationtech.jts.noding;

import org.locationtech.jts.geom.Coordinate;

public interface NodableSegmentString extends SegmentString {
    void addIntersection(Coordinate coordinate, int i);
}
