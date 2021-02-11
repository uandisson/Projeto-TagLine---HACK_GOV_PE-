package org.locationtech.jts.geomgraph.index;

import java.util.List;

public abstract class EdgeSetIntersector {
    public abstract void computeIntersections(List list, List list2, SegmentIntersector segmentIntersector);

    public abstract void computeIntersections(List list, SegmentIntersector segmentIntersector, boolean z);

    public EdgeSetIntersector() {
    }
}
