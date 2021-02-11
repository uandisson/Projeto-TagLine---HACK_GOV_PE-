package org.locationtech.jts.operation.overlay;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geomgraph.DirectedEdge;
import org.locationtech.jts.geomgraph.EdgeRing;

public class MinimalEdgeRing extends EdgeRing {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MinimalEdgeRing(DirectedEdge start, GeometryFactory geometryFactory) {
        super(start, geometryFactory);
    }

    public DirectedEdge getNext(DirectedEdge de) {
        return de.getNextMin();
    }

    public void setEdgeRing(DirectedEdge de, EdgeRing er) {
        de.setMinEdgeRing(er);
    }
}
