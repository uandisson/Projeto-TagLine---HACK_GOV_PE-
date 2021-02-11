package org.locationtech.jts.operation.relate;

import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.EdgeEnd;
import org.locationtech.jts.geomgraph.EdgeIntersection;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.geomgraph.Node;
import org.locationtech.jts.geomgraph.NodeFactory;
import org.locationtech.jts.geomgraph.NodeMap;

public class RelateNodeGraph {
    private NodeMap nodes;

    public RelateNodeGraph() {
        NodeMap nodeMap;
        NodeFactory nodeFactory;
        new RelateNodeFactory();
        new NodeMap(nodeFactory);
        this.nodes = nodeMap;
    }

    public Iterator getNodeIterator() {
        return this.nodes.iterator();
    }

    public void build(GeometryGraph geometryGraph) {
        EdgeEndBuilder eeBuilder;
        GeometryGraph geomGraph = geometryGraph;
        computeIntersectionNodes(geomGraph, 0);
        copyNodesAndLabels(geomGraph, 0);
        new EdgeEndBuilder();
        insertEdgeEnds(eeBuilder.computeEdgeEnds(geomGraph.getEdgeIterator()));
    }

    public void computeIntersectionNodes(GeometryGraph geomGraph, int i) {
        int argIndex = i;
        Iterator edgeIt = geomGraph.getEdgeIterator();
        while (edgeIt.hasNext()) {
            Edge e = (Edge) edgeIt.next();
            int eLoc = e.getLabel().getLocation(argIndex);
            Iterator eiIt = e.getEdgeIntersectionList().iterator();
            while (eiIt.hasNext()) {
                RelateNode n = (RelateNode) this.nodes.addNode(((EdgeIntersection) eiIt.next()).coord);
                if (eLoc == 1) {
                    n.setLabelBoundary(argIndex);
                } else if (n.getLabel().isNull(argIndex)) {
                    n.setLabel(argIndex, 0);
                }
            }
        }
    }

    public void copyNodesAndLabels(GeometryGraph geomGraph, int i) {
        int argIndex = i;
        Iterator nodeIt = geomGraph.getNodeIterator();
        while (nodeIt.hasNext()) {
            Node graphNode = (Node) nodeIt.next();
            this.nodes.addNode(graphNode.getCoordinate()).setLabel(argIndex, graphNode.getLabel().getLocation(argIndex));
        }
    }

    public void insertEdgeEnds(List ee) {
        Iterator i = ee.iterator();
        while (i.hasNext()) {
            this.nodes.add((EdgeEnd) i.next());
        }
    }
}
