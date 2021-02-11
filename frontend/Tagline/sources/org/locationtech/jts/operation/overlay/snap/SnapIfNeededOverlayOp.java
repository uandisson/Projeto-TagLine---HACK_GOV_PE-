package org.locationtech.jts.operation.overlay.snap;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.operation.overlay.OverlayOp;

public class SnapIfNeededOverlayOp {
    private Geometry[] geom = new Geometry[2];

    public static Geometry overlayOp(Geometry g0, Geometry g1, int opCode) {
        SnapIfNeededOverlayOp op;
        new SnapIfNeededOverlayOp(g0, g1);
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

    public SnapIfNeededOverlayOp(Geometry g1, Geometry g2) {
        this.geom[0] = g1;
        this.geom[1] = g2;
    }

    public Geometry getResultGeometry(int i) {
        int opCode = i;
        Geometry result = null;
        boolean isSuccess = false;
        RuntimeException savedException = null;
        try {
            result = OverlayOp.overlayOp(this.geom[0], this.geom[1], opCode);
            if (1 != 0) {
                isSuccess = true;
            }
        } catch (RuntimeException e) {
            savedException = e;
        }
        if (!isSuccess) {
            try {
                result = SnapOverlayOp.overlayOp(this.geom[0], this.geom[1], opCode);
            } catch (RuntimeException e2) {
                RuntimeException runtimeException = e2;
                throw savedException;
            }
        }
        return result;
    }
}
