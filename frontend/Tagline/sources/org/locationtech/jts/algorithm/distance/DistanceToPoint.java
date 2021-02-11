package org.locationtech.jts.algorithm.distance;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;

public class DistanceToPoint {
    public DistanceToPoint() {
    }

    public static void computeDistance(Geometry geometry, Coordinate coordinate, PointPairDistance pointPairDistance) {
        Geometry geom = geometry;
        Coordinate pt = coordinate;
        PointPairDistance ptDist = pointPairDistance;
        if (geom instanceof LineString) {
            computeDistance((LineString) geom, pt, ptDist);
        } else if (geom instanceof Polygon) {
            computeDistance((Polygon) geom, pt, ptDist);
        } else if (geom instanceof GeometryCollection) {
            GeometryCollection gc = (GeometryCollection) geom;
            for (int i = 0; i < gc.getNumGeometries(); i++) {
                computeDistance(gc.getGeometryN(i), pt, ptDist);
            }
        } else {
            ptDist.setMinimum(geom.getCoordinate(), pt);
        }
    }

    public static void computeDistance(LineString line, Coordinate coordinate, PointPairDistance pointPairDistance) {
        LineSegment lineSegment;
        Coordinate pt = coordinate;
        PointPairDistance ptDist = pointPairDistance;
        new LineSegment();
        LineSegment tempSegment = lineSegment;
        Coordinate[] coords = line.getCoordinates();
        for (int i = 0; i < coords.length - 1; i++) {
            tempSegment.setCoordinates(coords[i], coords[i + 1]);
            ptDist.setMinimum(tempSegment.closestPoint(pt), pt);
        }
    }

    public static void computeDistance(LineSegment segment, Coordinate coordinate, PointPairDistance ptDist) {
        Coordinate pt = coordinate;
        ptDist.setMinimum(segment.closestPoint(pt), pt);
    }

    public static void computeDistance(Polygon polygon, Coordinate coordinate, PointPairDistance pointPairDistance) {
        Polygon poly = polygon;
        Coordinate pt = coordinate;
        PointPairDistance ptDist = pointPairDistance;
        computeDistance(poly.getExteriorRing(), pt, ptDist);
        for (int i = 0; i < poly.getNumInteriorRing(); i++) {
            computeDistance(poly.getInteriorRingN(i), pt, ptDist);
        }
    }
}
