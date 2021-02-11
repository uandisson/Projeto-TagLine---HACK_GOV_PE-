package org.locationtech.jts.geomgraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.TopologyException;
import org.locationtech.jts.util.Assert;

public abstract class EdgeRing {
    private List edges;
    protected GeometryFactory geometryFactory;
    private ArrayList holes;
    private boolean isHole;
    private Label label;
    private int maxNodeDegree = -1;
    private List pts;
    private LinearRing ring;
    private EdgeRing shell;
    protected DirectedEdge startDe;

    public abstract DirectedEdge getNext(DirectedEdge directedEdge);

    public abstract void setEdgeRing(DirectedEdge directedEdge, EdgeRing edgeRing);

    public EdgeRing(DirectedEdge start, GeometryFactory geometryFactory2) {
        List list;
        List list2;
        Label label2;
        ArrayList arrayList;
        new ArrayList();
        this.edges = list;
        new ArrayList();
        this.pts = list2;
        new Label(-1);
        this.label = label2;
        new ArrayList();
        this.holes = arrayList;
        this.geometryFactory = geometryFactory2;
        computePoints(start);
        computeRing();
    }

    public boolean isIsolated() {
        return this.label.getGeometryCount() == 1;
    }

    public boolean isHole() {
        return this.isHole;
    }

    public Coordinate getCoordinate(int i) {
        return (Coordinate) this.pts.get(i);
    }

    public LinearRing getLinearRing() {
        return this.ring;
    }

    public Label getLabel() {
        return this.label;
    }

    public boolean isShell() {
        return this.shell == null;
    }

    public EdgeRing getShell() {
        return this.shell;
    }

    public void setShell(EdgeRing edgeRing) {
        EdgeRing shell2 = edgeRing;
        this.shell = shell2;
        if (shell2 != null) {
            shell2.addHole(this);
        }
    }

    public void addHole(EdgeRing ring2) {
        boolean add = this.holes.add(ring2);
    }

    public Polygon toPolygon(GeometryFactory geometryFactory2) {
        GeometryFactory geometryFactory3 = geometryFactory2;
        LinearRing[] holeLR = new LinearRing[this.holes.size()];
        for (int i = 0; i < this.holes.size(); i++) {
            holeLR[i] = ((EdgeRing) this.holes.get(i)).getLinearRing();
        }
        return geometryFactory3.createPolygon(getLinearRing(), holeLR);
    }

    public void computeRing() {
        if (this.ring == null) {
            Coordinate[] coord = new Coordinate[this.pts.size()];
            for (int i = 0; i < this.pts.size(); i++) {
                coord[i] = (Coordinate) this.pts.get(i);
            }
            this.ring = this.geometryFactory.createLinearRing(coord);
            this.isHole = CGAlgorithms.isCCW(this.ring.getCoordinates());
        }
    }

    public List getEdges() {
        return this.edges;
    }

    /* access modifiers changed from: protected */
    public void computePoints(DirectedEdge directedEdge) {
        Throwable th;
        Throwable th2;
        StringBuilder sb;
        DirectedEdge start = directedEdge;
        this.startDe = start;
        DirectedEdge de = start;
        boolean isFirstEdge = true;
        while (de != null) {
            if (de.getEdgeRing() == this) {
                Throwable th3 = th2;
                new StringBuilder();
                new TopologyException(sb.append("Directed Edge visited twice during ring-building at ").append(de.getCoordinate()).toString());
                throw th3;
            }
            boolean add = this.edges.add(de);
            Label label2 = de.getLabel();
            Assert.isTrue(label2.isArea());
            mergeLabel(label2);
            addPoints(de.getEdge(), de.isForward(), isFirstEdge);
            isFirstEdge = false;
            setEdgeRing(de, this);
            de = getNext(de);
            if (de == this.startDe) {
                return;
            }
        }
        Throwable th4 = th;
        new TopologyException("Found null DirectedEdge");
        throw th4;
    }

    public int getMaxNodeDegree() {
        if (this.maxNodeDegree < 0) {
            computeMaxNodeDegree();
        }
        return this.maxNodeDegree;
    }

    private void computeMaxNodeDegree() {
        this.maxNodeDegree = 0;
        DirectedEdge de = this.startDe;
        do {
            int degree = ((DirectedEdgeStar) de.getNode().getEdges()).getOutgoingDegree(this);
            if (degree > this.maxNodeDegree) {
                this.maxNodeDegree = degree;
            }
            de = getNext(de);
        } while (de != this.startDe);
        this.maxNodeDegree *= 2;
    }

    public void setInResult() {
        DirectedEdge de = this.startDe;
        do {
            de.getEdge().setInResult(true);
            de = de.getNext();
        } while (de != this.startDe);
    }

    /* access modifiers changed from: protected */
    public void mergeLabel(Label label2) {
        Label deLabel = label2;
        mergeLabel(deLabel, 0);
        mergeLabel(deLabel, 1);
    }

    /* access modifiers changed from: protected */
    public void mergeLabel(Label deLabel, int i) {
        int geomIndex = i;
        int loc = deLabel.getLocation(geomIndex, 2);
        if (loc != -1 && this.label.getLocation(geomIndex) == -1) {
            this.label.setLocation(geomIndex, loc);
        }
    }

    /* access modifiers changed from: protected */
    public void addPoints(Edge edge, boolean isForward, boolean z) {
        boolean isFirstEdge = z;
        Coordinate[] edgePts = edge.getCoordinates();
        if (isForward) {
            int startIndex = 1;
            if (isFirstEdge) {
                startIndex = 0;
            }
            for (int i = startIndex; i < edgePts.length; i++) {
                boolean add = this.pts.add(edgePts[i]);
            }
            return;
        }
        int startIndex2 = edgePts.length - 2;
        if (isFirstEdge) {
            startIndex2 = edgePts.length - 1;
        }
        for (int i2 = startIndex2; i2 >= 0; i2--) {
            boolean add2 = this.pts.add(edgePts[i2]);
        }
    }

    public boolean containsPoint(Coordinate coordinate) {
        Coordinate p = coordinate;
        LinearRing shell2 = getLinearRing();
        if (!shell2.getEnvelopeInternal().contains(p)) {
            return false;
        }
        if (!CGAlgorithms.isPointInRing(p, shell2.getCoordinates())) {
            return false;
        }
        Iterator i = this.holes.iterator();
        while (i.hasNext()) {
            if (((EdgeRing) i.next()).containsPoint(p)) {
                return false;
            }
        }
        return true;
    }
}
