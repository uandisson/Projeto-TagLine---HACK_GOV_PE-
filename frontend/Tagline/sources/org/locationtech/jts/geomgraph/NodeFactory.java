package org.locationtech.jts.geomgraph;

import org.locationtech.jts.geom.Coordinate;

public class NodeFactory {
    public NodeFactory() {
    }

    public Node createNode(Coordinate coord) {
        Node node;
        new Node(coord, (EdgeEndStar) null);
        return node;
    }
}
