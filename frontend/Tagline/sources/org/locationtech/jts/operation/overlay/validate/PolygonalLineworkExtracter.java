package org.locationtech.jts.operation.overlay.validate;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFilter;
import org.locationtech.jts.geom.Polygon;

/* compiled from: FuzzyPointLocator */
class PolygonalLineworkExtracter implements GeometryFilter {
    private List linework;

    public PolygonalLineworkExtracter() {
        List list;
        new ArrayList();
        this.linework = list;
    }

    public void filter(Geometry geometry) {
        Geometry g = geometry;
        if (g instanceof Polygon) {
            Polygon poly = (Polygon) g;
            boolean add = this.linework.add(poly.getExteriorRing());
            for (int i = 0; i < poly.getNumInteriorRing(); i++) {
                boolean add2 = this.linework.add(poly.getInteriorRingN(i));
            }
        }
    }

    public List getLinework() {
        return this.linework;
    }
}
