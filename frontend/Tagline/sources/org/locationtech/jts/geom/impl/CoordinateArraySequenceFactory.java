package org.locationtech.jts.geom.impl;

import java.io.Serializable;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFactory;

public final class CoordinateArraySequenceFactory implements CoordinateSequenceFactory, Serializable {
    private static final CoordinateArraySequenceFactory instanceObject;
    private static final long serialVersionUID = -4099577099607551657L;

    static {
        CoordinateArraySequenceFactory coordinateArraySequenceFactory;
        new CoordinateArraySequenceFactory();
        instanceObject = coordinateArraySequenceFactory;
    }

    private CoordinateArraySequenceFactory() {
    }

    private Object readResolve() {
        return instance();
    }

    public static CoordinateArraySequenceFactory instance() {
        return instanceObject;
    }

    public CoordinateSequence create(Coordinate[] coordinates) {
        CoordinateSequence coordinateSequence;
        new CoordinateArraySequence(coordinates);
        return coordinateSequence;
    }

    public CoordinateSequence create(CoordinateSequence coordSeq) {
        CoordinateSequence coordinateSequence;
        new CoordinateArraySequence(coordSeq);
        return coordinateSequence;
    }

    public CoordinateSequence create(int i, int i2) {
        CoordinateSequence coordinateSequence;
        CoordinateSequence coordinateSequence2;
        int size = i;
        int dimension = i2;
        if (dimension > 3) {
            dimension = 3;
        }
        if (dimension < 2) {
            new CoordinateArraySequence(size);
            return coordinateSequence2;
        }
        new CoordinateArraySequence(size, dimension);
        return coordinateSequence;
    }
}
