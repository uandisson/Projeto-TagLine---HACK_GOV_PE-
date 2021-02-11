package org.locationtech.jts.triangulate;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineSegment;

public class Segment {
    private Object data;

    /* renamed from: ls */
    private LineSegment f511ls;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Segment(double r26, double r28, double r30, double r32, double r34, double r36) {
        /*
            r25 = this;
            r1 = r25
            r2 = r26
            r4 = r28
            r6 = r30
            r8 = r32
            r10 = r34
            r12 = r36
            r14 = r1
            org.locationtech.jts.geom.Coordinate r15 = new org.locationtech.jts.geom.Coordinate
            r24 = r15
            r15 = r24
            r16 = r24
            r17 = r2
            r19 = r4
            r21 = r6
            r16.<init>(r17, r19, r21)
            org.locationtech.jts.geom.Coordinate r16 = new org.locationtech.jts.geom.Coordinate
            r24 = r16
            r16 = r24
            r17 = r24
            r18 = r8
            r20 = r10
            r22 = r12
            r17.<init>(r18, r20, r22)
            r14.<init>(r15, r16)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.triangulate.Segment.<init>(double, double, double, double, double, double):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Segment(double r28, double r30, double r32, double r34, double r36, double r38, java.lang.Object r40) {
        /*
            r27 = this;
            r1 = r27
            r2 = r28
            r4 = r30
            r6 = r32
            r8 = r34
            r10 = r36
            r12 = r38
            r14 = r40
            r15 = r1
            org.locationtech.jts.geom.Coordinate r16 = new org.locationtech.jts.geom.Coordinate
            r25 = r16
            r16 = r25
            r17 = r25
            r18 = r2
            r20 = r4
            r22 = r6
            r17.<init>(r18, r20, r22)
            org.locationtech.jts.geom.Coordinate r17 = new org.locationtech.jts.geom.Coordinate
            r25 = r17
            r17 = r25
            r18 = r25
            r19 = r8
            r21 = r10
            r23 = r12
            r18.<init>(r19, r21, r23)
            r18 = r14
            r15.<init>(r16, r17, r18)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.triangulate.Segment.<init>(double, double, double, double, double, double, java.lang.Object):void");
    }

    public Segment(Coordinate p0, Coordinate p1, Object data2) {
        LineSegment lineSegment;
        this.data = null;
        new LineSegment(p0, p1);
        this.f511ls = lineSegment;
        this.data = data2;
    }

    public Segment(Coordinate p0, Coordinate p1) {
        LineSegment lineSegment;
        this.data = null;
        new LineSegment(p0, p1);
        this.f511ls = lineSegment;
    }

    public Coordinate getStart() {
        return this.f511ls.getCoordinate(0);
    }

    public Coordinate getEnd() {
        return this.f511ls.getCoordinate(1);
    }

    public double getStartX() {
        return this.f511ls.getCoordinate(0).f412x;
    }

    public double getStartY() {
        return this.f511ls.getCoordinate(0).f413y;
    }

    public double getStartZ() {
        return this.f511ls.getCoordinate(0).f414z;
    }

    public double getEndX() {
        return this.f511ls.getCoordinate(1).f412x;
    }

    public double getEndY() {
        return this.f511ls.getCoordinate(1).f413y;
    }

    public double getEndZ() {
        return this.f511ls.getCoordinate(1).f414z;
    }

    public LineSegment getLineSegment() {
        return this.f511ls;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data2) {
        Object obj = data2;
        this.data = obj;
    }

    public boolean equalsTopo(Segment s) {
        return this.f511ls.equalsTopo(s.getLineSegment());
    }

    public Coordinate intersection(Segment s) {
        return this.f511ls.intersection(s.getLineSegment());
    }

    public String toString() {
        return this.f511ls.toString();
    }
}
