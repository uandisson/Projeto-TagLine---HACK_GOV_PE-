package org.locationtech.jts.linearref;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;

class LocationIndexOfLine {
    private Geometry linearGeom;

    public static LinearLocation[] indicesOf(Geometry linearGeom2, Geometry subLine) {
        LocationIndexOfLine locater;
        new LocationIndexOfLine(linearGeom2);
        return locater.indicesOf(subLine);
    }

    public LocationIndexOfLine(Geometry linearGeom2) {
        this.linearGeom = linearGeom2;
    }

    public LinearLocation[] indicesOf(Geometry geometry) {
        LocationIndexOfPoint locationIndexOfPoint;
        Geometry subLine = geometry;
        Coordinate startPt = ((LineString) subLine.getGeometryN(0)).getCoordinateN(0);
        LineString lastLine = (LineString) subLine.getGeometryN(subLine.getNumGeometries() - 1);
        Coordinate endPt = lastLine.getCoordinateN(lastLine.getNumPoints() - 1);
        new LocationIndexOfPoint(this.linearGeom);
        LocationIndexOfPoint locPt = locationIndexOfPoint;
        LinearLocation[] subLineLoc = new LinearLocation[2];
        subLineLoc[0] = locPt.indexOf(startPt);
        if (subLine.getLength() == 0.0d) {
            subLineLoc[1] = (LinearLocation) subLineLoc[0].clone();
        } else {
            subLineLoc[1] = locPt.indexOfAfter(endPt, subLineLoc[0]);
        }
        return subLineLoc;
    }
}
