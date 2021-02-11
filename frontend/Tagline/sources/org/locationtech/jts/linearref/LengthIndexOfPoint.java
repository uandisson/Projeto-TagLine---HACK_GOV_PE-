package org.locationtech.jts.linearref;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.util.Assert;

class LengthIndexOfPoint {
    private Geometry linearGeom;

    public static double indexOf(Geometry linearGeom2, Coordinate inputPt) {
        LengthIndexOfPoint locater;
        new LengthIndexOfPoint(linearGeom2);
        return locater.indexOf(inputPt);
    }

    public static double indexOfAfter(Geometry linearGeom2, Coordinate inputPt, double minIndex) {
        LengthIndexOfPoint locater;
        new LengthIndexOfPoint(linearGeom2);
        return locater.indexOfAfter(inputPt, minIndex);
    }

    public LengthIndexOfPoint(Geometry linearGeom2) {
        this.linearGeom = linearGeom2;
    }

    public double indexOf(Coordinate inputPt) {
        return indexOfFromStart(inputPt, -1.0d);
    }

    public double indexOfAfter(Coordinate coordinate, double d) {
        Coordinate inputPt = coordinate;
        double minIndex = d;
        if (minIndex < 0.0d) {
            return indexOf(inputPt);
        }
        double endIndex = this.linearGeom.getLength();
        if (endIndex < minIndex) {
            return endIndex;
        }
        double closestAfter = indexOfFromStart(inputPt, minIndex);
        Assert.isTrue(closestAfter >= minIndex, "computed index is before specified minimum index");
        return closestAfter;
    }

    private double indexOfFromStart(Coordinate coordinate, double d) {
        LineSegment lineSegment;
        LinearIterator linearIterator;
        Coordinate inputPt = coordinate;
        double minIndex = d;
        double minDistance = Double.MAX_VALUE;
        double ptMeasure = minIndex;
        double segmentStartMeasure = 0.0d;
        new LineSegment();
        LineSegment seg = lineSegment;
        new LinearIterator(this.linearGeom);
        LinearIterator it = linearIterator;
        while (it.hasNext()) {
            if (!it.isEndOfLine()) {
                seg.f422p0 = it.getSegmentStart();
                seg.f423p1 = it.getSegmentEnd();
                double segDistance = seg.distance(inputPt);
                double segMeasureToPt = segmentNearestMeasure(seg, inputPt, segmentStartMeasure);
                if (segDistance < minDistance && segMeasureToPt > minIndex) {
                    ptMeasure = segMeasureToPt;
                    minDistance = segDistance;
                }
                segmentStartMeasure += seg.getLength();
            }
            it.next();
        }
        return ptMeasure;
    }

    private double segmentNearestMeasure(LineSegment lineSegment, Coordinate inputPt, double d) {
        LineSegment seg = lineSegment;
        double segmentStartMeasure = d;
        double projFactor = seg.projectionFactor(inputPt);
        if (projFactor <= 0.0d) {
            return segmentStartMeasure;
        }
        if (projFactor <= 1.0d) {
            return segmentStartMeasure + (projFactor * seg.getLength());
        }
        return segmentStartMeasure + seg.getLength();
    }
}
