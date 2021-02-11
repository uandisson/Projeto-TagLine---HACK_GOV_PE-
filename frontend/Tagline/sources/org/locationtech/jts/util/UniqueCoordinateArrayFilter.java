package org.locationtech.jts.util;

import java.util.ArrayList;
import java.util.TreeSet;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateFilter;

public class UniqueCoordinateArrayFilter implements CoordinateFilter {
    ArrayList list;
    TreeSet treeSet;

    public static Coordinate[] filterCoordinates(Coordinate[] coordinateArr) {
        UniqueCoordinateArrayFilter uniqueCoordinateArrayFilter;
        Coordinate[] coords = coordinateArr;
        new UniqueCoordinateArrayFilter();
        UniqueCoordinateArrayFilter filter = uniqueCoordinateArrayFilter;
        for (int i = 0; i < coords.length; i++) {
            filter.filter(coords[i]);
        }
        return filter.getCoordinates();
    }

    public UniqueCoordinateArrayFilter() {
        TreeSet treeSet2;
        ArrayList arrayList;
        new TreeSet();
        this.treeSet = treeSet2;
        new ArrayList();
        this.list = arrayList;
    }

    public Coordinate[] getCoordinates() {
        return (Coordinate[]) this.list.toArray(new Coordinate[this.list.size()]);
    }

    public void filter(Coordinate coordinate) {
        Coordinate coord = coordinate;
        if (!this.treeSet.contains(coord)) {
            boolean add = this.list.add(coord);
            boolean add2 = this.treeSet.add(coord);
        }
    }
}
