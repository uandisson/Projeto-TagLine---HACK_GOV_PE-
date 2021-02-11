package org.locationtech.jts.geom;

public class MultiPoint extends GeometryCollection implements Puntal {
    private static final long serialVersionUID = -8048474874175355449L;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MultiPoint(org.locationtech.jts.geom.Point[] r12, org.locationtech.jts.geom.PrecisionModel r13, int r14) {
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
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.MultiPoint.<init>(org.locationtech.jts.geom.Point[], org.locationtech.jts.geom.PrecisionModel, int):void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MultiPoint(Point[] points, GeometryFactory factory) {
        super(points, factory);
    }

    public int getDimension() {
        return 0;
    }

    public int getBoundaryDimension() {
        return -1;
    }

    public String getGeometryType() {
        return "MultiPoint";
    }

    public Geometry getBoundary() {
        return getFactory().createGeometryCollection((Geometry[]) null);
    }

    public boolean isValid() {
        return true;
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
    public Coordinate getCoordinate(int n) {
        return ((Point) this.geometries[n]).getCoordinate();
    }

    /* access modifiers changed from: protected */
    public int getSortIndex() {
        return 1;
    }
}
