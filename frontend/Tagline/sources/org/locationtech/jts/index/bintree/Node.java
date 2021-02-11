package org.locationtech.jts.index.bintree;

import org.locationtech.jts.util.Assert;

public class Node extends NodeBase {
    private double centre;
    private Interval interval;
    private int level;

    public static Node createNode(Interval itemInterval) {
        Key key;
        Node node;
        new Key(itemInterval);
        Key key2 = key;
        new Node(key2.getInterval(), key2.getLevel());
        return node;
    }

    public static Node createExpanded(Node node, Interval addInterval) {
        Interval interval2;
        Node node2 = node;
        new Interval(addInterval);
        Interval expandInt = interval2;
        if (node2 != null) {
            expandInt.expandToInclude(node2.interval);
        }
        Node largerNode = createNode(expandInt);
        if (node2 != null) {
            largerNode.insert(node2);
        }
        return largerNode;
    }

    public Node(Interval interval2, int level2) {
        Interval interval3 = interval2;
        this.interval = interval3;
        this.level = level2;
        this.centre = (interval3.getMin() + interval3.getMax()) / 2.0d;
    }

    public Interval getInterval() {
        return this.interval;
    }

    /* access modifiers changed from: protected */
    public boolean isSearchMatch(Interval itemInterval) {
        return itemInterval.overlaps(this.interval);
    }

    public Node getNode(Interval interval2) {
        Interval searchInterval = interval2;
        int subnodeIndex = getSubnodeIndex(searchInterval, this.centre);
        if (subnodeIndex != -1) {
            return getSubnode(subnodeIndex).getNode(searchInterval);
        }
        return this;
    }

    public NodeBase find(Interval interval2) {
        Interval searchInterval = interval2;
        int subnodeIndex = getSubnodeIndex(searchInterval, this.centre);
        if (subnodeIndex == -1) {
            return this;
        } else if (this.subnode[subnodeIndex] != null) {
            return this.subnode[subnodeIndex].find(searchInterval);
        } else {
            return this;
        }
    }

    /* access modifiers changed from: package-private */
    public void insert(Node node) {
        Node node2 = node;
        Assert.isTrue(this.interval == null || this.interval.contains(node2.interval));
        int index = getSubnodeIndex(node2.interval, this.centre);
        if (node2.level == this.level - 1) {
            this.subnode[index] = node2;
            return;
        }
        Node childNode = createSubnode(index);
        childNode.insert(node2);
        this.subnode[index] = childNode;
    }

    private Node getSubnode(int i) {
        int index = i;
        if (this.subnode[index] == null) {
            this.subnode[index] = createSubnode(index);
        }
        return this.subnode[index];
    }

    private Node createSubnode(int index) {
        Interval subInt;
        Node node;
        double min = 0.0d;
        double max = 0.0d;
        switch (index) {
            case 0:
                min = this.interval.getMin();
                max = this.centre;
                break;
            case 1:
                min = this.centre;
                max = this.interval.getMax();
                break;
        }
        new Interval(min, max);
        new Node(subInt, this.level - 1);
        return node;
    }
}
