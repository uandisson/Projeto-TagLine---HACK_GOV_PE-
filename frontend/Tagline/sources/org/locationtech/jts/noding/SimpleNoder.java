package org.locationtech.jts.noding;

import java.util.Collection;
import org.locationtech.jts.geom.Coordinate;

public class SimpleNoder extends SinglePassNoder {
    private Collection nodedSegStrings;

    public SimpleNoder() {
    }

    public Collection getNodedSubstrings() {
        return NodedSegmentString.getNodedSubstrings(this.nodedSegStrings);
    }

    public void computeNodes(Collection collection) {
        Collection<SegmentString> inputSegStrings = collection;
        this.nodedSegStrings = inputSegStrings;
        for (SegmentString edge0 : inputSegStrings) {
            for (SegmentString edge1 : inputSegStrings) {
                computeIntersects(edge0, edge1);
            }
        }
    }

    private void computeIntersects(SegmentString segmentString, SegmentString segmentString2) {
        SegmentString e0 = segmentString;
        SegmentString e1 = segmentString2;
        Coordinate[] pts0 = e0.getCoordinates();
        Coordinate[] pts1 = e1.getCoordinates();
        for (int i0 = 0; i0 < pts0.length - 1; i0++) {
            for (int i1 = 0; i1 < pts1.length - 1; i1++) {
                this.segInt.processIntersections(e0, i0, e1, i1);
            }
        }
    }
}
