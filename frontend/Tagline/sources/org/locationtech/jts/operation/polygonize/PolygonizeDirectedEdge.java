package org.locationtech.jts.operation.polygonize;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.planargraph.DirectedEdge;
import org.locationtech.jts.planargraph.Node;

class PolygonizeDirectedEdge extends DirectedEdge {
    private EdgeRing edgeRing = null;
    private long label = -1;
    private PolygonizeDirectedEdge next = null;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PolygonizeDirectedEdge(Node from, Node to, Coordinate directionPt, boolean edgeDirection) {
        super(from, to, directionPt, edgeDirection);
    }

    public long getLabel() {
        return this.label;
    }

    public void setLabel(long label2) {
        long j = label2;
        this.label = j;
    }

    public PolygonizeDirectedEdge getNext() {
        return this.next;
    }

    public void setNext(PolygonizeDirectedEdge next2) {
        PolygonizeDirectedEdge polygonizeDirectedEdge = next2;
        this.next = polygonizeDirectedEdge;
    }

    public boolean isInRing() {
        return this.edgeRing != null;
    }

    public void setRing(EdgeRing edgeRing2) {
        EdgeRing edgeRing3 = edgeRing2;
        this.edgeRing = edgeRing3;
    }

    public EdgeRing getRing() {
        return this.edgeRing;
    }
}
