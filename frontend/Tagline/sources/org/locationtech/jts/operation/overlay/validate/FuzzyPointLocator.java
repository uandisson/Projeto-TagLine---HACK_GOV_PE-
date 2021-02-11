package org.locationtech.jts.operation.overlay.validate;

import org.locationtech.jts.algorithm.PointLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.GeometryFilter;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;

public class FuzzyPointLocator {
    private double boundaryDistanceTolerance;

    /* renamed from: g */
    private Geometry f494g;
    private MultiLineString linework;
    private PointLocator ptLocator;
    private LineSegment seg;

    public FuzzyPointLocator(Geometry geometry, double boundaryDistanceTolerance2) {
        PointLocator pointLocator;
        LineSegment lineSegment;
        Geometry g = geometry;
        new PointLocator();
        this.ptLocator = pointLocator;
        new LineSegment();
        this.seg = lineSegment;
        this.f494g = g;
        this.boundaryDistanceTolerance = boundaryDistanceTolerance2;
        this.linework = extractLinework(g);
    }

    public int getLocation(Coordinate coordinate) {
        Coordinate pt = coordinate;
        if (isWithinToleranceOfBoundary(pt)) {
            return 1;
        }
        return this.ptLocator.locate(pt, this.f494g);
    }

    private MultiLineString extractLinework(Geometry geometry) {
        PolygonalLineworkExtracter polygonalLineworkExtracter;
        Geometry g = geometry;
        new PolygonalLineworkExtracter();
        PolygonalLineworkExtracter extracter = polygonalLineworkExtracter;
        g.apply((GeometryFilter) extracter);
        return g.getFactory().createMultiLineString(GeometryFactory.toLineStringArray(extracter.getLinework()));
    }

    private boolean isWithinToleranceOfBoundary(Coordinate coordinate) {
        Coordinate pt = coordinate;
        for (int i = 0; i < this.linework.getNumGeometries(); i++) {
            CoordinateSequence seq = ((LineString) this.linework.getGeometryN(i)).getCoordinateSequence();
            for (int j = 0; j < seq.size() - 1; j++) {
                seq.getCoordinate(j, this.seg.f422p0);
                seq.getCoordinate(j + 1, this.seg.f423p1);
                if (this.seg.distance(pt) <= this.boundaryDistanceTolerance) {
                    return true;
                }
            }
        }
        return false;
    }
}
