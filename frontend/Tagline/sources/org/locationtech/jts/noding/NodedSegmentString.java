package org.locationtech.jts.noding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.p006io.WKTWriter;

public class NodedSegmentString implements NodableSegmentString {
    private Object data;
    private SegmentNodeList nodeList;
    private Coordinate[] pts;

    public static List getNodedSubstrings(Collection segStrings) {
        List list;
        new ArrayList();
        List resultEdgelist = list;
        getNodedSubstrings(segStrings, resultEdgelist);
        return resultEdgelist;
    }

    public static void getNodedSubstrings(Collection segStrings, Collection collection) {
        Collection resultEdgelist = collection;
        Iterator i = segStrings.iterator();
        while (i.hasNext()) {
            ((NodedSegmentString) i.next()).getNodeList().addSplitEdges(resultEdgelist);
        }
    }

    public NodedSegmentString(Coordinate[] pts2, Object data2) {
        SegmentNodeList segmentNodeList;
        new SegmentNodeList(this);
        this.nodeList = segmentNodeList;
        this.pts = pts2;
        this.data = data2;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data2) {
        Object obj = data2;
        this.data = obj;
    }

    public SegmentNodeList getNodeList() {
        return this.nodeList;
    }

    public int size() {
        return this.pts.length;
    }

    public Coordinate getCoordinate(int i) {
        return this.pts[i];
    }

    public Coordinate[] getCoordinates() {
        return this.pts;
    }

    public boolean isClosed() {
        return this.pts[0].equals(this.pts[this.pts.length - 1]);
    }

    public int getSegmentOctant(int i) {
        int index = i;
        if (index == this.pts.length - 1) {
            return -1;
        }
        return safeOctant(getCoordinate(index), getCoordinate(index + 1));
    }

    private int safeOctant(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        if (p0.equals2D(p1)) {
            return 0;
        }
        return Octant.octant(p0, p1);
    }

    public void addIntersections(LineIntersector lineIntersector, int i, int i2) {
        LineIntersector li = lineIntersector;
        int segmentIndex = i;
        int geomIndex = i2;
        for (int i3 = 0; i3 < li.getIntersectionNum(); i3++) {
            addIntersection(li, segmentIndex, geomIndex, i3);
        }
    }

    public void addIntersection(LineIntersector li, int segmentIndex, int i, int intIndex) {
        Coordinate intPt;
        int i2 = i;
        new Coordinate(li.getIntersection(intIndex));
        addIntersection(intPt, segmentIndex);
    }

    public void addIntersection(Coordinate intPt, int segmentIndex) {
        SegmentNode addIntersectionNode = addIntersectionNode(intPt, segmentIndex);
    }

    public SegmentNode addIntersectionNode(Coordinate coordinate, int segmentIndex) {
        Coordinate intPt = coordinate;
        int normalizedSegmentIndex = segmentIndex;
        int nextSegIndex = normalizedSegmentIndex + 1;
        if (nextSegIndex < this.pts.length) {
            if (intPt.equals2D(this.pts[nextSegIndex])) {
                normalizedSegmentIndex = nextSegIndex;
            }
        }
        return this.nodeList.add(intPt, normalizedSegmentIndex);
    }

    public String toString() {
        CoordinateSequence coordinateSequence;
        new CoordinateArraySequence(this.pts);
        return WKTWriter.toLineString(coordinateSequence);
    }
}
