package org.locationtech.jts.geom.impl;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFactory;
import org.locationtech.jts.geom.impl.PackedCoordinateSequence;

public class PackedCoordinateSequenceFactory implements CoordinateSequenceFactory {
    public static final int DOUBLE = 0;
    public static final PackedCoordinateSequenceFactory DOUBLE_FACTORY;
    public static final int FLOAT = 1;
    public static final PackedCoordinateSequenceFactory FLOAT_FACTORY;
    private int dimension;
    private int type;

    static {
        PackedCoordinateSequenceFactory packedCoordinateSequenceFactory;
        PackedCoordinateSequenceFactory packedCoordinateSequenceFactory2;
        new PackedCoordinateSequenceFactory(0);
        DOUBLE_FACTORY = packedCoordinateSequenceFactory;
        new PackedCoordinateSequenceFactory(1);
        FLOAT_FACTORY = packedCoordinateSequenceFactory2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public PackedCoordinateSequenceFactory() {
        this(0);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public PackedCoordinateSequenceFactory(int type2) {
        this(type2, 3);
    }

    public PackedCoordinateSequenceFactory(int type2, int dimension2) {
        this.type = 0;
        this.dimension = 3;
        setType(type2);
        setDimension(dimension2);
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        Throwable th;
        StringBuilder sb;
        int type2 = i;
        if (type2 == 0 || type2 == 1) {
            this.type = type2;
            return;
        }
        Throwable th2 = th;
        new StringBuilder();
        new IllegalArgumentException(sb.append("Unknown type ").append(type2).toString());
        throw th2;
    }

    public int getDimension() {
        return this.dimension;
    }

    public void setDimension(int dimension2) {
        int i = dimension2;
        this.dimension = i;
    }

    public CoordinateSequence create(Coordinate[] coordinateArr) {
        CoordinateSequence coordinateSequence;
        CoordinateSequence coordinateSequence2;
        Coordinate[] coordinates = coordinateArr;
        if (this.type == 0) {
            new PackedCoordinateSequence.Double(coordinates, this.dimension);
            return coordinateSequence2;
        }
        new PackedCoordinateSequence.Float(coordinates, this.dimension);
        return coordinateSequence;
    }

    public CoordinateSequence create(CoordinateSequence coordinateSequence) {
        CoordinateSequence coordinateSequence2;
        CoordinateSequence coordinateSequence3;
        CoordinateSequence coordSeq = coordinateSequence;
        if (this.type == 0) {
            new PackedCoordinateSequence.Double(coordSeq.toCoordinateArray(), this.dimension);
            return coordinateSequence3;
        }
        new PackedCoordinateSequence.Float(coordSeq.toCoordinateArray(), this.dimension);
        return coordinateSequence2;
    }

    public CoordinateSequence create(double[] dArr, int i) {
        CoordinateSequence coordinateSequence;
        CoordinateSequence coordinateSequence2;
        double[] packedCoordinates = dArr;
        int dimension2 = i;
        if (this.type == 0) {
            new PackedCoordinateSequence.Double(packedCoordinates, dimension2);
            return coordinateSequence2;
        }
        new PackedCoordinateSequence.Float(packedCoordinates, dimension2);
        return coordinateSequence;
    }

    public CoordinateSequence create(float[] fArr, int i) {
        CoordinateSequence coordinateSequence;
        CoordinateSequence coordinateSequence2;
        float[] packedCoordinates = fArr;
        int dimension2 = i;
        if (this.type == 0) {
            new PackedCoordinateSequence.Double(packedCoordinates, dimension2);
            return coordinateSequence2;
        }
        new PackedCoordinateSequence.Float(packedCoordinates, dimension2);
        return coordinateSequence;
    }

    public CoordinateSequence create(int i, int i2) {
        CoordinateSequence coordinateSequence;
        CoordinateSequence coordinateSequence2;
        int size = i;
        int dimension2 = i2;
        if (this.type == 0) {
            new PackedCoordinateSequence.Double(size, dimension2);
            return coordinateSequence2;
        }
        new PackedCoordinateSequence.Float(size, dimension2);
        return coordinateSequence;
    }
}
