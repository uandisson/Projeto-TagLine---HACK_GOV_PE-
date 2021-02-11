package org.locationtech.jts.algorithm;

import java.util.Iterator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryCollectionIterator;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class PointLocator {
    private BoundaryNodeRule boundaryRule = BoundaryNodeRule.OGC_SFS_BOUNDARY_RULE;
    private boolean isIn;
    private int numBoundaries;

    public PointLocator() {
    }

    public PointLocator(BoundaryNodeRule boundaryNodeRule) {
        Throwable th;
        BoundaryNodeRule boundaryRule2 = boundaryNodeRule;
        if (boundaryRule2 == null) {
            Throwable th2 = th;
            new IllegalArgumentException("Rule must be non-null");
            throw th2;
        }
        this.boundaryRule = boundaryRule2;
    }

    public boolean intersects(Coordinate p, Geometry geom) {
        return locate(p, geom) != 2;
    }

    public int locate(Coordinate coordinate, Geometry geometry) {
        Coordinate p = coordinate;
        Geometry geom = geometry;
        if (geom.isEmpty()) {
            return 2;
        }
        if (geom instanceof LineString) {
            return locate(p, (LineString) geom);
        }
        if (geom instanceof Polygon) {
            return locate(p, (Polygon) geom);
        }
        this.isIn = false;
        this.numBoundaries = 0;
        computeLocation(p, geom);
        if (this.boundaryRule.isInBoundary(this.numBoundaries)) {
            return 1;
        }
        if (this.numBoundaries > 0 || this.isIn) {
            return 0;
        }
        return 2;
    }

    private void computeLocation(Coordinate coordinate, Geometry geometry) {
        Iterator it;
        Coordinate p = coordinate;
        Geometry geom = geometry;
        if (geom instanceof Point) {
            updateLocationInfo(locate(p, (Point) geom));
        }
        if (geom instanceof LineString) {
            updateLocationInfo(locate(p, (LineString) geom));
        } else if (geom instanceof Polygon) {
            updateLocationInfo(locate(p, (Polygon) geom));
        } else if (geom instanceof MultiLineString) {
            MultiLineString ml = (MultiLineString) geom;
            for (int i = 0; i < ml.getNumGeometries(); i++) {
                updateLocationInfo(locate(p, (LineString) ml.getGeometryN(i)));
            }
        } else if (geom instanceof MultiPolygon) {
            MultiPolygon mpoly = (MultiPolygon) geom;
            for (int i2 = 0; i2 < mpoly.getNumGeometries(); i2++) {
                updateLocationInfo(locate(p, (Polygon) mpoly.getGeometryN(i2)));
            }
        } else if (geom instanceof GeometryCollection) {
            new GeometryCollectionIterator((GeometryCollection) geom);
            Iterator geomi = it;
            while (geomi.hasNext()) {
                Geometry g2 = (Geometry) geomi.next();
                if (g2 != geom) {
                    computeLocation(p, g2);
                }
            }
        }
    }

    private void updateLocationInfo(int i) {
        int loc = i;
        if (loc == 0) {
            this.isIn = true;
        }
        if (loc == 1) {
            this.numBoundaries++;
        }
    }

    private int locate(Coordinate p, Point pt) {
        if (pt.getCoordinate().equals2D(p)) {
            return 0;
        }
        return 2;
    }

    private int locate(Coordinate coordinate, LineString lineString) {
        Coordinate p = coordinate;
        LineString l = lineString;
        if (!l.getEnvelopeInternal().intersects(p)) {
            return 2;
        }
        Coordinate[] pt = l.getCoordinates();
        if (!l.isClosed() && (p.equals(pt[0]) || p.equals(pt[pt.length - 1]))) {
            return 1;
        }
        if (CGAlgorithms.isOnLine(p, pt)) {
            return 0;
        }
        return 2;
    }

    private int locateInPolygonRing(Coordinate coordinate, LinearRing linearRing) {
        Coordinate p = coordinate;
        LinearRing ring = linearRing;
        if (!ring.getEnvelopeInternal().intersects(p)) {
            return 2;
        }
        return CGAlgorithms.locatePointInRing(p, ring.getCoordinates());
    }

    private int locate(Coordinate coordinate, Polygon polygon) {
        Coordinate p = coordinate;
        Polygon poly = polygon;
        if (poly.isEmpty()) {
            return 2;
        }
        int shellLoc = locateInPolygonRing(p, (LinearRing) poly.getExteriorRing());
        if (shellLoc == 2) {
            return 2;
        }
        if (shellLoc == 1) {
            return 1;
        }
        for (int i = 0; i < poly.getNumInteriorRing(); i++) {
            int holeLoc = locateInPolygonRing(p, (LinearRing) poly.getInteriorRingN(i));
            if (holeLoc == 0) {
                return 2;
            }
            if (holeLoc == 1) {
                return 1;
            }
        }
        return 0;
    }
}
