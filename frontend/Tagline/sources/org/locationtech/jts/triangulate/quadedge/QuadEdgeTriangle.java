package org.locationtech.jts.triangulate.quadedge;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

public class QuadEdgeTriangle {
    private Object data;
    private QuadEdge[] edge;

    public static List createOn(QuadEdgeSubdivision subdiv) {
        QuadEdgeTriangleBuilderVisitor quadEdgeTriangleBuilderVisitor;
        new QuadEdgeTriangleBuilderVisitor();
        QuadEdgeTriangleBuilderVisitor visitor = quadEdgeTriangleBuilderVisitor;
        subdiv.visitTriangles(visitor, false);
        return visitor.getTriangles();
    }

    public static boolean contains(Vertex[] vertexArr, Coordinate pt) {
        Vertex[] tri = vertexArr;
        Coordinate[] coordinateArr = new Coordinate[4];
        coordinateArr[0] = tri[0].getCoordinate();
        Coordinate[] coordinateArr2 = coordinateArr;
        coordinateArr2[1] = tri[1].getCoordinate();
        Coordinate[] coordinateArr3 = coordinateArr2;
        coordinateArr3[2] = tri[2].getCoordinate();
        Coordinate[] ring = coordinateArr3;
        ring[3] = tri[0].getCoordinate();
        return CGAlgorithms.isPointInRing(pt, ring);
    }

    public static boolean contains(QuadEdge[] quadEdgeArr, Coordinate pt) {
        QuadEdge[] tri = quadEdgeArr;
        Coordinate[] coordinateArr = new Coordinate[4];
        coordinateArr[0] = tri[0].orig().getCoordinate();
        Coordinate[] coordinateArr2 = coordinateArr;
        coordinateArr2[1] = tri[1].orig().getCoordinate();
        Coordinate[] coordinateArr3 = coordinateArr2;
        coordinateArr3[2] = tri[2].orig().getCoordinate();
        Coordinate[] ring = coordinateArr3;
        ring[3] = tri[0].orig().getCoordinate();
        return CGAlgorithms.isPointInRing(pt, ring);
    }

    public static Geometry toPolygon(Vertex[] vertexArr) {
        GeometryFactory geometryFactory;
        Vertex[] v = vertexArr;
        Coordinate[] coordinateArr = new Coordinate[4];
        coordinateArr[0] = v[0].getCoordinate();
        Coordinate[] coordinateArr2 = coordinateArr;
        coordinateArr2[1] = v[1].getCoordinate();
        Coordinate[] coordinateArr3 = coordinateArr2;
        coordinateArr3[2] = v[2].getCoordinate();
        Coordinate[] ringPts = coordinateArr3;
        ringPts[3] = v[0].getCoordinate();
        new GeometryFactory();
        GeometryFactory fact = geometryFactory;
        return fact.createPolygon(fact.createLinearRing(ringPts), (LinearRing[]) null);
    }

    public static Geometry toPolygon(QuadEdge[] quadEdgeArr) {
        GeometryFactory geometryFactory;
        QuadEdge[] e = quadEdgeArr;
        Coordinate[] coordinateArr = new Coordinate[4];
        coordinateArr[0] = e[0].orig().getCoordinate();
        Coordinate[] coordinateArr2 = coordinateArr;
        coordinateArr2[1] = e[1].orig().getCoordinate();
        Coordinate[] coordinateArr3 = coordinateArr2;
        coordinateArr3[2] = e[2].orig().getCoordinate();
        Coordinate[] ringPts = coordinateArr3;
        ringPts[3] = e[0].orig().getCoordinate();
        new GeometryFactory();
        GeometryFactory fact = geometryFactory;
        return fact.createPolygon(fact.createLinearRing(ringPts), (LinearRing[]) null);
    }

    public static int nextIndex(int index) {
        int index2 = (index + 1) % 3;
        return index2;
    }

    public QuadEdgeTriangle(QuadEdge[] quadEdgeArr) {
        QuadEdge[] edge2 = quadEdgeArr;
        this.edge = (QuadEdge[]) edge2.clone();
        for (int i = 0; i < 3; i++) {
            edge2[i].setData(this);
        }
    }

    public void setData(Object data2) {
        Object obj = data2;
        this.data = obj;
    }

    public Object getData() {
        return this.data;
    }

    public void kill() {
        this.edge = null;
    }

    public boolean isLive() {
        return this.edge != null;
    }

    public QuadEdge[] getEdges() {
        return this.edge;
    }

    public QuadEdge getEdge(int i) {
        return this.edge[i];
    }

    public Vertex getVertex(int i) {
        return this.edge[i].orig();
    }

    public Vertex[] getVertices() {
        Vertex[] vert = new Vertex[3];
        for (int i = 0; i < 3; i++) {
            vert[i] = getVertex(i);
        }
        return vert;
    }

    public Coordinate getCoordinate(int i) {
        return this.edge[i].orig().getCoordinate();
    }

    public int getEdgeIndex(QuadEdge quadEdge) {
        QuadEdge e = quadEdge;
        for (int i = 0; i < 3; i++) {
            if (this.edge[i] == e) {
                return i;
            }
        }
        return -1;
    }

    public int getEdgeIndex(Vertex vertex) {
        Vertex v = vertex;
        for (int i = 0; i < 3; i++) {
            if (this.edge[i].orig() == v) {
                return i;
            }
        }
        return -1;
    }

    public void getEdgeSegment(int i, LineSegment lineSegment) {
        int i2 = i;
        LineSegment seg = lineSegment;
        seg.f422p0 = this.edge[i2].orig().getCoordinate();
        seg.f423p1 = this.edge[(i2 + 1) % 3].orig().getCoordinate();
    }

    public Coordinate[] getCoordinates() {
        Coordinate coordinate;
        Coordinate[] pts = new Coordinate[4];
        for (int i = 0; i < 3; i++) {
            pts[i] = this.edge[i].orig().getCoordinate();
        }
        new Coordinate(pts[0]);
        pts[3] = coordinate;
        return pts;
    }

    public boolean contains(Coordinate pt) {
        return CGAlgorithms.isPointInRing(pt, getCoordinates());
    }

    public Polygon getGeometry(GeometryFactory geometryFactory) {
        GeometryFactory fact = geometryFactory;
        return fact.createPolygon(fact.createLinearRing(getCoordinates()), (LinearRing[]) null);
    }

    public String toString() {
        GeometryFactory geometryFactory;
        new GeometryFactory();
        return getGeometry(geometryFactory).toString();
    }

    public boolean isBorder() {
        for (int i = 0; i < 3; i++) {
            if (getAdjacentTriangleAcrossEdge(i) == null) {
                return true;
            }
        }
        return false;
    }

    public boolean isBorder(int i) {
        return getAdjacentTriangleAcrossEdge(i) == null;
    }

    public QuadEdgeTriangle getAdjacentTriangleAcrossEdge(int edgeIndex) {
        return (QuadEdgeTriangle) getEdge(edgeIndex).sym().getData();
    }

    public int getAdjacentTriangleEdgeIndex(int i) {
        int i2 = i;
        return getAdjacentTriangleAcrossEdge(i2).getEdgeIndex(getEdge(i2).sym());
    }

    public List getTrianglesAdjacentToVertex(int vertexIndex) {
        List list;
        new ArrayList();
        List adjTris = list;
        QuadEdge start = getEdge(vertexIndex);
        QuadEdge qe = start;
        do {
            QuadEdgeTriangle adjTri = (QuadEdgeTriangle) qe.getData();
            if (adjTri != null) {
                boolean add = adjTris.add(adjTri);
            }
            qe = qe.oNext();
        } while (qe != start);
        return adjTris;
    }

    public QuadEdgeTriangle[] getNeighbours() {
        QuadEdgeTriangle[] neigh = new QuadEdgeTriangle[3];
        for (int i = 0; i < 3; i++) {
            neigh[i] = (QuadEdgeTriangle) getEdge(i).sym().getData();
        }
        return neigh;
    }

    private static class QuadEdgeTriangleBuilderVisitor implements TriangleVisitor {
        private List triangles;

        public QuadEdgeTriangleBuilderVisitor() {
            List list;
            new ArrayList();
            this.triangles = list;
        }

        public void visit(QuadEdge[] edges) {
            Object obj;
            new QuadEdgeTriangle(edges);
            boolean add = this.triangles.add(obj);
        }

        public List getTriangles() {
            return this.triangles;
        }
    }
}
