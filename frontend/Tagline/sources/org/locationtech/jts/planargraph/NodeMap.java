package org.locationtech.jts.planargraph;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.locationtech.jts.geom.Coordinate;

public class NodeMap {
    private Map nodeMap;

    public NodeMap() {
        Map map;
        new TreeMap();
        this.nodeMap = map;
    }

    public Node add(Node node) {
        Node n = node;
        Object put = this.nodeMap.put(n.getCoordinate(), n);
        return n;
    }

    public Node remove(Coordinate pt) {
        return (Node) this.nodeMap.remove(pt);
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
}
