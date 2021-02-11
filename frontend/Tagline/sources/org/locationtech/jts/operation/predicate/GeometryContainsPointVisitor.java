package org.locationtech.jts.operation.predicate;

import org.locationtech.jts.algorithm.locate.SimplePointInAreaLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.util.ShortCircuitedGeometryVisitor;

/* compiled from: RectangleIntersects */
class GeometryContainsPointVisitor extends ShortCircuitedGeometryVisitor {
    private boolean containsPoint = false;
    private Envelope rectEnv;
    private CoordinateSequence rectSeq;

    public GeometryContainsPointVisitor(Polygon polygon) {
        Polygon rectangle = polygon;
        this.rectSeq = rectangle.getExteriorRing().getCoordinateSequence();
        this.rectEnv = rectangle.getEnvelopeInternal();
    }

    public boolean containsPoint() {
        return this.containsPoint;
    }

    /* access modifiers changed from: protected */
    public void visit(Geometry geometry) {
        Coordinate coordinate;
        Geometry geom = geometry;
        if (geom instanceof Polygon) {
            Envelope elementEnv = geom.getEnvelopeInternal();
            if (this.rectEnv.intersects(elementEnv)) {
                new Coordinate();
                Coordinate rectPt = coordinate;
                for (int i = 0; i < 4; i++) {
                    this.rectSeq.getCoordinate(i, rectPt);
                    if (elementEnv.contains(rectPt) && SimplePointInAreaLocator.containsPointInPolygon(rectPt, (Polygon) geom)) {
                        this.containsPoint = true;
                        return;
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isDone() {
        return this.containsPoint;
    }
}
