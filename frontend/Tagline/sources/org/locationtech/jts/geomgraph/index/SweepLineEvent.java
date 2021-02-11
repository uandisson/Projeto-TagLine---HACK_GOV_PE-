package org.locationtech.jts.geomgraph.index;

public class SweepLineEvent implements Comparable {
    private static final int DELETE = 2;
    private static final int INSERT = 1;
    private int deleteEventIndex;
    private int eventType = 2;
    private SweepLineEvent insertEvent = null;
    private Object label;
    private Object obj;
    private double xValue;

    public SweepLineEvent(Object label2, double x, Object obj2) {
        this.label = label2;
        this.xValue = x;
        this.obj = obj2;
    }

    public SweepLineEvent(double x, SweepLineEvent insertEvent2) {
        this.xValue = x;
        this.insertEvent = insertEvent2;
    }

    public boolean isInsert() {
        return this.eventType == 1;
    }

    public boolean isDelete() {
        return this.eventType == 2;
    }

    public SweepLineEvent getInsertEvent() {
        return this.insertEvent;
    }

    public int getDeleteEventIndex() {
        return this.deleteEventIndex;
    }

    public void setDeleteEventIndex(int deleteEventIndex2) {
        int i = deleteEventIndex2;
        this.deleteEventIndex = i;
    }

    public Object getObject() {
        return this.obj;
    }

    public boolean isSameLabel(SweepLineEvent sweepLineEvent) {
        SweepLineEvent ev = sweepLineEvent;
        if (this.label == null) {
            return false;
        }
        return this.label == ev.label;
    }

    public int compareTo(Object o) {
        SweepLineEvent pe = (SweepLineEvent) o;
        if (this.xValue < pe.xValue) {
            return -1;
        }
        if (this.xValue > pe.xValue) {
            return 1;
        }
        if (this.eventType < pe.eventType) {
            return -1;
        }
        if (this.eventType > pe.eventType) {
            return 1;
        }
        return 0;
    }
}
