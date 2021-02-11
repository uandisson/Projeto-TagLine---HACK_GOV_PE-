package org.locationtech.jts.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.TreeSet;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.CoordinateFilter;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.util.Assert;
import org.locationtech.jts.util.UniqueCoordinateArrayFilter;

public class ConvexHull {
    private GeometryFactory geomFactory;
    private Coordinate[] inputPts;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ConvexHull(org.locationtech.jts.geom.Geometry r6) {
        /*
            r5 = this;
            r0 = r5
            r1 = r6
            r2 = r0
            r3 = r1
            org.locationtech.jts.geom.Coordinate[] r3 = extractCoordinates(r3)
            r4 = r1
            org.locationtech.jts.geom.GeometryFactory r4 = r4.getFactory()
            r2.<init>(r3, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.algorithm.ConvexHull.<init>(org.locationtech.jts.geom.Geometry):void");
    }

    public ConvexHull(Coordinate[] pts, GeometryFactory geomFactory2) {
        this.inputPts = UniqueCoordinateArrayFilter.filterCoordinates(pts);
        this.geomFactory = geomFactory2;
    }

    private static Coordinate[] extractCoordinates(Geometry geom) {
        UniqueCoordinateArrayFilter uniqueCoordinateArrayFilter;
        new UniqueCoordinateArrayFilter();
        UniqueCoordinateArrayFilter filter = uniqueCoordinateArrayFilter;
        geom.apply((CoordinateFilter) filter);
        return filter.getCoordinates();
    }

    public Geometry getConvexHull() {
        if (this.inputPts.length == 0) {
            return this.geomFactory.createGeometryCollection((Geometry[]) null);
        }
        if (this.inputPts.length == 1) {
            return this.geomFactory.createPoint(this.inputPts[0]);
        }
        if (this.inputPts.length == 2) {
            return this.geomFactory.createLineString(this.inputPts);
        }
        Coordinate[] reducedPts = this.inputPts;
        if (this.inputPts.length > 50) {
            reducedPts = reduce(this.inputPts);
        }
        return lineOrPolygon(toCoordinateArray(grahamScan(preSort(reducedPts))));
    }

    /* access modifiers changed from: protected */
    public Coordinate[] toCoordinateArray(Stack stack) {
        Stack stack2 = stack;
        Coordinate[] coordinates = new Coordinate[stack2.size()];
        for (int i = 0; i < stack2.size(); i++) {
            coordinates[i] = (Coordinate) stack2.get(i);
        }
        return coordinates;
    }

    private Coordinate[] reduce(Coordinate[] coordinateArr) {
        TreeSet treeSet;
        Coordinate[] inputPts2 = coordinateArr;
        Coordinate[] polyPts = computeOctRing(inputPts2);
        if (polyPts == null) {
            return inputPts2;
        }
        new TreeSet();
        TreeSet reducedSet = treeSet;
        for (int i = 0; i < polyPts.length; i++) {
            boolean add = reducedSet.add(polyPts[i]);
        }
        for (int i2 = 0; i2 < inputPts2.length; i2++) {
            if (!CGAlgorithms.isPointInRing(inputPts2[i2], polyPts)) {
                boolean add2 = reducedSet.add(inputPts2[i2]);
            }
        }
        Coordinate[] reducedPts = CoordinateArrays.toCoordinateArray(reducedSet);
        if (reducedPts.length < 3) {
            return padArray3(reducedPts);
        }
        return reducedPts;
    }

    private Coordinate[] padArray3(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        Coordinate[] pad = new Coordinate[3];
        for (int i = 0; i < pad.length; i++) {
            if (i < pts.length) {
                pad[i] = pts[i];
            } else {
                pad[i] = pts[0];
            }
        }
        return pad;
    }

    private Coordinate[] preSort(Coordinate[] coordinateArr) {
        Comparator comparator;
        Coordinate[] pts = coordinateArr;
        for (int i = 1; i < pts.length; i++) {
            if (pts[i].f413y < pts[0].f413y || (pts[i].f413y == pts[0].f413y && pts[i].f412x < pts[0].f412x)) {
                Coordinate t = pts[0];
                pts[0] = pts[i];
                pts[i] = t;
            }
        }
        new RadialComparator(pts[0]);
        Arrays.sort(pts, 1, pts.length, comparator);
        return pts;
    }

    private Stack grahamScan(Coordinate[] coordinateArr) {
        Stack stack;
        Coordinate p;
        Coordinate[] c = coordinateArr;
        new Stack();
        Stack ps = stack;
        Object push = ps.push(c[0]);
        Object push2 = ps.push(c[1]);
        Object push3 = ps.push(c[2]);
        for (int i = 3; i < c.length; i++) {
            Object pop = ps.pop();
            while (true) {
                p = (Coordinate) pop;
                if (ps.empty() || CGAlgorithms.computeOrientation((Coordinate) ps.peek(), p, c[i]) <= 0) {
                    Object push4 = ps.push(p);
                    Object push5 = ps.push(c[i]);
                } else {
                    pop = ps.pop();
                }
            }
            Object push42 = ps.push(p);
            Object push52 = ps.push(c[i]);
        }
        Object push6 = ps.push(c[0]);
        return ps;
    }

    private boolean isBetween(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate c1 = coordinate;
        Coordinate c2 = coordinate2;
        Coordinate c3 = coordinate3;
        if (CGAlgorithms.computeOrientation(c1, c2, c3) != 0) {
            return false;
        }
        if (c1.f412x != c3.f412x) {
            if (c1.f412x <= c2.f412x && c2.f412x <= c3.f412x) {
                return true;
            }
            if (c3.f412x <= c2.f412x && c2.f412x <= c1.f412x) {
                return true;
            }
        }
        if (c1.f413y != c3.f413y) {
            if (c1.f413y <= c2.f413y && c2.f413y <= c3.f413y) {
                return true;
            }
            if (c3.f413y <= c2.f413y && c2.f413y <= c1.f413y) {
                return true;
            }
        }
        return false;
    }

    private Coordinate[] computeOctRing(Coordinate[] inputPts2) {
        CoordinateList coordinateList;
        Coordinate[] octPts = computeOctPts(inputPts2);
        new CoordinateList();
        CoordinateList coordList = coordinateList;
        boolean add = coordList.add(octPts, false);
        if (coordList.size() < 3) {
            return null;
        }
        coordList.closeRing();
        return coordList.toCoordinateArray();
    }

    private Coordinate[] computeOctPts(Coordinate[] coordinateArr) {
        Coordinate[] inputPts2 = coordinateArr;
        Coordinate[] pts = new Coordinate[8];
        for (int j = 0; j < pts.length; j++) {
            pts[j] = inputPts2[0];
        }
        for (int i = 1; i < inputPts2.length; i++) {
            if (inputPts2[i].f412x < pts[0].f412x) {
                pts[0] = inputPts2[i];
            }
            if (inputPts2[i].f412x - inputPts2[i].f413y < pts[1].f412x - pts[1].f413y) {
                pts[1] = inputPts2[i];
            }
            if (inputPts2[i].f413y > pts[2].f413y) {
                pts[2] = inputPts2[i];
            }
            if (inputPts2[i].f412x + inputPts2[i].f413y > pts[3].f412x + pts[3].f413y) {
                pts[3] = inputPts2[i];
            }
            if (inputPts2[i].f412x > pts[4].f412x) {
                pts[4] = inputPts2[i];
            }
            if (inputPts2[i].f412x - inputPts2[i].f413y > pts[5].f412x - pts[5].f413y) {
                pts[5] = inputPts2[i];
            }
            if (inputPts2[i].f413y < pts[6].f413y) {
                pts[6] = inputPts2[i];
            }
            if (inputPts2[i].f412x + inputPts2[i].f413y < pts[7].f412x + pts[7].f413y) {
                pts[7] = inputPts2[i];
            }
        }
        return pts;
    }

    private Geometry lineOrPolygon(Coordinate[] coordinates) {
        Coordinate[] coordinates2 = cleanRing(coordinates);
        if (coordinates2.length == 3) {
            GeometryFactory geometryFactory = this.geomFactory;
            Coordinate[] coordinateArr = new Coordinate[2];
            coordinateArr[0] = coordinates2[0];
            Coordinate[] coordinateArr2 = coordinateArr;
            coordinateArr2[1] = coordinates2[1];
            return geometryFactory.createLineString(coordinateArr2);
        }
        return this.geomFactory.createPolygon(this.geomFactory.createLinearRing(coordinates2), (LinearRing[]) null);
    }

    private Coordinate[] cleanRing(Coordinate[] coordinateArr) {
        ArrayList arrayList;
        Coordinate[] original = coordinateArr;
        Assert.equals(original[0], original[original.length - 1]);
        new ArrayList();
        ArrayList cleanedRing = arrayList;
        Coordinate previousDistinctCoordinate = null;
        for (int i = 0; i <= original.length - 2; i++) {
            Coordinate currentCoordinate = original[i];
            Coordinate nextCoordinate = original[i + 1];
            if (!currentCoordinate.equals(nextCoordinate) && (previousDistinctCoordinate == null || !isBetween(previousDistinctCoordinate, currentCoordinate, nextCoordinate))) {
                boolean add = cleanedRing.add(currentCoordinate);
                previousDistinctCoordinate = currentCoordinate;
            }
        }
        boolean add2 = cleanedRing.add(original[original.length - 1]);
        return (Coordinate[]) cleanedRing.toArray(new Coordinate[cleanedRing.size()]);
    }

    private static class RadialComparator implements Comparator {
        private Coordinate origin;

        public RadialComparator(Coordinate origin2) {
            this.origin = origin2;
        }

        public int compare(Object o1, Object o2) {
            return polarCompare(this.origin, (Coordinate) o1, (Coordinate) o2);
        }

        private static int polarCompare(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
            Coordinate o = coordinate;
            Coordinate p = coordinate2;
            Coordinate q = coordinate3;
            double dxp = p.f412x - o.f412x;
            double dyp = p.f413y - o.f413y;
            double dxq = q.f412x - o.f412x;
            double dyq = q.f413y - o.f413y;
            int orient = CGAlgorithms.computeOrientation(o, p, q);
            if (orient == 1) {
                return 1;
            }
            if (orient == -1) {
                return -1;
            }
            double op = (dxp * dxp) + (dyp * dyp);
            double oq = (dxq * dxq) + (dyq * dyq);
            if (op < oq) {
                return -1;
            }
            if (op > oq) {
                return 1;
            }
            return 0;
        }
    }
}
