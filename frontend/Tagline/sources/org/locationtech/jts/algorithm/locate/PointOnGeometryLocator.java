package org.locationtech.jts.algorithm.locate;

import org.locationtech.jts.geom.Coordinate;

public interface PointOnGeometryLocator {
    int locate(Coordinate coordinate);
}
