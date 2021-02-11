package org.locationtech.jts.operation.polygonize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.planargraph.DirectedEdge;
import org.locationtech.jts.planargraph.Edge;
import org.locationtech.jts.planargraph.Node;
import org.locationtech.jts.planargraph.PlanarGraph;
import org.locationtech.jts.util.Assert;

class PolygonizeGraph extends PlanarGraph {
    private GeometryFactory factory;

    private static int getDegreeNonDeleted(Node node) {
        int degree = 0;
        for (PolygonizeDirectedEdge de : node.getOutEdges().getEdges()) {
            if (!de.isMarked()) {
                degree++;
            }
        }
        return degree;
    }

    private static int getDegree(Node node, long j) {
        long label = j;
        int degree = 0;
        for (PolygonizeDirectedEdge de : node.getOutEdges().getEdges()) {
            if (de.getLabel() == label) {
                degree++;
            }
        }
        return degree;
    }

    public static void deleteAllEdges(Node node) {
        for (PolygonizeDirectedEdge de : node.getOutEdges().getEdges()) {
            de.setMarked(true);
            PolygonizeDirectedEdge sym = (PolygonizeDirectedEdge) de.getSym();
            if (sym != null) {
                sym.setMarked(true);
            }
        }
    }

    public PolygonizeGraph(GeometryFactory factory2) {
        this.factory = factory2;
    }

    public void addEdge(LineString lineString) {
        DirectedEdge de0;
        DirectedEdge de1;
        Edge edge;
        LineString line = lineString;
        if (!line.isEmpty()) {
            Coordinate[] linePts = CoordinateArrays.removeRepeatedPoints(line.getCoordinates());
            if (linePts.length >= 2) {
                Coordinate startPt = linePts[0];
                Coordinate endPt = linePts[linePts.length - 1];
                Node nStart = getNode(startPt);
                Node nEnd = getNode(endPt);
                new PolygonizeDirectedEdge(nStart, nEnd, linePts[1], true);
                new PolygonizeDirectedEdge(nEnd, nStart, linePts[linePts.length - 2], false);
                new PolygonizeEdge(line);
                Edge edge2 = edge;
                edge2.setDirectedEdges(de0, de1);
                add(edge2);
            }
        }
    }

    private Node getNode(Coordinate coordinate) {
        Node node;
        Coordinate pt = coordinate;
        Node node2 = findNode(pt);
        if (node2 == null) {
            new Node(pt);
            node2 = node;
            add(node2);
        }
        return node2;
    }

    private void computeNextCWEdges() {
        Iterator iNode = nodeIterator();
        while (iNode.hasNext()) {
            computeNextCWEdges((Node) iNode.next());
        }
    }

    private void convertMaximalToMinimalEdgeRings(List ringEdges) {
        Iterator i = ringEdges.iterator();
        while (i.hasNext()) {
            PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) i.next();
            long label = de.getLabel();
            List<Node> intNodes = findIntersectionNodes(de, label);
            if (intNodes != null) {
                for (Node node : intNodes) {
                    computeNextCCWEdges(node, label);
                }
            }
        }
    }

    private static List findIntersectionNodes(PolygonizeDirectedEdge polygonizeDirectedEdge, long j) {
        List list;
        PolygonizeDirectedEdge startDE = polygonizeDirectedEdge;
        long label = j;
        PolygonizeDirectedEdge de = startDE;
        List intNodes = null;
        do {
            Node node = de.getFromNode();
            if (getDegree(node, label) > 1) {
                if (intNodes == null) {
                    new ArrayList();
                    intNodes = list;
                }
                boolean add = intNodes.add(node);
            }
            de = de.getNext();
            Assert.isTrue(de != null, "found null DE in ring");
            Assert.isTrue(de == startDE || !de.isInRing(), "found DE already in ring");
        } while (de != startDE);
        return intNodes;
    }

    public List getEdgeRings() {
        List list;
        computeNextCWEdges();
        label(this.dirEdges, -1);
        convertMaximalToMinimalEdgeRings(findLabeledEdgeRings(this.dirEdges));
        new ArrayList();
        List edgeRingList = list;
        for (PolygonizeDirectedEdge de : this.dirEdges) {
            if (!de.isMarked() && !de.isInRing()) {
                boolean add = edgeRingList.add(findEdgeRing(de));
            }
        }
        return edgeRingList;
    }

    private static List findLabeledEdgeRings(Collection dirEdges) {
        List list;
        new ArrayList();
        List edgeRingStarts = list;
        long currLabel = 1;
        Iterator i = dirEdges.iterator();
        while (i.hasNext()) {
            PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) i.next();
            if (!de.isMarked() && de.getLabel() < 0) {
                boolean add = edgeRingStarts.add(de);
                label(EdgeRing.findDirEdgesInRing(de), currLabel);
                currLabel++;
            }
        }
        return edgeRingStarts;
    }

    public List deleteCutEdges() {
        List list;
        computeNextCWEdges();
        List findLabeledEdgeRings = findLabeledEdgeRings(this.dirEdges);
        new ArrayList();
        List cutLines = list;
        for (PolygonizeDirectedEdge de : this.dirEdges) {
            if (!de.isMarked()) {
                PolygonizeDirectedEdge sym = (PolygonizeDirectedEdge) de.getSym();
                if (de.getLabel() == sym.getLabel()) {
                    de.setMarked(true);
                    sym.setMarked(true);
                    boolean add = cutLines.add(((PolygonizeEdge) de.getEdge()).getLine());
                }
            }
        }
        return cutLines;
    }

    private static void label(Collection dirEdges, long j) {
        long label = j;
        Iterator i = dirEdges.iterator();
        while (i.hasNext()) {
            ((PolygonizeDirectedEdge) i.next()).setLabel(label);
        }
    }

    private static void computeNextCWEdges(Node node) {
        PolygonizeDirectedEdge startDE = null;
        PolygonizeDirectedEdge prevDE = null;
        for (PolygonizeDirectedEdge outDE : node.getOutEdges().getEdges()) {
            if (!outDE.isMarked()) {
                if (startDE == null) {
                    startDE = outDE;
                }
                if (prevDE != null) {
                    ((PolygonizeDirectedEdge) prevDE.getSym()).setNext(outDE);
                }
                prevDE = outDE;
            }
        }
        if (prevDE != null) {
            ((PolygonizeDirectedEdge) prevDE.getSym()).setNext(startDE);
        }
    }

    private static void computeNextCCWEdges(Node node, long j) {
        long label = j;
        PolygonizeDirectedEdge firstOutDE = null;
        PolygonizeDirectedEdge prevInDE = null;
        List edges = node.getOutEdges().getEdges();
        for (int i = edges.size() - 1; i >= 0; i--) {
            PolygonizeDirectedEdge de = (PolygonizeDirectedEdge) edges.get(i);
            PolygonizeDirectedEdge sym = (PolygonizeDirectedEdge) de.getSym();
            PolygonizeDirectedEdge outDE = null;
            if (de.getLabel() == label) {
                outDE = de;
            }
            PolygonizeDirectedEdge inDE = null;
            if (sym.getLabel() == label) {
                inDE = sym;
            }
            if (outDE != null || inDE != null) {
                if (inDE != null) {
                    prevInDE = inDE;
                }
                if (outDE != null) {
                    if (prevInDE != null) {
                        prevInDE.setNext(outDE);
                        prevInDE = null;
                    }
                    if (firstOutDE == null) {
                        firstOutDE = outDE;
                    }
                }
            }
        }
        if (prevInDE != null) {
            Assert.isTrue(firstOutDE != null);
            prevInDE.setNext(firstOutDE);
        }
    }

    private EdgeRing findEdgeRing(PolygonizeDirectedEdge startDE) {
        EdgeRing edgeRing;
        new EdgeRing(this.factory);
        EdgeRing er = edgeRing;
        er.build(startDE);
        return er;
    }

    public Collection deleteDangles() {
        Set set;
        Stack stack;
        List<Object> nodesToRemove = findNodesOfDegree(1);
        new HashSet();
        Set dangleLines = set;
        new Stack();
        Stack nodeStack = stack;
        for (Object push : nodesToRemove) {
            Object push2 = nodeStack.push(push);
        }
        while (!nodeStack.isEmpty()) {
            Node node = (Node) nodeStack.pop();
            deleteAllEdges(node);
            for (PolygonizeDirectedEdge de : node.getOutEdges().getEdges()) {
                de.setMarked(true);
                PolygonizeDirectedEdge sym = (PolygonizeDirectedEdge) de.getSym();
                if (sym != null) {
                    sym.setMarked(true);
                }
                boolean add = dangleLines.add(((PolygonizeEdge) de.getEdge()).getLine());
                Node toNode = de.getToNode();
                if (getDegreeNonDeleted(toNode) == 1) {
                    Object push3 = nodeStack.push(toNode);
                }
            }
        }
        return dangleLines;
    }

    public void computeDepthParity() {
        while (0 != 0) {
            computeDepthParity((PolygonizeDirectedEdge) null);
        }
    }

    private void computeDepthParity(PolygonizeDirectedEdge de) {
    }
}
