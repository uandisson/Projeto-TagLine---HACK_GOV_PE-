package org.locationtech.jts.operation.overlay;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.DirectedEdgeStar;
import org.locationtech.jts.geomgraph.EdgeEndStar;
import org.locationtech.jts.geomgraph.Node;
import org.locationtech.jts.geomgraph.NodeFactory;

public class OverlayNodeFactory extends NodeFactory {
    public OverlayNodeFactory() {
    }

    public Node createNode(Coordinate coord) {
        Node node;
        EdgeEndStar edgeEndStar;
        new DirectedEdgeStar();
        new Node(coord, edgeEndStar);
        return node;
    }
}
