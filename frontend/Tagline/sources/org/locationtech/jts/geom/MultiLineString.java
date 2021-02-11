package org.locationtech.jts.geom;

import org.locationtech.jts.operation.BoundaryOp;

public class MultiLineString extends GeometryCollection implements Lineal {
    private static final long serialVersionUID = 8166665132445433741L;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MultiLineString(org.locationtech.jts.geom.LineString[] r12, org.locationtech.jts.geom.PrecisionModel r13, int r14) {
        /*
            r11 = this;
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r0
            r5 = r1
            org.locationtech.jts.geom.GeometryFactory r6 = new org.locationtech.jts.geom.GeometryFactory
            r10 = r6
            r6 = r10
            r7 = r10
            r8 = r2
            r9 = r3
            r7.<init>(r8, r9)
            r4.<init>(r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.MultiLineString.<init>(org.locationtech.jts.geom.LineString[], org.locationtech.jts.geom.PrecisionModel, int):void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MultiLineString(LineString[] lineStrings, GeometryFactory factory) {
        super(lineStrings, factory);
    }

    public int getDimension() {
        return 1;
    }

    public int getBoundaryDimension() {
        if (isClosed()) {
            return -1;
        }
        return 0;
    }

    public String getGeometryType() {
        return "MultiLineString";
    }

    public boolean isClosed() {
        if (isEmpty()) {
            return false;
        }
        for (int i = 0; i < this.geometries.length; i++) {
            if (!((LineString) this.geometries[i]).isClosed()) {
                return false;
            }
        }
        return true;
    }

    public Geometry getBoundary() {
        BoundaryOp boundaryOp;
        new BoundaryOp(this);
        return boundaryOp.getBoundary();
    }

    public Geometry reverse() {
        int nLines = this.geometries.length;
        LineString[] revLines = new LineString[nLines];
        for (int i = 0; i < this.geometries.length; i++) {
            revLines[(nLines - 1) - i] = (LineString) this.geometries[i].reverse();
        }
        return getFactory().createMultiLineString(revLines);
    }

    public boolean equalsExact(Geometry geometry, double d) {
        Geometry other = geometry;
        double tolerance = d;
        if (!isEquivalentClass(other)) {
            return false;
        }
        return super.equalsExact(other, tolerance);
    }

    /* access modifiers changed from: protected */
    public int getSortIndex() {
        return 4;
    }
}
