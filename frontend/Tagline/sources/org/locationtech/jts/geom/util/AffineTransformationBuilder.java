package org.locationtech.jts.geom.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.math.Matrix;

public class AffineTransformationBuilder {
    private Coordinate dest0;
    private Coordinate dest1;
    private Coordinate dest2;
    private double m00;
    private double m01;
    private double m02;
    private double m10;
    private double m11;
    private double m12;
    private Coordinate src0;
    private Coordinate src1;
    private Coordinate src2;

    public AffineTransformationBuilder(Coordinate src02, Coordinate src12, Coordinate src22, Coordinate dest02, Coordinate dest12, Coordinate dest22) {
        this.src0 = src02;
        this.src1 = src12;
        this.src2 = src22;
        this.dest0 = dest02;
        this.dest1 = dest12;
        this.dest2 = dest22;
    }

    public AffineTransformation getTransformation() {
        AffineTransformation affineTransformation;
        if (!compute()) {
            return null;
        }
        new AffineTransformation(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12);
        return affineTransformation;
    }

    private boolean compute() {
        double[] dArr = new double[3];
        dArr[0] = this.dest0.f412x;
        double[] dArr2 = dArr;
        dArr2[1] = this.dest1.f412x;
        double[] bx = dArr2;
        bx[2] = this.dest2.f412x;
        double[] row0 = solve(bx);
        if (row0 == null) {
            return false;
        }
        this.m00 = row0[0];
        this.m01 = row0[1];
        this.m02 = row0[2];
        double[] dArr3 = new double[3];
        dArr3[0] = this.dest0.f413y;
        double[] dArr4 = dArr3;
        dArr4[1] = this.dest1.f413y;
        double[] by = dArr4;
        by[2] = this.dest2.f413y;
        double[] row1 = solve(by);
        if (row1 == null) {
            return false;
        }
        this.m10 = row1[0];
        this.m11 = row1[1];
        this.m12 = row1[2];
        return true;
    }

    private double[] solve(double[] b) {
        double[][] dArr = new double[3][];
        double[][] dArr2 = dArr;
        double[][] dArr3 = dArr;
        double[] dArr4 = new double[3];
        dArr4[0] = this.src0.f412x;
        double[] dArr5 = dArr4;
        dArr5[1] = this.src0.f413y;
        double[] dArr6 = dArr5;
        dArr6[2] = 1.0d;
        dArr3[0] = dArr6;
        double[][] dArr7 = dArr2;
        double[][] dArr8 = dArr7;
        double[][] dArr9 = dArr7;
        double[] dArr10 = new double[3];
        dArr10[0] = this.src1.f412x;
        double[] dArr11 = dArr10;
        dArr11[1] = this.src1.f413y;
        double[] dArr12 = dArr11;
        dArr12[2] = 1.0d;
        dArr9[1] = dArr12;
        double[][] dArr13 = dArr8;
        double[][] a = dArr13;
        double[][] dArr14 = dArr13;
        double[] dArr15 = new double[3];
        dArr15[0] = this.src2.f412x;
        double[] dArr16 = dArr15;
        dArr16[1] = this.src2.f413y;
        double[] dArr17 = dArr16;
        dArr17[2] = 1.0d;
        dArr14[2] = dArr17;
        return Matrix.solve(a, b);
    }
}
