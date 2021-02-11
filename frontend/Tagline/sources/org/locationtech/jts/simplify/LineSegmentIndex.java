package org.locationtech.jts.simplify;

import java.util.List;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.index.quadtree.Quadtree;

class LineSegmentIndex {
    private Quadtree index;

    public LineSegmentIndex() {
        Quadtree quadtree;
        new Quadtree();
        this.index = quadtree;
    }

    public void add(TaggedLineString line) {
        TaggedLineSegment[] segs = line.getSegments();
        for (int i = 0; i < segs.length; i++) {
            add((LineSegment) segs[i]);
        }
    }

    public void add(LineSegment lineSegment) {
        Envelope envelope;
        LineSegment seg = lineSegment;
        new Envelope(seg.f422p0, seg.f423p1);
        this.index.insert(envelope, seg);
    }

    public void remove(LineSegment lineSegment) {
        Envelope envelope;
        LineSegment seg = lineSegment;
        new Envelope(seg.f422p0, seg.f423p1);
        boolean remove = this.index.remove(envelope, seg);
    }

    public List query(LineSegment lineSegment) {
        Envelope envelope;
        LineSegmentVisitor lineSegmentVisitor;
        LineSegment querySeg = lineSegment;
        new Envelope(querySeg.f422p0, querySeg.f423p1);
        Envelope env = envelope;
        new LineSegmentVisitor(querySeg);
        LineSegmentVisitor visitor = lineSegmentVisitor;
        this.index.query(env, visitor);
        return visitor.getItems();
    }
}
