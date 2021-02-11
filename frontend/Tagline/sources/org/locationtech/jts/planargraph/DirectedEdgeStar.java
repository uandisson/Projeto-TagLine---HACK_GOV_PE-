package org.locationtech.jts.planargraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;

public class DirectedEdgeStar {
    protected List outEdges;
    private boolean sorted = false;

    public DirectedEdgeStar() {
        List list;
        new ArrayList();
        this.outEdges = list;
    }

    public void add(DirectedEdge de) {
        boolean add = this.outEdges.add(de);
        this.sorted = false;
    }

    public void remove(DirectedEdge de) {
        boolean remove = this.outEdges.remove(de);
    }

    public Iterator iterator() {
        sortEdges();
        return this.outEdges.iterator();
    }

    public int getDegree() {
        return this.outEdges.size();
    }

    public Coordinate getCoordinate() {
        Iterator it = iterator();
        if (!it.hasNext()) {
            return null;
        }
        return ((DirectedEdge) it.next()).getCoordinate();
    }

    public List getEdges() {
        sortEdges();
        return this.outEdges;
    }

    private void sortEdges() {
        if (!this.sorted) {
            Collections.sort(this.outEdges);
            this.sorted = true;
        }
    }

    public int getIndex(Edge edge) {
        Edge edge2 = edge;
        sortEdges();
        for (int i = 0; i < this.outEdges.size(); i++) {
            if (((DirectedEdge) this.outEdges.get(i)).getEdge() == edge2) {
                return i;
            }
        }
        return -1;
    }

    public int getIndex(DirectedEdge directedEdge) {
        DirectedEdge dirEdge = directedEdge;
        sortEdges();
        for (int i = 0; i < this.outEdges.size(); i++) {
            if (((DirectedEdge) this.outEdges.get(i)) == dirEdge) {
                return i;
            }
        }
        return -1;
    }

    public int getIndex(int i) {
        int modi = i % this.outEdges.size();
        if (modi < 0) {
            modi += this.outEdges.size();
        }
        return modi;
    }

    public DirectedEdge getNextEdge(DirectedEdge dirEdge) {
        return (DirectedEdge) this.outEdges.get(getIndex(getIndex(dirEdge) + 1));
    }

    public DirectedEdge getNextCWEdge(DirectedEdge dirEdge) {
        return (DirectedEdge) this.outEdges.get(getIndex(getIndex(dirEdge) - 1));
    }
}
