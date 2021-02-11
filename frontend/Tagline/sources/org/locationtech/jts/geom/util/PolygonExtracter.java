package org.locationtech.jts.geom.util;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFilter;
import org.locationtech.jts.geom.Polygon;

public class PolygonExtracter implements GeometryFilter {
    private List comps;

    public static List getPolygons(Geometry geometry, List list) {
        GeometryFilter geometryFilter;
        Geometry geom = geometry;
        List list2 = list;
        if (geom instanceof Polygon) {
            boolean add = list2.add(geom);
        } else if (geom instanceof GeometryCollection) {
            new PolygonExtracter(list2);
            geom.apply(geometryFilter);
        }
        return list2;
    }

    public static List getPolygons(Geometry geom) {
        List list;
        new ArrayList();
        return getPolygons(geom, list);
    }

    public PolygonExtracter(List comps2) {
        this.comps = comps2;
    }

    public void filter(Geometry geometry) {
        Geometry geom = geometry;
        if (geom instanceof Polygon) {
            boolean add = this.comps.add(geom);
        }
    }
}
