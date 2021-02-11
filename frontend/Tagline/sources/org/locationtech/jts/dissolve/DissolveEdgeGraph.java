package org.locationtech.jts.dissolve;

import org.locationtech.jts.edgegraph.EdgeGraph;
import org.locationtech.jts.edgegraph.HalfEdge;
import org.locationtech.jts.geom.Coordinate;

class DissolveEdgeGraph extends EdgeGraph {
    DissolveEdgeGraph() {
    }

    /* access modifiers changed from: protected */
    public HalfEdge createEdge(Coordinate p0) {
        HalfEdge halfEdge;
        new DissolveHalfEdge(p0);
        return halfEdge;
    }
}
