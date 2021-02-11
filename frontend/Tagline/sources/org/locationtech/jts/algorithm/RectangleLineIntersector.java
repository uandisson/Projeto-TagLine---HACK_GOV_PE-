package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

public class RectangleLineIntersector {
    private Coordinate diagDown0;
    private Coordinate diagDown1;
    private Coordinate diagUp0;
    private Coordinate diagUp1;

    /* renamed from: li */
    private LineIntersector f405li;
    private Envelope rectEnv;

    public RectangleLineIntersector(Envelope envelope) {
        LineIntersector lineIntersector;
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        Envelope rectEnv2 = envelope;
        new RobustLineIntersector();
        this.f405li = lineIntersector;
        this.rectEnv = rectEnv2;
        new Coordinate(rectEnv2.getMinX(), rectEnv2.getMinY());
        this.diagUp0 = coordinate;
        new Coordinate(rectEnv2.getMaxX(), rectEnv2.getMaxY());
        this.diagUp1 = coordinate2;
        new Coordinate(rectEnv2.getMinX(), rectEnv2.getMaxY());
        this.diagDown0 = coordinate3;
        new Coordinate(rectEnv2.getMaxX(), rectEnv2.getMinY());
        this.diagDown1 = coordinate4;
    }

    public boolean intersects(Coordinate coordinate, Coordinate coordinate2) {
        Envelope segEnv;
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        new Envelope(p0, p1);
        if (!this.rectEnv.intersects(segEnv)) {
            return false;
        }
        if (this.rectEnv.intersects(p0)) {
            return true;
        }
        if (this.rectEnv.intersects(p1)) {
            return true;
        }
        if (p0.compareTo(p1) > 0) {
            Coordinate tmp = p0;
            p0 = p1;
            p1 = tmp;
        }
        boolean isSegUpwards = false;
        if (p1.f413y > p0.f413y) {
            isSegUpwards = true;
        }
        if (isSegUpwards) {
            this.f405li.computeIntersection(p0, p1, this.diagDown0, this.diagDown1);
        } else {
            this.f405li.computeIntersection(p0, p1, this.diagUp0, this.diagUp1);
        }
        if (this.f405li.hasIntersection()) {
            return true;
        }
        return false;
    }
}
