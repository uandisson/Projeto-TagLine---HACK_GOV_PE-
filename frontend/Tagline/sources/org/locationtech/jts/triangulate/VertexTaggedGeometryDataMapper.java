package org.locationtech.jts.triangulate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

public class VertexTaggedGeometryDataMapper {
    private Map coordDataMap;

    public VertexTaggedGeometryDataMapper() {
        Map map;
        new TreeMap();
        this.coordDataMap = map;
    }

    public void loadSourceGeometries(Collection geoms) {
        Iterator i = geoms.iterator();
        while (i.hasNext()) {
            Geometry geom = (Geometry) i.next();
            loadVertices(geom.getCoordinates(), geom.getUserData());
        }
    }

    public void loadSourceGeometries(Geometry geometry) {
        Geometry geomColl = geometry;
        for (int i = 0; i < geomColl.getNumGeometries(); i++) {
            Geometry geom = geomColl.getGeometryN(i);
            loadVertices(geom.getCoordinates(), geom.getUserData());
        }
    }

    private void loadVertices(Coordinate[] coordinateArr, Object obj) {
        Coordinate[] pts = coordinateArr;
        Object data = obj;
        for (int i = 0; i < pts.length; i++) {
            Object put = this.coordDataMap.put(pts[i], data);
        }
    }

    public List getCoordinates() {
        List list;
        new ArrayList(this.coordDataMap.keySet());
        return list;
    }

    public void transferData(Geometry geometry) {
        Geometry targetGeom = geometry;
        for (int i = 0; i < targetGeom.getNumGeometries(); i++) {
            Geometry geom = targetGeom.getGeometryN(i);
            Coordinate vertexKey = (Coordinate) geom.getUserData();
            if (vertexKey != null) {
                geom.setUserData(this.coordDataMap.get(vertexKey));
            }
        }
    }
}
