package org.locationtech.jts.geom;

public interface CoordinateSequenceFilter {
    void filter(CoordinateSequence coordinateSequence, int i);

    boolean isDone();

    boolean isGeometryChanged();
}
