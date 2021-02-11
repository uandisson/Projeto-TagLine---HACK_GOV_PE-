package org.locationtech.jts.geom.prep;

import org.locationtech.jts.geom.Geometry;

class PreparedPolygonContains extends AbstractPreparedPolygonContains {
    public static boolean contains(PreparedPolygon prep, Geometry geom) {
        PreparedPolygonContains polyInt;
        new PreparedPolygonContains(prep);
        return polyInt.contains(geom);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PreparedPolygonContains(PreparedPolygon prepPoly) {
        super(prepPoly);
    }

    public boolean contains(Geometry geom) {
        return eval(geom);
    }

    /* access modifiers changed from: protected */
    public boolean fullTopologicalPredicate(Geometry geom) {
        return this.prepPoly.getGeometry().contains(geom);
    }
}
