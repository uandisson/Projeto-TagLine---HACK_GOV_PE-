package org.locationtech.jts.operation.buffer;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.PrecisionModel;

public class OffsetCurveBuilder {
    private BufferParameters bufParams;
    private double distance = 0.0d;
    private PrecisionModel precisionModel;

    public OffsetCurveBuilder(PrecisionModel precisionModel2, BufferParameters bufParams2) {
        this.precisionModel = precisionModel2;
        this.bufParams = bufParams2;
    }

    public BufferParameters getBufferParameters() {
        return this.bufParams;
    }

    public Coordinate[] getLineCurve(Coordinate[] coordinateArr, double d) {
        Coordinate[] inputPts = coordinateArr;
        double distance2 = d;
        this.distance = distance2;
        if (distance2 < 0.0d && !this.bufParams.isSingleSided()) {
            return null;
        }
        if (distance2 == 0.0d) {
            return null;
        }
        OffsetSegmentGenerator segGen = getSegGen(Math.abs(distance2));
        if (inputPts.length <= 1) {
            computePointCurve(inputPts[0], segGen);
        } else if (this.bufParams.isSingleSided()) {
            computeSingleSidedBufferCurve(inputPts, distance2 < 0.0d, segGen);
        } else {
            computeLineBufferCurve(inputPts, segGen);
        }
        return segGen.getCoordinates();
    }

    public Coordinate[] getRingCurve(Coordinate[] coordinateArr, int i, double d) {
        Coordinate[] inputPts = coordinateArr;
        int side = i;
        double distance2 = d;
        this.distance = distance2;
        if (inputPts.length <= 2) {
            return getLineCurve(inputPts, distance2);
        }
        if (distance2 == 0.0d) {
            return copyCoordinates(inputPts);
        }
        OffsetSegmentGenerator segGen = getSegGen(distance2);
        computeRingBufferCurve(inputPts, side, segGen);
        return segGen.getCoordinates();
    }

    public Coordinate[] getOffsetCurve(Coordinate[] coordinateArr, double d) {
        Coordinate[] inputPts = coordinateArr;
        double distance2 = d;
        this.distance = distance2;
        if (distance2 == 0.0d) {
            return null;
        }
        boolean isRightSide = distance2 < 0.0d;
        OffsetSegmentGenerator segGen = getSegGen(Math.abs(distance2));
        if (inputPts.length <= 1) {
            computePointCurve(inputPts[0], segGen);
        } else {
            computeOffsetCurve(inputPts, isRightSide, segGen);
        }
        Coordinate[] curvePts = segGen.getCoordinates();
        if (isRightSide) {
            CoordinateArrays.reverse(curvePts);
        }
        return curvePts;
    }

    private static Coordinate[] copyCoordinates(Coordinate[] coordinateArr) {
        Coordinate coordinate;
        Coordinate[] pts = coordinateArr;
        Coordinate[] copy = new Coordinate[pts.length];
        for (int i = 0; i < copy.length; i++) {
            new Coordinate(pts[i]);
            copy[i] = coordinate;
        }
        return copy;
    }

    private OffsetSegmentGenerator getSegGen(double distance2) {
        OffsetSegmentGenerator offsetSegmentGenerator;
        new OffsetSegmentGenerator(this.precisionModel, this.bufParams, distance2);
        return offsetSegmentGenerator;
    }

    private double simplifyTolerance(double bufDistance) {
        return bufDistance * this.bufParams.getSimplifyFactor();
    }

    private void computePointCurve(Coordinate coordinate, OffsetSegmentGenerator offsetSegmentGenerator) {
        Coordinate pt = coordinate;
        OffsetSegmentGenerator segGen = offsetSegmentGenerator;
        switch (this.bufParams.getEndCapStyle()) {
            case 1:
                segGen.createCircle(pt);
                return;
            case 3:
                segGen.createSquare(pt);
                return;
            default:
                return;
        }
    }

    private void computeLineBufferCurve(Coordinate[] coordinateArr, OffsetSegmentGenerator offsetSegmentGenerator) {
        Coordinate[] inputPts = coordinateArr;
        OffsetSegmentGenerator segGen = offsetSegmentGenerator;
        double distTol = simplifyTolerance(this.distance);
        Coordinate[] simp1 = BufferInputLineSimplifier.simplify(inputPts, distTol);
        int n1 = simp1.length - 1;
        segGen.initSideSegments(simp1[0], simp1[1], 1);
        for (int i = 2; i <= n1; i++) {
            segGen.addNextSegment(simp1[i], true);
        }
        segGen.addLastSegment();
        segGen.addLineEndCap(simp1[n1 - 1], simp1[n1]);
        Coordinate[] simp2 = BufferInputLineSimplifier.simplify(inputPts, -distTol);
        int n2 = simp2.length - 1;
        segGen.initSideSegments(simp2[n2], simp2[n2 - 1], 1);
        for (int i2 = n2 - 2; i2 >= 0; i2--) {
            segGen.addNextSegment(simp2[i2], true);
        }
        segGen.addLastSegment();
        segGen.addLineEndCap(simp2[1], simp2[0]);
        segGen.closeRing();
    }

    private void computeSingleSidedBufferCurve(Coordinate[] coordinateArr, boolean isRightSide, OffsetSegmentGenerator offsetSegmentGenerator) {
        Coordinate[] inputPts = coordinateArr;
        OffsetSegmentGenerator segGen = offsetSegmentGenerator;
        double distTol = simplifyTolerance(this.distance);
        if (isRightSide) {
            segGen.addSegments(inputPts, true);
            Coordinate[] simp2 = BufferInputLineSimplifier.simplify(inputPts, -distTol);
            int n2 = simp2.length - 1;
            segGen.initSideSegments(simp2[n2], simp2[n2 - 1], 1);
            segGen.addFirstSegment();
            for (int i = n2 - 2; i >= 0; i--) {
                segGen.addNextSegment(simp2[i], true);
            }
        } else {
            segGen.addSegments(inputPts, false);
            Coordinate[] simp1 = BufferInputLineSimplifier.simplify(inputPts, distTol);
            int n1 = simp1.length - 1;
            segGen.initSideSegments(simp1[0], simp1[1], 1);
            segGen.addFirstSegment();
            for (int i2 = 2; i2 <= n1; i2++) {
                segGen.addNextSegment(simp1[i2], true);
            }
        }
        segGen.addLastSegment();
        segGen.closeRing();
    }

    private void computeOffsetCurve(Coordinate[] coordinateArr, boolean isRightSide, OffsetSegmentGenerator offsetSegmentGenerator) {
        Coordinate[] inputPts = coordinateArr;
        OffsetSegmentGenerator segGen = offsetSegmentGenerator;
        double distTol = simplifyTolerance(this.distance);
        if (isRightSide) {
            Coordinate[] simp2 = BufferInputLineSimplifier.simplify(inputPts, -distTol);
            int n2 = simp2.length - 1;
            segGen.initSideSegments(simp2[n2], simp2[n2 - 1], 1);
            segGen.addFirstSegment();
            for (int i = n2 - 2; i >= 0; i--) {
                segGen.addNextSegment(simp2[i], true);
            }
        } else {
            Coordinate[] simp1 = BufferInputLineSimplifier.simplify(inputPts, distTol);
            int n1 = simp1.length - 1;
            segGen.initSideSegments(simp1[0], simp1[1], 1);
            segGen.addFirstSegment();
            for (int i2 = 2; i2 <= n1; i2++) {
                segGen.addNextSegment(simp1[i2], true);
            }
        }
        segGen.addLastSegment();
    }

    private void computeRingBufferCurve(Coordinate[] coordinateArr, int i, OffsetSegmentGenerator offsetSegmentGenerator) {
        Coordinate[] inputPts = coordinateArr;
        int side = i;
        OffsetSegmentGenerator segGen = offsetSegmentGenerator;
        double distTol = simplifyTolerance(this.distance);
        if (side == 2) {
            distTol = -distTol;
        }
        Coordinate[] simp = BufferInputLineSimplifier.simplify(inputPts, distTol);
        int n = simp.length - 1;
        segGen.initSideSegments(simp[n - 1], simp[0], side);
        int i2 = 1;
        while (i2 <= n) {
            segGen.addNextSegment(simp[i2], i2 != 1);
            i2++;
        }
        segGen.closeRing();
    }
}
