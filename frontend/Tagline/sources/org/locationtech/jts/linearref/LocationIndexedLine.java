package org.locationtech.jts.linearref;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;

public class LocationIndexedLine {
    private Geometry linearGeom;

    public LocationIndexedLine(Geometry linearGeom2) {
        this.linearGeom = linearGeom2;
        checkGeometryType();
    }

    private void checkGeometryType() {
        Throwable th;
        if (!(this.linearGeom instanceof LineString) && !(this.linearGeom instanceof MultiLineString)) {
            Throwable th2 = th;
            new IllegalArgumentException("Input geometry must be linear");
            throw th2;
        }
    }

    public Coordinate extractPoint(LinearLocation index) {
        return index.getCoordinate(this.linearGeom);
    }

    public Coordinate extractPoint(LinearLocation index, double offsetDistance) {
        LinearLocation indexLow = index.toLowest(this.linearGeom);
        return indexLow.getSegment(this.linearGeom).pointAlongOffset(indexLow.getSegmentFraction(), offsetDistance);
    }

    public Geometry extractLine(LinearLocation startIndex, LinearLocation endIndex) {
        return ExtractLineByLocation.extract(this.linearGeom, startIndex, endIndex);
    }

    public LinearLocation indexOf(Coordinate pt) {
        return LocationIndexOfPoint.indexOf(this.linearGeom, pt);
    }

    public LinearLocation indexOfAfter(Coordinate pt, LinearLocation minIndex) {
        return LocationIndexOfPoint.indexOfAfter(this.linearGeom, pt, minIndex);
    }

    public LinearLocation[] indicesOf(Geometry subLine) {
        return LocationIndexOfLine.indicesOf(this.linearGeom, subLine);
    }

    public LinearLocation project(Coordinate pt) {
        return LocationIndexOfPoint.indexOf(this.linearGeom, pt);
    }

    public LinearLocation getStartIndex() {
        LinearLocation linearLocation;
        new LinearLocation();
        return linearLocation;
    }

    public LinearLocation getEndIndex() {
        return LinearLocation.getEndLocation(this.linearGeom);
    }

    public boolean isValidIndex(LinearLocation index) {
        return index.isValid(this.linearGeom);
    }

    public LinearLocation clampIndex(LinearLocation index) {
        LinearLocation loc = (LinearLocation) index.clone();
        loc.clamp(this.linearGeom);
        return loc;
    }
}
