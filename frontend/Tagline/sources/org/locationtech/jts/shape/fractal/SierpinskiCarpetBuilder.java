package org.locationtech.jts.shape.fractal;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.shape.GeometricShapeBuilder;

public class SierpinskiCarpetBuilder extends GeometricShapeBuilder {
    private CoordinateList coordList;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public SierpinskiCarpetBuilder(GeometryFactory geomFactory) {
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
        Coordinate origin = getSquareBaseLine().getCoordinate(0);
        LinearRing[] holes = getHoles(level, origin.f412x, origin.f413y, getDiameter());
        return this.geomFactory.createPolygon((LinearRing) ((Polygon) this.geomFactory.toGeometry(getSquareExtent())).getExteriorRing(), holes);
    }

    private LinearRing[] getHoles(int n, double originX, double originY, double width) {
        List list;
        new ArrayList();
        List holeList = list;
        addHoles(n, originX, originY, width, holeList);
        return GeometryFactory.toLinearRingArray(holeList);
    }

    private void addHoles(int i, double d, double d2, double d3, List list) {
        int n = i;
        double originX = d;
        double originY = d2;
        double width = d3;
        List holeList = list;
        if (n >= 0) {
            int n2 = n - 1;
            double widthThird = width / 3.0d;
            double d4 = (width * 2.0d) / 3.0d;
            double d5 = width / 9.0d;
            addHoles(n2, originX, originY, widthThird, holeList);
            addHoles(n2, originX + widthThird, originY, widthThird, holeList);
            addHoles(n2, originX + (2.0d * widthThird), originY, widthThird, holeList);
            addHoles(n2, originX, originY + widthThird, widthThird, holeList);
            addHoles(n2, originX + (2.0d * widthThird), originY + widthThird, widthThird, holeList);
            addHoles(n2, originX, originY + (2.0d * widthThird), widthThird, holeList);
            addHoles(n2, originX + widthThird, originY + (2.0d * widthThird), widthThird, holeList);
            addHoles(n2, originX + (2.0d * widthThird), originY + (2.0d * widthThird), widthThird, holeList);
            boolean add = holeList.add(createSquareHole(originX + widthThird, originY + widthThird, widthThird));
        }
    }

    private LinearRing createSquareHole(double d, double d2, double d3) {
        Coordinate coordinate;
        Coordinate coordinate2;
        Coordinate coordinate3;
        Coordinate coordinate4;
        Coordinate coordinate5;
        double x = d;
        double y = d2;
        double width = d3;
        Coordinate[] coordinateArr = new Coordinate[5];
        new Coordinate(x, y);
        coordinateArr[0] = coordinate;
        Coordinate[] coordinateArr2 = coordinateArr;
        new Coordinate(x + width, y);
        coordinateArr2[1] = coordinate2;
        Coordinate[] coordinateArr3 = coordinateArr2;
        new Coordinate(x + width, y + width);
        coordinateArr3[2] = coordinate3;
        Coordinate[] coordinateArr4 = coordinateArr3;
        new Coordinate(x, y + width);
        coordinateArr4[3] = coordinate4;
        Coordinate[] pts = coordinateArr4;
        new Coordinate(x, y);
        pts[4] = coordinate5;
        return this.geomFactory.createLinearRing(pts);
    }
}
