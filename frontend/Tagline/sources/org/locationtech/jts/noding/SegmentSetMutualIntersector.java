package org.locationtech.jts.noding;

import java.util.Collection;

public interface SegmentSetMutualIntersector {
    void process(Collection collection, SegmentIntersector segmentIntersector);
}
