package org.locationtech.jts.operation.relate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.PointLocator;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.IntersectionMatrix;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.EdgeEnd;
import org.locationtech.jts.geomgraph.EdgeIntersection;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.geomgraph.Label;
import org.locationtech.jts.geomgraph.Node;
import org.locationtech.jts.geomgraph.NodeFactory;
import org.locationtech.jts.geomgraph.NodeMap;
import org.locationtech.jts.geomgraph.index.SegmentIntersector;
import org.locationtech.jts.util.Assert;

public class RelateComputer {
    private GeometryGraph[] arg;

    /* renamed from: im */
    private IntersectionMatrix f498im = null;
    private Coordinate invalidPoint;
    private ArrayList isolatedEdges;

    /* renamed from: li */
    private LineIntersector f499li;
    private NodeMap nodes;
    private PointLocator ptLocator;

    public RelateComputer(GeometryGraph[] arg2) {
        LineIntersector lineIntersector;
        PointLocator pointLocator;
        NodeMap nodeMap;
        NodeFactory nodeFactory;
        ArrayList arrayList;
        new RobustLineIntersector();
        this.f499li = lineIntersector;
        new PointLocator();
        this.ptLocator = pointLocator;
        new RelateNodeFactory();
        new NodeMap(nodeFactory);
        this.nodes = nodeMap;
        new ArrayList();
        this.isolatedEdges = arrayList;
        this.arg = arg2;
    }

    public IntersectionMatrix computeIM() {
        IntersectionMatrix intersectionMatrix;
        EdgeEndBuilder edgeEndBuilder;
        new IntersectionMatrix();
        IntersectionMatrix im = intersectionMatrix;
        im.set(2, 2, 2);
        if (!this.arg[0].getGeometry().getEnvelopeInternal().intersects(this.arg[1].getGeometry().getEnvelopeInternal())) {
            computeDisjointIM(im);
            return im;
        }
        SegmentIntersector computeSelfNodes = this.arg[0].computeSelfNodes(this.f499li, false);
        SegmentIntersector computeSelfNodes2 = this.arg[1].computeSelfNodes(this.f499li, false);
        SegmentIntersector intersector = this.arg[0].computeEdgeIntersections(this.arg[1], this.f499li, false);
        computeIntersectionNodes(0);
        computeIntersectionNodes(1);
        copyNodesAndLabels(0);
        copyNodesAndLabels(1);
        labelIsolatedNodes();
        computeProperIntersectionIM(intersector, im);
        new EdgeEndBuilder();
        EdgeEndBuilder eeBuilder = edgeEndBuilder;
        insertEdgeEnds(eeBuilder.computeEdgeEnds(this.arg[0].getEdgeIterator()));
        insertEdgeEnds(eeBuilder.computeEdgeEnds(this.arg[1].getEdgeIterator()));
        labelNodeEdges();
        labelIsolatedEdges(0, 1);
        labelIsolatedEdges(1, 0);
        updateIM(im);
        return im;
    }

    private void insertEdgeEnds(List ee) {
        Iterator i = ee.iterator();
        while (i.hasNext()) {
            this.nodes.add((EdgeEnd) i.next());
        }
    }

    private void computeProperIntersectionIM(SegmentIntersector segmentIntersector, IntersectionMatrix intersectionMatrix) {
        SegmentIntersector intersector = segmentIntersector;
        IntersectionMatrix im = intersectionMatrix;
        int dimA = this.arg[0].getGeometry().getDimension();
        int dimB = this.arg[1].getGeometry().getDimension();
        boolean hasProper = intersector.hasProperIntersection();
        boolean hasProperInterior = intersector.hasProperInteriorIntersection();
        if (dimA == 2 && dimB == 2) {
            if (hasProper) {
                im.setAtLeast("212101212");
            }
        } else if (dimA == 2 && dimB == 1) {
            if (hasProper) {
                im.setAtLeast("FFF0FFFF2");
            }
            if (hasProperInterior) {
                im.setAtLeast("1FFFFF1FF");
            }
        } else if (dimA == 1 && dimB == 2) {
            if (hasProper) {
                im.setAtLeast("F0FFFFFF2");
            }
            if (hasProperInterior) {
                im.setAtLeast("1F1FFFFFF");
            }
        } else if (dimA == 1 && dimB == 1 && hasProperInterior) {
            im.setAtLeast("0FFFFFFFF");
        }
    }

    private void copyNodesAndLabels(int i) {
        int argIndex = i;
        Iterator i2 = this.arg[argIndex].getNodeIterator();
        while (i2.hasNext()) {
            Node graphNode = (Node) i2.next();
            this.nodes.addNode(graphNode.getCoordinate()).setLabel(argIndex, graphNode.getLabel().getLocation(argIndex));
        }
    }

    private void computeIntersectionNodes(int i) {
        int argIndex = i;
        Iterator i2 = this.arg[argIndex].getEdgeIterator();
        while (i2.hasNext()) {
            Edge e = (Edge) i2.next();
            int eLoc = e.getLabel().getLocation(argIndex);
            Iterator eiIt = e.getEdgeIntersectionList().iterator();
            while (eiIt.hasNext()) {
                RelateNode n = (RelateNode) this.nodes.addNode(((EdgeIntersection) eiIt.next()).coord);
                if (eLoc == 1) {
                    n.setLabelBoundary(argIndex);
                } else if (n.getLabel().isNull(argIndex)) {
                    n.setLabel(argIndex, 0);
                }
            }
        }
    }

    private void labelIntersectionNodes(int i) {
        int argIndex = i;
        Iterator i2 = this.arg[argIndex].getEdgeIterator();
        while (i2.hasNext()) {
            Edge e = (Edge) i2.next();
            int eLoc = e.getLabel().getLocation(argIndex);
            Iterator eiIt = e.getEdgeIntersectionList().iterator();
            while (eiIt.hasNext()) {
                RelateNode n = (RelateNode) this.nodes.find(((EdgeIntersection) eiIt.next()).coord);
                if (n.getLabel().isNull(argIndex)) {
                    if (eLoc == 1) {
                        n.setLabelBoundary(argIndex);
                    } else {
                        n.setLabel(argIndex, 0);
                    }
                }
            }
        }
    }

    private void computeDisjointIM(IntersectionMatrix intersectionMatrix) {
        IntersectionMatrix im = intersectionMatrix;
        Geometry ga = this.arg[0].getGeometry();
        if (!ga.isEmpty()) {
            im.set(0, 2, ga.getDimension());
            im.set(1, 2, ga.getBoundaryDimension());
        }
        Geometry gb = this.arg[1].getGeometry();
        if (!gb.isEmpty()) {
            im.set(2, 0, gb.getDimension());
            im.set(2, 1, gb.getBoundaryDimension());
        }
    }

    private void labelNodeEdges() {
        Iterator ni = this.nodes.iterator();
        while (ni.hasNext()) {
            ((RelateNode) ni.next()).getEdges().computeLabelling(this.arg);
        }
    }

    private void updateIM(IntersectionMatrix intersectionMatrix) {
        IntersectionMatrix im = intersectionMatrix;
        Iterator ei = this.isolatedEdges.iterator();
        while (ei.hasNext()) {
            ((Edge) ei.next()).updateIM(im);
        }
        Iterator ni = this.nodes.iterator();
        while (ni.hasNext()) {
            RelateNode node = (RelateNode) ni.next();
            node.updateIM(im);
            node.updateIMFromEdges(im);
        }
    }

    private void labelIsolatedEdges(int thisIndex, int i) {
        int targetIndex = i;
        Iterator ei = this.arg[thisIndex].getEdgeIterator();
        while (ei.hasNext()) {
            Edge e = (Edge) ei.next();
            if (e.isIsolated()) {
                labelIsolatedEdge(e, targetIndex, this.arg[targetIndex].getGeometry());
                boolean add = this.isolatedEdges.add(e);
            }
        }
    }

    private void labelIsolatedEdge(Edge edge, int i, Geometry geometry) {
        Edge e = edge;
        int targetIndex = i;
        Geometry target = geometry;
        if (target.getDimension() > 0) {
            e.getLabel().setAllLocations(targetIndex, this.ptLocator.locate(e.getCoordinate(), target));
            return;
        }
        e.getLabel().setAllLocations(targetIndex, 2);
    }

    private void labelIsolatedNodes() {
        Iterator ni = this.nodes.iterator();
        while (ni.hasNext()) {
            Node n = (Node) ni.next();
            Label label = n.getLabel();
            Assert.isTrue(label.getGeometryCount() > 0, "node with empty label found");
            if (n.isIsolated()) {
                if (label.isNull(0)) {
                    labelIsolatedNode(n, 0);
                } else {
                    labelIsolatedNode(n, 1);
                }
            }
        }
    }

    private void labelIsolatedNode(Node node, int i) {
        Node n = node;
        int targetIndex = i;
        n.getLabel().setAllLocations(targetIndex, this.ptLocator.locate(n.getCoordinate(), this.arg[targetIndex].getGeometry()));
    }
}
