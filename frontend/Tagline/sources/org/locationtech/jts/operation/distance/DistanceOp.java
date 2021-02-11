package org.locationtech.jts.operation.distance;

import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.algorithm.PointLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.util.LinearComponentExtracter;
import org.locationtech.jts.geom.util.PointExtracter;
import org.locationtech.jts.geom.util.PolygonExtracter;

public class DistanceOp {
    private Geometry[] geom;
    private double minDistance;
    private GeometryLocation[] minDistanceLocation;
    private PointLocator ptLocator;
    private double terminateDistance;

    public static double distance(Geometry g0, Geometry g1) {
        DistanceOp distOp;
        new DistanceOp(g0, g1);
        return distOp.distance();
    }

    public static boolean isWithinDistance(Geometry g0, Geometry g1, double d) {
        DistanceOp distOp;
        double distance = d;
        new DistanceOp(g0, g1, distance);
        return distOp.distance() <= distance;
    }

    public static Coordinate[] nearestPoints(Geometry g0, Geometry g1) {
        DistanceOp distOp;
        new DistanceOp(g0, g1);
        return distOp.nearestPoints();
    }

    public static Coordinate[] closestPoints(Geometry g0, Geometry g1) {
        DistanceOp distOp;
        new DistanceOp(g0, g1);
        return distOp.nearestPoints();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public DistanceOp(Geometry g0, Geometry g1) {
        this(g0, g1, 0.0d);
    }

    public DistanceOp(Geometry g0, Geometry g1, double terminateDistance2) {
        PointLocator pointLocator;
        this.terminateDistance = 0.0d;
        new PointLocator();
        this.ptLocator = pointLocator;
        this.minDistance = Double.MAX_VALUE;
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

    public Coordinate[] closestPoints() {
        return nearestPoints();
    }

    public GeometryLocation[] nearestLocations() {
        computeMinDistance();
        return this.minDistanceLocation;
    }

    public GeometryLocation[] closestLocations() {
        return nearestLocations();
    }

    private void updateMinDistance(GeometryLocation[] geometryLocationArr, boolean z) {
        GeometryLocation[] locGeom = geometryLocationArr;
        boolean flip = z;
        if (locGeom[0] != null) {
            if (flip) {
                this.minDistanceLocation[0] = locGeom[1];
                this.minDistanceLocation[1] = locGeom[0];
                return;
            }
            this.minDistanceLocation[0] = locGeom[0];
            this.minDistanceLocation[1] = locGeom[1];
        }
    }

    private void computeMinDistance() {
        if (this.minDistanceLocation == null) {
            this.minDistanceLocation = new GeometryLocation[2];
            computeContainmentDistance();
            if (this.minDistance > this.terminateDistance) {
                computeFacetDistance();
            }
        }
    }

    private void computeContainmentDistance() {
        GeometryLocation[] locPtPoly = new GeometryLocation[2];
        computeContainmentDistance(0, locPtPoly);
        if (this.minDistance > this.terminateDistance) {
            computeContainmentDistance(1, locPtPoly);
        }
    }

    private void computeContainmentDistance(int i, GeometryLocation[] geometryLocationArr) {
        int polyGeomIndex = i;
        GeometryLocation[] locPtPoly = geometryLocationArr;
        int locationsIndex = 1 - polyGeomIndex;
        List polys = PolygonExtracter.getPolygons(this.geom[polyGeomIndex]);
        if (polys.size() > 0) {
            computeContainmentDistance(ConnectedElementLocationFilter.getLocations(this.geom[locationsIndex]), polys, locPtPoly);
            if (this.minDistance <= this.terminateDistance) {
                this.minDistanceLocation[locationsIndex] = locPtPoly[0];
                this.minDistanceLocation[polyGeomIndex] = locPtPoly[1];
            }
        }
    }

    private void computeContainmentDistance(List list, List list2, GeometryLocation[] geometryLocationArr) {
        List locs = list;
        List polys = list2;
        GeometryLocation[] locPtPoly = geometryLocationArr;
        for (int i = 0; i < locs.size(); i++) {
            GeometryLocation loc = (GeometryLocation) locs.get(i);
            int j = 0;
            while (j < polys.size()) {
                computeContainmentDistance(loc, (Polygon) polys.get(j), locPtPoly);
                if (this.minDistance > this.terminateDistance) {
                    j++;
                } else {
                    return;
                }
            }
        }
    }

    private void computeContainmentDistance(GeometryLocation geometryLocation, Polygon polygon, GeometryLocation[] geometryLocationArr) {
        GeometryLocation geometryLocation2;
        GeometryLocation ptLoc = geometryLocation;
        Polygon poly = polygon;
        GeometryLocation[] locPtPoly = geometryLocationArr;
        Coordinate pt = ptLoc.getCoordinate();
        if (2 != this.ptLocator.locate(pt, (Geometry) poly)) {
            this.minDistance = 0.0d;
            locPtPoly[0] = ptLoc;
            new GeometryLocation(poly, pt);
            locPtPoly[1] = geometryLocation2;
        }
    }

    private void computeFacetDistance() {
        GeometryLocation[] locGeom = new GeometryLocation[2];
        List lines0 = LinearComponentExtracter.getLines(this.geom[0]);
        List lines1 = LinearComponentExtracter.getLines(this.geom[1]);
        List pts0 = PointExtracter.getPoints(this.geom[0]);
        List pts1 = PointExtracter.getPoints(this.geom[1]);
        computeMinDistanceLines(lines0, lines1, locGeom);
        updateMinDistance(locGeom, false);
        if (this.minDistance > this.terminateDistance) {
            locGeom[0] = null;
            locGeom[1] = null;
            computeMinDistanceLinesPoints(lines0, pts1, locGeom);
            updateMinDistance(locGeom, false);
            if (this.minDistance > this.terminateDistance) {
                locGeom[0] = null;
                locGeom[1] = null;
                computeMinDistanceLinesPoints(lines1, pts0, locGeom);
                updateMinDistance(locGeom, true);
                if (this.minDistance > this.terminateDistance) {
                    locGeom[0] = null;
                    locGeom[1] = null;
                    computeMinDistancePoints(pts0, pts1, locGeom);
                    updateMinDistance(locGeom, false);
                }
            }
        }
    }

    private void computeMinDistanceLines(List list, List list2, GeometryLocation[] geometryLocationArr) {
        List lines0 = list;
        List lines1 = list2;
        GeometryLocation[] locGeom = geometryLocationArr;
        for (int i = 0; i < lines0.size(); i++) {
            LineString line0 = (LineString) lines0.get(i);
            int j = 0;
            while (j < lines1.size()) {
                computeMinDistance(line0, (LineString) lines1.get(j), locGeom);
                if (this.minDistance > this.terminateDistance) {
                    j++;
                } else {
                    return;
                }
            }
        }
    }

    private void computeMinDistancePoints(List list, List list2, GeometryLocation[] geometryLocationArr) {
        GeometryLocation geometryLocation;
        GeometryLocation geometryLocation2;
        List points0 = list;
        List points1 = list2;
        GeometryLocation[] locGeom = geometryLocationArr;
        for (int i = 0; i < points0.size(); i++) {
            Point pt0 = (Point) points0.get(i);
            int j = 0;
            while (j < points1.size()) {
                Point pt1 = (Point) points1.get(j);
                double dist = pt0.getCoordinate().distance(pt1.getCoordinate());
                if (dist < this.minDistance) {
                    this.minDistance = dist;
                    new GeometryLocation(pt0, 0, pt0.getCoordinate());
                    locGeom[0] = geometryLocation;
                    new GeometryLocation(pt1, 0, pt1.getCoordinate());
                    locGeom[1] = geometryLocation2;
                }
                if (this.minDistance > this.terminateDistance) {
                    j++;
                } else {
                    return;
                }
            }
        }
    }

    private void computeMinDistanceLinesPoints(List list, List list2, GeometryLocation[] geometryLocationArr) {
        List lines = list;
        List points = list2;
        GeometryLocation[] locGeom = geometryLocationArr;
        for (int i = 0; i < lines.size(); i++) {
            LineString line = (LineString) lines.get(i);
            int j = 0;
            while (j < points.size()) {
                computeMinDistance(line, (Point) points.get(j), locGeom);
                if (this.minDistance > this.terminateDistance) {
                    j++;
                } else {
                    return;
                }
            }
        }
    }

    private void computeMinDistance(LineString lineString, LineString lineString2, GeometryLocation[] geometryLocationArr) {
        LineSegment seg0;
        LineSegment seg1;
        GeometryLocation geometryLocation;
        GeometryLocation geometryLocation2;
        LineString line0 = lineString;
        LineString line1 = lineString2;
        GeometryLocation[] locGeom = geometryLocationArr;
        if (line0.getEnvelopeInternal().distance(line1.getEnvelopeInternal()) <= this.minDistance) {
            Coordinate[] coord0 = line0.getCoordinates();
            Coordinate[] coord1 = line1.getCoordinates();
            for (int i = 0; i < coord0.length - 1; i++) {
                int j = 0;
                while (j < coord1.length - 1) {
                    double dist = CGAlgorithms.distanceLineLine(coord0[i], coord0[i + 1], coord1[j], coord1[j + 1]);
                    if (dist < this.minDistance) {
                        this.minDistance = dist;
                        new LineSegment(coord0[i], coord0[i + 1]);
                        new LineSegment(coord1[j], coord1[j + 1]);
                        Coordinate[] closestPt = seg0.closestPoints(seg1);
                        new GeometryLocation(line0, i, closestPt[0]);
                        locGeom[0] = geometryLocation;
                        new GeometryLocation(line1, j, closestPt[1]);
                        locGeom[1] = geometryLocation2;
                    }
                    if (this.minDistance > this.terminateDistance) {
                        j++;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private void computeMinDistance(LineString lineString, Point point, GeometryLocation[] geometryLocationArr) {
        LineSegment seg;
        GeometryLocation geometryLocation;
        GeometryLocation geometryLocation2;
        LineString line = lineString;
        Point pt = point;
        GeometryLocation[] locGeom = geometryLocationArr;
        if (line.getEnvelopeInternal().distance(pt.getEnvelopeInternal()) <= this.minDistance) {
            Coordinate[] coord0 = line.getCoordinates();
            Coordinate coord = pt.getCoordinate();
            int i = 0;
            while (i < coord0.length - 1) {
                double dist = CGAlgorithms.distancePointLine(coord, coord0[i], coord0[i + 1]);
                if (dist < this.minDistance) {
                    this.minDistance = dist;
                    new LineSegment(coord0[i], coord0[i + 1]);
                    new GeometryLocation(line, i, seg.closestPoint(coord));
                    locGeom[0] = geometryLocation;
                    new GeometryLocation(pt, 0, coord);
                    locGeom[1] = geometryLocation2;
                }
                if (this.minDistance > this.terminateDistance) {
                    i++;
                } else {
                    return;
                }
            }
        }
    }
}
