package org.locationtech.jts.geom.prep;

import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Puntal;

public class PreparedPoint extends BasicPreparedGeometry {
    public /* bridge */ /* synthetic */ boolean contains(Geometry geometry) {
        return super.contains(geometry);
    }

    public /* bridge */ /* synthetic */ boolean containsProperly(Geometry geometry) {
        return super.containsProperly(geometry);
    }

    public /* bridge */ /* synthetic */ boolean coveredBy(Geometry geometry) {
        return super.coveredBy(geometry);
    }

    public /* bridge */ /* synthetic */ boolean covers(Geometry geometry) {
        return super.covers(geometry);
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
    public PreparedPoint(Puntal point) {
        super((Geometry) point);
    }

    public boolean intersects(Geometry geometry) {
        Geometry g = geometry;
        if (!envelopesIntersect(g)) {
            return false;
        }
        return isAnyTargetComponentInTest(g);
    }
}
