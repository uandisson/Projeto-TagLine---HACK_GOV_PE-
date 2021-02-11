package org.locationtech.jts.util;

import java.util.ArrayList;

public class PriorityQueue {
    private ArrayList items;
    private int size = 0;

    public PriorityQueue() {
        ArrayList arrayList;
        new ArrayList();
        this.items = arrayList;
        boolean add = this.items.add((Object) null);
    }

    public void add(Comparable comparable) {
        Comparable x = comparable;
        boolean add = this.items.add((Object) null);
        this.size++;
        int hole = this.size;
        Object obj = this.items.set(0, x);
        while (x.compareTo(this.items.get(hole / 2)) < 0) {
            Object obj2 = this.items.set(hole, this.items.get(hole / 2));
            hole /= 2;
        }
        Object obj3 = this.items.set(hole, x);
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        this.size = 0;
        this.items.clear();
    }

    public Object poll() {
        if (isEmpty()) {
            return null;
        }
        Object minItem = this.items.get(1);
        Object obj = this.items.set(1, this.items.get(this.size));
        this.size--;
        reorder(1);
        return minItem;
    }

    private void reorder(int i) {
        int hole = i;
        Object tmp = this.items.get(hole);
        while (hole * 2 <= this.size) {
            int child = hole * 2;
            if (child != this.size && ((Comparable) this.items.get(child + 1)).compareTo(this.items.get(child)) < 0) {
                child++;
            }
            if (((Comparable) this.items.get(child)).compareTo(tmp) >= 0) {
                break;
            }
            Object obj = this.items.set(hole, this.items.get(child));
            hole = child;
        }
        Object obj2 = this.items.set(hole, tmp);
    }
}
