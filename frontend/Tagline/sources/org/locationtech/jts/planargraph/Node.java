package org.locationtech.jts.planargraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.locationtech.jts.geom.Coordinate;

public class Node extends GraphComponent {
    protected DirectedEdgeStar deStar;

    /* renamed from: pt */
    protected Coordinate f507pt;

    public static Collection getEdgesBetween(Node node0, Node node1) {
        Set set;
        new HashSet(DirectedEdge.toEdges(node0.getOutEdges().getEdges()));
        Set commonEdges = set;
        boolean retainAll = commonEdges.retainAll(DirectedEdge.toEdges(node1.getOutEdges().getEdges()));
        return commonEdges;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Node(org.locationtech.jts.geom.Coordinate r8) {
        /*
            r7 = this;
            r0 = r7
            r1 = r8
            r2 = r0
            r3 = r1
            org.locationtech.jts.planargraph.DirectedEdgeStar r4 = new org.locationtech.jts.planargraph.DirectedEdgeStar
            r6 = r4
            r4 = r6
            r5 = r6
            r5.<init>()
            r2.<init>(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.planargraph.Node.<init>(org.locationtech.jts.geom.Coordinate):void");
    }

    public Node(Coordinate pt, DirectedEdgeStar deStar2) {
        this.f507pt = pt;
        this.deStar = deStar2;
    }

    public Coordinate getCoordinate() {
        return this.f507pt;
    }

    public void addOutEdge(DirectedEdge de) {
        this.deStar.add(de);
    }

    public DirectedEdgeStar getOutEdges() {
        return this.deStar;
    }

    public int getDegree() {
        return this.deStar.getDegree();
    }

    public int getIndex(Edge edge) {
        return this.deStar.getIndex(edge);
    }

    public void remove(DirectedEdge de) {
        this.deStar.remove(de);
    }

    /* access modifiers changed from: package-private */
    public void remove() {
        this.f507pt = null;
    }

    public boolean isRemoved() {
        return this.f507pt == null;
    }
}
