package org.locationtech.jts.geomgraph.index;

import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.Edge;

public class SimpleEdgeSetIntersector extends EdgeSetIntersector {
    int nOverlaps;

    public SimpleEdgeSetIntersector() {
    }

    public void computeIntersections(List list, SegmentIntersector segmentIntersector, boolean z) {
        List<Edge> edges = list;
        SegmentIntersector si = segmentIntersector;
        boolean testAllSegments = z;
        this.nOverlaps = 0;
        for (Edge edge0 : edges) {
            for (Edge edge1 : edges) {
                if (testAllSegments || edge0 != edge1) {
                    computeIntersects(edge0, edge1, si);
                }
            }
        }
    }

    public void computeIntersections(List edges0, List list, SegmentIntersector segmentIntersector) {
        List<Edge> edges1 = list;
        SegmentIntersector si = segmentIntersector;
        this.nOverlaps = 0;
        Iterator i0 = edges0.iterator();
        while (i0.hasNext()) {
            Edge edge0 = (Edge) i0.next();
            for (Edge edge1 : edges1) {
                computeIntersects(edge0, edge1, si);
            }
        }
    }

    private void computeIntersects(Edge edge, Edge edge2, SegmentIntersector segmentIntersector) {
        Edge e0 = edge;
        Edge e1 = edge2;
        SegmentIntersector si = segmentIntersector;
        Coordinate[] pts0 = e0.getCoordinates();
        Coordinate[] pts1 = e1.getCoordinates();
        for (int i0 = 0; i0 < pts0.length - 1; i0++) {
            for (int i1 = 0; i1 < pts1.length - 1; i1++) {
                si.addIntersections(e0, i0, e1, i1);
            }
        }
    }
}
