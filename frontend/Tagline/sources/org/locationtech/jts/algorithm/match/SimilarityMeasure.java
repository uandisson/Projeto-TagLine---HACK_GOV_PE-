package org.locationtech.jts.algorithm.match;

import org.locationtech.jts.geom.Geometry;

public interface SimilarityMeasure {
    double measure(Geometry geometry, Geometry geometry2);
}
