package org.locationtech.jts.linearref;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.util.Assert;

class ExtractLineByLocation {
    private Geometry line;

    public static Geometry extract(Geometry line2, LinearLocation start, LinearLocation end) {
        ExtractLineByLocation ls;
        new ExtractLineByLocation(line2);
        return ls.extract(start, end);
    }

    public ExtractLineByLocation(Geometry line2) {
        this.line = line2;
    }

    public Geometry extract(LinearLocation linearLocation, LinearLocation linearLocation2) {
        LinearLocation start = linearLocation;
        LinearLocation end = linearLocation2;
        if (end.compareTo(start) < 0) {
            return reverse(computeLinear(end, start));
        }
        return computeLinear(start, end);
    }

    private Geometry reverse(Geometry geometry) {
        Geometry linear = geometry;
        if (linear instanceof LineString) {
            return ((LineString) linear).reverse();
        }
        if (linear instanceof MultiLineString) {
            return ((MultiLineString) linear).reverse();
        }
        Assert.shouldNeverReachHere("non-linear geometry encountered");
        return null;
    }

    private LineString computeLine(LinearLocation linearLocation, LinearLocation linearLocation2) {
        CoordinateList coordinateList;
        LinearLocation start = linearLocation;
        LinearLocation end = linearLocation2;
        Coordinate[] coordinates = this.line.getCoordinates();
        new CoordinateList();
        CoordinateList newCoordinates = coordinateList;
        int startSegmentIndex = start.getSegmentIndex();
        if (start.getSegmentFraction() > 0.0d) {
            startSegmentIndex++;
        }
        int lastSegmentIndex = end.getSegmentIndex();
        if (end.getSegmentFraction() == 1.0d) {
            lastSegmentIndex++;
        }
        if (lastSegmentIndex >= coordinates.length) {
            lastSegmentIndex = coordinates.length - 1;
        }
        if (!start.isVertex()) {
            boolean add = newCoordinates.add(start.getCoordinate(this.line));
        }
        for (int i = startSegmentIndex; i <= lastSegmentIndex; i++) {
            boolean add2 = newCoordinates.add(coordinates[i]);
        }
        if (!end.isVertex()) {
            boolean add3 = newCoordinates.add(end.getCoordinate(this.line));
        }
        if (newCoordinates.size() <= 0) {
            boolean add4 = newCoordinates.add(start.getCoordinate(this.line));
        }
        Coordinate[] newCoordinateArray = newCoordinates.toCoordinateArray();
        if (newCoordinateArray.length <= 1) {
            Coordinate[] coordinateArr = new Coordinate[2];
            coordinateArr[0] = newCoordinateArray[0];
            Coordinate[] coordinateArr2 = coordinateArr;
            coordinateArr2[1] = newCoordinateArray[0];
            newCoordinateArray = coordinateArr2;
        }
        return this.line.getFactory().createLineString(newCoordinateArray);
    }

    private Geometry computeLinear(LinearLocation linearLocation, LinearLocation linearLocation2) {
        LinearGeometryBuilder linearGeometryBuilder;
        LinearIterator linearIterator;
        LinearLocation start = linearLocation;
        LinearLocation end = linearLocation2;
        new LinearGeometryBuilder(this.line.getFactory());
        LinearGeometryBuilder builder = linearGeometryBuilder;
        builder.setFixInvalidLines(true);
        if (!start.isVertex()) {
            builder.add(start.getCoordinate(this.line));
        }
        new LinearIterator(this.line, start);
        LinearIterator it = linearIterator;
        while (it.hasNext() && end.compareLocationValues(it.getComponentIndex(), it.getVertexIndex(), 0.0d) >= 0) {
            builder.add(it.getSegmentStart());
            if (it.isEndOfLine()) {
                builder.endLine();
            }
            it.next();
        }
        if (!end.isVertex()) {
            builder.add(end.getCoordinate(this.line));
        }
        return builder.getGeometry();
    }
}
