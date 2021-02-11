package org.locationtech.jts.index.sweepline;

public class SweepLineInterval {
    private Object item;
    private double max;
    private double min;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SweepLineInterval(double min2, double max2) {
        this(min2, max2, (Object) null);
    }

    public SweepLineInterval(double d, double d2, Object obj) {
        double min2 = d;
        double max2 = d2;
        Object item2 = obj;
        this.min = min2 < max2 ? min2 : max2;
        this.max = max2 > min2 ? max2 : min2;
        this.item = item2;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public Object getItem() {
        return this.item;
    }
}
