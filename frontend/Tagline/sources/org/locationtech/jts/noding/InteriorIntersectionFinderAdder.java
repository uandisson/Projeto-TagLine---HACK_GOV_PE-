package org.locationtech.jts.noding;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.LineIntersector;

public class InteriorIntersectionFinderAdder implements SegmentIntersector {
    private final List interiorIntersections;

    /* renamed from: li */
    private LineIntersector f462li;

    public InteriorIntersectionFinderAdder(LineIntersector li) {
        List list;
        this.f462li = li;
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
            this.f462li.computeIntersection(e0.getCoordinates()[segIndex0], e0.getCoordinates()[segIndex0 + 1], e1.getCoordinates()[segIndex1], e1.getCoordinates()[segIndex1 + 1]);
            if (this.f462li.hasIntersection() && this.f462li.isInteriorIntersection()) {
                for (int intIndex = 0; intIndex < this.f462li.getIntersectionNum(); intIndex++) {
                    boolean add = this.interiorIntersections.add(this.f462li.getIntersection(intIndex));
                }
                ((NodedSegmentString) e0).addIntersections(this.f462li, segIndex0, 0);
                ((NodedSegmentString) e1).addIntersections(this.f462li, segIndex1, 1);
            }
        }
    }

    public boolean isDone() {
        return false;
    }
}
