package org.locationtech.jts.operation.overlay.snap;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.util.GeometryTransformer;

/* compiled from: GeometrySnapper */
class SnapTransformer extends GeometryTransformer {
    private boolean isSelfSnap = false;
    private Coordinate[] snapPts;
    private double snapTolerance;

    SnapTransformer(double snapTolerance2, Coordinate[] snapPts2) {
        this.snapTolerance = snapTolerance2;
        this.snapPts = snapPts2;
    }

    SnapTransformer(double snapTolerance2, Coordinate[] snapPts2, boolean isSelfSnap2) {
        this.snapTolerance = snapTolerance2;
        this.snapPts = snapPts2;
        this.isSelfSnap = isSelfSnap2;
    }

    /* access modifiers changed from: protected */
    public CoordinateSequence transformCoordinates(CoordinateSequence coords, Geometry geometry) {
        Geometry geometry2 = geometry;
        return this.factory.getCoordinateSequenceFactory().create(snapLine(coords.toCoordinateArray(), this.snapPts));
    }

    private Coordinate[] snapLine(Coordinate[] srcPts, Coordinate[] snapPts2) {
        LineStringSnapper lineStringSnapper;
        new LineStringSnapper(srcPts, this.snapTolerance);
        LineStringSnapper snapper = lineStringSnapper;
        snapper.setAllowSnappingToSourceVertices(this.isSelfSnap);
        return snapper.snapTo(snapPts2);
    }
}
