package org.locationtech.jts.geom;

public interface CoordinateSequenceFactory {
    CoordinateSequence create(int i, int i2);

    CoordinateSequence create(CoordinateSequence coordinateSequence);

    CoordinateSequence create(Coordinate[] coordinateArr);
}
