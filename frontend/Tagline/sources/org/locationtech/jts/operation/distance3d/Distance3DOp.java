package org.locationtech.jts.operation.distance3d;

import org.locationtech.jts.algorithm.CGAlgorithms3D;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.operation.distance.GeometryLocation;

public class Distance3DOp {
    private Geometry[] geom;
    private boolean isDone;
    private double minDistance;
    private GeometryLocation[] minDistanceLocation;
    private double terminateDistance;

    public static double distance(Geometry g0, Geometry g1) {
        Distance3DOp distOp;
        new Distance3DOp(g0, g1);
        return distOp.distance();
    }

    public static boolean isWithinDistance(Geometry g0, Geometry g1, double d) {
        Distance3DOp distOp;
        double distance = d;
        new Distance3DOp(g0, g1, distance);
        return distOp.distance() <= distance;
    }

    public static Coordinate[] nearestPoints(Geometry g0, Geometry g1) {
        Distance3DOp distOp;
        new Distance3DOp(g0, g1);
        return distOp.nearestPoints();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public Distance3DOp(Geometry g0, Geometry g1) {
        this(g0, g1, 0.0d);
    }

    public Distance3DOp(Geometry g0, Geometry g1, double terminateDistance2) {
        this.terminateDistance = 0.0d;
        this.minDistance = Double.MAX_VALUE;
        this.isDone = false;
        this.geom = new Geometry[2];
        this.geom[0] = g0;
        this.geom[1] = g1;
        this.terminateDistance = terminateDistance2;
    }

    public double distance() {
        Throwable th;
        if (this.geom[0] == null || this.geom[1] == null) {
            Throwable th2 = th;
            new IllegalArgumentException("null geometries are not supported");
            throw th2;
        } else if (this.geom[0].isEmpty() || this.geom[1].isEmpty()) {
            return 0.0d;
        } else {
            computeMinDistance();
            return this.minDistance;
        }
    }

    public Coordinate[] nearestPoints() {
        computeMinDistance();
        Coordinate[] coordinateArr = new Coordinate[2];
        coordinateArr[0] = this.minDistanceLocation[0].getCoordinate();
        Coordinate[] nearestPts = coordinateArr;
        nearestPts[1] = this.minDistanceLocation[1].getCoordinate();
        return nearestPts;
    }

    public GeometryLocation[] nearestLocations() {
        computeMinDistance();
        return this.minDistanceLocation;
    }

    private void updateDistance(double dist, GeometryLocation geometryLocation, GeometryLocation geometryLocation2, boolean flip) {
        GeometryLocation loc0 = geometryLocation;
        GeometryLocation loc1 = geometryLocation2;
        this.minDistance = dist;
        int index = flip ? 1 : 0;
        this.minDistanceLocation[index] = loc0;
        this.minDistanceLocation[1 - index] = loc1;
        if (this.minDistance < this.terminateDistance) {
            this.isDone = true;
        }
    }

    private void computeMinDistance() {
        if (this.minDistanceLocation == null) {
            this.minDistanceLocation = new GeometryLocation[2];
            int geomIndex = mostPolygonalIndex();
            computeMinDistanceMultiMulti(this.geom[geomIndex], this.geom[1 - geomIndex], geomIndex == 0);
        }
    }

    private int mostPolygonalIndex() {
        int dim0 = this.geom[0].getDimension();
        int dim1 = this.geom[1].getDimension();
        if (dim0 < 2 || dim1 < 2) {
            if (dim0 >= 2) {
                return 0;
            }
            if (dim1 >= 2) {
                return 1;
            }
            return 0;
        } else if (this.geom[0].getNumPoints() > this.geom[1].getNumPoints()) {
            return 0;
        } else {
            return 1;
        }
    }

    private void computeMinDistanceMultiMulti(Geometry geometry, Geometry geometry2, boolean z) {
        Geometry g0 = geometry;
        Geometry g1 = geometry2;
        boolean flip = z;
        if (g0 instanceof GeometryCollection) {
            int n = g0.getNumGeometries();
            int i = 0;
            while (i < n) {
                computeMinDistanceMultiMulti(g0.getGeometryN(i), g1, flip);
                if (!this.isDone) {
                    i++;
                } else {
                    return;
                }
            }
        } else if (!g0.isEmpty()) {
            if (g0 instanceof Polygon) {
                computeMinDistanceOneMulti(polyPlane(g0), g1, flip);
            } else {
                computeMinDistanceOneMulti(g0, g1, flip);
            }
        }
    }

    private void computeMinDistanceOneMulti(Geometry geometry, Geometry geometry2, boolean z) {
        Geometry g0 = geometry;
        Geometry g1 = geometry2;
        boolean flip = z;
        if (g1 instanceof GeometryCollection) {
            int n = g1.getNumGeometries();
            int i = 0;
            while (i < n) {
                computeMinDistanceOneMulti(g0, g1.getGeometryN(i), flip);
                if (!this.isDone) {
                    i++;
                } else {
                    return;
                }
            }
            return;
        }
        computeMinDistance(g0, g1, flip);
    }

    private void computeMinDistanceOneMulti(PlanarPolygon3D planarPolygon3D, Geometry geometry, boolean z) {
        PlanarPolygon3D poly = planarPolygon3D;
        Geometry geom2 = geometry;
        boolean flip = z;
        if (geom2 instanceof GeometryCollection) {
            int n = geom2.getNumGeometries();
            int i = 0;
            while (i < n) {
                computeMinDistanceOneMulti(poly, geom2.getGeometryN(i), flip);
                if (!this.isDone) {
                    i++;
                } else {
                    return;
                }
            }
        } else if (geom2 instanceof Point) {
            computeMinDistancePolygonPoint(poly, (Point) geom2, flip);
        } else if (geom2 instanceof LineString) {
            computeMinDistancePolygonLine(poly, (LineString) geom2, flip);
        } else if (geom2 instanceof Polygon) {
            computeMinDistancePolygonPolygon(poly, (Polygon) geom2, flip);
        }
    }

    private static PlanarPolygon3D polyPlane(Geometry poly) {
        PlanarPolygon3D planarPolygon3D;
        new PlanarPolygon3D((Polygon) poly);
        return planarPolygon3D;
    }

    private void computeMinDistance(Geometry geometry, Geometry geometry2, boolean z) {
        Geometry g0 = geometry;
        Geometry g1 = geometry2;
        boolean flip = z;
        if (g0 instanceof Point) {
            if (g1 instanceof Point) {
                computeMinDistancePointPoint((Point) g0, (Point) g1, flip);
                return;
            } else if (g1 instanceof LineString) {
                computeMinDistanceLinePoint((LineString) g1, (Point) g0, !flip);
                return;
            } else if (g1 instanceof Polygon) {
                computeMinDistancePolygonPoint(polyPlane(g1), (Point) g0, !flip);
                return;
            }
        }
        if (g0 instanceof LineString) {
            if (g1 instanceof Point) {
                computeMinDistanceLinePoint((LineString) g0, (Point) g1, flip);
                return;
            } else if (g1 instanceof LineString) {
                computeMinDistanceLineLine((LineString) g0, (LineString) g1, flip);
                return;
            } else if (g1 instanceof Polygon) {
                computeMinDistancePolygonLine(polyPlane(g1), (LineString) g0, !flip);
                return;
            }
        }
        if (!(g0 instanceof Polygon)) {
            return;
        }
        if (g1 instanceof Point) {
            computeMinDistancePolygonPoint(polyPlane(g0), (Point) g1, flip);
        } else if (g1 instanceof LineString) {
            computeMinDistancePolygonLine(polyPlane(g0), (LineString) g1, flip);
        } else if (g1 instanceof Polygon) {
            computeMinDistancePolygonPolygon(polyPlane(g0), (Polygon) g1, flip);
        }
    }

    private void computeMinDistancePolygonPolygon(PlanarPolygon3D planarPolygon3D, Polygon polygon, boolean z) {
        PlanarPolygon3D polyPlane1;
        PlanarPolygon3D poly0 = planarPolygon3D;
        Polygon poly1 = polygon;
        boolean flip = z;
        computeMinDistancePolygonRings(poly0, poly1, flip);
        if (!this.isDone) {
            new PlanarPolygon3D(poly1);
            computeMinDistancePolygonRings(polyPlane1, poly0.getPolygon(), flip);
        }
    }

    private void computeMinDistancePolygonRings(PlanarPolygon3D planarPolygon3D, Polygon polygon, boolean z) {
        PlanarPolygon3D poly = planarPolygon3D;
        Polygon ringPoly = polygon;
        boolean flip = z;
        computeMinDistancePolygonLine(poly, ringPoly.getExteriorRing(), flip);
        if (!this.isDone) {
            int nHole = ringPoly.getNumInteriorRing();
            int i = 0;
            while (i < nHole) {
                computeMinDistancePolygonLine(poly, ringPoly.getInteriorRingN(i), flip);
                if (!this.isDone) {
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private void computeMinDistancePolygonLine(PlanarPolygon3D planarPolygon3D, LineString lineString, boolean z) {
        GeometryLocation geometryLocation;
        GeometryLocation geometryLocation2;
        PlanarPolygon3D poly = planarPolygon3D;
        LineString line = lineString;
        boolean flip = z;
        Coordinate intPt = intersection(poly, line);
        if (intPt != null) {
            new GeometryLocation(poly.getPolygon(), 0, intPt);
            new GeometryLocation(line, 0, intPt);
            updateDistance(0.0d, geometryLocation, geometryLocation2, flip);
            return;
        }
        computeMinDistanceLineLine(poly.getPolygon().getExteriorRing(), line, flip);
        if (!this.isDone) {
            int nHole = poly.getPolygon().getNumInteriorRing();
            int i = 0;
            while (i < nHole) {
                computeMinDistanceLineLine(poly.getPolygon().getInteriorRingN(i), line, flip);
                if (!this.isDone) {
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private Coordinate intersection(PlanarPolygon3D planarPolygon3D, LineString line) {
        Coordinate coordinate;
        Coordinate coordinate2;
        PlanarPolygon3D poly = planarPolygon3D;
        CoordinateSequence seq = line.getCoordinateSequence();
        if (seq.size() == 0) {
            return null;
        }
        new Coordinate();
        Coordinate p0 = coordinate;
        seq.getCoordinate(0, p0);
        double d0 = poly.getPlane().orientedDistance(p0);
        new Coordinate();
        Coordinate p1 = coordinate2;
        for (int i = 0; i < seq.size() - 1; i++) {
            seq.getCoordinate(i, p0);
            seq.getCoordinate(i + 1, p1);
            double d1 = poly.getPlane().orientedDistance(p1);
            if (d0 * d1 <= 0.0d) {
                Coordinate intPt = segmentPoint(p0, p1, d0, d1);
                if (poly.intersects(intPt)) {
                    return intPt;
                }
                d0 = d1;
            }
        }
        return null;
    }

    private void computeMinDistancePolygonPoint(PlanarPolygon3D planarPolygon3D, Point point, boolean z) {
        GeometryLocation geometryLocation;
        GeometryLocation geometryLocation2;
        PlanarPolygon3D polyPlane = planarPolygon3D;
        Point point2 = point;
        boolean flip = z;
        Coordinate pt = point2.getCoordinate();
        LineString shell = polyPlane.getPolygon().getExteriorRing();
        if (polyPlane.intersects(pt, shell)) {
            int nHole = polyPlane.getPolygon().getNumInteriorRing();
            for (int i = 0; i < nHole; i++) {
                LineString hole = polyPlane.getPolygon().getInteriorRingN(i);
                if (polyPlane.intersects(pt, hole)) {
                    computeMinDistanceLinePoint(hole, point2, flip);
                    return;
                }
            }
            double dist = Math.abs(polyPlane.getPlane().orientedDistance(pt));
            new GeometryLocation(polyPlane.getPolygon(), 0, pt);
            new GeometryLocation(point2, 0, pt);
            updateDistance(dist, geometryLocation, geometryLocation2, flip);
        }
        computeMinDistanceLinePoint(shell, point2, flip);
    }

    private void computeMinDistanceLineLine(LineString lineString, LineString lineString2, boolean z) {
        LineSegment seg0;
        LineSegment seg1;
        GeometryLocation geometryLocation;
        GeometryLocation geometryLocation2;
        LineString line0 = lineString;
        LineString line1 = lineString2;
        boolean flip = z;
        Coordinate[] coord0 = line0.getCoordinates();
        Coordinate[] coord1 = line1.getCoordinates();
        for (int i = 0; i < coord0.length - 1; i++) {
            int j = 0;
            while (j < coord1.length - 1) {
                double dist = CGAlgorithms3D.distanceSegmentSegment(coord0[i], coord0[i + 1], coord1[j], coord1[j + 1]);
                if (dist < this.minDistance) {
                    this.minDistance = dist;
                    new LineSegment(coord0[i], coord0[i + 1]);
                    new LineSegment(coord1[j], coord1[j + 1]);
                    Coordinate[] closestPt = seg0.closestPoints(seg1);
                    new GeometryLocation(line0, i, closestPt[0]);
                    new GeometryLocation(line1, j, closestPt[1]);
                    updateDistance(dist, geometryLocation, geometryLocation2, flip);
                }
                if (!this.isDone) {
                    j++;
                } else {
                    return;
                }
            }
        }
    }

    private void computeMinDistanceLinePoint(LineString lineString, Point point, boolean z) {
        LineSegment seg;
        GeometryLocation geometryLocation;
        GeometryLocation geometryLocation2;
        LineString line = lineString;
        Point point2 = point;
        boolean flip = z;
        Coordinate[] lineCoord = line.getCoordinates();
        Coordinate coord = point2.getCoordinate();
        int i = 0;
        while (i < lineCoord.length - 1) {
            double dist = CGAlgorithms3D.distancePointSegment(coord, lineCoord[i], lineCoord[i + 1]);
            if (dist < this.minDistance) {
                new LineSegment(lineCoord[i], lineCoord[i + 1]);
                new GeometryLocation(line, i, seg.closestPoint(coord));
                new GeometryLocation(point2, 0, coord);
                updateDistance(dist, geometryLocation, geometryLocation2, flip);
            }
            if (!this.isDone) {
                i++;
            } else {
                return;
            }
        }
    }

    private void computeMinDistancePointPoint(Point point, Point point2, boolean z) {
        GeometryLocation geometryLocation;
        GeometryLocation geometryLocation2;
        Point point0 = point;
        Point point1 = point2;
        boolean flip = z;
        double dist = CGAlgorithms3D.distance(point0.getCoordinate(), point1.getCoordinate());
        if (dist < this.minDistance) {
            new GeometryLocation(point0, 0, point0.getCoordinate());
            new GeometryLocation(point1, 0, point1.getCoordinate());
            updateDistance(dist, geometryLocation, geometryLocation2, flip);
        }
    }

    private static Coordinate segmentPoint(Coordinate coordinate, Coordinate coordinate2, double d, double d2) {
        Coordinate p0;
        Coordinate p02;
        Coordinate p03;
        Coordinate p04 = coordinate;
        Coordinate p1 = coordinate2;
        double d0 = d;
        double d1 = d2;
        if (d0 <= 0.0d) {
            new Coordinate(p04);
            return p03;
        } else if (d1 <= 0.0d) {
            new Coordinate(p1);
            return p02;
        } else {
            double f = Math.abs(d0) / (Math.abs(d0) + Math.abs(d1));
            new Coordinate(p04.f412x + (f * (p1.f412x - p04.f412x)), p04.f413y + (f * (p1.f413y - p04.f413y)), p04.f414z + (f * (p1.f414z - p04.f414z)));
            return p0;
        }
    }
}
