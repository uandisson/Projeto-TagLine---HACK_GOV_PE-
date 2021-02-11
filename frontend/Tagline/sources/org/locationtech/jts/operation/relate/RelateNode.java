package org.locationtech.jts.operation.relate;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.IntersectionMatrix;
import org.locationtech.jts.geomgraph.EdgeEndStar;
import org.locationtech.jts.geomgraph.Node;

public class RelateNode extends Node {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public RelateNode(Coordinate coord, EdgeEndStar edges) {
        super(coord, edges);
    }

    /* access modifiers changed from: protected */
    public void computeIM(IntersectionMatrix im) {
        im.setAtLeastIfValid(this.label.getLocation(0), this.label.getLocation(1), 0);
    }

    /* access modifiers changed from: package-private */
    public void updateIMFromEdges(IntersectionMatrix im) {
        ((EdgeEndBundleStar) this.edges).updateIM(im);
    }
}
