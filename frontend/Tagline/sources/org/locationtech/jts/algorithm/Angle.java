package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;

public class Angle {
    public static final int CLOCKWISE = -1;
    public static final int COUNTERCLOCKWISE = 1;
    public static final int NONE = 0;
    public static final double PI_OVER_2 = 1.5707963267948966d;
    public static final double PI_OVER_4 = 0.7853981633974483d;
    public static final double PI_TIMES_2 = 6.283185307179586d;

    private Angle() {
    }

    public static double toDegrees(double radians) {
        return (radians * 180.0d) / 3.141592653589793d;
    }

    public static double toRadians(double angleDegrees) {
        return (angleDegrees * 3.141592653589793d) / 180.0d;
    }

    public static double angle(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        return Math.atan2(p1.f413y - p0.f413y, p1.f412x - p0.f412x);
    }

    public static double angle(Coordinate coordinate) {
        Coordinate p = coordinate;
        return Math.atan2(p.f413y, p.f412x);
    }

    public static boolean isAcute(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        Coordinate p2 = coordinate3;
        return ((p0.f412x - p1.f412x) * (p2.f412x - p1.f412x)) + ((p0.f413y - p1.f413y) * (p2.f413y - p1.f413y)) > 0.0d;
    }

    public static boolean isObtuse(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        Coordinate p2 = coordinate3;
        return ((p0.f412x - p1.f412x) * (p2.f412x - p1.f412x)) + ((p0.f413y - p1.f413y) * (p2.f413y - p1.f413y)) < 0.0d;
    }

    public static double angleBetween(Coordinate tip1, Coordinate coordinate, Coordinate tip2) {
        Coordinate tail = coordinate;
        return diff(angle(tail, tip1), angle(tail, tip2));
    }

    public static double angleBetweenOriented(Coordinate tip1, Coordinate coordinate, Coordinate tip2) {
        Coordinate tail = coordinate;
        double angDel = angle(tail, tip2) - angle(tail, tip1);
        if (angDel <= -3.141592653589793d) {
            return angDel + 6.283185307179586d;
        }
        if (angDel > 3.141592653589793d) {
            return angDel - 6.283185307179586d;
        }
        return angDel;
    }

    public static double interiorAngle(Coordinate p0, Coordinate coordinate, Coordinate p2) {
        Coordinate p1 = coordinate;
        return Math.abs(angle(p1, p2) - angle(p1, p0));
    }

    public static int getTurn(double ang1, double ang2) {
        double crossproduct = Math.sin(ang2 - ang1);
        if (crossproduct > 0.0d) {
            return 1;
        }
        if (crossproduct < 0.0d) {
            return -1;
        }
        return 0;
    }

    public static double normalize(double d) {
        double angle = d;
        while (angle > 3.141592653589793d) {
            angle -= 6.283185307179586d;
        }
        while (angle <= -3.141592653589793d) {
            angle += 6.283185307179586d;
        }
        return angle;
    }

    public static double normalizePositive(double d) {
        double angle = d;
        if (angle < 0.0d) {
            while (angle < 0.0d) {
                angle += 6.283185307179586d;
            }
            if (angle >= 6.283185307179586d) {
                angle = 0.0d;
            }
        } else {
            while (angle >= 6.283185307179586d) {
                angle -= 6.283185307179586d;
            }
            if (angle < 0.0d) {
                angle = 0.0d;
            }
        }
        return angle;
    }

    public static double diff(double d, double d2) {
        double delAngle;
        double ang1 = d;
        double ang2 = d2;
        if (ang1 < ang2) {
            delAngle = ang2 - ang1;
        } else {
            delAngle = ang1 - ang2;
        }
        if (delAngle > 3.141592653589793d) {
            delAngle = 6.283185307179586d - delAngle;
        }
        return delAngle;
    }
}
