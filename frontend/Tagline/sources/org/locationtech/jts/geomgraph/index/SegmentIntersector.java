package org.locationtech.jts.geomgraph.index;

import java.util.Collection;
import java.util.Iterator;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.Node;

public class SegmentIntersector {
    private Collection[] bdyNodes;
    private boolean hasIntersection = false;
    private boolean hasProper = false;
    private boolean hasProperInterior = false;
    private boolean includeProper;
    private boolean isDone = false;
    private boolean isDoneWhenProperInt = false;
    private boolean isSelfIntersection;

    /* renamed from: li */
    private LineIntersector f439li;
    private int numIntersections = 0;
    public int numTests = 0;
    private Coordinate properIntersectionPoint = null;
    private boolean recordIsolated;

    public static boolean isAdjacentSegments(int i1, int i2) {
        return Math.abs(i1 - i2) == 1;
    }

    public SegmentIntersector(LineIntersector li, boolean includeProper2, boolean recordIsolated2) {
        this.f439li = li;
        this.includeProper = includeProper2;
        this.recordIsolated = recordIsolated2;
    }

    public void setBoundaryNodes(Collection bdyNodes0, Collection bdyNodes1) {
        this.bdyNodes = new Collection[2];
        this.bdyNodes[0] = bdyNodes0;
        this.bdyNodes[1] = bdyNodes1;
    }

    public void setIsDoneIfProperInt(boolean isDoneWhenProperInt2) {
        boolean z = isDoneWhenProperInt2;
        this.isDoneWhenProperInt = z;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public Coordinate getProperIntersectionPoint() {
        return this.properIntersectionPoint;
    }

    public boolean hasIntersection() {
        return this.hasIntersection;
    }

    public boolean hasProperIntersection() {
        return this.hasProper;
    }

    public boolean hasProperInteriorIntersection() {
        return this.hasProperInterior;
    }

    private boolean isTrivialIntersection(Edge edge, int i, Edge e1, int i2) {
        Edge e0 = edge;
        int segIndex0 = i;
        int segIndex1 = i2;
        if (e0 == e1 && this.f439li.getIntersectionNum() == 1) {
            if (isAdjacentSegments(segIndex0, segIndex1)) {
                return true;
            }
            if (e0.isClosed()) {
                int maxSegIndex = e0.getNumPoints() - 1;
                if ((segIndex0 == 0 && segIndex1 == maxSegIndex) || (segIndex1 == 0 && segIndex0 == maxSegIndex)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addIntersections(Edge edge, int i, Edge edge2, int i2) {
        Edge e0 = edge;
        int segIndex0 = i;
        Edge e1 = edge2;
        int segIndex1 = i2;
        if (e0 != e1 || segIndex0 != segIndex1) {
            this.numTests++;
            this.f439li.computeIntersection(e0.getCoordinates()[segIndex0], e0.getCoordinates()[segIndex0 + 1], e1.getCoordinates()[segIndex1], e1.getCoordinates()[segIndex1 + 1]);
            if (this.f439li.hasIntersection()) {
                if (this.recordIsolated) {
                    e0.setIsolated(false);
                    e1.setIsolated(false);
                }
                this.numIntersections++;
                if (!isTrivialIntersection(e0, segIndex0, e1, segIndex1)) {
                    this.hasIntersection = true;
                    if (this.includeProper || !this.f439li.isProper()) {
                        e0.addIntersections(this.f439li, segIndex0, 0);
                        e1.addIntersections(this.f439li, segIndex1, 1);
                    }
                    if (this.f439li.isProper()) {
                        this.properIntersectionPoint = (Coordinate) this.f439li.getIntersection(0).clone();
                        this.hasProper = true;
                        if (this.isDoneWhenProperInt) {
                            this.isDone = true;
                        }
                        if (!isBoundaryPoint(this.f439li, this.bdyNodes)) {
                            this.hasProperInterior = true;
                        }
                    }
                }
            }
        }
    }

    private boolean isBoundaryPoint(LineIntersector lineIntersector, Collection[] collectionArr) {
        LineIntersector li = lineIntersector;
        Collection[] bdyNodes2 = collectionArr;
        if (bdyNodes2 == null) {
            return false;
        }
        if (isBoundaryPoint(li, bdyNodes2[0])) {
            return true;
        }
        if (isBoundaryPoint(li, bdyNodes2[1])) {
            return true;
        }
        return false;
    }

    private boolean isBoundaryPoint(LineIntersector lineIntersector, Collection bdyNodes2) {
        LineIntersector li = lineIntersector;
        Iterator i = bdyNodes2.iterator();
        while (i.hasNext()) {
            if (li.isIntersection(((Node) i.next()).getCoordinate())) {
                return true;
            }
        }
        return false;
    }
}
