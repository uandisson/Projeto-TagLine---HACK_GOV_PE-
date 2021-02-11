package org.locationtech.jts.index.intervalrtree;

import org.locationtech.jts.index.ItemVisitor;

public class IntervalRTreeLeafNode extends IntervalRTreeNode {
    private Object item;

    public IntervalRTreeLeafNode(double min, double max, Object item2) {
        this.min = min;
        this.max = max;
        this.item = item2;
    }

    public void query(double queryMin, double queryMax, ItemVisitor itemVisitor) {
        ItemVisitor visitor = itemVisitor;
        if (intersects(queryMin, queryMax)) {
            visitor.visitItem(this.item);
        }
    }
}
