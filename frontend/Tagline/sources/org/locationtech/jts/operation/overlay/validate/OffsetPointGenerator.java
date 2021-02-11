package org.locationtech.jts.operation.overlay.validate;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.util.LinearComponentExtracter;

public class OffsetPointGenerator {
    private boolean doLeft = true;
    private boolean doRight = true;

    /* renamed from: g */
    private Geometry f495g;

    public OffsetPointGenerator(Geometry g) {
        this.f495g = g;
    }

    public void setSidesToGenerate(boolean doLeft2, boolean doRight2) {
        this.doLeft = doLeft2;
        this.doRight = doRight2;
    }

    public List getPoints(double d) {
        List list;
        double offsetDistance = d;
        new ArrayList();
        List offsetPts = list;
        for (LineString line : LinearComponentExtracter.getLines(this.f495g)) {
            extractPoints(line, offsetDistance, offsetPts);
        }
        return offsetPts;
    }

    private void extractPoints(LineString line, double d, List list) {
        double offsetDistance = d;
        List offsetPts = list;
        Coordinate[] pts = line.getCoordinates();
        for (int i = 0; i < pts.length - 1; i++) {
            computeOffsetPoints(pts[i], pts[i + 1], offsetDistance, offsetPts);
        }
    }

    private void computeOffsetPoints(Coordinate coordinate, Coordinate coordinate2, double d, List list) {
        Object obj;
        Object obj2;
        Coordinate p0 = coordinate;
        Coordinate p1 = coordinate2;
        double offsetDistance = d;
        List offsetPts = list;
        double dx = p1.f412x - p0.f412x;
        double dy = p1.f413y - p0.f413y;
        double len = Math.sqrt((dx * dx) + (dy * dy));
        double ux = (offsetDistance * dx) / len;
        double uy = (offsetDistance * dy) / len;
        double midX = (p1.f412x + p0.f412x) / 2.0d;
        double midY = (p1.f413y + p0.f413y) / 2.0d;
        if (this.doLeft) {
            new Coordinate(midX - uy, midY + ux);
            boolean add = offsetPts.add(obj2);
        }
        if (this.doRight) {
            new Coordinate(midX + uy, midY - ux);
            boolean add2 = offsetPts.add(obj);
        }
    }
}
