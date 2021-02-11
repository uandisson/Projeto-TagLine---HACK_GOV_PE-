package org.locationtech.jts.operation.overlay;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.PointLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geomgraph.Depth;
import org.locationtech.jts.geomgraph.DirectedEdge;
import org.locationtech.jts.geomgraph.DirectedEdgeStar;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.EdgeList;
import org.locationtech.jts.geomgraph.EdgeNodingValidator;
import org.locationtech.jts.geomgraph.Label;
import org.locationtech.jts.geomgraph.Node;
import org.locationtech.jts.geomgraph.PlanarGraph;
import org.locationtech.jts.geomgraph.index.SegmentIntersector;
import org.locationtech.jts.operation.GeometryGraphOperation;
import org.locationtech.jts.util.Assert;

public class OverlayOp extends GeometryGraphOperation {
    public static final int DIFFERENCE = 3;
    public static final int INTERSECTION = 1;
    public static final int SYMDIFFERENCE = 4;
    public static final int UNION = 2;
    private EdgeList edgeList;
    private GeometryFactory geomFact;
    private PlanarGraph graph;
    private final PointLocator ptLocator;
    private Geometry resultGeom;
    private List resultLineList;
    private List resultPointList;
    private List resultPolyList;

    public static Geometry overlayOp(Geometry geom0, Geometry geom1, int opCode) {
        OverlayOp gov;
        new OverlayOp(geom0, geom1);
        return gov.getResultGeometry(opCode);
    }

    public static boolean isResultOfOp(Label label, int opCode) {
        Label label2 = label;
        return isResultOfOp(label2.getLocation(0), label2.getLocation(1), opCode);
    }

    public static boolean isResultOfOp(int i, int i2, int i3) {
        int loc0 = i;
        int loc1 = i2;
        int overlayOpCode = i3;
        if (loc0 == 1) {
            loc0 = 0;
        }
        if (loc1 == 1) {
            loc1 = 0;
        }
        switch (overlayOpCode) {
            case 1:
                return loc0 == 0 && loc1 == 0;
            case 2:
                return loc0 == 0 || loc1 == 0;
            case 3:
                return loc0 == 0 && loc1 != 0;
            case 4:
                return (loc0 == 0 && loc1 != 0) || (loc0 != 0 && loc1 == 0);
            default:
                return false;
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public OverlayOp(org.locationtech.jts.geom.Geometry r10, org.locationtech.jts.geom.Geometry r11) {
        /*
            r9 = this;
            r0 = r9
            r1 = r10
            r2 = r11
            r3 = r0
            r4 = r1
            r5 = r2
            r3.<init>(r4, r5)
            r3 = r0
            org.locationtech.jts.algorithm.PointLocator r4 = new org.locationtech.jts.algorithm.PointLocator
            r8 = r4
            r4 = r8
            r5 = r8
            r5.<init>()
            r3.ptLocator = r4
            r3 = r0
            org.locationtech.jts.geomgraph.EdgeList r4 = new org.locationtech.jts.geomgraph.EdgeList
            r8 = r4
            r4 = r8
            r5 = r8
            r5.<init>()
            r3.edgeList = r4
            r3 = r0
            java.util.ArrayList r4 = new java.util.ArrayList
            r8 = r4
            r4 = r8
            r5 = r8
            r5.<init>()
            r3.resultPolyList = r4
            r3 = r0
            java.util.ArrayList r4 = new java.util.ArrayList
            r8 = r4
            r4 = r8
            r5 = r8
            r5.<init>()
            r3.resultLineList = r4
            r3 = r0
            java.util.ArrayList r4 = new java.util.ArrayList
            r8 = r4
            r4 = r8
            r5 = r8
            r5.<init>()
            r3.resultPointList = r4
            r3 = r0
            org.locationtech.jts.geomgraph.PlanarGraph r4 = new org.locationtech.jts.geomgraph.PlanarGraph
            r8 = r4
            r4 = r8
            r5 = r8
            org.locationtech.jts.operation.overlay.OverlayNodeFactory r6 = new org.locationtech.jts.operation.overlay.OverlayNodeFactory
            r8 = r6
            r6 = r8
            r7 = r8
            r7.<init>()
            r5.<init>(r6)
            r3.graph = r4
            r3 = r0
            r4 = r1
            org.locationtech.jts.geom.GeometryFactory r4 = r4.getFactory()
            r3.geomFact = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.operation.overlay.OverlayOp.<init>(org.locationtech.jts.geom.Geometry, org.locationtech.jts.geom.Geometry):void");
    }

    public Geometry getResultGeometry(int overlayOpCode) {
        computeOverlay(overlayOpCode);
        return this.resultGeom;
    }

    public PlanarGraph getGraph() {
        return this.graph;
    }

    private void computeOverlay(int i) {
        List list;
        PolygonBuilder polygonBuilder;
        LineBuilder lineBuilder;
        PointBuilder pointBuilder;
        int opCode = i;
        copyPoints(0);
        copyPoints(1);
        SegmentIntersector computeSelfNodes = this.arg[0].computeSelfNodes(this.f478li, false);
        SegmentIntersector computeSelfNodes2 = this.arg[1].computeSelfNodes(this.f478li, false);
        SegmentIntersector computeEdgeIntersections = this.arg[0].computeEdgeIntersections(this.arg[1], this.f478li, true);
        new ArrayList();
        List baseSplitEdges = list;
        this.arg[0].computeSplitEdges(baseSplitEdges);
        this.arg[1].computeSplitEdges(baseSplitEdges);
        List list2 = baseSplitEdges;
        insertUniqueEdges(baseSplitEdges);
        computeLabelsFromDepths();
        replaceCollapsedEdges();
        EdgeNodingValidator.checkValid(this.edgeList.getEdges());
        this.graph.addEdges(this.edgeList.getEdges());
        computeLabelling();
        labelIncompleteNodes();
        findResultAreaEdges(opCode);
        cancelDuplicateResultEdges();
        new PolygonBuilder(this.geomFact);
        PolygonBuilder polyBuilder = polygonBuilder;
        polyBuilder.add(this.graph);
        this.resultPolyList = polyBuilder.getPolygons();
        new LineBuilder(this, this.geomFact, this.ptLocator);
        this.resultLineList = lineBuilder.build(opCode);
        new PointBuilder(this, this.geomFact, this.ptLocator);
        this.resultPointList = pointBuilder.build(opCode);
        this.resultGeom = computeGeometry(this.resultPointList, this.resultLineList, this.resultPolyList, opCode);
    }

    private void insertUniqueEdges(List edges) {
        Iterator i = edges.iterator();
        while (i.hasNext()) {
            insertUniqueEdge((Edge) i.next());
        }
    }

    /* access modifiers changed from: protected */
    public void insertUniqueEdge(Edge edge) {
        Label label;
        Edge e = edge;
        Edge existingEdge = this.edgeList.findEqualEdge(e);
        if (existingEdge != null) {
            Label existingLabel = existingEdge.getLabel();
            Label labelToMerge = e.getLabel();
            if (!existingEdge.isPointwiseEqual(e)) {
                new Label(e.getLabel());
                labelToMerge = label;
                labelToMerge.flip();
            }
            Depth depth = existingEdge.getDepth();
            if (depth.isNull()) {
                depth.add(existingLabel);
            }
            depth.add(labelToMerge);
            existingLabel.merge(labelToMerge);
            return;
        }
        this.edgeList.add(e);
    }

    private void computeLabelsFromDepths() {
        Iterator it = this.edgeList.iterator();
        while (it.hasNext()) {
            Edge e = (Edge) it.next();
            Label lbl = e.getLabel();
            Depth depth = e.getDepth();
            if (!depth.isNull()) {
                depth.normalize();
                for (int i = 0; i < 2; i++) {
                    if (!lbl.isNull(i) && lbl.isArea() && !depth.isNull(i)) {
                        if (depth.getDelta(i) == 0) {
                            lbl.toLine(i);
                        } else {
                            Assert.isTrue(!depth.isNull(i, 1), "depth of LEFT side has not been initialized");
                            lbl.setLocation(i, 1, depth.getLocation(i, 1));
                            Assert.isTrue(!depth.isNull(i, 2), "depth of RIGHT side has not been initialized");
                            lbl.setLocation(i, 2, depth.getLocation(i, 2));
                        }
                    }
                }
            }
        }
    }

    private void replaceCollapsedEdges() {
        List list;
        new ArrayList();
        List newEdges = list;
        Iterator it = this.edgeList.iterator();
        while (it.hasNext()) {
            Edge e = (Edge) it.next();
            if (e.isCollapsed()) {
                it.remove();
                boolean add = newEdges.add(e.getCollapsedEdge());
            }
        }
        this.edgeList.addAll(newEdges);
    }

    private void copyPoints(int i) {
        int argIndex = i;
        Iterator i2 = this.arg[argIndex].getNodeIterator();
        while (i2.hasNext()) {
            Node graphNode = (Node) i2.next();
            this.graph.addNode(graphNode.getCoordinate()).setLabel(argIndex, graphNode.getLabel().getLocation(argIndex));
        }
    }

    private void computeLabelling() {
        for (Node node : this.graph.getNodes()) {
            node.getEdges().computeLabelling(this.arg);
        }
        mergeSymLabels();
        updateNodeLabelling();
    }

    private void mergeSymLabels() {
        for (Node node : this.graph.getNodes()) {
            ((DirectedEdgeStar) node.getEdges()).mergeSymLabels();
        }
    }

    private void updateNodeLabelling() {
        for (Node node : this.graph.getNodes()) {
            node.getLabel().merge(((DirectedEdgeStar) node.getEdges()).getLabel());
        }
    }

    private void labelIncompleteNodes() {
        int nodeCount = 0;
        for (Node n : this.graph.getNodes()) {
            Label label = n.getLabel();
            if (n.isIsolated()) {
                nodeCount++;
                if (label.isNull(0)) {
                    labelIncompleteNode(n, 0);
                } else {
                    labelIncompleteNode(n, 1);
                }
            }
            ((DirectedEdgeStar) n.getEdges()).updateLabelling(label);
        }
    }

    private void labelIncompleteNode(Node node, int i) {
        Node n = node;
        int targetIndex = i;
        n.getLabel().setLocation(targetIndex, this.ptLocator.locate(n.getCoordinate(), this.arg[targetIndex].getGeometry()));
    }

    private void findResultAreaEdges(int i) {
        int opCode = i;
        for (DirectedEdge de : this.graph.getEdgeEnds()) {
            Label label = de.getLabel();
            if (label.isArea() && !de.isInteriorAreaEdge() && isResultOfOp(label.getLocation(0, 2), label.getLocation(1, 2), opCode)) {
                de.setInResult(true);
            }
        }
    }

    private void cancelDuplicateResultEdges() {
        for (DirectedEdge de : this.graph.getEdgeEnds()) {
            DirectedEdge sym = de.getSym();
            if (de.isInResult() && sym.isInResult()) {
                de.setInResult(false);
                sym.setInResult(false);
            }
        }
    }

    public boolean isCoveredByLA(Coordinate coordinate) {
        Coordinate coord = coordinate;
        if (isCovered(coord, this.resultLineList)) {
            return true;
        }
        if (isCovered(coord, this.resultPolyList)) {
            return true;
        }
        return false;
    }

    public boolean isCoveredByA(Coordinate coord) {
        if (isCovered(coord, this.resultPolyList)) {
            return true;
        }
        return false;
    }

    private boolean isCovered(Coordinate coordinate, List geomList) {
        Coordinate coord = coordinate;
        Iterator it = geomList.iterator();
        while (it.hasNext()) {
            if (this.ptLocator.locate(coord, (Geometry) it.next()) != 2) {
                return true;
            }
        }
        return false;
    }

    private Geometry computeGeometry(List resultPointList2, List resultLineList2, List resultPolyList2, int i) {
        List list;
        int opcode = i;
        new ArrayList();
        List geomList = list;
        boolean addAll = geomList.addAll(resultPointList2);
        boolean addAll2 = geomList.addAll(resultLineList2);
        boolean addAll3 = geomList.addAll(resultPolyList2);
        if (geomList.isEmpty()) {
            return createEmptyResult(opcode, this.arg[0].getGeometry(), this.arg[1].getGeometry(), this.geomFact);
        }
        return this.geomFact.buildGeometry(geomList);
    }

    public static Geometry createEmptyResult(int overlayOpCode, Geometry a, Geometry b, GeometryFactory geometryFactory) {
        GeometryFactory geomFact2 = geometryFactory;
        Geometry result = null;
        switch (resultDimension(overlayOpCode, a, b)) {
            case -1:
                result = geomFact2.createGeometryCollection(new Geometry[0]);
                break;
            case 0:
                result = geomFact2.createPoint((Coordinate) null);
                break;
            case 1:
                result = geomFact2.createLineString((Coordinate[]) null);
                break;
            case 2:
                result = geomFact2.createPolygon((LinearRing) null, (LinearRing[]) null);
                break;
        }
        return result;
    }

    private static int resultDimension(int opCode, Geometry g0, Geometry g1) {
        int dim0 = g0.getDimension();
        int dim1 = g1.getDimension();
        int resultDimension = -1;
        switch (opCode) {
            case 1:
                resultDimension = Math.min(dim0, dim1);
                break;
            case 2:
                resultDimension = Math.max(dim0, dim1);
                break;
            case 3:
                resultDimension = dim0;
                break;
            case 4:
                resultDimension = Math.max(dim0, dim1);
                break;
        }
        return resultDimension;
    }
}
