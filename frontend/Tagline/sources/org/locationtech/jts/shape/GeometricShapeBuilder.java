package org.locationtech.jts.shape;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineSegment;

public abstract class GeometricShapeBuilder {
    protected Envelope extent;
    protected GeometryFactory geomFactory;
    protected int numPts = 0;

    public abstract Geometry getGeometry();

    public GeometricShapeBuilder(GeometryFactory geomFactory2) {
        Envelope envelope;
        new Envelope(0.0d, 1.0d, 0.0d, 1.0d);
        this.extent = envelope;
        this.geomFactory = geomFactory2;
    }

    public void setExtent(Envelope extent2) {
        Envelope envelope = extent2;
        this.extent = envelope;
    }

    public Envelope getExtent() {
        return this.extent;
    }

    public Coordinate getCentre() {
        return this.extent.centre();
    }

    public double getDiameter() {
        return Math.min(this.extent.getHeight(), this.extent.getWidth());
    }

    public double getRadius() {
        return getDiameter() / 2.0d;
    }

    public LineSegment getSquareBaseLine() {
        Coordinate p0;
        Coordinate p1;
        LineSegment lineSegment;
        double radius = getRadius();
        Coordinate centre = getCentre();
        new Coordinate(centre.f412x - radius, centre.f413y - radius);
        new Coordinate(centre.f412x + radius, centre.f413y - radius);
        new LineSegment(p0, p1);
        return lineSegment;
    }

    public Envelope getSquareExtent() {
        Envelope envelope;
        double radius = getRadius();
        Coordinate centre = getCentre();
        new Envelope(centre.f412x - radius, centre.f412x + radius, centre.f413y - radius, centre.f413y + radius);
        return envelope;
    }

    public void setNumPoints(int numPts2) {
        int i = numPts2;
        this.numPts = i;
    }

    /* access modifiers changed from: protected */
    public Coordinate createCoord(double x, double y) {
        Coordinate coordinate;
        new Coordinate(x, y);
        Coordinate pt = coordinate;
        this.geomFactory.getPrecisionModel().makePrecise(pt);
        return pt;
    }
}
