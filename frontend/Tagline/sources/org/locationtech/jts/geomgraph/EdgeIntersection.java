package org.locationtech.jts.geomgraph;

import java.io.PrintStream;
import org.locationtech.jts.geom.Coordinate;

public class EdgeIntersection implements Comparable {
    public Coordinate coord;
    public double dist;
    public int segmentIndex;

    public EdgeIntersection(Coordinate coord2, int segmentIndex2, double dist2) {
        Coordinate coordinate;
        new Coordinate(coord2);
        this.coord = coordinate;
        this.segmentIndex = segmentIndex2;
        this.dist = dist2;
    }

    public Coordinate getCoordinate() {
        return this.coord;
    }

    public int getSegmentIndex() {
        return this.segmentIndex;
    }

    public double getDistance() {
        return this.dist;
    }

    public int compareTo(Object obj) {
        EdgeIntersection other = (EdgeIntersection) obj;
        return compare(other.segmentIndex, other.dist);
    }

    public int compare(int i, double d) {
        int segmentIndex2 = i;
        double dist2 = d;
        if (this.segmentIndex < segmentIndex2) {
            return -1;
        }
        if (this.segmentIndex > segmentIndex2) {
            return 1;
        }
        if (this.dist < dist2) {
            return -1;
        }
        if (this.dist > dist2) {
            return 1;
        }
        return 0;
    }

    public boolean isEndPoint(int i) {
        int maxSegmentIndex = i;
        if (this.segmentIndex == 0 && this.dist == 0.0d) {
            return true;
        }
        if (this.segmentIndex == maxSegmentIndex) {
            return true;
        }
        return false;
    }

    public void print(PrintStream printStream) {
        StringBuilder sb;
        StringBuilder sb2;
        PrintStream out = printStream;
        out.print(this.coord);
        new StringBuilder();
        out.print(sb.append(" seg # = ").append(this.segmentIndex).toString());
        new StringBuilder();
        out.println(sb2.append(" dist = ").append(this.dist).toString());
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append(this.coord).append(" seg # = ").append(this.segmentIndex).append(" dist = ").append(this.dist).toString();
    }
}
