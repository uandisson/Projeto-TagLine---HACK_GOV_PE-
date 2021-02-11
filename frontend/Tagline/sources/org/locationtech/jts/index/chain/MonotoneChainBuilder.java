package org.locationtech.jts.index.chain;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geomgraph.Quadrant;

public class MonotoneChainBuilder {
    public static int[] toIntArray(List list) {
        List list2 = list;
        int[] array = new int[list2.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = ((Integer) list2.get(i)).intValue();
        }
        return array;
    }

    public static List getChains(Coordinate[] pts) {
        return getChains(pts, (Object) null);
    }

    public static List getChains(Coordinate[] coordinateArr, Object obj) {
        List list;
        Object obj2;
        Coordinate[] pts = coordinateArr;
        Object context = obj;
        new ArrayList();
        List mcList = list;
        int[] startIndex = getChainStartIndices(pts);
        for (int i = 0; i < startIndex.length - 1; i++) {
            new MonotoneChain(pts, startIndex[i], startIndex[i + 1], context);
            boolean add = mcList.add(obj2);
        }
        return mcList;
    }

    public static int[] getChainStartIndices(Coordinate[] coordinateArr) {
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

    private static int findChainEnd(Coordinate[] coordinateArr, int i) {
        Coordinate[] pts = coordinateArr;
        int start = i;
        int safeStart = start;
        while (safeStart < pts.length - 1 && pts[safeStart].equals2D(pts[safeStart + 1])) {
            safeStart++;
        }
        if (safeStart >= pts.length - 1) {
            return pts.length - 1;
        }
        int chainQuad = Quadrant.quadrant(pts[safeStart], pts[safeStart + 1]);
        int last = start + 1;
        while (last < pts.length && (pts[last - 1].equals2D(pts[last]) || Quadrant.quadrant(pts[last - 1], pts[last]) == chainQuad)) {
            last++;
        }
        return last - 1;
    }

    public MonotoneChainBuilder() {
    }
}
