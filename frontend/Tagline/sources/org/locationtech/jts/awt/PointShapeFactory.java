package org.locationtech.jts.awt;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public interface PointShapeFactory {
    Shape createPoint(Point2D point2D);

    public static abstract class BasePointShapeFactory implements PointShapeFactory {
        public static final double DEFAULT_SIZE = 3.0d;
        protected double size = 3.0d;

        public abstract Shape createPoint(Point2D point2D);

        public BasePointShapeFactory() {
        }

        public BasePointShapeFactory(double size2) {
            this.size = size2;
        }
    }

    public static class Point extends BasePointShapeFactory {
        public Point() {
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Point(double size) {
            super(size);
        }

        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.awt.Shape createPoint(java.awt.geom.Point2D r16) {
            /*
                r15 = this;
                r1 = r15
                r2 = r16
                java.awt.geom.Line2D$Double r4 = new java.awt.geom.Line2D$Double
                r14 = r4
                r4 = r14
                r5 = r14
                r6 = r2
                double r6 = r6.getX()
                r8 = r2
                double r8 = r8.getY()
                r10 = r2
                double r10 = r10.getX()
                r12 = r2
                double r12 = r12.getY()
                r5.<init>(r6, r8, r10, r12)
                r3 = r4
                r4 = r3
                r1 = r4
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.awt.PointShapeFactory.Point.createPoint(java.awt.geom.Point2D):java.awt.Shape");
        }
    }

    public static class Square extends BasePointShapeFactory {
        public Square() {
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Square(double size) {
            super(size);
        }

        public Shape createPoint(Point2D point2D) {
            Rectangle2D.Double doubleR;
            Point2D point = point2D;
            new Rectangle2D.Double(0.0d, 0.0d, this.size, this.size);
            Rectangle2D.Double pointMarker = doubleR;
            pointMarker.x = point.getX() - (this.size / 2.0d);
            pointMarker.y = point.getY() - (this.size / 2.0d);
            return pointMarker;
        }
    }

    public static class Star extends BasePointShapeFactory {
        public Star() {
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Star(double size) {
            super(size);
        }

        public Shape createPoint(Point2D point2D) {
            GeneralPath generalPath;
            Point2D point = point2D;
            new GeneralPath();
            GeneralPath path = generalPath;
            path.moveTo((float) point.getX(), (float) (point.getY() - (this.size / 2.0d)));
            path.lineTo((float) (point.getX() + ((this.size * 1.0d) / 8.0d)), (float) (point.getY() - ((this.size * 1.0d) / 8.0d)));
            path.lineTo((float) (point.getX() + (this.size / 2.0d)), (float) (point.getY() - ((this.size * 1.0d) / 8.0d)));
            path.lineTo((float) (point.getX() + ((this.size * 2.0d) / 8.0d)), (float) (point.getY() + ((this.size * 1.0d) / 8.0d)));
            path.lineTo((float) (point.getX() + ((this.size * 3.0d) / 8.0d)), (float) (point.getY() + (this.size / 2.0d)));
            path.lineTo((float) point.getX(), (float) (point.getY() + ((this.size * 2.0d) / 8.0d)));
            path.lineTo((float) (point.getX() - ((this.size * 3.0d) / 8.0d)), (float) (point.getY() + (this.size / 2.0d)));
            path.lineTo((float) (point.getX() - ((this.size * 2.0d) / 8.0d)), (float) (point.getY() + ((this.size * 1.0d) / 8.0d)));
            path.lineTo((float) (point.getX() - (this.size / 2.0d)), (float) (point.getY() - ((this.size * 1.0d) / 8.0d)));
            path.lineTo((float) (point.getX() - ((this.size * 1.0d) / 8.0d)), (float) (point.getY() - ((this.size * 1.0d) / 8.0d)));
            path.closePath();
            return path;
        }
    }

    public static class Triangle extends BasePointShapeFactory {
        public Triangle() {
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Triangle(double size) {
            super(size);
        }

        public Shape createPoint(Point2D point2D) {
            GeneralPath generalPath;
            Point2D point = point2D;
            new GeneralPath();
            GeneralPath path = generalPath;
            path.moveTo((float) point.getX(), (float) (point.getY() - (this.size / 2.0d)));
            path.lineTo((float) (point.getX() + (this.size / 2.0d)), (float) (point.getY() + (this.size / 2.0d)));
            path.lineTo((float) (point.getX() - (this.size / 2.0d)), (float) (point.getY() + (this.size / 2.0d)));
            path.lineTo((float) point.getX(), (float) (point.getY() - (this.size / 2.0d)));
            return path;
        }
    }

    public static class Circle extends BasePointShapeFactory {
        public Circle() {
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Circle(double size) {
            super(size);
        }

        public Shape createPoint(Point2D point2D) {
            Ellipse2D.Double doubleR;
            Point2D point = point2D;
            new Ellipse2D.Double(0.0d, 0.0d, this.size, this.size);
            Ellipse2D.Double pointMarker = doubleR;
            pointMarker.x = point.getX() - (this.size / 2.0d);
            pointMarker.y = point.getY() - (this.size / 2.0d);
            return pointMarker;
        }
    }

    public static class Cross extends BasePointShapeFactory {
        public Cross() {
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public Cross(double size) {
            super(size);
        }

        public Shape createPoint(Point2D point2D) {
            GeneralPath generalPath;
            Point2D point = point2D;
            float x1 = (float) (point.getX() - (this.size / 2.0d));
            float x2 = (float) (point.getX() - (this.size / 4.0d));
            float x3 = (float) (point.getX() + (this.size / 4.0d));
            float x4 = (float) (point.getX() + (this.size / 2.0d));
            float y1 = (float) (point.getY() - (this.size / 2.0d));
            float y2 = (float) (point.getY() - (this.size / 4.0d));
            float y3 = (float) (point.getY() + (this.size / 4.0d));
            float y4 = (float) (point.getY() + (this.size / 2.0d));
            new GeneralPath();
            GeneralPath path = generalPath;
            path.moveTo(x2, y1);
            path.lineTo(x3, y1);
            path.lineTo(x3, y2);
            path.lineTo(x4, y2);
            path.lineTo(x4, y3);
            path.lineTo(x3, y3);
            path.lineTo(x3, y4);
            path.lineTo(x2, y4);
            path.lineTo(x2, y3);
            path.lineTo(x1, y3);
            path.lineTo(x1, y2);
            path.lineTo(x2, y2);
            path.lineTo(x2, y1);
            return path;
        }
    }

    /* renamed from: org.locationtech.jts.awt.PointShapeFactory$X */
    public static class C1536X extends BasePointShapeFactory {
        public C1536X() {
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public C1536X(double size) {
            super(size);
        }

        public Shape createPoint(Point2D point2D) {
            GeneralPath generalPath;
            Point2D point = point2D;
            new GeneralPath();
            GeneralPath path = generalPath;
            path.moveTo((float) point.getX(), (float) (point.getY() - ((this.size * 1.0d) / 8.0d)));
            path.lineTo((float) (point.getX() + ((this.size * 2.0d) / 8.0d)), (float) (point.getY() - (this.size / 2.0d)));
            path.lineTo((float) (point.getX() + (this.size / 2.0d)), (float) (point.getY() - (this.size / 2.0d)));
            path.lineTo((float) (point.getX() + ((this.size * 1.0d) / 8.0d)), (float) point.getY());
            path.lineTo((float) (point.getX() + (this.size / 2.0d)), (float) (point.getY() + (this.size / 2.0d)));
            path.lineTo((float) (point.getX() + ((this.size * 2.0d) / 8.0d)), (float) (point.getY() + (this.size / 2.0d)));
            path.lineTo((float) point.getX(), (float) (point.getY() + ((this.size * 1.0d) / 8.0d)));
            path.lineTo((float) (point.getX() - ((this.size * 2.0d) / 8.0d)), (float) (point.getY() + (this.size / 2.0d)));
            path.lineTo((float) (point.getX() - (this.size / 2.0d)), (float) (point.getY() + (this.size / 2.0d)));
            path.lineTo((float) (point.getX() - ((this.size * 1.0d) / 8.0d)), (float) point.getY());
            path.lineTo((float) (point.getX() - (this.size / 2.0d)), (float) (point.getY() - (this.size / 2.0d)));
            path.lineTo((float) (point.getX() - ((this.size * 2.0d) / 8.0d)), (float) (point.getY() - (this.size / 2.0d)));
            path.closePath();
            return path;
        }
    }
}
