package org.locationtech.jts.geom;

public class Location {
    public static final int BOUNDARY = 1;
    public static final int EXTERIOR = 2;
    public static final int INTERIOR = 0;
    public static final int NONE = -1;

    public Location() {
    }

    public static char toLocationSymbol(int i) {
        Throwable th;
        StringBuilder sb;
        int locationValue = i;
        switch (locationValue) {
            case -1:
                return '-';
            case 0:
                return 'i';
            case 1:
                return 'b';
            case 2:
                return 'e';
            default:
                Throwable th2 = th;
                new StringBuilder();
                new IllegalArgumentException(sb.append("Unknown location value: ").append(locationValue).toString());
                throw th2;
        }
    }
}
