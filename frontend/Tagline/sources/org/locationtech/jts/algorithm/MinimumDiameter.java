package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

public class MinimumDiameter {
    private Coordinate[] convexHullPts;
    private final Geometry inputGeom;
    private final boolean isConvex;
    private LineSegment minBaseSeg;
    private int minPtIndex;
    private double minWidth;
    private Coordinate minWidthPt;

    public static Geometry getMinimumRectangle(Geometry geom) {
        MinimumDiameter minimumDiameter;
        new MinimumDiameter(geom);
        return minimumDiameter.getMinimumRectangle();
    }

    public static Geometry getMinimumDiameter(Geometry geom) {
        MinimumDiameter minimumDiameter;
        new MinimumDiameter(geom);
        return minimumDiameter.getDiameter();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MinimumDiameter(Geometry inputGeom2) {
        this(inputGeom2, false);
    }

    public MinimumDiameter(Geometry inputGeom2, boolean isConvex2) {
        LineSegment lineSegment;
        this.convexHullPts = null;
        new LineSegment();
        this.minBaseSeg = lineSegment;
        this.minWidthPt = null;
        this.minWidth = 0.0d;
        this.inputGeom = inputGeom2;
        this.isConvex = isConvex2;
    }

    public double getLength() {
        computeMinimumDiameter();
        return this.minWidth;
    }

    public Coordinate getWidthCoordinate() {
        computeMinimumDiameter();
        return this.minWidthPt;
    }

    public LineString getSupportingSegment() {
        computeMinimumDiameter();
        GeometryFactory factory = this.inputGeom.getFactory();
        Coordinate[] coordinateArr = new Coordinate[2];
        coordinateArr[0] = this.minBaseSeg.f422p0;
        Coordinate[] coordinateArr2 = coordinateArr;
        coordinateArr2[1] = this.minBaseSeg.f423p1;
        return factory.createLineString(coordinateArr2);
    }

    public LineString getDiameter() {
        computeMinimumDiameter();
        if (this.minWidthPt == null) {
            return this.inputGeom.getFactory().createLineString((Coordinate[]) null);
        }
        Coordinate basePt = this.minBaseSeg.project(this.minWidthPt);
        GeometryFactory factory = this.inputGeom.getFactory();
        Coordinate[] coordinateArr = new Coordinate[2];
        coordinateArr[0] = basePt;
        Coordinate[] coordinateArr2 = coordinateArr;
        coordinateArr2[1] = this.minWidthPt;
        return factory.createLineString(coordinateArr2);
    }

    private void computeMinimumDiameter() {
        ConvexHull convexHull;
        if (this.minWidthPt == null) {
            if (this.isConvex) {
                computeWidthConvex(this.inputGeom);
                return;
            }
            new ConvexHull(this.inputGeom);
            computeWidthConvex(convexHull.getConvexHull());
        }
    }

    private void computeWidthConvex(Geometry geometry) {
        Geometry convexGeom = geometry;
        if (convexGeom instanceof Polygon) {
            this.convexHullPts = ((Polygon) convexGeom).getExteriorRing().getCoordinates();
        } else {
            this.convexHullPts = convexGeom.getCoordinates();
        }
        if (this.convexHullPts.length == 0) {
            this.minWidth = 0.0d;
            this.minWidthPt = null;
            this.minBaseSeg = null;
        } else if (this.convexHullPts.length == 1) {
            this.minWidth = 0.0d;
            this.minWidthPt = this.convexHullPts[0];
            this.minBaseSeg.f422p0 = this.convexHullPts[0];
            this.minBaseSeg.f423p1 = this.convexHullPts[0];
        } else if (this.convexHullPts.length == 2 || this.convexHullPts.length == 3) {
            this.minWidth = 0.0d;
            this.minWidthPt = this.convexHullPts[0];
            this.minBaseSeg.f422p0 = this.convexHullPts[0];
            this.minBaseSeg.f423p1 = this.convexHullPts[1];
        } else {
            computeConvexRingMinDiameter(this.convexHullPts);
        }
    }

    private void computeConvexRingMinDiameter(Coordinate[] coordinateArr) {
        LineSegment lineSegment;
        Coordinate[] pts = coordinateArr;
        this.minWidth = Double.MAX_VALUE;
        int currMaxIndex = 1;
        new LineSegment();
        LineSegment seg = lineSegment;
        for (int i = 0; i < pts.length - 1; i++) {
            seg.f422p0 = pts[i];
            seg.f423p1 = pts[i + 1];
            currMaxIndex = findMaxPerpDistance(pts, seg, currMaxIndex);
        }
    }

    private int findMaxPerpDistance(Coordinate[] coordinateArr, LineSegment lineSegment, int i) {
        LineSegment lineSegment2;
        Coordinate[] pts = coordinateArr;
        LineSegment seg = lineSegment;
        int startIndex = i;
        double maxPerpDistance = seg.distancePerpendicular(pts[startIndex]);
        double nextPerpDistance = maxPerpDistance;
        int maxIndex = startIndex;
        int nextIndex = maxIndex;
        while (nextPerpDistance >= maxPerpDistance) {
            maxPerpDistance = nextPerpDistance;
            maxIndex = nextIndex;
            nextIndex = nextIndex(pts, maxIndex);
            nextPerpDistance = seg.distancePerpendicular(pts[nextIndex]);
        }
        if (maxPerpDistance < this.minWidth) {
            this.minPtIndex = maxIndex;
            this.minWidth = maxPerpDistance;
            this.minWidthPt = pts[this.minPtIndex];
            new LineSegment(seg);
            this.minBaseSeg = lineSegment2;
        }
        return maxIndex;
    }

    private static int nextIndex(Coordinate[] pts, int index) {
        int index2 = index + 1;
        if (index2 >= pts.length) {
            index2 = 0;
        }
        return index2;
    }

    public Geometry getMinimumRectangle() {
        computeMinimumDiameter();
        if (this.minWidth == 0.0d) {
            if (this.minBaseSeg.f422p0.equals2D(this.minBaseSeg.f423p1)) {
                return this.inputGeom.getFactory().createPoint(this.minBaseSeg.f422p0);
            }
            return this.minBaseSeg.toGeometry(this.inputGeom.getFactory());
        }
        double dx = this.minBaseSeg.f423p1.f412x - this.minBaseSeg.f422p0.f412x;
        double dy = this.minBaseSeg.f423p1.f413y - this.minBaseSeg.f422p0.f413y;
        double minPara = Double.MAX_VALUE;
        double maxPara = -1.7976931348623157E308d;
        double minPerp = Double.MAX_VALUE;
        double maxPerp = -1.7976931348623157E308d;
        int i = 0;
        while (true) {
            if (i < this.convexHullPts.length) {
                double paraC = computeC(dx, dy, this.convexHullPts[i]);
                if (paraC > maxPara) {
                    maxPara = paraC;
                }
                if (paraC < minPara) {
                    minPara = paraC;
                }
                double perpC = computeC(-dy, dx, this.convexHullPts[i]);
                if (perpC > maxPerp) {
                    maxPerp = perpC;
                }
                if (perpC < minPerp) {
                    minPerp = perpC;
                }
                i++;
            } else {
                LineSegment maxPerpLine = computeSegmentForLine(-dx, -dy, maxPerp);
                LineSegment minPerpLine = computeSegmentForLine(-dx, -dy, minPerp);
                LineSegment maxParaLine = computeSegmentForLine(-dy, dx, maxPara);
                LineSegment minParaLine = computeSegmentForLine(-dy, dx, minPara);
                Coordinate p0 = maxParaLine.lineIntersection(maxPerpLine);
                Coordinate p1 = minParaLine.lineIntersection(maxPerpLine);
                Coordinate p2 = minParaLine.lineIntersection(minPerpLine);
                Coordinate p3 = maxParaLine.lineIntersection(minPerpLine);
                GeometryFactory factory = this.inputGeom.getFactory();
                Coordinate[] coordinateArr = new Coordinate[5];
                coordinateArr[0] = p0;
                Coordinate[] coordinateArr2 = coordinateArr;
                coordinateArr2[1] = p1;
                Coordinate[] coordinateArr3 = coordinateArr2;
                coordinateArr3[2] = p2;
                Coordinate[] coordinateArr4 = coordinateArr3;
                coordinateArr4[3] = p3;
                Coordinate[] coordinateArr5 = coordinateArr4;
                coordinateArr5[4] = p0;
                return this.inputGeom.getFactory().createPolygon(factory.createLinearRing(coordinateArr5), (LinearRing[]) null);
            }
        }
    }

    private static double computeC(double a, double b, Coordinate coordinate) {
        Coordinate p = coordinate;
        return (a * p.f413y) - (b * p.f412x);
    }

    private static LineSegment computeSegmentForLine(double d, double d2, double d3) {
        Coordinate coordinate;
        Coordinate p0;
        Coordinate coordinate2;
        Coordinate p1;
        LineSegment lineSegment;
        Coordinate coordinate3;
        Coordinate coordinate4;
        double a = d;
        double b = d2;
        double c = d3;
        if (Math.abs(b) > Math.abs(a)) {
            new Coordinate(0.0d, c / b);
            p0 = coordinate3;
            new Coordinate(1.0d, (c / b) - (a / b));
            p1 = coordinate4;
        } else {
            new Coordinate(c / a, 0.0d);
            p0 = coordinate;
            new Coordinate((c / a) - (b / a), 1.0d);
            p1 = coordinate2;
        }
        new LineSegment(p0, p1);
        return lineSegment;
    }
}
