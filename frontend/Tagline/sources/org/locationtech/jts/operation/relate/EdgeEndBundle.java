package org.locationtech.jts.operation.relate;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.BoundaryNodeRule;
import org.locationtech.jts.geom.IntersectionMatrix;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.EdgeEnd;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.geomgraph.Label;

public class EdgeEndBundle extends EdgeEnd {
    private List edgeEnds;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public EdgeEndBundle(org.locationtech.jts.algorithm.BoundaryNodeRule r12, org.locationtech.jts.geomgraph.EdgeEnd r13) {
        /*
            r11 = this;
            r0 = r11
            r1 = r12
            r2 = r13
            r3 = r0
            r4 = r2
            org.locationtech.jts.geomgraph.Edge r4 = r4.getEdge()
            r5 = r2
            org.locationtech.jts.geom.Coordinate r5 = r5.getCoordinate()
            r6 = r2
            org.locationtech.jts.geom.Coordinate r6 = r6.getDirectedCoordinate()
            org.locationtech.jts.geomgraph.Label r7 = new org.locationtech.jts.geomgraph.Label
            r10 = r7
            r7 = r10
            r8 = r10
            r9 = r2
            org.locationtech.jts.geomgraph.Label r9 = r9.getLabel()
            r8.<init>((org.locationtech.jts.geomgraph.Label) r9)
            r3.<init>(r4, r5, r6, r7)
            r3 = r0
            java.util.ArrayList r4 = new java.util.ArrayList
            r10 = r4
            r4 = r10
            r5 = r10
            r5.<init>()
            r3.edgeEnds = r4
            r3 = r0
            r4 = r2
            r3.insert(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.operation.relate.EdgeEndBundle.<init>(org.locationtech.jts.algorithm.BoundaryNodeRule, org.locationtech.jts.geomgraph.EdgeEnd):void");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public EdgeEndBundle(EdgeEnd e) {
        this((BoundaryNodeRule) null, e);
    }

    public Label getLabel() {
        return this.label;
    }

    public Iterator iterator() {
        return this.edgeEnds.iterator();
    }

    public List getEdgeEnds() {
        return this.edgeEnds;
    }

    public void insert(EdgeEnd e) {
        boolean add = this.edgeEnds.add(e);
    }

    public void computeLabel(BoundaryNodeRule boundaryNodeRule) {
        Label label;
        Label label2;
        BoundaryNodeRule boundaryNodeRule2 = boundaryNodeRule;
        boolean isArea = false;
        Iterator it = iterator();
        while (it.hasNext()) {
            if (((EdgeEnd) it.next()).getLabel().isArea()) {
                isArea = true;
            }
        }
        if (isArea) {
            new Label(-1, -1, -1);
            this.label = label2;
        } else {
            new Label(-1);
            this.label = label;
        }
        for (int i = 0; i < 2; i++) {
            computeLabelOn(i, boundaryNodeRule2);
            if (isArea) {
                computeLabelSides(i);
            }
        }
    }

    private void computeLabelOn(int i, BoundaryNodeRule boundaryNodeRule) {
        int geomIndex = i;
        BoundaryNodeRule boundaryNodeRule2 = boundaryNodeRule;
        int boundaryCount = 0;
        boolean foundInterior = false;
        Iterator it = iterator();
        while (it.hasNext()) {
            int loc = ((EdgeEnd) it.next()).getLabel().getLocation(geomIndex);
            if (loc == 1) {
                boundaryCount++;
            }
            if (loc == 0) {
                foundInterior = true;
            }
        }
        int loc2 = -1;
        if (foundInterior) {
            loc2 = 0;
        }
        if (boundaryCount > 0) {
            loc2 = GeometryGraph.determineBoundary(boundaryNodeRule2, boundaryCount);
        }
        this.label.setLocation(geomIndex, loc2);
    }

    private void computeLabelSides(int i) {
        int geomIndex = i;
        computeLabelSide(geomIndex, 1);
        computeLabelSide(geomIndex, 2);
    }

    private void computeLabelSide(int i, int i2) {
        int geomIndex = i;
        int side = i2;
        Iterator it = iterator();
        while (it.hasNext()) {
            EdgeEnd e = (EdgeEnd) it.next();
            if (e.getLabel().isArea()) {
                int loc = e.getLabel().getLocation(geomIndex, side);
                if (loc == 0) {
                    this.label.setLocation(geomIndex, side, 0);
                    return;
                } else if (loc == 2) {
                    this.label.setLocation(geomIndex, side, 2);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void updateIM(IntersectionMatrix im) {
        Edge.updateIM(this.label, im);
    }

    public void print(PrintStream printStream) {
        StringBuilder sb;
        PrintStream out = printStream;
        new StringBuilder();
        out.println(sb.append("EdgeEndBundle--> Label: ").append(this.label).toString());
        Iterator it = iterator();
        while (it.hasNext()) {
            ((EdgeEnd) it.next()).print(out);
            out.println();
        }
    }
}
