package org.locationtech.jts.index.bintree;

import org.locationtech.jts.index.quadtree.IntervalSize;
import org.locationtech.jts.util.Assert;

public class Root extends NodeBase {
    private static final double origin = 0.0d;

    public Root() {
    }

    public void insert(Interval interval, Object obj) {
        Interval itemInterval = interval;
        Object item = obj;
        int index = getSubnodeIndex(itemInterval, 0.0d);
        if (index == -1) {
            add(item);
            return;
        }
        Node node = this.subnode[index];
        if (node == null || !node.getInterval().contains(itemInterval)) {
            this.subnode[index] = Node.createExpanded(node, itemInterval);
        }
        insertContained(this.subnode[index], itemInterval, item);
    }

    private void insertContained(Node node, Interval interval, Object obj) {
        NodeBase node2;
        Node tree = node;
        Interval itemInterval = interval;
        Object item = obj;
        Assert.isTrue(tree.getInterval().contains(itemInterval));
        if (IntervalSize.isZeroWidth(itemInterval.getMin(), itemInterval.getMax())) {
            node2 = tree.find(itemInterval);
        } else {
            node2 = tree.getNode(itemInterval);
        }
        node2.add(item);
    }

    /* access modifiers changed from: protected */
    public boolean isSearchMatch(Interval interval) {
        Interval interval2 = interval;
        return true;
    }
}
