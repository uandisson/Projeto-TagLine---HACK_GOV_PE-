package org.locationtech.jts.geom.util;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFilter;
import org.locationtech.jts.geom.LineString;

public class LineStringExtracter implements GeometryFilter {
    private List comps;

    public static List getLines(Geometry geometry, List list) {
        GeometryFilter geometryFilter;
        Geometry geom = geometry;
        List lines = list;
        if (geom instanceof LineString) {
            boolean add = lines.add(geom);
        } else if (geom instanceof GeometryCollection) {
            new LineStringExtracter(lines);
            geom.apply(geometryFilter);
        }
        return lines;
    }

    public static List getLines(Geometry geom) {
        List list;
        new ArrayList();
        return getLines(geom, list);
    }

    public static Geometry getGeometry(Geometry geometry) {
        Geometry geom = geometry;
        return geom.getFactory().buildGeometry(getLines(geom));
    }

    public LineStringExtracter(List comps2) {
        this.comps = comps2;
    }

    public void filter(Geometry geometry) {
        Geometry geom = geometry;
        if (geom instanceof LineString) {
            boolean add = this.comps.add(geom);
        }
    }
}
