package org.locationtech.jts.awt;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import org.locationtech.jts.awt.PointShapeFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class ShapeWriter {
    public static final PointShapeFactory DEFAULT_POINT_FACTORY;
    public static final PointTransformation DEFAULT_POINT_TRANSFORMATION;
    private double decimationDistance;
    private boolean doRemoveDuplicatePoints;
    private PointShapeFactory pointFactory;
    private PointTransformation pointTransformer;
    private Point2D transPoint;

    static {
        PointTransformation pointTransformation;
        PointShapeFactory pointShapeFactory;
        new IdentityPointTransformation();
        DEFAULT_POINT_TRANSFORMATION = pointTransformation;
        new PointShapeFactory.Square(3.0d);
        DEFAULT_POINT_FACTORY = pointShapeFactory;
    }

    public ShapeWriter(PointTransformation pointTransformation, PointShapeFactory pointShapeFactory) {
        Point2D point2D;
        PointTransformation pointTransformer2 = pointTransformation;
        PointShapeFactory pointFactory2 = pointShapeFactory;
        this.pointTransformer = DEFAULT_POINT_TRANSFORMATION;
        this.pointFactory = DEFAULT_POINT_FACTORY;
        new Point2D.Double();
        this.transPoint = point2D;
        this.doRemoveDuplicatePoints = false;
        this.decimationDistance = 0.0d;
        if (pointTransformer2 != null) {
            this.pointTransformer = pointTransformer2;
        }
        if (pointFactory2 != null) {
            this.pointFactory = pointFactory2;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ShapeWriter(PointTransformation pointTransformer2) {
        this(pointTransformer2, (PointShapeFactory) null);
    }

    public ShapeWriter() {
        Point2D point2D;
        this.pointTransformer = DEFAULT_POINT_TRANSFORMATION;
        this.pointFactory = DEFAULT_POINT_FACTORY;
        new Point2D.Double();
        this.transPoint = point2D;
        this.doRemoveDuplicatePoints = false;
        this.decimationDistance = 0.0d;
    }

    public void setRemoveDuplicatePoints(boolean doRemoveDuplicatePoints2) {
        boolean z = doRemoveDuplicatePoints2;
        this.doRemoveDuplicatePoints = z;
    }

    public void setDecimation(double decimationDistance2) {
        double d = decimationDistance2;
        this.decimationDistance = d;
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.awt.Shape toShape(org.locationtech.jts.geom.Geometry r8) {
        /*
            r7 = this;
            r0 = r7
            r1 = r8
            r2 = r1
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x0013
            java.awt.geom.GeneralPath r2 = new java.awt.geom.GeneralPath
            r6 = r2
            r2 = r6
            r3 = r6
            r3.<init>()
            r0 = r2
        L_0x0012:
            return r0
        L_0x0013:
            r2 = r1
            boolean r2 = r2 instanceof org.locationtech.jts.geom.Polygon
            if (r2 == 0) goto L_0x0022
            r2 = r0
            r3 = r1
            org.locationtech.jts.geom.Polygon r3 = (org.locationtech.jts.geom.Polygon) r3
            java.awt.Shape r2 = r2.toShape((org.locationtech.jts.geom.Polygon) r3)
            r0 = r2
            goto L_0x0012
        L_0x0022:
            r2 = r1
            boolean r2 = r2 instanceof org.locationtech.jts.geom.LineString
            if (r2 == 0) goto L_0x0031
            r2 = r0
            r3 = r1
            org.locationtech.jts.geom.LineString r3 = (org.locationtech.jts.geom.LineString) r3
            java.awt.geom.GeneralPath r2 = r2.toShape((org.locationtech.jts.geom.LineString) r3)
            r0 = r2
            goto L_0x0012
        L_0x0031:
            r2 = r1
            boolean r2 = r2 instanceof org.locationtech.jts.geom.MultiLineString
            if (r2 == 0) goto L_0x0040
            r2 = r0
            r3 = r1
            org.locationtech.jts.geom.MultiLineString r3 = (org.locationtech.jts.geom.MultiLineString) r3
            java.awt.geom.GeneralPath r2 = r2.toShape((org.locationtech.jts.geom.MultiLineString) r3)
            r0 = r2
            goto L_0x0012
        L_0x0040:
            r2 = r1
            boolean r2 = r2 instanceof org.locationtech.jts.geom.Point
            if (r2 == 0) goto L_0x004f
            r2 = r0
            r3 = r1
            org.locationtech.jts.geom.Point r3 = (org.locationtech.jts.geom.Point) r3
            java.awt.Shape r2 = r2.toShape((org.locationtech.jts.geom.Point) r3)
            r0 = r2
            goto L_0x0012
        L_0x004f:
            r2 = r1
            boolean r2 = r2 instanceof org.locationtech.jts.geom.GeometryCollection
            if (r2 == 0) goto L_0x005e
            r2 = r0
            r3 = r1
            org.locationtech.jts.geom.GeometryCollection r3 = (org.locationtech.jts.geom.GeometryCollection) r3
            java.awt.Shape r2 = r2.toShape((org.locationtech.jts.geom.GeometryCollection) r3)
            r0 = r2
            goto L_0x0012
        L_0x005e:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            r6 = r2
            r2 = r6
            r3 = r6
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r6 = r4
            r4 = r6
            r5 = r6
            r5.<init>()
            java.lang.String r5 = "Unrecognized Geometry class: "
            java.lang.StringBuilder r4 = r4.append(r5)
            r5 = r1
            java.lang.Class r5 = r5.getClass()
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.awt.ShapeWriter.toShape(org.locationtech.jts.geom.Geometry):java.awt.Shape");
    }

    private Shape toShape(Polygon polygon) {
        PolygonShape polygonShape;
        Polygon p = polygon;
        new PolygonShape();
        PolygonShape poly = polygonShape;
        appendRing(poly, p.getExteriorRing().getCoordinates());
        for (int j = 0; j < p.getNumInteriorRing(); j++) {
            appendRing(poly, p.getInteriorRingN(j).getCoordinates());
        }
        return poly;
    }

    private void appendRing(PolygonShape polygonShape, Coordinate[] coordinateArr) {
        PolygonShape poly = polygonShape;
        Coordinate[] coords = coordinateArr;
        double prevx = Double.NaN;
        double prevy = Double.NaN;
        Coordinate prev = null;
        int n = coords.length - 1;
        for (int i = 0; i < n; i++) {
            if (this.decimationDistance > 0.0d) {
                boolean isDecimated = prev != null && Math.abs(coords[i].f412x - prev.f412x) < this.decimationDistance && Math.abs(coords[i].f413y - prev.f413y) < this.decimationDistance;
                if (i >= n || !isDecimated) {
                    prev = coords[i];
                }
            }
            Point2D transformPoint = transformPoint(coords[i], this.transPoint);
            if (this.doRemoveDuplicatePoints) {
                boolean isDup = this.transPoint.getX() == prevx && this.transPoint.getY() == prevy;
                if (i >= n || !isDup) {
                    prevx = this.transPoint.getX();
                    prevy = this.transPoint.getY();
                }
            }
            poly.addToRing(this.transPoint);
        }
        poly.endRing();
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.awt.Shape toShape(org.locationtech.jts.geom.GeometryCollection r10) {
        /*
            r9 = this;
            r0 = r9
            r1 = r10
            org.locationtech.jts.awt.GeometryCollectionShape r5 = new org.locationtech.jts.awt.GeometryCollectionShape
            r8 = r5
            r5 = r8
            r6 = r8
            r6.<init>()
            r2 = r5
            r5 = 0
            r3 = r5
        L_0x000d:
            r5 = r3
            r6 = r1
            int r6 = r6.getNumGeometries()
            if (r5 >= r6) goto L_0x0029
            r5 = r1
            r6 = r3
            org.locationtech.jts.geom.Geometry r5 = r5.getGeometryN(r6)
            r4 = r5
            r5 = r2
            r6 = r0
            r7 = r4
            java.awt.Shape r6 = r6.toShape((org.locationtech.jts.geom.Geometry) r7)
            r5.add(r6)
            int r3 = r3 + 1
            goto L_0x000d
        L_0x0029:
            r5 = r2
            r0 = r5
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.awt.ShapeWriter.toShape(org.locationtech.jts.geom.GeometryCollection):java.awt.Shape");
    }

    private GeneralPath toShape(MultiLineString multiLineString) {
        GeneralPath generalPath;
        MultiLineString mls = multiLineString;
        new GeneralPath();
        GeneralPath path = generalPath;
        for (int i = 0; i < mls.getNumGeometries(); i++) {
            path.append(toShape((LineString) mls.getGeometryN(i)), false);
        }
        return path;
    }

    private GeneralPath toShape(LineString lineString) {
        GeneralPath generalPath;
        LineString lineString2 = lineString;
        new GeneralPath();
        GeneralPath shape = generalPath;
        Coordinate prev = lineString2.getCoordinateN(0);
        Point2D transformPoint = transformPoint(prev, this.transPoint);
        shape.moveTo((float) this.transPoint.getX(), (float) this.transPoint.getY());
        double prevx = this.transPoint.getX();
        double prevy = this.transPoint.getY();
        int n = lineString2.getNumPoints() - 1;
        for (int i = 1; i <= n; i++) {
            Coordinate currentCoord = lineString2.getCoordinateN(i);
            if (this.decimationDistance > 0.0d) {
                boolean isDecimated = prev != null && Math.abs(currentCoord.f412x - prev.f412x) < this.decimationDistance && Math.abs(currentCoord.f413y - prev.f413y) < this.decimationDistance;
                if (i >= n || !isDecimated) {
                    prev = currentCoord;
                }
            }
            Point2D transformPoint2 = transformPoint(currentCoord, this.transPoint);
            if (this.doRemoveDuplicatePoints) {
                boolean isDup = this.transPoint.getX() == prevx && this.transPoint.getY() == prevy;
                if (i >= n || !isDup) {
                    prevx = this.transPoint.getX();
                    prevy = this.transPoint.getY();
                }
            }
            shape.lineTo((float) this.transPoint.getX(), (float) this.transPoint.getY());
        }
        return shape;
    }

    private Shape toShape(Point point) {
        return this.pointFactory.createPoint(transformPoint(point.getCoordinate()));
    }

    private Point2D transformPoint(Coordinate model) {
        Point2D point2D;
        new Point2D.Double();
        return transformPoint(model, point2D);
    }

    private Point2D transformPoint(Coordinate model, Point2D point2D) {
        Point2D view = point2D;
        this.pointTransformer.transform(model, view);
        return view;
    }
}
