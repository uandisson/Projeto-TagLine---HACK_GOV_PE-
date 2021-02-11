package org.locationtech.jts.awt;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

public class GeometryCollectionShape implements Shape {
    private ArrayList shapes;

    public GeometryCollectionShape() {
        ArrayList arrayList;
        new ArrayList();
        this.shapes = arrayList;
    }

    public void add(Shape shape) {
        boolean add = this.shapes.add(shape);
    }

    public Rectangle getBounds() {
        Throwable th;
        Throwable th2 = th;
        new UnsupportedOperationException("Method getBounds() not yet implemented.");
        throw th2;
    }

    public Rectangle2D getBounds2D() {
        Rectangle2D rectangle = null;
        Iterator i = this.shapes.iterator();
        while (i.hasNext()) {
            Shape shape = (Shape) i.next();
            if (rectangle == null) {
                rectangle = shape.getBounds2D();
            } else {
                rectangle.add(shape.getBounds2D());
            }
        }
        return rectangle;
    }

    public boolean contains(double d, double d2) {
        Throwable th;
        double d3 = d;
        double d4 = d2;
        Throwable th2 = th;
        new UnsupportedOperationException("Method contains() not yet implemented.");
        throw th2;
    }

    public boolean contains(Point2D point2D) {
        Throwable th;
        Point2D point2D2 = point2D;
        Throwable th2 = th;
        new UnsupportedOperationException("Method contains() not yet implemented.");
        throw th2;
    }

    public boolean intersects(double d, double d2, double d3, double d4) {
        Throwable th;
        double d5 = d;
        double d6 = d2;
        double d7 = d3;
        double d8 = d4;
        Throwable th2 = th;
        new UnsupportedOperationException("Method intersects() not yet implemented.");
        throw th2;
    }

    public boolean intersects(Rectangle2D rectangle2D) {
        Throwable th;
        Rectangle2D rectangle2D2 = rectangle2D;
        Throwable th2 = th;
        new UnsupportedOperationException("Method intersects() not yet implemented.");
        throw th2;
    }

    public boolean contains(double d, double d2, double d3, double d4) {
        Throwable th;
        double d5 = d;
        double d6 = d2;
        double d7 = d3;
        double d8 = d4;
        Throwable th2 = th;
        new UnsupportedOperationException("Method contains() not yet implemented.");
        throw th2;
    }

    public boolean contains(Rectangle2D rectangle2D) {
        Throwable th;
        Rectangle2D rectangle2D2 = rectangle2D;
        Throwable th2 = th;
        new UnsupportedOperationException("Method contains() not yet implemented.");
        throw th2;
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.awt.geom.PathIterator getPathIterator(java.awt.geom.AffineTransform r8) {
        /*
            r7 = this;
            r0 = r7
            r1 = r8
            org.locationtech.jts.awt.ShapeCollectionPathIterator r2 = new org.locationtech.jts.awt.ShapeCollectionPathIterator
            r6 = r2
            r2 = r6
            r3 = r6
            r4 = r0
            java.util.ArrayList r4 = r4.shapes
            r5 = r1
            r3.<init>(r4, r5)
            r0 = r2
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.awt.GeometryCollectionShape.getPathIterator(java.awt.geom.AffineTransform):java.awt.geom.PathIterator");
    }

    public PathIterator getPathIterator(AffineTransform at, double d) {
        double d2 = d;
        return getPathIterator(at);
    }
}
