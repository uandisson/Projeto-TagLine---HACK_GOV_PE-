package org.locationtech.jts.triangulate;

import org.locationtech.jts.geom.Coordinate;

public interface ConstraintVertexFactory {
    ConstraintVertex createVertex(Coordinate coordinate, Segment segment);
}
