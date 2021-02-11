package org.locationtech.jts.geom;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;
import org.locationtech.jts.util.Assert;

public class GeometryCollection extends Geometry {
    private static final long serialVersionUID = -5694727726395021467L;
    protected Geometry[] geometries;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public GeometryCollection(org.locationtech.jts.geom.Geometry[] r12, org.locationtech.jts.geom.PrecisionModel r13, int r14) {
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
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.GeometryCollection.<init>(org.locationtech.jts.geom.Geometry[], org.locationtech.jts.geom.PrecisionModel, int):void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public GeometryCollection(Geometry[] geometryArr, GeometryFactory factory) {
        super(factory);
        Throwable th;
        Geometry[] geometries2 = geometryArr;
        geometries2 = geometries2 == null ? new Geometry[0] : geometries2;
        if (hasNullElements(geometries2)) {
            Throwable th2 = th;
            new IllegalArgumentException("geometries must not contain null elements");
            throw th2;
        }
        this.geometries = geometries2;
    }

    public Coordinate getCoordinate() {
        if (isEmpty()) {
            return null;
        }
        return this.geometries[0].getCoordinate();
    }

    public Coordinate[] getCoordinates() {
        Coordinate[] coordinates = new Coordinate[getNumPoints()];
        int k = -1;
        for (int i = 0; i < this.geometries.length; i++) {
            Coordinate[] childCoordinates = this.geometries[i].getCoordinates();
            for (int j = 0; j < childCoordinates.length; j++) {
                k++;
                coordinates[k] = childCoordinates[j];
            }
        }
        return coordinates;
    }

    public boolean isEmpty() {
        for (int i = 0; i < this.geometries.length; i++) {
            if (!this.geometries[i].isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int getDimension() {
        int dimension = -1;
        for (int i = 0; i < this.geometries.length; i++) {
            dimension = Math.max(dimension, this.geometries[i].getDimension());
        }
        return dimension;
    }

    public int getBoundaryDimension() {
        int dimension = -1;
        for (int i = 0; i < this.geometries.length; i++) {
            dimension = Math.max(dimension, this.geometries[i].getBoundaryDimension());
        }
        return dimension;
    }

    public int getNumGeometries() {
        return this.geometries.length;
    }

    public Geometry getGeometryN(int n) {
        return this.geometries[n];
    }

    public int getNumPoints() {
        int numPoints = 0;
        for (int i = 0; i < this.geometries.length; i++) {
            numPoints += this.geometries[i].getNumPoints();
        }
        return numPoints;
    }

    public String getGeometryType() {
        return "GeometryCollection";
    }

    public Geometry getBoundary() {
        checkNotGeometryCollection(this);
        Assert.shouldNeverReachHere();
        return null;
    }

    public double getArea() {
        double area = 0.0d;
        for (int i = 0; i < this.geometries.length; i++) {
            area += this.geometries[i].getArea();
        }
        return area;
    }

    public double getLength() {
        double sum = 0.0d;
        for (int i = 0; i < this.geometries.length; i++) {
            sum += this.geometries[i].getLength();
        }
        return sum;
    }

    public boolean equalsExact(Geometry geometry, double d) {
        Geometry other = geometry;
        double tolerance = d;
        if (!isEquivalentClass(other)) {
            return false;
        }
        GeometryCollection otherCollection = (GeometryCollection) other;
        if (this.geometries.length != otherCollection.geometries.length) {
            return false;
        }
        for (int i = 0; i < this.geometries.length; i++) {
            if (!this.geometries[i].equalsExact(otherCollection.geometries[i], tolerance)) {
                return false;
            }
        }
        return true;
    }

    public void apply(CoordinateFilter coordinateFilter) {
        CoordinateFilter filter = coordinateFilter;
        for (int i = 0; i < this.geometries.length; i++) {
            this.geometries[i].apply(filter);
        }
    }

    public void apply(CoordinateSequenceFilter coordinateSequenceFilter) {
        CoordinateSequenceFilter filter = coordinateSequenceFilter;
        if (this.geometries.length != 0) {
            for (int i = 0; i < this.geometries.length; i++) {
                this.geometries[i].apply(filter);
                if (filter.isDone()) {
                    break;
                }
            }
            if (filter.isGeometryChanged()) {
                geometryChanged();
            }
        }
    }

    public void apply(GeometryFilter geometryFilter) {
        GeometryFilter filter = geometryFilter;
        filter.filter(this);
        for (int i = 0; i < this.geometries.length; i++) {
            this.geometries[i].apply(filter);
        }
    }

    public void apply(GeometryComponentFilter geometryComponentFilter) {
        GeometryComponentFilter filter = geometryComponentFilter;
        filter.filter(this);
        for (int i = 0; i < this.geometries.length; i++) {
            this.geometries[i].apply(filter);
        }
    }

    public Object clone() {
        GeometryCollection gc = (GeometryCollection) super.clone();
        gc.geometries = new Geometry[this.geometries.length];
        for (int i = 0; i < this.geometries.length; i++) {
            gc.geometries[i] = (Geometry) this.geometries[i].clone();
        }
        return gc;
    }

    public void normalize() {
        for (int i = 0; i < this.geometries.length; i++) {
            this.geometries[i].normalize();
        }
        Arrays.sort(this.geometries);
    }

    /* access modifiers changed from: protected */
    public Envelope computeEnvelopeInternal() {
        Envelope envelope;
        new Envelope();
        Envelope envelope2 = envelope;
        for (int i = 0; i < this.geometries.length; i++) {
            envelope2.expandToInclude(this.geometries[i].getEnvelopeInternal());
        }
        return envelope2;
    }

    /* access modifiers changed from: protected */
    public int compareToSameClass(Object o) {
        Collection collection;
        Collection collection2;
        new TreeSet(Arrays.asList(this.geometries));
        Collection collection3 = collection;
        new TreeSet(Arrays.asList(((GeometryCollection) o).geometries));
        return compare(collection3, collection2);
    }

    /* access modifiers changed from: protected */
    public int compareToSameClass(Object o, CoordinateSequenceComparator coordinateSequenceComparator) {
        CoordinateSequenceComparator comp = coordinateSequenceComparator;
        GeometryCollection gc = (GeometryCollection) o;
        int n1 = getNumGeometries();
        int n2 = gc.getNumGeometries();
        int i = 0;
        while (i < n1 && i < n2) {
            int holeComp = getGeometryN(i).compareToSameClass(gc.getGeometryN(i), comp);
            if (holeComp != 0) {
                return holeComp;
            }
            i++;
        }
        if (i < n1) {
            return 1;
        }
        if (i < n2) {
            return -1;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getSortIndex() {
        return 7;
    }

    public Geometry reverse() {
        Geometry[] revGeoms = new Geometry[this.geometries.length];
        for (int i = 0; i < this.geometries.length; i++) {
            revGeoms[i] = this.geometries[i].reverse();
        }
        return getFactory().createGeometryCollection(revGeoms);
    }
}
