package org.locationtech.jts.dissolve;

import org.locationtech.jts.edgegraph.MarkHalfEdge;
import org.locationtech.jts.geom.Coordinate;

class DissolveHalfEdge extends MarkHalfEdge {
    private boolean isStart = false;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DissolveHalfEdge(Coordinate orig) {
        super(orig);
    }

    public boolean isStart() {
        return this.isStart;
    }

    public void setStart() {
        this.isStart = true;
    }
}
