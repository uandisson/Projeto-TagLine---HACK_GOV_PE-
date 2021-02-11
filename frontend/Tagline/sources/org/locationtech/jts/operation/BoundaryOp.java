package org.locationtech.jts.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.locationtech.jts.algorithm.BoundaryNodeRule;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.Point;

public class BoundaryOp {
    private BoundaryNodeRule bnRule;
    private Map endpointMap;
    private Geometry geom;
    private GeometryFactory geomFact;

    public static Geometry getBoundary(Geometry g) {
        BoundaryOp bop;
        new BoundaryOp(g);
        return bop.getBoundary();
    }

    public static Geometry getBoundary(Geometry g, BoundaryNodeRule bnRule2) {
        BoundaryOp bop;
        new BoundaryOp(g, bnRule2);
        return bop.getBoundary();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public BoundaryOp(Geometry geom2) {
        this(geom2, BoundaryNodeRule.MOD2_BOUNDARY_RULE);
    }

    public BoundaryOp(Geometry geometry, BoundaryNodeRule bnRule2) {
        Geometry geom2 = geometry;
        this.geom = geom2;
        this.geomFact = geom2.getFactory();
        this.bnRule = bnRule2;
    }

    public Geometry getBoundary() {
        if (this.geom instanceof LineString) {
            return boundaryLineString((LineString) this.geom);
        }
        if (this.geom instanceof MultiLineString) {
            return boundaryMultiLineString((MultiLineString) this.geom);
        }
        return this.geom.getBoundary();
    }

    private MultiPoint getEmptyMultiPoint() {
        return this.geomFact.createMultiPoint((CoordinateSequence) null);
    }

    private Geometry boundaryMultiLineString(MultiLineString multiLineString) {
        MultiLineString mLine = multiLineString;
        if (this.geom.isEmpty()) {
            return getEmptyMultiPoint();
        }
        Coordinate[] bdyPts = computeBoundaryCoordinates(mLine);
        if (bdyPts.length == 1) {
            return this.geomFact.createPoint(bdyPts[0]);
        }
        return this.geomFact.createMultiPoint(bdyPts);
    }

    private Coordinate[] computeBoundaryCoordinates(MultiLineString multiLineString) {
        List list;
        Map map;
        MultiLineString mLine = multiLineString;
        new ArrayList();
        List bdyPts = list;
        new TreeMap();
        this.endpointMap = map;
        for (int i = 0; i < mLine.getNumGeometries(); i++) {
            LineString line = (LineString) mLine.getGeometryN(i);
            if (line.getNumPoints() != 0) {
                addEndpoint(line.getCoordinateN(0));
                addEndpoint(line.getCoordinateN(line.getNumPoints() - 1));
            }
        }
        for (Map.Entry entry : this.endpointMap.entrySet()) {
            if (this.bnRule.isInBoundary(((Counter) entry.getValue()).count)) {
                boolean add = bdyPts.add(entry.getKey());
            }
        }
        return CoordinateArrays.toCoordinateArray(bdyPts);
    }

    private void addEndpoint(Coordinate coordinate) {
        Counter counter;
        Coordinate pt = coordinate;
        Counter counter2 = (Counter) this.endpointMap.get(pt);
        if (counter2 == null) {
            new Counter();
            counter2 = counter;
            Object put = this.endpointMap.put(pt, counter2);
        }
        counter2.count++;
    }

    private Geometry boundaryLineString(LineString lineString) {
        LineString line = lineString;
        if (this.geom.isEmpty()) {
            return getEmptyMultiPoint();
        }
        if (!line.isClosed()) {
            GeometryFactory geometryFactory = this.geomFact;
            Point[] pointArr = new Point[2];
            pointArr[0] = line.getStartPoint();
            Point[] pointArr2 = pointArr;
            pointArr2[1] = line.getEndPoint();
            return geometryFactory.createMultiPoint(pointArr2);
        } else if (this.bnRule.isInBoundary(2)) {
            return line.getStartPoint();
        } else {
            return this.geomFact.createMultiPoint((Coordinate[]) null);
        }
    }
}
