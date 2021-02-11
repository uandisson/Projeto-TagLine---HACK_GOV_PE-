package org.locationtech.jts.operation.predicate;

import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.RectangleLineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.util.LinearComponentExtracter;
import org.locationtech.jts.geom.util.ShortCircuitedGeometryVisitor;

/* compiled from: RectangleIntersects */
class RectangleIntersectsSegmentVisitor extends ShortCircuitedGeometryVisitor {
    private boolean hasIntersection = false;

    /* renamed from: p0 */
    private Coordinate f496p0;

    /* renamed from: p1 */
    private Coordinate f497p1;
    private Envelope rectEnv;
    private RectangleLineIntersector rectIntersector;

    public RectangleIntersectsSegmentVisitor(Polygon rectangle) {
        Coordinate coordinate;
        Coordinate coordinate2;
        RectangleLineIntersector rectangleLineIntersector;
        new Coordinate();
        this.f496p0 = coordinate;
        new Coordinate();
        this.f497p1 = coordinate2;
        this.rectEnv = rectangle.getEnvelopeInternal();
        new RectangleLineIntersector(this.rectEnv);
        this.rectIntersector = rectangleLineIntersector;
    }

    public boolean intersects() {
        return this.hasIntersection;
    }

    /* access modifiers changed from: protected */
    public void visit(Geometry geometry) {
        Geometry geom = geometry;
        if (this.rectEnv.intersects(geom.getEnvelopeInternal())) {
            checkIntersectionWithLineStrings(LinearComponentExtracter.getLines(geom));
        }
    }

    private void checkIntersectionWithLineStrings(List lines) {
        Iterator i = lines.iterator();
        while (i.hasNext()) {
            checkIntersectionWithSegments((LineString) i.next());
            if (this.hasIntersection) {
                return;
            }
        }
    }

    private void checkIntersectionWithSegments(LineString testLine) {
        CoordinateSequence seq1 = testLine.getCoordinateSequence();
        for (int j = 1; j < seq1.size(); j++) {
            seq1.getCoordinate(j - 1, this.f496p0);
            seq1.getCoordinate(j, this.f497p1);
            if (this.rectIntersector.intersects(this.f496p0, this.f497p1)) {
                this.hasIntersection = true;
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isDone() {
        return this.hasIntersection;
    }
}
