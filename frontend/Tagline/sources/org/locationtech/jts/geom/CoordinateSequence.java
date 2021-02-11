package org.locationtech.jts.geom;

public interface CoordinateSequence extends Cloneable {

    /* renamed from: M */
    public static final int f415M = 3;

    /* renamed from: X */
    public static final int f416X = 0;

    /* renamed from: Y */
    public static final int f417Y = 1;

    /* renamed from: Z */
    public static final int f418Z = 2;

    Object clone();

    Envelope expandEnvelope(Envelope envelope);

    Coordinate getCoordinate(int i);

    void getCoordinate(int i, Coordinate coordinate);

    Coordinate getCoordinateCopy(int i);

    int getDimension();

    double getOrdinate(int i, int i2);

    double getX(int i);

    double getY(int i);

    void setOrdinate(int i, int i2, double d);

    int size();

    Coordinate[] toCoordinateArray();
}
