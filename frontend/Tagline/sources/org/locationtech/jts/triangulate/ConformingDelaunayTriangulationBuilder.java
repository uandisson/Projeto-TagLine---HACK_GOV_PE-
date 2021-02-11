package org.locationtech.jts.triangulate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.util.LinearComponentExtracter;
import org.locationtech.jts.triangulate.quadedge.QuadEdgeSubdivision;

public class ConformingDelaunayTriangulationBuilder {
    private Geometry constraintLines;
    private Map constraintVertexMap;
    private Collection siteCoords;
    private QuadEdgeSubdivision subdiv = null;
    private double tolerance = 0.0d;

    public ConformingDelaunayTriangulationBuilder() {
        Map map;
        new TreeMap();
        this.constraintVertexMap = map;
    }

    public void setSites(Geometry geom) {
        CoordinateList extractUniqueCoordinates = DelaunayTriangulationBuilder.extractUniqueCoordinates(geom);
        this.siteCoords = extractUniqueCoordinates;
    }

    public void setConstraints(Geometry constraintLines2) {
        Geometry geometry = constraintLines2;
        this.constraintLines = geometry;
    }

    public void setTolerance(double tolerance2) {
        double d = tolerance2;
        this.tolerance = d;
    }

    private void create() {
        List list;
        ConformingDelaunayTriangulator conformingDelaunayTriangulator;
        List list2;
        if (this.subdiv == null) {
            Envelope siteEnv = DelaunayTriangulationBuilder.envelope(this.siteCoords);
            new ArrayList();
            List segments = list;
            if (this.constraintLines != null) {
                siteEnv.expandToInclude(this.constraintLines.getEnvelopeInternal());
                createVertices(this.constraintLines);
                segments = createConstraintSegments(this.constraintLines);
            }
            new ConformingDelaunayTriangulator(createSiteVertices(this.siteCoords), this.tolerance);
            ConformingDelaunayTriangulator cdt = conformingDelaunayTriangulator;
            new ArrayList(this.constraintVertexMap.values());
            cdt.setConstraints(segments, list2);
            cdt.formInitialDelaunay();
            cdt.enforceConstraints();
            this.subdiv = cdt.getSubdivision();
        }
    }

    private List createSiteVertices(Collection coords) {
        List list;
        Object obj;
        new ArrayList();
        List verts = list;
        Iterator i = coords.iterator();
        while (i.hasNext()) {
            Coordinate coord = (Coordinate) i.next();
            if (!this.constraintVertexMap.containsKey(coord)) {
                new ConstraintVertex(coord);
                boolean add = verts.add(obj);
            }
        }
        return verts;
    }

    private void createVertices(Geometry geom) {
        Object obj;
        Coordinate[] coords = geom.getCoordinates();
        for (int i = 0; i < coords.length; i++) {
            new ConstraintVertex(coords[i]);
            Object put = this.constraintVertexMap.put(coords[i], obj);
        }
    }

    private static List createConstraintSegments(Geometry geom) {
        List list;
        List<LineString> lines = LinearComponentExtracter.getLines(geom);
        new ArrayList();
        List constraintSegs = list;
        for (LineString line : lines) {
            createConstraintSegments(line, constraintSegs);
        }
        return constraintSegs;
    }

    private static void createConstraintSegments(LineString line, List list) {
        Object obj;
        List constraintSegs = list;
        Coordinate[] coords = line.getCoordinates();
        for (int i = 1; i < coords.length; i++) {
            new Segment(coords[i - 1], coords[i]);
            boolean add = constraintSegs.add(obj);
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
