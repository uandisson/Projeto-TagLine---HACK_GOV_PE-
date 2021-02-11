package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.Point;

public class InteriorPointPoint {
    private Coordinate centroid;
    private Coordinate interiorPoint = null;
    private double minDistance = Double.MAX_VALUE;

    public InteriorPointPoint(Geometry geometry) {
        Geometry g = geometry;
        this.centroid = g.getCentroid().getCoordinate();
        add(g);
    }

    private void add(Geometry geometry) {
        Geometry geom = geometry;
        if (geom instanceof Point) {
            add(geom.getCoordinate());
        } else if (geom instanceof GeometryCollection) {
            GeometryCollection gc = (GeometryCollection) geom;
            for (int i = 0; i < gc.getNumGeometries(); i++) {
                add(gc.getGeometryN(i));
            }
        }
    }

    private void add(Coordinate coordinate) {
        Coordinate coordinate2;
        Coordinate point = coordinate;
        double dist = point.distance(this.centroid);
        if (dist < this.minDistance) {
            new Coordinate(point);
            this.interiorPoint = coordinate2;
            this.minDistance = dist;
        }
    }

    public Coordinate getInteriorPoint() {
        return this.interiorPoint;
    }
}
