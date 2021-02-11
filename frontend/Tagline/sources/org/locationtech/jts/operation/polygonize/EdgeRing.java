package org.locationtech.jts.operation.polygonize;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.p006io.WKTWriter;
import org.locationtech.jts.planargraph.DirectedEdge;
import org.locationtech.jts.util.Assert;

class EdgeRing {
    private List deList;
    private GeometryFactory factory;
    private List holes;
    private boolean isHole;
    private boolean isIncluded = false;
    private boolean isIncludedSet = false;
    private boolean isProcessed = false;
    private DirectedEdge lowestEdge = null;
    private LinearRing ring = null;
    private Coordinate[] ringPts = null;
    private EdgeRing shell;

    public static EdgeRing findEdgeRingContaining(EdgeRing testEr, List shellList) {
        LinearRing testRing = testEr.getRing();
        Envelope testEnv = testRing.getEnvelopeInternal();
        Coordinate coordinateN = testRing.getCoordinateN(0);
        EdgeRing minShell = null;
        Envelope minShellEnv = null;
        Iterator it = shellList.iterator();
        while (it.hasNext()) {
            EdgeRing tryShell = (EdgeRing) it.next();
            LinearRing tryShellRing = tryShell.getRing();
            Envelope tryShellEnv = tryShellRing.getEnvelopeInternal();
            if (!tryShellEnv.equals(testEnv) && tryShellEnv.contains(testEnv)) {
                boolean isContained = false;
                if (CGAlgorithms.isPointInRing(CoordinateArrays.ptNotInList(testRing.getCoordinates(), tryShellRing.getCoordinates()), tryShellRing.getCoordinates())) {
                    isContained = true;
                }
                if (isContained && (minShell == null || minShellEnv.contains(tryShellEnv))) {
                    minShell = tryShell;
                    minShellEnv = minShell.getRing().getEnvelopeInternal();
                }
            }
        }
        return minShell;
    }

    public static Coordinate ptNotInList(Coordinate[] coordinateArr, Coordinate[] coordinateArr2) {
        Coordinate[] testPts = coordinateArr;
        Coordinate[] pts = coordinateArr2;
        for (int i = 0; i < testPts.length; i++) {
            Coordinate testPt = testPts[i];
            if (!isInList(testPt, pts)) {
                return testPt;
            }
        }
        return null;
    }

    public static boolean isInList(Coordinate coordinate, Coordinate[] coordinateArr) {
        Coordinate pt = coordinate;
        Coordinate[] pts = coordinateArr;
        for (int i = 0; i < pts.length; i++) {
            if (pt.equals(pts[i])) {
                return true;
            }
        }
        return false;
    }

    public static List findDirEdgesInRing(PolygonizeDirectedEdge polygonizeDirectedEdge) {
        List list;
        PolygonizeDirectedEdge startDE = polygonizeDirectedEdge;
        PolygonizeDirectedEdge de = startDE;
        new ArrayList();
        List edges = list;
        do {
            boolean add = edges.add(de);
            de = de.getNext();
            Assert.isTrue(de != null, "found null DE in ring");
            Assert.isTrue(de == startDE || !de.isInRing(), "found DE already in ring");
        } while (de != startDE);
        return edges;
    }

    public EdgeRing(GeometryFactory factory2) {
        List list;
        new ArrayList();
        this.deList = list;
        this.factory = factory2;
    }

    public void build(PolygonizeDirectedEdge polygonizeDirectedEdge) {
        PolygonizeDirectedEdge startDE = polygonizeDirectedEdge;
        PolygonizeDirectedEdge de = startDE;
        do {
            add(de);
            de.setRing(this);
            de = de.getNext();
            Assert.isTrue(de != null, "found null DE in ring");
            Assert.isTrue(de == startDE || !de.isInRing(), "found DE already in ring");
        } while (de != startDE);
    }

    private void add(DirectedEdge de) {
        boolean add = this.deList.add(de);
    }

    public boolean isHole() {
        return this.isHole;
    }

    public void computeHole() {
        this.isHole = CGAlgorithms.isCCW(getRing().getCoordinates());
    }

    public void addHole(LinearRing linearRing) {
        List list;
        LinearRing hole = linearRing;
        if (this.holes == null) {
            new ArrayList();
            this.holes = list;
        }
        boolean add = this.holes.add(hole);
    }

    public void addHole(EdgeRing edgeRing) {
        List list;
        EdgeRing holeER = edgeRing;
        holeER.setShell(this);
        LinearRing hole = holeER.getRing();
        if (this.holes == null) {
            new ArrayList();
            this.holes = list;
        }
        boolean add = this.holes.add(hole);
    }

    public Polygon getPolygon() {
        LinearRing[] holeLR = null;
        if (this.holes != null) {
            holeLR = new LinearRing[this.holes.size()];
            for (int i = 0; i < this.holes.size(); i++) {
                holeLR[i] = (LinearRing) this.holes.get(i);
            }
        }
        return this.factory.createPolygon(this.ring, holeLR);
    }

    public boolean isValid() {
        Coordinate[] coordinates = getCoordinates();
        if (this.ringPts.length <= 3) {
            return false;
        }
        LinearRing ring2 = getRing();
        return this.ring.isValid();
    }

    public boolean isIncludedSet() {
        return this.isIncludedSet;
    }

    public boolean isIncluded() {
        return this.isIncluded;
    }

    public void setIncluded(boolean isIncluded2) {
        this.isIncluded = isIncluded2;
        this.isIncludedSet = true;
    }

    private Coordinate[] getCoordinates() {
        CoordinateList coordinateList;
        if (this.ringPts == null) {
            new CoordinateList();
            CoordinateList coordList = coordinateList;
            for (DirectedEdge de : this.deList) {
                addEdge(((PolygonizeEdge) de.getEdge()).getLine().getCoordinates(), de.getEdgeDirection(), coordList);
            }
            this.ringPts = coordList.toCoordinateArray();
        }
        return this.ringPts;
    }

    public LineString getLineString() {
        Coordinate[] coordinates = getCoordinates();
        return this.factory.createLineString(this.ringPts);
    }

    public LinearRing getRing() {
        if (this.ring != null) {
            return this.ring;
        }
        Coordinate[] coordinates = getCoordinates();
        if (this.ringPts.length < 3) {
            System.out.println(this.ringPts);
        }
        try {
            this.ring = this.factory.createLinearRing(this.ringPts);
        } catch (Exception e) {
            Exception exc = e;
            System.out.println(this.ringPts);
        }
        return this.ring;
    }

    private static void addEdge(Coordinate[] coordinateArr, boolean isForward, CoordinateList coordinateList) {
        Coordinate[] coords = coordinateArr;
        CoordinateList coordList = coordinateList;
        if (isForward) {
            for (int i = 0; i < coords.length; i++) {
                coordList.add(coords[i], false);
            }
            return;
        }
        for (int i2 = coords.length - 1; i2 >= 0; i2--) {
            coordList.add(coords[i2], false);
        }
    }

    public void setShell(EdgeRing shell2) {
        EdgeRing edgeRing = shell2;
        this.shell = edgeRing;
    }

    public boolean hasShell() {
        return this.shell != null;
    }

    public EdgeRing getShell() {
        if (isHole()) {
            return this.shell;
        }
        return this;
    }

    public boolean isOuterHole() {
        if (!this.isHole) {
            return false;
        }
        return !hasShell();
    }

    public boolean isOuterShell() {
        return getOuterHole() != null;
    }

    public EdgeRing getOuterHole() {
        if (isHole()) {
            return null;
        }
        for (int i = 0; i < this.deList.size(); i++) {
            EdgeRing adjRing = ((PolygonizeDirectedEdge) ((PolygonizeDirectedEdge) this.deList.get(i)).getSym()).getRing();
            if (adjRing.isOuterHole()) {
                return adjRing;
            }
        }
        return null;
    }

    public void updateIncluded() {
        if (!isHole()) {
            int i = 0;
            while (i < this.deList.size()) {
                EdgeRing adjShell = ((PolygonizeDirectedEdge) ((PolygonizeDirectedEdge) this.deList.get(i)).getSym()).getRing().getShell();
                if (adjShell == null || !adjShell.isIncludedSet()) {
                    i++;
                } else {
                    setIncluded(!adjShell.isIncluded());
                    return;
                }
            }
        }
    }

    public String toString() {
        CoordinateSequence coordinateSequence;
        new CoordinateArraySequence(getCoordinates());
        return WKTWriter.toLineString(coordinateSequence);
    }

    public boolean isProcessed() {
        return this.isProcessed;
    }

    public void setProcessed(boolean isProcessed2) {
        boolean z = isProcessed2;
        this.isProcessed = z;
    }

    static class EnvelopeComparator implements Comparator {
        EnvelopeComparator() {
        }

        public int compare(Object obj0, Object obj1) {
            return ((EdgeRing) obj0).getRing().getEnvelope().compareTo(((EdgeRing) obj1).getRing().getEnvelope());
        }
    }
}
