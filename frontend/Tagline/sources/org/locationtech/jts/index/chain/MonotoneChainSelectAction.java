package org.locationtech.jts.index.chain;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.LineSegment;

public class MonotoneChainSelectAction {
    LineSegment selectedSegment;
    Envelope tempEnv1;

    public MonotoneChainSelectAction() {
        Envelope envelope;
        LineSegment lineSegment;
        new Envelope();
        this.tempEnv1 = envelope;
        new LineSegment();
        this.selectedSegment = lineSegment;
    }

    public void select(MonotoneChain mc, int startIndex) {
        mc.getLineSegment(startIndex, this.selectedSegment);
        select(this.selectedSegment);
    }

    public void select(LineSegment seg) {
    }
}
