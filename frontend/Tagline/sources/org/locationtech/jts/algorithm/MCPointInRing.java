package org.locationtech.jts.algorithm;

import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.index.bintree.Bintree;
import org.locationtech.jts.index.bintree.Interval;
import org.locationtech.jts.index.chain.MonotoneChain;
import org.locationtech.jts.index.chain.MonotoneChainBuilder;
import org.locationtech.jts.index.chain.MonotoneChainSelectAction;

public class MCPointInRing implements PointInRing {
    private int crossings = 0;
    private Interval interval;
    private LinearRing ring;
    private Bintree tree;

    class MCSelecter extends MonotoneChainSelectAction {

        /* renamed from: p */
        Coordinate f403p;
        final /* synthetic */ MCPointInRing this$0;

        public MCSelecter(MCPointInRing this$02, Coordinate p) {
            this.this$0 = this$02;
            this.f403p = p;
        }

        public void select(LineSegment ls) {
            this.this$0.testLineSegment(this.f403p, ls);
        }
    }

    public MCPointInRing(LinearRing ring2) {
        Interval interval2;
        new Interval();
        this.interval = interval2;
        this.ring = ring2;
        buildIndex();
    }

    private void buildIndex() {
        Bintree bintree;
        new Bintree();
        this.tree = bintree;
        List mcList = MonotoneChainBuilder.getChains(CoordinateArrays.removeRepeatedPoints(this.ring.getCoordinates()));
        for (int i = 0; i < mcList.size(); i++) {
            MonotoneChain mc = (MonotoneChain) mcList.get(i);
            Envelope mcEnv = mc.getEnvelope();
            this.interval.min = mcEnv.getMinY();
            this.interval.max = mcEnv.getMaxY();
            this.tree.insert(this.interval, mc);
        }
    }

    public boolean isInside(Coordinate coordinate) {
        Envelope envelope;
        MCSelecter mCSelecter;
        Coordinate pt = coordinate;
        this.crossings = 0;
        new Envelope(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, pt.f413y, pt.f413y);
        Envelope rayEnv = envelope;
        this.interval.min = pt.f413y;
        this.interval.max = pt.f413y;
        List<MonotoneChain> segs = this.tree.query(this.interval);
        new MCSelecter(this, pt);
        MCSelecter mcSelecter = mCSelecter;
        for (MonotoneChain mc : segs) {
            testMonotoneChain(rayEnv, mcSelecter, mc);
        }
        if (this.crossings % 2 == 1) {
            return true;
        }
        return false;
    }

    private void testMonotoneChain(Envelope rayEnv, MCSelecter mcSelecter, MonotoneChain mc) {
        mc.select(rayEnv, mcSelecter);
    }

    /* access modifiers changed from: private */
    public void testLineSegment(Coordinate coordinate, LineSegment lineSegment) {
        Coordinate p = coordinate;
        LineSegment seg = lineSegment;
        Coordinate p1 = seg.f422p0;
        Coordinate p2 = seg.f423p1;
        double x1 = p1.f412x - p.f412x;
        double y1 = p1.f413y - p.f413y;
        double x2 = p2.f412x - p.f412x;
        double y2 = p2.f413y - p.f413y;
        if (((y1 > 0.0d && y2 <= 0.0d) || (y2 > 0.0d && y1 <= 0.0d)) && 0.0d < ((double) RobustDeterminant.signOfDet2x2(x1, y1, x2, y2)) / (y2 - y1)) {
            this.crossings++;
        }
    }
}
