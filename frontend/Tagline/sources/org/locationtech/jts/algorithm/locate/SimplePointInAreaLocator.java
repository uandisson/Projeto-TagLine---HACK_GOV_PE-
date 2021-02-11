package org.locationtech.jts.algorithm.locate;

import java.util.Iterator;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryCollectionIterator;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

public class SimplePointInAreaLocator implements PointOnGeometryLocator {
    private Geometry geom;

    public static int locate(Coordinate coordinate, Geometry geometry) {
        Coordinate p = coordinate;
        Geometry geom2 = geometry;
        if (geom2.isEmpty()) {
            return 2;
        }
        if (containsPoint(p, geom2)) {
            return 0;
        }
        return 2;
    }

    private static boolean containsPoint(Coordinate coordinate, Geometry geometry) {
        Iterator it;
        Coordinate p = coordinate;
        Geometry geom2 = geometry;
        if (geom2 instanceof Polygon) {
            return containsPointInPolygon(p, (Polygon) geom2);
        }
        if (geom2 instanceof GeometryCollection) {
            new GeometryCollectionIterator((GeometryCollection) geom2);
            Iterator geomi = it;
            while (geomi.hasNext()) {
                Geometry g2 = (Geometry) geomi.next();
                if (g2 != geom2 && containsPoint(p, g2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsPointInPolygon(Coordinate coordinate, Polygon polygon) {
        Coordinate p = coordinate;
        Polygon poly = polygon;
        if (poly.isEmpty()) {
            return false;
        }
        if (!isPointInRing(p, (LinearRing) poly.getExteriorRing())) {
            return false;
        }
        for (int i = 0; i < poly.getNumInteriorRing(); i++) {
            if (isPointInRing(p, (LinearRing) poly.getInteriorRingN(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPointInRing(Coordinate coordinate, LinearRing linearRing) {
        Coordinate p = coordinate;
        LinearRing ring = linearRing;
        if (!ring.getEnvelopeInternal().intersects(p)) {
            return false;
        }
        return CGAlgorithms.isPointInRing(p, ring.getCoordinates());
    }

    public SimplePointInAreaLocator(Geometry geom2) {
        this.geom = geom2;
    }

    public int locate(Coordinate p) {
        return locate(p, this.geom);
    }
}
