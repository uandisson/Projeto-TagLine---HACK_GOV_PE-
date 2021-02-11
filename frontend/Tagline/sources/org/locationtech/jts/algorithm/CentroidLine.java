package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;

public class CentroidLine {
    private Coordinate centSum;
    private double totalLength = 0.0d;

    public CentroidLine() {
        Coordinate coordinate;
        new Coordinate();
        this.centSum = coordinate;
    }

    public void add(Geometry geometry) {
        Geometry geom = geometry;
        if (geom instanceof LineString) {
            add(geom.getCoordinates());
        } else if (geom instanceof Polygon) {
            Polygon poly = (Polygon) geom;
            add(poly.getExteriorRing().getCoordinates());
            for (int i = 0; i < poly.getNumInteriorRing(); i++) {
                add(poly.getInteriorRingN(i).getCoordinates());
            }
        } else if (geom instanceof GeometryCollection) {
            GeometryCollection gc = (GeometryCollection) geom;
            for (int i2 = 0; i2 < gc.getNumGeometries(); i2++) {
                add(gc.getGeometryN(i2));
            }
        }
    }

    public Coordinate getCentroid() {
        Coordinate coordinate;
        new Coordinate();
        Coordinate cent = coordinate;
        cent.f412x = this.centSum.f412x / this.totalLength;
        cent.f413y = this.centSum.f413y / this.totalLength;
        return cent;
    }

    public void add(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        for (int i = 0; i < pts.length - 1; i++) {
            double segmentLen = pts[i].distance(pts[i + 1]);
            this.totalLength += segmentLen;
            double midx = (pts[i].f412x + pts[i + 1].f412x) / 2.0d;
            this.centSum.f412x += segmentLen * midx;
            double midy = (pts[i].f413y + pts[i + 1].f413y) / 2.0d;
            this.centSum.f413y += segmentLen * midy;
        }
    }
}
