package org.locationtech.jts.operation.buffer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geomgraph.DirectedEdge;

class SubgraphDepthLocater {
    private CGAlgorithms cga;
    private LineSegment seg;
    private Collection subgraphs;

    public SubgraphDepthLocater(List subgraphs2) {
        LineSegment lineSegment;
        CGAlgorithms cGAlgorithms;
        new LineSegment();
        this.seg = lineSegment;
        new CGAlgorithms();
        this.cga = cGAlgorithms;
        this.subgraphs = subgraphs2;
    }

    public int getDepth(Coordinate p) {
        List stabbedSegments = findStabbedSegments(p);
        if (stabbedSegments.size() == 0) {
            return 0;
        }
        return ((DepthSegment) Collections.min(stabbedSegments)).leftDepth;
    }

    private List findStabbedSegments(Coordinate coordinate) {
        List list;
        Coordinate stabbingRayLeftPt = coordinate;
        new ArrayList();
        List stabbedSegments = list;
        for (BufferSubgraph bsg : this.subgraphs) {
            Envelope env = bsg.getEnvelope();
            if (stabbingRayLeftPt.f413y >= env.getMinY() && stabbingRayLeftPt.f413y <= env.getMaxY()) {
                findStabbedSegments(stabbingRayLeftPt, bsg.getDirectedEdges(), stabbedSegments);
            }
        }
        return stabbedSegments;
    }

    private void findStabbedSegments(Coordinate coordinate, List dirEdges, List list) {
        Coordinate stabbingRayLeftPt = coordinate;
        List stabbedSegments = list;
        Iterator i = dirEdges.iterator();
        while (i.hasNext()) {
            DirectedEdge de = (DirectedEdge) i.next();
            if (de.isForward()) {
                findStabbedSegments(stabbingRayLeftPt, de, stabbedSegments);
            }
        }
    }

    private void findStabbedSegments(Coordinate coordinate, DirectedEdge directedEdge, List list) {
        Object obj;
        Coordinate stabbingRayLeftPt = coordinate;
        DirectedEdge dirEdge = directedEdge;
        List stabbedSegments = list;
        Coordinate[] pts = dirEdge.getEdge().getCoordinates();
        for (int i = 0; i < pts.length - 1; i++) {
            this.seg.f422p0 = pts[i];
            this.seg.f423p1 = pts[i + 1];
            if (this.seg.f422p0.f413y > this.seg.f423p1.f413y) {
                this.seg.reverse();
            }
            if (Math.max(this.seg.f422p0.f412x, this.seg.f423p1.f412x) >= stabbingRayLeftPt.f412x && !this.seg.isHorizontal() && stabbingRayLeftPt.f413y >= this.seg.f422p0.f413y && stabbingRayLeftPt.f413y <= this.seg.f423p1.f413y && CGAlgorithms.computeOrientation(this.seg.f422p0, this.seg.f423p1, stabbingRayLeftPt) != -1) {
                int depth = dirEdge.getDepth(1);
                if (!this.seg.f422p0.equals(pts[i])) {
                    depth = dirEdge.getDepth(2);
                }
                new DepthSegment(this.seg, depth);
                boolean add = stabbedSegments.add(obj);
            }
        }
    }

    static class DepthSegment implements Comparable {
        /* access modifiers changed from: private */
        public int leftDepth;
        private LineSegment upwardSeg;

        public DepthSegment(LineSegment seg, int depth) {
            LineSegment lineSegment;
            new LineSegment(seg);
            this.upwardSeg = lineSegment;
            this.leftDepth = depth;
        }

        public int compareTo(Object obj) {
            DepthSegment other = (DepthSegment) obj;
            if (this.upwardSeg.minX() >= other.upwardSeg.maxX()) {
                return 1;
            }
            if (this.upwardSeg.maxX() <= other.upwardSeg.minX()) {
                return -1;
            }
            int orientIndex = this.upwardSeg.orientationIndex(other.upwardSeg);
            if (orientIndex != 0) {
                return orientIndex;
            }
            int orientIndex2 = -1 * other.upwardSeg.orientationIndex(this.upwardSeg);
            if (orientIndex2 != 0) {
                return orientIndex2;
            }
            return this.upwardSeg.compareTo(other.upwardSeg);
        }

        private int compareX(LineSegment lineSegment, LineSegment lineSegment2) {
            LineSegment seg0 = lineSegment;
            LineSegment seg1 = lineSegment2;
            int compare0 = seg0.f422p0.compareTo(seg1.f422p0);
            if (compare0 != 0) {
                return compare0;
            }
            return seg0.f423p1.compareTo(seg1.f423p1);
        }

        public String toString() {
            return this.upwardSeg.toString();
        }
    }
}
