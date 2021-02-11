package org.locationtech.jts.precision;

import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.index.strtree.ItemBoundable;
import org.locationtech.jts.index.strtree.ItemDistance;
import org.locationtech.jts.operation.distance.FacetSequence;
import org.locationtech.jts.operation.distance.FacetSequenceTreeBuilder;

public class MinimumClearance {
    private Geometry inputGeom;
    private double minClearance;
    private Coordinate[] minClearancePts;

    public static double getDistance(Geometry g) {
        MinimumClearance rp;
        new MinimumClearance(g);
        return rp.getDistance();
    }

    public static Geometry getLine(Geometry g) {
        MinimumClearance rp;
        new MinimumClearance(g);
        return rp.getLine();
    }

    public MinimumClearance(Geometry geom) {
        this.inputGeom = geom;
    }

    public double getDistance() {
        compute();
        return this.minClearance;
    }

    public LineString getLine() {
        compute();
        if (this.minClearancePts == null || this.minClearancePts[0] == null) {
            return this.inputGeom.getFactory().createLineString((Coordinate[]) null);
        }
        return this.inputGeom.getFactory().createLineString(this.minClearancePts);
    }

    private void compute() {
        ItemDistance itemDistance;
        MinClearanceDistance minClearanceDistance;
        if (this.minClearancePts == null) {
            this.minClearancePts = new Coordinate[2];
            this.minClearance = Double.MAX_VALUE;
            if (!this.inputGeom.isEmpty()) {
                new MinClearanceDistance((C15731) null);
                Object[] nearest = FacetSequenceTreeBuilder.build(this.inputGeom).nearestNeighbour(itemDistance);
                new MinClearanceDistance((C15731) null);
                MinClearanceDistance mcd = minClearanceDistance;
                this.minClearance = mcd.distance((FacetSequence) nearest[0], (FacetSequence) nearest[1]);
                this.minClearancePts = mcd.getCoordinates();
            }
        }
    }

    private static class MinClearanceDistance implements ItemDistance {
        private double minDist;
        private Coordinate[] minPts;

        private MinClearanceDistance() {
            this.minDist = Double.MAX_VALUE;
            this.minPts = new Coordinate[2];
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ MinClearanceDistance(C15731 r4) {
            this();
            C15731 r1 = r4;
        }

        public Coordinate[] getCoordinates() {
            return this.minPts;
        }

        public double distance(ItemBoundable b1, ItemBoundable b2) {
            FacetSequence fs1 = (FacetSequence) b1.getItem();
            FacetSequence fs2 = (FacetSequence) b2.getItem();
            this.minDist = Double.MAX_VALUE;
            return distance(fs1, fs2);
        }

        public double distance(FacetSequence facetSequence, FacetSequence facetSequence2) {
            FacetSequence fs1 = facetSequence;
            FacetSequence fs2 = facetSequence2;
            double vertexDistance = vertexDistance(fs1, fs2);
            if (fs1.size() == 1 && fs2.size() == 1) {
                return this.minDist;
            }
            if (this.minDist <= 0.0d) {
                return this.minDist;
            }
            double segmentDistance = segmentDistance(fs1, fs2);
            if (this.minDist <= 0.0d) {
                return this.minDist;
            }
            double segmentDistance2 = segmentDistance(fs2, fs1);
            return this.minDist;
        }

        private double vertexDistance(FacetSequence facetSequence, FacetSequence facetSequence2) {
            FacetSequence fs1 = facetSequence;
            FacetSequence fs2 = facetSequence2;
            for (int i1 = 0; i1 < fs1.size(); i1++) {
                for (int i2 = 0; i2 < fs2.size(); i2++) {
                    Coordinate p1 = fs1.getCoordinate(i1);
                    Coordinate p2 = fs2.getCoordinate(i2);
                    if (!p1.equals2D(p2)) {
                        double d = p1.distance(p2);
                        if (d < this.minDist) {
                            this.minDist = d;
                            this.minPts[0] = p1;
                            this.minPts[1] = p2;
                            if (d == 0.0d) {
                                return d;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            return this.minDist;
        }

        private double segmentDistance(FacetSequence facetSequence, FacetSequence facetSequence2) {
            FacetSequence fs1 = facetSequence;
            FacetSequence fs2 = facetSequence2;
            for (int i1 = 0; i1 < fs1.size(); i1++) {
                for (int i2 = 1; i2 < fs2.size(); i2++) {
                    Coordinate p = fs1.getCoordinate(i1);
                    Coordinate seg0 = fs2.getCoordinate(i2 - 1);
                    Coordinate seg1 = fs2.getCoordinate(i2);
                    if (!p.equals2D(seg0) && !p.equals2D(seg1)) {
                        double d = CGAlgorithms.distancePointLine(p, seg0, seg1);
                        if (d < this.minDist) {
                            this.minDist = d;
                            updatePts(p, seg0, seg1);
                            if (d == 0.0d) {
                                return d;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            return this.minDist;
        }

        private void updatePts(Coordinate coordinate, Coordinate seg0, Coordinate seg1) {
            LineSegment seg;
            Coordinate coordinate2;
            Coordinate p = coordinate;
            this.minPts[0] = p;
            new LineSegment(seg0, seg1);
            new Coordinate(seg.closestPoint(p));
            this.minPts[1] = coordinate2;
        }
    }
}
