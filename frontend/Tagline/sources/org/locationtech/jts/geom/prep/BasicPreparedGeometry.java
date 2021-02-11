package org.locationtech.jts.geom.prep;

import java.util.List;
import org.locationtech.jts.algorithm.PointLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.util.ComponentCoordinateExtracter;

class BasicPreparedGeometry implements PreparedGeometry {
    private final Geometry baseGeom;
    private final List representativePts;

    public BasicPreparedGeometry(Geometry geometry) {
        Geometry geom = geometry;
        this.baseGeom = geom;
        this.representativePts = ComponentCoordinateExtracter.getCoordinates(geom);
    }

    public Geometry getGeometry() {
        return this.baseGeom;
    }

    public List getRepresentativePoints() {
        return this.representativePts;
    }

    public boolean isAnyTargetComponentInTest(Geometry geometry) {
        PointLocator pointLocator;
        Geometry testGeom = geometry;
        new PointLocator();
        PointLocator locator = pointLocator;
        for (Coordinate p : this.representativePts) {
            if (locator.intersects(p, testGeom)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean envelopesIntersect(Geometry g) {
        if (!this.baseGeom.getEnvelopeInternal().intersects(g.getEnvelopeInternal())) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean envelopeCovers(Geometry g) {
        if (!this.baseGeom.getEnvelopeInternal().covers(g.getEnvelopeInternal())) {
            return false;
        }
        return true;
    }

    public boolean contains(Geometry g) {
        return this.baseGeom.contains(g);
    }

    public boolean containsProperly(Geometry geometry) {
        Geometry g = geometry;
        if (!this.baseGeom.getEnvelopeInternal().contains(g.getEnvelopeInternal())) {
            return false;
        }
        return this.baseGeom.relate(g, "T**FF*FF*");
    }

    public boolean coveredBy(Geometry g) {
        return this.baseGeom.coveredBy(g);
    }

    public boolean covers(Geometry g) {
        return this.baseGeom.covers(g);
    }

    public boolean crosses(Geometry g) {
        return this.baseGeom.crosses(g);
    }

    public boolean disjoint(Geometry g) {
        return !intersects(g);
    }

    public boolean intersects(Geometry g) {
        return this.baseGeom.intersects(g);
    }

    public boolean overlaps(Geometry g) {
        return this.baseGeom.overlaps(g);
    }

    public boolean touches(Geometry g) {
        return this.baseGeom.touches(g);
    }

    public boolean within(Geometry g) {
        return this.baseGeom.within(g);
    }

    public String toString() {
        return this.baseGeom.toString();
    }
}
