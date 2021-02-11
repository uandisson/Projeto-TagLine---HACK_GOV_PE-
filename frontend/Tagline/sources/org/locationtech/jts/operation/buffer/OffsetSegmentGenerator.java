package org.locationtech.jts.operation.buffer;

import org.locationtech.jts.algorithm.Angle;
import org.locationtech.jts.algorithm.CGAlgorithms;
import org.locationtech.jts.algorithm.HCoordinate;
import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.NotRepresentableException;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.PrecisionModel;

class OffsetSegmentGenerator {
    private static final double CURVE_VERTEX_SNAP_DISTANCE_FACTOR = 1.0E-6d;
    private static final double INSIDE_TURN_VERTEX_SNAP_DISTANCE_FACTOR = 0.001d;
    private static final int MAX_CLOSING_SEG_LEN_FACTOR = 80;
    private static final double OFFSET_SEGMENT_SEPARATION_FACTOR = 0.001d;
    private BufferParameters bufParams;
    private int closingSegLengthFactor = 1;
    private double distance = 0.0d;
    private double filletAngleQuantum;
    private boolean hasNarrowConcaveAngle;

    /* renamed from: li */
    private LineIntersector f480li;
    private double maxCurveSegmentError = 0.0d;
    private LineSegment offset0;
    private LineSegment offset1;
    private PrecisionModel precisionModel;

    /* renamed from: s0 */
    private Coordinate f481s0;

    /* renamed from: s1 */
    private Coordinate f482s1;

    /* renamed from: s2 */
    private Coordinate f483s2;
    private LineSegment seg0;
    private LineSegment seg1;
    private OffsetSegmentString segList;
    private int side;

    public OffsetSegmentGenerator(PrecisionModel precisionModel2, BufferParameters bufferParameters, double d) {
        LineSegment lineSegment;
        LineSegment lineSegment2;
        LineSegment lineSegment3;
        LineSegment lineSegment4;
        LineIntersector lineIntersector;
        BufferParameters bufParams2 = bufferParameters;
        double distance2 = d;
        new LineSegment();
        this.seg0 = lineSegment;
        new LineSegment();
        this.seg1 = lineSegment2;
        new LineSegment();
        this.offset0 = lineSegment3;
        new LineSegment();
        this.offset1 = lineSegment4;
        this.side = 0;
        this.hasNarrowConcaveAngle = false;
        this.precisionModel = precisionModel2;
        this.bufParams = bufParams2;
        new RobustLineIntersector();
        this.f480li = lineIntersector;
        this.filletAngleQuantum = 1.5707963267948966d / ((double) bufParams2.getQuadrantSegments());
        if (bufParams2.getQuadrantSegments() >= 8 && bufParams2.getJoinStyle() == 1) {
            this.closingSegLengthFactor = 80;
        }
        init(distance2);
    }

    public boolean hasNarrowConcaveAngle() {
        return this.hasNarrowConcaveAngle;
    }

    private void init(double d) {
        OffsetSegmentString offsetSegmentString;
        double distance2 = d;
        this.distance = distance2;
        this.maxCurveSegmentError = distance2 * (1.0d - Math.cos(this.filletAngleQuantum / 2.0d));
        new OffsetSegmentString();
        this.segList = offsetSegmentString;
        this.segList.setPrecisionModel(this.precisionModel);
        this.segList.setMinimumVertexDistance(distance2 * CURVE_VERTEX_SNAP_DISTANCE_FACTOR);
    }

    public void initSideSegments(Coordinate coordinate, Coordinate coordinate2, int i) {
        Coordinate s1 = coordinate;
        Coordinate s2 = coordinate2;
        int side2 = i;
        this.f482s1 = s1;
        this.f483s2 = s2;
        this.side = side2;
        this.seg1.setCoordinates(s1, s2);
        computeOffsetSegment(this.seg1, side2, this.distance, this.offset1);
    }

    public Coordinate[] getCoordinates() {
        return this.segList.getCoordinates();
    }

    public void closeRing() {
        this.segList.closeRing();
    }

    public void addSegments(Coordinate[] pt, boolean isForward) {
        this.segList.addPts(pt, isForward);
    }

    public void addFirstSegment() {
        this.segList.addPt(this.offset1.f422p0);
    }

    public void addLastSegment() {
        this.segList.addPt(this.offset1.f423p1);
    }

    public void addNextSegment(Coordinate p, boolean z) {
        boolean addStartPoint = z;
        this.f481s0 = this.f482s1;
        this.f482s1 = this.f483s2;
        this.f483s2 = p;
        this.seg0.setCoordinates(this.f481s0, this.f482s1);
        computeOffsetSegment(this.seg0, this.side, this.distance, this.offset0);
        this.seg1.setCoordinates(this.f482s1, this.f483s2);
        computeOffsetSegment(this.seg1, this.side, this.distance, this.offset1);
        if (!this.f482s1.equals(this.f483s2)) {
            int orientation = CGAlgorithms.computeOrientation(this.f481s0, this.f482s1, this.f483s2);
            boolean outsideTurn = (orientation == -1 && this.side == 1) || (orientation == 1 && this.side == 2);
            if (orientation == 0) {
                addCollinear(addStartPoint);
            } else if (outsideTurn) {
                addOutsideTurn(orientation, addStartPoint);
            } else {
                addInsideTurn(orientation, addStartPoint);
            }
        }
    }

    private void addCollinear(boolean z) {
        boolean addStartPoint = z;
        this.f480li.computeIntersection(this.f481s0, this.f482s1, this.f482s1, this.f483s2);
        if (this.f480li.getIntersectionNum() < 2) {
            return;
        }
        if (this.bufParams.getJoinStyle() == 3 || this.bufParams.getJoinStyle() == 2) {
            if (addStartPoint) {
                this.segList.addPt(this.offset0.f423p1);
            }
            this.segList.addPt(this.offset1.f422p0);
            return;
        }
        addFillet(this.f482s1, this.offset0.f423p1, this.offset1.f422p0, -1, this.distance);
    }

    private void addOutsideTurn(int i, boolean z) {
        int orientation = i;
        boolean addStartPoint = z;
        if (this.offset0.f423p1.distance(this.offset1.f422p0) < this.distance * 0.001d) {
            this.segList.addPt(this.offset0.f423p1);
        } else if (this.bufParams.getJoinStyle() == 2) {
            addMitreJoin(this.f482s1, this.offset0, this.offset1, this.distance);
        } else if (this.bufParams.getJoinStyle() == 3) {
            addBevelJoin(this.offset0, this.offset1);
        } else {
            if (addStartPoint) {
                this.segList.addPt(this.offset0.f423p1);
            }
            addFillet(this.f482s1, this.offset0.f423p1, this.offset1.f422p0, orientation, this.distance);
            this.segList.addPt(this.offset1.f422p0);
        }
    }

    private void addInsideTurn(int i, boolean z) {
        Coordinate mid0;
        Coordinate mid1;
        int i2 = i;
        boolean z2 = z;
        this.f480li.computeIntersection(this.offset0.f422p0, this.offset0.f423p1, this.offset1.f422p0, this.offset1.f423p1);
        if (this.f480li.hasIntersection()) {
            this.segList.addPt(this.f480li.getIntersection(0));
            return;
        }
        this.hasNarrowConcaveAngle = true;
        if (this.offset0.f423p1.distance(this.offset1.f422p0) < this.distance * 0.001d) {
            this.segList.addPt(this.offset0.f423p1);
            return;
        }
        this.segList.addPt(this.offset0.f423p1);
        if (this.closingSegLengthFactor > 0) {
            new Coordinate(((((double) this.closingSegLengthFactor) * this.offset0.f423p1.f412x) + this.f482s1.f412x) / ((double) (this.closingSegLengthFactor + 1)), ((((double) this.closingSegLengthFactor) * this.offset0.f423p1.f413y) + this.f482s1.f413y) / ((double) (this.closingSegLengthFactor + 1)));
            this.segList.addPt(mid0);
            new Coordinate(((((double) this.closingSegLengthFactor) * this.offset1.f422p0.f412x) + this.f482s1.f412x) / ((double) (this.closingSegLengthFactor + 1)), ((((double) this.closingSegLengthFactor) * this.offset1.f422p0.f413y) + this.f482s1.f413y) / ((double) (this.closingSegLengthFactor + 1)));
            this.segList.addPt(mid1);
        } else {
            this.segList.addPt(this.f482s1);
        }
        this.segList.addPt(this.offset1.f422p0);
    }

    private void computeOffsetSegment(LineSegment lineSegment, int side2, double d, LineSegment lineSegment2) {
        LineSegment seg = lineSegment;
        double distance2 = d;
        LineSegment offset = lineSegment2;
        int sideSign = side2 == 1 ? 1 : -1;
        double dx = seg.f423p1.f412x - seg.f422p0.f412x;
        double dy = seg.f423p1.f413y - seg.f422p0.f413y;
        double len = Math.sqrt((dx * dx) + (dy * dy));
        double ux = ((((double) sideSign) * distance2) * dx) / len;
        double uy = ((((double) sideSign) * distance2) * dy) / len;
        offset.f422p0.f412x = seg.f422p0.f412x - uy;
        offset.f422p0.f413y = seg.f422p0.f413y + ux;
        offset.f423p1.f412x = seg.f423p1.f412x - uy;
        offset.f423p1.f413y = seg.f423p1.f413y + ux;
    }

    public void addLineEndCap(Coordinate coordinate, Coordinate coordinate2) {
        LineSegment lineSegment;
        LineSegment lineSegment2;
        LineSegment lineSegment3;
        Coordinate coordinate3;
        Coordinate squareCapLOffset;
        Coordinate squareCapROffset;
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        new LineSegment(p0, p1);
        LineSegment seg = lineSegment;
        new LineSegment();
        LineSegment offsetL = lineSegment2;
        computeOffsetSegment(seg, 1, this.distance, offsetL);
        new LineSegment();
        LineSegment offsetR = lineSegment3;
        computeOffsetSegment(seg, 2, this.distance, offsetR);
        double angle = Math.atan2(p1.f413y - p0.f413y, p1.f412x - p0.f412x);
        switch (this.bufParams.getEndCapStyle()) {
            case 1:
                this.segList.addPt(offsetL.f423p1);
                addFillet(p1, angle + 1.5707963267948966d, angle - 1.5707963267948966d, -1, this.distance);
                this.segList.addPt(offsetR.f423p1);
                return;
            case 2:
                this.segList.addPt(offsetL.f423p1);
                this.segList.addPt(offsetR.f423p1);
                return;
            case 3:
                new Coordinate();
                Coordinate squareCapSideOffset = coordinate3;
                squareCapSideOffset.f412x = Math.abs(this.distance) * Math.cos(angle);
                squareCapSideOffset.f413y = Math.abs(this.distance) * Math.sin(angle);
                new Coordinate(offsetL.f423p1.f412x + squareCapSideOffset.f412x, offsetL.f423p1.f413y + squareCapSideOffset.f413y);
                new Coordinate(offsetR.f423p1.f412x + squareCapSideOffset.f412x, offsetR.f423p1.f413y + squareCapSideOffset.f413y);
                this.segList.addPt(squareCapLOffset);
                this.segList.addPt(squareCapROffset);
                return;
            default:
                return;
        }
    }

    private void addMitreJoin(Coordinate coordinate, LineSegment lineSegment, LineSegment lineSegment2, double d) {
        Coordinate coordinate2;
        Coordinate intPt;
        double mitreRatio;
        Coordinate p = coordinate;
        LineSegment offset02 = lineSegment;
        LineSegment offset12 = lineSegment2;
        double distance2 = d;
        boolean isMitreWithinLimit = true;
        try {
            intPt = HCoordinate.intersection(offset02.f422p0, offset02.f423p1, offset12.f422p0, offset12.f423p1);
            if (distance2 <= 0.0d) {
                mitreRatio = 1.0d;
            } else {
                mitreRatio = intPt.distance(p) / Math.abs(distance2);
            }
            if (mitreRatio > this.bufParams.getMitreLimit()) {
                isMitreWithinLimit = false;
            }
        } catch (NotRepresentableException e) {
            NotRepresentableException notRepresentableException = e;
            new Coordinate(0.0d, 0.0d);
            intPt = coordinate2;
            isMitreWithinLimit = false;
        }
        if (isMitreWithinLimit) {
            this.segList.addPt(intPt);
        } else {
            addLimitedMitreJoin(offset02, offset12, distance2, this.bufParams.getMitreLimit());
        }
    }

    private void addLimitedMitreJoin(LineSegment lineSegment, LineSegment lineSegment2, double d, double mitreLimit) {
        Coordinate bevelMidPt;
        LineSegment lineSegment3;
        LineSegment lineSegment4 = lineSegment;
        LineSegment lineSegment5 = lineSegment2;
        double distance2 = d;
        Coordinate basePt = this.seg0.f423p1;
        double ang0 = Angle.angle(basePt, this.seg0.f422p0);
        double angle = Angle.angle(basePt, this.seg1.f423p1);
        double angDiffHalf = Angle.angleBetweenOriented(this.seg0.f422p0, basePt, this.seg1.f423p1) / 2.0d;
        double mitreMidAng = Angle.normalize(Angle.normalize(ang0 + angDiffHalf) + 3.141592653589793d);
        double mitreDist = mitreLimit * distance2;
        double bevelHalfLen = distance2 - (mitreDist * Math.abs(Math.sin(angDiffHalf)));
        new Coordinate(basePt.f412x + (mitreDist * Math.cos(mitreMidAng)), basePt.f413y + (mitreDist * Math.sin(mitreMidAng)));
        new LineSegment(basePt, bevelMidPt);
        LineSegment mitreMidLine = lineSegment3;
        Coordinate bevelEndLeft = mitreMidLine.pointAlongOffset(1.0d, bevelHalfLen);
        Coordinate bevelEndRight = mitreMidLine.pointAlongOffset(1.0d, -bevelHalfLen);
        if (this.side == 1) {
            this.segList.addPt(bevelEndLeft);
            this.segList.addPt(bevelEndRight);
            return;
        }
        this.segList.addPt(bevelEndRight);
        this.segList.addPt(bevelEndLeft);
    }

    private void addBevelJoin(LineSegment offset02, LineSegment offset12) {
        this.segList.addPt(offset02.f423p1);
        this.segList.addPt(offset12.f422p0);
    }

    private void addFillet(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, int i, double d) {
        Coordinate p = coordinate;
        Coordinate p0 = coordinate2;
        Coordinate p1 = coordinate3;
        int direction = i;
        double radius = d;
        double startAngle = Math.atan2(p0.f413y - p.f413y, p0.f412x - p.f412x);
        double endAngle = Math.atan2(p1.f413y - p.f413y, p1.f412x - p.f412x);
        if (direction == -1) {
            if (startAngle <= endAngle) {
                startAngle += 6.283185307179586d;
            }
        } else if (startAngle >= endAngle) {
            startAngle -= 6.283185307179586d;
        }
        this.segList.addPt(p0);
        addFillet(p, startAngle, endAngle, direction, radius);
        this.segList.addPt(p1);
    }

    private void addFillet(Coordinate coordinate, double d, double d2, int direction, double d3) {
        Coordinate coordinate2;
        Coordinate p = coordinate;
        double startAngle = d;
        double endAngle = d2;
        double radius = d3;
        int directionFactor = direction == -1 ? -1 : 1;
        double totalAngle = Math.abs(startAngle - endAngle);
        int nSegs = (int) ((totalAngle / this.filletAngleQuantum) + 0.5d);
        if (nSegs >= 1) {
            double currAngleInc = totalAngle / ((double) nSegs);
            new Coordinate();
            Coordinate pt = coordinate2;
            for (double currAngle = 0.0d; currAngle < totalAngle; currAngle += currAngleInc) {
                double angle = startAngle + (((double) directionFactor) * currAngle);
                pt.f412x = p.f412x + (radius * Math.cos(angle));
                pt.f413y = p.f413y + (radius * Math.sin(angle));
                this.segList.addPt(pt);
            }
        }
    }

    public void createCircle(Coordinate coordinate) {
        Coordinate pt;
        Coordinate p = coordinate;
        new Coordinate(p.f412x + this.distance, p.f413y);
        this.segList.addPt(pt);
        addFillet(p, 0.0d, 6.283185307179586d, -1, this.distance);
        this.segList.closeRing();
    }

    public void createSquare(Coordinate coordinate) {
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        Coordinate coordinate5;
        Coordinate p = coordinate;
        new Coordinate(p.f412x + this.distance, p.f413y + this.distance);
        this.segList.addPt(coordinate2);
        new Coordinate(p.f412x + this.distance, p.f413y - this.distance);
        this.segList.addPt(coordinate3);
        new Coordinate(p.f412x - this.distance, p.f413y - this.distance);
        this.segList.addPt(coordinate4);
        new Coordinate(p.f412x - this.distance, p.f413y + this.distance);
        this.segList.addPt(coordinate5);
        this.segList.closeRing();
    }
}
