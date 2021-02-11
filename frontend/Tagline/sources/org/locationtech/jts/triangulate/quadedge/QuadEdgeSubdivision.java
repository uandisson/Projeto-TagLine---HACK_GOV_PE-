package org.locationtech.jts.triangulate.quadedge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.Triangle;
import org.locationtech.jts.p006io.WKTWriter;

public class QuadEdgeSubdivision {
    private static final double EDGE_COINCIDENCE_TOL_FACTOR = 1000.0d;
    private double edgeCoincidenceTolerance;
    private Envelope frameEnv;
    private Vertex[] frameVertex;
    private QuadEdgeLocator locator;
    private List quadEdges;
    private LineSegment seg;
    private QuadEdge startingEdge;
    private double tolerance;
    private QuadEdge[] triEdges;
    private int visitedKey = 0;

    public static void getTriangleEdges(QuadEdge startQE, QuadEdge[] quadEdgeArr) {
        Throwable th;
        QuadEdge[] triEdge = quadEdgeArr;
        triEdge[0] = startQE;
        triEdge[1] = triEdge[0].lNext();
        triEdge[2] = triEdge[1].lNext();
        if (triEdge[2].lNext() != triEdge[0]) {
            Throwable th2 = th;
            new IllegalArgumentException("Edges do not form a triangle");
            throw th2;
        }
    }

    public QuadEdgeSubdivision(Envelope env, double d) {
        List list;
        LineSegment lineSegment;
        QuadEdgeLocator quadEdgeLocator;
        double tolerance2 = d;
        new ArrayList();
        this.quadEdges = list;
        this.frameVertex = new Vertex[3];
        this.locator = null;
        new LineSegment();
        this.seg = lineSegment;
        this.triEdges = new QuadEdge[3];
        this.tolerance = tolerance2;
        this.edgeCoincidenceTolerance = tolerance2 / EDGE_COINCIDENCE_TOL_FACTOR;
        createFrame(env);
        this.startingEdge = initSubdiv();
        new LastFoundQuadEdgeLocator(this);
        this.locator = quadEdgeLocator;
    }

    private void createFrame(Envelope envelope) {
        double offset;
        Vertex vertex;
        Vertex vertex2;
        Vertex vertex3;
        Envelope envelope2;
        Envelope env = envelope;
        double deltaX = env.getWidth();
        double deltaY = env.getHeight();
        if (deltaX > deltaY) {
            offset = deltaX * 10.0d;
        } else {
            offset = deltaY * 10.0d;
        }
        new Vertex((env.getMaxX() + env.getMinX()) / 2.0d, env.getMaxY() + offset);
        this.frameVertex[0] = vertex;
        new Vertex(env.getMinX() - offset, env.getMinY() - offset);
        this.frameVertex[1] = vertex2;
        new Vertex(env.getMaxX() + offset, env.getMinY() - offset);
        this.frameVertex[2] = vertex3;
        new Envelope(this.frameVertex[0].getCoordinate(), this.frameVertex[1].getCoordinate());
        this.frameEnv = envelope2;
        this.frameEnv.expandToInclude(this.frameVertex[2].getCoordinate());
    }

    private QuadEdge initSubdiv() {
        QuadEdge ea = makeEdge(this.frameVertex[0], this.frameVertex[1]);
        QuadEdge eb = makeEdge(this.frameVertex[1], this.frameVertex[2]);
        QuadEdge.splice(ea.sym(), eb);
        QuadEdge ec = makeEdge(this.frameVertex[2], this.frameVertex[0]);
        QuadEdge.splice(eb.sym(), ec);
        QuadEdge.splice(ec.sym(), ea);
        return ea;
    }

    public double getTolerance() {
        return this.tolerance;
    }

    public Envelope getEnvelope() {
        Envelope envelope;
        new Envelope(this.frameEnv);
        return envelope;
    }

    public Collection getEdges() {
        return this.quadEdges;
    }

    public void setLocator(QuadEdgeLocator locator2) {
        QuadEdgeLocator quadEdgeLocator = locator2;
        this.locator = quadEdgeLocator;
    }

    public QuadEdge makeEdge(Vertex o, Vertex d) {
        QuadEdge q = QuadEdge.makeEdge(o, d);
        boolean add = this.quadEdges.add(q);
        return q;
    }

    public QuadEdge connect(QuadEdge a, QuadEdge b) {
        QuadEdge q = QuadEdge.connect(a, b);
        boolean add = this.quadEdges.add(q);
        return q;
    }

    public void delete(QuadEdge quadEdge) {
        QuadEdge e = quadEdge;
        QuadEdge.splice(e, e.oPrev());
        QuadEdge.splice(e.sym(), e.sym().oPrev());
        QuadEdge eSym = e.sym();
        QuadEdge eRot = e.rot();
        QuadEdge eRotSym = e.rot().sym();
        boolean remove = this.quadEdges.remove(e);
        boolean remove2 = this.quadEdges.remove(eSym);
        boolean remove3 = this.quadEdges.remove(eRot);
        boolean remove4 = this.quadEdges.remove(eRotSym);
        e.delete();
        eSym.delete();
        eRot.delete();
        eRotSym.delete();
    }

    public QuadEdge locateFromEdge(Vertex vertex, QuadEdge startEdge) {
        QuadEdge e;
        Throwable th;
        Vertex v = vertex;
        int iter = 0;
        int maxIter = this.quadEdges.size();
        QuadEdge quadEdge = startEdge;
        while (true) {
            e = quadEdge;
            iter++;
            if (iter <= maxIter) {
                if (!v.equals(e.orig()) && !v.equals(e.dest())) {
                    if (!v.rightOf(e)) {
                        if (v.rightOf(e.oNext())) {
                            if (v.rightOf(e.dPrev())) {
                                break;
                            }
                            quadEdge = e.dPrev();
                        } else {
                            quadEdge = e.oNext();
                        }
                    } else {
                        quadEdge = e.sym();
                    }
                } else {
                    break;
                }
            } else {
                Throwable th2 = th;
                new LocateFailureException(e.toLineSegment());
                throw th2;
            }
        }
        return e;
    }

    public QuadEdge locate(Vertex v) {
        return this.locator.locate(v);
    }

    public QuadEdge locate(Coordinate p) {
        Vertex vertex;
        new Vertex(p);
        return this.locator.locate(vertex);
    }

    public QuadEdge locate(Coordinate coordinate, Coordinate coordinate2) {
        Vertex vertex;
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        new Vertex(p0);
        QuadEdge e = this.locator.locate(vertex);
        if (e == null) {
            return null;
        }
        QuadEdge base = e;
        if (e.dest().getCoordinate().equals2D(p0)) {
            base = e.sym();
        }
        QuadEdge locEdge = base;
        while (!locEdge.dest().getCoordinate().equals2D(p1)) {
            locEdge = locEdge.oNext();
            if (locEdge == base) {
                return null;
            }
        }
        return locEdge;
    }

    public QuadEdge insertSite(Vertex vertex) {
        Vertex v = vertex;
        QuadEdge e = locate(v);
        if (v.equals(e.orig(), this.tolerance) || v.equals(e.dest(), this.tolerance)) {
            return e;
        }
        QuadEdge base = makeEdge(e.orig(), v);
        QuadEdge.splice(base, e);
        QuadEdge startEdge = base;
        do {
            base = connect(e, base.sym());
            e = base.oPrev();
        } while (e.lNext() != startEdge);
        return startEdge;
    }

    public boolean isFrameEdge(QuadEdge quadEdge) {
        QuadEdge e = quadEdge;
        if (isFrameVertex(e.orig()) || isFrameVertex(e.dest())) {
            return true;
        }
        return false;
    }

    public boolean isFrameBorderEdge(QuadEdge quadEdge) {
        QuadEdge e = quadEdge;
        getTriangleEdges(e, new QuadEdge[3]);
        getTriangleEdges(e.sym(), new QuadEdge[3]);
        if (isFrameVertex(e.lNext().dest())) {
            return true;
        }
        if (isFrameVertex(e.sym().lNext().dest())) {
            return true;
        }
        return false;
    }

    public boolean isFrameVertex(Vertex vertex) {
        Vertex v = vertex;
        if (v.equals(this.frameVertex[0])) {
            return true;
        }
        if (v.equals(this.frameVertex[1])) {
            return true;
        }
        if (v.equals(this.frameVertex[2])) {
            return true;
        }
        return false;
    }

    public boolean isOnEdge(QuadEdge quadEdge, Coordinate p) {
        QuadEdge e = quadEdge;
        this.seg.setCoordinates(e.orig().getCoordinate(), e.dest().getCoordinate());
        return this.seg.distance(p) < this.edgeCoincidenceTolerance;
    }

    public boolean isVertexOfEdge(QuadEdge quadEdge, Vertex vertex) {
        QuadEdge e = quadEdge;
        Vertex v = vertex;
        if (v.equals(e.orig(), this.tolerance) || v.equals(e.dest(), this.tolerance)) {
            return true;
        }
        return false;
    }

    public Collection getVertices(boolean z) {
        Set set;
        boolean includeFrame = z;
        new HashSet();
        Set vertices = set;
        for (QuadEdge qe : this.quadEdges) {
            Vertex v = qe.orig();
            if (includeFrame || !isFrameVertex(v)) {
                boolean add = vertices.add(v);
            }
            Vertex vd = qe.dest();
            if (includeFrame || !isFrameVertex(vd)) {
                boolean add2 = vertices.add(vd);
            }
        }
        return vertices;
    }

    public List getVertexUniqueEdges(boolean z) {
        List list;
        Set set;
        boolean includeFrame = z;
        new ArrayList();
        List edges = list;
        new HashSet();
        Set visitedVertices = set;
        for (QuadEdge qe : this.quadEdges) {
            Vertex v = qe.orig();
            if (!visitedVertices.contains(v)) {
                boolean add = visitedVertices.add(v);
                if (includeFrame || !isFrameVertex(v)) {
                    boolean add2 = edges.add(qe);
                }
            }
            QuadEdge qd = qe.sym();
            Vertex vd = qd.orig();
            if (!visitedVertices.contains(vd)) {
                boolean add3 = visitedVertices.add(vd);
                if (includeFrame || !isFrameVertex(vd)) {
                    boolean add4 = edges.add(qd);
                }
            }
        }
        return edges;
    }

    public List getPrimaryEdges(boolean z) {
        List list;
        Stack stack;
        Set set;
        boolean includeFrame = z;
        this.visitedKey++;
        new ArrayList();
        List edges = list;
        new Stack();
        Stack edgeStack = stack;
        Object push = edgeStack.push(this.startingEdge);
        new HashSet();
        Set visitedEdges = set;
        while (!edgeStack.empty()) {
            QuadEdge edge = (QuadEdge) edgeStack.pop();
            if (!visitedEdges.contains(edge)) {
                QuadEdge priQE = edge.getPrimary();
                if (includeFrame || !isFrameEdge(priQE)) {
                    boolean add = edges.add(priQE);
                }
                Object push2 = edgeStack.push(edge.oNext());
                Object push3 = edgeStack.push(edge.sym().oNext());
                boolean add2 = visitedEdges.add(edge);
                boolean add3 = visitedEdges.add(edge.sym());
            }
        }
        return edges;
    }

    private static class TriangleCircumcentreVisitor implements TriangleVisitor {
        public TriangleCircumcentreVisitor() {
        }

        public void visit(QuadEdge[] quadEdgeArr) {
            Vertex vertex;
            QuadEdge[] triEdges = quadEdgeArr;
            new Vertex(Triangle.circumcentre(triEdges[0].orig().getCoordinate(), triEdges[1].orig().getCoordinate(), triEdges[2].orig().getCoordinate()));
            Vertex ccVertex = vertex;
            for (int i = 0; i < 3; i++) {
                triEdges[i].rot().setOrig(ccVertex);
            }
        }
    }

    public void visitTriangles(TriangleVisitor triangleVisitor, boolean z) {
        Stack stack;
        Set set;
        QuadEdge[] triEdges2;
        TriangleVisitor triVisitor = triangleVisitor;
        boolean includeFrame = z;
        this.visitedKey++;
        new Stack();
        Stack edgeStack = stack;
        Object push = edgeStack.push(this.startingEdge);
        new HashSet();
        Set visitedEdges = set;
        while (!edgeStack.empty()) {
            QuadEdge edge = (QuadEdge) edgeStack.pop();
            if (!visitedEdges.contains(edge) && (triEdges2 = fetchTriangleToVisit(edge, edgeStack, includeFrame, visitedEdges)) != null) {
                triVisitor.visit(triEdges2);
            }
        }
    }

    private QuadEdge[] fetchTriangleToVisit(QuadEdge quadEdge, Stack stack, boolean z, Set set) {
        QuadEdge edge = quadEdge;
        Stack edgeStack = stack;
        boolean includeFrame = z;
        Set visitedEdges = set;
        QuadEdge curr = edge;
        int edgeCount = 0;
        boolean isFrame = false;
        do {
            this.triEdges[edgeCount] = curr;
            if (isFrameEdge(curr)) {
                isFrame = true;
            }
            QuadEdge sym = curr.sym();
            if (!visitedEdges.contains(sym)) {
                Object push = edgeStack.push(sym);
            }
            boolean add = visitedEdges.add(curr);
            edgeCount++;
            curr = curr.lNext();
        } while (curr != edge);
        if (!isFrame || includeFrame) {
            return this.triEdges;
        }
        return null;
    }

    public List getTriangleEdges(boolean includeFrame) {
        TriangleEdgesListVisitor triangleEdgesListVisitor;
        new TriangleEdgesListVisitor((C15751) null);
        TriangleEdgesListVisitor visitor = triangleEdgesListVisitor;
        visitTriangles(visitor, includeFrame);
        return visitor.getTriangleEdges();
    }

    private static class TriangleEdgesListVisitor implements TriangleVisitor {
        private List triList;

        private TriangleEdgesListVisitor() {
            List list;
            new ArrayList();
            this.triList = list;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ TriangleEdgesListVisitor(C15751 r4) {
            this();
            C15751 r1 = r4;
        }

        public void visit(QuadEdge[] triEdges) {
            boolean add = this.triList.add(triEdges.clone());
        }

        public List getTriangleEdges() {
            return this.triList;
        }
    }

    public List getTriangleVertices(boolean includeFrame) {
        TriangleVertexListVisitor triangleVertexListVisitor;
        new TriangleVertexListVisitor((C15751) null);
        TriangleVertexListVisitor visitor = triangleVertexListVisitor;
        visitTriangles(visitor, includeFrame);
        return visitor.getTriangleVertices();
    }

    private static class TriangleVertexListVisitor implements TriangleVisitor {
        private List triList;

        private TriangleVertexListVisitor() {
            List list;
            new ArrayList();
            this.triList = list;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ TriangleVertexListVisitor(C15751 r4) {
            this();
            C15751 r1 = r4;
        }

        public void visit(QuadEdge[] quadEdgeArr) {
            QuadEdge[] triEdges = quadEdgeArr;
            List list = this.triList;
            Vertex[] vertexArr = new Vertex[3];
            vertexArr[0] = triEdges[0].orig();
            Vertex[] vertexArr2 = vertexArr;
            vertexArr2[1] = triEdges[1].orig();
            Vertex[] vertexArr3 = vertexArr2;
            vertexArr3[2] = triEdges[2].orig();
            boolean add = list.add(vertexArr3);
        }

        public List getTriangleVertices() {
            return this.triList;
        }
    }

    public List getTriangleCoordinates(boolean includeFrame) {
        TriangleCoordinatesVisitor triangleCoordinatesVisitor;
        new TriangleCoordinatesVisitor();
        TriangleCoordinatesVisitor visitor = triangleCoordinatesVisitor;
        visitTriangles(visitor, includeFrame);
        return visitor.getTriangles();
    }

    private static class TriangleCoordinatesVisitor implements TriangleVisitor {
        private CoordinateList coordList;
        private List triCoords;

        public TriangleCoordinatesVisitor() {
            CoordinateList coordinateList;
            List list;
            new CoordinateList();
            this.coordList = coordinateList;
            new ArrayList();
            this.triCoords = list;
        }

        public void visit(QuadEdge[] quadEdgeArr) {
            QuadEdge[] triEdges = quadEdgeArr;
            this.coordList.clear();
            for (int i = 0; i < 3; i++) {
                boolean add = this.coordList.add(triEdges[i].orig().getCoordinate());
            }
            if (this.coordList.size() > 0) {
                this.coordList.closeRing();
                Coordinate[] pts = this.coordList.toCoordinateArray();
                if (pts.length == 4) {
                    boolean add2 = this.triCoords.add(pts);
                }
            }
        }

        private void checkTriangleSize(Coordinate[] coordinateArr) {
            Coordinate[] pts = coordinateArr;
            Object obj = "";
            if (pts.length >= 2) {
                String loc = WKTWriter.toLineString(pts[0], pts[1]);
            } else if (pts.length >= 1) {
                String loc2 = WKTWriter.toPoint(pts[0]);
            }
        }

        public List getTriangles() {
            return this.triCoords;
        }
    }

    public Geometry getEdges(GeometryFactory geometryFactory) {
        GeometryFactory geomFact = geometryFactory;
        List<QuadEdge> quadEdges2 = getPrimaryEdges(false);
        LineString[] edges = new LineString[quadEdges2.size()];
        int i = 0;
        for (QuadEdge qe : quadEdges2) {
            int i2 = i;
            i++;
            Coordinate[] coordinateArr = new Coordinate[2];
            coordinateArr[0] = qe.orig().getCoordinate();
            Coordinate[] coordinateArr2 = coordinateArr;
            coordinateArr2[1] = qe.dest().getCoordinate();
            edges[i2] = geomFact.createLineString(coordinateArr2);
        }
        return geomFact.createMultiLineString(edges);
    }

    public Geometry getTriangles(GeometryFactory geometryFactory) {
        GeometryFactory geomFact = geometryFactory;
        List<Coordinate[]> triPtsList = getTriangleCoordinates(false);
        Polygon[] tris = new Polygon[triPtsList.size()];
        int i = 0;
        for (Coordinate[] triPt : triPtsList) {
            int i2 = i;
            i++;
            tris[i2] = geomFact.createPolygon(geomFact.createLinearRing(triPt), (LinearRing[]) null);
        }
        return geomFact.createGeometryCollection(tris);
    }

    public Geometry getVoronoiDiagram(GeometryFactory geometryFactory) {
        GeometryFactory geomFact = geometryFactory;
        return geomFact.createGeometryCollection(GeometryFactory.toGeometryArray(getVoronoiCellPolygons(geomFact)));
    }

    public List getVoronoiCellPolygons(GeometryFactory geometryFactory) {
        TriangleVisitor triangleVisitor;
        List list;
        GeometryFactory geomFact = geometryFactory;
        new TriangleCircumcentreVisitor();
        visitTriangles(triangleVisitor, true);
        new ArrayList();
        List cells = list;
        for (QuadEdge qe : getVertexUniqueEdges(false)) {
            boolean add = cells.add(getVoronoiCellPolygon(qe, geomFact));
        }
        return cells;
    }

    public Polygon getVoronoiCellPolygon(QuadEdge quadEdge, GeometryFactory geometryFactory) {
        List list;
        CoordinateList coordinateList;
        QuadEdge qe = quadEdge;
        GeometryFactory geomFact = geometryFactory;
        new ArrayList();
        List cellPts = list;
        QuadEdge startQE = qe;
        do {
            boolean add = cellPts.add(qe.rot().orig().getCoordinate());
            qe = qe.oPrev();
        } while (qe != startQE);
        new CoordinateList();
        CoordinateList coordList = coordinateList;
        boolean addAll = coordList.addAll(cellPts, false);
        coordList.closeRing();
        if (coordList.size() < 4) {
            System.out.println(coordList);
            boolean add2 = coordList.add(coordList.get(coordList.size() - 1), true);
        }
        Polygon cellPoly = geomFact.createPolygon(geomFact.createLinearRing(coordList.toCoordinateArray()), (LinearRing[]) null);
        cellPoly.setUserData(startQE.orig().getCoordinate());
        return cellPoly;
    }
}
