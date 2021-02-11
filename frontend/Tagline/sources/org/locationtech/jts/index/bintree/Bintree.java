package org.locationtech.jts.index.bintree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Bintree {
    private double minExtent = 1.0d;
    private Root root;

    public static Interval ensureExtent(Interval interval, double d) {
        Interval itemInterval;
        Interval itemInterval2 = interval;
        double minExtent2 = d;
        double min = itemInterval2.getMin();
        double max = itemInterval2.getMax();
        if (min != max) {
            return itemInterval2;
        }
        if (min == max) {
            min -= minExtent2 / 2.0d;
            max = min + (minExtent2 / 2.0d);
        }
        new Interval(min, max);
        return itemInterval;
    }

    public Bintree() {
        Root root2;
        new Root();
        this.root = root2;
    }

    public int depth() {
        if (this.root != null) {
            return this.root.depth();
        }
        return 0;
    }

    public int size() {
        if (this.root != null) {
            return this.root.size();
        }
        return 0;
    }

    public int nodeSize() {
        if (this.root != null) {
            return this.root.nodeSize();
        }
        return 0;
    }

    public void insert(Interval interval, Object item) {
        Interval itemInterval = interval;
        collectStats(itemInterval);
        this.root.insert(ensureExtent(itemInterval, this.minExtent), item);
    }

    public boolean remove(Interval itemInterval, Object item) {
        return this.root.remove(ensureExtent(itemInterval, this.minExtent), item);
    }

    public Iterator iterator() {
        List list;
        new ArrayList();
        List foundItems = list;
        List addAllItems = this.root.addAllItems(foundItems);
        return foundItems.iterator();
    }

    public List query(double d) {
        Interval interval;
        double x = d;
        new Interval(x, x);
        return query(interval);
    }

    public List query(Interval interval) {
        List list;
        new ArrayList();
        List foundItems = list;
        query(interval, foundItems);
        return foundItems;
    }

    public void query(Interval interval, Collection foundItems) {
        this.root.addAllItemsFromOverlapping(interval, foundItems);
    }

    private void collectStats(Interval interval) {
        double del = interval.getWidth();
        if (del < this.minExtent && del > 0.0d) {
            this.minExtent = del;
        }
    }
}
