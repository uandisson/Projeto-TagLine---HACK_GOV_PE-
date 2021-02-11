package org.locationtech.jts.operation.overlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.TopologyException;
import org.locationtech.jts.geomgraph.DirectedEdge;
import org.locationtech.jts.geomgraph.DirectedEdgeStar;
import org.locationtech.jts.geomgraph.Label;
import org.locationtech.jts.geomgraph.Node;
import org.locationtech.jts.geomgraph.PlanarGraph;

public class ConsistentPolygonRingChecker {
    private final int LINKING_TO_OUTGOING = 2;
    private final int SCANNING_FOR_INCOMING = 1;
    private PlanarGraph graph;

    public ConsistentPolygonRingChecker(PlanarGraph graph2) {
        this.graph = graph2;
    }

    public void checkAll() {
        check(1);
        check(3);
        check(2);
        check(4);
    }

    public void check(int i) {
        int opCode = i;
        Iterator nodeit = this.graph.getNodeIterator();
        while (nodeit.hasNext()) {
            testLinkResultDirectedEdges((DirectedEdgeStar) ((Node) nodeit.next()).getEdges(), opCode);
        }
    }

    private List getPotentialResultAreaEdges(DirectedEdgeStar deStar, int i) {
        List list;
        int opCode = i;
        new ArrayList();
        List resultAreaEdgeList = list;
        Iterator it = deStar.iterator();
        while (it.hasNext()) {
            DirectedEdge de = (DirectedEdge) it.next();
            if (isPotentialResultAreaEdge(de, opCode) || isPotentialResultAreaEdge(de.getSym(), opCode)) {
                boolean add = resultAreaEdgeList.add(de);
            }
        }
        return resultAreaEdgeList;
    }

    private boolean isPotentialResultAreaEdge(DirectedEdge directedEdge, int i) {
        DirectedEdge de = directedEdge;
        int opCode = i;
        Label label = de.getLabel();
        if (!label.isArea() || de.isInteriorAreaEdge() || !OverlayOp.isResultOfOp(label.getLocation(0, 2), label.getLocation(1, 2), opCode)) {
            return false;
        }
        return true;
    }

    private void testLinkResultDirectedEdges(DirectedEdgeStar directedEdgeStar, int i) {
        Throwable th;
        DirectedEdgeStar deStar = directedEdgeStar;
        int opCode = i;
        List ringEdges = getPotentialResultAreaEdges(deStar, opCode);
        DirectedEdge firstOut = null;
        int state = 1;
        for (int i2 = 0; i2 < ringEdges.size(); i2++) {
            DirectedEdge nextOut = (DirectedEdge) ringEdges.get(i2);
            DirectedEdge nextIn = nextOut.getSym();
            if (nextOut.getLabel().isArea()) {
                if (firstOut == null && isPotentialResultAreaEdge(nextOut, opCode)) {
                    firstOut = nextOut;
                }
                switch (state) {
                    case 1:
                        if (isPotentialResultAreaEdge(nextIn, opCode)) {
                            DirectedEdge incoming = nextIn;
                            state = 2;
                            break;
                        } else {
                            break;
                        }
                    case 2:
                        if (isPotentialResultAreaEdge(nextOut, opCode)) {
                            state = 1;
                            break;
                        } else {
                            break;
                        }
                }
            }
        }
        if (state == 2 && firstOut == null) {
            Throwable th2 = th;
            new TopologyException("no outgoing dirEdge found", deStar.getCoordinate());
            throw th2;
        }
    }
}
