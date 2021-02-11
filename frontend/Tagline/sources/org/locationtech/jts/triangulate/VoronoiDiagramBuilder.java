package org.locationtech.jts.triangulate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.quadedge.QuadEdgeSubdivision;

public class VoronoiDiagramBuilder {
    private Envelope clipEnv = null;
    private Envelope diagramEnv = null;
    private Collection siteCoords;
    private QuadEdgeSubdivision subdiv = null;
    private double tolerance = 0.0d;

    public VoronoiDiagramBuilder() {
    }

    public void setSites(Geometry geom) {
        CoordinateList extractUniqueCoordinates = DelaunayTriangulationBuilder.extractUniqueCoordinates(geom);
        this.siteCoords = extractUniqueCoordinates;
    }

    public void setSites(Collection coords) {
        CoordinateList unique = DelaunayTriangulationBuilder.unique(CoordinateArrays.toCoordinateArray(coords));
        this.siteCoords = unique;
    }

    public void setClipEnvelope(Envelope clipEnv2) {
        Envelope envelope = clipEnv2;
        this.clipEnv = envelope;
    }

    public void setTolerance(double tolerance2) {
        double d = tolerance2;
        this.tolerance = d;
    }

    private void create() {
        QuadEdgeSubdivision quadEdgeSubdivision;
        IncrementalDelaunayTriangulator triangulator;
        if (this.subdiv == null) {
            Envelope siteEnv = DelaunayTriangulationBuilder.envelope(this.siteCoords);
            this.diagramEnv = siteEnv;
            this.diagramEnv.expandBy(Math.max(this.diagramEnv.getWidth(), this.diagramEnv.getHeight()));
            if (this.clipEnv != null) {
                this.diagramEnv.expandToInclude(this.clipEnv);
            }
            List vertices = DelaunayTriangulationBuilder.toVertices(this.siteCoords);
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

    public Geometry getDiagram(GeometryFactory geomFact) {
        create();
        return clipGeometryCollection(this.subdiv.getVoronoiDiagram(geomFact), this.diagramEnv);
    }

    private static Geometry clipGeometryCollection(Geometry geometry, Envelope envelope) {
        List list;
        Geometry geom = geometry;
        Envelope clipEnv2 = envelope;
        Geometry clipPoly = geom.getFactory().toGeometry(clipEnv2);
        new ArrayList();
        List clipped = list;
        for (int i = 0; i < geom.getNumGeometries(); i++) {
            Geometry g = geom.getGeometryN(i);
            Geometry result = null;
            if (clipEnv2.contains(g.getEnvelopeInternal())) {
                result = g;
            } else if (clipEnv2.intersects(g.getEnvelopeInternal())) {
                result = clipPoly.intersection(g);
                result.setUserData(g.getUserData());
            }
            if (result != null && !result.isEmpty()) {
                boolean add = clipped.add(result);
            }
        }
        return geom.getFactory().createGeometryCollection(GeometryFactory.toGeometryArray(clipped));
    }
}
