package org.locationtech.jts.geom;

import java.io.Serializable;

class DefaultCoordinateSequence implements CoordinateSequence, Serializable {
    private static final long serialVersionUID = -915438501601840650L;
    private Coordinate[] coordinates;

    public DefaultCoordinateSequence(Coordinate[] coordinateArr) {
        Throwable th;
        Coordinate[] coordinates2 = coordinateArr;
        if (Geometry.hasNullElements(coordinates2)) {
            Throwable th2 = th;
            new IllegalArgumentException("Null coordinate");
            throw th2;
        }
        this.coordinates = coordinates2;
    }

    public DefaultCoordinateSequence(CoordinateSequence coordinateSequence) {
        CoordinateSequence coordSeq = coordinateSequence;
        this.coordinates = new Coordinate[coordSeq.size()];
        for (int i = 0; i < this.coordinates.length; i++) {
            this.coordinates[i] = coordSeq.getCoordinateCopy(i);
        }
    }

    public DefaultCoordinateSequence(int i) {
        Coordinate coordinate;
        int size = i;
        this.coordinates = new Coordinate[size];
        for (int i2 = 0; i2 < size; i2++) {
            new Coordinate();
            this.coordinates[i2] = coordinate;
        }
    }

    public int getDimension() {
        return 3;
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

    public void setOrdinate(int i, int ordinateIndex, double d) {
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
                return;
        }
    }

    public Object clone() {
        DefaultCoordinateSequence defaultCoordinateSequence;
        Coordinate[] cloneCoordinates = new Coordinate[size()];
        for (int i = 0; i < this.coordinates.length; i++) {
            cloneCoordinates[i] = (Coordinate) this.coordinates[i].clone();
        }
        new DefaultCoordinateSequence(cloneCoordinates);
        return defaultCoordinateSequence;
    }

    public int size() {
        return this.coordinates.length;
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
