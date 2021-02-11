package org.locationtech.jts.index.bintree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class NodeBase {
    protected List items;
    protected Node[] subnode = new Node[2];

    /* access modifiers changed from: protected */
    public abstract boolean isSearchMatch(Interval interval);

    public static int getSubnodeIndex(Interval interval, double d) {
        Interval interval2 = interval;
        double centre = d;
        int subnodeIndex = -1;
        if (interval2.min >= centre) {
            subnodeIndex = 1;
        }
        if (interval2.max <= centre) {
            subnodeIndex = 0;
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

    public void add(Object item) {
        boolean add = this.items.add(item);
    }

    public List addAllItems(List list) {
        List items2 = list;
        boolean addAll = items2.addAll(this.items);
        for (int i = 0; i < 2; i++) {
            if (this.subnode[i] != null) {
                List addAllItems = this.subnode[i].addAllItems(items2);
            }
        }
        return items2;
    }

    public void addAllItemsFromOverlapping(Interval interval, Collection collection) {
        Interval interval2 = interval;
        Collection resultItems = collection;
        if (interval2 == null || isSearchMatch(interval2)) {
            boolean addAll = resultItems.addAll(this.items);
            if (this.subnode[0] != null) {
                this.subnode[0].addAllItemsFromOverlapping(interval2, resultItems);
            }
            if (this.subnode[1] != null) {
                this.subnode[1].addAllItemsFromOverlapping(interval2, resultItems);
            }
        }
    }

    public boolean remove(Interval interval, Object obj) {
        Interval itemInterval = interval;
        Object item = obj;
        if (!isSearchMatch(itemInterval)) {
            return false;
        }
        boolean found = false;
        int i = 0;
        while (true) {
            if (i >= 2) {
                break;
            }
            if (this.subnode[i] != null) {
                found = this.subnode[i].remove(itemInterval, item);
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
        for (int i = 0; i < 2; i++) {
            if (this.subnode[i] != null) {
                return true;
            }
        }
        return false;
    }

    public boolean hasItems() {
        return !this.items.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public int depth() {
        int sqd;
        int maxSubDepth = 0;
        for (int i = 0; i < 2; i++) {
            if (this.subnode[i] != null && (sqd = this.subnode[i].depth()) > maxSubDepth) {
                maxSubDepth = sqd;
            }
        }
        return maxSubDepth + 1;
    }

    /* access modifiers changed from: package-private */
    public int size() {
        int subSize = 0;
        for (int i = 0; i < 2; i++) {
            if (this.subnode[i] != null) {
                subSize += this.subnode[i].size();
            }
        }
        return subSize + this.items.size();
    }

    /* access modifiers changed from: package-private */
    public int nodeSize() {
        int subSize = 0;
        for (int i = 0; i < 2; i++) {
            if (this.subnode[i] != null) {
                subSize += this.subnode[i].nodeSize();
            }
        }
        return subSize + 1;
    }
}
