package org.locationtech.jts.operation.buffer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geomgraph.DirectedEdge;
import org.locationtech.jts.geomgraph.DirectedEdgeStar;
import org.locationtech.jts.geomgraph.Label;
import org.locationtech.jts.geomgraph.Node;

class BufferSubgraph implements Comparable {
    private List dirEdgeList;
    private Envelope env = null;
    private RightmostEdgeFinder finder;
    private List nodes;
    private Coordinate rightMostCoord = null;

    public BufferSubgraph() {
        List list;
        List list2;
        RightmostEdgeFinder rightmostEdgeFinder;
        new ArrayList();
        this.dirEdgeList = list;
        new ArrayList();
        this.nodes = list2;
        new RightmostEdgeFinder();
        this.finder = rightmostEdgeFinder;
    }

    public List getDirectedEdges() {
        return this.dirEdgeList;
    }

    public List getNodes() {
        return this.nodes;
    }

    public Envelope getEnvelope() {
        Envelope envelope;
        if (this.env == null) {
            new Envelope();
            Envelope edgeEnv = envelope;
            for (DirectedEdge dirEdge : this.dirEdgeList) {
                Coordinate[] pts = dirEdge.getEdge().getCoordinates();
                for (int i = 0; i < pts.length - 1; i++) {
                    edgeEnv.expandToInclude(pts[i]);
                }
            }
            this.env = edgeEnv;
        }
        return this.env;
    }

    public Coordinate getRightmostCoordinate() {
        return this.rightMostCoord;
    }

    public void create(Node node) {
        addReachable(node);
        this.finder.findEdge(this.dirEdgeList);
        this.rightMostCoord = this.finder.getCoordinate();
    }

    private void addReachable(Node startNode) {
        Stack stack;
        new Stack();
        Stack nodeStack = stack;
        boolean add = nodeStack.add(startNode);
        while (!nodeStack.empty()) {
            add((Node) nodeStack.pop(), nodeStack);
        }
    }

    private void add(Node node, Stack stack) {
        Node node2 = node;
        Stack nodeStack = stack;
        node2.setVisited(true);
        boolean add = this.nodes.add(node2);
        Iterator i = ((DirectedEdgeStar) node2.getEdges()).iterator();
        while (i.hasNext()) {
            DirectedEdge de = (DirectedEdge) i.next();
            boolean add2 = this.dirEdgeList.add(de);
            Node symNode = de.getSym().getNode();
            if (!symNode.isVisited()) {
                Object push = nodeStack.push(symNode);
            }
        }
    }

    private void clearVisitedEdges() {
        for (DirectedEdge de : this.dirEdgeList) {
            de.setVisited(false);
        }
    }

    public void computeDepth(int outsideDepth) {
        clearVisitedEdges();
        DirectedEdge de = this.finder.getEdge();
        Node node = de.getNode();
        Label label = de.getLabel();
        de.setEdgeDepths(2, outsideDepth);
        copySymDepths(de);
        computeDepths(de);
    }

    private void computeDepths(DirectedEdge directedEdge) {
        Set set;
        LinkedList linkedList;
        DirectedEdge startEdge = directedEdge;
        new HashSet();
        Set nodesVisited = set;
        new LinkedList();
        LinkedList nodeQueue = linkedList;
        Node startNode = startEdge.getNode();
        nodeQueue.addLast(startNode);
        boolean add = nodesVisited.add(startNode);
        startEdge.setVisited(true);
        while (!nodeQueue.isEmpty()) {
            Node n = (Node) nodeQueue.removeFirst();
            boolean add2 = nodesVisited.add(n);
            computeNodeDepth(n);
            Iterator i = ((DirectedEdgeStar) n.getEdges()).iterator();
            while (i.hasNext()) {
                DirectedEdge sym = ((DirectedEdge) i.next()).getSym();
                if (!sym.isVisited()) {
                    Node adjNode = sym.getNode();
                    if (!nodesVisited.contains(adjNode)) {
                        nodeQueue.addLast(adjNode);
                        boolean add3 = nodesVisited.add(adjNode);
                    }
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0033 A[EDGE_INSN: B:18:0x0033->B:8:0x0033 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:3:0x0017  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void computeNodeDepth(org.locationtech.jts.geomgraph.Node r11) {
        /*
            r10 = this;
            r0 = r10
            r1 = r11
            r5 = 0
            r2 = r5
            r5 = r1
            org.locationtech.jts.geomgraph.EdgeEndStar r5 = r5.getEdges()
            org.locationtech.jts.geomgraph.DirectedEdgeStar r5 = (org.locationtech.jts.geomgraph.DirectedEdgeStar) r5
            java.util.Iterator r5 = r5.iterator()
            r3 = r5
        L_0x0010:
            r5 = r3
            boolean r5 = r5.hasNext()
            if (r5 == 0) goto L_0x0033
            r5 = r3
            java.lang.Object r5 = r5.next()
            org.locationtech.jts.geomgraph.DirectedEdge r5 = (org.locationtech.jts.geomgraph.DirectedEdge) r5
            r4 = r5
            r5 = r4
            boolean r5 = r5.isVisited()
            if (r5 != 0) goto L_0x0031
            r5 = r4
            org.locationtech.jts.geomgraph.DirectedEdge r5 = r5.getSym()
            boolean r5 = r5.isVisited()
            if (r5 == 0) goto L_0x005b
        L_0x0031:
            r5 = r4
            r2 = r5
        L_0x0033:
            r5 = r2
            if (r5 != 0) goto L_0x005c
            org.locationtech.jts.geom.TopologyException r5 = new org.locationtech.jts.geom.TopologyException
            r9 = r5
            r5 = r9
            r6 = r9
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r9 = r7
            r7 = r9
            r8 = r9
            r8.<init>()
            java.lang.String r8 = "unable to find edge to compute depths at "
            java.lang.StringBuilder r7 = r7.append(r8)
            r8 = r1
            org.locationtech.jts.geom.Coordinate r8 = r8.getCoordinate()
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r5
        L_0x005b:
            goto L_0x0010
        L_0x005c:
            r5 = r1
            org.locationtech.jts.geomgraph.EdgeEndStar r5 = r5.getEdges()
            org.locationtech.jts.geomgraph.DirectedEdgeStar r5 = (org.locationtech.jts.geomgraph.DirectedEdgeStar) r5
            r6 = r2
            r5.computeDepths(r6)
            r5 = r1
            org.locationtech.jts.geomgraph.EdgeEndStar r5 = r5.getEdges()
            org.locationtech.jts.geomgraph.DirectedEdgeStar r5 = (org.locationtech.jts.geomgraph.DirectedEdgeStar) r5
            java.util.Iterator r5 = r5.iterator()
            r3 = r5
        L_0x0073:
            r5 = r3
            boolean r5 = r5.hasNext()
            if (r5 == 0) goto L_0x008d
            r5 = r3
            java.lang.Object r5 = r5.next()
            org.locationtech.jts.geomgraph.DirectedEdge r5 = (org.locationtech.jts.geomgraph.DirectedEdge) r5
            r4 = r5
            r5 = r4
            r6 = 1
            r5.setVisited(r6)
            r5 = r0
            r6 = r4
            r5.copySymDepths(r6)
            goto L_0x0073
        L_0x008d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.operation.buffer.BufferSubgraph.computeNodeDepth(org.locationtech.jts.geomgraph.Node):void");
    }

    private void copySymDepths(DirectedEdge directedEdge) {
        DirectedEdge de = directedEdge;
        DirectedEdge sym = de.getSym();
        sym.setDepth(1, de.getDepth(2));
        sym.setDepth(2, de.getDepth(1));
    }

    public void findResultEdges() {
        for (DirectedEdge de : this.dirEdgeList) {
            if (de.getDepth(2) >= 1 && de.getDepth(1) <= 0 && !de.isInteriorAreaEdge()) {
                de.setInResult(true);
            }
        }
    }

    public int compareTo(Object o) {
        BufferSubgraph graph = (BufferSubgraph) o;
        if (this.rightMostCoord.f412x < graph.rightMostCoord.f412x) {
            return -1;
        }
        if (this.rightMostCoord.f412x > graph.rightMostCoord.f412x) {
            return 1;
        }
        return 0;
    }
}
