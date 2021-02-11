package org.locationtech.jts.index.intervalrtree;

import java.util.Comparator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.index.ItemVisitor;
import org.locationtech.jts.p006io.WKTWriter;

public abstract class IntervalRTreeNode {
    protected double max = Double.NEGATIVE_INFINITY;
    protected double min = Double.POSITIVE_INFINITY;

    public abstract void query(double d, double d2, ItemVisitor itemVisitor);

    public IntervalRTreeNode() {
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    /* access modifiers changed from: protected */
    public boolean intersects(double d, double queryMax) {
        double queryMin = d;
        if (this.min > queryMax || this.max < queryMin) {
            return false;
        }
        return true;
    }

    public String toString() {
        Coordinate coordinate;
        Coordinate coordinate2;
        new Coordinate(this.min, 0.0d);
        new Coordinate(this.max, 0.0d);
        return WKTWriter.toLineString(coordinate, coordinate2);
    }

    public static class NodeComparator implements Comparator {
        public NodeComparator() {
        }

        public int compare(Object o1, Object o2) {
            IntervalRTreeNode n1 = (IntervalRTreeNode) o1;
            IntervalRTreeNode n2 = (IntervalRTreeNode) o2;
            double mid1 = (n1.min + n1.max) / 2.0d;
            double mid2 = (n2.min + n2.max) / 2.0d;
            if (mid1 < mid2) {
                return -1;
            }
            if (mid1 > mid2) {
                return 1;
            }
            return 0;
        }
    }
}
