package org.locationtech.jts.geomgraph;

import java.io.PrintStream;
import org.locationtech.jts.geom.TopologyException;

public class DirectedEdge extends EdgeEnd {
    private int[] depth = {0, -999, -999};
    private EdgeRing edgeRing;
    protected boolean isForward;
    private boolean isInResult = false;
    private boolean isVisited = false;
    private EdgeRing minEdgeRing;
    private DirectedEdge next;
    private DirectedEdge nextMin;
    private DirectedEdge sym;

    public static int depthFactor(int i, int i2) {
        int currLocation = i;
        int nextLocation = i2;
        if (currLocation == 2 && nextLocation == 0) {
            return 1;
        }
        if (currLocation == 0 && nextLocation == 2) {
            return -1;
        }
        return 0;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DirectedEdge(org.locationtech.jts.geomgraph.Edge r10, boolean r11) {
        /*
            r9 = this;
            r0 = r9
            r1 = r10
            r2 = r11
            r4 = r0
            r5 = r1
            r4.<init>(r5)
            r4 = r0
            r5 = 0
            r4.isInResult = r5
            r4 = r0
            r5 = 0
            r4.isVisited = r5
            r4 = r0
            r5 = 3
            int[] r5 = new int[r5]
            r5 = {0, -999, -999} // fill-array
            r4.depth = r5
            r4 = r0
            r5 = r2
            r4.isForward = r5
            r4 = r2
            if (r4 == 0) goto L_0x0035
            r4 = r0
            r5 = r1
            r6 = 0
            org.locationtech.jts.geom.Coordinate r5 = r5.getCoordinate(r6)
            r6 = r1
            r7 = 1
            org.locationtech.jts.geom.Coordinate r6 = r6.getCoordinate(r7)
            r4.init(r5, r6)
        L_0x0030:
            r4 = r0
            r4.computeDirectedLabel()
            return
        L_0x0035:
            r4 = r1
            int r4 = r4.getNumPoints()
            r5 = 1
            int r4 = r4 + -1
            r3 = r4
            r4 = r0
            r5 = r1
            r6 = r3
            org.locationtech.jts.geom.Coordinate r5 = r5.getCoordinate(r6)
            r6 = r1
            r7 = r3
            r8 = 1
            int r7 = r7 + -1
            org.locationtech.jts.geom.Coordinate r6 = r6.getCoordinate(r7)
            r4.init(r5, r6)
            goto L_0x0030
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geomgraph.DirectedEdge.<init>(org.locationtech.jts.geomgraph.Edge, boolean):void");
    }

    public Edge getEdge() {
        return this.edge;
    }

    public void setInResult(boolean isInResult2) {
        boolean z = isInResult2;
        this.isInResult = z;
    }

    public boolean isInResult() {
        return this.isInResult;
    }

    public boolean isVisited() {
        return this.isVisited;
    }

    public void setVisited(boolean isVisited2) {
        boolean z = isVisited2;
        this.isVisited = z;
    }

    public void setEdgeRing(EdgeRing edgeRing2) {
        EdgeRing edgeRing3 = edgeRing2;
        this.edgeRing = edgeRing3;
    }

    public EdgeRing getEdgeRing() {
        return this.edgeRing;
    }

    public void setMinEdgeRing(EdgeRing minEdgeRing2) {
        EdgeRing edgeRing2 = minEdgeRing2;
        this.minEdgeRing = edgeRing2;
    }

    public EdgeRing getMinEdgeRing() {
        return this.minEdgeRing;
    }

    public int getDepth(int position) {
        return this.depth[position];
    }

    public void setDepth(int i, int i2) {
        Throwable th;
        int position = i;
        int depthVal = i2;
        if (this.depth[position] == -999 || this.depth[position] == depthVal) {
            this.depth[position] = depthVal;
            return;
        }
        Throwable th2 = th;
        new TopologyException("assigned depths do not match", getCoordinate());
        throw th2;
    }

    public int getDepthDelta() {
        int depthDelta = this.edge.getDepthDelta();
        if (!this.isForward) {
            depthDelta = -depthDelta;
        }
        return depthDelta;
    }

    public void setVisitedEdge(boolean z) {
        boolean isVisited2 = z;
        setVisited(isVisited2);
        this.sym.setVisited(isVisited2);
    }

    public DirectedEdge getSym() {
        return this.sym;
    }

    public boolean isForward() {
        return this.isForward;
    }

    public void setSym(DirectedEdge de) {
        DirectedEdge directedEdge = de;
        this.sym = directedEdge;
    }

    public DirectedEdge getNext() {
        return this.next;
    }

    public void setNext(DirectedEdge next2) {
        DirectedEdge directedEdge = next2;
        this.next = directedEdge;
    }

    public DirectedEdge getNextMin() {
        return this.nextMin;
    }

    public void setNextMin(DirectedEdge nextMin2) {
        DirectedEdge directedEdge = nextMin2;
        this.nextMin = directedEdge;
    }

    public boolean isLineEdge() {
        return (this.label.isLine(0) || this.label.isLine(1)) && (!this.label.isArea(0) || this.label.allPositionsEqual(0, 2)) && (!this.label.isArea(1) || this.label.allPositionsEqual(1, 2));
    }

    public boolean isInteriorAreaEdge() {
        boolean isInteriorAreaEdge = true;
        for (int i = 0; i < 2; i++) {
            if (!this.label.isArea(i) || this.label.getLocation(i, 1) != 0 || this.label.getLocation(i, 2) != 0) {
                isInteriorAreaEdge = false;
            }
        }
        return isInteriorAreaEdge;
    }

    private void computeDirectedLabel() {
        Label label;
        new Label(this.edge.getLabel());
        this.label = label;
        if (!this.isForward) {
            this.label.flip();
        }
    }

    public void setEdgeDepths(int i, int i2) {
        int position = i;
        int depth2 = i2;
        int depthDelta = getEdge().getDepthDelta();
        if (!this.isForward) {
            depthDelta = -depthDelta;
        }
        int directionFactor = 1;
        if (position == 1) {
            directionFactor = -1;
        }
        setDepth(position, depth2);
        setDepth(Position.opposite(position), depth2 + (depthDelta * directionFactor));
    }

    public void print(PrintStream printStream) {
        StringBuilder sb;
        StringBuilder sb2;
        PrintStream out = printStream;
        super.print(out);
        new StringBuilder();
        out.print(sb.append(" ").append(this.depth[1]).append("/").append(this.depth[2]).toString());
        new StringBuilder();
        out.print(sb2.append(" (").append(getDepthDelta()).append(")").toString());
        if (this.isInResult) {
            out.print(" inResult");
        }
    }

    public void printEdge(PrintStream printStream) {
        PrintStream out = printStream;
        print(out);
        out.print(" ");
        if (this.isForward) {
            this.edge.print(out);
        } else {
            this.edge.printReverse(out);
        }
    }
}
