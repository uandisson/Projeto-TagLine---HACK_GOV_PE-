package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;

public class InteriorPointArea {
    private GeometryFactory factory;
    private Coordinate interiorPoint = null;
    private double maxWidth = 0.0d;

    /* access modifiers changed from: private */
    public static double avg(double a, double b) {
        return (a + b) / 2.0d;
    }

    public InteriorPointArea(Geometry geometry) {
        Geometry g = geometry;
        this.factory = g.getFactory();
        add(g);
    }

    public Coordinate getInteriorPoint() {
        return this.interiorPoint;
    }

    private void add(Geometry geometry) {
        Geometry geom = geometry;
        if (geom instanceof Polygon) {
            addPolygon(geom);
        } else if (geom instanceof GeometryCollection) {
            GeometryCollection gc = (GeometryCollection) geom;
            for (int i = 0; i < gc.getNumGeometries(); i++) {
                add(gc.getGeometryN(i));
            }
        }
    }

    private void addPolygon(Geometry geometry) {
        double width;
        Coordinate intPt;
        Geometry geometry2 = geometry;
        if (!geometry2.isEmpty()) {
            LineString bisector = horizontalBisector(geometry2);
            if (bisector.getLength() == 0.0d) {
                width = 0.0d;
                intPt = bisector.getCoordinate();
            } else {
                Geometry widestIntersection = widestGeometry(bisector.intersection(geometry2));
                width = widestIntersection.getEnvelopeInternal().getWidth();
                intPt = centre(widestIntersection.getEnvelopeInternal());
            }
            if (this.interiorPoint == null || width > this.maxWidth) {
                this.interiorPoint = intPt;
                this.maxWidth = width;
            }
        }
    }

    private Geometry widestGeometry(Geometry geometry) {
        Geometry geometry2 = geometry;
        if (!(geometry2 instanceof GeometryCollection)) {
            return geometry2;
        }
        return widestGeometry((GeometryCollection) geometry2);
    }

    private Geometry widestGeometry(GeometryCollection geometryCollection) {
        GeometryCollection gc = geometryCollection;
        if (gc.isEmpty()) {
            return gc;
        }
        Geometry widestGeometry = gc.getGeometryN(0);
        for (int i = 1; i < gc.getNumGeometries(); i++) {
            if (gc.getGeometryN(i).getEnvelopeInternal().getWidth() > widestGeometry.getEnvelopeInternal().getWidth()) {
                widestGeometry = gc.getGeometryN(i);
            }
        }
        return widestGeometry;
    }

    /* access modifiers changed from: protected */
    public LineString horizontalBisector(Geometry geometry) {
        Coordinate coordinate;
        Coordinate coordinate2;
        Geometry geometry2 = geometry;
        Envelope envelope = geometry2.getEnvelopeInternal();
        double bisectY = SafeBisectorFinder.getBisectorY((Polygon) geometry2);
        GeometryFactory geometryFactory = this.factory;
        Coordinate[] coordinateArr = new Coordinate[2];
        new Coordinate(envelope.getMinX(), bisectY);
        coordinateArr[0] = coordinate;
        Coordinate[] coordinateArr2 = coordinateArr;
        new Coordinate(envelope.getMaxX(), bisectY);
        coordinateArr2[1] = coordinate2;
        return geometryFactory.createLineString(coordinateArr2);
    }

    public static Coordinate centre(Envelope envelope) {
        Coordinate coordinate;
        Envelope envelope2 = envelope;
        new Coordinate(avg(envelope2.getMinX(), envelope2.getMaxX()), avg(envelope2.getMinY(), envelope2.getMaxY()));
        return coordinate;
    }

    private static class SafeBisectorFinder {
        private double centreY;
        private double hiY = Double.MAX_VALUE;
        private double loY = -1.7976931348623157E308d;
        private Polygon poly;

        public static double getBisectorY(Polygon poly2) {
            SafeBisectorFinder finder;
            new SafeBisectorFinder(poly2);
            return finder.getBisectorY();
        }

        public SafeBisectorFinder(Polygon polygon) {
            Polygon poly2 = polygon;
            this.poly = poly2;
            this.hiY = poly2.getEnvelopeInternal().getMaxY();
            this.loY = poly2.getEnvelopeInternal().getMinY();
            this.centreY = InteriorPointArea.avg(this.loY, this.hiY);
        }

        public double getBisectorY() {
            process(this.poly.getExteriorRing());
            for (int i = 0; i < this.poly.getNumInteriorRing(); i++) {
                process(this.poly.getInteriorRingN(i));
            }
            return InteriorPointArea.avg(this.hiY, this.loY);
        }

        private void process(LineString line) {
            CoordinateSequence seq = line.getCoordinateSequence();
            for (int i = 0; i < seq.size(); i++) {
                updateInterval(seq.getY(i));
            }
        }

        private void updateInterval(double d) {
            double y = d;
            if (y <= this.centreY) {
                if (y > this.loY) {
                    this.loY = y;
                }
            } else if (y > this.centreY && y < this.hiY) {
                this.hiY = y;
            }
        }
    }
}
