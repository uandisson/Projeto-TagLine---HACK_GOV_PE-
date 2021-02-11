package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;

public class RayCrossingCounter {
    private int crossingCount = 0;
    private boolean isPointOnSegment = false;

    /* renamed from: p */
    private Coordinate f404p;

    public static int locatePointInRing(Coordinate p, Coordinate[] coordinateArr) {
        RayCrossingCounter rayCrossingCounter;
        Coordinate[] ring = coordinateArr;
        new RayCrossingCounter(p);
        RayCrossingCounter counter = rayCrossingCounter;
        for (int i = 1; i < ring.length; i++) {
            counter.countSegment(ring[i], ring[i - 1]);
            if (counter.isOnSegment()) {
                return counter.getLocation();
            }
        }
        return counter.getLocation();
    }

    public static int locatePointInRing(Coordinate p, CoordinateSequence coordinateSequence) {
        RayCrossingCounter rayCrossingCounter;
        Coordinate coordinate;
        Coordinate coordinate2;
        CoordinateSequence ring = coordinateSequence;
        new RayCrossingCounter(p);
        RayCrossingCounter counter = rayCrossingCounter;
        new Coordinate();
        Coordinate p1 = coordinate;
        new Coordinate();
        Coordinate p2 = coordinate2;
        for (int i = 1; i < ring.size(); i++) {
            ring.getCoordinate(i, p1);
            ring.getCoordinate(i - 1, p2);
            counter.countSegment(p1, p2);
            if (counter.isOnSegment()) {
                return counter.getLocation();
            }
        }
        return counter.getLocation();
    }

    public RayCrossingCounter(Coordinate p) {
        this.f404p = p;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0144, code lost:
        if (r5.f413y > r3.f404p.f413y) goto L_0x0146;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void countSegment(org.locationtech.jts.geom.Coordinate r26, org.locationtech.jts.geom.Coordinate r27) {
        /*
            r25 = this;
            r3 = r25
            r4 = r26
            r5 = r27
            r16 = r4
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r18 = r3
            r0 = r18
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r18 = r0
            r0 = r18
            double r0 = r0.f412x
            r18 = r0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 >= 0) goto L_0x003b
            r16 = r5
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r18 = r3
            r0 = r18
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r18 = r0
            r0 = r18
            double r0 = r0.f412x
            r18 = r0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 >= 0) goto L_0x003b
        L_0x003a:
            return
        L_0x003b:
            r16 = r3
            r0 = r16
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r16 = r0
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r18 = r5
            r0 = r18
            double r0 = r0.f412x
            r18 = r0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 != 0) goto L_0x007a
            r16 = r3
            r0 = r16
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r16 = r0
            r0 = r16
            double r0 = r0.f413y
            r16 = r0
            r18 = r5
            r0 = r18
            double r0 = r0.f413y
            r18 = r0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 != 0) goto L_0x007a
            r16 = r3
            r17 = 1
            r0 = r17
            r1 = r16
            r1.isPointOnSegment = r0
            goto L_0x003a
        L_0x007a:
            r16 = r4
            r0 = r16
            double r0 = r0.f413y
            r16 = r0
            r18 = r3
            r0 = r18
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r18 = r0
            r0 = r18
            double r0 = r0.f413y
            r18 = r0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 != 0) goto L_0x0112
            r16 = r5
            r0 = r16
            double r0 = r0.f413y
            r16 = r0
            r18 = r3
            r0 = r18
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r18 = r0
            r0 = r18
            double r0 = r0.f413y
            r18 = r0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 != 0) goto L_0x0112
            r16 = r4
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r6 = r16
            r16 = r5
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r8 = r16
            r16 = r6
            r18 = r8
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 <= 0) goto L_0x00de
            r16 = r5
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r6 = r16
            r16 = r4
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r8 = r16
        L_0x00de:
            r16 = r3
            r0 = r16
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r16 = r0
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r18 = r6
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 < 0) goto L_0x0110
            r16 = r3
            r0 = r16
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r16 = r0
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r18 = r8
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 > 0) goto L_0x0110
            r16 = r3
            r17 = 1
            r0 = r17
            r1 = r16
            r1.isPointOnSegment = r0
        L_0x0110:
            goto L_0x003a
        L_0x0112:
            r16 = r4
            r0 = r16
            double r0 = r0.f413y
            r16 = r0
            r18 = r3
            r0 = r18
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r18 = r0
            r0 = r18
            double r0 = r0.f413y
            r18 = r0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 <= 0) goto L_0x0146
            r16 = r5
            r0 = r16
            double r0 = r0.f413y
            r16 = r0
            r18 = r3
            r0 = r18
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r18 = r0
            r0 = r18
            double r0 = r0.f413y
            r18 = r0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 <= 0) goto L_0x017a
        L_0x0146:
            r16 = r5
            r0 = r16
            double r0 = r0.f413y
            r16 = r0
            r18 = r3
            r0 = r18
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r18 = r0
            r0 = r18
            double r0 = r0.f413y
            r18 = r0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 <= 0) goto L_0x023a
            r16 = r4
            r0 = r16
            double r0 = r0.f413y
            r16 = r0
            r18 = r3
            r0 = r18
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r18 = r0
            r0 = r18
            double r0 = r0.f413y
            r18 = r0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 > 0) goto L_0x023a
        L_0x017a:
            r16 = r4
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r18 = r3
            r0 = r18
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r18 = r0
            r0 = r18
            double r0 = r0.f412x
            r18 = r0
            double r16 = r16 - r18
            r6 = r16
            r16 = r4
            r0 = r16
            double r0 = r0.f413y
            r16 = r0
            r18 = r3
            r0 = r18
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r18 = r0
            r0 = r18
            double r0 = r0.f413y
            r18 = r0
            double r16 = r16 - r18
            r8 = r16
            r16 = r5
            r0 = r16
            double r0 = r0.f412x
            r16 = r0
            r18 = r3
            r0 = r18
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r18 = r0
            r0 = r18
            double r0 = r0.f412x
            r18 = r0
            double r16 = r16 - r18
            r10 = r16
            r16 = r5
            r0 = r16
            double r0 = r0.f413y
            r16 = r0
            r18 = r3
            r0 = r18
            org.locationtech.jts.geom.Coordinate r0 = r0.f404p
            r18 = r0
            r0 = r18
            double r0 = r0.f413y
            r18 = r0
            double r16 = r16 - r18
            r12 = r16
            r16 = r6
            r18 = r8
            r20 = r10
            r22 = r12
            int r16 = org.locationtech.jts.algorithm.RobustDeterminant.signOfDet2x2(r16, r18, r20, r22)
            r0 = r16
            double r0 = (double) r0
            r16 = r0
            r14 = r16
            r16 = r14
            r18 = 0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 != 0) goto L_0x0209
            r16 = r3
            r17 = 1
            r0 = r17
            r1 = r16
            r1.isPointOnSegment = r0
            goto L_0x003a
        L_0x0209:
            r16 = r12
            r18 = r8
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 >= 0) goto L_0x021a
            r16 = r14
            r0 = r16
            double r0 = -r0
            r16 = r0
            r14 = r16
        L_0x021a:
            r16 = r14
            r18 = 0
            int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r16 <= 0) goto L_0x023a
            r16 = r3
            r24 = r16
            r16 = r24
            r17 = r24
            r0 = r17
            int r0 = r0.crossingCount
            r17 = r0
            r18 = 1
            int r17 = r17 + 1
            r0 = r17
            r1 = r16
            r1.crossingCount = r0
        L_0x023a:
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.algorithm.RayCrossingCounter.countSegment(org.locationtech.jts.geom.Coordinate, org.locationtech.jts.geom.Coordinate):void");
    }

    public boolean isOnSegment() {
        return this.isPointOnSegment;
    }

    public int getLocation() {
        if (this.isPointOnSegment) {
            return 1;
        }
        if (this.crossingCount % 2 == 1) {
            return 0;
        }
        return 2;
    }

    public boolean isPointInPolygon() {
        return getLocation() != 2;
    }
}
