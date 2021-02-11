package org.locationtech.jts.operation.valid;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class RepeatedPointTester {
    private Coordinate repeatedCoord;

    public RepeatedPointTester() {
    }

    public Coordinate getCoordinate() {
        return this.repeatedCoord;
    }

    public boolean hasRepeatedPoint(Geometry geometry) {
        Throwable th;
        Geometry g = geometry;
        if (g.isEmpty()) {
            return false;
        }
        if (g instanceof Point) {
            return false;
        }
        if (g instanceof MultiPoint) {
            return false;
        }
        if (g instanceof LineString) {
            return hasRepeatedPoint(((LineString) g).getCoordinates());
        }
        if (g instanceof Polygon) {
            return hasRepeatedPoint((Polygon) g);
        }
        if (g instanceof GeometryCollection) {
            return hasRepeatedPoint((GeometryCollection) g);
        }
        Throwable th2 = th;
        new UnsupportedOperationException(g.getClass().getName());
        throw th2;
    }

    public boolean hasRepeatedPoint(Coordinate[] coordinateArr) {
        Coordinate[] coord = coordinateArr;
        for (int i = 1; i < coord.length; i++) {
            if (coord[i - 1].equals(coord[i])) {
                this.repeatedCoord = coord[i];
                return true;
            }
        }
        return false;
    }

    private boolean hasRepeatedPoint(Polygon polygon) {
        Polygon p = polygon;
        if (hasRepeatedPoint(p.getExteriorRing().getCoordinates())) {
            return true;
        }
        for (int i = 0; i < p.getNumInteriorRing(); i++) {
            if (hasRepeatedPoint(p.getInteriorRingN(i).getCoordinates())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasRepeatedPoint(GeometryCollection geometryCollection) {
        GeometryCollection gc = geometryCollection;
        for (int i = 0; i < gc.getNumGeometries(); i++) {
            if (hasRepeatedPoint(gc.getGeometryN(i))) {
                return true;
            }
        }
        return false;
    }
}
