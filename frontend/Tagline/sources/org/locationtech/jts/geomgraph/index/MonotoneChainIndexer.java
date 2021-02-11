package org.locationtech.jts.geomgraph.index;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.Quadrant;

public class MonotoneChainIndexer {
    public static int[] toIntArray(List list) {
        List list2 = list;
        int[] array = new int[list2.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = ((Integer) list2.get(i)).intValue();
        }
        return array;
    }

    public MonotoneChainIndexer() {
    }

    public int[] getChainStartIndices(Coordinate[] coordinateArr) {
        List list;
        Object obj;
        Object obj2;
        Coordinate[] pts = coordinateArr;
        int start = 0;
        new ArrayList();
        List startIndexList = list;
        new Integer(0);
        boolean add = startIndexList.add(obj);
        do {
            int last = findChainEnd(pts, start);
            new Integer(last);
            boolean add2 = startIndexList.add(obj2);
            start = last;
        } while (start < pts.length - 1);
        return toIntArray(startIndexList);
    }

    private int findChainEnd(Coordinate[] coordinateArr, int i) {
        Coordinate[] pts = coordinateArr;
        int start = i;
        int chainQuad = Quadrant.quadrant(pts[start], pts[start + 1]);
        int last = start + 1;
        while (last < pts.length && Quadrant.quadrant(pts[last - 1], pts[last]) == chainQuad) {
            last++;
        }
        return last - 1;
    }
}
