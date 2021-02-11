package org.locationtech.jts.operation.buffer.validate;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateFilter;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFilter;
import org.locationtech.jts.geom.Geometry;

public class BufferCurveMaximumDistanceFinder {
    private Geometry inputGeom;
    private PointPairDistance maxPtDist;

    public BufferCurveMaximumDistanceFinder(Geometry inputGeom2) {
        PointPairDistance pointPairDistance;
        new PointPairDistance();
        this.maxPtDist = pointPairDistance;
        this.inputGeom = inputGeom2;
    }

    public double findDistance(Geometry geometry) {
        Geometry bufferCurve = geometry;
        computeMaxVertexDistance(bufferCurve);
        computeMaxMidpointDistance(bufferCurve);
        return this.maxPtDist.getDistance();
    }

    public PointPairDistance getDistancePoints() {
        return this.maxPtDist;
    }

    private void computeMaxVertexDistance(Geometry curve) {
        MaxPointDistanceFilter maxPointDistanceFilter;
        new MaxPointDistanceFilter(this.inputGeom);
        MaxPointDistanceFilter distFilter = maxPointDistanceFilter;
        curve.apply((CoordinateFilter) distFilter);
        this.maxPtDist.setMaximum(distFilter.getMaxPointDistance());
    }

    private void computeMaxMidpointDistance(Geometry curve) {
        MaxMidpointDistanceFilter maxMidpointDistanceFilter;
        new MaxMidpointDistanceFilter(this.inputGeom);
        MaxMidpointDistanceFilter distFilter = maxMidpointDistanceFilter;
        curve.apply((CoordinateSequenceFilter) distFilter);
        this.maxPtDist.setMaximum(distFilter.getMaxPointDistance());
    }

    public static class MaxPointDistanceFilter implements CoordinateFilter {
        private Geometry geom;
        private PointPairDistance maxPtDist;
        private PointPairDistance minPtDist;

        public MaxPointDistanceFilter(Geometry geom2) {
            PointPairDistance pointPairDistance;
            PointPairDistance pointPairDistance2;
            new PointPairDistance();
            this.maxPtDist = pointPairDistance;
            new PointPairDistance();
            this.minPtDist = pointPairDistance2;
            this.geom = geom2;
        }

        public void filter(Coordinate pt) {
            this.minPtDist.initialize();
            DistanceToPointFinder.computeDistance(this.geom, pt, this.minPtDist);
            this.maxPtDist.setMaximum(this.minPtDist);
        }

        public PointPairDistance getMaxPointDistance() {
            return this.maxPtDist;
        }
    }

    public static class MaxMidpointDistanceFilter implements CoordinateSequenceFilter {
        private Geometry geom;
        private PointPairDistance maxPtDist;
        private PointPairDistance minPtDist;

        public MaxMidpointDistanceFilter(Geometry geom2) {
            PointPairDistance pointPairDistance;
            PointPairDistance pointPairDistance2;
            new PointPairDistance();
            this.maxPtDist = pointPairDistance;
            new PointPairDistance();
            this.minPtDist = pointPairDistance2;
            this.geom = geom2;
        }

        public void filter(CoordinateSequence coordinateSequence, int i) {
            Coordinate midPt;
            CoordinateSequence seq = coordinateSequence;
            int index = i;
            if (index != 0) {
                Coordinate p0 = seq.getCoordinate(index - 1);
                Coordinate p1 = seq.getCoordinate(index);
                new Coordinate((p0.f412x + p1.f412x) / 2.0d, (p0.f413y + p1.f413y) / 2.0d);
                this.minPtDist.initialize();
                DistanceToPointFinder.computeDistance(this.geom, midPt, this.minPtDist);
                this.maxPtDist.setMaximum(this.minPtDist);
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
