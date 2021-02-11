package org.locationtech.jts.operation.relate;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.EdgeEndStar;
import org.locationtech.jts.geomgraph.Node;
import org.locationtech.jts.geomgraph.NodeFactory;

public class RelateNodeFactory extends NodeFactory {
    public RelateNodeFactory() {
    }

    public Node createNode(Coordinate coord) {
        Node node;
        EdgeEndStar edgeEndStar;
        new EdgeEndBundleStar();
        new RelateNode(coord, edgeEndStar);
        return node;
    }
}
