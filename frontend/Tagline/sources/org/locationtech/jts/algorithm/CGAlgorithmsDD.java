package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.math.C1564DD;

public class CGAlgorithmsDD {
    private static final double DP_SAFE_EPSILON = 1.0E-15d;

    private CGAlgorithmsDD() {
    }

    public static int orientationIndex(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q = coordinate3;
        int index = orientationIndexFilter(p1, p2, q);
        if (index <= 1) {
            return index;
        }
        C1564DD dx1 = C1564DD.valueOf(p2.f412x).selfAdd(-p1.f412x);
        C1564DD dy1 = C1564DD.valueOf(p2.f413y).selfAdd(-p1.f413y);
        C1564DD dx2 = C1564DD.valueOf(q.f412x).selfAdd(-p2.f412x);
        return dx1.selfMultiply(C1564DD.valueOf(q.f413y).selfAdd(-p2.f413y)).selfSubtract(dy1.selfMultiply(dx2)).signum();
    }

    public static int signOfDet2x2(C1564DD x1, C1564DD y1, C1564DD x2, C1564DD y2) {
        return x1.multiply(y2).selfSubtract(y1.multiply(x2)).signum();
    }

    private static int orientationIndexFilter(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        double detsum;
        Coordinate pa = coordinate;
        Coordinate pb = coordinate2;
        Coordinate pc = coordinate3;
        double detleft = (pa.f412x - pc.f412x) * (pb.f413y - pc.f413y);
        double detright = (pa.f413y - pc.f413y) * (pb.f412x - pc.f412x);
        double det = detleft - detright;
        if (detleft > 0.0d) {
            if (detright <= 0.0d) {
                return signum(det);
            }
            detsum = detleft + detright;
        } else if (detleft >= 0.0d) {
            return signum(det);
        } else {
            if (detright >= 0.0d) {
                return signum(det);
            }
            detsum = (-detleft) - detright;
        }
        double errbound = DP_SAFE_EPSILON * detsum;
        if (det >= errbound || (-det) >= errbound) {
            return signum(det);
        }
        return 2;
    }

    private static int signum(double d) {
        double x = d;
        if (x > 0.0d) {
            return 1;
        }
        if (x < 0.0d) {
            return -1;
        }
        return 0;
    }

    public static Coordinate intersection(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4) {
        Coordinate p1;
        Coordinate p12 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q1 = coordinate3;
        Coordinate q2 = coordinate4;
        C1564DD denom = C1564DD.valueOf(q2.f413y).selfSubtract(q1.f413y).selfMultiply(C1564DD.valueOf(p2.f412x).selfSubtract(p12.f412x)).subtract(C1564DD.valueOf(q2.f412x).selfSubtract(q1.f412x).selfMultiply(C1564DD.valueOf(p2.f413y).selfSubtract(p12.f413y)));
        new Coordinate(C1564DD.valueOf(p12.f412x).selfAdd(C1564DD.valueOf(p2.f412x).selfSubtract(p12.f412x).selfMultiply(C1564DD.valueOf(q2.f412x).selfSubtract(q1.f412x).selfMultiply(C1564DD.valueOf(p12.f413y).selfSubtract(q1.f413y)).subtract(C1564DD.valueOf(q2.f413y).selfSubtract(q1.f413y).selfMultiply(C1564DD.valueOf(p12.f412x).selfSubtract(q1.f412x))).selfDivide(denom).doubleValue())).doubleValue(), C1564DD.valueOf(q1.f413y).selfAdd(C1564DD.valueOf(q2.f413y).selfSubtract(q1.f413y).selfMultiply(C1564DD.valueOf(p2.f412x).selfSubtract(p12.f412x).selfMultiply(C1564DD.valueOf(p12.f413y).selfSubtract(q1.f413y)).subtract(C1564DD.valueOf(p2.f413y).selfSubtract(p12.f413y).selfMultiply(C1564DD.valueOf(p12.f412x).selfSubtract(q1.f412x))).selfDivide(denom).doubleValue())).doubleValue());
        return p1;
    }
}
