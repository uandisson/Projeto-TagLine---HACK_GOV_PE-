package org.locationtech.jts.triangulate.quadedge;

public interface TriangleVisitor {
    void visit(QuadEdge[] quadEdgeArr);
}
