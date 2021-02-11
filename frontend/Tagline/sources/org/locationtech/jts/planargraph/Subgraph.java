package org.locationtech.jts.planargraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Subgraph {
    protected List dirEdges;
    protected Set edges;
    protected NodeMap nodeMap;
    protected PlanarGraph parentGraph;

    public Subgraph(PlanarGraph parentGraph2) {
        Set set;
        List list;
        NodeMap nodeMap2;
        new HashSet();
        this.edges = set;
        new ArrayList();
        this.dirEdges = list;
        new NodeMap();
        this.nodeMap = nodeMap2;
        this.parentGraph = parentGraph2;
    }

    public PlanarGraph getParent() {
        return this.parentGraph;
    }

    public void add(Edge edge) {
        Edge e = edge;
        if (!this.edges.contains(e)) {
            boolean add = this.edges.add(e);
            boolean add2 = this.dirEdges.add(e.getDirEdge(0));
            boolean add3 = this.dirEdges.add(e.getDirEdge(1));
            Node add4 = this.nodeMap.add(e.getDirEdge(0).getFromNode());
            Node add5 = this.nodeMap.add(e.getDirEdge(1).getFromNode());
        }
    }

    public Iterator dirEdgeIterator() {
        return this.dirEdges.iterator();
    }

    public Iterator edgeIterator() {
        return this.edges.iterator();
    }

    public Iterator nodeIterator() {
        return this.nodeMap.iterator();
    }

    public boolean contains(Edge e) {
        return this.edges.contains(e);
    }
}
