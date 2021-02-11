package org.locationtech.jts.edgegraph;

import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.Quadrant;
import org.locationtech.jts.util.Assert;

public class HalfEdge {
    private HalfEdge next;
    private Coordinate orig;
    private HalfEdge sym;

    public static HalfEdge create(Coordinate p0, Coordinate p1) {
        HalfEdge halfEdge;
        HalfEdge e1;
        new HalfEdge(p0);
        HalfEdge e0 = halfEdge;
        new HalfEdge(p1);
        e0.init(e1);
        return e0;
    }

    public static HalfEdge init(HalfEdge halfEdge, HalfEdge halfEdge2) {
        Throwable th;
        HalfEdge e0 = halfEdge;
        HalfEdge e1 = halfEdge2;
        if (e0.sym == null && e1.sym == null && e0.next == null && e1.next == null) {
            e0.init(e1);
            return e0;
        }
        Throwable th2 = th;
        new IllegalStateException("Edges are already initialized");
        throw th2;
    }

    public HalfEdge(Coordinate orig2) {
        this.orig = orig2;
    }

    /* access modifiers changed from: protected */
    public void init(HalfEdge halfEdge) {
        HalfEdge e = halfEdge;
        setSym(e);
        e.setSym(this);
        setNext(e);
        e.setNext(this);
    }

    public Coordinate orig() {
        return this.orig;
    }

    public Coordinate dest() {
        return this.sym.orig;
    }

    public HalfEdge sym() {
        return this.sym;
    }

    private void setSym(HalfEdge e) {
        HalfEdge halfEdge = e;
        this.sym = halfEdge;
    }

    public HalfEdge next() {
        return this.next;
    }

    public HalfEdge prev() {
        return this.sym.next().sym;
    }

    public void setNext(HalfEdge e) {
        HalfEdge halfEdge = e;
        this.next = halfEdge;
    }

    public HalfEdge oNext() {
        return this.sym.next;
    }

    public HalfEdge find(Coordinate coordinate) {
        Coordinate dest = coordinate;
        HalfEdge oNext = this;
        while (oNext != null) {
            if (oNext.dest().equals2D(dest)) {
                return oNext;
            }
            oNext = oNext.oNext();
            if (oNext == this) {
                return null;
            }
        }
        return null;
    }

    public boolean equals(Coordinate p0, Coordinate p1) {
        return this.orig.equals2D(p0) && this.sym.orig.equals(p1);
    }

    public void insert(HalfEdge halfEdge) {
        HalfEdge e = halfEdge;
        if (oNext() == this) {
            insertAfter(e);
            return;
        }
        int ecmp = compareTo(e);
        HalfEdge ePrev = this;
        do {
            HalfEdge oNext = ePrev.oNext();
            if (oNext.compareTo(e) != ecmp || oNext == this) {
                ePrev.insertAfter(e);
                return;
            }
            ePrev = oNext;
        } while (ePrev != this);
        Assert.shouldNeverReachHere();
    }

    private void insertAfter(HalfEdge halfEdge) {
        HalfEdge e = halfEdge;
        Assert.equals(this.orig, e.orig());
        HalfEdge save = oNext();
        this.sym.setNext(e);
        e.sym().setNext(save);
    }

    public int compareTo(Object obj) {
        return compareAngularDirection((HalfEdge) obj);
    }

    public int compareAngularDirection(HalfEdge halfEdge) {
        HalfEdge e = halfEdge;
        double dx = deltaX();
        double dy = deltaY();
        double dx2 = e.deltaX();
        double dy2 = e.deltaY();
        if (dx == dx2 && dy == dy2) {
            return 0;
        }
        double quadrant = (double) Quadrant.quadrant(dx, dy);
        double quadrant2 = (double) Quadrant.quadrant(dx2, dy2);
        if (quadrant > quadrant2) {
            return 1;
        }
        if (quadrant < quadrant2) {
            return -1;
        }
        return CGAlgorithms.computeOrientation(e.orig, e.dest(), dest());
    }

    public double deltaX() {
        return this.sym.orig.f412x - this.orig.f412x;
    }

    public double deltaY() {
        return this.sym.orig.f413y - this.orig.f413y;
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("HE(").append(this.orig.f412x).append(" ").append(this.orig.f413y).append(", ").append(this.sym.orig.f412x).append(" ").append(this.sym.orig.f413y).append(")").toString();
    }

    public int degree() {
        int degree = 0;
        HalfEdge e = this;
        do {
            degree++;
            e = e.oNext();
        } while (e != this);
        return degree;
    }

    public HalfEdge prevNode() {
        HalfEdge e = this;
        while (e.degree() == 2) {
            e = e.prev();
            if (e == this) {
                return null;
            }
        }
        return e;
    }
}
