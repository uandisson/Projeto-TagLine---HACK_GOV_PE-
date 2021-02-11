package org.locationtech.jts.noding;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.util.Assert;

public class SegmentNodeList {
    private NodedSegmentString edge;
    private Map nodeMap;

    public SegmentNodeList(NodedSegmentString edge2) {
        Map map;
        new TreeMap();
        this.nodeMap = map;
        this.edge = edge2;
    }

    public NodedSegmentString getEdge() {
        return this.edge;
    }

    public SegmentNode add(Coordinate coordinate, int i) {
        SegmentNode segmentNode;
        Coordinate intPt = coordinate;
        int segmentIndex = i;
        new SegmentNode(this.edge, intPt, segmentIndex, this.edge.getSegmentOctant(segmentIndex));
        SegmentNode eiNew = segmentNode;
        SegmentNode ei = (SegmentNode) this.nodeMap.get(eiNew);
        if (ei != null) {
            Assert.isTrue(ei.coord.equals2D(intPt), "Found equal nodes with different coordinates");
            return ei;
        }
        Object put = this.nodeMap.put(eiNew, eiNew);
        return eiNew;
    }

    public Iterator iterator() {
        return this.nodeMap.values().iterator();
    }

    private void addEndpoints() {
        int maxSegIndex = this.edge.size() - 1;
        SegmentNode add = add(this.edge.getCoordinate(0), 0);
        SegmentNode add2 = add(this.edge.getCoordinate(maxSegIndex), maxSegIndex);
    }

    private void addCollapsedNodes() {
        List list;
        new ArrayList();
        List<Integer> collapsedVertexIndexes = list;
        findCollapsesFromInsertedNodes(collapsedVertexIndexes);
        findCollapsesFromExistingVertices(collapsedVertexIndexes);
        for (Integer intValue : collapsedVertexIndexes) {
            int vertexIndex = intValue.intValue();
            SegmentNode add = add(this.edge.getCoordinate(vertexIndex), vertexIndex);
        }
    }

    private void findCollapsesFromExistingVertices(List list) {
        Object obj;
        List collapsedVertexIndexes = list;
        for (int i = 0; i < this.edge.size() - 2; i++) {
            Coordinate p0 = this.edge.getCoordinate(i);
            Coordinate coordinate = this.edge.getCoordinate(i + 1);
            if (p0.equals2D(this.edge.getCoordinate(i + 2))) {
                new Integer(i + 1);
                boolean add = collapsedVertexIndexes.add(obj);
            }
        }
    }

    private void findCollapsesFromInsertedNodes(List list) {
        Object obj;
        List collapsedVertexIndexes = list;
        int[] collapsedVertexIndex = new int[1];
        Iterator it = iterator();
        SegmentNode segmentNode = (SegmentNode) it.next();
        while (true) {
            SegmentNode eiPrev = segmentNode;
            if (it.hasNext()) {
                SegmentNode ei = (SegmentNode) it.next();
                if (findCollapseIndex(eiPrev, ei, collapsedVertexIndex)) {
                    new Integer(collapsedVertexIndex[0]);
                    boolean add = collapsedVertexIndexes.add(obj);
                }
                segmentNode = ei;
            } else {
                return;
            }
        }
    }

    private boolean findCollapseIndex(SegmentNode segmentNode, SegmentNode segmentNode2, int[] iArr) {
        SegmentNode ei0 = segmentNode;
        SegmentNode ei1 = segmentNode2;
        int[] collapsedVertexIndex = iArr;
        if (!ei0.coord.equals2D(ei1.coord)) {
            return false;
        }
        int numVerticesBetween = ei1.segmentIndex - ei0.segmentIndex;
        if (!ei1.isInterior()) {
            numVerticesBetween--;
        }
        if (numVerticesBetween != 1) {
            return false;
        }
        collapsedVertexIndex[0] = ei0.segmentIndex + 1;
        return true;
    }

    public void addSplitEdges(Collection collection) {
        Collection edgeList = collection;
        addEndpoints();
        addCollapsedNodes();
        Iterator it = iterator();
        SegmentNode segmentNode = (SegmentNode) it.next();
        while (true) {
            SegmentNode eiPrev = segmentNode;
            if (it.hasNext()) {
                SegmentNode ei = (SegmentNode) it.next();
                boolean add = edgeList.add(createSplitEdge(eiPrev, ei));
                segmentNode = ei;
            } else {
                return;
            }
        }
    }

    private void checkSplitEdgesCorrectness(List list) {
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        StringBuilder sb2;
        List splitEdges = list;
        Coordinate[] edgePts = this.edge.getCoordinates();
        Coordinate pt0 = ((SegmentString) splitEdges.get(0)).getCoordinate(0);
        if (!pt0.equals2D(edgePts[0])) {
            Throwable th3 = th2;
            new StringBuilder();
            new RuntimeException(sb2.append("bad split edge start point at ").append(pt0).toString());
            throw th3;
        }
        Coordinate[] splitnPts = ((SegmentString) splitEdges.get(splitEdges.size() - 1)).getCoordinates();
        Coordinate ptn = splitnPts[splitnPts.length - 1];
        if (!ptn.equals2D(edgePts[edgePts.length - 1])) {
            Throwable th4 = th;
            new StringBuilder();
            new RuntimeException(sb.append("bad split edge end point at ").append(ptn).toString());
            throw th4;
        }
    }

    /* access modifiers changed from: package-private */
    public SegmentString createSplitEdge(SegmentNode segmentNode, SegmentNode segmentNode2) {
        Coordinate coordinate;
        SegmentString segmentString;
        Coordinate coordinate2;
        SegmentNode ei0 = segmentNode;
        SegmentNode ei1 = segmentNode2;
        int npts = (ei1.segmentIndex - ei0.segmentIndex) + 2;
        boolean useIntPt1 = ei1.isInterior() || !ei1.coord.equals2D(this.edge.getCoordinate(ei1.segmentIndex));
        if (!useIntPt1) {
            npts--;
        }
        Coordinate[] pts = new Coordinate[npts];
        int ipt = 0 + 1;
        new Coordinate(ei0.coord);
        pts[0] = coordinate;
        for (int i = ei0.segmentIndex + 1; i <= ei1.segmentIndex; i++) {
            int i2 = ipt;
            ipt++;
            pts[i2] = this.edge.getCoordinate(i);
        }
        if (useIntPt1) {
            new Coordinate(ei1.coord);
            pts[ipt] = coordinate2;
        }
        new NodedSegmentString(pts, this.edge.getData());
        return segmentString;
    }

    public Coordinate[] getSplitCoordinates() {
        CoordinateList coordinateList;
        new CoordinateList();
        CoordinateList coordList = coordinateList;
        addEndpoints();
        Iterator it = iterator();
        SegmentNode segmentNode = (SegmentNode) it.next();
        while (true) {
            SegmentNode eiPrev = segmentNode;
            if (!it.hasNext()) {
                return coordList.toCoordinateArray();
            }
            SegmentNode ei = (SegmentNode) it.next();
            addEdgeCoordinates(eiPrev, ei, coordList);
            segmentNode = ei;
        }
    }

    private void addEdgeCoordinates(SegmentNode segmentNode, SegmentNode segmentNode2, CoordinateList coordinateList) {
        Coordinate coordinate;
        Object obj;
        SegmentNode ei0 = segmentNode;
        SegmentNode ei1 = segmentNode2;
        CoordinateList coordList = coordinateList;
        int npts = (ei1.segmentIndex - ei0.segmentIndex) + 2;
        boolean useIntPt1 = ei1.isInterior() || !ei1.coord.equals2D(this.edge.getCoordinate(ei1.segmentIndex));
        if (!useIntPt1) {
            int npts2 = npts - 1;
        }
        new Coordinate(ei0.coord);
        coordList.add(coordinate, false);
        for (int i = ei0.segmentIndex + 1; i <= ei1.segmentIndex; i++) {
            boolean add = coordList.add(this.edge.getCoordinate(i));
        }
        if (useIntPt1) {
            new Coordinate(ei1.coord);
            boolean add2 = coordList.add(obj);
        }
    }

    public void print(PrintStream printStream) {
        PrintStream out = printStream;
        out.println("Intersections:");
        Iterator it = iterator();
        while (it.hasNext()) {
            ((SegmentNode) it.next()).print(out);
        }
    }
}
