package org.locationtech.jts.triangulate.quadedge;

import java.util.Collection;
import java.util.LinkedList;

public class EdgeConnectedTriangleTraversal {
    private LinkedList triQueue;

    public EdgeConnectedTriangleTraversal() {
        LinkedList linkedList;
        new LinkedList();
        this.triQueue = linkedList;
    }

    public void init(QuadEdgeTriangle tri) {
        this.triQueue.addLast(tri);
    }

    public void init(Collection tris) {
        boolean addAll = this.triQueue.addAll(tris);
    }

    public void visitAll(TraversalVisitor traversalVisitor) {
        TraversalVisitor visitor = traversalVisitor;
        while (!this.triQueue.isEmpty()) {
            process((QuadEdgeTriangle) this.triQueue.removeFirst(), visitor);
        }
    }

    private void process(QuadEdgeTriangle quadEdgeTriangle, TraversalVisitor traversalVisitor) {
        QuadEdgeTriangle currTri = quadEdgeTriangle;
        TraversalVisitor visitor = traversalVisitor;
        QuadEdgeTriangle[] neighbours = currTri.getNeighbours();
        for (int i = 0; i < 3; i++) {
            QuadEdgeTriangle neighTri = (QuadEdgeTriangle) currTri.getEdge(i).sym().getData();
            if (neighTri != null && visitor.visit(currTri, i, neighTri)) {
                this.triQueue.addLast(neighTri);
            }
        }
    }
}
