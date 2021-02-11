package org.locationtech.jts.geom.util;

import java.util.ArrayList;
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
import org.locationtech.jts.util.Assert;

public class GeometryEditor {
    private GeometryFactory factory = null;
    private boolean isUserDataCopied = false;

    public interface GeometryEditorOperation {
        Geometry edit(Geometry geometry, GeometryFactory geometryFactory);
    }

    public GeometryEditor() {
    }

    public GeometryEditor(GeometryFactory factory2) {
        this.factory = factory2;
    }

    public void setCopyUserData(boolean isUserDataCopied2) {
        boolean z = isUserDataCopied2;
        this.isUserDataCopied = z;
    }

    public Geometry edit(Geometry geometry, GeometryEditorOperation geometryEditorOperation) {
        Geometry geometry2 = geometry;
        GeometryEditorOperation operation = geometryEditorOperation;
        if (geometry2 == null) {
            return null;
        }
        Geometry result = editInternal(geometry2, operation);
        if (this.isUserDataCopied) {
            result.setUserData(geometry2.getUserData());
        }
        return result;
    }

    private Geometry editInternal(Geometry geometry, GeometryEditorOperation geometryEditorOperation) {
        StringBuilder sb;
        Geometry geometry2 = geometry;
        GeometryEditorOperation operation = geometryEditorOperation;
        if (this.factory == null) {
            this.factory = geometry2.getFactory();
        }
        if (geometry2 instanceof GeometryCollection) {
            return editGeometryCollection((GeometryCollection) geometry2, operation);
        }
        if (geometry2 instanceof Polygon) {
            return editPolygon((Polygon) geometry2, operation);
        }
        if (geometry2 instanceof Point) {
            return operation.edit(geometry2, this.factory);
        }
        if (geometry2 instanceof LineString) {
            return operation.edit(geometry2, this.factory);
        }
        new StringBuilder();
        Assert.shouldNeverReachHere(sb.append("Unsupported Geometry class: ").append(geometry2.getClass().getName()).toString());
        return null;
    }

    private Polygon editPolygon(Polygon polygon, GeometryEditorOperation geometryEditorOperation) {
        ArrayList arrayList;
        GeometryEditorOperation operation = geometryEditorOperation;
        Polygon newPolygon = (Polygon) operation.edit(polygon, this.factory);
        if (newPolygon == null) {
            newPolygon = this.factory.createPolygon((CoordinateSequence) null);
        }
        if (newPolygon.isEmpty()) {
            return newPolygon;
        }
        LinearRing shell = (LinearRing) edit(newPolygon.getExteriorRing(), operation);
        if (shell == null || shell.isEmpty()) {
            return this.factory.createPolygon((LinearRing) null, (LinearRing[]) null);
        }
        new ArrayList();
        ArrayList holes = arrayList;
        for (int i = 0; i < newPolygon.getNumInteriorRing(); i++) {
            LinearRing hole = (LinearRing) edit(newPolygon.getInteriorRingN(i), operation);
            if (hole != null && !hole.isEmpty()) {
                boolean add = holes.add(hole);
            }
        }
        return this.factory.createPolygon(shell, (LinearRing[]) holes.toArray(new LinearRing[0]));
    }

    private GeometryCollection editGeometryCollection(GeometryCollection collection, GeometryEditorOperation geometryEditorOperation) {
        ArrayList arrayList;
        GeometryEditorOperation operation = geometryEditorOperation;
        GeometryCollection collectionForType = (GeometryCollection) operation.edit(collection, this.factory);
        new ArrayList();
        ArrayList geometries = arrayList;
        for (int i = 0; i < collectionForType.getNumGeometries(); i++) {
            Geometry geometry = edit(collectionForType.getGeometryN(i), operation);
            if (geometry != null && !geometry.isEmpty()) {
                boolean add = geometries.add(geometry);
            }
        }
        if (collectionForType.getClass() == MultiPoint.class) {
            return this.factory.createMultiPoint((Point[]) geometries.toArray(new Point[0]));
        }
        if (collectionForType.getClass() == MultiLineString.class) {
            return this.factory.createMultiLineString((LineString[]) geometries.toArray(new LineString[0]));
        }
        if (collectionForType.getClass() == MultiPolygon.class) {
            return this.factory.createMultiPolygon((Polygon[]) geometries.toArray(new Polygon[0]));
        }
        return this.factory.createGeometryCollection((Geometry[]) geometries.toArray(new Geometry[0]));
    }

    public static class NoOpGeometryOperation implements GeometryEditorOperation {
        public NoOpGeometryOperation() {
        }

        public Geometry edit(Geometry geometry, GeometryFactory geometryFactory) {
            GeometryFactory geometryFactory2 = geometryFactory;
            return geometry;
        }
    }

    public static abstract class CoordinateOperation implements GeometryEditorOperation {
        public abstract Coordinate[] edit(Coordinate[] coordinateArr, Geometry geometry);

        public CoordinateOperation() {
        }

        public final Geometry edit(Geometry geometry, GeometryFactory geometryFactory) {
            Geometry geometry2 = geometry;
            GeometryFactory factory = geometryFactory;
            if (geometry2 instanceof LinearRing) {
                return factory.createLinearRing(edit(geometry2.getCoordinates(), geometry2));
            }
            if (geometry2 instanceof LineString) {
                return factory.createLineString(edit(geometry2.getCoordinates(), geometry2));
            }
            if (!(geometry2 instanceof Point)) {
                return geometry2;
            }
            Coordinate[] newCoordinates = edit(geometry2.getCoordinates(), geometry2);
            return factory.createPoint(newCoordinates.length > 0 ? newCoordinates[0] : null);
        }
    }

    public static abstract class CoordinateSequenceOperation implements GeometryEditorOperation {
        public abstract CoordinateSequence edit(CoordinateSequence coordinateSequence, Geometry geometry);

        public CoordinateSequenceOperation() {
        }

        public final Geometry edit(Geometry geometry, GeometryFactory geometryFactory) {
            Geometry geometry2 = geometry;
            GeometryFactory factory = geometryFactory;
            if (geometry2 instanceof LinearRing) {
                return factory.createLinearRing(edit(((LinearRing) geometry2).getCoordinateSequence(), geometry2));
            }
            if (geometry2 instanceof LineString) {
                return factory.createLineString(edit(((LineString) geometry2).getCoordinateSequence(), geometry2));
            }
            if (geometry2 instanceof Point) {
                return factory.createPoint(edit(((Point) geometry2).getCoordinateSequence(), geometry2));
            }
            return geometry2;
        }
    }
}
