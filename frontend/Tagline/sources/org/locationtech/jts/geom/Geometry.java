package org.locationtech.jts.geom;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import org.locationtech.jts.algorithm.Centroid;
import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.algorithm.InteriorPointArea;
import org.locationtech.jts.algorithm.InteriorPointLine;
import org.locationtech.jts.algorithm.InteriorPointPoint;
import org.locationtech.jts.geom.util.GeometryCollectionMapper;
import org.locationtech.jts.geom.util.GeometryMapper;
import org.locationtech.jts.operation.IsSimpleOp;
import org.locationtech.jts.operation.buffer.BufferOp;
import org.locationtech.jts.operation.distance.DistanceOp;
import org.locationtech.jts.operation.overlay.OverlayOp;
import org.locationtech.jts.operation.overlay.snap.SnapIfNeededOverlayOp;
import org.locationtech.jts.operation.predicate.RectangleContains;
import org.locationtech.jts.operation.predicate.RectangleIntersects;
import org.locationtech.jts.operation.relate.RelateOp;
import org.locationtech.jts.operation.union.UnaryUnionOp;
import org.locationtech.jts.operation.valid.IsValidOp;
import org.locationtech.jts.p006io.WKTWriter;
import org.locationtech.jts.util.Assert;

public abstract class Geometry implements Cloneable, Comparable, Serializable {
    static final int SORTINDEX_GEOMETRYCOLLECTION = 7;
    static final int SORTINDEX_LINEARRING = 3;
    static final int SORTINDEX_LINESTRING = 2;
    static final int SORTINDEX_MULTILINESTRING = 4;
    static final int SORTINDEX_MULTIPOINT = 1;
    static final int SORTINDEX_MULTIPOLYGON = 6;
    static final int SORTINDEX_POINT = 0;
    static final int SORTINDEX_POLYGON = 5;
    private static final GeometryComponentFilter geometryChangedFilter;
    private static final long serialVersionUID = 8763622679187376702L;
    protected int SRID;
    protected Envelope envelope;
    protected final GeometryFactory factory;
    private Object userData = null;

    public abstract void apply(CoordinateFilter coordinateFilter);

    public abstract void apply(CoordinateSequenceFilter coordinateSequenceFilter);

    public abstract void apply(GeometryComponentFilter geometryComponentFilter);

    public abstract void apply(GeometryFilter geometryFilter);

    /* access modifiers changed from: protected */
    public abstract int compareToSameClass(Object obj);

    /* access modifiers changed from: protected */
    public abstract int compareToSameClass(Object obj, CoordinateSequenceComparator coordinateSequenceComparator);

    /* access modifiers changed from: protected */
    public abstract Envelope computeEnvelopeInternal();

    public abstract boolean equalsExact(Geometry geometry, double d);

    public abstract Geometry getBoundary();

    public abstract int getBoundaryDimension();

    public abstract Coordinate getCoordinate();

    public abstract Coordinate[] getCoordinates();

    public abstract int getDimension();

    public abstract String getGeometryType();

    public abstract int getNumPoints();

    /* access modifiers changed from: protected */
    public abstract int getSortIndex();

    public abstract boolean isEmpty();

    public abstract void normalize();

    public abstract Geometry reverse();

    static {
        GeometryComponentFilter geometryComponentFilter;
        new GeometryComponentFilter() {
            public void filter(Geometry geom) {
                geom.geometryChangedAction();
            }
        };
        geometryChangedFilter = geometryComponentFilter;
    }

    public Geometry(GeometryFactory geometryFactory) {
        GeometryFactory factory2 = geometryFactory;
        this.factory = factory2;
        this.SRID = factory2.getSRID();
    }

    protected static boolean hasNonEmptyElements(Geometry[] geometryArr) {
        Geometry[] geometries = geometryArr;
        for (int i = 0; i < geometries.length; i++) {
            if (!geometries[i].isEmpty()) {
                return true;
            }
        }
        return false;
    }

    protected static boolean hasNullElements(Object[] objArr) {
        Object[] array = objArr;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                return true;
            }
        }
        return false;
    }

    public int getSRID() {
        return this.SRID;
    }

    public void setSRID(int SRID2) {
        int i = SRID2;
        this.SRID = i;
    }

    public GeometryFactory getFactory() {
        return this.factory;
    }

    public Object getUserData() {
        return this.userData;
    }

    public int getNumGeometries() {
        return 1;
    }

    public Geometry getGeometryN(int i) {
        int i2 = i;
        return this;
    }

    public void setUserData(Object userData2) {
        Object obj = userData2;
        this.userData = obj;
    }

    public PrecisionModel getPrecisionModel() {
        return this.factory.getPrecisionModel();
    }

    public boolean isSimple() {
        IsSimpleOp op;
        new IsSimpleOp(this);
        return op.isSimple();
    }

    public boolean isValid() {
        return IsValidOp.isValid(this);
    }

    public double distance(Geometry g) {
        return DistanceOp.distance(this, g);
    }

    public boolean isWithinDistance(Geometry geometry, double d) {
        Geometry geom = geometry;
        double distance = d;
        if (getEnvelopeInternal().distance(geom.getEnvelopeInternal()) > distance) {
            return false;
        }
        return DistanceOp.isWithinDistance(this, geom, distance);
    }

    public boolean isRectangle() {
        return false;
    }

    public double getArea() {
        return 0.0d;
    }

    public double getLength() {
        return 0.0d;
    }

    public Point getCentroid() {
        if (isEmpty()) {
            return this.factory.createPoint((Coordinate) null);
        }
        return createPointFromInternalCoord(Centroid.getCentroid(this), this);
    }

    public Point getInteriorPoint() {
        InteriorPointArea intPt;
        Coordinate interiorPt;
        InteriorPointLine intPt2;
        InteriorPointPoint intPt3;
        if (isEmpty()) {
            return this.factory.createPoint((Coordinate) null);
        }
        int dim = getDimension();
        if (dim == 0) {
            new InteriorPointPoint(this);
            interiorPt = intPt3.getInteriorPoint();
        } else if (dim == 1) {
            new InteriorPointLine(this);
            interiorPt = intPt2.getInteriorPoint();
        } else {
            new InteriorPointArea(this);
            interiorPt = intPt.getInteriorPoint();
        }
        return createPointFromInternalCoord(interiorPt, this);
    }

    public Geometry getEnvelope() {
        return getFactory().toGeometry(getEnvelopeInternal());
    }

    public Envelope getEnvelopeInternal() {
        Envelope envelope2;
        if (this.envelope == null) {
            this.envelope = computeEnvelopeInternal();
        }
        new Envelope(this.envelope);
        return envelope2;
    }

    public void geometryChanged() {
        apply(geometryChangedFilter);
    }

    /* access modifiers changed from: protected */
    public void geometryChangedAction() {
        this.envelope = null;
    }

    public boolean disjoint(Geometry g) {
        return !intersects(g);
    }

    public boolean touches(Geometry geometry) {
        Geometry g = geometry;
        if (!getEnvelopeInternal().intersects(g.getEnvelopeInternal())) {
            return false;
        }
        return relate(g).isTouches(getDimension(), g.getDimension());
    }

    public boolean intersects(Geometry geometry) {
        Geometry g = geometry;
        if (!getEnvelopeInternal().intersects(g.getEnvelopeInternal())) {
            return false;
        }
        if (isRectangle()) {
            return RectangleIntersects.intersects((Polygon) this, g);
        }
        if (g.isRectangle()) {
            return RectangleIntersects.intersects((Polygon) g, this);
        }
        if (!isGeometryCollection() && !g.isGeometryCollection()) {
            return relate(g).isIntersects();
        }
        for (int i = 0; i < getNumGeometries(); i++) {
            for (int j = 0; j < g.getNumGeometries(); j++) {
                if (getGeometryN(i).intersects(g.getGeometryN(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean crosses(Geometry geometry) {
        Geometry g = geometry;
        if (!getEnvelopeInternal().intersects(g.getEnvelopeInternal())) {
            return false;
        }
        return relate(g).isCrosses(getDimension(), g.getDimension());
    }

    public boolean within(Geometry g) {
        return g.contains(this);
    }

    public boolean contains(Geometry geometry) {
        Geometry g = geometry;
        if (!getEnvelopeInternal().contains(g.getEnvelopeInternal())) {
            return false;
        }
        if (isRectangle()) {
            return RectangleContains.contains((Polygon) this, g);
        }
        return relate(g).isContains();
    }

    public boolean overlaps(Geometry geometry) {
        Geometry g = geometry;
        if (!getEnvelopeInternal().intersects(g.getEnvelopeInternal())) {
            return false;
        }
        return relate(g).isOverlaps(getDimension(), g.getDimension());
    }

    public boolean covers(Geometry geometry) {
        Geometry g = geometry;
        if (!getEnvelopeInternal().covers(g.getEnvelopeInternal())) {
            return false;
        }
        if (isRectangle()) {
            return true;
        }
        return relate(g).isCovers();
    }

    public boolean coveredBy(Geometry g) {
        return g.covers(this);
    }

    public boolean relate(Geometry g, String intersectionPattern) {
        return relate(g).matches(intersectionPattern);
    }

    public IntersectionMatrix relate(Geometry geometry) {
        Geometry g = geometry;
        checkNotGeometryCollection(this);
        checkNotGeometryCollection(g);
        return RelateOp.relate(this, g);
    }

    public boolean equals(Geometry geometry) {
        Geometry g = geometry;
        if (g == null) {
            return false;
        }
        return equalsTopo(g);
    }

    public boolean equalsTopo(Geometry geometry) {
        Geometry g = geometry;
        if (!getEnvelopeInternal().equals(g.getEnvelopeInternal())) {
            return false;
        }
        return relate(g).isEquals(getDimension(), g.getDimension());
    }

    public boolean equals(Object obj) {
        Object o = obj;
        if (!(o instanceof Geometry)) {
            return false;
        }
        return equalsExact((Geometry) o);
    }

    public int hashCode() {
        return getEnvelopeInternal().hashCode();
    }

    public String toString() {
        return toText();
    }

    public String toText() {
        WKTWriter writer;
        new WKTWriter();
        return writer.write(this);
    }

    public Geometry buffer(double distance) {
        return BufferOp.bufferOp(this, distance);
    }

    public Geometry buffer(double distance, int quadrantSegments) {
        return BufferOp.bufferOp(this, distance, quadrantSegments);
    }

    public Geometry buffer(double distance, int quadrantSegments, int endCapStyle) {
        return BufferOp.bufferOp(this, distance, quadrantSegments, endCapStyle);
    }

    public Geometry convexHull() {
        ConvexHull convexHull;
        new ConvexHull(this);
        return convexHull.getConvexHull();
    }

    public Geometry intersection(Geometry geometry) {
        GeometryMapper.MapOp mapOp;
        Geometry other = geometry;
        if (isEmpty() || other.isEmpty()) {
            return OverlayOp.createEmptyResult(1, this, other, this.factory);
        }
        if (isGeometryCollection()) {
            final Geometry geometry2 = other;
            new GeometryMapper.MapOp(this) {
                final /* synthetic */ Geometry this$0;

                {
                    this.this$0 = this$0;
                }

                public Geometry map(Geometry g) {
                    return g.intersection(geometry2);
                }
            };
            return GeometryCollectionMapper.map((GeometryCollection) this, mapOp);
        }
        checkNotGeometryCollection(this);
        checkNotGeometryCollection(other);
        return SnapIfNeededOverlayOp.overlayOp(this, other, 1);
    }

    public Geometry union(Geometry geometry) {
        Geometry other = geometry;
        if (isEmpty() || other.isEmpty()) {
            if (isEmpty() && other.isEmpty()) {
                return OverlayOp.createEmptyResult(2, this, other, this.factory);
            }
            if (isEmpty()) {
                return (Geometry) other.clone();
            }
            if (other.isEmpty()) {
                return (Geometry) clone();
            }
        }
        checkNotGeometryCollection(this);
        checkNotGeometryCollection(other);
        return SnapIfNeededOverlayOp.overlayOp(this, other, 2);
    }

    public Geometry difference(Geometry geometry) {
        Geometry other = geometry;
        if (isEmpty()) {
            return OverlayOp.createEmptyResult(3, this, other, this.factory);
        }
        if (other.isEmpty()) {
            return (Geometry) clone();
        }
        checkNotGeometryCollection(this);
        checkNotGeometryCollection(other);
        return SnapIfNeededOverlayOp.overlayOp(this, other, 3);
    }

    public Geometry symDifference(Geometry geometry) {
        Geometry other = geometry;
        if (isEmpty() || other.isEmpty()) {
            if (isEmpty() && other.isEmpty()) {
                return OverlayOp.createEmptyResult(4, this, other, this.factory);
            }
            if (isEmpty()) {
                return (Geometry) other.clone();
            }
            if (other.isEmpty()) {
                return (Geometry) clone();
            }
        }
        checkNotGeometryCollection(this);
        checkNotGeometryCollection(other);
        return SnapIfNeededOverlayOp.overlayOp(this, other, 4);
    }

    public Geometry union() {
        return UnaryUnionOp.union(this);
    }

    public boolean equalsExact(Geometry geometry) {
        Geometry other = geometry;
        return this == other || equalsExact(other, 0.0d);
    }

    public boolean equalsNorm(Geometry geometry) {
        Geometry g = geometry;
        if (g == null) {
            return false;
        }
        return norm().equalsExact(g.norm());
    }

    public Object clone() {
        Envelope envelope2;
        try {
            Geometry clone = (Geometry) super.clone();
            if (clone.envelope != null) {
                new Envelope(clone.envelope);
                clone.envelope = envelope2;
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            CloneNotSupportedException cloneNotSupportedException = e;
            Assert.shouldNeverReachHere();
            return null;
        }
    }

    public Geometry norm() {
        Geometry copy = (Geometry) clone();
        copy.normalize();
        return copy;
    }

    public int compareTo(Object obj) {
        Object o = obj;
        Geometry other = (Geometry) o;
        if (getSortIndex() != other.getSortIndex()) {
            return getSortIndex() - other.getSortIndex();
        }
        if (isEmpty() && other.isEmpty()) {
            return 0;
        }
        if (isEmpty()) {
            return -1;
        }
        if (other.isEmpty()) {
            return 1;
        }
        return compareToSameClass(o);
    }

    public int compareTo(Object obj, CoordinateSequenceComparator coordinateSequenceComparator) {
        Object o = obj;
        CoordinateSequenceComparator comp = coordinateSequenceComparator;
        Geometry other = (Geometry) o;
        if (getSortIndex() != other.getSortIndex()) {
            return getSortIndex() - other.getSortIndex();
        }
        if (isEmpty() && other.isEmpty()) {
            return 0;
        }
        if (isEmpty()) {
            return -1;
        }
        if (other.isEmpty()) {
            return 1;
        }
        return compareToSameClass(o, comp);
    }

    /* access modifiers changed from: protected */
    public boolean isEquivalentClass(Geometry other) {
        return getClass().getName().equals(other.getClass().getName());
    }

    /* access modifiers changed from: protected */
    public void checkNotGeometryCollection(Geometry g) {
        Throwable th;
        if (g.getClass().equals(GeometryCollection.class)) {
            Throwable th2 = th;
            new IllegalArgumentException("This method does not support GeometryCollection arguments");
            throw th2;
        }
    }

    /* access modifiers changed from: protected */
    public boolean isGeometryCollection() {
        return getClass().equals(GeometryCollection.class);
    }

    /* access modifiers changed from: protected */
    public int compare(Collection a, Collection b) {
        Iterator i = a.iterator();
        Iterator j = b.iterator();
        while (i.hasNext() && j.hasNext()) {
            int comparison = ((Comparable) i.next()).compareTo((Comparable) j.next());
            if (comparison != 0) {
                return comparison;
            }
        }
        if (i.hasNext()) {
            return 1;
        }
        if (j.hasNext()) {
            return -1;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean equal(Coordinate coordinate, Coordinate coordinate2, double d) {
        Coordinate a = coordinate;
        Coordinate b = coordinate2;
        double tolerance = d;
        if (tolerance == 0.0d) {
            return a.equals(b);
        }
        return a.distance(b) <= tolerance;
    }

    private Point createPointFromInternalCoord(Coordinate coordinate, Geometry geometry) {
        Coordinate coord = coordinate;
        Geometry exemplar = geometry;
        exemplar.getPrecisionModel().makePrecise(coord);
        return exemplar.getFactory().createPoint(coord);
    }
}
