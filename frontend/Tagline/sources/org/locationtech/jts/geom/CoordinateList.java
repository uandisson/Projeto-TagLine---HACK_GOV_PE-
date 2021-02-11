package org.locationtech.jts.geom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CoordinateList extends ArrayList {
    private static final Coordinate[] coordArrayType = new Coordinate[0];

    public CoordinateList() {
    }

    public CoordinateList(Coordinate[] coordinateArr) {
        Coordinate[] coord = coordinateArr;
        ensureCapacity(coord.length);
        boolean add = add(coord, true);
    }

    public CoordinateList(Coordinate[] coordinateArr, boolean allowRepeated) {
        Coordinate[] coord = coordinateArr;
        ensureCapacity(coord.length);
        boolean add = add(coord, allowRepeated);
    }

    public Coordinate getCoordinate(int i) {
        return (Coordinate) get(i);
    }

    public boolean add(Coordinate[] coordinateArr, boolean z, int i, int i2) {
        Coordinate[] coord = coordinateArr;
        boolean allowRepeated = z;
        int start = i;
        int end = i2;
        int inc = 1;
        if (start > end) {
            inc = -1;
        }
        int i3 = start;
        while (true) {
            int i4 = i3;
            if (i4 == end) {
                return true;
            }
            add(coord[i4], allowRepeated);
            i3 = i4 + inc;
        }
    }

    public boolean add(Coordinate[] coordinateArr, boolean z, boolean direction) {
        Coordinate[] coord = coordinateArr;
        boolean allowRepeated = z;
        if (direction) {
            for (int i = 0; i < coord.length; i++) {
                add(coord[i], allowRepeated);
            }
        } else {
            for (int i2 = coord.length - 1; i2 >= 0; i2--) {
                add(coord[i2], allowRepeated);
            }
        }
        return true;
    }

    public boolean add(Coordinate[] coord, boolean allowRepeated) {
        boolean add = add(coord, allowRepeated, true);
        return true;
    }

    public boolean add(Object obj, boolean allowRepeated) {
        add((Coordinate) obj, allowRepeated);
        return true;
    }

    public void add(Coordinate coordinate, boolean allowRepeated) {
        Coordinate coord = coordinate;
        if (allowRepeated || size() < 1 || !((Coordinate) get(size() - 1)).equals2D(coord)) {
            boolean add = super.add(coord);
        }
    }

    public void add(int i, Coordinate coordinate, boolean allowRepeated) {
        int size;
        int i2 = i;
        Coordinate coord = coordinate;
        if (!allowRepeated && (size = size()) > 0) {
            if (i2 > 0 && ((Coordinate) get(i2 - 1)).equals2D(coord)) {
                return;
            }
            if (i2 < size && ((Coordinate) get(i2)).equals2D(coord)) {
                return;
            }
        }
        super.add(i2, coord);
    }

    public boolean addAll(Collection coll, boolean z) {
        boolean allowRepeated = z;
        boolean isChanged = false;
        Iterator i = coll.iterator();
        while (i.hasNext()) {
            add((Coordinate) i.next(), allowRepeated);
            isChanged = true;
        }
        return isChanged;
    }

    public void closeRing() {
        Coordinate coordinate;
        if (size() > 0) {
            new Coordinate((Coordinate) get(0));
            add(coordinate, false);
        }
    }

    public Coordinate[] toCoordinateArray() {
        return (Coordinate[]) toArray(coordArrayType);
    }

    public Object clone() {
        CoordinateList clone = (CoordinateList) super.clone();
        for (int i = 0; i < size(); i++) {
            clone.add(i, ((Coordinate) get(i)).clone());
        }
        return clone;
    }
}
