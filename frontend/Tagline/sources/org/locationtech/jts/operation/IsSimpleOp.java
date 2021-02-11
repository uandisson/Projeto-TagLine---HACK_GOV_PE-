package org.locationtech.jts.operation;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.locationtech.jts.algorithm.BoundaryNodeRule;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.geom.util.LinearComponentExtracter;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.EdgeIntersection;
import org.locationtech.jts.geomgraph.GeometryGraph;
import org.locationtech.jts.geomgraph.index.SegmentIntersector;

public class IsSimpleOp {
    private Geometry inputGeom;
    private boolean isClosedEndpointsInInterior = true;
    private Coordinate nonSimpleLocation = null;

    public IsSimpleOp() {
    }

    public IsSimpleOp(Geometry geom) {
        this.inputGeom = geom;
    }

    public IsSimpleOp(Geometry geom, BoundaryNodeRule boundaryNodeRule) {
        this.inputGeom = geom;
        this.isClosedEndpointsInInterior = !boundaryNodeRule.isInBoundary(2);
    }

    public boolean isSimple() {
        this.nonSimpleLocation = null;
        return computeSimple(this.inputGeom);
    }

    private boolean computeSimple(Geometry geometry) {
        Geometry geom = geometry;
        this.nonSimpleLocation = null;
        if (geom.isEmpty()) {
            return true;
        }
        if (geom instanceof LineString) {
            return isSimpleLinearGeometry(geom);
        }
        if (geom instanceof MultiLineString) {
            return isSimpleLinearGeometry(geom);
        }
        if (geom instanceof MultiPoint) {
            return isSimpleMultiPoint((MultiPoint) geom);
        }
        if (geom instanceof Polygonal) {
            return isSimplePolygonal(geom);
        }
        if (geom instanceof GeometryCollection) {
            return isSimpleGeometryCollection(geom);
        }
        return true;
    }

    public Coordinate getNonSimpleLocation() {
        return this.nonSimpleLocation;
    }

    public boolean isSimple(LineString geom) {
        return isSimpleLinearGeometry(geom);
    }

    public boolean isSimple(MultiLineString geom) {
        return isSimpleLinearGeometry(geom);
    }

    public boolean isSimple(MultiPoint mp) {
        return isSimpleMultiPoint(mp);
    }

    private boolean isSimpleMultiPoint(MultiPoint multiPoint) {
        Set set;
        MultiPoint mp = multiPoint;
        if (mp.isEmpty()) {
            return true;
        }
        new TreeSet();
        Set points = set;
        for (int i = 0; i < mp.getNumGeometries(); i++) {
            Coordinate p = ((Point) mp.getGeometryN(i)).getCoordinate();
            if (points.contains(p)) {
                this.nonSimpleLocation = p;
                return false;
            }
            boolean add = points.add(p);
        }
        return true;
    }

    private boolean isSimplePolygonal(Geometry geom) {
        for (LinearRing ring : LinearComponentExtracter.getLines(geom)) {
            if (!isSimpleLinearGeometry(ring)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSimpleGeometryCollection(Geometry geometry) {
        Geometry geom = geometry;
        for (int i = 0; i < geom.getNumGeometries(); i++) {
            if (!computeSimple(geom.getGeometryN(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isSimpleLinearGeometry(Geometry geometry) {
        GeometryGraph geometryGraph;
        LineIntersector li;
        Geometry geom = geometry;
        if (geom.isEmpty()) {
            return true;
        }
        new GeometryGraph(0, geom);
        GeometryGraph graph = geometryGraph;
        new RobustLineIntersector();
        SegmentIntersector si = graph.computeSelfNodes(li, true);
        if (!si.hasIntersection()) {
            return true;
        }
        if (si.hasProperIntersection()) {
            this.nonSimpleLocation = si.getProperIntersectionPoint();
            return false;
        } else if (hasNonEndpointIntersection(graph)) {
            return false;
        } else {
            if (!this.isClosedEndpointsInInterior || !hasClosedEndpointIntersection(graph)) {
                return true;
            }
            return false;
        }
    }

    private boolean hasNonEndpointIntersection(GeometryGraph graph) {
        Iterator i = graph.getEdgeIterator();
        while (i.hasNext()) {
            Edge e = (Edge) i.next();
            int maxSegmentIndex = e.getMaximumSegmentIndex();
            Iterator eiIt = e.getEdgeIntersectionList().iterator();
            while (true) {
                if (eiIt.hasNext()) {
                    EdgeIntersection ei = (EdgeIntersection) eiIt.next();
                    if (!ei.isEndPoint(maxSegmentIndex)) {
                        this.nonSimpleLocation = ei.getCoordinate();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static class EndpointInfo {
        int degree = 0;
        boolean isClosed = false;

        /* renamed from: pt */
        Coordinate f479pt;

        public EndpointInfo(Coordinate pt) {
            this.f479pt = pt;
        }

        public Coordinate getCoordinate() {
            return this.f479pt;
        }

        public void addEndpoint(boolean isClosed2) {
            this.degree++;
            this.isClosed |= isClosed2;
        }
    }

    private boolean hasClosedEndpointIntersection(GeometryGraph graph) {
        Map map;
        new TreeMap();
        Map endPoints = map;
        Iterator i = graph.getEdgeIterator();
        while (i.hasNext()) {
            Edge e = (Edge) i.next();
            int maximumSegmentIndex = e.getMaximumSegmentIndex();
            boolean isClosed = e.isClosed();
            addEndpoint(endPoints, e.getCoordinate(0), isClosed);
            addEndpoint(endPoints, e.getCoordinate(e.getNumPoints() - 1), isClosed);
        }
        for (EndpointInfo eiInfo : endPoints.values()) {
            if (eiInfo.isClosed && eiInfo.degree != 2) {
                this.nonSimpleLocation = eiInfo.getCoordinate();
                return true;
            }
        }
        return false;
    }

    private void addEndpoint(Map map, Coordinate coordinate, boolean z) {
        EndpointInfo endpointInfo;
        Map endPoints = map;
        Coordinate p = coordinate;
        boolean isClosed = z;
        EndpointInfo eiInfo = (EndpointInfo) endPoints.get(p);
        if (eiInfo == null) {
            new EndpointInfo(p);
            eiInfo = endpointInfo;
            Object put = endPoints.put(p, eiInfo);
        }
        eiInfo.addEndpoint(isClosed);
    }
}
