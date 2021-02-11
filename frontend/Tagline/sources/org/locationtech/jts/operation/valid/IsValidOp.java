package org.locationtech.jts.operation.valid;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.MCPointInRing;
import org.locationtech.jts.algorithm.PointInRing;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.EdgeIntersection;
import org.locationtech.jts.geomgraph.EdgeIntersectionList;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.geomgraph.index.SegmentIntersector;
import org.locationtech.jts.util.Assert;

public class IsValidOp {
    private boolean isSelfTouchingRingFormingHoleValid = false;
    private Geometry parentGeometry;
    private TopologyValidationError validErr;

    public static boolean isValid(Geometry geom) {
        IsValidOp isValidOp;
        new IsValidOp(geom);
        return isValidOp.isValid();
    }

    public static boolean isValid(Coordinate coordinate) {
        Coordinate coord = coordinate;
        if (Double.isNaN(coord.f412x)) {
            return false;
        }
        if (Double.isInfinite(coord.f412x)) {
            return false;
        }
        if (Double.isNaN(coord.f413y)) {
            return false;
        }
        if (Double.isInfinite(coord.f413y)) {
            return false;
        }
        return true;
    }

    public static Coordinate findPtNotNode(Coordinate[] coordinateArr, LinearRing searchRing, GeometryGraph graph) {
        Coordinate[] testCoords = coordinateArr;
        EdgeIntersectionList eiList = graph.findEdge(searchRing).getEdgeIntersectionList();
        for (int i = 0; i < testCoords.length; i++) {
            Coordinate pt = testCoords[i];
            if (!eiList.isIntersection(pt)) {
                return pt;
            }
        }
        return null;
    }

    public IsValidOp(Geometry parentGeometry2) {
        this.parentGeometry = parentGeometry2;
    }

    public void setSelfTouchingRingFormingHoleValid(boolean isValid) {
        boolean z = isValid;
        this.isSelfTouchingRingFormingHoleValid = z;
    }

    public boolean isValid() {
        checkValid(this.parentGeometry);
        return this.validErr == null;
    }

    public TopologyValidationError getValidationError() {
        checkValid(this.parentGeometry);
        return this.validErr;
    }

    private void checkValid(Geometry geometry) {
        Throwable th;
        Geometry g = geometry;
        this.validErr = null;
        if (!g.isEmpty()) {
            if (g instanceof Point) {
                checkValid((Point) g);
            } else if (g instanceof MultiPoint) {
                checkValid((MultiPoint) g);
            } else if (g instanceof LinearRing) {
                checkValid((LinearRing) g);
            } else if (g instanceof LineString) {
                checkValid((LineString) g);
            } else if (g instanceof Polygon) {
                checkValid((Polygon) g);
            } else if (g instanceof MultiPolygon) {
                checkValid((MultiPolygon) g);
            } else if (g instanceof GeometryCollection) {
                checkValid((GeometryCollection) g);
            } else {
                Throwable th2 = th;
                new UnsupportedOperationException(g.getClass().getName());
                throw th2;
            }
        }
    }

    private void checkValid(Point g) {
        checkInvalidCoordinates(g.getCoordinates());
    }

    private void checkValid(MultiPoint g) {
        checkInvalidCoordinates(g.getCoordinates());
    }

    private void checkValid(LineString lineString) {
        GeometryGraph graph;
        LineString g = lineString;
        checkInvalidCoordinates(g.getCoordinates());
        if (this.validErr == null) {
            new GeometryGraph(0, g);
            checkTooFewPoints(graph);
        }
    }

    private void checkValid(LinearRing linearRing) {
        GeometryGraph geometryGraph;
        LineIntersector li;
        LinearRing g = linearRing;
        checkInvalidCoordinates(g.getCoordinates());
        if (this.validErr == null) {
            checkClosedRing(g);
            if (this.validErr == null) {
                new GeometryGraph(0, g);
                GeometryGraph graph = geometryGraph;
                checkTooFewPoints(graph);
                if (this.validErr == null) {
                    new RobustLineIntersector();
                    SegmentIntersector computeSelfNodes = graph.computeSelfNodes(li, true, true);
                    checkNoSelfIntersectingRings(graph);
                }
            }
        }
    }

    private void checkValid(Polygon polygon) {
        GeometryGraph geometryGraph;
        Polygon g = polygon;
        checkInvalidCoordinates(g);
        if (this.validErr == null) {
            checkClosedRings(g);
            if (this.validErr == null) {
                new GeometryGraph(0, g);
                GeometryGraph graph = geometryGraph;
                checkTooFewPoints(graph);
                if (this.validErr == null) {
                    checkConsistentArea(graph);
                    if (this.validErr == null) {
                        if (!this.isSelfTouchingRingFormingHoleValid) {
                            checkNoSelfIntersectingRings(graph);
                            if (this.validErr != null) {
                                return;
                            }
                        }
                        checkHolesInShell(g, graph);
                        if (this.validErr == null) {
                            checkHolesNotNested(g, graph);
                            if (this.validErr == null) {
                                checkConnectedInteriors(graph);
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkValid(MultiPolygon multiPolygon) {
        GeometryGraph geometryGraph;
        MultiPolygon g = multiPolygon;
        int i = 0;
        while (i < g.getNumGeometries()) {
            Polygon p = (Polygon) g.getGeometryN(i);
            checkInvalidCoordinates(p);
            if (this.validErr == null) {
                checkClosedRings(p);
                if (this.validErr == null) {
                    i++;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
        new GeometryGraph(0, g);
        GeometryGraph graph = geometryGraph;
        checkTooFewPoints(graph);
        if (this.validErr == null) {
            checkConsistentArea(graph);
            if (this.validErr == null) {
                if (!this.isSelfTouchingRingFormingHoleValid) {
                    checkNoSelfIntersectingRings(graph);
                    if (this.validErr != null) {
                        return;
                    }
                }
                int i2 = 0;
                while (i2 < g.getNumGeometries()) {
                    checkHolesInShell((Polygon) g.getGeometryN(i2), graph);
                    if (this.validErr == null) {
                        i2++;
                    } else {
                        return;
                    }
                }
                int i3 = 0;
                while (i3 < g.getNumGeometries()) {
                    checkHolesNotNested((Polygon) g.getGeometryN(i3), graph);
                    if (this.validErr == null) {
                        i3++;
                    } else {
                        return;
                    }
                }
                checkShellsNotNested(g, graph);
                if (this.validErr == null) {
                    checkConnectedInteriors(graph);
                }
            }
        }
    }

    private void checkValid(GeometryCollection geometryCollection) {
        GeometryCollection gc = geometryCollection;
        int i = 0;
        while (i < gc.getNumGeometries()) {
            checkValid(gc.getGeometryN(i));
            if (this.validErr == null) {
                i++;
            } else {
                return;
            }
        }
    }

    private void checkInvalidCoordinates(Coordinate[] coordinateArr) {
        TopologyValidationError topologyValidationError;
        Coordinate[] coords = coordinateArr;
        for (int i = 0; i < coords.length; i++) {
            if (!isValid(coords[i])) {
                new TopologyValidationError(10, coords[i]);
                this.validErr = topologyValidationError;
                return;
            }
        }
    }

    private void checkInvalidCoordinates(Polygon polygon) {
        Polygon poly = polygon;
        checkInvalidCoordinates(poly.getExteriorRing().getCoordinates());
        if (this.validErr == null) {
            int i = 0;
            while (i < poly.getNumInteriorRing()) {
                checkInvalidCoordinates(poly.getInteriorRingN(i).getCoordinates());
                if (this.validErr == null) {
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private void checkClosedRings(Polygon polygon) {
        Polygon poly = polygon;
        checkClosedRing((LinearRing) poly.getExteriorRing());
        if (this.validErr == null) {
            int i = 0;
            while (i < poly.getNumInteriorRing()) {
                checkClosedRing((LinearRing) poly.getInteriorRingN(i));
                if (this.validErr == null) {
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private void checkClosedRing(LinearRing linearRing) {
        TopologyValidationError topologyValidationError;
        LinearRing ring = linearRing;
        if (!ring.isClosed()) {
            Coordinate pt = null;
            if (ring.getNumPoints() >= 1) {
                pt = ring.getCoordinateN(0);
            }
            new TopologyValidationError(11, pt);
            this.validErr = topologyValidationError;
        }
    }

    private void checkTooFewPoints(GeometryGraph geometryGraph) {
        TopologyValidationError topologyValidationError;
        GeometryGraph graph = geometryGraph;
        if (graph.hasTooFewPoints()) {
            new TopologyValidationError(9, graph.getInvalidPoint());
            this.validErr = topologyValidationError;
        }
    }

    private void checkConsistentArea(GeometryGraph graph) {
        ConsistentAreaTester consistentAreaTester;
        TopologyValidationError topologyValidationError;
        TopologyValidationError topologyValidationError2;
        new ConsistentAreaTester(graph);
        ConsistentAreaTester cat = consistentAreaTester;
        if (!cat.isNodeConsistentArea()) {
            new TopologyValidationError(5, cat.getInvalidPoint());
            this.validErr = topologyValidationError2;
        } else if (cat.hasDuplicateRings()) {
            new TopologyValidationError(8, cat.getInvalidPoint());
            this.validErr = topologyValidationError;
        }
    }

    private void checkNoSelfIntersectingRings(GeometryGraph graph) {
        Iterator i = graph.getEdgeIterator();
        while (i.hasNext()) {
            checkNoSelfIntersectingRing(((Edge) i.next()).getEdgeIntersectionList());
            if (this.validErr != null) {
                return;
            }
        }
    }

    private void checkNoSelfIntersectingRing(EdgeIntersectionList eiList) {
        Set set;
        TopologyValidationError topologyValidationError;
        new TreeSet();
        Set nodeSet = set;
        boolean isFirst = true;
        Iterator i = eiList.iterator();
        while (i.hasNext()) {
            EdgeIntersection ei = (EdgeIntersection) i.next();
            if (isFirst) {
                isFirst = false;
            } else if (nodeSet.contains(ei.coord)) {
                new TopologyValidationError(6, ei.coord);
                this.validErr = topologyValidationError;
                return;
            } else {
                boolean add = nodeSet.add(ei.coord);
            }
        }
    }

    private void checkHolesInShell(Polygon polygon, GeometryGraph geometryGraph) {
        PointInRing pointInRing;
        Coordinate holePt;
        TopologyValidationError topologyValidationError;
        Polygon p = polygon;
        GeometryGraph graph = geometryGraph;
        LinearRing shell = (LinearRing) p.getExteriorRing();
        new MCPointInRing(shell);
        PointInRing pir = pointInRing;
        int i = 0;
        while (i < p.getNumInteriorRing() && (holePt = findPtNotNode(((LinearRing) p.getInteriorRingN(i)).getCoordinates(), shell, graph)) != null) {
            if (!pir.isInside(holePt)) {
                new TopologyValidationError(2, holePt);
                this.validErr = topologyValidationError;
                return;
            }
            i++;
        }
    }

    private void checkHolesNotNested(Polygon polygon, GeometryGraph graph) {
        IndexedNestedRingTester indexedNestedRingTester;
        TopologyValidationError topologyValidationError;
        Polygon p = polygon;
        new IndexedNestedRingTester(graph);
        IndexedNestedRingTester nestedTester = indexedNestedRingTester;
        for (int i = 0; i < p.getNumInteriorRing(); i++) {
            nestedTester.add((LinearRing) p.getInteriorRingN(i));
        }
        if (!nestedTester.isNonNested()) {
            new TopologyValidationError(3, nestedTester.getNestedPoint());
            this.validErr = topologyValidationError;
        }
    }

    private void checkShellsNotNested(MultiPolygon multiPolygon, GeometryGraph geometryGraph) {
        MultiPolygon mp = multiPolygon;
        GeometryGraph graph = geometryGraph;
        for (int i = 0; i < mp.getNumGeometries(); i++) {
            LinearRing shell = (LinearRing) ((Polygon) mp.getGeometryN(i)).getExteriorRing();
            for (int j = 0; j < mp.getNumGeometries(); j++) {
                if (i != j) {
                    checkShellNotNested(shell, (Polygon) mp.getGeometryN(j), graph);
                    if (this.validErr != null) {
                        return;
                    }
                }
            }
        }
    }

    private void checkShellNotNested(LinearRing linearRing, Polygon polygon, GeometryGraph geometryGraph) {
        TopologyValidationError topologyValidationError;
        TopologyValidationError topologyValidationError2;
        LinearRing shell = linearRing;
        Polygon p = polygon;
        GeometryGraph graph = geometryGraph;
        Coordinate[] shellPts = shell.getCoordinates();
        LinearRing polyShell = (LinearRing) p.getExteriorRing();
        Coordinate[] polyPts = polyShell.getCoordinates();
        Coordinate shellPt = findPtNotNode(shellPts, polyShell, graph);
        if (shellPt == null || !CGAlgorithms.isPointInRing(shellPt, polyPts)) {
            return;
        }
        if (p.getNumInteriorRing() <= 0) {
            new TopologyValidationError(7, shellPt);
            this.validErr = topologyValidationError2;
            return;
        }
        Coordinate badNestedPt = null;
        int i = 0;
        while (i < p.getNumInteriorRing()) {
            badNestedPt = checkShellInsideHole(shell, (LinearRing) p.getInteriorRingN(i), graph);
            if (badNestedPt != null) {
                i++;
            } else {
                return;
            }
        }
        new TopologyValidationError(7, badNestedPt);
        this.validErr = topologyValidationError;
    }

    private Coordinate checkShellInsideHole(LinearRing linearRing, LinearRing linearRing2, GeometryGraph geometryGraph) {
        LinearRing shell = linearRing;
        LinearRing hole = linearRing2;
        GeometryGraph graph = geometryGraph;
        Coordinate[] shellPts = shell.getCoordinates();
        Coordinate[] holePts = hole.getCoordinates();
        Coordinate shellPt = findPtNotNode(shellPts, hole, graph);
        if (shellPt != null && !CGAlgorithms.isPointInRing(shellPt, holePts)) {
            return shellPt;
        }
        Coordinate holePt = findPtNotNode(holePts, shell, graph);
        if (holePt == null) {
            Assert.shouldNeverReachHere("points in shell and hole appear to be equal");
            return null;
        } else if (CGAlgorithms.isPointInRing(holePt, shellPts)) {
            return holePt;
        } else {
            return null;
        }
    }

    private void checkConnectedInteriors(GeometryGraph graph) {
        ConnectedInteriorTester connectedInteriorTester;
        TopologyValidationError topologyValidationError;
        new ConnectedInteriorTester(graph);
        ConnectedInteriorTester cit = connectedInteriorTester;
        if (!cit.isInteriorsConnected()) {
            new TopologyValidationError(4, cit.getCoordinate());
            this.validErr = topologyValidationError;
        }
    }
}
