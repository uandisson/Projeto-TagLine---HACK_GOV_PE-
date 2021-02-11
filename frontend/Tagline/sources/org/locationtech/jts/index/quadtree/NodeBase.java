package org.locationtech.jts.index.quadtree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.ItemVisitor;

public abstract class NodeBase implements Serializable {
    protected List items;
    protected Node[] subnode = new Node[4];

    /* access modifiers changed from: protected */
    public abstract boolean isSearchMatch(Envelope envelope);

    public static int getSubnodeIndex(Envelope envelope, double d, double d2) {
        Envelope env = envelope;
        double centrex = d;
        double centrey = d2;
        int subnodeIndex = -1;
        if (env.getMinX() >= centrex) {
            if (env.getMinY() >= centrey) {
                subnodeIndex = 3;
            }
            if (env.getMaxY() <= centrey) {
                subnodeIndex = 1;
            }
        }
        if (env.getMaxX() <= centrex) {
            if (env.getMinY() >= centrey) {
                subnodeIndex = 2;
            }
            if (env.getMaxY() <= centrey) {
                subnodeIndex = 0;
            }
        }
        return subnodeIndex;
    }

    public NodeBase() {
        List list;
        new ArrayList();
        this.items = list;
    }

    public List getItems() {
        return this.items;
    }

    public boolean hasItems() {
        return !this.items.isEmpty();
    }

    public void add(Object item) {
        boolean add = this.items.add(item);
    }

    public boolean remove(Envelope envelope, Object obj) {
        Envelope itemEnv = envelope;
        Object item = obj;
        if (!isSearchMatch(itemEnv)) {
            return false;
        }
        boolean found = false;
        int i = 0;
        while (true) {
            if (i >= 4) {
                break;
            }
            if (this.subnode[i] != null) {
                found = this.subnode[i].remove(itemEnv, item);
                if (found) {
                    if (this.subnode[i].isPrunable()) {
                        this.subnode[i] = null;
                    }
                }
            }
            i++;
        }
        if (found) {
            return found;
        }
        return this.items.remove(item);
    }

    public boolean isPrunable() {
        return !hasChildren() && !hasItems();
    }

    public boolean hasChildren() {
        for (int i = 0; i < 4; i++) {
            if (this.subnode[i] != null) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        boolean isEmpty = true;
        if (!this.items.isEmpty()) {
            isEmpty = false;
        }
        for (int i = 0; i < 4; i++) {
            if (this.subnode[i] != null && !this.subnode[i].isEmpty()) {
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    public List addAllItems(List list) {
        List resultItems = list;
        boolean addAll = resultItems.addAll(this.items);
        for (int i = 0; i < 4; i++) {
            if (this.subnode[i] != null) {
                List addAllItems = this.subnode[i].addAllItems(resultItems);
            }
        }
        return resultItems;
    }

    public void addAllItemsFromOverlapping(Envelope envelope, List list) {
        Envelope searchEnv = envelope;
        List resultItems = list;
        if (isSearchMatch(searchEnv)) {
            boolean addAll = resultItems.addAll(this.items);
            for (int i = 0; i < 4; i++) {
                if (this.subnode[i] != null) {
                    this.subnode[i].addAllItemsFromOverlapping(searchEnv, resultItems);
                }
            }
        }
    }

    public void visit(Envelope envelope, ItemVisitor itemVisitor) {
        Envelope searchEnv = envelope;
        ItemVisitor visitor = itemVisitor;
        if (isSearchMatch(searchEnv)) {
            visitItems(searchEnv, visitor);
            for (int i = 0; i < 4; i++) {
                if (this.subnode[i] != null) {
                    this.subnode[i].visit(searchEnv, visitor);
                }
            }
        }
    }

    private void visitItems(Envelope envelope, ItemVisitor itemVisitor) {
        Envelope envelope2 = envelope;
        ItemVisitor visitor = itemVisitor;
        for (Object visitItem : this.items) {
            visitor.visitItem(visitItem);
        }
    }

    /* access modifiers changed from: package-private */
    public int depth() {
        int sqd;
        int maxSubDepth = 0;
        for (int i = 0; i < 4; i++) {
            if (this.subnode[i] != null && (sqd = this.subnode[i].depth()) > maxSubDepth) {
                maxSubDepth = sqd;
            }
        }
        return maxSubDepth + 1;
    }

    /* access modifiers changed from: package-private */
    public int size() {
        int subSize = 0;
        for (int i = 0; i < 4; i++) {
            if (this.subnode[i] != null) {
                subSize += this.subnode[i].size();
            }
        }
        return subSize + this.items.size();
    }

    /* access modifiers changed from: package-private */
    public int getNodeCount() {
        int subSize = 0;
        for (int i = 0; i < 4; i++) {
            if (this.subnode[i] != null) {
                subSize += this.subnode[i].size();
            }
        }
        return subSize + 1;
    }
}
