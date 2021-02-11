package org.locationtech.jts.geom;

import java.io.Serializable;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.algorithm.HCoordinate;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.NotRepresentableException;
import org.locationtech.jts.algorithm.RobustLineIntersector;

public class LineSegment implements Comparable, Serializable {
    private static final long serialVersionUID = 3252005833466256227L;

    /* renamed from: p0 */
    public Coordinate f422p0;

    /* renamed from: p1 */
    public Coordinate f423p1;

    public LineSegment(Coordinate p0, Coordinate p1) {
        this.f422p0 = p0;
        this.f423p1 = p1;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LineSegment(double r20, double r22, double r24, double r26) {
        /*
            r19 = this;
            r1 = r19
            r2 = r20
            r4 = r22
            r6 = r24
            r8 = r26
            r10 = r1
            org.locationtech.jts.geom.Coordinate r11 = new org.locationtech.jts.geom.Coordinate
            r18 = r11
            r11 = r18
            r12 = r18
            r13 = r2
            r15 = r4
            r12.<init>(r13, r15)
            org.locationtech.jts.geom.Coordinate r12 = new org.locationtech.jts.geom.Coordinate
            r18 = r12
            r12 = r18
            r13 = r18
            r14 = r6
            r16 = r8
            r13.<init>(r14, r16)
            r10.<init>(r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.LineSegment.<init>(double, double, double, double):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LineSegment(org.locationtech.jts.geom.LineSegment r6) {
        /*
            r5 = this;
            r0 = r5
            r1 = r6
            r2 = r0
            r3 = r1
            org.locationtech.jts.geom.Coordinate r3 = r3.f422p0
            r4 = r1
            org.locationtech.jts.geom.Coordinate r4 = r4.f423p1
            r2.<init>(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.LineSegment.<init>(org.locationtech.jts.geom.LineSegment):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LineSegment() {
        /*
            r6 = this;
            r0 = r6
            r1 = r0
            org.locationtech.jts.geom.Coordinate r2 = new org.locationtech.jts.geom.Coordinate
            r5 = r2
            r2 = r5
            r3 = r5
            r3.<init>()
            org.locationtech.jts.geom.Coordinate r3 = new org.locationtech.jts.geom.Coordinate
            r5 = r3
            r3 = r5
            r4 = r5
            r4.<init>()
            r1.<init>(r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.LineSegment.<init>():void");
    }

    public Coordinate getCoordinate(int i) {
        if (i == 0) {
            return this.f422p0;
        }
        return this.f423p1;
    }

    public void setCoordinates(LineSegment lineSegment) {
        LineSegment ls = lineSegment;
        setCoordinates(ls.f422p0, ls.f423p1);
    }

    public void setCoordinates(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        this.f422p0.f412x = p0.f412x;
        this.f422p0.f413y = p0.f413y;
        this.f423p1.f412x = p1.f412x;
        this.f423p1.f413y = p1.f413y;
    }

    public double minX() {
        return Math.min(this.f422p0.f412x, this.f423p1.f412x);
    }

    public double maxX() {
        return Math.max(this.f422p0.f412x, this.f423p1.f412x);
    }

    public double minY() {
        return Math.min(this.f422p0.f413y, this.f423p1.f413y);
    }

    public double maxY() {
        return Math.max(this.f422p0.f413y, this.f423p1.f413y);
    }

    public double getLength() {
        return this.f422p0.distance(this.f423p1);
    }

    public boolean isHorizontal() {
        return this.f422p0.f413y == this.f423p1.f413y;
    }

    public boolean isVertical() {
        return this.f422p0.f412x == this.f423p1.f412x;
    }

    public int orientationIndex(LineSegment lineSegment) {
        LineSegment seg = lineSegment;
        int orient0 = CGAlgorithms.orientationIndex(this.f422p0, this.f423p1, seg.f422p0);
        int orient1 = CGAlgorithms.orientationIndex(this.f422p0, this.f423p1, seg.f423p1);
        if (orient0 >= 0 && orient1 >= 0) {
            return Math.max(orient0, orient1);
        }
        if (orient0 > 0 || orient1 > 0) {
            return 0;
        }
        return Math.max(orient0, orient1);
    }

    public int orientationIndex(Coordinate p) {
        return CGAlgorithms.orientationIndex(this.f422p0, this.f423p1, p);
    }

    public void reverse() {
        Coordinate temp = this.f422p0;
        this.f422p0 = this.f423p1;
        this.f423p1 = temp;
    }

    public void normalize() {
        if (this.f423p1.compareTo(this.f422p0) < 0) {
            reverse();
        }
    }

    public double angle() {
        return Math.atan2(this.f423p1.f413y - this.f422p0.f413y, this.f423p1.f412x - this.f422p0.f412x);
    }

    public Coordinate midPoint() {
        return midPoint(this.f422p0, this.f423p1);
    }

    public static Coordinate midPoint(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0;
        Coordinate p02 = coordinate;
        Coordinate p1 = coordinate2;
        new Coordinate((p02.f412x + p1.f412x) / 2.0d, (p02.f413y + p1.f413y) / 2.0d);
        return p0;
    }

    public double distance(LineSegment lineSegment) {
        LineSegment ls = lineSegment;
        return CGAlgorithms.distanceLineLine(this.f422p0, this.f423p1, ls.f422p0, ls.f423p1);
    }

    public double distance(Coordinate p) {
        return CGAlgorithms.distancePointLine(p, this.f422p0, this.f423p1);
    }

    public double distancePerpendicular(Coordinate p) {
        return CGAlgorithms.distancePointLinePerpendicular(p, this.f422p0, this.f423p1);
    }

    public Coordinate pointAlong(double d) {
        Coordinate coordinate;
        double segmentLengthFraction = d;
        new Coordinate();
        Coordinate coord = coordinate;
        coord.f412x = this.f422p0.f412x + (segmentLengthFraction * (this.f423p1.f412x - this.f422p0.f412x));
        coord.f413y = this.f422p0.f413y + (segmentLengthFraction * (this.f423p1.f413y - this.f422p0.f413y));
        return coord;
    }

    public Coordinate pointAlongOffset(double d, double d2) {
        Coordinate coord;
        Throwable th;
        double segmentLengthFraction = d;
        double offsetDistance = d2;
        double segx = this.f422p0.f412x + (segmentLengthFraction * (this.f423p1.f412x - this.f422p0.f412x));
        double segy = this.f422p0.f413y + (segmentLengthFraction * (this.f423p1.f413y - this.f422p0.f413y));
        double dx = this.f423p1.f412x - this.f422p0.f412x;
        double dy = this.f423p1.f413y - this.f422p0.f413y;
        double len = Math.sqrt((dx * dx) + (dy * dy));
        double ux = 0.0d;
        double uy = 0.0d;
        if (offsetDistance != 0.0d) {
            if (len <= 0.0d) {
                Throwable th2 = th;
                new IllegalStateException("Cannot compute offset from zero-length line segment");
                throw th2;
            }
            ux = (offsetDistance * dx) / len;
            uy = (offsetDistance * dy) / len;
        }
        new Coordinate(segx - uy, segy + ux);
        return coord;
    }

    public double projectionFactor(Coordinate coordinate) {
        Coordinate p = coordinate;
        if (p.equals(this.f422p0)) {
            return 0.0d;
        }
        if (p.equals(this.f423p1)) {
            return 1.0d;
        }
        double dx = this.f423p1.f412x - this.f422p0.f412x;
        double dy = this.f423p1.f413y - this.f422p0.f413y;
        double len = (dx * dx) + (dy * dy);
        if (len <= 0.0d) {
            return Double.NaN;
        }
        return (((p.f412x - this.f422p0.f412x) * dx) + ((p.f413y - this.f422p0.f413y) * dy)) / len;
    }

    public double segmentFraction(Coordinate inputPt) {
        double segFrac = projectionFactor(inputPt);
        if (segFrac < 0.0d) {
            segFrac = 0.0d;
        } else if (segFrac > 1.0d || Double.isNaN(segFrac)) {
            segFrac = 1.0d;
        }
        return segFrac;
    }

    public Coordinate project(Coordinate coordinate) {
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate p = coordinate;
        if (p.equals(this.f422p0) || p.equals(this.f423p1)) {
            new Coordinate(p);
            return coordinate2;
        }
        double r = projectionFactor(p);
        new Coordinate();
        Coordinate coord = coordinate3;
        coord.f412x = this.f422p0.f412x + (r * (this.f423p1.f412x - this.f422p0.f412x));
        coord.f413y = this.f422p0.f413y + (r * (this.f423p1.f413y - this.f422p0.f413y));
        return coord;
    }

    public LineSegment project(LineSegment lineSegment) {
        LineSegment lineSegment2;
        LineSegment seg = lineSegment;
        double pf0 = projectionFactor(seg.f422p0);
        double pf1 = projectionFactor(seg.f423p1);
        if (pf0 >= 1.0d && pf1 >= 1.0d) {
            return null;
        }
        if (pf0 <= 0.0d && pf1 <= 0.0d) {
            return null;
        }
        Coordinate newp0 = project(seg.f422p0);
        if (pf0 < 0.0d) {
            newp0 = this.f422p0;
        }
        if (pf0 > 1.0d) {
            newp0 = this.f423p1;
        }
        Coordinate newp1 = project(seg.f423p1);
        if (pf1 < 0.0d) {
            newp1 = this.f422p0;
        }
        if (pf1 > 1.0d) {
            newp1 = this.f423p1;
        }
        new LineSegment(newp0, newp1);
        return lineSegment2;
    }

    public Coordinate closestPoint(Coordinate coordinate) {
        Coordinate p = coordinate;
        double factor = projectionFactor(p);
        if (factor > 0.0d && factor < 1.0d) {
            return project(p);
        }
        if (this.f422p0.distance(p) < this.f423p1.distance(p)) {
            return this.f422p0;
        }
        return this.f423p1;
    }

    public Coordinate[] closestPoints(LineSegment lineSegment) {
        LineSegment line = lineSegment;
        Coordinate intPt = intersection(line);
        if (intPt != null) {
            Coordinate[] coordinateArr = new Coordinate[2];
            coordinateArr[0] = intPt;
            Coordinate[] coordinateArr2 = coordinateArr;
            coordinateArr2[1] = intPt;
            return coordinateArr2;
        }
        Coordinate close00 = closestPoint(line.f422p0);
        double minDistance = close00.distance(line.f422p0);
        Coordinate[] closestPt = {close00, line.f422p0};
        Coordinate close01 = closestPoint(line.f423p1);
        double dist = close01.distance(line.f423p1);
        if (dist < minDistance) {
            minDistance = dist;
            closestPt[0] = close01;
            closestPt[1] = line.f423p1;
        }
        Coordinate close10 = line.closestPoint(this.f422p0);
        double dist2 = close10.distance(this.f422p0);
        if (dist2 < minDistance) {
            minDistance = dist2;
            closestPt[0] = this.f422p0;
            closestPt[1] = close10;
        }
        Coordinate close11 = line.closestPoint(this.f423p1);
        double dist3 = close11.distance(this.f423p1);
        if (dist3 < minDistance) {
            double minDistance2 = dist3;
            closestPt[0] = this.f423p1;
            closestPt[1] = close11;
        }
        return closestPt;
    }

    public Coordinate intersection(LineSegment lineSegment) {
        LineIntersector lineIntersector;
        LineSegment line = lineSegment;
        new RobustLineIntersector();
        LineIntersector li = lineIntersector;
        li.computeIntersection(this.f422p0, this.f423p1, line.f422p0, line.f423p1);
        if (li.hasIntersection()) {
            return li.getIntersection(0);
        }
        return null;
    }

    public Coordinate lineIntersection(LineSegment lineSegment) {
        LineSegment line = lineSegment;
        try {
            return HCoordinate.intersection(this.f422p0, this.f423p1, line.f422p0, line.f423p1);
        } catch (NotRepresentableException e) {
            NotRepresentableException notRepresentableException = e;
            return null;
        }
    }

    public LineString toGeometry(GeometryFactory geomFactory) {
        Coordinate[] coordinateArr = new Coordinate[2];
        coordinateArr[0] = this.f422p0;
        Coordinate[] coordinateArr2 = coordinateArr;
        coordinateArr2[1] = this.f423p1;
        return geomFactory.createLineString(coordinateArr2);
    }

    public boolean equals(Object obj) {
        Object o = obj;
        if (!(o instanceof LineSegment)) {
            return false;
        }
        LineSegment other = (LineSegment) o;
        return this.f422p0.equals(other.f422p0) && this.f423p1.equals(other.f423p1);
    }

    public int hashCode() {
        long bits0 = Double.doubleToLongBits(this.f422p0.f412x) ^ (Double.doubleToLongBits(this.f422p0.f413y) * 31);
        int hash0 = ((int) bits0) ^ ((int) (bits0 >> 32));
        long bits1 = Double.doubleToLongBits(this.f423p1.f412x) ^ (Double.doubleToLongBits(this.f423p1.f413y) * 31);
        return hash0 ^ (((int) bits1) ^ ((int) (bits1 >> 32)));
    }

    public int compareTo(Object o) {
        LineSegment other = (LineSegment) o;
        int comp0 = this.f422p0.compareTo(other.f422p0);
        if (comp0 != 0) {
            return comp0;
        }
        return this.f423p1.compareTo(other.f423p1);
    }

    public boolean equalsTopo(LineSegment lineSegment) {
        LineSegment other = lineSegment;
        return (this.f422p0.equals(other.f422p0) && this.f423p1.equals(other.f423p1)) || (this.f422p0.equals(other.f423p1) && this.f423p1.equals(other.f422p0));
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("LINESTRING( ").append(this.f422p0.f412x).append(" ").append(this.f422p0.f413y).append(", ").append(this.f423p1.f412x).append(" ").append(this.f423p1.f413y).append(")").toString();
    }
}
