package org.locationtech.jts.operation.overlay.snap;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;

public class LineStringSnapper {
    private boolean allowSnappingToSourceVertices;
    private boolean isClosed;
    private LineSegment seg;
    private double snapTolerance;
    private Coordinate[] srcPts;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public LineStringSnapper(LineString srcLine, double snapTolerance2) {
        this(srcLine.getCoordinates(), snapTolerance2);
    }

    public LineStringSnapper(Coordinate[] coordinateArr, double snapTolerance2) {
        LineSegment lineSegment;
        Coordinate[] srcPts2 = coordinateArr;
        this.snapTolerance = 0.0d;
        new LineSegment();
        this.seg = lineSegment;
        this.allowSnappingToSourceVertices = false;
        this.isClosed = false;
        this.srcPts = srcPts2;
        this.isClosed = isClosed(srcPts2);
        this.snapTolerance = snapTolerance2;
    }

    public void setAllowSnappingToSourceVertices(boolean allowSnappingToSourceVertices2) {
        boolean z = allowSnappingToSourceVertices2;
        this.allowSnappingToSourceVertices = z;
    }

    private static boolean isClosed(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        if (pts.length <= 1) {
            return false;
        }
        return pts[0].equals2D(pts[pts.length - 1]);
    }

    public Coordinate[] snapTo(Coordinate[] coordinateArr) {
        CoordinateList coordinateList;
        Coordinate[] snapPts = coordinateArr;
        new CoordinateList(this.srcPts);
        CoordinateList coordList = coordinateList;
        snapVertices(coordList, snapPts);
        snapSegments(coordList, snapPts);
        return coordList.toCoordinateArray();
    }

    private void snapVertices(CoordinateList coordinateList, Coordinate[] coordinateArr) {
        int size;
        Object obj;
        Object obj2;
        CoordinateList srcCoords = coordinateList;
        Coordinate[] snapPts = coordinateArr;
        if (this.isClosed) {
            size = srcCoords.size() - 1;
        } else {
            size = srcCoords.size();
        }
        int end = size;
        for (int i = 0; i < end; i++) {
            Coordinate snapVert = findSnapForVertex((Coordinate) srcCoords.get(i), snapPts);
            if (snapVert != null) {
                new Coordinate(snapVert);
                Object obj3 = srcCoords.set(i, obj);
                if (i == 0 && this.isClosed) {
                    new Coordinate(snapVert);
                    Object obj4 = srcCoords.set(srcCoords.size() - 1, obj2);
                }
            }
        }
    }

    private Coordinate findSnapForVertex(Coordinate coordinate, Coordinate[] coordinateArr) {
        Coordinate pt = coordinate;
        Coordinate[] snapPts = coordinateArr;
        for (int i = 0; i < snapPts.length; i++) {
            if (pt.equals2D(snapPts[i])) {
                return null;
            }
            if (pt.distance(snapPts[i]) < this.snapTolerance) {
                return snapPts[i];
            }
        }
        return null;
    }

    private void snapSegments(CoordinateList coordinateList, Coordinate[] coordinateArr) {
        Coordinate coordinate;
        CoordinateList srcCoords = coordinateList;
        Coordinate[] snapPts = coordinateArr;
        if (snapPts.length != 0) {
            int distinctPtCount = snapPts.length;
            if (snapPts[0].equals2D(snapPts[snapPts.length - 1])) {
                distinctPtCount = snapPts.length - 1;
            }
            for (int i = 0; i < distinctPtCount; i++) {
                Coordinate snapPt = snapPts[i];
                int index = findSegmentIndexToSnap(snapPt, srcCoords);
                if (index >= 0) {
                    new Coordinate(snapPt);
                    srcCoords.add(index + 1, coordinate, false);
                }
            }
        }
    }

    private int findSegmentIndexToSnap(Coordinate coordinate, CoordinateList coordinateList) {
        Coordinate snapPt = coordinate;
        CoordinateList srcCoords = coordinateList;
        double minDist = Double.MAX_VALUE;
        int snapIndex = -1;
        for (int i = 0; i < srcCoords.size() - 1; i++) {
            this.seg.f422p0 = (Coordinate) srcCoords.get(i);
            this.seg.f423p1 = (Coordinate) srcCoords.get(i + 1);
            if (!this.seg.f422p0.equals2D(snapPt) && !this.seg.f423p1.equals2D(snapPt)) {
                double dist = this.seg.distance(snapPt);
                if (dist < this.snapTolerance && dist < minDist) {
                    minDist = dist;
                    snapIndex = i;
                }
            } else if (!this.allowSnappingToSourceVertices) {
                return -1;
            }
        }
        return snapIndex;
    }
}
