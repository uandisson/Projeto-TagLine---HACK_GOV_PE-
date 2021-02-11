package org.locationtech.jts.shape.random;

import org.locationtech.jts.algorithm.locate.IndexedPointInAreaLocator;
import org.locationtech.jts.algorithm.locate.PointOnGeometryLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.shape.GeometricShapeBuilder;

public class RandomPointsBuilder extends GeometricShapeBuilder {
    private PointOnGeometryLocator extentLocator;
    protected Geometry maskPoly = null;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public RandomPointsBuilder() {
        /*
            r5 = this;
            r0 = r5
            r1 = r0
            org.locationtech.jts.geom.GeometryFactory r2 = new org.locationtech.jts.geom.GeometryFactory
            r4 = r2
            r2 = r4
            r3 = r4
            r3.<init>()
            r1.<init>(r2)
            r1 = r0
            r2 = 0
            r1.maskPoly = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.shape.random.RandomPointsBuilder.<init>():void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public RandomPointsBuilder(GeometryFactory geomFact) {
        super(geomFact);
    }

    public void setExtent(Geometry geometry) {
        PointOnGeometryLocator pointOnGeometryLocator;
        Throwable th;
        Geometry mask = geometry;
        if (!(mask instanceof Polygonal)) {
            Throwable th2 = th;
            new IllegalArgumentException("Only polygonal extents are supported");
            throw th2;
        }
        this.maskPoly = mask;
        setExtent(mask.getEnvelopeInternal());
        new IndexedPointInAreaLocator(mask);
        this.extentLocator = pointOnGeometryLocator;
    }

    public Geometry getGeometry() {
        Coordinate[] pts = new Coordinate[this.numPts];
        int i = 0;
        while (i < this.numPts) {
            Coordinate p = createRandomCoord(getExtent());
            if (this.extentLocator == null || isInExtent(p)) {
                int i2 = i;
                i++;
                pts[i2] = p;
            }
        }
        return this.geomFactory.createMultiPoint(pts);
    }

    /* access modifiers changed from: protected */
    public boolean isInExtent(Coordinate coordinate) {
        Coordinate p = coordinate;
        if (this.extentLocator == null) {
            return getExtent().contains(p);
        }
        return this.extentLocator.locate(p) != 2;
    }

    /* access modifiers changed from: protected */
    public Coordinate createCoord(double x, double y) {
        Coordinate coordinate;
        new Coordinate(x, y);
        Coordinate pt = coordinate;
        this.geomFactory.getPrecisionModel().makePrecise(pt);
        return pt;
    }

    /* access modifiers changed from: protected */
    public Coordinate createRandomCoord(Envelope envelope) {
        Envelope env = envelope;
        return createCoord(env.getMinX() + (env.getWidth() * Math.random()), env.getMinY() + (env.getHeight() * Math.random()));
    }
}
