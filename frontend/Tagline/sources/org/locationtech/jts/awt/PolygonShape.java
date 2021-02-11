package org.locationtech.jts.awt;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;
import org.locationtech.jts.geom.Coordinate;

public class PolygonShape implements Shape {
    private GeneralPath polygonPath;
    private GeneralPath ringPath;

    public PolygonShape(Coordinate[] shellVertices, Collection holeVerticesCollection) {
        this.polygonPath = toPath(shellVertices);
        Iterator i = holeVerticesCollection.iterator();
        while (i.hasNext()) {
            this.polygonPath.append(toPath((Coordinate[]) i.next()), false);
        }
    }

    public PolygonShape() {
    }

    /* access modifiers changed from: package-private */
    public void addToRing(Point2D point2D) {
        GeneralPath generalPath;
        Point2D p = point2D;
        if (this.ringPath == null) {
            new GeneralPath(0);
            this.ringPath = generalPath;
            this.ringPath.moveTo((float) p.getX(), (float) p.getY());
            return;
        }
        this.ringPath.lineTo((float) p.getX(), (float) p.getY());
    }

    /* access modifiers changed from: package-private */
    public void endRing() {
        this.ringPath.closePath();
        if (this.polygonPath == null) {
            this.polygonPath = this.ringPath;
        } else {
            this.polygonPath.append(this.ringPath, false);
        }
        this.ringPath = null;
    }

    private GeneralPath toPath(Coordinate[] coordinateArr) {
        GeneralPath generalPath;
        Coordinate[] coordinates = coordinateArr;
        new GeneralPath(0, coordinates.length);
        GeneralPath path = generalPath;
        if (coordinates.length > 0) {
            path.moveTo((float) coordinates[0].f412x, (float) coordinates[0].f413y);
            for (int i = 0; i < coordinates.length; i++) {
                path.lineTo((float) coordinates[i].f412x, (float) coordinates[i].f413y);
            }
        }
        return path;
    }

    public Rectangle getBounds() {
        return this.polygonPath.getBounds();
    }

    public Rectangle2D getBounds2D() {
        return this.polygonPath.getBounds2D();
    }

    public boolean contains(double x, double y) {
        return this.polygonPath.contains(x, y);
    }

    public boolean contains(Point2D p) {
        return this.polygonPath.contains(p);
    }

    public boolean intersects(double x, double y, double w, double h) {
        return this.polygonPath.intersects(x, y, w, h);
    }

    public boolean intersects(Rectangle2D r) {
        return this.polygonPath.intersects(r);
    }

    public boolean contains(double x, double y, double w, double h) {
        return this.polygonPath.contains(x, y, w, h);
    }

    public boolean contains(Rectangle2D r) {
        return this.polygonPath.contains(r);
    }

    public PathIterator getPathIterator(AffineTransform at) {
        return this.polygonPath.getPathIterator(at);
    }

    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return getPathIterator(at, flatness);
    }
}
