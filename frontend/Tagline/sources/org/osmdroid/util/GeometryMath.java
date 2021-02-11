package org.osmdroid.util;

import android.graphics.Point;
import android.graphics.Rect;

public class GeometryMath {
    public static final double DEG2RAD = 0.017453292519943295d;
    public static final double RAD2DEG = 57.29577951308232d;

    public GeometryMath() {
    }

    public static final Rect getBoundingBoxForRotatatedRectangle(Rect rect, float angle, Rect reuse) {
        Rect rect2 = rect;
        return getBoundingBoxForRotatatedRectangle(rect2, rect2.centerX(), rect2.centerY(), angle, reuse);
    }

    public static final Rect getBoundingBoxForRotatatedRectangle(Rect rect, Point point, float angle, Rect reuse) {
        Point centerPoint = point;
        return getBoundingBoxForRotatatedRectangle(rect, centerPoint.x, centerPoint.y, angle, reuse);
    }

    public static final Rect getBoundingBoxForRotatatedRectangle(Rect rect, int i, int i2, float f, Rect rect2) {
        Rect rect3;
        Rect rect4 = rect;
        int centerX = i;
        int centerY = i2;
        float angle = f;
        Rect reuse = rect2;
        if (reuse == null) {
            new Rect();
            reuse = rect3;
        }
        double theta = ((double) angle) * 0.017453292519943295d;
        double sinTheta = Math.sin(theta);
        double cosTheta = Math.cos(theta);
        double dx1 = (double) (rect4.left - centerX);
        double dy1 = (double) (rect4.top - centerY);
        double newX1 = (((double) centerX) - (dx1 * cosTheta)) + (dy1 * sinTheta);
        double newY1 = (((double) centerY) - (dx1 * sinTheta)) - (dy1 * cosTheta);
        double dx2 = (double) (rect4.right - centerX);
        double dy2 = (double) (rect4.top - centerY);
        double newX2 = (((double) centerX) - (dx2 * cosTheta)) + (dy2 * sinTheta);
        double newY2 = (((double) centerY) - (dx2 * sinTheta)) - (dy2 * cosTheta);
        double dx3 = (double) (rect4.left - centerX);
        double dy3 = (double) (rect4.bottom - centerY);
        double newX3 = (((double) centerX) - (dx3 * cosTheta)) + (dy3 * sinTheta);
        double newY3 = (((double) centerY) - (dx3 * sinTheta)) - (dy3 * cosTheta);
        double dx4 = (double) (rect4.right - centerX);
        double dy4 = (double) (rect4.bottom - centerY);
        double newX4 = (((double) centerX) - (dx4 * cosTheta)) + (dy4 * sinTheta);
        double newY4 = (((double) centerY) - (dx4 * sinTheta)) - (dy4 * cosTheta);
        reuse.set((int) Min4(newX1, newX2, newX3, newX4), (int) Min4(newY1, newY2, newY3, newY4), (int) Max4(newX1, newX2, newX3, newX4), (int) Max4(newY1, newY2, newY3, newY4));
        return reuse;
    }

    private static double Min4(double a, double b, double c, double d) {
        return Math.floor(Math.min(Math.min(a, b), Math.min(c, d)));
    }

    private static double Max4(double a, double b, double c, double d) {
        return Math.ceil(Math.max(Math.max(a, b), Math.max(c, d)));
    }
}
