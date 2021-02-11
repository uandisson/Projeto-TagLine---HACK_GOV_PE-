package org.locationtech.jts.math;

import org.locationtech.jts.geom.Coordinate;

public class Plane3D {
    public static final int XY_PLANE = 1;
    public static final int XZ_PLANE = 3;
    public static final int YZ_PLANE = 2;
    private Coordinate basePt;
    private Vector3D normal;

    public Plane3D(Vector3D normal2, Coordinate basePt2) {
        this.normal = normal2;
        this.basePt = basePt2;
    }

    public double orientedDistance(Coordinate p) {
        Vector3D pb;
        Throwable th;
        new Vector3D(p, this.basePt);
        double pbdDotNormal = pb.dot(this.normal);
        if (!Double.isNaN(pbdDotNormal)) {
            return pbdDotNormal / this.normal.length();
        }
        Throwable th2 = th;
        new IllegalArgumentException("3D Coordinate has NaN ordinate");
        throw th2;
    }

    public int closestAxisPlane() {
        double xmag = Math.abs(this.normal.getX());
        double ymag = Math.abs(this.normal.getY());
        double zmag = Math.abs(this.normal.getZ());
        if (xmag > ymag) {
            if (xmag > zmag) {
                return 2;
            }
            return 1;
        } else if (zmag > ymag) {
            return 1;
        } else {
            return 3;
        }
    }
}
