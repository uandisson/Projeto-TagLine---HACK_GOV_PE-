package org.locationtech.jts.triangulate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.index.kdtree.KdNode;
import org.locationtech.jts.index.kdtree.KdTree;
import org.locationtech.jts.triangulate.quadedge.LastFoundQuadEdgeLocator;
import org.locationtech.jts.triangulate.quadedge.QuadEdge;
import org.locationtech.jts.triangulate.quadedge.QuadEdgeLocator;
import org.locationtech.jts.triangulate.quadedge.QuadEdgeSubdivision;
import org.locationtech.jts.triangulate.quadedge.Vertex;
import org.locationtech.jts.util.Debug;

public class ConformingDelaunayTriangulator {
    private static final int MAX_SPLIT_ITER = 99;
    private Envelope computeAreaEnv;
    private Geometry convexHull;
    private IncrementalDelaunayTriangulator incDel;
    private List initialVertices;
    private KdTree kdt;
    private List segVertices;
    private List segments;
    private ConstraintSplitPointFinder splitFinder;
    private Coordinate splitPt;
    private QuadEdgeSubdivision subdiv = null;
    private double tolerance;
    private ConstraintVertexFactory vertexFactory;

    private static Envelope computeVertexEnvelope(Collection vertices) {
        Envelope envelope;
        new Envelope();
        Envelope env = envelope;
        Iterator i = vertices.iterator();
        while (i.hasNext()) {
            env.expandToInclude(((Vertex) i.next()).getCoordinate());
        }
        return env;
    }

    public ConformingDelaunayTriangulator(Collection initialVertices2, double d) {
        List list;
        ConstraintSplitPointFinder constraintSplitPointFinder;
        List list2;
        KdTree kdTree;
        double tolerance2 = d;
        new ArrayList();
        this.segments = list;
        new NonEncroachingSplitPointFinder();
        this.splitFinder = constraintSplitPointFinder;
        this.kdt = null;
        this.vertexFactory = null;
        this.splitPt = null;
        new ArrayList(initialVertices2);
        this.initialVertices = list2;
        this.tolerance = tolerance2;
        new KdTree(tolerance2);
        this.kdt = kdTree;
    }

    public void setConstraints(List segments2, List segVertices2) {
        this.segments = segments2;
        this.segVertices = segVertices2;
    }

    public void setSplitPointFinder(ConstraintSplitPointFinder splitFinder2) {
        ConstraintSplitPointFinder constraintSplitPointFinder = splitFinder2;
        this.splitFinder = constraintSplitPointFinder;
    }

    public double getTolerance() {
        return this.tolerance;
    }

    public ConstraintVertexFactory getVertexFactory() {
        return this.vertexFactory;
    }

    public void setVertexFactory(ConstraintVertexFactory vertexFactory2) {
        ConstraintVertexFactory constraintVertexFactory = vertexFactory2;
        this.vertexFactory = constraintVertexFactory;
    }

    public QuadEdgeSubdivision getSubdivision() {
        return this.subdiv;
    }

    public KdTree getKDT() {
        return this.kdt;
    }

    public List getInitialVertices() {
        return this.initialVertices;
    }

    public Collection getConstraintSegments() {
        return this.segments;
    }

    public Geometry getConvexHull() {
        return this.convexHull;
    }

    private void computeBoundingBox() {
        Envelope envelope;
        Envelope envelope2;
        Envelope vertexEnv = computeVertexEnvelope(this.initialVertices);
        Envelope segEnv = computeVertexEnvelope(this.segVertices);
        new Envelope(vertexEnv);
        Envelope allPointsEnv = envelope;
        allPointsEnv.expandToInclude(segEnv);
        double delta = Math.max(allPointsEnv.getWidth() * 0.2d, allPointsEnv.getHeight() * 0.2d);
        new Envelope(allPointsEnv);
        this.computeAreaEnv = envelope2;
        this.computeAreaEnv.expandBy(delta);
    }

    private void computeConvexHull() {
        GeometryFactory fact;
        ConvexHull hull;
        new GeometryFactory();
        Coordinate[] coords = getPointArray();
        new ConvexHull(coords, fact);
        this.convexHull = hull.getConvexHull();
    }

    private Coordinate[] getPointArray() {
        Coordinate[] pts = new Coordinate[(this.initialVertices.size() + this.segVertices.size())];
        int index = 0;
        for (Vertex v : this.initialVertices) {
            int i = index;
            index++;
            pts[i] = v.getCoordinate();
        }
        for (Vertex v2 : this.segVertices) {
            int i2 = index;
            index++;
            pts[i2] = v2.getCoordinate();
        }
        return pts;
    }

    private ConstraintVertex createVertex(Coordinate coordinate) {
        ConstraintVertex constraintVertex;
        ConstraintVertex v;
        Coordinate p = coordinate;
        if (this.vertexFactory != null) {
            v = this.vertexFactory.createVertex(p, (Segment) null);
        } else {
            new ConstraintVertex(p);
            v = constraintVertex;
        }
        return v;
    }

    private ConstraintVertex createVertex(Coordinate coordinate, Segment segment) {
        ConstraintVertex constraintVertex;
        ConstraintVertex v;
        Coordinate p = coordinate;
        Segment seg = segment;
        if (this.vertexFactory != null) {
            v = this.vertexFactory.createVertex(p, seg);
        } else {
            new ConstraintVertex(p);
            v = constraintVertex;
        }
        v.setOnConstraint(true);
        return v;
    }

    private void insertSites(Collection collection) {
        StringBuilder sb;
        Collection<ConstraintVertex> vertices = collection;
        new StringBuilder();
        Debug.println(sb.append("Adding sites: ").append(vertices.size()).toString());
        for (ConstraintVertex v : vertices) {
            ConstraintVertex insertSite = insertSite(v);
        }
    }

    private ConstraintVertex insertSite(ConstraintVertex constraintVertex) {
        ConstraintVertex v = constraintVertex;
        KdNode kdnode = this.kdt.insert(v.getCoordinate(), v);
        if (!kdnode.isRepeated()) {
            QuadEdge insertSite = this.incDel.insertSite(v);
            return v;
        }
        ConstraintVertex snappedV = (ConstraintVertex) kdnode.getData();
        snappedV.merge(v);
        return snappedV;
    }

    public void insertSite(Coordinate p) {
        ConstraintVertex insertSite = insertSite(createVertex(p));
    }

    public void formInitialDelaunay() {
        QuadEdgeSubdivision quadEdgeSubdivision;
        QuadEdgeLocator quadEdgeLocator;
        IncrementalDelaunayTriangulator incrementalDelaunayTriangulator;
        computeBoundingBox();
        new QuadEdgeSubdivision(this.computeAreaEnv, this.tolerance);
        this.subdiv = quadEdgeSubdivision;
        new LastFoundQuadEdgeLocator(this.subdiv);
        this.subdiv.setLocator(quadEdgeLocator);
        new IncrementalDelaunayTriangulator(this.subdiv);
        this.incDel = incrementalDelaunayTriangulator;
        insertSites(this.initialVertices);
    }

    public void enforceConstraints() {
        StringBuilder sb;
        Throwable th;
        addConstraintVertices();
        int count = 0;
        do {
            int splits = enforceGabriel(this.segments);
            count++;
            new StringBuilder();
            Debug.println(sb.append("Iter: ").append(count).append("   Splits: ").append(splits).append("   Current # segments = ").append(this.segments.size()).toString());
            if (splits <= 0 || count >= 99) {
            }
            int splits2 = enforceGabriel(this.segments);
            count++;
            new StringBuilder();
            Debug.println(sb.append("Iter: ").append(count).append("   Splits: ").append(splits2).append("   Current # segments = ").append(this.segments.size()).toString());
            break;
        } while (count >= 99);
        if (count == 99) {
            Debug.println("ABORTED! Too many iterations while enforcing constraints");
            if (!Debug.isDebugging()) {
                Throwable th2 = th;
                new ConstraintEnforcementException("Too many splitting iterations while enforcing constraints.  Last split point was at: ", this.splitPt);
                throw th2;
            }
        }
    }

    private void addConstraintVertices() {
        computeConvexHull();
        insertSites(this.segVertices);
    }

    private int enforceGabriel(Collection collection) {
        List list;
        List list2;
        Object obj;
        Object obj2;
        StringBuilder sb;
        Collection<Segment> segsToInsert = collection;
        new ArrayList();
        List newSegments = list;
        int splits = 0;
        new ArrayList();
        List segsToRemove = list2;
        for (Segment seg : segsToInsert) {
            Coordinate encroachPt = findNonGabrielPoint(seg);
            if (encroachPt != null) {
                this.splitPt = this.splitFinder.findSplitPoint(seg, encroachPt);
                ConstraintVertex splitVertex = createVertex(this.splitPt, seg);
                ConstraintVertex insertedVertex = insertSite(splitVertex);
                if (!insertedVertex.getCoordinate().equals2D(this.splitPt)) {
                    new StringBuilder();
                    Debug.println(sb.append("Split pt snapped to: ").append(insertedVertex).toString());
                }
                new Segment(seg.getStartX(), seg.getStartY(), seg.getStartZ(), splitVertex.getX(), splitVertex.getY(), splitVertex.getZ(), seg.getData());
                new Segment(splitVertex.getX(), splitVertex.getY(), splitVertex.getZ(), seg.getEndX(), seg.getEndY(), seg.getEndZ(), seg.getData());
                boolean add = newSegments.add(obj);
                boolean add2 = newSegments.add(obj2);
                boolean add3 = segsToRemove.add(seg);
                splits++;
            }
        }
        boolean removeAll = segsToInsert.removeAll(segsToRemove);
        boolean addAll = segsToInsert.addAll(newSegments);
        return splits;
    }

    private Coordinate findNonGabrielPoint(Segment segment) {
        Coordinate coordinate;
        Envelope envelope;
        Segment seg = segment;
        Coordinate p = seg.getStart();
        Coordinate q = seg.getEnd();
        new Coordinate((p.f412x + q.f412x) / 2.0d, (p.f413y + q.f413y) / 2.0d);
        Coordinate midPt = coordinate;
        double segRadius = p.distance(midPt);
        new Envelope(midPt);
        Envelope env = envelope;
        env.expandBy(segRadius);
        Coordinate closestNonGabriel = null;
        double minDist = Double.MAX_VALUE;
        for (KdNode nextNode : this.kdt.query(env)) {
            Coordinate testPt = nextNode.getCoordinate();
            if (!testPt.equals2D(p) && !testPt.equals2D(q)) {
                double testRadius = midPt.distance(testPt);
                if (testRadius < segRadius) {
                    double testDist = testRadius;
                    if (closestNonGabriel == null || testDist < minDist) {
                        closestNonGabriel = testPt;
                        minDist = testDist;
                    }
                }
            }
        }
        return closestNonGabriel;
    }
}
