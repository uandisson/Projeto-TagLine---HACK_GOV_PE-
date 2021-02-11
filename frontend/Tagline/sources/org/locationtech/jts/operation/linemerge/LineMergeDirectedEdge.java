package org.locationtech.jts.operation.linemerge;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.planargraph.DirectedEdge;
import org.locationtech.jts.planargraph.Node;
import org.locationtech.jts.util.Assert;

public class LineMergeDirectedEdge extends DirectedEdge {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LineMergeDirectedEdge(Node from, Node to, Coordinate directionPt, boolean edgeDirection) {
        super(from, to, directionPt, edgeDirection);
    }

    public LineMergeDirectedEdge getNext() {
        if (getToNode().getDegree() != 2) {
            return null;
        }
        if (getToNode().getOutEdges().getEdges().get(0) == getSym()) {
            return (LineMergeDirectedEdge) getToNode().getOutEdges().getEdges().get(1);
        }
        Assert.isTrue(getToNode().getOutEdges().getEdges().get(1) == getSym());
        return (LineMergeDirectedEdge) getToNode().getOutEdges().getEdges().get(0);
    }
}
