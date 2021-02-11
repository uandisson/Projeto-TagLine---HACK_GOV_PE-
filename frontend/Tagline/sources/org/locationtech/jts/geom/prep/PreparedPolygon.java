package org.locationtech.jts.geom.prep;

import java.util.List;
import org.locationtech.jts.algorithm.locate.IndexedPointInAreaLocator;
import org.locationtech.jts.algorithm.locate.PointOnGeometryLocator;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.noding.FastSegmentSetIntersectionFinder;
import org.locationtech.jts.noding.SegmentStringUtil;
import org.locationtech.jts.operation.predicate.RectangleContains;
import org.locationtech.jts.operation.predicate.RectangleIntersects;

public class PreparedPolygon extends BasicPreparedGeometry {
    private final boolean isRectangle = getGeometry().isRectangle();
    private PointOnGeometryLocator pia = null;
    private FastSegmentSetIntersectionFinder segIntFinder = null;

    public /* bridge */ /* synthetic */ boolean coveredBy(Geometry geometry) {
        return super.coveredBy(geometry);
    }

    public /* bridge */ /* synthetic */ boolean crosses(Geometry geometry) {
        return super.crosses(geometry);
    }

    public /* bridge */ /* synthetic */ boolean disjoint(Geometry geometry) {
        return super.disjoint(geometry);
    }

    public /* bridge */ /* synthetic */ Geometry getGeometry() {
        return super.getGeometry();
    }

    public /* bridge */ /* synthetic */ List getRepresentativePoints() {
        return super.getRepresentativePoints();
    }

    public /* bridge */ /* synthetic */ boolean isAnyTargetComponentInTest(Geometry geometry) {
        return super.isAnyTargetComponentInTest(geometry);
    }

    public /* bridge */ /* synthetic */ boolean overlaps(Geometry geometry) {
        return super.overlaps(geometry);
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public /* bridge */ /* synthetic */ boolean touches(Geometry geometry) {
        return super.touches(geometry);
    }

    public /* bridge */ /* synthetic */ boolean within(Geometry geometry) {
        return super.within(geometry);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PreparedPolygon(Polygonal poly) {
        super((Geometry) poly);
    }

    public synchronized FastSegmentSetIntersectionFinder getIntersectionFinder() {
        FastSegmentSetIntersectionFinder fastSegmentSetIntersectionFinder;
        FastSegmentSetIntersectionFinder fastSegmentSetIntersectionFinder2;
        synchronized (this) {
            if (this.segIntFinder == null) {
                new FastSegmentSetIntersectionFinder(SegmentStringUtil.extractSegmentStrings(getGeometry()));
                this.segIntFinder = fastSegmentSetIntersectionFinder2;
            }
            fastSegmentSetIntersectionFinder = this.segIntFinder;
        }
        return fastSegmentSetIntersectionFinder;
    }

    public synchronized PointOnGeometryLocator getPointLocator() {
        PointOnGeometryLocator pointOnGeometryLocator;
        PointOnGeometryLocator pointOnGeometryLocator2;
        synchronized (this) {
            if (this.pia == null) {
                new IndexedPointInAreaLocator(getGeometry());
                this.pia = pointOnGeometryLocator2;
            }
            pointOnGeometryLocator = this.pia;
        }
        return pointOnGeometryLocator;
    }

    public boolean intersects(Geometry geometry) {
        Geometry g = geometry;
        if (!envelopesIntersect(g)) {
            return false;
        }
        if (this.isRectangle) {
            return RectangleIntersects.intersects((Polygon) getGeometry(), g);
        }
        return PreparedPolygonIntersects.intersects(this, g);
    }

    public boolean contains(Geometry geometry) {
        Geometry g = geometry;
        if (!envelopeCovers(g)) {
            return false;
        }
        if (this.isRectangle) {
            return RectangleContains.contains((Polygon) getGeometry(), g);
        }
        return PreparedPolygonContains.contains(this, g);
    }

    public boolean containsProperly(Geometry geometry) {
        Geometry g = geometry;
        if (!envelopeCovers(g)) {
            return false;
        }
        return PreparedPolygonContainsProperly.containsProperly(this, g);
    }

    public boolean covers(Geometry geometry) {
        Geometry g = geometry;
        if (!envelopeCovers(g)) {
            return false;
        }
        if (this.isRectangle) {
            return true;
        }
        return PreparedPolygonCovers.covers(this, g);
    }
}
