package org.locationtech.jts.index.quadtree;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.util.Assert;

public class Root extends NodeBase {
    private static final Coordinate origin;

    static {
        Coordinate coordinate;
        new Coordinate(0.0d, 0.0d);
        origin = coordinate;
    }

    public Root() {
    }

    public void insert(Envelope envelope, Object obj) {
        Envelope itemEnv = envelope;
        Object item = obj;
        int index = getSubnodeIndex(itemEnv, origin.f412x, origin.f413y);
        if (index == -1) {
            add(item);
            return;
        }
        Node node = this.subnode[index];
        if (node == null || !node.getEnvelope().contains(itemEnv)) {
            this.subnode[index] = Node.createExpanded(node, itemEnv);
        }
        insertContained(this.subnode[index], itemEnv, item);
    }

    private void insertContained(Node node, Envelope envelope, Object obj) {
        NodeBase node2;
        Node tree = node;
        Envelope itemEnv = envelope;
        Object item = obj;
        Assert.isTrue(tree.getEnvelope().contains(itemEnv));
        boolean isZeroX = IntervalSize.isZeroWidth(itemEnv.getMinX(), itemEnv.getMaxX());
        boolean isZeroY = IntervalSize.isZeroWidth(itemEnv.getMinY(), itemEnv.getMaxY());
        if (isZeroX || isZeroY) {
            node2 = tree.find(itemEnv);
        } else {
            node2 = tree.getNode(itemEnv);
        }
        node2.add(item);
    }

    /* access modifiers changed from: protected */
    public boolean isSearchMatch(Envelope envelope) {
        Envelope envelope2 = envelope;
        return true;
    }
}
