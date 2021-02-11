package org.locationtech.jts.geom;

import java.util.Arrays;
import org.locationtech.jts.algorithm.CGAlgorithms;

public class Polygon extends Geometry implements Polygonal {
    private static final long serialVersionUID = -3494792200821764533L;
    protected LinearRing[] holes;
    protected LinearRing shell;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Polygon(org.locationtech.jts.geom.LinearRing r13, org.locationtech.jts.geom.PrecisionModel r14, int r15) {
        /*
            r12 = this;
            r0 = r12
            r1 = r13
            r2 = r14
            r3 = r15
            r4 = r0
            r5 = r1
            r6 = 0
            org.locationtech.jts.geom.LinearRing[] r6 = new org.locationtech.jts.geom.LinearRing[r6]
            org.locationtech.jts.geom.GeometryFactory r7 = new org.locationtech.jts.geom.GeometryFactory
            r11 = r7
            r7 = r11
            r8 = r11
            r9 = r2
            r10 = r3
            r8.<init>(r9, r10)
            r4.<init>((org.locationtech.jts.geom.LinearRing) r5, (org.locationtech.jts.geom.LinearRing[]) r6, (org.locationtech.jts.geom.GeometryFactory) r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.Polygon.<init>(org.locationtech.jts.geom.LinearRing, org.locationtech.jts.geom.PrecisionModel, int):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Polygon(org.locationtech.jts.geom.LinearRing r14, org.locationtech.jts.geom.LinearRing[] r15, org.locationtech.jts.geom.PrecisionModel r16, int r17) {
        /*
            r13 = this;
            r0 = r13
            r1 = r14
            r2 = r15
            r3 = r16
            r4 = r17
            r5 = r0
            r6 = r1
            r7 = r2
            org.locationtech.jts.geom.GeometryFactory r8 = new org.locationtech.jts.geom.GeometryFactory
            r12 = r8
            r8 = r12
            r9 = r12
            r10 = r3
            r11 = r4
            r9.<init>(r10, r11)
            r5.<init>((org.locationtech.jts.geom.LinearRing) r6, (org.locationtech.jts.geom.LinearRing[]) r7, (org.locationtech.jts.geom.GeometryFactory) r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.Polygon.<init>(org.locationtech.jts.geom.LinearRing, org.locationtech.jts.geom.LinearRing[], org.locationtech.jts.geom.PrecisionModel, int):void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public Polygon(LinearRing linearRing, LinearRing[] linearRingArr, GeometryFactory factory) {
        super(factory);
        Throwable th;
        Throwable th2;
        LinearRing shell2 = linearRing;
        LinearRing[] holes2 = linearRingArr;
        this.shell = null;
        shell2 = shell2 == null ? getFactory().createLinearRing((CoordinateSequence) null) : shell2;
        holes2 = holes2 == null ? new LinearRing[0] : holes2;
        if (hasNullElements(holes2)) {
            Throwable th3 = th2;
            new IllegalArgumentException("holes must not contain null elements");
            throw th3;
        } else if (!shell2.isEmpty() || !hasNonEmptyElements(holes2)) {
            this.shell = shell2;
            this.holes = holes2;
        } else {
            Throwable th4 = th;
            new IllegalArgumentException("shell is empty but holes are not");
            throw th4;
        }
    }

    public Coordinate getCoordinate() {
        return this.shell.getCoordinate();
    }

    public Coordinate[] getCoordinates() {
        if (isEmpty()) {
            return new Coordinate[0];
        }
        Coordinate[] coordinates = new Coordinate[getNumPoints()];
        int k = -1;
        Coordinate[] shellCoordinates = this.shell.getCoordinates();
        for (int x = 0; x < shellCoordinates.length; x++) {
            k++;
            coordinates[k] = shellCoordinates[x];
        }
        for (int i = 0; i < this.holes.length; i++) {
            Coordinate[] childCoordinates = this.holes[i].getCoordinates();
            for (int j = 0; j < childCoordinates.length; j++) {
                k++;
                coordinates[k] = childCoordinates[j];
            }
        }
        return coordinates;
    }

    public int getNumPoints() {
        int numPoints = this.shell.getNumPoints();
        for (int i = 0; i < this.holes.length; i++) {
            numPoints += this.holes[i].getNumPoints();
        }
        return numPoints;
    }

    public int getDimension() {
        return 2;
    }

    public int getBoundaryDimension() {
        return 1;
    }

    public boolean isEmpty() {
        return this.shell.isEmpty();
    }

    public boolean isRectangle() {
        if (getNumInteriorRing() != 0) {
            return false;
        }
        if (this.shell == null) {
            return false;
        }
        if (this.shell.getNumPoints() != 5) {
            return false;
        }
        CoordinateSequence seq = this.shell.getCoordinateSequence();
        Envelope env = getEnvelopeInternal();
        for (int i = 0; i < 5; i++) {
            double x = seq.getX(i);
            if (x != env.getMinX() && x != env.getMaxX()) {
                return false;
            }
            double y = seq.getY(i);
            if (y != env.getMinY() && y != env.getMaxY()) {
                return false;
            }
        }
        double prevX = seq.getX(0);
        double prevY = seq.getY(0);
        for (int i2 = 1; i2 <= 4; i2++) {
            double x2 = seq.getX(i2);
            double y2 = seq.getY(i2);
            if ((x2 != prevX) == (y2 != prevY)) {
                return false;
            }
            prevX = x2;
            prevY = y2;
        }
        return true;
    }

    public LineString getExteriorRing() {
        return this.shell;
    }

    public int getNumInteriorRing() {
        return this.holes.length;
    }

    public LineString getInteriorRingN(int n) {
        return this.holes[n];
    }

    public String getGeometryType() {
        return "Polygon";
    }

    public double getArea() {
        double area = 0.0d + Math.abs(CGAlgorithms.signedArea(this.shell.getCoordinateSequence()));
        for (int i = 0; i < this.holes.length; i++) {
            area -= Math.abs(CGAlgorithms.signedArea(this.holes[i].getCoordinateSequence()));
        }
        return area;
    }

    public double getLength() {
        double len = 0.0d + this.shell.getLength();
        for (int i = 0; i < this.holes.length; i++) {
            len += this.holes[i].getLength();
        }
        return len;
    }

    public Geometry getBoundary() {
        if (isEmpty()) {
            return getFactory().createMultiLineString((LineString[]) null);
        }
        LinearRing[] rings = new LinearRing[(this.holes.length + 1)];
        rings[0] = this.shell;
        for (int i = 0; i < this.holes.length; i++) {
            rings[i + 1] = this.holes[i];
        }
        if (rings.length <= 1) {
            return getFactory().createLinearRing(rings[0].getCoordinateSequence());
        }
        return getFactory().createMultiLineString(rings);
    }

    /* access modifiers changed from: protected */
    public Envelope computeEnvelopeInternal() {
        return this.shell.getEnvelopeInternal();
    }

    public boolean equalsExact(Geometry geometry, double d) {
        Geometry other = geometry;
        double tolerance = d;
        if (!isEquivalentClass(other)) {
            return false;
        }
        Polygon otherPolygon = (Polygon) other;
        if (!this.shell.equalsExact(otherPolygon.shell, tolerance)) {
            return false;
        }
        if (this.holes.length != otherPolygon.holes.length) {
            return false;
        }
        for (int i = 0; i < this.holes.length; i++) {
            if (!this.holes[i].equalsExact(otherPolygon.holes[i], tolerance)) {
                return false;
            }
        }
        return true;
    }

    public void apply(CoordinateFilter coordinateFilter) {
        CoordinateFilter filter = coordinateFilter;
        this.shell.apply(filter);
        for (int i = 0; i < this.holes.length; i++) {
            this.holes[i].apply(filter);
        }
    }

    public void apply(CoordinateSequenceFilter coordinateSequenceFilter) {
        CoordinateSequenceFilter filter = coordinateSequenceFilter;
        this.shell.apply(filter);
        if (!filter.isDone()) {
            for (int i = 0; i < this.holes.length; i++) {
                this.holes[i].apply(filter);
                if (filter.isDone()) {
                    break;
                }
            }
        }
        if (filter.isGeometryChanged()) {
            geometryChanged();
        }
    }

    public void apply(GeometryFilter filter) {
        filter.filter(this);
    }

    public void apply(GeometryComponentFilter geometryComponentFilter) {
        GeometryComponentFilter filter = geometryComponentFilter;
        filter.filter(this);
        this.shell.apply(filter);
        for (int i = 0; i < this.holes.length; i++) {
            this.holes[i].apply(filter);
        }
    }

    public Object clone() {
        Polygon poly = (Polygon) super.clone();
        poly.shell = (LinearRing) this.shell.clone();
        poly.holes = new LinearRing[this.holes.length];
        for (int i = 0; i < this.holes.length; i++) {
            poly.holes[i] = (LinearRing) this.holes[i].clone();
        }
        return poly;
    }

    public Geometry convexHull() {
        return getExteriorRing().convexHull();
    }

    public void normalize() {
        normalize(this.shell, true);
        for (int i = 0; i < this.holes.length; i++) {
            normalize(this.holes[i], false);
        }
        Arrays.sort(this.holes);
    }

    /* access modifiers changed from: protected */
    public int compareToSameClass(Object o) {
        return this.shell.compareToSameClass(((Polygon) o).shell);
    }

    /* access modifiers changed from: protected */
    public int compareToSameClass(Object o, CoordinateSequenceComparator coordinateSequenceComparator) {
        CoordinateSequenceComparator comp = coordinateSequenceComparator;
        Polygon poly = (Polygon) o;
        int shellComp = this.shell.compareToSameClass(poly.shell, comp);
        if (shellComp != 0) {
            return shellComp;
        }
        int nHole1 = getNumInteriorRing();
        int nHole2 = poly.getNumInteriorRing();
        int i = 0;
        while (i < nHole1 && i < nHole2) {
            int holeComp = ((LinearRing) getInteriorRingN(i)).compareToSameClass((LinearRing) poly.getInteriorRingN(i), comp);
            if (holeComp != 0) {
                return holeComp;
            }
            i++;
        }
        if (i < nHole1) {
            return 1;
        }
        if (i < nHole2) {
            return -1;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getSortIndex() {
        return 5;
    }

    private void normalize(LinearRing linearRing, boolean z) {
        LinearRing ring = linearRing;
        boolean clockwise = z;
        if (!ring.isEmpty()) {
            Coordinate[] uniqueCoordinates = new Coordinate[(ring.getCoordinates().length - 1)];
            System.arraycopy(ring.getCoordinates(), 0, uniqueCoordinates, 0, uniqueCoordinates.length);
            CoordinateArrays.scroll(uniqueCoordinates, CoordinateArrays.minCoordinate(ring.getCoordinates()));
            System.arraycopy(uniqueCoordinates, 0, ring.getCoordinates(), 0, uniqueCoordinates.length);
            ring.getCoordinates()[uniqueCoordinates.length] = uniqueCoordinates[0];
            if (CGAlgorithms.isCCW(ring.getCoordinates()) == clockwise) {
                CoordinateArrays.reverse(ring.getCoordinates());
            }
        }
    }

    public Geometry reverse() {
        Polygon poly = (Polygon) super.clone();
        poly.shell = (LinearRing) ((LinearRing) this.shell.clone()).reverse();
        poly.holes = new LinearRing[this.holes.length];
        for (int i = 0; i < this.holes.length; i++) {
            poly.holes[i] = (LinearRing) ((LinearRing) this.holes[i].clone()).reverse();
        }
        return poly;
    }
}
