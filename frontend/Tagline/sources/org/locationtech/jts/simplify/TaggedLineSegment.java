package org.locationtech.jts.simplify;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineSegment;

class TaggedLineSegment extends LineSegment {
    private int index;
    private Geometry parent;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TaggedLineSegment(Coordinate p0, Coordinate p1, Geometry parent2, int index2) {
        super(p0, p1);
        this.parent = parent2;
        this.index = index2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public TaggedLineSegment(Coordinate p0, Coordinate p1) {
        this(p0, p1, (Geometry) null, -1);
    }

    public Geometry getParent() {
        return this.parent;
    }

    public int getIndex() {
        return this.index;
    }
}
