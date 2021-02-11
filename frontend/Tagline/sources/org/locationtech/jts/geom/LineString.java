package org.locationtech.jts.geom;

import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.operation.BoundaryOp;

public class LineString extends Geometry implements Lineal {
    private static final long serialVersionUID = 3110669828065365560L;
    protected CoordinateSequence points;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LineString(org.locationtech.jts.geom.Coordinate[] r11, org.locationtech.jts.geom.PrecisionModel r12, int r13) {
        /*
            r10 = this;
            r0 = r10
            r1 = r11
            r2 = r12
            r3 = r13
            r4 = r0
            org.locationtech.jts.geom.GeometryFactory r5 = new org.locationtech.jts.geom.GeometryFactory
            r9 = r5
            r5 = r9
            r6 = r9
            r7 = r2
            r8 = r3
            r6.<init>(r7, r8)
            r4.<init>(r5)
            r4 = r0
            r5 = r0
            org.locationtech.jts.geom.GeometryFactory r5 = r5.getFactory()
            org.locationtech.jts.geom.CoordinateSequenceFactory r5 = r5.getCoordinateSequenceFactory()
            r6 = r1
            org.locationtech.jts.geom.CoordinateSequence r5 = r5.create((org.locationtech.jts.geom.Coordinate[]) r6)
            r4.init(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.LineString.<init>(org.locationtech.jts.geom.Coordinate[], org.locationtech.jts.geom.PrecisionModel, int):void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LineString(CoordinateSequence points2, GeometryFactory factory) {
        super(factory);
        init(points2);
    }

    private void init(CoordinateSequence coordinateSequence) {
        Throwable th;
        StringBuilder sb;
        CoordinateSequence points2 = coordinateSequence;
        if (points2 == null) {
            points2 = getFactory().getCoordinateSequenceFactory().create(new Coordinate[0]);
        }
        if (points2.size() == 1) {
            Throwable th2 = th;
            new StringBuilder();
            new IllegalArgumentException(sb.append("Invalid number of points in LineString (found ").append(points2.size()).append(" - must be 0 or >= 2)").toString());
            throw th2;
        }
        this.points = points2;
    }

    public Coordinate[] getCoordinates() {
        return this.points.toCoordinateArray();
    }

    public CoordinateSequence getCoordinateSequence() {
        return this.points;
    }

    public Coordinate getCoordinateN(int n) {
        return this.points.getCoordinate(n);
    }

    public Coordinate getCoordinate() {
        if (isEmpty()) {
            return null;
        }
        return this.points.getCoordinate(0);
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

    public boolean isEmpty() {
        return this.points.size() == 0;
    }

    public int getNumPoints() {
        return this.points.size();
    }

    public Point getPointN(int n) {
        return getFactory().createPoint(this.points.getCoordinate(n));
    }

    public Point getStartPoint() {
        if (isEmpty()) {
            return null;
        }
        return getPointN(0);
    }

    public Point getEndPoint() {
        if (isEmpty()) {
            return null;
        }
        return getPointN(getNumPoints() - 1);
    }

    public boolean isClosed() {
        if (isEmpty()) {
            return false;
        }
        return getCoordinateN(0).equals2D(getCoordinateN(getNumPoints() - 1));
    }

    public boolean isRing() {
        return isClosed() && isSimple();
    }

    public String getGeometryType() {
        return "LineString";
    }

    public double getLength() {
        return CGAlgorithms.length(this.points);
    }

    public Geometry getBoundary() {
        BoundaryOp boundaryOp;
        new BoundaryOp(this);
        return boundaryOp.getBoundary();
    }

    public Geometry reverse() {
        CoordinateSequence seq = (CoordinateSequence) this.points.clone();
        CoordinateSequences.reverse(seq);
        return getFactory().createLineString(seq);
    }

    public boolean isCoordinate(Coordinate coordinate) {
        Coordinate pt = coordinate;
        for (int i = 0; i < this.points.size(); i++) {
            if (this.points.getCoordinate(i).equals(pt)) {
                return true;
            }
        }
        return false;
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
        return this.points.expandEnvelope(envelope);
    }

    public boolean equalsExact(Geometry geometry, double d) {
        Geometry other = geometry;
        double tolerance = d;
        if (!isEquivalentClass(other)) {
            return false;
        }
        LineString otherLineString = (LineString) other;
        if (this.points.size() != otherLineString.points.size()) {
            return false;
        }
        for (int i = 0; i < this.points.size(); i++) {
            if (!equal(this.points.getCoordinate(i), otherLineString.points.getCoordinate(i), tolerance)) {
                return false;
            }
        }
        return true;
    }

    public void apply(CoordinateFilter coordinateFilter) {
        CoordinateFilter filter = coordinateFilter;
        for (int i = 0; i < this.points.size(); i++) {
            filter.filter(this.points.getCoordinate(i));
        }
    }

    public void apply(CoordinateSequenceFilter coordinateSequenceFilter) {
        CoordinateSequenceFilter filter = coordinateSequenceFilter;
        if (this.points.size() != 0) {
            for (int i = 0; i < this.points.size(); i++) {
                filter.filter(this.points, i);
                if (filter.isDone()) {
                    break;
                }
            }
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
        LineString ls = (LineString) super.clone();
        ls.points = (CoordinateSequence) this.points.clone();
        return ls;
    }

    public void normalize() {
        int i = 0;
        while (i < this.points.size() / 2) {
            int j = (this.points.size() - 1) - i;
            if (this.points.getCoordinate(i).equals(this.points.getCoordinate(j))) {
                i++;
            } else if (this.points.getCoordinate(i).compareTo(this.points.getCoordinate(j)) > 0) {
                CoordinateSequence copy = (CoordinateSequence) this.points.clone();
                CoordinateSequences.reverse(copy);
                this.points = copy;
                return;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isEquivalentClass(Geometry other) {
        return other instanceof LineString;
    }

    /* access modifiers changed from: protected */
    public int compareToSameClass(Object o) {
        LineString line = (LineString) o;
        int i = 0;
        int j = 0;
        while (i < this.points.size() && j < line.points.size()) {
            int comparison = this.points.getCoordinate(i).compareTo(line.points.getCoordinate(j));
            if (comparison != 0) {
                return comparison;
            }
            i++;
            j++;
        }
        if (i < this.points.size()) {
            return 1;
        }
        if (j < line.points.size()) {
            return -1;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int compareToSameClass(Object o, CoordinateSequenceComparator comp) {
        return comp.compare((Object) this.points, (Object) ((LineString) o).points);
    }

    /* access modifiers changed from: protected */
    public int getSortIndex() {
        return 2;
    }
}
