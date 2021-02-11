package org.locationtech.jts.linearref;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

public class LengthIndexedLine {
    private Geometry linearGeom;

    public LengthIndexedLine(Geometry linearGeom2) {
        this.linearGeom = linearGeom2;
    }

    public Coordinate extractPoint(double index) {
        return LengthLocationMap.getLocation(this.linearGeom, index).getCoordinate(this.linearGeom);
    }

    public Coordinate extractPoint(double index, double offsetDistance) {
        LinearLocation locLow = LengthLocationMap.getLocation(this.linearGeom, index).toLowest(this.linearGeom);
        return locLow.getSegment(this.linearGeom).pointAlongOffset(locLow.getSegmentFraction(), offsetDistance);
    }

    public Geometry extractLine(double startIndex, double endIndex) {
        Object obj;
        new LocationIndexedLine(this.linearGeom);
        Object obj2 = obj;
        double startIndex2 = clampIndex(startIndex);
        double endIndex2 = clampIndex(endIndex);
        return ExtractLineByLocation.extract(this.linearGeom, locationOf(startIndex2, startIndex2 == endIndex2), locationOf(endIndex2));
    }

    private LinearLocation locationOf(double index) {
        return LengthLocationMap.getLocation(this.linearGeom, index);
    }

    private LinearLocation locationOf(double index, boolean resolveLower) {
        return LengthLocationMap.getLocation(this.linearGeom, index, resolveLower);
    }

    public double indexOf(Coordinate pt) {
        return LengthIndexOfPoint.indexOf(this.linearGeom, pt);
    }

    public double indexOfAfter(Coordinate pt, double minIndex) {
        return LengthIndexOfPoint.indexOfAfter(this.linearGeom, pt, minIndex);
    }

    public double[] indicesOf(Geometry subLine) {
        LinearLocation[] locIndex = LocationIndexOfLine.indicesOf(this.linearGeom, subLine);
        double[] dArr = new double[2];
        dArr[0] = LengthLocationMap.getLength(this.linearGeom, locIndex[0]);
        double[] index = dArr;
        index[1] = LengthLocationMap.getLength(this.linearGeom, locIndex[1]);
        return index;
    }

    public double project(Coordinate pt) {
        return LengthIndexOfPoint.indexOf(this.linearGeom, pt);
    }

    public double getStartIndex() {
        return 0.0d;
    }

    public double getEndIndex() {
        return this.linearGeom.getLength();
    }

    public boolean isValidIndex(double d) {
        double index = d;
        return index >= getStartIndex() && index <= getEndIndex();
    }

    public double clampIndex(double index) {
        double posIndex = positiveIndex(index);
        double startIndex = getStartIndex();
        if (posIndex < startIndex) {
            return startIndex;
        }
        double endIndex = getEndIndex();
        if (posIndex > endIndex) {
            return endIndex;
        }
        return posIndex;
    }

    private double positiveIndex(double d) {
        double index = d;
        if (index >= 0.0d) {
            return index;
        }
        return this.linearGeom.getLength() + index;
    }
}
