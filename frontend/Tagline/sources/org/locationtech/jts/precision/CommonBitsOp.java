package org.locationtech.jts.precision;

import org.locationtech.jts.geom.Geometry;

public class CommonBitsOp {
    private CommonBitsRemover cbr;
    private boolean returnToOriginalPrecision;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public CommonBitsOp() {
        this(true);
    }

    public CommonBitsOp(boolean returnToOriginalPrecision2) {
        this.returnToOriginalPrecision = true;
        this.returnToOriginalPrecision = returnToOriginalPrecision2;
    }

    public Geometry intersection(Geometry geom0, Geometry geom1) {
        Geometry[] geom = removeCommonBits(geom0, geom1);
        return computeResultPrecision(geom[0].intersection(geom[1]));
    }

    public Geometry union(Geometry geom0, Geometry geom1) {
        Geometry[] geom = removeCommonBits(geom0, geom1);
        return computeResultPrecision(geom[0].union(geom[1]));
    }

    public Geometry difference(Geometry geom0, Geometry geom1) {
        Geometry[] geom = removeCommonBits(geom0, geom1);
        return computeResultPrecision(geom[0].difference(geom[1]));
    }

    public Geometry symDifference(Geometry geom0, Geometry geom1) {
        Geometry[] geom = removeCommonBits(geom0, geom1);
        return computeResultPrecision(geom[0].symDifference(geom[1]));
    }

    public Geometry buffer(Geometry geom0, double distance) {
        return computeResultPrecision(removeCommonBits(geom0).buffer(distance));
    }

    private Geometry computeResultPrecision(Geometry geometry) {
        Geometry result = geometry;
        if (this.returnToOriginalPrecision) {
            this.cbr.addCommonBits(result);
        }
        return result;
    }

    private Geometry removeCommonBits(Geometry geometry) {
        CommonBitsRemover commonBitsRemover;
        Geometry geom0 = geometry;
        new CommonBitsRemover();
        this.cbr = commonBitsRemover;
        this.cbr.add(geom0);
        return this.cbr.removeCommonBits((Geometry) geom0.clone());
    }

    private Geometry[] removeCommonBits(Geometry geometry, Geometry geometry2) {
        CommonBitsRemover commonBitsRemover;
        Geometry geom0 = geometry;
        Geometry geom1 = geometry2;
        new CommonBitsRemover();
        this.cbr = commonBitsRemover;
        this.cbr.add(geom0);
        this.cbr.add(geom1);
        return new Geometry[]{this.cbr.removeCommonBits((Geometry) geom0.clone()), this.cbr.removeCommonBits((Geometry) geom1.clone())};
    }
}
