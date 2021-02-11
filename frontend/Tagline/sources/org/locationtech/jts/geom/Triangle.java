package org.locationtech.jts.geom;

import org.locationtech.jts.algorithm.Angle;
import org.locationtech.jts.algorithm.HCoordinate;

public class Triangle {

    /* renamed from: p0 */
    public Coordinate f425p0;

    /* renamed from: p1 */
    public Coordinate f426p1;

    /* renamed from: p2 */
    public Coordinate f427p2;

    public static boolean isAcute(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        if (!Angle.isAcute(a, b, c)) {
            return false;
        }
        if (!Angle.isAcute(b, c, a)) {
            return false;
        }
        if (!Angle.isAcute(c, a, b)) {
            return false;
        }
        return true;
    }

    public static HCoordinate perpendicularBisector(Coordinate coordinate, Coordinate coordinate2) {
        HCoordinate l1;
        HCoordinate l2;
        HCoordinate hCoordinate;
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        double dx = b.f412x - a.f412x;
        double dy = b.f413y - a.f413y;
        new HCoordinate(a.f412x + (dx / 2.0d), a.f413y + (dy / 2.0d), 1.0d);
        new HCoordinate((a.f412x - dy) + (dx / 2.0d), a.f413y + dx + (dy / 2.0d), 1.0d);
        new HCoordinate(l1, l2);
        return hCoordinate;
    }

    public static Coordinate circumcentre(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate a;
        Coordinate a2 = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        double cx = c.f412x;
        double cy = c.f413y;
        double ax = a2.f412x - cx;
        double ay = a2.f413y - cy;
        double bx = b.f412x - cx;
        double by = b.f413y - cy;
        double denom = 2.0d * det(ax, ay, bx, by);
        double numx = det(ay, (ax * ax) + (ay * ay), by, (bx * bx) + (by * by));
        new Coordinate(cx - (numx / denom), cy + (det(ax, (ax * ax) + (ay * ay), bx, (bx * bx) + (by * by)) / denom));
        return a;
    }

    private static double det(double m00, double m01, double m10, double m11) {
        return (m00 * m11) - (m01 * m10);
    }

    public static Coordinate inCentre(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate a;
        Coordinate a2 = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        double len0 = b.distance(c);
        double len1 = a2.distance(c);
        double len2 = a2.distance(b);
        double circum = len0 + len1 + len2;
        new Coordinate((((len0 * a2.f412x) + (len1 * b.f412x)) + (len2 * c.f412x)) / circum, (((len0 * a2.f413y) + (len1 * b.f413y)) + (len2 * c.f413y)) / circum);
        return a;
    }

    public static Coordinate centroid(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate a;
        Coordinate a2 = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        new Coordinate(((a2.f412x + b.f412x) + c.f412x) / 3.0d, ((a2.f413y + b.f413y) + c.f413y) / 3.0d);
        return a;
    }

    public static double longestSideLength(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        double lenAB = a.distance(b);
        double lenBC = b.distance(c);
        double lenCA = c.distance(a);
        double maxLen = lenAB;
        if (lenBC > maxLen) {
            maxLen = lenBC;
        }
        if (lenCA > maxLen) {
            maxLen = lenCA;
        }
        return maxLen;
    }

    public static Coordinate angleBisector(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate splitPt;
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        double len0 = b.distance(a);
        double frac = len0 / (len0 + b.distance(c));
        new Coordinate(a.f412x + (frac * (c.f412x - a.f412x)), a.f413y + (frac * (c.f413y - a.f413y)));
        return splitPt;
    }

    public static double area(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        return Math.abs((((c.f412x - a.f412x) * (b.f413y - a.f413y)) - ((b.f412x - a.f412x) * (c.f413y - a.f413y))) / 2.0d);
    }

    public static double signedArea(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        return (((c.f412x - a.f412x) * (b.f413y - a.f413y)) - ((b.f412x - a.f412x) * (c.f413y - a.f413y))) / 2.0d;
    }

    public static double area3D(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        Coordinate c = coordinate3;
        double ux = b.f412x - a.f412x;
        double uy = b.f413y - a.f413y;
        double uz = b.f414z - a.f414z;
        double vx = c.f412x - a.f412x;
        double vy = c.f413y - a.f413y;
        double vz = c.f414z - a.f414z;
        double crossx = (uy * vz) - (uz * vy);
        double crossy = (uz * vx) - (ux * vz);
        double crossz = (ux * vy) - (uy * vx);
        return Math.sqrt(((crossx * crossx) + (crossy * crossy)) + (crossz * crossz)) / 2.0d;
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

    public Triangle(Coordinate p0, Coordinate p1, Coordinate p2) {
        this.f425p0 = p0;
        this.f426p1 = p1;
        this.f427p2 = p2;
    }

    public Coordinate inCentre() {
        return inCentre(this.f425p0, this.f426p1, this.f427p2);
    }

    public boolean isAcute() {
        return isAcute(this.f425p0, this.f426p1, this.f427p2);
    }

    public Coordinate circumcentre() {
        return circumcentre(this.f425p0, this.f426p1, this.f427p2);
    }

    public Coordinate centroid() {
        return centroid(this.f425p0, this.f426p1, this.f427p2);
    }

    public double longestSideLength() {
        return longestSideLength(this.f425p0, this.f426p1, this.f427p2);
    }

    public double area() {
        return area(this.f425p0, this.f426p1, this.f427p2);
    }

    public double signedArea() {
        return signedArea(this.f425p0, this.f426p1, this.f427p2);
    }

    public double area3D() {
        return area3D(this.f425p0, this.f426p1, this.f427p2);
    }

    public double interpolateZ(Coordinate coordinate) {
        Throwable th;
        Coordinate p = coordinate;
        if (p != null) {
            return interpolateZ(p, this.f425p0, this.f426p1, this.f427p2);
        }
        Throwable th2 = th;
        new IllegalArgumentException("Supplied point is null.");
        throw th2;
    }
}
