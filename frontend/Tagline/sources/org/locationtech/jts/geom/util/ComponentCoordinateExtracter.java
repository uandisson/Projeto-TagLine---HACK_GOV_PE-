package org.locationtech.jts.geom.util;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryComponentFilter;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

public class ComponentCoordinateExtracter implements GeometryComponentFilter {
    private List coords;

    public static List getCoordinates(Geometry geom) {
        List list;
        GeometryComponentFilter geometryComponentFilter;
        new ArrayList();
        List coords2 = list;
        new ComponentCoordinateExtracter(coords2);
        geom.apply(geometryComponentFilter);
        return coords2;
    }

    public ComponentCoordinateExtracter(List coords2) {
        this.coords = coords2;
    }

    public void filter(Geometry geometry) {
        Geometry geom = geometry;
        if ((geom instanceof LineString) || (geom instanceof Point)) {
            boolean add = this.coords.add(geom.getCoordinate());
        }
    }
}
