package org.locationtech.jts.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateFilter;

public class CoordinateCountFilter implements CoordinateFilter {

    /* renamed from: n */
    private int f514n = 0;

    public CoordinateCountFilter() {
    }

    public int getCount() {
        return this.f514n;
    }

    public void filter(Coordinate coordinate) {
        Coordinate coordinate2 = coordinate;
        this.f514n++;
    }
}
