package org.locationtech.jts.triangulate;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineSegment;

public class SplitSegment {
    private double minimumLen = 0.0d;
    private LineSegment seg;
    private double segLen;
    private Coordinate splitPt;

    private static Coordinate pointAlongReverse(LineSegment lineSegment, double d) {
        Coordinate coordinate;
        LineSegment seg2 = lineSegment;
        double segmentLengthFraction = d;
        new Coordinate();
        Coordinate coord = coordinate;
        coord.f412x = seg2.f423p1.f412x - (segmentLengthFraction * (seg2.f423p1.f412x - seg2.f422p0.f412x));
        coord.f413y = seg2.f423p1.f413y - (segmentLengthFraction * (seg2.f423p1.f413y - seg2.f422p0.f413y));
        return coord;
    }

    public SplitSegment(LineSegment lineSegment) {
        LineSegment seg2 = lineSegment;
        this.seg = seg2;
        this.segLen = seg2.getLength();
    }

    public void setMinimumLength(double minLen) {
        double d = minLen;
        this.minimumLen = d;
    }

    public Coordinate getSplitPoint() {
        return this.splitPt;
    }

    public void splitAt(double length, Coordinate endPt) {
        double frac = getConstrainedLength(length) / this.segLen;
        if (endPt.equals2D(this.seg.f422p0)) {
            this.splitPt = this.seg.pointAlong(frac);
            return;
        }
        this.splitPt = pointAlongReverse(this.seg, frac);
    }

    public void splitAt(Coordinate coordinate) {
        Coordinate pt = coordinate;
        double minFrac = this.minimumLen / this.segLen;
        if (pt.distance(this.seg.f422p0) < this.minimumLen) {
            this.splitPt = this.seg.pointAlong(minFrac);
        } else if (pt.distance(this.seg.f423p1) < this.minimumLen) {
            this.splitPt = pointAlongReverse(this.seg, minFrac);
        } else {
            this.splitPt = pt;
        }
    }

    private double getConstrainedLength(double d) {
        double len = d;
        if (len < this.minimumLen) {
            return this.minimumLen;
        }
        return len;
    }
}
