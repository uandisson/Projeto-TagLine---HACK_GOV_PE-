package org.locationtech.jts.index.quadtree;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.util.Assert;

public class Node extends NodeBase {
    private double centrex;
    private double centrey;
    private Envelope env;
    private int level;

    public static Node createNode(Envelope env2) {
        Key key;
        Node node;
        new Key(env2);
        Key key2 = key;
        new Node(key2.getEnvelope(), key2.getLevel());
        return node;
    }

    public static Node createExpanded(Node node, Envelope addEnv) {
        Envelope envelope;
        Node node2 = node;
        new Envelope(addEnv);
        Envelope expandEnv = envelope;
        if (node2 != null) {
            expandEnv.expandToInclude(node2.env);
        }
        Node largerNode = createNode(expandEnv);
        if (node2 != null) {
            largerNode.insertNode(node2);
        }
        return largerNode;
    }

    public Node(Envelope envelope, int level2) {
        Envelope env2 = envelope;
        this.env = env2;
        this.level = level2;
        this.centrex = (env2.getMinX() + env2.getMaxX()) / 2.0d;
        this.centrey = (env2.getMinY() + env2.getMaxY()) / 2.0d;
    }

    public Envelope getEnvelope() {
        return this.env;
    }

    /* access modifiers changed from: protected */
    public boolean isSearchMatch(Envelope envelope) {
        Envelope searchEnv = envelope;
        if (searchEnv == null) {
            return false;
        }
        return this.env.intersects(searchEnv);
    }

    public Node getNode(Envelope envelope) {
        Envelope searchEnv = envelope;
        int subnodeIndex = getSubnodeIndex(searchEnv, this.centrex, this.centrey);
        if (subnodeIndex != -1) {
            return getSubnode(subnodeIndex).getNode(searchEnv);
        }
        return this;
    }

    public NodeBase find(Envelope envelope) {
        Envelope searchEnv = envelope;
        int subnodeIndex = getSubnodeIndex(searchEnv, this.centrex, this.centrey);
        if (subnodeIndex == -1) {
            return this;
        } else if (this.subnode[subnodeIndex] != null) {
            return this.subnode[subnodeIndex].find(searchEnv);
        } else {
            return this;
        }
    }

    /* access modifiers changed from: package-private */
    public void insertNode(Node node) {
        Node node2 = node;
        Assert.isTrue(this.env == null || this.env.contains(node2.env));
        int index = getSubnodeIndex(node2.env, this.centrex, this.centrey);
        if (node2.level == this.level - 1) {
            this.subnode[index] = node2;
            return;
        }
        Node childNode = createSubnode(index);
        childNode.insertNode(node2);
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
        Envelope sqEnv;
        Node node;
        double minx = 0.0d;
        double maxx = 0.0d;
        double miny = 0.0d;
        double maxy = 0.0d;
        switch (index) {
            case 0:
                minx = this.env.getMinX();
                maxx = this.centrex;
                miny = this.env.getMinY();
                maxy = this.centrey;
                break;
            case 1:
                minx = this.centrex;
                maxx = this.env.getMaxX();
                miny = this.env.getMinY();
                maxy = this.centrey;
                break;
            case 2:
                minx = this.env.getMinX();
                maxx = this.centrex;
                miny = this.centrey;
                maxy = this.env.getMaxY();
                break;
            case 3:
                minx = this.centrex;
                maxx = this.env.getMaxX();
                miny = this.centrey;
                maxy = this.env.getMaxY();
                break;
        }
        new Envelope(minx, maxx, miny, maxy);
        new Node(sqEnv, this.level - 1);
        return node;
    }
}
