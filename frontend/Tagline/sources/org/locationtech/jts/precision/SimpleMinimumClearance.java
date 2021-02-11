package org.locationtech.jts.precision;

import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateFilter;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFilter;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;

public class SimpleMinimumClearance {
    /* access modifiers changed from: private */
    public Geometry inputGeom;
    private double minClearance;
    private Coordinate[] minClearancePts;

    public static double getDistance(Geometry g) {
        SimpleMinimumClearance rp;
        new SimpleMinimumClearance(g);
        return rp.getDistance();
    }

    public static Geometry getLine(Geometry g) {
        SimpleMinimumClearance rp;
        new SimpleMinimumClearance(g);
        return rp.getLine();
    }

    public SimpleMinimumClearance(Geometry geom) {
        this.inputGeom = geom;
    }

    public double getDistance() {
        compute();
        return this.minClearance;
    }

    public LineString getLine() {
        compute();
        return this.inputGeom.getFactory().createLineString(this.minClearancePts);
    }

    private void compute() {
        CoordinateFilter coordinateFilter;
        if (this.minClearancePts == null) {
            this.minClearancePts = new Coordinate[2];
            this.minClearance = Double.MAX_VALUE;
            new VertexCoordinateFilter(this);
            this.inputGeom.apply(coordinateFilter);
        }
    }

    /* access modifiers changed from: private */
    public void updateClearance(double d, Coordinate coordinate, Coordinate coordinate2) {
        Coordinate coordinate3;
        Coordinate coordinate4;
        double candidateValue = d;
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        if (candidateValue < this.minClearance) {
            this.minClearance = candidateValue;
            new Coordinate(p0);
            this.minClearancePts[0] = coordinate3;
            new Coordinate(p1);
            this.minClearancePts[1] = coordinate4;
        }
    }

    /* access modifiers changed from: private */
    public void updateClearance(double d, Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate coordinate4;
        LineSegment seg;
        Coordinate coordinate5;
        double candidateValue = d;
        Coordinate p = coordinate;
        Coordinate seg0 = coordinate2;
        Coordinate seg1 = coordinate3;
        if (candidateValue < this.minClearance) {
            this.minClearance = candidateValue;
            new Coordinate(p);
            this.minClearancePts[0] = coordinate4;
            new LineSegment(seg0, seg1);
            new Coordinate(seg.closestPoint(p));
            this.minClearancePts[1] = coordinate5;
        }
    }

    private class VertexCoordinateFilter implements CoordinateFilter {
        final /* synthetic */ SimpleMinimumClearance this$0;

        public VertexCoordinateFilter(SimpleMinimumClearance simpleMinimumClearance) {
            this.this$0 = simpleMinimumClearance;
        }

        public void filter(Coordinate coord) {
            CoordinateSequenceFilter coordinateSequenceFilter;
            new ComputeMCCoordinateSequenceFilter(this.this$0, coord);
            this.this$0.inputGeom.apply(coordinateSequenceFilter);
        }
    }

    private class ComputeMCCoordinateSequenceFilter implements CoordinateSequenceFilter {
        private Coordinate queryPt;
        final /* synthetic */ SimpleMinimumClearance this$0;

        public ComputeMCCoordinateSequenceFilter(SimpleMinimumClearance simpleMinimumClearance, Coordinate queryPt2) {
            this.this$0 = simpleMinimumClearance;
            this.queryPt = queryPt2;
        }

        public void filter(CoordinateSequence coordinateSequence, int i) {
            CoordinateSequence seq = coordinateSequence;
            int i2 = i;
            checkVertexDistance(seq.getCoordinate(i2));
            if (i2 > 0) {
                checkSegmentDistance(seq.getCoordinate(i2 - 1), seq.getCoordinate(i2));
            }
        }

        private void checkVertexDistance(Coordinate coordinate) {
            Coordinate vertex = coordinate;
            double vertexDist = vertex.distance(this.queryPt);
            if (vertexDist > 0.0d) {
                this.this$0.updateClearance(vertexDist, this.queryPt, vertex);
            }
        }

        private void checkSegmentDistance(Coordinate coordinate, Coordinate coordinate2) {
            Coordinate seg0 = coordinate;
            Coordinate seg1 = coordinate2;
            if (!this.queryPt.equals2D(seg0) && !this.queryPt.equals2D(seg1)) {
                double segDist = CGAlgorithms.distancePointLine(this.queryPt, seg1, seg0);
                if (segDist > 0.0d) {
                    this.this$0.updateClearance(segDist, this.queryPt, seg1, seg0);
                }
            }
        }

        public boolean isDone() {
            return false;
        }

        public boolean isGeometryChanged() {
            return false;
        }
    }
}
