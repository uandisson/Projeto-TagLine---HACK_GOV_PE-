package org.locationtech.jts.index.bintree;

public class Interval {
    public double max;
    public double min;

    public Interval() {
        this.min = 0.0d;
        this.max = 0.0d;
    }

    public Interval(double min2, double max2) {
        init(min2, max2);
    }

    public Interval(Interval interval) {
        Interval interval2 = interval;
        init(interval2.min, interval2.max);
    }

    public void init(double d, double d2) {
        double min2 = d;
        double max2 = d2;
        this.min = min2;
        this.max = max2;
        if (min2 > max2) {
            this.min = max2;
            this.max = min2;
        }
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public double getWidth() {
        return this.max - this.min;
    }

    public void expandToInclude(Interval interval) {
        Interval interval2 = interval;
        if (interval2.max > this.max) {
            this.max = interval2.max;
        }
        if (interval2.min < this.min) {
            this.min = interval2.min;
        }
    }

    public boolean overlaps(Interval interval) {
        Interval interval2 = interval;
        return overlaps(interval2.min, interval2.max);
    }

    public boolean overlaps(double d, double max2) {
        double min2 = d;
        if (this.min > max2 || this.max < min2) {
            return false;
        }
        return true;
    }

    public boolean contains(Interval interval) {
        Interval interval2 = interval;
        return contains(interval2.min, interval2.max);
    }

    public boolean contains(double min2, double max2) {
        return min2 >= this.min && max2 <= this.max;
    }

    public boolean contains(double d) {
        double p = d;
        return p >= this.min && p <= this.max;
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("[").append(this.min).append(", ").append(this.max).append("]").toString();
    }
}
