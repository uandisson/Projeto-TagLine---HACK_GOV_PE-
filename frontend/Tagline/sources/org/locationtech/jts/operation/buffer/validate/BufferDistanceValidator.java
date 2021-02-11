package org.locationtech.jts.operation.buffer.validate;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.algorithm.distance.DiscreteHausdorffDistance;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryComponentFilter;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.util.LinearComponentExtracter;
import org.locationtech.jts.geom.util.PolygonExtracter;
import org.locationtech.jts.operation.distance.DistanceOp;
import org.locationtech.jts.p006io.WKTWriter;

public class BufferDistanceValidator {
    private static final double MAX_DISTANCE_DIFF_FRAC = 0.012d;
    private static boolean VERBOSE = false;
    private double bufDistance;
    private String errMsg = null;
    private Geometry errorIndicator = null;
    private Coordinate errorLocation = null;
    private Geometry input;
    private boolean isValid = true;
    private double maxDistanceFound;
    private double maxValidDistance;
    private double minDistanceFound;
    private double minValidDistance;
    private Geometry result;

    public BufferDistanceValidator(Geometry input2, double bufDistance2, Geometry result2) {
        this.input = input2;
        this.bufDistance = bufDistance2;
        this.result = result2;
    }

    public boolean isValid() {
        StringBuilder sb;
        double posDistance = Math.abs(this.bufDistance);
        double distDelta = MAX_DISTANCE_DIFF_FRAC * posDistance;
        this.minValidDistance = posDistance - distDelta;
        this.maxValidDistance = posDistance + distDelta;
        if (this.input.isEmpty() || this.result.isEmpty()) {
            return true;
        }
        if (this.bufDistance > 0.0d) {
            checkPositiveValid();
        } else {
            checkNegativeValid();
        }
        if (VERBOSE) {
            PrintStream printStream = System.out;
            new StringBuilder();
            printStream.println(sb.append("Min Dist= ").append(this.minDistanceFound).append("  err= ").append(1.0d - (this.minDistanceFound / this.bufDistance)).append("  Max Dist= ").append(this.maxDistanceFound).append("  err= ").append((this.maxDistanceFound / this.bufDistance) - 1.0d).toString());
        }
        return this.isValid;
    }

    public String getErrorMessage() {
        return this.errMsg;
    }

    public Coordinate getErrorLocation() {
        return this.errorLocation;
    }

    public Geometry getErrorIndicator() {
        return this.errorIndicator;
    }

    private void checkPositiveValid() {
        Geometry bufCurve = this.result.getBoundary();
        checkMinimumDistance(this.input, bufCurve, this.minValidDistance);
        if (this.isValid) {
            checkMaximumDistance(this.input, bufCurve, this.maxValidDistance);
        }
    }

    private void checkNegativeValid() {
        if ((this.input instanceof Polygon) || (this.input instanceof MultiPolygon) || (this.input instanceof GeometryCollection)) {
            Geometry inputCurve = getPolygonLines(this.input);
            checkMinimumDistance(inputCurve, this.result, this.minValidDistance);
            if (this.isValid) {
                checkMaximumDistance(inputCurve, this.result, this.maxValidDistance);
            }
        }
    }

    private Geometry getPolygonLines(Geometry geometry) {
        List list;
        GeometryComponentFilter geometryComponentFilter;
        Geometry g = geometry;
        new ArrayList();
        List lines = list;
        new LinearComponentExtracter(lines);
        GeometryComponentFilter geometryComponentFilter2 = geometryComponentFilter;
        for (Polygon poly : PolygonExtracter.getPolygons(g)) {
            poly.apply(geometryComponentFilter2);
        }
        return g.getFactory().buildGeometry(lines);
    }

    private void checkMinimumDistance(Geometry geometry, Geometry g2, double d) {
        DistanceOp distanceOp;
        StringBuilder sb;
        Geometry g1 = geometry;
        double minDist = d;
        new DistanceOp(g1, g2, minDist);
        DistanceOp distOp = distanceOp;
        this.minDistanceFound = distOp.distance();
        if (this.minDistanceFound < minDist) {
            this.isValid = false;
            Coordinate[] pts = distOp.nearestPoints();
            this.errorLocation = distOp.nearestPoints()[1];
            this.errorIndicator = g1.getFactory().createLineString(pts);
            new StringBuilder();
            this.errMsg = sb.append("Distance between buffer curve and input is too small (").append(this.minDistanceFound).append(" at ").append(WKTWriter.toLineString(pts[0], pts[1])).append(" )").toString();
        }
    }

    private void checkMaximumDistance(Geometry geometry, Geometry bufCurve, double maxDist) {
        DiscreteHausdorffDistance discreteHausdorffDistance;
        StringBuilder sb;
        Geometry input2 = geometry;
        new DiscreteHausdorffDistance(bufCurve, input2);
        DiscreteHausdorffDistance haus = discreteHausdorffDistance;
        haus.setDensifyFraction(0.25d);
        this.maxDistanceFound = haus.orientedDistance();
        if (this.maxDistanceFound > maxDist) {
            this.isValid = false;
            Coordinate[] pts = haus.getCoordinates();
            this.errorLocation = pts[1];
            this.errorIndicator = input2.getFactory().createLineString(pts);
            new StringBuilder();
            this.errMsg = sb.append("Distance between buffer curve and input is too large (").append(this.maxDistanceFound).append(" at ").append(WKTWriter.toLineString(pts[0], pts[1])).append(")").toString();
        }
    }
}
