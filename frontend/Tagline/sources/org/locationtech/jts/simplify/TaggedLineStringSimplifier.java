package org.locationtech.jts.simplify;

import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.algorithm.RobustLineIntersector;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineSegment;

public class TaggedLineStringSimplifier {
    private double distanceTolerance = 0.0d;
    private LineSegmentIndex inputIndex;

    /* renamed from: li */
    private LineIntersector f508li;
    private TaggedLineString line;
    private Coordinate[] linePts;
    private LineSegmentIndex outputIndex;

    public TaggedLineStringSimplifier(LineSegmentIndex inputIndex2, LineSegmentIndex outputIndex2) {
        LineIntersector lineIntersector;
        LineSegmentIndex lineSegmentIndex;
        LineSegmentIndex lineSegmentIndex2;
        new RobustLineIntersector();
        this.f508li = lineIntersector;
        new LineSegmentIndex();
        this.inputIndex = lineSegmentIndex;
        new LineSegmentIndex();
        this.outputIndex = lineSegmentIndex2;
        this.inputIndex = inputIndex2;
        this.outputIndex = outputIndex2;
    }

    public void setDistanceTolerance(double distanceTolerance2) {
        double d = distanceTolerance2;
        this.distanceTolerance = d;
    }

    /* access modifiers changed from: package-private */
    public void simplify(TaggedLineString taggedLineString) {
        TaggedLineString line2 = taggedLineString;
        this.line = line2;
        this.linePts = line2.getParentCoordinates();
        simplifySection(0, this.linePts.length - 1, 0);
    }

    private void simplifySection(int i, int i2, int depth) {
        LineSegment lineSegment;
        int i3 = i;
        int j = i2;
        int depth2 = depth + 1;
        int[] sectionIndex = new int[2];
        if (i3 + 1 == j) {
            this.line.addToResult(this.line.getSegment(i3));
            return;
        }
        boolean isValidToSimplify = true;
        if (this.line.getResultSize() < this.line.getMinimumSize() && depth2 + 1 < this.line.getMinimumSize()) {
            isValidToSimplify = false;
        }
        double[] distance = new double[1];
        int furthestPtIndex = findFurthestPoint(this.linePts, i3, j, distance);
        if (distance[0] > this.distanceTolerance) {
            isValidToSimplify = false;
        }
        new LineSegment();
        LineSegment candidateSeg = lineSegment;
        candidateSeg.f422p0 = this.linePts[i3];
        candidateSeg.f423p1 = this.linePts[j];
        sectionIndex[0] = i3;
        sectionIndex[1] = j;
        if (hasBadIntersection(this.line, sectionIndex, candidateSeg)) {
            isValidToSimplify = false;
        }
        if (isValidToSimplify) {
            this.line.addToResult(flatten(i3, j));
            return;
        }
        simplifySection(i3, furthestPtIndex, depth2);
        simplifySection(furthestPtIndex, j, depth2);
    }

    private int findFurthestPoint(Coordinate[] coordinateArr, int i, int i2, double[] dArr) {
        LineSegment lineSegment;
        Coordinate[] pts = coordinateArr;
        int i3 = i;
        int j = i2;
        double[] maxDistance = dArr;
        new LineSegment();
        LineSegment seg = lineSegment;
        seg.f422p0 = pts[i3];
        seg.f423p1 = pts[j];
        double maxDist = -1.0d;
        int maxIndex = i3;
        for (int k = i3 + 1; k < j; k++) {
            double distance = seg.distance(pts[k]);
            if (distance > maxDist) {
                maxDist = distance;
                maxIndex = k;
            }
        }
        maxDistance[0] = maxDist;
        return maxIndex;
    }

    private LineSegment flatten(int i, int i2) {
        LineSegment lineSegment;
        int start = i;
        int end = i2;
        new LineSegment(this.linePts[start], this.linePts[end]);
        LineSegment newSeg = lineSegment;
        remove(this.line, start, end);
        this.outputIndex.add(newSeg);
        return newSeg;
    }

    private boolean hasBadIntersection(TaggedLineString taggedLineString, int[] iArr, LineSegment lineSegment) {
        TaggedLineString parentLine = taggedLineString;
        int[] sectionIndex = iArr;
        LineSegment candidateSeg = lineSegment;
        if (hasBadOutputIntersection(candidateSeg)) {
            return true;
        }
        if (hasBadInputIntersection(parentLine, sectionIndex, candidateSeg)) {
            return true;
        }
        return false;
    }

    private boolean hasBadOutputIntersection(LineSegment lineSegment) {
        LineSegment candidateSeg = lineSegment;
        for (LineSegment querySeg : this.outputIndex.query(candidateSeg)) {
            if (hasInteriorIntersection(querySeg, candidateSeg)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasBadInputIntersection(TaggedLineString taggedLineString, int[] iArr, LineSegment lineSegment) {
        TaggedLineString parentLine = taggedLineString;
        int[] sectionIndex = iArr;
        LineSegment candidateSeg = lineSegment;
        for (TaggedLineSegment querySeg : this.inputIndex.query(candidateSeg)) {
            if (hasInteriorIntersection(querySeg, candidateSeg) && !isInLineSection(parentLine, sectionIndex, querySeg)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInLineSection(TaggedLineString line2, int[] iArr, TaggedLineSegment taggedLineSegment) {
        int[] sectionIndex = iArr;
        TaggedLineSegment seg = taggedLineSegment;
        if (seg.getParent() != line2.getParent()) {
            return false;
        }
        int segIndex = seg.getIndex();
        if (segIndex < sectionIndex[0] || segIndex >= sectionIndex[1]) {
            return false;
        }
        return true;
    }

    private boolean hasInteriorIntersection(LineSegment lineSegment, LineSegment lineSegment2) {
        LineSegment seg0 = lineSegment;
        LineSegment seg1 = lineSegment2;
        this.f508li.computeIntersection(seg0.f422p0, seg0.f423p1, seg1.f422p0, seg1.f423p1);
        return this.f508li.isInteriorIntersection();
    }

    private void remove(TaggedLineString taggedLineString, int start, int i) {
        TaggedLineString line2 = taggedLineString;
        int end = i;
        for (int i2 = start; i2 < end; i2++) {
            this.inputIndex.remove(line2.getSegment(i2));
        }
    }
}
