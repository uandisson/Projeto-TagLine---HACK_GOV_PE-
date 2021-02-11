package org.locationtech.jts.operation.distance3d;

import org.locationtech.jts.algorithm.RayCrossingCounter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.math.Plane3D;
import org.locationtech.jts.math.Vector3D;

public class PlanarPolygon3D {
    private int facingPlane = -1;
    private Plane3D plane;
    private Polygon poly;

    public PlanarPolygon3D(Polygon polygon) {
        Polygon poly2 = polygon;
        this.poly = poly2;
        this.plane = findBestFitPlane(poly2);
        this.facingPlane = this.plane.closestAxisPlane();
    }

    private Plane3D findBestFitPlane(Polygon poly2) {
        Plane3D plane3D;
        CoordinateSequence seq = poly2.getExteriorRing().getCoordinateSequence();
        Coordinate basePt = averagePoint(seq);
        new Plane3D(averageNormal(seq), basePt);
        return plane3D;
    }

    private Vector3D averageNormal(CoordinateSequence coordinateSequence) {
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate coordinate3;
        CoordinateSequence seq = coordinateSequence;
        int n = seq.size();
        new Coordinate(0.0d, 0.0d, 0.0d);
        Coordinate sum = coordinate;
        new Coordinate(0.0d, 0.0d, 0.0d);
        Coordinate p1 = coordinate2;
        new Coordinate(0.0d, 0.0d, 0.0d);
        Coordinate p2 = coordinate3;
        for (int i = 0; i < n - 1; i++) {
            seq.getCoordinate(i, p1);
            seq.getCoordinate(i + 1, p2);
            sum.f412x += (p1.f413y - p2.f413y) * (p1.f414z + p2.f414z);
            sum.f413y += (p1.f414z - p2.f414z) * (p1.f412x + p2.f412x);
            sum.f414z += (p1.f412x - p2.f412x) * (p1.f413y + p2.f413y);
        }
        sum.f412x /= (double) n;
        sum.f413y /= (double) n;
        sum.f414z /= (double) n;
        return Vector3D.create(sum).normalize();
    }

    private Coordinate averagePoint(CoordinateSequence coordinateSequence) {
        Coordinate coordinate;
        CoordinateSequence seq = coordinateSequence;
        new Coordinate(0.0d, 0.0d, 0.0d);
        Coordinate a = coordinate;
        int n = seq.size();
        for (int i = 0; i < n; i++) {
            a.f412x += seq.getOrdinate(i, 0);
            a.f413y += seq.getOrdinate(i, 1);
            a.f414z += seq.getOrdinate(i, 2);
        }
        a.f412x /= (double) n;
        a.f413y /= (double) n;
        a.f414z /= (double) n;
        return a;
    }

    public Plane3D getPlane() {
        return this.plane;
    }

    public Polygon getPolygon() {
        return this.poly;
    }

    public boolean intersects(Coordinate coordinate) {
        Coordinate intPt = coordinate;
        if (2 == locate(intPt, this.poly.getExteriorRing())) {
            return false;
        }
        for (int i = 0; i < this.poly.getNumInteriorRing(); i++) {
            if (0 == locate(intPt, this.poly.getInteriorRingN(i))) {
                return false;
            }
        }
        return true;
    }

    private int locate(Coordinate pt, LineString ring) {
        return RayCrossingCounter.locatePointInRing(project(pt, this.facingPlane), project(ring.getCoordinateSequence(), this.facingPlane));
    }

    public boolean intersects(Coordinate pt, LineString ring) {
        return 2 != RayCrossingCounter.locatePointInRing(project(pt, this.facingPlane), project(ring.getCoordinateSequence(), this.facingPlane));
    }

    private static CoordinateSequence project(CoordinateSequence coordinateSequence, int facingPlane2) {
        CoordinateSequence seq = coordinateSequence;
        switch (facingPlane2) {
            case 1:
                return AxisPlaneCoordinateSequence.projectToXY(seq);
            case 3:
                return AxisPlaneCoordinateSequence.projectToXZ(seq);
            default:
                return AxisPlaneCoordinateSequence.projectToYZ(seq);
        }
    }

    private static Coordinate project(Coordinate coordinate, int facingPlane2) {
        Coordinate p;
        Coordinate p2;
        Coordinate p3;
        Coordinate p4 = coordinate;
        switch (facingPlane2) {
            case 1:
                new Coordinate(p4.f412x, p4.f413y);
                return p2;
            case 3:
                new Coordinate(p4.f412x, p4.f414z);
                return p;
            default:
                new Coordinate(p4.f413y, p4.f414z);
                return p3;
        }
    }
}
