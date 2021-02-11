package org.locationtech.jts.noding;

import java.util.Collection;
import java.util.Iterator;
import org.locationtech.jts.geom.Coordinate;

public class SimpleSegmentSetMutualIntersector implements SegmentSetMutualIntersector {
    private final Collection baseSegStrings;

    public SimpleSegmentSetMutualIntersector(Collection segStrings) {
        this.baseSegStrings = segStrings;
    }

    public void process(Collection collection, SegmentIntersector segmentIntersector) {
        Collection segStrings = collection;
        SegmentIntersector segInt = segmentIntersector;
        for (SegmentString baseSS : this.baseSegStrings) {
            Iterator j = segStrings.iterator();
            while (true) {
                if (j.hasNext()) {
                    intersect(baseSS, (SegmentString) j.next(), segInt);
                    if (segInt.isDone()) {
                        return;
                    }
                }
            }
        }
    }

    private void intersect(SegmentString segmentString, SegmentString segmentString2, SegmentIntersector segmentIntersector) {
        SegmentString ss0 = segmentString;
        SegmentString ss1 = segmentString2;
        SegmentIntersector segInt = segmentIntersector;
        Coordinate[] pts0 = ss0.getCoordinates();
        Coordinate[] pts1 = ss1.getCoordinates();
        for (int i0 = 0; i0 < pts0.length - 1; i0++) {
            int i1 = 0;
            while (i1 < pts1.length - 1) {
                segInt.processIntersections(ss0, i0, ss1, i1);
                if (!segInt.isDone()) {
                    i1++;
                } else {
                    return;
                }
            }
        }
    }
}
