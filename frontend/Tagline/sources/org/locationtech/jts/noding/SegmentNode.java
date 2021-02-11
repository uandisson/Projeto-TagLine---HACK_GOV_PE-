package org.locationtech.jts.noding;

import java.io.PrintStream;
import org.locationtech.jts.geom.Coordinate;

public class SegmentNode implements Comparable {
    public final Coordinate coord;
    private final boolean isInterior;
    private final NodedSegmentString segString;
    public final int segmentIndex;
    private final int segmentOctant;

    public SegmentNode(NodedSegmentString nodedSegmentString, Coordinate coordinate, int i, int segmentOctant2) {
        Coordinate coordinate2;
        NodedSegmentString segString2 = nodedSegmentString;
        Coordinate coord2 = coordinate;
        int segmentIndex2 = i;
        this.segString = segString2;
        new Coordinate(coord2);
        this.coord = coordinate2;
        this.segmentIndex = segmentIndex2;
        this.segmentOctant = segmentOctant2;
        this.isInterior = !coord2.equals2D(segString2.getCoordinate(segmentIndex2));
    }

    public Coordinate getCoordinate() {
        return this.coord;
    }

    public boolean isInterior() {
        return this.isInterior;
    }

    public boolean isEndPoint(int i) {
        int maxSegmentIndex = i;
        if (this.segmentIndex == 0 && !this.isInterior) {
            return true;
        }
        if (this.segmentIndex == maxSegmentIndex) {
            return true;
        }
        return false;
    }

    public int compareTo(Object obj) {
        SegmentNode other = (SegmentNode) obj;
        if (this.segmentIndex < other.segmentIndex) {
            return -1;
        }
        if (this.segmentIndex > other.segmentIndex) {
            return 1;
        }
        if (this.coord.equals2D(other.coord)) {
            return 0;
        }
        return SegmentPointComparator.compare(this.segmentOctant, this.coord, other.coord);
    }

    public void print(PrintStream printStream) {
        StringBuilder sb;
        PrintStream out = printStream;
        out.print(this.coord);
        new StringBuilder();
        out.print(sb.append(" seg # = ").append(this.segmentIndex).toString());
    }
}
