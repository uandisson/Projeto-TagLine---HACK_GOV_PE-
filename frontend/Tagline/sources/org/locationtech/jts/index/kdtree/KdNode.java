package org.locationtech.jts.index.kdtree;

import org.locationtech.jts.geom.Coordinate;

public class KdNode {
    private int count;
    private Object data;
    private KdNode left;

    /* renamed from: p */
    private Coordinate f442p = null;
    private KdNode right;

    public KdNode(double _x, double _y, Object data2) {
        Coordinate coordinate;
        new Coordinate(_x, _y);
        this.f442p = coordinate;
        this.left = null;
        this.right = null;
        this.count = 1;
        this.data = data2;
    }

    public KdNode(Coordinate p, Object data2) {
        Coordinate coordinate;
        new Coordinate(p);
        this.f442p = coordinate;
        this.left = null;
        this.right = null;
        this.count = 1;
        this.data = data2;
    }

    public double getX() {
        return this.f442p.f412x;
    }

    public double getY() {
        return this.f442p.f413y;
    }

    public Coordinate getCoordinate() {
        return this.f442p;
    }

    public Object getData() {
        return this.data;
    }

    public KdNode getLeft() {
        return this.left;
    }

    public KdNode getRight() {
        return this.right;
    }

    /* access modifiers changed from: package-private */
    public void increment() {
        this.count++;
    }

    public int getCount() {
        return this.count;
    }

    public boolean isRepeated() {
        return this.count > 1;
    }

    /* access modifiers changed from: package-private */
    public void setLeft(KdNode _left) {
        KdNode kdNode = _left;
        this.left = kdNode;
    }

    /* access modifiers changed from: package-private */
    public void setRight(KdNode _right) {
        KdNode kdNode = _right;
        this.right = kdNode;
    }
}
