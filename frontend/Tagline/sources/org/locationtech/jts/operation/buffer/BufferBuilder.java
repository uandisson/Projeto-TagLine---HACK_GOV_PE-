package org.locationtech.jts.operation.buffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.EdgeList;
import org.locationtech.jts.geomgraph.Label;
import org.locationtech.jts.geomgraph.Node;
import org.locationtech.jts.geomgraph.NodeFactory;
import org.locationtech.jts.geomgraph.PlanarGraph;
import org.locationtech.jts.noding.IntersectionAdder;
import org.locationtech.jts.noding.MCIndexNoder;
import org.locationtech.jts.noding.Noder;
import org.locationtech.jts.noding.SegmentIntersector;
import org.locationtech.jts.noding.SegmentString;
import org.locationtech.jts.operation.overlay.OverlayNodeFactory;
import org.locationtech.jts.operation.overlay.PolygonBuilder;

class BufferBuilder {
    private BufferParameters bufParams;
    private EdgeList edgeList;
    private GeometryFactory geomFact;
    private PlanarGraph graph;
    private Noder workingNoder;
    private PrecisionModel workingPrecisionModel;

    private static int depthDelta(Label label) {
        Label label2 = label;
        int lLoc = label2.getLocation(0, 1);
        int rLoc = label2.getLocation(0, 2);
        if (lLoc == 0 && rLoc == 2) {
            return 1;
        }
        if (lLoc == 2 && rLoc == 0) {
            return -1;
        }
        return 0;
    }

    public BufferBuilder(BufferParameters bufParams2) {
        EdgeList edgeList2;
        new EdgeList();
        this.edgeList = edgeList2;
        this.bufParams = bufParams2;
    }

    public void setWorkingPrecisionModel(PrecisionModel pm) {
        PrecisionModel precisionModel = pm;
        this.workingPrecisionModel = precisionModel;
    }

    public void setNoder(Noder noder) {
        Noder noder2 = noder;
        this.workingNoder = noder2;
    }

    public Geometry buffer(Geometry geometry, double d) {
        OffsetCurveBuilder curveBuilder;
        OffsetCurveSetBuilder curveSetBuilder;
        PlanarGraph planarGraph;
        NodeFactory nodeFactory;
        PolygonBuilder polygonBuilder;
        Geometry g = geometry;
        double distance = d;
        PrecisionModel precisionModel = this.workingPrecisionModel;
        if (precisionModel == null) {
            precisionModel = g.getPrecisionModel();
        }
        this.geomFact = g.getFactory();
        new OffsetCurveBuilder(precisionModel, this.bufParams);
        new OffsetCurveSetBuilder(g, distance, curveBuilder);
        List bufferSegStrList = curveSetBuilder.getCurves();
        if (bufferSegStrList.size() <= 0) {
            return createEmptyResultGeometry();
        }
        computeNodedEdges(bufferSegStrList, precisionModel);
        new OverlayNodeFactory();
        new PlanarGraph(nodeFactory);
        this.graph = planarGraph;
        this.graph.addEdges(this.edgeList.getEdges());
        List subgraphList = createSubgraphs(this.graph);
        new PolygonBuilder(this.geomFact);
        PolygonBuilder polyBuilder = polygonBuilder;
        buildSubgraphs(subgraphList, polyBuilder);
        List resultPolyList = polyBuilder.getPolygons();
        if (resultPolyList.size() <= 0) {
            return createEmptyResultGeometry();
        }
        return this.geomFact.buildGeometry(resultPolyList);
    }

    private Noder getNoder(PrecisionModel precisionModel) {
        MCIndexNoder mCIndexNoder;
        LineIntersector lineIntersector;
        SegmentIntersector segmentIntersector;
        PrecisionModel precisionModel2 = precisionModel;
        if (this.workingNoder != null) {
            return this.workingNoder;
        }
        new MCIndexNoder();
        MCIndexNoder noder = mCIndexNoder;
        new RobustLineIntersector();
        LineIntersector li = lineIntersector;
        li.setPrecisionModel(precisionModel2);
        new IntersectionAdder(li);
        noder.setSegmentIntersector(segmentIntersector);
        return noder;
    }

    private void computeNodedEdges(List bufferSegStrList, PrecisionModel precisionModel) {
        Edge edge;
        Label label;
        Noder noder = getNoder(precisionModel);
        noder.computeNodes(bufferSegStrList);
        for (SegmentString segStr : noder.getNodedSubstrings()) {
            Coordinate[] pts = segStr.getCoordinates();
            if (pts.length != 2 || !pts[0].equals2D(pts[1])) {
                new Label((Label) segStr.getData());
                new Edge(segStr.getCoordinates(), label);
                insertUniqueEdge(edge);
            }
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
            existingLabel.merge(labelToMerge);
            existingEdge.setDepthDelta(existingEdge.getDepthDelta() + depthDelta(labelToMerge));
            return;
        }
        this.edgeList.add(e);
        e.setDepthDelta(depthDelta(e.getLabel()));
    }

    private List createSubgraphs(PlanarGraph graph2) {
        List list;
        BufferSubgraph bufferSubgraph;
        new ArrayList();
        List subgraphList = list;
        for (Node node : graph2.getNodes()) {
            if (!node.isVisited()) {
                new BufferSubgraph();
                BufferSubgraph subgraph = bufferSubgraph;
                subgraph.create(node);
                boolean add = subgraphList.add(subgraph);
            }
        }
        Collections.sort(subgraphList, Collections.reverseOrder());
        return subgraphList;
    }

    private void buildSubgraphs(List subgraphList, PolygonBuilder polygonBuilder) {
        List list;
        SubgraphDepthLocater locater;
        PolygonBuilder polyBuilder = polygonBuilder;
        new ArrayList();
        List processedGraphs = list;
        Iterator i = subgraphList.iterator();
        while (i.hasNext()) {
            BufferSubgraph subgraph = (BufferSubgraph) i.next();
            Coordinate p = subgraph.getRightmostCoordinate();
            new SubgraphDepthLocater(processedGraphs);
            subgraph.computeDepth(locater.getDepth(p));
            subgraph.findResultEdges();
            boolean add = processedGraphs.add(subgraph);
            polyBuilder.add(subgraph.getDirectedEdges(), subgraph.getNodes());
        }
    }

    private static Geometry convertSegStrings(Iterator it) {
        GeometryFactory geometryFactory;
        List list;
        Iterator it2 = it;
        new GeometryFactory();
        GeometryFactory fact = geometryFactory;
        new ArrayList();
        List lines = list;
        while (it2.hasNext()) {
            boolean add = lines.add(fact.createLineString(((SegmentString) it2.next()).getCoordinates()));
        }
        return fact.buildGeometry(lines);
    }

    private Geometry createEmptyResultGeometry() {
        return this.geomFact.createPolygon((LinearRing) null, (LinearRing[]) null);
    }
}
