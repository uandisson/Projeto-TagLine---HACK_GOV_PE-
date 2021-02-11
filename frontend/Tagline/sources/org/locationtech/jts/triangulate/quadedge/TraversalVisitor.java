package org.locationtech.jts.triangulate.quadedge;

public interface TraversalVisitor {
    boolean visit(QuadEdgeTriangle quadEdgeTriangle, int i, QuadEdgeTriangle quadEdgeTriangle2);
}
