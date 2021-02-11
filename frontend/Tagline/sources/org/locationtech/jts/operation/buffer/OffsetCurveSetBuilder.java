package org.locationtech.jts.operation.buffer;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.Triangle;
import org.locationtech.jts.geomgraph.Label;
import org.locationtech.jts.geomgraph.Position;
import org.locationtech.jts.noding.NodedSegmentString;

public class OffsetCurveSetBuilder {
    private OffsetCurveBuilder curveBuilder;
    private List curveList;
    private double distance;
    private Geometry inputGeom;

    public OffsetCurveSetBuilder(Geometry inputGeom2, double distance2, OffsetCurveBuilder curveBuilder2) {
        List list;
        new ArrayList();
        this.curveList = list;
        this.inputGeom = inputGeom2;
        this.distance = distance2;
        this.curveBuilder = curveBuilder2;
    }

    public List getCurves() {
        add(this.inputGeom);
        return this.curveList;
    }

    private void addCurve(Coordinate[] coordinateArr, int i, int i2) {
        Object obj;
        Object obj2;
        Coordinate[] coord = coordinateArr;
        int leftLoc = i;
        int rightLoc = i2;
        if (coord != null && coord.length >= 2) {
            new Label(0, 1, leftLoc, rightLoc);
            new NodedSegmentString(coord, obj2);
            boolean add = this.curveList.add(obj);
        }
    }

    private void add(Geometry geometry) {
        Throwable th;
        Geometry g = geometry;
        if (!g.isEmpty()) {
            if (g instanceof Polygon) {
                addPolygon((Polygon) g);
            } else if (g instanceof LineString) {
                addLineString((LineString) g);
            } else if (g instanceof Point) {
                addPoint((Point) g);
            } else if (g instanceof MultiPoint) {
                addCollection((MultiPoint) g);
            } else if (g instanceof MultiLineString) {
                addCollection((MultiLineString) g);
            } else if (g instanceof MultiPolygon) {
                addCollection((MultiPolygon) g);
            } else if (g instanceof GeometryCollection) {
                addCollection((GeometryCollection) g);
            } else {
                Throwable th2 = th;
                new UnsupportedOperationException(g.getClass().getName());
                throw th2;
            }
        }
    }

    private void addCollection(GeometryCollection geometryCollection) {
        GeometryCollection gc = geometryCollection;
        for (int i = 0; i < gc.getNumGeometries(); i++) {
            add(gc.getGeometryN(i));
        }
    }

    private void addPoint(Point point) {
        Point p = point;
        if (this.distance > 0.0d) {
            addCurve(this.curveBuilder.getLineCurve(p.getCoordinates(), this.distance), 2, 0);
        }
    }

    private void addLineString(LineString lineString) {
        LineString line = lineString;
        if (this.distance > 0.0d || this.curveBuilder.getBufferParameters().isSingleSided()) {
            addCurve(this.curveBuilder.getLineCurve(CoordinateArrays.removeRepeatedPoints(line.getCoordinates()), this.distance), 2, 0);
        }
    }

    private void addPolygon(Polygon polygon) {
        Polygon p = polygon;
        double offsetDistance = this.distance;
        int offsetSide = 1;
        if (this.distance < 0.0d) {
            offsetDistance = -this.distance;
            offsetSide = 2;
        }
        LinearRing shell = (LinearRing) p.getExteriorRing();
        Coordinate[] shellCoord = CoordinateArrays.removeRepeatedPoints(shell.getCoordinates());
        if (this.distance < 0.0d && isErodedCompletely(shell, this.distance)) {
            return;
        }
        if (this.distance > 0.0d || shellCoord.length >= 3) {
            addPolygonRing(shellCoord, offsetDistance, offsetSide, 2, 0);
            for (int i = 0; i < p.getNumInteriorRing(); i++) {
                LinearRing hole = (LinearRing) p.getInteriorRingN(i);
                Coordinate[] holeCoord = CoordinateArrays.removeRepeatedPoints(hole.getCoordinates());
                if (this.distance <= 0.0d || !isErodedCompletely(hole, -this.distance)) {
                    addPolygonRing(holeCoord, offsetDistance, Position.opposite(offsetSide), 0, 2);
                }
            }
        }
    }

    private void addPolygonRing(Coordinate[] coordinateArr, double d, int i, int i2, int i3) {
        Coordinate[] coord = coordinateArr;
        double offsetDistance = d;
        int side = i;
        int cwLeftLoc = i2;
        int cwRightLoc = i3;
        if (offsetDistance != 0.0d || coord.length >= 4) {
            int leftLoc = cwLeftLoc;
            int rightLoc = cwRightLoc;
            if (coord.length >= 4 && CGAlgorithms.isCCW(coord)) {
                leftLoc = cwRightLoc;
                rightLoc = cwLeftLoc;
                side = Position.opposite(side);
            }
            addCurve(this.curveBuilder.getRingCurve(coord, side, offsetDistance), leftLoc, rightLoc);
        }
    }

    private boolean isErodedCompletely(LinearRing linearRing, double d) {
        boolean z;
        LinearRing ring = linearRing;
        double bufferDistance = d;
        Coordinate[] ringCoord = ring.getCoordinates();
        if (ringCoord.length < 4) {
            if (bufferDistance < 0.0d) {
                z = true;
            } else {
                z = false;
            }
            return z;
        } else if (ringCoord.length == 4) {
            return isTriangleErodedCompletely(ringCoord, bufferDistance);
        } else {
            Envelope env = ring.getEnvelopeInternal();
            double envMinDimension = Math.min(env.getHeight(), env.getWidth());
            if (bufferDistance >= 0.0d || 2.0d * Math.abs(bufferDistance) <= envMinDimension) {
                return false;
            }
            return true;
        }
    }

    private boolean isTriangleErodedCompletely(Coordinate[] coordinateArr, double bufferDistance) {
        Triangle triangle;
        Coordinate[] triangleCoord = coordinateArr;
        new Triangle(triangleCoord[0], triangleCoord[1], triangleCoord[2]);
        Triangle tri = triangle;
        return CGAlgorithms.distancePointLine(tri.inCentre(), tri.f425p0, tri.f426p1) < Math.abs(bufferDistance);
    }
}
