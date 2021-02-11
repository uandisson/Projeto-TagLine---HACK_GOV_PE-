package org.locationtech.jts.triangulate.quadedge;

import org.locationtech.jts.geom.LineSegment;

public class LocateFailureException extends RuntimeException {
    private LineSegment seg = null;

    private static String msgWithSpatial(String str, LineSegment lineSegment) {
        StringBuilder sb;
        String msg = str;
        LineSegment seg2 = lineSegment;
        if (seg2 == null) {
            return msg;
        }
        new StringBuilder();
        return sb.append(msg).append(" [ ").append(seg2).append(" ]").toString();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LocateFailureException(String msg) {
        super(msg);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LocateFailureException(java.lang.String r9, org.locationtech.jts.geom.LineSegment r10) {
        /*
            r8 = this;
            r0 = r8
            r1 = r9
            r2 = r10
            r3 = r0
            r4 = r1
            r5 = r2
            java.lang.String r4 = msgWithSpatial(r4, r5)
            r3.<init>(r4)
            r3 = r0
            r4 = 0
            r3.seg = r4
            r3 = r0
            org.locationtech.jts.geom.LineSegment r4 = new org.locationtech.jts.geom.LineSegment
            r7 = r4
            r4 = r7
            r5 = r7
            r6 = r2
            r5.<init>(r6)
            r3.seg = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.triangulate.quadedge.LocateFailureException.<init>(java.lang.String, org.locationtech.jts.geom.LineSegment):void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public LocateFailureException(org.locationtech.jts.geom.LineSegment r8) {
        /*
            r7 = this;
            r0 = r7
            r1 = r8
            r2 = r0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r6 = r3
            r3 = r6
            r4 = r6
            r4.<init>()
            java.lang.String r4 = "Locate failed to converge (at edge: "
            java.lang.StringBuilder r3 = r3.append(r4)
            r4 = r1
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = ").  Possible causes include invalid Subdivision topology or very close sites"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            r2 = r0
            r3 = 0
            r2.seg = r3
            r2 = r0
            org.locationtech.jts.geom.LineSegment r3 = new org.locationtech.jts.geom.LineSegment
            r6 = r3
            r3 = r6
            r4 = r6
            r5 = r1
            r4.<init>(r5)
            r2.seg = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.triangulate.quadedge.LocateFailureException.<init>(org.locationtech.jts.geom.LineSegment):void");
    }

    public LineSegment getSegment() {
        return this.seg;
    }
}
