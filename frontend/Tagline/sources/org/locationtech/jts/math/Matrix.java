package org.locationtech.jts.math;

public class Matrix {
    public Matrix() {
    }

    private static void swapRows(double[][] dArr, int i, int i2) {
        double[][] m = dArr;
        int i3 = i;
        int j = i2;
        if (i3 != j) {
            for (int col = 0; col < m[0].length; col++) {
                double temp = m[i3][col];
                m[i3][col] = m[j][col];
                m[j][col] = temp;
            }
        }
    }

    private static void swapRows(double[] dArr, int i, int i2) {
        double[] m = dArr;
        int i3 = i;
        int j = i2;
        if (i3 != j) {
            double temp = m[i3];
            m[i3] = m[j];
            m[j] = temp;
        }
    }

    public static double[] solve(double[][] dArr, double[] dArr2) {
        Throwable th;
        double[][] a = dArr;
        double[] b = dArr2;
        int n = b.length;
        if (a.length == n && a[0].length == n) {
            for (int i = 0; i < n; i++) {
                int maxElementRow = i;
                for (int j = i + 1; j < n; j++) {
                    if (Math.abs(a[j][i]) > Math.abs(a[maxElementRow][i])) {
                        maxElementRow = j;
                    }
                }
                if (a[maxElementRow][i] == 0.0d) {
                    return null;
                }
                swapRows(a, i, maxElementRow);
                swapRows(b, i, maxElementRow);
                for (int j2 = i + 1; j2 < n; j2++) {
                    double rowFactor = a[j2][i] / a[i][i];
                    for (int k = n - 1; k >= i; k--) {
                        double[] dArr3 = a[j2];
                        int i2 = k;
                        dArr3[i2] = dArr3[i2] - (a[i][k] * rowFactor);
                    }
                    double[] dArr4 = b;
                    int i3 = j2;
                    dArr4[i3] = dArr4[i3] - (b[i] * rowFactor);
                }
            }
            double[] solution = new double[n];
            for (int j3 = n - 1; j3 >= 0; j3--) {
                double t = 0.0d;
                for (int k2 = j3 + 1; k2 < n; k2++) {
                    t += a[j3][k2] * solution[k2];
                }
                solution[j3] = (b[j3] - t) / a[j3][j3];
            }
            return solution;
        }
        Throwable th2 = th;
        new IllegalArgumentException("Matrix A is incorrectly sized");
        throw th2;
    }
}
