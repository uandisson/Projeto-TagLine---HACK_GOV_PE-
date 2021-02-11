package org.locationtech.jts.operation.overlay;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.index.EdgeSetIntersector;
import org.locationtech.jts.geomgraph.index.SegmentIntersector;
import org.locationtech.jts.geomgraph.index.SimpleMCSweepLineIntersector;

public class EdgeSetNoder {
    private List inputEdges;

    /* renamed from: li */
    private LineIntersector f491li;

    public EdgeSetNoder(LineIntersector li) {
        List list;
        new ArrayList();
        this.inputEdges = list;
        this.f491li = li;
    }

    public void addEdges(List edges) {
        boolean addAll = this.inputEdges.addAll(edges);
    }

    public List getNodedEdges() {
        EdgeSetIntersector esi;
        SegmentIntersector si;
        List list;
        new SimpleMCSweepLineIntersector();
        new SegmentIntersector(this.f491li, true, false);
        esi.computeIntersections(this.inputEdges, si, true);
        new ArrayList();
        List splitEdges = list;
        for (Edge e : this.inputEdges) {
            e.getEdgeIntersectionList().addSplitEdges(splitEdges);
        }
        return splitEdges;
    }
}
