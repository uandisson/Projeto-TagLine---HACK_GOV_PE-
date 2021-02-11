package org.locationtech.jts.geom.prep;

import org.locationtech.jts.geom.Geometry;

class PreparedPolygonCovers extends AbstractPreparedPolygonContains {
    public static boolean covers(PreparedPolygon prep, Geometry geom) {
        PreparedPolygonCovers polyInt;
        new PreparedPolygonCovers(prep);
        return polyInt.covers(geom);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PreparedPolygonCovers(PreparedPolygon prepPoly) {
        super(prepPoly);
        this.requireSomePointInInterior = false;
    }

    public boolean covers(Geometry geom) {
        return eval(geom);
    }

    /* access modifiers changed from: protected */
    public boolean fullTopologicalPredicate(Geometry geom) {
        return this.prepPoly.getGeometry().covers(geom);
    }
}
