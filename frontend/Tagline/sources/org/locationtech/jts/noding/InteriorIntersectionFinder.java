package org.locationtech.jts.noding;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.geom.Coordinate;

public class InteriorIntersectionFinder implements SegmentIntersector {
    private boolean findAllIntersections = false;
    private Coordinate[] intSegments = null;
    private Coordinate interiorIntersection = null;
    private int intersectionCount;
    private List intersections;
    private boolean isCheckEndSegmentsOnly = false;
    private boolean keepIntersections;

    /* renamed from: li */
    private LineIntersector f461li;

    public static InteriorIntersectionFinder createAnyIntersectionFinder(LineIntersector li) {
        InteriorIntersectionFinder interiorIntersectionFinder;
        new InteriorIntersectionFinder(li);
        return interiorIntersectionFinder;
    }

    public static InteriorIntersectionFinder createAllIntersectionsFinder(LineIntersector li) {
        InteriorIntersectionFinder interiorIntersectionFinder;
        new InteriorIntersectionFinder(li);
        InteriorIntersectionFinder finder = interiorIntersectionFinder;
        finder.setFindAllIntersections(true);
        return finder;
    }

    public static InteriorIntersectionFinder createIntersectionCounter(LineIntersector li) {
        InteriorIntersectionFinder interiorIntersectionFinder;
        new InteriorIntersectionFinder(li);
        InteriorIntersectionFinder finder = interiorIntersectionFinder;
        finder.setFindAllIntersections(true);
        finder.setKeepIntersections(false);
        return finder;
    }

    public InteriorIntersectionFinder(LineIntersector li) {
        List list;
        new ArrayList();
        this.intersections = list;
        this.intersectionCount = 0;
        this.keepIntersections = true;
        this.f461li = li;
        this.interiorIntersection = null;
    }

    public void setFindAllIntersections(boolean findAllIntersections2) {
        boolean z = findAllIntersections2;
        this.findAllIntersections = z;
    }

    public void setKeepIntersections(boolean keepIntersections2) {
        boolean z = keepIntersections2;
        this.keepIntersections = z;
    }

    public List getIntersections() {
        return this.intersections;
    }

    public int count() {
        return this.intersectionCount;
    }

    public void setCheckEndSegmentsOnly(boolean isCheckEndSegmentsOnly2) {
        boolean z = isCheckEndSegmentsOnly2;
        this.isCheckEndSegmentsOnly = z;
    }

    public boolean hasIntersection() {
        return this.interiorIntersection != null;
    }

    public Coordinate getInteriorIntersection() {
        return this.interiorIntersection;
    }

    public Coordinate[] getIntersectionSegments() {
        return this.intSegments;
    }

    public void processIntersections(SegmentString segmentString, int i, SegmentString segmentString2, int i2) {
        SegmentString e0 = segmentString;
        int segIndex0 = i;
        SegmentString e1 = segmentString2;
        int segIndex1 = i2;
        if (!this.findAllIntersections && hasIntersection()) {
            return;
        }
        if (e0 != e1 || segIndex0 != segIndex1) {
            if (this.isCheckEndSegmentsOnly) {
                if (!(isEndSegment(e0, segIndex0) || isEndSegment(e1, segIndex1))) {
                    return;
                }
            }
            Coordinate p00 = e0.getCoordinates()[segIndex0];
            Coordinate p01 = e0.getCoordinates()[segIndex0 + 1];
            Coordinate p10 = e1.getCoordinates()[segIndex1];
            Coordinate p11 = e1.getCoordinates()[segIndex1 + 1];
            this.f461li.computeIntersection(p00, p01, p10, p11);
            if (this.f461li.hasIntersection() && this.f461li.isInteriorIntersection()) {
                this.intSegments = new Coordinate[4];
                this.intSegments[0] = p00;
                this.intSegments[1] = p01;
                this.intSegments[2] = p10;
                this.intSegments[3] = p11;
                this.interiorIntersection = this.f461li.getIntersection(0);
                if (this.keepIntersections) {
                    boolean add = this.intersections.add(this.interiorIntersection);
                }
                this.intersectionCount++;
            }
        }
    }

    private boolean isEndSegment(SegmentString segmentString, int i) {
        SegmentString segStr = segmentString;
        int index = i;
        if (index == 0) {
            return true;
        }
        if (index >= segStr.size() - 2) {
            return true;
        }
        return false;
    }

    public boolean isDone() {
        if (this.findAllIntersections) {
            return false;
        }
        return this.interiorIntersection != null;
    }
}
