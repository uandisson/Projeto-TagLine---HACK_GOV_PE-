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

public class MCIndexNoder extends SinglePassNoder {
    private int idCounter = 0;
    private SpatialIndex index;
    private List monoChains;
    private int nOverlaps = 0;
    private Collection nodedSegStrings;

    public MCIndexNoder() {
        List list;
        SpatialIndex spatialIndex;
        new ArrayList();
        this.monoChains = list;
        new STRtree();
        this.index = spatialIndex;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MCIndexNoder(SegmentIntersector si) {
        super(si);
        List list;
        SpatialIndex spatialIndex;
        new ArrayList();
        this.monoChains = list;
        new STRtree();
        this.index = spatialIndex;
    }

    public List getMonotoneChains() {
        return this.monoChains;
    }

    public SpatialIndex getIndex() {
        return this.index;
    }

    public Collection getNodedSubstrings() {
        return NodedSegmentString.getNodedSubstrings(this.nodedSegStrings);
    }

    public void computeNodes(Collection collection) {
        Collection<SegmentString> inputSegStrings = collection;
        this.nodedSegStrings = inputSegStrings;
        for (SegmentString add : inputSegStrings) {
            add(add);
        }
        intersectChains();
    }

    private void intersectChains() {
        MonotoneChainOverlapAction monotoneChainOverlapAction;
        new SegmentOverlapAction(this, this.segInt);
        MonotoneChainOverlapAction overlapAction = monotoneChainOverlapAction;
        for (MonotoneChain queryChain : this.monoChains) {
            Iterator j = this.index.query(queryChain.getEnvelope()).iterator();
            while (true) {
                if (j.hasNext()) {
                    MonotoneChain testChain = (MonotoneChain) j.next();
                    if (testChain.getId() > queryChain.getId()) {
                        queryChain.computeOverlaps(testChain, overlapAction);
                        this.nOverlaps++;
                    }
                    if (this.segInt.isDone()) {
                        return;
                    }
                }
            }
        }
    }

    private void add(SegmentString segmentString) {
        SegmentString segStr = segmentString;
        for (MonotoneChain mc : MonotoneChainBuilder.getChains(segStr.getCoordinates(), segStr)) {
            int i = this.idCounter;
            int i2 = i + 1;
            this.idCounter = i2;
            mc.setId(i);
            this.index.insert(mc.getEnvelope(), mc);
            boolean add = this.monoChains.add(mc);
        }
    }

    public class SegmentOverlapAction extends MonotoneChainOverlapAction {

        /* renamed from: si */
        private SegmentIntersector f467si = null;
        final /* synthetic */ MCIndexNoder this$0;

        public SegmentOverlapAction(MCIndexNoder this$02, SegmentIntersector si) {
            this.this$0 = this$02;
            this.f467si = si;
        }

        public void overlap(MonotoneChain mc1, int start1, MonotoneChain mc2, int start2) {
            SegmentString ss1 = (SegmentString) mc1.getContext();
            SegmentString ss2 = (SegmentString) mc2.getContext();
            this.f467si.processIntersections(ss1, start1, ss2, start2);
        }
    }
}
