package org.locationtech.jts.operation.valid;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.index.sweepline.SweepLineIndex;
import org.locationtech.jts.index.sweepline.SweepLineInterval;
import org.locationtech.jts.index.sweepline.SweepLineOverlapAction;
import org.locationtech.jts.util.Assert;

public class SweeplineNestedRingTester {
    private GeometryGraph graph;
    private Coordinate nestedPt = null;
    private List rings;
    private SweepLineIndex sweepLine;

    public SweeplineNestedRingTester(GeometryGraph graph2) {
        List list;
        new ArrayList();
        this.rings = list;
        this.graph = graph2;
    }

    public Coordinate getNestedPoint() {
        return this.nestedPt;
    }

    public void add(LinearRing ring) {
        boolean add = this.rings.add(ring);
    }

    public boolean isNonNested() {
        OverlapAction overlapAction;
        buildIndex();
        new OverlapAction(this);
        OverlapAction action = overlapAction;
        this.sweepLine.computeOverlaps(action);
        return action.isNonNested;
    }

    private void buildIndex() {
        SweepLineIndex sweepLineIndex;
        SweepLineInterval sweepInt;
        new SweepLineIndex();
        this.sweepLine = sweepLineIndex;
        for (int i = 0; i < this.rings.size(); i++) {
            LinearRing ring = (LinearRing) this.rings.get(i);
            Envelope env = ring.getEnvelopeInternal();
            new SweepLineInterval(env.getMinX(), env.getMaxX(), ring);
            this.sweepLine.add(sweepInt);
        }
    }

    /* access modifiers changed from: private */
    public boolean isInside(LinearRing linearRing, LinearRing linearRing2) {
        LinearRing innerRing = linearRing;
        LinearRing searchRing = linearRing2;
        Coordinate[] innerRingPts = innerRing.getCoordinates();
        Coordinate[] searchRingPts = searchRing.getCoordinates();
        if (!innerRing.getEnvelopeInternal().intersects(searchRing.getEnvelopeInternal())) {
            return false;
        }
        Coordinate innerRingPt = IsValidOp.findPtNotNode(innerRingPts, searchRing, this.graph);
        Assert.isTrue(innerRingPt != null, "Unable to find a ring point not a node of the search ring");
        if (!CGAlgorithms.isPointInRing(innerRingPt, searchRingPts)) {
            return false;
        }
        this.nestedPt = innerRingPt;
        return true;
    }

    class OverlapAction implements SweepLineOverlapAction {
        boolean isNonNested = true;
        final /* synthetic */ SweeplineNestedRingTester this$0;

        OverlapAction(SweeplineNestedRingTester this$02) {
            this.this$0 = this$02;
        }

        public void overlap(SweepLineInterval s0, SweepLineInterval s1) {
            LinearRing innerRing = (LinearRing) s0.getItem();
            LinearRing searchRing = (LinearRing) s1.getItem();
            if (innerRing != searchRing && this.this$0.isInside(innerRing, searchRing)) {
                this.isNonNested = false;
            }
        }
    }
}
