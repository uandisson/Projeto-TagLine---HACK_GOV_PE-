package org.locationtech.jts.noding;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;

public class OrientedCoordinateArray implements Comparable {
    private boolean orientation;
    private Coordinate[] pts;

    public OrientedCoordinateArray(Coordinate[] coordinateArr) {
        Coordinate[] pts2 = coordinateArr;
        this.pts = pts2;
        this.orientation = orientation(pts2);
    }

    private static boolean orientation(Coordinate[] pts2) {
        return CoordinateArrays.increasingDirection(pts2) == 1;
    }

    public int compareTo(Object o1) {
        OrientedCoordinateArray oca = (OrientedCoordinateArray) o1;
        return compareOriented(this.pts, this.orientation, oca.pts, oca.orientation);
    }

    private static int compareOriented(Coordinate[] coordinateArr, boolean z, Coordinate[] coordinateArr2, boolean z2) {
        Coordinate[] pts1 = coordinateArr;
        boolean orientation1 = z;
        Coordinate[] pts2 = coordinateArr2;
        boolean orientation2 = z2;
        int dir1 = orientation1 ? 1 : -1;
        int dir2 = orientation2 ? 1 : -1;
        int limit1 = orientation1 ? pts1.length : -1;
        int limit2 = orientation2 ? pts2.length : -1;
        int i1 = orientation1 ? 0 : pts1.length - 1;
        int i2 = orientation2 ? 0 : pts2.length - 1;
        while (true) {
            int compPt = pts1[i1].compareTo(pts2[i2]);
            if (compPt != 0) {
                return compPt;
            }
            i1 += dir1;
            i2 += dir2;
            boolean done1 = i1 == limit1;
            boolean done2 = i2 == limit2;
            if (done1 && !done2) {
                return -1;
            }
            if (!done1 && done2) {
                return 1;
            }
            if (done1 && done2) {
                return 0;
            }
        }
    }
}
