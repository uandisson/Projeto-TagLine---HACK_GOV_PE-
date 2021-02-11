package org.locationtech.jts.triangulate.quadedge;

import java.io.PrintStream;
import org.locationtech.jts.algorithm.HCoordinate;
import org.locationtech.jts.algorithm.NotRepresentableException;
import org.locationtech.jts.geom.Coordinate;

public class Vertex {
    public static final int BEHIND = 3;
    public static final int BETWEEN = 4;
    public static final int BEYOND = 2;
    public static final int DESTINATION = 6;
    public static final int LEFT = 0;
    public static final int ORIGIN = 5;
    public static final int RIGHT = 1;

    /* renamed from: p */
    private Coordinate f512p;

    public Vertex(double _x, double _y) {
        Coordinate coordinate;
        new Coordinate(_x, _y);
        this.f512p = coordinate;
    }

    public Vertex(double _x, double _y, double _z) {
        Coordinate coordinate;
        new Coordinate(_x, _y, _z);
        this.f512p = coordinate;
    }

    public Vertex(Coordinate _p) {
        Coordinate coordinate;
        new Coordinate(_p);
        this.f512p = coordinate;
    }

    public double getX() {
        return this.f512p.f412x;
    }

    public double getY() {
        return this.f512p.f413y;
    }

    public double getZ() {
        return this.f512p.f414z;
    }

    public void setZ(double _z) {
        double d = _z;
        this.f512p.f414z = d;
    }

    public Coordinate getCoordinate() {
        return this.f512p;
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("POINT (").append(this.f512p.f412x).append(" ").append(this.f512p.f413y).append(")").toString();
    }

    public boolean equals(Vertex vertex) {
        Vertex _x = vertex;
        if (this.f512p.f412x == _x.getX() && this.f512p.f413y == _x.getY()) {
            return true;
        }
        return false;
    }

    public boolean equals(Vertex _x, double tolerance) {
        if (this.f512p.distance(_x.getCoordinate()) < tolerance) {
            return true;
        }
        return false;
    }

    public int classify(Vertex vertex, Vertex vertex2) {
        Vertex p0 = vertex;
        Vertex p1 = vertex2;
        Vertex a = p1.sub(p0);
        Vertex b = sub(p0);
        double sa = a.crossProduct(b);
        if (sa > 0.0d) {
            return 0;
        }
        if (sa < 0.0d) {
            return 1;
        }
        if (a.getX() * b.getX() < 0.0d || a.getY() * b.getY() < 0.0d) {
            return 3;
        }
        if (a.magn() < b.magn()) {
            return 2;
        }
        if (p0.equals(this)) {
            return 5;
        }
        if (p1.equals(this)) {
            return 6;
        }
        return 4;
    }

    /* access modifiers changed from: package-private */
    public double crossProduct(Vertex vertex) {
        Vertex v = vertex;
        return (this.f512p.f412x * v.getY()) - (this.f512p.f413y * v.getX());
    }

    /* access modifiers changed from: package-private */
    public double dot(Vertex vertex) {
        Vertex v = vertex;
        return (this.f512p.f412x * v.getX()) + (this.f512p.f413y * v.getY());
    }

    /* access modifiers changed from: package-private */
    public Vertex times(double d) {
        Vertex vertex;
        double c = d;
        new Vertex(c * this.f512p.f412x, c * this.f512p.f413y);
        return vertex;
    }

    /* access modifiers changed from: package-private */
    public Vertex sum(Vertex vertex) {
        Vertex vertex2;
        Vertex v = vertex;
        new Vertex(this.f512p.f412x + v.getX(), this.f512p.f413y + v.getY());
        return vertex2;
    }

    /* access modifiers changed from: package-private */
    public Vertex sub(Vertex vertex) {
        Vertex vertex2;
        Vertex v = vertex;
        new Vertex(this.f512p.f412x - v.getX(), this.f512p.f413y - v.getY());
        return vertex2;
    }

    /* access modifiers changed from: package-private */
    public double magn() {
        return Math.sqrt((this.f512p.f412x * this.f512p.f412x) + (this.f512p.f413y * this.f512p.f413y));
    }

    /* access modifiers changed from: package-private */
    public Vertex cross() {
        Vertex vertex;
        new Vertex(this.f512p.f413y, -this.f512p.f412x);
        return vertex;
    }

    public boolean isInCircle(Vertex a, Vertex b, Vertex c) {
        return TrianglePredicate.isInCircleRobust(a.f512p, b.f512p, c.f512p, this.f512p);
    }

    public final boolean isCCW(Vertex vertex, Vertex vertex2) {
        Vertex b = vertex;
        Vertex c = vertex2;
        return ((b.f512p.f412x - this.f512p.f412x) * (c.f512p.f413y - this.f512p.f413y)) - ((b.f512p.f413y - this.f512p.f413y) * (c.f512p.f412x - this.f512p.f412x)) > 0.0d;
    }

    public final boolean rightOf(QuadEdge quadEdge) {
        QuadEdge e = quadEdge;
        return isCCW(e.dest(), e.orig());
    }

    public final boolean leftOf(QuadEdge quadEdge) {
        QuadEdge e = quadEdge;
        return isCCW(e.orig(), e.dest());
    }

    private HCoordinate bisector(Vertex vertex, Vertex vertex2) {
        HCoordinate l1;
        HCoordinate l2;
        HCoordinate hCoordinate;
        Vertex a = vertex;
        Vertex b = vertex2;
        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();
        new HCoordinate(a.getX() + (dx / 2.0d), a.getY() + (dy / 2.0d), 1.0d);
        new HCoordinate((a.getX() - dy) + (dx / 2.0d), a.getY() + dx + (dy / 2.0d), 1.0d);
        new HCoordinate(l1, l2);
        return hCoordinate;
    }

    private double distance(Vertex vertex, Vertex vertex2) {
        Vertex v1 = vertex;
        Vertex v2 = vertex2;
        return Math.sqrt(Math.pow(v2.getX() - v1.getX(), 2.0d) + Math.pow(v2.getY() - v1.getY(), 2.0d));
    }

    public double circumRadiusRatio(Vertex vertex, Vertex vertex2) {
        Vertex b = vertex;
        Vertex c = vertex2;
        double radius = distance(circleCenter(b, c), b);
        double edgeLength = distance(this, b);
        double el = distance(b, c);
        if (el < edgeLength) {
            edgeLength = el;
        }
        double el2 = distance(c, this);
        if (el2 < edgeLength) {
            edgeLength = el2;
        }
        return radius / edgeLength;
    }

    public Vertex midPoint(Vertex vertex) {
        Vertex vertex2;
        Vertex a = vertex;
        new Vertex((this.f512p.f412x + a.getX()) / 2.0d, (this.f512p.f413y + a.getY()) / 2.0d, (this.f512p.f414z + a.getZ()) / 2.0d);
        return vertex2;
    }

    public Vertex circleCenter(Vertex vertex, Vertex vertex2) {
        Vertex vertex3;
        HCoordinate hCoordinate;
        StringBuilder sb;
        Vertex vertex4;
        Vertex b = vertex;
        Vertex c = vertex2;
        new Vertex(getX(), getY());
        Vertex a = vertex3;
        new HCoordinate(bisector(a, b), bisector(b, c));
        HCoordinate hcc = hCoordinate;
        Vertex cc = null;
        try {
            Vertex vertex5 = vertex4;
            new Vertex(hcc.getX(), hcc.getY());
            cc = vertex5;
        } catch (NotRepresentableException e) {
            NotRepresentableException nre = e;
            PrintStream printStream = System.err;
            new StringBuilder();
            printStream.println(sb.append("a: ").append(a).append("  b: ").append(b).append("  c: ").append(c).toString());
            System.err.println(nre);
        }
        return cc;
    }

    public double interpolateZValue(Vertex vertex, Vertex vertex2, Vertex vertex3) {
        Vertex v0 = vertex;
        Vertex v1 = vertex2;
        Vertex v2 = vertex3;
        double x0 = v0.getX();
        double y0 = v0.getY();
        double a = v1.getX() - x0;
        double b = v2.getX() - x0;
        double c = v1.getY() - y0;
        double d = v2.getY() - y0;
        double det = (a * d) - (b * c);
        double dx = getX() - x0;
        double dy = getY() - y0;
        return v0.getZ() + ((((d * dx) - (b * dy)) / det) * (v1.getZ() - v0.getZ())) + (((((-c) * dx) + (a * dy)) / det) * (v2.getZ() - v0.getZ()));
    }

    public static double interpolateZ(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate p = coordinate;
        Coordinate v0 = coordinate2;
        Coordinate v1 = coordinate3;
        Coordinate v2 = coordinate4;
        double x0 = v0.f412x;
        double y0 = v0.f413y;
        double a = v1.f412x - x0;
        double b = v2.f412x - x0;
        double c = v1.f413y - y0;
        double d = v2.f413y - y0;
        double det = (a * d) - (b * c);
        double dx = p.f412x - x0;
        double dy = p.f413y - y0;
        return v0.f414z + ((((d * dx) - (b * dy)) / det) * (v1.f414z - v0.f414z)) + (((((-c) * dx) + (a * dy)) / det) * (v2.f414z - v0.f414z));
    }

    public static double interpolateZ(Coordinate p, Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        double segLen = p0.distance(p1);
        double ptLen = p.distance(p0);
        return p0.f414z + ((p1.f414z - p0.f414z) * (ptLen / segLen));
    }
}
