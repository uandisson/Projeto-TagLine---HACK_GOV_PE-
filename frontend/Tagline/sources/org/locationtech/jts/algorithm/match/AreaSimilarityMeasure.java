package org.locationtech.jts.algorithm.match;

import org.locationtech.jts.geom.Geometry;

public class AreaSimilarityMeasure implements SimilarityMeasure {
    public AreaSimilarityMeasure() {
    }

    public double measure(Geometry geometry, Geometry geometry2) {
        Geometry g1 = geometry;
        Geometry g2 = geometry2;
        return g1.intersection(g2).getArea() / g1.union(g2).getArea();
    }
}
