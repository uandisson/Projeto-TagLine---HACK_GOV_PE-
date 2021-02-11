package org.locationtech.jts.noding;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import org.locationtech.jts.geom.CoordinateArrays;

public class SegmentStringDissolver {
    private SegmentStringMerger merger;
    private Map ocaMap;

    public interface SegmentStringMerger {
        void merge(SegmentString segmentString, SegmentString segmentString2, boolean z);
    }

    public SegmentStringDissolver(SegmentStringMerger merger2) {
        Map map;
        new TreeMap();
        this.ocaMap = map;
        this.merger = merger2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public SegmentStringDissolver() {
        this((SegmentStringMerger) null);
    }

    public void dissolve(Collection segStrings) {
        Iterator i = segStrings.iterator();
        while (i.hasNext()) {
            dissolve((SegmentString) i.next());
        }
    }

    private void add(OrientedCoordinateArray oca, SegmentString segString) {
        Object put = this.ocaMap.put(oca, segString);
    }

    public void dissolve(SegmentString segmentString) {
        OrientedCoordinateArray orientedCoordinateArray;
        SegmentString segString = segmentString;
        new OrientedCoordinateArray(segString.getCoordinates());
        OrientedCoordinateArray oca = orientedCoordinateArray;
        SegmentString existing = findMatching(oca, segString);
        if (existing == null) {
            add(oca, segString);
        } else if (this.merger != null) {
            this.merger.merge(existing, segString, CoordinateArrays.equals(existing.getCoordinates(), segString.getCoordinates()));
        }
    }

    private SegmentString findMatching(OrientedCoordinateArray oca, SegmentString segmentString) {
        SegmentString segmentString2 = segmentString;
        return (SegmentString) this.ocaMap.get(oca);
    }

    public Collection getDissolved() {
        return this.ocaMap.values();
    }
}
