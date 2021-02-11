package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;

public class NonRobustLineIntersector extends LineIntersector {
    public static boolean isSameSignAndNonZero(double d, double d2) {
        double a = d;
        double b = d2;
        if (a == 0.0d || b == 0.0d) {
            return false;
        }
        return (a < 0.0d && b < 0.0d) || (a > 0.0d && b > 0.0d);
    }

    public NonRobustLineIntersector() {
    }

    public void computeIntersection(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate p = coordinate;
        Coordinate p1 = coordinate2;
        Coordinate p2 = coordinate3;
        this.isProper = false;
        double a1 = p2.f413y - p1.f413y;
        double b1 = p1.f412x - p2.f412x;
        if ((a1 * p.f412x) + (b1 * p.f413y) + ((p2.f412x * p1.f413y) - (p1.f412x * p2.f413y)) != 0.0d) {
            this.result = 0;
            return;
        }
        double dist = rParameter(p1, p2, p);
        if (dist < 0.0d || dist > 1.0d) {
            this.result = 0;
            return;
        }
        this.isProper = true;
        if (p.equals(p1) || p.equals(p2)) {
            this.isProper = false;
        }
        this.result = 1;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x022d, code lost:
        if (r3.f401pa.equals(r7) != false) goto L_0x022f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int computeIntersect(org.locationtech.jts.geom.Coordinate r41, org.locationtech.jts.geom.Coordinate r42, org.locationtech.jts.geom.Coordinate r43, org.locationtech.jts.geom.Coordinate r44) {
        /*
            r40 = this;
            r3 = r40
            r4 = r41
            r5 = r42
            r6 = r43
            r7 = r44
            r34 = r3
            r35 = 0
            r0 = r35
            r1 = r34
            r1.isProper = r0
            r34 = r5
            r0 = r34
            double r0 = r0.f413y
            r34 = r0
            r36 = r4
            r0 = r36
            double r0 = r0.f413y
            r36 = r0
            double r34 = r34 - r36
            r8 = r34
            r34 = r4
            r0 = r34
            double r0 = r0.f412x
            r34 = r0
            r36 = r5
            r0 = r36
            double r0 = r0.f412x
            r36 = r0
            double r34 = r34 - r36
            r10 = r34
            r34 = r5
            r0 = r34
            double r0 = r0.f412x
            r34 = r0
            r36 = r4
            r0 = r36
            double r0 = r0.f413y
            r36 = r0
            double r34 = r34 * r36
            r36 = r4
            r0 = r36
            double r0 = r0.f412x
            r36 = r0
            r38 = r5
            r0 = r38
            double r0 = r0.f413y
            r38 = r0
            double r36 = r36 * r38
            double r34 = r34 - r36
            r12 = r34
            r34 = r8
            r36 = r6
            r0 = r36
            double r0 = r0.f412x
            r36 = r0
            double r34 = r34 * r36
            r36 = r10
            r38 = r6
            r0 = r38
            double r0 = r0.f413y
            r38 = r0
            double r36 = r36 * r38
            double r34 = r34 + r36
            r36 = r12
            double r34 = r34 + r36
            r24 = r34
            r34 = r8
            r36 = r7
            r0 = r36
            double r0 = r0.f412x
            r36 = r0
            double r34 = r34 * r36
            r36 = r10
            r38 = r7
            r0 = r38
            double r0 = r0.f413y
            r38 = r0
            double r36 = r36 * r38
            double r34 = r34 + r36
            r36 = r12
            double r34 = r34 + r36
            r26 = r34
            r34 = r24
            r36 = 0
            int r34 = (r34 > r36 ? 1 : (r34 == r36 ? 0 : -1))
            if (r34 == 0) goto L_0x00c3
            r34 = r26
            r36 = 0
            int r34 = (r34 > r36 ? 1 : (r34 == r36 ? 0 : -1))
            if (r34 == 0) goto L_0x00c3
            r34 = r24
            r36 = r26
            boolean r34 = isSameSignAndNonZero(r34, r36)
            if (r34 == 0) goto L_0x00c3
            r34 = 0
            r3 = r34
        L_0x00c2:
            return r3
        L_0x00c3:
            r34 = r7
            r0 = r34
            double r0 = r0.f413y
            r34 = r0
            r36 = r6
            r0 = r36
            double r0 = r0.f413y
            r36 = r0
            double r34 = r34 - r36
            r14 = r34
            r34 = r6
            r0 = r34
            double r0 = r0.f412x
            r34 = r0
            r36 = r7
            r0 = r36
            double r0 = r0.f412x
            r36 = r0
            double r34 = r34 - r36
            r16 = r34
            r34 = r7
            r0 = r34
            double r0 = r0.f412x
            r34 = r0
            r36 = r6
            r0 = r36
            double r0 = r0.f413y
            r36 = r0
            double r34 = r34 * r36
            r36 = r6
            r0 = r36
            double r0 = r0.f412x
            r36 = r0
            r38 = r7
            r0 = r38
            double r0 = r0.f413y
            r38 = r0
            double r36 = r36 * r38
            double r34 = r34 - r36
            r18 = r34
            r34 = r14
            r36 = r4
            r0 = r36
            double r0 = r0.f412x
            r36 = r0
            double r34 = r34 * r36
            r36 = r16
            r38 = r4
            r0 = r38
            double r0 = r0.f413y
            r38 = r0
            double r36 = r36 * r38
            double r34 = r34 + r36
            r36 = r18
            double r34 = r34 + r36
            r20 = r34
            r34 = r14
            r36 = r5
            r0 = r36
            double r0 = r0.f412x
            r36 = r0
            double r34 = r34 * r36
            r36 = r16
            r38 = r5
            r0 = r38
            double r0 = r0.f413y
            r38 = r0
            double r36 = r36 * r38
            double r34 = r34 + r36
            r36 = r18
            double r34 = r34 + r36
            r22 = r34
            r34 = r20
            r36 = 0
            int r34 = (r34 > r36 ? 1 : (r34 == r36 ? 0 : -1))
            if (r34 == 0) goto L_0x0173
            r34 = r22
            r36 = 0
            int r34 = (r34 > r36 ? 1 : (r34 == r36 ? 0 : -1))
            if (r34 == 0) goto L_0x0173
            r34 = r20
            r36 = r22
            boolean r34 = isSameSignAndNonZero(r34, r36)
            if (r34 == 0) goto L_0x0173
            r34 = 0
            r3 = r34
            goto L_0x00c2
        L_0x0173:
            r34 = r8
            r36 = r16
            double r34 = r34 * r36
            r36 = r14
            r38 = r10
            double r36 = r36 * r38
            double r34 = r34 - r36
            r28 = r34
            r34 = r28
            r36 = 0
            int r34 = (r34 > r36 ? 1 : (r34 == r36 ? 0 : -1))
            if (r34 != 0) goto L_0x019d
            r34 = r3
            r35 = r4
            r36 = r5
            r37 = r6
            r38 = r7
            int r34 = r34.computeCollinearIntersection(r35, r36, r37, r38)
            r3 = r34
            goto L_0x00c2
        L_0x019d:
            r34 = r10
            r36 = r18
            double r34 = r34 * r36
            r36 = r16
            r38 = r12
            double r36 = r36 * r38
            double r34 = r34 - r36
            r30 = r34
            r34 = r3
            r0 = r34
            org.locationtech.jts.geom.Coordinate r0 = r0.f401pa
            r34 = r0
            r35 = r30
            r37 = r28
            double r35 = r35 / r37
            r0 = r35
            r2 = r34
            r2.f412x = r0
            r34 = r14
            r36 = r12
            double r34 = r34 * r36
            r36 = r8
            r38 = r18
            double r36 = r36 * r38
            double r34 = r34 - r36
            r32 = r34
            r34 = r3
            r0 = r34
            org.locationtech.jts.geom.Coordinate r0 = r0.f401pa
            r34 = r0
            r35 = r32
            r37 = r28
            double r35 = r35 / r37
            r0 = r35
            r2 = r34
            r2.f413y = r0
            r34 = r3
            r35 = 1
            r0 = r35
            r1 = r34
            r1.isProper = r0
            r34 = r3
            r0 = r34
            org.locationtech.jts.geom.Coordinate r0 = r0.f401pa
            r34 = r0
            r35 = r4
            boolean r34 = r34.equals(r35)
            if (r34 != 0) goto L_0x022f
            r34 = r3
            r0 = r34
            org.locationtech.jts.geom.Coordinate r0 = r0.f401pa
            r34 = r0
            r35 = r5
            boolean r34 = r34.equals(r35)
            if (r34 != 0) goto L_0x022f
            r34 = r3
            r0 = r34
            org.locationtech.jts.geom.Coordinate r0 = r0.f401pa
            r34 = r0
            r35 = r6
            boolean r34 = r34.equals(r35)
            if (r34 != 0) goto L_0x022f
            r34 = r3
            r0 = r34
            org.locationtech.jts.geom.Coordinate r0 = r0.f401pa
            r34 = r0
            r35 = r7
            boolean r34 = r34.equals(r35)
            if (r34 == 0) goto L_0x0239
        L_0x022f:
            r34 = r3
            r35 = 0
            r0 = r35
            r1 = r34
            r1.isProper = r0
        L_0x0239:
            r34 = r3
            r0 = r34
            org.locationtech.jts.geom.PrecisionModel r0 = r0.precisionModel
            r34 = r0
            if (r34 == 0) goto L_0x0256
            r34 = r3
            r0 = r34
            org.locationtech.jts.geom.PrecisionModel r0 = r0.precisionModel
            r34 = r0
            r35 = r3
            r0 = r35
            org.locationtech.jts.geom.Coordinate r0 = r0.f401pa
            r35 = r0
            r34.makePrecise((org.locationtech.jts.geom.Coordinate) r35)
        L_0x0256:
            r34 = 1
            r3 = r34
            goto L_0x00c2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.algorithm.NonRobustLineIntersector.computeIntersect(org.locationtech.jts.geom.Coordinate, org.locationtech.jts.geom.Coordinate, org.locationtech.jts.geom.Coordinate, org.locationtech.jts.geom.Coordinate):int");
    }

    private int computeCollinearIntersection(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate q3;
        double t3;
        Coordinate q4;
        double t4;
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate p3 = coordinate3;
        Coordinate p4 = coordinate4;
        double r3 = rParameter(p1, p2, p3);
        double r4 = rParameter(p1, p2, p4);
        if (r3 < r4) {
            q3 = p3;
            t3 = r3;
            q4 = p4;
            t4 = r4;
        } else {
            q3 = p4;
            t3 = r4;
            q4 = p3;
            t4 = r3;
        }
        if (t3 > 1.0d || t4 < 0.0d) {
            return 0;
        }
        if (q4 == p1) {
            this.f401pa.setCoordinate(p1);
            return 1;
        } else if (q3 == p2) {
            this.f401pa.setCoordinate(p2);
            return 1;
        } else {
            this.f401pa.setCoordinate(p1);
            if (t3 > 0.0d) {
                this.f401pa.setCoordinate(q3);
            }
            this.f402pb.setCoordinate(p2);
            if (t4 < 1.0d) {
                this.f402pb.setCoordinate(q4);
            }
            return 2;
        }
    }

    private double rParameter(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        double r;
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate p = coordinate3;
        if (Math.abs(p2.f412x - p1.f412x) > Math.abs(p2.f413y - p1.f413y)) {
            r = (p.f412x - p1.f412x) / (p2.f412x - p1.f412x);
        } else {
            r = (p.f413y - p1.f413y) / (p2.f413y - p1.f413y);
        }
        return r;
    }
}
