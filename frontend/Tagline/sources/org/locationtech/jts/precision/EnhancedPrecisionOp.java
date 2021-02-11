package org.locationtech.jts.precision;

import org.locationtech.jts.geom.Geometry;

public class EnhancedPrecisionOp {
    public EnhancedPrecisionOp() {
    }

    public static Geometry intersection(Geometry geometry, Geometry geometry2) {
        RuntimeException originalEx;
        CommonBitsOp cbo;
        Geometry geom0 = geometry;
        Geometry geom1 = geometry2;
        try {
            return geom0.intersection(geom1);
        } catch (RuntimeException e) {
            originalEx = e;
            new CommonBitsOp(true);
            Geometry resultEP = cbo.intersection(geom0, geom1);
            if (resultEP.isValid()) {
                return resultEP;
            }
            throw originalEx;
        } catch (RuntimeException e2) {
            RuntimeException runtimeException = e2;
            throw originalEx;
        }
    }

    public static Geometry union(Geometry geometry, Geometry geometry2) {
        RuntimeException originalEx;
        CommonBitsOp cbo;
        Geometry geom0 = geometry;
        Geometry geom1 = geometry2;
        try {
            return geom0.union(geom1);
        } catch (RuntimeException e) {
            originalEx = e;
            new CommonBitsOp(true);
            Geometry resultEP = cbo.union(geom0, geom1);
            if (resultEP.isValid()) {
                return resultEP;
            }
            throw originalEx;
        } catch (RuntimeException e2) {
            RuntimeException runtimeException = e2;
            throw originalEx;
        }
    }

    public static Geometry difference(Geometry geometry, Geometry geometry2) {
        RuntimeException originalEx;
        CommonBitsOp cbo;
        Geometry geom0 = geometry;
        Geometry geom1 = geometry2;
        try {
            return geom0.difference(geom1);
        } catch (RuntimeException e) {
            originalEx = e;
            new CommonBitsOp(true);
            Geometry resultEP = cbo.difference(geom0, geom1);
            if (resultEP.isValid()) {
                return resultEP;
            }
            throw originalEx;
        } catch (RuntimeException e2) {
            RuntimeException runtimeException = e2;
            throw originalEx;
        }
    }

    public static Geometry symDifference(Geometry geometry, Geometry geometry2) {
        RuntimeException originalEx;
        CommonBitsOp cbo;
        Geometry geom0 = geometry;
        Geometry geom1 = geometry2;
        try {
            return geom0.symDifference(geom1);
        } catch (RuntimeException e) {
            originalEx = e;
            new CommonBitsOp(true);
            Geometry resultEP = cbo.symDifference(geom0, geom1);
            if (resultEP.isValid()) {
                return resultEP;
            }
            throw originalEx;
        } catch (RuntimeException e2) {
            RuntimeException runtimeException = e2;
            throw originalEx;
        }
    }

    public static Geometry buffer(Geometry geometry, double d) {
        RuntimeException originalEx;
        CommonBitsOp cbo;
        Geometry geom = geometry;
        double distance = d;
        try {
            return geom.buffer(distance);
        } catch (RuntimeException e) {
            originalEx = e;
            new CommonBitsOp(true);
            Geometry resultEP = cbo.buffer(geom, distance);
            if (resultEP.isValid()) {
                return resultEP;
            }
            throw originalEx;
        } catch (RuntimeException e2) {
            RuntimeException runtimeException = e2;
            throw originalEx;
        }
    }
}
