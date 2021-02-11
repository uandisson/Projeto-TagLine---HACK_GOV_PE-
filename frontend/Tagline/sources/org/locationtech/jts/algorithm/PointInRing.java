package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;

public interface PointInRing {
    boolean isInside(Coordinate coordinate);
}
