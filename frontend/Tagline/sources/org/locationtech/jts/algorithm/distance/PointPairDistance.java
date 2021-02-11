package org.locationtech.jts.algorithm.distance;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.p006io.WKTWriter;

public class PointPairDistance {
    private double distance = Double.NaN;
    private boolean isNull = true;

    /* renamed from: pt */
    private Coordinate[] f408pt;

    public PointPairDistance() {
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate[] coordinateArr = new Coordinate[2];
        new Coordinate();
        coordinateArr[0] = coordinate;
        Coordinate[] coordinateArr2 = coordinateArr;
        new Coordinate();
        coordinateArr2[1] = coordinate2;
        this.f408pt = coordinateArr2;
    }

    public void initialize() {
        this.isNull = true;
    }

    public void initialize(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        this.f408pt[0].setCoordinate(p0);
        this.f408pt[1].setCoordinate(p1);
        this.distance = p0.distance(p1);
        this.isNull = false;
    }

    private void initialize(Coordinate p0, Coordinate p1, double distance2) {
        this.f408pt[0].setCoordinate(p0);
        this.f408pt[1].setCoordinate(p1);
        this.distance = distance2;
        this.isNull = false;
    }

    public double getDistance() {
        return this.distance;
    }

    public Coordinate[] getCoordinates() {
        return this.f408pt;
    }

    public Coordinate getCoordinate(int i) {
        return this.f408pt[i];
    }

    public void setMaximum(PointPairDistance pointPairDistance) {
        PointPairDistance ptDist = pointPairDistance;
        setMaximum(ptDist.f408pt[0], ptDist.f408pt[1]);
    }

    public void setMaximum(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        if (this.isNull) {
            initialize(p0, p1);
            return;
        }
        double dist = p0.distance(p1);
        if (dist > this.distance) {
            initialize(p0, p1, dist);
        }
    }

    public void setMinimum(PointPairDistance pointPairDistance) {
        PointPairDistance ptDist = pointPairDistance;
        setMinimum(ptDist.f408pt[0], ptDist.f408pt[1]);
    }

    public void setMinimum(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        if (this.isNull) {
            initialize(p0, p1);
            return;
        }
        double dist = p0.distance(p1);
        if (dist < this.distance) {
            initialize(p0, p1, dist);
        }
    }

    public String toString() {
        return WKTWriter.toLineString(this.f408pt[0], this.f408pt[1]);
    }
}
