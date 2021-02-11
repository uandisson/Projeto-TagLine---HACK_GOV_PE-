package org.locationtech.jts.triangulate.quadedge;

public class LastFoundQuadEdgeLocator implements QuadEdgeLocator {
    private QuadEdge lastEdge = null;
    private QuadEdgeSubdivision subdiv;

    public LastFoundQuadEdgeLocator(QuadEdgeSubdivision subdiv2) {
        this.subdiv = subdiv2;
        init();
    }

    private void init() {
        this.lastEdge = findEdge();
    }

    private QuadEdge findEdge() {
        return (QuadEdge) this.subdiv.getEdges().iterator().next();
    }

    public QuadEdge locate(Vertex vertex) {
        Vertex v = vertex;
        if (!this.lastEdge.isLive()) {
            init();
        }
        QuadEdge e = this.subdiv.locateFromEdge(v, this.lastEdge);
        this.lastEdge = e;
        return e;
    }
}
