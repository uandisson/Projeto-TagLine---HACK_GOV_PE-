package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;

public class HCoordinate {

    /* renamed from: w */
    public double f398w;

    /* renamed from: x */
    public double f399x;

    /* renamed from: y */
    public double f400y;

    public static Coordinate intersection(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) throws NotRepresentableException {
        Throwable th;
        Coordinate p1;
        Coordinate p12 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q1 = coordinate3;
        Coordinate q2 = coordinate4;
        double px = p12.f413y - p2.f413y;
        double py = p2.f412x - p12.f412x;
        double pw = (p12.f412x * p2.f413y) - (p2.f412x * p12.f413y);
        double qx = q1.f413y - q2.f413y;
        double qy = q2.f412x - q1.f412x;
        double qw = (q1.f412x * q2.f413y) - (q2.f412x * q1.f413y);
        double w = (px * qy) - (qx * py);
        double xInt = ((py * qw) - (qy * pw)) / w;
        double yInt = ((qx * pw) - (px * qw)) / w;
        if (Double.isNaN(xInt) || Double.isInfinite(xInt) || Double.isNaN(yInt) || Double.isInfinite(yInt)) {
            Throwable th2 = th;
            new NotRepresentableException();
            throw th2;
        }
        new Coordinate(xInt, yInt);
        return p1;
    }

    public HCoordinate() {
        this.f399x = 0.0d;
        this.f400y = 0.0d;
        this.f398w = 1.0d;
    }

    public HCoordinate(double _x, double _y, double _w) {
        this.f399x = _x;
        this.f400y = _y;
        this.f398w = _w;
    }

    public HCoordinate(double _x, double _y) {
        this.f399x = _x;
        this.f400y = _y;
        this.f398w = 1.0d;
    }

    public HCoordinate(Coordinate coordinate) {
        Coordinate p = coordinate;
        this.f399x = p.f412x;
        this.f400y = p.f413y;
        this.f398w = 1.0d;
    }

    public HCoordinate(HCoordinate hCoordinate, HCoordinate hCoordinate2) {
        HCoordinate p1 = hCoordinate;
        HCoordinate p2 = hCoordinate2;
        this.f399x = (p1.f400y * p2.f398w) - (p2.f400y * p1.f398w);
        this.f400y = (p2.f399x * p1.f398w) - (p1.f399x * p2.f398w);
        this.f398w = (p1.f399x * p2.f400y) - (p2.f399x * p1.f400y);
    }

    public HCoordinate(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        this.f399x = p1.f413y - p2.f413y;
        this.f400y = p2.f412x - p1.f412x;
        this.f398w = (p1.f412x * p2.f413y) - (p2.f412x * p1.f413y);
    }

    public HCoordinate(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q1 = coordinate3;
        Coordinate q2 = coordinate4;
        double px = p1.f413y - p2.f413y;
        double py = p2.f412x - p1.f412x;
        double pw = (p1.f412x * p2.f413y) - (p2.f412x * p1.f413y);
        double qx = q1.f413y - q2.f413y;
        double qy = q2.f412x - q1.f412x;
        double qw = (q1.f412x * q2.f413y) - (q2.f412x * q1.f413y);
        this.f399x = (py * qw) - (qy * pw);
        this.f400y = (qx * pw) - (px * qw);
        this.f398w = (px * qy) - (qx * py);
    }

    public double getX() throws NotRepresentableException {
        Throwable th;
        double a = this.f399x / this.f398w;
        if (!Double.isNaN(a) && !Double.isInfinite(a)) {
            return a;
        }
        Throwable th2 = th;
        new NotRepresentableException();
        throw th2;
    }

    public double getY() throws NotRepresentableException {
        Throwable th;
        double a = this.f400y / this.f398w;
        if (!Double.isNaN(a) && !Double.isInfinite(a)) {
            return a;
        }
        Throwable th2 = th;
        new NotRepresentableException();
        throw th2;
    }

    public Coordinate getCoordinate() throws NotRepresentableException {
        Coordinate coordinate;
        new Coordinate();
        Coordinate p = coordinate;
        p.f412x = getX();
        p.f413y = getY();
        return p;
    }
}
