package org.locationtech.jts.awt;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.Collection;
import java.util.Iterator;

public class ShapeCollectionPathIterator implements PathIterator {
    private AffineTransform affineTransform;
    private PathIterator currentPathIterator;
    private boolean done = false;
    private Iterator shapeIterator;

    public ShapeCollectionPathIterator(Collection shapes, AffineTransform affineTransform2) {
        PathIterator pathIterator;
        new PathIterator(this) {
            final /* synthetic */ ShapeCollectionPathIterator this$0;

            {
                this.this$0 = this$0;
            }

            public int getWindingRule() {
                Throwable th;
                Throwable th2 = th;
                new UnsupportedOperationException();
                throw th2;
            }

            public boolean isDone() {
                return true;
            }

            public void next() {
            }

            public int currentSegment(float[] fArr) {
                Throwable th;
                float[] fArr2 = fArr;
                Throwable th2 = th;
                new UnsupportedOperationException();
                throw th2;
            }

            public int currentSegment(double[] dArr) {
                Throwable th;
                double[] dArr2 = dArr;
                Throwable th2 = th;
                new UnsupportedOperationException();
                throw th2;
            }
        };
        this.currentPathIterator = pathIterator;
        this.shapeIterator = shapes.iterator();
        this.affineTransform = affineTransform2;
        next();
    }

    public int getWindingRule() {
        return 0;
    }

    public boolean isDone() {
        return this.done;
    }

    public void next() {
        this.currentPathIterator.next();
        if (this.currentPathIterator.isDone() && !this.shapeIterator.hasNext()) {
            this.done = true;
        } else if (this.currentPathIterator.isDone()) {
            this.currentPathIterator = ((Shape) this.shapeIterator.next()).getPathIterator(this.affineTransform);
        }
    }

    public int currentSegment(float[] coords) {
        return this.currentPathIterator.currentSegment(coords);
    }

    public int currentSegment(double[] coords) {
        return this.currentPathIterator.currentSegment(coords);
    }
}
