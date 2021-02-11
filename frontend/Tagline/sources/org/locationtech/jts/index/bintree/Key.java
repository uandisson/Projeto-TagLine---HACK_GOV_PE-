package org.locationtech.jts.index.bintree;

import org.locationtech.jts.index.quadtree.DoubleBits;

public class Key {
    private Interval interval;
    private int level = 0;

    /* renamed from: pt */
    private double f440pt = 0.0d;

    public static int computeLevel(Interval interval2) {
        return DoubleBits.exponent(interval2.getWidth()) + 1;
    }

    public Key(Interval interval2) {
        computeKey(interval2);
    }

    public double getPoint() {
        return this.f440pt;
    }

    public int getLevel() {
        return this.level;
    }

    public Interval getInterval() {
        return this.interval;
    }

    public void computeKey(Interval interval2) {
        Interval interval3;
        Interval itemInterval = interval2;
        this.level = computeLevel(itemInterval);
        new Interval();
        this.interval = interval3;
        computeInterval(this.level, itemInterval);
        while (!this.interval.contains(itemInterval)) {
            this.level++;
            computeInterval(this.level, itemInterval);
        }
    }

    private void computeInterval(int level2, Interval itemInterval) {
        double size = DoubleBits.powerOf2(level2);
        this.f440pt = Math.floor(itemInterval.getMin() / size) * size;
        this.interval.init(this.f440pt, this.f440pt + size);
    }
}
