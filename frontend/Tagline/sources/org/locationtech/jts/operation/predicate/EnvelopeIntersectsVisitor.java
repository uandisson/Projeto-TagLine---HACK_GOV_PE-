package org.locationtech.jts.operation.predicate;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.util.ShortCircuitedGeometryVisitor;

/* compiled from: RectangleIntersects */
class EnvelopeIntersectsVisitor extends ShortCircuitedGeometryVisitor {
    private boolean intersects = false;
    private Envelope rectEnv;

    public EnvelopeIntersectsVisitor(Envelope rectEnv2) {
        this.rectEnv = rectEnv2;
    }

    public boolean intersects() {
        return this.intersects;
    }

    /* access modifiers changed from: protected */
    public void visit(Geometry element) {
        Envelope elementEnv = element.getEnvelopeInternal();
        if (this.rectEnv.intersects(elementEnv)) {
            if (this.rectEnv.contains(elementEnv)) {
                this.intersects = true;
            } else if (elementEnv.getMinX() >= this.rectEnv.getMinX() && elementEnv.getMaxX() <= this.rectEnv.getMaxX()) {
                this.intersects = true;
            } else if (elementEnv.getMinY() >= this.rectEnv.getMinY() && elementEnv.getMaxY() <= this.rectEnv.getMaxY()) {
                this.intersects = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isDone() {
        return this.intersects;
    }
}
