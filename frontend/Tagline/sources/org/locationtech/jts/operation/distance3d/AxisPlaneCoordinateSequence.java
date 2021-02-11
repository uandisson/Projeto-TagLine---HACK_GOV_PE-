package org.locationtech.jts.operation.distance3d;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;

public class AxisPlaneCoordinateSequence implements CoordinateSequence {
    private static final int[] XY_INDEX = {0, 1};
    private static final int[] XZ_INDEX = {0, 2};
    private static final int[] YZ_INDEX = {1, 2};
    private int[] indexMap;
    private CoordinateSequence seq;

    public static CoordinateSequence projectToXY(CoordinateSequence seq2) {
        CoordinateSequence seq3;
        new AxisPlaneCoordinateSequence(seq2, XY_INDEX);
        return seq3;
    }

    public static CoordinateSequence projectToXZ(CoordinateSequence seq2) {
        CoordinateSequence seq3;
        new AxisPlaneCoordinateSequence(seq2, XZ_INDEX);
        return seq3;
    }

    public static CoordinateSequence projectToYZ(CoordinateSequence seq2) {
        CoordinateSequence seq3;
        new AxisPlaneCoordinateSequence(seq2, YZ_INDEX);
        return seq3;
    }

    private AxisPlaneCoordinateSequence(CoordinateSequence seq2, int[] indexMap2) {
        this.seq = seq2;
        this.indexMap = indexMap2;
    }

    public int getDimension() {
        return 2;
    }

    public Coordinate getCoordinate(int i) {
        return getCoordinateCopy(i);
    }

    public Coordinate getCoordinateCopy(int i) {
        Coordinate coordinate;
        int i2 = i;
        new Coordinate(getX(i2), getY(i2), getZ(i2));
        return coordinate;
    }

    public void getCoordinate(int i, Coordinate coordinate) {
        int index = i;
        Coordinate coord = coordinate;
        coord.f412x = getOrdinate(index, 0);
        coord.f413y = getOrdinate(index, 1);
        coord.f414z = getOrdinate(index, 2);
    }

    public double getX(int index) {
        return getOrdinate(index, 0);
    }

    public double getY(int index) {
        return getOrdinate(index, 1);
    }

    public double getZ(int index) {
        return getOrdinate(index, 2);
    }

    public double getOrdinate(int i, int i2) {
        int index = i;
        int ordinateIndex = i2;
        if (ordinateIndex > 1) {
            return 0.0d;
        }
        return this.seq.getOrdinate(index, this.indexMap[ordinateIndex]);
    }

    public int size() {
        return this.seq.size();
    }

    public void setOrdinate(int i, int i2, double d) {
        Throwable th;
        int i3 = i;
        int i4 = i2;
        double d2 = d;
        Throwable th2 = th;
        new UnsupportedOperationException();
        throw th2;
    }

    public Coordinate[] toCoordinateArray() {
        Throwable th;
        Throwable th2 = th;
        new UnsupportedOperationException();
        throw th2;
    }

    public Envelope expandEnvelope(Envelope envelope) {
        Throwable th;
        Envelope envelope2 = envelope;
        Throwable th2 = th;
        new UnsupportedOperationException();
        throw th2;
    }

    public Object clone() {
        Throwable th;
        Throwable th2 = th;
        new UnsupportedOperationException();
        throw th2;
    }
}
