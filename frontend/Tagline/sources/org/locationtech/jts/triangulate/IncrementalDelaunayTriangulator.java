package org.locationtech.jts.triangulate;

import java.util.Collection;
import java.util.Iterator;
import org.locationtech.jts.triangulate.quadedge.QuadEdge;
import org.locationtech.jts.triangulate.quadedge.QuadEdgeSubdivision;
import org.locationtech.jts.triangulate.quadedge.Vertex;

public class IncrementalDelaunayTriangulator {
    private boolean isUsingTolerance = false;
    private QuadEdgeSubdivision subdiv;

    public IncrementalDelaunayTriangulator(QuadEdgeSubdivision quadEdgeSubdivision) {
        QuadEdgeSubdivision subdiv2 = quadEdgeSubdivision;
        this.subdiv = subdiv2;
        this.isUsingTolerance = subdiv2.getTolerance() > 0.0d;
    }

    public void insertSites(Collection vertices) {
        Iterator i = vertices.iterator();
        while (i.hasNext()) {
            QuadEdge insertSite = insertSite((Vertex) i.next());
        }
    }

    public QuadEdge insertSite(Vertex vertex) {
        QuadEdge e;
        Vertex v = vertex;
        QuadEdge e2 = this.subdiv.locate(v);
        if (this.subdiv.isVertexOfEdge(e2, v)) {
            return e2;
        }
        if (this.subdiv.isOnEdge(e2, v.getCoordinate())) {
            e2 = e2.oPrev();
            this.subdiv.delete(e2.oNext());
        }
        QuadEdge base = this.subdiv.makeEdge(e.orig(), v);
        QuadEdge.splice(base, e);
        QuadEdge startEdge = base;
        do {
            base = this.subdiv.connect(e, base.sym());
            e = base.oPrev();
        } while (e.lNext() != startEdge);
        while (true) {
            QuadEdge t = e.oPrev();
            if (t.dest().rightOf(e) && v.isInCircle(e.orig(), t.dest(), e.dest())) {
                QuadEdge.swap(e);
                e = e.oPrev();
            } else if (e.oNext() == startEdge) {
                return base;
            } else {
                e = e.oNext().lPrev();
            }
        }
    }
}
