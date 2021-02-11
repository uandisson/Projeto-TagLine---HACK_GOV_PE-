package org.locationtech.jts.operation.relate;

import org.locationtech.jts.algorithm.BoundaryNodeRule;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.IntersectionMatrix;
import org.locationtech.jts.operation.GeometryGraphOperation;

public class RelateOp extends GeometryGraphOperation {
    private RelateComputer relate;

    public static IntersectionMatrix relate(Geometry a, Geometry b) {
        RelateOp relOp;
        new RelateOp(a, b);
        return relOp.getIntersectionMatrix();
    }

    public static IntersectionMatrix relate(Geometry a, Geometry b, BoundaryNodeRule boundaryNodeRule) {
        RelateOp relOp;
        new RelateOp(a, b, boundaryNodeRule);
        return relOp.getIntersectionMatrix();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public RelateOp(Geometry g0, Geometry g1) {
        super(g0, g1);
        RelateComputer relateComputer;
        new RelateComputer(this.arg);
        this.relate = relateComputer;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public RelateOp(Geometry g0, Geometry g1, BoundaryNodeRule boundaryNodeRule) {
        super(g0, g1, boundaryNodeRule);
        RelateComputer relateComputer;
        new RelateComputer(this.arg);
        this.relate = relateComputer;
    }

    public IntersectionMatrix getIntersectionMatrix() {
        return this.relate.computeIM();
    }
}
