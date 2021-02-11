package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;

public class InteriorPointLine {
    private Coordinate centroid;
    private Coordinate interiorPoint = null;
    private double minDistance = Double.MAX_VALUE;

    public InteriorPointLine(Geometry geometry) {
        Geometry g = geometry;
        this.centroid = g.getCentroid().getCoordinate();
        addInterior(g);
        if (this.interiorPoint == null) {
            addEndpoints(g);
        }
    }

    public Coordinate getInteriorPoint() {
        return this.interiorPoint;
    }

    private void addInterior(Geometry geometry) {
        Geometry geom = geometry;
        if (geom instanceof LineString) {
            addInterior(geom.getCoordinates());
        } else if (geom instanceof GeometryCollection) {
            GeometryCollection gc = (GeometryCollection) geom;
            for (int i = 0; i < gc.getNumGeometries(); i++) {
                addInterior(gc.getGeometryN(i));
            }
        }
    }

    private void addInterior(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        for (int i = 1; i < pts.length - 1; i++) {
            add(pts[i]);
        }
    }

    private void addEndpoints(Geometry geometry) {
        Geometry geom = geometry;
        if (geom instanceof LineString) {
            addEndpoints(geom.getCoordinates());
        } else if (geom instanceof GeometryCollection) {
            GeometryCollection gc = (GeometryCollection) geom;
            for (int i = 0; i < gc.getNumGeometries(); i++) {
                addEndpoints(gc.getGeometryN(i));
            }
        }
    }

    private void addEndpoints(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        add(pts[0]);
        add(pts[pts.length - 1]);
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
}
