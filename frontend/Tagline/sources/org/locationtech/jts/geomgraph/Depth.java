package org.locationtech.jts.geomgraph;

import java.lang.reflect.Array;

public class Depth {
    private static final int NULL_VALUE = -1;
    private int[][] depth = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{2, 3}));

    public static int depthAtLocation(int i) {
        int location = i;
        if (location == 2) {
            return 0;
        }
        if (location == 0) {
            return 1;
        }
        return -1;
    }

    public Depth() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                this.depth[i][j] = -1;
            }
        }
    }

    public int getDepth(int geomIndex, int posIndex) {
        return this.depth[geomIndex][posIndex];
    }

    public void setDepth(int geomIndex, int posIndex, int depthValue) {
        this.depth[geomIndex][posIndex] = depthValue;
    }

    public int getLocation(int geomIndex, int posIndex) {
        if (this.depth[geomIndex][posIndex] <= 0) {
            return 2;
        }
        return 0;
    }

    public void add(int i, int i2, int location) {
        int geomIndex = i;
        int posIndex = i2;
        if (location == 0) {
            int[] iArr = this.depth[geomIndex];
            int i3 = posIndex;
            iArr[i3] = iArr[i3] + 1;
        }
    }

    public boolean isNull() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.depth[i][j] != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isNull(int geomIndex) {
        return this.depth[geomIndex][1] == -1;
    }

    public boolean isNull(int geomIndex, int posIndex) {
        return this.depth[geomIndex][posIndex] == -1;
    }

    public void add(Label label) {
        Label lbl = label;
        for (int i = 0; i < 2; i++) {
            for (int j = 1; j < 3; j++) {
                int loc = lbl.getLocation(i, j);
                if (loc == 2 || loc == 0) {
                    if (isNull(i, j)) {
                        this.depth[i][j] = depthAtLocation(loc);
                    } else {
                        int[] iArr = this.depth[i];
                        int i2 = j;
                        iArr[i2] = iArr[i2] + depthAtLocation(loc);
                    }
                }
            }
        }
    }

    public int getDelta(int i) {
        int geomIndex = i;
        return this.depth[geomIndex][2] - this.depth[geomIndex][1];
    }

    public void normalize() {
        for (int i = 0; i < 2; i++) {
            if (!isNull(i)) {
                int minDepth = this.depth[i][1];
                if (this.depth[i][2] < minDepth) {
                    minDepth = this.depth[i][2];
                }
                if (minDepth < 0) {
                    minDepth = 0;
                }
                for (int j = 1; j < 3; j++) {
                    int newValue = 0;
                    if (this.depth[i][j] > minDepth) {
                        newValue = 1;
                    }
                    this.depth[i][j] = newValue;
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("A: ").append(this.depth[0][1]).append(",").append(this.depth[0][2]).append(" B: ").append(this.depth[1][1]).append(",").append(this.depth[1][2]).toString();
    }
}
