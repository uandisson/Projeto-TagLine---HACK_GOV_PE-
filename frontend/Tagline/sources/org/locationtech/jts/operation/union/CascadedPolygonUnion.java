package org.locationtech.jts.operation.union;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.geom.util.GeometryCombiner;
import org.locationtech.jts.geom.util.PolygonExtracter;
import org.locationtech.jts.index.strtree.STRtree;

public class CascadedPolygonUnion {
    private static final int STRTREE_NODE_CAPACITY = 4;
    private GeometryFactory geomFactory = null;
    private Collection inputPolys;

    public static Geometry union(Collection polys) {
        CascadedPolygonUnion op;
        new CascadedPolygonUnion(polys);
        return op.union();
    }

    public CascadedPolygonUnion(Collection polys) {
        Collection collection;
        this.inputPolys = polys;
        if (this.inputPolys == null) {
            new ArrayList();
            this.inputPolys = collection;
        }
    }

    public Geometry union() {
        STRtree sTRtree;
        Throwable th;
        if (this.inputPolys == null) {
            Throwable th2 = th;
            new IllegalStateException("union() method cannot be called twice");
            throw th2;
        } else if (this.inputPolys.isEmpty()) {
            return null;
        } else {
            this.geomFactory = ((Geometry) this.inputPolys.iterator().next()).getFactory();
            new STRtree(4);
            STRtree index = sTRtree;
            for (Geometry item : this.inputPolys) {
                index.insert(item.getEnvelopeInternal(), item);
            }
            this.inputPolys = null;
            return unionTree(index.itemsTree());
        }
    }

    private Geometry unionTree(List geomTree) {
        return binaryUnion(reduceToGeometries(geomTree));
    }

    private Geometry repeatedUnion(List geoms) {
        Geometry union = null;
        Iterator i = geoms.iterator();
        while (i.hasNext()) {
            Geometry g = (Geometry) i.next();
            if (union == null) {
                union = (Geometry) g.clone();
            } else {
                union = union.union(g);
            }
        }
        return union;
    }

    private Geometry bufferUnion(List list) {
        List geoms = list;
        return ((Geometry) geoms.get(0)).getFactory().buildGeometry(geoms).buffer(0.0d);
    }

    private Geometry bufferUnion(Geometry geometry, Geometry g1) {
        Geometry g0 = geometry;
        Geometry[] geometryArr = new Geometry[2];
        geometryArr[0] = g0;
        Geometry[] geometryArr2 = geometryArr;
        geometryArr2[1] = g1;
        return g0.getFactory().createGeometryCollection(geometryArr2).buffer(0.0d);
    }

    private Geometry binaryUnion(List list) {
        List geoms = list;
        return binaryUnion(geoms, 0, geoms.size());
    }

    private Geometry binaryUnion(List list, int i, int i2) {
        List geoms = list;
        int start = i;
        int end = i2;
        if (end - start <= 1) {
            return unionSafe(getGeometry(geoms, start), (Geometry) null);
        } else if (end - start == 2) {
            return unionSafe(getGeometry(geoms, start), getGeometry(geoms, start + 1));
        } else {
            int mid = (end + start) / 2;
            return unionSafe(binaryUnion(geoms, start, mid), binaryUnion(geoms, mid, end));
        }
    }

    private static Geometry getGeometry(List list, int i) {
        List list2 = list;
        int index = i;
        if (index >= list2.size()) {
            return null;
        }
        return (Geometry) list2.get(index);
    }

    private List reduceToGeometries(List geomTree) {
        List list;
        new ArrayList();
        List geoms = list;
        for (Object o : geomTree) {
            Geometry geom = null;
            if (o instanceof List) {
                geom = unionTree((List) o);
            } else if (o instanceof Geometry) {
                geom = (Geometry) o;
            }
            boolean add = geoms.add(geom);
        }
        return geoms;
    }

    private Geometry unionSafe(Geometry geometry, Geometry geometry2) {
        Geometry g0 = geometry;
        Geometry g1 = geometry2;
        if (g0 == null && g1 == null) {
            return null;
        }
        if (g0 == null) {
            return (Geometry) g1.clone();
        }
        if (g1 == null) {
            return (Geometry) g0.clone();
        }
        return unionOptimized(g0, g1);
    }

    private Geometry unionOptimized(Geometry geometry, Geometry geometry2) {
        Geometry g0 = geometry;
        Geometry g1 = geometry2;
        Envelope g0Env = g0.getEnvelopeInternal();
        Envelope g1Env = g1.getEnvelopeInternal();
        if (!g0Env.intersects(g1Env)) {
            return GeometryCombiner.combine(g0, g1);
        }
        if (g0.getNumGeometries() <= 1 && g1.getNumGeometries() <= 1) {
            return unionActual(g0, g1);
        }
        return unionUsingEnvelopeIntersection(g0, g1, g0Env.intersection(g1Env));
    }

    private Geometry unionUsingEnvelopeIntersection(Geometry g0, Geometry g1, Envelope envelope) {
        List list;
        Envelope common = envelope;
        new ArrayList();
        List disjointPolys = list;
        boolean add = disjointPolys.add(unionActual(extractByEnvelope(common, g0, disjointPolys), extractByEnvelope(common, g1, disjointPolys)));
        return GeometryCombiner.combine(disjointPolys);
    }

    private Geometry extractByEnvelope(Envelope envelope, Geometry geometry, List list) {
        List list2;
        Envelope env = envelope;
        Geometry geom = geometry;
        List disjointGeoms = list;
        new ArrayList();
        List intersectingGeoms = list2;
        for (int i = 0; i < geom.getNumGeometries(); i++) {
            Geometry elem = geom.getGeometryN(i);
            if (elem.getEnvelopeInternal().intersects(env)) {
                boolean add = intersectingGeoms.add(elem);
            } else {
                boolean add2 = disjointGeoms.add(elem);
            }
        }
        return this.geomFactory.buildGeometry(intersectingGeoms);
    }

    private Geometry unionActual(Geometry g0, Geometry g1) {
        return restrictToPolygons(g0.union(g1));
    }

    private static Geometry restrictToPolygons(Geometry geometry) {
        Geometry g = geometry;
        if (g instanceof Polygonal) {
            return g;
        }
        List polygons = PolygonExtracter.getPolygons(g);
        if (polygons.size() == 1) {
            return (Polygon) polygons.get(0);
        }
        return g.getFactory().createMultiPolygon(GeometryFactory.toPolygonArray(polygons));
    }
}
