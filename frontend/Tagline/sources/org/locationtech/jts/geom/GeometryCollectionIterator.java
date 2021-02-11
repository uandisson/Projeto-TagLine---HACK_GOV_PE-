package org.locationtech.jts.geom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class GeometryCollectionIterator implements Iterator {
    private boolean atStart = true;
    private int index = 0;
    private int max;
    private Geometry parent;
    private GeometryCollectionIterator subcollectionIterator;

    public GeometryCollectionIterator(Geometry geometry) {
        Geometry parent2 = geometry;
        this.parent = parent2;
        this.max = parent2.getNumGeometries();
    }

    public boolean hasNext() {
        if (this.atStart) {
            return true;
        }
        if (this.subcollectionIterator != null) {
            if (this.subcollectionIterator.hasNext()) {
                return true;
            }
            this.subcollectionIterator = null;
        }
        if (this.index >= this.max) {
            return false;
        }
        return true;
    }

    public Object next() {
        GeometryCollectionIterator geometryCollectionIterator;
        Throwable th;
        if (this.atStart) {
            this.atStart = false;
            if (isAtomic(this.parent)) {
                this.index++;
            }
            return this.parent;
        }
        if (this.subcollectionIterator != null) {
            if (this.subcollectionIterator.hasNext()) {
                return this.subcollectionIterator.next();
            }
            this.subcollectionIterator = null;
        }
        if (this.index >= this.max) {
            Throwable th2 = th;
            new NoSuchElementException();
            throw th2;
        }
        Geometry geometry = this.parent;
        int i = this.index;
        int i2 = i + 1;
        this.index = i2;
        Geometry obj = geometry.getGeometryN(i);
        if (!(obj instanceof GeometryCollection)) {
            return obj;
        }
        new GeometryCollectionIterator((GeometryCollection) obj);
        this.subcollectionIterator = geometryCollectionIterator;
        return this.subcollectionIterator.next();
    }

    private static boolean isAtomic(Geometry geom) {
        return !(geom instanceof GeometryCollection);
    }

    public void remove() {
        Throwable th;
        Throwable th2 = th;
        new UnsupportedOperationException(getClass().getName());
        throw th2;
    }
}
