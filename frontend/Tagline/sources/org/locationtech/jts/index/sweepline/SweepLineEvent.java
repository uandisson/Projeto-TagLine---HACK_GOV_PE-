package org.locationtech.jts.index.sweepline;

public class SweepLineEvent implements Comparable {
    public static final int DELETE = 2;
    public static final int INSERT = 1;
    private int deleteEventIndex;
    private int eventType = 1;
    private SweepLineEvent insertEvent;
    SweepLineInterval sweepInt;
    private double xValue;

    public SweepLineEvent(double x, SweepLineEvent sweepLineEvent, SweepLineInterval sweepLineInterval) {
        SweepLineEvent insertEvent2 = sweepLineEvent;
        SweepLineInterval sweepInt2 = sweepLineInterval;
        this.xValue = x;
        this.insertEvent = insertEvent2;
        if (insertEvent2 != null) {
            this.eventType = 2;
        }
        this.sweepInt = sweepInt2;
    }

    public boolean isInsert() {
        return this.insertEvent == null;
    }

    public boolean isDelete() {
        return this.insertEvent != null;
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

    /* access modifiers changed from: package-private */
    public SweepLineInterval getInterval() {
        return this.sweepInt;
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
