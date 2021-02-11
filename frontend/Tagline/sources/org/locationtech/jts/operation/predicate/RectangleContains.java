package org.locationtech.jts.operation.predicate;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class RectangleContains {
    private Envelope rectEnv;

    public static boolean contains(Polygon rectangle, Geometry b) {
        RectangleContains rc;
        new RectangleContains(rectangle);
        return rc.contains(b);
    }

    public RectangleContains(Polygon rectangle) {
        this.rectEnv = rectangle.getEnvelopeInternal();
    }

    public boolean contains(Geometry geometry) {
        Geometry geom = geometry;
        if (!this.rectEnv.contains(geom.getEnvelopeInternal())) {
            return false;
        }
        if (isContainedInBoundary(geom)) {
            return false;
        }
        return true;
    }

    private boolean isContainedInBoundary(Geometry geometry) {
        Geometry geom = geometry;
        if (geom instanceof Polygon) {
            return false;
        }
        if (geom instanceof Point) {
            return isPointContainedInBoundary((Point) geom);
        }
        if (geom instanceof LineString) {
            return isLineStringContainedInBoundary((LineString) geom);
        }
        for (int i = 0; i < geom.getNumGeometries(); i++) {
            if (!isContainedInBoundary(geom.getGeometryN(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isPointContainedInBoundary(Point point) {
        return isPointContainedInBoundary(point.getCoordinate());
    }

    private boolean isPointContainedInBoundary(Coordinate coordinate) {
        Coordinate pt = coordinate;
        return pt.f412x == this.rectEnv.getMinX() || pt.f412x == this.rectEnv.getMaxX() || pt.f413y == this.rectEnv.getMinY() || pt.f413y == this.rectEnv.getMaxY();
    }

    private boolean isLineStringContainedInBoundary(LineString line) {
        Coordinate coordinate;
        Coordinate coordinate2;
        CoordinateSequence seq = line.getCoordinateSequence();
        new Coordinate();
        Coordinate p0 = coordinate;
        new Coordinate();
        Coordinate p1 = coordinate2;
        for (int i = 0; i < seq.size() - 1; i++) {
            seq.getCoordinate(i, p0);
            seq.getCoordinate(i + 1, p1);
            if (!isLineSegmentContainedInBoundary(p0, p1)) {
                return false;
            }
        }
        return true;
    }

    private boolean isLineSegmentContainedInBoundary(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        if (p0.equals(p1)) {
            return isPointContainedInBoundary(p0);
        }
        if (p0.f412x == p1.f412x) {
            if (p0.f412x == this.rectEnv.getMinX() || p0.f412x == this.rectEnv.getMaxX()) {
                return true;
            }
        } else if (p0.f413y == p1.f413y && (p0.f413y == this.rectEnv.getMinY() || p0.f413y == this.rectEnv.getMaxY())) {
            return true;
        }
        return false;
    }
}
