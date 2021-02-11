package org.locationtech.jts.operation.polygonize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryComponentFilter;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.operation.polygonize.EdgeRing;

public class Polygonizer {
    protected List cutEdges;
    protected Collection dangles;
    private boolean extractOnlyPolygonal;
    private GeometryFactory geomFactory;
    protected PolygonizeGraph graph;
    protected List holeList;
    protected List invalidRingLines;
    private boolean isCheckingRingsValid;
    private LineStringAdder lineStringAdder;
    protected List polyList;
    protected List shellList;

    private class LineStringAdder implements GeometryComponentFilter {
        final /* synthetic */ Polygonizer this$0;

        private LineStringAdder(Polygonizer polygonizer) {
            this.this$0 = polygonizer;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ LineStringAdder(Polygonizer x0, C15721 r7) {
            this(x0);
            C15721 r2 = r7;
        }

        public void filter(Geometry geometry) {
            Geometry g = geometry;
            if (g instanceof LineString) {
                this.this$0.add((LineString) g);
            }
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public Polygonizer() {
        this(false);
    }

    public Polygonizer(boolean extractOnlyPolygonal2) {
        LineStringAdder lineStringAdder2;
        Collection collection;
        List list;
        List list2;
        new LineStringAdder(this, (C15721) null);
        this.lineStringAdder = lineStringAdder2;
        new ArrayList();
        this.dangles = collection;
        new ArrayList();
        this.cutEdges = list;
        new ArrayList();
        this.invalidRingLines = list2;
        this.holeList = null;
        this.shellList = null;
        this.polyList = null;
        this.isCheckingRingsValid = true;
        this.geomFactory = null;
        this.extractOnlyPolygonal = extractOnlyPolygonal2;
    }

    public void add(Collection geomList) {
        Iterator i = geomList.iterator();
        while (i.hasNext()) {
            add((Geometry) i.next());
        }
    }

    public void add(Geometry g) {
        g.apply((GeometryComponentFilter) this.lineStringAdder);
    }

    /* access modifiers changed from: private */
    public void add(LineString lineString) {
        PolygonizeGraph polygonizeGraph;
        LineString line = lineString;
        this.geomFactory = line.getFactory();
        if (this.graph == null) {
            new PolygonizeGraph(this.geomFactory);
            this.graph = polygonizeGraph;
        }
        this.graph.addEdge(line);
    }

    public void setCheckRingsValid(boolean isCheckingRingsValid2) {
        boolean z = isCheckingRingsValid2;
        this.isCheckingRingsValid = z;
    }

    public Collection getPolygons() {
        polygonize();
        return this.polyList;
    }

    public Geometry getGeometry() {
        GeometryFactory geometryFactory;
        if (this.geomFactory == null) {
            new GeometryFactory();
            this.geomFactory = geometryFactory;
        }
        polygonize();
        if (this.extractOnlyPolygonal) {
            return this.geomFactory.buildGeometry(this.polyList);
        }
        return this.geomFactory.createGeometryCollection(GeometryFactory.toGeometryArray(this.polyList));
    }

    public Collection getDangles() {
        polygonize();
        return this.dangles;
    }

    public Collection getCutEdges() {
        polygonize();
        return this.cutEdges;
    }

    public Collection getInvalidRingLines() {
        polygonize();
        return this.invalidRingLines;
    }

    private void polygonize() {
        List list;
        List list2;
        List list3;
        Comparator comparator;
        if (this.polyList == null) {
            new ArrayList();
            this.polyList = list;
            if (this.graph != null) {
                this.dangles = this.graph.deleteDangles();
                this.cutEdges = this.graph.deleteCutEdges();
                List edgeRingList = this.graph.getEdgeRings();
                new ArrayList();
                List validEdgeRingList = list2;
                new ArrayList();
                this.invalidRingLines = list3;
                if (this.isCheckingRingsValid) {
                    findValidRings(edgeRingList, validEdgeRingList, this.invalidRingLines);
                } else {
                    validEdgeRingList = edgeRingList;
                }
                findShellsAndHoles(validEdgeRingList);
                assignHolesToShells(this.holeList, this.shellList);
                new EdgeRing.EnvelopeComparator();
                Collections.sort(this.shellList, comparator);
                boolean includeAll = true;
                if (this.extractOnlyPolygonal) {
                    findDisjointShells(this.shellList);
                    includeAll = false;
                }
                this.polyList = extractPolygons(this.shellList, includeAll);
            }
        }
    }

    private void findValidRings(List edgeRingList, List list, List list2) {
        List validEdgeRingList = list;
        List invalidRingList = list2;
        Iterator i = edgeRingList.iterator();
        while (i.hasNext()) {
            EdgeRing er = (EdgeRing) i.next();
            if (er.isValid()) {
                boolean add = validEdgeRingList.add(er);
            } else {
                boolean add2 = invalidRingList.add(er.getLineString());
            }
        }
    }

    private void findShellsAndHoles(List edgeRingList) {
        List list;
        List list2;
        new ArrayList();
        this.holeList = list;
        new ArrayList();
        this.shellList = list2;
        Iterator i = edgeRingList.iterator();
        while (i.hasNext()) {
            EdgeRing er = (EdgeRing) i.next();
            er.computeHole();
            if (er.isHole()) {
                boolean add = this.holeList.add(er);
            } else {
                boolean add2 = this.shellList.add(er);
            }
        }
    }

    private static void assignHolesToShells(List holeList2, List list) {
        List shellList2 = list;
        Iterator i = holeList2.iterator();
        while (i.hasNext()) {
            assignHoleToShell((EdgeRing) i.next(), shellList2);
        }
    }

    private static void assignHoleToShell(EdgeRing edgeRing, List shellList2) {
        EdgeRing holeER = edgeRing;
        EdgeRing shell = EdgeRing.findEdgeRingContaining(holeER, shellList2);
        if (shell != null) {
            shell.addHole(holeER);
        }
    }

    private static void findDisjointShells(List list) {
        boolean isMoreToScan;
        List<EdgeRing> shellList2 = list;
        findOuterShells(shellList2);
        do {
            isMoreToScan = false;
            for (EdgeRing er : shellList2) {
                if (!er.isIncludedSet()) {
                    er.updateIncluded();
                    if (!er.isIncludedSet()) {
                        isMoreToScan = true;
                    }
                }
            }
        } while (isMoreToScan);
    }

    private static void findOuterShells(List shellList2) {
        Iterator i = shellList2.iterator();
        while (i.hasNext()) {
            EdgeRing er = (EdgeRing) i.next();
            EdgeRing outerHoleER = er.getOuterHole();
            if (outerHoleER != null && !outerHoleER.isProcessed()) {
                er.setIncluded(true);
                outerHoleER.setProcessed(true);
            }
        }
    }

    private static List extractPolygons(List shellList2, boolean z) {
        List list;
        boolean includeAll = z;
        new ArrayList();
        List polyList2 = list;
        Iterator i = shellList2.iterator();
        while (i.hasNext()) {
            EdgeRing er = (EdgeRing) i.next();
            if (includeAll || er.isIncluded()) {
                boolean add = polyList2.add(er.getPolygon());
            }
        }
        return polyList2;
    }
}
