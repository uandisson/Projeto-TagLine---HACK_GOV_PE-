package org.locationtech.jts.index.strtree;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.util.PriorityQueue;

class BoundablePair implements Comparable {
    private Boundable boundable1;
    private Boundable boundable2;
    private double distance = distance();
    private ItemDistance itemDistance;

    public BoundablePair(Boundable boundable12, Boundable boundable22, ItemDistance itemDistance2) {
        this.boundable1 = boundable12;
        this.boundable2 = boundable22;
        this.itemDistance = itemDistance2;
    }

    public Boundable getBoundable(int i) {
        if (i == 0) {
            return this.boundable1;
        }
        return this.boundable2;
    }

    private double distance() {
        if (isLeaves()) {
            return this.itemDistance.distance((ItemBoundable) this.boundable1, (ItemBoundable) this.boundable2);
        }
        return ((Envelope) this.boundable1.getBounds()).distance((Envelope) this.boundable2.getBounds());
    }

    public double getDistance() {
        return this.distance;
    }

    public int compareTo(Object o) {
        BoundablePair nd = (BoundablePair) o;
        if (this.distance < nd.distance) {
            return -1;
        }
        if (this.distance > nd.distance) {
            return 1;
        }
        return 0;
    }

    public boolean isLeaves() {
        return !isComposite(this.boundable1) && !isComposite(this.boundable2);
    }

    public static boolean isComposite(Object item) {
        return item instanceof AbstractNode;
    }

    private static double area(Boundable b) {
        return ((Envelope) b.getBounds()).getArea();
    }

    public void expandToQueue(PriorityQueue priorityQueue, double d) {
        Throwable th;
        PriorityQueue priQ = priorityQueue;
        double minDistance = d;
        boolean isComp1 = isComposite(this.boundable1);
        boolean isComp2 = isComposite(this.boundable2);
        if (!isComp1 || !isComp2) {
            if (isComp1) {
                expand(this.boundable1, this.boundable2, priQ, minDistance);
            } else if (isComp2) {
                expand(this.boundable2, this.boundable1, priQ, minDistance);
            } else {
                Throwable th2 = th;
                new IllegalArgumentException("neither boundable is composite");
                throw th2;
            }
        } else if (area(this.boundable1) > area(this.boundable2)) {
            expand(this.boundable1, this.boundable2, priQ, minDistance);
        } else {
            expand(this.boundable2, this.boundable1, priQ, minDistance);
        }
    }

    private void expand(Boundable bndComposite, Boundable boundable, PriorityQueue priorityQueue, double d) {
        BoundablePair boundablePair;
        Boundable bndOther = boundable;
        PriorityQueue priQ = priorityQueue;
        double minDistance = d;
        for (Boundable child : ((AbstractNode) bndComposite).getChildBoundables()) {
            new BoundablePair(child, bndOther, this.itemDistance);
            BoundablePair bp = boundablePair;
            if (bp.getDistance() < minDistance) {
                priQ.add(bp);
            }
        }
    }
}
