package org.locationtech.jts.simplify;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.util.GeometryTransformer;

public class VWSimplifier {
    /* access modifiers changed from: private */
    public double distanceTolerance;
    private Geometry inputGeom;
    private boolean isEnsureValidTopology = true;

    public static Geometry simplify(Geometry geom, double distanceTolerance2) {
        VWSimplifier vWSimplifier;
        new VWSimplifier(geom);
        VWSimplifier simp = vWSimplifier;
        simp.setDistanceTolerance(distanceTolerance2);
        return simp.getResultGeometry();
    }

    public VWSimplifier(Geometry inputGeom2) {
        this.inputGeom = inputGeom2;
    }

    public void setDistanceTolerance(double d) {
        Throwable th;
        double distanceTolerance2 = d;
        if (distanceTolerance2 < 0.0d) {
            Throwable th2 = th;
            new IllegalArgumentException("Tolerance must be non-negative");
            throw th2;
        }
        this.distanceTolerance = distanceTolerance2;
    }

    public void setEnsureValid(boolean isEnsureValidTopology2) {
        boolean z = isEnsureValidTopology2;
        this.isEnsureValidTopology = z;
    }

    public Geometry getResultGeometry() {
        VWTransformer vWTransformer;
        if (this.inputGeom.isEmpty()) {
            return (Geometry) this.inputGeom.clone();
        }
        new VWTransformer(this, this.isEnsureValidTopology);
        return vWTransformer.transform(this.inputGeom);
    }

    class VWTransformer extends GeometryTransformer {
        private boolean isEnsureValidTopology = true;
        final /* synthetic */ VWSimplifier this$0;

        public VWTransformer(VWSimplifier this$02, boolean isEnsureValidTopology2) {
            this.this$0 = this$02;
            this.isEnsureValidTopology = isEnsureValidTopology2;
        }

        /* access modifiers changed from: protected */
        public CoordinateSequence transformCoordinates(CoordinateSequence coords, Geometry geometry) {
            Coordinate[] newPts;
            Geometry geometry2 = geometry;
            Coordinate[] inputPts = coords.toCoordinateArray();
            if (inputPts.length == 0) {
                newPts = new Coordinate[0];
            } else {
                newPts = VWLineSimplifier.simplify(inputPts, this.this$0.distanceTolerance);
            }
            return this.factory.getCoordinateSequenceFactory().create(newPts);
        }

        /* access modifiers changed from: protected */
        public Geometry transformPolygon(Polygon polygon, Geometry geometry) {
            Polygon geom = polygon;
            Geometry parent = geometry;
            if (geom.isEmpty()) {
                return null;
            }
            Geometry rawGeom = super.transformPolygon(geom, parent);
            if (parent instanceof MultiPolygon) {
                return rawGeom;
            }
            return createValidArea(rawGeom);
        }

        /* access modifiers changed from: protected */
        public Geometry transformLinearRing(LinearRing geom, Geometry geometry) {
            Geometry parent = geometry;
            boolean removeDegenerateRings = parent instanceof Polygon;
            Geometry simpResult = super.transformLinearRing(geom, parent);
            if (!removeDegenerateRings || (simpResult instanceof LinearRing)) {
                return simpResult;
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public Geometry transformMultiPolygon(MultiPolygon geom, Geometry parent) {
            return createValidArea(super.transformMultiPolygon(geom, parent));
        }

        private Geometry createValidArea(Geometry geometry) {
            Geometry rawAreaGeom = geometry;
            if (this.isEnsureValidTopology) {
                return rawAreaGeom.buffer(0.0d);
            }
            return rawAreaGeom;
        }
    }
}
