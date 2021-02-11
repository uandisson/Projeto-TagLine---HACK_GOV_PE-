package org.locationtech.jts.operation.union;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.util.GeometryExtracter;
import org.locationtech.jts.operation.overlay.snap.SnapIfNeededOverlayOp;

public class UnaryUnionOp {
    private GeometryFactory geomFact = null;
    private List lines;
    private List points;
    private List polygons;

    public static Geometry union(Collection geoms) {
        UnaryUnionOp op;
        new UnaryUnionOp(geoms);
        return op.union();
    }

    public static Geometry union(Collection geoms, GeometryFactory geomFact2) {
        UnaryUnionOp op;
        new UnaryUnionOp(geoms, geomFact2);
        return op.union();
    }

    public static Geometry union(Geometry geom) {
        UnaryUnionOp op;
        new UnaryUnionOp(geom);
        return op.union();
    }

    public UnaryUnionOp(Collection geoms, GeometryFactory geomFact2) {
        List list;
        List list2;
        List list3;
        new ArrayList();
        this.polygons = list;
        new ArrayList();
        this.lines = list2;
        new ArrayList();
        this.points = list3;
        this.geomFact = geomFact2;
        extract(geoms);
    }

    public UnaryUnionOp(Collection geoms) {
        List list;
        List list2;
        List list3;
        new ArrayList();
        this.polygons = list;
        new ArrayList();
        this.lines = list2;
        new ArrayList();
        this.points = list3;
        extract(geoms);
    }

    public UnaryUnionOp(Geometry geom) {
        List list;
        List list2;
        List list3;
        new ArrayList();
        this.polygons = list;
        new ArrayList();
        this.lines = list2;
        new ArrayList();
        this.points = list3;
        extract(geom);
    }

    private void extract(Collection geoms) {
        Iterator i = geoms.iterator();
        while (i.hasNext()) {
            extract((Geometry) i.next());
        }
    }

    private void extract(Geometry geometry) {
        Geometry geom = geometry;
        if (this.geomFact == null) {
            this.geomFact = geom.getFactory();
        }
        List extract = GeometryExtracter.extract(geom, Polygon.class, this.polygons);
        List extract2 = GeometryExtracter.extract(geom, LineString.class, this.lines);
        List extract3 = GeometryExtracter.extract(geom, Point.class, this.points);
    }

    public Geometry union() {
        Geometry union;
        if (this.geomFact == null) {
            return null;
        }
        Geometry unionPoints = null;
        if (this.points.size() > 0) {
            unionPoints = unionNoOpt(this.geomFact.buildGeometry(this.points));
        }
        Geometry unionLines = null;
        if (this.lines.size() > 0) {
            unionLines = unionNoOpt(this.geomFact.buildGeometry(this.lines));
        }
        Geometry unionPolygons = null;
        if (this.polygons.size() > 0) {
            unionPolygons = CascadedPolygonUnion.union(this.polygons);
        }
        Geometry unionLA = unionWithNull(unionLines, unionPolygons);
        if (unionPoints == null) {
            union = unionLA;
        } else if (unionLA == null) {
            union = unionPoints;
        } else {
            union = PointGeometryUnion.union(unionPoints, unionLA);
        }
        if (union == null) {
            return this.geomFact.createGeometryCollection((Geometry[]) null);
        }
        return union;
    }

    private Geometry unionWithNull(Geometry geometry, Geometry geometry2) {
        Geometry g0 = geometry;
        Geometry g1 = geometry2;
        if (g0 == null && g1 == null) {
            return null;
        }
        if (g1 == null) {
            return g0;
        }
        if (g0 == null) {
            return g1;
        }
        return g0.union(g1);
    }

    private Geometry unionNoOpt(Geometry g0) {
        return SnapIfNeededOverlayOp.overlayOp(g0, this.geomFact.createPoint((Coordinate) null), 2);
    }
}
