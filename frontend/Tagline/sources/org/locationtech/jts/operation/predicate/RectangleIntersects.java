package org.locationtech.jts.operation.predicate;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;

public class RectangleIntersects {
    private Envelope rectEnv;
    private Polygon rectangle;

    public static boolean intersects(Polygon rectangle2, Geometry b) {
        RectangleIntersects rp;
        new RectangleIntersects(rectangle2);
        return rp.intersects(b);
    }

    public RectangleIntersects(Polygon polygon) {
        Polygon rectangle2 = polygon;
        this.rectangle = rectangle2;
        this.rectEnv = rectangle2.getEnvelopeInternal();
    }

    public boolean intersects(Geometry geometry) {
        EnvelopeIntersectsVisitor envelopeIntersectsVisitor;
        GeometryContainsPointVisitor geometryContainsPointVisitor;
        RectangleIntersectsSegmentVisitor rectangleIntersectsSegmentVisitor;
        Geometry geom = geometry;
        if (!this.rectEnv.intersects(geom.getEnvelopeInternal())) {
            return false;
        }
        new EnvelopeIntersectsVisitor(this.rectEnv);
        EnvelopeIntersectsVisitor visitor = envelopeIntersectsVisitor;
        visitor.applyTo(geom);
        if (visitor.intersects()) {
            return true;
        }
        new GeometryContainsPointVisitor(this.rectangle);
        GeometryContainsPointVisitor ecpVisitor = geometryContainsPointVisitor;
        ecpVisitor.applyTo(geom);
        if (ecpVisitor.containsPoint()) {
            return true;
        }
        new RectangleIntersectsSegmentVisitor(this.rectangle);
        RectangleIntersectsSegmentVisitor riVisitor = rectangleIntersectsSegmentVisitor;
        riVisitor.applyTo(geom);
        if (riVisitor.intersects()) {
            return true;
        }
        return false;
    }
}
