package org.locationtech.jts.index.strtree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.ItemVisitor;
import org.locationtech.jts.index.SpatialIndex;
import org.locationtech.jts.index.strtree.AbstractSTRtree;
import org.locationtech.jts.util.Assert;
import org.locationtech.jts.util.PriorityQueue;

public class STRtree extends AbstractSTRtree implements SpatialIndex, Serializable {
    private static final int DEFAULT_NODE_CAPACITY = 10;
    private static AbstractSTRtree.IntersectsOp intersectsOp = null;
    private static final long serialVersionUID = 259274702368956900L;
    private static Comparator xComparator;
    private static Comparator yComparator;

    private static final class STRtreeNode extends AbstractNode {
        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ STRtreeNode(int x0, C15481 r7) {
            this(x0);
            C15481 r2 = r7;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        private STRtreeNode(int level) {
            super(level);
        }

        /* access modifiers changed from: protected */
        public Object computeBounds() {
            Envelope envelope;
            Envelope bounds = null;
            for (Boundable childBoundable : getChildBoundables()) {
                if (bounds == null) {
                    new Envelope((Envelope) childBoundable.getBounds());
                    bounds = envelope;
                } else {
                    bounds.expandToInclude((Envelope) childBoundable.getBounds());
                }
            }
            return bounds;
        }
    }

    static {
        Comparator comparator;
        Comparator comparator2;
        AbstractSTRtree.IntersectsOp intersectsOp2;
        new Comparator() {
            public int compare(Object o1, Object o2) {
                return AbstractSTRtree.compareDoubles(STRtree.centreX((Envelope) ((Boundable) o1).getBounds()), STRtree.centreX((Envelope) ((Boundable) o2).getBounds()));
            }
        };
        xComparator = comparator;
        new Comparator() {
            public int compare(Object o1, Object o2) {
                return AbstractSTRtree.compareDoubles(STRtree.centreY((Envelope) ((Boundable) o1).getBounds()), STRtree.centreY((Envelope) ((Boundable) o2).getBounds()));
            }
        };
        yComparator = comparator2;
        new AbstractSTRtree.IntersectsOp() {
            public boolean intersects(Object aBounds, Object bBounds) {
                return ((Envelope) aBounds).intersects((Envelope) bBounds);
            }
        };
        intersectsOp = intersectsOp2;
    }

    /* access modifiers changed from: private */
    public static double centreX(Envelope envelope) {
        Envelope e = envelope;
        return avg(e.getMinX(), e.getMaxX());
    }

    /* access modifiers changed from: private */
    public static double centreY(Envelope envelope) {
        Envelope e = envelope;
        return avg(e.getMinY(), e.getMaxY());
    }

    private static double avg(double a, double b) {
        return (a + b) / 2.0d;
    }

    /* access modifiers changed from: protected */
    public List createParentBoundables(List list, int i) {
        ArrayList arrayList;
        List childBoundables = list;
        int newLevel = i;
        Assert.isTrue(!childBoundables.isEmpty());
        int minLeafCount = (int) Math.ceil(((double) childBoundables.size()) / ((double) getNodeCapacity()));
        new ArrayList(childBoundables);
        ArrayList sortedChildBoundables = arrayList;
        Collections.sort(sortedChildBoundables, xComparator);
        return createParentBoundablesFromVerticalSlices(verticalSlices(sortedChildBoundables, (int) Math.ceil(Math.sqrt((double) minLeafCount))), newLevel);
    }

    private List createParentBoundablesFromVerticalSlices(List[] listArr, int i) {
        List list;
        List[] verticalSlices = listArr;
        int newLevel = i;
        Assert.isTrue(verticalSlices.length > 0);
        new ArrayList();
        List parentBoundables = list;
        for (int i2 = 0; i2 < verticalSlices.length; i2++) {
            boolean addAll = parentBoundables.addAll(createParentBoundablesFromVerticalSlice(verticalSlices[i2], newLevel));
        }
        return parentBoundables;
    }

    /* access modifiers changed from: protected */
    public List createParentBoundablesFromVerticalSlice(List childBoundables, int newLevel) {
        return super.createParentBoundables(childBoundables, newLevel);
    }

    /* access modifiers changed from: protected */
    public List[] verticalSlices(List list, int i) {
        List list2;
        List childBoundables = list;
        int sliceCount = i;
        int sliceCapacity = (int) Math.ceil(((double) childBoundables.size()) / ((double) sliceCount));
        List[] slices = new List[sliceCount];
        Iterator i2 = childBoundables.iterator();
        for (int j = 0; j < sliceCount; j++) {
            new ArrayList();
            slices[j] = list2;
            int boundablesAddedToSlice = 0;
            while (i2.hasNext() && boundablesAddedToSlice < sliceCapacity) {
                boolean add = slices[j].add((Boundable) i2.next());
                boundablesAddedToSlice++;
            }
        }
        return slices;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public STRtree() {
        this(10);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public STRtree(int nodeCapacity) {
        super(nodeCapacity);
    }

    /* access modifiers changed from: protected */
    public AbstractNode createNode(int level) {
        AbstractNode abstractNode;
        new STRtreeNode(level, (C15481) null);
        return abstractNode;
    }

    /* access modifiers changed from: protected */
    public AbstractSTRtree.IntersectsOp getIntersectsOp() {
        return intersectsOp;
    }

    public void insert(Envelope envelope, Object obj) {
        Envelope itemEnv = envelope;
        Object item = obj;
        if (!itemEnv.isNull()) {
            super.insert(itemEnv, item);
        }
    }

    public List query(Envelope searchEnv) {
        return super.query(searchEnv);
    }

    public void query(Envelope searchEnv, ItemVisitor visitor) {
        super.query(searchEnv, visitor);
    }

    public boolean remove(Envelope itemEnv, Object item) {
        return super.remove(itemEnv, item);
    }

    public int size() {
        return super.size();
    }

    public int depth() {
        return super.depth();
    }

    /* access modifiers changed from: protected */
    public Comparator getComparator() {
        return yComparator;
    }

    public Object[] nearestNeighbour(ItemDistance itemDist) {
        BoundablePair bp;
        new BoundablePair(getRoot(), getRoot(), itemDist);
        return nearestNeighbour(bp);
    }

    public Object[] nearestNeighbour(Envelope env, Object item, ItemDistance itemDist, int k) {
        Boundable bnd;
        BoundablePair bp;
        new ItemBoundable(env, item);
        new BoundablePair(getRoot(), bnd, itemDist);
        return nearestNeighbour(bp, k);
    }

    public Object nearestNeighbour(Envelope env, Object item, ItemDistance itemDist) {
        Boundable bnd;
        BoundablePair bp;
        new ItemBoundable(env, item);
        new BoundablePair(getRoot(), bnd, itemDist);
        return nearestNeighbour(bp)[0];
    }

    public Object[] nearestNeighbour(STRtree tree, ItemDistance itemDist) {
        BoundablePair bp;
        new BoundablePair(getRoot(), tree.getRoot(), itemDist);
        return nearestNeighbour(bp);
    }

    private Object[] nearestNeighbour(BoundablePair initBndPair) {
        return nearestNeighbour(initBndPair, Double.POSITIVE_INFINITY);
    }

    private Object[] nearestNeighbour(BoundablePair initBndPair, int k) {
        return nearestNeighbour(initBndPair, Double.POSITIVE_INFINITY, k);
    }

    private Object[] nearestNeighbour(BoundablePair initBndPair, double maxDistance) {
        PriorityQueue priorityQueue;
        double distanceLowerBound = maxDistance;
        BoundablePair minPair = null;
        new PriorityQueue();
        PriorityQueue priQ = priorityQueue;
        priQ.add(initBndPair);
        while (!priQ.isEmpty() && distanceLowerBound > 0.0d) {
            BoundablePair bndPair = (BoundablePair) priQ.poll();
            double currentDistance = bndPair.getDistance();
            if (currentDistance >= distanceLowerBound) {
                break;
            } else if (bndPair.isLeaves()) {
                distanceLowerBound = currentDistance;
                minPair = bndPair;
            } else {
                bndPair.expandToQueue(priQ, distanceLowerBound);
            }
        }
        Object[] objArr = new Object[2];
        objArr[0] = ((ItemBoundable) minPair.getBoundable(0)).getItem();
        Object[] objArr2 = objArr;
        objArr2[1] = ((ItemBoundable) minPair.getBoundable(1)).getItem();
        return objArr2;
    }

    private Object[] nearestNeighbour(BoundablePair initBndPair, double maxDistance, int i) {
        PriorityQueue priorityQueue;
        java.util.PriorityQueue priorityQueue2;
        Comparator comparator;
        int k = i;
        double distanceLowerBound = maxDistance;
        new PriorityQueue();
        PriorityQueue priQ = priorityQueue;
        priQ.add(initBndPair);
        new BoundablePairDistanceComparator(false);
        new java.util.PriorityQueue(k, comparator);
        java.util.PriorityQueue priorityQueue3 = priorityQueue2;
        while (!priQ.isEmpty() && distanceLowerBound >= 0.0d) {
            BoundablePair bndPair = (BoundablePair) priQ.poll();
            double currentDistance = bndPair.getDistance();
            if (currentDistance >= distanceLowerBound) {
                break;
            } else if (!bndPair.isLeaves()) {
                bndPair.expandToQueue(priQ, distanceLowerBound);
            } else if (priorityQueue3.size() < k) {
                boolean add = priorityQueue3.add(bndPair);
            } else {
                if (((BoundablePair) priorityQueue3.peek()).getDistance() > currentDistance) {
                    Object poll = priorityQueue3.poll();
                    boolean add2 = priorityQueue3.add(bndPair);
                }
                distanceLowerBound = ((BoundablePair) priorityQueue3.peek()).getDistance();
            }
        }
        return getItems(priorityQueue3);
    }

    private static Object[] getItems(java.util.PriorityQueue<BoundablePair> priorityQueue) {
        java.util.PriorityQueue<BoundablePair> kNearestNeighbors = priorityQueue;
        Object[] items = new Object[kNearestNeighbors.size()];
        Iterator<BoundablePair> resultIterator = kNearestNeighbors.iterator();
        int count = 0;
        while (resultIterator.hasNext()) {
            items[count] = ((ItemBoundable) resultIterator.next().getBoundable(0)).getItem();
            count++;
        }
        return items;
    }
}
