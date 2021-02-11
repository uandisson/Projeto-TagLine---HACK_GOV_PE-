package org.locationtech.jts.noding;

import org.locationtech.jts.geom.Coordinate;

public interface SegmentString {
    Coordinate getCoordinate(int i);

    Coordinate[] getCoordinates();

    Object getData();

    boolean isClosed();

    void setData(Object obj);

    int size();
}
