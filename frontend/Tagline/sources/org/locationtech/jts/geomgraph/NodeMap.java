package org.locationtech.jts.geomgraph;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.locationtech.jts.geom.Coordinate;

public class NodeMap {
    NodeFactory nodeFact;
    Map nodeMap;

    public NodeMap(NodeFactory nodeFact2) {
        Map map;
        new TreeMap();
        this.nodeMap = map;
        this.nodeFact = nodeFact2;
    }

    public Node addNode(Coordinate coordinate) {
        Coordinate coord = coordinate;
        Node node = (Node) this.nodeMap.get(coord);
        if (node == null) {
            node = this.nodeFact.createNode(coord);
            Object put = this.nodeMap.put(coord, node);
        }
        return node;
    }

    public Node addNode(Node node) {
        Node n = node;
        Node node2 = (Node) this.nodeMap.get(n.getCoordinate());
        if (node2 == null) {
            Object put = this.nodeMap.put(n.getCoordinate(), n);
            return n;
        }
        node2.mergeLabel(n);
        return node2;
    }

    public void add(EdgeEnd edgeEnd) {
        EdgeEnd e = edgeEnd;
        addNode(e.getCoordinate()).add(e);
    }

    public Node find(Coordinate coord) {
        return (Node) this.nodeMap.get(coord);
    }

    public Iterator iterator() {
        return this.nodeMap.values().iterator();
    }

    public Collection values() {
        return this.nodeMap.values();
    }

    public Collection getBoundaryNodes(int i) {
        Collection collection;
        int geomIndex = i;
        new ArrayList();
        Collection bdyNodes = collection;
        Iterator i2 = iterator();
        while (i2.hasNext()) {
            Node node = (Node) i2.next();
            if (node.getLabel().getLocation(geomIndex) == 1) {
                boolean add = bdyNodes.add(node);
            }
        }
        return bdyNodes;
    }

    public void print(PrintStream printStream) {
        PrintStream out = printStream;
        Iterator it = iterator();
        while (it.hasNext()) {
            ((Node) it.next()).print(out);
        }
    }
}
