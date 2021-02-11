package org.locationtech.jts.shape.random;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.math.MathUtil;
import org.locationtech.jts.shape.GeometricShapeBuilder;

public class RandomPointsInGridBuilder extends GeometricShapeBuilder {
    private double gutterFraction = 0.0d;
    private boolean isConstrainedToCircle = false;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public RandomPointsInGridBuilder() {
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
            r1 = r0
            r2 = 0
            r1.isConstrainedToCircle = r2
            r1 = r0
            r2 = 0
            r1.gutterFraction = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.locationtech.jts.shape.random.RandomPointsInGridBuilder.<init>():void");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public RandomPointsInGridBuilder(GeometryFactory geomFact) {
        super(geomFact);
    }

    public void setConstrainedToCircle(boolean isConstrainedToCircle2) {
        boolean z = isConstrainedToCircle2;
        this.isConstrainedToCircle = z;
    }

    public void setGutterFraction(double gutterFraction2) {
        double d = gutterFraction2;
        this.gutterFraction = d;
    }

    public Geometry getGeometry() {
        int nCells = (int) Math.sqrt((double) this.numPts);
        if (nCells * nCells < this.numPts) {
            nCells++;
        }
        double gridDX = getExtent().getWidth() / ((double) nCells);
        double gridDY = getExtent().getHeight() / ((double) nCells);
        double gutterFrac = MathUtil.clamp(this.gutterFraction, 0.0d, 1.0d);
        double gutterOffsetX = (gridDX * gutterFrac) / 2.0d;
        double gutterOffsetY = (gridDY * gutterFrac) / 2.0d;
        double cellFrac = 1.0d - gutterFrac;
        double cellDX = cellFrac * gridDX;
        double cellDY = cellFrac * gridDY;
        Coordinate[] pts = new Coordinate[(nCells * nCells)];
        int index = 0;
        for (int i = 0; i < nCells; i++) {
            for (int j = 0; j < nCells; j++) {
                int i2 = index;
                index++;
                pts[i2] = randomPointInCell(getExtent().getMinX() + (((double) i) * gridDX) + gutterOffsetX, getExtent().getMinY() + (((double) j) * gridDY) + gutterOffsetY, cellDX, cellDY);
            }
        }
        return this.geomFactory.createMultiPoint(pts);
    }

    private Coordinate randomPointInCell(double d, double d2, double d3, double d4) {
        double orgX = d;
        double orgY = d2;
        double xLen = d3;
        double yLen = d4;
        if (this.isConstrainedToCircle) {
            return randomPointInCircle(orgX, orgY, xLen, yLen);
        }
        return randomPointInGridCell(orgX, orgY, xLen, yLen);
    }

    private Coordinate randomPointInGridCell(double orgX, double orgY, double xLen, double yLen) {
        return createCoord(orgX + (xLen * Math.random()), orgY + (yLen * Math.random()));
    }

    private static Coordinate randomPointInCircle(double orgX, double orgY, double d, double d2) {
        Coordinate coordinate;
        double width = d;
        double height = d2;
        double rndAng = 6.283185307179586d * Math.random();
        double rndRadius2 = Math.sqrt(Math.random());
        double rndX = (width / 2.0d) * rndRadius2 * Math.cos(rndAng);
        new Coordinate(orgX + (width / 2.0d) + rndX, orgY + (height / 2.0d) + ((height / 2.0d) * rndRadius2 * Math.sin(rndAng)));
        return coordinate;
    }
}
