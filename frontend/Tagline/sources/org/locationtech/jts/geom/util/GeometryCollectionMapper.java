package org.locationtech.jts.geom.util;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.util.GeometryMapper;

public class GeometryCollectionMapper {
    private GeometryMapper.MapOp mapOp = null;

    public static GeometryCollection map(GeometryCollection gc, GeometryMapper.MapOp op) {
        GeometryCollectionMapper mapper;
        new GeometryCollectionMapper(op);
        return mapper.map(gc);
    }

    public GeometryCollectionMapper(GeometryMapper.MapOp mapOp2) {
        this.mapOp = mapOp2;
    }

    public GeometryCollection map(GeometryCollection geometryCollection) {
        List list;
        GeometryCollection gc = geometryCollection;
        new ArrayList();
        List mapped = list;
        for (int i = 0; i < gc.getNumGeometries(); i++) {
            Geometry g = this.mapOp.map(gc.getGeometryN(i));
            if (!g.isEmpty()) {
                boolean add = mapped.add(g);
            }
        }
        return gc.getFactory().createGeometryCollection(GeometryFactory.toGeometryArray(mapped));
    }
}
