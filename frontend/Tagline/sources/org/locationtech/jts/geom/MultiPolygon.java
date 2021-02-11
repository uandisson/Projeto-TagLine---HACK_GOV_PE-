package org.locationtech.jts.geom;

import java.util.ArrayList;

public class MultiPolygon extends GeometryCollection implements Polygonal {
    private static final long serialVersionUID = -551033529766975875L;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MultiPolygon(org.locationtech.jts.geom.Polygon[] r12, org.locationtech.jts.geom.PrecisionModel r13, int r14) {
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
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.MultiPolygon.<init>(org.locationtech.jts.geom.Polygon[], org.locationtech.jts.geom.PrecisionModel, int):void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MultiPolygon(Polygon[] polygons, GeometryFactory factory) {
        super(polygons, factory);
    }

    public int getDimension() {
        return 2;
    }

    public int getBoundaryDimension() {
        return 1;
    }

    public String getGeometryType() {
        return "MultiPolygon";
    }

    public Geometry getBoundary() {
        ArrayList arrayList;
        if (isEmpty()) {
            return getFactory().createMultiLineString((LineString[]) null);
        }
        new ArrayList();
        ArrayList allRings = arrayList;
        for (int i = 0; i < this.geometries.length; i++) {
            Geometry rings = ((Polygon) this.geometries[i]).getBoundary();
            for (int j = 0; j < rings.getNumGeometries(); j++) {
                boolean add = allRings.add(rings.getGeometryN(j));
            }
        }
        return getFactory().createMultiLineString((LineString[]) allRings.toArray(new LineString[allRings.size()]));
    }

    public boolean equalsExact(Geometry geometry, double d) {
        Geometry other = geometry;
        double tolerance = d;
        if (!isEquivalentClass(other)) {
            return false;
        }
        return super.equalsExact(other, tolerance);
    }

    public Geometry reverse() {
        Polygon[] revGeoms = new Polygon[this.geometries.length];
        for (int i = 0; i < this.geometries.length; i++) {
            revGeoms[i] = (Polygon) this.geometries[i].reverse();
        }
        return getFactory().createMultiPolygon(revGeoms);
    }

    /* access modifiers changed from: protected */
    public int getSortIndex() {
        return 6;
    }
}
