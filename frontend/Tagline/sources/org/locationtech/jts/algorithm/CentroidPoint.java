package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.Point;

public class CentroidPoint {
    private Coordinate centSum;
    private int ptCount = 0;

    public CentroidPoint() {
        Coordinate coordinate;
        new Coordinate();
        this.centSum = coordinate;
    }

    public void add(Geometry geometry) {
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

    public void add(Coordinate coordinate) {
        Coordinate pt = coordinate;
        this.ptCount++;
        this.centSum.f412x += pt.f412x;
        this.centSum.f413y += pt.f413y;
    }

    public Coordinate getCentroid() {
        Coordinate coordinate;
        new Coordinate();
        Coordinate cent = coordinate;
        cent.f412x = this.centSum.f412x / ((double) this.ptCount);
        cent.f413y = this.centSum.f413y / ((double) this.ptCount);
        return cent;
    }
}
