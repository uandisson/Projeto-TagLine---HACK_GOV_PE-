package org.locationtech.jts.densify;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.util.GeometryTransformer;

public class Densifier {
    /* access modifiers changed from: private */
    public double distanceTolerance;
    private Geometry inputGeom;

    public static Geometry densify(Geometry geom, double distanceTolerance2) {
        Densifier densifier;
        new Densifier(geom);
        Densifier densifier2 = densifier;
        densifier2.setDistanceTolerance(distanceTolerance2);
        return densifier2.getResultGeometry();
    }

    /* access modifiers changed from: private */
    public static Coordinate[] densifyPoints(Coordinate[] coordinateArr, double d, PrecisionModel precisionModel) {
        LineSegment lineSegment;
        CoordinateList coordinateList;
        Coordinate[] pts = coordinateArr;
        double distanceTolerance2 = d;
        PrecisionModel precModel = precisionModel;
        new LineSegment();
        LineSegment seg = lineSegment;
        new CoordinateList();
        CoordinateList coordList = coordinateList;
        for (int i = 0; i < pts.length - 1; i++) {
            seg.f422p0 = pts[i];
            seg.f423p1 = pts[i + 1];
            coordList.add(seg.f422p0, false);
            double len = seg.getLength();
            int densifiedSegCount = ((int) (len / distanceTolerance2)) + 1;
            if (densifiedSegCount > 1) {
                double densifiedSegLen = len / ((double) densifiedSegCount);
                for (int j = 1; j < densifiedSegCount; j++) {
                    Coordinate p = seg.pointAlong((((double) j) * densifiedSegLen) / len);
                    precModel.makePrecise(p);
                    coordList.add(p, false);
                }
            }
        }
        coordList.add(pts[pts.length - 1], false);
        return coordList.toCoordinateArray();
    }

    public Densifier(Geometry inputGeom2) {
        this.inputGeom = inputGeom2;
    }

    public void setDistanceTolerance(double d) {
        Throwable th;
        double distanceTolerance2 = d;
        if (distanceTolerance2 <= 0.0d) {
            Throwable th2 = th;
            new IllegalArgumentException("Tolerance must be positive");
            throw th2;
        }
        this.distanceTolerance = distanceTolerance2;
    }

    public Geometry getResultGeometry() {
        DensifyTransformer densifyTransformer;
        new DensifyTransformer(this);
        return densifyTransformer.transform(this.inputGeom);
    }

    class DensifyTransformer extends GeometryTransformer {
        final /* synthetic */ Densifier this$0;

        DensifyTransformer(Densifier this$02) {
            this.this$0 = this$02;
        }

        /* access modifiers changed from: protected */
        public CoordinateSequence transformCoordinates(CoordinateSequence coords, Geometry geometry) {
            Geometry parent = geometry;
            Coordinate[] newPts = Densifier.densifyPoints(coords.toCoordinateArray(), this.this$0.distanceTolerance, parent.getPrecisionModel());
            if ((parent instanceof LineString) && newPts.length == 1) {
                newPts = new Coordinate[0];
            }
            return this.factory.getCoordinateSequenceFactory().create(newPts);
        }

        /* access modifiers changed from: protected */
        public Geometry transformPolygon(Polygon geom, Geometry geometry) {
            Geometry parent = geometry;
            Geometry roughGeom = super.transformPolygon(geom, parent);
            if (parent instanceof MultiPolygon) {
                return roughGeom;
            }
            return createValidArea(roughGeom);
        }

        /* access modifiers changed from: protected */
        public Geometry transformMultiPolygon(MultiPolygon geom, Geometry parent) {
            return createValidArea(super.transformMultiPolygon(geom, parent));
        }

        private Geometry createValidArea(Geometry roughAreaGeom) {
            return roughAreaGeom.buffer(0.0d);
        }
    }
}
