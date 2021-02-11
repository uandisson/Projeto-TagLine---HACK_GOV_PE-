package org.locationtech.jts.operation.overlay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.TopologyException;
import org.locationtech.jts.geomgraph.DirectedEdge;
import org.locationtech.jts.geomgraph.EdgeRing;
import org.locationtech.jts.geomgraph.PlanarGraph;
import org.locationtech.jts.util.Assert;

public class PolygonBuilder {
    private GeometryFactory geometryFactory;
    private List shellList;

    public PolygonBuilder(GeometryFactory geometryFactory2) {
        List list;
        new ArrayList();
        this.shellList = list;
        this.geometryFactory = geometryFactory2;
    }

    public void add(PlanarGraph planarGraph) {
        PlanarGraph graph = planarGraph;
        add(graph.getEdgeEnds(), graph.getNodes());
    }

    public void add(Collection dirEdges, Collection nodes) {
        List list;
        PlanarGraph.linkResultDirectedEdges(nodes);
        List maxEdgeRings = buildMaximalEdgeRings(dirEdges);
        new ArrayList();
        List freeHoleList = list;
        sortShellsAndHoles(buildMinimalEdgeRings(maxEdgeRings, this.shellList, freeHoleList), this.shellList, freeHoleList);
        placeFreeHoles(this.shellList, freeHoleList);
    }

    public List getPolygons() {
        return computePolygons(this.shellList);
    }

    private List buildMaximalEdgeRings(Collection dirEdges) {
        List list;
        MaximalEdgeRing maximalEdgeRing;
        new ArrayList();
        List maxEdgeRings = list;
        Iterator it = dirEdges.iterator();
        while (it.hasNext()) {
            DirectedEdge de = (DirectedEdge) it.next();
            if (de.isInResult() && de.getLabel().isArea() && de.getEdgeRing() == null) {
                new MaximalEdgeRing(de, this.geometryFactory);
                MaximalEdgeRing er = maximalEdgeRing;
                boolean add = maxEdgeRings.add(er);
                er.setInResult();
            }
        }
        return maxEdgeRings;
    }

    private List buildMinimalEdgeRings(List maxEdgeRings, List list, List list2) {
        List list3;
        List shellList2 = list;
        List freeHoleList = list2;
        new ArrayList();
        List edgeRings = list3;
        Iterator it = maxEdgeRings.iterator();
        while (it.hasNext()) {
            MaximalEdgeRing er = (MaximalEdgeRing) it.next();
            if (er.getMaxNodeDegree() > 2) {
                er.linkDirectedEdgesForMinimalEdgeRings();
                List minEdgeRings = er.buildMinimalRings();
                EdgeRing shell = findShell(minEdgeRings);
                if (shell != null) {
                    placePolygonHoles(shell, minEdgeRings);
                    boolean add = shellList2.add(shell);
                } else {
                    boolean addAll = freeHoleList.addAll(minEdgeRings);
                }
            } else {
                boolean add2 = edgeRings.add(er);
            }
        }
        return edgeRings;
    }

    private EdgeRing findShell(List minEdgeRings) {
        int shellCount = 0;
        EdgeRing shell = null;
        Iterator it = minEdgeRings.iterator();
        while (it.hasNext()) {
            EdgeRing er = (MinimalEdgeRing) it.next();
            if (!er.isHole()) {
                shell = er;
                shellCount++;
            }
        }
        Assert.isTrue(shellCount <= 1, "found two shells in MinimalEdgeRing list");
        return shell;
    }

    private void placePolygonHoles(EdgeRing edgeRing, List minEdgeRings) {
        EdgeRing shell = edgeRing;
        Iterator it = minEdgeRings.iterator();
        while (it.hasNext()) {
            MinimalEdgeRing er = (MinimalEdgeRing) it.next();
            if (er.isHole()) {
                er.setShell(shell);
            }
        }
    }

    private void sortShellsAndHoles(List edgeRings, List list, List list2) {
        List shellList2 = list;
        List freeHoleList = list2;
        Iterator it = edgeRings.iterator();
        while (it.hasNext()) {
            EdgeRing er = (EdgeRing) it.next();
            if (er.isHole()) {
                boolean add = freeHoleList.add(er);
            } else {
                boolean add2 = shellList2.add(er);
            }
        }
    }

    private void placeFreeHoles(List list, List freeHoleList) {
        Throwable th;
        List shellList2 = list;
        Iterator it = freeHoleList.iterator();
        while (it.hasNext()) {
            EdgeRing hole = (EdgeRing) it.next();
            if (hole.getShell() == null) {
                EdgeRing shell = findEdgeRingContaining(hole, shellList2);
                if (shell == null) {
                    Throwable th2 = th;
                    new TopologyException("unable to assign hole to a shell", hole.getCoordinate(0));
                    throw th2;
                }
                hole.setShell(shell);
            }
        }
    }

    private EdgeRing findEdgeRingContaining(EdgeRing testEr, List shellList2) {
        LinearRing testRing = testEr.getLinearRing();
        Envelope testEnv = testRing.getEnvelopeInternal();
        Coordinate testPt = testRing.getCoordinateN(0);
        EdgeRing minShell = null;
        Envelope minEnv = null;
        Iterator it = shellList2.iterator();
        while (it.hasNext()) {
            EdgeRing tryShell = (EdgeRing) it.next();
            LinearRing tryRing = tryShell.getLinearRing();
            Envelope tryEnv = tryRing.getEnvelopeInternal();
            if (minShell != null) {
                minEnv = minShell.getLinearRing().getEnvelopeInternal();
            }
            boolean isContained = false;
            if (tryEnv.contains(testEnv) && CGAlgorithms.isPointInRing(testPt, tryRing.getCoordinates())) {
                isContained = true;
            }
            if (isContained && (minShell == null || minEnv.contains(tryEnv))) {
                minShell = tryShell;
            }
        }
        return minShell;
    }

    private List computePolygons(List shellList2) {
        List list;
        new ArrayList();
        List resultPolyList = list;
        Iterator it = shellList2.iterator();
        while (it.hasNext()) {
            boolean add = resultPolyList.add(((EdgeRing) it.next()).toPolygon(this.geometryFactory));
        }
        return resultPolyList;
    }

    public boolean containsPoint(Coordinate coordinate) {
        Coordinate p = coordinate;
        for (EdgeRing er : this.shellList) {
            if (er.containsPoint(p)) {
                return true;
            }
        }
        return false;
    }
}
