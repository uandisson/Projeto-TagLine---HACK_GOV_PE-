package org.locationtech.jts.triangulate;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.triangulate.quadedge.Vertex;

public class ConstraintVertex extends Vertex {
    private Object constraint = null;
    private boolean isOnConstraint;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ConstraintVertex(Coordinate p) {
        super(p);
    }

    public void setOnConstraint(boolean isOnConstraint2) {
        boolean z = isOnConstraint2;
        this.isOnConstraint = z;
    }

    public boolean isOnConstraint() {
        return this.isOnConstraint;
    }

    public void setConstraint(Object constraint2) {
        this.isOnConstraint = true;
        this.constraint = constraint2;
    }

    public Object getConstraint() {
        return this.constraint;
    }

    /* access modifiers changed from: protected */
    public void merge(ConstraintVertex constraintVertex) {
        ConstraintVertex other = constraintVertex;
        if (other.isOnConstraint) {
            this.isOnConstraint = true;
            this.constraint = other.constraint;
        }
    }
}
