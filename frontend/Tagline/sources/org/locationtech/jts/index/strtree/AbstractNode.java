package org.locationtech.jts.index.strtree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.util.Assert;

public abstract class AbstractNode implements Boundable, Serializable {
    private static final long serialVersionUID = 6493722185909573708L;
    private Object bounds = null;
    private ArrayList childBoundables;
    private int level;

    /* access modifiers changed from: protected */
    public abstract Object computeBounds();

    public AbstractNode() {
        ArrayList arrayList;
        new ArrayList();
        this.childBoundables = arrayList;
    }

    public AbstractNode(int level2) {
        ArrayList arrayList;
        new ArrayList();
        this.childBoundables = arrayList;
        this.level = level2;
    }

    public List getChildBoundables() {
        return this.childBoundables;
    }

    public Object getBounds() {
        if (this.bounds == null) {
            this.bounds = computeBounds();
        }
        return this.bounds;
    }

    public int getLevel() {
        return this.level;
    }

    public int size() {
        return this.childBoundables.size();
    }

    public boolean isEmpty() {
        return this.childBoundables.isEmpty();
    }

    public void addChildBoundable(Boundable boundable) {
        Boundable childBoundable = boundable;
        Assert.isTrue(this.bounds == null);
        boolean add = this.childBoundables.add(childBoundable);
    }
}
