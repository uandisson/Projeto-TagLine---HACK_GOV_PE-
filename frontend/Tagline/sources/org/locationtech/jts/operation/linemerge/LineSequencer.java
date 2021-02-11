package org.locationtech.jts.operation.linemerge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryComponentFilter;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.planargraph.DirectedEdge;
import org.locationtech.jts.planargraph.GraphComponent;
import org.locationtech.jts.planargraph.Node;
import org.locationtech.jts.planargraph.Subgraph;
import org.locationtech.jts.planargraph.algorithm.ConnectedSubgraphFinder;
import org.locationtech.jts.util.Assert;

public class LineSequencer {
    private GeometryFactory factory;
    private LineMergeGraph graph;
    private boolean isRun = false;
    private boolean isSequenceable = false;
    private int lineCount = 0;
    private Geometry sequencedGeometry = null;

    public LineSequencer() {
        LineMergeGraph lineMergeGraph;
        GeometryFactory geometryFactory;
        new LineMergeGraph();
        this.graph = lineMergeGraph;
        new GeometryFactory();
        this.factory = geometryFactory;
    }

    public static Geometry sequence(Geometry geom) {
        LineSequencer lineSequencer;
        new LineSequencer();
        LineSequencer sequencer = lineSequencer;
        sequencer.add(geom);
        return sequencer.getSequencedLineStrings();
    }

    public static boolean isSequenced(Geometry geometry) {
        Set set;
        List list;
        Geometry geom = geometry;
        if (!(geom instanceof MultiLineString)) {
            return true;
        }
        MultiLineString mls = (MultiLineString) geom;
        new TreeSet();
        Set prevSubgraphNodes = set;
        Coordinate lastNode = null;
        new ArrayList();
        List currNodes = list;
        for (int i = 0; i < mls.getNumGeometries(); i++) {
            LineString line = (LineString) mls.getGeometryN(i);
            Coordinate startNode = line.getCoordinateN(0);
            Coordinate endNode = line.getCoordinateN(line.getNumPoints() - 1);
            if (prevSubgraphNodes.contains(startNode)) {
                return false;
            }
            if (prevSubgraphNodes.contains(endNode)) {
                return false;
            }
            if (lastNode != null && !startNode.equals(lastNode)) {
                boolean addAll = prevSubgraphNodes.addAll(currNodes);
                currNodes.clear();
            }
            boolean add = currNodes.add(startNode);
            boolean add2 = currNodes.add(endNode);
            lastNode = endNode;
        }
        return true;
    }

    public void add(Collection geometries) {
        Iterator i = geometries.iterator();
        while (i.hasNext()) {
            add((Geometry) i.next());
        }
    }

    public void add(Geometry geometry) {
        GeometryComponentFilter geometryComponentFilter;
        new GeometryComponentFilter(this) {
            final /* synthetic */ LineSequencer this$0;

            {
                this.this$0 = this$0;
            }

            public void filter(Geometry geometry) {
                Geometry component = geometry;
                if (component instanceof LineString) {
                    this.this$0.addLine((LineString) component);
                }
            }
        };
        geometry.apply(geometryComponentFilter);
    }

    /* access modifiers changed from: private */
    public void addLine(LineString lineString) {
        LineString lineString2 = lineString;
        if (this.factory == null) {
            this.factory = lineString2.getFactory();
        }
        this.graph.addEdge(lineString2);
        this.lineCount++;
    }

    public boolean isSequenceable() {
        computeSequence();
        return this.isSequenceable;
    }

    public Geometry getSequencedLineStrings() {
        computeSequence();
        return this.sequencedGeometry;
    }

    private void computeSequence() {
        if (!this.isRun) {
            this.isRun = true;
            List sequences = findSequences();
            if (sequences != null) {
                this.sequencedGeometry = buildSequencedGeometry(sequences);
                this.isSequenceable = true;
                Assert.isTrue(this.lineCount == this.sequencedGeometry.getNumGeometries(), "Lines were missing from result");
                Assert.isTrue((this.sequencedGeometry instanceof LineString) || (this.sequencedGeometry instanceof MultiLineString), "Result is not lineal");
            }
        }
    }

    private List findSequences() {
        List list;
        ConnectedSubgraphFinder csFinder;
        new ArrayList();
        List sequences = list;
        new ConnectedSubgraphFinder(this.graph);
        for (Subgraph subgraph : csFinder.getConnectedSubgraphs()) {
            if (!hasSequence(subgraph)) {
                return null;
            }
            boolean add = sequences.add(findSequence(subgraph));
        }
        return sequences;
    }

    private boolean hasSequence(Subgraph graph2) {
        int oddDegreeCount = 0;
        Iterator i = graph2.nodeIterator();
        while (i.hasNext()) {
            if (((Node) i.next()).getDegree() % 2 == 1) {
                oddDegreeCount++;
            }
        }
        return oddDegreeCount <= 2;
    }

    private List findSequence(Subgraph subgraph) {
        List list;
        Subgraph graph2 = subgraph;
        GraphComponent.setVisited(graph2.edgeIterator(), false);
        DirectedEdge startDESym = ((DirectedEdge) findLowestDegreeNode(graph2).getOutEdges().iterator().next()).getSym();
        new LinkedList();
        List seq = list;
        ListIterator lit = seq.listIterator();
        addReverseSubpath(startDESym, lit, false);
        while (lit.hasPrevious()) {
            DirectedEdge unvisitedOutDE = findUnvisitedBestOrientedDE(((DirectedEdge) lit.previous()).getFromNode());
            if (unvisitedOutDE != null) {
                addReverseSubpath(unvisitedOutDE.getSym(), lit, true);
            }
        }
        return orient(seq);
    }

    private static DirectedEdge findUnvisitedBestOrientedDE(Node node) {
        DirectedEdge wellOrientedDE = null;
        DirectedEdge unvisitedDE = null;
        Iterator i = node.getOutEdges().iterator();
        while (i.hasNext()) {
            DirectedEdge de = (DirectedEdge) i.next();
            if (!de.getEdge().isVisited()) {
                unvisitedDE = de;
                if (de.getEdgeDirection()) {
                    wellOrientedDE = de;
                }
            }
        }
        if (wellOrientedDE != null) {
            return wellOrientedDE;
        }
        return unvisitedDE;
    }

    private void addReverseSubpath(DirectedEdge directedEdge, ListIterator listIterator, boolean z) {
        Node fromNode;
        DirectedEdge de = directedEdge;
        ListIterator lit = listIterator;
        boolean expectedClosed = z;
        Node endNode = de.getToNode();
        while (true) {
            lit.add(de.getSym());
            de.getEdge().setVisited(true);
            fromNode = de.getFromNode();
            DirectedEdge unvisitedOutDE = findUnvisitedBestOrientedDE(fromNode);
            if (unvisitedOutDE == null) {
                break;
            }
            de = unvisitedOutDE.getSym();
        }
        if (expectedClosed) {
            Assert.isTrue(fromNode == endNode, "path not contiguous");
        }
    }

    private static Node findLowestDegreeNode(Subgraph graph2) {
        int minDegree = Integer.MAX_VALUE;
        Node minDegreeNode = null;
        Iterator i = graph2.nodeIterator();
        while (i.hasNext()) {
            Node node = (Node) i.next();
            if (minDegreeNode == null || node.getDegree() < minDegree) {
                minDegree = node.getDegree();
                minDegreeNode = node;
            }
        }
        return minDegreeNode;
    }

    private List orient(List list) {
        List seq = list;
        DirectedEdge startEdge = (DirectedEdge) seq.get(0);
        DirectedEdge endEdge = (DirectedEdge) seq.get(seq.size() - 1);
        boolean flipSeq = false;
        if (startEdge.getFromNode().getDegree() == 1 || endEdge.getToNode().getDegree() == 1) {
            boolean hasObviousStartNode = false;
            if (endEdge.getToNode().getDegree() == 1 && !endEdge.getEdgeDirection()) {
                hasObviousStartNode = true;
                flipSeq = true;
            }
            if (startEdge.getFromNode().getDegree() == 1 && startEdge.getEdgeDirection()) {
                hasObviousStartNode = true;
                flipSeq = false;
            }
            if (!hasObviousStartNode && startEdge.getFromNode().getDegree() == 1) {
                flipSeq = true;
            }
        }
        if (flipSeq) {
            return reverse(seq);
        }
        return seq;
    }

    private List reverse(List seq) {
        LinkedList linkedList;
        new LinkedList();
        LinkedList newSeq = linkedList;
        Iterator i = seq.iterator();
        while (i.hasNext()) {
            newSeq.addFirst(((DirectedEdge) i.next()).getSym());
        }
        return newSeq;
    }

    private Geometry buildSequencedGeometry(List sequences) {
        List list;
        new ArrayList();
        List lines = list;
        Iterator i1 = sequences.iterator();
        while (i1.hasNext()) {
            for (DirectedEdge de : (List) i1.next()) {
                LineString line = ((LineMergeEdge) de.getEdge()).getLine();
                LineString lineToAdd = line;
                if (!de.getEdgeDirection() && !line.isClosed()) {
                    lineToAdd = reverse(line);
                }
                boolean add = lines.add(lineToAdd);
            }
        }
        if (lines.size() == 0) {
            return this.factory.createMultiLineString(new LineString[0]);
        }
        return this.factory.buildGeometry(lines);
    }

    private static LineString reverse(LineString lineString) {
        Coordinate coordinate;
        LineString line = lineString;
        Coordinate[] pts = line.getCoordinates();
        Coordinate[] revPts = new Coordinate[pts.length];
        int len = pts.length;
        for (int i = 0; i < len; i++) {
            new Coordinate(pts[i]);
            revPts[(len - 1) - i] = coordinate;
        }
        return line.getFactory().createLineString(revPts);
    }
}
