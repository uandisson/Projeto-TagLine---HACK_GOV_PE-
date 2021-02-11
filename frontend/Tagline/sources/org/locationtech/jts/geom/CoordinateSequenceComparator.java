package org.locationtech.jts.geom;

import java.util.Comparator;

public class CoordinateSequenceComparator implements Comparator {
    protected int dimensionLimit;

    public static int compare(double d, double d2) {
        double a = d;
        double b = d2;
        if (a < b) {
            return -1;
        }
        if (a > b) {
            return 1;
        }
        if (Double.isNaN(a)) {
            if (Double.isNaN(b)) {
                return 0;
            }
            return -1;
        } else if (Double.isNaN(b)) {
            return 1;
        } else {
            return 0;
        }
    }

    public CoordinateSequenceComparator() {
        this.dimensionLimit = Integer.MAX_VALUE;
    }

    public CoordinateSequenceComparator(int dimensionLimit2) {
        this.dimensionLimit = dimensionLimit2;
    }

    public int compare(Object o1, Object o2) {
        CoordinateSequence s1 = (CoordinateSequence) o1;
        CoordinateSequence s2 = (CoordinateSequence) o2;
        int size1 = s1.size();
        int size2 = s2.size();
        int dim1 = s1.getDimension();
        int dim2 = s2.getDimension();
        int minDim = dim1;
        if (dim2 < minDim) {
            minDim = dim2;
        }
        boolean dimLimited = false;
        if (this.dimensionLimit <= minDim) {
            minDim = this.dimensionLimit;
            dimLimited = true;
        }
        if (!dimLimited) {
            if (dim1 < dim2) {
                return -1;
            }
            if (dim1 > dim2) {
                return 1;
            }
        }
        int i = 0;
        while (i < size1 && i < size2) {
            int ptComp = compareCoordinate(s1, s2, i, minDim);
            if (ptComp != 0) {
                return ptComp;
            }
            i++;
        }
        if (i < size1) {
            return 1;
        }
        if (i < size2) {
            return -1;
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int compareCoordinate(CoordinateSequence coordinateSequence, CoordinateSequence coordinateSequence2, int i, int i2) {
        CoordinateSequence s1 = coordinateSequence;
        CoordinateSequence s2 = coordinateSequence2;
        int i3 = i;
        int dimension = i2;
        for (int d = 0; d < dimension; d++) {
            int comp = compare(s1.getOrdinate(i3, d), s2.getOrdinate(i3, d));
            if (comp != 0) {
                return comp;
            }
        }
        return 0;
    }
}
