package org.locationtech.jts.geom.prep;

import org.locationtech.jts.geom.Geometry;

public interface PreparedGeometry {
    boolean contains(Geometry geometry);

    boolean containsProperly(Geometry geometry);

    boolean coveredBy(Geometry geometry);

    boolean covers(Geometry geometry);

    boolean crosses(Geometry geometry);

    boolean disjoint(Geometry geometry);

    Geometry getGeometry();

    boolean intersects(Geometry geometry);

    boolean overlaps(Geometry geometry);

    boolean touches(Geometry geometry);

    boolean within(Geometry geometry);
}
