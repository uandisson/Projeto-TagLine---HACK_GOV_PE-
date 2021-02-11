package org.locationtech.jts.geom;

import org.locationtech.jts.util.StringUtil;

public class CoordinateSequences {
    public CoordinateSequences() {
    }

    public static void reverse(CoordinateSequence coordinateSequence) {
        CoordinateSequence seq = coordinateSequence;
        int last = seq.size() - 1;
        int mid = last / 2;
        for (int i = 0; i <= mid; i++) {
            swap(seq, i, last - i);
        }
    }

    public static void swap(CoordinateSequence coordinateSequence, int i, int i2) {
        CoordinateSequence seq = coordinateSequence;
        int i3 = i;
        int j = i2;
        if (i3 != j) {
            for (int dim = 0; dim < seq.getDimension(); dim++) {
                double tmp = seq.getOrdinate(i3, dim);
                seq.setOrdinate(i3, dim, seq.getOrdinate(j, dim));
                seq.setOrdinate(j, dim, tmp);
            }
        }
    }

    public static void copy(CoordinateSequence coordinateSequence, int i, CoordinateSequence coordinateSequence2, int i2, int i3) {
        CoordinateSequence src = coordinateSequence;
        int srcPos = i;
        CoordinateSequence dest = coordinateSequence2;
        int destPos = i2;
        int length = i3;
        for (int i4 = 0; i4 < length; i4++) {
            copyCoord(src, srcPos + i4, dest, destPos + i4);
        }
    }

    public static void copyCoord(CoordinateSequence coordinateSequence, int i, CoordinateSequence coordinateSequence2, int i2) {
        CoordinateSequence src = coordinateSequence;
        int srcPos = i;
        CoordinateSequence dest = coordinateSequence2;
        int destPos = i2;
        int minDim = Math.min(src.getDimension(), dest.getDimension());
        for (int dim = 0; dim < minDim; dim++) {
            dest.setOrdinate(destPos, dim, src.getOrdinate(srcPos, dim));
        }
    }

    public static boolean isRing(CoordinateSequence coordinateSequence) {
        CoordinateSequence seq = coordinateSequence;
        int n = seq.size();
        if (n == 0) {
            return true;
        }
        if (n <= 3) {
            return false;
        }
        return seq.getOrdinate(0, 0) == seq.getOrdinate(n + -1, 0) && seq.getOrdinate(0, 1) == seq.getOrdinate(n + -1, 1);
    }

    public static CoordinateSequence ensureValidRing(CoordinateSequenceFactory coordinateSequenceFactory, CoordinateSequence coordinateSequence) {
        CoordinateSequenceFactory fact = coordinateSequenceFactory;
        CoordinateSequence seq = coordinateSequence;
        int n = seq.size();
        if (n == 0) {
            return seq;
        }
        if (n <= 3) {
            return createClosedRing(fact, seq, 4);
        }
        if (seq.getOrdinate(0, 0) == seq.getOrdinate(n + -1, 0) && seq.getOrdinate(0, 1) == seq.getOrdinate(n + -1, 1)) {
            return seq;
        }
        return createClosedRing(fact, seq, n + 1);
    }

    private static CoordinateSequence createClosedRing(CoordinateSequenceFactory fact, CoordinateSequence coordinateSequence, int i) {
        CoordinateSequence seq = coordinateSequence;
        int size = i;
        CoordinateSequence newseq = fact.create(size, seq.getDimension());
        int n = seq.size();
        copy(seq, 0, newseq, 0, n);
        for (int i2 = n; i2 < size; i2++) {
            copy(seq, 0, newseq, i2, 1);
        }
        return newseq;
    }

    public static CoordinateSequence extend(CoordinateSequenceFactory fact, CoordinateSequence coordinateSequence, int i) {
        CoordinateSequence seq = coordinateSequence;
        int size = i;
        CoordinateSequence newseq = fact.create(size, seq.getDimension());
        int n = seq.size();
        copy(seq, 0, newseq, 0, n);
        if (n > 0) {
            for (int i2 = n; i2 < size; i2++) {
                copy(seq, n - 1, newseq, i2, 1);
            }
        }
        return newseq;
    }

    public static boolean isEqual(CoordinateSequence coordinateSequence, CoordinateSequence coordinateSequence2) {
        CoordinateSequence cs1 = coordinateSequence;
        CoordinateSequence cs2 = coordinateSequence2;
        int cs1Size = cs1.size();
        if (cs1Size != cs2.size()) {
            return false;
        }
        int dim = Math.min(cs1.getDimension(), cs2.getDimension());
        for (int i = 0; i < cs1Size; i++) {
            for (int d = 0; d < dim; d++) {
                double v1 = cs1.getOrdinate(i, d);
                double v2 = cs2.getOrdinate(i, d);
                if (cs1.getOrdinate(i, d) != cs2.getOrdinate(i, d) && (!Double.isNaN(v1) || !Double.isNaN(v2))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String toString(CoordinateSequence coordinateSequence) {
        StringBuilder sb;
        CoordinateSequence cs = coordinateSequence;
        int size = cs.size();
        if (size == 0) {
            return "()";
        }
        int dim = cs.getDimension();
        new StringBuilder();
        StringBuilder builder = sb;
        StringBuilder append = builder.append('(');
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                StringBuilder append2 = builder.append(" ");
            }
            for (int d = 0; d < dim; d++) {
                if (d > 0) {
                    StringBuilder append3 = builder.append(",");
                }
                StringBuilder append4 = builder.append(StringUtil.toString(cs.getOrdinate(i, d)));
            }
        }
        StringBuilder append5 = builder.append(')');
        return builder.toString();
    }
}
