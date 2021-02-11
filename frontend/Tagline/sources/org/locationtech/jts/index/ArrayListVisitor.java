package org.locationtech.jts.index;

import java.util.ArrayList;

public class ArrayListVisitor implements ItemVisitor {
    private ArrayList items;

    public ArrayListVisitor() {
        ArrayList arrayList;
        new ArrayList();
        this.items = arrayList;
    }

    public void visitItem(Object item) {
        boolean add = this.items.add(item);
    }

    public ArrayList getItems() {
        return this.items;
    }
}
