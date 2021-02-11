package org.locationtech.jts.geomgraph;

import com.microsoft.appcenter.Constants;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;

public class PlanarGraph {
    protected List edgeEndList;
    protected List edges;
    protected NodeMap nodes;

    public static void linkResultDirectedEdges(Collection nodes2) {
        Iterator nodeit = nodes2.iterator();
        while (nodeit.hasNext()) {
            ((DirectedEdgeStar) ((Node) nodeit.next()).getEdges()).linkResultDirectedEdges();
        }
    }

    public PlanarGraph(NodeFactory nodeFact) {
        List list;
        List list2;
        NodeMap nodeMap;
        new ArrayList();
        this.edges = list;
        new ArrayList();
        this.edgeEndList = list2;
        new NodeMap(nodeFact);
        this.nodes = nodeMap;
    }

    public PlanarGraph() {
        List list;
        List list2;
        NodeMap nodeMap;
        NodeFactory nodeFactory;
        new ArrayList();
        this.edges = list;
        new ArrayList();
        this.edgeEndList = list2;
        new NodeFactory();
        new NodeMap(nodeFactory);
        this.nodes = nodeMap;
    }

    public Iterator getEdgeIterator() {
        return this.edges.iterator();
    }

    public Collection getEdgeEnds() {
        return this.edgeEndList;
    }

    public boolean isBoundaryNode(int i, Coordinate coord) {
        int geomIndex = i;
        Node node = this.nodes.find(coord);
        if (node == null) {
            return false;
        }
        Label label = node.getLabel();
        if (label == null || label.getLocation(geomIndex) != 1) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void insertEdge(Edge e) {
        boolean add = this.edges.add(e);
    }

    public void add(EdgeEnd edgeEnd) {
        EdgeEnd e = edgeEnd;
        this.nodes.add(e);
        boolean add = this.edgeEndList.add(e);
    }

    public Iterator getNodeIterator() {
        return this.nodes.iterator();
    }

    public Collection getNodes() {
        return this.nodes.values();
    }

    public Node addNode(Node node) {
        return this.nodes.addNode(node);
    }

    public Node addNode(Coordinate coord) {
        return this.nodes.addNode(coord);
    }

    public Node find(Coordinate coord) {
        return this.nodes.find(coord);
    }

    public void addEdges(List edgesToAdd) {
        DirectedEdge directedEdge;
        DirectedEdge directedEdge2;
        Iterator it = edgesToAdd.iterator();
        while (it.hasNext()) {
            Edge e = (Edge) it.next();
            boolean add = this.edges.add(e);
            new DirectedEdge(e, true);
            DirectedEdge de1 = directedEdge;
            new DirectedEdge(e, false);
            DirectedEdge de2 = directedEdge2;
            de1.setSym(de2);
            de2.setSym(de1);
            add(de1);
            add(de2);
        }
    }

    public void linkResultDirectedEdges() {
        Iterator nodeit = this.nodes.iterator();
        while (nodeit.hasNext()) {
            ((DirectedEdgeStar) ((Node) nodeit.next()).getEdges()).linkResultDirectedEdges();
        }
    }

    public void linkAllDirectedEdges() {
        Iterator nodeit = this.nodes.iterator();
        while (nodeit.hasNext()) {
            ((DirectedEdgeStar) ((Node) nodeit.next()).getEdges()).linkAllDirectedEdges();
        }
    }

    public EdgeEnd findEdgeEnd(Edge edge) {
        Edge e = edge;
        for (EdgeEnd ee : getEdgeEnds()) {
            if (ee.getEdge() == e) {
                return ee;
            }
        }
        return null;
    }

    public Edge findEdge(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        for (int i = 0; i < this.edges.size(); i++) {
            Edge e = (Edge) this.edges.get(i);
            Coordinate[] eCoord = e.getCoordinates();
            if (p0.equals(eCoord[0]) && p1.equals(eCoord[1])) {
                return e;
            }
        }
        return null;
    }

    public Edge findEdgeInSameDirection(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        for (int i = 0; i < this.edges.size(); i++) {
            Edge e = (Edge) this.edges.get(i);
            Coordinate[] eCoord = e.getCoordinates();
            if (matchInSameDirection(p0, p1, eCoord[0], eCoord[1])) {
                return e;
            }
            if (matchInSameDirection(p0, p1, eCoord[eCoord.length - 1], eCoord[eCoord.length - 2])) {
                return e;
            }
        }
        return null;
    }

    private boolean matchInSameDirection(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        Coordinate ep0 = coordinate3;
        Coordinate ep1 = coordinate4;
        if (!p0.equals(ep0)) {
            return false;
        }
        if (CGAlgorithms.computeOrientation(p0, p1, ep1) == 0 && Quadrant.quadrant(p0, p1) == Quadrant.quadrant(ep0, ep1)) {
            return true;
        }
        return false;
    }

    public void printEdges(PrintStream printStream) {
        StringBuilder sb;
        PrintStream out = printStream;
        out.println("Edges:");
        for (int i = 0; i < this.edges.size(); i++) {
            new StringBuilder();
            out.println(sb.append("edge ").append(i).append(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR).toString());
            Edge e = (Edge) this.edges.get(i);
            e.print(out);
            e.eiList.print(out);
        }
    }

    /* access modifiers changed from: package-private */
    public void debugPrint(Object o) {
        System.out.print(o);
    }

    /* access modifiers changed from: package-private */
    public void debugPrintln(Object o) {
        System.out.println(o);
    }
}
