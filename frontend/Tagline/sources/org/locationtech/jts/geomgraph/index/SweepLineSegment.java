package org.locationtech.jts.geomgraph.index;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.Edge;

public class SweepLineSegment {
    Edge edge;
    int ptIndex;
    Coordinate[] pts;

    public SweepLineSegment(Edge edge2, int ptIndex2) {
        Edge edge3 = edge2;
        this.edge = edge3;
        this.ptIndex = ptIndex2;
        this.pts = edge3.getCoordinates();
    }

    public double getMinX() {
        double x1 = this.pts[this.ptIndex].f412x;
        double x2 = this.pts[this.ptIndex + 1].f412x;
        return x1 < x2 ? x1 : x2;
    }

    public double getMaxX() {
        double x1 = this.pts[this.ptIndex].f412x;
        double x2 = this.pts[this.ptIndex + 1].f412x;
        return x1 > x2 ? x1 : x2;
    }

    public void computeIntersections(SweepLineSegment sweepLineSegment, SegmentIntersector si) {
        SweepLineSegment ss = sweepLineSegment;
        si.addIntersections(this.edge, this.ptIndex, ss.edge, ss.ptIndex);
    }
}
