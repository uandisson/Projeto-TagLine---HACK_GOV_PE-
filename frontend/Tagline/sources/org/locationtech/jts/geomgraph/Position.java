package org.locationtech.jts.geomgraph;

public class Position {
    public static final int LEFT = 1;

    /* renamed from: ON */
    public static final int f433ON = 0;
    public static final int RIGHT = 2;

    public Position() {
    }

    public static final int opposite(int i) {
        int position = i;
        if (position == 1) {
            return 2;
        }
        if (position == 2) {
            return 1;
        }
        return position;
    }
}
