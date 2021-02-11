package org.locationtech.jts.index.strtree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.index.ItemVisitor;
import org.locationtech.jts.util.Assert;

public abstract class AbstractSTRtree implements Serializable {
    private static final int DEFAULT_NODE_CAPACITY = 10;
    private static final long serialVersionUID = -3886435814360241337L;
    private boolean built;
    private ArrayList itemBoundables;
    private int nodeCapacity;
    protected AbstractNode root;

    protected interface IntersectsOp {
        boolean intersects(Object obj, Object obj2);
    }

    /* access modifiers changed from: protected */
    public abstract AbstractNode createNode(int i);

    /* access modifiers changed from: protected */
    public abstract Comparator getComparator();

    /* access modifiers changed from: protected */
    public abstract IntersectsOp getIntersectsOp();

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public AbstractSTRtree() {
        this(10);
    }

    public AbstractSTRtree(int i) {
        ArrayList arrayList;
        int nodeCapacity2 = i;
        this.built = false;
        new ArrayList();
        this.itemBoundables = arrayList;
        Assert.isTrue(nodeCapacity2 > 1, "Node capacity must be greater than 1");
        this.nodeCapacity = nodeCapacity2;
    }

    public synchronized void build() {
        AbstractNode createHigherLevels;
        synchronized (this) {
            if (!this.built) {
                if (this.itemBoundables.isEmpty()) {
                    createHigherLevels = createNode(0);
                } else {
                    createHigherLevels = createHigherLevels(this.itemBoundables, -1);
                }
                this.root = createHigherLevels;
                this.itemBoundables = null;
                this.built = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    public List createParentBoundables(List list, int i) {
        ArrayList arrayList;
        ArrayList arrayList2;
        List childBoundables = list;
        int newLevel = i;
        Assert.isTrue(!childBoundables.isEmpty());
        new ArrayList();
        ArrayList parentBoundables = arrayList;
        boolean add = parentBoundables.add(createNode(newLevel));
        new ArrayList(childBoundables);
        ArrayList sortedChildBoundables = arrayList2;
        Collections.sort(sortedChildBoundables, getComparator());
        Iterator i2 = sortedChildBoundables.iterator();
        while (i2.hasNext()) {
            Boundable childBoundable = (Boundable) i2.next();
            if (lastNode(parentBoundables).getChildBoundables().size() == getNodeCapacity()) {
                boolean add2 = parentBoundables.add(createNode(newLevel));
            }
            lastNode(parentBoundables).addChildBoundable(childBoundable);
        }
        return parentBoundables;
    }

    /* access modifiers changed from: protected */
    public AbstractNode lastNode(List list) {
        List nodes = list;
        return (AbstractNode) nodes.get(nodes.size() - 1);
    }

    protected static int compareDoubles(double d, double d2) {
        double a = d;
        double b = d2;
        return a > b ? 1 : a < b ? -1 : 0;
    }

    private AbstractNode createHigherLevels(List list, int i) {
        List boundablesOfALevel = list;
        int level = i;
        Assert.isTrue(!boundablesOfALevel.isEmpty());
        List parentBoundables = createParentBoundables(boundablesOfALevel, level + 1);
        if (parentBoundables.size() == 1) {
            return (AbstractNode) parentBoundables.get(0);
        }
        return createHigherLevels(parentBoundables, level + 1);
    }

    public AbstractNode getRoot() {
        build();
        return this.root;
    }

    public int getNodeCapacity() {
        return this.nodeCapacity;
    }

    public boolean isEmpty() {
        if (!this.built) {
            return this.itemBoundables.isEmpty();
        }
        return this.root.isEmpty();
    }

    /* access modifiers changed from: protected */
    public int size() {
        if (isEmpty()) {
            return 0;
        }
        build();
        return size(this.root);
    }

    /* access modifiers changed from: protected */
    public int size(AbstractNode node) {
        int size = 0;
        for (Boundable childBoundable : node.getChildBoundables()) {
            if (childBoundable instanceof AbstractNode) {
                size += size((AbstractNode) childBoundable);
            } else if (childBoundable instanceof ItemBoundable) {
                size++;
            }
        }
        return size;
    }

    /* access modifiers changed from: protected */
    public int depth() {
        if (isEmpty()) {
            return 0;
        }
        build();
        return depth(this.root);
    }

    /* access modifiers changed from: protected */
    public int depth(AbstractNode node) {
        int childDepth;
        int maxChildDepth = 0;
        for (Boundable childBoundable : node.getChildBoundables()) {
            if ((childBoundable instanceof AbstractNode) && (childDepth = depth((AbstractNode) childBoundable)) > maxChildDepth) {
                maxChildDepth = childDepth;
            }
        }
        return maxChildDepth + 1;
    }

    /* access modifiers changed from: protected */
    public void insert(Object obj, Object obj2) {
        Object obj3;
        Object bounds = obj;
        Object item = obj2;
        Assert.isTrue(!this.built, "Cannot insert items into an STR packed R-tree after it has been built.");
        new ItemBoundable(bounds, item);
        boolean add = this.itemBoundables.add(obj3);
    }

    /* access modifiers changed from: protected */
    public List query(Object obj) {
        ArrayList arrayList;
        Object searchBounds = obj;
        build();
        new ArrayList();
        ArrayList matches = arrayList;
        if (isEmpty()) {
            return matches;
        }
        if (getIntersectsOp().intersects(this.root.getBounds(), searchBounds)) {
            query(searchBounds, this.root, (List) matches);
        }
        return matches;
    }

    /* access modifiers changed from: protected */
    public void query(Object obj, ItemVisitor itemVisitor) {
        Object searchBounds = obj;
        ItemVisitor visitor = itemVisitor;
        build();
        if (!isEmpty() && getIntersectsOp().intersects(this.root.getBounds(), searchBounds)) {
            query(searchBounds, this.root, visitor);
        }
    }

    private void query(Object obj, AbstractNode node, List list) {
        Object searchBounds = obj;
        List matches = list;
        List childBoundables = node.getChildBoundables();
        for (int i = 0; i < childBoundables.size(); i++) {
            Boundable childBoundable = (Boundable) childBoundables.get(i);
            if (getIntersectsOp().intersects(childBoundable.getBounds(), searchBounds)) {
                if (childBoundable instanceof AbstractNode) {
                    query(searchBounds, (AbstractNode) childBoundable, matches);
                } else if (childBoundable instanceof ItemBoundable) {
                    boolean add = matches.add(((ItemBoundable) childBoundable).getItem());
                } else {
                    Assert.shouldNeverReachHere();
                }
            }
        }
    }

    private void query(Object obj, AbstractNode node, ItemVisitor itemVisitor) {
        Object searchBounds = obj;
        ItemVisitor visitor = itemVisitor;
        List childBoundables = node.getChildBoundables();
        for (int i = 0; i < childBoundables.size(); i++) {
            Boundable childBoundable = (Boundable) childBoundables.get(i);
            if (getIntersectsOp().intersects(childBoundable.getBounds(), searchBounds)) {
                if (childBoundable instanceof AbstractNode) {
                    query(searchBounds, (AbstractNode) childBoundable, visitor);
                } else if (childBoundable instanceof ItemBoundable) {
                    visitor.visitItem(((ItemBoundable) childBoundable).getItem());
                } else {
                    Assert.shouldNeverReachHere();
                }
            }
        }
    }

    public List itemsTree() {
        List list;
        build();
        List valuesTree = itemsTree(this.root);
        if (valuesTree != null) {
            return valuesTree;
        }
        new ArrayList();
        return list;
    }

    private List itemsTree(AbstractNode node) {
        List list;
        new ArrayList();
        List valuesTreeForNode = list;
        for (Boundable childBoundable : node.getChildBoundables()) {
            if (childBoundable instanceof AbstractNode) {
                List valuesTreeForChild = itemsTree((AbstractNode) childBoundable);
                if (valuesTreeForChild != null) {
                    boolean add = valuesTreeForNode.add(valuesTreeForChild);
                }
            } else if (childBoundable instanceof ItemBoundable) {
                boolean add2 = valuesTreeForNode.add(((ItemBoundable) childBoundable).getItem());
            } else {
                Assert.shouldNeverReachHere();
            }
        }
        if (valuesTreeForNode.size() <= 0) {
            return null;
        }
        return valuesTreeForNode;
    }

    /* access modifiers changed from: protected */
    public boolean remove(Object obj, Object obj2) {
        Object searchBounds = obj;
        Object item = obj2;
        build();
        if (getIntersectsOp().intersects(this.root.getBounds(), searchBounds)) {
            return remove(searchBounds, this.root, item);
        }
        return false;
    }

    private boolean removeItem(AbstractNode abstractNode, Object obj) {
        AbstractNode node = abstractNode;
        Object item = obj;
        Boundable childToRemove = null;
        for (Boundable childBoundable : node.getChildBoundables()) {
            if ((childBoundable instanceof ItemBoundable) && ((ItemBoundable) childBoundable).getItem() == item) {
                childToRemove = childBoundable;
            }
        }
        if (childToRemove == null) {
            return false;
        }
        boolean remove = node.getChildBoundables().remove(childToRemove);
        return true;
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean remove(java.lang.Object r13, org.locationtech.jts.index.strtree.AbstractNode r14, java.lang.Object r15) {
        /*
            r12 = this;
            r0 = r12
            r1 = r13
            r2 = r14
            r3 = r15
            r8 = r0
            r9 = r2
            r10 = r3
            boolean r8 = r8.removeItem(r9, r10)
            r4 = r8
            r8 = r4
            if (r8 == 0) goto L_0x0012
            r8 = 1
            r0 = r8
        L_0x0011:
            return r0
        L_0x0012:
            r8 = 0
            r5 = r8
            r8 = r2
            java.util.List r8 = r8.getChildBoundables()
            java.util.Iterator r8 = r8.iterator()
            r6 = r8
        L_0x001e:
            r8 = r6
            boolean r8 = r8.hasNext()
            if (r8 == 0) goto L_0x0056
            r8 = r6
            java.lang.Object r8 = r8.next()
            org.locationtech.jts.index.strtree.Boundable r8 = (org.locationtech.jts.index.strtree.Boundable) r8
            r7 = r8
            r8 = r0
            org.locationtech.jts.index.strtree.AbstractSTRtree$IntersectsOp r8 = r8.getIntersectsOp()
            r9 = r7
            java.lang.Object r9 = r9.getBounds()
            r10 = r1
            boolean r8 = r8.intersects(r9, r10)
            if (r8 != 0) goto L_0x003f
            goto L_0x001e
        L_0x003f:
            r8 = r7
            boolean r8 = r8 instanceof org.locationtech.jts.index.strtree.AbstractNode
            if (r8 == 0) goto L_0x0071
            r8 = r0
            r9 = r1
            r10 = r7
            org.locationtech.jts.index.strtree.AbstractNode r10 = (org.locationtech.jts.index.strtree.AbstractNode) r10
            r11 = r3
            boolean r8 = r8.remove(r9, r10, r11)
            r4 = r8
            r8 = r4
            if (r8 == 0) goto L_0x0071
            r8 = r7
            org.locationtech.jts.index.strtree.AbstractNode r8 = (org.locationtech.jts.index.strtree.AbstractNode) r8
            r5 = r8
        L_0x0056:
            r8 = r5
            if (r8 == 0) goto L_0x006e
            r8 = r5
            java.util.List r8 = r8.getChildBoundables()
            boolean r8 = r8.isEmpty()
            if (r8 == 0) goto L_0x006e
            r8 = r2
            java.util.List r8 = r8.getChildBoundables()
            r9 = r5
            boolean r8 = r8.remove(r9)
        L_0x006e:
            r8 = r4
            r0 = r8
            goto L_0x0011
        L_0x0071:
            goto L_0x001e
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.index.strtree.AbstractSTRtree.remove(java.lang.Object, org.locationtech.jts.index.strtree.AbstractNode, java.lang.Object):boolean");
    }

    /* access modifiers changed from: protected */
    public List boundablesAtLevel(int level) {
        ArrayList arrayList;
        new ArrayList();
        ArrayList boundables = arrayList;
        boundablesAtLevel(level, this.root, boundables);
        return boundables;
    }

    private void boundablesAtLevel(int i, AbstractNode abstractNode, Collection collection) {
        int level = i;
        AbstractNode top = abstractNode;
        Collection boundables = collection;
        Assert.isTrue(level > -2);
        if (top.getLevel() == level) {
            boolean add = boundables.add(top);
            return;
        }
        for (Boundable boundable : top.getChildBoundables()) {
            if (boundable instanceof AbstractNode) {
                boundablesAtLevel(level, (AbstractNode) boundable, boundables);
            } else {
                Assert.isTrue(boundable instanceof ItemBoundable);
                if (level == -1) {
                    boolean add2 = boundables.add(boundable);
                }
            }
        }
    }
}
