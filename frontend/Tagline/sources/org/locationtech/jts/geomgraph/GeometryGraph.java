package org.locationtech.jts.geomgraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.locationtech.jts.algorithm.BoundaryNodeRule;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.PointLocator;
import org.locationtech.jts.algorithm.locate.IndexedPointInAreaLocator;
import org.locationtech.jts.algorithm.locate.PointOnGeometryLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.geomgraph.index.EdgeSetIntersector;
import org.locationtech.jts.geomgraph.index.SegmentIntersector;
import org.locationtech.jts.geomgraph.index.SimpleMCSweepLineIntersector;
import org.locationtech.jts.util.Assert;

public class GeometryGraph extends PlanarGraph {
    private PointOnGeometryLocator areaPtLocator;
    private int argIndex;
    private BoundaryNodeRule boundaryNodeRule;
    private Collection boundaryNodes;
    private boolean hasTooFewPoints;
    private Coordinate invalidPoint;
    private Map lineEdgeMap;
    private Geometry parentGeom;
    private final PointLocator ptLocator;
    private boolean useBoundaryDeterminationRule;

    public static int determineBoundary(BoundaryNodeRule boundaryNodeRule2, int boundaryCount) {
        return boundaryNodeRule2.isInBoundary(boundaryCount) ? 1 : 0;
    }

    private EdgeSetIntersector createEdgeSetIntersector() {
        EdgeSetIntersector edgeSetIntersector;
        new SimpleMCSweepLineIntersector();
        return edgeSetIntersector;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public GeometryGraph(int argIndex2, Geometry parentGeom2) {
        this(argIndex2, parentGeom2, BoundaryNodeRule.OGC_SFS_BOUNDARY_RULE);
    }

    public GeometryGraph(int argIndex2, Geometry geometry, BoundaryNodeRule boundaryNodeRule2) {
        Map map;
        PointLocator pointLocator;
        Geometry parentGeom2 = geometry;
        new HashMap();
        this.lineEdgeMap = map;
        this.boundaryNodeRule = null;
        this.useBoundaryDeterminationRule = true;
        this.hasTooFewPoints = false;
        this.invalidPoint = null;
        this.areaPtLocator = null;
        new PointLocator();
        this.ptLocator = pointLocator;
        this.argIndex = argIndex2;
        this.parentGeom = parentGeom2;
        this.boundaryNodeRule = boundaryNodeRule2;
        if (parentGeom2 != null) {
            add(parentGeom2);
        }
    }

    public boolean hasTooFewPoints() {
        return this.hasTooFewPoints;
    }

    public Coordinate getInvalidPoint() {
        return this.invalidPoint;
    }

    public Geometry getGeometry() {
        return this.parentGeom;
    }

    public BoundaryNodeRule getBoundaryNodeRule() {
        return this.boundaryNodeRule;
    }

    public Collection getBoundaryNodes() {
        if (this.boundaryNodes == null) {
            this.boundaryNodes = this.nodes.getBoundaryNodes(this.argIndex);
        }
        return this.boundaryNodes;
    }

    public Coordinate[] getBoundaryPoints() {
        Collection<Node> coll = getBoundaryNodes();
        Coordinate[] pts = new Coordinate[coll.size()];
        int i = 0;
        for (Node node : coll) {
            int i2 = i;
            i++;
            pts[i2] = (Coordinate) node.getCoordinate().clone();
        }
        return pts;
    }

    public Edge findEdge(LineString line) {
        return (Edge) this.lineEdgeMap.get(line);
    }

    public void computeSplitEdges(List list) {
        List edgelist = list;
        for (Edge e : this.edges) {
            e.eiList.addSplitEdges(edgelist);
        }
    }

    private void add(Geometry geometry) {
        Throwable th;
        Geometry g = geometry;
        if (!g.isEmpty()) {
            if (g instanceof MultiPolygon) {
                this.useBoundaryDeterminationRule = false;
            }
            if (g instanceof Polygon) {
                addPolygon((Polygon) g);
            } else if (g instanceof LineString) {
                addLineString((LineString) g);
            } else if (g instanceof Point) {
                addPoint((Point) g);
            } else if (g instanceof MultiPoint) {
                addCollection((MultiPoint) g);
            } else if (g instanceof MultiLineString) {
                addCollection((MultiLineString) g);
            } else if (g instanceof MultiPolygon) {
                addCollection((MultiPolygon) g);
            } else if (g instanceof GeometryCollection) {
                addCollection((GeometryCollection) g);
            } else {
                Throwable th2 = th;
                new UnsupportedOperationException(g.getClass().getName());
                throw th2;
            }
        }
    }

    private void addCollection(GeometryCollection geometryCollection) {
        GeometryCollection gc = geometryCollection;
        for (int i = 0; i < gc.getNumGeometries(); i++) {
            add(gc.getGeometryN(i));
        }
    }

    private void addPoint(Point p) {
        insertPoint(this.argIndex, p.getCoordinate(), 0);
    }

    private void addPolygonRing(LinearRing linearRing, int i, int i2) {
        Edge edge;
        Label label;
        LinearRing lr = linearRing;
        int cwLeft = i;
        int cwRight = i2;
        if (!lr.isEmpty()) {
            Coordinate[] coord = CoordinateArrays.removeRepeatedPoints(lr.getCoordinates());
            if (coord.length < 4) {
                this.hasTooFewPoints = true;
                this.invalidPoint = coord[0];
                return;
            }
            int left = cwLeft;
            int right = cwRight;
            if (CGAlgorithms.isCCW(coord)) {
                left = cwRight;
                right = cwLeft;
            }
            new Label(this.argIndex, 1, left, right);
            new Edge(coord, label);
            Edge e = edge;
            Object put = this.lineEdgeMap.put(lr, e);
            insertEdge(e);
            insertPoint(this.argIndex, coord[0], 1);
        }
    }

    private void addPolygon(Polygon polygon) {
        Polygon p = polygon;
        addPolygonRing((LinearRing) p.getExteriorRing(), 2, 0);
        for (int i = 0; i < p.getNumInteriorRing(); i++) {
            addPolygonRing((LinearRing) p.getInteriorRingN(i), 0, 2);
        }
    }

    private void addLineString(LineString lineString) {
        Edge edge;
        Label label;
        LineString line = lineString;
        Coordinate[] coord = CoordinateArrays.removeRepeatedPoints(line.getCoordinates());
        if (coord.length < 2) {
            this.hasTooFewPoints = true;
            this.invalidPoint = coord[0];
            return;
        }
        new Label(this.argIndex, 0);
        new Edge(coord, label);
        Edge e = edge;
        Object put = this.lineEdgeMap.put(line, e);
        insertEdge(e);
        Assert.isTrue(coord.length >= 2, "found LineString with single point");
        insertBoundaryPoint(this.argIndex, coord[0]);
        insertBoundaryPoint(this.argIndex, coord[coord.length - 1]);
    }

    public void addEdge(Edge edge) {
        Edge e = edge;
        insertEdge(e);
        Coordinate[] coord = e.getCoordinates();
        insertPoint(this.argIndex, coord[0], 1);
        insertPoint(this.argIndex, coord[coord.length - 1], 1);
    }

    public void addPoint(Coordinate pt) {
        insertPoint(this.argIndex, pt, 0);
    }

    public SegmentIntersector computeSelfNodes(LineIntersector li, boolean computeRingSelfNodes) {
        return computeSelfNodes(li, computeRingSelfNodes, false);
    }

    public SegmentIntersector computeSelfNodes(LineIntersector li, boolean z, boolean isDoneIfProperInt) {
        SegmentIntersector segmentIntersector;
        boolean computeRingSelfNodes = z;
        new SegmentIntersector(li, true, false);
        SegmentIntersector si = segmentIntersector;
        si.setIsDoneIfProperInt(isDoneIfProperInt);
        createEdgeSetIntersector().computeIntersections(this.edges, si, computeRingSelfNodes || !((this.parentGeom instanceof LinearRing) || (this.parentGeom instanceof Polygon) || (this.parentGeom instanceof MultiPolygon)));
        addSelfIntersectionNodes(this.argIndex);
        return si;
    }

    public SegmentIntersector computeEdgeIntersections(GeometryGraph geometryGraph, LineIntersector li, boolean includeProper) {
        SegmentIntersector segmentIntersector;
        GeometryGraph g = geometryGraph;
        new SegmentIntersector(li, includeProper, true);
        SegmentIntersector si = segmentIntersector;
        si.setBoundaryNodes(getBoundaryNodes(), g.getBoundaryNodes());
        createEdgeSetIntersector().computeIntersections(this.edges, g.edges, si);
        return si;
    }

    private void insertPoint(int i, Coordinate coord, int i2) {
        Label label;
        int argIndex2 = i;
        int onLocation = i2;
        Node n = this.nodes.addNode(coord);
        Label lbl = n.getLabel();
        if (lbl == null) {
            new Label(argIndex2, onLocation);
            n.label = label;
            return;
        }
        lbl.setLocation(argIndex2, onLocation);
    }

    private void insertBoundaryPoint(int i, Coordinate coord) {
        int argIndex2 = i;
        Label lbl = this.nodes.addNode(coord).getLabel();
        int boundaryCount = 1;
        if (lbl.getLocation(argIndex2, 0) == 1) {
            boundaryCount = 1 + 1;
        }
        lbl.setLocation(argIndex2, determineBoundary(this.boundaryNodeRule, boundaryCount));
    }

    private void addSelfIntersectionNodes(int i) {
        int argIndex2 = i;
        for (Edge e : this.edges) {
            int eLoc = e.getLabel().getLocation(argIndex2);
            Iterator eiIt = e.eiList.iterator();
            while (eiIt.hasNext()) {
                addSelfIntersectionNode(argIndex2, ((EdgeIntersection) eiIt.next()).coord, eLoc);
            }
        }
    }

    private void addSelfIntersectionNode(int i, Coordinate coordinate, int i2) {
        int argIndex2 = i;
        Coordinate coord = coordinate;
        int loc = i2;
        if (!isBoundaryNode(argIndex2, coord)) {
            if (loc != 1 || !this.useBoundaryDeterminationRule) {
                insertPoint(argIndex2, coord, loc);
            } else {
                insertBoundaryPoint(argIndex2, coord);
            }
        }
    }

    public int locate(Coordinate coordinate) {
        PointOnGeometryLocator pointOnGeometryLocator;
        Coordinate pt = coordinate;
        if (!(this.parentGeom instanceof Polygonal) || this.parentGeom.getNumGeometries() <= 50) {
            return this.ptLocator.locate(pt, this.parentGeom);
        }
        if (this.areaPtLocator == null) {
            new IndexedPointInAreaLocator(this.parentGeom);
            this.areaPtLocator = pointOnGeometryLocator;
        }
        return this.areaPtLocator.locate(pt);
    }
}
