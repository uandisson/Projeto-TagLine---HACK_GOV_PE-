package org.locationtech.jts.geom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryComponentFilter;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;

public class LinearComponentExtracter implements GeometryComponentFilter {
    private boolean isForcedToLineString = false;
    private Collection lines;

    public static Collection getLines(Collection geoms, Collection collection) {
        Collection lines2 = collection;
        Iterator i = geoms.iterator();
        while (i.hasNext()) {
            Collection lines3 = getLines((Geometry) i.next(), lines2);
        }
        return lines2;
    }

    public static Collection getLines(Collection geoms, Collection collection, boolean z) {
        Collection lines2 = collection;
        boolean forceToLineString = z;
        Iterator i = geoms.iterator();
        while (i.hasNext()) {
            Collection lines3 = getLines((Geometry) i.next(), lines2, forceToLineString);
        }
        return lines2;
    }

    public static Collection getLines(Geometry geometry, Collection collection) {
        GeometryComponentFilter geometryComponentFilter;
        Geometry geom = geometry;
        Collection lines2 = collection;
        if (geom instanceof LineString) {
            boolean add = lines2.add(geom);
        } else {
            new LinearComponentExtracter(lines2);
            geom.apply(geometryComponentFilter);
        }
        return lines2;
    }

    public static Collection getLines(Geometry geom, Collection collection, boolean forceToLineString) {
        GeometryComponentFilter geometryComponentFilter;
        Collection lines2 = collection;
        new LinearComponentExtracter(lines2, forceToLineString);
        geom.apply(geometryComponentFilter);
        return lines2;
    }

    public static List getLines(Geometry geom) {
        return getLines(geom, false);
    }

    public static List getLines(Geometry geom, boolean forceToLineString) {
        List list;
        GeometryComponentFilter geometryComponentFilter;
        new ArrayList();
        List lines2 = list;
        new LinearComponentExtracter(lines2, forceToLineString);
        geom.apply(geometryComponentFilter);
        return lines2;
    }

    public static Geometry getGeometry(Geometry geometry) {
        Geometry geom = geometry;
        return geom.getFactory().buildGeometry(getLines(geom));
    }

    public static Geometry getGeometry(Geometry geometry, boolean forceToLineString) {
        Geometry geom = geometry;
        return geom.getFactory().buildGeometry(getLines(geom, forceToLineString));
    }

    public LinearComponentExtracter(Collection lines2) {
        this.lines = lines2;
    }

    public LinearComponentExtracter(Collection lines2, boolean isForcedToLineString2) {
        this.lines = lines2;
        this.isForcedToLineString = isForcedToLineString2;
    }

    public void setForceToLineString(boolean isForcedToLineString2) {
        boolean z = isForcedToLineString2;
        this.isForcedToLineString = z;
    }

    public void filter(Geometry geometry) {
        Geometry geom = geometry;
        if (this.isForcedToLineString && (geom instanceof LinearRing)) {
            boolean add = this.lines.add(geom.getFactory().createLineString(((LinearRing) geom).getCoordinateSequence()));
        } else if (geom instanceof LineString) {
            boolean add2 = this.lines.add(geom);
        }
    }
}
