package org.locationtech.jts.geom.prep;

import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.noding.SegmentIntersectionDetector;
import org.locationtech.jts.noding.SegmentStringUtil;

abstract class AbstractPreparedPolygonContains extends PreparedPolygonPredicate {
    private boolean hasNonProperIntersection = false;
    private boolean hasProperIntersection = false;
    private boolean hasSegmentIntersection = false;
    protected boolean requireSomePointInInterior = true;

    /* access modifiers changed from: protected */
    public abstract boolean fullTopologicalPredicate(Geometry geometry);

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AbstractPreparedPolygonContains(PreparedPolygon prepPoly) {
        super(prepPoly);
    }

    /* access modifiers changed from: protected */
    public boolean eval(Geometry geometry) {
        Geometry geom = geometry;
        if (!isAllTestComponentsInTarget(geom)) {
            return false;
        }
        if (this.requireSomePointInInterior && geom.getDimension() == 0) {
            return isAnyTestComponentInTargetInterior(geom);
        }
        boolean properIntersectionImpliesNotContained = isProperIntersectionImpliesNotContainedSituation(geom);
        findAndClassifyIntersections(geom);
        if (properIntersectionImpliesNotContained && this.hasProperIntersection) {
            return false;
        }
        if (this.hasSegmentIntersection && !this.hasNonProperIntersection) {
            return false;
        }
        if (this.hasSegmentIntersection) {
            return fullTopologicalPredicate(geom);
        }
        if (!(geom instanceof Polygonal) || !isAnyTargetComponentInAreaTest(geom, this.prepPoly.getRepresentativePoints())) {
            return true;
        }
        return false;
    }

    private boolean isProperIntersectionImpliesNotContainedSituation(Geometry testGeom) {
        if (testGeom instanceof Polygonal) {
            return true;
        }
        if (isSingleShell(this.prepPoly.getGeometry())) {
            return true;
        }
        return false;
    }

    private boolean isSingleShell(Geometry geometry) {
        Geometry geom = geometry;
        if (geom.getNumGeometries() != 1) {
            return false;
        }
        if (((Polygon) geom.getGeometryN(0)).getNumInteriorRing() == 0) {
            return true;
        }
        return false;
    }

    private void findAndClassifyIntersections(Geometry geom) {
        SegmentIntersectionDetector segmentIntersectionDetector;
        List lineSegStr = SegmentStringUtil.extractSegmentStrings(geom);
        new SegmentIntersectionDetector();
        SegmentIntersectionDetector intDetector = segmentIntersectionDetector;
        intDetector.setFindAllIntersectionTypes(true);
        boolean intersects = this.prepPoly.getIntersectionFinder().intersects(lineSegStr, intDetector);
        this.hasSegmentIntersection = intDetector.hasIntersection();
        this.hasProperIntersection = intDetector.hasProperIntersection();
        this.hasNonProperIntersection = intDetector.hasNonProperIntersection();
    }
}
