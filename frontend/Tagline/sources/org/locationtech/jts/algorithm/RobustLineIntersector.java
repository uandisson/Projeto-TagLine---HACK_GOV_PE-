package org.locationtech.jts.algorithm;

import java.io.PrintStream;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;

public class RobustLineIntersector extends LineIntersector {
    public RobustLineIntersector() {
    }

    public void computeIntersection(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate p = coordinate;
        Coordinate p1 = coordinate2;
        Coordinate p2 = coordinate3;
        this.isProper = false;
        if (Envelope.intersects(p1, p2, p) && CGAlgorithms.orientationIndex(p1, p2, p) == 0 && CGAlgorithms.orientationIndex(p2, p1, p) == 0) {
            this.isProper = true;
            if (p.equals(p1) || p.equals(p2)) {
                this.isProper = false;
            }
            this.result = 1;
            return;
        }
        this.result = 0;
    }

    /* access modifiers changed from: protected */
    public int computeIntersect(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate coordinate5;
        Coordinate coordinate6;
        Coordinate coordinate7;
        Coordinate coordinate8;
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q1 = coordinate3;
        Coordinate q2 = coordinate4;
        this.isProper = false;
        if (!Envelope.intersects(p1, p2, q1, q2)) {
            return 0;
        }
        int Pq1 = CGAlgorithms.orientationIndex(p1, p2, q1);
        int Pq2 = CGAlgorithms.orientationIndex(p1, p2, q2);
        if ((Pq1 > 0 && Pq2 > 0) || (Pq1 < 0 && Pq2 < 0)) {
            return 0;
        }
        int Qp1 = CGAlgorithms.orientationIndex(q1, q2, p1);
        int Qp2 = CGAlgorithms.orientationIndex(q1, q2, p2);
        if ((Qp1 > 0 && Qp2 > 0) || (Qp1 < 0 && Qp2 < 0)) {
            return 0;
        }
        if (Pq1 == 0 && Pq2 == 0 && Qp1 == 0 && Qp2 == 0) {
            return computeCollinearIntersection(p1, p2, q1, q2);
        }
        if (Pq1 == 0 || Pq2 == 0 || Qp1 == 0 || Qp2 == 0) {
            this.isProper = false;
            if (p1.equals2D(q1) || p1.equals2D(q2)) {
                this.intPt[0] = p1;
            } else if (p2.equals2D(q1) || p2.equals2D(q2)) {
                this.intPt[0] = p2;
            } else if (Pq1 == 0) {
                new Coordinate(q1);
                this.intPt[0] = coordinate8;
            } else if (Pq2 == 0) {
                new Coordinate(q2);
                this.intPt[0] = coordinate7;
            } else if (Qp1 == 0) {
                new Coordinate(p1);
                this.intPt[0] = coordinate6;
            } else if (Qp2 == 0) {
                new Coordinate(p2);
                this.intPt[0] = coordinate5;
            }
        } else {
            this.isProper = true;
            this.intPt[0] = intersection(p1, p2, q1, q2);
        }
        return 1;
    }

    private int computeCollinearIntersection(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q1 = coordinate3;
        Coordinate q2 = coordinate4;
        boolean p1q1p2 = Envelope.intersects(p1, p2, q1);
        boolean p1q2p2 = Envelope.intersects(p1, p2, q2);
        boolean q1p1q2 = Envelope.intersects(q1, q2, p1);
        boolean q1p2q2 = Envelope.intersects(q1, q2, p2);
        if (p1q1p2 && p1q2p2) {
            this.intPt[0] = q1;
            this.intPt[1] = q2;
            return 2;
        } else if (q1p1q2 && q1p2q2) {
            this.intPt[0] = p1;
            this.intPt[1] = p2;
            return 2;
        } else if (p1q1p2 && q1p1q2) {
            this.intPt[0] = q1;
            this.intPt[1] = p1;
            return (!q1.equals(p1) || p1q2p2 || q1p2q2) ? 2 : 1;
        } else if (p1q1p2 && q1p2q2) {
            this.intPt[0] = q1;
            this.intPt[1] = p2;
            return (!q1.equals(p2) || p1q2p2 || q1p1q2) ? 2 : 1;
        } else if (p1q2p2 && q1p1q2) {
            this.intPt[0] = q2;
            this.intPt[1] = p1;
            return (!q2.equals(p1) || p1q1p2 || q1p2q2) ? 2 : 1;
        } else if (!p1q2p2 || !q1p2q2) {
            return 0;
        } else {
            this.intPt[0] = q2;
            this.intPt[1] = p2;
            return (!q2.equals(p2) || p1q1p2 || q1p1q2) ? 2 : 1;
        }
    }

    private Coordinate intersection(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate coordinate5;
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q1 = coordinate3;
        Coordinate q2 = coordinate4;
        Coordinate intPt = intersectionWithNormalization(p1, p2, q1, q2);
        if (!isInSegmentEnvelopes(intPt)) {
            new Coordinate(nearestEndpoint(p1, p2, q1, q2));
            intPt = coordinate5;
        }
        if (this.precisionModel != null) {
            this.precisionModel.makePrecise(intPt);
        }
        return intPt;
    }

    private void checkDD(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2, Coordinate coordinate) {
        StringBuilder sb;
        StringBuilder sb2;
        Coordinate intPt = coordinate;
        Coordinate intPtDD = CGAlgorithmsDD.intersection(p1, p2, q1, q2);
        boolean isIn = isInSegmentEnvelopes(intPtDD);
        PrintStream printStream = System.out;
        new StringBuilder();
        printStream.println(sb.append("DD in env = ").append(isIn).append("  --------------------- ").append(intPtDD).toString());
        if (intPt.distance(intPtDD) > 1.0E-4d) {
            PrintStream printStream2 = System.out;
            new StringBuilder();
            printStream2.println(sb2.append("Distance = ").append(intPt.distance(intPtDD)).toString());
        }
    }

    private Coordinate intersectionWithNormalization(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2) {
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        Coordinate coordinate5;
        new Coordinate(p1);
        Coordinate n1 = coordinate;
        new Coordinate(p2);
        Coordinate n2 = coordinate2;
        new Coordinate(q1);
        Coordinate n3 = coordinate3;
        new Coordinate(q2);
        Coordinate n4 = coordinate4;
        new Coordinate();
        Coordinate normPt = coordinate5;
        normalizeToEnvCentre(n1, n2, n3, n4, normPt);
        Coordinate intPt = safeHCoordinateIntersection(n1, n2, n3, n4);
        intPt.f412x += normPt.f412x;
        intPt.f413y += normPt.f413y;
        return intPt;
    }

    private Coordinate safeHCoordinateIntersection(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate intPt;
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q1 = coordinate3;
        Coordinate q2 = coordinate4;
        try {
            intPt = HCoordinate.intersection(p1, p2, q1, q2);
        } catch (NotRepresentableException e) {
            NotRepresentableException notRepresentableException = e;
            intPt = nearestEndpoint(p1, p2, q1, q2);
        }
        return intPt;
    }

    private void normalizeToMinimum(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4, Coordinate coordinate5) {
        Coordinate n1 = coordinate;
        Coordinate n2 = coordinate2;
        Coordinate n3 = coordinate3;
        Coordinate n4 = coordinate4;
        Coordinate normPt = coordinate5;
        normPt.f412x = smallestInAbsValue(n1.f412x, n2.f412x, n3.f412x, n4.f412x);
        normPt.f413y = smallestInAbsValue(n1.f413y, n2.f413y, n3.f413y, n4.f413y);
        n1.f412x -= normPt.f412x;
        n1.f413y -= normPt.f413y;
        n2.f412x -= normPt.f412x;
        n2.f413y -= normPt.f413y;
        n3.f412x -= normPt.f412x;
        n3.f413y -= normPt.f413y;
        n4.f412x -= normPt.f412x;
        n4.f413y -= normPt.f413y;
    }

    private void normalizeToEnvCentre(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4, Coordinate coordinate5) {
        Coordinate n00 = coordinate;
        Coordinate n01 = coordinate2;
        Coordinate n10 = coordinate3;
        Coordinate n11 = coordinate4;
        Coordinate normPt = coordinate5;
        double minX0 = n00.f412x < n01.f412x ? n00.f412x : n01.f412x;
        double minY0 = n00.f413y < n01.f413y ? n00.f413y : n01.f413y;
        double maxX0 = n00.f412x > n01.f412x ? n00.f412x : n01.f412x;
        double maxY0 = n00.f413y > n01.f413y ? n00.f413y : n01.f413y;
        double minX1 = n10.f412x < n11.f412x ? n10.f412x : n11.f412x;
        double minY1 = n10.f413y < n11.f413y ? n10.f413y : n11.f413y;
        double maxX1 = n10.f412x > n11.f412x ? n10.f412x : n11.f412x;
        double maxY1 = n10.f413y > n11.f413y ? n10.f413y : n11.f413y;
        double intMinX = minX0 > minX1 ? minX0 : minX1;
        double intMaxX = maxX0 < maxX1 ? maxX0 : maxX1;
        double intMinY = minY0 > minY1 ? minY0 : minY1;
        double intMaxY = maxY0 < maxY1 ? maxY0 : maxY1;
        normPt.f412x = (intMinX + intMaxX) / 2.0d;
        normPt.f413y = (intMinY + intMaxY) / 2.0d;
        n00.f412x -= normPt.f412x;
        n00.f413y -= normPt.f413y;
        n01.f412x -= normPt.f412x;
        n01.f413y -= normPt.f413y;
        n10.f412x -= normPt.f412x;
        n10.f413y -= normPt.f413y;
        n11.f412x -= normPt.f412x;
        n11.f413y -= normPt.f413y;
    }

    private double smallestInAbsValue(double x1, double d, double d2, double d3) {
        double x2 = d;
        double x3 = d2;
        double x4 = d3;
        double x = x1;
        double xabs = Math.abs(x);
        if (Math.abs(x2) < xabs) {
            x = x2;
            xabs = Math.abs(x2);
        }
        if (Math.abs(x3) < xabs) {
            x = x3;
            xabs = Math.abs(x3);
        }
        if (Math.abs(x4) < xabs) {
            x = x4;
        }
        return x;
    }

    private boolean isInSegmentEnvelopes(Coordinate coordinate) {
        Envelope envelope;
        Envelope env1;
        Coordinate intPt = coordinate;
        new Envelope(this.inputLines[0][0], this.inputLines[0][1]);
        Envelope env0 = envelope;
        new Envelope(this.inputLines[1][0], this.inputLines[1][1]);
        return env0.contains(intPt) && env1.contains(intPt);
    }

    private static Coordinate nearestEndpoint(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q1 = coordinate3;
        Coordinate q2 = coordinate4;
        Coordinate nearestPt = p1;
        double minDist = CGAlgorithms.distancePointLine(p1, q1, q2);
        double dist = CGAlgorithms.distancePointLine(p2, q1, q2);
        if (dist < minDist) {
            minDist = dist;
            nearestPt = p2;
        }
        double dist2 = CGAlgorithms.distancePointLine(q1, p1, p2);
        if (dist2 < minDist) {
            minDist = dist2;
            nearestPt = q1;
        }
        double dist3 = CGAlgorithms.distancePointLine(q2, p1, p2);
        if (dist3 < minDist) {
            double minDist2 = dist3;
            nearestPt = q2;
        }
        return nearestPt;
    }
}
