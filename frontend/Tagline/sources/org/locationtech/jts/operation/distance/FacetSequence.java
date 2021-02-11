package org.locationtech.jts.operation.distance;

import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;

public class FacetSequence {
    private int end;

    /* renamed from: p0 */
    private Coordinate f485p0;

    /* renamed from: p1 */
    private Coordinate f486p1;

    /* renamed from: pt */
    private Coordinate f487pt;
    private CoordinateSequence pts;

    /* renamed from: q0 */
    private Coordinate f488q0;

    /* renamed from: q1 */
    private Coordinate f489q1;
    private Coordinate seqPt;
    private int start;

    public FacetSequence(CoordinateSequence pts2, int start2, int end2) {
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        Coordinate coordinate5;
        Coordinate coordinate6;
        new Coordinate();
        this.f487pt = coordinate;
        new Coordinate();
        this.seqPt = coordinate2;
        new Coordinate();
        this.f485p0 = coordinate3;
        new Coordinate();
        this.f486p1 = coordinate4;
        new Coordinate();
        this.f488q0 = coordinate5;
        new Coordinate();
        this.f489q1 = coordinate6;
        this.pts = pts2;
        this.start = start2;
        this.end = end2;
    }

    public FacetSequence(CoordinateSequence pts2, int i) {
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        Coordinate coordinate5;
        Coordinate coordinate6;
        int start2 = i;
        new Coordinate();
        this.f487pt = coordinate;
        new Coordinate();
        this.seqPt = coordinate2;
        new Coordinate();
        this.f485p0 = coordinate3;
        new Coordinate();
        this.f486p1 = coordinate4;
        new Coordinate();
        this.f488q0 = coordinate5;
        new Coordinate();
        this.f489q1 = coordinate6;
        this.pts = pts2;
        this.start = start2;
        this.end = start2 + 1;
    }

    public Envelope getEnvelope() {
        Envelope envelope;
        new Envelope();
        Envelope env = envelope;
        for (int i = this.start; i < this.end; i++) {
            env.expandToInclude(this.pts.getX(i), this.pts.getY(i));
        }
        return env;
    }

    public int size() {
        return this.end - this.start;
    }

    public Coordinate getCoordinate(int index) {
        return this.pts.getCoordinate(this.start + index);
    }

    public boolean isPoint() {
        return this.end - this.start == 1;
    }

    public double distance(FacetSequence facetSequence) {
        FacetSequence facetSeq = facetSequence;
        boolean isPoint = isPoint();
        boolean isPointOther = facetSeq.isPoint();
        if (isPoint && isPointOther) {
            this.pts.getCoordinate(this.start, this.f487pt);
            facetSeq.pts.getCoordinate(facetSeq.start, this.seqPt);
            return this.f487pt.distance(this.seqPt);
        } else if (isPoint) {
            this.pts.getCoordinate(this.start, this.f487pt);
            return computePointLineDistance(this.f487pt, facetSeq);
        } else if (!isPointOther) {
            return computeLineLineDistance(facetSeq);
        } else {
            facetSeq.pts.getCoordinate(facetSeq.start, this.seqPt);
            return computePointLineDistance(this.seqPt, this);
        }
    }

    private double computeLineLineDistance(FacetSequence facetSequence) {
        FacetSequence facetSeq = facetSequence;
        double minDistance = Double.MAX_VALUE;
        for (int i = this.start; i < this.end - 1; i++) {
            for (int j = facetSeq.start; j < facetSeq.end - 1; j++) {
                this.pts.getCoordinate(i, this.f485p0);
                this.pts.getCoordinate(i + 1, this.f486p1);
                facetSeq.pts.getCoordinate(j, this.f488q0);
                facetSeq.pts.getCoordinate(j + 1, this.f489q1);
                double dist = CGAlgorithms.distanceLineLine(this.f485p0, this.f486p1, this.f488q0, this.f489q1);
                if (dist == 0.0d) {
                    return 0.0d;
                }
                if (dist < minDistance) {
                    minDistance = dist;
                }
            }
        }
        return minDistance;
    }

    private double computePointLineDistance(Coordinate coordinate, FacetSequence facetSequence) {
        Coordinate pt = coordinate;
        FacetSequence facetSeq = facetSequence;
        double minDistance = Double.MAX_VALUE;
        for (int i = facetSeq.start; i < facetSeq.end - 1; i++) {
            facetSeq.pts.getCoordinate(i, this.f488q0);
            facetSeq.pts.getCoordinate(i + 1, this.f489q1);
            double dist = CGAlgorithms.distancePointLine(pt, this.f488q0, this.f489q1);
            if (dist == 0.0d) {
                return 0.0d;
            }
            if (dist < minDistance) {
                minDistance = dist;
            }
        }
        return minDistance;
    }

    public String toString() {
        StringBuffer stringBuffer;
        Coordinate coordinate;
        StringBuilder sb;
        new StringBuffer();
        StringBuffer buf = stringBuffer;
        StringBuffer append = buf.append("LINESTRING ( ");
        new Coordinate();
        Coordinate p = coordinate;
        for (int i = this.start; i < this.end; i++) {
            if (i > this.start) {
                StringBuffer append2 = buf.append(", ");
            }
            this.pts.getCoordinate(i, p);
            new StringBuilder();
            StringBuffer append3 = buf.append(sb.append(p.f412x).append(" ").append(p.f413y).toString());
        }
        StringBuffer append4 = buf.append(" )");
        return buf.toString();
    }
}
