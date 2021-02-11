package org.locationtech.jts.geom.util;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFilter;

public class GeometryExtracter implements GeometryFilter {
    private Class clz;
    private List comps;

    protected static boolean isOfClass(Object o, Class clz2) {
        return clz2.isAssignableFrom(o.getClass());
    }

    public static List extract(Geometry geometry, Class cls, List list) {
        GeometryFilter geometryFilter;
        Geometry geom = geometry;
        Class clz2 = cls;
        List list2 = list;
        if (isOfClass(geom, clz2)) {
            boolean add = list2.add(geom);
        } else if (geom instanceof GeometryCollection) {
            new GeometryExtracter(clz2, list2);
            geom.apply(geometryFilter);
        }
        return list2;
    }

    public static List extract(Geometry geom, Class clz2) {
        List list;
        new ArrayList();
        return extract(geom, clz2, list);
    }

    public GeometryExtracter(Class clz2, List comps2) {
        this.clz = clz2;
        this.comps = comps2;
    }

    public void filter(Geometry geometry) {
        Geometry geom = geometry;
        if (this.clz == null || isOfClass(geom, this.clz)) {
            boolean add = this.comps.add(geom);
        }
    }
}
