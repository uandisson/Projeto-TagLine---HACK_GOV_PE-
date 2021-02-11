package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LinearRing;

public class SimplePointInRing implements PointInRing {
    private Coordinate[] pts;

    public SimplePointInRing(LinearRing ring) {
        this.pts = ring.getCoordinates();
    }

    public boolean isInside(Coordinate pt) {
        return CGAlgorithms.isPointInRing(pt, this.pts);
    }
}
