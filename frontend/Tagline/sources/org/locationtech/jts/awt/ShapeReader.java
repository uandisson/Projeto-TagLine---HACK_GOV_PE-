package org.locationtech.jts.awt;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;

public class ShapeReader {
    private static final AffineTransform INVERT_Y = AffineTransform.getScaleInstance(1.0d, -1.0d);
    private GeometryFactory geometryFactory;

    public static Geometry read(PathIterator pathIt, GeometryFactory geomFact) {
        ShapeReader pc;
        new ShapeReader(geomFact);
        return pc.read(pathIt);
    }

    public static Geometry read(Shape shp, double flatness, GeometryFactory geomFact) {
        return read(shp.getPathIterator(INVERT_Y, flatness), geomFact);
    }

    public ShapeReader(GeometryFactory geometryFactory2) {
        this.geometryFactory = geometryFactory2;
    }

    public Geometry read(PathIterator pathIt) {
        List list;
        List list2;
        List pathPtSeq = toCoordinates(pathIt);
        new ArrayList();
        List polys = list;
        int seqIndex = 0;
        while (seqIndex < pathPtSeq.size()) {
            LinearRing shell = this.geometryFactory.createLinearRing((Coordinate[]) pathPtSeq.get(seqIndex));
            seqIndex++;
            new ArrayList();
            List holes = list2;
            while (seqIndex < pathPtSeq.size() && isHole((Coordinate[]) pathPtSeq.get(seqIndex))) {
                boolean add = holes.add(this.geometryFactory.createLinearRing((Coordinate[]) pathPtSeq.get(seqIndex)));
                seqIndex++;
            }
            boolean add2 = polys.add(this.geometryFactory.createPolygon(shell, GeometryFactory.toLinearRingArray(holes)));
        }
        return this.geometryFactory.buildGeometry(polys);
    }

    private boolean isHole(Coordinate[] pts) {
        return CGAlgorithms.isCCW(pts);
    }

    public static List toCoordinates(PathIterator pathIterator) {
        List list;
        Coordinate[] pts;
        PathIterator pathIt = pathIterator;
        new ArrayList();
        List coordArrays = list;
        while (!pathIt.isDone() && (pts = nextCoordinateArray(pathIt)) != null) {
            boolean add = coordArrays.add(pts);
        }
        return coordArrays;
    }

    private static Coordinate[] nextCoordinateArray(PathIterator pathIterator) {
        Throwable th;
        Object obj;
        CoordinateList coordinateList;
        Object obj2;
        PathIterator pathIt = pathIterator;
        double[] pathPt = new double[6];
        CoordinateList coordList = null;
        boolean isDone = false;
        while (!pathIt.isDone()) {
            switch (pathIt.currentSegment(pathPt)) {
                case 0:
                    if (coordList == null) {
                        new CoordinateList();
                        coordList = coordinateList;
                        new Coordinate(pathPt[0], pathPt[1]);
                        boolean add = coordList.add(obj2);
                        pathIt.next();
                        break;
                    } else {
                        isDone = true;
                        break;
                    }
                case 1:
                    new Coordinate(pathPt[0], pathPt[1]);
                    boolean add2 = coordList.add(obj);
                    pathIt.next();
                    break;
                case 4:
                    coordList.closeRing();
                    pathIt.next();
                    isDone = true;
                    break;
                default:
                    Throwable th2 = th;
                    new IllegalArgumentException("unhandled (non-linear) segment type encountered");
                    throw th2;
            }
            if (isDone) {
                return coordList.toCoordinateArray();
            }
        }
        return coordList.toCoordinateArray();
    }
}
