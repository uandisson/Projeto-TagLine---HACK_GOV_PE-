package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class Centroid {
    private Coordinate areaBasePt = null;
    private double areasum2;
    private Coordinate cg3;
    private Coordinate lineCentSum;
    private Coordinate ptCentSum;
    private int ptCount;
    private double totalLength;
    private Coordinate triangleCent3;

    public static Coordinate getCentroid(Geometry geom) {
        Centroid cent;
        new Centroid(geom);
        return cent.getCentroid();
    }

    public Centroid(Geometry geom) {
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        new Coordinate();
        this.triangleCent3 = coordinate;
        this.areasum2 = 0.0d;
        new Coordinate();
        this.cg3 = coordinate2;
        new Coordinate();
        this.lineCentSum = coordinate3;
        this.totalLength = 0.0d;
        this.ptCount = 0;
        new Coordinate();
        this.ptCentSum = coordinate4;
        this.areaBasePt = null;
        add(geom);
    }

    private void add(Geometry geometry) {
        Geometry geom = geometry;
        if (!geom.isEmpty()) {
            if (geom instanceof Point) {
                addPoint(geom.getCoordinate());
            } else if (geom instanceof LineString) {
                addLineSegments(geom.getCoordinates());
            } else if (geom instanceof Polygon) {
                add((Polygon) geom);
            } else if (geom instanceof GeometryCollection) {
                GeometryCollection gc = (GeometryCollection) geom;
                for (int i = 0; i < gc.getNumGeometries(); i++) {
                    add(gc.getGeometryN(i));
                }
            }
        }
    }

    public Coordinate getCentroid() {
        Coordinate coordinate;
        new Coordinate();
        Coordinate cent = coordinate;
        if (Math.abs(this.areasum2) > 0.0d) {
            cent.f412x = (this.cg3.f412x / 3.0d) / this.areasum2;
            cent.f413y = (this.cg3.f413y / 3.0d) / this.areasum2;
        } else if (this.totalLength > 0.0d) {
            cent.f412x = this.lineCentSum.f412x / this.totalLength;
            cent.f413y = this.lineCentSum.f413y / this.totalLength;
        } else if (this.ptCount <= 0) {
            return null;
        } else {
            cent.f412x = this.ptCentSum.f412x / ((double) this.ptCount);
            cent.f413y = this.ptCentSum.f413y / ((double) this.ptCount);
        }
        return cent;
    }

    private void setAreaBasePoint(Coordinate basePt) {
        Coordinate coordinate = basePt;
        this.areaBasePt = coordinate;
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
        if (pts.length > 0) {
            setAreaBasePoint(pts[0]);
        }
        boolean isPositiveArea = !CGAlgorithms.isCCW(pts);
        for (int i = 0; i < pts.length - 1; i++) {
            addTriangle(this.areaBasePt, pts[i], pts[i + 1], isPositiveArea);
        }
        addLineSegments(pts);
    }

    private void addHole(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        boolean isPositiveArea = CGAlgorithms.isCCW(pts);
        for (int i = 0; i < pts.length - 1; i++) {
            addTriangle(this.areaBasePt, pts[i], pts[i + 1], isPositiveArea);
        }
        addLineSegments(pts);
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

    private void addLineSegments(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        double lineLen = 0.0d;
        for (int i = 0; i < pts.length - 1; i++) {
            double segmentLen = pts[i].distance(pts[i + 1]);
            if (segmentLen != 0.0d) {
                lineLen += segmentLen;
                double midx = (pts[i].f412x + pts[i + 1].f412x) / 2.0d;
                this.lineCentSum.f412x += segmentLen * midx;
                double midy = (pts[i].f413y + pts[i + 1].f413y) / 2.0d;
                this.lineCentSum.f413y += segmentLen * midy;
            }
        }
        this.totalLength += lineLen;
        if (lineLen == 0.0d && pts.length > 0) {
            addPoint(pts[0]);
        }
    }

    private void addPoint(Coordinate coordinate) {
        Coordinate pt = coordinate;
        this.ptCount++;
        this.ptCentSum.f412x += pt.f412x;
        this.ptCentSum.f413y += pt.f413y;
    }
}
