package org.locationtech.jts.geom.prep;

import java.util.List;
import org.locationtech.jts.algorithm.PointLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.util.ComponentCoordinateExtracter;
import org.locationtech.jts.noding.SegmentStringUtil;

class PreparedLineStringIntersects {
    protected PreparedLineString prepLine;

    public static boolean intersects(PreparedLineString prep, Geometry geom) {
        PreparedLineStringIntersects op;
        new PreparedLineStringIntersects(prep);
        return op.intersects(geom);
    }

    public PreparedLineStringIntersects(PreparedLineString prepLine2) {
        this.prepLine = prepLine2;
    }

    public boolean intersects(Geometry geometry) {
        Geometry geom = geometry;
        List lineSegStr = SegmentStringUtil.extractSegmentStrings(geom);
        if (lineSegStr.size() > 0 && this.prepLine.getIntersectionFinder().intersects(lineSegStr)) {
            return true;
        }
        if (geom.getDimension() == 1) {
            return false;
        }
        if (geom.getDimension() == 2 && this.prepLine.isAnyTargetComponentInTest(geom)) {
            return true;
        }
        if (geom.getDimension() == 0) {
            return isAnyTestPointInTarget(geom);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isAnyTestPointInTarget(Geometry testGeom) {
        PointLocator pointLocator;
        new PointLocator();
        PointLocator locator = pointLocator;
        for (Coordinate p : ComponentCoordinateExtracter.getCoordinates(testGeom)) {
            if (locator.intersects(p, this.prepLine.getGeometry())) {
                return true;
            }
        }
        return false;
    }
}
