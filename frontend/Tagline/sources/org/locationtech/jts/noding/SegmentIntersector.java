package org.locationtech.jts.noding;

public interface SegmentIntersector {
    boolean isDone();

    void processIntersections(SegmentString segmentString, int i, SegmentString segmentString2, int i2);
}
