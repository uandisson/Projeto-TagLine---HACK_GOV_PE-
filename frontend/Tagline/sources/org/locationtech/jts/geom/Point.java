package org.locationtech.jts.geom;

import org.locationtech.jts.util.Assert;

public class Point extends Geometry implements Puntal {
    private static final long serialVersionUID = 4902022702746614570L;
    private CoordinateSequence coordinates;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Point(org.locationtech.jts.geom.Coordinate r12, org.locationtech.jts.geom.PrecisionModel r13, int r14) {
        /*
            r11 = this;
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r14
            r4 = r0
            org.locationtech.jts.geom.GeometryFactory r5 = new org.locationtech.jts.geom.GeometryFactory
            r10 = r5
            r5 = r10
            r6 = r10
            r7 = r2
            r8 = r3
            r6.<init>(r7, r8)
            r4.<init>(r5)
            r4 = r0
            r5 = r0
            org.locationtech.jts.geom.GeometryFactory r5 = r5.getFactory()
            org.locationtech.jts.geom.CoordinateSequenceFactory r5 = r5.getCoordinateSequenceFactory()
            r6 = r1
            if (r6 == 0) goto L_0x0031
            r6 = 1
            org.locationtech.jts.geom.Coordinate[] r6 = new org.locationtech.jts.geom.Coordinate[r6]
            r10 = r6
            r6 = r10
            r7 = r10
            r8 = 0
            r9 = r1
            r7[r8] = r9
        L_0x0029:
            org.locationtech.jts.geom.CoordinateSequence r5 = r5.create((org.locationtech.jts.geom.Coordinate[]) r6)
            r4.init(r5)
            return
        L_0x0031:
            r6 = 0
            org.locationtech.jts.geom.Coordinate[] r6 = new org.locationtech.jts.geom.Coordinate[r6]
            goto L_0x0029
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.Point.<init>(org.locationtech.jts.geom.Coordinate, org.locationtech.jts.geom.PrecisionModel, int):void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public Point(CoordinateSequence coordinates2, GeometryFactory factory) {
        super(factory);
        init(coordinates2);
    }

    private void init(CoordinateSequence coordinateSequence) {
        CoordinateSequence coordinates2 = coordinateSequence;
        if (coordinates2 == null) {
            coordinates2 = getFactory().getCoordinateSequenceFactory().create(new Coordinate[0]);
        }
        Assert.isTrue(coordinates2.size() <= 1);
        this.coordinates = coordinates2;
    }

    public Coordinate[] getCoordinates() {
        Coordinate[] coordinateArr;
        if (isEmpty()) {
            coordinateArr = new Coordinate[0];
        } else {
            Coordinate[] coordinateArr2 = new Coordinate[1];
            coordinateArr = coordinateArr2;
            coordinateArr2[0] = getCoordinate();
        }
        return coordinateArr;
    }

    public int getNumPoints() {
        return isEmpty() ? 0 : 1;
    }

    public boolean isEmpty() {
        return this.coordinates.size() == 0;
    }

    public boolean isSimple() {
        return true;
    }

    public int getDimension() {
        return 0;
    }

    public int getBoundaryDimension() {
        return -1;
    }

    public double getX() {
        Throwable th;
        if (getCoordinate() != null) {
            return getCoordinate().f412x;
        }
        Throwable th2 = th;
        new IllegalStateException("getX called on empty Point");
        throw th2;
    }

    public double getY() {
        Throwable th;
        if (getCoordinate() != null) {
            return getCoordinate().f413y;
        }
        Throwable th2 = th;
        new IllegalStateException("getY called on empty Point");
        throw th2;
    }

    public Coordinate getCoordinate() {
        return this.coordinates.size() != 0 ? this.coordinates.getCoordinate(0) : null;
    }

    public String getGeometryType() {
        return "Point";
    }

    public Geometry getBoundary() {
        return getFactory().createGeometryCollection((Geometry[]) null);
    }

    /* access modifiers changed from: protected */
    public Envelope computeEnvelopeInternal() {
        Envelope envelope;
        Envelope envelope2;
        if (isEmpty()) {
            new Envelope();
            return envelope2;
        }
        new Envelope();
        Envelope env = envelope;
        env.expandToInclude(this.coordinates.getX(0), this.coordinates.getY(0));
        return env;
    }

    public boolean equalsExact(Geometry geometry, double d) {
        Geometry other = geometry;
        double tolerance = d;
        if (!isEquivalentClass(other)) {
            return false;
        }
        if (isEmpty() && other.isEmpty()) {
            return true;
        }
        if (isEmpty() != other.isEmpty()) {
            return false;
        }
        return equal(((Point) other).getCoordinate(), getCoordinate(), tolerance);
    }

    public void apply(CoordinateFilter coordinateFilter) {
        CoordinateFilter filter = coordinateFilter;
        if (!isEmpty()) {
            filter.filter(getCoordinate());
        }
    }

    public void apply(CoordinateSequenceFilter coordinateSequenceFilter) {
        CoordinateSequenceFilter filter = coordinateSequenceFilter;
        if (!isEmpty()) {
            filter.filter(this.coordinates, 0);
            if (filter.isGeometryChanged()) {
                geometryChanged();
            }
        }
    }

    public void apply(GeometryFilter filter) {
        filter.filter(this);
    }

    public void apply(GeometryComponentFilter filter) {
        filter.filter(this);
    }

    public Object clone() {
        Point p = (Point) super.clone();
        p.coordinates = (CoordinateSequence) this.coordinates.clone();
        return p;
    }

    public Geometry reverse() {
        return (Geometry) clone();
    }

    public void normalize() {
    }

    /* access modifiers changed from: protected */
    public int compareToSameClass(Object other) {
        return getCoordinate().compareTo(((Point) other).getCoordinate());
    }

    /* access modifiers changed from: protected */
    public int compareToSameClass(Object other, CoordinateSequenceComparator comp) {
        return comp.compare((Object) this.coordinates, (Object) ((Point) other).coordinates);
    }

    /* access modifiers changed from: protected */
    public int getSortIndex() {
        return 0;
    }

    public CoordinateSequence getCoordinateSequence() {
        return this.coordinates;
    }
}
