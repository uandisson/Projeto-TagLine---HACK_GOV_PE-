package org.locationtech.jts.geomgraph.index;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geomgraph.Edge;

public class MonotoneChainEdge {

    /* renamed from: e */
    Edge f438e;
    Envelope env1;
    Envelope env2;
    Coordinate[] pts;
    int[] startIndex;

    public MonotoneChainEdge(Edge edge) {
        Envelope envelope;
        Envelope envelope2;
        MonotoneChainIndexer mcb;
        Edge e = edge;
        new Envelope();
        this.env1 = envelope;
        new Envelope();
        this.env2 = envelope2;
        this.f438e = e;
        this.pts = e.getCoordinates();
        new MonotoneChainIndexer();
        this.startIndex = mcb.getChainStartIndices(this.pts);
    }

    public Coordinate[] getCoordinates() {
        return this.pts;
    }

    public int[] getStartIndexes() {
        return this.startIndex;
    }

    public double getMinX(int i) {
        int chainIndex = i;
        double x1 = this.pts[this.startIndex[chainIndex]].f412x;
        double x2 = this.pts[this.startIndex[chainIndex + 1]].f412x;
        return x1 < x2 ? x1 : x2;
    }

    public double getMaxX(int i) {
        int chainIndex = i;
        double x1 = this.pts[this.startIndex[chainIndex]].f412x;
        double x2 = this.pts[this.startIndex[chainIndex + 1]].f412x;
        return x1 > x2 ? x1 : x2;
    }

    public void computeIntersects(MonotoneChainEdge monotoneChainEdge, SegmentIntersector segmentIntersector) {
        MonotoneChainEdge mce = monotoneChainEdge;
        SegmentIntersector si = segmentIntersector;
        for (int i = 0; i < this.startIndex.length - 1; i++) {
            for (int j = 0; j < mce.startIndex.length - 1; j++) {
                computeIntersectsForChain(i, mce, j, si);
            }
        }
    }

    public void computeIntersectsForChain(int i, MonotoneChainEdge monotoneChainEdge, int i2, SegmentIntersector si) {
        int chainIndex0 = i;
        MonotoneChainEdge mce = monotoneChainEdge;
        int chainIndex1 = i2;
        computeIntersectsForChain(this.startIndex[chainIndex0], this.startIndex[chainIndex0 + 1], mce, mce.startIndex[chainIndex1], mce.startIndex[chainIndex1 + 1], si);
    }

    private void computeIntersectsForChain(int i, int i2, MonotoneChainEdge monotoneChainEdge, int i3, int i4, SegmentIntersector segmentIntersector) {
        int start0 = i;
        int end0 = i2;
        MonotoneChainEdge mce = monotoneChainEdge;
        int start1 = i3;
        int end1 = i4;
        SegmentIntersector ei = segmentIntersector;
        Coordinate p00 = this.pts[start0];
        Coordinate p01 = this.pts[end0];
        Coordinate p10 = mce.pts[start1];
        Coordinate p11 = mce.pts[end1];
        if (end0 - start0 == 1 && end1 - start1 == 1) {
            ei.addIntersections(this.f438e, start0, mce.f438e, start1);
            return;
        }
        this.env1.init(p00, p01);
        this.env2.init(p10, p11);
        if (this.env1.intersects(this.env2)) {
            int mid0 = (start0 + end0) / 2;
            int mid1 = (start1 + end1) / 2;
            if (start0 < mid0) {
                if (start1 < mid1) {
                    computeIntersectsForChain(start0, mid0, mce, start1, mid1, ei);
                }
                if (mid1 < end1) {
                    computeIntersectsForChain(start0, mid0, mce, mid1, end1, ei);
                }
            }
            if (mid0 < end0) {
                if (start1 < mid1) {
                    computeIntersectsForChain(mid0, end0, mce, start1, mid1, ei);
                }
                if (mid1 < end1) {
                    computeIntersectsForChain(mid0, end0, mce, mid1, end1, ei);
                }
            }
        }
    }
}
