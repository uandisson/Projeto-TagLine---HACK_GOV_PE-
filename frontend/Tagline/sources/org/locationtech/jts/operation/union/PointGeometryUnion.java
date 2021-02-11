package org.locationtech.jts.operation.union;

import java.util.Set;
import java.util.TreeSet;
import org.locationtech.jts.algorithm.PointLocator;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateArrays;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Puntal;
import org.locationtech.jts.geom.util.GeometryCombiner;

public class PointGeometryUnion {
    private GeometryFactory geomFact;
    private Geometry otherGeom;
    private Geometry pointGeom;

    public static Geometry union(Puntal pointGeom2, Geometry otherGeom2) {
        PointGeometryUnion unioner;
        new PointGeometryUnion(pointGeom2, otherGeom2);
        return unioner.union();
    }

    public PointGeometryUnion(Puntal pointGeom2, Geometry geometry) {
        Geometry otherGeom2 = geometry;
        this.pointGeom = (Geometry) pointGeom2;
        this.otherGeom = otherGeom2;
        this.geomFact = otherGeom2.getFactory();
    }

    public Geometry union() {
        PointLocator pointLocator;
        Set set;
        Geometry ptComp;
        new PointLocator();
        PointLocator locater = pointLocator;
        new TreeSet();
        Set exteriorCoords = set;
        for (int i = 0; i < this.pointGeom.getNumGeometries(); i++) {
            Coordinate coord = ((Point) this.pointGeom.getGeometryN(i)).getCoordinate();
            if (locater.locate(coord, this.otherGeom) == 2) {
                boolean add = exteriorCoords.add(coord);
            }
        }
        if (exteriorCoords.size() == 0) {
            return this.otherGeom;
        }
        Coordinate[] coords = CoordinateArrays.toCoordinateArray(exteriorCoords);
        if (coords.length == 1) {
            ptComp = this.geomFact.createPoint(coords[0]);
        } else {
            ptComp = this.geomFact.createMultiPoint(coords);
        }
        return GeometryCombiner.combine(ptComp, this.otherGeom);
    }
}
