package org.locationtech.jts.geom.impl;

import java.lang.ref.SoftReference;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequences;
import org.locationtech.jts.geom.Envelope;

public abstract class PackedCoordinateSequence implements CoordinateSequence {
    protected SoftReference coordRef;
    protected int dimension;

    public abstract Object clone();

    /* access modifiers changed from: protected */
    public abstract Coordinate getCoordinateInternal(int i);

    public abstract double getOrdinate(int i, int i2);

    public abstract void setOrdinate(int i, int i2, double d);

    public PackedCoordinateSequence() {
    }

    public int getDimension() {
        return this.dimension;
    }

    public Coordinate getCoordinate(int i) {
        int i2 = i;
        Coordinate[] coords = getCachedCoords();
        if (coords != null) {
            return coords[i2];
        }
        return getCoordinateInternal(i2);
    }

    public Coordinate getCoordinateCopy(int i) {
        return getCoordinateInternal(i);
    }

    public void getCoordinate(int i, Coordinate coordinate) {
        int i2 = i;
        Coordinate coord = coordinate;
        coord.f412x = getOrdinate(i2, 0);
        coord.f413y = getOrdinate(i2, 1);
        if (this.dimension > 2) {
            coord.f414z = getOrdinate(i2, 2);
        }
    }

    public Coordinate[] toCoordinateArray() {
        SoftReference softReference;
        Coordinate[] coords = getCachedCoords();
        if (coords != null) {
            return coords;
        }
        Coordinate[] coords2 = new Coordinate[size()];
        for (int i = 0; i < coords2.length; i++) {
            coords2[i] = getCoordinateInternal(i);
        }
        new SoftReference(coords2);
        this.coordRef = softReference;
        return coords2;
    }

    private Coordinate[] getCachedCoords() {
        if (this.coordRef == null) {
            return null;
        }
        Coordinate[] coords = (Coordinate[]) this.coordRef.get();
        if (coords != null) {
            return coords;
        }
        this.coordRef = null;
        return null;
    }

    public double getX(int index) {
        return getOrdinate(index, 0);
    }

    public double getY(int index) {
        return getOrdinate(index, 1);
    }

    public void setX(int index, double value) {
        this.coordRef = null;
        setOrdinate(index, 0, value);
    }

    public void setY(int index, double value) {
        this.coordRef = null;
        setOrdinate(index, 1, value);
    }

    public String toString() {
        return CoordinateSequences.toString(this);
    }

    public static class Double extends PackedCoordinateSequence {
        double[] coords;

        public Double(double[] dArr, int i) {
            Throwable th;
            Throwable th2;
            double[] coords2 = dArr;
            int dimensions = i;
            if (dimensions < 2) {
                Throwable th3 = th2;
                new IllegalArgumentException("Must have at least 2 dimensions");
                throw th3;
            } else if (coords2.length % dimensions != 0) {
                Throwable th4 = th;
                new IllegalArgumentException("Packed array does not contain an integral number of coordinates");
                throw th4;
            } else {
                this.dimension = dimensions;
                this.coords = coords2;
            }
        }

        public Double(float[] fArr, int dimensions) {
            float[] coordinates = fArr;
            this.coords = new double[coordinates.length];
            this.dimension = dimensions;
            for (int i = 0; i < coordinates.length; i++) {
                this.coords[i] = (double) coordinates[i];
            }
        }

        public Double(Coordinate[] coordinateArr, int i) {
            Coordinate[] coordinates = coordinateArr;
            int dimension = i;
            coordinates = coordinates == null ? new Coordinate[0] : coordinates;
            this.dimension = dimension;
            this.coords = new double[(coordinates.length * this.dimension)];
            for (int i2 = 0; i2 < coordinates.length; i2++) {
                this.coords[i2 * this.dimension] = coordinates[i2].f412x;
                if (this.dimension >= 2) {
                    this.coords[(i2 * this.dimension) + 1] = coordinates[i2].f413y;
                }
                if (this.dimension >= 3) {
                    this.coords[(i2 * this.dimension) + 2] = coordinates[i2].f414z;
                }
            }
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public Double(Coordinate[] coordinates) {
            this(coordinates, 3);
        }

        public Double(int size, int dimension) {
            this.dimension = dimension;
            this.coords = new double[(size * this.dimension)];
        }

        public Coordinate getCoordinateInternal(int i) {
            Coordinate coordinate;
            int i2 = i;
            new Coordinate(this.coords[i2 * this.dimension], this.coords[(i2 * this.dimension) + 1], this.dimension == 2 ? Double.NaN : this.coords[(i2 * this.dimension) + 2]);
            return coordinate;
        }

        public double[] getRawCoordinates() {
            return this.coords;
        }

        public int size() {
            return this.coords.length / this.dimension;
        }

        public Object clone() {
            Double doubleR;
            double[] clone = new double[this.coords.length];
            System.arraycopy(this.coords, 0, clone, 0, this.coords.length);
            new Double(clone, this.dimension);
            return doubleR;
        }

        public double getOrdinate(int index, int ordinate) {
            return this.coords[(index * this.dimension) + ordinate];
        }

        public void setOrdinate(int index, int ordinate, double value) {
            this.coordRef = null;
            this.coords[(index * this.dimension) + ordinate] = value;
        }

        public Envelope expandEnvelope(Envelope envelope) {
            Envelope env = envelope;
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= this.coords.length) {
                    return env;
                }
                env.expandToInclude(this.coords[i2], this.coords[i2 + 1]);
                i = i2 + this.dimension;
            }
        }
    }

    public static class Float extends PackedCoordinateSequence {
        float[] coords;

        public Float(float[] fArr, int i) {
            Throwable th;
            Throwable th2;
            float[] coords2 = fArr;
            int dimensions = i;
            if (dimensions < 2) {
                Throwable th3 = th2;
                new IllegalArgumentException("Must have at least 2 dimensions");
                throw th3;
            } else if (coords2.length % dimensions != 0) {
                Throwable th4 = th;
                new IllegalArgumentException("Packed array does not contain an integral number of coordinates");
                throw th4;
            } else {
                this.dimension = dimensions;
                this.coords = coords2;
            }
        }

        public Float(double[] dArr, int dimension) {
            double[] coordinates = dArr;
            this.coords = new float[coordinates.length];
            this.dimension = dimension;
            for (int i = 0; i < coordinates.length; i++) {
                this.coords[i] = (float) coordinates[i];
            }
        }

        public Float(Coordinate[] coordinateArr, int i) {
            Coordinate[] coordinates = coordinateArr;
            int dimension = i;
            coordinates = coordinates == null ? new Coordinate[0] : coordinates;
            this.dimension = dimension;
            this.coords = new float[(coordinates.length * this.dimension)];
            for (int i2 = 0; i2 < coordinates.length; i2++) {
                this.coords[i2 * this.dimension] = (float) coordinates[i2].f412x;
                if (this.dimension >= 2) {
                    this.coords[(i2 * this.dimension) + 1] = (float) coordinates[i2].f413y;
                }
                if (this.dimension >= 3) {
                    this.coords[(i2 * this.dimension) + 2] = (float) coordinates[i2].f414z;
                }
            }
        }

        public Float(int size, int dimension) {
            this.dimension = dimension;
            this.coords = new float[(size * this.dimension)];
        }

        public Coordinate getCoordinateInternal(int i) {
            Coordinate coordinate;
            int i2 = i;
            new Coordinate((double) this.coords[i2 * this.dimension], (double) this.coords[(i2 * this.dimension) + 1], this.dimension == 2 ? Double.NaN : (double) this.coords[(i2 * this.dimension) + 2]);
            return coordinate;
        }

        public float[] getRawCoordinates() {
            return this.coords;
        }

        public int size() {
            return this.coords.length / this.dimension;
        }

        public Object clone() {
            Float floatR;
            float[] clone = new float[this.coords.length];
            System.arraycopy(this.coords, 0, clone, 0, this.coords.length);
            new Float(clone, this.dimension);
            return floatR;
        }

        public double getOrdinate(int index, int ordinate) {
            return (double) this.coords[(index * this.dimension) + ordinate];
        }

        public void setOrdinate(int index, int ordinate, double value) {
            this.coordRef = null;
            this.coords[(index * this.dimension) + ordinate] = (float) value;
        }

        public Envelope expandEnvelope(Envelope envelope) {
            Envelope env = envelope;
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= this.coords.length) {
                    return env;
                }
                env.expandToInclude((double) this.coords[i2], (double) this.coords[i2 + 1]);
                i = i2 + this.dimension;
            }
        }
    }
}
