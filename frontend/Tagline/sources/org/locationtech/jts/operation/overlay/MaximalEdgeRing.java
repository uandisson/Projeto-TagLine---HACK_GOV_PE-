package org.locationtech.jts.operation.overlay;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geomgraph.DirectedEdge;
import org.locationtech.jts.geomgraph.DirectedEdgeStar;
import org.locationtech.jts.geomgraph.EdgeRing;

public class MaximalEdgeRing extends EdgeRing {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MaximalEdgeRing(DirectedEdge start, GeometryFactory geometryFactory) {
        super(start, geometryFactory);
    }

    public DirectedEdge getNext(DirectedEdge de) {
        return de.getNext();
    }

    public void setEdgeRing(DirectedEdge de, EdgeRing er) {
        de.setEdgeRing(er);
    }

    public void linkDirectedEdgesForMinimalEdgeRings() {
        DirectedEdge de = this.startDe;
        do {
            ((DirectedEdgeStar) de.getNode().getEdges()).linkMinimalDirectedEdges(this);
            de = de.getNext();
        } while (de != this.startDe);
    }

    public List buildMinimalRings() {
        List list;
        Object obj;
        new ArrayList();
        List minEdgeRings = list;
        DirectedEdge de = this.startDe;
        do {
            if (de.getMinEdgeRing() == null) {
                new MinimalEdgeRing(de, this.geometryFactory);
                boolean add = minEdgeRings.add(obj);
            }
            de = de.getNext();
        } while (de != this.startDe);
        return minEdgeRings;
    }
}
