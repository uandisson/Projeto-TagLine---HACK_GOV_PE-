package org.locationtech.jts.shape.fractal;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.math.Vector2D;
import org.locationtech.jts.shape.GeometricShapeBuilder;

public class KochSnowflakeBuilder extends GeometricShapeBuilder {
    private static final double HEIGHT_FACTOR = Math.sin(1.0471975511965976d);
    private static final double ONE_THIRD = 0.3333333333333333d;
    private static final double THIRD_HEIGHT = (HEIGHT_FACTOR / 3.0d);
    private static final double TWO_THIRDS = 0.6666666666666666d;
    private CoordinateList coordList;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KochSnowflakeBuilder(GeometryFactory geomFactory) {
        super(geomFactory);
        CoordinateList coordinateList;
        new CoordinateList();
        this.coordList = coordinateList;
    }

    public static int recursionLevelForSize(int numPts) {
        return (int) (Math.log((double) (numPts / 3)) / Math.log(4.0d));
    }

    public Geometry getGeometry() {
        int level = recursionLevelForSize(this.numPts);
        LineSegment baseLine = getSquareBaseLine();
        return this.geomFactory.createPolygon(this.geomFactory.createLinearRing(getBoundary(level, baseLine.getCoordinate(0), baseLine.getLength())), (LinearRing[]) null);
    }

    private Coordinate[] getBoundary(int i, Coordinate coordinate, double d) {
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        int level = i;
        Coordinate origin = coordinate;
        double width = d;
        double y = origin.f413y;
        if (level > 0) {
            y += THIRD_HEIGHT * width;
        }
        new Coordinate(origin.f412x, y);
        Coordinate p0 = coordinate2;
        new Coordinate(origin.f412x + (width / 2.0d), y + (width * HEIGHT_FACTOR));
        Coordinate p1 = coordinate3;
        new Coordinate(origin.f412x + width, y);
        Coordinate p2 = coordinate4;
        addSide(level, p0, p1);
        addSide(level, p1, p2);
        addSide(level, p2, p0);
        this.coordList.closeRing();
        return this.coordList.toCoordinateArray();
    }

    public void addSide(int i, Coordinate coordinate, Coordinate coordinate2) {
        int level = i;
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        if (level == 0) {
            addSegment(p0, p1);
            return;
        }
        Vector2D base = Vector2D.create(p0, p1);
        Coordinate offsetPt = base.multiply(THIRD_HEIGHT).rotateByQuarterCircle(1).translate(base.multiply(0.5d).translate(p0));
        int n2 = level - 1;
        Coordinate thirdPt = base.multiply(ONE_THIRD).translate(p0);
        Coordinate twoThirdPt = base.multiply(TWO_THIRDS).translate(p0);
        addSide(n2, p0, thirdPt);
        addSide(n2, thirdPt, offsetPt);
        addSide(n2, offsetPt, twoThirdPt);
        addSide(n2, twoThirdPt, p1);
    }

    private void addSegment(Coordinate coordinate, Coordinate p1) {
        Coordinate coordinate2 = coordinate;
        boolean add = this.coordList.add(p1);
    }
}
