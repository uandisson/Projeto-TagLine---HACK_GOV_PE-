package org.locationtech.jts.operation.overlay.snap;

import java.util.Set;
import java.util.TreeSet;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.geom.PrecisionModel;

public class GeometrySnapper {
    private static final double SNAP_PRECISION_FACTOR = 1.0E-9d;
    private Geometry srcGeom;

    public static double computeOverlaySnapTolerance(Geometry geometry) {
        Geometry g = geometry;
        double snapTolerance = computeSizeBasedSnapTolerance(g);
        PrecisionModel pm = g.getPrecisionModel();
        if (pm.getType() == PrecisionModel.FIXED) {
            double fixedSnapTol = ((1.0d / pm.getScale()) * 2.0d) / 1.415d;
            if (fixedSnapTol > snapTolerance) {
                snapTolerance = fixedSnapTol;
            }
        }
        return snapTolerance;
    }

    public static double computeSizeBasedSnapTolerance(Geometry g) {
        Envelope env = g.getEnvelopeInternal();
        return Math.min(env.getHeight(), env.getWidth()) * SNAP_PRECISION_FACTOR;
    }

    public static double computeOverlaySnapTolerance(Geometry g0, Geometry g1) {
        return Math.min(computeOverlaySnapTolerance(g0), computeOverlaySnapTolerance(g1));
    }

    public static Geometry[] snap(Geometry g0, Geometry geometry, double d) {
        GeometrySnapper snapper0;
        GeometrySnapper snapper1;
        Geometry g1 = geometry;
        double snapTolerance = d;
        Geometry[] snapGeom = new Geometry[2];
        new GeometrySnapper(g0);
        snapGeom[0] = snapper0.snapTo(g1, snapTolerance);
        new GeometrySnapper(g1);
        snapGeom[1] = snapper1.snapTo(snapGeom[0], snapTolerance);
        return snapGeom;
    }

    public static Geometry snapToSelf(Geometry geom, double snapTolerance, boolean cleanResult) {
        GeometrySnapper snapper0;
        new GeometrySnapper(geom);
        return snapper0.snapToSelf(snapTolerance, cleanResult);
    }

    public GeometrySnapper(Geometry srcGeom2) {
        this.srcGeom = srcGeom2;
    }

    public Geometry snapTo(Geometry snapGeom, double snapTolerance) {
        SnapTransformer snapTrans;
        new SnapTransformer(snapTolerance, extractTargetCoordinates(snapGeom));
        return snapTrans.transform(this.srcGeom);
    }

    public Geometry snapToSelf(double snapTolerance, boolean cleanResult) {
        SnapTransformer snapTrans;
        new SnapTransformer(snapTolerance, extractTargetCoordinates(this.srcGeom), true);
        Geometry snappedGeom = snapTrans.transform(this.srcGeom);
        Geometry result = snappedGeom;
        if (cleanResult && (result instanceof Polygonal)) {
            result = snappedGeom.buffer(0.0d);
        }
        return result;
    }

    private Coordinate[] extractTargetCoordinates(Geometry g) {
        Set set;
        new TreeSet();
        Set ptSet = set;
        Coordinate[] pts = g.getCoordinates();
        for (int i = 0; i < pts.length; i++) {
            boolean add = ptSet.add(pts[i]);
        }
        return (Coordinate[]) ptSet.toArray(new Coordinate[0]);
    }

    private double computeSnapTolerance(Coordinate[] ringPts) {
        return computeMinimumSegmentLength(ringPts) / 10.0d;
    }

    private double computeMinimumSegmentLength(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        double minSegLen = Double.MAX_VALUE;
        for (int i = 0; i < pts.length - 1; i++) {
            double segLen = pts[i].distance(pts[i + 1]);
            if (segLen < minSegLen) {
                minSegLen = segLen;
            }
        }
        return minSegLen;
    }
}
