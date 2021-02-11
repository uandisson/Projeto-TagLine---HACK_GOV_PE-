package org.locationtech.jts.index.strtree;

import org.locationtech.jts.geom.Geometry;

public class GeometryItemDistance implements ItemDistance {
    public GeometryItemDistance() {
    }

    public double distance(ItemBoundable item1, ItemBoundable item2) {
        return ((Geometry) item1.getItem()).distance((Geometry) item2.getItem());
    }
}
