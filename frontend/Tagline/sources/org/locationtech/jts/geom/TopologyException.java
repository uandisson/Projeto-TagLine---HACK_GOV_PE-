package org.locationtech.jts.geom;

public class TopologyException extends RuntimeException {

    /* renamed from: pt */
    private Coordinate f424pt = null;

    private static String msgWithCoord(String str, Coordinate coordinate) {
        StringBuilder sb;
        String msg = str;
        Coordinate pt = coordinate;
        if (pt == null) {
            return msg;
        }
        new StringBuilder();
        return sb.append(msg).append(" [ ").append(pt).append(" ]").toString();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public TopologyException(String msg) {
        super(msg);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TopologyException(java.lang.String r9, org.locationtech.jts.geom.Coordinate r10) {
        /*
            r8 = this;
            r0 = r8
            r1 = r9
            r2 = r10
            r3 = r0
            r4 = r1
            r5 = r2
            java.lang.String r4 = msgWithCoord(r4, r5)
            r3.<init>(r4)
            r3 = r0
            r4 = 0
            r3.f424pt = r4
            r3 = r0
            org.locationtech.jts.geom.Coordinate r4 = new org.locationtech.jts.geom.Coordinate
            r7 = r4
            r4 = r7
            r5 = r7
            r6 = r2
            r5.<init>(r6)
            r3.f424pt = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.geom.TopologyException.<init>(java.lang.String, org.locationtech.jts.geom.Coordinate):void");
    }

    public Coordinate getCoordinate() {
        return this.f424pt;
    }
}
