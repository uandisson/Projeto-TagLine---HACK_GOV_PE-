package org.locationtech.jts.geom;

import java.lang.reflect.Array;

public class IntersectionMatrix implements Cloneable {
    private int[][] matrix;

    public IntersectionMatrix() {
        this.matrix = (int[][]) Array.newInstance(Integer.TYPE, new int[]{3, 3});
        setAll(-1);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public IntersectionMatrix(String elements) {
        this();
        set(elements);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public IntersectionMatrix(IntersectionMatrix intersectionMatrix) {
        this();
        IntersectionMatrix other = intersectionMatrix;
        this.matrix[0][0] = other.matrix[0][0];
        this.matrix[0][1] = other.matrix[0][1];
        this.matrix[0][2] = other.matrix[0][2];
        this.matrix[1][0] = other.matrix[1][0];
        this.matrix[1][1] = other.matrix[1][1];
        this.matrix[1][2] = other.matrix[1][2];
        this.matrix[2][0] = other.matrix[2][0];
        this.matrix[2][1] = other.matrix[2][1];
        this.matrix[2][2] = other.matrix[2][2];
    }

    public void add(IntersectionMatrix intersectionMatrix) {
        IntersectionMatrix im = intersectionMatrix;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                setAtLeast(i, j, im.get(i, j));
            }
        }
    }

    public static boolean isTrue(int i) {
        int actualDimensionValue = i;
        if (actualDimensionValue >= 0 || actualDimensionValue == -2) {
            return true;
        }
        return false;
    }

    public static boolean matches(int i, char c) {
        int actualDimensionValue = i;
        char requiredDimensionSymbol = c;
        if (requiredDimensionSymbol == '*') {
            return true;
        }
        if (requiredDimensionSymbol == 'T' && (actualDimensionValue >= 0 || actualDimensionValue == -2)) {
            return true;
        }
        if (requiredDimensionSymbol == 'F' && actualDimensionValue == -1) {
            return true;
        }
        if (requiredDimensionSymbol == '0' && actualDimensionValue == 0) {
            return true;
        }
        if (requiredDimensionSymbol == '1' && actualDimensionValue == 1) {
            return true;
        }
        if (requiredDimensionSymbol == '2' && actualDimensionValue == 2) {
            return true;
        }
        return false;
    }

    public static boolean matches(String actualDimensionSymbols, String requiredDimensionSymbols) {
        IntersectionMatrix m;
        new IntersectionMatrix(actualDimensionSymbols);
        return m.matches(requiredDimensionSymbols);
    }

    public void set(int row, int column, int dimensionValue) {
        this.matrix[row][column] = dimensionValue;
    }

    public void set(String str) {
        String dimensionSymbols = str;
        for (int i = 0; i < dimensionSymbols.length(); i++) {
            this.matrix[i / 3][i % 3] = Dimension.toDimensionValue(dimensionSymbols.charAt(i));
        }
    }

    public void setAtLeast(int i, int i2, int i3) {
        int row = i;
        int column = i2;
        int minimumDimensionValue = i3;
        if (this.matrix[row][column] < minimumDimensionValue) {
            this.matrix[row][column] = minimumDimensionValue;
        }
    }

    public void setAtLeastIfValid(int i, int i2, int i3) {
        int row = i;
        int column = i2;
        int minimumDimensionValue = i3;
        if (row >= 0 && column >= 0) {
            setAtLeast(row, column, minimumDimensionValue);
        }
    }

    public void setAtLeast(String str) {
        String minimumDimensionSymbols = str;
        for (int i = 0; i < minimumDimensionSymbols.length(); i++) {
            setAtLeast(i / 3, i % 3, Dimension.toDimensionValue(minimumDimensionSymbols.charAt(i)));
        }
    }

    public void setAll(int i) {
        int dimensionValue = i;
        for (int ai = 0; ai < 3; ai++) {
            for (int bi = 0; bi < 3; bi++) {
                this.matrix[ai][bi] = dimensionValue;
            }
        }
    }

    public int get(int row, int column) {
        return this.matrix[row][column];
    }

    public boolean isDisjoint() {
        return this.matrix[0][0] == -1 && this.matrix[0][1] == -1 && this.matrix[1][0] == -1 && this.matrix[1][1] == -1;
    }

    public boolean isIntersects() {
        return !isDisjoint();
    }

    public boolean isTouches(int i, int i2) {
        int dimensionOfGeometryA = i;
        int dimensionOfGeometryB = i2;
        if (dimensionOfGeometryA > dimensionOfGeometryB) {
            return isTouches(dimensionOfGeometryB, dimensionOfGeometryA);
        }
        if ((dimensionOfGeometryA != 2 || dimensionOfGeometryB != 2) && ((dimensionOfGeometryA != 1 || dimensionOfGeometryB != 1) && ((dimensionOfGeometryA != 1 || dimensionOfGeometryB != 2) && ((dimensionOfGeometryA != 0 || dimensionOfGeometryB != 2) && (dimensionOfGeometryA != 0 || dimensionOfGeometryB != 1))))) {
            return false;
        }
        return this.matrix[0][0] == -1 && (isTrue(this.matrix[0][1]) || isTrue(this.matrix[1][0]) || isTrue(this.matrix[1][1]));
    }

    public boolean isCrosses(int i, int i2) {
        int dimensionOfGeometryA = i;
        int dimensionOfGeometryB = i2;
        if ((dimensionOfGeometryA == 0 && dimensionOfGeometryB == 1) || ((dimensionOfGeometryA == 0 && dimensionOfGeometryB == 2) || (dimensionOfGeometryA == 1 && dimensionOfGeometryB == 2))) {
            return isTrue(this.matrix[0][0]) && isTrue(this.matrix[0][2]);
        } else if ((dimensionOfGeometryA == 1 && dimensionOfGeometryB == 0) || ((dimensionOfGeometryA == 2 && dimensionOfGeometryB == 0) || (dimensionOfGeometryA == 2 && dimensionOfGeometryB == 1))) {
            return isTrue(this.matrix[0][0]) && isTrue(this.matrix[2][0]);
        } else if (dimensionOfGeometryA != 1 || dimensionOfGeometryB != 1) {
            return false;
        } else {
            return this.matrix[0][0] == 0;
        }
    }

    public boolean isWithin() {
        return isTrue(this.matrix[0][0]) && this.matrix[0][2] == -1 && this.matrix[1][2] == -1;
    }

    public boolean isContains() {
        return isTrue(this.matrix[0][0]) && this.matrix[2][0] == -1 && this.matrix[2][1] == -1;
    }

    public boolean isCovers() {
        return (isTrue(this.matrix[0][0]) || isTrue(this.matrix[0][1]) || isTrue(this.matrix[1][0]) || isTrue(this.matrix[1][1])) && this.matrix[2][0] == -1 && this.matrix[2][1] == -1;
    }

    public boolean isCoveredBy() {
        return (isTrue(this.matrix[0][0]) || isTrue(this.matrix[0][1]) || isTrue(this.matrix[1][0]) || isTrue(this.matrix[1][1])) && this.matrix[0][2] == -1 && this.matrix[1][2] == -1;
    }

    public boolean isEquals(int dimensionOfGeometryA, int dimensionOfGeometryB) {
        if (dimensionOfGeometryA != dimensionOfGeometryB) {
            return false;
        }
        return isTrue(this.matrix[0][0]) && this.matrix[0][2] == -1 && this.matrix[1][2] == -1 && this.matrix[2][0] == -1 && this.matrix[2][1] == -1;
    }

    public boolean isOverlaps(int i, int i2) {
        int dimensionOfGeometryA = i;
        int dimensionOfGeometryB = i2;
        if ((dimensionOfGeometryA == 0 && dimensionOfGeometryB == 0) || (dimensionOfGeometryA == 2 && dimensionOfGeometryB == 2)) {
            return isTrue(this.matrix[0][0]) && isTrue(this.matrix[0][2]) && isTrue(this.matrix[2][0]);
        } else if (dimensionOfGeometryA != 1 || dimensionOfGeometryB != 1) {
            return false;
        } else {
            return this.matrix[0][0] == 1 && isTrue(this.matrix[0][2]) && isTrue(this.matrix[2][0]);
        }
    }

    public boolean matches(String str) {
        Throwable th;
        StringBuilder sb;
        String requiredDimensionSymbols = str;
        if (requiredDimensionSymbols.length() != 9) {
            Throwable th2 = th;
            new StringBuilder();
            new IllegalArgumentException(sb.append("Should be length 9: ").append(requiredDimensionSymbols).toString());
            throw th2;
        }
        for (int ai = 0; ai < 3; ai++) {
            for (int bi = 0; bi < 3; bi++) {
                if (!matches(this.matrix[ai][bi], requiredDimensionSymbols.charAt((3 * ai) + bi))) {
                    return false;
                }
            }
        }
        return true;
    }

    public IntersectionMatrix transpose() {
        int temp = this.matrix[1][0];
        this.matrix[1][0] = this.matrix[0][1];
        this.matrix[0][1] = temp;
        int temp2 = this.matrix[2][0];
        this.matrix[2][0] = this.matrix[0][2];
        this.matrix[0][2] = temp2;
        int temp3 = this.matrix[2][1];
        this.matrix[2][1] = this.matrix[1][2];
        this.matrix[1][2] = temp3;
        return this;
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder("123456789");
        StringBuilder builder = sb;
        for (int ai = 0; ai < 3; ai++) {
            for (int bi = 0; bi < 3; bi++) {
                builder.setCharAt((3 * ai) + bi, Dimension.toDimensionSymbol(this.matrix[ai][bi]));
            }
        }
        return builder.toString();
    }
}
