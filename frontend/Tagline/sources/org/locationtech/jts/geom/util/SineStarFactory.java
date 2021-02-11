package org.locationtech.jts.geom.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.util.GeometricShapeFactory;

public class SineStarFactory extends GeometricShapeFactory {
    protected double armLengthRatio = 0.5d;
    protected int numArms = 8;

    public SineStarFactory() {
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SineStarFactory(GeometryFactory geomFact) {
        super(geomFact);
    }

    public void setNumArms(int numArms2) {
        int i = numArms2;
        this.numArms = i;
    }

    public void setArmLengthRatio(double armLengthRatio2) {
        double d = armLengthRatio2;
        this.armLengthRatio = d;
    }

    public Geometry createSineStar() {
        Coordinate coordinate;
        Envelope env = this.dim.getEnvelope();
        double radius = env.getWidth() / 2.0d;
        double armRatio = this.armLengthRatio;
        if (armRatio < 0.0d) {
            armRatio = 0.0d;
        }
        if (armRatio > 1.0d) {
            armRatio = 1.0d;
        }
        double armMaxLen = armRatio * radius;
        double insideRadius = (1.0d - armRatio) * radius;
        double centreX = env.getMinX() + radius;
        double centreY = env.getMinY() + radius;
        Coordinate[] pts = new Coordinate[(this.nPts + 1)];
        int iPt = 0;
        int i = 0;
        while (true) {
            if (i < this.nPts) {
                double ptArcFrac = (((double) i) / ((double) this.nPts)) * ((double) this.numArms);
                double curveRadius = insideRadius + (armMaxLen * ((Math.cos(6.283185307179586d * (ptArcFrac - Math.floor(ptArcFrac))) + 1.0d) / 2.0d));
                double ang = ((double) i) * (6.283185307179586d / ((double) this.nPts));
                int i2 = iPt;
                iPt++;
                pts[i2] = coord((curveRadius * Math.cos(ang)) + centreX, (curveRadius * Math.sin(ang)) + centreY);
                i++;
            } else {
                new Coordinate(pts[0]);
                pts[iPt] = coordinate;
                return this.geomFact.createPolygon(this.geomFact.createLinearRing(pts), (LinearRing[]) null);
            }
        }
    }
}
