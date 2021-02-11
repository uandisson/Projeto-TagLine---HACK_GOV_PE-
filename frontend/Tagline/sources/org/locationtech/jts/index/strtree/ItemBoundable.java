package org.locationtech.jts.index.strtree;

import java.io.Serializable;

public class ItemBoundable implements Boundable, Serializable {
    private Object bounds;
    private Object item;

    public ItemBoundable(Object bounds2, Object item2) {
        this.bounds = bounds2;
        this.item = item2;
    }

    public Object getBounds() {
        return this.bounds;
    }

    public Object getItem() {
        return this.item;
    }
}
