package org.locationtech.jts.planargraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.locationtech.jts.geom.Coordinate;

public abstract class PlanarGraph {
    protected Set dirEdges;
    protected Set edges;
    protected NodeMap nodeMap;

    public PlanarGraph() {
        Set set;
        Set set2;
        NodeMap nodeMap2;
        new HashSet();
        this.edges = set;
        new HashSet();
        this.dirEdges = set2;
        new NodeMap();
        this.nodeMap = nodeMap2;
    }

    public Node findNode(Coordinate pt) {
        return this.nodeMap.find(pt);
    }

    /* access modifiers changed from: protected */
    public void add(Node node) {
        Node add = this.nodeMap.add(node);
    }

    /* access modifiers changed from: protected */
    public void add(Edge edge) {
        Edge edge2 = edge;
        boolean add = this.edges.add(edge2);
        add(edge2.getDirEdge(0));
        add(edge2.getDirEdge(1));
    }

    /* access modifiers changed from: protected */
    public void add(DirectedEdge dirEdge) {
        boolean add = this.dirEdges.add(dirEdge);
    }

    public Iterator nodeIterator() {
        return this.nodeMap.iterator();
    }

    public boolean contains(Edge e) {
        return this.edges.contains(e);
    }

    public boolean contains(DirectedEdge de) {
        return this.dirEdges.contains(de);
    }

    public Collection getNodes() {
        return this.nodeMap.values();
    }

    public Iterator dirEdgeIterator() {
        return this.dirEdges.iterator();
    }

    public Iterator edgeIterator() {
        return this.edges.iterator();
    }

    public Collection getEdges() {
        return this.edges;
    }

    public void remove(Edge edge) {
        Edge edge2 = edge;
        remove(edge2.getDirEdge(0));
        remove(edge2.getDirEdge(1));
        boolean remove = this.edges.remove(edge2);
        edge2.remove();
    }

    public void remove(DirectedEdge directedEdge) {
        DirectedEdge de = directedEdge;
        DirectedEdge sym = de.getSym();
        if (sym != null) {
            sym.setSym((DirectedEdge) null);
        }
        de.getFromNode().remove(de);
        de.remove();
        boolean remove = this.dirEdges.remove(de);
    }

    public void remove(Node node) {
        Node node2 = node;
        for (DirectedEdge de : node2.getOutEdges().getEdges()) {
            DirectedEdge sym = de.getSym();
            if (sym != null) {
                remove(sym);
            }
            boolean remove = this.dirEdges.remove(de);
            Edge edge = de.getEdge();
            if (edge != null) {
                boolean remove2 = this.edges.remove(edge);
            }
        }
        Node remove3 = this.nodeMap.remove(node2.getCoordinate());
        node2.remove();
    }

    public List findNodesOfDegree(int i) {
        List list;
        int degree = i;
        new ArrayList();
        List nodesFound = list;
        Iterator i2 = nodeIterator();
        while (i2.hasNext()) {
            Node node = (Node) i2.next();
            if (node.getDegree() == degree) {
                boolean add = nodesFound.add(node);
            }
        }
        return nodesFound;
    }
}
