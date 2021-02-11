package org.locationtech.jts.geomgraph;

import java.io.PrintStream;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.IntersectionMatrix;
import org.locationtech.jts.geomgraph.index.MonotoneChainEdge;

public class Edge extends GraphComponent {
    private Depth depth;
    private int depthDelta;
    EdgeIntersectionList eiList;
    private Envelope env;
    private boolean isIsolated;
    private MonotoneChainEdge mce;
    private String name;
    Coordinate[] pts;

    public static void updateIM(Label label, IntersectionMatrix intersectionMatrix) {
        Label label2 = label;
        IntersectionMatrix im = intersectionMatrix;
        im.setAtLeastIfValid(label2.getLocation(0, 0), label2.getLocation(1, 0), 1);
        if (label2.isArea()) {
            im.setAtLeastIfValid(label2.getLocation(0, 1), label2.getLocation(1, 1), 2);
            im.setAtLeastIfValid(label2.getLocation(0, 2), label2.getLocation(1, 2), 2);
        }
    }

    public Edge(Coordinate[] pts2, Label label) {
        EdgeIntersectionList edgeIntersectionList;
        Depth depth2;
        new EdgeIntersectionList(this);
        this.eiList = edgeIntersectionList;
        this.isIsolated = true;
        new Depth();
        this.depth = depth2;
        this.depthDelta = 0;
        this.pts = pts2;
        this.label = label;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public Edge(Coordinate[] pts2) {
        this(pts2, (Label) null);
    }

    public int getNumPoints() {
        return this.pts.length;
    }

    public void setName(String name2) {
        String str = name2;
        this.name = str;
    }

    public Coordinate[] getCoordinates() {
        return this.pts;
    }

    public Coordinate getCoordinate(int i) {
        return this.pts[i];
    }

    public Coordinate getCoordinate() {
        if (this.pts.length > 0) {
            return this.pts[0];
        }
        return null;
    }

    public Envelope getEnvelope() {
        Envelope envelope;
        if (this.env == null) {
            new Envelope();
            this.env = envelope;
            for (int i = 0; i < this.pts.length; i++) {
                this.env.expandToInclude(this.pts[i]);
            }
        }
        return this.env;
    }

    public Depth getDepth() {
        return this.depth;
    }

    public int getDepthDelta() {
        return this.depthDelta;
    }

    public void setDepthDelta(int depthDelta2) {
        int i = depthDelta2;
        this.depthDelta = i;
    }

    public int getMaximumSegmentIndex() {
        return this.pts.length - 1;
    }

    public EdgeIntersectionList getEdgeIntersectionList() {
        return this.eiList;
    }

    public MonotoneChainEdge getMonotoneChainEdge() {
        MonotoneChainEdge monotoneChainEdge;
        if (this.mce == null) {
            new MonotoneChainEdge(this);
            this.mce = monotoneChainEdge;
        }
        return this.mce;
    }

    public boolean isClosed() {
        return this.pts[0].equals(this.pts[this.pts.length - 1]);
    }

    public boolean isCollapsed() {
        if (!this.label.isArea()) {
            return false;
        }
        if (this.pts.length != 3) {
            return false;
        }
        if (this.pts[0].equals(this.pts[2])) {
            return true;
        }
        return false;
    }

    public Edge getCollapsedEdge() {
        Edge newe;
        new Edge(new Coordinate[]{this.pts[0], this.pts[1]}, Label.toLineLabel(this.label));
        return newe;
    }

    public void setIsolated(boolean isIsolated2) {
        boolean z = isIsolated2;
        this.isIsolated = z;
    }

    public boolean isIsolated() {
        return this.isIsolated;
    }

    public void addIntersections(LineIntersector lineIntersector, int i, int i2) {
        LineIntersector li = lineIntersector;
        int segmentIndex = i;
        int geomIndex = i2;
        for (int i3 = 0; i3 < li.getIntersectionNum(); i3++) {
            addIntersection(li, segmentIndex, geomIndex, i3);
        }
    }

    public void addIntersection(LineIntersector lineIntersector, int segmentIndex, int geomIndex, int i) {
        Coordinate coordinate;
        LineIntersector li = lineIntersector;
        int intIndex = i;
        new Coordinate(li.getIntersection(intIndex));
        Coordinate intPt = coordinate;
        int normalizedSegmentIndex = segmentIndex;
        double dist = li.getEdgeDistance(geomIndex, intIndex);
        int nextSegIndex = normalizedSegmentIndex + 1;
        if (nextSegIndex < this.pts.length) {
            if (intPt.equals2D(this.pts[nextSegIndex])) {
                normalizedSegmentIndex = nextSegIndex;
                dist = 0.0d;
            }
        }
        EdgeIntersection add = this.eiList.add(intPt, normalizedSegmentIndex, dist);
    }

    public void computeIM(IntersectionMatrix im) {
        updateIM(this.label, im);
    }

    public boolean equals(Object obj) {
        Object o = obj;
        if (!(o instanceof Edge)) {
            return false;
        }
        Edge e = (Edge) o;
        if (this.pts.length != e.pts.length) {
            return false;
        }
        boolean isEqualForward = true;
        boolean isEqualReverse = true;
        int iRev = this.pts.length;
        for (int i = 0; i < this.pts.length; i++) {
            if (!this.pts[i].equals2D(e.pts[i])) {
                isEqualForward = false;
            }
            iRev--;
            if (!this.pts[i].equals2D(e.pts[iRev])) {
                isEqualReverse = false;
            }
            if (!isEqualForward && !isEqualReverse) {
                return false;
            }
        }
        return true;
    }

    public boolean isPointwiseEqual(Edge edge) {
        Edge e = edge;
        if (this.pts.length != e.pts.length) {
            return false;
        }
        for (int i = 0; i < this.pts.length; i++) {
            if (!this.pts[i].equals2D(e.pts[i])) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        new StringBuilder();
        StringBuilder builder = sb;
        new StringBuilder();
        StringBuilder append = builder.append(sb2.append("edge ").append(this.name).append(": ").toString());
        StringBuilder append2 = builder.append("LINESTRING (");
        for (int i = 0; i < this.pts.length; i++) {
            if (i > 0) {
                StringBuilder append3 = builder.append(",");
            }
            new StringBuilder();
            StringBuilder append4 = builder.append(sb4.append(this.pts[i].f412x).append(" ").append(this.pts[i].f413y).toString());
        }
        new StringBuilder();
        StringBuilder append5 = builder.append(sb3.append(")  ").append(this.label).append(" ").append(this.depthDelta).toString());
        return builder.toString();
    }

    public void print(PrintStream printStream) {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        PrintStream out = printStream;
        new StringBuilder();
        out.print(sb.append("edge ").append(this.name).append(": ").toString());
        out.print("LINESTRING (");
        for (int i = 0; i < this.pts.length; i++) {
            if (i > 0) {
                out.print(",");
            }
            new StringBuilder();
            out.print(sb3.append(this.pts[i].f412x).append(" ").append(this.pts[i].f413y).toString());
        }
        new StringBuilder();
        out.print(sb2.append(")  ").append(this.label).append(" ").append(this.depthDelta).toString());
    }

    public void printReverse(PrintStream printStream) {
        StringBuilder sb;
        StringBuilder sb2;
        PrintStream out = printStream;
        new StringBuilder();
        out.print(sb.append("edge ").append(this.name).append(": ").toString());
        for (int i = this.pts.length - 1; i >= 0; i--) {
            new StringBuilder();
            out.print(sb2.append(this.pts[i]).append(" ").toString());
        }
        out.println("");
    }
}
