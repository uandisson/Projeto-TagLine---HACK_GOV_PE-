package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Triangle;
import org.locationtech.jts.util.Assert;

public class MinimumBoundingCircle {
    private Coordinate centre = null;
    private Coordinate[] extremalPts = null;
    private Geometry input;
    private double radius = 0.0d;

    public MinimumBoundingCircle(Geometry geom) {
        this.input = geom;
    }

    public Geometry getCircle() {
        compute();
        if (this.centre == null) {
            return this.input.getFactory().createPolygon((LinearRing) null, (LinearRing[]) null);
        }
        Point centrePoint = this.input.getFactory().createPoint(this.centre);
        if (this.radius == 0.0d) {
            return centrePoint;
        }
        return centrePoint.buffer(this.radius);
    }

    public Geometry getFarthestPoints() {
        compute();
        switch (this.extremalPts.length) {
            case 0:
                return this.input.getFactory().createLineString((CoordinateSequence) null);
            case 1:
                return this.input.getFactory().createPoint(this.centre);
            default:
                Coordinate p0 = this.extremalPts[0];
                Coordinate p1 = this.extremalPts[this.extremalPts.length - 1];
                GeometryFactory factory = this.input.getFactory();
                Coordinate[] coordinateArr = new Coordinate[2];
                coordinateArr[0] = p0;
                Coordinate[] coordinateArr2 = coordinateArr;
                coordinateArr2[1] = p1;
                return factory.createLineString(coordinateArr2);
        }
    }

    public Geometry getDiameter() {
        compute();
        switch (this.extremalPts.length) {
            case 0:
                return this.input.getFactory().createLineString((CoordinateSequence) null);
            case 1:
                return this.input.getFactory().createPoint(this.centre);
            default:
                Coordinate p0 = this.extremalPts[0];
                Coordinate p1 = this.extremalPts[1];
                GeometryFactory factory = this.input.getFactory();
                Coordinate[] coordinateArr = new Coordinate[2];
                coordinateArr[0] = p0;
                Coordinate[] coordinateArr2 = coordinateArr;
                coordinateArr2[1] = p1;
                return factory.createLineString(coordinateArr2);
        }
    }

    public Coordinate[] getExtremalPoints() {
        compute();
        return this.extremalPts;
    }

    public Coordinate getCentre() {
        compute();
        return this.centre;
    }

    public double getRadius() {
        compute();
        return this.radius;
    }

    private void computeCentre() {
        Coordinate coordinate;
        switch (this.extremalPts.length) {
            case 0:
                this.centre = null;
                return;
            case 1:
                this.centre = this.extremalPts[0];
                return;
            case 2:
                new Coordinate((this.extremalPts[0].f412x + this.extremalPts[1].f412x) / 2.0d, (this.extremalPts[0].f413y + this.extremalPts[1].f413y) / 2.0d);
                this.centre = coordinate;
                return;
            case 3:
                this.centre = Triangle.circumcentre(this.extremalPts[0], this.extremalPts[1], this.extremalPts[2]);
                return;
            default:
                return;
        }
    }

    private void compute() {
        if (this.extremalPts == null) {
            computeCirclePoints();
            computeCentre();
            if (this.centre != null) {
                this.radius = this.centre.distance(this.extremalPts[0]);
            }
        }
    }

    private void computeCirclePoints() {
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        Coordinate coordinate5;
        Coordinate coordinate6;
        if (this.input.isEmpty()) {
            this.extremalPts = new Coordinate[0];
        } else if (this.input.getNumPoints() == 1) {
            Coordinate[] coordinateArr = new Coordinate[1];
            new Coordinate(this.input.getCoordinates()[0]);
            coordinateArr[0] = coordinate6;
            this.extremalPts = coordinateArr;
        } else {
            Coordinate[] hullPts = this.input.convexHull().getCoordinates();
            Coordinate[] pts = hullPts;
            if (hullPts[0].equals2D(hullPts[hullPts.length - 1])) {
                pts = new Coordinate[(hullPts.length - 1)];
                CoordinateArrays.copyDeep(hullPts, 0, pts, 0, hullPts.length - 1);
            }
            if (pts.length <= 2) {
                this.extremalPts = CoordinateArrays.copyDeep(pts);
                return;
            }
            Coordinate P = lowestPoint(pts);
            Coordinate Q = pointWitMinAngleWithX(pts, P);
            for (int i = 0; i < pts.length; i++) {
                Coordinate R = pointWithMinAngleWithSegment(pts, P, Q);
                if (Angle.isObtuse(P, R, Q)) {
                    Coordinate[] coordinateArr2 = new Coordinate[2];
                    new Coordinate(P);
                    coordinateArr2[0] = coordinate;
                    Coordinate[] coordinateArr3 = coordinateArr2;
                    new Coordinate(Q);
                    coordinateArr3[1] = coordinate2;
                    this.extremalPts = coordinateArr3;
                    return;
                }
                if (Angle.isObtuse(R, P, Q)) {
                    P = R;
                } else if (Angle.isObtuse(R, Q, P)) {
                    Q = R;
                } else {
                    Coordinate[] coordinateArr4 = new Coordinate[3];
                    new Coordinate(P);
                    coordinateArr4[0] = coordinate3;
                    Coordinate[] coordinateArr5 = coordinateArr4;
                    new Coordinate(Q);
                    coordinateArr5[1] = coordinate4;
                    Coordinate[] coordinateArr6 = coordinateArr5;
                    new Coordinate(R);
                    coordinateArr6[2] = coordinate5;
                    this.extremalPts = coordinateArr6;
                    return;
                }
            }
            Assert.shouldNeverReachHere("Logic failure in Minimum Bounding Circle algorithm!");
        }
    }

    private static Coordinate lowestPoint(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        Coordinate min = pts[0];
        for (int i = 1; i < pts.length; i++) {
            if (pts[i].f413y < min.f413y) {
                min = pts[i];
            }
        }
        return min;
    }

    private static Coordinate pointWitMinAngleWithX(Coordinate[] coordinateArr, Coordinate coordinate) {
        Coordinate[] pts = coordinateArr;
        Coordinate P = coordinate;
        double minSin = Double.MAX_VALUE;
        Coordinate minAngPt = null;
        for (int i = 0; i < pts.length; i++) {
            Coordinate p = pts[i];
            if (p != P) {
                double dx = p.f412x - P.f412x;
                double dy = p.f413y - P.f413y;
                if (dy < 0.0d) {
                    dy = -dy;
                }
                double sin = dy / Math.sqrt((dx * dx) + (dy * dy));
                if (sin < minSin) {
                    minSin = sin;
                    minAngPt = p;
                }
            }
        }
        return minAngPt;
    }

    private static Coordinate pointWithMinAngleWithSegment(Coordinate[] coordinateArr, Coordinate coordinate, Coordinate coordinate2) {
        Coordinate[] pts = coordinateArr;
        Coordinate P = coordinate;
        Coordinate Q = coordinate2;
        double minAng = Double.MAX_VALUE;
        Coordinate minAngPt = null;
        for (int i = 0; i < pts.length; i++) {
            Coordinate p = pts[i];
            if (!(p == P || p == Q)) {
                double ang = Angle.angleBetween(P, p, Q);
                if (ang < minAng) {
                    minAng = ang;
                    minAngPt = p;
                }
            }
        }
        return minAngPt;
    }
}
