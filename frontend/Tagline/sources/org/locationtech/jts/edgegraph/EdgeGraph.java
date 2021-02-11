package org.locationtech.jts.edgegraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.locationtech.jts.geom.Coordinate;

public class EdgeGraph {
    private Map vertexMap;

    public EdgeGraph() {
        Map map;
        new HashMap();
        this.vertexMap = map;
    }

    /* access modifiers changed from: protected */
    public HalfEdge createEdge(Coordinate orig) {
        HalfEdge halfEdge;
        new HalfEdge(orig);
        return halfEdge;
    }

    private HalfEdge create(Coordinate p0, Coordinate p1) {
        HalfEdge e0 = createEdge(p0);
        HalfEdge init = HalfEdge.init(e0, createEdge(p1));
        return e0;
    }

    public HalfEdge addEdge(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate orig = coordinate;
        Coordinate dest = coordinate2;
        if (!isValidEdge(orig, dest)) {
            return null;
        }
        HalfEdge eAdj = (HalfEdge) this.vertexMap.get(orig);
        HalfEdge eSame = null;
        if (eAdj != null) {
            eSame = eAdj.find(dest);
        }
        if (eSame != null) {
            return eSame;
        }
        return insert(orig, dest, eAdj);
    }

    public static boolean isValidEdge(Coordinate orig, Coordinate dest) {
        return dest.compareTo(orig) != 0;
    }

    private HalfEdge insert(Coordinate coordinate, Coordinate coordinate2, HalfEdge halfEdge) {
        Coordinate orig = coordinate;
        Coordinate dest = coordinate2;
        HalfEdge eAdj = halfEdge;
        HalfEdge e = create(orig, dest);
        if (eAdj != null) {
            eAdj.insert(e);
        } else {
            Object put = this.vertexMap.put(orig, e);
        }
        HalfEdge eAdjDest = (HalfEdge) this.vertexMap.get(dest);
        if (eAdjDest != null) {
            eAdjDest.insert(e.sym());
        } else {
            Object put2 = this.vertexMap.put(dest, e.sym());
        }
        return e;
    }

    public Collection getVertexEdges() {
        return this.vertexMap.values();
    }

    public HalfEdge findEdge(Coordinate orig, Coordinate coordinate) {
        Coordinate dest = coordinate;
        HalfEdge e = (HalfEdge) this.vertexMap.get(orig);
        if (e == null) {
            return null;
        }
        return e.find(dest);
    }
}
