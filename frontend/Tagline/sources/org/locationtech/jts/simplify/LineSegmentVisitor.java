package org.locationtech.jts.simplify;

import java.util.ArrayList;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.index.ItemVisitor;

/* compiled from: LineSegmentIndex */
class LineSegmentVisitor implements ItemVisitor {
    private ArrayList items;
    private LineSegment querySeg;

    public LineSegmentVisitor(LineSegment querySeg2) {
        ArrayList arrayList;
        new ArrayList();
        this.items = arrayList;
        this.querySeg = querySeg2;
    }

    public void visitItem(Object obj) {
        Object item = obj;
        LineSegment seg = (LineSegment) item;
        if (Envelope.intersects(seg.f422p0, seg.f423p1, this.querySeg.f422p0, this.querySeg.f423p1)) {
            boolean add = this.items.add(item);
        }
    }

    public ArrayList getItems() {
        return this.items;
    }
}
