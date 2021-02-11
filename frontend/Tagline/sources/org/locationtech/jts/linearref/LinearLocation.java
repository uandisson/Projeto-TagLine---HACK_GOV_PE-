package org.locationtech.jts.linearref;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;

public class LinearLocation implements Comparable {
    private int componentIndex;
    private double segmentFraction;
    private int segmentIndex;

    public static LinearLocation getEndLocation(Geometry linear) {
        LinearLocation linearLocation;
        new LinearLocation();
        LinearLocation loc = linearLocation;
        loc.setToEnd(linear);
        return loc;
    }

    public static Coordinate pointAlongSegmentByFraction(Coordinate coordinate, Coordinate coordinate2, double d) {
        Coordinate p0;
        Coordinate p02 = coordinate;
        Coordinate p1 = coordinate2;
        double frac = d;
        if (frac <= 0.0d) {
            return p02;
        }
        if (frac >= 1.0d) {
            return p1;
        }
        new Coordinate(((p1.f412x - p02.f412x) * frac) + p02.f412x, ((p1.f413y - p02.f413y) * frac) + p02.f413y, ((p1.f414z - p02.f414z) * frac) + p02.f414z);
        return p0;
    }

    public LinearLocation() {
        this.componentIndex = 0;
        this.segmentIndex = 0;
        this.segmentFraction = 0.0d;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public LinearLocation(int segmentIndex2, double segmentFraction2) {
        this(0, segmentIndex2, segmentFraction2);
    }

    public LinearLocation(int componentIndex2, int segmentIndex2, double segmentFraction2) {
        this.componentIndex = 0;
        this.segmentIndex = 0;
        this.segmentFraction = 0.0d;
        this.componentIndex = componentIndex2;
        this.segmentIndex = segmentIndex2;
        this.segmentFraction = segmentFraction2;
        normalize();
    }

    private LinearLocation(int componentIndex2, int segmentIndex2, double segmentFraction2, boolean doNormalize) {
        this.componentIndex = 0;
        this.segmentIndex = 0;
        this.segmentFraction = 0.0d;
        this.componentIndex = componentIndex2;
        this.segmentIndex = segmentIndex2;
        this.segmentFraction = segmentFraction2;
        if (doNormalize) {
            normalize();
        }
    }

    public LinearLocation(LinearLocation linearLocation) {
        LinearLocation loc = linearLocation;
        this.componentIndex = 0;
        this.segmentIndex = 0;
        this.segmentFraction = 0.0d;
        this.componentIndex = loc.componentIndex;
        this.segmentIndex = loc.segmentIndex;
        this.segmentFraction = loc.segmentFraction;
    }

    private void normalize() {
        if (this.segmentFraction < 0.0d) {
            this.segmentFraction = 0.0d;
        }
        if (this.segmentFraction > 1.0d) {
            this.segmentFraction = 1.0d;
        }
        if (this.componentIndex < 0) {
            this.componentIndex = 0;
            this.segmentIndex = 0;
            this.segmentFraction = 0.0d;
        }
        if (this.segmentIndex < 0) {
            this.segmentIndex = 0;
            this.segmentFraction = 0.0d;
        }
        if (this.segmentFraction == 1.0d) {
            this.segmentFraction = 0.0d;
            this.segmentIndex++;
        }
    }

    public void clamp(Geometry geometry) {
        Geometry linear = geometry;
        if (this.componentIndex >= linear.getNumGeometries()) {
            setToEnd(linear);
        } else if (this.segmentIndex >= linear.getNumPoints()) {
            this.segmentIndex = ((LineString) linear.getGeometryN(this.componentIndex)).getNumPoints() - 1;
            this.segmentFraction = 1.0d;
        }
    }

    public void snapToVertex(Geometry geometry, double d) {
        Geometry linearGeom = geometry;
        double minDistance = d;
        if (this.segmentFraction > 0.0d && this.segmentFraction < 1.0d) {
            double segLen = getSegmentLength(linearGeom);
            double lenToStart = this.segmentFraction * segLen;
            double lenToEnd = segLen - lenToStart;
            if (lenToStart <= lenToEnd && lenToStart < minDistance) {
                this.segmentFraction = 0.0d;
            } else if (lenToEnd <= lenToStart && lenToEnd < minDistance) {
                this.segmentFraction = 1.0d;
            }
        }
    }

    public double getSegmentLength(Geometry linearGeom) {
        LineString lineComp = (LineString) linearGeom.getGeometryN(this.componentIndex);
        int segIndex = this.segmentIndex;
        if (this.segmentIndex >= lineComp.getNumPoints() - 1) {
            segIndex = lineComp.getNumPoints() - 2;
        }
        return lineComp.getCoordinateN(segIndex).distance(lineComp.getCoordinateN(segIndex + 1));
    }

    public void setToEnd(Geometry geometry) {
        Geometry linear = geometry;
        this.componentIndex = linear.getNumGeometries() - 1;
        this.segmentIndex = ((LineString) linear.getGeometryN(this.componentIndex)).getNumPoints() - 1;
        this.segmentFraction = 1.0d;
    }

    public int getComponentIndex() {
        return this.componentIndex;
    }

    public int getSegmentIndex() {
        return this.segmentIndex;
    }

    public double getSegmentFraction() {
        return this.segmentFraction;
    }

    public boolean isVertex() {
        return this.segmentFraction <= 0.0d || this.segmentFraction >= 1.0d;
    }

    public Coordinate getCoordinate(Geometry linearGeom) {
        LineString lineComp = (LineString) linearGeom.getGeometryN(this.componentIndex);
        Coordinate p0 = lineComp.getCoordinateN(this.segmentIndex);
        if (this.segmentIndex >= lineComp.getNumPoints() - 1) {
            return p0;
        }
        return pointAlongSegmentByFraction(p0, lineComp.getCoordinateN(this.segmentIndex + 1), this.segmentFraction);
    }

    public LineSegment getSegment(Geometry linearGeom) {
        LineSegment lineSegment;
        LineSegment lineSegment2;
        LineString lineComp = (LineString) linearGeom.getGeometryN(this.componentIndex);
        Coordinate p0 = lineComp.getCoordinateN(this.segmentIndex);
        if (this.segmentIndex >= lineComp.getNumPoints() - 1) {
            new LineSegment(lineComp.getCoordinateN(lineComp.getNumPoints() - 2), p0);
            return lineSegment2;
        }
        new LineSegment(p0, lineComp.getCoordinateN(this.segmentIndex + 1));
        return lineSegment;
    }

    public boolean isValid(Geometry geometry) {
        Geometry linearGeom = geometry;
        if (this.componentIndex < 0 || this.componentIndex >= linearGeom.getNumGeometries()) {
            return false;
        }
        LineString lineComp = (LineString) linearGeom.getGeometryN(this.componentIndex);
        if (this.segmentIndex < 0 || this.segmentIndex > lineComp.getNumPoints()) {
            return false;
        }
        if (this.segmentIndex == lineComp.getNumPoints() && this.segmentFraction != 0.0d) {
            return false;
        }
        if (this.segmentFraction < 0.0d || this.segmentFraction > 1.0d) {
            return false;
        }
        return true;
    }

    public int compareTo(Object o) {
        LinearLocation other = (LinearLocation) o;
        if (this.componentIndex < other.componentIndex) {
            return -1;
        }
        if (this.componentIndex > other.componentIndex) {
            return 1;
        }
        if (this.segmentIndex < other.segmentIndex) {
            return -1;
        }
        if (this.segmentIndex > other.segmentIndex) {
            return 1;
        }
        if (this.segmentFraction < other.segmentFraction) {
            return -1;
        }
        if (this.segmentFraction > other.segmentFraction) {
            return 1;
        }
        return 0;
    }

    public int compareLocationValues(int i, int i2, double d) {
        int componentIndex1 = i;
        int segmentIndex1 = i2;
        double segmentFraction1 = d;
        if (this.componentIndex < componentIndex1) {
            return -1;
        }
        if (this.componentIndex > componentIndex1) {
            return 1;
        }
        if (this.segmentIndex < segmentIndex1) {
            return -1;
        }
        if (this.segmentIndex > segmentIndex1) {
            return 1;
        }
        if (this.segmentFraction < segmentFraction1) {
            return -1;
        }
        if (this.segmentFraction > segmentFraction1) {
            return 1;
        }
        return 0;
    }

    public static int compareLocationValues(int i, int i2, double d, int i3, int i4, double d2) {
        int componentIndex0 = i;
        int segmentIndex0 = i2;
        double segmentFraction0 = d;
        int componentIndex1 = i3;
        int segmentIndex1 = i4;
        double segmentFraction1 = d2;
        if (componentIndex0 < componentIndex1) {
            return -1;
        }
        if (componentIndex0 > componentIndex1) {
            return 1;
        }
        if (segmentIndex0 < segmentIndex1) {
            return -1;
        }
        if (segmentIndex0 > segmentIndex1) {
            return 1;
        }
        if (segmentFraction0 < segmentFraction1) {
            return -1;
        }
        if (segmentFraction0 > segmentFraction1) {
            return 1;
        }
        return 0;
    }

    public boolean isOnSameSegment(LinearLocation linearLocation) {
        LinearLocation loc = linearLocation;
        if (this.componentIndex != loc.componentIndex) {
            return false;
        }
        if (this.segmentIndex == loc.segmentIndex) {
            return true;
        }
        if (loc.segmentIndex - this.segmentIndex == 1 && loc.segmentFraction == 0.0d) {
            return true;
        }
        if (this.segmentIndex - loc.segmentIndex == 1 && this.segmentFraction == 0.0d) {
            return true;
        }
        return false;
    }

    public boolean isEndpoint(Geometry linearGeom) {
        int nseg = ((LineString) linearGeom.getGeometryN(this.componentIndex)).getNumPoints() - 1;
        return this.segmentIndex >= nseg || (this.segmentIndex == nseg && this.segmentFraction >= 1.0d);
    }

    public LinearLocation toLowest(Geometry linearGeom) {
        LinearLocation linearLocation;
        int nseg = ((LineString) linearGeom.getGeometryN(this.componentIndex)).getNumPoints() - 1;
        if (this.segmentIndex < nseg) {
            return this;
        }
        new LinearLocation(this.componentIndex, nseg, 1.0d, false);
        return linearLocation;
    }

    public Object clone() {
        LinearLocation linearLocation;
        new LinearLocation(this.componentIndex, this.segmentIndex, this.segmentFraction);
        return linearLocation;
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("LinearLoc[").append(this.componentIndex).append(", ").append(this.segmentIndex).append(", ").append(this.segmentFraction).append("]").toString();
    }
}
