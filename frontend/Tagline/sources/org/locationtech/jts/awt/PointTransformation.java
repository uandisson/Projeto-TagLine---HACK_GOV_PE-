package org.locationtech.jts.awt;

import java.awt.geom.Point2D;
import org.locationtech.jts.geom.Coordinate;

public interface PointTransformation {
    void transform(Coordinate coordinate, Point2D point2D);
}
