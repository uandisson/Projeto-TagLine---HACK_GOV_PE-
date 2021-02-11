package org.locationtech.jts.geomgraph;

import com.microsoft.appcenter.Constants;
import java.io.PrintStream;
import org.locationtech.jts.algorithm.BoundaryNodeRule;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.util.Assert;

public class EdgeEnd implements Comparable {

    /* renamed from: dx */
    private double f428dx;

    /* renamed from: dy */
    private double f429dy;
    protected Edge edge;
    protected Label label;
    private Node node;

    /* renamed from: p0 */
    private Coordinate f430p0;

    /* renamed from: p1 */
    private Coordinate f431p1;
    private int quadrant;

    protected EdgeEnd(Edge edge2) {
        this.edge = edge2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public EdgeEnd(Edge edge2, Coordinate p0, Coordinate p1) {
        this(edge2, p0, p1, (Label) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public EdgeEnd(Edge edge2, Coordinate p0, Coordinate p1, Label label2) {
        this(edge2);
        init(p0, p1);
        this.label = label2;
    }

    /* access modifiers changed from: protected */
    public void init(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        this.f430p0 = p0;
        this.f431p1 = p1;
        this.f428dx = p1.f412x - p0.f412x;
        this.f429dy = p1.f413y - p0.f413y;
        this.quadrant = Quadrant.quadrant(this.f428dx, this.f429dy);
        Assert.isTrue((this.f428dx == 0.0d && this.f429dy == 0.0d) ? false : true, "EdgeEnd with identical endpoints found");
    }

    public Edge getEdge() {
        return this.edge;
    }

    public Label getLabel() {
        return this.label;
    }

    public Coordinate getCoordinate() {
        return this.f430p0;
    }

    public Coordinate getDirectedCoordinate() {
        return this.f431p1;
    }

    public int getQuadrant() {
        return this.quadrant;
    }

    public double getDx() {
        return this.f428dx;
    }

    public double getDy() {
        return this.f429dy;
    }

    public void setNode(Node node2) {
        Node node3 = node2;
        this.node = node3;
    }

    public Node getNode() {
        return this.node;
    }

    public int compareTo(Object obj) {
        return compareDirection((EdgeEnd) obj);
    }

    public int compareDirection(EdgeEnd edgeEnd) {
        EdgeEnd e = edgeEnd;
        if (this.f428dx == e.f428dx && this.f429dy == e.f429dy) {
            return 0;
        }
        if (this.quadrant > e.quadrant) {
            return 1;
        }
        if (this.quadrant < e.quadrant) {
            return -1;
        }
        return CGAlgorithms.computeOrientation(e.f430p0, e.f431p1, this.f431p1);
    }

    public void computeLabel(BoundaryNodeRule boundaryNodeRule) {
    }

    public void print(PrintStream out) {
        StringBuilder sb;
        double angle = Math.atan2(this.f429dy, this.f428dx);
        String className = getClass().getName();
        String name = className.substring(className.lastIndexOf(46) + 1);
        new StringBuilder();
        out.print(sb.append("  ").append(name).append(": ").append(this.f430p0).append(" - ").append(this.f431p1).append(" ").append(this.quadrant).append(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR).append(angle).append("   ").append(this.label).toString());
    }

    public String toString() {
        StringBuilder sb;
        double angle = Math.atan2(this.f429dy, this.f428dx);
        String className = getClass().getName();
        String name = className.substring(className.lastIndexOf(46) + 1);
        new StringBuilder();
        return sb.append("  ").append(name).append(": ").append(this.f430p0).append(" - ").append(this.f431p1).append(" ").append(this.quadrant).append(Constants.COMMON_SCHEMA_PREFIX_SEPARATOR).append(angle).append("   ").append(this.label).toString();
    }
}
