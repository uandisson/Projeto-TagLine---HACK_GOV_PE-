package org.locationtech.jts.operation.valid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geomgraph.DirectedEdge;
import org.locationtech.jts.geomgraph.EdgeRing;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.geomgraph.NodeFactory;
import org.locationtech.jts.geomgraph.PlanarGraph;
import org.locationtech.jts.operation.overlay.MaximalEdgeRing;
import org.locationtech.jts.operation.overlay.OverlayNodeFactory;
import org.locationtech.jts.util.Assert;

public class ConnectedInteriorTester {
    private Coordinate disconnectedRingcoord;
    private GeometryGraph geomGraph;
    private GeometryFactory geometryFactory;

    public static Coordinate findDifferentPoint(Coordinate[] coordinateArr, Coordinate coordinate) {
        Coordinate[] coord = coordinateArr;
        Coordinate pt = coordinate;
        for (int i = 0; i < coord.length; i++) {
            if (!coord[i].equals(pt)) {
                return coord[i];
            }
        }
        return null;
    }

    public ConnectedInteriorTester(GeometryGraph geomGraph2) {
        GeometryFactory geometryFactory2;
        new GeometryFactory();
        this.geometryFactory = geometryFactory2;
        this.geomGraph = geomGraph2;
    }

    public Coordinate getCoordinate() {
        return this.disconnectedRingcoord;
    }

    public boolean isInteriorsConnected() {
        List list;
        PlanarGraph planarGraph;
        NodeFactory nodeFactory;
        new ArrayList();
        List splitEdges = list;
        this.geomGraph.computeSplitEdges(splitEdges);
        new OverlayNodeFactory();
        new PlanarGraph(nodeFactory);
        PlanarGraph graph = planarGraph;
        graph.addEdges(splitEdges);
        setInteriorEdgesInResult(graph);
        graph.linkResultDirectedEdges();
        List edgeRings = buildEdgeRings(graph.getEdgeEnds());
        visitShellInteriors(this.geomGraph.getGeometry(), graph);
        return !hasUnvisitedShellEdge(edgeRings);
    }

    private void setInteriorEdgesInResult(PlanarGraph graph) {
        for (DirectedEdge de : graph.getEdgeEnds()) {
            if (de.getLabel().getLocation(0, 2) == 0) {
                de.setInResult(true);
            }
        }
    }

    private List buildEdgeRings(Collection dirEdges) {
        List list;
        MaximalEdgeRing maximalEdgeRing;
        new ArrayList();
        List edgeRings = list;
        Iterator it = dirEdges.iterator();
        while (it.hasNext()) {
            DirectedEdge de = (DirectedEdge) it.next();
            if (de.isInResult() && de.getEdgeRing() == null) {
                new MaximalEdgeRing(de, this.geometryFactory);
                MaximalEdgeRing er = maximalEdgeRing;
                er.linkDirectedEdgesForMinimalEdgeRings();
                boolean addAll = edgeRings.addAll(er.buildMinimalRings());
            }
        }
        return edgeRings;
    }

    private void visitShellInteriors(Geometry geometry, PlanarGraph planarGraph) {
        Geometry g = geometry;
        PlanarGraph graph = planarGraph;
        if (g instanceof Polygon) {
            visitInteriorRing(((Polygon) g).getExteriorRing(), graph);
        }
        if (g instanceof MultiPolygon) {
            MultiPolygon mp = (MultiPolygon) g;
            for (int i = 0; i < mp.getNumGeometries(); i++) {
                visitInteriorRing(((Polygon) mp.getGeometryN(i)).getExteriorRing(), graph);
            }
        }
    }

    private void visitInteriorRing(LineString ring, PlanarGraph planarGraph) {
        PlanarGraph graph = planarGraph;
        Coordinate[] pts = ring.getCoordinates();
        Coordinate pt0 = pts[0];
        DirectedEdge de = (DirectedEdge) graph.findEdgeEnd(graph.findEdgeInSameDirection(pt0, findDifferentPoint(pts, pt0)));
        DirectedEdge intDe = null;
        if (de.getLabel().getLocation(0, 2) == 0) {
            intDe = de;
        } else if (de.getSym().getLabel().getLocation(0, 2) == 0) {
            intDe = de.getSym();
        }
        Assert.isTrue(intDe != null, "unable to find dirEdge with Interior on RHS");
        visitLinkedDirectedEdges(intDe);
    }

    /* access modifiers changed from: protected */
    public void visitLinkedDirectedEdges(DirectedEdge directedEdge) {
        DirectedEdge start = directedEdge;
        DirectedEdge startDe = start;
        DirectedEdge de = start;
        do {
            Assert.isTrue(de != null, "found null Directed Edge");
            de.setVisited(true);
            de = de.getNext();
        } while (de != startDe);
    }

    private boolean hasUnvisitedShellEdge(List list) {
        List edgeRings = list;
        for (int i = 0; i < edgeRings.size(); i++) {
            EdgeRing er = (EdgeRing) edgeRings.get(i);
            if (!er.isHole()) {
                List edges = er.getEdges();
                if (((DirectedEdge) edges.get(0)).getLabel().getLocation(0, 2) != 0) {
                    continue;
                } else {
                    for (int j = 0; j < edges.size(); j++) {
                        DirectedEdge de = (DirectedEdge) edges.get(j);
                        if (!de.isVisited()) {
                            this.disconnectedRingcoord = de.getCoordinate();
                            return true;
                        }
                    }
                    continue;
                }
            }
        }
        return false;
    }
}
