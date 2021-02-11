package org.locationtech.jts.noding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.index.SpatialIndex;
import org.locationtech.jts.index.chain.MonotoneChain;
import org.locationtech.jts.index.chain.MonotoneChainBuilder;
import org.locationtech.jts.index.chain.MonotoneChainOverlapAction;
import org.locationtech.jts.index.strtree.STRtree;

public class MCIndexSegmentSetMutualIntersector implements SegmentSetMutualIntersector {
    private STRtree index;

    public MCIndexSegmentSetMutualIntersector(Collection baseSegStrings) {
        STRtree sTRtree;
        new STRtree();
        this.index = sTRtree;
        initBaseSegments(baseSegStrings);
    }

    public SpatialIndex getIndex() {
        return this.index;
    }

    private void initBaseSegments(Collection segStrings) {
        Iterator i = segStrings.iterator();
        while (i.hasNext()) {
            addToIndex((SegmentString) i.next());
        }
        this.index.build();
    }

    private void addToIndex(SegmentString segmentString) {
        SegmentString segStr = segmentString;
        for (MonotoneChain mc : MonotoneChainBuilder.getChains(segStr.getCoordinates(), segStr)) {
            this.index.insert(mc.getEnvelope(), mc);
        }
    }

    public void process(Collection segStrings, SegmentIntersector segmentIntersector) {
        List list;
        SegmentIntersector segInt = segmentIntersector;
        new ArrayList();
        List monoChains = list;
        Iterator i = segStrings.iterator();
        while (i.hasNext()) {
            addToMonoChains((SegmentString) i.next(), monoChains);
        }
        intersectChains(monoChains, segInt);
    }

    private void addToMonoChains(SegmentString segmentString, List list) {
        SegmentString segStr = segmentString;
        List monoChains = list;
        for (MonotoneChain mc : MonotoneChainBuilder.getChains(segStr.getCoordinates(), segStr)) {
            boolean add = monoChains.add(mc);
        }
    }

    private void intersectChains(List monoChains, SegmentIntersector segmentIntersector) {
        MonotoneChainOverlapAction monotoneChainOverlapAction;
        SegmentIntersector segInt = segmentIntersector;
        new SegmentOverlapAction(this, segInt);
        MonotoneChainOverlapAction overlapAction = monotoneChainOverlapAction;
        Iterator i = monoChains.iterator();
        while (i.hasNext()) {
            MonotoneChain queryChain = (MonotoneChain) i.next();
            Iterator j = this.index.query(queryChain.getEnvelope()).iterator();
            while (true) {
                if (j.hasNext()) {
                    queryChain.computeOverlaps((MonotoneChain) j.next(), overlapAction);
                    if (segInt.isDone()) {
                        return;
                    }
                }
            }
        }
    }

    public class SegmentOverlapAction extends MonotoneChainOverlapAction {

        /* renamed from: si */
        private SegmentIntersector f468si = null;
        final /* synthetic */ MCIndexSegmentSetMutualIntersector this$0;

        public SegmentOverlapAction(MCIndexSegmentSetMutualIntersector this$02, SegmentIntersector si) {
            this.this$0 = this$02;
            this.f468si = si;
        }

        public void overlap(MonotoneChain mc1, int start1, MonotoneChain mc2, int start2) {
            SegmentString ss1 = (SegmentString) mc1.getContext();
            SegmentString ss2 = (SegmentString) mc2.getContext();
            this.f468si.processIntersections(ss1, start1, ss2, start2);
        }
    }
}
