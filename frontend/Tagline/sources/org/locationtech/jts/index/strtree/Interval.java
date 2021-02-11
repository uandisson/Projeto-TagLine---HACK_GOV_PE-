package org.locationtech.jts.index.strtree;

import org.locationtech.jts.util.Assert;

public class Interval {
    private double max;
    private double min;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Interval(org.locationtech.jts.index.strtree.Interval r9) {
        /*
            r8 = this;
            r1 = r8
            r2 = r9
            r3 = r1
            r4 = r2
            double r4 = r4.min
            r6 = r2
            double r6 = r6.max
            r3.<init>(r4, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.index.strtree.Interval.<init>(org.locationtech.jts.index.strtree.Interval):void");
    }

    public Interval(double d, double d2) {
        double min2 = d;
        double max2 = d2;
        Assert.isTrue(min2 <= max2);
        this.min = min2;
        this.max = max2;
    }

    public double getCentre() {
        return (this.min + this.max) / 2.0d;
    }

    public Interval expandToInclude(Interval interval) {
        Interval other = interval;
        this.max = Math.max(this.max, other.max);
        this.min = Math.min(this.min, other.min);
        return this;
    }

    public boolean intersects(Interval interval) {
        Interval other = interval;
        return other.min <= this.max && other.max >= this.min;
    }

    public boolean equals(Object obj) {
        Object o = obj;
        if (!(o instanceof Interval)) {
            return false;
        }
        Interval other = (Interval) o;
        return this.min == other.min && this.max == other.max;
    }
}
