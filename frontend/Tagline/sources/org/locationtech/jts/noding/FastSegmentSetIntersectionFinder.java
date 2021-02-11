package org.locationtech.jts.noding;

import java.util.Collection;

public class FastSegmentSetIntersectionFinder {
    private final SegmentSetMutualIntersector segSetMutInt;

    public FastSegmentSetIntersectionFinder(Collection baseSegStrings) {
        SegmentSetMutualIntersector segmentSetMutualIntersector;
        new MCIndexSegmentSetMutualIntersector(baseSegStrings);
        this.segSetMutInt = segmentSetMutualIntersector;
    }

    public SegmentSetMutualIntersector getSegmentSetIntersector() {
        return this.segSetMutInt;
    }

    public boolean intersects(Collection segStrings) {
        SegmentIntersectionDetector intFinder;
        new SegmentIntersectionDetector();
        return intersects(segStrings, intFinder);
    }

    public boolean intersects(Collection segStrings, SegmentIntersectionDetector segmentIntersectionDetector) {
        SegmentIntersectionDetector intDetector = segmentIntersectionDetector;
        this.segSetMutInt.process(segStrings, intDetector);
        return intDetector.hasIntersection();
    }
}
