package org.locationtech.jts.operation.buffer;

import java.util.ArrayList;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;

class OffsetSegmentString {
    private static final Coordinate[] COORDINATE_ARRAY_TYPE = new Coordinate[0];
    private double minimimVertexDistance = 0.0d;
    private PrecisionModel precisionModel = null;
    private ArrayList ptList;

    public OffsetSegmentString() {
        ArrayList arrayList;
        new ArrayList();
        this.ptList = arrayList;
    }

    public void setPrecisionModel(PrecisionModel precisionModel2) {
        PrecisionModel precisionModel3 = precisionModel2;
        this.precisionModel = precisionModel3;
    }

    public void setMinimumVertexDistance(double minimimVertexDistance2) {
        double d = minimimVertexDistance2;
        this.minimimVertexDistance = d;
    }

    public void addPt(Coordinate pt) {
        Coordinate coordinate;
        new Coordinate(pt);
        Coordinate bufPt = coordinate;
        this.precisionModel.makePrecise(bufPt);
        if (!isRedundant(bufPt)) {
            boolean add = this.ptList.add(bufPt);
        }
    }

    public void addPts(Coordinate[] coordinateArr, boolean isForward) {
        Coordinate[] pt = coordinateArr;
        if (isForward) {
            for (int i = 0; i < pt.length; i++) {
                addPt(pt[i]);
            }
            return;
        }
        for (int i2 = pt.length - 1; i2 >= 0; i2--) {
            addPt(pt[i2]);
        }
    }

    private boolean isRedundant(Coordinate coordinate) {
        Coordinate pt = coordinate;
        if (this.ptList.size() < 1) {
            return false;
        }
        if (pt.distance((Coordinate) this.ptList.get(this.ptList.size() - 1)) < this.minimimVertexDistance) {
            return true;
        }
        return false;
    }

    public void closeRing() {
        Coordinate coordinate;
        if (this.ptList.size() >= 1) {
            new Coordinate((Coordinate) this.ptList.get(0));
            Coordinate startPt = coordinate;
            Coordinate lastPt = (Coordinate) this.ptList.get(this.ptList.size() - 1);
            if (this.ptList.size() >= 2) {
                Coordinate last2Pt = (Coordinate) this.ptList.get(this.ptList.size() - 2);
            }
            if (!startPt.equals(lastPt)) {
                boolean add = this.ptList.add(startPt);
            }
        }
    }

    public void reverse() {
    }

    public Coordinate[] getCoordinates() {
        return (Coordinate[]) this.ptList.toArray(COORDINATE_ARRAY_TYPE);
    }

    public String toString() {
        GeometryFactory fact;
        new GeometryFactory();
        return fact.createLineString(getCoordinates()).toString();
    }
}
