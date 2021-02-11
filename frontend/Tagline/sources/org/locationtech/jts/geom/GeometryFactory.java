package org.locationtech.jts.geom;

import java.io.Serializable;
import java.util.Collection;
import org.locationtech.jts.geom.impl.CoordinateArraySequenceFactory;
import org.locationtech.jts.geom.util.GeometryEditor;
import org.locationtech.jts.util.Assert;

public class GeometryFactory implements Serializable {
    private static final long serialVersionUID = -6820524753094095635L;
    private int SRID;
    /* access modifiers changed from: private */
    public CoordinateSequenceFactory coordinateSequenceFactory;
    private PrecisionModel precisionModel;

    public static Point createPointFromInternalCoord(Coordinate coordinate, Geometry geometry) {
        Coordinate coord = coordinate;
        Geometry exemplar = geometry;
        exemplar.getPrecisionModel().makePrecise(coord);
        return exemplar.getFactory().createPoint(coord);
    }

    public GeometryFactory(PrecisionModel precisionModel2, int SRID2, CoordinateSequenceFactory coordinateSequenceFactory2) {
        this.precisionModel = precisionModel2;
        this.coordinateSequenceFactory = coordinateSequenceFactory2;
        this.SRID = SRID2;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public GeometryFactory(org.locationtech.jts.geom.CoordinateSequenceFactory r8) {
        /*
            r7 = this;
            r0 = r7
            r1 = r8
            r2 = r0
            org.locationtech.jts.geom.PrecisionModel r3 = new org.locationtech.jts.geom.PrecisionModel
            r6 = r3
            r3 = r6
            r4 = r6
            r4.<init>()
            r4 = 0
            r5 = r1
            r2.<init>(r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.GeometryFactory.<init>(org.locationtech.jts.geom.CoordinateSequenceFactory):void");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public GeometryFactory(PrecisionModel precisionModel2) {
        this(precisionModel2, 0, getDefaultCoordinateSequenceFactory());
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public GeometryFactory(PrecisionModel precisionModel2, int SRID2) {
        this(precisionModel2, SRID2, getDefaultCoordinateSequenceFactory());
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public GeometryFactory() {
        /*
            r5 = this;
            r0 = r5
            r1 = r0
            org.locationtech.jts.geom.PrecisionModel r2 = new org.locationtech.jts.geom.PrecisionModel
            r4 = r2
            r2 = r4
            r3 = r4
            r3.<init>()
            r3 = 0
            r1.<init>(r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.GeometryFactory.<init>():void");
    }

    private static CoordinateSequenceFactory getDefaultCoordinateSequenceFactory() {
        return CoordinateArraySequenceFactory.instance();
    }

    public static Point[] toPointArray(Collection collection) {
        Collection points = collection;
        return (Point[]) points.toArray(new Point[points.size()]);
    }

    public static Geometry[] toGeometryArray(Collection collection) {
        Collection geometries = collection;
        if (geometries == null) {
            return null;
        }
        return (Geometry[]) geometries.toArray(new Geometry[geometries.size()]);
    }

    public static LinearRing[] toLinearRingArray(Collection collection) {
        Collection linearRings = collection;
        return (LinearRing[]) linearRings.toArray(new LinearRing[linearRings.size()]);
    }

    public static LineString[] toLineStringArray(Collection collection) {
        Collection lineStrings = collection;
        return (LineString[]) lineStrings.toArray(new LineString[lineStrings.size()]);
    }

    public static Polygon[] toPolygonArray(Collection collection) {
        Collection polygons = collection;
        return (Polygon[]) polygons.toArray(new Polygon[polygons.size()]);
    }

    public static MultiPolygon[] toMultiPolygonArray(Collection collection) {
        Collection multiPolygons = collection;
        return (MultiPolygon[]) multiPolygons.toArray(new MultiPolygon[multiPolygons.size()]);
    }

    public static MultiLineString[] toMultiLineStringArray(Collection collection) {
        Collection multiLineStrings = collection;
        return (MultiLineString[]) multiLineStrings.toArray(new MultiLineString[multiLineStrings.size()]);
    }

    public static MultiPoint[] toMultiPointArray(Collection collection) {
        Collection multiPoints = collection;
        return (MultiPoint[]) multiPoints.toArray(new MultiPoint[multiPoints.size()]);
    }

    public Geometry toGeometry(Envelope envelope) {
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        Coordinate coordinate5;
        Coordinate coordinate6;
        Coordinate coordinate7;
        Coordinate coordinate8;
        Envelope envelope2 = envelope;
        if (envelope2.isNull()) {
            return createPoint((CoordinateSequence) null);
        }
        if (envelope2.getMinX() == envelope2.getMaxX() && envelope2.getMinY() == envelope2.getMaxY()) {
            new Coordinate(envelope2.getMinX(), envelope2.getMinY());
            return createPoint(coordinate8);
        } else if (envelope2.getMinX() == envelope2.getMaxX() || envelope2.getMinY() == envelope2.getMaxY()) {
            Coordinate[] coordinateArr = new Coordinate[2];
            new Coordinate(envelope2.getMinX(), envelope2.getMinY());
            coordinateArr[0] = coordinate;
            Coordinate[] coordinateArr2 = coordinateArr;
            new Coordinate(envelope2.getMaxX(), envelope2.getMaxY());
            coordinateArr2[1] = coordinate2;
            return createLineString(coordinateArr2);
        } else {
            Coordinate[] coordinateArr3 = new Coordinate[5];
            new Coordinate(envelope2.getMinX(), envelope2.getMinY());
            coordinateArr3[0] = coordinate3;
            Coordinate[] coordinateArr4 = coordinateArr3;
            new Coordinate(envelope2.getMinX(), envelope2.getMaxY());
            coordinateArr4[1] = coordinate4;
            Coordinate[] coordinateArr5 = coordinateArr4;
            new Coordinate(envelope2.getMaxX(), envelope2.getMaxY());
            coordinateArr5[2] = coordinate5;
            Coordinate[] coordinateArr6 = coordinateArr5;
            new Coordinate(envelope2.getMaxX(), envelope2.getMinY());
            coordinateArr6[3] = coordinate6;
            Coordinate[] coordinateArr7 = coordinateArr6;
            new Coordinate(envelope2.getMinX(), envelope2.getMinY());
            coordinateArr7[4] = coordinate7;
            return createPolygon(createLinearRing(coordinateArr7), (LinearRing[]) null);
        }
    }

    public PrecisionModel getPrecisionModel() {
        return this.precisionModel;
    }

    public Point createPoint(Coordinate coordinate) {
        CoordinateSequence coordinateSequence;
        Coordinate coordinate2 = coordinate;
        if (coordinate2 != null) {
            coordinateSequence = getCoordinateSequenceFactory().create(new Coordinate[]{coordinate2});
        } else {
            coordinateSequence = null;
        }
        return createPoint(coordinateSequence);
    }

    public Point createPoint(CoordinateSequence coordinates) {
        Point point;
        new Point(coordinates, this);
        return point;
    }

    public MultiLineString createMultiLineString(LineString[] lineStrings) {
        MultiLineString multiLineString;
        new MultiLineString(lineStrings, this);
        return multiLineString;
    }

    public GeometryCollection createGeometryCollection(Geometry[] geometries) {
        GeometryCollection geometryCollection;
        new GeometryCollection(geometries, this);
        return geometryCollection;
    }

    public MultiPolygon createMultiPolygon(Polygon[] polygons) {
        MultiPolygon multiPolygon;
        new MultiPolygon(polygons, this);
        return multiPolygon;
    }

    public LinearRing createLinearRing(Coordinate[] coordinateArr) {
        Coordinate[] coordinates = coordinateArr;
        return createLinearRing(coordinates != null ? getCoordinateSequenceFactory().create(coordinates) : null);
    }

    public LinearRing createLinearRing(CoordinateSequence coordinates) {
        LinearRing linearRing;
        new LinearRing(coordinates, this);
        return linearRing;
    }

    public MultiPoint createMultiPoint(Point[] point) {
        MultiPoint multiPoint;
        new MultiPoint(point, this);
        return multiPoint;
    }

    public MultiPoint createMultiPoint(Coordinate[] coordinateArr) {
        Coordinate[] coordinates = coordinateArr;
        return createMultiPoint(coordinates != null ? getCoordinateSequenceFactory().create(coordinates) : null);
    }

    public MultiPoint createMultiPoint(CoordinateSequence coordinateSequence) {
        CoordinateSequence coordinates = coordinateSequence;
        if (coordinates == null) {
            return createMultiPoint(new Point[0]);
        }
        Point[] points = new Point[coordinates.size()];
        for (int i = 0; i < coordinates.size(); i++) {
            CoordinateSequence ptSeq = getCoordinateSequenceFactory().create(1, coordinates.getDimension());
            CoordinateSequences.copy(coordinates, i, ptSeq, 0, 1);
            points[i] = createPoint(ptSeq);
        }
        return createMultiPoint(points);
    }

    public Polygon createPolygon(LinearRing shell, LinearRing[] holes) {
        Polygon polygon;
        new Polygon(shell, holes, this);
        return polygon;
    }

    public Polygon createPolygon(CoordinateSequence shell) {
        return createPolygon(createLinearRing(shell));
    }

    public Polygon createPolygon(Coordinate[] shell) {
        return createPolygon(createLinearRing(shell));
    }

    public Polygon createPolygon(LinearRing shell) {
        return createPolygon(shell, (LinearRing[]) null);
    }

    public Geometry buildGeometry(Collection collection) {
        StringBuilder sb;
        Collection<Geometry> geomList = collection;
        Class geomClass = null;
        boolean isHeterogeneous = false;
        boolean hasGeometryCollection = false;
        for (Geometry geom : geomList) {
            Class partClass = geom.getClass();
            if (geomClass == null) {
                geomClass = partClass;
            }
            if (partClass != geomClass) {
                isHeterogeneous = true;
            }
            if (geom instanceof GeometryCollection) {
                hasGeometryCollection = true;
            }
        }
        if (geomClass == null) {
            return createGeometryCollection((Geometry[]) null);
        }
        if (isHeterogeneous || hasGeometryCollection) {
            return createGeometryCollection(toGeometryArray(geomList));
        }
        Geometry geom0 = (Geometry) geomList.iterator().next();
        if (geomList.size() > 1) {
            if (geom0 instanceof Polygon) {
                return createMultiPolygon(toPolygonArray(geomList));
            }
            if (geom0 instanceof LineString) {
                return createMultiLineString(toLineStringArray(geomList));
            }
            if (geom0 instanceof Point) {
                return createMultiPoint(toPointArray(geomList));
            }
            new StringBuilder();
            Assert.shouldNeverReachHere(sb.append("Unhandled class: ").append(geom0.getClass().getName()).toString());
        }
        return geom0;
    }

    public LineString createLineString(Coordinate[] coordinateArr) {
        Coordinate[] coordinates = coordinateArr;
        return createLineString(coordinates != null ? getCoordinateSequenceFactory().create(coordinates) : null);
    }

    public LineString createLineString(CoordinateSequence coordinates) {
        LineString lineString;
        new LineString(coordinates, this);
        return lineString;
    }

    public Geometry createGeometry(Geometry g) {
        GeometryEditor editor;
        GeometryEditor.GeometryEditorOperation geometryEditorOperation;
        new GeometryEditor(this);
        new GeometryEditor.CoordinateSequenceOperation(this) {
            final /* synthetic */ GeometryFactory this$0;

            {
                this.this$0 = this$0;
            }

            public CoordinateSequence edit(CoordinateSequence coordSeq, Geometry geometry) {
                Geometry geometry2 = geometry;
                return this.this$0.coordinateSequenceFactory.create(coordSeq);
            }
        };
        return editor.edit(g, geometryEditorOperation);
    }

    public int getSRID() {
        return this.SRID;
    }

    public CoordinateSequenceFactory getCoordinateSequenceFactory() {
        return this.coordinateSequenceFactory;
    }
}
