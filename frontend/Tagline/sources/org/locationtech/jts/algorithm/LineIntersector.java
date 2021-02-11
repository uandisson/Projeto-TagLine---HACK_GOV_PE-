package org.locationtech.jts.algorithm;

import java.lang.reflect.Array;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.p006io.WKTWriter;
import org.locationtech.jts.util.Assert;

public abstract class LineIntersector {
    public static final int COLLINEAR = 2;
    public static final int COLLINEAR_INTERSECTION = 2;
    public static final int DONT_INTERSECT = 0;
    public static final int DO_INTERSECT = 1;
    public static final int NO_INTERSECTION = 0;
    public static final int POINT_INTERSECTION = 1;
    protected Coordinate[][] inputLines = ((Coordinate[][]) Array.newInstance(Coordinate.class, new int[]{2, 2}));
    protected int[][] intLineIndex;
    protected Coordinate[] intPt = new Coordinate[2];
    protected boolean isProper;

    /* renamed from: pa */
    protected Coordinate f401pa;

    /* renamed from: pb */
    protected Coordinate f402pb;
    protected PrecisionModel precisionModel = null;
    protected int result;

    /* access modifiers changed from: protected */
    public abstract int computeIntersect(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4);

    public abstract void computeIntersection(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3);

    public static double computeEdgeDistance(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        double dist;
        Coordinate p = coordinate;
        Coordinate p0 = coordinate2;
        Coordinate p1 = coordinate3;
        double dx = Math.abs(p1.f412x - p0.f412x);
        double dy = Math.abs(p1.f413y - p0.f413y);
        if (p.equals(p0)) {
            dist = 0.0d;
        } else if (!p.equals(p1)) {
            double pdx = Math.abs(p.f412x - p0.f412x);
            double pdy = Math.abs(p.f413y - p0.f413y);
            if (dx > dy) {
                dist = pdx;
            } else {
                dist = pdy;
            }
            if (dist == 0.0d && !p.equals(p0)) {
                dist = Math.max(pdx, pdy);
            }
        } else if (dx > dy) {
            dist = dx;
        } else {
            dist = dy;
        }
        Assert.isTrue(dist != 0.0d || p.equals(p0), "Bad distance calculation");
        return dist;
    }

    public static double nonRobustComputeEdgeDistance(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate p = coordinate;
        Coordinate p1 = coordinate2;
        Coordinate coordinate4 = coordinate3;
        double dx = p.f412x - p1.f412x;
        double dy = p.f413y - p1.f413y;
        double dist = Math.sqrt((dx * dx) + (dy * dy));
        Assert.isTrue(dist != 0.0d || p.equals(p1), "Invalid distance calculation");
        return dist;
    }

    public LineIntersector() {
        Coordinate coordinate;
        Coordinate coordinate2;
        new Coordinate();
        this.intPt[0] = coordinate;
        new Coordinate();
        this.intPt[1] = coordinate2;
        this.f401pa = this.intPt[0];
        this.f402pb = this.intPt[1];
        this.result = 0;
    }

    public void setMakePrecise(PrecisionModel precisionModel2) {
        PrecisionModel precisionModel3 = precisionModel2;
        this.precisionModel = precisionModel3;
    }

    public void setPrecisionModel(PrecisionModel precisionModel2) {
        PrecisionModel precisionModel3 = precisionModel2;
        this.precisionModel = precisionModel3;
    }

    public Coordinate getEndpoint(int segmentIndex, int ptIndex) {
        return this.inputLines[segmentIndex][ptIndex];
    }

    /* access modifiers changed from: protected */
    public boolean isCollinear() {
        return this.result == 2;
    }

    public void computeIntersection(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate p3 = coordinate3;
        Coordinate p4 = coordinate4;
        this.inputLines[0][0] = p1;
        this.inputLines[0][1] = p2;
        this.inputLines[1][0] = p3;
        this.inputLines[1][1] = p4;
        this.result = computeIntersect(p1, p2, p3, p4);
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append(WKTWriter.toLineString(this.inputLines[0][0], this.inputLines[0][1])).append(" - ").append(WKTWriter.toLineString(this.inputLines[1][0], this.inputLines[1][1])).append(getTopologySummary()).toString();
    }

    private String getTopologySummary() {
        StringBuilder sb;
        new StringBuilder();
        StringBuilder catBuilder = sb;
        if (isEndPoint()) {
            StringBuilder append = catBuilder.append(" endpoint");
        }
        if (this.isProper) {
            StringBuilder append2 = catBuilder.append(" proper");
        }
        if (isCollinear()) {
            StringBuilder append3 = catBuilder.append(" collinear");
        }
        return catBuilder.toString();
    }

    /* access modifiers changed from: protected */
    public boolean isEndPoint() {
        return hasIntersection() && !this.isProper;
    }

    public boolean hasIntersection() {
        return this.result != 0;
    }

    public int getIntersectionNum() {
        return this.result;
    }

    public Coordinate getIntersection(int intIndex) {
        return this.intPt[intIndex];
    }

    /* access modifiers changed from: protected */
    public void computeIntLineIndex() {
        if (this.intLineIndex == null) {
            this.intLineIndex = (int[][]) Array.newInstance(Integer.TYPE, new int[]{2, 2});
            computeIntLineIndex(0);
            computeIntLineIndex(1);
        }
    }

    public boolean isIntersection(Coordinate coordinate) {
        Coordinate pt = coordinate;
        for (int i = 0; i < this.result; i++) {
            if (this.intPt[i].equals2D(pt)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInteriorIntersection() {
        if (isInteriorIntersection(0)) {
            return true;
        }
        if (isInteriorIntersection(1)) {
            return true;
        }
        return false;
    }

    public boolean isInteriorIntersection(int i) {
        int inputLineIndex = i;
        for (int i2 = 0; i2 < this.result; i2++) {
            if (!this.intPt[i2].equals2D(this.inputLines[inputLineIndex][0]) && !this.intPt[i2].equals2D(this.inputLines[inputLineIndex][1])) {
                return true;
            }
        }
        return false;
    }

    public boolean isProper() {
        return hasIntersection() && this.isProper;
    }

    public Coordinate getIntersectionAlongSegment(int segmentIndex, int intIndex) {
        computeIntLineIndex();
        return this.intPt[this.intLineIndex[segmentIndex][intIndex]];
    }

    public int getIndexAlongSegment(int segmentIndex, int intIndex) {
        computeIntLineIndex();
        return this.intLineIndex[segmentIndex][intIndex];
    }

    /* access modifiers changed from: protected */
    public void computeIntLineIndex(int i) {
        int segmentIndex = i;
        if (getEdgeDistance(segmentIndex, 0) > getEdgeDistance(segmentIndex, 1)) {
            this.intLineIndex[segmentIndex][0] = 0;
            this.intLineIndex[segmentIndex][1] = 1;
            return;
        }
        this.intLineIndex[segmentIndex][0] = 1;
        this.intLineIndex[segmentIndex][1] = 0;
    }

    public double getEdgeDistance(int i, int intIndex) {
        int segmentIndex = i;
        return computeEdgeDistance(this.intPt[intIndex], this.inputLines[segmentIndex][0], this.inputLines[segmentIndex][1]);
    }
}
