package org.locationtech.jts.planargraph;

import com.microsoft.appcenter.Constants;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.Quadrant;

public class DirectedEdge extends GraphComponent implements Comparable {
    protected double angle;
    protected boolean edgeDirection;
    protected Node from;

    /* renamed from: p0 */
    protected Coordinate f504p0;

    /* renamed from: p1 */
    protected Coordinate f505p1;
    protected Edge parentEdge;
    protected int quadrant;
    protected DirectedEdge sym = null;

    /* renamed from: to */
    protected Node f506to;

    public static List toEdges(Collection dirEdges) {
        List list;
        new ArrayList();
        List edges = list;
        Iterator i = dirEdges.iterator();
        while (i.hasNext()) {
            boolean add = edges.add(((DirectedEdge) i.next()).parentEdge);
        }
        return edges;
    }

    public DirectedEdge(Node node, Node to, Coordinate directionPt, boolean edgeDirection2) {
        Node from2 = node;
        this.from = from2;
        this.f506to = to;
        this.edgeDirection = edgeDirection2;
        this.f504p0 = from2.getCoordinate();
        this.f505p1 = directionPt;
        double dx = this.f505p1.f412x - this.f504p0.f412x;
        double dy = this.f505p1.f413y - this.f504p0.f413y;
        this.quadrant = Quadrant.quadrant(dx, dy);
        this.angle = Math.atan2(dy, dx);
    }

    public Edge getEdge() {
        return this.parentEdge;
    }

    public void setEdge(Edge parentEdge2) {
        Edge edge = parentEdge2;
        this.parentEdge = edge;
    }

    public int getQuadrant() {
        return this.quadrant;
    }

    public Coordinate getDirectionPt() {
        return this.f505p1;
    }

    public boolean getEdgeDirection() {
        return this.edgeDirection;
    }

    public Node getFromNode() {
        return this.from;
    }

    public Node getToNode() {
        return this.f506to;
    }

    public Coordinate getCoordinate() {
        return this.from.getCoordinate();
    }

    public double getAngle() {
        return this.angle;
    }

    public DirectedEdge getSym() {
        return this.sym;
    }

    public void setSym(DirectedEdge sym2) {
        DirectedEdge directedEdge = sym2;
        this.sym = directedEdge;
    }

    /* access modifiers changed from: package-private */
    public void remove() {
        this.sym = null;
        this.parentEdge = null;
    }

    public boolean isRemoved() {
        return this.parentEdge == null;
    }

    public int compareTo(Object obj) {
        return compareDirection((DirectedEdge) obj);
    }

    public int compareDirection(DirectedEdge directedEdge) {
        DirectedEdge e = directedEdge;
        if (this.quadrant > e.quadrant) {
            return 1;
        }
        if (this.quadrant < e.quadrant) {
            return -1;
        }
        return CGAlgorithms.computeOrientation(e.f504p0, e.f505p1, this.f505p1);
    }

    public void print(PrintStream out) {
        StringBuilder sb;
        String className = getClass().getName();
        String name = className.substring(className.lastIndexOf(46) + 1);
        new StringBuilder();
        out.print(sb.append("  ").append(name).append(": ").append(this.f504p0).append(" - ").append(this.f505p1).append(" ").append(this.quadrant).append(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR).append(this.angle).toString());
    }
}
