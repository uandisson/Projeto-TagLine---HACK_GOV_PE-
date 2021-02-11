package org.locationtech.jts.index.chain;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LineSegment;

public class MonotoneChainOverlapAction {
    protected LineSegment overlapSeg1;
    protected LineSegment overlapSeg2;
    Envelope tempEnv1;
    Envelope tempEnv2;

    public MonotoneChainOverlapAction() {
        Envelope envelope;
        Envelope envelope2;
        LineSegment lineSegment;
        LineSegment lineSegment2;
        new Envelope();
        this.tempEnv1 = envelope;
        new Envelope();
        this.tempEnv2 = envelope2;
        new LineSegment();
        this.overlapSeg1 = lineSegment;
        new LineSegment();
        this.overlapSeg2 = lineSegment2;
    }

    public void overlap(MonotoneChain mc1, int start1, MonotoneChain mc2, int start2) {
        mc1.getLineSegment(start1, this.overlapSeg1);
        mc2.getLineSegment(start2, this.overlapSeg2);
        overlap(this.overlapSeg1, this.overlapSeg2);
    }

    public void overlap(LineSegment seg1, LineSegment seg2) {
    }
}
