package org.locationtech.jts.geom;

import org.locationtech.jts.p006io.gml2.GMLConstants;

public class LinearRing extends LineString {
    public static final int MINIMUM_VALID_SIZE = 4;
    private static final long serialVersionUID = -4261142084085851829L;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LinearRing(org.locationtech.jts.geom.Coordinate[] r12, org.locationtech.jts.geom.PrecisionModel r13, int r14) {
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
            r4.<init>((org.locationtech.jts.geom.Coordinate[]) r5, (org.locationtech.jts.geom.GeometryFactory) r6)
            r4 = r0
            r4.validateConstruction()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.LinearRing.<init>(org.locationtech.jts.geom.Coordinate[], org.locationtech.jts.geom.PrecisionModel, int):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private LinearRing(org.locationtech.jts.geom.Coordinate[] r7, org.locationtech.jts.geom.GeometryFactory r8) {
        /*
            r6 = this;
            r0 = r6
            r1 = r7
            r2 = r8
            r3 = r0
            r4 = r2
            org.locationtech.jts.geom.CoordinateSequenceFactory r4 = r4.getCoordinateSequenceFactory()
            r5 = r1
            org.locationtech.jts.geom.CoordinateSequence r4 = r4.create((org.locationtech.jts.geom.Coordinate[]) r5)
            r5 = r2
            r3.<init>((org.locationtech.jts.geom.CoordinateSequence) r4, (org.locationtech.jts.geom.GeometryFactory) r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.LinearRing.<init>(org.locationtech.jts.geom.Coordinate[], org.locationtech.jts.geom.GeometryFactory):void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LinearRing(CoordinateSequence points, GeometryFactory factory) {
        super(points, factory);
        validateConstruction();
    }

    private void validateConstruction() {
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        if (!isEmpty() && !super.isClosed()) {
            Throwable th3 = th2;
            new IllegalArgumentException("Points of LinearRing do not form a closed linestring");
            throw th3;
        } else if (getCoordinateSequence().size() >= 1 && getCoordinateSequence().size() < 4) {
            Throwable th4 = th;
            new StringBuilder();
            new IllegalArgumentException(sb.append("Invalid number of points in LinearRing (found ").append(getCoordinateSequence().size()).append(" - must be 0 or >= 4)").toString());
            throw th4;
        }
    }

    public int getBoundaryDimension() {
        return -1;
    }

    public boolean isClosed() {
        if (isEmpty()) {
            return true;
        }
        return super.isClosed();
    }

    public String getGeometryType() {
        return GMLConstants.GML_LINEARRING;
    }

    /* access modifiers changed from: protected */
    public int getSortIndex() {
        return 3;
    }

    public Geometry reverse() {
        CoordinateSequence seq = (CoordinateSequence) this.points.clone();
        CoordinateSequences.reverse(seq);
        return getFactory().createLinearRing(seq);
    }
}
