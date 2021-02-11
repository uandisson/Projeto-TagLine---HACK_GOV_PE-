package org.locationtech.jts.edgegraph;

import java.util.Collection;
import java.util.Iterator;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryComponentFilter;
import org.locationtech.jts.geom.LineString;

public class EdgeGraphBuilder {
    private EdgeGraph graph;

    public static EdgeGraph build(Collection geoms) {
        EdgeGraphBuilder edgeGraphBuilder;
        new EdgeGraphBuilder();
        EdgeGraphBuilder builder = edgeGraphBuilder;
        builder.add(geoms);
        return builder.getGraph();
    }

    public EdgeGraphBuilder() {
        EdgeGraph edgeGraph;
        new EdgeGraph();
        this.graph = edgeGraph;
    }

    public EdgeGraph getGraph() {
        return this.graph;
    }

    public void add(Geometry geometry) {
        GeometryComponentFilter geometryComponentFilter;
        new GeometryComponentFilter(this) {
            final /* synthetic */ EdgeGraphBuilder this$0;

            {
                this.this$0 = this$0;
            }

            public void filter(Geometry geometry) {
                Geometry component = geometry;
                if (component instanceof LineString) {
                    this.this$0.add((LineString) component);
                }
            }
        };
        geometry.apply(geometryComponentFilter);
    }

    public void add(Collection geometries) {
        Iterator i = geometries.iterator();
        while (i.hasNext()) {
            add((Geometry) i.next());
        }
    }

    /* access modifiers changed from: private */
    public void add(LineString lineString) {
        CoordinateSequence seq = lineString.getCoordinateSequence();
        for (int i = 1; i < seq.size(); i++) {
            HalfEdge addEdge = this.graph.addEdge(seq.getCoordinate(i - 1), seq.getCoordinate(i));
        }
    }
}
