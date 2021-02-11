package org.locationtech.jts.linearref;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.util.Assert;

class LocationIndexOfPoint {
    private Geometry linearGeom;

    public static LinearLocation indexOf(Geometry linearGeom2, Coordinate inputPt) {
        LocationIndexOfPoint locater;
        new LocationIndexOfPoint(linearGeom2);
        return locater.indexOf(inputPt);
    }

    public static LinearLocation indexOfAfter(Geometry linearGeom2, Coordinate inputPt, LinearLocation minIndex) {
        LocationIndexOfPoint locater;
        new LocationIndexOfPoint(linearGeom2);
        return locater.indexOfAfter(inputPt, minIndex);
    }

    public LocationIndexOfPoint(Geometry linearGeom2) {
        this.linearGeom = linearGeom2;
    }

    public LinearLocation indexOf(Coordinate inputPt) {
        return indexOfFromStart(inputPt, (LinearLocation) null);
    }

    public LinearLocation indexOfAfter(Coordinate coordinate, LinearLocation linearLocation) {
        Coordinate inputPt = coordinate;
        LinearLocation minIndex = linearLocation;
        if (minIndex == null) {
            return indexOf(inputPt);
        }
        LinearLocation endLoc = LinearLocation.getEndLocation(this.linearGeom);
        if (endLoc.compareTo(minIndex) <= 0) {
            return endLoc;
        }
        LinearLocation closestAfter = indexOfFromStart(inputPt, minIndex);
        Assert.isTrue(closestAfter.compareTo(minIndex) >= 0, "computed location is before specified minimum location");
        return closestAfter;
    }

    private LinearLocation indexOfFromStart(Coordinate coordinate, LinearLocation linearLocation) {
        LineSegment lineSegment;
        LinearIterator linearIterator;
        LinearLocation loc;
        LinearLocation linearLocation2;
        Coordinate inputPt = coordinate;
        LinearLocation minIndex = linearLocation;
        double minDistance = Double.MAX_VALUE;
        int minComponentIndex = 0;
        int minSegmentIndex = 0;
        double minFrac = -1.0d;
        new LineSegment();
        LineSegment seg = lineSegment;
        new LinearIterator(this.linearGeom);
        LinearIterator it = linearIterator;
        while (it.hasNext()) {
            if (!it.isEndOfLine()) {
                seg.f422p0 = it.getSegmentStart();
                seg.f423p1 = it.getSegmentEnd();
                double segDistance = seg.distance(inputPt);
                double segFrac = seg.segmentFraction(inputPt);
                int candidateComponentIndex = it.getComponentIndex();
                int candidateSegmentIndex = it.getVertexIndex();
                if (segDistance < minDistance && (minIndex == null || minIndex.compareLocationValues(candidateComponentIndex, candidateSegmentIndex, segFrac) < 0)) {
                    minComponentIndex = candidateComponentIndex;
                    minSegmentIndex = candidateSegmentIndex;
                    minFrac = segFrac;
                    minDistance = segDistance;
                }
            }
            it.next();
        }
        if (minDistance == Double.MAX_VALUE) {
            new LinearLocation(minIndex);
            return linearLocation2;
        }
        new LinearLocation(minComponentIndex, minSegmentIndex, minFrac);
        return loc;
    }
}
