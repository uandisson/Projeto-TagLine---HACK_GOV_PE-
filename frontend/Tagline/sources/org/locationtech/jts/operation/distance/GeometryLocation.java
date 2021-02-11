package org.locationtech.jts.operation.distance;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

public class GeometryLocation {
    public static final int INSIDE_AREA = -1;
    private Geometry component;

    /* renamed from: pt */
    private Coordinate f490pt;
    private int segIndex;

    public GeometryLocation(Geometry component2, int segIndex2, Coordinate pt) {
        this.component = null;
        this.f490pt = null;
        this.component = component2;
        this.segIndex = segIndex2;
        this.f490pt = pt;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public GeometryLocation(Geometry component2, Coordinate pt) {
        this(component2, -1, pt);
    }

    public Geometry getGeometryComponent() {
        return this.component;
    }

    public int getSegmentIndex() {
        return this.segIndex;
    }

    public Coordinate getCoordinate() {
        return this.f490pt;
    }

    public boolean isInsideArea() {
        return this.segIndex == -1;
    }
}
