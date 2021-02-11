package org.locationtech.jts.precision;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.util.GeometryEditor;

public class PrecisionReducerCoordinateOperation extends GeometryEditor.CoordinateOperation {
    private boolean removeCollapsed = true;
    private PrecisionModel targetPM;

    public PrecisionReducerCoordinateOperation(PrecisionModel targetPM2, boolean removeCollapsed2) {
        this.targetPM = targetPM2;
        this.removeCollapsed = removeCollapsed2;
    }

    public Coordinate[] edit(Coordinate[] coordinateArr, Geometry geometry) {
        CoordinateList noRepeatedCoordList;
        Coordinate coordinate;
        Coordinate[] coordinates = coordinateArr;
        Geometry geom = geometry;
        if (coordinates.length == 0) {
            return null;
        }
        Coordinate[] reducedCoords = new Coordinate[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            new Coordinate(coordinates[i]);
            Coordinate coord = coordinate;
            this.targetPM.makePrecise(coord);
            reducedCoords[i] = coord;
        }
        new CoordinateList(reducedCoords, false);
        Coordinate[] noRepeatedCoords = noRepeatedCoordList.toCoordinateArray();
        int minLength = 0;
        if (geom instanceof LineString) {
            minLength = 2;
        }
        if (geom instanceof LinearRing) {
            minLength = 4;
        }
        Coordinate[] collapsedCoords = reducedCoords;
        if (this.removeCollapsed) {
            collapsedCoords = null;
        }
        if (noRepeatedCoords.length < minLength) {
            return collapsedCoords;
        }
        return noRepeatedCoords;
    }
}
