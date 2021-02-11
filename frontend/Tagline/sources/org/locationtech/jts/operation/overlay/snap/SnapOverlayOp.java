package org.locationtech.jts.operation.overlay.snap;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.operation.overlay.OverlayOp;
import org.locationtech.jts.precision.CommonBitsRemover;

public class SnapOverlayOp {
    private CommonBitsRemover cbr;
    private Geometry[] geom = new Geometry[2];
    private double snapTolerance;

    public static Geometry overlayOp(Geometry g0, Geometry g1, int opCode) {
        SnapOverlayOp op;
        new SnapOverlayOp(g0, g1);
        return op.getResultGeometry(opCode);
    }

    public static Geometry intersection(Geometry g0, Geometry g1) {
        return overlayOp(g0, g1, 1);
    }

    public static Geometry union(Geometry g0, Geometry g1) {
        return overlayOp(g0, g1, 2);
    }

    public static Geometry difference(Geometry g0, Geometry g1) {
        return overlayOp(g0, g1, 3);
    }

    public static Geometry symDifference(Geometry g0, Geometry g1) {
        return overlayOp(g0, g1, 4);
    }

    public SnapOverlayOp(Geometry g1, Geometry g2) {
        this.geom[0] = g1;
        this.geom[1] = g2;
        computeSnapTolerance();
    }

    private void computeSnapTolerance() {
        this.snapTolerance = GeometrySnapper.computeOverlaySnapTolerance(this.geom[0], this.geom[1]);
    }

    public Geometry getResultGeometry(int opCode) {
        Geometry[] prepGeom = snap(this.geom);
        return prepareResult(OverlayOp.overlayOp(prepGeom[0], prepGeom[1], opCode));
    }

    private Geometry selfSnap(Geometry geometry) {
        GeometrySnapper snapper0;
        Geometry geom2 = geometry;
        new GeometrySnapper(geom2);
        return snapper0.snapTo(geom2, this.snapTolerance);
    }

    private Geometry[] snap(Geometry[] geom2) {
        Geometry[] remGeom = removeCommonBits(geom2);
        return GeometrySnapper.snap(remGeom[0], remGeom[1], this.snapTolerance);
    }

    private Geometry prepareResult(Geometry geometry) {
        Geometry geom2 = geometry;
        this.cbr.addCommonBits(geom2);
        return geom2;
    }

    private Geometry[] removeCommonBits(Geometry[] geometryArr) {
        CommonBitsRemover commonBitsRemover;
        Geometry[] geom2 = geometryArr;
        new CommonBitsRemover();
        this.cbr = commonBitsRemover;
        this.cbr.add(geom2[0]);
        this.cbr.add(geom2[1]);
        return new Geometry[]{this.cbr.removeCommonBits((Geometry) geom2[0].clone()), this.cbr.removeCommonBits((Geometry) geom2[1].clone())};
    }

    private void checkValid(Geometry g) {
        if (!g.isValid()) {
            System.out.println("Snapped geometry is invalid");
        }
    }
}
