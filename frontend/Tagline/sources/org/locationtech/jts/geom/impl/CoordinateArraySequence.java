package org.locationtech.jts.geom.impl;

import java.io.Serializable;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;

public class CoordinateArraySequence implements CoordinateSequence, Serializable {
    private static final long serialVersionUID = -915438501601840650L;
    private Coordinate[] coordinates;
    private int dimension;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public CoordinateArraySequence(Coordinate[] coordinates2) {
        this(coordinates2, 3);
    }

    public CoordinateArraySequence(Coordinate[] coordinateArr, int dimension2) {
        Coordinate[] coordinates2 = coordinateArr;
        this.dimension = 3;
        this.coordinates = coordinates2;
        this.dimension = dimension2;
        if (coordinates2 == null) {
            this.coordinates = new Coordinate[0];
        }
    }

    public CoordinateArraySequence(int i) {
        Coordinate coordinate;
        int size = i;
        this.dimension = 3;
        this.coordinates = new Coordinate[size];
        for (int i2 = 0; i2 < size; i2++) {
            new Coordinate();
            this.coordinates[i2] = coordinate;
        }
    }

    public CoordinateArraySequence(int i, int dimension2) {
        Coordinate coordinate;
        int size = i;
        this.dimension = 3;
        this.coordinates = new Coordinate[size];
        this.dimension = dimension2;
        for (int i2 = 0; i2 < size; i2++) {
            new Coordinate();
            this.coordinates[i2] = coordinate;
        }
    }

    public CoordinateArraySequence(CoordinateSequence coordinateSequence) {
        CoordinateSequence coordSeq = coordinateSequence;
        this.dimension = 3;
        if (coordSeq == null) {
            this.coordinates = new Coordinate[0];
            return;
        }
        this.dimension = coordSeq.getDimension();
        this.coordinates = new Coordinate[coordSeq.size()];
        for (int i = 0; i < this.coordinates.length; i++) {
            this.coordinates[i] = coordSeq.getCoordinateCopy(i);
        }
    }

    public int getDimension() {
        return this.dimension;
    }

    public Coordinate getCoordinate(int i) {
        return this.coordinates[i];
    }

    public Coordinate getCoordinateCopy(int i) {
        Coordinate coordinate;
        new Coordinate(this.coordinates[i]);
        return coordinate;
    }

    public void getCoordinate(int i, Coordinate coordinate) {
        int index = i;
        Coordinate coord = coordinate;
        coord.f412x = this.coordinates[index].f412x;
        coord.f413y = this.coordinates[index].f413y;
        coord.f414z = this.coordinates[index].f414z;
    }

    public double getX(int index) {
        return this.coordinates[index].f412x;
    }

    public double getY(int index) {
        return this.coordinates[index].f413y;
    }

    public double getOrdinate(int i, int ordinateIndex) {
        int index = i;
        switch (ordinateIndex) {
            case 0:
                return this.coordinates[index].f412x;
            case 1:
                return this.coordinates[index].f413y;
            case 2:
                return this.coordinates[index].f414z;
            default:
                return Double.NaN;
        }
    }

    public Object clone() {
        CoordinateArraySequence coordinateArraySequence;
        Coordinate[] cloneCoordinates = new Coordinate[size()];
        for (int i = 0; i < this.coordinates.length; i++) {
            cloneCoordinates[i] = (Coordinate) this.coordinates[i].clone();
        }
        new CoordinateArraySequence(cloneCoordinates, this.dimension);
        return coordinateArraySequence;
    }

    public int size() {
        return this.coordinates.length;
    }

    public void setOrdinate(int i, int ordinateIndex, double d) {
        Throwable th;
        int index = i;
        double value = d;
        switch (ordinateIndex) {
            case 0:
                this.coordinates[index].f412x = value;
                return;
            case 1:
                this.coordinates[index].f413y = value;
                return;
            case 2:
                this.coordinates[index].f414z = value;
                return;
            default:
                Throwable th2 = th;
                new IllegalArgumentException("invalid ordinateIndex");
                throw th2;
        }
    }

    public Coordinate[] toCoordinateArray() {
        return this.coordinates;
    }

    public Envelope expandEnvelope(Envelope envelope) {
        Envelope env = envelope;
        for (int i = 0; i < this.coordinates.length; i++) {
            env.expandToInclude(this.coordinates[i]);
        }
        return env;
    }

    public String toString() {
        StringBuilder sb;
        if (this.coordinates.length <= 0) {
            return "()";
        }
        new StringBuilder(17 * this.coordinates.length);
        StringBuilder strBuilder = sb;
        StringBuilder append = strBuilder.append('(');
        StringBuilder append2 = strBuilder.append(this.coordinates[0]);
        for (int i = 1; i < this.coordinates.length; i++) {
            StringBuilder append3 = strBuilder.append(", ");
            StringBuilder append4 = strBuilder.append(this.coordinates[i]);
        }
        StringBuilder append5 = strBuilder.append(')');
        return strBuilder.toString();
    }
}
