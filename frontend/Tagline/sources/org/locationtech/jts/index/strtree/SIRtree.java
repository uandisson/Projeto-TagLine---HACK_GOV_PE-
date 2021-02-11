package org.locationtech.jts.index.strtree;

import java.util.Comparator;
import java.util.List;
import org.locationtech.jts.index.strtree.AbstractSTRtree;

public class SIRtree extends AbstractSTRtree {
    private Comparator comparator;
    private AbstractSTRtree.IntersectsOp intersectsOp;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SIRtree() {
        this(10);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SIRtree(int nodeCapacity) {
        super(nodeCapacity);
        Comparator comparator2;
        AbstractSTRtree.IntersectsOp intersectsOp2;
        new Comparator(this) {
            final /* synthetic */ SIRtree this$0;

            {
                this.this$0 = this$0;
            }

            public int compare(Object o1, Object o2) {
                return AbstractSTRtree.compareDoubles(((Interval) ((Boundable) o1).getBounds()).getCentre(), ((Interval) ((Boundable) o2).getBounds()).getCentre());
            }
        };
        this.comparator = comparator2;
        new AbstractSTRtree.IntersectsOp(this) {
            final /* synthetic */ SIRtree this$0;

            {
                this.this$0 = this$0;
            }

            public boolean intersects(Object aBounds, Object bBounds) {
                return ((Interval) aBounds).intersects((Interval) bBounds);
            }
        };
        this.intersectsOp = intersectsOp2;
    }

    /* access modifiers changed from: protected */
    public AbstractNode createNode(int level) {
        AbstractNode abstractNode;
        new AbstractNode(this, level) {
            final /* synthetic */ SIRtree this$0;

            {
                this.this$0 = this$0;
            }

            /* access modifiers changed from: protected */
            public Object computeBounds() {
                Interval interval;
                Interval bounds = null;
                for (Boundable childBoundable : getChildBoundables()) {
                    if (bounds == null) {
                        new Interval((Interval) childBoundable.getBounds());
                        bounds = interval;
                    } else {
                        Interval expandToInclude = bounds.expandToInclude((Interval) childBoundable.getBounds());
                    }
                }
                return bounds;
            }
        };
        return abstractNode;
    }

    public void insert(double d, double d2, Object item) {
        Object obj;
        double x1 = d;
        double x2 = d2;
        new Interval(Math.min(x1, x2), Math.max(x1, x2));
        super.insert(obj, item);
    }

    public List query(double d) {
        double x = d;
        return query(x, x);
    }

    public List query(double d, double d2) {
        Object obj;
        double x1 = d;
        double x2 = d2;
        new Interval(Math.min(x1, x2), Math.max(x1, x2));
        return super.query(obj);
    }

    /* access modifiers changed from: protected */
    public AbstractSTRtree.IntersectsOp getIntersectsOp() {
        return this.intersectsOp;
    }

    /* access modifiers changed from: protected */
    public Comparator getComparator() {
        return this.comparator;
    }
}
