package org.locationtech.jts.precision;

import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFilter;
import org.locationtech.jts.geom.PrecisionModel;

public class CoordinatePrecisionReducerFilter implements CoordinateSequenceFilter {
    private PrecisionModel precModel;

    public CoordinatePrecisionReducerFilter(PrecisionModel precModel2) {
        this.precModel = precModel2;
    }

    public void filter(CoordinateSequence coordinateSequence, int i) {
        CoordinateSequence seq = coordinateSequence;
        int i2 = i;
        seq.setOrdinate(i2, 0, this.precModel.makePrecise(seq.getOrdinate(i2, 0)));
        seq.setOrdinate(i2, 1, this.precModel.makePrecise(seq.getOrdinate(i2, 1)));
    }

    public boolean isDone() {
        return false;
    }

    public boolean isGeometryChanged() {
        return true;
    }
}
