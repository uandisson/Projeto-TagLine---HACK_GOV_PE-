package org.locationtech.jts.operation.overlay;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.PointLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geomgraph.Node;

public class PointBuilder {
    private GeometryFactory geometryFactory;

    /* renamed from: op */
    private OverlayOp f493op;
    private List resultPointList;

    public PointBuilder(OverlayOp op, GeometryFactory geometryFactory2, PointLocator pointLocator) {
        List list;
        PointLocator pointLocator2 = pointLocator;
        new ArrayList();
        this.resultPointList = list;
        this.f493op = op;
        this.geometryFactory = geometryFactory2;
    }

    public List build(int opCode) {
        extractNonCoveredResultNodes(opCode);
        return this.resultPointList;
    }

    private void extractNonCoveredResultNodes(int i) {
        int opCode = i;
        for (Node n : this.f493op.getGraph().getNodes()) {
            if (!n.isInResult() && !n.isIncidentEdgeInResult()) {
                if ((n.getEdges().getDegree() == 0 || opCode == 1) && OverlayOp.isResultOfOp(n.getLabel(), opCode)) {
                    filterCoveredNodeToPoint(n);
                }
            }
        }
    }

    private void filterCoveredNodeToPoint(Node n) {
        Coordinate coord = n.getCoordinate();
        if (!this.f493op.isCoveredByLA(coord)) {
            boolean add = this.resultPointList.add(this.geometryFactory.createPoint(coord));
        }
    }
}
