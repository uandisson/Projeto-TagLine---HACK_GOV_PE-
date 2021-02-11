package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.Polygon;

public class CentroidArea {
    private double areasum2;
    private Coordinate basePt = null;
    private Coordinate centSum;
    private Coordinate cg3;
    private double totalLength;
    private Coordinate triangleCent3;

    public CentroidArea() {
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate coordinate3;
        new Coordinate();
        this.triangleCent3 = coordinate;
        this.areasum2 = 0.0d;
        new Coordinate();
        this.cg3 = coordinate2;
        new Coordinate();
        this.centSum = coordinate3;
        this.totalLength = 0.0d;
        this.basePt = null;
    }

    public void add(Geometry geometry) {
        Geometry geom = geometry;
        if (geom instanceof Polygon) {
            Polygon poly = (Polygon) geom;
            setBasePoint(poly.getExteriorRing().getCoordinateN(0));
            add(poly);
        } else if (geom instanceof GeometryCollection) {
            GeometryCollection gc = (GeometryCollection) geom;
            for (int i = 0; i < gc.getNumGeometries(); i++) {
                add(gc.getGeometryN(i));
            }
        }
    }

    public void add(Coordinate[] coordinateArr) {
        Coordinate[] ring = coordinateArr;
        setBasePoint(ring[0]);
        addShell(ring);
    }

    public Coordinate getCentroid() {
        Coordinate coordinate;
        new Coordinate();
        Coordinate cent = coordinate;
        if (Math.abs(this.areasum2) > 0.0d) {
            cent.f412x = (this.cg3.f412x / 3.0d) / this.areasum2;
            cent.f413y = (this.cg3.f413y / 3.0d) / this.areasum2;
        } else {
            cent.f412x = this.centSum.f412x / this.totalLength;
            cent.f413y = this.centSum.f413y / this.totalLength;
        }
        return cent;
    }

    private void setBasePoint(Coordinate coordinate) {
        Coordinate basePt2 = coordinate;
        if (this.basePt == null) {
            this.basePt = basePt2;
        }
    }

    private void add(Polygon polygon) {
        Polygon poly = polygon;
        addShell(poly.getExteriorRing().getCoordinates());
        for (int i = 0; i < poly.getNumInteriorRing(); i++) {
            addHole(poly.getInteriorRingN(i).getCoordinates());
        }
    }

    private void addShell(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        boolean isPositiveArea = !CGAlgorithms.isCCW(pts);
        for (int i = 0; i < pts.length - 1; i++) {
            addTriangle(this.basePt, pts[i], pts[i + 1], isPositiveArea);
        }
        addLinearSegments(pts);
    }

    private void addHole(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        boolean isPositiveArea = CGAlgorithms.isCCW(pts);
        for (int i = 0; i < pts.length - 1; i++) {
            addTriangle(this.basePt, pts[i], pts[i + 1], isPositiveArea);
        }
        addLinearSegments(pts);
    }

    private void addTriangle(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, boolean isPositiveArea) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        Coordinate p2 = coordinate3;
        double sign = isPositiveArea ? 1.0d : -1.0d;
        centroid3(p0, p1, p2, this.triangleCent3);
        double area2 = area2(p0, p1, p2);
        this.cg3.f412x += sign * area2 * this.triangleCent3.f412x;
        this.cg3.f413y += sign * area2 * this.triangleCent3.f413y;
        this.areasum2 += sign * area2;
    }

    private static void centroid3(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate p3 = coordinate3;
        Coordinate c = coordinate4;
        c.f412x = p1.f412x + p2.f412x + p3.f412x;
        c.f413y = p1.f413y + p2.f413y + p3.f413y;
    }

    private static double area2(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate p3 = coordinate3;
        return ((p2.f412x - p1.f412x) * (p3.f413y - p1.f413y)) - ((p3.f412x - p1.f412x) * (p2.f413y - p1.f413y));
    }

    private void addLinearSegments(Coordinate[] coordinateArr) {
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
