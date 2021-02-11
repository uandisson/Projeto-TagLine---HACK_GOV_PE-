package org.locationtech.jts.operation.valid;

import org.locationtech.jts.geom.Coordinate;

public class TopologyValidationError {
    public static final int DISCONNECTED_INTERIOR = 4;
    public static final int DUPLICATE_RINGS = 8;
    public static final int ERROR = 0;
    public static final int HOLE_OUTSIDE_SHELL = 2;
    public static final int INVALID_COORDINATE = 10;
    public static final int NESTED_HOLES = 3;
    public static final int NESTED_SHELLS = 7;
    public static final int REPEATED_POINT = 1;
    public static final int RING_NOT_CLOSED = 11;
    public static final int RING_SELF_INTERSECTION = 6;
    public static final int SELF_INTERSECTION = 5;
    public static final int TOO_FEW_POINTS = 9;
    public static final String[] errMsg;
    private int errorType;

    /* renamed from: pt */
    private Coordinate f503pt;

    static {
        String[] strArr = new String[12];
        strArr[0] = "Topology Validation Error";
        String[] strArr2 = strArr;
        strArr2[1] = "Repeated Point";
        String[] strArr3 = strArr2;
        strArr3[2] = "Hole lies outside shell";
        String[] strArr4 = strArr3;
        strArr4[3] = "Holes are nested";
        String[] strArr5 = strArr4;
        strArr5[4] = "Interior is disconnected";
        String[] strArr6 = strArr5;
        strArr6[5] = "Self-intersection";
        String[] strArr7 = strArr6;
        strArr7[6] = "Ring Self-intersection";
        String[] strArr8 = strArr7;
        strArr8[7] = "Nested shells";
        String[] strArr9 = strArr8;
        strArr9[8] = "Duplicate Rings";
        String[] strArr10 = strArr9;
        strArr10[9] = "Too few distinct points in geometry component";
        String[] strArr11 = strArr10;
        strArr11[10] = "Invalid Coordinate";
        String[] strArr12 = strArr11;
        strArr12[11] = "Ring is not closed";
        errMsg = strArr12;
    }

    public TopologyValidationError(int errorType2, Coordinate coordinate) {
        Coordinate pt = coordinate;
        this.errorType = errorType2;
        if (pt != null) {
            this.f503pt = (Coordinate) pt.clone();
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public TopologyValidationError(int errorType2) {
        this(errorType2, (Coordinate) null);
    }

    public Coordinate getCoordinate() {
        return this.f503pt;
    }

    public int getErrorType() {
        return this.errorType;
    }

    public String getMessage() {
        return errMsg[this.errorType];
    }

    public String toString() {
        StringBuilder sb;
        StringBuilder sb2;
        String locStr = "";
        if (this.f503pt != null) {
            new StringBuilder();
            locStr = sb2.append(" at or near point ").append(this.f503pt).toString();
        }
        new StringBuilder();
        return sb.append(getMessage()).append(locStr).toString();
    }
}
