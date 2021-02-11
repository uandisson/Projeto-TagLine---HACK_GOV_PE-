package org.locationtech.jts.simplify;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;

class TaggedLineString {
    private int minimumSize;
    private LineString parentLine;
    private List resultSegs;
    private TaggedLineSegment[] segs;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public TaggedLineString(LineString parentLine2) {
        this(parentLine2, 2);
    }

    public TaggedLineString(LineString parentLine2, int minimumSize2) {
        List list;
        new ArrayList();
        this.resultSegs = list;
        this.parentLine = parentLine2;
        this.minimumSize = minimumSize2;
        init();
    }

    public int getMinimumSize() {
        return this.minimumSize;
    }

    public LineString getParent() {
        return this.parentLine;
    }

    public Coordinate[] getParentCoordinates() {
        return this.parentLine.getCoordinates();
    }

    public Coordinate[] getResultCoordinates() {
        return extractCoordinates(this.resultSegs);
    }

    public int getResultSize() {
        int resultSegsSize = this.resultSegs.size();
        return resultSegsSize == 0 ? 0 : resultSegsSize + 1;
    }

    public TaggedLineSegment getSegment(int i) {
        return this.segs[i];
    }

    private void init() {
        TaggedLineSegment seg;
        Coordinate[] pts = this.parentLine.getCoordinates();
        this.segs = new TaggedLineSegment[(pts.length - 1)];
        for (int i = 0; i < pts.length - 1; i++) {
            new TaggedLineSegment(pts[i], pts[i + 1], this.parentLine, i);
            this.segs[i] = seg;
        }
    }

    public TaggedLineSegment[] getSegments() {
        return this.segs;
    }

    public void addToResult(LineSegment seg) {
        boolean add = this.resultSegs.add(seg);
    }

    public LineString asLineString() {
        return this.parentLine.getFactory().createLineString(extractCoordinates(this.resultSegs));
    }

    public LinearRing asLinearRing() {
        return this.parentLine.getFactory().createLinearRing(extractCoordinates(this.resultSegs));
    }

    private static Coordinate[] extractCoordinates(List list) {
        List segs2 = list;
        Coordinate[] pts = new Coordinate[(segs2.size() + 1)];
        LineSegment seg = null;
        for (int i = 0; i < segs2.size(); i++) {
            seg = (LineSegment) segs2.get(i);
            pts[i] = seg.f422p0;
        }
        pts[pts.length - 1] = seg.f423p1;
        return pts;
    }
}
