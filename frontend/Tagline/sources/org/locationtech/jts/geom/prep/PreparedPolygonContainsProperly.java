package org.locationtech.jts.geom.prep;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.noding.SegmentStringUtil;

class PreparedPolygonContainsProperly extends PreparedPolygonPredicate {
    public static boolean containsProperly(PreparedPolygon prep, Geometry geom) {
        PreparedPolygonContainsProperly polyInt;
        new PreparedPolygonContainsProperly(prep);
        return polyInt.containsProperly(geom);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PreparedPolygonContainsProperly(PreparedPolygon prepPoly) {
        super(prepPoly);
    }

    public boolean containsProperly(Geometry geometry) {
        Geometry geom = geometry;
        if (!isAllTestComponentsInTargetInterior(geom)) {
            return false;
        }
        if (this.prepPoly.getIntersectionFinder().intersects(SegmentStringUtil.extractSegmentStrings(geom))) {
            return false;
        }
        if (!(geom instanceof Polygonal) || !isAnyTargetComponentInAreaTest(geom, this.prepPoly.getRepresentativePoints())) {
            return true;
        }
        return false;
    }
}
