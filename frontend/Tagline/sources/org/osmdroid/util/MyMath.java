package org.osmdroid.util;

import org.osmdroid.views.util.constants.MathConstants;

public class MyMath implements MathConstants {
    private MyMath() {
    }

    public static double gudermannInverse(double aLatitude) {
        return Math.log(Math.tan(0.7853981852531433d + ((0.01745329238474369d * aLatitude) / 2.0d)));
    }

    public static double gudermann(double y) {
        return 57.295780181884766d * Math.atan(Math.sinh(y));
    }

    public static int mod(int i, int i2) {
        int number = i;
        int modulus = i2;
        if (number > 0) {
            return number % modulus;
        }
        while (number < 0) {
            number += modulus;
        }
        return number;
    }
}
