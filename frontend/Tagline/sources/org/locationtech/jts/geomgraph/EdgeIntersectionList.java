package org.locationtech.jts.geomgraph;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.locationtech.jts.geom.Coordinate;

public class EdgeIntersectionList {
    Edge edge;
    private Map nodeMap;

    public EdgeIntersectionList(Edge edge2) {
        Map map;
        new TreeMap();
        this.nodeMap = map;
        this.edge = edge2;
    }

    public EdgeIntersection add(Coordinate intPt, int segmentIndex, double dist) {
        EdgeIntersection edgeIntersection;
        new EdgeIntersection(intPt, segmentIndex, dist);
        EdgeIntersection eiNew = edgeIntersection;
        EdgeIntersection ei = (EdgeIntersection) this.nodeMap.get(eiNew);
        if (ei != null) {
            return ei;
        }
        Object put = this.nodeMap.put(eiNew, eiNew);
        return eiNew;
    }

    public Iterator iterator() {
        return this.nodeMap.values().iterator();
    }

    public boolean isIntersection(Coordinate coordinate) {
        Coordinate pt = coordinate;
        Iterator it = iterator();
        while (it.hasNext()) {
            if (((EdgeIntersection) it.next()).coord.equals(pt)) {
                return true;
            }
        }
        return false;
    }

    public void addEndpoints() {
        int maxSegIndex = this.edge.pts.length - 1;
        EdgeIntersection add = add(this.edge.pts[0], 0, 0.0d);
        EdgeIntersection add2 = add(this.edge.pts[maxSegIndex], maxSegIndex, 0.0d);
    }

    public void addSplitEdges(List list) {
        List edgeList = list;
        addEndpoints();
        Iterator it = iterator();
        EdgeIntersection edgeIntersection = (EdgeIntersection) it.next();
        while (true) {
            EdgeIntersection eiPrev = edgeIntersection;
            if (it.hasNext()) {
                EdgeIntersection ei = (EdgeIntersection) it.next();
                boolean add = edgeList.add(createSplitEdge(eiPrev, ei));
                edgeIntersection = ei;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Edge createSplitEdge(EdgeIntersection edgeIntersection, EdgeIntersection edgeIntersection2) {
        Coordinate coordinate;
        Edge edge2;
        Label label;
        EdgeIntersection ei0 = edgeIntersection;
        EdgeIntersection ei1 = edgeIntersection2;
        int npts = (ei1.segmentIndex - ei0.segmentIndex) + 2;
        boolean useIntPt1 = ei1.dist > 0.0d || !ei1.coord.equals2D(this.edge.pts[ei1.segmentIndex]);
        if (!useIntPt1) {
            npts--;
        }
        Coordinate[] pts = new Coordinate[npts];
        int ipt = 0 + 1;
        new Coordinate(ei0.coord);
        pts[0] = coordinate;
        for (int i = ei0.segmentIndex + 1; i <= ei1.segmentIndex; i++) {
            int i2 = ipt;
            ipt++;
            pts[i2] = this.edge.pts[i];
        }
        if (useIntPt1) {
            pts[ipt] = ei1.coord;
        }
        new Label(this.edge.label);
        new Edge(pts, label);
        return edge2;
    }

    public void print(PrintStream printStream) {
        PrintStream out = printStream;
        out.println("Intersections:");
        Iterator it = iterator();
        while (it.hasNext()) {
            ((EdgeIntersection) it.next()).print(out);
        }
    }
}
