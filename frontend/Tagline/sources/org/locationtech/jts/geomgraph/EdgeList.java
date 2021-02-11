package org.locationtech.jts.geomgraph;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.noding.OrientedCoordinateArray;

public class EdgeList {
    private List edges;
    private Map ocaMap;

    public EdgeList() {
        List list;
        Map map;
        new ArrayList();
        this.edges = list;
        new TreeMap();
        this.ocaMap = map;
    }

    public void add(Edge edge) {
        Object obj;
        Edge e = edge;
        boolean add = this.edges.add(e);
        new OrientedCoordinateArray(e.getCoordinates());
        Object put = this.ocaMap.put(obj, e);
    }

    public void addAll(Collection edgeColl) {
        Iterator i = edgeColl.iterator();
        while (i.hasNext()) {
            add((Edge) i.next());
        }
    }

    public List getEdges() {
        return this.edges;
    }

    public Edge findEqualEdge(Edge e) {
        Object obj;
        new OrientedCoordinateArray(e.getCoordinates());
        return (Edge) this.ocaMap.get(obj);
    }

    public Iterator iterator() {
        return this.edges.iterator();
    }

    public Edge get(int i) {
        return (Edge) this.edges.get(i);
    }

    public int findEdgeIndex(Edge edge) {
        Edge e = edge;
        for (int i = 0; i < this.edges.size(); i++) {
            if (((Edge) this.edges.get(i)).equals(e)) {
                return i;
            }
        }
        return -1;
    }

    public void print(PrintStream printStream) {
        StringBuilder sb;
        PrintStream out = printStream;
        out.print("MULTILINESTRING ( ");
        for (int j = 0; j < this.edges.size(); j++) {
            Edge e = (Edge) this.edges.get(j);
            if (j > 0) {
                out.print(",");
            }
            out.print("(");
            Coordinate[] pts = e.getCoordinates();
            for (int i = 0; i < pts.length; i++) {
                if (i > 0) {
                    out.print(",");
                }
                new StringBuilder();
                out.print(sb.append(pts[i].f412x).append(" ").append(pts[i].f413y).toString());
            }
            out.println(")");
        }
        out.print(")  ");
    }
}
