package org.locationtech.jts.geom;

import java.util.Collection;
import java.util.Comparator;
import org.locationtech.jts.math.MathUtil;

public class CoordinateArrays {
    private static final Coordinate[] coordArrayType = new Coordinate[0];

    private CoordinateArrays() {
    }

    public static boolean isRing(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        if (pts.length < 4) {
            return false;
        }
        if (!pts[0].equals2D(pts[pts.length - 1])) {
            return false;
        }
        return true;
    }

    public static Coordinate ptNotInList(Coordinate[] coordinateArr, Coordinate[] coordinateArr2) {
        Coordinate[] testPts = coordinateArr;
        Coordinate[] pts = coordinateArr2;
        for (int i = 0; i < testPts.length; i++) {
            Coordinate testPt = testPts[i];
            if (indexOf(testPt, pts) < 0) {
                return testPt;
            }
        }
        return null;
    }

    public static int compare(Coordinate[] coordinateArr, Coordinate[] coordinateArr2) {
        Coordinate[] pts1 = coordinateArr;
        Coordinate[] pts2 = coordinateArr2;
        int i = 0;
        while (i < pts1.length && i < pts2.length) {
            int compare = pts1[i].compareTo(pts2[i]);
            if (compare != 0) {
                return compare;
            }
            i++;
        }
        if (i < pts2.length) {
            return -1;
        }
        if (i < pts1.length) {
            return 1;
        }
        return 0;
    }

    public static class ForwardComparator implements Comparator {
        public ForwardComparator() {
        }

        public int compare(Object o1, Object o2) {
            return CoordinateArrays.compare((Coordinate[]) o1, (Coordinate[]) o2);
        }
    }

    public static int increasingDirection(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        for (int i = 0; i < pts.length / 2; i++) {
            int comp = pts[i].compareTo(pts[(pts.length - 1) - i]);
            if (comp != 0) {
                return comp;
            }
        }
        return 1;
    }

    /* access modifiers changed from: private */
    public static boolean isEqualReversed(Coordinate[] coordinateArr, Coordinate[] coordinateArr2) {
        Coordinate[] pts1 = coordinateArr;
        Coordinate[] pts2 = coordinateArr2;
        for (int i = 0; i < pts1.length; i++) {
            if (pts1[i].compareTo(pts2[(pts1.length - i) - 1]) != 0) {
                return false;
            }
        }
        return true;
    }

    public static class BidirectionalComparator implements Comparator {
        public BidirectionalComparator() {
        }

        public int compare(Object o1, Object o2) {
            Coordinate[] pts1 = (Coordinate[]) o1;
            Coordinate[] pts2 = (Coordinate[]) o2;
            if (pts1.length < pts2.length) {
                return -1;
            }
            if (pts1.length > pts2.length) {
                return 1;
            }
            if (pts1.length == 0) {
                return 0;
            }
            int forwardComp = CoordinateArrays.compare(pts1, pts2);
            if (CoordinateArrays.isEqualReversed(pts1, pts2)) {
                return 0;
            }
            return forwardComp;
        }

        public int OLDcompare(Object o1, Object o2) {
            Coordinate[] pts1 = (Coordinate[]) o1;
            Coordinate[] pts2 = (Coordinate[]) o2;
            if (pts1.length < pts2.length) {
                return -1;
            }
            if (pts1.length > pts2.length) {
                return 1;
            }
            if (pts1.length == 0) {
                return 0;
            }
            int dir1 = CoordinateArrays.increasingDirection(pts1);
            int dir2 = CoordinateArrays.increasingDirection(pts2);
            int i1 = dir1 > 0 ? 0 : pts1.length - 1;
            int i2 = dir2 > 0 ? 0 : pts1.length - 1;
            for (int i = 0; i < pts1.length; i++) {
                int comparePt = pts1[i1].compareTo(pts2[i2]);
                if (comparePt != 0) {
                    return comparePt;
                }
                i1 += dir1;
                i2 += dir2;
            }
            return 0;
        }
    }

    public static Coordinate[] copyDeep(Coordinate[] coordinateArr) {
        Coordinate coordinate;
        Coordinate[] coordinates = coordinateArr;
        Coordinate[] copy = new Coordinate[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            new Coordinate(coordinates[i]);
            copy[i] = coordinate;
        }
        return copy;
    }

    public static void copyDeep(Coordinate[] coordinateArr, int i, Coordinate[] coordinateArr2, int i2, int i3) {
        Coordinate coordinate;
        Coordinate[] src = coordinateArr;
        int srcStart = i;
        Coordinate[] dest = coordinateArr2;
        int destStart = i2;
        int length = i3;
        for (int i4 = 0; i4 < length; i4++) {
            new Coordinate(src[srcStart + i4]);
            dest[destStart + i4] = coordinate;
        }
    }

    public static Coordinate[] toCoordinateArray(Collection coordList) {
        return (Coordinate[]) coordList.toArray(coordArrayType);
    }

    public static boolean hasRepeatedPoints(Coordinate[] coordinateArr) {
        Coordinate[] coord = coordinateArr;
        for (int i = 1; i < coord.length; i++) {
            if (coord[i - 1].equals(coord[i])) {
                return true;
            }
        }
        return false;
    }

    public static Coordinate[] atLeastNCoordinatesOrNothing(int n, Coordinate[] coordinateArr) {
        Coordinate[] c = coordinateArr;
        return c.length >= n ? c : new Coordinate[0];
    }

    public static Coordinate[] removeRepeatedPoints(Coordinate[] coordinateArr) {
        CoordinateList coordList;
        Coordinate[] coord = coordinateArr;
        if (!hasRepeatedPoints(coord)) {
            return coord;
        }
        new CoordinateList(coord, false);
        return coordList.toCoordinateArray();
    }

    public static Coordinate[] removeNull(Coordinate[] coordinateArr) {
        Coordinate[] coord = coordinateArr;
        int nonNull = 0;
        for (int i = 0; i < coord.length; i++) {
            if (coord[i] != null) {
                nonNull++;
            }
        }
        Coordinate[] newCoord = new Coordinate[nonNull];
        if (nonNull == 0) {
            return newCoord;
        }
        int j = 0;
        for (int i2 = 0; i2 < coord.length; i2++) {
            if (coord[i2] != null) {
                int i3 = j;
                j++;
                newCoord[i3] = coord[i2];
            }
        }
        return newCoord;
    }

    public static void reverse(Coordinate[] coordinateArr) {
        Coordinate[] coord = coordinateArr;
        int last = coord.length - 1;
        int mid = last / 2;
        for (int i = 0; i <= mid; i++) {
            Coordinate tmp = coord[i];
            coord[i] = coord[last - i];
            coord[last - i] = tmp;
        }
    }

    public static boolean equals(Coordinate[] coordinateArr, Coordinate[] coordinateArr2) {
        Coordinate[] coord1 = coordinateArr;
        Coordinate[] coord2 = coordinateArr2;
        if (coord1 == coord2) {
            return true;
        }
        if (coord1 == null || coord2 == null) {
            return false;
        }
        if (coord1.length != coord2.length) {
            return false;
        }
        for (int i = 0; i < coord1.length; i++) {
            if (!coord1[i].equals(coord2[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(Coordinate[] coordinateArr, Coordinate[] coordinateArr2, Comparator comparator) {
        Coordinate[] coord1 = coordinateArr;
        Coordinate[] coord2 = coordinateArr2;
        Comparator coordinateComparator = comparator;
        if (coord1 == coord2) {
            return true;
        }
        if (coord1 == null || coord2 == null) {
            return false;
        }
        if (coord1.length != coord2.length) {
            return false;
        }
        for (int i = 0; i < coord1.length; i++) {
            if (coordinateComparator.compare(coord1[i], coord2[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    public static Coordinate minCoordinate(Coordinate[] coordinateArr) {
        Coordinate[] coordinates = coordinateArr;
        Coordinate minCoord = null;
        for (int i = 0; i < coordinates.length; i++) {
            if (minCoord == null || minCoord.compareTo(coordinates[i]) > 0) {
                minCoord = coordinates[i];
            }
        }
        return minCoord;
    }

    public static void scroll(Coordinate[] coordinateArr, Coordinate firstCoordinate) {
        Coordinate[] coordinates = coordinateArr;
        int i = indexOf(firstCoordinate, coordinates);
        if (i >= 0) {
            Coordinate[] newCoordinates = new Coordinate[coordinates.length];
            System.arraycopy(coordinates, i, newCoordinates, 0, coordinates.length - i);
            System.arraycopy(coordinates, 0, newCoordinates, coordinates.length - i, i);
            System.arraycopy(newCoordinates, 0, coordinates, 0, coordinates.length);
        }
    }

    public static int indexOf(Coordinate coordinate, Coordinate[] coordinateArr) {
        Coordinate coordinate2 = coordinate;
        Coordinate[] coordinates = coordinateArr;
        for (int i = 0; i < coordinates.length; i++) {
            if (coordinate2.equals(coordinates[i])) {
                return i;
            }
        }
        return -1;
    }

    public static Coordinate[] extract(Coordinate[] coordinateArr, int start, int end) {
        Coordinate[] pts = coordinateArr;
        int start2 = MathUtil.clamp(start, 0, pts.length);
        int end2 = MathUtil.clamp(end, -1, pts.length);
        int npts = (end2 - start2) + 1;
        if (end2 < 0) {
            npts = 0;
        }
        if (start2 >= pts.length) {
            npts = 0;
        }
        if (end2 < start2) {
            npts = 0;
        }
        Coordinate[] extractPts = new Coordinate[npts];
        if (npts == 0) {
            return extractPts;
        }
        int iPts = 0;
        for (int i = start2; i <= end2; i++) {
            int i2 = iPts;
            iPts++;
            extractPts[i2] = pts[i];
        }
        return extractPts;
    }

    public static Envelope envelope(Coordinate[] coordinateArr) {
        Envelope envelope;
        Coordinate[] coordinates = coordinateArr;
        new Envelope();
        Envelope env = envelope;
        for (int i = 0; i < coordinates.length; i++) {
            env.expandToInclude(coordinates[i]);
        }
        return env;
    }

    public static Coordinate[] intersection(Coordinate[] coordinateArr, Envelope envelope) {
        CoordinateList coordinateList;
        Coordinate[] coordinates = coordinateArr;
        Envelope env = envelope;
        new CoordinateList();
        CoordinateList coordList = coordinateList;
        for (int i = 0; i < coordinates.length; i++) {
            if (env.intersects(coordinates[i])) {
                coordList.add(coordinates[i], true);
            }
        }
        return coordList.toCoordinateArray();
    }
}
