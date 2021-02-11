package org.locationtech.jts.algorithm.distance;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateFilter;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFilter;
import org.locationtech.jts.geom.Geometry;

public class DiscreteHausdorffDistance {
    private double densifyFrac = 0.0d;

    /* renamed from: g0 */
    private Geometry f406g0;

    /* renamed from: g1 */
    private Geometry f407g1;
    private PointPairDistance ptDist;

    public static double distance(Geometry g0, Geometry g1) {
        DiscreteHausdorffDistance dist;
        new DiscreteHausdorffDistance(g0, g1);
        return dist.distance();
    }

    public static double distance(Geometry g0, Geometry g1, double densifyFrac2) {
        DiscreteHausdorffDistance discreteHausdorffDistance;
        new DiscreteHausdorffDistance(g0, g1);
        DiscreteHausdorffDistance dist = discreteHausdorffDistance;
        dist.setDensifyFraction(densifyFrac2);
        return dist.distance();
    }

    public DiscreteHausdorffDistance(Geometry g0, Geometry g1) {
        PointPairDistance pointPairDistance;
        new PointPairDistance();
        this.ptDist = pointPairDistance;
        this.f406g0 = g0;
        this.f407g1 = g1;
    }

    public void setDensifyFraction(double d) {
        Throwable th;
        double densifyFrac2 = d;
        if (densifyFrac2 > 1.0d || densifyFrac2 <= 0.0d) {
            Throwable th2 = th;
            new IllegalArgumentException("Fraction is not in range (0.0 - 1.0]");
            throw th2;
        }
        this.densifyFrac = densifyFrac2;
    }

    public double distance() {
        compute(this.f406g0, this.f407g1);
        return this.ptDist.getDistance();
    }

    public double orientedDistance() {
        computeOrientedDistance(this.f406g0, this.f407g1, this.ptDist);
        return this.ptDist.getDistance();
    }

    public Coordinate[] getCoordinates() {
        return this.ptDist.getCoordinates();
    }

    private void compute(Geometry geometry, Geometry geometry2) {
        Geometry g0 = geometry;
        Geometry g1 = geometry2;
        computeOrientedDistance(g0, g1, this.ptDist);
        computeOrientedDistance(g1, g0, this.ptDist);
    }

    private void computeOrientedDistance(Geometry geometry, Geometry geometry2, PointPairDistance pointPairDistance) {
        MaxPointDistanceFilter maxPointDistanceFilter;
        MaxDensifiedByFractionDistanceFilter maxDensifiedByFractionDistanceFilter;
        Geometry discreteGeom = geometry;
        Geometry geom = geometry2;
        PointPairDistance ptDist2 = pointPairDistance;
        new MaxPointDistanceFilter(geom);
        MaxPointDistanceFilter distFilter = maxPointDistanceFilter;
        discreteGeom.apply((CoordinateFilter) distFilter);
        ptDist2.setMaximum(distFilter.getMaxPointDistance());
        if (this.densifyFrac > 0.0d) {
            new MaxDensifiedByFractionDistanceFilter(geom, this.densifyFrac);
            MaxDensifiedByFractionDistanceFilter fracFilter = maxDensifiedByFractionDistanceFilter;
            discreteGeom.apply((CoordinateSequenceFilter) fracFilter);
            ptDist2.setMaximum(fracFilter.getMaxPointDistance());
        }
    }

    public static class MaxPointDistanceFilter implements CoordinateFilter {
        private DistanceToPoint euclideanDist;
        private Geometry geom;
        private PointPairDistance maxPtDist;
        private PointPairDistance minPtDist;

        public MaxPointDistanceFilter(Geometry geom2) {
            PointPairDistance pointPairDistance;
            PointPairDistance pointPairDistance2;
            DistanceToPoint distanceToPoint;
            new PointPairDistance();
            this.maxPtDist = pointPairDistance;
            new PointPairDistance();
            this.minPtDist = pointPairDistance2;
            new DistanceToPoint();
            this.euclideanDist = distanceToPoint;
            this.geom = geom2;
        }

        public void filter(Coordinate pt) {
            this.minPtDist.initialize();
            DistanceToPoint.computeDistance(this.geom, pt, this.minPtDist);
            this.maxPtDist.setMaximum(this.minPtDist);
        }

        public PointPairDistance getMaxPointDistance() {
            return this.maxPtDist;
        }
    }

    public static class MaxDensifiedByFractionDistanceFilter implements CoordinateSequenceFilter {
        private Geometry geom;
        private PointPairDistance maxPtDist;
        private PointPairDistance minPtDist;
        private int numSubSegs = 0;

        public MaxDensifiedByFractionDistanceFilter(Geometry geom2, double fraction) {
            PointPairDistance pointPairDistance;
            PointPairDistance pointPairDistance2;
            new PointPairDistance();
            this.maxPtDist = pointPairDistance;
            new PointPairDistance();
            this.minPtDist = pointPairDistance2;
            this.geom = geom2;
            this.numSubSegs = (int) Math.rint(1.0d / fraction);
        }

        public void filter(CoordinateSequence coordinateSequence, int i) {
            Coordinate pt;
            CoordinateSequence seq = coordinateSequence;
            int index = i;
            if (index != 0) {
                Coordinate p0 = seq.getCoordinate(index - 1);
                Coordinate p1 = seq.getCoordinate(index);
                double delx = (p1.f412x - p0.f412x) / ((double) this.numSubSegs);
                double dely = (p1.f413y - p0.f413y) / ((double) this.numSubSegs);
                int i2 = 0;
                while (true) {
                    if (i2 < this.numSubSegs) {
                        new Coordinate(p0.f412x + (((double) i2) * delx), p0.f413y + (((double) i2) * dely));
                        this.minPtDist.initialize();
                        DistanceToPoint.computeDistance(this.geom, pt, this.minPtDist);
                        this.maxPtDist.setMaximum(this.minPtDist);
                        i2++;
                    } else {
                        return;
                    }
                }
            }
        }

        public boolean isGeometryChanged() {
            return false;
        }

        public boolean isDone() {
            return false;
        }

        public PointPairDistance getMaxPointDistance() {
            return this.maxPtDist;
        }
    }
}
