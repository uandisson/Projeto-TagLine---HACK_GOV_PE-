package org.locationtech.jts.geom.prep;

import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.algorithm.locate.PointOnGeometryLocator;
import org.locationtech.jts.algorithm.locate.SimplePointInAreaLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.util.ComponentCoordinateExtracter;

abstract class PreparedPolygonPredicate {
    protected PreparedPolygon prepPoly;
    private PointOnGeometryLocator targetPointLocator;

    public PreparedPolygonPredicate(PreparedPolygon preparedPolygon) {
        PreparedPolygon prepPoly2 = preparedPolygon;
        this.prepPoly = prepPoly2;
        this.targetPointLocator = prepPoly2.getPointLocator();
    }

    /* access modifiers changed from: protected */
    public boolean isAllTestComponentsInTarget(Geometry testGeom) {
        for (Coordinate p : ComponentCoordinateExtracter.getCoordinates(testGeom)) {
            if (this.targetPointLocator.locate(p) == 2) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isAllTestComponentsInTargetInterior(Geometry testGeom) {
        for (Coordinate p : ComponentCoordinateExtracter.getCoordinates(testGeom)) {
            if (this.targetPointLocator.locate(p) != 0) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean isAnyTestComponentInTarget(Geometry testGeom) {
        for (Coordinate p : ComponentCoordinateExtracter.getCoordinates(testGeom)) {
            if (this.targetPointLocator.locate(p) != 2) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isAnyTestComponentInTargetInterior(Geometry testGeom) {
        for (Coordinate p : ComponentCoordinateExtracter.getCoordinates(testGeom)) {
            if (this.targetPointLocator.locate(p) == 0) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isAnyTargetComponentInAreaTest(Geometry testGeom, List targetRepPts) {
        PointOnGeometryLocator pointOnGeometryLocator;
        new SimplePointInAreaLocator(testGeom);
        PointOnGeometryLocator piaLoc = pointOnGeometryLocator;
        Iterator i = targetRepPts.iterator();
        while (i.hasNext()) {
            if (piaLoc.locate((Coordinate) i.next()) != 2) {
                return true;
            }
        }
        return false;
    }
}
