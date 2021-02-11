package org.locationtech.jts.geomgraph;

import java.io.PrintStream;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.IntersectionMatrix;

public class Node extends GraphComponent {
    protected Coordinate coord;
    protected EdgeEndStar edges;

    public Node(Coordinate coord2, EdgeEndStar edges2) {
        Label label;
        this.coord = coord2;
        this.edges = edges2;
        new Label(0, -1);
        this.label = label;
    }

    public Coordinate getCoordinate() {
        return this.coord;
    }

    public EdgeEndStar getEdges() {
        return this.edges;
    }

    public boolean isIncidentEdgeInResult() {
        for (DirectedEdge de : getEdges().getEdges()) {
            if (de.getEdge().isInResult()) {
                return true;
            }
        }
        return false;
    }

    public boolean isIsolated() {
        return this.label.getGeometryCount() == 1;
    }

    /* access modifiers changed from: protected */
    public void computeIM(IntersectionMatrix im) {
    }

    public void add(EdgeEnd edgeEnd) {
        EdgeEnd e = edgeEnd;
        this.edges.insert(e);
        e.setNode(this);
    }

    public void mergeLabel(Node n) {
        mergeLabel(n.label);
    }

    public void mergeLabel(Label label) {
        Label label2 = label;
        for (int i = 0; i < 2; i++) {
            int loc = computeMergedLocation(label2, i);
            if (this.label.getLocation(i) == -1) {
                this.label.setLocation(i, loc);
            }
        }
    }

    public void setLabel(int i, int i2) {
        Label label;
        int argIndex = i;
        int onLocation = i2;
        if (this.label == null) {
            new Label(argIndex, onLocation);
            this.label = label;
            return;
        }
        this.label.setLocation(argIndex, onLocation);
    }

    public void setLabelBoundary(int i) {
        int newLoc;
        int argIndex = i;
        if (this.label != null) {
            int loc = -1;
            if (this.label != null) {
                loc = this.label.getLocation(argIndex);
            }
            switch (loc) {
                case 0:
                    newLoc = 1;
                    break;
                case 1:
                    newLoc = 0;
                    break;
                default:
                    newLoc = 1;
                    break;
            }
            this.label.setLocation(argIndex, newLoc);
        }
    }

    /* access modifiers changed from: package-private */
    public int computeMergedLocation(Label label, int i) {
        Label label2 = label;
        int eltIndex = i;
        int loc = this.label.getLocation(eltIndex);
        if (!label2.isNull(eltIndex)) {
            int nLoc = label2.getLocation(eltIndex);
            if (loc != 1) {
                loc = nLoc;
            }
        }
        return loc;
    }

    public void print(PrintStream out) {
        StringBuilder sb;
        new StringBuilder();
        out.println(sb.append("node ").append(this.coord).append(" lbl: ").append(this.label).toString());
    }
}
