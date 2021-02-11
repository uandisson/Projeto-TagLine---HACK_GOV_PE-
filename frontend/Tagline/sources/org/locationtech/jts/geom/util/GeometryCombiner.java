package org.locationtech.jts.geom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

public class GeometryCombiner {
    private GeometryFactory geomFactory;
    private Collection inputGeoms;
    private boolean skipEmpty = false;

    public static Geometry combine(Collection geoms) {
        GeometryCombiner combiner;
        new GeometryCombiner(geoms);
        return combiner.combine();
    }

    public static Geometry combine(Geometry g0, Geometry g1) {
        GeometryCombiner combiner;
        new GeometryCombiner(createList(g0, g1));
        return combiner.combine();
    }

    public static Geometry combine(Geometry g0, Geometry g1, Geometry g2) {
        GeometryCombiner combiner;
        new GeometryCombiner(createList(g0, g1, g2));
        return combiner.combine();
    }

    private static List createList(Object obj0, Object obj1) {
        List list;
        new ArrayList();
        List list2 = list;
        boolean add = list2.add(obj0);
        boolean add2 = list2.add(obj1);
        return list2;
    }

    private static List createList(Object obj0, Object obj1, Object obj2) {
        List list;
        new ArrayList();
        List list2 = list;
        boolean add = list2.add(obj0);
        boolean add2 = list2.add(obj1);
        boolean add3 = list2.add(obj2);
        return list2;
    }

    public GeometryCombiner(Collection collection) {
        Collection geoms = collection;
        this.geomFactory = extractFactory(geoms);
        this.inputGeoms = geoms;
    }

    public static GeometryFactory extractFactory(Collection collection) {
        Collection geoms = collection;
        if (geoms.isEmpty()) {
            return null;
        }
        return ((Geometry) geoms.iterator().next()).getFactory();
    }

    public Geometry combine() {
        List list;
        new ArrayList();
        List elems = list;
        for (Geometry g : this.inputGeoms) {
            extractElements(g, elems);
        }
        if (elems.size() != 0) {
            return this.geomFactory.buildGeometry(elems);
        }
        if (this.geomFactory != null) {
            return this.geomFactory.createGeometryCollection((Geometry[]) null);
        }
        return null;
    }

    private void extractElements(Geometry geometry, List list) {
        Geometry geom = geometry;
        List elems = list;
        if (geom != null) {
            for (int i = 0; i < geom.getNumGeometries(); i++) {
                Geometry elemGeom = geom.getGeometryN(i);
                if (!this.skipEmpty || !elemGeom.isEmpty()) {
                    boolean add = elems.add(elemGeom);
                }
            }
        }
    }
}
