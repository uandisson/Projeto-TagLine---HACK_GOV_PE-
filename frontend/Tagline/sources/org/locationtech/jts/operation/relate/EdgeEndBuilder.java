package org.locationtech.jts.operation.relate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.EdgeEnd;
import org.locationtech.jts.geomgraph.EdgeIntersection;
import org.locationtech.jts.geomgraph.EdgeIntersectionList;
import org.locationtech.jts.geomgraph.Label;

public class EdgeEndBuilder {
    public EdgeEndBuilder() {
    }

    public List computeEdgeEnds(Iterator edges) {
        List list;
        new ArrayList();
        List l = list;
        Iterator i = edges;
        while (i.hasNext()) {
            computeEdgeEnds((Edge) i.next(), l);
        }
        return l;
    }

    public void computeEdgeEnds(Edge edge, List list) {
        Edge edge2 = edge;
        List l = list;
        EdgeIntersectionList eiList = edge2.getEdgeIntersectionList();
        eiList.addEndpoints();
        Iterator it = eiList.iterator();
        EdgeIntersection eiCurr = null;
        if (it.hasNext()) {
            EdgeIntersection eiNext = (EdgeIntersection) it.next();
            do {
                EdgeIntersection eiPrev = eiCurr;
                eiCurr = eiNext;
                eiNext = null;
                if (it.hasNext()) {
                    eiNext = (EdgeIntersection) it.next();
                }
                if (eiCurr != null) {
                    createEdgeEndForPrev(edge2, l, eiCurr, eiPrev);
                    createEdgeEndForNext(edge2, l, eiCurr, eiNext);
                }
            } while (eiCurr != null);
        }
    }

    /* access modifiers changed from: package-private */
    public void createEdgeEndForPrev(Edge edge, List list, EdgeIntersection edgeIntersection, EdgeIntersection edgeIntersection2) {
        Label label;
        Object obj;
        Edge edge2 = edge;
        List l = list;
        EdgeIntersection eiCurr = edgeIntersection;
        EdgeIntersection eiPrev = edgeIntersection2;
        int iPrev = eiCurr.segmentIndex;
        if (eiCurr.dist == 0.0d) {
            if (iPrev != 0) {
                iPrev--;
            } else {
                return;
            }
        }
        Coordinate pPrev = edge2.getCoordinate(iPrev);
        if (eiPrev != null && eiPrev.segmentIndex >= iPrev) {
            pPrev = eiPrev.coord;
        }
        new Label(edge2.getLabel());
        Label label2 = label;
        label2.flip();
        new EdgeEnd(edge2, eiCurr.coord, pPrev, label2);
        boolean add = l.add(obj);
    }

    /* access modifiers changed from: package-private */
    public void createEdgeEndForNext(Edge edge, List list, EdgeIntersection edgeIntersection, EdgeIntersection edgeIntersection2) {
        Object obj;
        Label label;
        Edge edge2 = edge;
        List l = list;
        EdgeIntersection eiCurr = edgeIntersection;
        EdgeIntersection eiNext = edgeIntersection2;
        int iNext = eiCurr.segmentIndex + 1;
        if (iNext < edge2.getNumPoints() || eiNext != null) {
            Coordinate pNext = edge2.getCoordinate(iNext);
            if (eiNext != null && eiNext.segmentIndex == eiCurr.segmentIndex) {
                pNext = eiNext.coord;
            }
            new Label(edge2.getLabel());
            new EdgeEnd(edge2, eiCurr.coord, pNext, label);
            boolean add = l.add(obj);
        }
    }
}
