package org.locationtech.jts.planargraph.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.locationtech.jts.planargraph.DirectedEdge;
import org.locationtech.jts.planargraph.Edge;
import org.locationtech.jts.planargraph.GraphComponent;
import org.locationtech.jts.planargraph.Node;
import org.locationtech.jts.planargraph.PlanarGraph;
import org.locationtech.jts.planargraph.Subgraph;

public class ConnectedSubgraphFinder {
    private PlanarGraph graph;

    public ConnectedSubgraphFinder(PlanarGraph graph2) {
        this.graph = graph2;
    }

    public List getConnectedSubgraphs() {
        List list;
        new ArrayList();
        List subgraphs = list;
        GraphComponent.setVisited(this.graph.nodeIterator(), false);
        Iterator i = this.graph.edgeIterator();
        while (i.hasNext()) {
            Node node = ((Edge) i.next()).getDirEdge(0).getFromNode();
            if (!node.isVisited()) {
                boolean add = subgraphs.add(findSubgraph(node));
            }
        }
        return subgraphs;
    }

    private Subgraph findSubgraph(Node node) {
        Subgraph subgraph;
        new Subgraph(this.graph);
        Subgraph subgraph2 = subgraph;
        addReachable(node, subgraph2);
        return subgraph2;
    }

    private void addReachable(Node startNode, Subgraph subgraph) {
        Stack stack;
        Subgraph subgraph2 = subgraph;
        new Stack();
        Stack nodeStack = stack;
        boolean add = nodeStack.add(startNode);
        while (!nodeStack.empty()) {
            addEdges((Node) nodeStack.pop(), nodeStack, subgraph2);
        }
    }

    private void addEdges(Node node, Stack stack, Subgraph subgraph) {
        Node node2 = node;
        Stack nodeStack = stack;
        Subgraph subgraph2 = subgraph;
        node2.setVisited(true);
        Iterator i = node2.getOutEdges().iterator();
        while (i.hasNext()) {
            DirectedEdge de = (DirectedEdge) i.next();
            subgraph2.add(de.getEdge());
            Node toNode = de.getToNode();
            if (!toNode.isVisited()) {
                Object push = nodeStack.push(toNode);
            }
        }
    }
}
