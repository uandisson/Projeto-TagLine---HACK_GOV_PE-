package org.locationtech.jts.math;

import org.locationtech.jts.geom.Coordinate;

public class Vector3D {

    /* renamed from: x */
    private double f457x;

    /* renamed from: y */
    private double f458y;

    /* renamed from: z */
    private double f459z;

    public static double dot(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate A = coordinate;
        Coordinate B = coordinate2;
        Coordinate C = coordinate3;
        Coordinate D = coordinate4;
        double ABx = B.f412x - A.f412x;
        double ABy = B.f413y - A.f413y;
        double ABz = B.f414z - A.f414z;
        return (ABx * (D.f412x - C.f412x)) + (ABy * (D.f413y - C.f413y)) + (ABz * (D.f414z - C.f414z));
    }

    public static Vector3D create(double x, double y, double z) {
        Vector3D vector3D;
        new Vector3D(x, y, z);
        return vector3D;
    }

    public static Vector3D create(Coordinate coord) {
        Vector3D vector3D;
        new Vector3D(coord);
        return vector3D;
    }

    public Vector3D(Coordinate coordinate) {
        Coordinate v = coordinate;
        this.f457x = v.f412x;
        this.f458y = v.f413y;
        this.f459z = v.f414z;
    }

    public static double dot(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate v1 = coordinate;
        Coordinate v2 = coordinate2;
        return (v1.f412x * v2.f412x) + (v1.f413y * v2.f413y) + (v1.f414z * v2.f414z);
    }

    public Vector3D(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate from = coordinate;
        Coordinate to = coordinate2;
        this.f457x = to.f412x - from.f412x;
        this.f458y = to.f413y - from.f413y;
        this.f459z = to.f414z - from.f414z;
    }

    public Vector3D(double x, double y, double z) {
        this.f457x = x;
        this.f458y = y;
        this.f459z = z;
    }

    public double getX() {
        return this.f457x;
    }

    public double getY() {
        return this.f458y;
    }

    public double getZ() {
        return this.f459z;
    }

    public double dot(Vector3D vector3D) {
        Vector3D v = vector3D;
        return (this.f457x * v.f457x) + (this.f458y * v.f458y) + (this.f459z * v.f459z);
    }

    public double length() {
        return Math.sqrt((this.f457x * this.f457x) + (this.f458y * this.f458y) + (this.f459z * this.f459z));
    }

    public static double length(Coordinate coordinate) {
        Coordinate v = coordinate;
        return Math.sqrt((v.f412x * v.f412x) + (v.f413y * v.f413y) + (v.f414z * v.f414z));
    }

    public Vector3D normalize() {
        if (length() > 0.0d) {
            return divide(length());
        }
        return create(0.0d, 0.0d, 0.0d);
    }

    private Vector3D divide(double d) {
        double d2 = d;
        return create(this.f457x / d2, this.f458y / d2, this.f459z / d2);
    }

    public static Coordinate normalize(Coordinate coordinate) {
        Coordinate v;
        Coordinate v2 = coordinate;
        double len = length(v2);
        new Coordinate(v2.f412x / len, v2.f413y / len, v2.f414z / len);
        return v;
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("[").append(this.f457x).append(", ").append(this.f458y).append(", ").append(this.f459z).append("]").toString();
    }
}
