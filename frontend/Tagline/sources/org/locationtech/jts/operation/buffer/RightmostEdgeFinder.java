package org.locationtech.jts.operation.buffer;

import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.DirectedEdge;
import org.locationtech.jts.geomgraph.DirectedEdgeStar;
import org.locationtech.jts.util.Assert;

class RightmostEdgeFinder {
    private Coordinate minCoord = null;
    private DirectedEdge minDe = null;
    private int minIndex = -1;
    private DirectedEdge orientedDe = null;

    public RightmostEdgeFinder() {
    }

    public DirectedEdge getEdge() {
        return this.orientedDe;
    }

    public Coordinate getCoordinate() {
        return this.minCoord;
    }

    public void findEdge(List dirEdgeList) {
        Iterator i = dirEdgeList.iterator();
        while (i.hasNext()) {
            DirectedEdge de = (DirectedEdge) i.next();
            if (de.isForward()) {
                checkForRightmostCoordinate(de);
            }
        }
        Assert.isTrue(this.minIndex != 0 || this.minCoord.equals(this.minDe.getCoordinate()), "inconsistency in rightmost processing");
        if (this.minIndex == 0) {
            findRightmostEdgeAtNode();
        } else {
            findRightmostEdgeAtVertex();
        }
        this.orientedDe = this.minDe;
        if (getRightmostSide(this.minDe, this.minIndex) == 1) {
            this.orientedDe = this.minDe.getSym();
        }
    }

    private void findRightmostEdgeAtNode() {
        this.minDe = ((DirectedEdgeStar) this.minDe.getNode().getEdges()).getRightmostEdge();
        if (!this.minDe.isForward()) {
            this.minDe = this.minDe.getSym();
            this.minIndex = this.minDe.getEdge().getCoordinates().length - 1;
        }
    }

    private void findRightmostEdgeAtVertex() {
        Coordinate[] pts = this.minDe.getEdge().getCoordinates();
        Assert.isTrue(this.minIndex > 0 && this.minIndex < pts.length, "rightmost point expected to be interior vertex of edge");
        Coordinate pPrev = pts[this.minIndex - 1];
        Coordinate pNext = pts[this.minIndex + 1];
        int orientation = CGAlgorithms.computeOrientation(this.minCoord, pNext, pPrev);
        boolean usePrev = false;
        if (pPrev.f413y < this.minCoord.f413y && pNext.f413y < this.minCoord.f413y && orientation == 1) {
            usePrev = true;
        } else if (pPrev.f413y > this.minCoord.f413y && pNext.f413y > this.minCoord.f413y && orientation == -1) {
            usePrev = true;
        }
        if (usePrev) {
            this.minIndex--;
        }
    }

    private void checkForRightmostCoordinate(DirectedEdge directedEdge) {
        DirectedEdge de = directedEdge;
        Coordinate[] coord = de.getEdge().getCoordinates();
        for (int i = 0; i < coord.length - 1; i++) {
            if (this.minCoord == null || coord[i].f412x > this.minCoord.f412x) {
                this.minDe = de;
                this.minIndex = i;
                this.minCoord = coord[i];
            }
        }
    }

    private int getRightmostSide(DirectedEdge directedEdge, int i) {
        DirectedEdge de = directedEdge;
        int index = i;
        int side = getRightmostSideOfSegment(de, index);
        if (side < 0) {
            side = getRightmostSideOfSegment(de, index - 1);
        }
        if (side < 0) {
            this.minCoord = null;
            checkForRightmostCoordinate(de);
        }
        return side;
    }

    private int getRightmostSideOfSegment(DirectedEdge de, int i) {
        int i2 = i;
        Coordinate[] coord = de.getEdge().getCoordinates();
        if (i2 < 0 || i2 + 1 >= coord.length) {
            return -1;
        }
        if (coord[i2].f413y == coord[i2 + 1].f413y) {
            return -1;
        }
        int pos = 1;
        if (coord[i2].f413y < coord[i2 + 1].f413y) {
            pos = 2;
        }
        return pos;
    }
}
