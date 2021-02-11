package org.locationtech.jts.geom.prep;

import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.noding.SegmentStringUtil;

class PreparedPolygonIntersects extends PreparedPolygonPredicate {
    public static boolean intersects(PreparedPolygon prep, Geometry geom) {
        PreparedPolygonIntersects polyInt;
        new PreparedPolygonIntersects(prep);
        return polyInt.intersects(geom);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PreparedPolygonIntersects(PreparedPolygon prepPoly) {
        super(prepPoly);
    }

    public boolean intersects(Geometry geometry) {
        Geometry geom = geometry;
        if (isAnyTestComponentInTarget(geom)) {
            return true;
        }
        if (geom.getDimension() == 0) {
            return false;
        }
        List lineSegStr = SegmentStringUtil.extractSegmentStrings(geom);
        if (lineSegStr.size() > 0 && this.prepPoly.getIntersectionFinder().intersects(lineSegStr)) {
            return true;
        }
        if (geom.getDimension() != 2 || !isAnyTargetComponentInAreaTest(geom, this.prepPoly.getRepresentativePoints())) {
            return false;
        }
        return true;
    }
}
