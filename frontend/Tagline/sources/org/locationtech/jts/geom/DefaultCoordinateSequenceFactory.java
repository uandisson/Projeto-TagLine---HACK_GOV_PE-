package org.locationtech.jts.geom;

import java.io.Serializable;

public class DefaultCoordinateSequenceFactory implements CoordinateSequenceFactory, Serializable {
    private static final DefaultCoordinateSequenceFactory instanceObject;
    private static final long serialVersionUID = -4099577099607551657L;

    static {
        DefaultCoordinateSequenceFactory defaultCoordinateSequenceFactory;
        new DefaultCoordinateSequenceFactory();
        instanceObject = defaultCoordinateSequenceFactory;
    }

    public DefaultCoordinateSequenceFactory() {
    }

    private Object readResolve() {
        return instance();
    }

    public static DefaultCoordinateSequenceFactory instance() {
        return instanceObject;
    }

    public CoordinateSequence create(Coordinate[] coordinates) {
        CoordinateSequence coordinateSequence;
        new DefaultCoordinateSequence(coordinates);
        return coordinateSequence;
    }

    public CoordinateSequence create(CoordinateSequence coordSeq) {
        CoordinateSequence coordinateSequence;
        new DefaultCoordinateSequence(coordSeq);
        return coordinateSequence;
    }

    public CoordinateSequence create(int size, int i) {
        CoordinateSequence coordinateSequence;
        int i2 = i;
        new DefaultCoordinateSequence(size);
        return coordinateSequence;
    }
}
