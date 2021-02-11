package org.locationtech.jts.linearref;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

public class LinearGeometryBuilder {
    private CoordinateList coordList = null;
    private boolean fixInvalidLines = false;
    private GeometryFactory geomFact;
    private boolean ignoreInvalidLines = false;
    private Coordinate lastPt = null;
    private List lines;

    public LinearGeometryBuilder(GeometryFactory geomFact2) {
        List list;
        new ArrayList();
        this.lines = list;
        this.geomFact = geomFact2;
    }

    public void setIgnoreInvalidLines(boolean ignoreInvalidLines2) {
        boolean z = ignoreInvalidLines2;
        this.ignoreInvalidLines = z;
    }

    public void setFixInvalidLines(boolean fixInvalidLines2) {
        boolean z = fixInvalidLines2;
        this.fixInvalidLines = z;
    }

    public void add(Coordinate pt) {
        add(pt, true);
    }

    public void add(Coordinate coordinate, boolean z) {
        CoordinateList coordinateList;
        Coordinate pt = coordinate;
        boolean allowRepeatedPoints = z;
        if (this.coordList == null) {
            new CoordinateList();
            this.coordList = coordinateList;
        }
        this.coordList.add(pt, allowRepeatedPoints);
        this.lastPt = pt;
    }

    public Coordinate getLastCoordinate() {
        return this.lastPt;
    }

    public void endLine() {
        if (this.coordList != null) {
            if (!this.ignoreInvalidLines || this.coordList.size() >= 2) {
                Coordinate[] rawPts = this.coordList.toCoordinateArray();
                Coordinate[] pts = rawPts;
                if (this.fixInvalidLines) {
                    pts = validCoordinateSequence(rawPts);
                }
                this.coordList = null;
                LineString line = null;
                try {
                    line = this.geomFact.createLineString(pts);
                } catch (IllegalArgumentException e) {
                    IllegalArgumentException ex = e;
                    if (!this.ignoreInvalidLines) {
                        throw ex;
                    }
                }
                if (line != null) {
                    boolean add = this.lines.add(line);
                    return;
                }
                return;
            }
            this.coordList = null;
        }
    }

    private Coordinate[] validCoordinateSequence(Coordinate[] coordinateArr) {
        Coordinate[] pts = coordinateArr;
        if (pts.length >= 2) {
            return pts;
        }
        Coordinate[] coordinateArr2 = new Coordinate[2];
        coordinateArr2[0] = pts[0];
        Coordinate[] validPts = coordinateArr2;
        validPts[1] = pts[0];
        return validPts;
    }

    public Geometry getGeometry() {
        endLine();
        return this.geomFact.buildGeometry(this.lines);
    }
}
