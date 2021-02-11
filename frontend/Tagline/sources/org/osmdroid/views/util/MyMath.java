package org.osmdroid.views.util;

public class MyMath {
    private MyMath() {
    }

    public static int getNextSquareNumberAbove(float factor) {
        int out = 0;
        int cur = 1;
        int i = 1;
        while (((float) cur) <= factor) {
            out = i;
            cur *= 2;
            i++;
        }
        return out;
    }
}
