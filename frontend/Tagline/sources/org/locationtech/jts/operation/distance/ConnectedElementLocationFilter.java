package org.locationtech.jts.operation.distance;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFilter;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class ConnectedElementLocationFilter implements GeometryFilter {
    private List locations;

    public static List getLocations(Geometry geom) {
        List list;
        GeometryFilter geometryFilter;
        new ArrayList();
        List locations2 = list;
        new ConnectedElementLocationFilter(locations2);
        geom.apply(geometryFilter);
        return locations2;
    }

    ConnectedElementLocationFilter(List locations2) {
        this.locations = locations2;
    }

    public void filter(Geometry geometry) {
        Object obj;
        Geometry geom = geometry;
        if ((geom instanceof Point) || (geom instanceof LineString) || (geom instanceof Polygon)) {
            new GeometryLocation(geom, 0, geom.getCoordinate());
            boolean add = this.locations.add(obj);
        }
    }
}
