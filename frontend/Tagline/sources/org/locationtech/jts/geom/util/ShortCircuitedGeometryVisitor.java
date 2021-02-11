package org.locationtech.jts.geom.util;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;

public abstract class ShortCircuitedGeometryVisitor {
    private boolean isDone = false;

    /* access modifiers changed from: protected */
    public abstract boolean isDone();

    /* access modifiers changed from: protected */
    public abstract void visit(Geometry geometry);

    public ShortCircuitedGeometryVisitor() {
    }

    public void applyTo(Geometry geometry) {
        Geometry geom = geometry;
        for (int i = 0; i < geom.getNumGeometries() && !this.isDone; i++) {
            Geometry element = geom.getGeometryN(i);
            if (!(element instanceof GeometryCollection)) {
                visit(element);
                if (isDone()) {
                    this.isDone = true;
                    return;
                }
            } else {
                applyTo(element);
            }
        }
    }
}
