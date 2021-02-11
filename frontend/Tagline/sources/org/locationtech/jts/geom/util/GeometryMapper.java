package org.locationtech.jts.geom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Geometry;

public class GeometryMapper {

    public interface MapOp {
        Geometry map(Geometry geometry);
    }

    public GeometryMapper() {
    }

    public static Geometry map(Geometry geometry, MapOp mapOp) {
        List list;
        Geometry geom = geometry;
        MapOp op = mapOp;
        new ArrayList();
        List mapped = list;
        for (int i = 0; i < geom.getNumGeometries(); i++) {
            Geometry g = op.map(geom.getGeometryN(i));
            if (g != null) {
                boolean add = mapped.add(g);
            }
        }
        return geom.getFactory().buildGeometry(mapped);
    }

    public static Collection map(Collection geoms, MapOp mapOp) {
        Collection collection;
        MapOp op = mapOp;
        new ArrayList();
        Collection mapped = collection;
        Iterator i = geoms.iterator();
        while (i.hasNext()) {
            Geometry gr = op.map((Geometry) i.next());
            if (gr != null) {
                boolean add = mapped.add(gr);
            }
        }
        return mapped;
    }
}
