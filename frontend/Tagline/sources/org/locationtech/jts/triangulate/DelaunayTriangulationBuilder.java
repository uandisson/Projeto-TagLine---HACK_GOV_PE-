package org.locationtech.jts.triangulate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.quadedge.QuadEdgeSubdivision;
import org.locationtech.jts.triangulate.quadedge.Vertex;

public class DelaunayTriangulationBuilder {
    private Collection siteCoords;
    private QuadEdgeSubdivision subdiv = null;
    private double tolerance = 0.0d;

    public static CoordinateList extractUniqueCoordinates(Geometry geometry) {
        CoordinateList coordinateList;
        Geometry geom = geometry;
        if (geom != null) {
            return unique(geom.getCoordinates());
        }
        new CoordinateList();
        return coordinateList;
    }

    public static CoordinateList unique(Coordinate[] coords) {
        CoordinateList coordList;
        Coordinate[] coordsCopy = CoordinateArrays.copyDeep(coords);
        Arrays.sort(coordsCopy);
        new CoordinateList(coordsCopy, false);
        return coordList;
    }

    public static List toVertices(Collection coords) {
        List list;
        Object obj;
        new ArrayList();
        List verts = list;
        Iterator i = coords.iterator();
        while (i.hasNext()) {
            new Vertex((Coordinate) i.next());
            boolean add = verts.add(obj);
        }
        return verts;
    }

    public static Envelope envelope(Collection coords) {
        Envelope envelope;
        new Envelope();
        Envelope env = envelope;
        Iterator i = coords.iterator();
        while (i.hasNext()) {
            env.expandToInclude((Coordinate) i.next());
        }
        return env;
    }

    public DelaunayTriangulationBuilder() {
    }

    public void setSites(Geometry geom) {
        CoordinateList extractUniqueCoordinates = extractUniqueCoordinates(geom);
        this.siteCoords = extractUniqueCoordinates;
    }

    public void setSites(Collection coords) {
        CoordinateList unique = unique(CoordinateArrays.toCoordinateArray(coords));
        this.siteCoords = unique;
    }

    public void setTolerance(double tolerance2) {
        double d = tolerance2;
        this.tolerance = d;
    }

    private void create() {
        QuadEdgeSubdivision quadEdgeSubdivision;
        IncrementalDelaunayTriangulator triangulator;
        if (this.subdiv == null) {
            Envelope siteEnv = envelope(this.siteCoords);
            List vertices = toVertices(this.siteCoords);
            new QuadEdgeSubdivision(siteEnv, this.tolerance);
            this.subdiv = quadEdgeSubdivision;
            new IncrementalDelaunayTriangulator(this.subdiv);
            triangulator.insertSites(vertices);
        }
    }

    public QuadEdgeSubdivision getSubdivision() {
        create();
        return this.subdiv;
    }

    public Geometry getEdges(GeometryFactory geomFact) {
        create();
        return this.subdiv.getEdges(geomFact);
    }

    public Geometry getTriangles(GeometryFactory geomFact) {
        create();
        return this.subdiv.getTriangles(geomFact);
    }
}
