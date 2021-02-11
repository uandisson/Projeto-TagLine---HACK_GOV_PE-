package org.locationtech.jts.geom.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFilter;
import org.locationtech.jts.geom.Point;

public class PointExtracter implements GeometryFilter {
    private List pts;

    public static List getPoints(Geometry geometry, List list) {
        GeometryFilter geometryFilter;
        Geometry geom = geometry;
        List list2 = list;
        if (geom instanceof Point) {
            boolean add = list2.add(geom);
        } else if (geom instanceof GeometryCollection) {
            new PointExtracter(list2);
            geom.apply(geometryFilter);
        }
        return list2;
    }

    public static List getPoints(Geometry geometry) {
        List list;
        Geometry geom = geometry;
        if (geom instanceof Point) {
            return Collections.singletonList(geom);
        }
        new ArrayList();
        return getPoints(geom, list);
    }

    public PointExtracter(List pts2) {
        this.pts = pts2;
    }

    public void filter(Geometry geometry) {
        Geometry geom = geometry;
        if (geom instanceof Point) {
            boolean add = this.pts.add(geom);
        }
    }
}
