package org.locationtech.jts.noding;

import java.util.Collection;

public abstract class SinglePassNoder implements Noder {
    protected SegmentIntersector segInt;

    public abstract void computeNodes(Collection collection);

    public abstract Collection getNodedSubstrings();

    public SinglePassNoder() {
    }

    public SinglePassNoder(SegmentIntersector segInt2) {
        setSegmentIntersector(segInt2);
    }

    public void setSegmentIntersector(SegmentIntersector segInt2) {
        SegmentIntersector segmentIntersector = segInt2;
        this.segInt = segmentIntersector;
    }
}
