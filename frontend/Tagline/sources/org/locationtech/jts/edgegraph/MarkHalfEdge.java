package org.locationtech.jts.edgegraph;

import org.locationtech.jts.geom.Coordinate;

public class MarkHalfEdge extends HalfEdge {
    private boolean isMarked = false;

    public static boolean isMarked(HalfEdge e) {
        return ((MarkHalfEdge) e).isMarked();
    }

    public static void mark(HalfEdge e) {
        ((MarkHalfEdge) e).mark();
    }

    public static void setMark(HalfEdge e, boolean isMarked2) {
        ((MarkHalfEdge) e).setMark(isMarked2);
    }

    public static void setMarkBoth(HalfEdge halfEdge, boolean z) {
        HalfEdge e = halfEdge;
        boolean isMarked2 = z;
        ((MarkHalfEdge) e).setMark(isMarked2);
        ((MarkHalfEdge) e.sym()).setMark(isMarked2);
    }

    public static void markBoth(HalfEdge halfEdge) {
        HalfEdge e = halfEdge;
        ((MarkHalfEdge) e).mark();
        ((MarkHalfEdge) e.sym()).mark();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MarkHalfEdge(Coordinate orig) {
        super(orig);
    }

    public boolean isMarked() {
        return this.isMarked;
    }

    public void mark() {
        this.isMarked = true;
    }

    public void setMark(boolean isMarked2) {
        boolean z = isMarked2;
        this.isMarked = z;
    }
}
