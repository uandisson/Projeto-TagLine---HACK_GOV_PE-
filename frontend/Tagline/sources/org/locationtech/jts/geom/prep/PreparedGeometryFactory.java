package org.locationtech.jts.geom.prep;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Lineal;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.geom.Puntal;

public class PreparedGeometryFactory {
    public static PreparedGeometry prepare(Geometry geom) {
        PreparedGeometryFactory preparedGeometryFactory;
        new PreparedGeometryFactory();
        return preparedGeometryFactory.create(geom);
    }

    public PreparedGeometryFactory() {
    }

    public PreparedGeometry create(Geometry geometry) {
        PreparedGeometry preparedGeometry;
        PreparedGeometry preparedGeometry2;
        PreparedGeometry preparedGeometry3;
        PreparedGeometry preparedGeometry4;
        Geometry geom = geometry;
        if (geom instanceof Polygonal) {
            new PreparedPolygon((Polygonal) geom);
            return preparedGeometry4;
        } else if (geom instanceof Lineal) {
            new PreparedLineString((Lineal) geom);
            return preparedGeometry3;
        } else if (geom instanceof Puntal) {
            new PreparedPoint((Puntal) geom);
            return preparedGeometry2;
        } else {
            new BasicPreparedGeometry(geom);
            return preparedGeometry;
        }
    }
}
