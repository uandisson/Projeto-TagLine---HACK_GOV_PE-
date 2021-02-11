package org.locationtech.jts.geom.util;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class GeometryTransformer {
    protected GeometryFactory factory = null;
    private Geometry inputGeom;
    private boolean preserveCollections = false;
    private boolean preserveGeometryCollectionType = true;
    private boolean preserveType = false;
    private boolean pruneEmptyGeometry = true;

    public GeometryTransformer() {
    }

    public Geometry getInputGeometry() {
        return this.inputGeom;
    }

    public final Geometry transform(Geometry geometry) {
        Throwable th;
        StringBuilder sb;
        Geometry inputGeom2 = geometry;
        this.inputGeom = inputGeom2;
        this.factory = inputGeom2.getFactory();
        if (inputGeom2 instanceof Point) {
            return transformPoint((Point) inputGeom2, (Geometry) null);
        }
        if (inputGeom2 instanceof MultiPoint) {
            return transformMultiPoint((MultiPoint) inputGeom2, (Geometry) null);
        }
        if (inputGeom2 instanceof LinearRing) {
            return transformLinearRing((LinearRing) inputGeom2, (Geometry) null);
        }
        if (inputGeom2 instanceof LineString) {
            return transformLineString((LineString) inputGeom2, (Geometry) null);
        }
        if (inputGeom2 instanceof MultiLineString) {
            return transformMultiLineString((MultiLineString) inputGeom2, (Geometry) null);
        }
        if (inputGeom2 instanceof Polygon) {
            return transformPolygon((Polygon) inputGeom2, (Geometry) null);
        }
        if (inputGeom2 instanceof MultiPolygon) {
            return transformMultiPolygon((MultiPolygon) inputGeom2, (Geometry) null);
        }
        if (inputGeom2 instanceof GeometryCollection) {
            return transformGeometryCollection((GeometryCollection) inputGeom2, (Geometry) null);
        }
        Throwable th2 = th;
        new StringBuilder();
        new IllegalArgumentException(sb.append("Unknown Geometry subtype: ").append(inputGeom2.getClass().getName()).toString());
        throw th2;
    }

    /* access modifiers changed from: protected */
    public final CoordinateSequence createCoordinateSequence(Coordinate[] coords) {
        return this.factory.getCoordinateSequenceFactory().create(coords);
    }

    /* access modifiers changed from: protected */
    public final CoordinateSequence copy(CoordinateSequence seq) {
        return (CoordinateSequence) seq.clone();
    }

    /* access modifiers changed from: protected */
    public CoordinateSequence transformCoordinates(CoordinateSequence coords, Geometry geometry) {
        Geometry geometry2 = geometry;
        return copy(coords);
    }

    /* access modifiers changed from: protected */
    public Geometry transformPoint(Point point, Geometry geometry) {
        Point geom = point;
        Geometry geometry2 = geometry;
        return this.factory.createPoint(transformCoordinates(geom.getCoordinateSequence(), geom));
    }

    /* access modifiers changed from: protected */
    public Geometry transformMultiPoint(MultiPoint multiPoint, Geometry geometry) {
        List list;
        MultiPoint geom = multiPoint;
        Geometry geometry2 = geometry;
        new ArrayList();
        List transGeomList = list;
        for (int i = 0; i < geom.getNumGeometries(); i++) {
            Geometry transformGeom = transformPoint((Point) geom.getGeometryN(i), geom);
            if (transformGeom != null && !transformGeom.isEmpty()) {
                boolean add = transGeomList.add(transformGeom);
            }
        }
        return this.factory.buildGeometry(transGeomList);
    }

    /* access modifiers changed from: protected */
    public Geometry transformLinearRing(LinearRing linearRing, Geometry geometry) {
        LinearRing geom = linearRing;
        Geometry geometry2 = geometry;
        CoordinateSequence seq = transformCoordinates(geom.getCoordinateSequence(), geom);
        if (seq == null) {
            return this.factory.createLinearRing((CoordinateSequence) null);
        }
        int seqSize = seq.size();
        if (seqSize <= 0 || seqSize >= 4 || this.preserveType) {
            return this.factory.createLinearRing(seq);
        }
        return this.factory.createLineString(seq);
    }

    /* access modifiers changed from: protected */
    public Geometry transformLineString(LineString lineString, Geometry geometry) {
        LineString geom = lineString;
        Geometry geometry2 = geometry;
        return this.factory.createLineString(transformCoordinates(geom.getCoordinateSequence(), geom));
    }

    /* access modifiers changed from: protected */
    public Geometry transformMultiLineString(MultiLineString multiLineString, Geometry geometry) {
        List list;
        MultiLineString geom = multiLineString;
        Geometry geometry2 = geometry;
        new ArrayList();
        List transGeomList = list;
        for (int i = 0; i < geom.getNumGeometries(); i++) {
            Geometry transformGeom = transformLineString((LineString) geom.getGeometryN(i), geom);
            if (transformGeom != null && !transformGeom.isEmpty()) {
                boolean add = transGeomList.add(transformGeom);
            }
        }
        return this.factory.buildGeometry(transGeomList);
    }

    /* access modifiers changed from: protected */
    public Geometry transformPolygon(Polygon polygon, Geometry geometry) {
        ArrayList arrayList;
        List list;
        Polygon geom = polygon;
        Geometry geometry2 = geometry;
        boolean isAllValidLinearRings = true;
        Geometry shell = transformLinearRing((LinearRing) geom.getExteriorRing(), geom);
        if (shell == null || !(shell instanceof LinearRing) || shell.isEmpty()) {
            isAllValidLinearRings = false;
        }
        new ArrayList();
        ArrayList holes = arrayList;
        for (int i = 0; i < geom.getNumInteriorRing(); i++) {
            Geometry hole = transformLinearRing((LinearRing) geom.getInteriorRingN(i), geom);
            if (hole != null && !hole.isEmpty()) {
                if (!(hole instanceof LinearRing)) {
                    isAllValidLinearRings = false;
                }
                boolean add = holes.add(hole);
            }
        }
        if (isAllValidLinearRings) {
            return this.factory.createPolygon((LinearRing) shell, (LinearRing[]) holes.toArray(new LinearRing[0]));
        }
        new ArrayList();
        List components = list;
        if (shell != null) {
            boolean add2 = components.add(shell);
        }
        boolean addAll = components.addAll(holes);
        return this.factory.buildGeometry(components);
    }

    /* access modifiers changed from: protected */
    public Geometry transformMultiPolygon(MultiPolygon multiPolygon, Geometry geometry) {
        List list;
        MultiPolygon geom = multiPolygon;
        Geometry geometry2 = geometry;
        new ArrayList();
        List transGeomList = list;
        for (int i = 0; i < geom.getNumGeometries(); i++) {
            Geometry transformGeom = transformPolygon((Polygon) geom.getGeometryN(i), geom);
            if (transformGeom != null && !transformGeom.isEmpty()) {
                boolean add = transGeomList.add(transformGeom);
            }
        }
        return this.factory.buildGeometry(transGeomList);
    }

    /* access modifiers changed from: protected */
    public Geometry transformGeometryCollection(GeometryCollection geometryCollection, Geometry geometry) {
        List list;
        GeometryCollection geom = geometryCollection;
        Geometry geometry2 = geometry;
        new ArrayList();
        List transGeomList = list;
        for (int i = 0; i < geom.getNumGeometries(); i++) {
            Geometry transformGeom = transform(geom.getGeometryN(i));
            if (transformGeom != null && (!this.pruneEmptyGeometry || !transformGeom.isEmpty())) {
                boolean add = transGeomList.add(transformGeom);
            }
        }
        if (this.preserveGeometryCollectionType) {
            return this.factory.createGeometryCollection(GeometryFactory.toGeometryArray(transGeomList));
        }
        return this.factory.buildGeometry(transGeomList);
    }
}
