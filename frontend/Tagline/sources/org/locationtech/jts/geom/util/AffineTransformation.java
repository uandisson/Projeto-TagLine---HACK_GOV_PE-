package org.locationtech.jts.geom.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.CoordinateSequenceFilter;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.util.Assert;

public class AffineTransformation implements Cloneable, CoordinateSequenceFilter {
    private double m00;
    private double m01;
    private double m02;
    private double m10;
    private double m11;
    private double m12;

    public static AffineTransformation reflectionInstance(double x0, double y0, double x1, double y1) {
        AffineTransformation affineTransformation;
        new AffineTransformation();
        AffineTransformation trans = affineTransformation;
        AffineTransformation toReflection = trans.setToReflection(x0, y0, x1, y1);
        return trans;
    }

    public static AffineTransformation reflectionInstance(double x, double y) {
        AffineTransformation affineTransformation;
        new AffineTransformation();
        AffineTransformation trans = affineTransformation;
        AffineTransformation toReflection = trans.setToReflection(x, y);
        return trans;
    }

    public static AffineTransformation rotationInstance(double d) {
        double theta = d;
        return rotationInstance(Math.sin(theta), Math.cos(theta));
    }

    public static AffineTransformation rotationInstance(double sinTheta, double cosTheta) {
        AffineTransformation affineTransformation;
        new AffineTransformation();
        AffineTransformation trans = affineTransformation;
        AffineTransformation toRotation = trans.setToRotation(sinTheta, cosTheta);
        return trans;
    }

    public static AffineTransformation rotationInstance(double d, double x, double y) {
        double theta = d;
        return rotationInstance(Math.sin(theta), Math.cos(theta), x, y);
    }

    public static AffineTransformation rotationInstance(double sinTheta, double cosTheta, double x, double y) {
        AffineTransformation affineTransformation;
        new AffineTransformation();
        AffineTransformation trans = affineTransformation;
        AffineTransformation toRotation = trans.setToRotation(sinTheta, cosTheta, x, y);
        return trans;
    }

    public static AffineTransformation scaleInstance(double xScale, double yScale) {
        AffineTransformation affineTransformation;
        new AffineTransformation();
        AffineTransformation trans = affineTransformation;
        AffineTransformation toScale = trans.setToScale(xScale, yScale);
        return trans;
    }

    public static AffineTransformation scaleInstance(double xScale, double yScale, double d, double d2) {
        AffineTransformation affineTransformation;
        double x = d;
        double y = d2;
        new AffineTransformation();
        AffineTransformation trans = affineTransformation;
        AffineTransformation translate = trans.translate(-x, -y);
        AffineTransformation scale = trans.scale(xScale, yScale);
        AffineTransformation translate2 = trans.translate(x, y);
        return trans;
    }

    public static AffineTransformation shearInstance(double xShear, double yShear) {
        AffineTransformation affineTransformation;
        new AffineTransformation();
        AffineTransformation trans = affineTransformation;
        AffineTransformation toShear = trans.setToShear(xShear, yShear);
        return trans;
    }

    public static AffineTransformation translationInstance(double x, double y) {
        AffineTransformation affineTransformation;
        new AffineTransformation();
        AffineTransformation trans = affineTransformation;
        AffineTransformation toTranslation = trans.setToTranslation(x, y);
        return trans;
    }

    public AffineTransformation() {
        AffineTransformation toIdentity = setToIdentity();
    }

    public AffineTransformation(double[] dArr) {
        double[] matrix = dArr;
        this.m00 = matrix[0];
        this.m01 = matrix[1];
        this.m02 = matrix[2];
        this.m10 = matrix[3];
        this.m11 = matrix[4];
        this.m12 = matrix[5];
    }

    public AffineTransformation(double m002, double m012, double m022, double m102, double m112, double m122) {
        AffineTransformation transformation = setTransformation(m002, m012, m022, m102, m112, m122);
    }

    public AffineTransformation(AffineTransformation trans) {
        AffineTransformation transformation = setTransformation(trans);
    }

    public AffineTransformation(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3, Coordinate coordinate4, Coordinate coordinate5, Coordinate coordinate6) {
        Coordinate coordinate7 = coordinate;
        Coordinate coordinate8 = coordinate2;
        Coordinate coordinate9 = coordinate3;
        Coordinate coordinate10 = coordinate4;
        Coordinate coordinate11 = coordinate5;
        Coordinate coordinate12 = coordinate6;
    }

    public AffineTransformation setToIdentity() {
        this.m00 = 1.0d;
        this.m01 = 0.0d;
        this.m02 = 0.0d;
        this.m10 = 0.0d;
        this.m11 = 1.0d;
        this.m12 = 0.0d;
        return this;
    }

    public AffineTransformation setTransformation(double m002, double m012, double m022, double m102, double m112, double m122) {
        this.m00 = m002;
        this.m01 = m012;
        this.m02 = m022;
        this.m10 = m102;
        this.m11 = m112;
        this.m12 = m122;
        return this;
    }

    public AffineTransformation setTransformation(AffineTransformation affineTransformation) {
        AffineTransformation trans = affineTransformation;
        this.m00 = trans.m00;
        this.m01 = trans.m01;
        this.m02 = trans.m02;
        this.m10 = trans.m10;
        this.m11 = trans.m11;
        this.m12 = trans.m12;
        return this;
    }

    public double[] getMatrixEntries() {
        double[] dArr = new double[6];
        dArr[0] = this.m00;
        double[] dArr2 = dArr;
        dArr2[1] = this.m01;
        double[] dArr3 = dArr2;
        dArr3[2] = this.m02;
        double[] dArr4 = dArr3;
        dArr4[3] = this.m10;
        double[] dArr5 = dArr4;
        dArr5[4] = this.m11;
        double[] dArr6 = dArr5;
        dArr6[5] = this.m12;
        return dArr6;
    }

    public double getDeterminant() {
        return (this.m00 * this.m11) - (this.m01 * this.m10);
    }

    public AffineTransformation getInverse() throws NoninvertibleTransformationException {
        AffineTransformation affineTransformation;
        Throwable th;
        double det = getDeterminant();
        if (det == 0.0d) {
            Throwable th2 = th;
            new NoninvertibleTransformationException("Transformation is non-invertible");
            throw th2;
        }
        double im00 = this.m11 / det;
        double im10 = (-this.m10) / det;
        double im01 = (-this.m01) / det;
        double im11 = this.m00 / det;
        new AffineTransformation(im00, im01, ((this.m01 * this.m12) - (this.m02 * this.m11)) / det, im10, im11, (((-this.m00) * this.m12) + (this.m10 * this.m02)) / det);
        return affineTransformation;
    }

    public AffineTransformation setToReflectionBasic(double d, double d2, double d3, double d4) {
        Throwable th;
        double x0 = d;
        double y0 = d2;
        double x1 = d3;
        double y1 = d4;
        if (x0 == x1 && y0 == y1) {
            Throwable th2 = th;
            new IllegalArgumentException("Reflection line points must be distinct");
            throw th2;
        }
        double dx = x1 - x0;
        double dy = y1 - y0;
        double d5 = Math.sqrt((dx * dx) + (dy * dy));
        double sin = dy / d5;
        double cos = dx / d5;
        double cs2 = 2.0d * sin * cos;
        double c2s2 = (cos * cos) - (sin * sin);
        this.m00 = c2s2;
        this.m01 = cs2;
        this.m02 = 0.0d;
        this.m10 = cs2;
        this.m11 = -c2s2;
        this.m12 = 0.0d;
        return this;
    }

    public AffineTransformation setToReflection(double d, double d2, double d3, double d4) {
        Throwable th;
        double x0 = d;
        double y0 = d2;
        double x1 = d3;
        double y1 = d4;
        if (x0 == x1 && y0 == y1) {
            Throwable th2 = th;
            new IllegalArgumentException("Reflection line points must be distinct");
            throw th2;
        }
        AffineTransformation toTranslation = setToTranslation(-x0, -y0);
        double dx = x1 - x0;
        double dy = y1 - y0;
        double d5 = Math.sqrt((dx * dx) + (dy * dy));
        double sin = dy / d5;
        double cos = dx / d5;
        AffineTransformation rotate = rotate(-sin, cos);
        AffineTransformation scale = scale(1.0d, -1.0d);
        AffineTransformation rotate2 = rotate(sin, cos);
        AffineTransformation translate = translate(x0, y0);
        return this;
    }

    public AffineTransformation setToReflection(double d, double d2) {
        Throwable th;
        double x = d;
        double y = d2;
        if (x == 0.0d && y == 0.0d) {
            Throwable th2 = th;
            new IllegalArgumentException("Reflection vector must be non-zero");
            throw th2;
        } else if (x == y) {
            this.m00 = 0.0d;
            this.m01 = 1.0d;
            this.m02 = 0.0d;
            this.m10 = 1.0d;
            this.m11 = 0.0d;
            this.m12 = 0.0d;
            return this;
        } else {
            double d3 = Math.sqrt((x * x) + (y * y));
            double sin = y / d3;
            double cos = x / d3;
            AffineTransformation rotate = rotate(-sin, cos);
            AffineTransformation scale = scale(1.0d, -1.0d);
            AffineTransformation rotate2 = rotate(sin, cos);
            return this;
        }
    }

    public AffineTransformation setToRotation(double d) {
        double theta = d;
        AffineTransformation toRotation = setToRotation(Math.sin(theta), Math.cos(theta));
        return this;
    }

    public AffineTransformation setToRotation(double d, double d2) {
        double sinTheta = d;
        double cosTheta = d2;
        this.m00 = cosTheta;
        this.m01 = -sinTheta;
        this.m02 = 0.0d;
        this.m10 = sinTheta;
        this.m11 = cosTheta;
        this.m12 = 0.0d;
        return this;
    }

    public AffineTransformation setToRotation(double d, double x, double y) {
        double theta = d;
        AffineTransformation toRotation = setToRotation(Math.sin(theta), Math.cos(theta), x, y);
        return this;
    }

    public AffineTransformation setToRotation(double d, double d2, double d3, double d4) {
        double sinTheta = d;
        double cosTheta = d2;
        double x = d3;
        double y = d4;
        this.m00 = cosTheta;
        this.m01 = -sinTheta;
        this.m02 = (x - (x * cosTheta)) + (y * sinTheta);
        this.m10 = sinTheta;
        this.m11 = cosTheta;
        this.m12 = (y - (x * sinTheta)) - (y * cosTheta);
        return this;
    }

    public AffineTransformation setToScale(double xScale, double yScale) {
        this.m00 = xScale;
        this.m01 = 0.0d;
        this.m02 = 0.0d;
        this.m10 = 0.0d;
        this.m11 = yScale;
        this.m12 = 0.0d;
        return this;
    }

    public AffineTransformation setToShear(double xShear, double yShear) {
        this.m00 = 1.0d;
        this.m01 = xShear;
        this.m02 = 0.0d;
        this.m10 = yShear;
        this.m11 = 1.0d;
        this.m12 = 0.0d;
        return this;
    }

    public AffineTransformation setToTranslation(double dx, double dy) {
        this.m00 = 1.0d;
        this.m01 = 0.0d;
        this.m02 = dx;
        this.m10 = 0.0d;
        this.m11 = 1.0d;
        this.m12 = dy;
        return this;
    }

    public AffineTransformation reflect(double x0, double y0, double x1, double y1) {
        AffineTransformation compose = compose(reflectionInstance(x0, y0, x1, y1));
        return this;
    }

    public AffineTransformation reflect(double x, double y) {
        AffineTransformation compose = compose(reflectionInstance(x, y));
        return this;
    }

    public AffineTransformation rotate(double theta) {
        AffineTransformation compose = compose(rotationInstance(theta));
        return this;
    }

    public AffineTransformation rotate(double sinTheta, double cosTheta) {
        AffineTransformation compose = compose(rotationInstance(sinTheta, cosTheta));
        return this;
    }

    public AffineTransformation rotate(double theta, double x, double y) {
        AffineTransformation compose = compose(rotationInstance(theta, x, y));
        return this;
    }

    public AffineTransformation rotate(double sinTheta, double cosTheta, double d, double d2) {
        double d3 = d;
        double d4 = d2;
        AffineTransformation compose = compose(rotationInstance(sinTheta, cosTheta));
        return this;
    }

    public AffineTransformation scale(double xScale, double yScale) {
        AffineTransformation compose = compose(scaleInstance(xScale, yScale));
        return this;
    }

    public AffineTransformation shear(double xShear, double yShear) {
        AffineTransformation compose = compose(shearInstance(xShear, yShear));
        return this;
    }

    public AffineTransformation translate(double x, double y) {
        AffineTransformation compose = compose(translationInstance(x, y));
        return this;
    }

    public AffineTransformation compose(AffineTransformation affineTransformation) {
        AffineTransformation trans = affineTransformation;
        double mp00 = (trans.m00 * this.m00) + (trans.m01 * this.m10);
        double mp01 = (trans.m00 * this.m01) + (trans.m01 * this.m11);
        double mp02 = (trans.m00 * this.m02) + (trans.m01 * this.m12) + trans.m02;
        double mp10 = (trans.m10 * this.m00) + (trans.m11 * this.m10);
        double mp11 = (trans.m10 * this.m01) + (trans.m11 * this.m11);
        double mp12 = (trans.m10 * this.m02) + (trans.m11 * this.m12) + trans.m12;
        this.m00 = mp00;
        this.m01 = mp01;
        this.m02 = mp02;
        this.m10 = mp10;
        this.m11 = mp11;
        this.m12 = mp12;
        return this;
    }

    public AffineTransformation composeBefore(AffineTransformation affineTransformation) {
        AffineTransformation trans = affineTransformation;
        double mp00 = (this.m00 * trans.m00) + (this.m01 * trans.m10);
        double mp01 = (this.m00 * trans.m01) + (this.m01 * trans.m11);
        double mp02 = (this.m00 * trans.m02) + (this.m01 * trans.m12) + this.m02;
        double mp10 = (this.m10 * trans.m00) + (this.m11 * trans.m10);
        double mp11 = (this.m10 * trans.m01) + (this.m11 * trans.m11);
        double mp12 = (this.m10 * trans.m02) + (this.m11 * trans.m12) + this.m12;
        this.m00 = mp00;
        this.m01 = mp01;
        this.m02 = mp02;
        this.m10 = mp10;
        this.m11 = mp11;
        this.m12 = mp12;
        return this;
    }

    public Coordinate transform(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate src = coordinate;
        Coordinate dest = coordinate2;
        double xp = (this.m00 * src.f412x) + (this.m01 * src.f413y) + this.m02;
        double yp = (this.m10 * src.f412x) + (this.m11 * src.f413y) + this.m12;
        dest.f412x = xp;
        dest.f413y = yp;
        return dest;
    }

    public Geometry transform(Geometry g) {
        Geometry g2 = (Geometry) g.clone();
        g2.apply((CoordinateSequenceFilter) this);
        return g2;
    }

    public void transform(CoordinateSequence coordinateSequence, int i) {
        CoordinateSequence seq = coordinateSequence;
        int i2 = i;
        double xp = (this.m00 * seq.getOrdinate(i2, 0)) + (this.m01 * seq.getOrdinate(i2, 1)) + this.m02;
        double yp = (this.m10 * seq.getOrdinate(i2, 0)) + (this.m11 * seq.getOrdinate(i2, 1)) + this.m12;
        seq.setOrdinate(i2, 0, xp);
        seq.setOrdinate(i2, 1, yp);
    }

    public void filter(CoordinateSequence seq, int i) {
        transform(seq, i);
    }

    public boolean isGeometryChanged() {
        return true;
    }

    public boolean isDone() {
        return false;
    }

    public boolean isIdentity() {
        return this.m00 == 1.0d && this.m01 == 0.0d && this.m02 == 0.0d && this.m10 == 0.0d && this.m11 == 1.0d && this.m12 == 0.0d;
    }

    public boolean equals(Object obj) {
        Object obj2 = obj;
        if (obj2 == null) {
            return false;
        }
        if (!(obj2 instanceof AffineTransformation)) {
            return false;
        }
        AffineTransformation trans = (AffineTransformation) obj2;
        return this.m00 == trans.m00 && this.m01 == trans.m01 && this.m02 == trans.m02 && this.m10 == trans.m10 && this.m11 == trans.m11 && this.m12 == trans.m12;
    }

    public String toString() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("AffineTransformation[[").append(this.m00).append(", ").append(this.m01).append(", ").append(this.m02).append("], [").append(this.m10).append(", ").append(this.m11).append(", ").append(this.m12).append("]]").toString();
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (Exception e) {
            Exception exc = e;
            Assert.shouldNeverReachHere();
            return null;
        }
    }
}
