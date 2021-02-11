package org.locationtech.jts.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequenceFilter;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.util.AffineTransformation;

public class GeometricShapeFactory {
    protected Dimensions dim;
    protected GeometryFactory geomFact;
    protected int nPts;
    protected PrecisionModel precModel;
    protected double rotationAngle;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public GeometricShapeFactory() {
        /*
            r5 = this;
            r0 = r5
            r1 = r0
            org.locationtech.jts.geom.GeometryFactory r2 = new org.locationtech.jts.geom.GeometryFactory
            r4 = r2
            r2 = r4
            r3 = r4
            r3.<init>()
            r1.<init>(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.util.GeometricShapeFactory.<init>():void");
    }

    public GeometricShapeFactory(GeometryFactory geometryFactory) {
        Dimensions dimensions;
        GeometryFactory geomFact2 = geometryFactory;
        this.precModel = null;
        new Dimensions(this);
        this.dim = dimensions;
        this.nPts = 100;
        this.rotationAngle = 0.0d;
        this.geomFact = geomFact2;
        this.precModel = geomFact2.getPrecisionModel();
    }

    public void setEnvelope(Envelope env) {
        this.dim.setEnvelope(env);
    }

    public void setBase(Coordinate base) {
        this.dim.setBase(base);
    }

    public void setCentre(Coordinate centre) {
        this.dim.setCentre(centre);
    }

    public void setNumPoints(int nPts2) {
        int i = nPts2;
        this.nPts = i;
    }

    public void setSize(double size) {
        this.dim.setSize(size);
    }

    public void setWidth(double width) {
        this.dim.setWidth(width);
    }

    public void setHeight(double height) {
        this.dim.setHeight(height);
    }

    public void setRotation(double radians) {
        double d = radians;
        this.rotationAngle = d;
    }

    /* access modifiers changed from: protected */
    public Geometry rotate(Geometry geometry) {
        Geometry geom = geometry;
        if (this.rotationAngle != 0.0d) {
            geom.apply((CoordinateSequenceFilter) AffineTransformation.rotationInstance(this.rotationAngle, this.dim.getCentre().f412x, this.dim.getCentre().f413y));
        }
        return geom;
    }

    public Polygon createRectangle() {
        Coordinate coordinate;
        int ipt = 0;
        int nSide = this.nPts / 4;
        if (nSide < 1) {
            nSide = 1;
        }
        double XsegLen = this.dim.getEnvelope().getWidth() / ((double) nSide);
        double YsegLen = this.dim.getEnvelope().getHeight() / ((double) nSide);
        Coordinate[] pts = new Coordinate[((4 * nSide) + 1)];
        Envelope env = this.dim.getEnvelope();
        for (int i = 0; i < nSide; i++) {
            int i2 = ipt;
            ipt++;
            pts[i2] = coord(env.getMinX() + (((double) i) * XsegLen), env.getMinY());
        }
        for (int i3 = 0; i3 < nSide; i3++) {
            int i4 = ipt;
            ipt++;
            pts[i4] = coord(env.getMaxX(), env.getMinY() + (((double) i3) * YsegLen));
        }
        for (int i5 = 0; i5 < nSide; i5++) {
            int i6 = ipt;
            ipt++;
            pts[i6] = coord(env.getMaxX() - (((double) i5) * XsegLen), env.getMaxY());
        }
        for (int i7 = 0; i7 < nSide; i7++) {
            int i8 = ipt;
            ipt++;
            pts[i8] = coord(env.getMinX(), env.getMaxY() - (((double) i7) * YsegLen));
        }
        int i9 = ipt;
        int ipt2 = ipt + 1;
        new Coordinate(pts[0]);
        pts[i9] = coordinate;
        return (Polygon) rotate(this.geomFact.createPolygon(this.geomFact.createLinearRing(pts), (LinearRing[]) null));
    }

    public Polygon createCircle() {
        return createEllipse();
    }

    public Polygon createEllipse() {
        Coordinate coordinate;
        Envelope env = this.dim.getEnvelope();
        double xRadius = env.getWidth() / 2.0d;
        double yRadius = env.getHeight() / 2.0d;
        double centreX = env.getMinX() + xRadius;
        double centreY = env.getMinY() + yRadius;
        Coordinate[] pts = new Coordinate[(this.nPts + 1)];
        int iPt = 0;
        int i = 0;
        while (true) {
            if (i < this.nPts) {
                double ang = ((double) i) * (6.283185307179586d / ((double) this.nPts));
                int i2 = iPt;
                iPt++;
                pts[i2] = coord((xRadius * Math.cos(ang)) + centreX, (yRadius * Math.sin(ang)) + centreY);
                i++;
            } else {
                new Coordinate(pts[0]);
                pts[iPt] = coordinate;
                return (Polygon) rotate(this.geomFact.createPolygon(this.geomFact.createLinearRing(pts), (LinearRing[]) null));
            }
        }
    }

    public Polygon createSquircle() {
        return createSupercircle(4.0d);
    }

    public Polygon createSupercircle(double d) {
        Coordinate coordinate;
        double power = d;
        double recipPow = 1.0d / power;
        double radius = this.dim.getMinSize() / 2.0d;
        Coordinate centre = this.dim.getCentre();
        double r4 = Math.pow(radius, power);
        double y0 = radius;
        double xyInt = Math.pow(r4 / 2.0d, recipPow);
        int nSegsInOct = this.nPts / 8;
        Coordinate[] pts = new Coordinate[((nSegsInOct * 8) + 1)];
        double xInc = xyInt / ((double) nSegsInOct);
        for (int i = 0; i <= nSegsInOct; i++) {
            double x = 0.0d;
            double y = y0;
            if (i != 0) {
                x = xInc * ((double) i);
                y = Math.pow(r4 - Math.pow(x, power), recipPow);
            }
            pts[i] = coordTrans(x, y, centre);
            pts[(2 * nSegsInOct) - i] = coordTrans(y, x, centre);
            pts[(2 * nSegsInOct) + i] = coordTrans(y, -x, centre);
            pts[(4 * nSegsInOct) - i] = coordTrans(x, -y, centre);
            pts[(4 * nSegsInOct) + i] = coordTrans(-x, -y, centre);
            pts[(6 * nSegsInOct) - i] = coordTrans(-y, -x, centre);
            pts[(6 * nSegsInOct) + i] = coordTrans(-y, x, centre);
            pts[(8 * nSegsInOct) - i] = coordTrans(-x, y, centre);
        }
        new Coordinate(pts[0]);
        pts[pts.length - 1] = coordinate;
        return (Polygon) rotate(this.geomFact.createPolygon(this.geomFact.createLinearRing(pts), (LinearRing[]) null));
    }

    public LineString createArc(double d, double angExtent) {
        double startAng = d;
        Envelope env = this.dim.getEnvelope();
        double xRadius = env.getWidth() / 2.0d;
        double yRadius = env.getHeight() / 2.0d;
        double centreX = env.getMinX() + xRadius;
        double centreY = env.getMinY() + yRadius;
        double angSize = angExtent;
        if (angSize <= 0.0d || angSize > 6.283185307179586d) {
            angSize = 6.283185307179586d;
        }
        double angInc = angSize / ((double) (this.nPts - 1));
        Coordinate[] pts = new Coordinate[this.nPts];
        int iPt = 0;
        int i = 0;
        while (true) {
            if (i < this.nPts) {
                double ang = startAng + (((double) i) * angInc);
                int i2 = iPt;
                iPt++;
                pts[i2] = coord((xRadius * Math.cos(ang)) + centreX, (yRadius * Math.sin(ang)) + centreY);
                i++;
            } else {
                return (LineString) rotate(this.geomFact.createLineString(pts));
            }
        }
    }

    public Polygon createArcPolygon(double d, double angExtent) {
        double startAng = d;
        Envelope env = this.dim.getEnvelope();
        double xRadius = env.getWidth() / 2.0d;
        double yRadius = env.getHeight() / 2.0d;
        double centreX = env.getMinX() + xRadius;
        double centreY = env.getMinY() + yRadius;
        double angSize = angExtent;
        if (angSize <= 0.0d || angSize > 6.283185307179586d) {
            angSize = 6.283185307179586d;
        }
        double angInc = angSize / ((double) (this.nPts - 1));
        Coordinate[] pts = new Coordinate[(this.nPts + 2)];
        int iPt = 0 + 1;
        pts[0] = coord(centreX, centreY);
        int i = 0;
        while (true) {
            if (i < this.nPts) {
                double ang = startAng + (angInc * ((double) i));
                int i2 = iPt;
                iPt++;
                pts[i2] = coord((xRadius * Math.cos(ang)) + centreX, (yRadius * Math.sin(ang)) + centreY);
                i++;
            } else {
                int i3 = iPt;
                int iPt2 = iPt + 1;
                pts[i3] = coord(centreX, centreY);
                return (Polygon) rotate(this.geomFact.createPolygon(this.geomFact.createLinearRing(pts), (LinearRing[]) null));
            }
        }
    }

    /* access modifiers changed from: protected */
    public Coordinate coord(double x, double y) {
        Coordinate coordinate;
        new Coordinate(x, y);
        Coordinate pt = coordinate;
        this.precModel.makePrecise(pt);
        return pt;
    }

    /* access modifiers changed from: protected */
    public Coordinate coordTrans(double x, double y, Coordinate coordinate) {
        Coordinate trans = coordinate;
        return coord(x + trans.f412x, y + trans.f413y);
    }

    protected class Dimensions {
        public Coordinate base;
        public Coordinate centre;
        public double height;
        final /* synthetic */ GeometricShapeFactory this$0;
        public double width;

        protected Dimensions(GeometricShapeFactory this$02) {
            this.this$0 = this$02;
        }

        public void setBase(Coordinate base2) {
            Coordinate coordinate = base2;
            this.base = coordinate;
        }

        public Coordinate getBase() {
            return this.base;
        }

        public void setCentre(Coordinate centre2) {
            Coordinate coordinate = centre2;
            this.centre = coordinate;
        }

        public Coordinate getCentre() {
            Coordinate coordinate;
            if (this.centre == null) {
                new Coordinate(this.base.f412x + (this.width / 2.0d), this.base.f413y + (this.height / 2.0d));
                this.centre = coordinate;
            }
            return this.centre;
        }

        public void setSize(double d) {
            double size = d;
            this.height = size;
            this.width = size;
        }

        public double getMinSize() {
            return Math.min(this.width, this.height);
        }

        public void setWidth(double width2) {
            double d = width2;
            this.width = d;
        }

        public double getWidth() {
            return this.width;
        }

        public double getHeight() {
            return this.height;
        }

        public void setHeight(double height2) {
            double d = height2;
            this.height = d;
        }

        public void setEnvelope(Envelope envelope) {
            Coordinate coordinate;
            Coordinate coordinate2;
            Envelope env = envelope;
            this.width = env.getWidth();
            this.height = env.getHeight();
            new Coordinate(env.getMinX(), env.getMinY());
            this.base = coordinate;
            new Coordinate(env.centre());
            this.centre = coordinate2;
        }

        public Envelope getEnvelope() {
            Envelope envelope;
            Envelope envelope2;
            Envelope envelope3;
            if (this.base != null) {
                new Envelope(this.base.f412x, this.base.f412x + this.width, this.base.f413y, this.base.f413y + this.height);
                return envelope3;
            } else if (this.centre != null) {
                new Envelope(this.centre.f412x - (this.width / 2.0d), this.centre.f412x + (this.width / 2.0d), this.centre.f413y - (this.height / 2.0d), this.centre.f413y + (this.height / 2.0d));
                return envelope2;
            } else {
                new Envelope(0.0d, this.width, 0.0d, this.height);
                return envelope;
            }
        }
    }
}
