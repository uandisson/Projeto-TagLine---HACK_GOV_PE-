package org.locationtech.jts.awt;

import java.awt.geom.Point2D;
import org.locationtech.jts.geom.Coordinate;

public class IdentityPointTransformation implements PointTransformation {
    public IdentityPointTransformation() {
    }

    public void transform(Coordinate coordinate, Point2D view) {
        Coordinate model = coordinate;
        view.setLocation(model.f412x, model.f413y);
    }
}
