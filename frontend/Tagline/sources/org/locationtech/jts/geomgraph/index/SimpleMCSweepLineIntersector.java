package org.locationtech.jts.geomgraph.index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geomgraph.Edge;

public class SimpleMCSweepLineIntersector extends EdgeSetIntersector {
    List events;
    int nOverlaps;

    public SimpleMCSweepLineIntersector() {
        List list;
        new ArrayList();
        this.events = list;
    }

    public void computeIntersections(List list, SegmentIntersector segmentIntersector, boolean testAllSegments) {
        List edges = list;
        SegmentIntersector si = segmentIntersector;
        if (testAllSegments) {
            add(edges, (Object) null);
        } else {
            add(edges);
        }
        computeIntersections(si);
    }

    public void computeIntersections(List list, List list2, SegmentIntersector si) {
        List edges0 = list;
        List edges1 = list2;
        add(edges0, (Object) edges0);
        add(edges1, (Object) edges1);
        computeIntersections(si);
    }

    private void add(List edges) {
        Iterator i = edges.iterator();
        while (i.hasNext()) {
            Edge edge = (Edge) i.next();
            add(edge, (Object) edge);
        }
    }

    private void add(List edges, Object obj) {
        Object edgeSet = obj;
        Iterator i = edges.iterator();
        while (i.hasNext()) {
            add((Edge) i.next(), edgeSet);
        }
    }

    private void add(Edge edge, Object obj) {
        MonotoneChain mc;
        SweepLineEvent sweepLineEvent;
        Object obj2;
        Object edgeSet = obj;
        MonotoneChainEdge mce = edge.getMonotoneChainEdge();
        int[] startIndex = mce.getStartIndexes();
        for (int i = 0; i < startIndex.length - 1; i++) {
            new MonotoneChain(mce, i);
            new SweepLineEvent(edgeSet, mce.getMinX(i), mc);
            SweepLineEvent insertEvent = sweepLineEvent;
            boolean add = this.events.add(insertEvent);
            new SweepLineEvent(mce.getMaxX(i), insertEvent);
            boolean add2 = this.events.add(obj2);
        }
    }

    private void prepareEvents() {
        Collections.sort(this.events);
        for (int i = 0; i < this.events.size(); i++) {
            SweepLineEvent ev = (SweepLineEvent) this.events.get(i);
            if (ev.isDelete()) {
                ev.getInsertEvent().setDeleteEventIndex(i);
            }
        }
    }

    private void computeIntersections(SegmentIntersector segmentIntersector) {
        SegmentIntersector si = segmentIntersector;
        this.nOverlaps = 0;
        prepareEvents();
        int i = 0;
        while (i < this.events.size()) {
            SweepLineEvent ev = (SweepLineEvent) this.events.get(i);
            if (ev.isInsert()) {
                processOverlaps(i, ev.getDeleteEventIndex(), ev, si);
            }
            if (!si.isDone()) {
                i++;
            } else {
                return;
            }
        }
    }

    private void processOverlaps(int start, int i, SweepLineEvent sweepLineEvent, SegmentIntersector segmentIntersector) {
        int end = i;
        SweepLineEvent ev0 = sweepLineEvent;
        SegmentIntersector si = segmentIntersector;
        MonotoneChain mc0 = (MonotoneChain) ev0.getObject();
        for (int i2 = start; i2 < end; i2++) {
            SweepLineEvent ev1 = (SweepLineEvent) this.events.get(i2);
            if (ev1.isInsert()) {
                MonotoneChain mc1 = (MonotoneChain) ev1.getObject();
                if (!ev0.isSameLabel(ev1)) {
                    mc0.computeIntersections(mc1, si);
                    this.nOverlaps++;
                }
            }
        }
    }
}
