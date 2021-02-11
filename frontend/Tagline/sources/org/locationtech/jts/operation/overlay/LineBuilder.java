package org.locationtech.jts.operation.overlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.PointLocator;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geomgraph.DirectedEdge;
import org.locationtech.jts.geomgraph.DirectedEdgeStar;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.Label;
import org.locationtech.jts.geomgraph.Node;
import org.locationtech.jts.util.Assert;

public class LineBuilder {
    private GeometryFactory geometryFactory;
    private List lineEdgesList;

    /* renamed from: op */
    private OverlayOp f492op;
    private PointLocator ptLocator;
    private List resultLineList;

    public LineBuilder(OverlayOp op, GeometryFactory geometryFactory2, PointLocator ptLocator2) {
        List list;
        List list2;
        new ArrayList();
        this.lineEdgesList = list;
        new ArrayList();
        this.resultLineList = list2;
        this.f492op = op;
        this.geometryFactory = geometryFactory2;
        this.ptLocator = ptLocator2;
    }

    public List build(int i) {
        int opCode = i;
        findCoveredLineEdges();
        collectLines(opCode);
        buildLines(opCode);
        return this.resultLineList;
    }

    private void findCoveredLineEdges() {
        for (Node node : this.f492op.getGraph().getNodes()) {
            ((DirectedEdgeStar) node.getEdges()).findCoveredLineEdges();
        }
        for (DirectedEdge de : this.f492op.getGraph().getEdgeEnds()) {
            Edge e = de.getEdge();
            if (de.isLineEdge() && !e.isCoveredSet()) {
                e.setCovered(this.f492op.isCoveredByA(de.getCoordinate()));
            }
        }
    }

    private void collectLines(int i) {
        int opCode = i;
        for (DirectedEdge de : this.f492op.getGraph().getEdgeEnds()) {
            collectLineEdge(de, opCode, this.lineEdgesList);
            collectBoundaryTouchEdge(de, opCode, this.lineEdgesList);
        }
    }

    private void collectLineEdge(DirectedEdge directedEdge, int i, List list) {
        DirectedEdge de = directedEdge;
        int opCode = i;
        List edges = list;
        Label label = de.getLabel();
        Edge e = de.getEdge();
        if (de.isLineEdge() && !de.isVisited() && OverlayOp.isResultOfOp(label, opCode) && !e.isCovered()) {
            boolean add = edges.add(e);
            de.setVisitedEdge(true);
        }
    }

    private void collectBoundaryTouchEdge(DirectedEdge directedEdge, int i, List list) {
        DirectedEdge de = directedEdge;
        int opCode = i;
        List edges = list;
        Label label = de.getLabel();
        if (!de.isLineEdge() && !de.isVisited() && !de.isInteriorAreaEdge() && !de.getEdge().isInResult()) {
            Assert.isTrue((!de.isInResult() && !de.getSym().isInResult()) || !de.getEdge().isInResult());
            if (OverlayOp.isResultOfOp(label, opCode) && opCode == 1) {
                boolean add = edges.add(de.getEdge());
                de.setVisitedEdge(true);
            }
        }
    }

    private void buildLines(int i) {
        int i2 = i;
        for (Edge e : this.lineEdgesList) {
            Label label = e.getLabel();
            boolean add = this.resultLineList.add(this.geometryFactory.createLineString(e.getCoordinates()));
            e.setInResult(true);
        }
    }

    private void labelIsolatedLines(List edgesList) {
        Iterator it = edgesList.iterator();
        while (it.hasNext()) {
            Edge e = (Edge) it.next();
            Label label = e.getLabel();
            if (e.isIsolated()) {
                if (label.isNull(0)) {
                    labelIsolatedLine(e, 0);
                } else {
                    labelIsolatedLine(e, 1);
                }
            }
        }
    }

    private void labelIsolatedLine(Edge edge, int i) {
        Edge e = edge;
        int targetIndex = i;
        e.getLabel().setLocation(targetIndex, this.ptLocator.locate(e.getCoordinate(), this.f492op.getArgGeometry(targetIndex)));
    }
}
