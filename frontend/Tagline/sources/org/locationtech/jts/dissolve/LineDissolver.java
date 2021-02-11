package org.locationtech.jts.dissolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.locationtech.jts.edgegraph.HalfEdge;
import org.locationtech.jts.edgegraph.MarkHalfEdge;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryComponentFilter;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

public class LineDissolver {
    private GeometryFactory factory;
    private DissolveEdgeGraph graph;
    private List lines;
    private Stack nodeEdgeStack;
    private Geometry result;
    private DissolveHalfEdge ringStartEdge;

    public static Geometry dissolve(Geometry g) {
        LineDissolver lineDissolver;
        new LineDissolver();
        LineDissolver d = lineDissolver;
        d.add(g);
        return d.getResult();
    }

    public LineDissolver() {
        List list;
        Stack stack;
        DissolveEdgeGraph dissolveEdgeGraph;
        new ArrayList();
        this.lines = list;
        new Stack();
        this.nodeEdgeStack = stack;
        new DissolveEdgeGraph();
        this.graph = dissolveEdgeGraph;
    }

    public void add(Geometry geometry) {
        GeometryComponentFilter geometryComponentFilter;
        new GeometryComponentFilter(this) {
            final /* synthetic */ LineDissolver this$0;

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
        LineString lineString2 = lineString;
        if (this.factory == null) {
            this.factory = lineString2.getFactory();
        }
        CoordinateSequence seq = lineString2.getCoordinateSequence();
        boolean doneStart = false;
        for (int i = 1; i < seq.size(); i++) {
            DissolveHalfEdge e = (DissolveHalfEdge) this.graph.addEdge(seq.getCoordinate(i - 1), seq.getCoordinate(i));
            if (e != null && !doneStart) {
                e.setStart();
                doneStart = true;
            }
        }
    }

    public Geometry getResult() {
        if (this.result == null) {
            computeResult();
        }
        return this.result;
    }

    private void computeResult() {
        for (HalfEdge e : this.graph.getVertexEdges()) {
            if (!MarkHalfEdge.isMarked(e)) {
                process(e);
            }
        }
        this.result = this.factory.buildGeometry(this.lines);
    }

    private void process(HalfEdge halfEdge) {
        HalfEdge e = halfEdge;
        HalfEdge eNode = e.prevNode();
        if (eNode == null) {
            eNode = e;
        }
        stackEdges(eNode);
        buildLines();
    }

    private void buildLines() {
        while (!this.nodeEdgeStack.empty()) {
            HalfEdge e = (HalfEdge) this.nodeEdgeStack.pop();
            if (!MarkHalfEdge.isMarked(e)) {
                buildLine(e);
            }
        }
    }

    private void updateRingStartEdge(DissolveHalfEdge dissolveHalfEdge) {
        DissolveHalfEdge e = dissolveHalfEdge;
        if (!e.isStart()) {
            e = (DissolveHalfEdge) e.sym();
            if (!e.isStart()) {
                return;
            }
        }
        if (this.ringStartEdge == null) {
            this.ringStartEdge = e;
        } else if (e.orig().compareTo(this.ringStartEdge.orig()) < 0) {
            this.ringStartEdge = e;
        }
    }

    private void buildLine(HalfEdge halfEdge) {
        CoordinateList coordinateList;
        HalfEdge eStart = halfEdge;
        new CoordinateList();
        CoordinateList line = coordinateList;
        DissolveHalfEdge e = (DissolveHalfEdge) eStart;
        this.ringStartEdge = null;
        MarkHalfEdge.markBoth(e);
        boolean add = line.add(e.orig().clone(), false);
        while (e.sym().degree() == 2) {
            updateRingStartEdge(e);
            DissolveHalfEdge eNext = (DissolveHalfEdge) e.next();
            if (eNext == eStart) {
                buildRing(this.ringStartEdge);
                return;
            }
            boolean add2 = line.add(eNext.orig().clone(), false);
            e = eNext;
            MarkHalfEdge.markBoth(e);
        }
        boolean add3 = line.add(e.dest().clone(), false);
        stackEdges(e.sym());
        addLine(line);
    }

    private void buildRing(HalfEdge halfEdge) {
        CoordinateList coordinateList;
        HalfEdge eNext;
        HalfEdge eStartRing = halfEdge;
        new CoordinateList();
        CoordinateList line = coordinateList;
        HalfEdge e = eStartRing;
        boolean add = line.add(e.orig().clone(), false);
        while (e.sym().degree() == 2 && (eNext = e.next()) != eStartRing) {
            boolean add2 = line.add(eNext.orig().clone(), false);
            e = eNext;
        }
        boolean add3 = line.add(e.dest().clone(), false);
        addLine(line);
    }

    private void addLine(CoordinateList line) {
        boolean add = this.lines.add(this.factory.createLineString(line.toCoordinateArray()));
    }

    private void stackEdges(HalfEdge halfEdge) {
        HalfEdge node = halfEdge;
        HalfEdge e = node;
        do {
            if (!MarkHalfEdge.isMarked(e)) {
                boolean add = this.nodeEdgeStack.add(e);
            }
            e = e.oNext();
        } while (e != node);
    }
}
