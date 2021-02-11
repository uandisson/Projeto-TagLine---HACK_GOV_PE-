package org.locationtech.jts.operation.buffer;

import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;

public class BufferInputLineSimplifier {
    private static final int DELETE = 1;
    private static final int INIT = 0;
    private static final int KEEP = 1;
    private static final int NUM_PTS_TO_CHECK = 10;
    private int angleOrientation = 1;
    private double distanceTol;
    private Coordinate[] inputLine;
    private byte[] isDeleted;

    public static Coordinate[] simplify(Coordinate[] inputLine2, double distanceTol2) {
        BufferInputLineSimplifier simp;
        new BufferInputLineSimplifier(inputLine2);
        return simp.simplify(distanceTol2);
    }

    public BufferInputLineSimplifier(Coordinate[] inputLine2) {
        this.inputLine = inputLine2;
    }

    public Coordinate[] simplify(double d) {
        double distanceTol2 = d;
        this.distanceTol = Math.abs(distanceTol2);
        if (distanceTol2 < 0.0d) {
            this.angleOrientation = -1;
        }
        this.isDeleted = new byte[this.inputLine.length];
        do {
        } while (deleteShallowConcavities());
        return collapseLine();
    }

    private boolean deleteShallowConcavities() {
        int i;
        int index = 1;
        int length = this.inputLine.length - 1;
        int midIndex = findNextNonDeletedIndex(1);
        int lastIndex = findNextNonDeletedIndex(midIndex);
        boolean isChanged = false;
        while (lastIndex < this.inputLine.length) {
            boolean isMiddleVertexDeleted = false;
            if (isDeletable(index, midIndex, lastIndex, this.distanceTol)) {
                this.isDeleted[midIndex] = 1;
                isMiddleVertexDeleted = true;
                isChanged = true;
            }
            if (isMiddleVertexDeleted) {
                i = lastIndex;
            } else {
                i = midIndex;
            }
            index = i;
            midIndex = findNextNonDeletedIndex(index);
            lastIndex = findNextNonDeletedIndex(midIndex);
        }
        return isChanged;
    }

    private int findNextNonDeletedIndex(int index) {
        int next = index + 1;
        while (next < this.inputLine.length && this.isDeleted[next] == 1) {
            next++;
        }
        return next;
    }

    private Coordinate[] collapseLine() {
        CoordinateList coordinateList;
        new CoordinateList();
        CoordinateList coordList = coordinateList;
        for (int i = 0; i < this.inputLine.length; i++) {
            if (this.isDeleted[i] != 1) {
                boolean add = coordList.add(this.inputLine[i]);
            }
        }
        return coordList.toCoordinateArray();
    }

    private boolean isDeletable(int i, int i1, int i2, double d) {
        int i0 = i;
        int i22 = i2;
        double distanceTol2 = d;
        Coordinate p0 = this.inputLine[i0];
        Coordinate p1 = this.inputLine[i1];
        Coordinate p2 = this.inputLine[i22];
        if (!isConcave(p0, p1, p2)) {
            return false;
        }
        if (!isShallow(p0, p1, p2, distanceTol2)) {
            return false;
        }
        return isShallowSampled(p0, p1, i0, i22, distanceTol2);
    }

    private boolean isShallowConcavity(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, double d) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        Coordinate p2 = coordinate3;
        double distanceTol2 = d;
        if (!(CGAlgorithms.computeOrientation(p0, p1, p2) == this.angleOrientation)) {
            return false;
        }
        return CGAlgorithms.distancePointLine(p1, p0, p2) < distanceTol2;
    }

    private boolean isShallowSampled(Coordinate coordinate, Coordinate coordinate2, int i, int i2, double d) {
        Coordinate p0 = coordinate;
        Coordinate p2 = coordinate2;
        int i0 = i;
        int i22 = i2;
        double distanceTol2 = d;
        int inc = (i22 - i0) / 10;
        if (inc <= 0) {
            inc = 1;
        }
        int i3 = i0;
        while (true) {
            int i4 = i3;
            if (i4 >= i22) {
                return true;
            }
            if (!isShallow(p0, p2, this.inputLine[i4], distanceTol2)) {
                return false;
            }
            i3 = i4 + inc;
        }
    }

    private boolean isShallow(Coordinate p0, Coordinate p1, Coordinate p2, double distanceTol2) {
        return CGAlgorithms.distancePointLine(p1, p0, p2) < distanceTol2;
    }

    private boolean isConcave(Coordinate p0, Coordinate p1, Coordinate p2) {
        return CGAlgorithms.computeOrientation(p0, p1, p2) == this.angleOrientation;
    }
}
