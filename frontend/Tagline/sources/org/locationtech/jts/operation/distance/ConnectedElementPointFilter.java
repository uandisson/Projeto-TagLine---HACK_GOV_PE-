package org.locationtech.jts.operation.distance;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFilter;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

public class ConnectedElementPointFilter implements GeometryFilter {
    private List pts;

    public static List getCoordinates(Geometry geom) {
        List list;
        GeometryFilter geometryFilter;
        new ArrayList();
        List pts2 = list;
        new ConnectedElementPointFilter(pts2);
        geom.apply(geometryFilter);
        return pts2;
    }

    ConnectedElementPointFilter(List pts2) {
        this.pts = pts2;
    }

    public void filter(Geometry geometry) {
        Geometry geom = geometry;
        if ((geom instanceof Point) || (geom instanceof LineString) || (geom instanceof Polygon)) {
            boolean add = this.pts.add(geom.getCoordinate());
        }
    }
}
