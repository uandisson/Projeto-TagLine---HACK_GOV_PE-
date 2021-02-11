package org.locationtech.jts.triangulate.quadedge;

import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.p006io.WKTWriter;

public class QuadEdge {
    private Object data = null;
    private QuadEdge next;
    private QuadEdge rot;
    private Vertex vertex;

    public static QuadEdge makeEdge(Vertex o, Vertex d) {
        QuadEdge quadEdge;
        QuadEdge quadEdge2;
        QuadEdge quadEdge3;
        QuadEdge quadEdge4;
        new QuadEdge();
        QuadEdge q0 = quadEdge;
        new QuadEdge();
        QuadEdge q1 = quadEdge2;
        new QuadEdge();
        QuadEdge q2 = quadEdge3;
        new QuadEdge();
        QuadEdge q3 = quadEdge4;
        q0.rot = q1;
        q1.rot = q2;
        q2.rot = q3;
        q3.rot = q0;
        q0.setNext(q0);
        q1.setNext(q3);
        q2.setNext(q2);
        q3.setNext(q1);
        QuadEdge base = q0;
        base.setOrig(o);
        base.setDest(d);
        return base;
    }

    public static QuadEdge connect(QuadEdge quadEdge, QuadEdge quadEdge2) {
        QuadEdge a = quadEdge;
        QuadEdge b = quadEdge2;
        QuadEdge e = makeEdge(a.dest(), b.orig());
        splice(e, a.lNext());
        splice(e.sym(), b);
        return e;
    }

    public static void splice(QuadEdge quadEdge, QuadEdge quadEdge2) {
        QuadEdge a = quadEdge;
        QuadEdge b = quadEdge2;
        QuadEdge alpha = a.oNext().rot();
        QuadEdge beta = b.oNext().rot();
        QuadEdge t1 = b.oNext();
        QuadEdge t2 = a.oNext();
        QuadEdge t3 = beta.oNext();
        QuadEdge t4 = alpha.oNext();
        a.setNext(t1);
        b.setNext(t2);
        alpha.setNext(t3);
        beta.setNext(t4);
    }

    public static void swap(QuadEdge quadEdge) {
        QuadEdge e = quadEdge;
        QuadEdge a = e.oPrev();
        QuadEdge b = e.sym().oPrev();
        splice(e, a);
        splice(e.sym(), b);
        splice(e, a.lNext());
        splice(e.sym(), b.lNext());
        e.setOrig(a.dest());
        e.setDest(b.dest());
    }

    private QuadEdge() {
    }

    public QuadEdge getPrimary() {
        if (orig().getCoordinate().compareTo(dest().getCoordinate()) > 0) {
            return sym();
        }
        return this;
    }

    public void setData(Object data2) {
        Object obj = data2;
        this.data = obj;
    }

    public Object getData() {
        return this.data;
    }

    public void delete() {
        this.rot = null;
    }

    public boolean isLive() {
        return this.rot != null;
    }

    public void setNext(QuadEdge next2) {
        QuadEdge quadEdge = next2;
        this.next = quadEdge;
    }

    public final QuadEdge rot() {
        return this.rot;
    }

    public final QuadEdge invRot() {
        return this.rot.sym();
    }

    public final QuadEdge sym() {
        return this.rot.rot;
    }

    public final QuadEdge oNext() {
        return this.next;
    }

    public final QuadEdge oPrev() {
        return this.rot.next.rot;
    }

    public final QuadEdge dNext() {
        return sym().oNext().sym();
    }

    public final QuadEdge dPrev() {
        return invRot().oNext().invRot();
    }

    public final QuadEdge lNext() {
        return invRot().oNext().rot();
    }

    public final QuadEdge lPrev() {
        return this.next.sym();
    }

    public final QuadEdge rNext() {
        return this.rot.next.invRot();
    }

    public final QuadEdge rPrev() {
        return sym().oNext();
    }

    /* access modifiers changed from: package-private */
    public void setOrig(Vertex o) {
        Vertex vertex2 = o;
        this.vertex = vertex2;
    }

    /* access modifiers changed from: package-private */
    public void setDest(Vertex d) {
        sym().setOrig(d);
    }

    public final Vertex orig() {
        return this.vertex;
    }

    public final Vertex dest() {
        return sym().orig();
    }

    public double getLength() {
        return orig().getCoordinate().distance(dest().getCoordinate());
    }

    public boolean equalsNonOriented(QuadEdge quadEdge) {
        QuadEdge qe = quadEdge;
        if (equalsOriented(qe)) {
            return true;
        }
        if (equalsOriented(qe.sym())) {
            return true;
        }
        return false;
    }

    public boolean equalsOriented(QuadEdge quadEdge) {
        QuadEdge qe = quadEdge;
        if (!orig().getCoordinate().equals2D(qe.orig().getCoordinate()) || !dest().getCoordinate().equals2D(qe.dest().getCoordinate())) {
            return false;
        }
        return true;
    }

    public LineSegment toLineSegment() {
        LineSegment lineSegment;
        new LineSegment(this.vertex.getCoordinate(), dest().getCoordinate());
        return lineSegment;
    }

    public String toString() {
        return WKTWriter.toLineString(this.vertex.getCoordinate(), dest().getCoordinate());
    }
}
