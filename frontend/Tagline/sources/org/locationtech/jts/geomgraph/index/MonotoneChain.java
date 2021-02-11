package org.locationtech.jts.geomgraph.index;

public class MonotoneChain {
    int chainIndex;
    MonotoneChainEdge mce;

    public MonotoneChain(MonotoneChainEdge mce2, int chainIndex2) {
        this.mce = mce2;
        this.chainIndex = chainIndex2;
    }

    public void computeIntersections(MonotoneChain monotoneChain, SegmentIntersector si) {
        MonotoneChain mc = monotoneChain;
        this.mce.computeIntersectsForChain(this.chainIndex, mc.mce, mc.chainIndex, si);
    }
}
