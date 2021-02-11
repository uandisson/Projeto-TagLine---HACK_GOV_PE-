package org.locationtech.jts.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateFilter;

public class CoordinateArrayFilter implements CoordinateFilter {

    /* renamed from: n */
    int f513n = 0;
    Coordinate[] pts = null;

    public CoordinateArrayFilter(int size) {
        this.pts = new Coordinate[size];
    }

    public Coordinate[] getCoordinates() {
        return this.pts;
    }

    public void filter(Coordinate coord) {
        Coordinate[] coordinateArr = this.pts;
        int i = this.f513n;
        int i2 = i + 1;
        this.f513n = i2;
        coordinateArr[i] = coord;
    }
}
