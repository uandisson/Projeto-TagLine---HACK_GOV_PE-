package org.locationtech.jts.index.sweepline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SweepLineIndex {
    List events;
    private boolean indexBuilt;
    private int nOverlaps;

    public SweepLineIndex() {
        List list;
        new ArrayList();
        this.events = list;
    }

    public void add(SweepLineInterval sweepLineInterval) {
        SweepLineEvent sweepLineEvent;
        Object obj;
        SweepLineInterval sweepInt = sweepLineInterval;
        new SweepLineEvent(sweepInt.getMin(), (SweepLineEvent) null, sweepInt);
        SweepLineEvent insertEvent = sweepLineEvent;
        boolean add = this.events.add(insertEvent);
        new SweepLineEvent(sweepInt.getMax(), insertEvent, sweepInt);
        boolean add2 = this.events.add(obj);
    }

    private void buildIndex() {
        if (!this.indexBuilt) {
            Collections.sort(this.events);
            for (int i = 0; i < this.events.size(); i++) {
                SweepLineEvent ev = (SweepLineEvent) this.events.get(i);
                if (ev.isDelete()) {
                    ev.getInsertEvent().setDeleteEventIndex(i);
                }
            }
            this.indexBuilt = true;
        }
    }

    public void computeOverlaps(SweepLineOverlapAction sweepLineOverlapAction) {
        SweepLineOverlapAction action = sweepLineOverlapAction;
        this.nOverlaps = 0;
        buildIndex();
        for (int i = 0; i < this.events.size(); i++) {
            SweepLineEvent ev = (SweepLineEvent) this.events.get(i);
            if (ev.isInsert()) {
                processOverlaps(i, ev.getDeleteEventIndex(), ev.getInterval(), action);
            }
        }
    }

    private void processOverlaps(int start, int i, SweepLineInterval sweepLineInterval, SweepLineOverlapAction sweepLineOverlapAction) {
        int end = i;
        SweepLineInterval s0 = sweepLineInterval;
        SweepLineOverlapAction action = sweepLineOverlapAction;
        for (int i2 = start; i2 < end; i2++) {
            SweepLineEvent ev = (SweepLineEvent) this.events.get(i2);
            if (ev.isInsert()) {
                action.overlap(s0, ev.getInterval());
                this.nOverlaps++;
            }
        }
    }
}
