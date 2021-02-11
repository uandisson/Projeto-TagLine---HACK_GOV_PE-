package org.locationtech.jts.noding;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.LineIntersector;

public class IntersectionFinderAdder implements SegmentIntersector {
    private final List interiorIntersections;

    /* renamed from: li */
    private LineIntersector f464li;

    public IntersectionFinderAdder(LineIntersector li) {
        List list;
        this.f464li = li;
        new ArrayList();
        this.interiorIntersections = list;
    }

    public List getInteriorIntersections() {
        return this.interiorIntersections;
    }

    public void processIntersections(SegmentString segmentString, int i, SegmentString segmentString2, int i2) {
        SegmentString e0 = segmentString;
        int segIndex0 = i;
        SegmentString e1 = segmentString2;
        int segIndex1 = i2;
        if (e0 != e1 || segIndex0 != segIndex1) {
            this.f464li.computeIntersection(e0.getCoordinates()[segIndex0], e0.getCoordinates()[segIndex0 + 1], e1.getCoordinates()[segIndex1], e1.getCoordinates()[segIndex1 + 1]);
            if (this.f464li.hasIntersection() && this.f464li.isInteriorIntersection()) {
                for (int intIndex = 0; intIndex < this.f464li.getIntersectionNum(); intIndex++) {
                    boolean add = this.interiorIntersections.add(this.f464li.getIntersection(intIndex));
                }
                ((NodedSegmentString) e0).addIntersections(this.f464li, segIndex0, 0);
                ((NodedSegmentString) e1).addIntersections(this.f464li, segIndex1, 1);
            }
        }
    }

    public boolean isDone() {
        return false;
    }
}
