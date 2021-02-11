package org.locationtech.jts.operation.linemerge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryComponentFilter;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.planargraph.GraphComponent;
import org.locationtech.jts.planargraph.Node;
import org.locationtech.jts.util.Assert;

public class LineMerger {
    private Collection edgeStrings = null;
    private GeometryFactory factory = null;
    private LineMergeGraph graph;
    private Collection mergedLineStrings = null;

    public LineMerger() {
        LineMergeGraph lineMergeGraph;
        new LineMergeGraph();
        this.graph = lineMergeGraph;
    }

    public void add(Geometry geometry) {
        GeometryComponentFilter geometryComponentFilter;
        new GeometryComponentFilter(this) {
            final /* synthetic */ LineMerger this$0;

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
        this.mergedLineStrings = null;
        Iterator i = geometries.iterator();
        while (i.hasNext()) {
            add((Geometry) i.next());
        }
    }

    /* access modifiers changed from: private */
    public void add(LineString lineString) {
        LineString lineString2 = lineString;
        if (this.factory == null) {
            this.factory = lineString2.getFactory();
        }
        this.graph.addEdge(lineString2);
    }

    private void merge() {
        Collection collection;
        Collection collection2;
        if (this.mergedLineStrings == null) {
            GraphComponent.setMarked(this.graph.nodeIterator(), false);
            GraphComponent.setMarked(this.graph.edgeIterator(), false);
            new ArrayList();
            this.edgeStrings = collection;
            buildEdgeStringsForObviousStartNodes();
            buildEdgeStringsForIsolatedLoops();
            new ArrayList();
            this.mergedLineStrings = collection2;
            for (EdgeString edgeString : this.edgeStrings) {
                boolean add = this.mergedLineStrings.add(edgeString.toLineString());
            }
        }
    }

    private void buildEdgeStringsForObviousStartNodes() {
        buildEdgeStringsForNonDegree2Nodes();
    }

    private void buildEdgeStringsForIsolatedLoops() {
        buildEdgeStringsForUnprocessedNodes();
    }

    private void buildEdgeStringsForUnprocessedNodes() {
        for (Node node : this.graph.getNodes()) {
            if (!node.isMarked()) {
                Assert.isTrue(node.getDegree() == 2);
                buildEdgeStringsStartingAt(node);
                node.setMarked(true);
            }
        }
    }

    private void buildEdgeStringsForNonDegree2Nodes() {
        for (Node node : this.graph.getNodes()) {
            if (node.getDegree() != 2) {
                buildEdgeStringsStartingAt(node);
                node.setMarked(true);
            }
        }
    }

    private void buildEdgeStringsStartingAt(Node node) {
        Iterator i = node.getOutEdges().iterator();
        while (i.hasNext()) {
            LineMergeDirectedEdge directedEdge = (LineMergeDirectedEdge) i.next();
            if (!directedEdge.getEdge().isMarked()) {
                boolean add = this.edgeStrings.add(buildEdgeStringStartingWith(directedEdge));
            }
        }
    }

    private EdgeString buildEdgeStringStartingWith(LineMergeDirectedEdge lineMergeDirectedEdge) {
        EdgeString edgeString;
        LineMergeDirectedEdge start = lineMergeDirectedEdge;
        new EdgeString(this.factory);
        EdgeString edgeString2 = edgeString;
        LineMergeDirectedEdge current = start;
        do {
            edgeString2.add(current);
            current.getEdge().setMarked(true);
            current = current.getNext();
            if (current == null || current == start) {
            }
            edgeString2.add(current);
            current.getEdge().setMarked(true);
            current = current.getNext();
            break;
        } while (current == start);
        return edgeString2;
    }

    public Collection getMergedLineStrings() {
        merge();
        return this.mergedLineStrings;
    }
}
