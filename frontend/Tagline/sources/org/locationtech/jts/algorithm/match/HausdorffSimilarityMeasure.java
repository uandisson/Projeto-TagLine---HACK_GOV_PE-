package org.locationtech.jts.algorithm.match;

import org.locationtech.jts.algorithm.distance.DiscreteHausdorffDistance;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;

public class HausdorffSimilarityMeasure implements SimilarityMeasure {
    private static final double DENSIFY_FRACTION = 0.25d;

    public HausdorffSimilarityMeasure() {
    }

    public double measure(Geometry geometry, Geometry geometry2) {
        Envelope envelope;
        Geometry g1 = geometry;
        Geometry g2 = geometry2;
        double distance = DiscreteHausdorffDistance.distance(g1, g2, DENSIFY_FRACTION);
        new Envelope(g1.getEnvelopeInternal());
        Envelope env = envelope;
        env.expandToInclude(g2.getEnvelopeInternal());
        return 1.0d - (distance / diagonalSize(env));
    }

    public static double diagonalSize(Envelope envelope) {
        Envelope env = envelope;
        if (env.isNull()) {
            return 0.0d;
        }
        double width = env.getWidth();
        double hgt = env.getHeight();
        return Math.sqrt((width * width) + (hgt * hgt));
    }
}
