package org.locationtech.jts.math;

import org.locationtech.jts.algorithm.Angle;
import org.locationtech.jts.algorithm.RobustDeterminant;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.util.Assert;

public class Vector2D {

    /* renamed from: x */
    private double f455x;

    /* renamed from: y */
    private double f456y;

    public static Vector2D create(double x, double y) {
        Vector2D vector2D;
        new Vector2D(x, y);
        return vector2D;
    }

    public static Vector2D create(Vector2D v) {
        Vector2D v2;
        new Vector2D(v);
        return v2;
    }

    public static Vector2D create(Coordinate coord) {
        Vector2D vector2D;
        new Vector2D(coord);
        return vector2D;
    }

    public static Vector2D create(Coordinate from, Coordinate to) {
        Vector2D vector2D;
        new Vector2D(from, to);
        return vector2D;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public Vector2D() {
        this(0.0d, 0.0d);
    }

    public Vector2D(double x, double y) {
        this.f455x = x;
        this.f456y = y;
    }

    public Vector2D(Vector2D vector2D) {
        Vector2D v = vector2D;
        this.f455x = v.f455x;
        this.f456y = v.f456y;
    }

    public Vector2D(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate from = coordinate;
        Coordinate to = coordinate2;
        this.f455x = to.f412x - from.f412x;
        this.f456y = to.f413y - from.f413y;
    }

    public Vector2D(Coordinate coordinate) {
        Coordinate v = coordinate;
        this.f455x = v.f412x;
        this.f456y = v.f413y;
    }

    public double getX() {
        return this.f455x;
    }

    public double getY() {
        return this.f456y;
    }

    public double getComponent(int index) {
        if (index == 0) {
            return this.f455x;
        }
        return this.f456y;
    }

    public Vector2D add(Vector2D vector2D) {
        Vector2D v = vector2D;
        return create(this.f455x + v.f455x, this.f456y + v.f456y);
    }

    public Vector2D subtract(Vector2D vector2D) {
        Vector2D v = vector2D;
        return create(this.f455x - v.f455x, this.f456y - v.f456y);
    }

    public Vector2D multiply(double d) {
        double d2 = d;
        return create(this.f455x * d2, this.f456y * d2);
    }

    public Vector2D divide(double d) {
        double d2 = d;
        return create(this.f455x / d2, this.f456y / d2);
    }

    public Vector2D negate() {
        return create(-this.f455x, -this.f456y);
    }

    public double length() {
        return Math.sqrt((this.f455x * this.f455x) + (this.f456y * this.f456y));
    }

    public double lengthSquared() {
        return (this.f455x * this.f455x) + (this.f456y * this.f456y);
    }

    public Vector2D normalize() {
        double length = length();
        if (length > 0.0d) {
            return divide(length);
        }
        return create(0.0d, 0.0d);
    }

    public Vector2D average(Vector2D v) {
        return weightedSum(v, 0.5d);
    }

    public Vector2D weightedSum(Vector2D vector2D, double d) {
        Vector2D v = vector2D;
        double frac = d;
        return create((frac * this.f455x) + ((1.0d - frac) * v.f455x), (frac * this.f456y) + ((1.0d - frac) * v.f456y));
    }

    public double distance(Vector2D vector2D) {
        Vector2D v = vector2D;
        double delx = v.f455x - this.f455x;
        double dely = v.f456y - this.f456y;
        return Math.sqrt((delx * delx) + (dely * dely));
    }

    public double dot(Vector2D vector2D) {
        Vector2D v = vector2D;
        return (this.f455x * v.f455x) + (this.f456y * v.f456y);
    }

    public double angle() {
        return Math.atan2(this.f456y, this.f455x);
    }

    public double angle(Vector2D v) {
        return Angle.diff(v.angle(), angle());
    }

    public double angleTo(Vector2D v) {
        double angDel = v.angle() - angle();
        if (angDel <= -3.141592653589793d) {
            return angDel + 6.283185307179586d;
        }
        if (angDel > 3.141592653589793d) {
            return angDel - 6.283185307179586d;
        }
        return angDel;
    }

    public Vector2D rotate(double d) {
        double angle = d;
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return create((this.f455x * cos) - (this.f456y * sin), (this.f455x * sin) + (this.f456y * cos));
    }

    public Vector2D rotateByQuarterCircle(int i) {
        int numQuarters = i;
        int nQuad = numQuarters % 4;
        if (numQuarters < 0 && nQuad != 0) {
            nQuad += 4;
        }
        switch (nQuad) {
            case 0:
                return create(this.f455x, this.f456y);
            case 1:
                return create(-this.f456y, this.f455x);
            case 2:
                return create(-this.f455x, -this.f456y);
            case 3:
                return create(this.f456y, -this.f455x);
            default:
                Assert.shouldNeverReachHere();
                return null;
        }
    }

    public boolean isParallel(Vector2D vector2D) {
        Vector2D v = vector2D;
        return 0.0d == ((double) RobustDeterminant.signOfDet2x2(this.f455x, this.f456y, v.f455x, v.f456y));
    }

    public Coordinate translate(Coordinate coordinate) {
        Coordinate coordinate2;
        Coordinate coord = coordinate;
        new Coordinate(this.f455x + coord.f412x, this.f456y + coord.f413y);
        return coordinate2;
    }

    public Coordinate toCoordinate() {
        Coordinate coordinate;
        new Coordinate(this.f455x, this.f456y);
        return coordinate;
    }

    public Object clone() {
        Vector2D vector2D;
        new Vector2D(this);
        return vector2D;
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("[").append(this.f455x).append(", ").append(this.f456y).append("]").toString();
    }

    public boolean equals(Object obj) {
        Object o = obj;
        if (!(o instanceof Vector2D)) {
            return false;
        }
        Vector2D v = (Vector2D) o;
        return this.f455x == v.f455x && this.f456y == v.f456y;
    }

    public int hashCode() {
        return (37 * ((37 * 17) + Coordinate.hashCode(this.f455x))) + Coordinate.hashCode(this.f456y);
    }
}
