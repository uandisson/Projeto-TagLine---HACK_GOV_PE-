package org.locationtech.jts.operation.valid;

import java.util.Iterator;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.geomgraph.index.SegmentIntersector;
import org.locationtech.jts.operation.relate.EdgeEndBundle;
import org.locationtech.jts.operation.relate.RelateNode;
import org.locationtech.jts.operation.relate.RelateNodeGraph;

public class ConsistentAreaTester {
    private GeometryGraph geomGraph;
    private Coordinate invalidPoint;

    /* renamed from: li */
    private final LineIntersector f502li;
    private RelateNodeGraph nodeGraph;

    public ConsistentAreaTester(GeometryGraph geomGraph2) {
        LineIntersector lineIntersector;
        RelateNodeGraph relateNodeGraph;
        new RobustLineIntersector();
        this.f502li = lineIntersector;
        new RelateNodeGraph();
        this.nodeGraph = relateNodeGraph;
        this.geomGraph = geomGraph2;
    }

    public Coordinate getInvalidPoint() {
        return this.invalidPoint;
    }

    public boolean isNodeConsistentArea() {
        SegmentIntersector intersector = this.geomGraph.computeSelfNodes(this.f502li, true, true);
        if (intersector.hasProperIntersection()) {
            this.invalidPoint = intersector.getProperIntersectionPoint();
            return false;
        }
        this.nodeGraph.build(this.geomGraph);
        return isNodeEdgeAreaLabelsConsistent();
    }

    private boolean isNodeEdgeAreaLabelsConsistent() {
        Iterator nodeIt = this.nodeGraph.getNodeIterator();
        while (nodeIt.hasNext()) {
            RelateNode node = (RelateNode) nodeIt.next();
            if (!node.getEdges().isAreaLabelsConsistent(this.geomGraph)) {
                this.invalidPoint = (Coordinate) node.getCoordinate().clone();
                return false;
            }
        }
        return true;
    }

    public boolean hasDuplicateRings() {
        Iterator nodeIt = this.nodeGraph.getNodeIterator();
        while (nodeIt.hasNext()) {
            Iterator i = ((RelateNode) nodeIt.next()).getEdges().iterator();
            while (true) {
                if (i.hasNext()) {
                    EdgeEndBundle eeb = (EdgeEndBundle) i.next();
                    if (eeb.getEdgeEnds().size() > 1) {
                        this.invalidPoint = eeb.getEdge().getCoordinate(0);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
