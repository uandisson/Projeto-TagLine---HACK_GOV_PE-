package org.locationtech.jts.index.intervalrtree;

import org.locationtech.jts.index.ItemVisitor;

public class IntervalRTreeBranchNode extends IntervalRTreeNode {
    private IntervalRTreeNode node1;
    private IntervalRTreeNode node2;

    public IntervalRTreeBranchNode(IntervalRTreeNode n1, IntervalRTreeNode n2) {
        this.node1 = n1;
        this.node2 = n2;
        buildExtent(this.node1, this.node2);
    }

    private void buildExtent(IntervalRTreeNode intervalRTreeNode, IntervalRTreeNode intervalRTreeNode2) {
        IntervalRTreeNode n1 = intervalRTreeNode;
        IntervalRTreeNode n2 = intervalRTreeNode2;
        this.min = Math.min(n1.min, n2.min);
        this.max = Math.max(n1.max, n2.max);
    }

    public void query(double d, double d2, ItemVisitor itemVisitor) {
        double queryMin = d;
        double queryMax = d2;
        ItemVisitor visitor = itemVisitor;
        if (intersects(queryMin, queryMax)) {
            if (this.node1 != null) {
                this.node1.query(queryMin, queryMax, visitor);
            }
            if (this.node2 != null) {
                this.node2.query(queryMin, queryMax, visitor);
            }
        }
    }
}
