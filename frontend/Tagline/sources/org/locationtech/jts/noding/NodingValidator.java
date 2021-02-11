package org.locationtech.jts.noding;

import java.util.Collection;
import java.util.Iterator;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

public class NodingValidator {
    private static final GeometryFactory fact;

    /* renamed from: li */
    private LineIntersector f469li;
    private Collection segStrings;

    static {
        GeometryFactory geometryFactory;
        new GeometryFactory();
        fact = geometryFactory;
    }

    public NodingValidator(Collection segStrings2) {
        LineIntersector lineIntersector;
        new RobustLineIntersector();
        this.f469li = lineIntersector;
        this.segStrings = segStrings2;
    }

    public void checkValid() {
        checkEndPtVertexIntersections();
        checkInteriorIntersections();
        checkCollapses();
    }

    private void checkCollapses() {
        for (SegmentString ss : this.segStrings) {
            checkCollapses(ss);
        }
    }

    private void checkCollapses(SegmentString ss) {
        Coordinate[] pts = ss.getCoordinates();
        for (int i = 0; i < pts.length - 2; i++) {
            checkCollapse(pts[i], pts[i + 1], pts[i + 2]);
        }
    }

    private void checkCollapse(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Throwable th;
        StringBuilder sb;
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        Coordinate p2 = coordinate3;
        if (p0.equals(p2)) {
            Throwable th2 = th;
            new StringBuilder();
            StringBuilder append = sb.append("found non-noded collapse at ");
            GeometryFactory geometryFactory = fact;
            Coordinate[] coordinateArr = new Coordinate[3];
            coordinateArr[0] = p0;
            Coordinate[] coordinateArr2 = coordinateArr;
            coordinateArr2[1] = p1;
            Coordinate[] coordinateArr3 = coordinateArr2;
            coordinateArr3[2] = p2;
            new RuntimeException(append.append(geometryFactory.createLineString(coordinateArr3)).toString());
            throw th2;
        }
    }

    private void checkInteriorIntersections() {
        for (SegmentString ss0 : this.segStrings) {
            for (SegmentString ss1 : this.segStrings) {
                checkInteriorIntersections(ss0, ss1);
            }
        }
    }

    private void checkInteriorIntersections(SegmentString segmentString, SegmentString segmentString2) {
        SegmentString ss0 = segmentString;
        SegmentString ss1 = segmentString2;
        Coordinate[] pts0 = ss0.getCoordinates();
        Coordinate[] pts1 = ss1.getCoordinates();
        for (int i0 = 0; i0 < pts0.length - 1; i0++) {
            for (int i1 = 0; i1 < pts1.length - 1; i1++) {
                checkInteriorIntersections(ss0, i0, ss1, i1);
            }
        }
    }

    private void checkInteriorIntersections(SegmentString segmentString, int i, SegmentString segmentString2, int i2) {
        Throwable th;
        StringBuilder sb;
        SegmentString e0 = segmentString;
        int segIndex0 = i;
        SegmentString e1 = segmentString2;
        int segIndex1 = i2;
        if (e0 != e1 || segIndex0 != segIndex1) {
            Coordinate p00 = e0.getCoordinates()[segIndex0];
            Coordinate p01 = e0.getCoordinates()[segIndex0 + 1];
            Coordinate p10 = e1.getCoordinates()[segIndex1];
            Coordinate p11 = e1.getCoordinates()[segIndex1 + 1];
            this.f469li.computeIntersection(p00, p01, p10, p11);
            if (!this.f469li.hasIntersection()) {
                return;
            }
            if (this.f469li.isProper() || hasInteriorIntersection(this.f469li, p00, p01) || hasInteriorIntersection(this.f469li, p10, p11)) {
                Throwable th2 = th;
                new StringBuilder();
                new RuntimeException(sb.append("found non-noded intersection at ").append(p00).append("-").append(p01).append(" and ").append(p10).append("-").append(p11).toString());
                throw th2;
            }
        }
    }

    private boolean hasInteriorIntersection(LineIntersector lineIntersector, Coordinate coordinate, Coordinate coordinate2) {
        LineIntersector li = lineIntersector;
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        for (int i = 0; i < li.getIntersectionNum(); i++) {
            Coordinate intPt = li.getIntersection(i);
            if (!intPt.equals(p0) && !intPt.equals(p1)) {
                return true;
            }
        }
        return false;
    }

    private void checkEndPtVertexIntersections() {
        for (SegmentString ss : this.segStrings) {
            Coordinate[] pts = ss.getCoordinates();
            checkEndPtVertexIntersections(pts[0], this.segStrings);
            checkEndPtVertexIntersections(pts[pts.length - 1], this.segStrings);
        }
    }

    private void checkEndPtVertexIntersections(Coordinate coordinate, Collection segStrings2) {
        Throwable th;
        StringBuilder sb;
        Coordinate testPt = coordinate;
        Iterator i = segStrings2.iterator();
        while (i.hasNext()) {
            Coordinate[] pts = ((SegmentString) i.next()).getCoordinates();
            int j = 1;
            while (true) {
                if (j < pts.length - 1) {
                    if (pts[j].equals(testPt)) {
                        Throwable th2 = th;
                        new StringBuilder();
                        new RuntimeException(sb.append("found endpt/interior pt intersection at index ").append(j).append(" :pt ").append(testPt).toString());
                        throw th2;
                    }
                    j++;
                }
            }
        }
    }
}
